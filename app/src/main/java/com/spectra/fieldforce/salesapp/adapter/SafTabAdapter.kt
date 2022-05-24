package com.spectra.fieldforce.salesapp.adapter

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.spectra.fieldforce.salesapp.fragment.GetAllCAFFrag
import com.spectra.fieldforce.salesapp.fragment.GetAllSAFFrag
import com.spectra.fieldforce.salesapp.fragment.GetQualifiedLeadFrag

internal class SafTabAdapter(
    var context: Context,
    fm: FragmentManager,
    var totalTabs: Int
) :
    FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                val getAllSAFFrag = GetAllSAFFrag()
                val bundle = Bundle()
                bundle.putString("STATUS", "ALL")
                bundle.putString("TAG", "0")
                getAllSAFFrag.arguments = bundle
                return getAllSAFFrag
            }
            1 -> {
                val getAllSAFFrag = GetAllSAFFrag()
                val bundle = Bundle()
                bundle.putString("STATUS", "Completed")
                bundle.putString("TAG", "1")
                getAllSAFFrag.arguments = bundle
                return getAllSAFFrag
            }
            2 -> {
                val getAllSAFFrag = GetAllSAFFrag()
                val bundle = Bundle()
                bundle.putString("STATUS", "!= Completed")
                bundle.putString("TAG", "1")
                getAllSAFFrag.arguments = bundle
                return getAllSAFFrag
            }
            else -> getItem(position)
        }
    }
    override fun getCount(): Int {
        return totalTabs
    }

}
