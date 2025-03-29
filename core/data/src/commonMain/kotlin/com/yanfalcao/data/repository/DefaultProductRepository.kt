package com.yanfalcao.data.repository

import com.yanfalcao.data.extensions.toEntity
import com.yanfalcao.data.extensions.toModel
import com.yanfalcao.database.dao.ItemComparisonDao
import com.yanfalcao.database.dao.ProductDao
import com.yanfalcao.model.ItemComparison
import com.yanfalcao.model.Product

internal class DefaultProductRepository(
    private val productDao: ProductDao,
    private val itemComparisonDao: ItemComparisonDao
): ProductRepository {
    override fun findProducts(): List<Product> {
        return productDao.findAll()
            .map { productDatabase ->
                val itemList: List<ItemComparison> = productDatabase.itemComparison
                    .map { item ->
                        val measurementEntity = productDatabase.product.entity
                        item.toModel(measurementEntity)
                    }

                productDatabase.product.toModel(itemList)
            }
    }

    override fun saveProduct(product: Product) {
        product.itens
            .map { item ->
                item.toEntity(productId = product.id)
            }
            .forEach { itemDatabase ->
                itemComparisonDao.insert(itemDatabase)
            }

        productDao.insert(product.toEntity())
    }

    override fun removeProduct(product: Product) {
        itemComparisonDao.deleteByProductId(product.id)
        productDao.delete(product.toEntity())
    }

    override fun updateProduct(product: Product) {
        product.itens
            .forEach { item ->
                val productId = product.id
                itemComparisonDao.insert(item.toEntity(productId))
            }

        productDao.insert(product.toEntity())
    }
}