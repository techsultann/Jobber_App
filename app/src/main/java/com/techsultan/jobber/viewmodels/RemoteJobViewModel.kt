package com.techsultan.jobber.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.techsultan.jobber.models.FavoriteJob
import com.techsultan.jobber.repository.RemoteJobRepository
import kotlinx.coroutines.launch

class RemoteJobViewModel(
    app: Application,
    private val repository: RemoteJobRepository
): AndroidViewModel(app) {


    fun remoteJobResult() = repository.remoteJobResult()

    fun addFavJobs(job: FavoriteJob) = viewModelScope.launch {

        repository.saveFavJob(job)
    }

    fun deleteJob(job: FavoriteJob) = viewModelScope.launch {
        repository.deleteJob(job)
    }

    fun getAllFavJobs() = repository.getAllJobs()
}