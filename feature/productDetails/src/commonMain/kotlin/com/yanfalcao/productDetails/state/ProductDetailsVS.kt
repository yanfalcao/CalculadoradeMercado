package com.yanfalcao.productDetails.state

import com.yanfalcao.model.Product
import com.yanfalcao.model.util.BaseUnits

data class ProductDetailsVS(
    val product: Product,
    val undoQueue: MutableList<ProductDetailsIntent> = mutableListOf(),
    val amountComparison: String = product.measureComparison.amountFormatted(),
    val itemPrice: String = "",
    val itemAmount: String = "",
    val itemBrand: String = "",
    val itemStore: String = "",
    val itemBaseUnit: BaseUnits = product.measureComparison.units,
    val itemAmountComparison: String = "",
)
