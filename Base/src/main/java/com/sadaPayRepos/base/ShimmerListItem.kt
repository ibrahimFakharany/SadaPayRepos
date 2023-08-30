package com.sadaPayRepos.base

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer

@Composable
fun ShimmerAnimation(
) {


    val ShimmerColorShades = listOf(

        Color.LightGray.copy(0.9f),

        Color.LightGray.copy(0.2f),

        Color.LightGray.copy(0.9f)

    )


    val transition = rememberInfiniteTransition()
    val translateAnim by transition.animateFloat(

        initialValue = 0f, targetValue = 1000f, animationSpec = infiniteRepeatable(
            tween(durationMillis = 1200, easing = FastOutSlowInEasing), RepeatMode.Reverse
        )
    )
    val brush = Brush.linearGradient(
        colors = ShimmerColorShades,
        start = Offset(10f, 10f),
        end = Offset(translateAnim, translateAnim)
    )

    shimmerItemLib(brush = brush)

}

@Composable
fun shimmerItemLib(brush: Brush) {
    Row(modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)) {
        Box(
            modifier = Modifier.size(64.dp).background(brush).clip(CircleShape).shimmer(),
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Box(
                modifier = Modifier.fillMaxWidth().height(16.dp).background(brush).shimmer(),
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier.weight(5f).height(16.dp).background(brush).shimmer(),
                )
                Spacer(modifier = Modifier.width(16.dp))
                Box(
                    modifier = Modifier.weight(1f).height(16.dp).background(brush).shimmer(),
                )
            }


        }

    }
}
