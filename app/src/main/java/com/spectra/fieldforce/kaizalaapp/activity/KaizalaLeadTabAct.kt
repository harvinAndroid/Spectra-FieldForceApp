package com.spectra.fieldforce.kaizalaapp.activity

import KaiGetAllLeadAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.spectra.fieldforce.R
import com.spectra.fieldforce.activity.MainActivity
import com.spectra.fieldforce.kaizalaapp.adapter.KaiContactTabAdapter
import com.spectra.fieldforce.kaizalaapp.adapter.KaiLeadTabAdapter
import com.spectra.fieldforce.utils.AppConstants
import kotlinx.android.synthetic.main.provision_fault_tab_screen.*
import kotlinx.android.synthetic.main.toolbar.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class KaizalaLeadTabAct  : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.provision_fault_tab_screen)
        searchtoolbar.rl_back.setOnClickListener(this)
        searchtoolbar.tv_lang.text= AppConstants.ALL_LEADS

        tabLayout.addTab(tabLayout.newTab().setText("All"))
        tabLayout.addTab(tabLayout.newTab().setText("Open"))
        tabLayout.addTab(tabLayout.newTab().setText("Qualified"))
        tabLayout.addTab(tabLayout.newTab().setText("DisQualified"))
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = tabLayout?.tabCount?.let { KaiLeadTabAdapter(this, supportFragmentManager, it) }

        CoroutineScope(Dispatchers.IO).launch {
            viewPager.adapter = adapter
            viewPager.offscreenPageLimit = 4
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

    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.rl_back -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onBackPressed() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        builder.setMessage(AppConstants.PREVIOUS_SCREEN)
        builder.setPositiveButton(
            AppConstants.YES
        ) { _, _ ->
            next()
        }
        builder.setNegativeButton(
            AppConstants.NO
        ) { dialog, _ ->
            dialog.cancel()
        }
        val alert: AlertDialog = builder.create()
        alert.show()

    }

    private fun next(){
        startActivity(Intent(this, KaizalaDashboard::class.java))
        finish()
    }
}