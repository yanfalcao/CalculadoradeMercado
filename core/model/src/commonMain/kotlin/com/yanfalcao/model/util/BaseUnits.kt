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
) {
    companion object {
        fun getEntityUnit(entity: String): EntityUnit {
            return when {
                Length.isUnit(entity) -> EntityUnit.Length
                Mass.isUnit(entity) -> EntityUnit.Mass
                Volume.isUnit(entity) -> EntityUnit.Volume
                else -> EntityUnit.Length
            }
        }
    }

    fun entityName(): String = this::class.simpleName!!

    fun getEntityUnit(): EntityUnit {
        return Companion.getEntityUnit(entityName())
    }
}

interface BaseUnitUtil {
    fun isUnit(entity: String): Boolean
    fun getUnit(baseUnitName: String): BaseUnits
}