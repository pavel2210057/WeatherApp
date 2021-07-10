package com.weather.test.data.restapi.interceptor

import com.weather.test.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ApiInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalUrl = chain.request().url()
        val request = chain.request().newBuilder()
            .url(
                originalUrl.newBuilder()
                    .addQueryParameter("appid", BuildConfig.API_KEY)
                    .build()
            )
            .build()

        return chain.proceed(request)
    }
}