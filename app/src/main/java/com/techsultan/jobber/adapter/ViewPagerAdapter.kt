package com.techsultan.jobber.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.techsultan.jobber.fragments.RemoteJobFragment
import com.techsultan.jobber.fragments.SavedJobFragment
import com.techsultan.jobber.fragments.SearchJobFragment
import com.techsultan.jobber.fragments.ViewPagerFragment

class ViewPagerAdapter(
    fragment: Fragment,
    private val fragments: List<Fragment>
) : FragmentStateAdapter(fragment) {


    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}