package com.yanfalcao.productDetails.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.yanfalcao.designsystem.util.Validation

@Composable
fun InputDisplayField(
    modifier: Modifier = Modifier,
    label: String? = null,
    posfix: String = "",
    onChange: (String) -> Unit,
    textState: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    checkFormat: Boolean = false
) {
    val inputText = remember { mutableStateOf("") }
    val outlineError = remember { mutableStateOf(false) }

    if (textState.isNotEmpty()) {
        inputText.value = textState
    }

    outlineError.value = if(checkFormat) {
        when (keyboardOptions.keyboardType) {
            KeyboardType.Number -> !Validation.validNumber(inputText.value)
            KeyboardType.Decimal -> !Validation.validNumber(inputText.value)
            KeyboardType.Unspecified -> !Validation.validString(inputText.value)
            KeyboardType.Text -> !Validation.validString(inputText.value)
            else -> false
        }
    } else {
        false
    }

    val borderErrorColor = if (outlineError.value)
        MaterialTheme.colorScheme.error
    else
        MaterialTheme.colorScheme.surface

    BasicTextField(
        value = inputText.value,
        onValueChange = { newText ->
            inputText.value = newText
            onChange(newText)
        },
        keyboardOptions = keyboardOptions,
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(size = 30.dp),
            )
            .height(height = 50.dp),
        cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
        textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
        singleLine = true,
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .border(
                        width = 1.5.dp,
                        color = borderErrorColor,
                        shape = RoundedCornerShape(size = 30.dp)
                    )
                    .padding(start = 20.dp, end = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box {
                    if (inputText.value.isEmpty()) {
                        Text(
                            text = label ?: "",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = MaterialTheme.colorScheme.outline,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                    Row{
                        Box(Modifier.weight(1f)) {
                            innerTextField()
                        }

                        if (posfix.isNotEmpty()) {
                            Text(
                                modifier = Modifier
                                    .weight(0.5f)
                                    .padding(start = 8.dp),
                                text = posfix,
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }
                    }

                }
            }
        },
    )
}