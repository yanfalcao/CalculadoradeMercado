package extension

import com.yanfalcao.data.extensions.*
import com.yanfalcao.database.model.CartEntity
import com.yanfalcao.model.Cart
import com.yanfalcao.model.Item
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.test.Test
import kotlin.test.assertEquals

class CartExtensionTest {

    @Test
    fun cartToEntity() {
        val cart = Cart(
            id = "1",
            name = "Sample Cart",
            list = emptyList(),
            createdAt = Clock.System.now()
        )

        val entity = cart.toEntity()

        assertEquals(cart.id, entity.id)
        assertEquals(cart.name, entity.name)
        assertEquals(cart.createdAt.epochSeconds, entity.createdAt)
    }

    @Test
    fun cartEntityToModel() {
        val entity = CartEntity(
            id = "1",
            name = "Sample Cart",
            createdAt = Clock.System.now().epochSeconds
        )

        val items = emptyList<Item>()

        val model = entity.toModel(items)

        assertEquals(entity.id, model.id)
        assertEquals(entity.name, model.name)
        assertEquals(items, model.list)
        assertEquals(Instant.fromEpochSeconds(entity.createdAt), model.createdAt)
    }
}