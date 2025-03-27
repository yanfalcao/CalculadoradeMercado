@file:OptIn(ExperimentalUuidApi::class)

package com.yanfalcao.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Entity(tableName = "item_comparison")
data class ItemComparisonEntity(
    @PrimaryKey
    val id: String = Uuid.random().toString(),
    @ColumnInfo("product_id")
    val productId: String,
    @ColumnInfo("cart_id")
    val cartId: String?,
    val brand: String,
    val store: String?,
    @ColumnInfo("unit_price")
    val unitPrice: Float,
    val quantity: Int,
    val unit: String,
    val size: Float,
)
