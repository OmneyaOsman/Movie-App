package banquemisr.challenge05.data.db

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import banquemisr.challenge05.DispatcherCoroutinesRule
import banquemisr.challenge05.data.db.dao.MoviesDao
import banquemisr.challenge05.data.entities.MovieEntity
import banquemisr.challenge05.data.entities.MovieType
import banquemisr.challenge05.data.entities.MoviesResponse
import banquemisr.challenge05.data.remote.api.MoviesService
import banquemisr.challenge05.data.utils.MockUtil
import com.squareup.moshi.Moshi
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalPagingApi
@RunWith(MockitoJUnitRunner::class)
class RemoteMediatorTest {
    @get:Rule
    val mainDispatcherRule = DispatcherCoroutinesRule()

    private lateinit var mockApi: MoviesService
    private lateinit var movieDb: MoviesDatabase
    lateinit var movieDao: MoviesDao
    private lateinit var remoteMediator: PagingKeyRemoteMediator


    @Before
    fun setUp() {
        val moshi = Moshi.Builder().build()
        movieDb = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MoviesDatabase::class.java
        )
            .allowMainThreadQueries()
            .addTypeConverter(GenereListConverter(moshi))
            .build()

        movieDao = movieDb.moviesDao()

    }

    @After
    fun tearDown() {
        movieDb.clearAllTables()
    }

    // The first case is when mockApi returns valid data.
//    The load() function should return MediatorResult.Success,
//    and the endOfPaginationReached property should be false.
    @Test
    fun refreshLoadReturnsSuccessResultWhenMoreDataIsPresent() = runTest {
        val mockData = MoviesResponse(1, MockUtil.moviesList(), 50, 100)
//        whenever(mockApi.fetchNowPlayingMovies(1)).thenReturn(mockData)
        val remoteMediator = PagingKeyRemoteMediator(
            MovieType.NOW_PLAYING,
            movieDb,
            mockApi
        )
        val pagingState = PagingState<Int, MovieEntity>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)

    }

}
