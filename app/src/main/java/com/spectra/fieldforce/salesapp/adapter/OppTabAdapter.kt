package com.spectra.fieldforce.salesapp.adapter

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.spectra.fieldforce.salesapp.fragment.GetAllOppurtunityFrag

internal class OppTabAdapter(
    var context: Context,
    fm: FragmentManager,
    var totalTabs: Int
) :
    FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                val getAllOpportunityFrag = GetAllOppurtunityFrag()
                val bundle = Bundle()
                bundle.putString("STATUS", "All")
                getAllOpportunityFrag.arguments = bundle
                return getAllOpportunityFrag
            }
            1 -> {
                val getAllOpportunityFrag = GetAllOppurtunityFrag()
                val bundle = Bundle()
                bundle.putString("STATUS", "Open")
                getAllOpportunityFrag.arguments = bundle
                return getAllOpportunityFrag
            }
            2 -> {
                val getAllOpportunityFrag = GetAllOppurtunityFrag()
                val bundle = Bundle()
                bundle.putString("STATUS", "Won")
                getAllOpportunityFrag.arguments = bundle
                return getAllOpportunityFrag
            }
            3 -> {
                val getAllOpportunityFrag = GetAllOppurtunityFrag()
                val bundle = Bundle()
                bundle.putString("STATUS", "Lost")
                getAllOpportunityFrag.arguments = bundle
                return getAllOpportunityFrag
            }
            else -> getItem(position)
        }
    }
    override fun getCount(): Int {
        return totalTabs
    }
}
