package com.yanfalcao.data.repository

import com.yanfalcao.model.Product

interface ProductRepository {
    fun findProducts(): List<Product>
    fun saveProduct(product: Product)
    fun removeProduct(product: Product)
    fun updateProduct(product: Product)
}