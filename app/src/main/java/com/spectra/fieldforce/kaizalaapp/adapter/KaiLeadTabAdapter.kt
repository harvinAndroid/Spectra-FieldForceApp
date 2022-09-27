package com.spectra.fieldforce.kaizalaapp.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.spectra.fieldforce.salesapp.fragment.GetAllLeadFrag
import com.spectra.fieldforce.salesapp.fragment.GetQualifiedLeadFrag
import android.os.Bundle
import com.spectra.fieldforce.kaizalaapp.fragment.KaizalaGetAllLeadFrag

internal class KaiLeadTabAdapter(
    var context: Context,
    fm: FragmentManager,
    var totalTabs: Int
) :

    FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                val getQualifiedLeadFrag = KaizalaGetAllLeadFrag()
                val bundle = Bundle()
                bundle.putString("TAG", "1")
                bundle.putString("STATUS", "Open")
                getQualifiedLeadFrag.arguments = bundle
                return getQualifiedLeadFrag
            }
            1 -> {
                val getQualifiedLeadFrag = KaizalaGetAllLeadFrag()
                val bundle = Bundle()
                bundle.putString("TAG", "1")
                bundle.putString("STATUS", "Open")
                getQualifiedLeadFrag.arguments = bundle
                return getQualifiedLeadFrag
            }
            2 -> {
                val getQualifiedLeadFrag = KaizalaGetAllLeadFrag()
                val bundle = Bundle()
                bundle.putString("TAG", "1")
                bundle.putString("STATUS", "Qualified")
                getQualifiedLeadFrag.arguments = bundle
                return getQualifiedLeadFrag
            }
            3 -> {
                val getQualifiedLeadFrag = KaizalaGetAllLeadFrag()
                val bundle = Bundle()
                bundle.putString("TAG", "1")
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
