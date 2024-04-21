package banquemisr.challenge05.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import banquemisr.challenge05.domain.model.Movie
import banquemisr.challenge05.domain.useCase.GetMovieDetailsUseCase
import banquemisr.challenge05.domain.useCase.HomeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCases: HomeUseCases,
    private val detailsUseCase: GetMovieDetailsUseCase
) : ViewModel() {
    var nowPlayingMovieList: MutableStateFlow<PagingData<Movie>> =
        MutableStateFlow(value = PagingData.empty())
        private set
    private var resultFlow: Flow<PagingData<Movie>>? = null
    val result = MutableStateFlow<Movie?>(null)

    init {
        loadNowPlayingMovie()
        viewModelScope.launch {
            result.value = detailsUseCase(100)
            Timber.e("Games")
        }

//        getGames()
    }

    //    fun getGames(): Flow<PagingData<Movie>> {
//        val newResult: Flow<PagingData<Movie>> = homeUseCases.getNowPlayingMovieUseCase.invoke()
//            .cachedIn(viewModelScope)
//        resultFlow = newResult
//        return newResult
//    }
    private fun loadNowPlayingMovie() {
        homeUseCases.getNowPlayingMovieUseCase.invoke().onEach {
            nowPlayingMovieList.value = it
        }.cachedIn(viewModelScope)
    }
}