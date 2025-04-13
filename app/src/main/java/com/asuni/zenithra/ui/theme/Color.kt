/**
 * Author: Anand Kumar
 * Email: aanandkumarbind9682@gmail.com
 * Date: 2025-04-13
 * Description: Color definitions for both light and dark modes in the Zenithra app using Material Design 3.
 *              This includes primary, secondary, error colors, as well as custom colors with varying opacities.
 */

package com.asuni.zenithra.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

// Light Theme Colors
val PrimaryLight = Color(0xFF007DFA)           // Primary color for light theme
val OnPrimaryLight = Color.White                // Text color on primary background
val BackgroundLight = Color(0xFFFFFFFF)        // Background color for light theme
val SurfaceLight = Color(0xFFFFFFFF)           // Surface color for light theme (e.g. cards)
val OnBackgroundLight = Color.Black            // Text color on background
val OnSurfaceLight = Color.Black               // Text color on surfaces

val GrayText = Color(0xFFAAAAAA)               // Gray text for light mode

// Dark Theme Colors
val PrimaryDark = Color(0xFF85F1B0)            // Primary color for dark theme
val OnPrimaryDark = Color.Black                // Text color on primary background in dark theme
val BackgroundDark = Color(0xFF121212)         // Background color for dark theme
val SurfaceDark = Color(0xFF545454)            // Surface color for dark theme
val OnBackgroundDark = Color.White             // Text color on background in dark theme
val OnSurfaceDark = Color.White                // Text color on surfaces in dark theme

val White = Color.White                       // Pure white color
val Black = Color.Black                       // Pure black color

// Text Sizes
val HeadingTextSize = 20.sp                    // Heading text size
val MessageTextSize16 = 16.sp                  // 16sp text size
val MessageTextSize14 = 14.sp                  // 14sp text size
val MessageTextSize12 = 12.sp                  // 12sp text size
