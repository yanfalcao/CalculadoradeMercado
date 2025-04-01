package com.yanfalcao.product.state

import com.yanfalcao.model.Product

sealed class ProductIntent {
    object LoadProducts : ProductIntent()
    data class RemoveProduct(val product: Product) : ProductIntent()
    data class CreateProduct(val product: Product) : ProductIntent()
    data class FilterProducts(val text: String) : ProductIntent()
    data class OpenProductToEdit(val product: Product) : ProductIntent()
    object OpenProductToCreate : ProductIntent()
    object UndoAction : ProductIntent()
    object RemoveLastUndo : ProductIntent()
}