package banquemisr.challenge05.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import banquemisr.challenge05.domain.model.Movie
import banquemisr.challenge05.domain.useCase.HomeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCases: HomeUseCases,
//    detailsUseCase: GetMovieDetailsUseCase
) : ViewModel() {

    val nowPlayingMovieList: StateFlow<PagingData<Movie>> =
        homeUseCases.getNowPlayingMovieUseCase.invoke()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), PagingData.empty())

    val nowPlayingMovieListt =
        homeUseCases.getNowPlayingMovieUseCase.invoke().flowOn(Dispatchers.IO)
            .cachedIn(viewModelScope)
//            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), PagingData.empty())
//
//            .flowOn(Dispatchers.IO)
//            .cachedIn(viewModelScope)


}