package banquemisr.challenge05.data.db

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import banquemisr.challenge05.core.remote.Constants.MOVIES_STARTING_PAGE_INDEX
import banquemisr.challenge05.core.remote.MovieType
import banquemisr.challenge05.core.remote.value
import banquemisr.challenge05.data.entities.MovieEntity
import banquemisr.challenge05.data.entities.RemoteKeys
import banquemisr.challenge05.data.remote.api.MoviesService
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class PagingKeyRemoteMediator(
    private val query: MovieType,
    private val db: MoviesDatabase,
    private val moviesService: MoviesService
) : RemoteMediator<Int, MovieEntity>() {

    private val moviesDao = db.moviesDao()
    private val remoteKeysDao = db.remoteKeysDao()
    private val movieType = query.value()

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)
        return if (System.currentTimeMillis() - (db.remoteKeysDao().getCreationTime()
                ?: 0) < cacheTimeout
        ) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            return InitializeAction.LAUNCH_INITIAL_REFRESH
        }


    }
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        return try {
            Log.e("Mediator", "Inserting items reversed ------->  Load")

            val page = when (loadType) {
                LoadType.REFRESH -> {
                    Log.e("Mediator", "Inserting items reversed ------->  LoadType.REFRESH")
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    Log.e(
                        "Mediator",
                        "Inserting items reversed -------> LoadType.REFRESH remoteKeys= ${
                            remoteKeys?.nextKey?.minus(1) ?: MOVIES_STARTING_PAGE_INDEX
                        }}"
                    )

                    remoteKeys?.nextKey?.minus(1) ?: MOVIES_STARTING_PAGE_INDEX
//                1

                }

                LoadType.PREPEND -> {
                    Log.e("Mediator", "Inserting items reversed ------->  LoadType.PREPEND")
//                return MediatorResult.Success(endOfPaginationReached = true)
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevKey = remoteKeys?.prevKey
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    Log.e(
                        "Mediator",
                        "Inserting items reversed -------> LoadType.PREPEND prevKey= $prevKey}"
                    )

                    prevKey
                }

                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextKey = remoteKeys?.nextKey
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    Log.e(
                        "Mediator",
                        "Inserting items reversed -------> LoadType.APPEND nextKey= $nextKey}"
                    )

                    nextKey
                }
            }


            // Suspending network load via Retrofit. This doesn't need to be
            // wrapped in a withContext(Dispatcher.IO) { ... } block since
            // Retrofit's Coroutine CallAdapter dispatches on a worker
            // thread.
            Log.e("Mediator", "Inserting items reversed ------->  getQuery")
            val response = when (query) {
                MovieType.Popular -> moviesService.fetchPopularMovies(page = page)
                MovieType.NowPlaying -> moviesService.fetchNowPlayingMovies(page = page)
                MovieType.UpComing -> moviesService.fetchUpcomingMovies(page = page)
            }
            Log.e("Mediator", "Inserting items reversed ------->  response for query $query")
            val movies: List<MovieEntity> = response.movieResponseList ?: emptyList()
            Log.e("Mediator", "Inserting items reversed ------->  response= $movies")

            val endOfPaginationReached = movies.isEmpty()

            Log.e(
                "Mediator",
                "Inserting items reversed ------->  endOfPaginationReached= $endOfPaginationReached"
            )

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    remoteKeysDao.deleteByQuery(movieType)
                    moviesDao.deleteByQuery(movieType)
                }
                val prevKey = if (page > 1) page - 1 else null
                val nextKey = if (endOfPaginationReached) null else page + 1
                val remoteKeys = movies.map {
                    RemoteKeys(
                        movieID = it.id,
                        prevKey = prevKey,
                        currentPage = page,
                        nextKey = nextKey,
                        label = movieType
                    )
                }


                // Insert new movies into database, which invalidates the
                // current PagingData, allowing Paging to present the updates
                // in the DB.
                db.remoteKeysDao().insertAll(remoteKeys)
                db.moviesDao().insertAll(movies)
                db.moviesDao().insertAll(movies.onEachIndexed { _, movie -> movie.page = page
                    movie.movieType = movieType })

                Log.e(
                    "Mediator",
                    "Inserting items reversed ------->  remoteKeys= ${
                        db.remoteKeysDao().getCreationTime()
                    }"
                )
                Log.e(
                    "Mediator",
                    "Inserting items reversed ------->  remoteKeys= ${db.moviesDao().getMovies()}"
                )

            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }


    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, MovieEntity>): RemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { movie ->
                // Get the remote keys of the last item retrieved
                db.remoteKeysDao().getRemoteKeyByMovieID(movie.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, MovieEntity>): RemoteKeys? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { movie ->
                // Get the remote keys of the first items retrieved
                db.remoteKeysDao().getRemoteKeyByMovieID(movie.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, MovieEntity>
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