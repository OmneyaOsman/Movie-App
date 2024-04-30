package banquemisr.challenge05.data.repo

import androidx.paging.map
import androidx.test.core.app.ApplicationProvider
import app.cash.turbine.test
import banquemisr.challenge05.core.remote.Response
import banquemisr.challenge05.data.db.MoviesDatabase
import banquemisr.challenge05.data.db.dao.MoviesDao
import banquemisr.challenge05.data.db.dao.RemoteKeysDao
import banquemisr.challenge05.data.mapper.asDomain
import banquemisr.challenge05.data.utils.DispatcherCoroutinesRule
import banquemisr.challenge05.data.utils.FakeMoviesService
import banquemisr.challenge05.data.utils.MoviesFactory
import banquemisr.challenge05.domain.repo.MoviesRepository
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MoviesRepositoryImpTest {

    @get:Rule
    val coroutinesRule = DispatcherCoroutinesRule()

    private lateinit var moviesRepository: MoviesRepository
    private val moviesService: FakeMoviesService = FakeMoviesService()
    private lateinit var moviesDatabase: MoviesDatabase
    private lateinit var moviesDao: MoviesDao
    private lateinit var remoteKeysDao: RemoteKeysDao


    @Before
    fun setUp() {
        moviesDatabase = MoviesFactory.createDB(ApplicationProvider.getApplicationContext())
        moviesDao = moviesDatabase.moviesDao()
        remoteKeysDao = moviesDatabase.remoteKeysDao()
        moviesRepository = MoviesRepositoryImp(moviesDatabase, moviesService)
    }

    @After
    fun tearDown() {
        moviesDatabase.clearAllTables()
        moviesService.apply {
            failureMsg = null
            movieList = emptyList()
        }

    }

    @Test
    fun callGetPopularMovies_ReturnCorrectPagingData() = runTest {
        moviesService.apply {
            movieList = MoviesFactory.moviesList()
        }
        val result = moviesRepository.getPopularMovies()
        result.test {
            val firstItem = awaitItem()
            firstItem.map {
                assertThat(it).isEqualTo(MoviesFactory.moviesList())
                awaitComplete()
            }
        }
    }

    @Test
    fun callGetUpComingMovies_ReturnCorrectPagingData() = runTest {
        moviesService.apply {
            movieList = MoviesFactory.moviesList()
        }
        val result = moviesRepository.getUpComingMovies()
        result.test {
            val firstItem = awaitItem()
            firstItem.map {
                assertThat(it).isEqualTo(MoviesFactory.moviesList())
                awaitComplete()
            }
        }
    }

    @Test
    fun callGetNowPlayingMovies_ReturnCorrectPagingData() = runTest {
        moviesService.apply {
            movieList = MoviesFactory.moviesList()
        }
        val result = moviesRepository.getNowPlayingMovies()
        result.test {
            val firstItem = awaitItem()
            firstItem.map {
                assertThat(it).isEqualTo(MoviesFactory.moviesList())
                awaitComplete()
            }
        }
    }

    @Test
    fun callGetMovieDetails_ReturnCorrectMovie() = runTest {
        moviesService.apply {
            movieList = MoviesFactory.moviesList()
        }
        val result = moviesRepository.getMovieDetails(929590)
        result.test {
            val firstItem = awaitItem()
            assertThat(firstItem).isEqualTo(Response.Loading)

            val secondItem = awaitItem()
            assertThat(secondItem).isEqualTo(Response.Success(MoviesFactory.moviesList()[0].asDomain()))
            awaitComplete()
        }
    }

    @Test
    fun callGetMovieDetails_ReturnErrorResponse() = runTest {
        moviesService.apply {
            failureMsg = "invalid Movie ID"
        }
        val result = moviesRepository.getMovieDetails(929590)
        result.test {
            val firstItem = awaitItem()
            assertThat(firstItem).isEqualTo(Response.Loading)

            val secondItem = awaitItem()
            TestCase.assertTrue((secondItem is Response.Error))
            awaitComplete()
        }
    }
}
