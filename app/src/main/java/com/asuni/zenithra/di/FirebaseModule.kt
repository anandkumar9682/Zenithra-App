/**
 * Author: Anand Kumar
 * Email: anandkumar.docs@gmail.com
 * Date: 2025-04-13
 * Description: This module provides dependencies related to Firebase services such as Firebase Authentication and Realtime Database.
 *              The module is used for Firebase Database and Firebase Authentication instances with Singleton scope.
 */

package com.asuni.zenithra.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class FirebaseModule @Inject constructor() {
    @Provides
    fun provideFirebaseDatabase(): DatabaseReference {
        return FirebaseDatabase.getInstance().reference.child("zenithra")
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }
}
