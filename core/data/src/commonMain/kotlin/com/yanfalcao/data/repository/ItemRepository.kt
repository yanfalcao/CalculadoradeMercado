package com.yanfalcao.data.repository

import com.yanfalcao.model.ItemComparison

interface ItemRepository {
    fun upgradeItem(itemComparison: ItemComparison, productId:String)
    fun createItem(itemComparison: ItemComparison, productId:String)
}