package com.yanfalcao.product.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yanfalcao.model.Product
import com.yanfalcao.product.state.ProductIntent

@Composable
fun ComparativeList(
    modifier: Modifier,
    products: List<Product>,
    handleIntent: (ProductIntent) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(20.dp),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        itemsIndexed(
            items = products,
            key = { _, product ->
                product.id
            }
        ) { index, product ->
            ComparativeListItem(
                listSize = products.size,
                index = index,
                product = product,
                onDelete = {
                    handleIntent(ProductIntent.RemoveProduct(product))
                },
                onEdit = {
                    handleIntent(ProductIntent.OpenProductToEdit(product))
                }
            )
        }
    }
}