package com.techsultan.jobber.fragments

import android.nfc.Tag
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.techsultan.jobber.R
import com.techsultan.jobber.adapter.RemoteJobAdapter
import com.techsultan.jobber.databinding.FragmentRemoteJobBinding
import com.techsultan.jobber.models.Job
import com.techsultan.jobber.utils.Constants
import com.techsultan.jobber.utils.Resource
import com.techsultan.jobber.viewmodels.RemoteJobApplication
import com.techsultan.jobber.viewmodels.RemoteJobViewModel
import com.techsultan.jobber.viewmodels.RemoteJobViewModelFactory
import okio.IOException


class RemoteJobFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {
    private var _binding: FragmentRemoteJobBinding? = null
    private val binding get() = _binding!!

    private val remoteJobViewModel: RemoteJobViewModel by viewModels {
        val application = activity?.applicationContext
        RemoteJobViewModelFactory((application as RemoteJobApplication).repository)
    }
    private lateinit var remoteJobAdapter: RemoteJobAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View  {
        // Inflate the layout for this fragment
        _binding = FragmentRemoteJobBinding.inflate(inflater, container, false)
        return binding.root


    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupRecyclerView()
        swipeLayout()

        remoteJobAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("job", it)

            }
            findNavController().navigate(R.id.action_viewPagerFragment_to_jobDetailsView, bundle)
        }



    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun setupRecyclerView() {
        remoteJobAdapter = RemoteJobAdapter()

        binding.rvRemoteJobs.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            addItemDecoration(object : DividerItemDecoration(activity, LinearLayout.VERTICAL) {})

            adapter = remoteJobAdapter

        }

        fetchData()


    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun fetchData(){

        remoteJobViewModel.remoteJob.observe(viewLifecycleOwner, Observer { response ->
            try {
                if (Constants.isInternetConnected(requireContext())) {

                    remoteJobAdapter.differ.submitList(response.data?.jobs)
                    swipeRefreshLayout.isRefreshing = false

                } else {
                    remoteJobViewModel.remoteJob.postValue(Resource.Error("No internet Connection"))
                }
            } catch (t: Throwable) {
                when(t) {
                    is IOException -> remoteJobViewModel.remoteJob.postValue(Resource.Error("Network failure"))
                    else -> remoteJobViewModel.remoteJob.postValue(Resource.Error("Conversion Error"))
                }
            }

        })
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun swipeLayout() {
        swipeRefreshLayout = binding.swipeContainer
        swipeRefreshLayout.setOnRefreshListener(this)

        swipeRefreshLayout.post {
            swipeRefreshLayout.isRefreshing = true
            setupRecyclerView()
        }
    }




    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRefresh() {
        setupRecyclerView()
    }
}