package com.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.app.presentation.screens.DetailScreen
import com.app.presentation.screens.HomeScreen
import com.app.presentation.viewmodels.MainViewModel

@Composable
fun NavGraph(
    modifier: Modifier,
    mainViewModel: MainViewModel,
    navController: NavHostController,
    startDestination: String,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                navigateToDetail = { itemId ->
                    navController.navigate(Screen.Detail.createRoute(itemId))
                },
                mainViewModel = mainViewModel,
            )
        }
        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument(ART_ID) { type = NavType.StringType }),
        ) { backStackEntry ->
            DetailScreen(
                itemId = backStackEntry.arguments?.getString(ART_ID),
                mainViewModel = mainViewModel,
            )
        }
    }
}
