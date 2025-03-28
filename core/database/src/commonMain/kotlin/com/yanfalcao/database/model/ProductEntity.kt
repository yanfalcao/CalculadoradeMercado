@file:OptIn(ExperimentalUuidApi::class)

package com.yanfalcao.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Entity(tableName = "product")
data class ProductEntity(
    @PrimaryKey
    val id: String = Uuid.random().toString(),
    val name: String,
    val entity: String,
    val measure: Float,
    @ColumnInfo("base_unit")
    val baseUnit: String,
    @ColumnInfo("created_at")
    val createdAt: Long,
)
