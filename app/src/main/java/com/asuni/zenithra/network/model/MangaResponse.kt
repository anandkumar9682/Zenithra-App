package com.asuni.zenithra.network.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Author: Anand Kumar
 * Email: anandkumar.docs@gmail.com
 * Date: 2025-04-13
 * Description: This data class represents the response structure for Manga data,
 * including Manga details such as title, authors, genres, etc.
 */

@Serializable
data class MangaResponse(
    @SerialName("code")
    val code: Int,

    @SerialName("data")
    val data: List<MangaItem>
)

@Parcelize
data class MangaItem(

    val isDummy: Boolean = false,

    @SerialName("id")
    val id: String,

    @SerialName("title")
    val title: String,

    @SerialName("sub_title")
    val subTitle: String?,

    @SerialName("status")
    val status: String,

    @SerialName("thumb")
    val thumb: String,

    @SerialName("summary")
    val summary: String,

    @SerialName("authors")
    val authors: List<String>,

    @SerialName("genres")
    val genres: List<String>,

    @SerialName("nsfw")
    val nsfw: Boolean,

    @SerialName("type")
    val type: String,

    @SerialName("total_chapter")
    val totalChapter: Int,

    @SerialName("create_at")
    val createdAt: Long,

    @SerialName("update_at")
    val updatedAt: Long
): Parcelable
