package com.yanfalcao.database.model

import androidx.room.Embedded
import androidx.room.Relation

data class ProductRelation(
    @Embedded
    val product: ProductEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "product_id"
    )
    val itemComparison: List<ItemComparisonEntity>,
)
