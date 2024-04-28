package banquemisr.challenge05.presentation

import android.util.Log
import androidx.paging.PagingData
import androidx.paging.map
import app.cash.turbine.test
import banquemisr.challenge05.data.entities.MovieEntity
import banquemisr.challenge05.data.repo.MoviesRepositoryImp
import banquemisr.challenge05.data.utils.MainCoroutinesRule
import banquemisr.challenge05.domain.model.Genre
import banquemisr.challenge05.domain.model.Movie
import banquemisr.challenge05.domain.useCase.GetNowPlayingMoviesUseCase
import banquemisr.challenge05.domain.useCase.HomeUseCases
import banquemisr.challenge05.presentation.home.HomeViewModel
import com.google.common.truth.Truth.assertThat
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {
    @get:Rule(order = 0)
    val mockkRule = MockKRule(this)

    @get:Rule(order = 1)
    val mainDispatcherRule = MainCoroutinesRule()

    private lateinit var viewModel: HomeViewModel

    private var moviesUseCases: HomeUseCases = mockk()

    private var mockMovieRepository: MoviesRepositoryImp = mockk()

    @Before
    fun setUp() {
        val pagingData = PagingData.from(resultList)
        every { moviesUseCases.getNowPlayingMovieUseCase() } returns flowOf(pagingData)

        mockkStatic(Log::class)
        every { Log.isLoggable(any(), any()) } returns false
        every { mockMovieRepository.getNowPlayingMovies() } returns flowOf(pagingData)
        every {
            moviesUseCases.getNowPlayingMovieUseCase
        } returns GetNowPlayingMoviesUseCase(mockMovieRepository)

        viewModel = HomeViewModel(moviesUseCases)

    }
    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `test initial state is empty`() = runTest {

        viewModel.nowPlayingMovies.test {
            val item = awaitItem()
            val empty = PagingData.empty<MovieEntity>()
            item.map {
                assertThat(it).isEqualTo(empty.map { model -> model })
            }
        }

    }


    @Test
    fun testGetTrendingMovies_ReturnPagingData() = runTest {


    }


    val resultList = listOf(
        Movie(
            929590,
            "/uv2twFGMk2qBdyJBJAVcrpRtSa9.jpg",
            listOf(Genre(0,"genre 1")),
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