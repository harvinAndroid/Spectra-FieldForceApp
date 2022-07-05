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

import com.spectra.fieldforce.databinding.CreateWanBinding
import com.spectra.fieldforce.salesapp.activity.SiteOpportunityAct
import com.spectra.fieldforce.salesapp.model.*
import com.spectra.fieldforce.utils.AppConstants
import com.spectra.fieldforce.utils.Constants
import kotlinx.android.synthetic.main.create_wan.*
import kotlinx.android.synthetic.main.update_leadtoolbar.view.*

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception


class WanFrag:Fragment(),View.OnClickListener,AdapterView.OnItemSelectedListener{

    private lateinit var binding: CreateWanBinding
    private var userName:String?=null
    private var password:String?=null
    private var strWanNum:String?=null
    private var strSiteID:String?=null
    private var strAllocationType:String?=null
    private var strAllocationUser:String?=null
    private var strLanPool:String?=null
    private var strLanAllocate:String?=null
    private var strIpBet:String?=null
    private var strProvider2:String?=null
    private var strStatus:String ? = null
    private var strAction:String ? = null
    private var strSolutionMode:String?=null
    private var screenStatus:String?=null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CreateWanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
          searchToolbarWan.rl_back.setOnClickListener(this)
        searchToolbarWan.flr.visibility=View.GONE
        searchToolbarWan.tv_lang.text = AppConstants.ADD_WAN
        val bundle = arguments
        strSiteID= bundle?.getString("SiteID")
        strStatus= bundle?.getString("Status")
        strWanNum= bundle?.getString("WanNo")
        screenStatus =bundle?.getString("ScreenStatus")
        val sp1: SharedPreferences? = context?.getSharedPreferences("Login", 0)
        userName = sp1?.getString("UserName", null)
        password = sp1?.getString("Password", null)
        buttonListener()

        if(screenStatus=="1"){
            binding.addLan.visibility=View.GONE
        }
        CoroutineScope(Dispatchers.IO).launch {
           getWanDetails()
        }
        CoroutineScope(Dispatchers.IO).launch {
            itemListener()
        }

    }
    private fun buttonListener(){
        addLan.setOnClickListener {
             if (strStatus == "1") {
                 strAction = Constants.CREATE_WAN
             } else if (strStatus == "2") {
                 strAction = Constants.UPDATE_WAN
             }
            CoroutineScope(Dispatchers.IO).launch {
                createWan()
            }
        }
    }

    private fun itemListener(){
        etWanIPAllocationType.setOnClickListener { spWanAllocationUser.performClick() }
        spWanAllocationUser.onItemSelectedListener = this

        etAdditionalIPPoolAllocationType.setOnClickListener { spAdditionalIPPoolAllocationType.performClick() }
        spAdditionalIPPoolAllocationType.onItemSelectedListener = this

        etAllocationTypeBetCPEExisting.setOnClickListener { spCPEExisting.performClick() }
        spCPEExisting.onItemSelectedListener = this

        etWanType.setOnClickListener { spwANType.performClick() }
        spwANType.onItemSelectedListener = this

        etwANBBProvider1D.setOnClickListener { spwANBBProvider1D.performClick() }
        spwANBBProvider1D.onItemSelectedListener = this

        etWanBBProvider2.setOnClickListener { spWanBBProvider2.performClick() }
        spWanBBProvider2.onItemSelectedListener = this



    }



    private fun createWan(){
        val wanIPAllocationType= strAllocationType
        val additionalIPPoolAllocationType =strAllocationUser
        val wANType=strLanAllocate
        val additionalIPGatewayCPE=etAdditionalIPGatewayCPE.text
        val wANBandMbps=etwANBandWidthMbps.text
        val wANGatewaySpectraCPEMask=etWanGatewayCPEMask.text
        val allBetCPEExisting=strLanPool
        val iPConfigCPE=etIPConfiguredCPE.text
        val wANCircuitID=etwANCircuitID.text
        val wANIPAddressCPEMask=etWanIPAddressMask.text
        val wANBBProvider1D=strIpBet
        val additionalIPConfigCPE=etAdditionalIPConfigCPE.text
        val wANBBProvider2=strProvider2
        val additionalIPPoolCPEExist=etAddIPPoolBetCPEExisting.text
        val gatewayAddressISPCPE=etGatewayAddressISPCPE.text
        val iPPoolISPCPEExistingL3Device=etIPPoolBetISPCPEExistL3.text
        createWanApi(wanIPAllocationType,additionalIPPoolAllocationType,wANType,additionalIPGatewayCPE?.toString(),
            wANBandMbps?.toString(), wANGatewaySpectraCPEMask?.toString(),allBetCPEExisting,
            iPConfigCPE?.toString(),wANCircuitID?.toString(),wANIPAddressCPEMask?.toString(),wANBBProvider1D,additionalIPConfigCPE?.toString(),
            wANBBProvider2,additionalIPPoolCPEExist?.toString(),gatewayAddressISPCPE?.toString(),
            iPPoolISPCPEExistingL3Device?.toString()
        )
    }

    private fun createWanApi(
        wanIPAllocationType: String?,
        additionalIPPoolAllocationType: String?,
        wANType: String?,
        additionalIPGatewayCPE: String?,
        wANBandMbps: String?,
        wANGatewaySpectraCPEMask: String?,
        allBetCPEExisting: String?,
        iPConfigCPE: String?,
        wANCircuitID: String?,
        wANIPAddressCPEMask: String?,
        wANBBProvider1D: String?,
        additionalIPConfigCPE: String?,
        wANBBProvider2: String?,
        additionalIPPoolCPEExist: String?,
        gatewayAddressISPCPE: String?,
        iPPoolISPCPEExistingL3Device: String?
    ) {
        val createWanReq = CreateWanReq(strAction,Constants.AUTH_KEY,additionalIPGatewayCPE ,additionalIPPoolAllocationType,
            additionalIPPoolCPEExist,additionalIPConfigCPE/*iPConfigCPE*/,allBetCPEExisting,gatewayAddressISPCPE,
            iPPoolISPCPEExistingL3Device,iPConfigCPE /*additionalIPConfigCPE*/,password,strSiteID,userName,
            wANBBProvider1D,wANBBProvider2,
            wANBandMbps,wANCircuitID,wANGatewaySpectraCPEMask,wANIPAddressCPEMask,
            wANType,wanIPAllocationType,strWanNum)

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.createWan(createWanReq)
        call.enqueue(object : Callback<CreateLeadResponse?> {
            override fun onResponse(call: Call<CreateLeadResponse?>, response: Response<CreateLeadResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        if(response.body()?.StatusCode=="200"){
                            Toast.makeText(
                                context, response.body()?.Response?.Message,
                                Toast.LENGTH_LONG
                            ).show()
                            next()
                        }else if(response.body()?.StatusCode=="201") {
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

    private fun  getWanDetails () {
        //inProgress()
        val getWanReq = GetWanReq(Constants.GET_WAN, Constants.AUTH_KEY,password,strSiteID,userName,strWanNum)

       val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getAllWan(getWanReq)
        call.enqueue(object : Callback<GetWanRes?> {
            override fun onResponse(call: Call<GetWanRes?>, response: Response<GetWanRes?>) {
                // OutProgress()
                if (response.isSuccessful && response.body() != null) {
                    try {
                        if(response.body()?.Response?.StatusCode==200) {
                            binding.wanData = response.body()?.Response?.Data?.get(0)
                            strAllocationType = response.body()?.Response?.Data?.get(0)?.WanIPAllocationtype
                            strIpBet = response.body()?.Response?.Data?.get(0)?.WANBBProvider1D
                            strAllocationUser = response.body()?.Response?.Data?.get(0)?.AdditionalIPPoolAllocationType
                            strLanAllocate = response.body()?.Response?.Data?.get(0)?.WANType
                            strProvider2 = response.body()?.Response?.Data?.get(0)?.WANBBProvider2
                            strSolutionMode = response.body()?.Response?.Data?.get(0)?.SolutionDeploymentMode
                            strWanNum = response.body()?.Response?.Data?.get(0)?.WanNo

                            when (strSolutionMode) {
                                "RL" -> {
                                    AdditionalIPPoolAllocationType.visibility=View.VISIBLE
                                    AdditionalIPGatewayCPE.visibility=View.VISIBLE
                                    AdditionalIPConfigCPE.visibility=View.VISIBLE
                                    AddIPPoolBetCPEExisting.visibility=View.VISIBLE
                                }
                                "RR" -> {
                                    CPEExisting.visibility=View.VISIBLE
                                    IPConfiguredCPE.visibility=View.VISIBLE
                                    GatewayAddressISPCPE.visibility=View.VISIBLE
                                    IPPoolBetISPCPEExistL3.visibility=View.VISIBLE
                                }
                                "WL" -> {
                                    WanAllocationUser.visibility=View.VISIBLE
                                    WanIPAddressMask.visibility=View.VISIBLE
                                    wanType.visibility=View.VISIBLE
                                    wanBandWidthMbps.visibility=View.VISIBLE
                                    WanGatewayCPEMask.visibility=View.VISIBLE
                                    wanCircuitID.visibility=View.VISIBLE
                                    wanBBProvider1D.visibility=View.VISIBLE
                                }
                                "WR" -> {
                                    WanAllocationUser.visibility=View.VISIBLE
                                    WanIPAddressMask.visibility=View.VISIBLE
                                    wanType.visibility=View.VISIBLE
                                    wanBandWidthMbps.visibility=View.VISIBLE
                                    WanGatewayCPEMask.visibility=View.VISIBLE
                                    wanBBProvider1D.visibility=View.VISIBLE
                                }
                            }

                            strLanPool =  response.body()?.Response?.Data?.get(0)?.AllocationtypebetSpectCPEandExistin
                            var allocatePosition = 0
                            resources.getStringArray(R.array.listCustomerServiceVal).forEachIndexed { index, s ->
                                if (s == strAllocationType) allocatePosition = index
                            }
                            val categoryAdapter = activity?.let {
                                ArrayAdapter(it, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.listAllocation)) }
                            categoryAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            spWanAllocationUser.adapter = categoryAdapter
                            spWanAllocationUser.setSelection(allocatePosition)
                            categoryAdapter?.notifyDataSetChanged()

                            var userPosition = 0
                            resources.getStringArray(R.array.list_of_prefferedvalue).forEachIndexed { index, s ->
                                if (s == strAllocationUser) userPosition = index
                            }
                            val userAdapter = activity?.let {
                                ArrayAdapter(it, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.listAllocation))
                            }
                            userAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            spAdditionalIPPoolAllocationType.adapter = userAdapter
                            spAdditionalIPPoolAllocationType.setSelection(userPosition)
                            userAdapter?.notifyDataSetChanged()

                            var allUserPosition = 0
                            resources.getStringArray(R.array.wanTypeVal).forEachIndexed { index, s ->
                                if (s == strLanAllocate) allUserPosition = index
                            }
                            val allUserAdapter = activity?.let {
                                ArrayAdapter(it, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.wanType))
                            }
                            allUserAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            spwANType.adapter = allUserAdapter
                            spwANType.setSelection(allUserPosition)
                            allUserAdapter?.notifyDataSetChanged()

                            var lanPosition = 0
                            resources.getStringArray(R.array.list_of_prefferedvalue).forEachIndexed { index, s ->
                                if (s == strLanPool) lanPosition = index
                            }
                            val lanAdapter = activity?.let {
                                ArrayAdapter(it, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.listAllocation))
                            }
                            lanAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            spCPEExisting.adapter = lanAdapter
                            spCPEExisting.setSelection(lanPosition)
                            lanAdapter?.notifyDataSetChanged()


                            var lanIpPosition = 0
                            resources.getStringArray(R.array.ext_serv_one_values).forEachIndexed { index, s ->
                                if (s == strIpBet) lanIpPosition = index
                            }
                            val lanIpAdapter = activity?.let {
                                ArrayAdapter(it, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.ext_serv_one))
                            }
                            lanIpAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            spwANBBProvider1D.adapter = lanIpAdapter
                            spwANBBProvider1D.setSelection(lanIpPosition)
                            lanIpAdapter?.notifyDataSetChanged()

                            var provider2Position = 0
                            resources.getStringArray(R.array.ext_serv_one_values).forEachIndexed { index, s ->
                                if (s == strProvider2) provider2Position = index
                            }
                            val provider2Adapter = activity?.let {
                                ArrayAdapter(it, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.ext_serv_one))
                            }
                            provider2Adapter?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            spWanBBProvider2.adapter = provider2Adapter
                            spWanBBProvider2.setSelection(provider2Position)
                            provider2Adapter?.notifyDataSetChanged()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<GetWanRes?>, t: Throwable) {
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
            R.id.spWanAllocationUser -> {
                etWanIPAllocationType.setText(resources.getStringArray(R.array.listAllocation)[position])
                strAllocationType =  resources.getStringArray(R.array.listCustomerServiceVal)[position]
                if(strAllocationType=="122050000"&& strSolutionMode=="WR"){
                    WanAllocationUser.visibility=View.VISIBLE
                    WanIPAddressMask.visibility=View.VISIBLE
                    wanType.visibility=View.VISIBLE
                    wanBandWidthMbps.visibility=View.VISIBLE
                    WanGatewayCPEMask.visibility=View.VISIBLE
                    wanBBProvider1D.visibility=View.VISIBLE
                }else if(strAllocationType=="122050001"&& strSolutionMode=="WR"){
                    WanAllocationUser.visibility=View.VISIBLE
                    wanType.visibility=View.VISIBLE
                    wanBandWidthMbps.visibility=View.VISIBLE
                    wanBBProvider1D.visibility=View.VISIBLE
                    WanIPAddressMask.visibility=View.GONE
                    WanGatewayCPEMask.visibility=View.GONE
                } else if(strAllocationType=="122050000"&& strSolutionMode=="WL"){
                    WanAllocationUser.visibility=View.VISIBLE
                    WanIPAddressMask.visibility=View.VISIBLE
                    wanType.visibility=View.VISIBLE
                    wanBandWidthMbps.visibility=View.VISIBLE
                    WanGatewayCPEMask.visibility=View.VISIBLE
                    wanCircuitID.visibility=View.VISIBLE
                    wanBBProvider1D.visibility=View.VISIBLE
                }else if(strAllocationType=="122050001"&& strSolutionMode=="WL"){
                    WanAllocationUser.visibility=View.VISIBLE
                    wanType.visibility=View.VISIBLE
                    wanBandWidthMbps.visibility=View.VISIBLE
                    wanBBProvider1D.visibility=View.VISIBLE
                    WanIPAddressMask.visibility=View.GONE
                    WanGatewayCPEMask.visibility=View.GONE
                    wanCircuitID.visibility=View.GONE
                }
            }
            R.id.spAdditionalIPPoolAllocationType ->{
                etAdditionalIPPoolAllocationType.setText(resources.getStringArray(R.array.listAllocation)[position])
                strAllocationUser =  resources.getStringArray(R.array.list_of_prefferedvalue)[position]
                if(strAllocationUser=="111260000"&& strSolutionMode=="RL"){
                    AdditionalIPPoolAllocationType.visibility=View.VISIBLE
                    AdditionalIPGatewayCPE.visibility=View.VISIBLE
                    AdditionalIPConfigCPE.visibility=View.VISIBLE
                    AddIPPoolBetCPEExisting.visibility=View.VISIBLE
                }else  if(strAllocationUser=="111260001"&& strSolutionMode=="RL"){
                    AdditionalIPPoolAllocationType.visibility=View.VISIBLE
                    AddIPPoolBetCPEExisting.visibility=View.VISIBLE
                    AdditionalIPGatewayCPE.visibility=View.GONE
                    AdditionalIPConfigCPE.visibility=View.GONE
                }
            }
            R.id.spwANType -> {
                etWanType.setText(resources.getStringArray(R.array.wanType)[position])
                strLanAllocate =  resources.getStringArray(R.array.wanTypeVal)[position]
            }
            R.id.spCPEExisting ->{
                etAllocationTypeBetCPEExisting.setText(resources.getStringArray(R.array.listAllocation)[position])
                strLanPool =  resources.getStringArray(R.array.list_of_prefferedvalue)[position]
                if(strLanPool=="111260000"&& strSolutionMode=="RR"){
                    CPEExisting.visibility=View.VISIBLE
                    IPConfiguredCPE.visibility=View.VISIBLE
                    GatewayAddressISPCPE.visibility=View.VISIBLE
                    IPPoolBetISPCPEExistL3.visibility=View.VISIBLE
                }else  if(strLanPool=="111260001"&& strSolutionMode=="RR"){
                    CPEExisting.visibility=View.VISIBLE
                    IPPoolBetISPCPEExistL3.visibility=View.VISIBLE
                    IPConfiguredCPE.visibility=View.GONE
                    GatewayAddressISPCPE.visibility=View.GONE
                }
            }
            R.id.spwANBBProvider1D ->{
                etwANBBProvider1D.setText(resources.getStringArray(R.array.ext_serv_one)[position])
                strIpBet =  resources.getStringArray(R.array.ext_serv_one_values)[position]
            }
            R.id.spWanBBProvider2 ->{
                etWanBBProvider2.setText(resources.getStringArray(R.array.ext_serv_one)[position])
                strProvider2 =  resources.getStringArray(R.array.ext_serv_one_values)[position]
            }


        }

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

}