/**
 * Author: Anand Kumar
 * Email: anandkumar.docs@gmail.com
 * Date: 2025-04-13
 * Description: This is the Room Database class that holds the DAOs for accessing the data.
 *              It includes the MangaDao and UsersDao for querying and manipulating
 *              the `MangaItemEntity` and `UserDetailsEntity` entities.
 */

package com.asuni.zenithra.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.asuni.zenithra.database.dao.MangaDao
import com.asuni.zenithra.database.dao.UsersDao
import com.asuni.zenithra.domain.MangaItemEntity
import com.asuni.zenithra.domain.UserDetailsEntity

@Database(
    entities = [MangaItemEntity::class, UserDetailsEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract val usersDao: UsersDao
    abstract val mangaDao: MangaDao
}