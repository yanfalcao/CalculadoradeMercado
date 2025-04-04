import com.yanfalcao.model.util.Volume
import com.yanfalcao.model.util.Volume.Companion.fluidOunce
import com.yanfalcao.model.util.Volume.Companion.gallon
import com.yanfalcao.model.util.Volume.Companion.liter
import com.yanfalcao.model.util.Volume.Companion.milliliter
import com.yanfalcao.model.util.Volume.Companion.pint
import com.yanfalcao.model.util.times
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class VolumeTest {
    @Test
    fun literToMilliliter() {
        val volume = 2 * liter `in` milliliter
        assertEquals(2000.0, volume)
    }

    @Test
    fun literToFluidOunce() {
        val volume = 1 * liter `in` fluidOunce
        assertEquals(33.81403866640732, volume, 1e-14)
    }

    @Test
    fun literToGallon() {
        val volume = 10 * liter `in` gallon
        assertEquals(2.641721770813072, volume, 1e-9)
    }

    @Test
    fun kilogramToOunce() {
        val volume = 0.5 * gallon `in` pint
        assertEquals(4.0, volume, 1e-9)
    }

    @Test
    fun unknownUnit() {
        assertFailsWith<IllegalArgumentException> {
            Volume.getUnit("unknown")
        }
    }
}