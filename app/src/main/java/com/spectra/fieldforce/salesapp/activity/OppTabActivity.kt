package com.spectra.fieldforce.salesapp.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.spectra.fieldforce.R
import com.spectra.fieldforce.salesapp.adapter.OppTabAdapter
import com.spectra.fieldforce.utils.AppConstants
import kotlinx.android.synthetic.main.provision_fault_tab_screen.*
import kotlinx.android.synthetic.main.toolbar.view.*

class OppTabActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.provision_fault_tab_screen)
        searchtoolbar.rl_back.setOnClickListener(this)
        searchtoolbar.tv_lang.text= AppConstants.ALL_OPPURTUNITY
        tabLayout?.newTab()?.let { tabLayout.addTab(it.setText("All Opportunity")) }
        tabLayout?.newTab()?.let { tabLayout.addTab(it.setText("Open")) }
        tabLayout?.newTab()?.let { tabLayout.addTab(it.setText("Won")) }
        tabLayout?.newTab()?.let { tabLayout.addTab(it.setText("Lost")) }
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = tabLayout?.tabCount?.let { OppTabAdapter(this, supportFragmentManager, it) }
        viewPager.adapter = adapter

        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
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
      next()
    }

    private fun next(){
        val intent = Intent(this, SalesDashboard::class.java)
        startActivity(intent)
        finish()
    }
}