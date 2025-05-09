package com.yanfalcao.productDetails.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import calculadorademercado.feature.productdetails.generated.resources.Res
import calculadorademercado.feature.productdetails.generated.resources.placeholder_amount
import calculadorademercado.feature.productdetails.generated.resources.placeholder_name
import calculadorademercado.feature.productdetails.generated.resources.title_comparison_unit
import com.yanfalcao.productDetails.extensions.baseUnitsToDropdownItem
import com.yanfalcao.productDetails.extensions.copyBaseUnitComparison
import com.yanfalcao.productDetails.extensions.entitiesToDropdownItem
import com.yanfalcao.productDetails.extensions.getBaseUnitDropItem
import com.yanfalcao.productDetails.extensions.getDropItem
import com.yanfalcao.productDetails.state.ProductDetailsIntent
import com.yanfalcao.productDetails.state.ProductDetailsVS
import org.jetbrains.compose.resources.stringResource

@Composable
fun ComparisonUnitSection(
    state: ProductDetailsVS,
    handleIntent: (ProductDetailsIntent) -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        InputDisplayField(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(Res.string.placeholder_name),
            onChange = { text ->
                handleIntent(
                    ProductDetailsIntent.EditProduct(
                        state = state.copy(
                            product = state.product.copy(name = text)
                        )
                    )
                )
            },
            textState = state.product.name,
            checkFormat = state.checkProductFormat
        )

        Spacer(Modifier.height(15.dp))

        DropdownField(
            modifier = Modifier.fillMaxWidth().height(50.dp),
            defaultItem = state.product.measureComparison.units.getDropItem(),
            itemList = state.product.measureComparison.units.entitiesToDropdownItem(),
            onUpdate = { item ->
                handleIntent(ProductDetailsIntent.EditProduct(
                    state = state.copy(
                        product = state.product.copyBaseUnitComparison(item.baseUnit)
                    )
                ))
            }
        )

        Spacer(Modifier.height(15.dp))

        Text(
            text = stringResource(Res.string.title_comparison_unit),
            style = MaterialTheme.typography.titleMedium,
        )

        Spacer(Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            NumberInputField(
                modifier = Modifier.weight(0.7f),
                label = stringResource(Res.string.placeholder_amount),
                onChange = { text ->
                    handleIntent(
                        ProductDetailsIntent.EditProduct(
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
                    handleIntent(ProductDetailsIntent.EditProduct(
                        state = state.copy(
                            product = state.product.copyBaseUnitComparison(item.baseUnit)
                        )
                    ))
                }
            )
        }
    }
}