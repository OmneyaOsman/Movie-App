package banquemisr.challenge05.domain.model


data class Movie(
    val id: Int,
    val backdropPath: String?,
    val genres: List<Genre>?,
    val originalLanguage: String?,
    val originalTitle: String,
    val overview: String?,
    val popularity: Double?,
    val posterPath: String?,
    val releaseDate: String?,
    val runtime: Int?,
    val title: String,
    val voteAverage: Double?,
    val voteCount: Int?,
    var movieType: String?
)