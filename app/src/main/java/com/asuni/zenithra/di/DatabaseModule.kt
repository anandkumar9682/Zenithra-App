/**
 * Author: Anand Kumar
 * Email: anandkumar.docs@gmail.com
 * Date: 2025-04-13
 * Description: This is the Dagger-Hilt module that provides database-related dependencies.
 *              It provides access to the Room database (`AppDatabase`), `UsersDao`, and `MangaDao`.
 */

package com.asuni.zenithra.di

import android.content.Context
import androidx.room.Room
import com.asuni.zenithra.database.AppDatabase
import com.asuni.zenithra.database.dao.MangaDao
import com.asuni.zenithra.database.dao.UsersDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "Users"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideUsersDao(appDatabase: AppDatabase): UsersDao {
        return appDatabase.usersDao
    }

    @Provides
    fun provideMangaDao(appDatabase: AppDatabase): MangaDao {
        return appDatabase.mangaDao
    }
}