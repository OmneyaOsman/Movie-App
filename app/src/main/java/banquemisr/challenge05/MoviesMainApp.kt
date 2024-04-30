package banquemisr.challenge05

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import banquemisr.challenge05.navigation.NavGraph

@Composable
fun MoviesMainApp() {
    val navController = rememberNavController()

    NavGraph(
        navController = navController,
    )
}