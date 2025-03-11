package com.yanfalcao.designsystem.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.yanfalcao.designsystem.ProductRoute

const val PRODUCT_ROUTE = "product_screen"

fun NavController.navigateToProduct(popUp: Boolean = false) {
    val destination = currentBackStackEntry?.destination?.route
    val route = PRODUCT_ROUTE

    if (!destination.isNullOrEmpty() && !destination.equals(route)) {
        this.navigate(route) {
            if (popUp) {
                popUpTo(destination) {
                    inclusive = true
                }
            }
        }
    }
}

fun NavGraphBuilder.productScreen(
) {
    composable(route = PRODUCT_ROUTE) {
        ProductRoute()
    }
}