package com.app.weatherapp.data.network.api

import android.content.Context
import com.app.weatherapp.data.network.exceptions.ApiException
import com.app.weatherapp.R
import com.app.weatherapp.base.App
import dagger.hilt.android.qualifiers.ApplicationContext
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response


abstract class SafeApiRequest(@ApplicationContext context: Context) {

    private val applicationContext = context

    suspend fun<T: Any> apiRequest(call: suspend () -> Response<T>) : Any {
        val response = call.invoke()
        if(response.isSuccessful)
            return response.body()!!
        else{
            val error = response.errorBody().toString()
            var message = ""
            error.let{
                message = try{
                    val json = JSONObject(it)
                    json.getString(applicationContext.getString(R.string.msg))
                }catch(e: JSONException){
                    applicationContext.getString(R.string.something_went_wrong)
                }
            }
            throw ApiException(message)
        }
    }
}