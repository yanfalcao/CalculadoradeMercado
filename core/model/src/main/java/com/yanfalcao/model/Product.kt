package com.yanfalcao.model

import com.yanfalcao.model.util.Measure
import com.yanfalcao.model.util.BaseUnits
import java.util.Date

data class Product(
    val id: String,
    val name: String,
    val measureComparison: Measure<BaseUnits>,
    val itens: List<ItemComparison>,
    val createdAt: Date,
)
