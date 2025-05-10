package com.yanfalcao.model.util

import com.yanfalcao.model.extension.toString

/**
 * @property amount of the measure (i.e. 2)
 * @property units  of the measure (i.e. Kilogram)
 */
class Measure<T: BaseUnits>(val amount: Double, val units: T): Comparable<Measure<T>> {
    /**
     * Convert this Measure into another compatible one with different units. Type must share parent
     * (i.e. Mile into Kilometer, because they both are made from Distance)
     */
    infix fun <A: T> `as`(other: A): Measure<T> = if (units == other) this else Measure(this `in` other, other)

    /**
     * Gets the value of the Measure in the given unit.
     */
    infix fun <A: T> `in`(other: A): Double = if (units == other) amount else  amount * (units.ratio / other.ratio)

    operator fun times(other: Number): Measure<T> = amount * other.toDouble() * units

    override fun compareTo(other: Measure<T>): Int = (this `as` other.units).amount.compareTo(other.amount)

    fun amountFormatted(): String {
        val text = amount.toString(2)
        val decimals = text.split(".").lastOrNull() ?: ""
        return when(decimals) {
            "0" -> text.replace(".0", "")
            else -> text
        }
    }

    fun amountWithAbbreviation(): String {
        var text = amount.toString(2)
        val decimals = text.split(".").lastOrNull() ?: ""
        text = when(decimals) {
            "0" -> text.replace(".0", "")
            else -> text
        }

        return text + units.abbreviation
    }

    override fun equals(other: Any?): Boolean {
        if (other == null || this::class != other::class) return false

        other as Measure<*>

        if(amount != other.amount) return false
        if(units::class != other.units::class) return false

        return true
    }
}

operator fun <A: BaseUnits> Number.times(other: A): Measure<A> = Measure(this.toDouble(), other)