package banquemisr.challenge05.domain.useCase

import android.util.Log
import androidx.paging.PagingData
import banquemisr.challenge05.domain.model.Movie
import banquemisr.challenge05.domain.repo.MoviesRepository
import kotlinx.coroutines.flow.Flow

class GetNowPlayingMoviesUseCase(val repository: MoviesRepository) {
    operator fun invoke() : Flow<PagingData<Movie>> {
        Log.e("NetworkModule" , "GetNowPlayingMoviesUseCase")
        return repository.getNowPlayingMovies()
    }
}