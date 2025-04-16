@file:OptIn(ExperimentalMaterial3Api::class)

package com.yanfalcao.productDetails.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import calculadorademercado.feature.productdetails.generated.resources.Res
import calculadorademercado.feature.productdetails.generated.resources.amount
import calculadorademercado.feature.productdetails.generated.resources.brand
import calculadorademercado.feature.productdetails.generated.resources.item_amount_unit
import calculadorademercado.feature.productdetails.generated.resources.measure_amount
import calculadorademercado.feature.productdetails.generated.resources.placeholder
import calculadorademercado.feature.productdetails.generated.resources.placeholder_name
import calculadorademercado.feature.productdetails.generated.resources.price
import calculadorademercado.feature.productdetails.generated.resources.store
import calculadorademercado.feature.productdetails.generated.resources.unit
import com.yanfalcao.model.extension.moneyStringFormat
import com.yanfalcao.productDetails.extensions.baseUnitsToDropdownItem
import com.yanfalcao.productDetails.extensions.copyBaseUnitComparison
import com.yanfalcao.productDetails.extensions.getBaseUnitDropItem
import com.yanfalcao.productDetails.state.ProductDetailsIntent
import com.yanfalcao.productDetails.state.ProductDetailsVS
import org.jetbrains.compose.resources.stringResource

@Composable
fun BottomSheetItem(
    state: ProductDetailsVS,
    onDismiss: () -> Unit,
    handleIntent: (ProductDetailsIntent) -> Unit,
) {
    val modalBottomSheetState: SheetState =
        rememberModalBottomSheetState(
            skipPartiallyExpanded = true,
        )

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        containerColor = MaterialTheme.colorScheme.background,
    ) {
        BottomSheetBody(
            state = state,
            handleIntent = handleIntent,
        )
    }
}

@Composable
private fun BottomSheetBody(
    state: ProductDetailsVS,
    handleIntent: (ProductDetailsIntent) -> Unit,
) {
    Column(
        modifier =
        Modifier
            .safeDrawingPadding()
            .padding(start = 15.dp, end = 15.dp, bottom = 15.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    stringResource(Res.string.brand),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                InputDisplayField(
                    label = stringResource(Res.string.placeholder),
                    onChange = { text ->
                        handleIntent(ProductDetailsIntent.EditItem(
                            state.copy(itemBrand = text)
                        ))
                    },
                    textState = state.itemBrand,
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    stringResource(Res.string.store),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                InputDisplayField(
                    label = stringResource(Res.string.placeholder),
                    onChange = { text ->
                        handleIntent(ProductDetailsIntent.EditItem(
                            state.copy(itemStore = text)
                        ))
                    },
                    textState = state.itemStore,
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    stringResource(Res.string.price),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                NumberInputField(
                    prefix = "R$",
                    label = "",
                    onChange = { text ->
                        handleIntent(ProductDetailsIntent.EditItem(
                            state.copy(itemPrice = text)
                        ))
                    },
                    textState = state.itemPrice,
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    stringResource(Res.string.amount),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                InputDisplayField(
                    label = stringResource(Res.string.placeholder),
                    onChange = { text ->
                        handleIntent(ProductDetailsIntent.EditItem(
                            state.copy(itemAmount = text)
                        ))
                    },
                    textState = state.itemAmount,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    stringResource(Res.string.unit),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                DropdownField(
                    modifier = Modifier.height(50.dp),
                    defaultItem = state.itemBaseUnit.getBaseUnitDropItem(),
                    itemList = state.itemBaseUnit.baseUnitsToDropdownItem(),
                    onUpdate = { item ->
                        handleIntent(ProductDetailsIntent.EditItem(
                            state.copy(itemBaseUnit = item.baseUnit)
                        ))
                    }
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    stringResource(Res.string.measure_amount),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                InputDisplayField(
                    label = "",
                    posfix = state.itemBaseUnit.abbreviation,
                    onChange = { text ->
                        handleIntent(ProductDetailsIntent.EditItem(
                            state.copy(itemAmountComparison = text)
                        ))
                    },
                    textState = state.itemAmountComparison,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    )
                )
            }
        }
    }
}