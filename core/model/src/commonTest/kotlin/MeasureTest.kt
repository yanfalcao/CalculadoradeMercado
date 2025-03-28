import com.yanfalcao.model.util.Length.Companion.kilometers
import com.yanfalcao.model.util.Length.Companion.miles
import com.yanfalcao.model.util.Length.Companion.centimeters
import com.yanfalcao.model.util.Length.Companion.feet
import com.yanfalcao.model.util.Length.Companion.inches
import com.yanfalcao.model.util.Length.Companion.meters
import com.yanfalcao.model.util.times
import kotlin.test.Test
import kotlin.test.assertEquals

class MeasureTest {
    @Test
    fun convertKilometersToMiles() {
        val distanceInKm = 1 * kilometers
        val distanceInMiles = distanceInKm `as` miles
        assertEquals(0.6213711922, distanceInMiles.amount, 1e-9)
    }

    @Test
    fun convertMilesToKilometers() {
        val distanceInMiles = 0.6213711922 * miles
        val distanceInKm = distanceInMiles `as` kilometers
        assertEquals(1.0, distanceInKm.amount, 1e-9)
    }

    @Test
    fun convertMetersToCentimeters() {
        val distanceInMeters = 1 * meters
        val distanceInCm = distanceInMeters `as` centimeters
        assertEquals(100.0, distanceInCm.amount, 1e-9)
    }

    @Test
    fun convertCentimetersToMeters() {
        val distanceInCm = 100 * centimeters
        val distanceInMeters = distanceInCm `as` meters
        assertEquals(1.0, distanceInMeters.amount, 1e-9)
    }

    @Test
    fun convertFeetToInches() {
        val distanceInFeet = 1 * feet
        val distanceInInches = distanceInFeet `as` inches
        assertEquals(12.0, distanceInInches.amount, 1e-9)
    }

    @Test
    fun convertInchesToFeet() {
        val distanceInInches = 12 * inches
        val distanceInFeet = distanceInInches `as` feet
        assertEquals(1.0, distanceInFeet.amount, 1e-9)
    }

    @Test
    fun multiplyMeasureByNumber() {
        val distanceInMeters = 2 * meters
        val result = distanceInMeters * 3
        assertEquals(6.0, result.amount, 1e-9)
        assertEquals(meters, result.units)
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