package com.yanfalcao.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.yanfalcao.database.model.CartEntity
import com.yanfalcao.database.model.CartRelation

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recipe: CartEntity): Long

    @Transaction
    @Query("SELECT * FROM cart WHERE cart.id = :id")
    fun findById(id: String): CartRelation?

    @Transaction
    @Query("SELECT * FROM cart ORDER BY created_at DESC LIMIT :limit")
    fun findLimited(limit: Int): List<CartRelation>

    @Transaction
    @Query("SELECT * FROM cart")
    fun findAll(): List<CartRelation>

    @Query("DELETE FROM cart")
    fun deleteAll(): Int
}