package banquemisr.challenge05.data.db.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import banquemisr.challenge05.data.db.GenereListConverter
import banquemisr.challenge05.data.db.MoviesDatabase
import banquemisr.challenge05.data.utils.MoviesFactory
import com.squareup.moshi.Moshi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.hamcrest.core.IsEqual
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@SmallTest
class MoviesDaoTest{

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    lateinit var db: MoviesDatabase
    private lateinit var dao: MoviesDao

    @Before
    fun initDB() {
        val moshi = Moshi.Builder().build()
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MoviesDatabase::class.java)
            .allowMainThreadQueries()
            .addTypeConverter(GenereListConverter(moshi))
            .build()

        dao = db.moviesDao()

    }

    @After
    fun closeDB() {
        db.close()
    }

    @Test
    fun whenInsertMoviesThenRetrieveListOfMovies() = runTest {
        //Arrange
        val list = MoviesFactory.moviesList()

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
        val list = MoviesFactory.moviesList()

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
        val list = MoviesFactory.moviesList()

        list.takeIf { it.isNotEmpty() }
            ?.let { dao.insertAll(it) }

        val retrievedMovieEntity = dao.getMovieDetails(list[0].id)

        MatcherAssert.assertThat(retrievedMovieEntity, IsEqual(list[0]))
        MatcherAssert.assertThat(retrievedMovieEntity.genres?.get(0)?.name, IsEqual(list[0].genres?.get(0)?.name))
    }

}