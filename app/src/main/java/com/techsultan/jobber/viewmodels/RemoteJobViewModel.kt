package com.techsultan.jobber.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techsultan.jobber.models.FavoriteJob
import com.techsultan.jobber.models.Job
import com.techsultan.jobber.models.RemoteJobResponse
import com.techsultan.jobber.repository.RemoteJobRepository
import com.techsultan.jobber.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class RemoteJobViewModel(
    private val repository: RemoteJobRepository
): ViewModel() {

    val remoteJob: MutableLiveData<Resource<RemoteJobResponse>> = MutableLiveData()

    init {
        getRemoteJob()
    }


    private fun getRemoteJob() = viewModelScope.launch {
        remoteJob.postValue(Resource.Loading())
        val response = repository.remoteJobResult()
        remoteJob.postValue(handleRemoteJobResponse(response))
    }

    private fun handleRemoteJobResponse(response: Response<RemoteJobResponse>) : Resource<RemoteJobResponse> {

        if (response.isSuccessful) {
            response.body().let { jobResponse ->
                return Resource.Success(jobResponse)
            }
        }

        return Resource.Error(response.message())
    }

    fun addFavJobs(job: FavoriteJob) = viewModelScope.launch {

        repository.saveFavJob(job)
    }

    fun deleteJob(job: FavoriteJob) = viewModelScope.launch {
        repository.deleteJob(job)
    }

    fun getAllFavJobs() = repository.getAllJobs().getAllJob()
}