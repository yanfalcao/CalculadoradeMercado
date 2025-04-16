package com.yanfalcao.productDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
            is ProductDetailsIntent.CreateProduct -> {}
            is ProductDetailsIntent.UndoAction -> TODO()
            is ProductDetailsIntent.RemoveLastUndo -> TODO()
            is ProductDetailsIntent.EditProduct -> editProduct(intent.product)
            is ProductDetailsIntent.EditState -> editState(intent.state)
            is ProductDetailsIntent.UpdateProduct -> updateProduct(intent.product)
            is ProductDetailsIntent.UpgradeItem -> TODO()
            is ProductDetailsIntent.OpenItemToCreate -> openItem()
            is ProductDetailsIntent.OpenItemToEdit -> openItem(intent.item)
            is ProductDetailsIntent.RemoveItem -> TODO()
            is ProductDetailsIntent.CloseItemEdit -> closeItem()
            is ProductDetailsIntent.EditItem -> editItem(intent.state)
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

    private fun editProduct(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            _productViewState.value = _productViewState.value.copy(
                product = product.copy(
                    itens = product.adjustItensToMeasureComparison()
                )
            )
        }
    }

    private fun editState(state: ProductDetailsVS) {
        viewModelScope.launch(Dispatchers.IO) {
            // Parse the amountComparison to Double, if it fails, use the default value
            val amountComparison: Double = try {
                state.amountComparison.toDouble()
            } catch (e: NumberFormatException) {
                state.product.measureComparison.amount
            }

            // Update the product view state with the new amountComparison
            _productViewState.value = state.copy(
                product = state.product.copy(
                    measureComparison = Measure(
                        amountComparison,
                        state.product.measureComparison.units
                    )
                )
            )
        }
    }

    private fun updateProduct(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            if(_productViewState.value.product.name.isNotEmpty()) {
                productRepository.updateProduct(product)
            }
        }
    }

    private fun openItem(itemComparison: ItemComparison? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            if(itemComparison != null) {
                _productViewState.value = _productViewState.value.copy(
                    itemBrand = itemComparison.brand,
                    itemPrice = itemComparison.totalPrice.toDouble().moneyStringFormat(),
                    itemStore = itemComparison.store ?: "",
                    itemAmount = itemComparison.amount.toString(),
                    itemBaseUnit = itemComparison.measure.units,
                    itemAmountComparison = itemComparison.measure.amountFormatted()
                )
            }

            EventManager.triggerEvent(EventManager.AppEvent.OpenBottomSheet)
        }
    }

    private fun closeItem() {
        viewModelScope.launch(Dispatchers.IO) {
            _productViewState.value = _productViewState.value.copy(
                itemBrand = "",
                itemPrice = "",
                itemStore = "",
                itemAmount = "",
                itemBaseUnit = _productViewState.value.product.measureComparison.units,
                itemAmountComparison = ""
            )

            EventManager.triggerEvent(EventManager.AppEvent.CloseBottomSheet)
        }
    }
}