package com.yanfalcao.product.state

import com.yanfalcao.model.Product

data class ProductViewState(
    val products: List<Product> = emptyList(),
    val undoQueue: MutableList<ProductIntent> = mutableListOf(),
    val isLoading: Boolean = false,
    val clearFilter: Boolean = false
)
