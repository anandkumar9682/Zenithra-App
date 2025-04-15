package com.asuni.zenithra.ui.dashboard.manga

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.asuni.zenithra.R
import com.asuni.zenithra.network.model.MangaItem
import com.asuni.zenithra.ui.theme.Black
import com.asuni.zenithra.ui.theme.White
import com.asuni.zenithra.util.formatTimestamp


@Composable
fun MangaDetailScreen(
    mangaItem: MangaItem,
    onBackClick: () -> Unit // Pass this from the calling screen
) {
    val scrollState = rememberScrollState()

    var isDarkTheme = isSystemInDarkTheme()
    val backgroundColor = if (isDarkTheme) Color(0xFF121212) else Color.White
    val textColor = if (isDarkTheme) Color.White else Color.Black


    Box(modifier = Modifier.fillMaxSize().background(color = backgroundColor)) {
        // --- Main Content ---
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_back_v2),
                        contentDescription = "Back",
                        tint = textColor
                    )
                }

                Text(
                    text ="Back",
                    style = MaterialTheme.typography.bodyMedium,
                    color = textColor,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }

            Spacer(modifier = Modifier.height(10.dp)) // Leave space for back button

            // --- Manga Thumbnail and Title ---
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {

                val painter = rememberAsyncImagePainter(model = mangaItem.thumb)
                val state = painter.state


                var isImageLoading = remember { mutableStateOf(true) }

                Column(
                    modifier = Modifier
                        .width(120.dp)
                        .height(170.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .height(140.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp)) // Add border around the Box
                    ) {
                        if (isImageLoading.value) {
                            ShimmerPlaceholder(modifier = Modifier.fillMaxSize())
                        }

                        val painter = rememberAsyncImagePainter(
                            model = mangaItem.thumb,
                            error = painterResource(id = R.drawable.icon_image_failure) // default if load fails
                        )

                        val state = painter.state
                        isImageLoading.value = state is AsyncImagePainter.State.Loading

                        Box(
                            modifier = Modifier.matchParentSize()
                        ) {
                            if (state is AsyncImagePainter.State.Error) {
                                // Show error image with specific size when loading fails
                                Image(
                                    painter = painterResource(id = R.drawable.icon_image_failure),
                                    contentDescription = "Error Image",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(40.dp) // Set size of error image
                                        .align(Alignment.Center) // Center the error image within the Box
                                )
                            } else {
                                // Show the loaded image
                                Image(
                                    painter = painter,
                                    contentDescription = "Manga Image",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.matchParentSize()
                                )
                            }
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = mangaItem.title,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.primary
                    )

                    if (!mangaItem.subTitle.isNullOrBlank())
                        Text(
                            text = mangaItem.subTitle,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // --- Status, Type, Dates ---
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                listOf(
                    "Status" to mangaItem.status,
                    "Type" to mangaItem.type,
                    "Created" to formatTimestamp(mangaItem.createdAt),
                    "Updated" to formatTimestamp(mangaItem.updatedAt)
                ).forEach { (label, value) ->
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(color = textColor)) {
                                append("$label: ")
                            }
                            withStyle(style = SpanStyle(color = Color.Gray)) {
                                append(value)
                            }
                        },
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // --- Genres ---
            if (mangaItem.genres.isNotEmpty()) {
                Text(
                    text = "Genres",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                ) {
                    mangaItem.genres.forEach { genre ->
                        Chip(
                            onClick = { /* Handle chip click */ },
                            label = { Text(genre, color = if(isDarkTheme) Black else White) },
                            modifier = Modifier
                                .padding(end = 10.dp)
                                .height(35.dp),
                            enabled = true,
                            shape = RoundedCornerShape(10.dp),
                            colors = ChipDefaults.chipColors(MaterialTheme.colorScheme.primary),
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // --- Summary ---
            Text(
                text = "Summary",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = textColor,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = mangaItem.summary,
                style = MaterialTheme.typography.bodyLarge,
                color = textColor.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(15.dp))
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MangaDetailScreenPreview() {
    val sampleManga =  MangaItem(
        id = "",
        title = "Title",
        subTitle = "Sub Title",
        status = "Running",
        thumb = "",
        summary = "Summry text",
        authors = emptyList(),
        genres = emptyList(),
        nsfw = false,
        type = "Type",
        totalChapter = 0,
        createdAt = 122212122,
        updatedAt = 122212122,
        isDummy = true
    )

    MangaDetailScreen(
        mangaItem = sampleManga,
        onBackClick = {} // No-op
    )
}
