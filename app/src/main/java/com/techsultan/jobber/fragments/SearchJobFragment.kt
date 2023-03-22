package com.techsultan.jobber.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.techsultan.jobber.R
import com.techsultan.jobber.adapter.RemoteJobAdapter
import com.techsultan.jobber.databinding.FragmentSearchJobBinding
import com.techsultan.jobber.models.Job
import com.techsultan.jobber.viewmodels.RemoteJobApplication
import com.techsultan.jobber.viewmodels.RemoteJobViewModel
import com.techsultan.jobber.viewmodels.RemoteJobViewModelFactory
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchJobFragment : Fragment() {

    private var _binding: FragmentSearchJobBinding? = null
    private val binding get() = _binding!!

    private val remoteJobViewModel: RemoteJobViewModel by viewModels {
        val application = activity?.applicationContext
        RemoteJobViewModelFactory((application as RemoteJobApplication).repository)
    }
    private lateinit var remoteJobAdapter: RemoteJobAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSearchJobBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchJob()
        setupRecyclerView()
    }




    private fun searchJob() {
        var job : kotlinx.coroutines.Job? = null
        binding.etSearch.addTextChangedListener{ text ->
            job?.cancel()
            job = MainScope().launch {
                delay(500L)
                text.let {
                    if (text.toString().isNotEmpty()) {
                        remoteJobViewModel.searchRemoteJob(text.toString())
                    }
                }
            }
        }
    }


    private fun setupRecyclerView() {
        remoteJobAdapter = RemoteJobAdapter()

        binding.rvSearchJobs.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            addItemDecoration(object : DividerItemDecoration(activity, LinearLayout.VERTICAL) {})

            adapter = remoteJobAdapter

            remoteJobViewModel.searchResult().observe(viewLifecycleOwner) { remoteJob ->
                remoteJobAdapter.differ.submitList(remoteJob?.jobs)
            }

        }


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}