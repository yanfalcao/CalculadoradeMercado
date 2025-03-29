package com.yanfalcao.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.yanfalcao.database.model.ProductEntity
import com.yanfalcao.database.model.ProductRelation

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recipe: ProductEntity): Long

    @Transaction
    @Query("SELECT * FROM product")
    fun findAll(): List<ProductRelation>

    @Delete
    fun delete(product: ProductEntity)
}