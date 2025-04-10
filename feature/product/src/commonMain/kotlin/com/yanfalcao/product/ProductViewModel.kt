package com.yanfalcao.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yanfalcao.data.repository.ProductRepository
import com.yanfalcao.designsystem.util.EnumSnackEvent
import com.yanfalcao.designsystem.util.EventManager
import com.yanfalcao.designsystem.util.EventManager.AppEvent
import com.yanfalcao.model.Product
import com.yanfalcao.product.state.ProductIntent
import com.yanfalcao.product.state.ProductViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel(
    private val productRepository: ProductRepository,
) : ViewModel() {
    private val _productViewState = MutableStateFlow(ProductViewState())
    val productViewState: StateFlow<ProductViewState> = _productViewState

    init {
        handleIntent(ProductIntent.LoadProducts)
    }

    fun handleIntent(intent: ProductIntent) {
        when (intent) {
            is ProductIntent.LoadProducts -> loadProducts()
            is ProductIntent.RemoveProduct -> removeProduct(intent.product)
            is ProductIntent.FilterProducts -> filterProducts(intent.text)
            is ProductIntent.OpenProductToEdit -> openProductToEdit(intent.product)
            is ProductIntent.OpenProductToCreate -> openProductToCreate()
            is ProductIntent.CreateProduct -> createProduct(intent.product)
            is ProductIntent.UndoAction -> undoAction()
            is ProductIntent.RemoveLastUndo -> removeUndoAction()
        }
    }

    private fun loadProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            _productViewState.value = _productViewState.value.copy(isLoading = true)

            productRepository.findProducts().collect { products ->
                _productViewState.value = _productViewState.value.copy(
                    isLoading = false,
                    products = products,
                    clearFilter = true
                )
            }
        }
    }

    private fun filterProducts(text: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val products = productRepository.findProductsByName(name = text)

            _productViewState.value = _productViewState.value.copy(
                isLoading = false,
                products = products,
                clearFilter = false
            )
        }
    }

    private fun undoAction() {
        viewModelScope.launch(Dispatchers.IO) {
            val undoQueue = _productViewState.value.undoQueue
            if (undoQueue.isNotEmpty()) {
                val lastIntent = undoQueue.removeLast()
                handleIntent(lastIntent)
            }
        }
    }

    private fun removeUndoAction() {
        viewModelScope.launch(Dispatchers.IO) {
            val undoQueue = _productViewState.value.undoQueue
            if (undoQueue.isNotEmpty()) {
                undoQueue.removeLast()
            }
        }
    }

    private fun removeProduct(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            productRepository.removeProduct(product)

            _productViewState.value.undoQueue.add(
                ProductIntent.CreateProduct(product)
            )

            EventManager.triggerEvent(AppEvent.ShowSnackbar(EnumSnackEvent.DELETE_PRODUCT))
        }
    }

    private fun createProduct(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            productRepository.saveProduct(product)
        }
    }

    private fun openProductToEdit(product: Product) {
        EventManager.triggerEvent(AppEvent.NavigateToProductDetail(product.id))
    }

    private fun openProductToCreate() {
        EventManager.triggerEvent(AppEvent.NavigateToProductDetail())
    }
}