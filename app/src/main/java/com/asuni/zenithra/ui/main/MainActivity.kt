/**
 * Author: Anand Kumar
 * Email: aanandkumarbind9682@gmail.com
 * Date: 2025-04-13
 * Description: Main entry point of the app. The MainActivity sets up the content view using Compose UI.
 *              It also applies the custom theme and initializes the app's Compose UI structure.
 */

package com.asuni.zenithra.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.asuni.zenithra.ui.navigations.ComposeApp
import com.asuni.zenithra.ui.theme.ZenithraTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * MainActivity is the entry point of the application and inherits from ComponentActivity.
 * This activity initializes the UI for the application.
 */
@AndroidEntryPoint // Annotation used for Hilt dependency injection.
class MainActivity : ComponentActivity() {

    /**
     * onCreate is called when the activity is created. Here we set the content view using Compose.
     * The UI is wrapped with a custom theme (ZenithraTheme) to ensure consistent styling throughout the app.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set the Compose UI content within this activity
        setContent {
            // Apply the custom theme to the app
            ZenithraTheme {
                // Initialize the ComposeApp function, passing the activity context
                ComposeApp(activity = this)
            }
        }
    }
}
