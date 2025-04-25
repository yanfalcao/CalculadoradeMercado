import app.cash.turbine.test
import com.yanfalcao.data.repository.ProductRepository
import com.yanfalcao.model.ItemComparison
import com.yanfalcao.model.Product
import com.yanfalcao.model.util.Mass.Companion.gram
import com.yanfalcao.model.util.Mass.Companion.kilogram
import com.yanfalcao.model.util.Measure
import com.yanfalcao.model.util.Volume
import com.yanfalcao.productDetails.ProductDetailsVM
import com.yanfalcao.productDetails.state.ProductDetailsIntent
import dev.mokkery.answering.calls
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.matcher.any
import dev.mokkery.mock
import dev.mokkery.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class ProductDetailsVMTest {
    lateinit var repositoryProduct: ProductRepository
    lateinit var repositoryItem: ItemRepository
    lateinit var viewModel: ProductDetailsVM
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
    val product = Product(
        id = "1",
        name = "Café",
        measureComparison = Measure(
            amount = 250.0,
            units = gram
        ),
        itens = listOf(item1, item2)
    )

    @BeforeTest
    fun setup() {
        repositoryProduct = mock<ProductRepository> {}
        repositoryItem = mock<ItemRepository> {}
        every { repositoryProduct.findProducts() } calls { flowOf() }
    }

    @Test
    fun productIntent_loadProducts() = runTest {
        viewModel = ProductDetailsVM(
            productId = null,
            repositoryProduct,
            repositoryItem,
        )

        viewModel.productViewState.test {
            every { repositoryProduct.findProductById(any()) } returns flowOf(product)

            viewModel.handleIntent(ProductDetailsIntent.LoadProduct("1"))

            verify { repositoryProduct.findProductById("1") }

            // initial state
            var state = awaitItem()
            // load product intent
            state = awaitItem()

            assertEquals(product.id, state.product.id)
            assertEquals(product.name, state.product.name)
            assertEquals(product.measureComparison, state.product.measureComparison)
            assertEquals(product.itens.size, state.product.itens.size)
        }
    }

    @Test
    fun productIntent_removeItem() = runTest {
        val productFlow = MutableStateFlow(product)
        viewModel = ProductDetailsVM(
            productId = null,
            repositoryProduct,
            repositoryItem,
        )

        viewModel.productViewState.test {
            every { repositoryProduct.findProductById(any()) } returns productFlow
            every { repositoryItem.removeItemComparison(any()) } calls {
                productFlow.value = productFlow.value.copy(
                    itens = productFlow.value.itens.filter { it.id != item1.id }
                )
            }

            viewModel.handleIntent(ProductDetailsIntent.LoadProduct("1"))
            viewModel.handleIntent(ProductDetailsIntent.RemoveItem(item1))

            // initial state
            var state = awaitItem()
            // load product intent
            state = awaitItem()
            // remove item intent
            state = awaitItem()

            assertEquals(product.id, state.product.id)
            assertEquals(product.name, state.product.name)
            assertEquals(product.measureComparison, state.product.measureComparison)
            assertEquals(1, state.product.itens.size)
        }
    }

    @Test
    fun productIntent_editProduct() = runTest {
        val productFlow = MutableStateFlow(product)
        viewModel = ProductDetailsVM(
            productId = null,
            repositoryProduct,
            repositoryItem,
        )

        viewModel.productViewState.test {
            every { repositoryProduct.findProductById(any()) } returns productFlow

            viewModel.handleIntent(ProductDetailsIntent.LoadProduct("1"))
            viewModel.handleIntent(ProductDetailsIntent.EditProduct(
                product.copy(name = "Leite")
            ))

            // initial state
            var state = awaitItem()
            // load product intent
            state = awaitItem()
            // edit product intent
            state = awaitItem()

            assertEquals(product.id, state.product.id)
            assertEquals("Leite", state.product.name)
            assertEquals(product.measureComparison, state.product.measureComparison)
            assertEquals(product.itens.size, state.product.itens.size)
        }
    }

    @Test
    fun productIntent_upgradeProduct() = runTest {
        val productFlow = MutableStateFlow(product)
        viewModel = ProductDetailsVM(
            productId = null,
            repositoryProduct,
            repositoryItem,
        )

        viewModel.productViewState.test {
            val editedProduct = product.copy(
                name = "Leite",
                measureComparison = Measure(1.0, Volume.liter)
            )
            every { repositoryProduct.findProductById(any()) } returns productFlow
            every { repositoryProduct.updateProduct(any()) } calls {
                productFlow.value = editedProduct
            }

            viewModel.handleIntent(ProductDetailsIntent.LoadProduct("1"))

            // initial state
            var state = awaitItem()
            // load product intent
            state = awaitItem()
            // upgrade product intent
            state = awaitItem()

            assertEquals(editedProduct.id, state.product.id)
            assertEquals(editedProduct.name, state.product.name)
            assertEquals(editedProduct.measureComparison, state.product.measureComparison)
        }
    }

    @Test
    fun productIntent_upgradeItem() = runTest {
        val editedItem1 = item1.copy(
            brand = "Café Gourmet - Latitude 33",
            totalPrice = 45f
        )
        val productFlow = MutableStateFlow(product)
        viewModel = ProductDetailsVM(
            productId = null,
            repositoryProduct,
            repositoryItem,
        )

        viewModel.productViewState.test {
            every { repositoryProduct.findProductById(any()) } returns productFlow
            every { repositoryItem.upgradeItemComparison(any()) } calls {
                productFlow.value = productFlow.value.copy(
                    itens = listOf(editedItem1, item2)
                )
            }

            viewModel.handleIntent(ProductDetailsIntent.LoadProduct("1"))
            viewModel.handleIntent(ProductDetailsIntent.UpgradeItem(item1))

            // initial state
            var state = awaitItem()
            // load product intent
            state = awaitItem()
            // upgrade item intent
            state = awaitItem()

            assertEquals(product.id, state.product.id)
            assertEquals(product.name, state.product.name)
            assertEquals(product.measureComparison, state.product.measureComparison)
            assertEquals(2, state.product.itens.size)
            assertEquals(editedItem1.brand, state.product.itens[0].brand)
            assertEquals(editedItem1.totalPrice, state.product.itens[0].totalPrice)
        }
    }

    @Test
    fun productIntent_undoAction() = runTest {
        viewModel = ProductDetailsVM(
            productId = null,
            repositoryProduct,
            repositoryItem,
        )

        viewModel.productViewState.test {
            viewModel.handleIntent(ProductDetailsIntent.RemoveItem(item1))
            viewModel.handleIntent(ProductDetailsIntent.UndoAction)

            // initial state
            awaitItem()

            // add intent on queue
            assertEquals(1, awaitItem().undoQueue.size)
            // undo action
            assertEquals(0, awaitItem().undoQueue.size)
        }
    }
}
