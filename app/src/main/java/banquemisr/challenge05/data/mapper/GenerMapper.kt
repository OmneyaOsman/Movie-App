package banquemisr.challenge05.data.mapper

import banquemisr.challenge05.core.remote.EntityMapper
import banquemisr.challenge05.data.entities.GenreEntity
import banquemisr.challenge05.domain.model.Genre

object GenreMapper : EntityMapper<List<Genre>, List<GenreEntity>> {

    override fun asDomain(entity: List<GenreEntity>): List<Genre> {
        return entity.map { genreEntity ->
            Genre(
                id = genreEntity.id,
                name = genreEntity.name
            )
        }
    }
}

fun List<GenreEntity>?.asDomain(): List<Genre> {
    return GenreMapper.asDomain(this.orEmpty())
}