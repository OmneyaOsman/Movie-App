package banquemisr.challenge05.data.utils

import android.content.Context
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import banquemisr.challenge05.data.db.GenereListConverter
import banquemisr.challenge05.data.db.MoviesDatabase
import banquemisr.challenge05.data.entities.GenreEntity
import banquemisr.challenge05.data.entities.MovieEntity
import banquemisr.challenge05.domain.model.Genre
import banquemisr.challenge05.domain.model.Movie
import com.squareup.moshi.Moshi

object MoviesFactory {

     fun createDB(applicationContext: Context): MoviesDatabase {
        val moshi = Moshi.Builder().build()
        return Room.inMemoryDatabaseBuilder(
            applicationContext,
            MoviesDatabase::class.java
        )
            .allowMainThreadQueries()
            .addTypeConverter(GenereListConverter(moshi))
            .build()
    }

    fun moviesList() =  listOf(
        MovieEntity(
            929590,
            "/uv2twFGMk2qBdyJBJAVcrpRtSa9.jpg",
            listOf(GenreEntity(0,"genre 1")),
            "en",
            "Civil War",
            "In the near future, a group of war journalists attempt to survive while reporting the truth as the United States stands on the brink of civil war.",
            532.705,
            "/sh7Rg8Er3tFcN9BpKIPOMvALgZd.jpg",
            "2024-04-10", 0,
            "Civil War",
            7.404,
            120,  "now_playing",1
        ), MovieEntity(
            823464, "/j3Z3XktmWB1VhsS8iXNcrR86PXi.jpg",
            listOf(GenreEntity(0,"genre")),
            "en",
            "Godzilla x Kong: The New Empire",
            "Following their explosive showdown, Godzilla and Kong must reunite against a colossal undiscovered threat hidden within our world, challenging their very existence – and our own.",
            1851.749,
            "/tMefBSflR6PGQLv7WvFPpKLZkyk.jpg",
            "2024-03-27", 0,
            "Godzilla x Kong: The New Empire",
            6.699,
            654, "now_playing",1
        )
    )

    val mockedPagingState = PagingState<Int, MovieEntity>(
        listOf(),
        null,
        PagingConfig(10),
        10
    )

    val resultList = listOf(
        Movie(
            929590,
            "/uv2twFGMk2qBdyJBJAVcrpRtSa9.jpg",
            listOf(Genre(0, "genre 1")),
            "en",
            "Civil War",
            "In the near future, a group of war journalists attempt to survive while reporting the truth as the United States stands on the brink of civil war.",
            532.705,
            "/sh7Rg8Er3tFcN9BpKIPOMvALgZd.jpg",
            "2024-04-10", 0,
            "Civil War",
            7.404,
            120, toString()
        ), Movie(
            823464, "/j3Z3XktmWB1VhsS8iXNcrR86PXi.jpg",
            listOf(Genre(0, "genre")),
            "en",
            "Godzilla x Kong: The New Empire",
            "Following their explosive showdown, Godzilla and Kong must reunite against a colossal undiscovered threat hidden within our world, challenging their very existence – and our own.",
            1851.749,
            "/tMefBSflR6PGQLv7WvFPpKLZkyk.jpg",
            "2024-03-27", 0,
            "Godzilla x Kong: The New Empire",
            6.699,
            654, toString()
        )
    )
}
