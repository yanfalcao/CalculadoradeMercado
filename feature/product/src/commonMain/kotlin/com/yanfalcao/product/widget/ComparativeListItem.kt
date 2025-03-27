package com.yanfalcao.product.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.yanfalcao.designsystem.icons.IconDelete
import com.yanfalcao.designsystem.icons.IconEdit
import kotlin.random.Random

@Composable
fun ComparativeListItem() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(getRandomPastelColor(), CircleShape)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "Item name",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Item description",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        IconButton(onClick = { /* Handle edit */ }) {
            IconEdit()
        }

        IconButton(onClick = { /* Handle delete */ }) {
            IconDelete()
        }
    }
}

fun getRandomPastelColor(): Color {
    val random = Random.Default
    return Color(
        red = (0.5f + 0.5f * random.nextFloat()).coerceIn(0f, 1f),
        green = (0.5f + 0.5f * random.nextFloat()).coerceIn(0f, 1f),
        blue = (0.5f + 0.5f * random.nextFloat()).coerceIn(0f, 1f),
        alpha = 1f
    )
}
