package banquemisr.challenge05.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import banquemisr.challenge05.domain.model.Movie
import banquemisr.challenge05.domain.useCase.GetMovieDetailsUseCase
import banquemisr.challenge05.domain.useCase.HomeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCases: HomeUseCases,
    detailsUseCase: GetMovieDetailsUseCase
) : ViewModel() {

    val nowPlayingMovieList : StateFlow<PagingData<Movie>> = homeUseCases.getNowPlayingMovieUseCase.invoke()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), PagingData.empty())
}