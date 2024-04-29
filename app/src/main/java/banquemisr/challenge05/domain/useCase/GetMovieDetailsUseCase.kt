package banquemisr.challenge05.domain.useCase

import banquemisr.challenge05.core.utils.Response
import banquemisr.challenge05.domain.model.Movie
import banquemisr.challenge05.domain.repo.MoviesRepository
import kotlinx.coroutines.flow.Flow

class GetMovieDetailsUseCase(val repository: MoviesRepository) {
    suspend operator fun invoke(movieId: Int): Flow<Response<Movie>> =
        repository.getMovieDetails(movieId)
}