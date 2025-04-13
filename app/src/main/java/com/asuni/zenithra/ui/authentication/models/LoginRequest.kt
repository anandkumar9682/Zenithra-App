package com.asuni.zenithra.ui.authentication.models

import com.google.gson.annotations.SerializedName

/**
 * Author: Anand Kumar
 * Email: anandkumar.docs@gmail.com
 * Date: 2025-04-13
 * Description: Data class representing the login request. Contains email and password as part of the
 * authentication request sent to the API.
 */
data class LoginRequest(
    @SerializedName("email") // The email address of the user
    val email: String,

    @SerializedName("password") // The password associated with the user's account
    val password: String
)
