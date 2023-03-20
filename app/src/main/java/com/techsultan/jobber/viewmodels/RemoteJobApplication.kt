package com.techsultan.jobber.viewmodels

import android.app.Application
import com.techsultan.jobber.db.JobDatabase
import com.techsultan.jobber.repository.RemoteJobRepository

class RemoteJobApplication : Application() {


    private val database by lazy { JobDatabase.getDatabase(this) }
    val repository by lazy { RemoteJobRepository(database.getJobDao()) }
}