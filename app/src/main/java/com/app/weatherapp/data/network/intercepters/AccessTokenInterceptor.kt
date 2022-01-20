package com.app.weatherapp.data.network.intercepters

import android.content.Context
import com.app.weatherapp.data.sharedPref.AppPreferences
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AccessTokenInterceptor(context: Context) : Interceptor {

    private val appContext = context.applicationContext
    private val preferences = AppPreferences(appContext)

    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = preferences.getAccessToken()
        val request = newRequestWithAccessToken(chain.request(), accessToken.toString())

        return chain.proceed(request)
    }

    private fun newRequestWithAccessToken(request: Request, accessToken: String): Request {
        return request.newBuilder()
            .header("Authorization", "Bearer $accessToken")
            .build()
    }

}
