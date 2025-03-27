package org.yanfalcao.calculadorademercado

import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.yanfalcao.product.navigation.PRODUCT_ROUTE
import com.yanfalcao.designsystem.theme.AppTheme
import com.yanfalcao.product.navigation.productScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

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