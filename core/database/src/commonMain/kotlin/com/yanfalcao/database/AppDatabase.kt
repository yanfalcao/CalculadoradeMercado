package com.yanfalcao.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.yanfalcao.database.dao.CartDao
import com.yanfalcao.database.dao.ItemComparisonDao
import com.yanfalcao.database.dao.ItemSimpleDao
import com.yanfalcao.database.dao.ProductDao
import com.yanfalcao.database.model.CartEntity
import com.yanfalcao.database.model.ItemComparisonEntity
import com.yanfalcao.database.model.ItemSimpleEntity
import com.yanfalcao.database.model.ProductEntity

@Database(
    version = 1,
    entities = [
        ProductEntity::class,
        CartEntity::class,
        ItemComparisonEntity::class,
        ItemSimpleEntity::class,
    ]
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun cartDao(): CartDao
    abstract fun itemComparisonDao(): ItemComparisonDao
    abstract fun itemSimpleDao(): ItemSimpleDao
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}