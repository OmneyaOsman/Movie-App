/*
 * Designed and developed by 2024 skydoves (Jaewoong Eum)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package banquemisr.challenge05.di

import banquemisr.challenge05.BuildConfig
import banquemisr.challenge05.data.remote.api.MoviesService
import banquemisr.challenge05.data.remote.interceptor.AuthorizationInterceptor
import banquemisr.challenge05.di.NetworkModuleConstants.RETROFIT_TIMEOUT
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
internal object NetworkModule {

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
            .connectTimeout(RETROFIT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(RETROFIT_TIMEOUT, TimeUnit.SECONDS)
            .addNetworkInterceptor(interceptor)
            .build()

    @Provides
    @Singleton
    fun provideRetrofitBuilder(
        client: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideMoviesAPI(retrofit: Retrofit): MoviesService =
        retrofit.create(MoviesService::class.java)


}

object NetworkModuleConstants {
    const val RETROFIT_TIMEOUT = 10L
}
