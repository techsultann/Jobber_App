package com.techsultan.jobber.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.techsultan.jobber.R
import com.techsultan.jobber.adapter.RemoteJobAdapter
import com.techsultan.jobber.databinding.FragmentSearchJobBinding
import com.techsultan.jobber.utils.Constants
import com.techsultan.jobber.utils.Resource
import com.techsultan.jobber.viewmodels.RemoteJobApplication
import com.techsultan.jobber.viewmodels.RemoteJobViewModel
import com.techsultan.jobber.viewmodels.RemoteJobViewModelFactory
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okio.IOException


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

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            if (Constants.isInternetConnected(requireContext())){
            searchJob()
            setupRecyclerView()
        } else {
            remoteJobViewModel.remoteJob.postValue(Resource.Error("No internet connection"))
        }

        } catch (t: Throwable) {
            when(t) {
                is IOException -> remoteJobViewModel.remoteJob.postValue(Resource.Error("Network Failure"))
                else -> remoteJobViewModel.remoteJob.postValue(Resource.Error("Conversion Error"))
            }



        }


        remoteJobAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("job", it)
            }

            findNavController().navigate(R.id.action_viewPagerFragment_to_jobDetailsView, bundle)
        }
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

            remoteJobViewModel.searchResult().observe(viewLifecycleOwner) { searchJob ->
                remoteJobAdapter.differ.submitList(searchJob?.jobs)
            }

        }


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}