package com.yanfalcao.data.extensions

import com.yanfalcao.database.model.ItemComparisonEntity
import com.yanfalcao.database.model.ItemSimpleEntity
import com.yanfalcao.model.ItemComparison
import com.yanfalcao.model.ItemSimple
import com.yanfalcao.model.util.BaseUnits
import com.yanfalcao.model.util.EntityUnit
import com.yanfalcao.model.util.Length
import com.yanfalcao.model.util.Mass
import com.yanfalcao.model.util.Measure
import com.yanfalcao.model.util.Volume

fun ItemSimple.toEntity(cartId: String): ItemSimpleEntity {
    return ItemSimpleEntity(
        id = this.id,
        cartId = cartId,
        name = this.name,
        unitPrice = this.totalPrice * this.amount,
        amount = this.amount
    )
}

fun ItemSimpleEntity.toModel(): ItemSimple {
    return ItemSimple(
        id = this.id,
        name = this.name,
        totalPrice = this.unitPrice / this.amount,
        amount = this.amount
    )
}

fun ItemComparison.toEntity(productId: String, cartId: String? = null): ItemComparisonEntity {
    return ItemComparisonEntity(
        id = this.id,
        productId = productId,
        cartId = cartId,
        brand = this.brand,
        store = this.store,
        unitPrice = this.totalPrice / this.amount,
        amount = this.amount,
        baseUnit = this.measure.units.baseUnitName,
        measure = this.measure.amount.toFloat()
    )
}

fun ItemComparisonEntity.toModel(entity: String): ItemComparison {
    val baseUnits = try {
        when(BaseUnits.getEntityUnit(entity)) {
            EntityUnit.Length -> Length.getUnit(this.baseUnit)
            EntityUnit.Mass -> Mass.getUnit(this.baseUnit)
            EntityUnit.Volume -> Volume.getUnit(this.baseUnit)
        }
    } catch (e: IllegalArgumentException) {
        Length.meters
    }

    return ItemComparison(
        id = this.id,
        totalPrice = this.unitPrice * this.amount,
        amount = this.amount,
        brand = this.brand,
        store = this.store,
        measure = Measure(this.measure.toDouble(), baseUnits)
    )
}