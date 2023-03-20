package com.techsultan.jobber.repository

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.techsultan.jobber.api.RetrofitInstance
import com.techsultan.jobber.db.FavoriteJobDao
import com.techsultan.jobber.models.FavoriteJob
import com.techsultan.jobber.models.RemoteJobResponse
import okio.IOException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class RemoteJobRepository(private val favoriteJob: FavoriteJobDao) {





    /*init {
        getRemoteJobResponse()
    }

    private fun getRemoteJobResponse() {


        val response = try {
            RetrofitInstance.apiService.getRemoteJobResponse()
        } catch (e: IOException) {
            Log.e(TAG, "IOException, you might not have internet connection")
        } catch (e: HttpException) {
            Log.e(TAG, "HTTPException, unexpected error")
        }


    }*/

    suspend fun remoteJobResult() = RetrofitInstance.apiService.getRemoteJobResponse()

    suspend fun saveFavJob(job: FavoriteJob) = favoriteJob.addFavJob(job)
    suspend fun deleteJob(job: FavoriteJob) = favoriteJob.deleteJob(job)
    fun getAllJobs() = favoriteJob
}




