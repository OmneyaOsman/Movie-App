package banquemisr.challenge05.data.entities


import androidx.compose.runtime.Immutable
import androidx.room.Entity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Immutable
@JsonClass(generateAdapter = true)
@Entity(tableName = "Movie", primaryKeys = [("id")])
data class MovieEntity(
    @Json(name = "id")
    val id: Int,
    @Json(name = "backdrop_path")
    val backdropPath: String?,
    @Json(name = "genres")
    val genres: List<GenreEntity>?,
    @Json(name = "original_language")
    val originalLanguage: String?,
    @Json(name = "original_title")
    val originalTitle: String,
    @Json(name = "overview")
    val overview: String?,
    @Json(name = "popularity")
    val popularity: Double?,
    @Json(name = "poster_path")
    val posterPath: String?,
    @Json(name = "release_date")
    val releaseDate: String?,
    @Json(name = "runtime")
    val runtime: Int?,
    @Json(name = "title")
    val title: String,
    @Json(name = "vote_average")
    val voteAverage: Double?,
    @Json(name = "vote_count")
    val voteCount: Int?,
    var movieType: String?
)