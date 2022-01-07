package com.spectra.fieldforce.salesapp.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.spectra.fieldforce.databinding.ExampleBinding


class EditProductFragment : Fragment() {
   lateinit var  leadContactInfoBinding: ExampleBinding

    companion object {
        fun newInstance(): EditProductFragment {
            return newInstance()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ):  View {
        leadContactInfoBinding = ExampleBinding.inflate(inflater, container, false)
        val activity = activity as Context
        return leadContactInfoBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
     /*   tv_lead.setOnClickListener {
            val fragmentB = LeadFragmentCopy()
            activity?.getSupportFragmentManager()?.beginTransaction()
                ?.replace(R.id.fragment_main, fragmentB, "fragmnetId")
                ?.commit();
        }*/
    }



   /* fun backFragment(fragment: LeadFragment){
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_main, fragment)
            .commit()
    }

    override fun onClick(p0: View?) {
        if (requireView().id == R.id.rl_back) {
            val leadFrag = LeadFragment()
            backFragment(leadFrag)
        }
    }*/

}