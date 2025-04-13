package com.asuni.zenithra.network

import com.asuni.zenithra.network.model.MangaResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Author: Anand Kumar
 * Email: anandkumar.docs@gmail.com
 * Date: 2025-04-13
 * Description: This interface defines the API endpoints for interacting with
 * the Manga-related data. The `fetchManga` function fetches the manga data based
 * on provided query parameters like genres, nsfw filter, type, and pagination.
 */
interface NetworkApi {
    /**
     * Fetches the list of manga items with the specified filters.
     *
     * @param page The page number for pagination.
     * @param genres The genre filter for manga.
     * @param nsfw A flag indicating whether NSFW content should be included.
     * @param type The type of manga (e.g., series, one-shot).
     * @param pageSize The number of items to retrieve per page.
     * @return A `Response` containing the `MangaResponse` data.
     */
    @GET("manga/fetch")
    suspend fun fetchManga(
        @Query("page") page: Int,
        @Query("genres") genres: String,
        @Query("nsfw") nsfw: Boolean,
        @Query("type") type: String,
        @Query("pageSize") pageSize: Int = 2
    ): Response<MangaResponse>
}
