package com.yanfalcao.model.util

open class Length(
    baseUnitName: String,
    abbreviation: String,
    ratio: Double = 1.0
): BaseUnits(baseUnitName, abbreviation, ratio) {
    companion object : BaseUnitUtil {
        // 1 mile = 1609.344 meters
        val miles       = Length("mile", "mi", 1609.3440)
        // 1 millimeter = 0.001 meters
        val millimeters = Length("millimeter", "mm", 0.0010)
        // 1 centimeter = 0.01 meters
        val centimeters = Length("centimeter","cm",    0.01)
        // Base unit of length used in rationalizing other units
        val meters      = Length("meter", "m", 1.0)
        // 1 inch = 0.0254 meters
        val inches      = Length("inch", "in", 0.0254)
        // 1 foot = 12 inches
        val feet        = Length("feet", "ft", 12 * inches `in` meters)
        // 1 kilometer = 1000 meters
        val kilometers  = Length("kilometer", "km", 1000.0)

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