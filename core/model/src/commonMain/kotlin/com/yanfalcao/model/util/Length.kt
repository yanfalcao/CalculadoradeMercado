package com.yanfalcao.model.util

open class Length(
    baseUnitName: String,
    abbreviation: String,
    ratio: Double = 1.0
): BaseUnits(baseUnitName, abbreviation, ratio) {
    companion object : BaseUnitUtil {
        val miles = Length("mile", "mi", 1609.3440)
        val millimeters = Length("millimeter", "mm", 0.0010)
        val centimeters = Length("centimeter","cm",    0.01)
        val meters = Length("meter", "m")
        val inches = Length("inch", "in", 0.0254)
        val feet = Length("feet", "ft", 12 * inches `in` meters)
        val kilometers = Length("kilometer", "km", 1000.0)

        override fun isUnit(entity: String): Boolean {
            return entity.equals(Length::class.simpleName)
        }

        override fun getUnit(baseUnitName: String): BaseUnits {
            return when (baseUnitName) {
                miles.baseUnitName -> miles
                millimeters.baseUnitName -> millimeters
                centimeters.baseUnitName -> centimeters
                meters.baseUnitName -> meters
                inches.baseUnitName -> inches
                feet.baseUnitName -> feet
                kilometers.baseUnitName -> kilometers
                else -> throw IllegalArgumentException("Unknown base unit name: $baseUnitName")
            }
        }
    }
}