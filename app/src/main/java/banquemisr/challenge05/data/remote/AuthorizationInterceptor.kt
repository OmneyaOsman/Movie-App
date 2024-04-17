package banquemisr.challenge05.data.remote

import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(private val accessToken : String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response =
        chain.request().let {
            it.newBuilder()
                .addHeader("Authorization", "Bearer $accessToken")
                .run {
                    chain.proceed(this.build())
                }
        }
}