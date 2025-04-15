package com.asuni.zenithra.repository

import com.asuni.zenithra.database.AppDatabase
import com.asuni.zenithra.domain.MangaItemEntity
import com.asuni.zenithra.domain.toEntity
import com.asuni.zenithra.network.NetworkApi
import com.asuni.zenithra.network.model.ApiResponse
import com.asuni.zenithra.network.model.MangaResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Author: Anand Kumar
 * Email: anandkumar.docs@gmail.com
 * Date: 2025-04-13
 * Description: Repository class responsible for fetching and storing manga data. It retrieves manga lists from
 * both the local database and remote API and manages operations such as saving manga data locally.
 */
class DataRepository @Inject constructor(
    private val apiService: NetworkApi,
    private val appDatabase: AppDatabase
) {

    /**
     * Fetches the local manga list from the database.
     *
     * @return A list of manga items from the local database.
     */
    suspend fun fetchLocalMangaList(limit: Int, offset: Int): List<MangaItemEntity> {
        return appDatabase.mangaDao.getMangaByPage(limit = limit, offset = offset)
    }

    /**
     * Fetches the manga list from the remote API and saves it to the local database.
     *
     * @param page The page number for pagination.
     * @param genres The genre filter for manga.
     * @param nsfw A flag indicating whether NSFW content should be included.
     * @param type The type of manga (e.g., series, one-shot).
     * @return A flow emitting the result of the API call.
     */
    suspend fun fetchMangaList(
        page: Int,
        genres: String,
        nsfw: Boolean = true,
        type: String = "all"
    ): Flow<ApiResponse<MangaResponse>> = flow {
        emit(ApiResponse.Loading()) // Emit loading state to notify the UI
        try {
            // Fetch data from the remote API
            val response = withContext(Dispatchers.IO) {
                apiService.fetchManga(page, genres, nsfw, type)
            }
            if (response.isSuccessful) {
                response.body()?.let {
                    // Save the fetched data into the local database
                    val entities = it.data.map { item -> item.toEntity() }
                    appDatabase.mangaDao.insertAll(entities)
                    emit(ApiResponse.Success(it)) // Emit success with manga data
                } ?: emit(ApiResponse.Error("No data received"))
            } else {
                val error = response.errorBody()?.string() ?: "Unknown error"
                emit(ApiResponse.Error("API Error: $error"))
            }

        } catch (e: Exception) {
            emit(ApiResponse.Error("Exception: ${e.message ?: "Something went wrong"}"))
        }
    }
}
