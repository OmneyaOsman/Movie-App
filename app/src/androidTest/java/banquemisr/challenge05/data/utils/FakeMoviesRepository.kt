package banquemisr.challenge05.data.utils

import androidx.paging.PagingData
import banquemisr.challenge05.core.remote.Response
import banquemisr.challenge05.domain.model.Movie
import banquemisr.challenge05.domain.repo.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeMoviesRepository : MoviesRepository {
    override fun getUpComingMovies(): Flow<PagingData<Movie>> =
        flowOf(PagingData.from(MoviesFactory.resultList))

    override fun getPopularMovies(): Flow<PagingData<Movie>> =
        flowOf(PagingData.from(MoviesFactory.resultList))

    override fun getNowPlayingMovies(): Flow<PagingData<Movie>> =
        flowOf(PagingData.from(MoviesFactory.resultList))

    override suspend fun getMovieDetails(movieId: Int): Flow<Response<Movie>> {
        return flowOf(Response.Success(MoviesFactory.resultList[0]))
    }
}