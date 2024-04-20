package banquemisr.challenge05.domain.repo

interface MoviesRepository {
    suspend fun getUpComingMovies()
    suspend fun getPopularMovies()
    suspend fun getNowPlayingMovies()
}