package com.techsultan.jobber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.techsultan.jobber.databinding.ActivityMainBinding
import com.techsultan.jobber.db.JobDatabase
import com.techsultan.jobber.repository.RemoteJobRepository
import com.techsultan.jobber.viewmodels.RemoteJobViewModel
import com.techsultan.jobber.viewmodels.RemoteJobViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.remoteJobNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Setting up collapsing toolbar
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.collapsingToolbarLayout.setupWithNavController(binding.toolbar, navController, appBarConfiguration)



        //setupAppBar(navController, appBarConfiguration)
        //setupBottomNavMenu(navController)
    }





    /*private fun setupAppBar(navController: NavController, appBarConfiguration: AppBarConfiguration) {
        val topAppBar = binding.toolbar
        topAppBar.setupWithNavController(navController, appBarConfiguration)
    }*/

    /*private fun setupBottomNavMenu(navController: NavController) {

        val bottomNav = binding.bottomNavView
        bottomNav.setupWithNavController(navController)
    }*/


}