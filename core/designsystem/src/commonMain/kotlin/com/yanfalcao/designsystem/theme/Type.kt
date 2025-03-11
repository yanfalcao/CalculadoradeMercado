package com.yanfalcao.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import calculadorademercado.core.designsystem.generated.resources.Montserrat_Bold
import calculadorademercado.core.designsystem.generated.resources.Montserrat_ExtraBold
import calculadorademercado.core.designsystem.generated.resources.Montserrat_Medium
import calculadorademercado.core.designsystem.generated.resources.Montserrat_Regular
import calculadorademercado.core.designsystem.generated.resources.Montserrat_SemiBold
import calculadorademercado.core.designsystem.generated.resources.Res
import org.jetbrains.compose.resources.Font

@Composable
fun AppTypography(): Typography {
    val MontserratFont =
        FontFamily(
            Font(Res.font.Montserrat_Regular, FontWeight.Normal),
            Font(Res.font.Montserrat_Medium, FontWeight.Medium),
            Font(Res.font.Montserrat_Bold, FontWeight.Bold),
            Font(Res.font.Montserrat_SemiBold, FontWeight.SemiBold),
            Font(Res.font.Montserrat_ExtraBold, FontWeight.ExtraBold),
        )

    return Typography(
        headlineLarge = MaterialTheme.typography.headlineLarge.copy(
            fontFamily = MontserratFont,
        ),
        headlineMedium = MaterialTheme.typography.headlineMedium.copy(
            fontFamily = MontserratFont,
        ),
        headlineSmall = MaterialTheme.typography.headlineSmall.copy(
            fontFamily = MontserratFont,
        ),
        titleLarge = MaterialTheme.typography.titleLarge.copy(
            fontFamily = MontserratFont,
        ),
        titleMedium = MaterialTheme.typography.titleMedium.copy(
            fontFamily = MontserratFont,
        ),
        titleSmall = MaterialTheme.typography.titleSmall.copy(
            fontFamily = MontserratFont,
        ),
        bodyLarge = MaterialTheme.typography.bodyLarge.copy(
            fontFamily = MontserratFont,
        ),
        bodyMedium = MaterialTheme.typography.bodyMedium.copy(
            fontFamily = MontserratFont,
        ),
        labelLarge = MaterialTheme.typography.labelLarge.copy(
            fontFamily = MontserratFont,
        ),
        labelMedium = MaterialTheme.typography.labelMedium.copy(
            fontFamily = MontserratFont,
        ),
        labelSmall = MaterialTheme.typography.labelSmall.copy(
            fontFamily = MontserratFont,
        ),
    )
}