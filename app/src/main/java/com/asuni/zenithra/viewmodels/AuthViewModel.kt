/**
 * Author: Anand Kumar
 * Email: aanandkumarbind9682@gmail.com
 * Date: 2025-04-13
 * Description: ViewModel for handling user authentication, including login, logout, password reset,
 * and Google authentication via Firebase. It provides methods for performing these actions and
 * maintains the corresponding UI states.
 */

package com.asuni.zenithra.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asuni.zenithra.network.model.ApiResponse
import com.asuni.zenithra.repository.AuthRepository
import com.asuni.zenithra.ui.authentication.models.LoginRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _signUpStatus = MutableStateFlow<ApiResponse<Pair<Boolean, String>>>(ApiResponse.Clear())
    val signUpStatus: StateFlow<ApiResponse<Pair<Boolean, String>>> = _signUpStatus

    fun signUp(name: String, mobile: String, email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.signUp(name, mobile, email, password).collect {
                _signUpStatus.value = it
            }
        }
    }

    fun clearSignUpRequest() {
        _signUpStatus.value = ApiResponse.Clear()
    }

    private val _loginStatus = MutableStateFlow<ApiResponse<Pair<Boolean, String>>>(ApiResponse.Clear())
    val loginStatus: StateFlow<ApiResponse<Pair<Boolean, String>>> = _loginStatus

    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            // Construct the LoginRequest
            val request = LoginRequest(
                email = email,
                password = password
            )
            authRepository.login(request).collect { apiResponse ->
                _loginStatus.value = apiResponse // Update the UI state based on the response
            }
        }
    }

    var countryCode by mutableStateOf("+91")
    var email by mutableStateOf("")
    var mobileNumber by mutableStateOf("")

    private val _signout = MutableStateFlow<ApiResponse<Boolean>>(ApiResponse.Clear())
    val signout: StateFlow<ApiResponse<Boolean>> = _signout
    fun signout() {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.logout().collect { apiResponse ->
                _signout.value = ApiResponse.Success(apiResponse.data?.first) // Update state with the response
            }
        }
    }

    fun clearLogout() {
        _signout.value = ApiResponse.Clear()
    }

    private val _resetPasswordStatus = MutableStateFlow<ApiResponse<Pair<Boolean, String>>>(ApiResponse.Clear())
    val resetPasswordStatus: StateFlow<ApiResponse<Pair<Boolean, String>>> = _resetPasswordStatus
    // New reset password method
    fun resetPassword(email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.resetPassword(email).collect { apiResponse ->
                _resetPasswordStatus.value = apiResponse // Update UI state based on the reset response
            }
        }
    }

    fun clearLoginRequest() {
        _loginStatus.value = ApiResponse.Clear()
        _resetPasswordStatus.value = ApiResponse.Clear()
    }

    private val _firebaseAuthWithGoogleStatus = MutableStateFlow<ApiResponse<Pair<Boolean, String>>>(ApiResponse.Clear())
    val firebaseAuthWithGoogleStatus: StateFlow<ApiResponse<Pair<Boolean, String>>> = _firebaseAuthWithGoogleStatus
    fun firebaseAuthWithGoogle(idToken: String) {
        viewModelScope.launch(Dispatchers.IO) {
            // Construct OTP verification request
            authRepository.signInWithGoogle(idToken).collect { apiResponse ->
                _firebaseAuthWithGoogleStatus.value = apiResponse // Update state with the response
            }
        }
    }

    fun isLoggedIn(): Boolean = authRepository.isLoggedIn()
}
