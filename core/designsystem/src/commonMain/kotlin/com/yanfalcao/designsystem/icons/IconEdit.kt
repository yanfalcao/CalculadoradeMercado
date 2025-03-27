package com.yanfalcao.designsystem.icons

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import calculadorademercado.core.designsystem.generated.resources.Res
import calculadorademercado.core.designsystem.generated.resources.cd_edit_button
import calculadorademercado.core.designsystem.generated.resources.ic_edit
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun IconEdit() {
    Image(
        painter = painterResource(Res.drawable.ic_edit),
        contentDescription = stringResource(Res.string.cd_edit_button),
    )
}