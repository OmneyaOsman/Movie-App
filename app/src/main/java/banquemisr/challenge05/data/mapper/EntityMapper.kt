package banquemisr.challenge05.data.mapper

interface EntityMapper<Domain, Entity> {
    fun asDomain(entity: Entity): Domain
}