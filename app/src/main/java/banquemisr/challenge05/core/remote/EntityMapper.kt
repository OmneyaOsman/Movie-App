package banquemisr.challenge05.core.remote

interface EntityMapper<Domain, Entity> {
    fun asDomain(entity: Entity): Domain
}