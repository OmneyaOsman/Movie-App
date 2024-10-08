package banquemisr.challenge05.data.utils

import banquemisr.challenge05.data.entities.MovieEntity
import banquemisr.challenge05.data.entities.MoviesResponse
import banquemisr.challenge05.data.remote.api.MoviesService
import java.io.IOException

class FakeMoviesService(list : List<MovieEntity> = emptyList()) : MoviesService {

    var failureMsg: String? = null

    var movieList: List<MovieEntity> = list


    private fun createMovieResponse() = MoviesResponse(
        movieResponseList = movieList,
        page = 1,
        totalPages = 50,
        totalResults = 100
    )

    override suspend fun fetchNowPlayingMovies(language: String, page: Int?): MoviesResponse {
        failureMsg?.let {
            throw IOException(it)
        }
        return createMovieResponse()
    }


    override suspend fun fetchUpcomingMovies(page: Int?, language: String): MoviesResponse {
        failureMsg?.let {
            throw IOException(it)
        }
        return createMovieResponse()
    }

    override suspend fun fetchPopularMovies(page: Int?, language: String): MoviesResponse {
        failureMsg?.let {
            throw IOException(it)
        }
        return createMovieResponse()
    }

    override suspend fun fetchMovieDetails(movieId: Int, language: String): MovieEntity {
        failureMsg?.let {
            throw IOException(it)
        }
        return MoviesFactory.moviesList().first { it.id == movieId }
    }
}