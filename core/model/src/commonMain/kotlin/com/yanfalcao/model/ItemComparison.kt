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
    fun getPriceByAmountComparison(measureComparison: Measure<BaseUnits>): String {
        try {
            // Convert the total price to the same unit as the measureComparison
            val amountItem = measure.amount * measure.units `in` measureComparison.units
            val amountComparison = measureComparison.amount
            val unitPrice = totalPrice / amount

            // Calculate the price per unit of the comparison measure
            val priceByAmountComparison = (unitPrice * amountComparison) / amountItem

            return priceByAmountComparison.moneyStringFormat()
        } catch (e: Exception) {
            e.printStackTrace()
            return "0.00"
        }
    }
}
