package banquemisr.challenge05.data.entities


import androidx.room.Entity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity("Genre",primaryKeys = [("id")])
data class GenreEntity(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String
)