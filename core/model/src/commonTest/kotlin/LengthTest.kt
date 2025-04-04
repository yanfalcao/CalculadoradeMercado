import com.yanfalcao.model.util.Length.Companion.kilometers
import com.yanfalcao.model.util.Length.Companion.miles
import com.yanfalcao.model.util.Length.Companion.centimeters
import com.yanfalcao.model.util.Length.Companion.feet
import com.yanfalcao.model.util.Length.Companion.inches
import com.yanfalcao.model.util.Length.Companion.meters
import com.yanfalcao.model.util.Length.Companion.millimeters
import com.yanfalcao.model.util.times
import kotlin.test.Test
import kotlin.test.assertEquals

class LengthTest {
    @Test
    fun convertKilometersToMiles() {
        val distance = 1 * kilometers `in` miles
        assertEquals(0.6213711922, distance, 1e-9)
    }

    @Test
    fun convertKilometersToMeters() {
        val distance = 1 * kilometers `in` meters
        assertEquals(1000.0, distance, 1e-9)
    }

    @Test
    fun convertCentimetersToMeters() {
        val distance = 100 * centimeters `in` meters
        assertEquals(1.0, distance, 1e-9)
    }

    @Test
    fun convertCentimetersToMillimeters() {
        val distance = 1 * centimeters `in` millimeters
        assertEquals(10.0, distance, 1e-9)
    }

    @Test
    fun convertFeetToMeter() {
        val distance = 1 * feet `in` meters
        assertEquals(0.3048, distance, 1e-9)
    }

    @Test
    fun convertInchesToMeter() {
        val distance = 1 * inches `in` meters
        assertEquals(0.0254, distance, 1e-9)
    }

    @Test
    fun compareMeasures() {
        val distance1 = 1 * meters
        val distance2 = 100 * centimeters
        assertEquals(0, distance1.compareTo(distance2))
    }

    @Test
    fun compareMeasuresPositiveResult() {
        val distance1 = 1 * meters
        val distance2 = 95 * centimeters
        assertEquals(1, distance1.compareTo(distance2))
    }

    @Test
    fun compareMeasuresNegativeResult() {
        val distance1 = 0.9 * meters
        val distance2 = 100 * centimeters
        assertEquals(-1, distance1.compareTo(distance2))
    }
}