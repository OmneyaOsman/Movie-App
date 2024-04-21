package banquemisr.challenge05.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import banquemisr.challenge05.presentation.details.DetailScreen
import banquemisr.challenge05.presentation.details.DetailsViewModel.Companion.MOVIE_ID
import banquemisr.challenge05.presentation.util.Screen

fun NavGraphBuilder.detailScreen(navController: NavController) {
//    composable(route = Screen.DetailScreen.route + "/{${MOVIE_ID}}") {
    composable(route = Screen.DetailScreen.route) {
        DetailScreen(
            onNavigateHome = {
                navController.navigate(Screen.HomeScreen.route)
            }
        )
    }
}