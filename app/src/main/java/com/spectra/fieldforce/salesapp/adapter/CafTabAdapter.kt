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
                val getAllCAFFrag = GetAllCAFFrag()
                val bundle = Bundle()
                bundle.putString("STATUS", "All")
                getAllCAFFrag.arguments = bundle
                return getAllCAFFrag
            }
            1 -> {
                val getAllCAFFrag = GetAllCAFFrag()
                val bundle = Bundle()
                bundle.putString("STATUS", "not equals Installation Completed")
                getAllCAFFrag.arguments = bundle
                return getAllCAFFrag
            }
            2 -> {
                val getAllCAFFrag = GetAllCAFFrag()
                val bundle = Bundle()
                bundle.putString("STATUS", "Installation Completed")
                getAllCAFFrag.arguments = bundle
                return getAllCAFFrag
            }
            else -> getItem(position)
        }
    }
    override fun getCount(): Int {
        return totalTabs
    }

}
