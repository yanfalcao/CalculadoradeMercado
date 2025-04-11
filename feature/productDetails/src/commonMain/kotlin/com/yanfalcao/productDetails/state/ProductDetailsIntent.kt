package com.yanfalcao.productDetails.state

import com.yanfalcao.model.Item
import com.yanfalcao.model.ItemComparison
import com.yanfalcao.model.Product

sealed class ProductDetailsIntent {
    data class LoadProduct(val productId: String?) : ProductDetailsIntent()
    data class RemoveItem(val item: ItemComparison) : ProductDetailsIntent()
    data class EditProduct(val product: Product) : ProductDetailsIntent()
    data class EditState(val state: ProductDetailsVS) : ProductDetailsIntent()
    data class UpdateProduct(val product: Product) : ProductDetailsIntent()
    data class UpgradeItem(val itemComparison: ItemComparison) : ProductDetailsIntent()
    object CreateProduct : ProductDetailsIntent()
    data class OpenItemToEdit(val item: ItemComparison) : ProductDetailsIntent()
    object OpenItemToCreate : ProductDetailsIntent()
    object UndoAction : ProductDetailsIntent()
    object RemoveLastUndo : ProductDetailsIntent()
}