package com.yanfalcao.model.util

import com.yanfalcao.model.util.Mass.Companion.gram
import com.yanfalcao.model.util.Mass.Companion.kilogram
import com.yanfalcao.model.util.Mass.Companion.milligram
import com.yanfalcao.model.util.Mass.Companion.ounce
import com.yanfalcao.model.util.Mass.Companion.pound
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class MassTest {

    @Test
    fun kilogramToGrams() {
        val mass = 1 * kilogram `in` gram
        assertEquals(1000.0, mass)
    }

    @Test
    fun gramsToMilligrams() {
        assertEquals(1000.0, 1 * gram `in` milligram)
    }

    @Test
    fun kilogramToPound() {
        assertEquals(2.2046244202, 1 * kilogram `in` pound, 1e-9)
    }

    @Test
    fun kilogramToOunce() {
        assertEquals(35.273990723, 1 * kilogram `in` ounce, 1e-9)
    }

    @Test
    fun unknownUnit() {
        assertFailsWith<IllegalArgumentException> {
            Mass.getUnit("unknown")
        }
    }
}