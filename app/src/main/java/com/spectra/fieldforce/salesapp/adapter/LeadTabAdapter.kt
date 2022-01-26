package com.spectra.fieldforce.salesapp.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.spectra.fieldforce.salesapp.fragment.GetAllLeadFrag
import com.spectra.fieldforce.salesapp.fragment.GetQualifiedLeadFrag
import android.os.Bundle

internal class LeadTabAdapter(
    var context: Context,
    fm: FragmentManager,
    var totalTabs: Int
) :

    FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                GetAllLeadFrag()
            }
            1 -> {
                val getQualifiedLeadFrag = GetQualifiedLeadFrag()
                val bundle = Bundle()
                bundle.putString("STATUS", "Open")
                getQualifiedLeadFrag.arguments = bundle
                return getQualifiedLeadFrag
            }
            2 -> {
                val getQualifiedLeadFrag = GetQualifiedLeadFrag()
                val bundle = Bundle()
                bundle.putString("STATUS", "Qualified")
                getQualifiedLeadFrag.arguments = bundle
                return getQualifiedLeadFrag
            }
            3 -> {
                val getQualifiedLeadFrag = GetQualifiedLeadFrag()
                val bundle = Bundle()
                bundle.putString("STATUS", "Disqualified")
                getQualifiedLeadFrag.arguments = bundle
                return getQualifiedLeadFrag
            }
            else -> getItem(position)
        }
    }
    override fun getCount(): Int {
        return totalTabs
    }
}
