package com.yanfalcao.product.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yanfalcao.designsystem.widget.SkeletonLoadingWidget

@Composable
fun LoadingProducts() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        repeat(times = 5) {
            SkeletonLoadingWidget(Modifier.height(100.dp))
        }
    }
}