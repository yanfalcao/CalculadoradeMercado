@file:OptIn(ExperimentalMaterial3Api::class)

package com.yanfalcao.designsystem

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import calculadorademercado.feature.product.generated.resources.Res
import calculadorademercado.feature.product.generated.resources.comparatives
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
    ) {

    }
}