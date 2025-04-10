package com.yanfalcao.productDetails.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.yanfalcao.productDetails.ProductDetailsRoute

const val PRODUCT_ID_KEY = "product_id"
const val PRODUCT_DETAILS_ROUTE = "product_details_screen/{${PRODUCT_ID_KEY}}"

fun NavController.navigateToProductDetails(
    productId: String? = null,
) {
    val destination = currentBackStackEntry?.destination?.route
    val route = PRODUCT_DETAILS_ROUTE
    val routeWithParam = route.replace(
        oldValue = "{${PRODUCT_ID_KEY}}",
        newValue = productId ?: "empty",
    )

    if (!destination.isNullOrEmpty() && !route.contains(destination)) {
        this.navigate(route = routeWithParam)
    }
}

fun NavGraphBuilder.productDetailsScreen(
    onBack: () -> Unit,
) {
    composable(
        PRODUCT_DETAILS_ROUTE,
        arguments = listOf(
            navArgument(PRODUCT_ID_KEY) {
                type = NavType.StringType
            },
        )
    ) {
        it.arguments?.getString(PRODUCT_ID_KEY)?.let { id ->
            val productId = if(id == "empty") null else id
            ProductDetailsRoute(
                productId = productId,
                onBackClick = onBack,
            )
        }

    }
}