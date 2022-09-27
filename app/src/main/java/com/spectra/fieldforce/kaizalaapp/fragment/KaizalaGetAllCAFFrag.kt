package com.spectra.fieldforce.kaizalaapp.fragment

import GetAllCAFAdapter
import KaiGetAllCAFAdapter
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.spectra.fieldforce.R
import com.spectra.fieldforce.api.ApiClient
import com.spectra.fieldforce.api.ApiClientKaizala
import com.spectra.fieldforce.api.ApiInterface
import com.spectra.fieldforce.databinding.*
import com.spectra.fieldforce.kaizalaapp.activity.KaizalaDashboard
import com.spectra.fieldforce.salesapp.activity.SalesDashboard
import com.spectra.fieldforce.salesapp.model.*
import com.spectra.fieldforce.utils.Constants
import com.spectra.fieldforce.utils.KaizalaAppConstant
import kotlinx.android.synthetic.main.fragment_all_lead_list.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import kotlin.collections.ArrayList

class KaizalaGetAllCAFFrag : Fragment(),View.OnClickListener {
    lateinit var  leadContactInfoBinding: FragmentAllLeadListBinding
    private var allCaf: ArrayList<CafData>? = null
    private var inAnimation: AlphaAnimation? = null
    private var outAnimation: AlphaAnimation? = null
    private var strTag :String?=null
    private var strSearch :String?=null
    private var UName: String? = null
    private var password : String? = null
    companion object {
        fun newInstance(): KaizalaGetAllCAFFrag {
            return newInstance()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ):  View {
        leadContactInfoBinding = FragmentAllLeadListBinding.inflate(inflater, container, false)
         return leadContactInfoBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = arguments
        strSearch = bundle?.getString("STATUS")
        strTag = bundle?.getString("TAG")
        val sp1: SharedPreferences? = context?.getSharedPreferences("Login", 0)
        UName = sp1?.getString("UserName", null)
        password = sp1?.getString("Password", null)
        if(strTag=="1"){
            linearrrrr.visibility=View.GONE
        }
        executeSearch()
        executeTask()

        fab_create_lead.visibility=View.GONE

    }

    private fun executeTask(){
        CoroutineScope(Dispatchers.IO).launch {

            getCAFList("")

        }
        CoroutineScope(Dispatchers.IO).launch {
            tv_count.setOnClickListener{
                val search = tv_search.text.toString()
                getCAFList(search)
            }
        }
    }
    private fun executeSearch(){
        CoroutineScope(Dispatchers.IO).launch {
            tv_search.addTextChangedListener(object : TextWatcher {

                override fun afterTextChanged(s: Editable) {
                    val search = tv_search.text.toString()
                    if(search.isBlank()){
                        tv_msg.visibility=View.GONE
                        getCAFList("")
                    }
                }

                override fun beforeTextChanged(s: CharSequence, start: Int,
                                               count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence, start: Int,
                                           before: Int, count: Int) {

                }
            })
        }
    }


    fun getCAFList(search: String) {
        try{
        inAnimation = AlphaAnimation(0f, 1f)
        inAnimation?.duration =200
        leadContactInfoBinding.progressLayout.progressOverlay.animation = inAnimation
        leadContactInfoBinding.progressLayout.progressOverlay.visibility = View.VISIBLE
        val getAllLeadRequest = GetAllLeadRequest(Constants.GETALLCAF, Constants.AUTH_KEY,strSearch,password,UName,search,"","",KaizalaAppConstant.HOME)

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
                        val msg = response.body()?.Response?.Message
                        if(response.body()?.Response?.StatusCode==200) {
                            allCaf = response.body()?.Response?.Data
                            allCaf?.let { setAdapter(it, context) }
                        }else{
                            Toast.makeText(context,msg, Toast.LENGTH_LONG).show()
                            tv_msg.visibility=View.GONE
                            tv_msg.text=(msg)
                            allCaf?.clear()
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
        }catch (E: Exception){
            E.printStackTrace()
        }
    }

    private fun setAdapter(allCaf: ArrayList<CafData>, context: Context?) {
        rv_lead_list?.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = KaiGetAllCAFAdapter(allCaf,context)
        }
    }

    override fun onClick(p0: View?) {
        val i = Intent(activity, KaizalaDashboard::class.java)
        startActivity(i)
    }

}