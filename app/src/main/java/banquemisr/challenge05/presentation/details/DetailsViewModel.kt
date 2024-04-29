package banquemisr.challenge05.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import banquemisr.challenge05.core.remote.Response
import banquemisr.challenge05.domain.model.Movie
import banquemisr.challenge05.domain.useCase.GetMovieDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val detailsUseCase: GetMovieDetailsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow<Response<Movie>>(Response.Loading)
    val state: StateFlow<Response<Movie>> = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = Response.Loading,
    )

    init {
        savedStateHandle.get<Int>(MOVIE_ID)?.let {
            loadMovieDetails(movieId = it)
        }
    }

     fun loadMovieDetails(movieId: Int) {
        viewModelScope.launch {
            detailsUseCase(movieId).onEach {
                _state.value = it
            }.launchIn(viewModelScope)
        }
    }

    companion object {
        const val MOVIE_ID = "movieID"
    }
}