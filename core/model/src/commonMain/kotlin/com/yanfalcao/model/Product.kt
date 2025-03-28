package com.yanfalcao.model

import com.yanfalcao.model.util.Measure
import com.yanfalcao.model.util.BaseUnits
import kotlinx.datetime.Instant

data class Product(
    val id: String,
    val name: String,
    val measureComparison: Measure<BaseUnits>,
    val itens: List<ItemComparison>,
    val createdAt: Instant,
)
