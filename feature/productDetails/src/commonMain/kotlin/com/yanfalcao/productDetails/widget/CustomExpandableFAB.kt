package com.yanfalcao.productDetails.widget

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import calculadorademercado.feature.productdetails.generated.resources.Res
import calculadorademercado.feature.productdetails.generated.resources.cd_options
import calculadorademercado.feature.productdetails.generated.resources.options
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

data class FABItem(
    val icon: DrawableResource,
    val text: String,
    val contentDescription: String? = null,
    val onClick: (() -> Unit)? = null,
)

@Composable
fun CustomExpandableFAB(
    modifier: Modifier = Modifier,
    items: List<FABItem>,
) {

    var buttonClicked by remember {
        mutableStateOf(false)
    }

    val interactionSource = MutableInteractionSource()

    Card(
        modifier = modifier.sizeIn(maxWidth = 200.dp),
        shape = RoundedCornerShape(30.dp),
        elevation = CardDefaults.elevatedCardElevation(4.dp)
    ) {
        Column {
            AnimatedVisibility(
                visible = buttonClicked,
                enter = expandVertically(tween(1300)) + fadeIn(),
                exit = shrinkVertically(tween(1200)) + fadeOut(
                    animationSpec = tween(1000)
                )
            ) {
                Column(
                    modifier = Modifier.padding(vertical = 16.dp, horizontal = 25.dp),
                ) {
                    items.forEach { item ->
                        Row(modifier = Modifier
                            .padding(vertical = 10.dp)
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null,
                                onClick = {
                                    item.onClick?.let { it() }
                                    buttonClicked = false
                                }
                            )) {

                            Image(
                                painter = painterResource(item.icon),
                                contentDescription = item.contentDescription
                            )

                            Spacer(modifier = Modifier.width(15.dp))

                            Text(text = item.text)
                        }
                    }
                }
            }

            Card(
                modifier = Modifier
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = {
                            buttonClicked = !buttonClicked
                        }
                    ),
                shape = RoundedCornerShape(30.dp),
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary)
            ) {
                Row(
                    modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = stringResource(Res.string.cd_options),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                    AnimatedVisibility(
                        visible = buttonClicked,
                        enter = expandVertically(animationSpec = tween(1300)) + fadeIn(),
                        exit = shrinkVertically(tween(1200)) + fadeOut(tween(1200))
                    ) {
                        Row(Modifier.fillMaxWidth(1f)) {
                            Spacer(modifier = Modifier.width(20.dp))
                            Text(text = stringResource(Res.string.options))
                        }
                    }
                }
            }

        }

    }

}