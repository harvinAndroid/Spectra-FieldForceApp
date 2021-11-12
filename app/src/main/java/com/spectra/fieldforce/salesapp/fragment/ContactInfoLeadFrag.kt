package com.spectra.fieldforce.salesapp.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.spectra.fieldforce.R
import com.spectra.fieldforce.databinding.LeadContactInfoBinding
import com.spectra.fieldforce.databinding.LeadFragmentBinding
import com.spectra.fieldforce.utils.AppConstants
import kotlinx.android.synthetic.main.lead_contact_info.*
import kotlinx.android.synthetic.main.lead_fragment.*
import kotlinx.android.synthetic.main.toolbar.view.*

class ContactInfoLeadFrag : BottomSheetDialogFragment(),View.OnClickListener {
    lateinit var  leadContactInfoBinding: LeadContactInfoBinding

    companion object {
        fun newInstance(): ContactInfoLeadFrag {
            return newInstance()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ):  View {
        leadContactInfoBinding = LeadContactInfoBinding.inflate(inflater, container, false)
        val activity = activity as Context
        return leadContactInfoBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchtoolbar.rl_back.setOnClickListener(this)
        searchtoolbar.tv_lang.text= AppConstants.LEAD_DETAILS
    }

    fun backFragment(fragment: LeadFragment){
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_main, fragment)
            .commit()
    }

    override fun onClick(p0: View?) {
        if (requireView().id == R.id.rl_back) {
            val leadFrag = LeadFragment()
            backFragment(leadFrag)
        }
    }

}