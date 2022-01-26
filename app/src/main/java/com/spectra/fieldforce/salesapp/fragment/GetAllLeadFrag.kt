package com.spectra.fieldforce.salesapp.fragment

import GetAllLeadAdapter
import android.content.Context
import android.content.Intent
import android.os.Bundle
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
import com.spectra.fieldforce.api.ApiInterface
import com.spectra.fieldforce.databinding.*
import com.spectra.fieldforce.salesapp.activity.SalesDashboard
import com.spectra.fieldforce.salesapp.model.AllLeadData
import com.spectra.fieldforce.salesapp.model.GetAllLeadRequest
import com.spectra.fieldforce.salesapp.model.GetAllLeadResponse
import com.spectra.fieldforce.utils.Constants
import kotlinx.android.synthetic.main.fragment_all_lead_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import kotlin.collections.ArrayList

class GetAllLeadFrag : Fragment(),View.OnClickListener {
lateinit var  leadContactInfoBinding: FragmentAllLeadListBinding
    private var lead : ArrayList<String>? = null
    private var allLead: ArrayList<AllLeadData>? = null
    private var inAnimation: AlphaAnimation? = null
    private var outAnimation: AlphaAnimation? = null

    val search :String?=null

    companion object {
        fun newInstance(): GetAllLeadFrag {
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
       /* searchtoolbarlead_list.rl_back.setOnClickListener(this)
        searchtoolbarlead_list.tv_lang.text= AppConstants.ALL_LEADS

     */
       // val search = tv_search.text.toString()
         getAllLeadList("")
       // init()

        tv_count.setOnClickListener{
            val search = tv_search.text.toString()
            getAllLeadList(search)
        }

        fab_create_lead.setOnClickListener {
            try {
                val fragmentB = CreateLeadFragment()
                parentFragmentManager.beginTransaction()
                        .replace(R.id.frag_container, fragmentB, "fragmnetId")
                        .commit();
            }catch (e:Exception){

            }
        }
    }



    fun getAllLeadList(srch: String) {
        inAnimation = AlphaAnimation(0f, 1f)
        inAnimation?.duration =200
        leadContactInfoBinding.progressLayout.progressOverlay.animation = inAnimation
        leadContactInfoBinding.progressLayout.progressOverlay.visibility = View.VISIBLE
        val getAllLeadRequest = GetAllLeadRequest(Constants.GET_AllLEADS, Constants.AUTH_KEY,"All","Target@2021#@","manager1",srch)

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getAllLead(getAllLeadRequest)
        call.enqueue(object : Callback<GetAllLeadResponse?> {
            override fun onResponse(call: Call<GetAllLeadResponse?>, response: Response<GetAllLeadResponse?>) {
                outAnimation = AlphaAnimation(1f, 0f)
                inAnimation?.duration =200
                leadContactInfoBinding.progressLayout.progressOverlay.animation = outAnimation
                leadContactInfoBinding.progressLayout.progressOverlay.visibility = View.GONE

                if (response.isSuccessful && response.body() != null) {
                    try {
                        val msg = response.body()?.Response?.Message
                        if(response.body()?.Response?.StatusCode==200) {
                            allLead = response.body()?.Response?.Data
                            setAdapter(allLead, context)
                        }else{
                            Toast.makeText(context,msg, Toast.LENGTH_LONG).show()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<GetAllLeadResponse?>, t: Throwable) {
                leadContactInfoBinding.progressLayout.progressOverlay.visibility = View.GONE
                Log.e("RetroError", t.toString())
            }
        })
    }
   /* private fun init() {
        leadContactInfoBinding.swipeRefreshLayout.setEnabled(true)
        leadContactInfoBinding.swipeRefreshLayout.setRefreshing(true)
        leadContactInfoBinding.swipeRefreshLayout.setOnRefreshListener {
            try {
                leadContactInfoBinding.swipeRefreshLayout.setRefreshing(true)
               getAllLeadList("")
            } catch (ex: Exception) {
                ex.message
            }
        }
    }*/


    private fun setAdapter(allLead: ArrayList<AllLeadData>?, context: Context?) {
        rv_lead_list?.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = allLead?.let { GetAllLeadAdapter(it,context) }
        }
    }

    override fun onClick(p0: View?) {
        val i = Intent(activity, SalesDashboard::class.java)
        startActivity(i)
    }

   /* override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }*/
}