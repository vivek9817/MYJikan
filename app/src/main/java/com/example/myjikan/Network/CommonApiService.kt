package com.example.myjikan.Network

import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CommonApiService {
    @GET("{path}")
    suspend fun getRequest(@Path("path", encoded = true) path: String): Any

    @POST("{path}")
    suspend fun postRequest(@Path("path", encoded = true) path: String, @Body requestBody: Any): Any
}