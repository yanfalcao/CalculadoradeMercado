package com.yanfalcao.product.widget

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import calculadorademercado.feature.product.generated.resources.Res
import calculadorademercado.feature.product.generated.resources.cd_add_floating_button
import org.jetbrains.compose.resources.stringResource

@Composable
fun FloatingButton(
    onClick: () -> Unit
) {
    FloatingActionButton(
        onClick = onClick,
        shape = CircleShape,
        containerColor = MaterialTheme.colorScheme.primary,
    ) {
        Icon(
            Icons.Filled.Add,
            contentDescription = stringResource(Res.string.cd_add_floating_button),
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}