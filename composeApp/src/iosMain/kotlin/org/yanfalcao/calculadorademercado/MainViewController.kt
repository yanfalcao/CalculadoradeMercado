 package org.yanfalcao.calculadorademercado

import androidx.compose.ui.window.ComposeUIViewController
import org.yanfalcao.calculadorademercado.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App()
}