@file:OptIn(ExperimentalUuidApi::class)

package com.yanfalcao.model

import com.yanfalcao.model.extension.moneyStringFormat
import com.yanfalcao.model.util.Measure
import com.yanfalcao.model.util.BaseUnits
import com.yanfalcao.model.util.Mass
import com.yanfalcao.model.util.times
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class ItemComparison(
    override val id: String = Uuid.random().toString(),
    override val totalPrice: Float = 0.0f,
    override val amount: Int = 0,
    val brand: String = "",
    val store: String? = null,
    val measure: Measure<BaseUnits> = Measure(0.0, Mass.gram),
) : Item() {
    fun getPriceByAmount(measureComparison: Measure<BaseUnits>): Double {
        try {
            // Convert the total price to the same unit as the measureComparison
            val amountItem = measure.amount * measure.units `in` measureComparison.units
            val amountComparison = measureComparison.amount
            val unitPrice = totalPrice / amount

            // Calculate the price per unit of the comparison measure
            return (unitPrice * amountComparison) / amountItem
        } catch (e: Exception) {
            e.printStackTrace()
            return 0.00
        }
    }

    fun getPriceByAmountString(measureComparison: Measure<BaseUnits>): String {
        return getPriceByAmount(measureComparison).moneyStringFormat()
    }

    override fun equals(other: Any?): Boolean {
        if (other == null || this::class != other::class) return false

        other as ItemComparison

        if (id != other.id) return false
        if (totalPrice != other.totalPrice) return false
        if (amount != other.amount) return false
        if (brand != other.brand) return false
        if (store != other.store) return false
        if (!measure.equals(other.measure)) return false

        return true
    }
}
