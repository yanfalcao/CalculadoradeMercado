package org.yanfalcao.calculadorademercado.di

import com.yanfalcao.database.di.databaseModule
import com.yanfalcao.product.di.productModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(
            productModule,
            databaseModule
        )
    }
}