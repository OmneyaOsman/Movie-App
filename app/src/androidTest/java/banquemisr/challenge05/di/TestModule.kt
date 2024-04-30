package banquemisr.challenge05.di

import banquemisr.challenge05.data.utils.FakeMoviesRepository
import banquemisr.challenge05.domain.repo.MoviesRepository
import banquemisr.challenge05.domain.useCase.GetMovieDetailsUseCase
import banquemisr.challenge05.domain.useCase.GetNowPlayingMoviesUseCase
import banquemisr.challenge05.domain.useCase.GetPopularMoviesUseCase
import banquemisr.challenge05.domain.useCase.GetUpComingMoviesUseCase
import banquemisr.challenge05.domain.useCase.HomeUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object TestModule {
    @Provides
    @Singleton
    fun provideHomeUseCases(
        movieRepository: MoviesRepository,
    ): HomeUseCases {
        return HomeUseCases(
            getNowPlayingMovieUseCase = GetNowPlayingMoviesUseCase(repository = movieRepository),
            getPopularMovieUseCase = GetPopularMoviesUseCase(
                repository = movieRepository
            ),
            getUpComingMoviesUseCase = GetUpComingMoviesUseCase(repository = movieRepository)
        )
    }

    @Singleton
    @Provides

    fun provideMovieDetailsUseCase(
        movieRepository: MoviesRepository
    ): GetMovieDetailsUseCase = GetMovieDetailsUseCase(movieRepository)

    @Singleton
    @Provides
    fun provideMoviesRepositoryImp(): MoviesRepository = FakeMoviesRepository()


}