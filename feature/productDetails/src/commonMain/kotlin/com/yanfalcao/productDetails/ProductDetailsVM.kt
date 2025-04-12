package com.yanfalcao.productDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yanfalcao.data.repository.ProductRepository
import com.yanfalcao.model.Product
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
            is ProductDetailsIntent.OpenItemToCreate -> {}
            is ProductDetailsIntent.OpenItemToEdit -> TODO()
            is ProductDetailsIntent.RemoveItem -> TODO()
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
}