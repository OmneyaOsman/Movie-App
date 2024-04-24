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

@OptIn(ExperimentalPagingApi::class)
class PagedKeyRemoteMediator(
    private val db: MoviesDatabase,
    private val gamesAPI: MoviesService,
    private val query: MovieType
) : RemoteMediator<Int, MovieEntity>() {
    private val unsplashImageDao = db.moviesDao()
    private val unsplashRemoteKeysDao = db.remoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextKey?.minus(1) ?: 1
                }

                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevKey
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }

                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextKey
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response = gamesAPI.fetchNowPlayingMovies(page = currentPage)
            val endOfPaginationReached = response.movieResponseList.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    unsplashImageDao.deleteMovies()
                    unsplashRemoteKeysDao.clearRemoteKeys()
                }
                val keys = response.movieResponseList.map { movieEntity ->
                    RemoteKeys(
                        movieID = movieEntity.id,
                        prevKey = prevPage,
                        nextKey = nextPage
                    )
                }
                unsplashRemoteKeysDao.insertAll(keys)
                unsplashImageDao.insertAll(response.movieResponseList)
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, MovieEntity>
    ): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                unsplashRemoteKeysDao.getRemoteKeyByMovieID(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, MovieEntity>
    ): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { MovieEntity ->
                unsplashRemoteKeysDao.getRemoteKeyByMovieID(id = MovieEntity.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, MovieEntity>
    ): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { MovieEntity ->
                unsplashRemoteKeysDao.getRemoteKeyByMovieID(id = MovieEntity.id)
            }
    }

}