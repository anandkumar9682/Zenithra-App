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

    var selectedGenres by mutableStateOf("")
    var selectedType by mutableStateOf("all")
    var nsfwEnabled by mutableStateOf(true)
    var currentPage by mutableStateOf(1)

    val mangaItems = mutableStateListOf<MangaItem>() // Replace `Manga` with your actual item model

    private var isFetching = false

    fun fetchMangaList() {
        if (isFetching) return
        isFetching = true

        viewModelScope.launch(Dispatchers.IO) {
            dataRepository.fetchMangaList(
                page = currentPage,
                genres = selectedGenres,
                nsfw = nsfwEnabled,
                type = selectedType
            ).collect { apiResponse ->
                _mangaList.value = apiResponse

                when (apiResponse) {
                    is ApiResponse.Success -> {
                        apiResponse.data?.data?.let { newItems ->
                            if (newItems.isNotEmpty()) {
                                val uniqueNewItems = newItems.filterNot { newItem ->
                                    mangaItems.any { existing -> existing.id == newItem.id }
                                }
                                if (uniqueNewItems.isNotEmpty()) {
                                    mangaItems.addAll(uniqueNewItems)
                                    currentPage++
                                }
                            }
                        }
                        isFetching = false
                    }

                    is ApiResponse.Error -> {
                        isFetching = false
                    }

                    else -> Unit
                }
            }
        }
    }

    fun getMangaItemById(mangaItemId: String): MangaItem? {
        return mangaItems.firstOrNull { it.id == mangaItemId }
    }

    fun fetchLocalMangaList(){
        viewModelScope.launch(Dispatchers.IO) {
            val localMangaItems =   dataRepository.fetchLocalMangaList().map { item -> item.toMangaItem() }
            mangaItems.addAll(localMangaItems)
        }
    }

}
