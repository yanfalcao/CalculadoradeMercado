package org.yanfalcao.calculadorademercado.di

import com.yanfalcao.data.di.dataModule
import com.yanfalcao.database.di.databaseModule
import com.yanfalcao.product.di.productModule
import com.yanfalcao.productDetails.di.productDetailsModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(
            productModule,
            productDetailsModule,
            databaseModule,
            dataModule
        )
    }
}