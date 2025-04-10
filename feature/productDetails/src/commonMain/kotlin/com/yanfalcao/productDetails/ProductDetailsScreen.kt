@file:OptIn(KoinExperimentalAPI::class)

package com.yanfalcao.productDetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import calculadorademercado.feature.productdetails.generated.resources.Res
import calculadorademercado.feature.productdetails.generated.resources.placeholder_name
import calculadorademercado.feature.productdetails.generated.resources.snackbar_delete
import calculadorademercado.feature.productdetails.generated.resources.undo
import com.yanfalcao.designsystem.util.EnumSnackEvent
import com.yanfalcao.designsystem.util.EventManager
import com.yanfalcao.designsystem.util.EventManager.AppEvent.ShowSnackbar
import com.yanfalcao.productDetails.extensions.copyBaseUnitComparison
import com.yanfalcao.productDetails.extensions.entitiesToDropdownItem
import com.yanfalcao.productDetails.extensions.getDropItem
import com.yanfalcao.productDetails.widget.FloatingButton
import com.yanfalcao.productDetails.state.ProductDetailsIntent
import com.yanfalcao.productDetails.state.ProductDetailsVS
import com.yanfalcao.productDetails.widget.CustomTopBar
import com.yanfalcao.productDetails.widget.DropdownField
import com.yanfalcao.productDetails.widget.EmptyListText
import com.yanfalcao.productDetails.widget.InputDisplayField
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import kotlinx.coroutines.launch
import org.koin.core.parameter.parametersOf

@Composable
fun ProductDetailsRoute(
    productId: String? = null,
    viewModel: ProductDetailsVM = koinViewModel(parameters = { parametersOf(productId) }),
    onBackClick: () -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val productViewState by viewModel.productViewState.collectAsState()

    val snackDeleteText = stringResource(Res.string.snackbar_delete)
    val undoLabelText = stringResource(Res.string.undo)

    LaunchedEffect(EventManager) {
        EventManager.eventsFlow.collect { event ->
            when(event) {
                is ShowSnackbar -> {
                    when(event.snackEvent) {
                        EnumSnackEvent.DELETE_PRODUCT -> {
                            coroutineScope.launch {
                                val result = snackbarHostState.showSnackbar(
                                    message = snackDeleteText,
                                    actionLabel = undoLabelText,
                                    duration = SnackbarDuration.Long,
                                )

                                when(result) {
                                    SnackbarResult.Dismissed -> {
                                        viewModel.handleIntent(ProductDetailsIntent.RemoveLastUndo)
                                    }
                                    SnackbarResult.ActionPerformed -> {
                                        viewModel.handleIntent(ProductDetailsIntent.UndoAction)
                                    }
                                }
                            }
                        }
                    }
                }
                else -> {}
            }
        }
    }

    ProductDetailsScreen(
        snackbarHostState = snackbarHostState,
        state = productViewState,
        handleIntent = viewModel::handleIntent,
        onBack = onBackClick,
    )
}

@Composable
fun ProductDetailsScreen(
    snackbarHostState: SnackbarHostState,
    state: ProductDetailsVS,
    handleIntent: (ProductDetailsIntent) -> Unit,
    onBack: () -> Unit,
) {
    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        topBar = {
            CustomTopBar(onBack)
        },
        floatingActionButton = {
            FloatingButton(
                onClick = { }
            )
        }
    ) { padding ->
        LazyColumn (
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 15.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                InputDisplayField(
                    modifier = Modifier.fillMaxWidth(),
                    label = stringResource(Res.string.placeholder_name),
                    handleIntent = handleIntent,
                    state = state,
                )
            }

            item {
                DropdownField(
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    defaultItem = state.product.measureComparison.units.getDropItem(),
                    itemList = state.product.measureComparison.units.entitiesToDropdownItem(),
                    onUpdate = { item ->
                        handleIntent(ProductDetailsIntent.UpdateProduct(
                            product = state.product.copyBaseUnitComparison(item.baseUnit)
                        ))
                    }
                )
            }

            if(state.product.itens.isEmpty()) {
                item { EmptyListText() }
            } else {

            }
        }
    }
}