/**
 * Author: Anand Kumar
 * Email: aanandkumarbind9682@gmail.com
 * Date: 2025-04-13
 * Description: Defines the shape styles used across the Zenithra app using Material Design 3 guidelines.
 */

package com.asuni.zenithra.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    small = RoundedCornerShape(4.dp),   // Used for small components like buttons
    medium = RoundedCornerShape(4.dp),  // Used for cards, modals, etc.
    large = RoundedCornerShape(0.dp)    // Used for full-width containers or background elements
)
