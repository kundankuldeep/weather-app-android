package com.app.weatherapp.models.forcastDataModels

data class ForecastDataResponse(
    val city: City?,
    val cnt: Int?,
    val cod: String?,
    val list: List<Data>?,
    val message: Int?
)