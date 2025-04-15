/**
 * Author: Anand Kumar
 * Email: aanandkumarbind9682@gmail.com
 * Date: 2025-04-13
 * Description: This class provides Firebase authentication and user management functionalities
 * such as login, logout, password reset, and Google sign-in integration. It also handles local
 * caching of user data via Room database.
 */

package com.asuni.zenithra.util.helper

import android.content.Context
import com.asuni.zenithra.database.dao.UsersDao
import com.asuni.zenithra.domain.UserDetailsEntity
import com.asuni.zenithra.ui.authentication.models.LoginRequest
import com.asuni.zenithra.util.NetworkUtils.isNetworkAvailable
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FirebaseHelper @Inject constructor(
    private val firebaseDbRef: DatabaseReference,
    private val usersDao: UsersDao,
    private val context: Context,
    private val auth: FirebaseAuth
) {
    companion object {
        private const val TAG = "TestLog"
    }

    suspend fun signUp(name: String, mobile: String, email: String, password: String): Pair<Boolean, String> =
        suspendCancellableCoroutine { cont ->
            if (!isNetworkAvailable(context)) {
                cont.resume(false to "❌ No Internet", null)
                return@suspendCancellableCoroutine
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        val userData = UserDetailsEntity(
                            uid = user?.uid ?: "",
                            name = name,
                            email = user?.email ?: email,
                            photoUrl = user?.photoUrl?.toString() ?: "",
                            phoneNumber = mobile,
                            emailVerified = user?.isEmailVerified.toString(),
                            accountCreated = user?.metadata?.creationTimestamp?.toString() ?: "",
                            lastSignIn = user?.metadata?.lastSignInTimestamp?.toString() ?: ""
                        )

                        CoroutineScope(Dispatchers.IO).launch {
                            usersDao.insertUser(userData)
                        }

                        cont.resume(true to "✅ Signup successful.", null)
                    } else {
                        val errorMessage = when (val exception = task.exception) {
                            is FirebaseAuthUserCollisionException -> "❌ Email already exists."
                            else -> "❌ ${exception?.message ?: "Signup failed."}"
                        }
                        cont.resume(false to errorMessage, null)
                    }
                }
        }


    suspend fun login(loginRequest: LoginRequest): Pair<Boolean, String> =
        suspendCancellableCoroutine { cont ->
            if (!isNetworkAvailable(context)) {
                cont.resume(false to "❌ No Internet", null)
                return@suspendCancellableCoroutine
            }
            auth.signInWithEmailAndPassword(loginRequest.email, loginRequest.password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        CoroutineScope(Dispatchers.IO).launch {
                            val userData = UserDetailsEntity(
                                uid = user?.uid ?: "",
                                name = user?.displayName ?: "NA",
                                email = user?.email ?: "NA",
                                photoUrl = user?.photoUrl?.toString() ?: "NA",
                                phoneNumber = user?.phoneNumber ?: "NA",
                                emailVerified = user?.isEmailVerified.toString(),
                                accountCreated = user?.metadata?.creationTimestamp?.toString() ?: "NA",
                                lastSignIn = user?.metadata?.lastSignInTimestamp?.toString() ?: "NA"
                            )
                            usersDao.insertUser(userData)
                        }

                        cont.resume(true to "✅ Login successful.", null)
                    } else {
                        val exception = task.exception
                        val errorMessage = when (exception) {
                            is FirebaseAuthInvalidCredentialsException -> "❌ Incorrect credentials. Please check your email and password."
                            is FirebaseAuthUserCollisionException -> "❌ This email is already in use by another account."
                            else -> "❌ ${exception?.message ?: "Unknown error occurred"}"
                        }
                        cont.resume(false to errorMessage, null)
                    }
                }
        }

    fun logout(): Pair<Boolean, String> {
        return try {
            auth.signOut()
            Pair(true, "✅ Logged out successfully.")
        } catch (e: Exception) {
            Pair(false, "❌ Logout failed: ${e.message}")
        }
    }

    suspend fun resetPassword(email: String): Pair<Boolean, String> =
        suspendCancellableCoroutine { cont ->
            if (!isNetworkAvailable(context)) {
                cont.resume(false to "❌ No Internet", null)
                return@suspendCancellableCoroutine
            }

            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        cont.resume(true to "Password reset email sent successfully.", null)
                    } else {
                        val exception = task.exception
                        val errorMessage = when (exception) {
                            is FirebaseAuthInvalidUserException -> "❌ Email not registered or invalid."
                            else -> "❌ ${exception?.message ?: "Unknown error"}"
                        }
                        cont.resume(false to errorMessage, null)
                    }
                }
        }


    suspend fun signInWithGoogle(idToken: String): Pair<Boolean, String> {
        return withContext(Dispatchers.IO) {
            if (!isNetworkAvailable(context)) {
                return@withContext Pair(false, "❌ No Internet")
            }

            val credential = GoogleAuthProvider.getCredential(idToken, null)
            return@withContext try {
                val authResult = auth.signInWithCredential(credential).await()
                val user = authResult.user ?: return@withContext Pair(false, "❌ User not found")

                val dbUser = usersDao.getUser()
                if (dbUser != null) {
                    dbUser.isUpdate = false
                    usersDao.insertUser(dbUser)
                } else {
                    val userData = UserDetailsEntity(
                        uid = user.uid,
                        name = user.displayName ?: "NA",
                        email = user.email ?: "NA",
                        photoUrl = user.photoUrl?.toString() ?: "NA",
                        phoneNumber = user.phoneNumber ?: "NA",
                        emailVerified = user.isEmailVerified.toString(),
                        accountCreated = user.metadata?.creationTimestamp?.toString() ?: "NA",
                        lastSignIn = user.metadata?.lastSignInTimestamp?.toString() ?: "NA"
                    )
                    usersDao.insertUser(userData)
                }

                Pair(true, "✅ Signed in successfully.")

            } catch (e: Exception) {
                usersDao.getUser()?.let {
                    it.isUpdate = true
                    usersDao.insertUser(it)
                }
                Pair(false, "❌ Authentication failed: ${e.message}")
            }
        }
    }
}
