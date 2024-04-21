package banquemisr.challenge05.presentation.details

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import banquemisr.challenge05.domain.useCase.GetMovieDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    val detailsUseCase: GetMovieDetailsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state: MutableState<DetailUIState> = mutableStateOf(DetailUIState())
        private set
    init {
        savedStateHandle.get<String>(MOVIE_ID)?.let {
            loadMovie(movieId = it.toInt())
            println("MOVIE ID $it")
        }
    }

    private fun loadMovie(movieId: Int) {
    }

    companion object {
        const val MOVIE_ID = "movieID"
    }
}