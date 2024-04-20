package banquemisr.challenge05.data.model


import androidx.room.Entity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity("Genre",primaryKeys = [("id")])
data class Genre(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String
)