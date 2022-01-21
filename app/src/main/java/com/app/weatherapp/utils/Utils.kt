package com.app.weatherapp.utils

import java.text.SimpleDateFormat
import java.util.*

object Utils {

    fun convertFromKelvinToCelsius(pTempInKelvin: Double): Double {
        return pTempInKelvin - 273.15
    }

    fun getWeekDay(dateStr: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd") //2022-01-21
        val date: Date = inputFormat.parse(dateStr)
        val outputFormat = SimpleDateFormat("EEEE")
        return outputFormat.format(date)
    }

}