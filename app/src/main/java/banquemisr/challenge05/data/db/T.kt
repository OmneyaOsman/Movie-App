package banquemisr.challenge05.data.db


import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import banquemisr.challenge05.data.entities.MovieEntity
import banquemisr.challenge05.data.entities.MovieType
import banquemisr.challenge05.data.entities.RemoteKeys
import banquemisr.challenge05.data.remote.api.MoviesService
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class PagedKeyRemoteMediator(
    private val query: MovieType,
    private val database: MoviesDatabase,
    private val service: MoviesService,
) : RemoteMediator<Int, MovieEntity>() {

    private val remoteKeyDao = database.remoteKeysDao()
    private val movieDao = database.moviesDao()

    override suspend fun initialize(): InitializeAction {
        val remoteKey = database.withTransaction {
            remoteKeyDao.getRemoteKeyByMovieID("discover_movie")
        } ?: return InitializeAction.LAUNCH_INITIAL_REFRESH

        val cacheTimeout = TimeUnit.HOURS.convert(1, TimeUnit.MILLISECONDS)

        return if ((System.currentTimeMillis() - remoteKey.createdAt) >= cacheTimeout) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    1
                }

                LoadType.PREPEND -> {
                    return MediatorResult.Success(true)
                }

                LoadType.APPEND -> {
                    val remoteKey = database.withTransaction {
                        remoteKeyDao.getRemoteKeyByMovieID("discover_movie")
                    } ?: return MediatorResult.Success(true)

                    if (remoteKey.nextKey == null) {
                        return MediatorResult.Success(true)
                    }

                    remoteKey.nextKey
                }
            }

            val result = service.fetchPopularMovies(
                page = page,
            )

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    movieDao.deleteMovies()
                }

                val nextPage = if (result.movieResponseList.isEmpty()) {
                    null
                } else {
                    page + 1
                }

//                    val movieEntities = result.movieResponseList.map {
//                        it.toMovieEntity()
//                    }

                remoteKeyDao.insertKey(
                    RemoteKeys(
                        movieID = "discover_movie",
                        nextKey = nextPage,
                        createdAt = System.currentTimeMillis()
                    )
                )
                movieDao.insertAll(result.movieResponseList)
            }

            MediatorResult.Success(
                endOfPaginationReached = result.movieResponseList.isEmpty()
            )
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }
}