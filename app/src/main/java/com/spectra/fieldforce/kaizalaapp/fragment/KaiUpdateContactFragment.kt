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
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.spectra.fieldforce.R
import com.spectra.fieldforce.api.ApiClient
import com.spectra.fieldforce.api.ApiClientKaizala
import com.spectra.fieldforce.api.ApiInterface
import com.spectra.fieldforce.databinding.KaiFragContactBinding
import com.spectra.fieldforce.kaizalaapp.activity.KaizalaContactTabActivity
import com.spectra.fieldforce.kaizalaapp.model.*
import com.spectra.fieldforce.salesapp.model.*
import com.spectra.fieldforce.utils.*
import kotlinx.android.synthetic.main.kai_frag_contact.*
import kotlinx.android.synthetic.main.update_leadtoolbar.view.*
import kotlinx.android.synthetic.main.update_leadtoolbar.view.rl_back
import kotlinx.android.synthetic.main.update_leadtoolbar.view.tv_lang
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern


class KaiUpdateContactFragment:Fragment(), View.OnClickListener{

    private lateinit var  binding: KaiFragContactBinding
    private lateinit var userName: String
    private lateinit var password:String
    private lateinit var inAnimation: AlphaAnimation
    private lateinit var outAnimation: AlphaAnimation
    private lateinit var competitorList : MutableList<CompResponse>
    private lateinit var competitorName : MutableList<String>
    private lateinit var source : MutableList<String>
    private lateinit var sourceList: MutableList<SrcData>
    private  var strState :String?=null
    private lateinit var strStatus :String
    private lateinit var strCity :String
    private lateinit var strArea :String
    private  var strSociety :String?=null
    private var strPlanCat :String ?=null
    private var strCompetitor :String ? =null
    private var strSource :String? = null
    private lateinit var planCategoryList : MutableList<PlanData>
    private lateinit var categoryName : MutableList<String>
    private lateinit var cityList : MutableList<KCityResponse>
    private lateinit var installCity : MutableList<String>
    private lateinit var installCityCode : MutableList<String>
    private lateinit var areaList : MutableList<KAreaResponse>
    private lateinit var Installarea : MutableList<String>
    private lateinit var InstallareaCode : MutableList<String>
    private lateinit var societyList : MutableList<SocietyResponse>
    private lateinit var installSociety : MutableList<String>
    private lateinit var installSocietyCode : MutableList<String>
    private var strContactId:String?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = KaiFragContactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contacttoolbar.rl_back.setOnClickListener(this)
        contacttoolbar.tv_lang.text=AppConstants.CONTACT_MANAGEMENT

        val sp1: SharedPreferences? = context?.getSharedPreferences("Login", 0)
        userName = sp1?.getString(AppConstants.USERNAME, null).toString()
        password = sp1?.getString(AppConstants.PASSWORD, null).toString()
        val bundle = arguments
        strContactId = bundle?.getString("ContactID")

        listener()

        getContactDetails()
        contacttoolbar.flr.setOnClickListener {
            try {
                val b = Bundle()
                b.putString("ContactId",strContactId )
                val activity =context as AppCompatActivity
                val kaiUpdateLeadFragment = KaiUpdateLeadFragment()
                kaiUpdateLeadFragment.arguments=b
                activity.supportFragmentManager.beginTransaction()
                    .replace(R.id.frag_container, kaiUpdateLeadFragment).addToBackStack(null).commit()

            }catch (e: Exception){

            }
        }

    }

    private fun setAdapter(){



        spStatus.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
               // val itemPosition = adapterView.selectedItemPosition
                strStatus= resources.getStringArray(R.array.list_of_conReasonValue)[i]
                Log.e("code", strStatus)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        spState.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
              //  val itemPosition = adapterView.selectedItemPosition
                strState = SalesAppConstants.list_state_code[i]
                Log.e("code", strState!!)
                getCity(strState)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }



    }


    private fun validEmail(email: String): Boolean {
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }



    private fun getContactDetails () {

        val kGetContactReq = KGetContactReq(Constants.KGETCONTACT,Constants.KAUTH,strContactId,password,Constants.USER+userName)

        val apiService = ApiClientKaizala.getClient().create(ApiInterface::class.java)
        val call = apiService.getKContact(kGetContactReq)
        call.enqueue(object : Callback<KContResponse?> {
             override fun onResponse(call: Call<KContResponse?>, response: Response<KContResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        if(response.body()?.StatusCode==200) {
                            binding.info= response.body()?.Response


                            strStatus = response.body()?.Response?.StatusReason.toString()
                            var statusPos = 0
                            resources.getStringArray(R.array.list_of_conReasonValue).forEachIndexed { index, s ->
                                if (s == strStatus) statusPos = index
                            }
                            val reasonAdapter =
                                context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item,resources.getStringArray(R.array.list_of_conReason)) }
                            reasonAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            spStatus.adapter = reasonAdapter
                            spStatus.setSelection(statusPos)

                           strState = response.body()?.Response?.State.toString()
                            var statePosition = 0
                            SalesConstant.list_state_code.forEachIndexed { index, s ->
                                if (s == strState) statePosition = index
                            }
                            val stateAdapter =
                            context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item,resources.getStringArray(R.array.list_of_state)) }
                            stateAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            spState.adapter = stateAdapter
                            spState.setSelection(statePosition)

                            strPlanCat = response.body()?.Response?.PlanCategory.toString()
                            strSource = response.body()?.Response?.Source.toString()
                            strCompetitor = response.body()?.Response?.CompetitorName.toString()
                            strCity = response.body()?.Response?.City.toString()
                            strArea = response.body()?.Response?.Area.toString()
                            strSociety = response.body()?.Response?.Society.toString()

                            getCompetitor()
                            getSource(KaizalaAppConstant.KAIZALA)
                            getPlanCategory()
                            setAdapter()
                            val status = response.body()?.Response?.StatusName.toString()
                            if(status=="Lead Created/Closed"){
                                kContactSubmit.visibility=View.GONE
                            }else{
                                kContactSubmit.visibility=View.VISIBLE
                            }

                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<KContResponse?>, t: Throwable) {
                Log.e("ContactRetroError", t.toString())
            }
        })
    }

    private fun isValidMobile(phone: String): Boolean {
        return Patterns.PHONE.matcher(phone).matches()
    }

    private fun listener(){
        binding.kContactSubmit.setOnClickListener {
            try {
                val fName = etPersonName.text.toString()
                val lName = etPersonName2.text.toString()
                val planCategory = spPlanCat.selectedItem.toString()
                val mobileOne = etMobileNum.text.toString()
                val mobileTwo = etMobileNum2.text.toString()
                val email = etEmail.text.toString()
                val source = spSource.selectedItem.toString()
                val compt = spCompt.selectedItem.toString()
                val remarks = etRemark.text.toString()

                if (fName.isBlank()) {
                    Toast.makeText(context, "Enter First Name", Toast.LENGTH_LONG).show()

                } else if (lName.isBlank()) {
                    Toast.makeText(context, "Enter Last Name", Toast.LENGTH_LONG).show()

                } else if (planCategory.isBlank() || planCategory == "Select Option") {
                    Toast.makeText(context, "Select PlanCategory", Toast.LENGTH_LONG).show()

                } else if (strState?.isBlank() == true || strState == "Select State") {
                    Toast.makeText(context, "Select State", Toast.LENGTH_LONG).show()

                } else if (strCity.isBlank() || strCity == "Select Option") {
                    Toast.makeText(context, "Select City", Toast.LENGTH_LONG).show()
                } else if (strArea.isBlank() || strArea == "Select Option") {
                    Toast.makeText(context, "Select City", Toast.LENGTH_LONG).show()
                } else if (strSociety?.isBlank() == true || strSociety == "Select Option") {
                    Toast.makeText(context, "Select Society", Toast.LENGTH_LONG).show()
                } else if (mobileOne.isBlank() || (mobileOne.length != 10)) {
                    Toast.makeText(context, "Enter Mobile Number", Toast.LENGTH_LONG).show()

                } /*else if (mobileTwo.isBlank() || (mobileTwo.length != 10)) {
                    Toast.makeText(context, "Enter Mobile Number2 ", Toast.LENGTH_LONG).show()

                } */else if (email.isBlank() || (!validEmail(email))) {
                    Toast.makeText(context, "Enter Email", Toast.LENGTH_LONG).show()

                } else if (source.isBlank() || source == "Select Option") {
                    Toast.makeText(context, "Select Source", Toast.LENGTH_LONG).show()

                } else if (compt.isBlank() || source == "Select Option") {
                    Toast.makeText(context, "Select Competitor", Toast.LENGTH_LONG).show()

                } else if (strStatus.isBlank() || strStatus == "Select Option") {
                    Toast.makeText(context, "Select Status", Toast.LENGTH_LONG).show()
                } else if (remarks.isBlank()) {
                    Toast.makeText(context, "Enter Remarks", Toast.LENGTH_LONG).show()
                } else {
                    updateKaiContact(
                        fName, lName, planCategory, mobileOne, mobileTwo, email,
                        source, compt, remarks
                    )
                }
            }catch (E:Exception){
                E.printStackTrace()

            }
        }

    }

    private fun updateKaiContact(
        fName: String,
        lName: String,
        planCategory: String,
        mobileOne: String,
        mobileTwo: String,
        email: String,
        source: String,
        compt: String,
        remarks: String
    ) {
        val kcreateReq = KCreateConatactReq(Constants.KUPDATECONTACT,Constants.KAUTH,strArea,"",
            KaizalaAppConstant.HOME,"", KaizalaAppConstant.KAIZALA,strCity,compt,strStatus,email,
            fName, "",lName,mobileOne,mobileTwo,password, planCategory, remarks,strSociety,
            source,strState,strStatus,Constants.USER+userName,strContactId)

        val apiService = ApiClientKaizala.getClient().create(ApiInterface::class.java)
        val call = apiService.updateKaiContact(kcreateReq)
        call.enqueue(object : Callback<KUpdateContactRes?> {
            override fun onResponse(call: Call<KUpdateContactRes?>, response: Response<KUpdateContactRes?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        if(response.body()?.Response?.StatusCode==1){
                            Toast.makeText(context,response.body()?.Response?.StatusName,Toast.LENGTH_LONG).show()
                            val intent = Intent (context, KaizalaContactTabActivity::class.java)
                            startActivity(intent)
                        }else{
                            Toast.makeText(context,response.body()?.Response?.StatusName,Toast.LENGTH_LONG).show()
                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<KUpdateContactRes?>, t: Throwable) {
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
                        val adapter12 = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, source) }
                        adapter12?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        binding.spSource.adapter = adapter12
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
        Intent(context, KaizalaContactTabActivity::class.java).also {
            startActivity(it)
        }
    }

    private fun inProgress(){
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

}

    private fun getCompetitor() {
        val getLeadSourceRequest =
                GetLeadSourceRequest(Constants.GETCOMP,Constants.KAUTH,
                    "","IN\\manager1"/*userName*/,password)

        val apiService = ApiClientKaizala.getClient().create(ApiInterface::class.java)
        val call = apiService.getCompList(getLeadSourceRequest)
        call.enqueue(object : Callback<KCompResponse?> {
            override fun onResponse(call: Call<KCompResponse?>, response: Response<KCompResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        competitorList= response.body()?.Response!!
                        competitorName = ArrayList<String>()
                        competitorName.add("Select Option")
                        for (item in competitorList)
                            competitorName.add(item.CompetitorName)

                        var competitorPosition = 0
                        competitorName.forEachIndexed { index, s ->
                            if (s == strCompetitor) competitorPosition = index
                        }

                        val adapter12 = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, competitorName) }
                        adapter12?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        binding.spCompt.adapter = adapter12
                        spCompt.setSelection(competitorPosition)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<KCompResponse?>, t: Throwable) {
                Log.e("RetroError", t.toString())
            }
        })
    }

    private fun getPlanCategory() {
        val getCategoryRequest =
            PlanCategoryRequest(Constants.KGETPLANcATEGORGY,Constants.KAUTH,
                "Home",password,Constants.USER+userName)

        val apiService = ApiClientKaizala.getClient().create(ApiInterface::class.java)
        val call = apiService.kgetPlanCat(getCategoryRequest)
        call.enqueue(object : Callback<KGetPlanCatRes?> {
            override fun onResponse(call: Call<KGetPlanCatRes?>, response: Response<KGetPlanCatRes?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        planCategoryList= response.body()?.Response!!
                        categoryName = ArrayList<String>()
                        categoryName.add("Select Option")
                        for (item in planCategoryList)
                            categoryName.add(item.CategoryName)

                        var categoryPosition = 0
                        categoryName.forEachIndexed { index, s ->
                            if (s == strPlanCat) categoryPosition = index
                        }

                        val adapter12 = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, categoryName) }
                        adapter12?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        spPlanCat.adapter = adapter12
                        spPlanCat.setSelection(categoryPosition)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<KGetPlanCatRes?>, t: Throwable) {
                Log.e("RetroError", t.toString())
            }
        })
    }




    fun getArea(str_city_code: String) {
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
                            Installarea = ArrayList<String>()
                            InstallareaCode = ArrayList<String>()
                            Installarea.add("Select Option")
                            InstallareaCode.add("0")
                            for (item in areaList) {
                                Installarea.add(item.AreaName + " (" + item.AreaCode + ")")
                                InstallareaCode.add(item.AreaCode)
                            }

                            var areaPosition = 0
                            InstallareaCode.forEachIndexed { index, s ->
                                if (s == strArea) areaPosition = index
                            }

                            val adapter121 = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item,
                                    Installarea) }
                            adapter121?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            searchArea.adapter = adapter121
                            searchArea.setSelection(areaPosition)

                            searchArea.onItemSelectedListener =
                                object : AdapterView.OnItemSelectedListener {
                                    override fun onItemSelected(adapterView: AdapterView<*>, view: View,
                                        i: Int, l: Long) {
                                        strArea = InstallareaCode[i]
                                        getSociety(strArea)
                                    }

                                    override fun onNothingSelected(parent: AdapterView<*>?) {

                                    }
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


    fun getSociety(strArea: String) {
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
                        installSocietyCode = ArrayList<String>()
                        installSociety.add("Select Option")
                        installSocietyCode.add("0")
                        for (item in societyList) {
                            installSociety.add(item.SocietyName +" ("+item.SocietyCode+")")
                            installSocietyCode.add(item.SocietyCode)
                        }
                        var societyPos = 0
                        installSocietyCode.forEachIndexed { index, s ->
                            if (s == strSociety) societyPos = index
                        }
                        val adapter121 = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, installSociety) }
                        adapter121?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        spSociety.adapter = adapter121
                        spSociety.setSelection(societyPos)

                        spSociety.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                                strSociety = installSocietyCode[i]
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




    fun getCity(strStaeId :String?) {
        val getCityRequest = GetCityRequest(Constants.KGetCity,Constants.KAUTH,password,strStaeId,Constants.USER+userName)

        val apiService = ApiClientKaizala.getClient().create(ApiInterface::class.java)
        val call = apiService.getKCityList(getCityRequest)
        call.enqueue(object : Callback<KGetCityRes?> {
            override fun onResponse(call: Call<KGetCityRes?>, response: Response<KGetCityRes?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {

                        cityList = response.body()?.Response!!
                        installCity = ArrayList<String>()
                        installCityCode = ArrayList<String>()
                        installCity.add("Select Option")
                        installCityCode.add("0")
                        for (item in cityList) {
                            installCity.add(item.CityName +" ("+item.CityCode+")")
                            installCityCode.add(item.CityCode)
                        }
                        var cityPosition = 0
                        installCityCode.forEachIndexed { index, s ->
                            if (s == strCity) cityPosition = index
                        }

                        val adapter121 = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, installCity) }
                        adapter121?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        spCity.adapter = adapter121
                        spCity.setSelection(cityPosition)

                        spCity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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