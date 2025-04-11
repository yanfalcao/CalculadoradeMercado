@file:OptIn(ExperimentalUuidApi::class)

package com.yanfalcao.model

import com.yanfalcao.model.util.Measure
import com.yanfalcao.model.util.BaseUnits
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class ItemComparison(
    override val id: String = Uuid.random().toString(),
    override val totalPrice: Float,
    override val amount: Int,
    val brand: String,
    val store: String? = null,
    val measure: Measure<BaseUnits>,
) : Item() {
    fun getPriceByMeasureComparison(measureComparison: Measure<BaseUnits>) {

    }
}
