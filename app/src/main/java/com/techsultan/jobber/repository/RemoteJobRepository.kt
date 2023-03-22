package com.techsultan.jobber.repository

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.techsultan.jobber.api.RetrofitInstance
import com.techsultan.jobber.db.FavoriteJobDao
import com.techsultan.jobber.models.FavoriteJob
import com.techsultan.jobber.models.RemoteJobResponse
import com.techsultan.jobber.utils.Resource
import okio.IOException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class RemoteJobRepository(private val favoriteJob: FavoriteJobDao) {


    private val remoteJobService = RetrofitInstance.apiService
    private val searchRemoteJobLiveData: MutableLiveData<RemoteJobResponse?> = MutableLiveData()



    fun searchRemoteJob(query: String?) {

        remoteJobService.searchRemoteJob(query).enqueue(
            object : Callback<RemoteJobResponse> {
                override fun onResponse(
                    call: Call<RemoteJobResponse>,
                    response: Response<RemoteJobResponse>
                ) {
                    searchRemoteJobLiveData.postValue(response.body())
                }

                override fun onFailure(call: Call<RemoteJobResponse>, t: Throwable) {
                    searchRemoteJobLiveData.postValue(null)
                }

            }
        )
    }

    fun searchJobResult() : MutableLiveData<RemoteJobResponse?> {

        return searchRemoteJobLiveData
    }

    suspend fun remoteJobResult() = RetrofitInstance.apiService.getRemoteJobResponse()

    suspend fun saveFavJob(job: FavoriteJob) = favoriteJob.addFavJob(job)
    suspend fun deleteJob(job: FavoriteJob) = favoriteJob.deleteJob(job)
    fun getAllJobs() = favoriteJob
}




