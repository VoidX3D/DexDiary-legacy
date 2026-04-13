package com.dexdiary.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dexdiary.presentation.ui.screens.*

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Editor : Screen("editor?date={date}") {
        fun createRoute(date: String?) = "editor?date=$date"
    }
    object Points : Screen("points")
    object Shop : Screen("shop")
    object Calendar : Screen("calendar")
    object Search : Screen("search")
    object Settings : Screen("settings")
    object About : Screen("about")
    object Setup : Screen("setup")
    object AiUsage : Screen("ai_usage")
    object NotificationSettings : Screen("notification_settings")
    object Analytics : Screen("analytics")
    object Achievements : Screen("achievements")
}

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(
            route = Screen.Editor.route,
            arguments = listOf(navArgument("date") { 
                type = NavType.StringType
                nullable = true
                defaultValue = null
            })
        ) { backStackEntry ->
            val date = backStackEntry.arguments?.getString("date")
            EditorScreen(navController, date)
        }
        composable(Screen.Points.route) {
            PointsDashboardScreen(navController)
        }
        composable(Screen.Shop.route) {
            ShopScreen(navController)
        }
        composable(Screen.Calendar.route) {
            CalendarScreen(navController)
        }
        composable(Screen.Search.route) {
            SearchScreen(navController)
        }
        composable(Screen.Settings.route) {
            SettingsScreen(navController)
        }
        composable(Screen.About.route) {
            AboutScreen(navController)
        }
        composable(Screen.Setup.route) {
            SetupScreen(navController)
        }
        composable(Screen.AiUsage.route) {
            AiUsageReportScreen(navController)
        }
        composable(Screen.NotificationSettings.route) {
            NotificationSettingsScreen(navController)
        }
        composable(Screen.Analytics.route) {
            AnalyticsScreen(navController)
        }
        composable(Screen.Achievements.route) {
            AchievementScreen(navController)
        }
    }
}
