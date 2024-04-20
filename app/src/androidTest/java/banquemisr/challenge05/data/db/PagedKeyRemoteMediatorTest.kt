package banquemisr.challenge05.data.db

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
//    val pagingState = PagingState<Int, MovieEntity>(
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