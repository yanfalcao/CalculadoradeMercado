@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.yanfalcao.productDetails.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import calculadorademercado.feature.productdetails.generated.resources.Res
import calculadorademercado.feature.productdetails.generated.resources.amount
import calculadorademercado.feature.productdetails.generated.resources.brand
import calculadorademercado.feature.productdetails.generated.resources.cancel
import calculadorademercado.feature.productdetails.generated.resources.measure_amount
import calculadorademercado.feature.productdetails.generated.resources.placeholder
import calculadorademercado.feature.productdetails.generated.resources.price
import calculadorademercado.feature.productdetails.generated.resources.save
import calculadorademercado.feature.productdetails.generated.resources.store
import calculadorademercado.feature.productdetails.generated.resources.unit
import com.yanfalcao.productDetails.ProductDetailsVM
import com.yanfalcao.productDetails.extensions.baseUnitsToDropdownItem
import com.yanfalcao.productDetails.extensions.getBaseUnitDropItem
import com.yanfalcao.productDetails.state.ProductDetailsIntent
import org.jetbrains.compose.resources.stringResource

@Composable
fun BottomSheetItem(
    viewModel: ProductDetailsVM,
) {
    val modalBottomSheetState: SheetState =
        rememberModalBottomSheetState(
            skipPartiallyExpanded = true,
        )

    ModalBottomSheet(
        onDismissRequest = { viewModel.handleIntent(ProductDetailsIntent.CloseItemEdit) },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        containerColor = MaterialTheme.colorScheme.background,
    ) {
        BottomSheetBody(
            viewModel = viewModel,
        )
    }
}

@Composable
private fun BottomSheetBody(
    viewModel: ProductDetailsVM,
) {
    val lazyListState = rememberLazyListState()

    if (lazyListState.isScrollInProgress) {
        val keyboardController = LocalSoftwareKeyboardController.current
        keyboardController?.hide()
    }

    LazyColumn (
        state = lazyListState,
        modifier =
        Modifier
            .safeDrawingPadding()
            .padding(start = 15.dp, end = 15.dp, bottom = 15.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        item {
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
                            viewModel.itemBrand.value = text
                        },
                        textState = viewModel.itemBrand.value,
                        checkFormat = viewModel.checkItemFormat.value
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
                            viewModel.itemStore.value = text
                        },
                        textState = viewModel.itemStore.value,
                    )
                }
            }
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }

        item {
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
                            viewModel.itemPrice.value = text
                        },
                        textState = viewModel.itemPrice.value,
                        checkFormat = viewModel.checkItemFormat.value
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
                            viewModel.itemAmount.value = text
                        },
                        textState = viewModel.itemAmount.value,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        checkFormat = viewModel.checkItemFormat.value
                    )
                }
            }
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }

        item {
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
                        defaultItem = viewModel.itemBaseUnit.value.getBaseUnitDropItem(),
                        itemList = viewModel.itemBaseUnit.value.baseUnitsToDropdownItem(),
                        onUpdate = { item ->
                            viewModel.itemBaseUnit.value = item.baseUnit
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
                        posfix = viewModel.itemBaseUnit.value.abbreviation,
                        onChange = { text ->
                            viewModel.itemAmountComparison.value = text
                        },
                        textState = viewModel.itemAmountComparison.value,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        checkFormat = viewModel.checkItemFormat.value
                    )
                }
            }
        }

        item { Spacer(modifier = Modifier.height(32.dp)) }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = { viewModel.handleIntent(ProductDetailsIntent.CloseItemEdit) },
                    modifier = Modifier.weight(1f).height(50.dp),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    )
                ) {
                    Text(
                        stringResource(Res.string.cancel),
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Button(
                    onClick = { viewModel.handleIntent(ProductDetailsIntent.UpgradeItem) },
                    modifier = Modifier.weight(1f).height(50.dp),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        stringResource(Res.string.save),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}