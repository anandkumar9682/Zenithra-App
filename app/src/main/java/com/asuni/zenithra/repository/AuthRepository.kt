package com.asuni.zenithra.repository

import com.asuni.zenithra.database.AppDatabase
import com.asuni.zenithra.network.NetworkApi
import com.asuni.zenithra.network.model.ApiResponse
import com.asuni.zenithra.ui.authentication.models.LoginRequest
import com.asuni.zenithra.util.helper.FirebaseHelper
import com.asuni.zenithra.util.helper.SharedPreferenceHelper
import com.asuni.zenithra.util.helper.SharedPreferenceHelper.Companion.IS_LOGED_IN
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Author: Anand Kumar
 * Email: anandkumar.docs@gmail.com
 * Date: 2025-04-13
 * Description: Repository class responsible for handling user-related operations such as login, logout,
 * fetching manga data, and password reset. It communicates with both local database (Room) and remote API
 * services, and manages user authentication via Firebase.
 */
class AuthRepository @Inject constructor(
    private val apiService: NetworkApi,
    private val appDatabase: AppDatabase,
    private val firebaseHelper: FirebaseHelper,
    private val sharedPreferenceHelper: SharedPreferenceHelper
) {

    /**
     * Sign up the user with the provided user and credentials details.
     * Emits loading, success, or error states based on the login result.
     */
    suspend fun signUp(name: String, mobile: String, email: String, password: String): Flow<ApiResponse<Pair<Boolean, String>>> = flow {
        emit(ApiResponse.Loading())
        try {
            val result = firebaseHelper.signUp(name, mobile, email, password)
            if (result.first) {
//                sharedPreferenceHelper.setBoolean(IS_LOGED_IN, true)
                emit(ApiResponse.Success(result))
            } else {
                emit(ApiResponse.Error(result.second))
            }
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.message ?: "Unknown error"))
        }
    }



    /**
     * Logs in the user with the provided login request containing credentials.
     * Emits loading, success, or error states based on the login result.
     */
    suspend fun login(loginRequest: LoginRequest): Flow<ApiResponse<Pair<Boolean, String>>> = flow {
        emit(ApiResponse.Loading()) // Emit loading state
        try {
            val response = firebaseHelper.login(loginRequest)
            if (response.first) {
                appDatabase.usersDao.deleteAllUsers()
                sharedPreferenceHelper.setBoolean(IS_LOGED_IN, true) // Store login status
                emit(ApiResponse.Success(response))
            } else {
                emit(ApiResponse.Error(response.second))
            }
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.message ?: "Unknown error"))
        }
    }


    /**
     * Logs out the user, clears login status, deletes user data from local database.
     * Emits loading, success, or error states based on the result.
     */
    fun logout(): Flow<ApiResponse<Pair<Boolean, String>>> = flow {
        emit(ApiResponse.Loading())
        try {
            val result = firebaseHelper.logout()
            if (result.first) {
                // Successfully logged out, clear session
                sharedPreferenceHelper.setBoolean(IS_LOGED_IN, false)
                appDatabase.usersDao.deleteAllUsers()
                emit(ApiResponse.Success(result))
            } else {
                emit(ApiResponse.Error(result.second))
            }
        } catch (e: Exception) {
            emit(ApiResponse.Error("❌ Logout failed: ${e.message}"))
        }
    }

    /**
     * Resets the password for the given email address.
     * Emits loading, success, or error states based on the result.
     */
    suspend fun resetPassword(email: String): Flow<ApiResponse<Pair<Boolean, String>>> = flow {
        emit(ApiResponse.Loading()) // Emit loading state
        try {
            val response = firebaseHelper.resetPassword(email)
            if (response.first) {
                emit(ApiResponse.Success(response))
            } else {
                emit(ApiResponse.Error(response.second))
            }
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.message ?: "Unknown error"))
        }
    }


    /**
     * Signs in the user using the Google ID token.
     * Emits loading, success, or error states based on the Google sign-in result.
     */
    suspend fun signInWithGoogle(idToken: String): Flow<ApiResponse<Pair<Boolean, String>>> = flow {
        emit(ApiResponse.Loading())
        try {
            val result = firebaseHelper.signInWithGoogle(idToken)
            if (result.first) {
                appDatabase.usersDao.deleteAllUsers()
                sharedPreferenceHelper.setBoolean(IS_LOGED_IN, true)
                emit(ApiResponse.Success(result))
            } else {
                emit(ApiResponse.Error(result.second))
            }
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.message ?: "Unknown error"))
        }
    }

    /**
     * Checks if the user is logged in by checking the shared preferences.
     * Returns true if the user is logged in, otherwise false.
     */
    fun isLoggedIn(): Boolean = sharedPreferenceHelper.getBoolean(IS_LOGED_IN, false)
}