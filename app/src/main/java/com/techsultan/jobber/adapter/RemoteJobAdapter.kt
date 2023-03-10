package com.techsultan.jobber.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.techsultan.jobber.databinding.JobLayoutAdapterBinding
import com.techsultan.jobber.fragments.RemoteJobFragmentDirections
import com.techsultan.jobber.models.Job

class RemoteJobAdapter : RecyclerView.Adapter<RemoteJobAdapter.RemoteJobViewHolder>() {


    inner class RemoteJobViewHolder(val binding: JobLayoutAdapterBinding) : RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Job>() {
        override fun areItemsTheSame(oldItem: Job, newItem: Job): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Job, newItem: Job): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RemoteJobViewHolder {
        return RemoteJobViewHolder(
            JobLayoutAdapterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: RemoteJobViewHolder, position: Int) {
        val job = differ.currentList[position]

        holder.binding.apply {

            Glide.with(holder.itemView).load(job.companyLogoUrl).into(ivCompanyLogo)

            tvJobTitle.text = job.title
            tvJobType.text = job.jobType
            tvLocation.text = job.candidateRequiredLocation
            tvDate.text = job.publicationDate

            holder.itemView.setOnClickListener { view ->

                val direction = RemoteJobFragmentDirections
                    .actionRemoteJobFragmentToJobDetailsView(job)
                view.findNavController().navigate(direction)

            }

        }
    }
}