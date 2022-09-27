package com.spectra.fieldforce.kaizalaapp.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.spectra.fieldforce.salesapp.fragment.GetAllLeadFrag
import com.spectra.fieldforce.salesapp.fragment.GetQualifiedLeadFrag
import android.os.Bundle
import com.spectra.fieldforce.kaizalaapp.fragment.KaizalaGetAllCAFFrag
import com.spectra.fieldforce.kaizalaapp.fragment.KaizalaGetAllContactFrag
import com.spectra.fieldforce.salesapp.fragment.GetAllContactFrag

internal class KaiContactTabAdapter(
    var context: Context,
    fm: FragmentManager,
    var totalTabs: Int
) :

    FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                val getAllContactFrag = KaizalaGetAllContactFrag()
                val bundle = Bundle()
                bundle.putString("STATUS", "ALL")
                bundle.putString("TAG", "0")
                getAllContactFrag.arguments = bundle
                return getAllContactFrag
            }
            1-> {
                val getAllContactFrag = KaizalaGetAllContactFrag()
                val bundle = Bundle()
                bundle.putString("STATUS", "not equal closed")
                bundle.putString("TAG", "1")
                getAllContactFrag.arguments = bundle
                return getAllContactFrag


            }
            2-> {
                val getAllContactFrag = KaizalaGetAllContactFrag()
                val bundle = Bundle()
                bundle.putString("STATUS", "CLOSED")
                bundle.putString("TAG", "1")
                getAllContactFrag.arguments = bundle
                return getAllContactFrag
            }
            else -> getItem(position)
        }
    }
    override fun getCount(): Int {
        return totalTabs
    }
}
