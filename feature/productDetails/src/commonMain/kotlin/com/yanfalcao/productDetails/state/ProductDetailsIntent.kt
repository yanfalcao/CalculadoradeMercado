package com.yanfalcao.productDetails.state

import com.yanfalcao.model.ItemComparison
import com.yanfalcao.model.Product

sealed class ProductDetailsIntent {
    data class LoadProduct(val productId: String?) : ProductDetailsIntent()
    data class RemoveItem(val item: ItemComparison) : ProductDetailsIntent()
    data class EditProduct(val product: Product) : ProductDetailsIntent()
    data class UpdateProduct(val product: Product) : ProductDetailsIntent()
    data class UpgradeItem(val itemComparison: ItemComparison) : ProductDetailsIntent()
    data class CreateProduct(val product: Product) : ProductDetailsIntent()
    data class OpenItemToEdit(val product: Product) : ProductDetailsIntent()
    object OpenItemToCreate : ProductDetailsIntent()
    object UndoAction : ProductDetailsIntent()
    object RemoveLastUndo : ProductDetailsIntent()
}