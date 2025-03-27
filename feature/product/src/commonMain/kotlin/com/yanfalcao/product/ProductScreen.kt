@file:OptIn(ExperimentalMaterial3Api::class, KoinExperimentalAPI::class)

package com.yanfalcao.product

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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

@Composable
fun ProductRoute() {
    ProductScreen()
}

@Composable
fun ProductScreen() {
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

            EmptyListText()
        }
    }
}