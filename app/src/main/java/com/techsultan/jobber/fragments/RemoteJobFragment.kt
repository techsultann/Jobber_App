package com.techsultan.jobber.fragments

import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.techsultan.jobber.R
import com.techsultan.jobber.adapter.RemoteJobAdapter
import com.techsultan.jobber.databinding.FragmentRemoteJobBinding
import com.techsultan.jobber.models.Job
import com.techsultan.jobber.utils.Resource
import com.techsultan.jobber.viewmodels.RemoteJobApplication
import com.techsultan.jobber.viewmodels.RemoteJobViewModel
import com.techsultan.jobber.viewmodels.RemoteJobViewModelFactory


class RemoteJobFragment : Fragment() {
    private var _binding: FragmentRemoteJobBinding? = null
    private val binding get() = _binding!!

    private val remoteJobViewModel: RemoteJobViewModel by viewModels {
        val application = activity?.applicationContext
        RemoteJobViewModelFactory((application as RemoteJobApplication).repository)
    }
    private lateinit var remoteJobAdapter: RemoteJobAdapter

    val TAG = "An error occured"



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View  {
        // Inflate the layout for this fragment
        _binding = FragmentRemoteJobBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupRecyclerView()

        remoteJobAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("job", it)

            }
            findNavController().navigate(R.id.action_remoteJobFragment_to_jobDetailsView, bundle)
        }


        remoteJobViewModel.remoteJob.observe(viewLifecycleOwner, Observer { response ->
            when(response) {
                is Resource.Success -> {
                    response.data?.let { _ ->
                        remoteJobAdapter.differ.submitList(response.data.jobs)
                    }
                }

                else -> {
                    response.message?.let { message ->
                        Log.e(TAG, "An error occurred: $message")
                    }
                }
            }
        })


    }


    private fun setupRecyclerView() {
        remoteJobAdapter = RemoteJobAdapter()

        binding.rvRemoteJobs.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            addItemDecoration(object : DividerItemDecoration(activity, LinearLayout.VERTICAL) {})

            adapter = remoteJobAdapter

        }


    }




    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}