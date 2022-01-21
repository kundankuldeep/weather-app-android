package com.app.weatherapp.models.forcastDataModels

import com.app.weatherapp.models.currentTempModels.Coord

data class City(
    val coord: Coord?,
    val country: String?,
    val id: Double?,
    val name: String?,
    val population: Double?,
    val sunrise: Double?,
    val sunset: Double?,
    val timezone: Double?
)