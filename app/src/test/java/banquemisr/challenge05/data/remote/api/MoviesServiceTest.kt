package banquemisr.challenge05.data.remote.api

import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.hamcrest.core.Is.`is`
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
    fun fetchMoviesFromNetworkTestThenReturnSuccessResponse() = runBlocking {
        enqueueResponse("/UpcomingMoviesReponse.json")
        val response = service.fetchUpcomingMovies(1)

        MatcherAssert.assertThat(
            response.movieResponseList[0].title,
            `is`("Godzilla x Kong: The New Empire")
        )
        MatcherAssert.assertThat(response.totalPages, `is`(52))
        MatcherAssert.assertThat(
            response.movieResponseList[0].releaseDate,
            `is`("2024-03-27")
        )
    }

    @Throws(IOException::class)
    @Test
    fun fetchMovieDetailsFromNetworkTestThenReturnSuccessResponse() = runBlocking {
        enqueueResponse("/MovieDetailsResponse.json")
        val response = service.fetchMovieDetails(823464)

        MatcherAssert.assertThat(
            response.title,
            CoreMatchers.`is`("Godzilla x Kong: The New Empire")
        )
        MatcherAssert.assertThat(
            response.originalTitle,
            `is`("Godzilla x Kong: The New Empire")
        )
    }

//    @Test
//    fun fetchMovieDetailsFromNetworkTestThenReturnHttpError() = runBlocking {
//        mockWebServer.enqueue(
//            MockResponse()
//                .setResponseCode(HTTP_CODE_AUTHENTICATION_FAILED)
//                .setBody("{}")
//        )
//
//        val result = getDailyForecasts.invoke("").toList()
//        val response = service.fetchMovieDetails(823464)
//
//
//        assertThat(response.).isNotNull()
//        assertThat(response).isEqualTo(HTTP_CODE_AUTHENTICATION_FAILED)
//
//    }

}