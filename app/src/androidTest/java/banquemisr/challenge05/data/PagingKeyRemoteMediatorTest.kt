package banquemisr.challenge05.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.RemoteMediator
import androidx.test.core.app.ApplicationProvider
import banquemisr.challenge05.DispatcherCoroutinesRule
import banquemisr.challenge05.core.remote.MovieType
import banquemisr.challenge05.data.db.MoviesDatabase
import banquemisr.challenge05.data.db.PagingKeyRemoteMediator
import banquemisr.challenge05.data.db.dao.MoviesDao
import banquemisr.challenge05.data.db.dao.RemoteKeysDao
import banquemisr.challenge05.data.utils.FakeMoviesService
import banquemisr.challenge05.data.utils.MoviesFactory
import banquemisr.challenge05.data.utils.MoviesFactory.mockedPagingState
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalPagingApi::class)
class PagingKeyRemoteMediatorTest {

    @get:Rule
    val coroutinesRule = DispatcherCoroutinesRule()

    private var moviesService: FakeMoviesService = FakeMoviesService()
    private lateinit var moviesDatabase: MoviesDatabase
    private lateinit var moviesDao: MoviesDao
    private lateinit var mockRemoteKeysDao: RemoteKeysDao

    @Before
    fun setUP() {
        moviesDatabase = MoviesFactory.createDB(ApplicationProvider.getApplicationContext())
        moviesDao = moviesDatabase.moviesDao()
        mockRemoteKeysDao = moviesDatabase.remoteKeysDao()
    }

    @After
    fun tearDown() {
        moviesDatabase.clearAllTables()
        moviesService.apply {
            failureMsg = null
            movieList = emptyList()
        }

    }

    /***   The first case is when mockApi returns valid data.
     * The load() function should return MediatorResult.Success,
     * and the endOfPaginationReached property should be false.**/
    @Test
    fun refreshLoadReturnsSuccessResultWhenMoreDataIsPresent() = runTest {

        moviesService.apply {
            movieList = MoviesFactory.moviesList()
        }
        val remoteMediator = PagingKeyRemoteMediator(
            MovieType.NowPlaying,
            moviesDatabase,
            moviesService
        )
        val result = remoteMediator.load(LoadType.REFRESH, mockedPagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    /***   The second case is when mockApi returns a successful response, but the returned data is empty.
     *The load() function should return MediatorResult.Success, and the endOfPaginationReached property should be true.**/
    @Test
    fun refreshLoadSuccessAndEndOfPaginationWhenNoMoreData() = runTest {
        moviesService.apply {
            movieList = emptyList()
        }

        val remoteMediator = PagingKeyRemoteMediator(
            MovieType.UpComing,
            moviesDatabase,
            moviesService
        )

        val result = remoteMediator.load(LoadType.REFRESH, mockedPagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertTrue((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    /*** The third case is when mockApi throws an exception when fetching the data.
     * The load() function should return MediatorResult.Error.**/

    @Test
    fun refreshLoadReturnsErrorResultWhenErrorOccurs() = runTest {
        moviesService.apply {
            failureMsg = "invalid API Key"
        }
        val remoteMediator = PagingKeyRemoteMediator(
            MovieType.Popular,
            moviesDatabase,
            moviesService
        )

        val result = remoteMediator.load(LoadType.REFRESH, mockedPagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Error)
    }

}