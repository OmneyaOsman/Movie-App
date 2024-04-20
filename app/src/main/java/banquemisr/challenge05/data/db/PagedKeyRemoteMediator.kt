package banquemisr.challenge05.data.db

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import banquemisr.challenge05.data.model.MovieModel
import banquemisr.challenge05.data.model.MovieType
import banquemisr.challenge05.data.model.RemoteKeys
import banquemisr.challenge05.data.remote.Constants.MOVIES_STARTING_PAGE_INDEX
import banquemisr.challenge05.data.remote.api.MoviesService
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class PagedKeyRemoteMediator(
    private val query: MovieType,
    private val db: MoviesDatabase,
    private val moviesService: MoviesService
) : RemoteMediator<Int, MovieModel>() {

    private val moviesDao = db.moviesDao()
    private val remoteKeysDao = db.remoteKeysDao()

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)
        return if (System.currentTimeMillis() - (db.remoteKeysDao().getCreationTime()
                ?: 0) < cacheTimeout
        ) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

//    override suspend fun initialize(): InitializeAction {
//        // Require that remote REFRESH is launched on initial load and succeeds before launching
//        // remote PREPEND / APPEND.
//        return InitializeAction.LAUNCH_INITIAL_REFRESH
//    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieModel>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: MOVIES_STARTING_PAGE_INDEX
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                if (prevKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
                prevKey
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                if (nextKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
                nextKey
            }
        }

        try {
            // Suspending network load via Retrofit. This doesn't need to be
            // wrapped in a withContext(Dispatcher.IO) { ... } block since
            // Retrofit's Coroutine CallAdapter dispatches on a worker
            // thread.
            val response = when (query) {
                MovieType.POPULAR -> moviesService.fetchPopularMovies(page = page)
                MovieType.NOW_PLAYING -> moviesService.fetchNowPlayingMovies(page = page)
                MovieType.UPCOMING -> moviesService.fetchUpcomingMovies(page = page)
            }
            val movies: List<MovieModel> = response.movieResponseList ?: emptyList()
            val endOfPaginationReached = response.movieResponseList.isEmpty()
            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    remoteKeysDao.clearRemoteKeys()
                    moviesDao.deleteMovies()
                }
                val prevKey = if (page > 1) page - 1 else null
                val nextKey = if (endOfPaginationReached) null else page + 1
                val remoteKeys = movies.map {
                    RemoteKeys(
                        movieID = it.id,
                        prevKey = prevKey,
                        currentPage = page!!,
                        nextKey = nextKey
                    )
                }
                // Insert new movies into database, which invalidates the
                // current PagingData, allowing Paging to present the updates
                // in the DB.
                db.remoteKeysDao().insertAll(remoteKeys)
                db.moviesDao().insertAll(movies)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }


    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, MovieModel>): RemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { movie ->
                // Get the remote keys of the last item retrieved
                db.remoteKeysDao().getRemoteKeyByMovieID(movie.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, MovieModel>): RemoteKeys? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { movie ->
                // Get the remote keys of the first items retrieved
                db.remoteKeysDao().getRemoteKeyByMovieID(movie.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, MovieModel>
    ): RemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { movieId ->
                db.remoteKeysDao().getRemoteKeyByMovieID(movieId)
            }
        }
    }


}