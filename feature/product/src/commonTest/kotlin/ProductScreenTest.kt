@file:OptIn(ExperimentalTestApi::class)

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import com.yanfalcao.designsystem.ProductScreen
import kotlin.test.Test

class ProductScreenTest {
    @Test
    fun checkEmptyProductScreen() = runComposeUiTest {
        setContent {
            ProductScreen()
        }

        onNodeWithText("Comparativos").assertExists()
        onNodeWithContentDescription("Adicionar comparativo").assertExists()
        onNodeWithText("Adicione comparativos").assertExists()
        onNodeWithText("Vamos comparar para economizar").assertExists()
    }
}