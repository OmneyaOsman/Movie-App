package banquemisr.challenge05.presentation.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun DetailScreen(movieId: Int, onNavigateBack: () -> Unit) {
    val movieDetailsViewModel: DetailsViewModel = hiltViewModel()
    val state by movieDetailsViewModel.state.collectAsStateWithLifecycle()

    MovieDetailsContent(
        state = state,
        onNavigateBack = onNavigateBack, onRetry = {
            movieDetailsViewModel.loadMovieDetails(movieId)
        }
    )

}

