@file:OptIn(ExperimentalMaterial3Api::class, KoinExperimentalAPI::class,
    ExperimentalMaterial3Api::class
)

package com.yanfalcao.product

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import calculadorademercado.feature.product.generated.resources.Res
import calculadorademercado.feature.product.generated.resources.comparatives
import calculadorademercado.feature.product.generated.resources.snackbar_delete
import calculadorademercado.feature.product.generated.resources.undo
import com.yanfalcao.designsystem.util.EnumSnackEvent
import com.yanfalcao.designsystem.util.EventManager
import com.yanfalcao.designsystem.util.EventManager.AppEvent.ShowSnackbar
import com.yanfalcao.designsystem.util.EventManager.AppEvent.NavigateToProductDetail
import com.yanfalcao.product.state.ProductIntent
import com.yanfalcao.product.state.ProductViewState
import com.yanfalcao.product.widget.ComparativeList
import com.yanfalcao.product.widget.CustomSearchBar
import com.yanfalcao.product.widget.EmptyListText
import com.yanfalcao.product.widget.FloatingButton
import com.yanfalcao.product.widget.LoadingProducts
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import kotlinx.coroutines.launch

@Composable
fun ProductRoute(
    navigateToProductDetail: (String?) -> Unit,
    viewModel: ProductViewModel = koinViewModel()
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
                                        viewModel.handleIntent(ProductIntent.RemoveLastUndo)
                                    }
                                    SnackbarResult.ActionPerformed -> {
                                        viewModel.handleIntent(ProductIntent.UndoAction)
                                    }
                                }
                            }
                        }
                    }
                }

                is NavigateToProductDetail -> {
                    navigateToProductDetail(event.productId)
                }
            }
        }
    }

    ProductScreen(
        snackbarHostState = snackbarHostState,
        state = productViewState,
        handleIntent = viewModel::handleIntent
    )
}

@Composable
fun ProductScreen(
    snackbarHostState: SnackbarHostState,
    state: ProductViewState,
    handleIntent: (ProductIntent) -> Unit
) {
    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                ),
                title = {
                    Text(
                        text = stringResource(Res.string.comparatives),
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingButton(
                onClick = { handleIntent(ProductIntent.OpenProductToCreate) }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 15.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomSearchBar(
                modifier = Modifier.fillMaxWidth(),
                handleIntent = handleIntent,
                state = state,
            )

            Spacer(Modifier.height(40.dp))

            if(state.isLoading) {
                LoadingProducts()

            } else if(state.products.isEmpty()) {
                EmptyListText()

            } else {
                val products = state.products
                ComparativeList(
                    modifier = Modifier.fillMaxSize(),
                    products = products,
                    handleIntent = handleIntent
                )
            }
        }
    }
}