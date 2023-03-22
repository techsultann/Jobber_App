package com.techsultan.jobber.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.techsultan.jobber.R
import com.techsultan.jobber.adapter.SavedJobAdapter
import com.techsultan.jobber.databinding.FragmentSavedJobBinding
import com.techsultan.jobber.models.FavoriteJob
import com.techsultan.jobber.viewmodels.RemoteJobApplication
import com.techsultan.jobber.viewmodels.RemoteJobViewModel
import com.techsultan.jobber.viewmodels.RemoteJobViewModelFactory

class SavedJobFragment : Fragment() {

    private var _binding: FragmentSavedJobBinding? = null
    private val binding get() = _binding!!

    private lateinit var savedJobAdapter: SavedJobAdapter
    private val remoteJobViewModel: RemoteJobViewModel by viewModels {
        val application = activity?.applicationContext
        RemoteJobViewModelFactory((application as RemoteJobApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSavedJobBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

    }


    private fun setupRecyclerView() {

        savedJobAdapter = SavedJobAdapter()

        binding.rvJobsSaved.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            addItemDecoration(object : DividerItemDecoration(activity, LinearLayout.VERTICAL) {})

            adapter = savedJobAdapter

        }

        remoteJobViewModel.getAllFavJobs().observe(viewLifecycleOwner) { favJob ->
            savedJobAdapter.differ.submitList(favJob)
            updateUi(favJob)
        }


    }

    private fun updateUi(job : List<FavoriteJob>) {

        if (job.isNotEmpty()) {
            binding.rvJobsSaved.visibility = View.VISIBLE
            binding.cardNoAvailable.visibility = View.GONE
        } else {

            binding.rvJobsSaved.visibility = View.GONE
            binding.cardNoAvailable.visibility = View.VISIBLE
        }

    }


}