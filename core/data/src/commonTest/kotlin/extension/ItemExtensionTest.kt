package extension

import com.yanfalcao.data.extensions.*
import com.yanfalcao.database.model.ItemComparisonEntity
import com.yanfalcao.database.model.ItemSimpleEntity
import com.yanfalcao.model.ItemComparison
import com.yanfalcao.model.ItemSimple
import com.yanfalcao.model.util.BaseUnits
import com.yanfalcao.model.util.Length
import com.yanfalcao.model.util.Measure
import kotlin.test.Test
import kotlin.test.assertEquals

class ItemExtensionTest {

    @Test
    fun test() {
        assertEquals(1, 1)
    }

    @Test
    fun itemSimpleToEntity() {
        val itemSimple = ItemSimple(
            id = "1",
            name = "Sample Item",
            unitPrice = 10.0f,
            amount = 2
        )

        val cartId = "cart123"

        val entity = itemSimple.toEntity(cartId)

        assertEquals(itemSimple.id, entity.id)
        assertEquals(cartId, entity.cartId)
        assertEquals(itemSimple.name, entity.name)
        assertEquals(itemSimple.unitPrice, entity.unitPrice)
        assertEquals(itemSimple.amount, entity.amount)
    }

    @Test
    fun itemSimpleEntityToModel() {
        val entity = ItemSimpleEntity(
            id = "1",
            cartId = "cart123",
            name = "Sample Item",
            unitPrice = 10.0f,
            amount = 2
        )

        val model = entity.toModel() as ItemSimple

        assertEquals(entity.id, model.id)
        assertEquals(entity.name, model.name)
        assertEquals(entity.unitPrice, model.unitPrice)
        assertEquals(entity.amount, model.amount)
    }

    @Test
    fun itemComparisonToEntity() {
        val measure: Measure<BaseUnits> = Measure(amount = 1.0, units = Length.meters)
        val model = ItemComparison(
            id = "1",
            unitPrice = 10.0f,
            amount = 2,
            brand = "BrandA",
            store = "StoreA",
            measure = measure
        )

        val productId = "product123"
        val cartId = "cart123"

        val entity = model.toEntity(productId, cartId)

        assertEquals(model.id, entity.id)
        assertEquals(productId, entity.productId)
        assertEquals(cartId, entity.cartId)
        assertEquals(model.brand, entity.brand)
        assertEquals(model.store, entity.store)
        assertEquals(model.unitPrice, entity.unitPrice)
        assertEquals(model.amount, entity.amount)
        assertEquals(model.measure.units.baseUnitName, entity.baseUnit)
        assertEquals(model.measure.amount.toFloat(), entity.measure)
    }

    @Test
    fun itemComparisonEntityToModel() {
        val entity = ItemComparisonEntity(
            id = "1",
            productId = "product123",
            cartId = "cart123",
            brand = "BrandA",
            store = "StoreA",
            unitPrice = 10.0f,
            amount = 2,
            baseUnit = "centimeter",
            measure = 15.0f
        )

        val model = entity.toModel("Length") as ItemComparison

        assertEquals(entity.id, model.id)
        assertEquals(entity.brand, model.brand)
        assertEquals(entity.store, model.store)
        assertEquals(entity.unitPrice, model.unitPrice)
        assertEquals(entity.amount, model.amount)
        assertEquals(entity.measure.toDouble(), model.measure.amount)
        assertEquals(entity.baseUnit, model.measure.units.baseUnitName)
    }

    @Test
    fun itemComparisonEntityToModelWithInvalidUnit() {
        val entity = ItemComparisonEntity(
            id = "1",
            productId = "product123",
            cartId = "cart123",
            brand = "BrandA",
            store = "StoreA",
            unitPrice = 10.0f,
            amount = 2,
            baseUnit = "InvallidUnit",
            measure = 15.0f
        )

        val model = entity.toModel("Length") as ItemComparison

        assertEquals(entity.id, model.id)
        assertEquals(entity.brand, model.brand)
        assertEquals(entity.store, model.store)
        assertEquals(entity.unitPrice, model.unitPrice)
        assertEquals(entity.amount, model.amount)
        assertEquals(entity.measure.toDouble(), model.measure.amount)
        assertEquals(Length.meters.baseUnitName, model.measure.units.baseUnitName)
    }
}