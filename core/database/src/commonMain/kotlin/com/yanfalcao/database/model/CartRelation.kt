package com.yanfalcao.database.model

import androidx.room.Embedded
import androidx.room.Relation

data class CartRelation(
    @Embedded
    val product: CartEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "cart_id"
    )
    val itemComparison: List<ItemComparisonEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "cart_id"
    )
    val itemSimple: List<ItemSimpleEntity>
)
