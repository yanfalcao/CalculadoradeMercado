@file:OptIn(ExperimentalTestApi::class)

import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import com.yanfalcao.model.Product
import com.yanfalcao.model.util.Length
import com.yanfalcao.model.util.Measure
import com.yanfalcao.product.ProductScreen
import com.yanfalcao.product.state.ProductViewState
import kotlin.test.Test

class ProductScreenTest {
    @Test
    fun checkEmptyProductScreen() = runComposeUiTest {
        setContent {
            val hostState = SnackbarHostState()

            ProductScreen(
                state = ProductViewState(
                    isLoading = false,
                    products = emptyList(),
                ),
                handleIntent = {},
                snackbarHostState = hostState,
            )
        }

        onNodeWithText("Comparativos").assertExists()
        onNodeWithContentDescription("Adicionar comparativo").assertExists()
        onNodeWithText("Adicione comparativos").assertExists()
        onNodeWithText("Vamos comparar para economizar").assertExists()
    }

    @Test
    fun checkProductScreenWithProducts() = runComposeUiTest {
        val products = listOf(
            Product(
                name = "Produto 1",
                measureComparison = Measure(100.0, Length.centimeters),
                itens = listOf(),
            ),
            Product(
                name = "Produto 2",
                measureComparison = Measure(100.0, Length.centimeters),
                itens = listOf(),
            )
        )

        setContent {
            val hostState = SnackbarHostState()

            ProductScreen(
                state = ProductViewState(
                    isLoading = false,
                    products = products,
                ),
                handleIntent = {},
                snackbarHostState = hostState,
            )
        }

        onNodeWithText("Produto 1").assertExists()
        onNodeWithText("Produto 2").assertExists()
    }
}