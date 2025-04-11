package com.yanfalcao.productDetails.widget

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import calculadorademercado.feature.productdetails.generated.resources.Res
import calculadorademercado.feature.productdetails.generated.resources.item_amount_unit
import calculadorademercado.feature.productdetails.generated.resources.item_price_unit
import com.yanfalcao.designsystem.icons.IconDelete
import com.yanfalcao.designsystem.icons.IconEdit
import com.yanfalcao.model.ItemComparison
import com.yanfalcao.model.util.BaseUnits
import com.yanfalcao.model.util.Measure
import com.yanfalcao.productDetails.state.ProductDetailsIntent
import org.jetbrains.compose.resources.stringResource

@Composable
fun ProductComparisonCard(
    item: ItemComparison,
    measureComparison: Measure<BaseUnits>,
    handleIntent: (ProductDetailsIntent) -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = MaterialTheme.colorScheme.background,
        border = BorderStroke(
            1.5.dp,
            color = MaterialTheme.colorScheme.primary
        )
    ) {
        Column(Modifier
            .fillMaxWidth()
            .padding(vertical = 15.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row {
                    Text(
                        item.brand,
                        style = MaterialTheme.typography.bodyMedium
                    )

                    if (!item.store.isNullOrEmpty()) {
                        Text(
                            "• ${item.store}",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }

                Row {
                    IconButton(
                        onClick = {
                            handleIntent(ProductDetailsIntent.OpenItemToEdit(item))
                        }
                    ) {
                        IconEdit()
                    }

                    IconButton(
                        onClick = {
                            handleIntent(ProductDetailsIntent.RemoveItem(item))
                        }
                    ) {
                        IconDelete()
                    }
                }
            }

            HorizontalDivider(
                modifier = Modifier.padding(top = 10.dp, bottom = 15.dp),
                color = MaterialTheme.colorScheme.primary,
                thickness = 1.5.dp
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    stringResource(Res.string.item_price_unit, item.unitPriceFormatted()),
                    style = MaterialTheme.typography.bodySmall
                )

                Text(
                    stringResource(Res.string.item_amount_unit, item.measure.amountWithAbbreviation()),
                    style = MaterialTheme.typography.bodySmall
                )

                Text(
                    "R$ dsf / ",
                    style = MaterialTheme.typography.bodySmall
                )
            }

        }
    }
}