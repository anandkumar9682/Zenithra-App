/**
 * Author: Anand Kumar
 * Email: aanandkumarbind9682@gmail.com
 * Date: 2025-04-14
 * Description: This file contains the Dashboard screen and Bottom Navigation bar implementation using Jetpack Compose for the Zenithra app.
 *              The Dashboard screen displays different screens such as History, Scan, and Manga Detail.
 *              The BottomNavigationBar provides navigation between these screens along with a logout functionality.
 */

package com.asuni.zenithra.ui.dashboard

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.asuni.zenithra.ui.dashboard.face_detection.CameraPermissionWithFaceDetections
import com.asuni.zenithra.ui.dashboard.manga.MagnaScreen
import com.asuni.zenithra.ui.dashboard.manga.MangaDetailScreen
import com.asuni.zenithra.ui.navigations.Route
import com.asuni.zenithra.viewmodels.AuthViewModel
import com.asuni.zenithra.viewmodels.DataViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DashboardScreen(navController: NavController, dataViewModel: DataViewModel, authViewModel: AuthViewModel) {
    val dashboardNavController = rememberNavController()
    val currentRoute = dashboardNavController.currentBackStackEntryAsState().value?.destination?.route
    val showBottomBar = currentRoute in listOf(Route.HISTORY, Route.SCAN)

    val isDarkTheme = isSystemInDarkTheme()
    val backgroundColor = if (isDarkTheme) Color(0xFF121212) else MaterialTheme.colorScheme.primary

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavigationBar(viewModel = authViewModel, navController = navController, dashboardNavController = dashboardNavController)
            }
        },
        modifier = Modifier.background(color = backgroundColor)
    ) { padding ->
        AnimatedNavHost(
            navController = dashboardNavController,
            startDestination = Route.HISTORY,
            modifier = Modifier.padding(padding),
            enterTransition = {
                // Slide-in from left to right
                slideInHorizontally(initialOffsetX = { -1000 }) + fadeIn()
            },
            exitTransition = {
                // Slide-out from right to left
                slideOutHorizontally(targetOffsetX = { 1000 }) + fadeOut()
            },
            popEnterTransition = {
                // Slide-in from right to left
                slideInHorizontally(initialOffsetX = { 1000 }) + fadeIn()
            },
            popExitTransition = {
                // Slide-out from left to right
                slideOutHorizontally(targetOffsetX = { -1000 }) + fadeOut()
            }
        ) {
            composable(Route.HISTORY) {
                MagnaScreen(viewModel = dataViewModel, navController = dashboardNavController)
            }
            composable(Route.SCAN) {
                CameraPermissionWithFaceDetections()
            }

            composable(
                route = Route.MANGA_DETAIL,
                arguments = listOf(navArgument("mangaItemId") { type = NavType.StringType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getString("mangaItemId")
                val item = dataViewModel.getMangaItemById(id.orEmpty())
                item?.let {
                    MangaDetailScreen(mangaItem = it, onBackClick = { dashboardNavController.popBackStack() })
                }
            }
        }
    }
}
