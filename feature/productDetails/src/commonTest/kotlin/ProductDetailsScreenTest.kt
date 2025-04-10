@file:OptIn(ExperimentalTestApi::class)

import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import com.yanfalcao.model.ItemComparison
import com.yanfalcao.model.Product
import com.yanfalcao.model.util.Measure
import com.yanfalcao.model.util.Volume
import com.yanfalcao.productDetails.ProductDetailsScreen
import com.yanfalcao.productDetails.state.ProductDetailsVS
import kotlin.test.Test

class ProductDetailsScreenTest {
    @Test
    fun checkEmptyProductScreen() = runComposeUiTest {
        setContent {
            val hostState = SnackbarHostState()

            ProductDetailsScreen(
                state = ProductDetailsVS(
                    product = Product(),
                ),
                handleIntent = {},
                snackbarHostState = hostState,
                onBack = {}
            )
        }

        onNodeWithText("Digite o nome do produto").assertExists()
        onNodeWithText("Massa").assertExists()
        onNodeWithText("100").assertExists()
        onNodeWithText("grama (g)").assertExists()
        onNodeWithText("Vamos comparar para economizar").assertExists()
    }

    @Test
    fun checkProductScreenWithProducts() = runComposeUiTest {
        val product = Product(
            name = "Leite",
            measureComparison = Measure(1.0, Volume.liter),
            itens = listOf(
                ItemComparison(
                    id = "1",
                    brand = "Leite Integral",
                    measure = Measure(1.0, Volume.liter),
                    unitPrice = 10f,
                    amount = 1
                ),
                ItemComparison(
                    id = "2",
                    brand = "Leite Desnatado",
                    measure = Measure(1.0, Volume.liter),
                    unitPrice = 8f,
                    amount = 1
                ),
            ),
        )

        setContent {
            val hostState = SnackbarHostState()

            ProductDetailsScreen(
                state = ProductDetailsVS(
                    product = product,
                ),
                handleIntent = {},
                snackbarHostState = hostState,
                onBack = {}
            )
        }

        onNodeWithText(product.name).assertExists()
        onNodeWithText("Volume").assertExists()
        onNodeWithText("1").assertExists()
        onNodeWithText("litro (l)").assertExists()

        onNodeWithText("Leite Integral").assertExists()
        onNodeWithText("Total: 1l").assertExists()
        onNodeWithText("R$ 10,00 / 1l").assertExists()
        onNodeWithText("20% mais caro")

        onNodeWithText("Leite Desnatado").assertExists()
        onNodeWithText("Total: 1l").assertExists()
        onNodeWithText("R$ 8,00 / 1l").assertExists()
    }
}