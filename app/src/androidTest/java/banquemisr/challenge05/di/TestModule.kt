package banquemisr.challenge05.di

import android.app.Application
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import banquemisr.challenge05.BuildConfig
import banquemisr.challenge05.data.db.GenereListConverter
import banquemisr.challenge05.data.db.MoviesDatabase
import banquemisr.challenge05.data.remote.api.MoviesService
import banquemisr.challenge05.data.remote.interceptor.AuthorizationInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object TestModule {

    @Provides
    @Singleton
    fun provideAuthorizationInterceptor(): AuthorizationInterceptor =
        AuthorizationInterceptor(BuildConfig.ACCESS_TOKEN)

    @Provides
    @Singleton
    fun provideGenereListConverter(moshi: Moshi): GenereListConverter {
        return GenereListConverter(moshi)
    }

    fun provideMovieDatabase(
        application: Application,
        genereListConverter: GenereListConverter
    ): MoviesDatabase = Room.inMemoryDatabaseBuilder(
        application,
        MoviesDatabase::class.java
    )
        .allowMainThreadQueries()
        .addTypeConverter(genereListConverter)
        .build()


    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor { logMessage ->
            Timber.e(logMessage)
        }.apply {
            level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }


    @Provides
    @Singleton
    fun provideOkHttpClient(
        interceptor: HttpLoggingInterceptor,
        authorizationInterceptor: AuthorizationInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(authorizationInterceptor)
            .connectTimeout(NetworkModuleConstants.RETROFIT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(NetworkModuleConstants.RETROFIT_TIMEOUT, TimeUnit.SECONDS)
            .addNetworkInterceptor(interceptor)
            .build()

    @Singleton
    @Provides
    fun providesMoshi() = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Provides
    @Singleton
    fun provideRetrofitBuilder(
        client: OkHttpClient, moshi: Moshi
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

    @Provides
    @Singleton
    fun provideMoviesAPI(retrofit: Retrofit): MoviesService =
        retrofit.create(MoviesService::class.java)


}