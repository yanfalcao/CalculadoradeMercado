package com.yanfalcao.product.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
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
import calculadorademercado.feature.product.generated.resources.Res
import calculadorademercado.feature.product.generated.resources.items_quantity
import com.yanfalcao.designsystem.icons.IconDelete
import com.yanfalcao.designsystem.icons.IconEdit
import com.yanfalcao.designsystem.widget.CustomGradient
import com.yanfalcao.model.Product
import com.yanfalcao.model.extension.formatToCustomString
import org.jetbrains.compose.resources.pluralStringResource
import kotlin.random.Random

@Composable
fun ComparativeListItem(
    listSize: Int,
    index: Int,
    product: Product,
    onDelete: () -> Unit,
    onEdit: () -> Unit
) {
    val itensSize = product.itens.size

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(
                    CustomGradient(listSize, index),
                    CircleShape
                )
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = product.name,
                style = MaterialTheme.typography.titleMedium
            )
            Row {
                Text(
                    text = pluralStringResource(
                        resource = Res.plurals.items_quantity,
                        quantity = itensSize,
                        itensSize
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = "•",
                    modifier = Modifier.padding(horizontal = 8.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = product.createdAt.formatToCustomString(),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

        }

        IconButton(onClick = onEdit) {
            IconEdit()
        }

        IconButton(onClick = onDelete) {
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
