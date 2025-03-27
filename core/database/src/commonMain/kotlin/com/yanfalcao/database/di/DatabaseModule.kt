package com.yanfalcao.database.di

import com.yanfalcao.database.AppDatabase
import com.yanfalcao.database.dao.CartDao
import com.yanfalcao.database.dao.ItemComparisonDao
import com.yanfalcao.database.dao.ItemSimpleDao
import com.yanfalcao.database.dao.ProductDao
import org.koin.core.module.Module
import org.koin.dsl.module

val databaseModule = module {
    single<CartDao> { get<AppDatabase>().cartDao() }
    single<ItemComparisonDao> { get<AppDatabase>().itemComparisonDao() }
    single<ItemSimpleDao> { get<AppDatabase>().itemSimpleDao() }
    single<ProductDao> { get<AppDatabase>().productDao() }
    includes(plataformModule())
}

expect fun plataformModule(): Module