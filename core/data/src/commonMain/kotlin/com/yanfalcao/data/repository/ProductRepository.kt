package com.yanfalcao.data.repository

import com.yanfalcao.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun findProducts(): Flow<List<Product>>
    fun findProductById(id: String): Flow<Product>
    suspend fun findProductsByName(name: String): List<Product>
    suspend fun saveProduct(product: Product)
    suspend fun removeProduct(product: Product)
    suspend fun updateProduct(product: Product)
}