package banquemisr.challenge05.data.db

import banquemisr.challenge05.data.model.Genre
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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
    fun fromString(value: String): List<Genre>? {
        val listType =
            Types.newParameterizedType(List::class.java, Genre::class.java)
        val adapter: JsonAdapter<List<Genre>> = moshi.adapter(listType)
        return adapter.fromJson(value)
    }

    @TypeConverter
    fun fromInfoType(type: List<Genre>?): String {
        val listType =
            Types.newParameterizedType(List::class.java, Genre::class.java)
        val adapter: JsonAdapter<List<Genre>> = moshi.adapter(listType)
        return adapter.toJson(type)
    }
}