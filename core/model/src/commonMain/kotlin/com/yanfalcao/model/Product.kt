@file:OptIn(ExperimentalUuidApi::class)

package com.yanfalcao.model

import com.yanfalcao.model.util.Measure
import com.yanfalcao.model.util.BaseUnits
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class Product(
    val id: String = Uuid.random().toString(),
    val name: String,
    val measureComparison: Measure<BaseUnits>,
    val itens: List<ItemComparison>,
    val createdAt: Instant = Clock.System.now(),
)
