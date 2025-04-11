package com.yanfalcao.productDetails.state

import com.yanfalcao.model.Product

data class ProductDetailsVS(
    val product: Product,
    val undoQueue: MutableList<ProductDetailsIntent> = mutableListOf(),
    val amountComparison: String = product.measureComparison.amountFormatted()
)
