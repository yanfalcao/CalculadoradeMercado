package com.yanfalcao.designsystem.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun CustomSearchBar(
    modifier: Modifier = Modifier
) {
    val searchText = remember { mutableStateOf(TextFieldValue()) }

    BasicTextField(
        value = searchText.value,
        onValueChange = { newText ->
            searchText.value = newText
        },
        modifier =
        modifier
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
                modifier = Modifier.padding(start = 15.dp, end = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    modifier = Modifier.size(25.dp),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface,
                )

                innerTextField()
            }
        },
    )
}