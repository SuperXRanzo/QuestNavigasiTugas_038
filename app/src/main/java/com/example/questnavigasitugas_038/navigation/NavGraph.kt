package com.example.questnavigasitugas_038.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.questnavigasitugas_038.screens.FormScreen
import com.example.questnavigasitugas_038.screens.ListScreen
import com.example.questnavigasitugas_038.screens.SplashScreen
import com.example.questnavigasitugas_038.viewmodel.MainViewModel

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object List : Screen("list")
    object Form : Screen("form")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel: MainViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(Screen.List.route) {
            ListScreen(navController = navController, viewModel = viewModel)
        }
        composable(Screen.Form.route) {
            FormScreen(navController = navController, viewModel = viewModel)
        }
    }
}