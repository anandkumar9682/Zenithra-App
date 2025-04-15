package com.asuni.zenithra.repository

import com.asuni.zenithra.database.AppDatabase
import com.asuni.zenithra.domain.MangaItemEntity
import com.asuni.zenithra.domain.toEntity
import com.asuni.zenithra.network.NetworkApi
import com.asuni.zenithra.network.model.ApiResponse
import com.asuni.zenithra.network.model.MangaItem
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
    suspend fun fetchLocalMangaList(): List<MangaItemEntity> {
        return appDatabase.mangaDao.getAllManga()
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

//            val dummyResponse = MangaResponse(
//                code = 200,
//                data = listOf(
//                    MangaItem(
//                        isDummy = false,
//                        id = "1",
//                        title = "The Cat Warrior",
//                        subTitle = "A Feline's Journey",
//                        status = "Ongoing",
//                        thumb = "https://picsum.photos/id/237/200/300",
//                        summary = "A brave cat embarks on an epic quest to protect its clan.\nA brave cat embarks on an epic quest to protect its clan.\nA brave cat embarks on an epic quest to protect its clan.\nA brave cat embarks on an epic quest to protect its clan.\nA brave cat embarks on an epic quest to protect its clan.\nA brave cat embarks on an epic quest to protect its clan.\nA brave cat embarks on an epic quest to protect its clan.\nA brave cat embarks on an epic quest to protect its clan.\nA brave cat embarks on an epic quest to protect its clan.\nA brave cat embarks on an epic quest to protect its clan.\nA brave cat embarks on an epic quest to protect its clan.\nA brave cat embarks on an epic quest to protect its clan.\nA brave cat embarks on an epic quest to protect its clan.\nA brave cat embarks on an epic quest to protect its clan.\nA brave cat embarks on an epic quest to protect its clan.\nA brave cat embarks on an epic quest to protect its clan.\nA brave cat embarks on an epic quest to protect its clan.\nA brave cat embarks on an epic quest to protect its clan.\nA brave cat embarks on an epic quest to protect its clan.\nA brave cat embarks on an epic quest to protect its clan.\nA brave cat embarks on an epic quest to protect its clan.\nA brave cat embarks on an epic quest to protect its clan.\nA brave cat embarks on an epic quest to protect its clan.\nA brave cat embarks on an epic quest to protect its clan.\nA brave cat embarks on an epic quest to protect its clan.\nA brave cat embarks on an epic quest to protect its clan.\nA brave cat embarks on an epic quest to protect its clan.\nA brave cat embarks on an epic quest to protect its clan.\nA brave cat embarks on an epic quest to protect its clan.\nA brave cat embarks on an epic quest to protect its clan.\nA brave cat embarks on an epic quest to protect its clan.\nA brave cat embarks on an epic quest to protect its clan.\nA brave cat embarks on an epic quest to protect its clan.\nA brave cat embarks on an epic quest to protect its clan.\n",
//                        authors = listOf("Anand Kumar"),
//                        genres = listOf("Action", "Adventure", "Fantasy"),
//                        nsfw = false,
//                        type = "Manga",
//                        totalChapter = 12,
//                        createdAt = System.currentTimeMillis(),
//                        updatedAt = System.currentTimeMillis()
//                    ),
//                    MangaItem(
//                        isDummy = false,
//                        id = "2",
//                        title = "Pixel Chronicles",
//                        subTitle = "Glitched Realms",
//                        status = "Completed",
//                        thumb = "https://picsum.photos/seed/picsum/200/300",
//                        summary = "A teen discovers a digital realm inside his console.",
//                        authors = listOf("A. K. Bind"),
//                        genres = listOf("Sci-Fi", "Mystery"),
//                        nsfw = false,
//                        type = "Manhwa",
//                        totalChapter = 45,
//                        createdAt = System.currentTimeMillis(),
//                        updatedAt = System.currentTimeMillis()
//                    ),
//                    MangaItem(
//                        isDummy = false,
//                        id = "3",
//                        title = "Silent Shades",
//                        subTitle = "The Grey Horizon",
//                        status = "Hiatus",
//                        thumb = "https://picsum.photos/200/300?grayscale",
//                        summary = "In a colorless world, one boy sees the truth.",
//                        authors = listOf("K. Anand"),
//                        genres = listOf("Drama", "Psychological", "Supernatural"),
//                        nsfw = false,
//                        type = "Webtoon",
//                        totalChapter = 20,
//                        createdAt = System.currentTimeMillis(),
//                        updatedAt = System.currentTimeMillis()
//                    ),
//                    MangaItem(
//                        isDummy = false,
//                        id = "4",
//                        title = "Blurred Lines",
//                        subTitle = null,
//                        status = "Ongoing",
//                        thumb = "https://picsum.photos/200/300/?blur",
//                        summary = "A detective with blurred memories solves surreal crimes.",
//                        authors = listOf("Zenithra"),
//                        genres = listOf("Mystery", "Thriller"),
//                        nsfw = true,
//                        type = "Doujinshi",
//                        totalChapter = 10,
//                        createdAt = System.currentTimeMillis(),
//                        updatedAt = System.currentTimeMillis()
//                    )
//                )
//            )
//            emit(ApiResponse.Success(dummyResponse))


        } catch (e: Exception) {
            emit(ApiResponse.Error("Exception: ${e.message ?: "Something went wrong"}"))
        }
    }
}
