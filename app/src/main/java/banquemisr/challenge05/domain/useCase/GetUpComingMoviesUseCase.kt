package banquemisr.challenge05.domain.useCase

import banquemisr.challenge05.domain.repo.MoviesRepository

class GetUpComingMoviesUseCase(val repository: MoviesRepository) {
    operator fun invoke() = repository.getUpComingMovies()
}