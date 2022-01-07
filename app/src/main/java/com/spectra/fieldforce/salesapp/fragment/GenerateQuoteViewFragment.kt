package com.spectra.fieldforce.salesapp.fragment

import GetAllQuoteAdapter
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
import com.spectra.fieldforce.databinding.FlrFragmentBinding
import com.spectra.fieldforce.databinding.FragmentAllOppurtunityListBinding
import com.spectra.fieldforce.salesapp.activity.OppurtunityActivity
import com.spectra.fieldforce.salesapp.activity.SalesDashboard
import com.spectra.fieldforce.salesapp.activity.UpdateLeadActivity
import com.spectra.fieldforce.salesapp.model.*
import com.spectra.fieldforce.utils.AppConstants
import com.spectra.fieldforce.utils.Constants
import kotlinx.android.synthetic.main.flr_fragment.*
import kotlinx.android.synthetic.main.fragment_all_lead_list.*
import kotlinx.android.synthetic.main.toolbar.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.util.*

class GenerateQuoteViewFragment: Fragment(),View.OnClickListener {
    lateinit var  binding: FragmentAllOppurtunityListBinding
    private var oppurtunity : ArrayList<String>? = null
    private var generatequote: ArrayList<QuoteData>? = null
    private var inAnimation: AlphaAnimation? = null
    private var outAnimation: AlphaAnimation? = null
    var str_Opp_Id : String? = null
    companion object {
        fun newInstance(): GenerateQuoteViewFragment {
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
        searchtoolbarlead_list.rl_back.setOnClickListener(this)
        searchtoolbarlead_list.tv_lang.text= AppConstants.GENERATEQUOTE
        val bundle = arguments
        str_Opp_Id = bundle!!.getString("OppId")!!
        getGenerateQuote()
    }

    fun getGenerateQuote () {
        inAnimation = AlphaAnimation(0f, 1f)
        inAnimation?.duration =200
        binding.progressLayout.progressOverlay.animation = inAnimation
        binding.progressLayout.progressOverlay.visibility = View.VISIBLE
        val getProductListRequest = str_Opp_Id?.let { GetProductListRequest(Constants.GET_QUOTE, Constants.AUTH_KEY, it,"Target@2021#@","manager1") }
        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getQuote(getProductListRequest)
        call.enqueue(object : Callback<GenQuoteResponse?> {
            override fun onResponse(call: Call<GenQuoteResponse?>, response: Response<GenQuoteResponse?>) {
                outAnimation = AlphaAnimation(1f, 0f)
                inAnimation?.duration =200
                binding.progressLayout.progressOverlay.animation = outAnimation
                binding.progressLayout.progressOverlay.visibility = View.GONE
                if (response.isSuccessful && response.body() != null) {
                    try {
                        if(response.body()?.Response?.StatusCode==200) {
                            val msg = response.body()!!.Response.Message
                            generatequote = response.body()!!.Response.Data
                            setAdapter(generatequote!!, context)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<GenQuoteResponse?>, t: Throwable) {
                binding.progressLayout.progressOverlay.visibility = View.GONE
                Log.e("RetroError", t.toString())
            }
        })
    }

    private fun setAdapter(generatequote: ArrayList<QuoteData>, context: Context?) {
        binding.rvFeasList.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = str_Opp_Id?.let { GetAllQuoteAdapter(generatequote,context, it) }
        }
    }

    override fun onClick(p0: View?) {
        val intent = Intent(context, OppurtunityActivity::class.java)
        intent.putExtra("OppId",str_Opp_Id)
        startActivity(intent)
    }

}