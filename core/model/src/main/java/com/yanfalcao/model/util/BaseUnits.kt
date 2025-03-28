package com.yanfalcao.model.util

/**
 * @constructor
 * @param baseUnitName to use when printing the base unit name (i.e. kilogram)
 * @param abbreviation to use when printing the abbreviation (i.e. kg)
 * @param ratio of this unit relative to the base-unit
 */
abstract class BaseUnits(
    val baseUnitName: String,
    val abbreviation: String,
    val ratio: Double = 1.0
)