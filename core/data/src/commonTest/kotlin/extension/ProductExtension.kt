package extension

import com.yanfalcao.data.extensions.*
import com.yanfalcao.database.model.ProductEntity
import com.yanfalcao.model.ItemComparison
import com.yanfalcao.model.Product
import com.yanfalcao.model.util.Length
import com.yanfalcao.model.util.Measure
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.test.Test
import kotlin.test.assertEquals

class ProductExtensionTest {

    @Test
    fun productToEntity() {
        val product = Product(
            id = "1",
            name = "Sample Product",
            measureComparison = Measure(1.0, Length.meters), emptyList(),
            createdAt = Clock.System.now()
        )

        val entity = product.toEntity()

        assertEquals(product.id, entity.id)
        assertEquals(product.name, entity.name)
        assertEquals(product.measureComparison.units.entityName(), entity.entity)
        assertEquals(product.measureComparison.amount, entity.measure.toDouble())
        assertEquals(product.measureComparison.units.baseUnitName, entity.baseUnit)
        assertEquals(product.createdAt.epochSeconds, entity.createdAt)
    }

    @Test
    fun productEntityToModel() {
        val entity = ProductEntity(
            id = "1",
            name = "Sample Product",
            measure = 1.0f,
            entity = Length.meters.entityName(),
            baseUnit = Length.meters.baseUnitName,
            createdAt = Clock.System.now().epochSeconds
        )

        val itens = emptyList<ItemComparison>()

        val model = entity.toModel(itens)

        assertEquals(entity.id, model.id)
        assertEquals(entity.name, model.name)
        assertEquals(entity.measure.toDouble(), model.measureComparison.amount)
        assertEquals(Length.meters.baseUnitName, model.measureComparison.units.baseUnitName)
        assertEquals(itens, model.itens)
        assertEquals(Instant.fromEpochSeconds(entity.createdAt), model.createdAt)
    }

    @Test
    fun productEntityToModelWithInvalidUnit() {
        val entity = ProductEntity(
            id = "1",
            name = "Sample Product",
            entity = Length.meters.entityName(),
            measure = 1.0f,
            baseUnit = "invalidUnit",
            createdAt = 1627849200
        )

        val itens = emptyList<ItemComparison>()

        val model = entity.toModel(itens)

        assertEquals(Length.meters.baseUnitName, model.measureComparison.units.baseUnitName)
    }
}