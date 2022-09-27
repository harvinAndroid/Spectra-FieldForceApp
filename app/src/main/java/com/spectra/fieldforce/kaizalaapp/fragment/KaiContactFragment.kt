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
import androidx.fragment.app.Fragment
import com.spectra.fieldforce.R
import com.spectra.fieldforce.api.ApiClient
import com.spectra.fieldforce.api.ApiClientKaizala
import com.spectra.fieldforce.api.ApiInterface
import com.spectra.fieldforce.databinding.KaiFragContactBinding
import com.spectra.fieldforce.kaizalaapp.activity.KaizalaContactTabActivity
import com.spectra.fieldforce.kaizalaapp.model.*
import com.spectra.fieldforce.salesapp.model.*
import com.spectra.fieldforce.utils.AppConstants
import com.spectra.fieldforce.utils.Constants
import com.spectra.fieldforce.utils.KaizalaAppConstant
import com.spectra.fieldforce.utils.SalesAppConstants
import kotlinx.android.synthetic.main.kai_frag_contact.*
import kotlinx.android.synthetic.main.update_leadtoolbar.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern


class KaiContactFragment:Fragment(), View.OnClickListener{

    private lateinit var  binding: KaiFragContactBinding
    private lateinit var userName: String
    private lateinit var password:String
    private lateinit var inAnimation: AlphaAnimation
    private lateinit var outAnimation: AlphaAnimation
    private lateinit var competitorList : MutableList<CompResponse>
    private lateinit var competitorName : MutableList<String>
    private lateinit var source : MutableList<String>
    private lateinit var sourceList: MutableList<SrcData>
    private lateinit var strCompetitor :String
    private lateinit var strState :String
    private lateinit var strStatus :String
    private lateinit var strCity :String
    private lateinit var strArea :String
    private  var strSociety :String?=null
    private lateinit var planCategoryList : ArrayList<PlanCategoryData>
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
    lateinit var adapter: ArrayAdapter<*>


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
        contacttoolbar.flr.visibility=View.GONE

        val sp1: SharedPreferences? = context?.getSharedPreferences("Login", 0)
        userName = sp1?.getString(AppConstants.USERNAME, null).toString()
        password = sp1?.getString(AppConstants.PASSWORD, null).toString()

     //   itemListener()

       getCompetitor()
       getSource(KaizalaAppConstant.KAIZALA)
       getPlanCategory()

       listener()
        setAdapter()

    }

    private fun setAdapter(){

        val reasonAdapter =
            context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item,resources.getStringArray(R.array.list_of_conReason)) }
        reasonAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        spStatus.adapter = reasonAdapter

        val stateAdapter =
            context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item,resources.getStringArray(R.array.list_of_state)) }
        stateAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        spState.adapter = stateAdapter

        spStatus.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                val itemPosition = adapterView.selectedItemPosition
                strStatus= resources.getStringArray(R.array.list_of_conReasonValue)[itemPosition]
                Log.e("code", strStatus)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        spState.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                val itemPosition = adapterView.selectedItemPosition
                strState = SalesAppConstants.list_state_code[itemPosition]
                Log.e("code", strState)
                getCity("")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }



    }


    private fun validEmail(email: String): Boolean {
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }


    private fun listener(){
        binding.kContactSubmit.setOnClickListener {
            val fName = etPersonName.text.toString()
            val lName = etPersonName2.text.toString()
            val planCategory = spPlanCat.selectedItem.toString()
            val mobileOne = etMobileNum.text.toString()
            val mobileTwo = etMobileNum2.text.toString()
            val email = etEmail.text.toString()
            val source = spSource.selectedItem.toString()
            val compt = spCompt.selectedItem.toString()
            val remarks = etRemark.text.toString()



            if(fName.isBlank()){
                Toast.makeText(context,"Enter First Name",Toast.LENGTH_LONG).show()

            }else if(lName.isBlank()){
               Toast.makeText(context,"Enter Last Name",Toast.LENGTH_LONG).show()

            }else if(planCategory.isBlank()||planCategory=="Select Option"){
                Toast.makeText(context,"Select PlanCategory",Toast.LENGTH_LONG).show()

            }else if(strState.isBlank()||strState=="Select State"){
                Toast.makeText(context,"Select State",Toast.LENGTH_LONG).show()

            }else if(strCity.isBlank()||strCity=="Select Option"){
                Toast.makeText(context,"Select City",Toast.LENGTH_LONG).show()
            }else if(strArea.isBlank()||strArea=="Select Option"){
                Toast.makeText(context,"Select City",Toast.LENGTH_LONG).show()
            }else if(strSociety?.isBlank() == true ||strSociety=="Select Option"){
                Toast.makeText(context,"Select Society",Toast.LENGTH_LONG).show()
            }
            else if(mobileOne.isBlank()||(mobileOne.length!=10)){
                Toast.makeText(context,"Enter Mobile Number",Toast.LENGTH_LONG).show()

            }else if(email.isBlank()||(!validEmail(email))){
                Toast.makeText(context,"Enter Email",Toast.LENGTH_LONG).show()

            }else if(source.isBlank()||source=="Select Option"){
                Toast.makeText(context,"Select Source",Toast.LENGTH_LONG).show()

            }else if(compt.isBlank()||source=="Select Option"){
                Toast.makeText(context,"Select Competitor",Toast.LENGTH_LONG).show()

            }else if(strStatus.isBlank()||strStatus=="Select Option"){
                Toast.makeText(context,"Select Status",Toast.LENGTH_LONG).show()

            }
            else if(remarks.isBlank()){
                Toast.makeText(context,"Enter Remarks",Toast.LENGTH_LONG).show()

            }else {

                createKaiContact(
                    fName, lName, planCategory, mobileOne, mobileTwo, email,
                    source, compt, remarks
                )
            }
        }

    }

    private fun createKaiContact(
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
        val kcreateReq = KCreateConatactReq(Constants.KCREATECONTACT,Constants.KAUTH,strArea,"",
            KaizalaAppConstant.HOME,"", KaizalaAppConstant.KAIZALA,strCity,compt,strStatus,email,
            fName, "",lName,mobileOne,mobileTwo,password, planCategory, remarks,strSociety,
            source,strState,strStatus,Constants.USER+userName,"")

        val apiService = ApiClientKaizala.getClient().create(ApiInterface::class.java)
        val call = apiService.creaKaiContact(kcreateReq)
        call.enqueue(object : Callback<KContactRespo?> {
            override fun onResponse(call: Call<KContactRespo?>, response: Response<KContactRespo?>) {
                if (response.isSuccessful && response.body() != null) {
                    if (response.isSuccessful && response.body() != null) {
                        try {
                            if(response.body()?.Response?.StatusCode==1||response.body()?.Response?.StatusCode==200){
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
            }

            override fun onFailure(call: Call<KContactRespo?>, t: Throwable) {
                Log.e("RetroError", t.toString())
            }
        })
    }













private fun Validate(){

       // validEmail(email)

    }


  /*  private fun itemListener(){
        contactLayout.et_ctEmailId.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus) {
                val email =  contactLayout.et_ctEmailId.text.toString()
                val isValid = Patterns.EMAIL_ADDRESS.matcher(email).matches();
                if (!isValid) {
                    Toast.makeText(context, "Invalid Email", Toast.LENGTH_LONG).show()
                }
            }
        }
    }*/


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
                        val adapter12 = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, competitorName) }
                        adapter12?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        binding.spCompt.adapter = adapter12
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
            PlanCategoryRequest(Constants.GETPLAN_CATEGORY,Constants.AUTH_KEY,
                KaizalaAppConstant.HOME,password,userName)
       /* val call = apiService.getPlanCategory(getCategoryRequest)
        call.enqueue(object : Callback<GetPlanCategoryRes?> {
*/
            val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getPlanCategory(getCategoryRequest)
        call.enqueue(object : Callback<GetPlanCategoryRes?> {
            override fun onResponse(call: Call<GetPlanCategoryRes?>, response: Response<GetPlanCategoryRes?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        planCategoryList= response.body()?.Response?.Data!!
                        categoryName = ArrayList<String>()
                        categoryName.add("Select Option")
                        for (item in planCategoryList)
                            categoryName.add(item.CategoryName)
                        val adapter12 = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, categoryName) }
                        adapter12?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        spPlanCat.adapter = adapter12
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<GetPlanCategoryRes?>, t: Throwable) {
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

                            val adapter121 = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, Installarea)}
                            adapter121?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            searchArea.adapter = adapter121
                            searchArea.onItemSelectedListener =
                                object : AdapterView.OnItemSelectedListener {
                                    override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                                        strArea = InstallareaCode[i]
                                        getSociety(strArea)
                                    }
                                    override fun onNothingSelected(parent: AdapterView<*>?) {

                                    }
                                }
                        }

/*

                            val adapter12 = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, Installarea!!) }
                        searchArea.threshold=0
                        adapter12?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        searchArea.setAdapter(adapter12)
                        searchArea.setOnFocusChangeListener { _, b ->
                            if (b) searchArea.showDropDown()
                        }
*/

                      /*  searchArea.onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, _ ->
                            str_area = adapter12?.getItem(position)
                            val area = searchArea.text.toString()

                            val split = area.split("(")
                            val Areaid = split[1]
                            val AreaName = split[0]
                            var areaPosition=0
                            Installarea?.forEachIndexed { index, s ->
                                if(s==area)areaPosition=index
                                area.let { Log.e("idddddddddd", it) }
                                return@forEachIndexed
                            }
                            val areaId = Areaid.split(")")
                            str_add_area_code = areaId[0]
                            str_add_area_code?.let { Log.e("compid", it) }

                        }*/
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

                        val adapter121 = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, installSociety) }
                        adapter121?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        spSociety.adapter = adapter121
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

                        val adapter121 = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, installCity) }
                        adapter121?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        spCity.adapter = adapter121
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