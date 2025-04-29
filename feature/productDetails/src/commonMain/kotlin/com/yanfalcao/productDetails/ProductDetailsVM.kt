package com.yanfalcao.productDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yanfalcao.data.repository.ItemRepository
import com.yanfalcao.data.repository.ProductRepository
import com.yanfalcao.designsystem.util.EnumSnackEvent
import com.yanfalcao.designsystem.util.EventManager
import com.yanfalcao.designsystem.util.EventManager.AppEvent.ShowSnackbar
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
            is ProductDetailsIntent.UndoAction -> undoAction()
            is ProductDetailsIntent.RemoveLastUndo -> removeLastUndo()
            is ProductDetailsIntent.RemoveItem -> removeItem(intent.item)
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

                    val deletedItens = _productViewState.value.deletedItens
                    deletedItens.forEach {
                        itemRepository.deleteItem(it, productId)
                    }
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
                        amountComparison = product.measureComparison.amountFormatted(),
                        itemBaseUnit = product.measureComparison.units,
                    )
                }
            }
        }
    }

    private fun undoAction() {
        viewModelScope.launch(Dispatchers.IO) {
            val undoQueue = _productViewState.value.undoQueue
            if(undoQueue.isNotEmpty()) {
                val last = undoQueue.removeLast()
                when (last) {
                    is ProductDetailsIntent.UpgradeItem -> {
                        val deletedItens = _productViewState.value.deletedItens
                        if (deletedItens.isNotEmpty()) {
                            val item = deletedItens.removeLast()
                            _productViewState.value = _productViewState.value.copy(
                                product = _productViewState.value.product.copy(
                                    itens = _productViewState.value.product.itens + item
                                ),
                                deletedItens = deletedItens,
                            )
                        }
                    }
                    else -> {handleIntent(last)}
                }

            }
        }
    }

    private fun removeLastUndo() {
        viewModelScope.launch(Dispatchers.IO) {
            val undoQueue = _productViewState.value.undoQueue
            if (undoQueue.isNotEmpty()) {
                undoQueue.removeLast()
            }
        }
    }

    private fun removeItem(itemComparison: ItemComparison) {
        viewModelScope.launch(Dispatchers.IO) {
            val product = _productViewState.value.product
            val filteredItens = product.itens.filter {
                it.id != itemComparison.id
            }
            val deletedItens = _productViewState.value.deletedItens
            deletedItens.add(itemComparison)

            _productViewState.value = _productViewState.value.copy(
                deletedItens = deletedItens,
                product = product.copy(
                    itens = filteredItens,
                )
            )

            _productViewState.value.undoQueue.add(
                ProductDetailsIntent.UpgradeItem
            )

            EventManager.triggerEvent(ShowSnackbar(EnumSnackEvent.DELETE_ITEM))
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
            // Find the item in the list of items, if not found, create a new one
            var itemComparison = if(itemId != null) {
                state.product.itens.find { it.id == itemId } ?: ItemComparison()
            } else {
                ItemComparison()
            }
            // Get the store from the state, if it's empty, format to null
            val store = state.itemStore.ifEmpty { null }

            // Validate the item fields, if valid, create or update the item. Else, screen handle the error
            if(state.isItemBrandValid()
                && state.isItemBrandValid()
                && state.isItemAmountValid()
                && state.isItemPriceValid()
            ) {
                // Format the item fields to the correct types
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

                // If the itemId is null, create a new item. Else, update the item
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
                // If the item fields are not valid, set the checkItemFormat to true
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