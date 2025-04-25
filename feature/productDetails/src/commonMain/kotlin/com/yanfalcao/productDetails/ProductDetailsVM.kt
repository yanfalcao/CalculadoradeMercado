package com.yanfalcao.productDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yanfalcao.data.repository.ItemRepository
import com.yanfalcao.data.repository.ProductRepository
import com.yanfalcao.designsystem.util.EventManager
import com.yanfalcao.model.ItemComparison
import com.yanfalcao.model.Product
import com.yanfalcao.model.extension.moneyStringFormat
import com.yanfalcao.model.util.Measure
import com.yanfalcao.productDetails.state.ProductDetailsIntent
import com.yanfalcao.productDetails.state.ProductDetailsVS
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductDetailsVM(
    private val productId: String? = null,
    private val productRepository: ProductRepository,
    private val itemRepository: ItemRepository,
) : ViewModel() {
    private val _productViewState = MutableStateFlow(ProductDetailsVS(
        product = Product()
    ))
    val productViewState: StateFlow<ProductDetailsVS> = _productViewState

    init {
        handleIntent(ProductDetailsIntent.LoadProduct(productId))
    }

    fun handleIntent(intent: ProductDetailsIntent) {
        when (intent) {
            is ProductDetailsIntent.LoadProduct -> loadProduct(intent.productId)
            is ProductDetailsIntent.SaveProduct -> saveProduct()
            is ProductDetailsIntent.UndoAction -> TODO()
            is ProductDetailsIntent.RemoveLastUndo -> TODO()
            is ProductDetailsIntent.RemoveItem -> TODO()
            is ProductDetailsIntent.EditProduct -> editProduct(intent.state)
            is ProductDetailsIntent.EditItem -> editItem(intent.state)
            is ProductDetailsIntent.UpgradeItem -> updateItem()
            is ProductDetailsIntent.OpenItemToCreate -> openItem()
            is ProductDetailsIntent.OpenItemToEdit -> openItem(intent.item)
            is ProductDetailsIntent.CloseItemEdit -> closeItem()
        }
    }

    private fun saveProduct() {
        viewModelScope.launch(Dispatchers.IO) {
            val product = _productViewState.value.product

            if(product.name.isNotEmpty()
            ) {
                if(productId.isNullOrEmpty()) {
                    productRepository.saveProduct(product)
                } else {
                    productRepository.updateProduct(product)
                }

                EventManager.triggerEvent(EventManager.AppEvent.CloseScreen)
            } else {
                _productViewState.value = _productViewState.value.copy(
                    checkProductFormat = true
                )
            }
        }
    }

    private fun loadProduct(productId: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            if(productId != null) {
                productRepository.findProductById(productId).collect { product ->
                    _productViewState.value = _productViewState.value.copy(
                        product = product,
                        amountComparison = product.measureComparison.amountFormatted()
                    )
                }
            }
        }
    }

    private fun editItem(item: ProductDetailsVS) {
        viewModelScope.launch(Dispatchers.IO) {
            _productViewState.value = item
        }
    }

    private fun editProduct(state: ProductDetailsVS) {
        viewModelScope.launch(Dispatchers.IO) {
            // Parse the amountComparison to Double, if it fails, use the default value
            val amountComparison: Double = try {
                state.amountComparison.toDouble()
            } catch (e: NumberFormatException) {
                state.product.measureComparison.amount
            }

            _productViewState.value = state.copy(
                product = state.product.copy(
                    itens = state.product.adjustItensToMeasureComparison(),
                    measureComparison = Measure(
                        amountComparison,
                        state.product.measureComparison.units
                    )
                )
            )
        }
    }

    private fun updateItem() {
        viewModelScope.launch(Dispatchers.IO) {
            val state = _productViewState.value
            val itemId = state.itemId
            var itemComparison = if(itemId != null) {
                state.product.itens.find { it.id == itemId } ?: ItemComparison()
            } else {
                ItemComparison()
            }
            val store = state.itemStore.ifEmpty { null }

            if(state.isItemBrandValid()
                && state.isItemBrandValid()
                && state.isItemAmountValid()
                && state.isItemPriceValid()
            ) {
                itemComparison = itemComparison.copy(
                    totalPrice = state.itemPrice.replace(",", ".").toFloat(),
                    amount = state.itemAmount.replace(",", ".").toDouble().toInt(),
                    brand = state.itemBrand,
                    store = store,
                    measure = Measure(
                        state.itemAmountComparison.replace(",", ".").toDouble(),
                        state.itemBaseUnit
                    )
                )

                if(itemId.isNullOrEmpty()) {
                    _productViewState.value = _productViewState.value.copy(
                        product = _productViewState.value.product.copy(
                            itens = _productViewState.value.product.itens + itemComparison
                        )
                    )
                } else {
                    _productViewState.value = _productViewState.value.copy(
                        product = _productViewState.value.product.copy(
                            itens = _productViewState.value.product.itens.map {
                                if(it.id == itemId) { itemComparison } else { it }
                            }
                        )
                    )
                }

                closeItem()
            } else {
                _productViewState.value = _productViewState.value.copy(
                    checkItemFormat = true
                )
            }
        }
    }

    private fun openItem(itemComparison: ItemComparison? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            if(itemComparison != null) {
                _productViewState.value = _productViewState.value.copy(
                    itemId = itemComparison.id,
                    itemBrand = itemComparison.brand,
                    itemPrice = itemComparison.totalPrice.toDouble().moneyStringFormat(),
                    itemStore = itemComparison.store ?: "",
                    itemAmount = itemComparison.amount.toString(),
                    itemBaseUnit = itemComparison.measure.units,
                    itemAmountComparison = itemComparison.measure.amountFormatted(),
                    checkItemFormat = false,
                )
            }

            EventManager.triggerEvent(EventManager.AppEvent.OpenBottomSheet)
        }
    }

    private fun closeItem() {
        viewModelScope.launch(Dispatchers.IO) {
            _productViewState.value = _productViewState.value.copy(
                itemId = null,
                itemBrand = "",
                itemPrice = "",
                itemStore = "",
                itemAmount = "",
                itemBaseUnit = _productViewState.value.product.measureComparison.units,
                itemAmountComparison = "",
                checkItemFormat = false,
            )

            EventManager.triggerEvent(EventManager.AppEvent.CloseBottomSheet)
        }
    }
}