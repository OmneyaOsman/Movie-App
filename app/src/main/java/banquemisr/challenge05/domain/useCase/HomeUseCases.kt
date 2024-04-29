package banquemisr.challenge05.domain.useCase

data class HomeUseCases(
    val getPopularMovieUseCase: GetPopularMoviesUseCase,
    val getNowPlayingMovieUseCase: GetNowPlayingMoviesUseCase,
    val getUpComingMoviesUseCase: GetUpComingMoviesUseCase
)