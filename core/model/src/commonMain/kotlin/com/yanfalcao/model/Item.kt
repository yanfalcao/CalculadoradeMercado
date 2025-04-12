package com.yanfalcao.model

import com.yanfalcao.model.extension.moneyStringFormat

abstract class Item {
    abstract val id: String
    abstract val totalPrice: Float
    abstract val amount: Int

    fun unitPriceFormatted(): String {
        return (totalPrice / amount).toDouble().moneyStringFormat()
    }
}
