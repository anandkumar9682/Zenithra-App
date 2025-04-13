/**
 * Author: Anand Kumar
 * Email: aanandkumarbind9682@gmail.com
 * Date: 2025-04-13
 * Description: Defines the navigation routes for the Zenithra app. This helps in managing and navigating
 *              between different screens such as Splash, Authenticate, and Dashboard.
 */

package com.asuni.zenithra.ui.navigations

/**
 * Sealed class to represent the different navigation routes in the app.
 * Each object within the class corresponds to a specific screen or route.
 */
sealed class NavRoute(val route: String) {
    /**
     * Route for the Splash screen.
     */
    object Splash : NavRoute("splash")

    /**
     * Route for the SignIn screen.
     */
    object SignIn : NavRoute("sign_in")

    /**
     * Route for the Dashboard screen.
     */
    object Dashboard : NavRoute("dashboard")

    /**
     * Route for the Signup screen.
     */
    object SignUp : NavRoute("sign_up")
}

/**
 * Object to hold constant routes for navigation. This includes general routes for history, scan, logout,
 * and detailed manga views.
 */
object Route {
    // Route for the History screen
    const val HISTORY = "history"

    // Route for the Scan screen
    const val SCAN = "scan"

    // Route for the Logout action
    const val LOGOUT = "Logout"

    // Route for Manga Detail screen with a dynamic parameter for mangaItemId
    const val MANGA_DETAIL = "mangaDetail/{mangaItemId}"
}
