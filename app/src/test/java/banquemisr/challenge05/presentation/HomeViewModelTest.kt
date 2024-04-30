package banquemisr.challenge05.presentation

import androidx.paging.PagingData
import androidx.paging.map
import app.cash.turbine.test
import banquemisr.challenge05.data.entities.MovieEntity
import banquemisr.challenge05.data.repo.MoviesRepositoryImp
import banquemisr.challenge05.data.utils.MainCoroutinesRule
import banquemisr.challenge05.data.utils.TestData.resultList
import banquemisr.challenge05.domain.useCase.GetNowPlayingMoviesUseCase
import banquemisr.challenge05.domain.useCase.GetPopularMoviesUseCase
import banquemisr.challenge05.domain.useCase.GetUpComingMoviesUseCase
import banquemisr.challenge05.domain.useCase.HomeUseCases
import banquemisr.challenge05.presentation.home.HomeViewModel
import com.google.common.truth.Truth.assertThat
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.junit4.MockKRule
import io.mockk.mockk
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

        every { moviesUseCases.getNowPlayingMovieUseCase.invoke() } returns flowOf(pagingData)

        every { mockMovieRepository.getNowPlayingMovies() } returns flowOf(pagingData)
        every { mockMovieRepository.getUpComingMovies() } returns flowOf(pagingData)
        every { mockMovieRepository.getPopularMovies() } returns flowOf(pagingData)

        every {
            moviesUseCases.getNowPlayingMovieUseCase
        } returns GetNowPlayingMoviesUseCase(mockMovieRepository)

        every {
            moviesUseCases.getPopularMovieUseCase
        } returns GetPopularMoviesUseCase(mockMovieRepository)

        every {
            moviesUseCases.getUpComingMoviesUseCase
        } returns GetUpComingMoviesUseCase(mockMovieRepository)

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
    fun testNowPlayingMovies_ReturnPagingData() = runTest {

        val expected = PagingData.from(resultList)

        viewModel.nowPlayingMovies.test {

            awaitItem().map {
                assertThat(it).isEqualTo(expected.map { model -> model })
            }
        }

    }

    @Test
    fun testPopularMovies_ReturnPagingData() = runTest {

        val expected = PagingData.from(resultList)

        viewModel.popularMovies.test {

            awaitItem().map {
                assertThat(it).isEqualTo(expected.map { model -> model })
            }
        }

    }

    @Test
    fun testUpComingMovies_ReturnPagingData() = runTest {

        val expected = PagingData.from(resultList)

        viewModel.upComingMovies.test {

            awaitItem().map {
                assertThat(it).isEqualTo(expected.map { model -> model })
            }
        }

    }


}