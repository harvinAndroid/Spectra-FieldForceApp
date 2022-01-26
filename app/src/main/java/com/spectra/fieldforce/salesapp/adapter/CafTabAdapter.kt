package com.spectra.fieldforce.salesapp.adapter

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.spectra.fieldforce.salesapp.fragment.GetAllCAFFrag
import com.spectra.fieldforce.salesapp.fragment.GetQualifiedLeadFrag

internal class CafTabAdapter(
    var context: Context,
    fm: FragmentManager,
    var totalTabs: Int
) :
    FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                val getQualifiedLeadFrag = GetAllCAFFrag()
                val bundle = Bundle()
                bundle.putString("STATUS", "All")
                bundle.putString("TAG", "0")
                getQualifiedLeadFrag.arguments = bundle
                return getQualifiedLeadFrag
            }
            1 -> {
                val getQualifiedLeadFrag = GetAllCAFFrag()
                val bundle = Bundle()
                bundle.putString("STATUS", "Installation Completed")
                bundle.putString("TAG", "1")
                getQualifiedLeadFrag.arguments = bundle
                return getQualifiedLeadFrag
            }
            2 -> {
                val getQualifiedLeadFrag = GetAllCAFFrag()
                val bundle = Bundle()
                bundle.putString("STATUS", "not equals Installation Completed")
                bundle.putString("TAG", "1")
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