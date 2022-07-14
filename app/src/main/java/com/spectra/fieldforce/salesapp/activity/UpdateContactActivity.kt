package com.spectra.fieldforce.salesapp.activity

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.spectra.fieldforce.R
import com.spectra.fieldforce.api.ApiClient
import com.spectra.fieldforce.api.ApiInterface
import com.spectra.fieldforce.databinding.SalescontactFragmentBinding
import com.spectra.fieldforce.salesapp.model.*
import com.spectra.fieldforce.utils.AppConstants
import com.spectra.fieldforce.utils.Constants
import com.spectra.fieldforce.utils.SalesConstant
import kotlinx.android.synthetic.main.contact_general_info.view.*
import kotlinx.android.synthetic.main.contact_remarks_row.view.*
import kotlinx.android.synthetic.main.lead_demo_fragment.*
import kotlinx.android.synthetic.main.lead_installation_address_row.view.*
import kotlinx.android.synthetic.main.sales_contact_address_row.view.*
import kotlinx.android.synthetic.main.sales_contact_address_row.view.et_spec_area
import kotlinx.android.synthetic.main.salescontact_fragment.*
import kotlinx.android.synthetic.main.update_lead_demo_fragment.*
import kotlinx.android.synthetic.main.update_lead_installation_address_row.*
import kotlinx.android.synthetic.main.update_lead_installation_address_row.view.*
import kotlinx.android.synthetic.main.update_leadtoolbar.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import java.util.regex.Pattern

class UpdateContactActivity: AppCompatActivity() , View.OnClickListener , AdapterView.OnItemSelectedListener,
    TimePickerDialog.OnTimeSetListener,DatePickerDialog.OnDateSetListener{
    lateinit var binding: SalescontactFragmentBinding

    private var source: ArrayList<String>? = null
    private var sourceList: MutableList<SrcData>? = null
    private var areaList: ArrayList<AreaData>? = null
    private var Installarea: ArrayList<String>? = null
    private var InstallareaCode: ArrayList<String>? = null
    private var cityList: ArrayList<CityData>? = null
    private var Installcity: ArrayList<String>? = null
    private var InstallcityCode: ArrayList<String>? = null
    private var state: ArrayList<String>? = null
    private var stateId: ArrayList<String>? = null
    private var inAnimation: AlphaAnimation? = null
    private var outAnimation: AlphaAnimation? = null
    private var buildingList: ArrayList<BuildData>? = null
    private var building: ArrayList<String>? = null
    private var buildingCode: ArrayList<String>? = null
    private var str_area: String? = null
    private var str_city: String? = null
    private var str_city_code: String? = null
    var strContactChnl: String? = null
    var strInstallBuild: String? = null
    var strInstallBuildCode: String? = null
    var str_lead_src: String? = null
    var date: String? = null
    var str_state: String? = null
    private var CompetitorList: ArrayList<CompetitorData>? = null
    private var CompetitorName: ArrayList<String>? = null
    var strContactId : String? = null
    private var PlanCategoryList: ArrayList<PlanCategoryData>? = null
    private var CategoryName: ArrayList<String>? = null

    var strplan: String? = null
    var strCompetitor: String? = null
    var strDNC: String? = null
    var strSource:String? = null
    var strDisposition: String? = null
    var strCompetitorName: String? = null
    var strCategoryName: String? = null
    var strStatusReason: String? = null
    var str_inst_state: String? = null

    var str_add_area_code: String? = null
    var userName: String? = null
    var password: String? = null
    var str_specificbuild: String? = null
    var strCity: String? = null
    var strArea: String? = null
    var day: Int?=null
    var month: Int?=null
    var year: Int?=null
    var hour: Int?=null
    var minute: Int?=null
    var myday: Int?=null
    var myMonth: Int?=null
    var myYear: Int?=null
    var myHour: Int?=null
    var myMinute: Int?=null
    var Building: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.salescontact_fragment)
        searchToolbarContact.rl_back.setOnClickListener(this)
        searchToolbarContact.tv_lang.text=AppConstants.UPDATE_CONTACT
        searchToolbarContact.flr.visibility=View.GONE
        val sp1: SharedPreferences = this.getSharedPreferences("Login", 0)
        userName = sp1.getString(AppConstants.USERNAME, null)
        password = sp1.getString(AppConstants.PASSWORD, null)
        val extras = intent.extras
        if (extras != null) {
            strContactId = extras.getString("ContactID")
        }
        listener()

        itemListener()
        setAdpter()

        getContact()
        contactSave.setOnClickListener {
            Validate()
        }
        searchToolbarContact.flr.setOnClickListener {
            moveLead()
        }
        contactLayout.et_ctfollowUp.setOnClickListener {
            val calendar = Calendar.getInstance()
            year = calendar[Calendar.YEAR]
            month = calendar[Calendar.MONTH]
            day = calendar[Calendar.DAY_OF_MONTH]
            val datePickerDialog =
                year?.let { it1 -> month?.let { it2 ->
                    day?.let { it3 ->
                        DatePickerDialog(this, this, it1,
                            it2, it3
                        )
                    }
                } }
            datePickerDialog?.show()

        }
    }

    private fun moveLead(){
        val intent = Intent(this, UpdateLeadActivity::class.java)
        val bundle = Bundle()
        bundle.putString("ContactID",strContactId )
        bundle.putString("Status","1")
        intent.putExtras(bundle)
        startActivity(intent)
    }

    private fun validEmail(email: String): Boolean {
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }


    private fun Validate() {
        val firstName = contactLayout.et_ctFirstName.text.toString()
        val lastName = contactLayout.et_ctLastName.text.toString()
        val mobile = contactLayout.et_ctMobileNumber.text.toString()
        val mobile2 = contactLayout.et_ctMobileNum2.text.toString()
        val email = contactLayout.et_ctEmailId.text.toString()
        val call = contactLayout.et_ctCallAttempt.text.toString()
        val followup = contactLayout.et_ctfollowUp.text.toString()
        val compaignName = contactLayout.et_ctCompaignName.text.toString()
        val planCategory = contactLayout.et_ctPlanCategory.text.toString()
        val reason = contactLayout.et_ctStatusReason.text.toString()
        val disposition = contactLayout.et_ctDisposition.text.toString()
        val state = layout_ContactAddress.et_ContactState.text.toString()
        val city = layout_ContactAddress.et_ContactCity.text.toString()
        val specificArea = layout_ContactAddress.et_ContactSpecificArea.text.toString()
        val specificBuilding = layout_ContactAddress.et_Contactspecific_building.text.toString()
        val remark = layout_ContactRemarks.et_contactRemark.text.toString()

        validEmail(email)
        if(firstName.isBlank()){
            Toast.makeText(this, "Please Enter First Name", Toast.LENGTH_SHORT).show()
        }else if(lastName.isBlank()){
            Toast.makeText(this, "Please Enter Last Name", Toast.LENGTH_SHORT).show()
        }
        else if(mobile.isBlank()){
            Toast.makeText(this, "Please Enter Mobile Number", Toast.LENGTH_SHORT).show()
        }else if(email.isBlank()){
            Toast.makeText(this, "Please Enter Email ID", Toast.LENGTH_SHORT).show()
        }else if(call.isBlank()){
            Toast.makeText(this, "Please Enter Call Attempted", Toast.LENGTH_SHORT).show()
        }else if(strContactChnl?.isBlank()==true||strContactChnl=="Select Channel"||strContactChnl=="null"){
            Toast.makeText(this, "Please Select Lead Channel", Toast.LENGTH_SHORT).show()
        }else if(str_lead_src?.isBlank()==true||str_lead_src=="null"){
            Toast.makeText(this, "Please Select Lead Source", Toast.LENGTH_SHORT).show()
        }else if(planCategory.isBlank()||planCategory=="Select Option"||planCategory=="null"){
            Toast.makeText(this, "Please Select Plan Category", Toast.LENGTH_SHORT).show()
        }else if(reason.isBlank()||reason=="Select Option"||reason=="null"){
            Toast.makeText(this, "Please Select Reason", Toast.LENGTH_SHORT).show()
        }else if(disposition.isBlank()||disposition=="Select Option"||disposition=="null"){
            Toast.makeText(this, "Please Select Disposition", Toast.LENGTH_SHORT).show()
        }else if(state.isBlank()||state=="Select State"||state=="null"){
            Toast.makeText(this, "Please Select State", Toast.LENGTH_SHORT).show()
        }else if(city.isBlank()||city=="Select City"||city=="null"){
            Toast.makeText(this, "Please Select City", Toast.LENGTH_SHORT).show()
        }else if(str_add_area_code?.isBlank() == true ||str_add_area_code=="0"||str_add_area_code=="null"){
            Toast.makeText(this, "Please Select Area", Toast.LENGTH_SHORT).show()
        }else if(strInstallBuildCode?.isBlank() == true ||strInstallBuildCode=="0"||strInstallBuildCode=="null"){
            Toast.makeText(this, "Please Select Building", Toast.LENGTH_SHORT).show()
        }else {

            updateContact(
                firstName,
                lastName,
                mobile,
                mobile2,
                email,
                call,
                remark,
                specificArea,
                specificBuilding,
                compaignName
            )
        }

    }


    private fun itemListener() {
        binding.contactLayout.etCtChannel.setOnClickListener { binding.contactLayout.spCtChannel.performClick() }
        binding.contactLayout.spCtChannel.onItemSelectedListener = this
        contactLayout.et_ctCompetitorName.setOnClickListener { contactLayout.sp_ctCompetitorName.performClick() }
        contactLayout.sp_ctCompetitorName.onItemSelectedListener = this

        contactLayout.et_ctSource.setOnClickListener { contactLayout.sp_ctSource.performClick() }
        contactLayout.sp_ctSource.onItemSelectedListener = this
        contactLayout.et_ctStatusReason.setOnClickListener { contactLayout.sp_ctStatusReason.performClick() }
        contactLayout.sp_ctStatusReason.onItemSelectedListener = this
        contactLayout.et_ctPlanCategory.setOnClickListener { contactLayout.sp_ctPlanCategory.performClick() }
        contactLayout.sp_ctPlanCategory.onItemSelectedListener = this
        contactLayout.et_ctDisposition.setOnClickListener { contactLayout.sp_ctDisposition.performClick() }
        contactLayout.sp_ctDisposition.onItemSelectedListener = this
        contactLayout.et_contactDNC.setOnClickListener { contactLayout.sp_ctDNCNum.performClick() }
        contactLayout.sp_ctDNCNum.onItemSelectedListener = this
        layout_ContactAddress.et_ContactState.setOnClickListener { layout_ContactAddress.sp_ContactState.performClick() }
        layout_ContactAddress.sp_ContactState.onItemSelectedListener = this
        layout_ContactAddress.et_ContactCity.setOnClickListener { layout_ContactAddress.sp_ContactCity.performClick() }
        layout_ContactAddress.sp_ContactCity.onItemSelectedListener = this
       /* layout_ContactAddress.et_ContactArea.setOnClickListener { layout_ContactAddress.sp_ContactArea.performClick() }
        layout_ContactAddress.sp_ContactArea.onItemSelectedListener = this
        layout_ContactAddress.et_Contactbuilding.setOnClickListener { layout_ContactAddress.sp_Contactbuilding_nm.performClick() }
        layout_ContactAddress.sp_Contactbuilding_nm.onItemSelectedListener = this*/

        contactLayout.et_ctEmailId.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                val email = contactLayout.et_ctEmailId.text.toString()
                val isValid = Patterns.EMAIL_ADDRESS.matcher(email).matches();
                if (!isValid) {
                    Toast.makeText(this, "Invalid Email", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun setAdpter() {
        val channel = ArrayAdapter(this@UpdateContactActivity, android.R.layout.simple_spinner_item, SalesConstant.list_of_channel)
        channel.setDropDownViewResource(android.R.layout.simple_spinner_item)
        binding.contactLayout.spCtChannel.adapter = channel
    }

    fun getSource(str_lead_chnl: String?) {
        val getLeadSourceRequest =
            str_lead_chnl?.let {
                GetLeadSourceRequest(
                    Constants.GET_SOURCE, Constants.AUTH_KEY,
                    it, userName, password
                )
            }
        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getLeadSource(getLeadSourceRequest)
        call.enqueue(object : Callback<GetLeadSourceResp?> {
            override fun onResponse(
                call: Call<GetLeadSourceResp?>,
                response: Response<GetLeadSourceResp?>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        val img = response.body()?.Response?.Message
                        img?.let { Log.e("image", it) }
                        sourceList = response.body()?.Response?.Data
                        source = ArrayList<String>()
                        //source!!.add("Select ")
                        for (item in sourceList!!)
                            source?.add(item.SourceName)

                        var sourcePosition=0
                        source?.forEachIndexed { index, s ->
                            if(s==strSource)sourcePosition=index
                        }
                        val adapter12 = ArrayAdapter(this@UpdateContactActivity, android.R.layout.simple_spinner_item,
                            source!!
                        )
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        contactLayout.sp_ctSource.adapter = adapter12
                        contactLayout.sp_ctSource.setSelection(sourcePosition)
                        adapter12.notifyDataSetChanged()
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


    fun listener() {
        contactSave.visibility = View.VISIBLE
        linearContactInfo.visibility = View.VISIBLE
        linearOne.setOnClickListener {
            linearContactInfo.visibility = View.VISIBLE
            linearAdd.visibility = View.GONE
            linearRemarkDetails.visibility = View.GONE
        }
        linearFourAddress.setOnClickListener {
            linearContactInfo.visibility = View.GONE
            linearAdd.visibility = View.VISIBLE
            linearRemarkDetails.visibility = View.GONE
        }
        linearNine.setOnClickListener {
            linearContactInfo.visibility = View.GONE
            linearAdd.visibility = View.GONE
            linearRemarkDetails.visibility = View.VISIBLE
        }
    }

    override fun onClick(p0: View?) {
        if (p0?.id == R.id.rl_back) {
            back()
        }
    }

    fun getContact () {
        inProgress()
        val getContactRequest = GetContactRequest(Constants.GETCONTACT,Constants.AUTH_KEY,strContactId,password,userName)

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getContact(getContactRequest)
        call.enqueue(object : Callback<GetContactResponse?> {
            @SuppressLint("SimpleDateFormat", "SetTextI18n")
            override fun onResponse(call: Call<GetContactResponse?>, response: Response<GetContactResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        outProgress()
                        if(response.body()?.StatusCode==200) {
                            contactLayout.et_ctFullName.setText(response.body()?.Response?.Data?.get(0)?.FullName)
                            contactLayout.et_ctFirstName.setText(response.body()?.Response?.Data?.get(0)?.FirstName)
                            contactLayout.et_ctLastName.setText(response.body()?.Response?.Data?.get(0)?.LastName)
                            contactLayout.et_ctMobileNumber.setText(response.body()?.Response?.Data?.get(0)?.MobileNumber)
                            contactLayout.et_ctMobileNum2.setText(response.body()?.Response?.Data?.get(0)?.MobileNumber2)
                            contactLayout.et_ctEmailId.setText(response.body()?.Response?.Data?.get(0)?.EmailAddress)
                            contactLayout.et_ctCallAttempt.setText(response.body()?.Response?.Data?.get(0)?.CallAttempted)
                            contactLayout.et_ctCompaignName.setText(response.body()?.Response?.Data?.get(0)?.CampaignName)
                            layout_ContactAddress.et_ContactSpecificArea.setText(response.body()?.Response?.Data?.get(0)?.SpecifyArea)
                            layout_ContactAddress.et_Contactspecific_building.setText(response.body()?.Response?.Data?.get(0)?.Specifybuilding)
                            strArea = response.body()?.Response?.Data?.get(0)?.Area
                            strCity = response.body()?.Response?.Data?.get(0)?.City
                            Building = response.body()?.Response?.Data?.get(0)?.Building
                            strSource = response.body()?.Response?.Data?.get(0)?.Source
                            strCompetitor = response.body()?.Response?.Data?.get(0)?.CompetitorName
                            strplan = response.body()?.Response?.Data?.get(0)?.PlanCategory
                            layout_ContactRemarks.et_contactRemark.setText(response.body()?.Response?.Data?.get(0)?.Remark)
                            str_add_area_code = response.body()?.Response?.Data?.get(0)?.AreaId
                            strInstallBuildCode = response.body()?.Response?.Data?.get(0)?.BuildingId
                            layout_ContactAddress.et_ContactArea.setText("$strArea($str_add_area_code)")
                            layout_ContactAddress.et_Contactbuilding.setText("$Building($strInstallBuildCode)")
                            val strContactstate = response.body()?.Response?.Data?.get(0)?.State
                            var cntstatePosition = 0
                            SalesConstant.list_of_state.forEachIndexed { index, s ->
                                if (s == strContactstate) cntstatePosition = index
                            }
                            val cntstateAdapter = ArrayAdapter(this@UpdateContactActivity, android.R.layout.simple_spinner_item, SalesConstant.list_of_state)
                            cntstateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            layout_ContactAddress.sp_ContactState.adapter = cntstateAdapter
                            layout_ContactAddress.sp_ContactState.setSelection(cntstatePosition)
                            cntstateAdapter.notifyDataSetChanged()

                            val strchannel = response.body()?.Response?.Data?.get(0)?.Channel
                            var channePosition = 0
                            SalesConstant.list_of_channel.forEachIndexed { index, s ->
                                if (s == strchannel) channePosition = index
                            }
                            val adapterchannel = ArrayAdapter(this@UpdateContactActivity, android.R.layout.simple_spinner_item, SalesConstant.list_of_channel)
                            adapterchannel.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            contactLayout.sp_ctChannel?.adapter = adapterchannel
                            contactLayout.sp_ctChannel.setSelection(channePosition)
                            adapterchannel.notifyDataSetChanged()

                            val reason = response.body()?.Response?.Data?.get(0)?.StatusReason
                            if(reason=="Lead Created/Closed"){
                                searchToolbarContact.flr.visibility=View.VISIBLE
                                locked()
                            }
                            var reasonPosition = 0
                            resources.getStringArray(R.array.list_of_conReason).forEachIndexed { index, s ->
                                if (s == reason) reasonPosition = index
                            }
                            val reasonAdapter = ArrayAdapter(this@UpdateContactActivity, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.list_of_conReason))
                            reasonAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            contactLayout.sp_ctStatusReason?.adapter = reasonAdapter
                            contactLayout.sp_ctStatusReason.setSelection(reasonPosition)
                            reasonAdapter.notifyDataSetChanged()

                            val disposition = response.body()?.Response?.Data?.get(0)?.Disposition
                            var dispositionPosition = 0
                            resources.getStringArray(R.array.listDisposition).forEachIndexed { index, s ->
                                if (s == disposition) dispositionPosition = index
                            }
                            val dispositionAdapter = ArrayAdapter(this@UpdateContactActivity, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.listDisposition))
                            dispositionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            contactLayout.sp_ctDisposition?.adapter = dispositionAdapter
                            contactLayout.sp_ctDisposition.setSelection(dispositionPosition)
                            dispositionAdapter.notifyDataSetChanged()

                            val dnc = response.body()?.Response?.Data?.get(0)?.DncNumber
                            var dncPosition = 0
                            resources.getStringArray(R.array.listDNC).forEachIndexed { index, s ->
                                if (s == dnc) dncPosition = index
                            }
                            val dncAdapter = ArrayAdapter(this@UpdateContactActivity, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.listDNC))
                            dncAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            contactLayout.sp_ctDNCNum?.adapter = dncAdapter
                            contactLayout.sp_ctDNCNum.setSelection(dncPosition)
                            dncAdapter.notifyDataSetChanged()
                            getPlanCategory()
                            getCompetitor()
                            val update = response.body()?.Response?.Data?.get(0)?.FollowupDate
                            if (update != null) {
                                if(update.isNotEmpty()){
                                    val split = update.split("-")
                                    val dat = split[0]
                                    val month = split[1]
                                    val year = split[2]
                                    contactLayout.et_ctfollowUp.setText("$dat-$month-$year")
                                    date=("$year-$month-$dat")
                                }
                            }
                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<GetContactResponse?>, t: Throwable) {
                binding.contactProgressLayout.progressOverlay.visibility = View.GONE
                Log.e("RetroError", t.toString())
            }
        })
    }

    private fun locked(){
        contactLayout.et_ctFullName.isEnabled=false
        contactLayout.et_ctFirstName.isFocusable=(false)
        contactLayout.et_ctMobileNum2.isFocusable=(false)
        contactLayout.et_ctMobileNumber.isFocusable=(false)
        contactLayout.et_ctFullName.isFocusableInTouchMode = false
        contactLayout.et_ctLastName.isEnabled=false
        contactLayout.et_ctLastName.isFocusableInTouchMode = false
        contactLayout.et_ctfollowUp.isEnabled=false
        contactLayout.et_ctfollowUp.isFocusableInTouchMode = false
        layout_ContactAddress.et_Contactspecific_building.isEnabled=false
        contactLayout.et_ctChannel.isEnabled=false
        contactLayout.et_ctCompaignName.isEnabled=false
        contactLayout.et_ctCallAttempt.isEnabled=false
        contactLayout.et_ctEmailId.isEnabled=false
        contactLayout.et_contactDNC.isEnabled=false
        contactLayout.et_ctDisposition.isEnabled=false
        contactLayout.et_ctPlanCategory.isEnabled=false
        contactLayout.et_ctStatusReason.isEnabled=false
        contactLayout.et_ctCompetitorName.isEnabled=false
        layout_ContactAddress.et_ContactCity.isEnabled=false
        layout_ContactAddress.et_ContactState.isEnabled=false
        layout_ContactAddress.et_Contactbuilding.isEnabled=false
        layout_ContactAddress.et_ContactArea.isEnabled=false
        layout_ContactAddress.et_ContactSpecificArea.isEnabled=false
        layout_ContactRemarks.et_contactRemark.isEnabled=false
        layout_ContactRemarks.et_contactRemark.isFocusableInTouchMode = false
        contactSave.visibility=View.GONE

    }

    fun back() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
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

    fun next() {
        Intent(this, ContactTabActivity::class.java).also {
            startActivity(it)
        }
    }

    private fun inProgress() {
        inAnimation = AlphaAnimation(0f, 1f)
        inAnimation?.duration = 200
        binding.contactProgressLayout.progressOverlay.animation = inAnimation
        binding.contactProgressLayout.progressOverlay.visibility = View.VISIBLE
    }

    fun updateContact(
        firstName: String,
        lastName: String,
        mobile: String,
        mobile2: String,
        email: String,
        call: String,
        remark: String,
        specificArea: String,
        specificBuilding: String,
        compaignName: String
    ) {

        inProgress()
        val createContactRequest = CreateContactRequest(
            Constants.UPDATECONTACT,
            Constants.AUTH_KEY,
            str_add_area_code,
            strInstallBuildCode,
            call,
            strContactChnl,
            str_city_code,
            strCompetitorName,
            strDisposition,
            email,
            firstName,
            date,
            lastName,
            mobile,
            mobile2,
            password,
            strCategoryName,
            remark,
            str_lead_src,
            specificArea,
            specificBuilding,
            str_inst_state,
            strStatusReason,
            userName,
            "Business",
            strDNC,
            compaignName,
            strContactId
        )

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.createContact(createContactRequest)
        call.enqueue(object : Callback<CreateLeadResponse?> {
            override fun onResponse(
                call: Call<CreateLeadResponse?>,
                response: Response<CreateLeadResponse?>
            ){
                if (response.isSuccessful && response.body() != null) {
                    try {
                        outProgress()
                        val img = response.body()?.Response?.Message
                        val id = response.body()?.Response?.LeadId
                        img?.let { Log.e("image", it) }
                        Toast.makeText(this@UpdateContactActivity, img, Toast.LENGTH_SHORT).show()
                        if (response.body()?.Response?.StatusCode == ("200")) {
                            next()
                        }else {
                            Toast.makeText(this@UpdateContactActivity, img, Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<CreateLeadResponse?>, t: Throwable) {
                binding.contactProgressLayout.progressOverlay.visibility = View.GONE
                Log.e("RetroError", t.toString())
            }
        })
    }

    private fun outProgress() {
        outAnimation = AlphaAnimation(1f, 0f)
        outAnimation?.duration = 200
        binding.contactProgressLayout.progressOverlay.animation = outAnimation
        binding.contactProgressLayout.progressOverlay.visibility = View.GONE

    }

    fun getCompetitor() {
        val getLeadSourceRequest =
            GetLeadSourceRequest(
                Constants.GET_COMPETITOR, Constants.AUTH_KEY,
                "", userName, password
            )

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getCompetitorList(getLeadSourceRequest)
        call.enqueue(object : Callback<GetCompetitorResponse?> {
            override fun onResponse(
                call: Call<GetCompetitorResponse?>,
                response: Response<GetCompetitorResponse?>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        val img = response.body()?.Response?.Message
                        img?.let { Log.e("image", it) }
                        CompetitorList = response.body()?.Response?.Data
                        CompetitorName = ArrayList<String>()
                        CompetitorName?.add("Select Option")
                        for (item in CompetitorList!!)
                            CompetitorName?.add(item.Name)

                        var competitorPosition=0
                        CompetitorName!!.forEachIndexed { index, s ->
                            if(s==strCompetitor)competitorPosition=index
                        }
                        val adapter12 = ArrayAdapter(this@UpdateContactActivity, android.R.layout.simple_spinner_item, CompetitorName!!)
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        contactLayout.sp_ctCompetitorName.adapter = adapter12
                        contactLayout.sp_ctCompetitorName.setSelection(competitorPosition)
                        adapter12.notifyDataSetChanged()

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<GetCompetitorResponse?>, t: Throwable) {
                Log.e("RetroError", t.toString())
            }
        })
    }

    fun getPlanCategory() {
        val getCategoryRequest =
            PlanCategoryRequest(
                Constants.GETPLAN_CATEGORY, Constants.AUTH_KEY,
                "Business", password, userName
            )

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getPlanCategory(getCategoryRequest)
        call.enqueue(object : Callback<GetPlanCategoryRes?> {
            override fun onResponse(
                call: Call<GetPlanCategoryRes?>,
                response: Response<GetPlanCategoryRes?>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        val img = response.body()?.Response?.Message
                        img?.let { Log.e("image", it) }
                        PlanCategoryList = response.body()?.Response?.Data
                        CategoryName = ArrayList<String>()
                        CategoryName?.add("Select Option")
                        for (item in PlanCategoryList!!)
                            CategoryName?.add(item.CategoryName)
                        var planPosition=0
                        CategoryName!!.forEachIndexed { index, s ->
                            if(s==strplan)planPosition=index
                        }
                        val adapter12 = ArrayAdapter(this@UpdateContactActivity, android.R.layout.simple_spinner_item, CategoryName!!)
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        contactLayout.sp_ctPlanCategory.adapter = adapter12
                        contactLayout.sp_ctPlanCategory.setSelection(planPosition)
                        adapter12.notifyDataSetChanged()

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

    private fun getBuilding(str_inst_area: String?, code: String?) {
        val getLeadBuildingRequest = GetLeadBuildingRequest(
            Constants.GET_BUILDING,
            Constants.AUTH_KEY,
            code,
            str_inst_area,
            password,
            userName
        )

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getleadBuilding(getLeadBuildingRequest)
        call.enqueue(object : Callback<GetLeadBuildingResponse?> {
            override fun onResponse(
                call: Call<GetLeadBuildingResponse?>,
                response: Response<GetLeadBuildingResponse?>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        //  val msg = response.body()!!.Response.Message
                        buildingList = response.body()?.Response?.Data
                        building = ArrayList<String>()
                        buildingCode = ArrayList<String>()
                       /* building?.add("Select Building")
                        buildingCode?.add("")*/
                        for (item in buildingList!!) {
                            building?.add(item.BuildingName+"("+item.BuildingCode)
                          /*  item.BuildingName?.let { building?.add(it) }
                            item.BuildingCode?.let { buildingCode?.add(it) }*/
                        }

                        val adapter12 = ArrayAdapter(this@UpdateContactActivity, android.R.layout.simple_spinner_item, building!!)
                        layout_ContactAddress.et_Contactbuilding.threshold=0
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        layout_ContactAddress.et_Contactbuilding.setAdapter(adapter12)

                        layout_ContactAddress.et_Contactbuilding.setOnFocusChangeListener { _, b ->
                            if (b) layout_ContactAddress.et_Contactbuilding.showDropDown()
                        }
                        layout_ContactAddress.et_Contactbuilding.onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, _ ->
                            strInstallBuild = adapter12.getItem(position)
                          //  strArea = adapter12.getItem(position)
                            val split = strInstallBuild?.split("(")
                            val buildingId = split?.get(1)
                            val buildingName = split?.get(0)
                            var buildingPosition=0
                            building?.forEachIndexed { index, s ->
                                if(s==strInstallBuild)buildingPosition=index
                                strInstallBuild.let { it?.let { it1 -> Log.e("idddddddddd", it1) } }
                                return@forEachIndexed
                            }
                            val areaId = buildingId?.split(")")
                            strInstallBuildCode = areaId?.get(0)
                             /* strInstallBuildCode = buildingCode?.get(position)*/
                            if(buildingName=="Other"){
                                layout_ContactAddress.et_Contactspecific_building.visibility=View.VISIBLE
                            }else{
                                layout_ContactAddress.et_Contactspecific_building.visibility=View.GONE
                            }
                        }


                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<GetLeadBuildingResponse?>, t: Throwable) {
                Log.e("RetroError", t.toString())
            }
        })
    }


    fun getArea(str_city: String?, str_city_code: String) {
        val getLeadAreaRequest = str_city?.let {
            GetLeadAreaRequest(
                Constants.Get_AREA, Constants.AUTH_KEY, str_city_code,
                it, "", userName, password, true
            )
        }

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getLeadArea(getLeadAreaRequest)
        call.enqueue(object : Callback<GetLeadAreaRes?> {
            override fun onResponse(
                call: Call<GetLeadAreaRes?>,
                response: Response<GetLeadAreaRes?>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        val img = response.body()?.Response?.Message
                        img?.let { Log.e("image", it) }
                        areaList = response.body()?.Response?.Data
                        Installarea = ArrayList<String>()
                        InstallareaCode = ArrayList<String>()
                        Installarea?.add("Select Area")
                      /*
                        InstallareaCode?.add("")*/
                        for (item in areaList!!) {
                            Installarea?.add(item.AreaName +" ("+item.AreaCode+")")
                           /* item.AreaName?.let { Installarea?.add(it) }
                            item.AreaCode?.let { InstallareaCode?.add(it) }*/
                        }

                        val adapter12 = ArrayAdapter(this@UpdateContactActivity, android.R.layout.simple_spinner_item, Installarea!!)
                        layout_ContactAddress.et_ContactArea.threshold=0
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        layout_ContactAddress.et_ContactArea.setAdapter(adapter12)

                        layout_ContactAddress.et_ContactArea.setOnFocusChangeListener { _, b ->
                            if (b) layout_ContactAddress.et_ContactArea.showDropDown()
                        }

                        layout_ContactAddress.et_ContactArea.onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, _ ->
                            strArea = adapter12.getItem(position)
                            val split = strArea?.split("(")
                            val Area = split?.get(0)
                            val Areaid = split?.get(1)
                            var areaPosition=0
                            Installarea?.forEachIndexed { index, s ->
                                if(s==strArea)areaPosition=index
                                strArea.let { it?.let { it1 -> Log.e("idddddddddd", it1) } }
                                return@forEachIndexed
                            }
                            val areaId = Areaid?.split(")")
                            str_add_area_code = areaId?.get(0)
                            str_add_area_code?.let { Log.e("compid", it) }
                            if (Area == "Other") {
                                layout_ContactAddress.et_spec_area.visibility = View.VISIBLE
                            } else {
                                layout_ContactAddress.et_spec_area.visibility = View.GONE
                            }
                            getBuilding(str_area,str_add_area_code)
                          /* str_add_area_code = InstallareaCode?.get(position)


                            val str: String = layout_ContactAddress.et_ContactArea.text.toString()
                            val index: Int? = str_area?.indexOf(str)
                            str_add_area_code = index?.let { InstallareaCode?.get(it) }
                            if (str_area == "Other") {
                                layout_ContactAddress.et_spec_area.visibility = View.VISIBLE
                            } else {
                                layout_ContactAddress.et_spec_area.visibility = View.GONE
                            }
                            getBuilding(str_area,str_add_area_code)*/
                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<GetLeadAreaRes?>, t: Throwable) {
                Log.e("RetroError", t.toString())
            }
        })
    }


    fun getCity(statecode: String?) {
        val getCityRequest =
            GetCityRequest(Constants.GET_CITY, Constants.AUTH_KEY, password, statecode, userName)

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getCityList(getCityRequest)
        call.enqueue(object : Callback<GetCityResponse?> {
            override fun onResponse(
                call: Call<GetCityResponse?>,
                response: Response<GetCityResponse?>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        val img = response.body()?.Response?.Message
                        img?.let { Log.e("image", it) }
                        cityList = response.body()?.Response?.Data
                        Installcity = ArrayList<String>()
                        InstallcityCode = ArrayList<String>()
                        Installcity?.add("Select City")
                        InstallcityCode?.add("")
                        for (item in cityList!!) {
                            Installcity?.add(item.CityName)
                            InstallcityCode?.add(item.CityCode)
                        }
                        var cityPosition = 0
                        Installcity?.forEachIndexed { index, s ->
                            if (s == strCity) cityPosition = index
                            return@forEachIndexed
                        }

                        val adapter121 = ArrayAdapter(this@UpdateContactActivity, android.R.layout.simple_spinner_item, Installcity!!)
                        adapter121.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        layout_ContactAddress.sp_ContactCity.adapter = adapter121
                        layout_ContactAddress.sp_ContactCity.setSelection(cityPosition)
                        adapter121.notifyDataSetChanged()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<GetCityResponse?>, t: Throwable) {
                Log.e("RetroError", t.toString())
            }
        })
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (parent?.id == R.id.sp_ctChannel) {
            binding.contactLayout.etCtChannel.setText(SalesConstant.list_of_channel[position])
            strContactChnl = SalesConstant.list_of_channel[position]
            getSource(strContactChnl)
        } else if (parent?.id == R.id.sp_ctSource) {
            contactLayout.et_ctSource.setText(source?.get(position))
            str_lead_src = source?.get(position)
        } /*else if (parent?.id == R.id.sp_ContactArea) {
            layout_ContactAddress.et_ContactArea.setText(Installarea?.get(position))
            str_area = Installarea?.get(position).toString()
            str_add_area_code = InstallareaCode?.get(position)
            getBuilding(str_area, str_add_area_code)
            if (str_area == "Other") {
                layout_ContactAddress.et_spec_area.visibility = View.VISIBLE
            } else {
                layout_ContactAddress.et_spec_area.visibility = View.GONE
            }
        } */else if (parent?.id == R.id.sp_ContactCity) {
            layout_ContactAddress.et_ContactCity.setText(Installcity?.get(position))
            str_city = Installcity?.get(position).toString()
            str_city_code = InstallcityCode?.get(position)
            getArea(str_city, str_city_code.toString())
        } else if (parent?.id == R.id.sp_ContactState) {
            layout_ContactAddress.et_ContactState.setText(SalesConstant.list_of_state[position])
            str_state = SalesConstant.list_of_state[position]
            str_inst_state = SalesConstant.list_state_code[position]
            getCity(str_inst_state)
        } /*else if (parent?.id == R.id.sp_Contactbuilding_nm) {
            layout_ContactAddress.et_Contactbuilding.setText(building?.get(position))
            strInstallBuild = building?.get(position)
            strInstallBuildCode = buildingCode?.get(position)
            if (strInstallBuild == "Other") {
                layout_ContactAddress.et_specific_build.visibility = View.VISIBLE
            } else {
                layout_ContactAddress.et_specific_build.visibility = View.GONE
            }
        }*/ else if (parent?.id == R.id.sp_ctStatusReason) {
            contactLayout.et_ctStatusReason.setText(
                resources.getStringArray(R.array.list_of_conReason)[position]
            )
            strStatusReason =
                resources.getStringArray(R.array.list_of_conReasonValue)[position].toString()
            if (strStatusReason == "111260000") {
                contactLayout.follow.visibility = View.VISIBLE
            } else {
                contactLayout.follow.visibility = View.GONE
                date=""
            }
        } else if (parent?.id == R.id.sp_ctCompetitorName) {
            contactLayout.et_ctCompetitorName.setText((CompetitorName?.get(position)))
            strCompetitorName = (CompetitorName?.get(position))
        } else if (parent?.id == R.id.sp_ctPlanCategory) {
            contactLayout.et_ctPlanCategory.setText((CategoryName?.get(position)))
            strCategoryName = (CategoryName?.get(position))
        } else if (parent?.id == R.id.sp_ctDisposition) {
            contactLayout.et_ctDisposition.setText(
                resources.getStringArray(R.array.listDisposition)[position]
            )
            strDisposition =
                resources.getStringArray(R.array.listDispositionValue)[position].toString()
        } else if (parent?.id == R.id.sp_ctDNCNum) {
            contactLayout.et_contactDNC.setText(
                resources.getStringArray(R.array.listDNC)[position]
            )
            strDNC = resources.getStringArray(R.array.list_of_gstval)[position].toString()
        }


    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }



    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        myYear = year
        myday = day
        myMonth = month+1
        val c = Calendar.getInstance()
        hour = c[Calendar.HOUR]
        minute = c[Calendar.MINUTE]
        val timePickerDialog = TimePickerDialog(
          this,
            this,
            hour!!,
            minute!!,
            DateFormat.is24HourFormat(this)
        )
        timePickerDialog.show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        myHour = hourOfDay
        myMinute = minute
        contactLayout.et_ctfollowUp.setText(myday.toString()+"-"+myMonth+"-"+myYear+" "+myHour+":"+myMinute+":"+"00".trimIndent())
        date=(myYear.toString()+"-"+myMonth+"-"+myday+" "+myHour+":"+myMinute+":"+"00".trimIndent())
    }

}