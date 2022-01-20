package com.app.weatherapp.data.network.api

import android.content.Context
import com.app.weatherapp.data.network.consts.ApiConstants
import com.app.weatherapp.data.network.intercepters.AccessTokenInterceptor
import com.app.weatherapp.data.network.intercepters.NetworkConnectionInterceptor
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RetrofitClient @Inject constructor(
    @ApplicationContext context: Context
) : SafeApiRequest(context) {

    private val applicationContext = context

    private val clientWithoutAuth by lazy {
        Retrofit.Builder().baseUrl(ApiConstants.Urls.BASE_URL).client(
            getClient()
        )
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(Api::class.java)
    }

    private fun getLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        return logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
    }

    private fun getClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .addInterceptor(NetworkConnectionInterceptor(applicationContext))
            .addInterceptor(getLoggingInterceptor())
            .build()
    }

    private val clientWithAuth by lazy {
        Retrofit.Builder().baseUrl(ApiConstants.Urls.BASE_URL)
            .client(
                OkHttpClient().newBuilder()
                    .addInterceptor(AccessTokenInterceptor(applicationContext))
                    .addInterceptor(getLoggingInterceptor())
                    .build()
            ).addConverterFactory(GsonConverterFactory.create()).build().create(Api::class.java)
    }

    suspend fun postRequest(request: Any, url: String, auth_type: String): Any {
        return when (auth_type) {
            ApiConstants.ApiHelpers.AUTH_WITHOUT -> apiRequest { clientWithoutAuth.postRequest(url, request) }
            else -> apiRequest { clientWithAuth.postRequest(url, request) }
        }
    }

    suspend fun getRequest(url: String, auth_type: String): Any {
        return when (auth_type) {
            ApiConstants.ApiHelpers.AUTH_WITHOUT -> apiRequest { clientWithoutAuth.getRequest(url) }
            else -> apiRequest { clientWithAuth.getRequest(url) }
        }
    }

    suspend fun deleteRequest(url: String, auth_type: String): Any {
        return when (auth_type) {
            ApiConstants.ApiHelpers.AUTH_WITHOUT  -> apiRequest { clientWithoutAuth.deleteRequest(url) }
            else -> apiRequest { clientWithAuth.deleteRequest(url) }
        }
    }
}