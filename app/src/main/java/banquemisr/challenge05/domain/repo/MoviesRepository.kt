package banquemisr.challenge05.domain.repo

import androidx.paging.PagingData
import banquemisr.challenge05.core.utils.Response
import banquemisr.challenge05.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun getUpComingMovies(): Flow<PagingData<Movie>>
    fun getPopularMovies(): Flow<PagingData<Movie>>
    fun getNowPlayingMovies(): Flow<PagingData<Movie>>
    suspend fun getMovieDetails(movieId: Int): Flow<Response<Movie>>
}