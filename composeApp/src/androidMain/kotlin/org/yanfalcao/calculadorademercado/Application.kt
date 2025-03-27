package org.yanfalcao.calculadorademercado

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.yanfalcao.calculadorademercado.di.initKoin

class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@AppApplication)
        }
    }
}