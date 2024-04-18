package banquemisr.challenge05.data.model.movieList


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MoviesResponse(
    @Json(name = "page")
    val page: Int,
    @Json(name = "results")
    val movieResponseList: List<MovieResponse>,
    @Json(name = "total_pages")
    val totalPages: Int,
    @Json(name = "total_results")
    val totalResults: Int
)