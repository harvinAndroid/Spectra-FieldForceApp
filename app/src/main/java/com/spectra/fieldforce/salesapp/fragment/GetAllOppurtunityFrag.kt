package com.spectra.fieldforce.salesapp.fragment

import GetAllOppurtunityAdapter
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import kotlin.collections.ArrayList

class GetAllOppurtunityFrag : Fragment(),View.OnClickListener {
    lateinit var  binding: FragmentAllOppurtunityListBinding
    private var oppurtunity : ArrayList<String>? = null
    private var alloppurtunity: ArrayList<OppurData>? = null
    private var inAnimation: AlphaAnimation? = null
    private var outAnimation: AlphaAnimation? = null

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
        val activity = activity as Context
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = arguments
        str_Search = bundle?.getString("STATUS")
        strtag = bundle?.getString("TAG")
        if(strtag=="1"){
            linearrrrr.visibility=View.GONE
        }
        tv_count.setOnClickListener{
            val search = tv_search.text.toString()
            getAlloppurtunityList(search)
        }
        getAlloppurtunityList("")

    }

    fun getAlloppurtunityList(search: String) {
        inAnimation = AlphaAnimation(0f, 1f)
        inAnimation?.duration =200
        binding.progressLayout.progressOverlay.animation = inAnimation
        binding.progressLayout.progressOverlay.visibility = View.VISIBLE
        val getAllLeadRequest = GetAllLeadRequest(Constants.GET_AllOPPURTUNITY, Constants.AUTH_KEY,str_Search,"Target@2021#@","manager1",search)

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
                        if(response.body()?.Response?.StatusCode==200) {
                             val msg = response.body()?.Response?.Message
                            alloppurtunity = response.body()!!.Response.Data
                            setAdapter(alloppurtunity, context)
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