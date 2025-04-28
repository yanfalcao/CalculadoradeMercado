package com.yanfalcao.productDetails.state

import com.yanfalcao.designsystem.util.Validation
import com.yanfalcao.model.ItemComparison
import com.yanfalcao.model.Product
import com.yanfalcao.model.util.BaseUnits

data class ProductDetailsVS(
    val product: Product,
    val deletedItens: MutableList<ItemComparison> = mutableListOf(),
    val undoQueue: MutableList<ProductDetailsIntent> = mutableListOf(),
    val amountComparison: String = product.measureComparison.amountFormatted(),
    val itemId: String? = null,
    val itemPrice: String = "",
    val itemAmount: String = "",
    val itemBrand: String = "",
    val itemStore: String = "",
    val itemBaseUnit: BaseUnits = product.measureComparison.units,
    val itemAmountComparison: String = "",
    val checkItemFormat: Boolean = false,
    val checkProductFormat: Boolean = false,
) {
    fun isItemPriceValid(): Boolean {
        return Validation.validNumber(itemPrice)
    }

    fun isItemAmountValid(): Boolean {
        return Validation.validNumber(itemAmount)
    }

    fun isItemBrandValid(): Boolean {
        return Validation.validString(itemBrand)
    }

    fun isItemAmountComparisonValid(): Boolean {
        return Validation.validNumber(itemAmountComparison)
    }
}
