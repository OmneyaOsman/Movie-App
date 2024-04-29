package banquemisr.challenge05.di

import android.app.Application
import androidx.room.Room
import banquemisr.challenge05.data.db.GenereListConverter
import banquemisr.challenge05.data.db.MoviesDatabase
import banquemisr.challenge05.data.db.dao.MoviesDao
import banquemisr.challenge05.data.db.dao.RemoteKeysDao
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideMovieDatabase(
        application: Application,
        genereListConverter: GenereListConverter
    ): MoviesDatabase =
        Room
            .databaseBuilder(
                application,
                MoviesDatabase::class.java,
                "movies_database"
            )
            .addTypeConverter(genereListConverter)
            .build()

    @Singleton
    @Provides
    fun provideMoviesDao(moviesDatabase: MoviesDatabase): MoviesDao = moviesDatabase.moviesDao()

    @Singleton
    @Provides
    fun provideRemoteKeysDao(moviesDatabase: MoviesDatabase): RemoteKeysDao =
        moviesDatabase.remoteKeysDao()

    @Provides
    @Singleton
    fun provideGenreListConverter(moshi: Moshi): GenereListConverter {
        return GenereListConverter(moshi)
    }

}