/**
 * Author: Anand Kumar
 * Email: aanandkumarbind9682@gmail.com
 * Date: 2025-04-13
 * Description: A composable function for displaying a logout confirmation dialog.
 *              The dialog shows an icon, a message, and two buttons ("Yes" to confirm logout, "No" to cancel).
 */

package com.asuni.zenithra.ui.authentication

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.asuni.zenithra.R
import com.asuni.zenithra.ui.theme.Black
import com.asuni.zenithra.ui.theme.White

/**
 * A composable function that displays a dialog to confirm user logout.
 *
 * @param onDismissRequest Function to handle dismiss request of the dialog.
 * @param onConfirmDelete Function to handle the confirmation action (logout).
 * @param onCancel Function to handle cancel action (dismiss the dialog).
 */
@Composable
fun DialogForLogoutConfirmation(
    onDismissRequest: () -> Unit,
    onConfirmDelete: () -> Unit,
    onCancel: () -> Unit
) {

    // Check if the system is in dark theme mode
    val isDarkTheme = isSystemInDarkTheme()

    // Set colors based on theme
    val backgroundColor = if (isDarkTheme) Color(0xFF121212) else MaterialTheme.colorScheme.primary
    val surfaceColor = if (isDarkTheme) Color(0xFF2C2C2C) else Color.White
    val textColor = if (isDarkTheme) Color.White else Color.Black

    Dialog(
        onDismissRequest = onDismissRequest, // Trigger dismiss request
        properties = DialogProperties(dismissOnClickOutside = false) // Prevent dismissing by clicking outside
    ) {
        Surface(
            modifier = Modifier
                .wrapContentSize()
                .padding(16.dp), // Add padding inside the dialog
            shape = RoundedCornerShape(10.dp), // Rounded corners for the dialog
            color = surfaceColor // Surface color based on the theme
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .background(color = surfaceColor) // Column background color based on the theme
            ) {
                // Icon for visual indication (e.g., warning or confirmation icon)
                Icon(
                    painter = painterResource(id = R.drawable.icon_info_red), // Custom icon resource
                    contentDescription = null,
                    tint = Color.Unspecified, // Do not apply any tint to the icon
                    modifier = Modifier.size(40.dp) // Icon size
                )

                Spacer(modifier = Modifier.height(8.dp)) // Spacer to create some space between elements

                // Title Text
                Text(
                    text = "Logout",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold, // Bold text
                        color = textColor // Text color based on the theme
                    )
                )

                Spacer(modifier = Modifier.height(8.dp)) // Spacer for spacing

                // Message Text
                Text(
                    text = "Are you sure you want to logout?",
                    style = MaterialTheme.typography.bodyMedium.copy(color = textColor), // Body text style with theme-based color
                )

                Spacer(modifier = Modifier.height(20.dp)) // Spacer for spacing between buttons and message

                // Row to arrange the buttons horizontally
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp), // Space between buttons
                    modifier = Modifier.fillMaxWidth() // Fill the width of the dialog
                ) {
                    // Cancel Button (No action to logout)
                    OutlinedButton(
                        onClick = onCancel, // Trigger onCancel lambda
                        modifier = Modifier.weight(1f), // Make buttons equal width
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary) // Border with theme color
                    ) {
                        Text(text = "No", color = if (isDarkTheme) White else Black) // Text color based on the theme
                    }

                    // Confirm (Yes) Button for logout confirmation
                    Button(
                        onClick = onConfirmDelete, // Trigger onConfirmDelete lambda
                        modifier = Modifier.weight(1f), // Equal width for both buttons
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary, // Button background color from theme
                            contentColor = textColor // Button text color
                        ),
                        shape = RoundedCornerShape(10.dp) // Rounded corners for the button
                    ) {
                        Text(text = "Yes", color = if (isDarkTheme) Black else White) // Text color based on the theme
                    }
                }
            }
        }
    }
}
