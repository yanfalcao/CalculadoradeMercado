@file:OptIn(KoinExperimentalAPI::class)

package com.yanfalcao.productDetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import calculadorademercado.feature.productdetails.generated.resources.Res
import calculadorademercado.feature.productdetails.generated.resources.add_item
import calculadorademercado.feature.productdetails.generated.resources.cd_add_item
import calculadorademercado.feature.productdetails.generated.resources.cd_back_button
import calculadorademercado.feature.productdetails.generated.resources.cd_save
import calculadorademercado.feature.productdetails.generated.resources.confirmation_dialog_message
import calculadorademercado.feature.productdetails.generated.resources.confirmation_dialog_title
import calculadorademercado.feature.productdetails.generated.resources.ic_save
import calculadorademercado.feature.productdetails.generated.resources.ic_tag
import calculadorademercado.feature.productdetails.generated.resources.product
import calculadorademercado.feature.productdetails.generated.resources.save
import calculadorademercado.feature.productdetails.generated.resources.snackbar_delete
import calculadorademercado.feature.productdetails.generated.resources.undo
import com.yanfalcao.designsystem.util.EnumSnackEvent
import com.yanfalcao.designsystem.util.EventManager
import com.yanfalcao.designsystem.util.EventManager.AppEvent.ShowSnackbar
import com.yanfalcao.designsystem.util.EventManager.AppEvent.OpenBottomSheet
import com.yanfalcao.designsystem.util.EventManager.AppEvent.CloseBottomSheet
import com.yanfalcao.designsystem.util.EventManager.AppEvent.CloseScreen
import com.yanfalcao.designsystem.util.EventManager.AppEvent.OpenConfirmationDialog
import com.yanfalcao.designsystem.util.EventManager.AppEvent.CloseConfirmationDialog
import com.yanfalcao.designsystem.widget.ConfirmationDialog
import com.yanfalcao.productDetails.state.ProductDetailsIntent
import com.yanfalcao.productDetails.widget.ComparisonUnitSection
import com.yanfalcao.designsystem.widget.CustomTopBar
import com.yanfalcao.productDetails.widget.BottomSheetItem
import com.yanfalcao.productDetails.widget.CustomExpandableFAB
import com.yanfalcao.productDetails.widget.EmptyListText
import com.yanfalcao.productDetails.widget.FABItem
import com.yanfalcao.productDetails.widget.ProductComparisonCard
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
    var showSheet by remember { mutableStateOf(false) }
    var showConfirmChanges by remember { mutableStateOf(false) }

    val snackDeleteText = stringResource(Res.string.snackbar_delete)
    val undoLabelText = stringResource(Res.string.undo)

    LaunchedEffect(EventManager) {
        EventManager.eventsFlow.collect { event ->
            when(event) {
                is ShowSnackbar -> {
                    when(event.snackEvent) {
                        EnumSnackEvent.DELETE_ITEM -> {
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
                        else -> {}
                    }
                }
                is OpenBottomSheet -> {
                    showSheet = true
                }
                is CloseBottomSheet -> {
                    showSheet = false
                }
                is CloseScreen -> {
                    onBackClick()
                }
                is OpenConfirmationDialog -> {
                    showConfirmChanges = true
                }
                is CloseConfirmationDialog -> {
                    showConfirmChanges = false
                }
                else -> {}
            }
        }
    }

    ProductDetailsScreen(
        showSheet = showSheet,
        showConfirmChanges = showConfirmChanges,
        snackbarHostState = snackbarHostState,
        viewModel = viewModel,
        onBack = onBackClick,
    )
}

@Composable
fun ProductDetailsScreen(
    showSheet: Boolean,
    showConfirmChanges: Boolean,
    snackbarHostState: SnackbarHostState,
    viewModel: ProductDetailsVM,
    onBack: () -> Unit,
) {
    val state by viewModel.productViewState.collectAsState()
    val lazyListState = rememberLazyListState()

    if (lazyListState.isScrollInProgress) {
        val keyboardController = LocalSoftwareKeyboardController.current
        keyboardController?.hide()
    }

    if (showConfirmChanges) {
        ConfirmationDialog(
            title = stringResource(Res.string.confirmation_dialog_title),
            text = stringResource(Res.string.confirmation_dialog_message),
            onConfirm = {
                EventManager.triggerEvent(CloseConfirmationDialog)
                viewModel.handleIntent(ProductDetailsIntent.SaveProduct)
            },
            onDismiss = {
                EventManager.triggerEvent(CloseConfirmationDialog)
                onBack()
            }
        )
    }

    if (showSheet) {
        BottomSheetItem(viewModel)
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        topBar = {
            CustomTopBar(
                title = stringResource(Res.string.product),
                contentDescription = stringResource(Res.string.cd_back_button),
                onBack = { viewModel.handleIntent(ProductDetailsIntent.CloseScreen) }
            )
        },
        floatingActionButton = {
            val itemList = listOf(
                FABItem(
                    icon = Res.drawable.ic_save,
                    text = stringResource(Res.string.save),
                    contentDescription = stringResource(Res.string.cd_save),
                    onClick = { viewModel.handleIntent(ProductDetailsIntent.SaveProduct) }
                ),
                FABItem(
                    icon = Res.drawable.ic_tag,
                    text = stringResource(Res.string.add_item),
                    contentDescription = stringResource(Res.string.cd_add_item),
                    onClick = { viewModel.handleIntent(ProductDetailsIntent.OpenItemToCreate) }
                ),
            )

            CustomExpandableFAB(
                items = itemList
            )
        }
    ) { padding ->
        LazyColumn (
            state = lazyListState,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 15.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                ComparisonUnitSection(
                    state = state,
                    handleIntent = viewModel::handleIntent,
                )
            }

            if(state.product.itens.isEmpty()) {
                item {
                    Box(
                        Modifier.padding(top = 30.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        EmptyListText()
                    }
                }
            } else {
                item { Spacer(Modifier.height(10.dp)) }

                state.product.itens.forEach { itemComparison ->
                    item {
                        ProductComparisonCard(
                            item = itemComparison,
                            state = state,
                            handleIntent = viewModel::handleIntent,
                        )
                    }
                }

                item { Spacer(Modifier.height(25.dp)) }
            }
        }
    }
}