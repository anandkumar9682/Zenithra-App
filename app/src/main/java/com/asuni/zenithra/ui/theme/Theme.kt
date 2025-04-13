/**
 * Author: Anand Kumar
 * Email: aanandkumarbind9682@gmail.com
 * Date: 2025-04-13
 * Description: Sets up the color scheme and theming (light/dark) using Material Design 3 for the Zenithra app.
 */

package com.asuni.zenithra.ui.theme

import androidx.activity.ComponentActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext

// Light Theme Color Scheme
private val LightColors = lightColorScheme(
    primary = PrimaryLight,
    onPrimary = OnPrimaryLight,
    background = BackgroundLight,
    onBackground = OnBackgroundLight,
    surface = SurfaceLight,
    onSurface = OnSurfaceLight
)

// Dark Theme Color Scheme
private val DarkColors = darkColorScheme(
    primary = PrimaryDark,
    onPrimary = OnPrimaryDark,
    background = BackgroundDark,
    onBackground = OnBackgroundDark,
    surface = SurfaceDark,
    onSurface = OnSurfaceDark
)

@Composable
fun ZenithraTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColors else LightColors

    // Change status bar color according to theme
    (LocalContext.current as? ComponentActivity)?.window?.statusBarColor = if (darkTheme) {
        Color(0xFF121212).toArgb() // Dark mode
    } else {
        Color(0xFF007DFA).toArgb() // Light mode
    }

    // Apply Material 3 theme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
