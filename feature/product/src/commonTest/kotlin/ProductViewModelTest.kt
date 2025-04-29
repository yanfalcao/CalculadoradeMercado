import app.cash.turbine.test
import com.yanfalcao.data.repository.ProductRepository
import com.yanfalcao.model.ItemComparison
import com.yanfalcao.model.Product
import com.yanfalcao.model.util.Mass.Companion.gram
import com.yanfalcao.model.util.Mass.Companion.kilogram
import com.yanfalcao.model.util.Measure
import com.yanfalcao.product.ProductViewModel
import com.yanfalcao.product.state.ProductIntent
import dev.mokkery.answering.calls
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import dev.mokkery.verify
import dev.mokkery.verifySuspend
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class ProductViewModelTest {
    lateinit var repository: ProductRepository
    lateinit var viewModel: ProductViewModel
    val item1 = ItemComparison(
        totalPrice = 15.4f,
        amount = 1,
        brand = "Marata",
        store = "Pão de Açúcar",
        measure = Measure(
            amount = 250.0,
            units = gram
        )
    )
    val item2 = ItemComparison(
        totalPrice = 35f,
        amount = 1,
        brand = "3 Corações",
        store = "Pão de Açúcar",
        measure = Measure(
            amount = 1.0,
            units = kilogram
        )
    )
    val product1 = Product(
        name = "Café",
        measureComparison = Measure(
            amount = 250.0,
            units = gram
        ),
        itens = listOf(item1, item2)
    )
    val product2 = Product(
        name = "Chocolate",
        measureComparison = Measure(
            amount = 250.0,
            units = gram
        ),
        itens = listOf(
            item1.copy(brand = "Melk", totalPrice = 20f),
            item2.copy(brand = "Nestlé")
        )
    )

    @BeforeTest
    fun setup() {
        repository = mock<ProductRepository> {}
        every { repository.findProducts() } calls { flowOf() }
    }

    @Test
    fun productIntent_loadProducts() = runTest {
        viewModel = ProductViewModel(repository)

        viewModel.productViewState.test {
            every { repository.findProducts() } returns flowOf(listOf(product1))

            assertTrue(awaitItem().isLoading)

            viewModel.handleIntent(ProductIntent.LoadProducts)

            verify { repository.findProducts() }

            val state = awaitItem()

            assertFalse(state.isLoading)
            assertTrue(state.products.isNotEmpty())
            assertEquals(1, state.products.size)
        }
    }

    @Test
    fun productIntent_removeProduct() = runTest {
        viewModel = ProductViewModel(repository)
        val productFlow = MutableStateFlow(listOf(product1.copy(), product2.copy()))

        viewModel.productViewState.test {
            every { repository.findProducts() } returns productFlow
            everySuspend { repository.removeProduct(any()) } calls {
                productFlow.value = listOf(product2.copy())
            }

            assertTrue(awaitItem().isLoading)

            viewModel.handleIntent(ProductIntent.LoadProducts)

            assertEquals(2, awaitItem().products.size)

            viewModel.handleIntent(ProductIntent.RemoveProduct(product1))

            verifySuspend { repository.removeProduct(product1) }

            assertEquals(1, awaitItem().products.size)
        }
    }

    @Test
    fun productIntent_filterProducts() = runTest {
        viewModel = ProductViewModel(repository)

        viewModel.productViewState.test {
            everySuspend { repository.findProductsByName("Café") } returns listOf(product1)

            assertTrue(awaitItem().isLoading)

            viewModel.handleIntent(ProductIntent.FilterProducts("Café"))

            val state = awaitItem()

            verifySuspend { repository.findProductsByName("Café") }
            assertEquals(1, state.products.size)
            assertEquals("Café", state.products[0].name)
        }
    }

    @Test
    fun productIntent_createProduct() = runTest {
        viewModel = ProductViewModel(repository)
        val productFlow = MutableStateFlow(listOf(product2.copy()))

        viewModel.productViewState.test {
            every { repository.findProducts() } returns productFlow
            everySuspend { repository.saveProduct(any()) } calls {
                productFlow.value = listOf(product1.copy(), product2.copy())
            }

            assertTrue(awaitItem().isLoading)

            viewModel.handleIntent(ProductIntent.LoadProducts)

            assertEquals(1, awaitItem().products.size)

            viewModel.handleIntent(ProductIntent.CreateProduct(product1))

            verifySuspend { repository.saveProduct(product1) }

            assertEquals(2, awaitItem().products.size)
        }
    }

    @Test
    fun productIntent_undoAction() = runTest {
        viewModel = ProductViewModel(repository)

        viewModel.productViewState.test {
            everySuspend { repository.removeProduct(any()) } calls {}
            everySuspend { repository.saveProduct(any()) } calls {}

            assertTrue(awaitItem().isLoading)

            viewModel.handleIntent(ProductIntent.RemoveProduct(product1))
            viewModel.handleIntent(ProductIntent.UndoAction)

            verifySuspend { repository.saveProduct(product1) }
        }
    }
}
