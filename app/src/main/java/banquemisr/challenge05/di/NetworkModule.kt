package banquemisr.challenge05.di

import android.content.Context
import banquemisr.challenge05.BuildConfig
import banquemisr.challenge05.data.remote.api.MoviesService
import banquemisr.challenge05.data.remote.interceptor.AuthorizationInterceptor
import banquemisr.challenge05.data.remote.interceptor.CacheInterceptor
import banquemisr.challenge05.di.NetworkModuleConstants.RETROFIT_TIMEOUT
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Provides
    @Singleton
    fun provideAuthorizationInterceptor(): AuthorizationInterceptor =
        AuthorizationInterceptor(BuildConfig.ACCESS_TOKEN)

    @Singleton
    @Provides
    fun provideCacheInterceptor() = CacheInterceptor()

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
        cacheInterceptor: CacheInterceptor,
        authorizationInterceptor: AuthorizationInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder().apply {
            addInterceptor(authorizationInterceptor)
            connectTimeout(RETROFIT_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(RETROFIT_TIMEOUT, TimeUnit.SECONDS)
            cache(Cache(File(context.cacheDir, "http-cache"), 10L * 1024L * 1024L))
            addNetworkInterceptor(cacheInterceptor)
            if (BuildConfig.DEBUG) {
                addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            }
        }.build()

    @Singleton
    @Provides
    fun providesMoshi():Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

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

object NetworkModuleConstants {
    const val RETROFIT_TIMEOUT = 10L
}
