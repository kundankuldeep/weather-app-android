package com.app.weatherapp.data.sharedPref

import android.content.Context
import android.content.SharedPreferences
import com.app.weatherapp.data.sharedPref.constants.PrefConstants
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AppPreferences @Inject constructor(
    @ApplicationContext context: Context
) {
    private var mPrefs: SharedPreferences? = null

    private val context = context.applicationContext

    private fun getSharedPreferences(): SharedPreferences? {
        return context.getSharedPreferences(PrefConstants.PrefFileName, Context.MODE_PRIVATE)
    }

    fun getAccessToken(): String? {
        return getSharedPreferences()?.getString(PrefConstants.ACCESS_TOKEN, null)
    }

    fun setAccessToken(accessToken: String?) {
        this.getSharedPreferences()?.edit()?.putString(PrefConstants.ACCESS_TOKEN, accessToken)
            ?.apply()
    }


    //--- clear all preferences
    fun clearPreferences() {
        mPrefs!!.edit().clear().apply()
    }

}