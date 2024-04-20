package banquemisr.challenge05.di

import banquemisr.challenge05.data.db.MoviesDatabase
import banquemisr.challenge05.data.remote.api.MoviesService
import banquemisr.challenge05.data.repo.MoviesRepositoryImp
import banquemisr.challenge05.domain.repo.MoviesRepository
import banquemisr.challenge05.domain.useCase.GetNowPlayingMovies
import banquemisr.challenge05.domain.useCase.GetPopularMovies
import banquemisr.challenge05.domain.useCase.GetUpComingMovies
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideMoviesRepositoryImp(
        db: MoviesDatabase,
        api: MoviesService,
    ): MoviesRepository = MoviesRepositoryImp(db, api)

    @Singleton
    @Provides
    fun provideGetNowPlayingMovies(
       repository: MoviesRepository
    ): GetNowPlayingMovies = GetNowPlayingMovies(repository)

    @Singleton
    @Provides
    fun provideGetUpComingMovies(
       repository: MoviesRepository
    ): GetUpComingMovies = GetUpComingMovies(repository)

    @Singleton
    @Provides
    fun provideGetPopularMovies(
       repository: MoviesRepository
    ): GetPopularMovies = GetPopularMovies(repository)
}