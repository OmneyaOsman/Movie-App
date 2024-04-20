package banquemisr.challenge05.domain.repo

import androidx.paging.PagingData
import banquemisr.challenge05.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    suspend fun getUpComingMovies(): Flow<PagingData<Movie>>
    suspend fun getPopularMovies(): Flow<PagingData<Movie>>
    suspend fun getNowPlayingMovies(): Flow<PagingData<Movie>>
    suspend fun getMovieDetails(movieId: Int): Movie
}