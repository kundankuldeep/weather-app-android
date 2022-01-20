package com.app.weatherapp.data.network.api

import retrofit2.Response
import retrofit2.http.*

interface Api {

    @POST
    suspend fun postRequest(@Url url: String, @Body body: Any): Response<Any>

    @GET
    suspend fun getRequest(@Url url: String): Response<Any>

    @DELETE
    suspend fun deleteRequest(@Url url: String): Response<Any>

}