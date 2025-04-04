package com.yanfalcao.model.util

open class Mass(
    baseUnitName: String,
    abbreviation: String,
    ratio: Double = 1.0
): BaseUnits(baseUnitName, abbreviation, ratio) {
    companion object : BaseUnitUtil {
        // 1 gram = 0.001 kilogram
        val kilogram = Mass("kilogram", "kg", 1000.0)
        val gram = Mass("gram", "g")
        val milligram = Mass("milligram", "mg", 0.001)
        val ounce = Mass("ounce", "oz", 28.3495)
        val pound = Mass("pound", "lb", 16 * ounce `in` gram)


        override fun isUnit(entity: String): Boolean {
            return entity.equals(Mass::class.simpleName)
        }

        override fun getUnit(baseUnitName: String): BaseUnits {
            return when (baseUnitName) {
                kilogram.baseUnitName -> kilogram
                gram.baseUnitName -> gram
                milligram.baseUnitName -> milligram
                ounce.baseUnitName -> ounce
                pound.baseUnitName -> pound
                else -> throw IllegalArgumentException("Unknown base unit name: $baseUnitName")
            }
        }
    }
}