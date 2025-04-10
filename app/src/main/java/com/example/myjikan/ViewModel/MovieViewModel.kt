package com.example.myjikan.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.myjikan.Model.MovieResponse
import com.example.myjikan.Network.ApiEndPoint
import com.example.myjikan.Network.AppRepository
import com.example.myjikan.Utils.ApiCallback
import com.example.myjikan.Utils.CommonUtils.convertLinkedTreeMapToClass
import kotlinx.coroutines.Dispatchers

class MovieViewModel() : ViewModel() {

    /*Get Top Anim List*/
    fun getTopAnimList() = liveData(Dispatchers.Default) {
        emit(ApiCallback.onLoading(data = null))
        try {
            AppRepository().getRequest(ApiEndPoint.GETTOPANIM).apply {
                emit(ApiCallback.onSuccess(data = this))
            }
        } catch (err: Throwable) {
            emit(ApiCallback.onFailure(data = null, message = err.localizedMessage))
        }
    }

    /*Get Anim Details*/
    fun getAnimDetails(
        animId: Long
    ) = liveData(Dispatchers.Default) {
        emit(ApiCallback.onLoading(data = null))
        try {
            AppRepository().getAnimRequest(ApiEndPoint.GENTANIMDETAILS + animId).apply {
                emit(ApiCallback.onSuccess(data = this))
            }
        } catch (err: Throwable) {
            emit(ApiCallback.onFailure(data = null, message = err.localizedMessage))
        }
    }
}