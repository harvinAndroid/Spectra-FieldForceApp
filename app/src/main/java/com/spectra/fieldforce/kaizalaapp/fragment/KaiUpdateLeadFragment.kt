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
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.spectra.fieldforce.R
import com.spectra.fieldforce.api.ApiClient
import com.spectra.fieldforce.api.ApiClientKaizala
import com.spectra.fieldforce.api.ApiInterface
import com.spectra.fieldforce.databinding.KaiCreateLeadBinding
import com.spectra.fieldforce.kaizalaapp.activity.KaizalaContactTabActivity
import com.spectra.fieldforce.kaizalaapp.activity.KaizalaLeadTabAct
import com.spectra.fieldforce.kaizalaapp.model.*
import com.spectra.fieldforce.salesapp.model.*
import com.spectra.fieldforce.utils.*
import kotlinx.android.synthetic.main.caftoolbar.view.*
import kotlinx.android.synthetic.main.kai_create_lead.*
import kotlinx.android.synthetic.main.kai_frag_contact.*
import kotlinx.android.synthetic.main.update_lead_company_details_row.view.*
import kotlinx.android.synthetic.main.update_lead_demo_fragment.*
import kotlinx.android.synthetic.main.update_leadtoolbar.*
import kotlinx.android.synthetic.main.update_leadtoolbar.view.*
import kotlinx.android.synthetic.main.update_leadtoolbar.view.rl_back
import kotlinx.android.synthetic.main.update_leadtoolbar.view.tv_lang
import kotlinx.android.synthetic.main.updatelead_contact_personadd_row.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import java.util.regex.Pattern

class KaiUpdateLeadFragment:Fragment(), View.OnClickListener{

    private lateinit var  binding: KaiCreateLeadBinding
    private lateinit var userName: String
    private lateinit var password:String
    private lateinit var inAnimation: AlphaAnimation
    private lateinit var outAnimation: AlphaAnimation
    private lateinit var competitorList : MutableList<CompResponse>
    private lateinit var competitorName : MutableList<String>
    private lateinit var source : MutableList<String>
    private lateinit var sourceList: MutableList<SrcData>
    private lateinit var planCategoryList : MutableList<PlanData>
    private lateinit var categoryName : MutableList<String>
    private lateinit var cityList : MutableList<KCityResponse>
    private lateinit var installCity : MutableList<String>
    private lateinit var installCityCode : MutableList<String>
    private lateinit var areaList : MutableList<KAreaResponse>
    private lateinit var installArea : MutableList<String>
    private lateinit var installAreaId : MutableList<String>
    private lateinit var societyList : MutableList<SocietyResponse>
    private lateinit var installSociety : MutableList<String>
    private lateinit var installSocietyId : MutableList<String>
    private var strCompanyId : String? = null
    private var strRelationId : String? = null
    private var strGroupId : String? = null
    private lateinit var company : MutableList<String>
    private lateinit var companyId : MutableList<String>
    private lateinit var companyList: MutableList<KCompanyResponse>
    private lateinit var group : MutableList<String>
    private lateinit var groupId : MutableList<String>
    private lateinit var relation : MutableList<String>
    private lateinit var relationId : MutableList<String>
    private lateinit var relationList: MutableList<KRelationResponse>
    lateinit var adapter: ArrayAdapter<*>
    private lateinit var strLeadId:String
    private var strState :String? = null
    private var strArea:String? =null
    private  var strCity :String?=null
    private  var strSociety :String?=null
    private var strSubBusiness :String ?=null
    private var strSalutationId :String ? =null
    private var strSource :String? = null
    private var strContactId:String?=null


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


        val sp1: SharedPreferences? = context?.getSharedPreferences("Login", 0)
        userName = sp1?.getString(AppConstants.USERNAME, null).toString()
        password = sp1?.getString(AppConstants.PASSWORD, null).toString()
        val bundle = arguments
        strLeadId = bundle?.getString("LeadID").toString()
        strContactId = bundle?.getString("ContactId").toString()
        getLeadDetails()
        getSource(KaizalaAppConstant.KAIZALA)
        listener()
        getCompany()
    }

    private fun listener(){
        btn_createNewLead.flr.setOnClickListener {
            val b = Bundle()
            b.putString("LeadId",strLeadId)
            val kaiCafFragment = KaiCafFragment()
            kaiCafFragment.arguments=b
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.frag_container, kaiCafFragment)?.addToBackStack(null)?.commit()

        }

        binding.btnLeadSubmit.setOnClickListener {
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
            val rela = strRelationId.toString()
            val subbus= strSubBusiness.toString()
            val salutation = strSalutationId.toString()
            if(subbus.isBlank()||subbus=="Select Option"){
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

            }else if (strSource?.isBlank()==true) {
                Toast.makeText(context, "Select Source", Toast.LENGTH_LONG).show()

            }  else if (strRemark.isBlank()) {
                Toast.makeText(context, "Enter Remarks", Toast.LENGTH_LONG).show()

            } else {
                updateKaiLead(
                    strFirstName,strLastName,strEmail,strMobile,strRemark,block,
                    plot,floor,pincode
                )
            }
        }
    }

    private fun validEmail(email: String): Boolean {
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }


    private fun getLeadDetails () {
        inProgress()
        if(strContactId.isNullOrBlank()||strContactId=="null"){
           strContactId=""
        }else if(strLeadId == "null"){
            strLeadId=""
        }
        val getLeadRequest = GetLeadRequest(Constants.GET_LEADS,Constants.AUTH_KEY,strLeadId,password,userName,strContactId)

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getLead(getLeadRequest)
        call.enqueue(object : Callback<LeadResponsee?> {
            override fun onResponse(call: Call<LeadResponsee?>, response: Response<LeadResponsee?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        if(response.body()?.StatusCode==200) {
                            binding.info = response.body()?.Response?.get(0)
                            outProgress()

                            strState = response.body()?.Response?.get(0)?.installationAddress?.InstallState.toString()
                            var statePosition = 0
                            SalesConstant.list_state_code.forEachIndexed { index, s ->
                                if (s == strState) statePosition = index
                            }
                            val stateAdapter =
                                context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item,resources.getStringArray(R.array.list_of_state)) }
                            stateAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            SpPDState.adapter = stateAdapter
                            SpPDState.setSelection(statePosition)

                            strSource = response.body()?.Response?.get(0)?.LeadSource.toString()
                            strCity = response.body()?.Response?.get(0)?.installationAddress?.InstallCity.toString()
                            strArea = response.body()?.Response?.get(0)?.installationAddress?.InstallArea.toString()
                            strSociety = response.body()?.Response?.get(0)?.installationAddress?.InstallSociety.toString()
                            strCompanyId = response.body()?.Response?.get(0)?.CompanyId.toString()
                            strGroupId = response.body()?.Response?.get(0)?.GroupId.toString()
                            strRelationId = response.body()?.Response?.get(0)?.RelationshipId.toString()
                            strSubBusiness = response.body()?.Response?.get(0)?.SubBusinessSegment.toString()
                            strSalutationId = response.body()?.Response?.get(0)?.SalutationId.toString()
                            val companyName = response.body()?.Response?.get(0)?.companyName.toString()
                            if(companyName.isNotEmpty()) {
                                Search_PDCompany.setText("$companyName($strCompanyId)")
                                getRelation(strCompanyId)
                            }
                            val groupName = response.body()?.Response?.get(0)?.GroupName.toString()
                            if(groupName.isNotEmpty()) {
                                Search_PDGroup.setText("$groupName($strGroupId)")
                            }
                            val areaName = response.body()?.Response?.get(0)?.installationAddress?.InstallAreaName.toString()
                            if(areaName.isNotEmpty()) {
                                Search_PDArea.setText("$areaName($strArea)")
                                getSociety(strArea)
                            }
                            getSource(KaizalaAppConstant.KAIZALA)
                            var subBusPosition = 0
                            KaizalaAppConstant.subBus.forEachIndexed { index, s ->
                                if (s == strSubBusiness) subBusPosition = index
                            }
                            val subBusAdapter =
                                context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item,KaizalaAppConstant.subBus) }
                            subBusAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            spinner_SubBusinessSeg.adapter = subBusAdapter
                            spinner_SubBusinessSeg.setSelection(subBusPosition)

                            var salutationPosition = 0
                            SalesAppConstants.list_of_salutationId.forEachIndexed { index, s ->
                                if (s == strSalutationId) salutationPosition = index
                            }
                            val salutationAdapter =
                                context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, SalesAppConstants.list_of_salutation) }
                            salutationAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            Spinner_PDSalutation.adapter = salutationAdapter
                            Spinner_PDSalutation.setSelection(salutationPosition)

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
                            when (response.body()?.Response?.get(0)?.Status) {
                                "1" -> {
                                    btnLeadSubmit.visibility=View.GONE
                                    btn_createNewLead.flr.visibility=View.VISIBLE
                                }
                                "2" -> {
                                    btnLeadSubmit.visibility = View.GONE
                                }
                                else -> {
                                    btnLeadSubmit.visibility = View.VISIBLE
                                    btn_createNewLead.flr.visibility=View.GONE
                                }
                            }
                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<LeadResponsee?>, t: Throwable) {
                binding.updateleadProgressLayout.progressOverlay.visibility=View.GONE
                Log.e("ContactRetroError", t.toString())
            }
        })
    }


    private fun updateKaiLead(
        strFirstName: String,
        strLastName: String,
        strEmail: String,
        strMobile: String,
        strRemark: String,
        block: String,
        plot: String,
        floor: String,
        pincode: String
    ) {

        val kCompanyDetail=KCompanyDetail("","","","")
        val kContactAddress=KContactAddress("","","","","",
            "","","","","","")
        val kInstallationAddress=KInstallationAddress(block,strArea,"",strCity,"10001",
            floor,pincode,plot,strSociety,"","",strState)
        val kSDWAN=KSDWAN("","","","",
            "","","","","","",
            "","","","","",
            "","")
        val kOtherDetail=KOtherDetail("","","",
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
        val kCreateLeadReq = KCreateLeadReq(Constants.UPDATE_LEAD,Constants.AUTH_KEY,strLeadId,KaizalaAppConstant.HOME,
            strCompanyId, "","",
            strEmail,"",strFirstName,strGroupId,"",
            strLastName, KaizalaAppConstant.KAIZALA,strSource,"",strMobile,password,
            "", strRelationId,strRemark,
            strSalutationId,"",strSubBusiness,
            userName,"",kCompanyDetail,kContactAddress,kInstallationAddress,
            kSDWAN,kOtherDetail)

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.createKaiContact(kCreateLeadReq)
        call.enqueue(object : Callback<GetLeadSourceResp?> {
            override fun onResponse(call: Call<GetLeadSourceResp?>, response: Response<GetLeadSourceResp?>) {
                if (response.isSuccessful && response.body() != null) {
                    if(response.body()?.StatusCode=="200") {
                        try {
                            val intent = Intent (context, KaizalaLeadTabAct::class.java)
                            startActivity(intent)
                            Toast.makeText(context, response.body()?.Response?.Message, Toast.LENGTH_LONG).show()
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

    fun getSource(str_lead_chnl: String?) {
        val getLeadSourceRequest =
            str_lead_chnl?.let {
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
                        var sourcePosition = 0
                        source.forEachIndexed { index, s ->
                            if (s == strSource) sourcePosition = index
                        }
                        val adapter12 = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, source) }
                        adapter12?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        binding.SpPDSource.adapter = adapter12
                        binding.SpPDSource.setSelection(sourcePosition)
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
        builder.setPositiveButton("Yes") { _, _ ->
            next()
        }
        builder.setNegativeButton("No") { dialog, _ ->
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

      private fun inProgress(){
          inAnimation = AlphaAnimation(0f, 1f)
          inAnimation.duration =200
          binding.updateleadProgressLayout.progressOverlay.animation = inAnimation
          binding.updateleadProgressLayout.progressOverlay.visibility = View.VISIBLE

      }

      private fun outProgress(){
          outAnimation = AlphaAnimation(0f, 1f)
          outAnimation.duration =200
          binding.updateleadProgressLayout.progressOverlay.animation = outAnimation
          binding.updateleadProgressLayout.progressOverlay.visibility = View.GONE

      }

   /* fun getArea(str_city_code: String?) {
        val getLeadAreaRequest =
            GetLeadAreaRequest(Constants.KGetAREA,Constants.KAUTH,str_city_code,
                "","",Constants.USER+userName,password,true)


        val apiService = ApiClientKaizala.getClient().create(ApiInterface::class.java)
        val call = apiService.getKArea(getLeadAreaRequest)
        call.enqueue(object : Callback<KGetAreaRes?> {
            override fun onResponse(call: Call<KGetAreaRes?>, response: Response<KGetAreaRes?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        if(response.body()?.StatusCode==200){
                            areaList= response.body()?.Response!!
                            Installarea = ArrayList<String>()
                            installAreaId = ArrayList<String>()
                            Installarea.add("Select Option")
                            installAreaId.add("0")
                            for (item in areaList) {
                                Installarea.add(item.AreaName +" ("+item.AreaCode+")")
                                installAreaId.add(item.AreaCode)
                            }

                            val adapter11 = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, Installarea) }
                            binding.SearchPDArea.threshold=0
                            adapter11?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            binding.SearchPDArea.setAdapter(adapter11)

                            binding.SearchPDArea.setOnFocusChangeListener { _, b ->
                                if (b)  binding.SearchPDArea.showDropDown()
                            }
                            binding.SearchPDArea.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
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
                        Search_PDCompany.threshold=0
                        adapter12?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        Search_PDCompany.setAdapter(adapter12)

                        Search_PDCompany.setOnFocusChangeListener { _, b ->
                            if (b) Search_PDCompany.showDropDown()
                        }
                        Search_PDCompany.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                            val strCompanyName = adapter12?.getItem(position)
                            val split = strCompanyName?.split("(")
                            val compID = split?.get(1)
                            val companyID = compID?.split(")")
                            strCompanyId = companyID?.get(0)
                            strCompanyId?.let { Log.e("compID", it) }
                            getRelation(strCompanyId)
                        }

                        val adapter11 = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, group) }
                        Search_PDGroup.threshold=0
                        adapter11?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        Search_PDGroup.setAdapter(adapter11)

                        Search_PDGroup.setOnFocusChangeListener { _, b ->
                            if (b) Search_PDGroup.showDropDown()
                        }
                        Search_PDGroup.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
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


    fun getRelation(str_cmny: String?) {
        val getCompanyRequest = str_cmny?.let { KGetRelationReq(Constants.GET_RELATIONSHIP,Constants.KAUTH, it,password,Constants.USER+userName) }
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

                        var relationPosition = 0
                        relationId.forEachIndexed { index, s ->
                            if (s == strRelationId) relationPosition = index
                        }
                        val adapter12 = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, relation) }
                        adapter12?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        Spinner_PDRelationShip.adapter = adapter12
                        Spinner_PDRelationShip.setSelection(relationPosition)

                        Spinner_PDRelationShip.onItemSelectedListener =
                            object : AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(adapterView: AdapterView<*>,
                                                            view: View, i: Int, l: Long) {
                                    strRelationId=relationId[i]
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

                        var societyPosition = 0
                        installSocietyId.forEachIndexed { index, s ->
                            if (s == strSociety) societyPosition = index
                        }
                        val adapter12 = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, installSociety) }
                        adapter12?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        SpPDSociety.adapter = adapter12
                        SpPDSociety.setSelection(societyPosition)

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
                        installCity = ArrayList<String>()
                        installCityCode = ArrayList<String>()
                        installCity.add("Select City")
                        installCityCode.add("0")
                        for (item in cityList) {
                            installCity.add(item.CityName)
                            installCityCode.add(item.CityCode)
                        }
                        var cityPosition = 0
                        installCityCode.forEachIndexed { index, s ->
                            if (s == strCity) cityPosition = index
                        }
                        val adapter12 = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, installCity) }
                        adapter12?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        SpPDCity.adapter = adapter12
                        SpPDCity.setSelection(cityPosition)

                        SpPDCity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                                strCity = installCityCode[i]
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