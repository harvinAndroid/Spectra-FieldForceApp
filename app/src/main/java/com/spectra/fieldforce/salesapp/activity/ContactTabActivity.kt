package com.spectra.fieldforce.salesapp.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.spectra.fieldforce.R
import com.spectra.fieldforce.salesapp.adapter.ContactTabAdapter
import com.spectra.fieldforce.utils.AppConstants
import kotlinx.android.synthetic.main.provision_fault_tab_screen.*
import kotlinx.android.synthetic.main.toolbar.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class ContactTabActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.provision_fault_tab_screen)
        searchtoolbar.rl_back.setOnClickListener(this)
        searchtoolbar.tv_lang.text= AppConstants.ALL_CONTACT
        try{
        tabLayout?.newTab()?.let { tabLayout.addTab(it.setText("All Contact")) }
        tabLayout?.newTab()?.let { tabLayout.addTab(it.setText("Open Contact")) }
        tabLayout?.newTab()?.let { tabLayout.addTab(it.setText("Closed Contact")) }

        tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = tabLayout?.tabCount?.let { ContactTabAdapter(this, supportFragmentManager, it) }
            CoroutineScope(Dispatchers.IO).launch {
                viewPager.adapter = adapter
                viewPager.offscreenPageLimit = 3
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
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.rl_back -> {
                next()
            }
        }
    }

    override fun onBackPressed() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        builder.setMessage("Do you want to go back to the previous screen?")
        builder.setPositiveButton(
            "Yes"
        ) { _, _ ->
            next()
        }
        builder.setNegativeButton(
            "No"
        ) { dialog, _ ->
            dialog.cancel()
        }
        val alert: AlertDialog = builder.create()
        alert.show()

    }

    private fun next(){
        val intent = Intent(this, SalesDashboard::class.java)
        startActivity(intent)
        finish()
    }
}