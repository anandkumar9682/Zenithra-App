/**
 * Author: Anand Kumar
 * Email: aanandkumarbind9682@gmail.com
 * Date: 2025-04-13
 * Description: Extension functions for NavController to simplify navigation actions
 * like moving to the login or dashboard screens. It handles pop-up behavior as well.
 */

package com.asuni.zenithra.util

import androidx.navigation.NavController
import com.asuni.zenithra.ui.navigations.NavRoute

fun NavController.navigateToSignIn() {
    this.navigate(NavRoute.SignIn.route)
}

fun NavController.navigateToDashboard() {
    this.navigate(NavRoute.Dashboard.route) {
        popUpTo(NavRoute.SignIn.route) { inclusive = true }
        popUpTo(NavRoute.Splash.route) { inclusive = true }
    }
}

fun NavController.navigateToSignUp() {
    this.navigate(NavRoute.SignUp.route)
}