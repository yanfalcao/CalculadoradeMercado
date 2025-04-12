@file:OptIn(ExperimentalUuidApi::class)

package com.yanfalcao.model

import com.yanfalcao.model.util.Measure
import com.yanfalcao.model.util.BaseUnits
import com.yanfalcao.model.util.Mass
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class Product(
    val id: String = Uuid.random().toString(),
    val name: String = "",
    val measureComparison: Measure<BaseUnits> = Measure(100.toDouble(), Mass.gram),
    var itens: List<ItemComparison> = listOf(),
    val createdAt: Instant = Clock.System.now(),
) {
    fun adjustItensToMeasureComparison(): List<ItemComparison> {
        val adjustedList = mutableListOf<ItemComparison>()
        itens.forEach { item ->
            if(item.measure.units::class == measureComparison.units::class) {
                adjustedList.add(item)
            } else {
                adjustedList.add(
                    item.copy(
                        measure = Measure(
                            amount = item.measure.amount,
                            units = measureComparison.units
                        )
                    )
                )
            }
        }

        return adjustedList
    }
}
