package com.yanfalcao.productDetails.state

import com.yanfalcao.model.ItemComparison
import com.yanfalcao.model.Product

data class ProductDetailsVS(
    val product: Product,
    val deletedItens: MutableList<ItemComparison> = mutableListOf(),
    val undoQueue: MutableList<ProductDetailsIntent> = mutableListOf(),
    val amountComparison: String = product.measureComparison.amountFormatted(),
    val checkProductFormat: Boolean = false,
)
