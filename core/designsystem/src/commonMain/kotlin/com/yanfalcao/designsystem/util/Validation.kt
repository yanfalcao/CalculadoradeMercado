package com.yanfalcao.designsystem.util

object Validation {
    fun validString(value: String): Boolean {
        return value.isNotEmpty() && value.isNotBlank()
    }

    fun validNumber(value: String): Boolean {
        return try {
            value.replace(",", ".").toDouble()
            true
        } catch (e: NumberFormatException) {
            false
        }
    }
}