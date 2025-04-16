package com.yanfalcao.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.yanfalcao.database.model.ItemComparisonEntity

@Dao
interface ItemComparisonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg item: ItemComparisonEntity)

    @Update
    fun update(vararg item: ItemComparisonEntity)

    @Transaction
    @Query("SELECT * FROM item_comparison")
    fun findAll(): List<ItemComparisonEntity>

    @Query("DELETE FROM item_comparison WHERE product_id = :productId")
    fun deleteByProductId(productId: String): Int
}