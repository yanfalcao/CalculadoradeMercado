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
    @ColumnInfo("measurement_unit")
    val measurementUnit: String,
    @ColumnInfo("comparison_size")
    val comparisonSize: Float,
    @ColumnInfo("comparison_unit")
    val comparisonUnit: String,
    @ColumnInfo("created_at")
    val createdAt: Long,
)
