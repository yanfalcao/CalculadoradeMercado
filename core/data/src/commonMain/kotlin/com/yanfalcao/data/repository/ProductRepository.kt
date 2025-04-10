package com.yanfalcao.data.repository

import com.yanfalcao.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun findProducts(): Flow<List<Product>>
    fun findProductById(id: String): Flow<Product>
    fun findProductsByName(name: String): List<Product>
    fun saveProduct(product: Product)
    fun removeProduct(product: Product)
    fun updateProduct(product: Product)
}