package com.yanfalcao.data.repository

import com.yanfalcao.model.ItemComparison

interface ItemRepository {
    suspend fun upgradeItem(itemComparison: ItemComparison, productId:String)
    suspend fun createItem(itemComparison: ItemComparison, productId:String)
    suspend fun deleteItem(itemComparison: ItemComparison, productId: String)
}