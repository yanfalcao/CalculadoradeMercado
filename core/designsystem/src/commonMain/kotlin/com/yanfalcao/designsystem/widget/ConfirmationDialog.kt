package com.yanfalcao.designsystem.widget

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import calculadorademercado.core.designsystem.generated.resources.Res
import calculadorademercado.core.designsystem.generated.resources.no
import calculadorademercado.core.designsystem.generated.resources.yes
import com.yanfalcao.designsystem.util.EventManager
import com.yanfalcao.designsystem.util.EventManager.AppEvent.CloseConfirmationDialog
import org.jetbrains.compose.resources.stringResource

@Composable
fun ConfirmationDialog(
    title: String,
    text: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = { EventManager.triggerEvent(CloseConfirmationDialog) },
        title = { Text(title) },
        text = { Text(text) },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(stringResource(Res.string.yes))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(Res.string.no))
            }
        },
    )
}