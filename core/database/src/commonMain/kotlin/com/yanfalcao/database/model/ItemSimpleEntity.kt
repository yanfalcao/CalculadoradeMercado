@file:OptIn(ExperimentalUuidApi::class)

package com.yanfalcao.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Entity(tableName = "item_simple")
data class ItemSimpleEntity(
    @PrimaryKey
    val id: String = Uuid.random().toString(),
    @ColumnInfo("cart_id")
    val cartId: String,
    val name: String,
    @ColumnInfo("unit_price")
    val unitPrice: Float,
    val amount: Int,
)
