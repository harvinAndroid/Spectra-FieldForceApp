package com.spectra.fieldforce.salesapp.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import com.spectra.fieldforce.databinding.SalesDashboardActivityBinding
import com.spectra.fieldforce.fragment.WcrFragment
import kotlinx.android.synthetic.main.sales_dashboard_activity.*
import java.util.*
import androidx.fragment.app.FragmentManager
import com.spectra.fieldforce.R
import com.spectra.fieldforce.salesapp.fragment.LeadFragment


class SalesDashboard:AppCompatActivity(){
    lateinit var binding: SalesDashboardActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.sales_dashboard_activity)
        init()
    }

    fun init(){
        cardview1.setOnClickListener {
            val fragment = LeadFragment()
            showFragment(fragment)
        }
    }

    fun showFragment(fragment: LeadFragment){
        val fram = supportFragmentManager.beginTransaction()
        fram.replace(R.id.fragment_main,fragment)
        fram.commit()
    }


}