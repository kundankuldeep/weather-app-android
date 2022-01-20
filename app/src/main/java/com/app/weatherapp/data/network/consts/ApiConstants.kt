package com.app.weatherapp.data.network.consts

object ApiConstants {

    object ApiHelpers {
        const val AUTH_WITH = "with"
        const val AUTH_WITHOUT = "without"

        const val WEATHER_API_KEY = "9b8cb8c7f11c077f8c4e217974d9ee40"
    }

    object StatusCode {
        const val SERVER_SUCCESS_CODE = 200
        const val SERVER_CREATED_CODE = 201
        const val SERVER_UNAUTHORIZED_CODE = 401
        const val SERVER_FORBIDDEN_CODE = 403
        const val SERVER_TIMEOUT_CODE = 408
        const val INTERNAL_SERVER_ERROR = 500
    }

    object Urls {
        const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
        const val CURRENT_WEATHER_URL = "weather?q=Bengaluru&APPID="
        const val FORECAST_DATA_URL = "forecast?q=Bengaluru&APPID="
    }
}