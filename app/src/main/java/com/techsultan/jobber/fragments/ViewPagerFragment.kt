package com.techsultan.jobber.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.techsultan.jobber.R
import com.techsultan.jobber.adapter.ViewPagerAdapter
import com.techsultan.jobber.databinding.FragmentViewPagerBinding


class ViewPagerFragment : Fragment() {

    private var _binding: FragmentViewPagerBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentViewPagerBinding.inflate(inflater, container, false)

        viewPager = binding.viewPager
        tabLayout = binding.tabLayout

        val fragments = listOf(
            RemoteJobFragment(),
            SearchJobFragment(),
            SavedJobFragment()
        )

        val adapter = ViewPagerAdapter(this, fragments)
        viewPager.adapter = adapter



        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Home"
                }
                1 -> {
                    tab.text = "Search"
                }
                2 -> {
                    tab.text = "Saved"
                }
            }
        }.attach()


        return binding.root


    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}