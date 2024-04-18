package banquemisr.challenge05.data.remote.api

import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test
import java.io.IOException

class MoviesServiceTest : AbstractAPIService<MoviesService>() {

    private lateinit var service: MoviesService

    @Before
    fun initService() {
        service = createService(MoviesService::class.java)
    }

    @Throws(IOException::class)
    @Test
    fun fetchMoviesFromNetworkTestThenReturnSuccessResponse() = runTest {
        enqueueResponse("/UpcomingMoviesReponse.json")
        val response = service.fetchUpcomingMovies(1)
        val responseBody = ((response as ApiResponse.Success).data)

        MatcherAssert.assertThat(
            responseBody.movieResponseList[0].title,
            CoreMatchers.`is`("Godzilla x Kong: The New Empire")
        )
        MatcherAssert.assertThat(responseBody.totalPages, CoreMatchers.`is`(52))
        MatcherAssert.assertThat(
            responseBody.movieResponseList[0].releaseDate,
            CoreMatchers.`is`("2024-03-27")
        )
    }

    @Throws(IOException::class)
    @Test
    fun fetchMovieDetailsFromNetworkTestThenReturnSuccessResponse() = runTest {
        enqueueResponse("/MovieDetailsResponse.json")
        val response = service.fetchMovieDetails(823464)
        val responseBody = requireNotNull((response as ApiResponse.Success).data)

        MatcherAssert.assertThat(
            responseBody.title,
            CoreMatchers.`is`("Godzilla x Kong: The New Empire")
        )
        MatcherAssert.assertThat(
            responseBody.originalTitle,
            CoreMatchers.`is`("Godzilla x Kong: The New Empire")
        )
    }

}