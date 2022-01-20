package com.app.weatherapp.data.repository

import com.app.weatherapp.data.network.api.RetrofitClient
import com.app.weatherapp.data.network.consts.ApiConstants
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val retrofitClient: RetrofitClient
) : BaseRepository(retrofitClient) {

    suspend fun getCurrentWeatherData(): Any {
        return retrofitClient.getRequest(
            ApiConstants.Urls.CURRENT_WEATHER_URL + ApiConstants.ApiHelpers.WEATHER_API_KEY,
            ApiConstants.ApiHelpers.AUTH_WITHOUT
        )
    }

    suspend fun getForecastData(): Any {
        return retrofitClient.getRequest(
            ApiConstants.Urls.FORECAST_DATA_URL,
            ApiConstants.ApiHelpers.AUTH_WITHOUT
        )
    }

}