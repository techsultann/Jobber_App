package com.techsultan.jobber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.techsultan.jobber.databinding.ActivityMainBinding
import com.techsultan.jobber.db.JobDatabase
import com.techsultan.jobber.repository.RemoteJobRepository
import com.techsultan.jobber.viewmodels.RemoteJobViewModel
import com.techsultan.jobber.viewmodels.RemoteJobViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: RemoteJobViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val remoteJobRepository = RemoteJobRepository(JobDatabase.getDatabase(this))
        val viewModelProviderFactory = RemoteJobViewModelFactory(application, remoteJobRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory)[RemoteJobViewModel::class.java]

        setSupportActionBar(binding.toolbar)
    }


}