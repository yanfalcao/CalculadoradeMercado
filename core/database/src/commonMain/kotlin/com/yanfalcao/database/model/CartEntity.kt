@file:OptIn(ExperimentalUuidApi::class)

package com.yanfalcao.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Entity(tableName = "cart")
data class CartEntity(
    @PrimaryKey
    val id: String = Uuid.random().toString(),
    val name: String,
    @ColumnInfo("created_at")
    val createdAt: Long,
)
