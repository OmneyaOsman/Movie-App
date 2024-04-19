package banquemisr.challenge05.data.db.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import banquemisr.challenge05.data.db.MoviesDatabase
import banquemisr.challenge05.data.model.MovieModel
import banquemisr.challenge05.data.model.MovieType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.hamcrest.core.IsEqual
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4ClassRunner::class)
@SmallTest
class MoviesDaoTest {

    private lateinit var dao: MoviesDao
    private lateinit var db: MoviesDatabase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, MoviesDatabase::class.java).build()
        dao = db.moviesDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDB() {
        db.close()
    }

    @Test
    fun whenInsertMoviesThenRetrieveListOfMovies() = runTest {
        //Arrange
        val list = listOfNowPlayingMovies

        list.takeIf { it.isNotEmpty() }
            ?.let { dao.insertAll(it) }


        //Act
        val retrievedList = dao.getMovies()

        //Assert
        MatcherAssert.assertThat(retrievedList, CoreMatchers.`is`(CoreMatchers.notNullValue()))
        MatcherAssert.assertThat(retrievedList, IsEqual(list.asReversed()))
    }

    @Test
    fun whenDeleteMoviesThenRetrieveListOfMoviesIsEmpty() = runTest {
        //Arrange
        val list = listOfNowPlayingMovies

        list.takeIf { it.isNotEmpty() }
            ?.let { dao.insertAll(it) }


        //Act
        dao.deleteMovies()
        val retrievedList = dao.getMovies()

        //Assert
        MatcherAssert.assertThat(retrievedList, CoreMatchers.`is`(CoreMatchers.notNullValue()))
        MatcherAssert.assertThat(retrievedList, CoreMatchers.not(list))
        MatcherAssert.assertThat(retrievedList, IsEqual(emptyList()))
    }

    @Test
    fun whenInsertMoviesThenRetrieveMovieDetails() = runTest {
        //Arrange
        val list = listOfNowPlayingMovies

        list.takeIf { it.isNotEmpty() }
            ?.let { dao.insertAll(it) }

        val retrievedMovieModel = dao.getMovieDetails(list[0].id)

        MatcherAssert.assertThat(retrievedMovieModel, IsEqual(list[0]))
    }
    private val listOfNowPlayingMovies = listOf(
        MovieModel(
            929590,
            "/uv2twFGMk2qBdyJBJAVcrpRtSa9.jpg",
//            emptyList(),
            "en",
            "Civil War",
            "In the near future, a group of war journalists attempt to survive while reporting the truth as the United States stands on the brink of civil war.",
            532.705,
            "/sh7Rg8Er3tFcN9BpKIPOMvALgZd.jpg",
            "2024-04-10", 0,
            "Civil War",
            7.404,
            120, MovieType.NOW_PLAYING.toString()
        ), MovieModel(
            823464, "/j3Z3XktmWB1VhsS8iXNcrR86PXi.jpg",
//            emptyList(),
            "en",
            "Godzilla x Kong: The New Empire",
            "Following their explosive showdown, Godzilla and Kong must reunite against a colossal undiscovered threat hidden within our world, challenging their very existence – and our own.",
            1851.749,
            "/tMefBSflR6PGQLv7WvFPpKLZkyk.jpg",
            "2024-03-27", 0,
            "Godzilla x Kong: The New Empire",
            6.699,
            654, MovieType.NOW_PLAYING.toString()
        )
    )
}