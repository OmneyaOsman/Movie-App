package banquemisr.challenge05.domain.useCase

import banquemisr.challenge05.domain.repo.MoviesRepository

class GetUpComingMovies(val repository: MoviesRepository) {
    suspend operator fun invoke() = repository.getUpComingMovies()
}