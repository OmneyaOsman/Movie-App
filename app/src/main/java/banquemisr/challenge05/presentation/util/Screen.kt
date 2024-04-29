package banquemisr.challenge05.presentation.util

sealed class Screen(val route: String) {
    object HomeScreen : Screen("home_screen")
    object DetailScreen : Screen("detail_screen")
//    object DetailScreen : Screen("detail_screen/{movieId}")
//    {
//        fun createRoute(movieId: Int): String = "detail_screen/$movieId"
//    }
}