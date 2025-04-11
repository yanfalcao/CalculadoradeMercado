package com.yanfalcao.productDetails.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import calculadorademercado.feature.productdetails.generated.resources.Res
import calculadorademercado.feature.productdetails.generated.resources.placeholder_amount
import calculadorademercado.feature.productdetails.generated.resources.title_comparison_unit
import com.yanfalcao.productDetails.extensions.baseUnitsToDropdownItem
import com.yanfalcao.productDetails.extensions.copyBaseUnitComparison
import com.yanfalcao.productDetails.extensions.getBaseUnitDropItem
import com.yanfalcao.productDetails.state.ProductDetailsIntent
import com.yanfalcao.productDetails.state.ProductDetailsVS
import org.jetbrains.compose.resources.stringResource

@Composable
fun ComparisonUnitSection(
    state: ProductDetailsVS,
    handleIntent: (ProductDetailsIntent) -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(Res.string.title_comparison_unit),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            NumberInputField(
                modifier = Modifier.weight(1f),
                label = stringResource(Res.string.placeholder_amount),
                onChange = { text ->
                    handleIntent(
                        ProductDetailsIntent.EditState(
                            state = state.copy(amountComparison = text)
                        )
                    )
                },
                textState = state.amountComparison,
            )
            DropdownField(
                modifier = Modifier.weight(1f).height(50.dp),
                defaultItem = state.product.measureComparison.units.getBaseUnitDropItem(),
                itemList = state.product.measureComparison.units.baseUnitsToDropdownItem(),
                onUpdate = { item ->
                    handleIntent(ProductDetailsIntent.UpdateProduct(
                        product = state.product.copyBaseUnitComparison(item.baseUnit)
                    ))
                }
            )
        }
    }
}