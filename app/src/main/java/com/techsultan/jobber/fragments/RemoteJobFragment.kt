package com.techsultan.jobber.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.techsultan.jobber.MainActivity
import com.techsultan.jobber.adapter.RemoteJobAdapter
import com.techsultan.jobber.databinding.FragmentRemoteJobBinding
import com.techsultan.jobber.viewmodels.RemoteJobViewModel


class RemoteJobFragment : Fragment() {

    private var _binding: FragmentRemoteJobBinding? = null
    private val binding get() = _binding!!
    private lateinit var remoteJobAdapter: RemoteJobAdapter
    private lateinit var viewModel: RemoteJobViewModel


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

        viewModel = (activity as MainActivity).viewModel

        setupRecyclerView()


    }


    private fun setupRecyclerView() {
        remoteJobAdapter = RemoteJobAdapter()

        binding.rvRemoteJobs.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            addItemDecoration(object : DividerItemDecoration(activity, LinearLayout.VERTICAL) {})

            adapter = remoteJobAdapter

        }

        fetchingData()

    }

    private fun fetchingData() {

        viewModel.remoteJobResult().observe(viewLifecycleOwner) { remoteJob ->

            if (remoteJob != null) {

                remoteJobAdapter.differ.submitList(remoteJob.jobs)
            }

        }

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}