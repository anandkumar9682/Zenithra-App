/**
 * Author: Anand Kumar
 * Email: aanandkumarbind9682@gmail.com
 * Date: 2025-04-14
 * Description: This file contains the composable setup for the app's navigation structure,
 *              including the Splash Screen, Authentication Flow, and Dashboard Navigation.
 */

package com.asuni.zenithra.ui.navigations

import android.app.Activity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.rememberNavController
import com.asuni.zenithra.ui.authentication.SignInScreen
import com.asuni.zenithra.ui.authentication.SignUpScreen
import com.asuni.zenithra.ui.dashboard.DashboardScreen
import com.asuni.zenithra.ui.main.SplashScreen
import com.asuni.zenithra.util.navigateToDashboard
import com.asuni.zenithra.util.navigateToSignIn
import com.asuni.zenithra.viewmodels.AuthViewModel
import com.asuni.zenithra.viewmodels.DataViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ComposeApp(activity: Activity) {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = hiltViewModel()
    val dataViewModel: DataViewModel = hiltViewModel()

    AnimatedNavHost(
        navController = navController,
        startDestination = NavRoute.Splash.route,
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { it },
                animationSpec = tween(300)
            )
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { -it },
                animationSpec = tween(300)
            )
        },
        popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { -it },
                animationSpec = tween(300)
            )
        },
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { it },
                animationSpec = tween(300)
            )
        }
    ) {
        addSplashGraph(navController, activity)
        addAuthGraph(navController, activity, authViewModel)
        addDashboardGraph(navController, dataViewModel, authViewModel)
    }
}

// Extension function to navigate to Splash Screen from anywhere in the app
fun NavController.navigateToSplashFirst() {
    this.navigate(NavRoute.Splash.route) {
        popUpTo(0) { inclusive = true }
    }
}

// Splash Graph
@OptIn(ExperimentalAnimationApi::class)
private fun NavGraphBuilder.addSplashGraph(navController: NavController, activity: Activity) {
    composable(NavRoute.Splash.route) {
        SplashScreen { isLoggedIn ->
            if (isLoggedIn) {
                navController.navigateToDashboard()
            } else {
                navController.navigateToSignIn()
            }
        }
    }
}

// Auth Graph
@OptIn(ExperimentalAnimationApi::class)
private fun NavGraphBuilder.addAuthGraph(navController: NavController, activity: Activity, viewModel: AuthViewModel) {
    composable(NavRoute.SignIn.route) {
        SignInScreen(
            activity = activity,
            navController = navController,
            viewModel = viewModel
        )
    }

    composable(NavRoute.SignUp.route) {
        SignUpScreen(
            activity = activity,
            navController = navController,
            viewModel = viewModel
        )
    }
}

// Dashboard Graph
@OptIn(ExperimentalAnimationApi::class)
private fun NavGraphBuilder.addDashboardGraph(navController: NavController, dataViewModel: DataViewModel, authViewModel: AuthViewModel) {
    composable(NavRoute.Dashboard.route) {
        DashboardScreen(
            navController = navController,
            dataViewModel = dataViewModel,
            authViewModel = authViewModel
        )
    }
}
