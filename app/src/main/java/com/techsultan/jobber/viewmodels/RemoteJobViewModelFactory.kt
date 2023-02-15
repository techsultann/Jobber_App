package com.techsultan.jobber.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.techsultan.jobber.repository.RemoteJobRepository

class RemoteJobViewModelFactory(
    val app: Application,
    private val repository: RemoteJobRepository
    ): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RemoteJobViewModel(app, repository) as T
    }
}