package com.techsultan.jobber.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.techsultan.jobber.R
import com.techsultan.jobber.databinding.FragmentJobDetailsViewBinding
import com.techsultan.jobber.models.Job


class JobDetailsViewFragment : Fragment() {

    private var _binding: FragmentJobDetailsViewBinding? = null
    private val binding get() = _binding!!

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