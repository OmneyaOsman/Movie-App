package banquemisr.challenge05.domain.useCase

import banquemisr.challenge05.domain.repo.MoviesRepository

class GetMovieDetails(val repository: MoviesRepository) {
    suspend operator fun invoke(movieId: Int) = repository.getMovieDetails(movieId)
}