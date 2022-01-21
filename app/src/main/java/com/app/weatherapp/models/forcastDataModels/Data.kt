package com.app.weatherapp.models.forcastDataModels

import com.app.weatherapp.models.currentTempModels.Clouds
import com.app.weatherapp.models.currentTempModels.Weather
import com.app.weatherapp.models.currentTempModels.Wind

data class Data (
    val clouds: Clouds?,
    val dt: Double?,
    val dt_txt: String?,
    val main: Main?,
    val pop: Double?,
    val rain: Rain?,
    val sys: Sys?,
    val visibility: Double?,
    val weather: List<Weather>?,
    val wind: Wind?
)