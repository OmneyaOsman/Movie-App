package banquemisr.challenge05.core.base

interface Mapper<in FROM, out TO> {
    fun mapToDomainModel(from: FROM): TO

}