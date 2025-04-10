package com.yanfalcao.productDetails.extensions

import com.yanfalcao.model.Product
import com.yanfalcao.model.util.BaseUnits
import com.yanfalcao.model.util.Measure

fun Product.copyBaseUnitComparison(
    baseUnits: BaseUnits
): Product {
    return this.copy(
        id = this.id,
        measureComparison = Measure(
            units = baseUnits,
            amount = this.measureComparison.amount
        )
    )
}