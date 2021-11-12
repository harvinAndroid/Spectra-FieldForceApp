package com.spectra.fieldforce.salesapp.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.spectra.fieldforce.R
import com.spectra.fieldforce.databinding.LeadFragmentBinding
import com.spectra.fieldforce.utils.AppConstants
import com.spectra.fieldforce.utils.Constants
import kotlinx.android.synthetic.main.lead_contact_info.*
import kotlinx.android.synthetic.main.lead_fragment.*
import kotlinx.android.synthetic.main.toolbar.view.*

class LeadFragment:Fragment(), View.OnClickListener{

    lateinit var  leadFragmentBinding: LeadFragmentBinding


    companion object {
        fun newInstance(): LeadFragment {
            return LeadFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        leadFragmentBinding = LeadFragmentBinding.inflate(inflater, container, false)
        val activity = activity as Context
        return leadFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchtoolbar_lead.rl_back.setOnClickListener(this)
        searchtoolbar_lead.tv_lang.text= AppConstants.LEAD_DETAILS
        init()

    }

    fun init(){
        card_view1.setOnClickListener {
            val fragment = ContactInfoLeadFrag()
            showFragment(fragment)
        }


    }

    override fun onClick(p0: View?) {
        if (requireView().id == R.id.rl_back) {

        }
    }

    fun showFragment(fragment: ContactInfoLeadFrag){
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_main, fragment)
            .commit()
    }


}