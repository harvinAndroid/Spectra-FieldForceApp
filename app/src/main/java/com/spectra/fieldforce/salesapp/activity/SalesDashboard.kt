package com.spectra.fieldforce.salesapp.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.spectra.fieldforce.databinding.SalesDashboardActivityBinding
import kotlinx.android.synthetic.main.sales_dashboard_activity.*
import com.spectra.fieldforce.R
import com.spectra.fieldforce.salesapp.fragment.*
import com.spectra.fieldforce.utils.AppConstants
import kotlinx.android.synthetic.main.toolbar.view.*
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import com.spectra.fieldforce.activity.MainActivity


class SalesDashboard:AppCompatActivity(),View.OnClickListener{
    lateinit var binding: SalesDashboardActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.sales_dashboard_activity)
        init()
        searchtoolbar.rl_back.setOnClickListener(this)
        searchtoolbar.tv_lang.text= AppConstants.DASHBOARD
    }

    fun init(){
        cardview1.setOnClickListener {
            val i = Intent(this, LeadTabActivity::class.java)
            startActivity(i)
            finish()
        }

        cardopp.setOnClickListener {
            val i = Intent(this, OppTabActivity::class.java)
            startActivity(i)
            finish()
        }
        cardflr.setOnClickListener {
            val i = Intent(this, CafTabActivity::class.java)
            startActivity(i)
            finish()
        }

    }

    fun showFragment(fragment: GetAllLeadFrag){
        val fram = supportFragmentManager.beginTransaction()
        fram.replace(R.id.fragment_main,fragment)
        fram.commit()
    }


    fun showFragmentopp(fragment: GetAllOppurtunityFrag){
        val fram = supportFragmentManager.beginTransaction()
        fram.replace(R.id.fragment_main,fragment)
        fram.commit()
    }

    fun showFragmentflr(fragment: GetAllCAFFrag){
        val fram = supportFragmentManager.beginTransaction()
        fram.replace(R.id.fragment_main,fragment)
        fram.commit()
    }

    override fun onClick(p0: View?) {
        if (p0?.id == R.id.rl_back) {

        }
    }

   /* override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }*/

    override fun onBackPressed() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        builder.setMessage("Do you want to Back?")
        builder.setPositiveButton(
            "Yes",
            DialogInterface.OnClickListener { dialog, which -> //if user pressed "yes", then he is allowed to exit from application
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            })
        builder.setNegativeButton(
            "No",
            DialogInterface.OnClickListener { dialog, which -> //if user select "No", just cancel this dialog and continue with app
                dialog.cancel()
            })
        val alert: AlertDialog = builder.create()
        alert.show()
    }


}