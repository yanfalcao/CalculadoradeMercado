package com.yanfalcao.data.repository

import com.yanfalcao.data.extensions.toEntity
import com.yanfalcao.data.extensions.toModel
import com.yanfalcao.database.dao.ItemComparisonDao
import com.yanfalcao.database.dao.ProductDao
import com.yanfalcao.database.model.ProductRelation
import com.yanfalcao.model.ItemComparison
import com.yanfalcao.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class DefaultProductRepository(
    private val productDao: ProductDao,
    private val itemComparisonDao: ItemComparisonDao
): ProductRepository {
    override fun findProducts(): Flow<List<Product>> {
        return productDao.findAll()
            .map { productRelationList ->
                mapToProductList(productRelationList)
            }
    }

    override fun findProductsByName(name: String): List<Product> {
        return productDao.findByName(name)
            .map { productRelationList ->
                val itemList = mapToItemComparisonList(productRelationList)
                productRelationList.product.toModel(itemList)
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

    private fun mapToItemComparisonList(productRelation: ProductRelation): List<ItemComparison> {
        return productRelation.itemComparison
            .map { item ->
                val measurementEntity = productRelation.product.entity
                item.toModel(measurementEntity)
            }
    }

    private fun mapToProductList(productRelationList: List<ProductRelation>): List<Product> {
        return productRelationList
            .map { productRelation ->
                val itemList = mapToItemComparisonList(productRelation)
                productRelation.product.toModel(itemList)
            }
    }

}