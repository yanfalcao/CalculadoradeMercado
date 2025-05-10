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
    private fun getCheapest(): ItemComparison? {
        return itens.minByOrNull { it.getPriceByAmount(measureComparison) }
    }

    fun isCheapest(itemId: String): Boolean {
        val cheapestItem = getCheapest()

        return cheapestItem?.let { it.id == itemId } ?: false
    }

    fun percentageDifferenceByCheapester(itemId: String): Double? {
        val cheapestItem = itens.minByOrNull { it.getPriceByAmount(measureComparison) }
        val item = itens.find { it.id == itemId }

        return if (cheapestItem != null && cheapestItem.id == itemId) {
            null
        } else {
            val itemPrice = item?.getPriceByAmount(measureComparison) ?: return null
            val cheapestPrice = cheapestItem?.getPriceByAmount(measureComparison) ?: return null

            ((itemPrice - cheapestPrice) / cheapestPrice) * 100
        }
    }

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

    override fun equals(other: Any?): Boolean {
        if (other == null || this::class != other::class) return false

        other as Product

        if (id != other.id) return false
        if (name != other.name) return false
        if (!measureComparison.equals(other.measureComparison)) return false
        if (itens.size != other.itens.size) return false

        val isEquals = other.itens.all { areAnyItensEqualTo(it) }
        if (!isEquals) return false

        return true
    }

    private fun areAnyItensEqualTo(item: ItemComparison): Boolean {
        return itens.any { it.equals(item) }
    }
}
