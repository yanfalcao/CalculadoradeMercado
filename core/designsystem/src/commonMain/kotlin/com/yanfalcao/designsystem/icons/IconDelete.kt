package com.yanfalcao.designsystem.icons

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import calculadorademercado.core.designsystem.generated.resources.Res
import calculadorademercado.core.designsystem.generated.resources.cd_delete_button
import calculadorademercado.core.designsystem.generated.resources.ic_trash
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun IconDelete() {
    Image(
        painter = painterResource(Res.drawable.ic_trash),
        contentDescription = stringResource(Res.string.cd_delete_button),
    )
}