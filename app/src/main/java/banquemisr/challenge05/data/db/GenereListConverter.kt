package banquemisr.challenge05.data.db

import banquemisr.challenge05.data.entities.GenreEntity
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import javax.inject.Inject


@ProvidedTypeConverter
class GenereListConverter @Inject constructor(
    private val moshi: Moshi,
) {
    @TypeConverter
    fun fromString(value: String): List<GenreEntity>? {
        val listType =
            Types.newParameterizedType(List::class.java, GenreEntity::class.java)
        val adapter: JsonAdapter<List<GenreEntity>> = moshi.adapter(listType)
        return adapter.fromJson(value)
    }

    @TypeConverter
    fun fromInfoType(type: List<GenreEntity>?): String {
        val listType =
            Types.newParameterizedType(List::class.java, GenreEntity::class.java)
        val adapter: JsonAdapter<List<GenreEntity>> = moshi.adapter(listType)
        return adapter.toJson(type)
    }
}