package com.asuni.zenithra.ui.dashboard.manga

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.asuni.zenithra.network.model.MangaItem

@Composable
fun ShimmerPlaceholder(modifier: Modifier = Modifier) {
    val transition = rememberInfiniteTransition()
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500, easing = LinearEasing)
        )
    )

    val brush = Brush.linearGradient(
        colors = listOf(
            Color.LightGray.copy(alpha = 0.2f),
            Color.Gray.copy(alpha = 0.2f),
            Color.LightGray.copy(alpha = 0.3f)
        ),
        start = Offset(translateAnim.value, translateAnim.value),
        end = Offset(translateAnim.value + 200f, translateAnim.value + 200f)
    )

    Box(
        modifier = modifier
            .background(brush = brush, shape = RoundedCornerShape(8.dp))
    )
}



@Composable
fun MagnaItemScreen(
    mangaItem: MangaItem,
    viewDetails: (MangaItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val isDummy = mangaItem.isDummy

    var isImageLoading = remember { mutableStateOf(true) }

    Column(
        modifier = modifier
            .padding(4.dp)
            .clickable(enabled = !isDummy) { viewDetails(mangaItem) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .height(140.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
        ) {
            if (isDummy || isImageLoading.value) {
                ShimmerPlaceholder(modifier = Modifier.fillMaxSize())
            }

            AsyncImage(
                model = mangaItem.thumb,
                contentDescription = "Manga Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .matchParentSize(),
                onState = {
                    isImageLoading.value = it is AsyncImagePainter.State.Loading
                }
            )
        }
    }
}
