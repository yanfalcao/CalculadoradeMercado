package com.yanfalcao.productDetails.state

import com.yanfalcao.model.ItemComparison
import com.yanfalcao.model.Product

sealed class ProductDetailsIntent {
    data class LoadProduct(val productId: String?) : ProductDetailsIntent()
    data class RemoveItem(val item: ItemComparison) : ProductDetailsIntent()
    object RemoveLastUndo : ProductDetailsIntent()
    data class EditProduct(val product: Product) : ProductDetailsIntent()
    data class EditState(val state: ProductDetailsVS) : ProductDetailsIntent()
    data class EditItem(val state: ProductDetailsVS) : ProductDetailsIntent()
    data class UpdateProduct(val product: Product) : ProductDetailsIntent()
    object UpgradeItem : ProductDetailsIntent()
    object CreateProduct : ProductDetailsIntent()
    data class OpenItemToEdit(val item: ItemComparison) : ProductDetailsIntent()
    object OpenItemToCreate : ProductDetailsIntent()
    object CloseItemEdit : ProductDetailsIntent()
    object UndoAction : ProductDetailsIntent()

}