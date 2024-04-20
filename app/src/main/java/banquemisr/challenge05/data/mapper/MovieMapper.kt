package banquemisr.challenge05.data.mapper

import banquemisr.challenge05.data.entities.GenreEntity
import banquemisr.challenge05.data.entities.MovieEntity
import banquemisr.challenge05.domain.model.Genre
import banquemisr.challenge05.domain.model.Movie
import javax.inject.Inject

object MovieMapper : EntityMapper<Movie, MovieEntity> {

    override fun asDomain(movieEntity: MovieEntity): Movie {
        return Movie(
            id = movieEntity.id,
            backdropPath = movieEntity.backdropPath,
            genres = movieEntity.genres.asDomain(),
            originalLanguage = movieEntity.originalLanguage,
            originalTitle = movieEntity.originalTitle,
            overview = movieEntity.overview,
            popularity = movieEntity.popularity,
            releaseDate = movieEntity.releaseDate,
            posterPath = movieEntity.posterPath,
            runtime = movieEntity.runtime,
            title = movieEntity.title,
            voteAverage = movieEntity.voteAverage,
            voteCount = movieEntity.voteCount,
            movieType = movieEntity.movieType
        )
    }
}

fun MovieEntity.asDomain(): Movie {
    return MovieMapper.asDomain(this)
}