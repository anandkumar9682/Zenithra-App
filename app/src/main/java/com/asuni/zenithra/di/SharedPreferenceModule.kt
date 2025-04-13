/**
 * Author: Anand Kumar
 * Email: anandkumar.docs@gmail.com
 * Date: 2025-04-13
 * Description: Provides SharedPreferences for storing app-related data securely.
 *              This SharedPreferences instance is scoped to the application's lifecycle and is available globally.
 */

package com.asuni.zenithra.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferenceModule {

    /**
     * Provides a SharedPreferences instance with a specific name.
     *
     * @param context Application context needed to get the SharedPreferences.
     * @return SharedPreferences instance used for storing and retrieving key-value pairs.
     */
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences("MySharedPrefZinithra", Context.MODE_PRIVATE)
}
