package com.adam0006.cinelist.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.adam0006.cinelist.database.MainViewModel
import com.adam0006.cinelist.screen.MainScreen
import com.adam0006.cinelist.screen.DetailScreen
import com.adam0006.cinelist.screen.SettingsScreen
import com.adam0006.cinelist.screen.EditScreen
import com.adam0006.cinelist.screen.AddScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    viewModel: MainViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            MainScreen(navController = navController, viewModel = viewModel)
        }

        composable(route = Screen.Add.route) {
            AddScreen(navController = navController, viewModel = viewModel)
        }

        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument(KEY_ID_FILM) { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt(KEY_ID_FILM) ?: -1
            DetailScreen(navController = navController, viewModel = viewModel, idFilm = id)
        }

        composable(route = Screen.Settings.route) {
            SettingsScreen(navController = navController)
        }

        composable(
            route = Screen.Edit.route,
            arguments = listOf(navArgument(KEY_ID_FILM) { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt(KEY_ID_FILM) ?: -1
            EditScreen(navController = navController, viewModel = viewModel, idFilm = id)
        }
    }
}