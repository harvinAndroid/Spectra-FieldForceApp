package com.spectra.fieldforce.salesapp.activity

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.spectra.fieldforce.R
import com.spectra.fieldforce.activity.MainActivity
import com.spectra.fieldforce.salesapp.adapter.CafTabAdapter
import com.spectra.fieldforce.utils.AppConstants
import kotlinx.android.synthetic.main.provision_fault_tab_screen.*
import kotlinx.android.synthetic.main.toolbar.view.*

class CafTabActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.provision_fault_tab_screen)
        searchtoolbar.rl_back.setOnClickListener(this)
        searchtoolbar.tv_lang.text= AppConstants.CAF
        tabLayout?.newTab()?.setText("All CAF")?.let { tabLayout.addTab(it) }
        tabLayout?.newTab()?.setText("Installation Completed")?.let { tabLayout.addTab(it) }
        tabLayout?.newTab()?.let { tabLayout.addTab(it.setText("Pending")) }
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = CafTabAdapter(this, supportFragmentManager, tabLayout.tabCount)
        viewPager.adapter = adapter

        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        tabLayout?.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {

            }
            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })

    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.rl_back -> {
                next()
            }
        }
    }

    override fun onBackPressed() {
       // super.onBackPressed()
        next()
    }

    private fun next(){
        val intent = Intent(this, SalesDashboard::class.java)
        startActivity(intent)
        finish()
    }
}