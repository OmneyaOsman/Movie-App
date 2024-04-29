package banquemisr.challenge05.data.db.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
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

@ExperimentalCoroutinesApi
@SmallTest
class MoviesDaoTest{

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    lateinit var moviesDatabase: MoviesDatabase
    private lateinit var moviesDao: MoviesDao

    @Before
    fun initDB() {
        val moshi = Moshi.Builder().build()
        moviesDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MoviesDatabase::class.java)
            .allowMainThreadQueries()
            .addTypeConverter(GenereListConverter(moshi))
            .build()

        moviesDao = moviesDatabase.moviesDao()

    }

    @After
    fun closeDB() {
        moviesDatabase.close()
    }

    @Test
    fun whenInsertMoviesThenRetrieveListOfMovies() = runTest {
        //Arrange
        val list = MoviesFactory.moviesList()

        list.takeIf { it.isNotEmpty() }
            ?.let { moviesDao.insertAll(it) }


        //Act
        val retrievedList = moviesDao.getMovies()

        //Assert
        MatcherAssert.assertThat(retrievedList, CoreMatchers.`is`(CoreMatchers.notNullValue()))
        MatcherAssert.assertThat(retrievedList, IsEqual(list.asReversed()))
    }

    @Test
    fun whenDeleteMoviesThenRetrieveListOfMoviesIsEmpty() = runTest {
        //Arrange
        val list = MoviesFactory.moviesList()

        list.takeIf { it.isNotEmpty() }
            ?.let { moviesDao.insertAll(it) }


        //Act
        moviesDao.deleteMovies()
        val retrievedList = moviesDao.getMovies()

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
            ?.let { moviesDao.insertAll(it) }

        val retrievedMovieEntity = moviesDao.getMovieDetails(list[0].id)

        MatcherAssert.assertThat(retrievedMovieEntity, IsEqual(list[0]))
        MatcherAssert.assertThat(retrievedMovieEntity.genres?.get(0)?.name, IsEqual(list[0].genres?.get(0)?.name))
    }

}