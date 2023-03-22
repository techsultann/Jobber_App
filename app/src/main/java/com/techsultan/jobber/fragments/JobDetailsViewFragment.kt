package com.techsultan.jobber.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.techsultan.jobber.MainActivity
import com.techsultan.jobber.databinding.FragmentJobDetailsViewBinding
import com.techsultan.jobber.models.FavoriteJob
import com.techsultan.jobber.models.Job
import com.techsultan.jobber.viewmodels.RemoteJobApplication
import com.techsultan.jobber.viewmodels.RemoteJobViewModel
import com.techsultan.jobber.viewmodels.RemoteJobViewModelFactory


class JobDetailsViewFragment : Fragment() {

    private var _binding: FragmentJobDetailsViewBinding? = null
    private val binding get() = _binding!!
    private val remoteJobViewModel: RemoteJobViewModel by viewModels {
        val application = activity?.applicationContext
        RemoteJobViewModelFactory((application as RemoteJobApplication).repository)
    }
    private lateinit var currentJob: Job
    private val args: JobDetailsViewFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentJobDetailsViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentJob = args.job!!

        setupWebView()

        binding.fabAddFavorite.setOnClickListener {
            addFavJob(view)


        }

    }
    private fun addFavJob(view: View) {

        val favJob =  FavoriteJob(

            0,
            currentJob.candidateRequiredLocation,
            currentJob.category,
            currentJob.companyLogoUrl,
            currentJob.companyName,
            currentJob.description,
            currentJob.jobId,
            currentJob.jobType,
            currentJob.publicationDate,
            currentJob.salary,
            currentJob.title,
            currentJob.url
        )

        remoteJobViewModel.addFavJobs(favJob)
        Snackbar.make(view, "Saved successfully", Snackbar.LENGTH_SHORT).show()
    }

    private fun setupWebView() {

        binding.webView.apply {

            webViewClient = WebViewClient()
            currentJob.url?.let { loadUrl(it) }

        }

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}