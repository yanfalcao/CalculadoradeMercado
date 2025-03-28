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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import calculadorademercado.feature.product.generated.resources.Res
import calculadorademercado.feature.product.generated.resources.comparatives
import com.yanfalcao.product.widget.ComparativeListItem
import com.yanfalcao.product.widget.CustomSearchBar
import com.yanfalcao.product.widget.FloatingButton
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@Composable
fun ProductRoute(
    viewModel: ProductViewModel = koinViewModel()
) {
    ProductScreen()
}

@Composable
fun ProductScreen() {
    val items = listOf(
        "Aveia" to "2 itens  21 fev, 2025",
        "Maionese" to "3 itens  21 fev, 2025",
        "Creme de Cabelo" to "4 itens  21 fev, 2025",
        "Frango" to "3 itens  20 fev, 2025"
    )

    Scaffold(
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
            FloatingButton()
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
            CustomSearchBar(modifier = Modifier.fillMaxWidth(),)

            Spacer(Modifier.height(40.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                items(items.size) { index ->
                    ComparativeListItem()
                }
            }
        }
    }
}