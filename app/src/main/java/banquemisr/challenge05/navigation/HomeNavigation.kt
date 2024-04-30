package banquemisr.challenge05.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import banquemisr.challenge05.presentation.home.HomeScreen
import banquemisr.challenge05.core.Screen

fun NavGraphBuilder.homeScreen(navController: NavController) {
    composable(Screen.HomeScreen.route) {
        HomeScreen(
            onNavigateDetailScreen = { id ->
                navController.navigate(Screen.DetailScreen.route + "/${id}")
            }
        )
    }
}