package com.yanfalcao.data.repository

import com.yanfalcao.data.extensions.toEntity
import com.yanfalcao.database.dao.ItemComparisonDao
import com.yanfalcao.model.ItemComparison

class DefaultItemRepository(
    private val itemComparisonDao: ItemComparisonDao,
) : ItemRepository {
    override fun upgradeItem(itemComparison: ItemComparison, productId: String) {
        itemComparisonDao.update(itemComparison.toEntity(productId))
    }

    override fun createItem(itemComparison: ItemComparison, productId: String) {
        itemComparisonDao.insert(itemComparison.toEntity(productId))
    }
}