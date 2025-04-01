package com.yanfalcao.model.extension

import kotlinx.datetime.Instant
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char

fun Instant.formatToCustomString(): String {
    val customFormat = DateTimeComponents.Format {
        dayOfMonth()
        char( ' ')
        monthName(MonthNames.ENGLISH_ABBREVIATED)
        chars(", ")
        year()
    }
    return this.format(customFormat)
}