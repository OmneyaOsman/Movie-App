package banquemisr.challenge05.data.remote.api

import banquemisr.challenge05.data.model.MovieDetailsResponse
import banquemisr.challenge05.data.model.MoviesResponse
import banquemisr.challenge05.data.remote.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesService {
    @GET("/now_playing")
    suspend fun fetchNowPlayingMovies(
        @Query("page") page: Int,
        @Query("language") language: String = Constants.LANGUAGE
    ): MoviesResponse

    @GET("/upcoming")
    suspend fun fetchUpcomingMovies(
        @Query("page") page: Int,
        @Query("language") language: String = Constants.LANGUAGE
    ): MoviesResponse

    @GET("/popular")
    suspend fun fetchPopularMovies(
        @Query("page") page: Int,
        @Query("language") language: String = Constants.LANGUAGE
    ): MoviesResponse

    @GET("{movie_id}")
    suspend fun fetchMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String = Constants.LANGUAGE
    ): MovieDetailsResponse
}