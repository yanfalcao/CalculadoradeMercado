package com.yanfalcao.productDetails.state

import com.yanfalcao.model.ItemComparison

sealed class ProductDetailsIntent {
    /***
     * Find a saved product by id
     */
    data class LoadProduct(val productId: String?) : ProductDetailsIntent()

    /***
     * Remove an item from the product but don't save in the local storage
     */
    data class RemoveItem(val item: ItemComparison) : ProductDetailsIntent()

    /***
     * Pop the last undo action from the stack
     */
    object RemoveLastUndo : ProductDetailsIntent()

    /***
     * Edit product fields state, without worry about if is valid or not
     */
    data class EditProduct(val state: ProductDetailsVS) : ProductDetailsIntent()

    /***
     * Edit the item fields, if all are valid, save the item in a temporary object.
     *  Else the screen handle the error
     */
    object UpgradeItem : ProductDetailsIntent()

    /***
     * Save a product in local storage
     */
    object SaveProduct : ProductDetailsIntent()

    /***
     * Open an existing item to edit
     */
    data class OpenItemToEdit(val item: ItemComparison) : ProductDetailsIntent()

    /***
     * Open a new item to create
     */
    object OpenItemToCreate : ProductDetailsIntent()

    /***
     * Close the item edit ignoring all fields filled in the screen and cleaning them
     */
    object CloseItemEdit : ProductDetailsIntent()

    /***
     * Undo the last action, as delete an item
     */
    object UndoAction : ProductDetailsIntent()
    /***
     * Verify any change before closing the screen to alert the user
     */
    object CloseScreen : ProductDetailsIntent()
}