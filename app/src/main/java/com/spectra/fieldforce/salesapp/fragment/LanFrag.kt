package com.spectra.fieldforce.salesapp.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import android.content.SharedPreferences
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import com.spectra.fieldforce.R
import com.spectra.fieldforce.api.ApiClient
import com.spectra.fieldforce.api.ApiInterface
import com.spectra.fieldforce.databinding.CreateLanBinding
import com.spectra.fieldforce.salesapp.activity.SiteOpportunityAct
import com.spectra.fieldforce.salesapp.model.CreateLanReq
import com.spectra.fieldforce.salesapp.model.CreateLeadResponse
import com.spectra.fieldforce.salesapp.model.GetAllLanReq
import com.spectra.fieldforce.salesapp.model.GetLanRes
import com.spectra.fieldforce.utils.AppConstants
import com.spectra.fieldforce.utils.Constants
import kotlinx.android.synthetic.main.create_lan.*
import kotlinx.android.synthetic.main.update_leadtoolbar.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception


class LanFrag:Fragment(),View.OnClickListener,AdapterView.OnItemSelectedListener{

    private lateinit var binding: CreateLanBinding
    private var userName:String?=null
    private var password:String?=null
    private var strLanNum:String?=null
    private var strSiteID:String?=null
    private var strAllocationType:String?=null
    private var strAddAllocationType:String?=null
    private var strAllocationUser:String?=null
    private var strAllnUser:String?=null
    private var strLanPool:String?=null
    private var strLanAllocate:String?=null
    private var strIpBet:String?=null
    private var strStatus:String ? = null
    private var strAction:String ? = null
    private var strSolutionMode :String? = null
    private var screenStatus :String?=null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CreateLanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchToolbarLan.rl_back.setOnClickListener(this)
        searchToolbarLan.tv_lang.text = AppConstants.ADD_LAN
        searchToolbarLan.flr.visibility=View.GONE
        val bundle = arguments
        strSiteID= bundle?.getString("SiteID")
        strStatus= bundle?.getString("Status")
        strLanNum =bundle?.getString("LanNum")
        screenStatus =bundle?.getString("ScreenStatus")

        val sp1: SharedPreferences? = context?.getSharedPreferences("Login", 0)
        userName = sp1?.getString("UserName", null)
        password = sp1?.getString("Password", null)
        buttonListener()
        if(screenStatus=="1"){
            binding.addLan.visibility=View.GONE
        }
        CoroutineScope(Dispatchers.IO).launch {
            getLanDetails()
        }
        CoroutineScope(Dispatchers.IO).launch {
            itemListener()
        }

    }
    private fun buttonListener(){
        addLan.setOnClickListener {
            if (strStatus == "1") {
                strAction = Constants.CREATE_LAN
            } else if (strStatus == "2") {
                strAction = Constants.UPDATE_LAN
            }

            CoroutineScope(Dispatchers.IO).launch {
                    createLan()
            }
        }


    }

    private fun itemListener(){
        etAllocationType.setOnClickListener { spAllocationType.performClick() }
        spAllocationType.onItemSelectedListener = this
      /*  etIpAllocationUser.setOnClickListener { spAllocateUser.performClick() }
        spAllocateUser.onItemSelectedListener = this*/
        etAddAllocationType.setOnClickListener { spAddAllocationType.performClick() }
        spAddAllocationType.onItemSelectedListener = this
        etLanIPAllocationUser.setOnClickListener { spAllocationUser.performClick() }
        spAllocationUser.onItemSelectedListener = this
       /* etIpAllBetIspExistingL3.setOnClickListener { spLanIpCpe.performClick() }
        spLanIpCpe.onItemSelectedListener = this*/
    }



    private fun createLan(){
        val  lanIPAllocationToUser= strAllnUser
        val ipAllBetIspExistingL3 = strIpBet
        val additionalIpPoolAll=  strAddAllocationType
        val ipPoolBetIspExistingL3 = etPoolBetIspExistingL3.text
        val lanIpAsIspCpe=etLanIpIspCpe.text
        val lanGatewayAddress=etLanGatewayAddress.text
        val additionalIpConfigured=etAdditionalIpConfigured.text
        val ipAllocationRange=etIpAllocationRange.text
        val lanIpPool=etLanIpPool.text
        val lanIpAddress=etLanIpAddress.text
        val ipPoolGateway=etIpPoolGateway.text
        val lanIpAllocation2=etIpAllocationRange.text
        val lanIpPool2=etLanIpPool2.text
        val additionalIpGateway=etAdditionalIpGateway.text
        val lanIpPool3=etLanIpPool3.text
        val lanIpAddress2=etLanIpAddress2.text
        val ipPoolGateway2=etIpPoolGateway2.text
        val ipAllocationUser=etIpAllocationUser.text
        val customerExistingIpPool1=etCustomerExistingIpPool1.text
        val customerExistingIpPool2=etCustomerExistingIpPool2.text
        val additionalIpPoolBetIspFw=etAdditionalIpPoolBetIs.text

        createLanApi(lanIPAllocationToUser,additionalIpPoolAll,ipAllBetIspExistingL3,
            ipPoolBetIspExistingL3?.toString(), lanIpAsIspCpe?.toString(),lanGatewayAddress?.toString(),
            additionalIpConfigured?.toString(),ipAllocationRange?.toString(),lanIpPool?.toString(),lanIpAddress?.toString(),
            ipPoolGateway?.toString(),lanIpAllocation2?.toString(),lanIpPool2?.toString(),
            additionalIpGateway?.toString(),lanIpPool3?.toString(),
            lanIpAddress2?.toString(),ipPoolGateway2?.toString(),
            ipAllocationUser?.toString(),customerExistingIpPool1?.toString(),customerExistingIpPool2?.toString(),
            additionalIpPoolBetIspFw?.toString()
        )
    }

    private fun createLanApi(
    lanIPAllocationToUser:String?,additionalIpPoolAll: String?,ipAllBetIspExistingL3: String?,
    ipPoolBetIspExistingL3: String?, lanIpAsIspCpe: String?,lanGatewayAddress: String?,
    additionalIpConfigured: String?,ipAllocationRange: String?,lanIpPool:String?,lanIpAddress: String?,
    ipPoolGateway: String?,lanIpAllocation2: String?,lanIpPool2: String?,additionalIpGateway: String?,
    lanIpPool3:String?,
    lanIpAddress2: String?,ipPoolGateway2: String?,
    ipAllocationUser: String?,customerExistingIpPool1: String?,customerExistingIpPool2: String?,
    additionalIpPoolBetIspFw: String?

    ) {
        val createLanReq = CreateLanReq(Constants.CREATE_LAN,Constants.AUTH_KEY,lanIPAllocationToUser,
            ipAllBetIspExistingL3,  additionalIpPoolAll, ipPoolBetIspExistingL3,
            lanIpAsIspCpe,lanGatewayAddress,additionalIpConfigured,
            ipAllocationRange,lanIpPool,lanIpAddress,ipPoolGateway,lanIpAllocation2,lanIpPool2,
            additionalIpGateway,lanIpPool3,lanIpAddress2,ipPoolGateway2,ipAllocationUser,
            customerExistingIpPool1,customerExistingIpPool2,additionalIpPoolBetIspFw,password,
            strSiteID,userName,strLanNum)

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.createLan(createLanReq)
        call.enqueue(object : Callback<CreateLeadResponse?> {
            override fun onResponse(call: Call<CreateLeadResponse?>, response: Response<CreateLeadResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        if(response.body()?.StatusCode=="200"){
                            Toast.makeText(context, response.body()?.Response?.Message, Toast.LENGTH_LONG).show()
                            next()
                        }else  if(response.body()?.StatusCode=="201"){
                            Toast.makeText(context, response.body()?.Response?.Message, Toast.LENGTH_LONG).show()
                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<CreateLeadResponse?>, t: Throwable) {
                Log.e("RetroError", t.toString())
            }
        })
    }

    private fun  getLanDetails () {
        //inProgress()
        val getAllLan = GetAllLanReq(Constants.GET_LAN, Constants.AUTH_KEY,strLanNum,password,strSiteID,userName)
        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getAllLan(getAllLan)
        call.enqueue(object : Callback<GetLanRes?> {
            override fun onResponse(call: Call<GetLanRes?>, response: Response<GetLanRes?>) {
                // OutProgress()
                if (response.isSuccessful && response.body() != null) {
                    try {
                        if(response.body()?.Response?.StatusCode==200) {
                            binding.lanData = response.body()?.Response?.Data?.get(0)
                            strAllocationType = response.body()?.Response?.Data?.get(0)?.LanIPAllocationtoUser
                            strAddAllocationType = response.body()?.Response?.Data?.get(0)?.AdditionalIpPoolallocation
                            strLanAllocate = response.body()?.Response?.Data?.get(0)?.LanIpPool
                            strAllnUser = response.body()?.Response?.Data?.get(0)?.LanIPAllocationtoUser
                            strAllocationUser = response.body()?.Response?.Data?.get(0)?.IpAllocationUser
                            strSolutionMode = response.body()?.Response?.Data?.get(0)?.SolutionDeploymentMode
                            strLanNum = response.body()?.Response?.Data?.get(0)?.LanNo

                            when {
                                strSolutionMode.equals("RL") -> {
                                    allocationUser.visibility=View.VISIBLE
                                    lanPool.visibility=View.VISIBLE
                                    LanIpAddress.visibility=View.VISIBLE
                                    poolGateway.visibility=View.VISIBLE
                                    lanpool2.visibility=View.VISIBLE
                                }
                                strSolutionMode.equals("RR") -> {
                                    PoolBetIspExistingL3.visibility=View.VISIBLE
                                    addAllocationType.visibility=View.VISIBLE
                                    LanIpIspCpe.visibility=View.VISIBLE
                                    LanGatewayAddress.visibility=View.VISIBLE
                                  //  lanPool.visibility=View.VISIBLE
                                  //  LanIpAddress.visibility=View.VISIBLE
                                  //  poolGateway.visibility=View.VISIBLE
                                    CustomerExistingIpPool1.visibility=View.VISIBLE
                                    CtExistingIpPool2.visibility=View.VISIBLE
                                }
                                strSolutionMode.equals("WL") -> {
                                    allocationUser.visibility=View.VISIBLE
                                    lanPool.visibility=View.VISIBLE
                                    LanIpAddress.visibility=View.VISIBLE
                                    poolGateway.visibility=View.VISIBLE
                                }
                                strSolutionMode.equals("WR") -> {
                                    allocationUser.visibility=View.VISIBLE
                                    allocationType.visibility=View.VISIBLE
                                    CustomerExistingIpPool1.visibility=View.VISIBLE
                                    AdditionalIpConfigured.visibility=View.VISIBLE
                                    lanPool.visibility=View.VISIBLE
                                    LanIpAddress.visibility=View.VISIBLE
                                    poolGateway.visibility=View.VISIBLE
                                    addGateway.visibility=View.VISIBLE
                                    AdditionalIpPoolBetIs.visibility=View.VISIBLE
                                }
                            }
                            var allocatePosition = 0
                            resources.getStringArray(R.array.list_of_prefferedvalue).forEachIndexed { index, s ->
                                if (s == strAllocationType) allocatePosition = index
                            }
                            val categoryAdapter = activity?.let {
                                ArrayAdapter(it,
                                    android.R.layout.simple_spinner_item,
                                    resources.getStringArray(R.array.listAllocation)
                                )
                            }
                            categoryAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            spAllocationType.adapter = categoryAdapter
                            spAllocationType.setSelection(allocatePosition)
                            categoryAdapter?.notifyDataSetChanged()

                            var addAdditional =0
                            resources.getStringArray(R.array.listCustomerServiceVal).forEachIndexed { index, s ->
                                if (s == strAddAllocationType) addAdditional = index
                            }
                            val  addAllocationTypeAdapter
                                    = activity?.let {
                                ArrayAdapter(it,
                                    android.R.layout.simple_spinner_item,
                                    resources.getStringArray(R.array.listAllocation)
                                )
                            }
                            addAllocationTypeAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            spAddAllocationType.adapter = addAllocationTypeAdapter
                            spAddAllocationType.setSelection(addAdditional)
                            addAllocationTypeAdapter?.notifyDataSetChanged()

                          /*  var userPosition = 0
                            resources.getStringArray(R.array.siteTypeVal).forEachIndexed { index, s ->
                                if (s == strAllocationUser) userPosition = index
                            }
                            val userAdapter = activity?.let {
                                ArrayAdapter(it,
                                    android.R.layout.simple_spinner_item,
                                    resources.getStringArray(R.array.listAllocation)
                                )
                            }
                            userAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            spAllocateUser.adapter = userAdapter
                            spAllocateUser.setSelection(userPosition)
                            userAdapter?.notifyDataSetChanged()*/

                            var allUserPosition = 0
                            resources.getStringArray(R.array.siteTypeVal).forEachIndexed { index, s ->
                                if (s == strAllnUser) allUserPosition = index
                            }
                            val allUserAdapter = activity?.let {
                                ArrayAdapter(it,
                                    android.R.layout.simple_spinner_item,
                                    resources.getStringArray(R.array.listAllocation)
                                )
                            }
                            allUserAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            spAllocationUser.adapter = allUserAdapter
                            spAllocationUser.setSelection(allUserPosition)
                            allUserAdapter?.notifyDataSetChanged()

                           /* var lanPosition = 0
                            resources.getStringArray(R.array.siteTypeVal).forEachIndexed { index, s ->
                                if (s == strLanAllocate) lanPosition = index
                            }
                            val lanAdapter = activity?.let {
                                ArrayAdapter(it,
                                    android.R.layout.simple_spinner_item,
                                    resources.getStringArray(R.array.listAllocation)
                                )
                            }
                            lanAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            spLanIpPool.adapter = lanAdapter
                            spLanIpPool.setSelection(lanPosition)
                            lanAdapter?.notifyDataSetChanged()*/

                            var lanIpPosition = 0
                            resources.getStringArray(R.array.siteTypeVal).forEachIndexed { index, s ->
                                if (s == strIpBet) lanIpPosition = index
                            }
                            val lanIpAdapter = activity?.let {
                                ArrayAdapter(it,
                                    android.R.layout.simple_spinner_item,
                                    resources.getStringArray(R.array.listAllocation)
                                )
                            }
                            lanIpAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            spLanIpCpe.adapter = lanIpAdapter
                            spLanIpCpe.setSelection(lanIpPosition)
                            lanIpAdapter?.notifyDataSetChanged()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<GetLanRes?>, t: Throwable) {
                Log.e("RetroError", t.toString())
            }
        })
    }



    override fun onClick(p0: View?) {
       next()
    }


    private fun next(){
        val i = Intent(activity, SiteOpportunityAct::class.java)
        i.putExtra("SiteID", strSiteID)
        startActivity(i)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (parent?.id) {
            R.id.spAllocationType -> {
                etAllocationType.setText(resources.getStringArray(R.array.listAllocation)[position])
                strAddAllocationType =  resources.getStringArray(R.array.list_of_prefferedvalue)[position]
                allocationType.visibility=View.VISIBLE
                if(strAllocationType=="111260000" && strSolutionMode=="WR"){
                    allocationUser.visibility=View.VISIBLE
                    allocationType.visibility=View.VISIBLE
                    AdditionalIpConfigured.visibility=View.VISIBLE
                    lanPool.visibility=View.VISIBLE
                    CustomerExistingIpPool1.visibility=View.VISIBLE
                    LanIpAddress.visibility=View.VISIBLE
                    poolGateway.visibility=View.VISIBLE
                    addGateway.visibility=View.VISIBLE
                    AdditionalIpPoolBetIs.visibility=View.VISIBLE
                }else if(strAllocationType=="111260001" && strSolutionMode=="WR"){
                    allocationUser.visibility=View.VISIBLE
                    allocationType.visibility=View.VISIBLE
                    CustomerExistingIpPool1.visibility=View.VISIBLE
                    AdditionalIpConfigured.visibility=View.GONE
                    lanPool.visibility=View.VISIBLE
                    LanIpAddress.visibility=View.VISIBLE
                    poolGateway.visibility=View.VISIBLE
                    addGateway.visibility=View.GONE
                    AdditionalIpPoolBetIs.visibility=View.VISIBLE
                }
            }
           /* R.id.spAllocateUser ->{
                etIpAllocationUser.setText(resources.getStringArray(R.array.listAllocation)[position])
                strAllocationUser =  resources.getStringArray(R.array.siteTypeVal)[position]
            }*/
            R.id.spAddAllocationType ->{
                etAddAllocationType.setText(resources.getStringArray(R.array.listAllocation)[position])
                strIpBet =  resources.getStringArray(R.array.listCustomerServiceVal)[position]
                if( strSolutionMode=="RR"){
                    PoolBetIspExistingL3.visibility=View.VISIBLE
                    addAllocationType.visibility=View.VISIBLE
                    LanIpIspCpe.visibility=View.VISIBLE
                    LanGatewayAddress.visibility=View.VISIBLE
                  //  lanPool.visibility=View.VISIBLE
                  //  LanIpAddress.visibility=View.VISIBLE
                 //   poolGateway.visibility=View.VISIBLE
                    CustomerExistingIpPool1.visibility=View.VISIBLE
                    CtExistingIpPool2.visibility=View.VISIBLE
                }
            }
            R.id.spAllocationUser -> {
                etLanIPAllocationUser.setText(resources.getStringArray(R.array.listAllocation)[position])
                strAllnUser =  resources.getStringArray(R.array.siteTypeVal)[position]
                allocationUser.visibility=View.VISIBLE
                if(strAllnUser=="122050001"&& strSolutionMode=="RL"){
                    lanpool2.visibility=View.VISIBLE
                    AllRange.visibility=View.VISIBLE
                    LanIpAddress.visibility=View.VISIBLE
                    poolGateway.visibility=View.VISIBLE
                }else if(strAllnUser=="122050000"&& strSolutionMode=="RL"){
                    lanpool2.visibility=View.VISIBLE
                    LanIpAddress.visibility=View.VISIBLE
                    poolGateway.visibility=View.VISIBLE
                    AllRange.visibility=View.GONE
                }else if(strAllnUser=="122050000"&& strSolutionMode=="WL"){
                    allocationUser.visibility=View.VISIBLE
                    lanPool.visibility=View.VISIBLE
                    LanIpAddress.visibility=View.VISIBLE
                    poolGateway.visibility=View.VISIBLE
                    lanpool2.visibility=View.VISIBLE
                    AllRange.visibility=View.GONE
                }else if(strAllnUser=="122050001"&& strSolutionMode=="WL"){
                    allocationUser.visibility=View.VISIBLE
                    AllRange.visibility=View.VISIBLE
                    lanpool2.visibility=View.VISIBLE
                    LanIpAddress.visibility=View.VISIBLE
                    poolGateway.visibility=View.VISIBLE
                }
            }
           /* R.id.spLanIpCpe ->{
                etIpAllBetIspExistingL3.setText(resources.getStringArray(R.array.listAllocation)[position])
                strIpBet =  resources.getStringArray(R.array.siteTypeVal)[position]
            }*/

        }

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

}