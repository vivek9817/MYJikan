package com.example.myjikan.Network

import com.example.myjikan.Model.AnimDetailsResponse
import com.example.myjikan.Model.MovieResponse
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CommonApiService {
    @GET("{path}")
    suspend fun getRequest(@Path("path", encoded = true) path: String): MovieResponse

    @GET("{path}")
    suspend fun getAnimRequest(@Path("path", encoded = true) path: String): AnimDetailsResponse
}