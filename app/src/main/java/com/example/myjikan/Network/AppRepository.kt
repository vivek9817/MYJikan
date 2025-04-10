package com.example.myjikan.Network

class AppRepository {

    private val apiService: CommonApiService? by lazy { RetrofitNodeInstance.commonApiService }

    suspend fun getRequest(endpoint: String) = apiService?.getRequest(endpoint)

    suspend fun getAnimRequest(endpoint: String) = apiService?.getAnimRequest(endpoint)
}