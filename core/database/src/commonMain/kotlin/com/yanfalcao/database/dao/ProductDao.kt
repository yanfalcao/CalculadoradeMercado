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
    @Query("SELECT * FROM product WHERE product.id = :id")
    fun findById(id: String): ProductRelation?

    @Transaction
    @Query("SELECT * FROM product ORDER BY created_at DESC LIMIT :limit")
    fun findLimited(limit: Int): List<ProductRelation>

    @Transaction
    @Query("SELECT * FROM product")
    fun findAll(): List<ProductRelation>

    @Delete
    fun delete(product: ProductEntity)
}