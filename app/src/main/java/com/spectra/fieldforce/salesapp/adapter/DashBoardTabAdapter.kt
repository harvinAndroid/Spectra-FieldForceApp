package com.spectra.fieldforce.salesapp.adapter

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.spectra.fieldforce.salesapp.fragment.BarChartSrCountFrag
import com.spectra.fieldforce.salesapp.fragment.GetAllOppurtunityFrag
import com.spectra.fieldforce.salesapp.fragment.LeadCountChartFragment

internal class DashBoardTabAdapter(
    var context: Context,
    fm: FragmentManager,
    var totalTabs: Int
) :
    FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                return LeadCountChartFragment()
            }
            1 -> {
                return BarChartSrCountFrag()
            }
            else -> getItem(position)
        }
    }
    override fun getCount(): Int {
        return totalTabs
    }
}
