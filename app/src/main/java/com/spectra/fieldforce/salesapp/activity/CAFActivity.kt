package com.spectra.fieldforce.salesapp.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.spectra.fieldforce.R
import com.spectra.fieldforce.databinding.CafDemoFragmentBinding
import com.spectra.fieldforce.utils.AppConstants
import kotlinx.android.synthetic.main.caf_demo_fragment.*
import kotlinx.android.synthetic.main.caf_detail_row.*
import kotlinx.android.synthetic.main.toolbar.view.*


class CAFActivity:AppCompatActivity(),View.OnClickListener {
    lateinit var binding:CafDemoFragmentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.caf_demo_fragment)
        searchtoolbarcaf.rl_back.setOnClickListener(this)
        searchtoolbarcaf.tv_lang.text = AppConstants.Caf
        share.setOnClickListener {
            val intent= Intent()
            intent.action=Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT,"Hey Check out this Great app:")
            intent.type="text/plain"
            startActivity(Intent.createChooser(intent,"Share To:"))
        }
    }


    override fun onClick(p0: View?) {

    }

}