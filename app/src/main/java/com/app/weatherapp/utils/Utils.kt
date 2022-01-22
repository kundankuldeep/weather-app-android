package com.app.weatherapp.utils

import android.annotation.SuppressLint
import android.os.Build
import android.transition.Slide
import android.transition.Transition
import android.transition.TransitionManager
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    fun convertFromKelvinToCelsius(pTempInKelvin: Double): Double {
        return pTempInKelvin - 273.15
    }

    @SuppressLint("SimpleDateFormat")
    fun getWeekDay(dateStr: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd") //2022-01-21
        val date: Date? = inputFormat.parse(dateStr)
        val outputFormat = SimpleDateFormat("EEEE")
        return if (date != null) outputFormat.format(date) else ""
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun animateFromBottom(parent: ViewGroup, targetView: View) {
        val transition: Transition = Slide(Gravity.BOTTOM)
        transition.duration = 600
        transition.addTarget(targetView)
        TransitionManager.beginDelayedTransition(parent, transition)
        targetView.visible(true)
    }

}