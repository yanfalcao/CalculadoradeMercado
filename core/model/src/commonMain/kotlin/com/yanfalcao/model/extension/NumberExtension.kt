package com.yanfalcao.model.extension

import kotlin.math.pow
import kotlin.math.roundToInt

fun Double.toString(numOfDec: Int): String {
    val integerPart = this.toInt()

    if (numOfDec > 0) {
        var dotAt = 1
        repeat(numOfDec) { dotAt *= 10 }
        val roundedValue = (this * dotAt).roundToInt()
        return (roundedValue.toFloat() / dotAt).toString()
    } else {
        return integerPart.toString()
    }
}

fun Double.moneyStringFormat(): String {
    var text = this.toString(2)
    var decimals = text.split(".").lastOrNull() ?: ""
    text += when(decimals.length) {
        0 -> ".00"
        1 -> "0"
        else -> ""
    }
    return text.replace(".", ",")
}