package com.asuni.zenithra.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * Author: Anand Kumar
 * Email: anandkumar.docs@gmail.com
 * Date: 2025-04-13
 * Description: Entity class for UserDetails, used in Room database for storing user information.
 */
@Entity(tableName = "user_details")
data class UserDetailsEntity(
    @PrimaryKey(autoGenerate = true)
    var userId: Long = 0, // Automatically generated unique user ID

    val uid: String = "NA", // User ID from Firebase or other source
    val name: String = "NA", // User's full name
    val email: String = "NA", // User's email address
    val photoUrl: String = "NA", // URL to user's profile photo
    val phoneNumber: String = "NA", // User's phone number
    val emailVerified: String = "NA", // Email verification status (e.g., "true" or "false")
    val accountCreated: String = "NA", // Account creation timestamp as a string
    val lastSignIn: String = "NA", // Timestamp of the user's last sign-in
    var isUpdate: Boolean = false // Flag to track if the user details have been updated
) : Serializable
