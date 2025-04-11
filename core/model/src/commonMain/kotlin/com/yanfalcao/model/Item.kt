package com.yanfalcao.model

import com.yanfalcao.model.extension.toString

abstract class Item {
    abstract val id: String
    abstract val totalPrice: Float
    abstract val amount: Int

    fun unitPriceFormatted(): String {
        var text = (totalPrice / amount).toDouble().toString(2)
        var decimals = text.split(".").lastOrNull() ?: ""
        text += when(decimals.length) {
            0 -> ".00"
            1 -> "0"
            else -> ""
        }
        return text.replace(".", ",")
    }
}
