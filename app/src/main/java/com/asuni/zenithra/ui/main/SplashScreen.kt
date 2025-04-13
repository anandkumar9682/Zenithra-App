/**
 * Author: Anand Kumar
 * Email: aanandkumarbind9682@gmail.com
 * Date: 2025-04-13
 * Description: Splash screen for the Zenithra app that shows the logo with fade-in and rotation animations.
 *              After 3 seconds, it navigates based on the user's login status.
 */

package com.asuni.zenithra.ui.main

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.asuni.zenithra.R
import com.asuni.zenithra.viewmodels.AuthViewModel
import kotlinx.coroutines.delay

/**
 * Composable function that displays a splash screen with a rotating and fading logo.
 * After 3 seconds, it navigates to a different screen based on the user's login status.
 *
 * @param navigate A lambda function used to navigate to the next screen based on the login status.
 */
@Composable
fun SplashScreen(navigate: (Boolean) -> Unit) {

    // ViewModel for authentication-related data
    val viewModel: AuthViewModel = hiltViewModel()

    // Rotation animation for the logo (360 degrees over 2 seconds)
    val rotation by animateFloatAsState(
        targetValue = 360f,
        animationSpec = tween(durationMillis = 2000) // Rotate over 2 seconds
    )

    // Determine whether the system is in dark theme
    val isDarkTheme = isSystemInDarkTheme()

    // Set background color based on dark or light theme
    val backgroundColor = if (isDarkTheme) Color(0xFF121212) else MaterialTheme.colorScheme.primary

    // Fade-in animation for the logo (opacity from 0 to 1 over 2 seconds)
    val alpha by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(durationMillis = 2000) // Fade-in over 2 seconds
    )

    // Surface to hold the splash content
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = backgroundColor // Set background color based on theme
    ) {
        Column(
            modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center) // Center the logo
        ) {
            // Display the logo with the rotating and fading animation
            Image(
                painter = painterResource(id = R.drawable.zenithra_logo),
                contentDescription = "Splash Image",
                modifier = Modifier
                    .size(150.dp) // Size of the logo
                    .graphicsLayer(
                        rotationZ = rotation, // Apply rotation
                        alpha = alpha // Apply fade-in effect
                    )
            )
        }
    }

    // Launch navigation after a delay of 3 seconds
    LaunchedEffect(Unit) {
        delay(3000) // Wait for 3 seconds
        // Navigate based on the user's login status
        navigate(viewModel.isLoggedIn())
    }
}
