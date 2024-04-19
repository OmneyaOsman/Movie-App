package banquemisr.challenge05.data.utils

import banquemisr.challenge05.data.model.Genre
import banquemisr.challenge05.data.model.MovieModel

object MockUtil {

    fun moviesList() =  listOf(
        MovieModel(
            929590,
            "/uv2twFGMk2qBdyJBJAVcrpRtSa9.jpg",
            emptyList(),
            "en",
            "Civil War",
            "In the near future, a group of war journalists attempt to survive while reporting the truth as the United States stands on the brink of civil war.",
            532.705,
            "/sh7Rg8Er3tFcN9BpKIPOMvALgZd.jpg",
            "2024-04-10", 0,
            "Civil War",
            7.404,
            120, toString()
        ), MovieModel(
            823464, "/j3Z3XktmWB1VhsS8iXNcrR86PXi.jpg",
            listOf(Genre(0,"genre")),
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
