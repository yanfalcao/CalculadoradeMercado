package com.yanfalcao.data.extensions

import com.yanfalcao.database.model.ProductEntity
import com.yanfalcao.model.ItemComparison
import com.yanfalcao.model.Product
import com.yanfalcao.model.util.BaseUnits
import com.yanfalcao.model.util.EntityUnit
import com.yanfalcao.model.util.Length
import com.yanfalcao.model.util.Mass
import com.yanfalcao.model.util.Measure
import com.yanfalcao.model.util.Volume
import kotlinx.datetime.Instant

fun Product.toEntity(): ProductEntity {
    return ProductEntity(
        id = this.id,
        name = this.name,
        entity = this.measureComparison.units.entityName(),
        measure = this.measureComparison.amount.toFloat(),
        baseUnit = this.measureComparison.units.baseUnitName,
        createdAt = this.createdAt.epochSeconds
    )
}

fun ProductEntity.toModel(itens: List<ItemComparison>): Product {
    val baseUnits = try {
        when(BaseUnits.getEntityUnit(entity)) {
            EntityUnit.Length -> Length.getUnit(this.baseUnit)
            EntityUnit.Mass -> Mass.getUnit(this.baseUnit)
            EntityUnit.Volume -> Volume.getUnit(this.baseUnit)
        }
    } catch (e: IllegalArgumentException) {
        Length.meters
    }

    return Product(
        id = this.id,
        name = this.name,
        measureComparison = Measure(this.measure.toDouble(), baseUnits),
        itens = itens,
        createdAt = Instant.fromEpochSeconds(createdAt)
    )
}