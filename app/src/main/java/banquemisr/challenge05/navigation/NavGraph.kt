package banquemisr.challenge05.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import banquemisr.challenge05.presentation.util.Screen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route
    ) {
        homeScreen(navController = navController)
        detailScreen(navController = navController)
    }
}