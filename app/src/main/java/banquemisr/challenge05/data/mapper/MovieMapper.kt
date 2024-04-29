package banquemisr.challenge05.data.mapper

import banquemisr.challenge05.data.entities.MovieEntity
import banquemisr.challenge05.domain.model.Movie

object MovieMapper : EntityMapper<Movie, MovieEntity> {

    override fun asDomain(entity: MovieEntity): Movie {
        return Movie(
            id = entity.id,
            backdropPath = entity.backdropPath,
            genres = entity.genres.asDomain(),
            originalLanguage = entity.originalLanguage,
            originalTitle = entity.originalTitle,
            overview = entity.overview,
            popularity = entity.popularity,
            releaseDate = entity.releaseDate,
            posterPath = entity.posterPath,
            runtime = entity.runtime,
            title = entity.title,
            voteAverage = entity.voteAverage,
            voteCount = entity.voteCount,
            movieType = entity.movieType
        )
    }
}

fun MovieEntity.asDomain(): Movie {
    return MovieMapper.asDomain(this)
}