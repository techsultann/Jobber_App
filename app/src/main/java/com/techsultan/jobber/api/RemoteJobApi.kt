package com.techsultan.jobber.api

import com.techsultan.jobber.models.RemoteJobResponse
import retrofit2.Call
import retrofit2.http.GET

interface RemoteJobApi {


    @GET("remote-jobs")
    fun getRemoteJobResponse() : Call<RemoteJobResponse>
}