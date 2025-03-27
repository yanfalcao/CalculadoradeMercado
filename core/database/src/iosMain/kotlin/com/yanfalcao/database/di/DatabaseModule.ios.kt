package com.yanfalcao.database.di

import com.yanfalcao.database.AppDatabase
import com.yanfalcao.database.getAppDatabase
import com.yanfalcao.database.getDatabaseBuilder
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun plataformModule(): Module = module {
    single<AppDatabase> {
        val builder = getDatabaseBuilder()
        getAppDatabase(builder)
    }
}