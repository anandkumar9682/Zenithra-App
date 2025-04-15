package com.asuni.zenithra.domain

import android.util.Log
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.asuni.zenithra.network.model.MangaItem

/**
 * Author: Anand Kumar
 * Email: anandkumar.docs@gmail.com
 * Date: 2025-04-13
 * Description: Entity class for Manga Item, used in Room database for storing manga details.
 */

@Entity(tableName = "manga_items")
data class MangaItemEntity(
    @PrimaryKey val id: String,
    val title: String,
    val subTitle: String?,
    val status: String,
    val thumb: String,
    val summary: String,
    val authors: String, // List of authors as a comma-separated string
    val genres: String,  // List of genres as a comma-separated string
    val nsfw: Boolean,
    val type: String,
    val totalChapter: Int,
    val createdAt: Long,
    val updatedAt: Long
)

/**
 * Extension function to convert MangaItem to MangaItemEntity.
 * Converts authors and genres lists into comma-separated strings.
 */
fun MangaItem.toEntity(): MangaItemEntity = MangaItemEntity(
    id = id,
    title = title,
    subTitle = subTitle,
    status = status,
    thumb = thumb,
    summary = summary,
    authors = authors.joinToString(","),
    genres = genres.joinToString(","),
    nsfw = nsfw,
    type = type,
    totalChapter = totalChapter,
    createdAt = createdAt,
    updatedAt = updatedAt
)

/**
 * Extension function to convert MangaItemEntity to MangaItem.
 * Converts comma-separated authors and genres strings back into lists.
 */
fun MangaItemEntity.toMangaItem(): MangaItem {
Log.d("TestLog","$this")
return MangaItem(
    id = id,
    title = title,
    subTitle = subTitle,
    status = status,
    thumb = thumb,
    summary = summary,
    authors = authors.split(",").takeIf { it.isNotEmpty() } ?: emptyList(),
    genres = genres.split(",").takeIf { it.isNotEmpty() } ?: emptyList(),
    nsfw = nsfw,
    type = type,
    totalChapter = totalChapter,
    createdAt = createdAt,
    updatedAt = updatedAt)
}