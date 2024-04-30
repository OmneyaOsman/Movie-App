package banquemisr.challenge05.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import banquemisr.challenge05.presentation.details.DetailScreen
import banquemisr.challenge05.presentation.details.DetailsViewModel.Companion.MOVIE_ID
import banquemisr.challenge05.core.Screen

fun NavGraphBuilder.detailScreen(navController: NavController) {
    composable(
        route = Screen.DetailScreen.route + "/{${MOVIE_ID}}",
        arguments = listOf(navArgument(MOVIE_ID) { type = NavType.IntType })
    ) {
//    composable(route = Screen.DetailScreen.route) {
        DetailScreen(it.arguments?.getInt(MOVIE_ID) ?: -1,
            onNavigateBack = {
                navController.popBackStack()
            }
        )
    }
}