package com.techsultan.jobber.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.techsultan.jobber.repository.RemoteJobRepository

class RemoteJobViewModelFactory(
    private val repository: RemoteJobRepository
    ): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RemoteJobViewModel(repository) as T
    }
}