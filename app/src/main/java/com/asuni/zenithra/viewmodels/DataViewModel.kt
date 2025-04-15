/**
 * Author: Anand Kumar
 * Email: aanandkumarbind9682@gmail.com
 * Date: 2025-04-13
 * Description: ViewModel for managing manga data. It fetches a list of manga from the repository,
 * handles pagination, and stores the data in a mutable list. It also provides functionality to fetch
 * local manga items and manage the selected genres, type, and NSFW settings.
 */

package com.asuni.zenithra.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asuni.zenithra.domain.toMangaItem
import com.asuni.zenithra.network.model.ApiResponse
import com.asuni.zenithra.network.model.MangaItem
import com.asuni.zenithra.network.model.MangaResponse
import com.asuni.zenithra.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DataViewModel @Inject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {

    private val _mangaList = MutableStateFlow<ApiResponse<MangaResponse>>(ApiResponse.Clear())
    val mangaList: StateFlow<ApiResponse<MangaResponse>> = _mangaList

    var mangaItems = mutableStateListOf<MangaItem>()

    var selectedGenres by mutableStateOf("")
    var selectedType by mutableStateOf("all")
    var nsfwEnabled by mutableStateOf(true)
    var currentPage by mutableStateOf(1)
    private var isFetching = false

    val pageSize = 20
    var pageNumber = 1

    fun fetchMangaList(isConnected: Boolean) {
        if (isFetching) return
        isFetching = true

        if (isConnected) {
            viewModelScope.launch(Dispatchers.IO) {
                dataRepository.fetchMangaList(
                    page = currentPage,
                    genres = selectedGenres,
                    nsfw = nsfwEnabled,
                    type = selectedType
                ).collect { response ->
                    _mangaList.value = response

                    if (response is ApiResponse.Success) {
                        response.data?.data?.let { newItems ->
                            val filtered = newItems.filterNot { apiItem ->
                                mangaItems.any { it.id == apiItem.id }
                            }
                            if (filtered.isNotEmpty()) {
                                mangaItems.addAll(filtered)
                                currentPage++
                            }
                        }
                    }
                    isFetching = false
                }
            }
        } else {
            fetchLocalMangaList()
        }
    }

    fun fetchLocalMangaList() {
        val offset = (pageNumber - 1) * pageSize
        viewModelScope.launch(Dispatchers.IO) {
            val localItems = dataRepository.fetchLocalMangaList(pageSize, offset)
                .map { it.toMangaItem() }
            mangaItems.addAll(localItems)
            pageNumber++
            isFetching = false
        }
    }

    fun refreshData(isConnected: Boolean) {
        viewModelScope.launch {
            mangaItems.clear()
            currentPage = 1
            pageNumber = 1
            fetchMangaList(isConnected)
        }
    }

    fun getMangaItemById(mangaItemId: String): MangaItem? {
        return mangaItems.firstOrNull { it.id == mangaItemId }
    }
}
