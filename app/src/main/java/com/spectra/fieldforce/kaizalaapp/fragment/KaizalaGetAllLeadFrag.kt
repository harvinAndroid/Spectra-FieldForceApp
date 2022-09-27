package com.spectra.fieldforce.kaizalaapp.fragment

import KaiGetAllLeadAdapter
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
import com.spectra.fieldforce.salesapp.model.AllLeadData
import com.spectra.fieldforce.salesapp.model.GetAllLeadRequest
import com.spectra.fieldforce.salesapp.model.GetAllLeadResponse
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

class KaizalaGetAllLeadFrag : Fragment(),View.OnClickListener {
    private lateinit var  leadContactInfoBinding: FragmentAllLeadListBinding
    private var allLead: ArrayList<AllLeadData>? = null
    private var inAnimation: AlphaAnimation? = null
    private var outAnimation: AlphaAnimation? = null
    private var UName: String? = null
    private var password : String? = null
    private var strSearch: String? = null
    private var strtag : String? = null

    companion object {
        fun newInstance(): KaizalaGetAllLeadFrag {
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
        val sp1: SharedPreferences? = context?.getSharedPreferences("Login", 0)
        UName = sp1?.getString("UserName", null)
        password = sp1?.getString("Password", null)
        strSearch = sp1?.getString("STATUS",null)
        strtag = sp1?.getString("TAG",null)
        excuteTask()
        excuteSearch()

        fab_create_lead.setOnClickListener {
            try {
                val fragmentB = KaiLeadFragment()
                parentFragmentManager.beginTransaction()
                        .replace(R.id.frag_container, fragmentB, "fragmnetId").commit();
            }catch (e:Exception){

            }
        }
     }

    private fun excuteTask(){
            CoroutineScope(Dispatchers.IO).launch {
                getAllLeadList("")
            }
        CoroutineScope(Dispatchers.IO).launch {
            tv_count.setOnClickListener {
                val search = tv_search.text.toString()
                getAllLeadList(search)
            }
        }
    }
   private fun excuteSearch(){
        CoroutineScope(Dispatchers.IO).launch {
            tv_search.addTextChangedListener(object : TextWatcher {

                override fun afterTextChanged(s: Editable) {
                    val search = tv_search.text.toString()
                    if (search.isBlank()) {
                        tv_msg.visibility = View.GONE
                        getAllLeadList("")
                    }
                }

                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

                }
            })
        }
    }



    fun getAllLeadList(srch: String) {
        try {
            inAnimation = AlphaAnimation(0f, 1f)
            inAnimation?.duration = 200
            leadContactInfoBinding.progressLayout.progressOverlay.animation = inAnimation
            leadContactInfoBinding.progressLayout.progressOverlay.visibility = View.VISIBLE

            val getAllLeadRequest = GetAllLeadRequest(Constants.GET_AllLEADS, Constants.AUTH_KEY, "All",
                password, UName, srch,"","",KaizalaAppConstant.HOME)
            val apiService = ApiClient.getClient().create(ApiInterface::class.java)
            val call = apiService.getAllLead(getAllLeadRequest)
            call.enqueue(object : Callback<GetAllLeadResponse?> {
                override fun onResponse(
                    call: Call<GetAllLeadResponse?>,
                    response: Response<GetAllLeadResponse?>
                ) {
                    outAnimation = AlphaAnimation(1f, 0f)
                    inAnimation?.duration = 200
                    leadContactInfoBinding.progressLayout.progressOverlay.animation = outAnimation
                    leadContactInfoBinding.progressLayout.progressOverlay.visibility = View.GONE

                    if (response.isSuccessful && response.body() != null) {
                        try {
                            val msg = response.body()?.Response?.Message
                            if (response.body()?.Response?.StatusCode == 200) {
                                allLead = response.body()?.Response?.Data
                                setAdapter(allLead, context)
                            } else {
                                Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
                                tv_msg.visibility = View.GONE
                                allLead?.clear()
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
        }catch (E: Exception){
            E.printStackTrace()
        }
    }



    private fun setAdapter(allLead: ArrayList<AllLeadData>?, context: Context?) {
        rv_lead_list?.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = allLead?.let { KaiGetAllLeadAdapter(it,context) }
        }
    }

    override fun onClick(p0: View?) {
        val i = Intent(activity, KaizalaDashboard::class.java)
        startActivity(i)
    }

}