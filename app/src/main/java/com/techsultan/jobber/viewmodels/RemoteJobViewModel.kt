package com.techsultan.jobber.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.techsultan.jobber.models.RemoteJobResponse
import com.techsultan.jobber.repository.RemoteJobRepository

class RemoteJobViewModel(
    app: Application,
    private val repository: RemoteJobRepository
): AndroidViewModel(app) {


    fun remoteJobResult() = repository.remoteJobResult()
}