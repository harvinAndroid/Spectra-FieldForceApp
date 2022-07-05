package com.spectra.fieldforce.salesapp.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.spectra.fieldforce.databinding.SalesDashboardActivityBinding
import kotlinx.android.synthetic.main.sales_dashboard_activity.*
import com.spectra.fieldforce.R
import com.spectra.fieldforce.utils.AppConstants
import kotlinx.android.synthetic.main.toolbar.view.*
import androidx.appcompat.app.AlertDialog
import com.spectra.fieldforce.activity.MainActivity
import com.spectra.fieldforce.salesapp.adapter.DashBoardTabAdapter
import com.spectra.fieldforce.salesapp.fragment.LeadCountChartFragment


class SalesDashboard:AppCompatActivity(),View.OnClickListener{
    lateinit var binding: SalesDashboardActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.sales_dashboard_activity)
        init()
        searchtoolbar.rl_back.setOnClickListener(this)
        searchtoolbar.tv_lang.text= AppConstants.MENU
    }

    fun init(){
        cardview1.setOnClickListener {
            Intent(this, LeadTabActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }

        cardopp.setOnClickListener {
            Intent(this, OppTabActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }
        cardflr.setOnClickListener {
            Intent(this, CafTabActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }
        cardviewContact.setOnClickListener {
            Intent(this, ContactTabActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }
        cardSaf.setOnClickListener {
            Intent(this, SafTabActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }
        leadCardView.setOnClickListener {
            Intent(this, DashboardActivity::class.java).also {
                startActivity(it)
                finish()
            }
               /* val leadCurrentMonthFrag = LeadCountChartFragment()
                this.supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_main, leadCurrentMonthFrag).addToBackStack(null).commit()
*/
        }
    }

    override fun onClick(p0: View?) {
        if (p0?.id == R.id.rl_back) {
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }
    }


    override fun onBackPressed() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        builder.setMessage("Do you want to Back?")
        builder.setPositiveButton(
            "Yes"
        ) { _, _ ->
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }
        builder.setNegativeButton(
            "No"
        ) { dialog, _ ->
            dialog.cancel()
        }
        val alert: AlertDialog = builder.create()
        alert.show()
    }


}