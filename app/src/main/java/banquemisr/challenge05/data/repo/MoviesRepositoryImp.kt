package banquemisr.challenge05.data.repo

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import banquemisr.challenge05.core.utils.Response
import banquemisr.challenge05.data.db.MoviesDatabase
import banquemisr.challenge05.data.db.PagingKeyRemoteMediator
import banquemisr.challenge05.data.entities.MovieEntity
import banquemisr.challenge05.data.entities.MovieType
import banquemisr.challenge05.data.mapper.asDomain
import banquemisr.challenge05.data.remote.Constants
import banquemisr.challenge05.data.remote.api.MoviesService
import banquemisr.challenge05.domain.model.Movie
import banquemisr.challenge05.domain.repo.MoviesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapLatest
import retrofit2.HttpException

class MoviesRepositoryImp(val db: MoviesDatabase, val api: MoviesService) : MoviesRepository {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getUpComingMovies(): Flow<PagingData<Movie>> =
        getMovies(MovieType.UPCOMING).mapLatest { pagingData ->
            pagingData.map { model ->
                model.asDomain()
            }
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getPopularMovies(): Flow<PagingData<Movie>> =
        getMovies(MovieType.POPULAR).mapLatest { pagingData ->
            pagingData.map { model ->
                model.asDomain()
            }
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getNowPlayingMovies(): Flow<PagingData<Movie>> {
        Log.e("NetworkModule", "getNowPlayingMovies Repo")
        return getMovies(MovieType.NOW_PLAYING).mapLatest { pagingData ->
            Log.e("NetworkModule", "getNowPlayingMovies Repo")
            pagingData.map { model ->
                Log.e("NetworkModule", "getNowPlayingMovies asDomain")

                model.asDomain()
            }
        }
    }

    override suspend fun getMovieDetails(movieId: Int): Flow<Response<Movie>> = flow {
        return@flow try {
            emit(Response.Loading)
            val result = api.fetchMovieDetails(movieId).asDomain()
            emit(Response.Success(result))
        } catch (e: Exception) {
            emit(Response.Error(e))
        } catch (e: HttpException) {
            emit(Response.Error(e))
        }
    }


    @OptIn(ExperimentalPagingApi::class)
    private fun getMovies(
        query: MovieType,
        pageSize: Int = Constants.PAGE_SIZE
    ): Flow<PagingData<MovieEntity>> {
        Log.e("MoviesRepositoryImp", query.toString())
        return Pager(
            config = PagingConfig(
                pageSize = pageSize, prefetchDistance = 10
            ),
            remoteMediator = PagingKeyRemoteMediator(query, db, api)
        ) {
            db.moviesDao().getMoviesPagingSource(query.toString())
        }.flow
    }
}