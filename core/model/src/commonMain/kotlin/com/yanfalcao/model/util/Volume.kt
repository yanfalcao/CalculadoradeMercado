package com.yanfalcao.model.util

open class Volume(
    baseUnitName: String,
    abbreviation: String,
    ratio: Double = 1.0
): BaseUnits(baseUnitName, abbreviation, ratio) {
    companion object : BaseUnitUtil {
        val liter = Volume("liter", "L")
        val milliliter = Volume("milliliter", "mL", 0.001)
        val fluidOunce = Volume("fluid ounce", "fl oz", 0.0295735156)
        // 1 gallon = 128 fluid ounces
        val gallon = Volume("gallon", "gal", 128 * fluidOunce `in` liter)
        // 1 pint = 0.473176 liters
        val pint = Volume("pint", "pt", 16 * fluidOunce `in` liter)


        override fun isUnit(entity: String): Boolean {
            return entity.equals(Volume::class.simpleName)
        }

        override fun getUnit(baseUnitName: String): BaseUnits {
            return when (baseUnitName) {
                liter.baseUnitName -> liter
                milliliter.baseUnitName -> milliliter
                gallon.baseUnitName -> gallon
                pint.baseUnitName -> pint
                fluidOunce.baseUnitName -> fluidOunce
                else -> throw IllegalArgumentException("Unknown base unit name: $baseUnitName")
            }
        }
    }
}