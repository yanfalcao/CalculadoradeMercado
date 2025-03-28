package com.yanfalcao.model

import com.yanfalcao.model.util.Measure
import com.yanfalcao.model.util.BaseUnits

data class ItemComparison(
    override val id: String,
    override val unitPrice: Float,
    override val amount: Int,
    val brand: String,
    val store: String?,
    val measure: Measure<BaseUnits>,
) : Item
