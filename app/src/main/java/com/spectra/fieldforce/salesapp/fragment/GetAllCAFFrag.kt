package com.spectra.fieldforce.salesapp.fragment

import GetAllCAFAdapter
import GetAllLeadAdapter
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.spectra.fieldforce.R
import com.spectra.fieldforce.api.ApiClient
import com.spectra.fieldforce.api.ApiInterface
import com.spectra.fieldforce.databinding.*
import com.spectra.fieldforce.salesapp.activity.SalesDashboard
import com.spectra.fieldforce.salesapp.model.*
import com.spectra.fieldforce.utils.AppConstants
import com.spectra.fieldforce.utils.Constants
import kotlinx.android.synthetic.main.edit_product_details.*
import kotlinx.android.synthetic.main.fragment_all_lead_list.*
import kotlinx.android.synthetic.main.toolbar.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import kotlin.collections.ArrayList

class GetAllCAFFrag : Fragment(),View.OnClickListener {
    lateinit var  leadContactInfoBinding: FragmentAllLeadListBinding
    private var lead : ArrayList<String>? = null
    private var allCaf: ArrayList<CafData>? = null
    private var inAnimation: AlphaAnimation? = null
    private var outAnimation: AlphaAnimation? = null

    companion object {
        fun newInstance(): GetAllCAFFrag {
            return newInstance()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ):  View {
        leadContactInfoBinding = FragmentAllLeadListBinding.inflate(inflater, container, false)
        val activity = activity as Context
        return leadContactInfoBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchtoolbarlead_list.rl_back.setOnClickListener(this)
        searchtoolbarlead_list.tv_lang.text= AppConstants.CAF
        getCAFList()
        fab_create_lead.visibility =View.GONE
        /*fab_create_lead.setOnClickListener {
            try {
                val fragmentB = CreateLeadFragment()
                parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_main, fragmentB, "fragmnetId")
                        .commit();
            }catch (e:Exception){

            }
        }*/
    }


    fun getCAFList () {
        inAnimation = AlphaAnimation(0f, 1f)
        inAnimation?.duration =200
        leadContactInfoBinding.progressLayout.progressOverlay.animation = inAnimation
        leadContactInfoBinding.progressLayout.progressOverlay.visibility = View.VISIBLE
        val getAllLeadRequest = GetAllLeadRequest(Constants.GETALLCAF, Constants.AUTH_KEY,"","Target@2021#@","manager1")

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getAllCAF(getAllLeadRequest)
        call.enqueue(object : Callback<GetAllCafListResponse?> {
            override fun onResponse(call: Call<GetAllCafListResponse?>, response: Response<GetAllCafListResponse?>) {
                outAnimation = AlphaAnimation(1f, 0f)
                inAnimation?.duration =200
                leadContactInfoBinding.progressLayout.progressOverlay.animation = outAnimation
                leadContactInfoBinding.progressLayout.progressOverlay.visibility = View.GONE

                if (response.isSuccessful && response.body() != null) {
                    try {
                        val msg = response.body()!!.Response.Message
                        if(response.body()?.Response?.StatusCode==200) {
                            allCaf = response.body()!!.Response.Data
                            setAdapter(allCaf!!, context)
                        }else{
                            Toast.makeText(context,msg, Toast.LENGTH_LONG).show()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<GetAllCafListResponse?>, t: Throwable) {
                leadContactInfoBinding.progressLayout.progressOverlay.visibility = View.GONE
                Log.e("RetroError", t.toString())
            }
        })
    }

    private fun setAdapter(allCaf: ArrayList<CafData>, context: Context?) {
        rv_lead_list?.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = GetAllCAFAdapter(allCaf,context)
        }
    }

    override fun onClick(p0: View?) {
        val i = Intent(activity, SalesDashboard::class.java)
        startActivity(i)
    }


}