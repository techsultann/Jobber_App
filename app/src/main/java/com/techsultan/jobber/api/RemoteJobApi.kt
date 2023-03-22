package com.techsultan.jobber.api

import com.techsultan.jobber.models.RemoteJobResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteJobApi {


    @GET("remote-jobs")
    suspend fun getRemoteJobResponse() : Response<RemoteJobResponse>


    @GET("remote-jobs")
    fun searchRemoteJob(
        @Query ("search")
        query: String?): Call<RemoteJobResponse>
}