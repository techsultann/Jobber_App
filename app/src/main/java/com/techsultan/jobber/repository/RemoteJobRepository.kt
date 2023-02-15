package com.techsultan.jobber.repository

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.techsultan.jobber.api.RemoteJobApi
import com.techsultan.jobber.api.RetrofitInstance
import com.techsultan.jobber.models.RemoteJobResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteJobRepository {

    private val remoteJobService = RetrofitInstance.apiService
    private val remoteJobResponseLiveData : MutableLiveData<RemoteJobResponse?> = MutableLiveData()



    init {
        getRemoteJobResponse()
    }

    private fun getRemoteJobResponse() {

        remoteJobService.getRemoteJobResponse().enqueue(
            object :  Callback<RemoteJobResponse> {
                override fun onResponse(
                    call: Call<RemoteJobResponse>,
                    response: Response<RemoteJobResponse>
                ) {
                    remoteJobResponseLiveData.postValue(response.body())
                }

                override fun onFailure(call: Call<RemoteJobResponse>, t: Throwable) {
                    remoteJobResponseLiveData.postValue(null)
                    Log.e(TAG, "onFailure: ${t.message}")
                }


            }
        )
    }

    fun remoteJobResult() : MutableLiveData<RemoteJobResponse?> {
        return remoteJobResponseLiveData
    }
}




