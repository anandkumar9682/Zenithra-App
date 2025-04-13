package com.asuni.zenithra.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.PersonSearch
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.asuni.zenithra.network.model.ApiResponse
import com.asuni.zenithra.ui.authentication.DialogForLogoutConfirmation
import com.asuni.zenithra.ui.navigations.Route
import com.asuni.zenithra.ui.navigations.navigateToSplashFirst
import com.asuni.zenithra.ui.theme.GrayText
import com.asuni.zenithra.ui.theme.White
import com.asuni.zenithra.util.showToast
import com.asuni.zenithra.viewmodels.AuthViewModel


@Composable
fun BottomNavigationBar(navController: NavController, viewModel: AuthViewModel, dashboardNavController: NavController) {
    val context = LocalContext.current
    val isDarkTheme = isSystemInDarkTheme()
    val surfaceColor = if (isDarkTheme) Color(0xFF2C2C2C) else Color.White
    val backgroundColor = if (isDarkTheme) Color(0xFF121212) else MaterialTheme.colorScheme.primary

    val items = listOf(
        NavItem(route = Route.HISTORY, icon = Icons.Default.MenuBook, title = "History"),
        NavItem(route = Route.SCAN, icon = Icons.Default.PersonSearch, title = "Face"),
        NavItem(route = Route.LOGOUT, icon = Icons.Default.ExitToApp, title = "Signout")
    )

    val logoutStatus = viewModel.signout.collectAsState().value

    LaunchedEffect(logoutStatus) {
        when (logoutStatus) {
            is ApiResponse.Success -> {
                context.showToast("Signout successfully.")
                navController.navigateToSplashFirst()
                viewModel.clearLogout()
            }
            is ApiResponse.Error -> {
                context.showToast(logoutStatus.errorMessage ?: "Something went wrong")
            }

            is ApiResponse.Clear -> {

            }
            is ApiResponse.Loading -> {

            }
        }
    }

    val logoutConfirmationDialog = remember { mutableStateOf(false) }
    if (logoutConfirmationDialog.value) {
        DialogForLogoutConfirmation(
            onDismissRequest = { logoutConfirmationDialog.value = false },
            onConfirmDelete = {
                viewModel.signout()
                logoutConfirmationDialog.value = false
            },
            onCancel = { logoutConfirmationDialog.value = false }
        )
    }

    Box(Modifier.fillMaxWidth()) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(10.dp),
            colors = CardDefaults.cardColors(containerColor = surfaceColor)
        ) {
            NavigationBar(
                containerColor = Color.Transparent,
                modifier = Modifier.background(surfaceColor)
            ) {
                val currentRoute = dashboardNavController.currentBackStackEntryAsState().value?.destination?.route
                items.forEach { item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.title) },
                        label = { Text(item.title) },
                        selected = currentRoute == item.route,
                        onClick = {
                            if (currentRoute != item.route && item.route != Route.LOGOUT) {
                                dashboardNavController.navigate(item.route) {
                                    popUpTo(dashboardNavController.graph.startDestinationId) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            } else {
                                logoutConfirmationDialog.value = true
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = backgroundColor,
                            unselectedIconColor = if (isDarkTheme) MaterialTheme.colorScheme.surface else GrayText,
                            unselectedTextColor = if (isDarkTheme) MaterialTheme.colorScheme.surface else GrayText,
                            selectedIconColor = if (isDarkTheme) MaterialTheme.colorScheme.primary else White,
                            selectedTextColor = MaterialTheme.colorScheme.primary
                        ),
                        alwaysShowLabel = true
                    )
                }
            }
        }
    }
}

data class NavItem(
    val route: String,
    val icon: ImageVector,
    val title: String
)
