package com.spectra.fieldforce.kaizalaapp.fragment


import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.spectra.fieldforce.R
import com.spectra.fieldforce.api.ApiClient
import com.spectra.fieldforce.api.ApiClientKaizala
import com.spectra.fieldforce.api.ApiInterface
import com.spectra.fieldforce.databinding.KaiCreateLeadBinding
import com.spectra.fieldforce.kaizalaapp.activity.KaizalaLeadTabAct
import com.spectra.fieldforce.kaizalaapp.model.*
import com.spectra.fieldforce.salesapp.model.*
import com.spectra.fieldforce.utils.AppConstants
import com.spectra.fieldforce.utils.Constants
import com.spectra.fieldforce.utils.KaizalaAppConstant
import com.spectra.fieldforce.utils.SalesAppConstants
import kotlinx.android.synthetic.main.kai_create_lead.*
import kotlinx.android.synthetic.main.update_leadtoolbar.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import java.util.regex.Pattern

class KaiLeadFragment:Fragment(), View.OnClickListener{

    private lateinit var  binding: KaiCreateLeadBinding
    private lateinit var userName: String
    private lateinit var password:String
    private lateinit var inAnimation: AlphaAnimation
    private lateinit var outAnimation: AlphaAnimation
    private lateinit var source : MutableList<String>
    private lateinit var sourceList: MutableList<SrcData>
    private lateinit var cityList : MutableList<KCityResponse>
    private lateinit var Installcity : MutableList<String>
    private lateinit var InstallcityCode : MutableList<String>
    private lateinit var areaList : MutableList<KAreaResponse>
    private lateinit var installArea : MutableList<String>
    private lateinit var installAreaId : MutableList<String>
    private lateinit var societyList : MutableList<SocietyResponse>
    private lateinit var installSociety : MutableList<String>
    private lateinit var installSocietyId : MutableList<String>
    private var strCompanyId : String? = null
    private lateinit var company : MutableList<String>
    private lateinit var companyId : MutableList<String>
    private lateinit var companyList: MutableList<KCompanyResponse>
    private lateinit var group : MutableList<String>
    private lateinit var groupId : MutableList<String>
    private lateinit var relation : MutableList<String>
    private lateinit var relationId : MutableList<String>
    private lateinit var relationList: MutableList<KRelationResponse>
    private var strState :String? = null
    private var strArea:String? =null
    private  var strCity :String?=null
    private  var strSociety :String?=null
    private var strSubBusiness :String ?=null
    private var strSalutationId :String ? =null
    private var strSource :String? = null
    private var strRelation :String ? = null
    private var strGroupId : String? =null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = KaiCreateLeadBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_createNewLead.rl_back.setOnClickListener(this)
        btn_createNewLead.tv_lang.text=AppConstants.LEAD_DETAILS
        btn_createNewLead.flr.visibility=View.GONE

        val sp1: SharedPreferences? = context?.getSharedPreferences("Login", 0)
        userName = sp1?.getString(AppConstants.USERNAME, null).toString()
        password = sp1?.getString(AppConstants.PASSWORD, null).toString()

     //   itemListener()


       getSource(KaizalaAppConstant.KAIZALA)
       listener()
       getCompany()
    }
    private fun validEmail(email: String): Boolean {
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }



    private fun listener(){

        binding.btnLeadSubmit.setOnClickListener {
            val strSubBus = binding.spinnerSubBusinessSeg.selectedItem.toString()
            val strFirstName = EditText_PDFirstName.text.toString()
            val strLastName = EditText_PDLastName.text.toString()
            val strEmail = EditText_PDEmail.text.toString()
            val strMobile = EditText_PDMobileNumber.text.toString()
            val strRemark = EditText_PDRemarks.text.toString()
            val block = EditText_PDBlock.text.toString()
            val plot = EditText_PDHouse.text.toString()
            val floor = EditText_PDFloor.text.toString()
            val pincode = editText_PDPostal.text.toString()
            strSource =  binding.SpPDSource.selectedItem.toString()
            val company = strCompanyId.toString()
            val group = strGroupId.toString()
            val rela = strRelation.toString()
            val salutation = strSalutationId.toString()
            if(strSubBus.isBlank()||strSubBus=="Select Option"){
                Toast.makeText(context, "Select Sub-Business Segment", Toast.LENGTH_LONG).show()
            }else if (company.isBlank() || company == "0" || company=="null") {
                Toast.makeText(context, "Select Company", Toast.LENGTH_LONG).show()
            } else if (group.isBlank() || group == "0" || group=="null") {
                Toast.makeText(context, "Select Group", Toast.LENGTH_LONG).show()
            } else if (rela.isBlank() || rela == "0" || rela=="null") {
                Toast.makeText(context, "Select Relationship", Toast.LENGTH_LONG).show()
            } else if (salutation.isBlank()|| salutation == "0") {
                Toast.makeText(context, "Select Salutation", Toast.LENGTH_LONG).show()
            } else if (strFirstName.isBlank()) {
                Toast.makeText(context, "Enter First Name", Toast.LENGTH_LONG).show()
            } else if (strMobile.isBlank()||(strMobile.length!=10)) {
                Toast.makeText(context, "Enter Mobile Number", Toast.LENGTH_LONG).show()
            }else if (strEmail.isBlank() || (!validEmail(strEmail))) {
                Toast.makeText(context, "Enter Email", Toast.LENGTH_LONG).show()
            } else if (strState?.isBlank() == true || strState == "Select State") {
                Toast.makeText(context, "Select State", Toast.LENGTH_LONG).show()

            } else if (strCity?.isBlank()==true || strCity == "Select Option") {
                Toast.makeText(context, "Select City", Toast.LENGTH_LONG).show()
            } else if (strArea?.isBlank() ==true|| strArea == "Select Option") {
                Toast.makeText(context, "Select City", Toast.LENGTH_LONG).show()
            } else if (strSociety?.isBlank() == true || strSociety == "Select Option") {
                Toast.makeText(context, "Select Society", Toast.LENGTH_LONG).show()
            }   else if (plot.isBlank()) {
                Toast.makeText(context, "Enter Plot", Toast.LENGTH_LONG).show()

            } else if (block.isBlank()) {
                Toast.makeText(context, "Enter Block ", Toast.LENGTH_LONG).show()

            } else if (floor.isBlank()) {
                Toast.makeText(context, "Enter Floor", Toast.LENGTH_LONG).show()

            } else if (pincode.isBlank()||pincode.length!=6) {
                Toast.makeText(context, "Enter PinCode", Toast.LENGTH_LONG).show()

            }else if (strSource?.isBlank()==true || strSource == "Select Option") {
                Toast.makeText(context, "Select Source", Toast.LENGTH_LONG).show()

            }  else if (strRemark.isBlank()) {
                Toast.makeText(context, "Enter Remarks", Toast.LENGTH_LONG).show()

            } else {
                createKaiLead(
                    strFirstName,
                    strLastName,
                    strEmail,
                    strMobile,
                    strRemark,
                    block,
                    plot,
                    floor,
                    pincode,
                    strSubBus
                )
            }
        }

        val subBusAdapter =
            context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item,KaizalaAppConstant.subBus) }
        subBusAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        spinner_SubBusinessSeg.adapter = subBusAdapter

        val stateAdapter =
            context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item,resources.getStringArray(R.array.list_of_state)) }
        stateAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        SpPDState.adapter = stateAdapter


        SpPDState.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                val itemPosition = adapterView.selectedItemPosition
                strState = SalesAppConstants.list_state_code[itemPosition]
                Log.e("code", strState.toString())
                getCity(strState)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        val salutationAdapter =
            context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, SalesAppConstants.list_of_salutation) }
        salutationAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        Spinner_PDSalutation.adapter = salutationAdapter

        Spinner_PDSalutation.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                val itemPosition = adapterView.selectedItemPosition
                strSalutationId = SalesAppConstants.list_of_salutationId[itemPosition]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

    }

    private fun createKaiLead(
        strFirstName: String,
        strLastName: String,
        strEmail: String,
        strMobile: String,
        strRemark: String,
        block: String,
        plot: String,
        floor: String,
        pincode: String,
        strSubBus: String
    ) {
        val kCompanyDetail = KCompanyDetail("", "", "", "")
        val kContactAddress = KContactAddress(
            "", "", "", "", "",
            "", "", "", "", "", ""
        )
        val kInstallationAddress = KInstallationAddress(
            block, strArea, "", strCity, "10001",
            floor, pincode, plot, strSociety, "", "", strState
        )
        val kSDWAN = KSDWAN(
            "", "", "", "",
            "", "", "", "", "", "",
            "", "", "", "", "",
            "", ""
        )
        val kOtherDetail = KOtherDetail(
            "", "", "",
            isDatacenter = false,
            isFirewall = false,
            isManagesWiFi = false,
            isVOIP = false,
            isVPN = false,
            media = "",
            serviceFromServiceProvider1 = "",
            serviceFromServiceProvider2 = "",
            targetInstallationPeriod = ""
        )
        val kCreateLeadReq = KCreateLeadReq(
            Constants.KCREATELEAD, Constants.AUTH_KEY, "", KaizalaAppConstant.HOME,
            strCompanyId, "", "",
            strEmail, "", strFirstName, strGroupId, "",
            strLastName, KaizalaAppConstant.KAIZALA, strSource, "", strMobile, password,
            "", strRelation, strRemark,
            strSalutationId, "", strSubBus,
            userName, "", kCompanyDetail, kContactAddress, kInstallationAddress,
            kSDWAN, kOtherDetail
        )

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.createKaiContact(kCreateLeadReq)
        call.enqueue(object : Callback<GetLeadSourceResp?> {
            override fun onResponse(
                call: Call<GetLeadSourceResp?>,
                response: Response<GetLeadSourceResp?>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    if(response.body()?.StatusCode=="200") {
                        try {
                            val intent = Intent (context, KaizalaLeadTabAct::class.java)
                            startActivity(intent)
                            Toast.makeText(context, response.body()?.Response?.Message, Toast.LENGTH_LONG)
                                .show()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }else{
                        Toast.makeText(context, response.body()?.Response?.Message, Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }

            override fun onFailure(call: Call<GetLeadSourceResp?>, t: Throwable) {
                Log.e("RetroError", t.toString())
            }
        })
    }



    fun getSource(str_lead_channel: String?) {
        val getLeadSourceRequest =
            str_lead_channel?.let {
                GetLeadSourceRequest(Constants.GET_SOURCE,Constants.AUTH_KEY, it,userName,password) }
        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getLeadSource(getLeadSourceRequest)
        call.enqueue(object : Callback<GetLeadSourceResp?> {
            override fun onResponse(call: Call<GetLeadSourceResp?>, response: Response<GetLeadSourceResp?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        val img = response.body()?.Response?.Message
                        img?.let { Log.e("image", it) }
                        sourceList= response.body()?.Response?.Data!!
                        source = ArrayList<String>()
                        for (item in sourceList)
                            source.add(item.SourceName)
                        val adapter12 = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, source) }
                        adapter12?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        binding.SpPDSource.adapter = adapter12
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<GetLeadSourceResp?>, t: Throwable) {
                Log.e("RetroError", t.toString())
            }
        })
    }
    override fun onClick(p0: View?) {
        if (p0?.id == R.id.rl_back) {
           back()
        }
    }

    fun back(){
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setCancelable(false)
        builder.setMessage("Do you want to go back to the previous screen?")
        builder.setPositiveButton(
            "Yes"
        ) { _, _ ->
            next()
        }
        builder.setNegativeButton(
            "No"
        ) { dialog, _ ->
            dialog.cancel()
        }
        val alert: AlertDialog = builder.create()
        alert.show()
    }

    fun next(){
        Intent(context, KaizalaLeadTabAct::class.java).also {
            startActivity(it)
        }
    }

  /*  private fun inProgress(){
        inAnimation = AlphaAnimation(0f, 1f)
        inAnimation.duration =200
        binding.contactProgressLayout.progressOverlay.animation = inAnimation
        binding.contactProgressLayout.progressOverlay.visibility = View.VISIBLE
    }



    private fun outProgress(){
        outAnimation = AlphaAnimation(1f, 0f)
        outAnimation.duration =200
        binding.contactProgressLayout.progressOverlay.animation = outAnimation
        binding.contactProgressLayout.progressOverlay.visibility = View.GONE

    }*/








    fun getArea(str_city_code: String?) {
        val getLeadAreaRequest =
            GetLeadAreaRequest(Constants.KGetAREA,Constants.KAUTH,str_city_code,
                "","",Constants.USER+userName,password,true)

        val apiService = ApiClientKaizala.getClient().create(ApiInterface::class.java)
        val call = apiService.getKArea(getLeadAreaRequest)
        call.enqueue(object : Callback<KGetAreaRes?> {
            override fun onResponse(call: Call<KGetAreaRes?>, response: Response<KGetAreaRes?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        if(response.body()?.StatusCode==200) {
                            areaList = response.body()?.Response!!
                            installArea = ArrayList<String>()
                            installAreaId = ArrayList<String>()
                            installArea.add("Select Option")
                            installAreaId.add("0")
                            for (item in areaList) {
                                installArea.add(item.AreaName + " (" + item.AreaCode + ")")
                                installAreaId.add(item.AreaCode)
                            }
                            val adapter11 = context?.let {
                                ArrayAdapter(
                                    it, android.R.layout.simple_spinner_item, installArea
                                )
                            }
                            binding.SearchPDArea.threshold = 0
                            adapter11?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            binding.SearchPDArea.setAdapter(adapter11)

                            binding.SearchPDArea.setOnFocusChangeListener { _, b ->
                                if (b) binding.SearchPDArea.showDropDown()
                            }
                            binding.SearchPDArea.onItemClickListener =
                                AdapterView.OnItemClickListener { _, _, position, _ ->
                                    val strGroupName = adapter11?.getItem(position)
                                    val split = strGroupName?.split("(")
                                    val groupId = split?.get(1)
                                    val groupidd = groupId?.split(")")
                                    strArea = groupidd?.get(0)
                                    strArea?.let { Log.e("AreaId", it) }
                                    getSociety(strArea)
                                }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<KGetAreaRes?>, t: Throwable) {
                Log.e("RetroError", t.toString())
            }
        })
    }

    fun getCompany() {
        val getCompanyRequest = GetCompanyRequest(Constants.GET_COMPANY,Constants.KAUTH,"",password,Constants.USER+userName)
        val apiService = ApiClientKaizala.getClient().create(ApiInterface::class.java)
        val call = apiService.getKComapnyList(getCompanyRequest)
        call.enqueue(object : Callback<KGetCompanyResponse?> {
            override fun onResponse(call: Call<KGetCompanyResponse?>, response: Response<KGetCompanyResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        companyList= response.body()?.Response!!
                        company = ArrayList<String>()
                        companyId = ArrayList<String>()
                        company.add("Select Company")
                        companyId.add("0")
                        group = ArrayList<String>()
                        groupId = ArrayList<String>()
                        group.add("Select Group")
                        groupId.add("0")
                        for (item in companyList){
                            company.add(item.Company_Name +" ("+item.Company_ID+")")
                            group.add(item.Group_Name +" ("+item.Group_ID+")")
                            companyId.add(item.Company_ID)
                            groupId.add(item.Group_ID)
                        }

                        val adapter12 = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, company) }
                        binding.SearchPDCompany.threshold=0
                        adapter12?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        binding.SearchPDCompany.setAdapter(adapter12)

                        binding.SearchPDCompany.setOnFocusChangeListener { _, b ->
                            if (b) binding.SearchPDCompany.showDropDown()
                        }
                        binding.SearchPDCompany.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                            val strCompanyName = adapter12?.getItem(position)
                            val split = strCompanyName?.split("(")
                            val compID = split?.get(1)
                            val companyID = compID?.split(")")
                            strCompanyId = companyID?.get(0)
                            strCompanyId?.let { Log.e("compID", it) }
                            getRelation(strCompanyId)
                        }

                        val adapter11 = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, group) }
                        binding.SearchPDGroup.threshold=0
                        adapter11?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        binding.SearchPDGroup.setAdapter(adapter11)

                        binding.SearchPDGroup.setOnFocusChangeListener { _, b ->
                            if (b) binding.SearchPDGroup.showDropDown()
                        }
                        binding.SearchPDGroup.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                            val strGroupName = adapter11?.getItem(position)
                            val split = strGroupName?.split("(")
                            val groupId = split?.get(1)
                            val groupID = groupId?.split(")")
                            strGroupId = groupID?.get(0)
                            strGroupId?.let { Log.e("GroupId", it) }
                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<KGetCompanyResponse?>, t: Throwable) {
                Log.e("RetroError", t.toString())
            }
        })
    }

    fun getRelation(Company: String?) {
        val getCompanyRequest = Company?.let { KGetRelationReq(Constants.GET_RELATIONSHIP,Constants.KAUTH, it,password,Constants.USER+userName) }
        val apiService = ApiClientKaizala.getClient().create(ApiInterface::class.java)
        val call = apiService.getKRelationList(getCompanyRequest)
        call.enqueue(object : Callback<KGetRelationRes?> {
            override fun onResponse(call: Call<KGetRelationRes?>, response: Response<KGetRelationRes?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        relationList= response.body()?.Response!!
                        relation = ArrayList<String>()
                        relationId = ArrayList<String>()
                        relation.add("Select Relation")
                        relationId.add("0")
                        for (item in relationList) {
                            relation.add(item.Relationship_Name +" ("+item.Relationship_ID+")")
                            relationId.add(item.Relationship_ID)
                        }

                        val adapter12 = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, relation) }
                        adapter12?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        Spinner_PDRelationShip.adapter = adapter12

                        Spinner_PDRelationShip.onItemSelectedListener =
                            object : AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(adapterView: AdapterView<*>,
                                                            view: View, i: Int, l: Long) {
                                    strRelation= relationId[i]
                                }

                                override fun onNothingSelected(parent: AdapterView<*>?) {

                                }
                            }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<KGetRelationRes?>, t: Throwable) {
                Log.e("RetroError", t.toString())
            }
        })
    }


    fun getSociety(strArea: String?) {
        val getSocietyReq =
            KGetSocietyReq(Constants.KGETSOCIETY,Constants.KAUTH,strArea,password,Constants.USER+userName)
        val apiService = ApiClientKaizala.getClient().create(ApiInterface::class.java)
        val call = apiService.getKSociety(getSocietyReq)
        call.enqueue(object : Callback<KGetSocietyRes?> {
            override fun onResponse(call: Call<KGetSocietyRes?>, response: Response<KGetSocietyRes?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        societyList= response.body()?.Response!!
                        installSociety = ArrayList<String>()
                        installSocietyId = ArrayList<String>()
                        installSociety.add("Select Option")
                        installSocietyId.add("0")
                        for (item in societyList) {
                            installSociety.add(item.SocietyName +" ("+item.SocietyCode+")")
                            installSocietyId.add(item.SocietyCode)
                        }
                        val adapter121 = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, installSociety) }
                        adapter121?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        SpPDSociety.adapter = adapter121

                        SpPDSociety.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                                strSociety = installSocietyId[i]
                            }

                            override fun onNothingSelected(parent: AdapterView<*>?) {

                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<KGetSocietyRes?>, t: Throwable) {
                Log.e("RetroError", t.toString())
            }
        })
    }




    fun getCity(strState: String?) {
        val getCityRequest = GetCityRequest(Constants.KGetCity,Constants.KAUTH,password,strState,Constants.USER+userName)

        val apiService = ApiClientKaizala.getClient().create(ApiInterface::class.java)
        val call = apiService.getKCityList(getCityRequest)
        call.enqueue(object : Callback<KGetCityRes?> {
            override fun onResponse(call: Call<KGetCityRes?>, response: Response<KGetCityRes?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        cityList = response.body()?.Response!!
                        Installcity = ArrayList<String>()
                        InstallcityCode = ArrayList<String>()
                        Installcity.add("Select City")
                        InstallcityCode.add("0")
                        for (item in cityList) {
                            Installcity.add(item.CityName)
                            InstallcityCode.add(item.CityCode)
                        }
                        val adapter121 = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, Installcity) }
                        adapter121?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        SpPDCity.adapter = adapter121

                        SpPDCity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                                strCity = InstallcityCode[i]
                                getArea(strCity)
                            }

                            override fun onNothingSelected(parent: AdapterView<*>?) {

                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<KGetCityRes?>, t: Throwable) {
                Log.e("RetroError", t.toString())
            }
        })
    }

}