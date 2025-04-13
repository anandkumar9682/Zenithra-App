/**
 * Author: Anand Kumar
 * Email: anandkumar.docs@gmail.com
 * Date: 2025-04-13
 * Description: This is the DAO interface for accessing Manga data from the Room database.
 *              It provides methods for inserting, querying, and clearing Manga items.
 */

package com.asuni.zenithra.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.asuni.zenithra.domain.MangaItemEntity

@Dao
interface MangaDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(mangaList: List<MangaItemEntity>)

    @Query("SELECT * FROM manga_items") // adjust table name
    suspend fun getAllManga(): List<MangaItemEntity>

    @Query("DELETE FROM manga_items")
    suspend fun clearAll()
}
