package com.yanfalcao.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.yanfalcao.database.model.ItemSimpleEntity

@Dao
interface ItemSimpleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg item: ItemSimpleEntity)

    @Transaction
    @Query("SELECT * FROM item_simple")
    suspend fun findAll(): List<ItemSimpleEntity>

    @Query("DELETE FROM item_simple WHERE cart_id = :cartId")
    suspend fun deleteByCartId(cartId: String): Int
}