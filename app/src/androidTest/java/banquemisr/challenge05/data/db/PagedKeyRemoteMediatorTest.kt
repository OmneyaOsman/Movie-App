package banquemisr.challenge05.data.db

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import banquemisr.challenge05.DispatcherCoroutinesRule
import banquemisr.challenge05.data.model.MovieModel
import banquemisr.challenge05.data.model.MovieType
import banquemisr.challenge05.data.model.MoviesResponse
import banquemisr.challenge05.data.remote.api.MoviesService
import banquemisr.challenge05.data.utils.MockUtil
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

//@ExperimentalPagingApi
//class PagedKeyRemoteMediatorTest {
//
//    @get:Rule
//    val mainDispatcherRule = DispatcherCoroutinesRule()
//
//    lateinit var mockWebServer: MockWebServer
//    lateinit var service: MoviesService
//    lateinit var mockDb: MoviesDatabase
//
//    private lateinit var remoteMediator: PagedKeyRemoteMediator
//
//
//
//    @Before
//    fun setUp() {
//        mockWebServer = MockWebServer()
//        mockWebServer.start()
//        service = createService(MoviesService::class.java)
//        mockDb = MoviesDatabase.create(
//        ApplicationProvider.getApplicationContext(),
//        useInMemory = true
//    )
//    }
////    private val mockDb = RedditDb.create(
////        ApplicationProvider.getApplicationContext(),
////        useInMemory = true
////    )
//
////    @Test
////    fun `test load refresh`() = runBlocking {
////        // Mock your API response
////        val data = MockUtil.moviesList()
////            coEvery { mockApi.fetchPopularMovies(page = 1) } returns MoviesResponse(1,data ,53,100)
////
////        // Mock your database interactions
////        every { mockDb.moviesDao().insertAll(movies = data) } just runs
////        every { mockDb.remoteKeysDao().insertAll(any()) } just runs
////
////        // Call the load method with LoadType.REFRESH
////        val result = remoteMediator.load(LoadType.REFRESH, mockMediatorOutput)
////
////        // Verify that the result is successful
////        assert(result is RemoteMediator.MediatorResult.Success)
////
////        // Verify that the expected methods were called
////        coVerify { mockApiService.fetchPopularMovies(page = any()) }
////        verify { mockDatabase.moviesDao().insertAll(any()) }
////        verify { mockDatabase.remoteKeysDao().insertAll(any()) }
////    }
//    @After
//    fun tearDown() {
//        mockDb.clearAllTables()
//        // Clear out failure message to default to the successful response.
////        mockApi.failureMsg = null
//        // Clear out posts after each test run.
////        mockApi.clearPosts()
//    }
//
////    The next step is to test the load() function. In this example, there are three cases to test:
////
////    The first case is when mockApi returns valid data.
////    The load() function should return MediatorResult.Success,
////    and the endOfPaginationReached property should be false.
//@Test
//fun refreshLoadReturnsSuccessResultWhenMoreDataIsPresent() = runTest {
//    // Add mock results for the API to return.
////    mockPosts.forEach { post -> mockApi. }
//    val remoteMediator = PagedKeyRemoteMediator(
//        MovieType.NOW_PLAYING,
//        mockDb,
//        mockApi
//    )
//    val pagingState = PagingState<Int, MovieModel>(
//        listOf(),
//        null,
//        PagingConfig(10),
//        10
//    )
//    val result = remoteMediator.load(LoadType.REFRESH, pagingState)
//    assertTrue(result is RemoteMediator.MediatorResult.Success)
//    assertFalse((result as  RemoteMediator.MediatorResult.Success).endOfPaginationReached )
//}
////    The second case is when mockApi returns a successful response, but the returned data is empty. The load() function should return MediatorResult.Success, and the endOfPaginationReached property should be true.
////    The third case is when mockApi throws an exception when fetching the data. The load() function should return MediatorResult.Error.
//
//
//
//
//    private fun createService(clazz: Class<T>): T {
//        return Retrofit.Builder()
//            .baseUrl(mockWebServer.url("/"))
//            .addConverterFactory(MoshiConverterFactory.create())
//            .build()
//            .create(clazz)
//    }
//}