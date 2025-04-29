package com.yanfalcao.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.yanfalcao.database.model.ProductEntity
import com.yanfalcao.database.model.ProductRelation
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipe: ProductEntity): Long

    @Update
    suspend fun update(recipe: ProductEntity): Int

    @Transaction
    @Query("SELECT * FROM product ORDER BY created_at DESC")
    fun findAll(): Flow<List<ProductRelation>>

    @Transaction
    @Query("SELECT * FROM product WHERE id = :id")
    fun findById(id: String): Flow<ProductRelation>

    @Transaction
    @Query("SELECT * FROM product WHERE name LIKE '%' || :name || '%' ORDER BY created_at DESC")
    suspend fun findByName(name: String): List<ProductRelation>

    @Delete
    suspend fun delete(product: ProductEntity)
}