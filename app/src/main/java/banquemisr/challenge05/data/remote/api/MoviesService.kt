package banquemisr.challenge05.data.remote.api

import banquemisr.challenge05.data.entities.MovieEntity
import banquemisr.challenge05.data.entities.MoviesResponse
import banquemisr.challenge05.core.remote.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesService {
    @GET("movie/now_playing")
    suspend fun fetchNowPlayingMovies(
        @Query("language") language: String = Constants.LANGUAGE,
        @Query("page") page: Int?
    ): MoviesResponse

    @GET("movie/upcoming")
    suspend fun fetchUpcomingMovies(
        @Query("page") page: Int?,
        @Query("language") language: String = Constants.LANGUAGE
    ): MoviesResponse

    @GET("movie/popular")
    suspend fun fetchPopularMovies(
        @Query("page") page: Int?,
        @Query("language") language: String = Constants.LANGUAGE
    ): MoviesResponse

    @GET("movie/{movie_id}")
    suspend fun fetchMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String = Constants.LANGUAGE
    ): MovieEntity
}