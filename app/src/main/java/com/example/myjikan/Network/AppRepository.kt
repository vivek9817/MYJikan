package com.example.myjikan.Network

class AppRepository {

    private val apiService: CommonApiService? by lazy { RetrofitNodeInstance.commonApiService }

    suspend fun getRequest(endpoint: String) = apiService?.getRequest(endpoint)

    suspend fun postRequest(endpoint: String, body: Any) = apiService?.postRequest(endpoint, body)
}