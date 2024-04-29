package banquemisr.challenge05.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import banquemisr.challenge05.domain.useCase.HomeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCases: HomeUseCases,
) : ViewModel() {

    val nowPlayingMovies =
        homeUseCases.getNowPlayingMovieUseCase.invoke()
            .flowOn(Dispatchers.IO)
            .cachedIn(viewModelScope)

    val upComingMovies =
        homeUseCases.getUpComingMoviesUseCase.invoke()
            .flowOn(Dispatchers.IO)
            .cachedIn(viewModelScope)

    val popularMovies =
        homeUseCases.getPopularMovieUseCase.invoke()
            .flowOn(Dispatchers.IO)
            .cachedIn(viewModelScope)
}