package org.yanfalcao.calculadorademercado

import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.ui.tooling.preview.Preview
import com.yanfalcao.designsystem.navigation.PRODUCT_ROUTE
import com.yanfalcao.designsystem.navigation.productScreen
import com.yanfalcao.designsystem.theme.AppTheme

@Composable
@Preview
fun App() {
    val navController = rememberNavController()

    AppTheme {
        NavHost(
            navController = navController,
            startDestination = PRODUCT_ROUTE
        ) {
            productScreen()
        }
    }
}