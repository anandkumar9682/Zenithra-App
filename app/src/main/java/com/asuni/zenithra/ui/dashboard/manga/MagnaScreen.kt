package com.asuni.zenithra.ui.dashboard.manga

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.asuni.zenithra.R
import com.asuni.zenithra.network.model.ApiResponse
import com.asuni.zenithra.network.model.MangaItem
import com.asuni.zenithra.ui.theme.HeadingTextSize
import com.asuni.zenithra.ui.theme.MessageTextSize16
import com.asuni.zenithra.util.NetworkObserver
import com.asuni.zenithra.util.NetworkUtils
import com.asuni.zenithra.viewmodels.DataViewModel


@Composable
fun MagnaScreen(viewModel: DataViewModel, navController: NavController) {

    val listState = rememberLazyListState()
    val context = LocalContext.current

    val mangaList by viewModel.mangaList.collectAsState()

    // 🔁 Pagination Trigger
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo }
            .collect { visibleItems ->
                val lastVisibleIndex = visibleItems.lastOrNull()?.index ?: return@collect

                val totalItems = listState.layoutInfo.totalItemsCount

                if (lastVisibleIndex >= totalItems - 3 && mangaList !is ApiResponse.Loading) {
                    viewModel.fetchMangaList()
                }
            }
    }

    val dummyList = List(15) {
        MangaItem(
            id = "",
            title = "",
            subTitle = "",
            status = "",
            thumb = "",
            summary = "",
            authors = emptyList(),
            genres = emptyList(),
            nsfw = false,
            type = "",
            totalChapter = 0,
            createdAt = 0,
            updatedAt = 0,
            isDummy = true
        )
    }

    val isConnected = remember { mutableStateOf(NetworkUtils.isInternetAvailable(context)) }

    LaunchedEffect(Unit) {
        NetworkObserver.startListening(context)
        NetworkObserver.isConnected.observeForever {
            isConnected.value = it
        }
    }

    LaunchedEffect(Unit) {
        if(isConnected.value){
            viewModel.fetchMangaList()
        }else{
            viewModel.fetchLocalMangaList()
        }
    }

    val displayList = if (viewModel.mangaItems.isNotEmpty()) {
        viewModel.mangaItems
    } else if (mangaList is ApiResponse.Loading) {
        dummyList
    } else emptyList()


    if (displayList.isNotEmpty()) {
        LazyColumn(state = listState, modifier = Modifier.padding(vertical = 4.dp)) {
            items(displayList.chunked(3)) { rowItems ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 4.dp, end = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    rowItems.forEach { item ->
                        MagnaItemScreen(
                            mangaItem = item,
                            viewDetails = {
                                navController.navigate("mangaDetail/${item.id}") },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    repeat(3 - rowItems.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }

            if (mangaList is ApiResponse.Loading && viewModel.mangaItems.isNotEmpty()) {
                item {
                    Text(
                        text = "Loading more...",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }

        }

    } else {

        var isDarkTheme = isSystemInDarkTheme()
        val backgroundColor = if (isDarkTheme) Color(0xFF121212) else Color.White
        val textColor = if (isDarkTheme) Color.White else Color.Black


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_empty_box),
                contentDescription = "No Manga",
                modifier = Modifier.size(120.dp) // More vertical than wide
            )
            Text(
                text = "No Manga Discovered 📚",
                fontSize = HeadingTextSize,
                modifier = Modifier.padding(top = 10.dp),
                textAlign = TextAlign.Center,
                color = textColor
            )
            Text(
                text = "Looks like your manga shelf is empty. Start scanning to uncover epic stories!",
                fontSize = MessageTextSize16,
                modifier = Modifier.padding(top = 10.dp),
                textAlign = TextAlign.Center,
                color = textColor
            )
        }
    }
}

