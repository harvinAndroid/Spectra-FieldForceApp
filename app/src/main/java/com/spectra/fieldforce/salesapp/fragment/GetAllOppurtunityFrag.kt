package com.spectra.fieldforce.salesapp.fragment

import GetAllOppurtunityAdapter
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
import com.spectra.fieldforce.api.ApiClient
import com.spectra.fieldforce.api.ApiInterface
import com.spectra.fieldforce.databinding.*
import com.spectra.fieldforce.salesapp.activity.SalesDashboard
import com.spectra.fieldforce.salesapp.model.*
import com.spectra.fieldforce.utils.Constants
import kotlinx.android.synthetic.main.fragment_all_lead_list.*
import kotlinx.android.synthetic.main.fragment_all_lead_list.linearrrrr
import kotlinx.android.synthetic.main.fragment_all_lead_list.tv_count
import kotlinx.android.synthetic.main.fragment_all_lead_list.tv_msg
import kotlinx.android.synthetic.main.fragment_all_lead_list.tv_search
import kotlinx.android.synthetic.main.fragment_all_oppurtunity_list.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import kotlin.collections.ArrayList
import kotlin.concurrent.thread

class GetAllOppurtunityFrag : Fragment(),View.OnClickListener {
    lateinit var  binding: FragmentAllOppurtunityListBinding
    private var oppurtunity : ArrayList<String>? = null
    private var alloppurtunity: ArrayList<OppurData>? = null
    private var inAnimation: AlphaAnimation? = null
    private var outAnimation: AlphaAnimation? = null
    var userName: String? = null
    var password : String? = null
    var strtag :String?=null
    var str_Search :String?=null

    companion object {
        fun newInstance(): GetAllOppurtunityFrag {
            return newInstance()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ):  View {
        binding = FragmentAllOppurtunityListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = arguments
        str_Search = bundle?.getString("STATUS")
        val sp1: SharedPreferences? = context?.getSharedPreferences("Login", 0)
        userName = sp1?.getString("UserName", null)
        password = sp1?.getString("Password", null)

       excuteSearch()
        excuteTask()


    }

    private fun excuteSearch(){
        CoroutineScope(Dispatchers.IO).launch {
            getAlloppurtunityList("")

        }
        CoroutineScope(Dispatchers.IO).launch {
            tv_count.setOnClickListener{
                val search = tv_oppsearch.text.toString()
                getAlloppurtunityList(search)
            }
        }

    }

    fun excuteTask(){
        CoroutineScope(Dispatchers.IO).launch {
            tv_oppsearch.addTextChangedListener(object : TextWatcher {

                override fun afterTextChanged(s: Editable) {
                    val search = tv_oppsearch.text.toString()
                    if(search.isBlank()){
                        tv_msg.visibility=View.GONE
                        getAlloppurtunityList("")
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

    fun getAlloppurtunityList(search: String) {
        inAnimation = AlphaAnimation(0f, 1f)
        inAnimation?.duration =200
        binding.progressLayout.progressOverlay.animation = inAnimation
        binding.progressLayout.progressOverlay.visibility = View.VISIBLE
        val getAllLeadRequest = GetAllLeadRequest(Constants.GET_AllOPPURTUNITY, Constants.AUTH_KEY,str_Search,password,userName,search,"","","")

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getAllOppurtunity(getAllLeadRequest)
        call.enqueue(object : Callback<GetAllOppurtunityResponse?> {
            override fun onResponse(call: Call<GetAllOppurtunityResponse?>, response: Response<GetAllOppurtunityResponse?>) {
                outAnimation = AlphaAnimation(1f, 0f)
                inAnimation?.duration =200
                binding.progressLayout.progressOverlay.animation = outAnimation
                binding.progressLayout.progressOverlay.visibility = View.GONE

                if (response.isSuccessful && response.body() != null) {
                    try {
                        val msg = response.body()?.Response?.Message
                        if(response.body()?.Response?.StatusCode==200) {
                            alloppurtunity = response.body()?.Response?.Data
                            if(alloppurtunity!=null) {
                                setAdapter(alloppurtunity, context)
                            }
                        }else if(response.body()?.Response?.StatusCode==400) {
                            Toast.makeText(context,msg, Toast.LENGTH_LONG).show()
                            tv_msg.visibility=View.GONE
                            tv_msg.text=("Your Search value is not valid")
                            alloppurtunity?.clear()
                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<GetAllOppurtunityResponse?>, t: Throwable) {
                binding.progressLayout.progressOverlay.visibility = View.GONE

                Log.e("RetroError", t.toString())
            }
        })
    }

    private fun setAdapter(allOppurtunity: ArrayList<OppurData>?, context: Context?) {
        binding.rvFeasList.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
           adapter = allOppurtunity?.let { GetAllOppurtunityAdapter(it,context) }
        }
    }

    override fun onClick(p0: View?) {
        val i = Intent(activity, SalesDashboard::class.java)
        startActivity(i)
    }
}