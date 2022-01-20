package com.app.weatherapp.data.repository

import com.app.weatherapp.data.network.api.RetrofitClient

abstract class BaseRepository(
    private val retrofitClient: RetrofitClient
) {
}