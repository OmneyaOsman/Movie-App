package banquemisr.challenge05.data.db

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import banquemisr.challenge05.DispatcherCoroutinesRule
import banquemisr.challenge05.data.entities.GenreEntity
import banquemisr.challenge05.data.entities.MovieEntity
import banquemisr.challenge05.data.entities.MovieType
import banquemisr.challenge05.data.entities.MoviesResponse
import banquemisr.challenge05.data.mapper.asDomain
import banquemisr.challenge05.data.remote.api.MoviesService
import com.squareup.moshi.Moshi
import io.mockk.MockKAnnotations
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock

@ExperimentalPagingApi
class RemoteMediatorTest{
    @get:Rule(order = 1)
    val mainDispatcherRule = DispatcherCoroutinesRule()
    private  var mockApi: MoviesService = mock()
    private lateinit var movieDb: MoviesDatabase
    private lateinit var remoteMediator: PagingKeyRemoteMediator


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        val moshi = Moshi.Builder().build()
        movieDb = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MoviesDatabase::class.java
        )
            .allowMainThreadQueries()
            .addTypeConverter(GenereListConverter(moshi))
            .build()

        PagingKeyRemoteMediator(
            MovieType.NOW_PLAYING,
            movieDb,
            mockApi
        )
    }

    @After
    fun tearDown() {
//        movieDb.clearAllTables()
//        clearAllMocks()
    }

    // The first case is when mockApi returns valid data.
//    The load() function should return MediatorResult.Success,
//    and the endOfPaginationReached property should be false.
    @Test
    fun refreshLoadReturnsSuccessResultWhenMoreDataIsPresent() = runTest {
        val moviesResponse = MoviesResponse(1, moviesList(), 50, 100)
        val pagingData = PagingData.from(moviesResponse.movieResponseList)
        val pagingState = PagingState<Int, MovieEntity>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )
        given(mockApi.fetchNowPlayingMovies(page = 1)).willReturn(moviesResponse)
        val expectedResult = PagingSource.LoadResult.Page(
            data = moviesResponse.movieResponseList.map { it.asDomain() },
            prevKey = null,
            nextKey = 1
        )
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
//        assertEquals(
//            expectedResult, remoteMediator.load(
//                PagingSource.LoadParams.Refresh(
//                    key = 0,
//                    loadSize = 1,
//                    placeholdersEnabled = false
//                )
//            )
//        )
    }

    fun moviesList() =  listOf(
        MovieEntity(
            929590,
            "/uv2twFGMk2qBdyJBJAVcrpRtSa9.jpg",
            listOf(GenreEntity(0,"genre 1")),
            "en",
            "Civil War",
            "In the near future, a group of war journalists attempt to survive while reporting the truth as the United States stands on the brink of civil war.",
            532.705,
            "/sh7Rg8Er3tFcN9BpKIPOMvALgZd.jpg",
            "2024-04-10", 0,
            "Civil War",
            7.404,
            120, toString()
        ), MovieEntity(
            823464, "/j3Z3XktmWB1VhsS8iXNcrR86PXi.jpg",
            listOf(GenreEntity(0,"genre")),
            "en",
            "Godzilla x Kong: The New Empire",
            "Following their explosive showdown, Godzilla and Kong must reunite against a colossal undiscovered threat hidden within our world, challenging their very existence – and our own.",
            1851.749,
            "/tMefBSflR6PGQLv7WvFPpKLZkyk.jpg",
            "2024-03-27", 0,
            "Godzilla x Kong: The New Empire",
            6.699,
            654, toString()
        )
    )

}
