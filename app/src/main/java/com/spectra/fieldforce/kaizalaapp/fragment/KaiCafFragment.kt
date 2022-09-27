package com.spectra.fieldforce.kaizalaapp.fragment


import android.Manifest
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.spectra.fieldforce.BuildConfig
import com.spectra.fieldforce.R
import com.spectra.fieldforce.activity.BaseActivity
import com.spectra.fieldforce.api.ApiClient
import com.spectra.fieldforce.api.ApiClientKaizala
import com.spectra.fieldforce.api.ApiInterface
import com.spectra.fieldforce.databinding.KaiFragCafBinding
import com.spectra.fieldforce.kaizalaapp.activity.KaizalaCafTabActivity
import com.spectra.fieldforce.kaizalaapp.activity.KaizalaContactTabActivity
import com.spectra.fieldforce.kaizalaapp.model.*
import com.spectra.fieldforce.kaizalaapp.model.DocumentData
import com.spectra.fieldforce.kaizalaapp.model.DocumentRequired
import com.spectra.fieldforce.kaizalaapp.model.PaymentDetail
import com.spectra.fieldforce.salesapp.model.*
import com.spectra.fieldforce.utils.*
import kotlinx.android.synthetic.main.caf_demo_fragment.*
import kotlinx.android.synthetic.main.caf_otherinfo_row.view.*
import kotlinx.android.synthetic.main.caf_payment_details_row.view.*
import kotlinx.android.synthetic.main.kai_create_lead.*
import kotlinx.android.synthetic.main.kai_frag_caf.*
import kotlinx.android.synthetic.main.update_leadtoolbar.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.ArrayList

class KaiCafFragment:Fragment(), View.OnClickListener{

    private lateinit var  binding: KaiFragCafBinding
    private lateinit var userName: String
    private lateinit var password:String
    private lateinit var strLeadId:String
    private lateinit var inAnimation: AlphaAnimation
    private lateinit var outAnimation: AlphaAnimation
    private lateinit var source : MutableList<String>
    private lateinit var sourceList: MutableList<SrcData>
    private lateinit var cityList : MutableList<KCityResponse>
    private lateinit var Installcity : MutableList<String>
    private lateinit var InstallcityCode : MutableList<String>
    private lateinit var areaList : MutableList<KAreaResponse>
    private lateinit var Installarea : MutableList<String>
    private lateinit var installAreaId : MutableList<String>
    private lateinit var buildingList : MutableList<KBuildResponse>
    private lateinit var building : MutableList<String>
    private lateinit var buildingId : MutableList<String>
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
    private lateinit var document : MutableList<String>
    private lateinit var productList : MutableList<KProductResponse>
    private lateinit var product : MutableList<String>
    private lateinit var productId : MutableList<String>
    private var strArea:String? =null
    private var strBuilding:String? =null
    private  var strCity :String?=null
    private  var strSociety :String?=null
    private var strDepositType :String ?=null
    private var strPaySlip :String ?=null
    private var strSalutationId :String ? =null
    private var strPaymentStatus :String ? =null
    private var strFirmId :String? = null
    private var strRelation :String ? = null
    private var strGroupId : String? =null
    private var strGstId :String? = null
    private var strProductId:String? = null
    private var data_image: DocumentData? = null
    var name: ArrayList<DocumentData>? = null
    private  var voter:Boolean = false
    private  var DrivingLic:Boolean = false
private var passport:Boolean =false
private var rent: Boolean = false
private  var TelephoneBill: Boolean =false
private  var ElectricityBill: Boolean = false
private  var waterBill: Boolean = false
private  var AadharCard: Boolean = false
private  var GasConnec: Boolean = false
private  var CreditCard : Boolean = false
private  var Centrestate : Boolean =false
private var BankPass: Boolean =false
private var rationCard: Boolean = false
private var censtatephoto: Boolean = false
private  var panCard: Boolean = false
private  var Vcradphoto: Boolean = false
private  var ACphoto: Boolean = false
private var DLphoto: Boolean =false
private  var PPid: Boolean = false
    private var strDate:String ? =null
    var bitmap1: Bitmap? = null
    var cameraFileUri: Uri? = null
    var selectedMediaPath: String? = null
    var currentImagePath: String? = null
    var filepath: String? = null
    var str_ext1: String? = ""
    var uri: Uri? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = KaiFragCafBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_newLead.rl_back.setOnClickListener(this)
        btn_newLead.tv_lang.text=AppConstants.CAF
        btn_newLead.flr.visibility=View.GONE
        val bundle = arguments
        strLeadId = bundle?.getString("LeadId").toString()
        val sp1: SharedPreferences? = context?.getSharedPreferences("Login", 0)
        userName = sp1?.getString(AppConstants.USERNAME, null).toString()
        password = sp1?.getString(AppConstants.PASSWORD, null).toString()

        listener()
        getCompany()
        getCity("")
        buttonListener()
        Calender()
        foucus()
        getLeadDetails()
    }


    private fun getLeadDetails () {
        val kGetLeadReq = KGetLeadReq(Constants.GET_LEADS,Constants.AUTH_KEY,strLeadId,password,userName)

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getKLead(kGetLeadReq)
        call.enqueue(object : Callback<KGetLeadRes?> {
            override fun onResponse(call: Call<KGetLeadRes?>, response: Response<KGetLeadRes?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        if(response.body()?.StatusCode==200) {
                            strCity = response.body()?.Response?.get(0)?.installationAddress?.InstallCity.toString()
                            strArea = response.body()?.Response?.get(0)?.installationAddress?.InstallArea.toString()
                            strSociety = response.body()?.Response?.get(0)?.installationAddress?.InstallSociety.toString()
                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<KGetLeadRes?>, t: Throwable) {
                Log.e("ContactRetroError", t.toString())
            }
        })
    }

    private fun validEmail(email: String): Boolean {
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
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

    private fun buttonListener(){
        binding.BtnCafSubmit.setOnClickListener {

            if(voterCard.isChecked){
                 voter=true
            }
            if(binding.DrivingLic.isChecked){
                DrivingLic=true
            }

            if(binding.passport.isChecked){
                passport=true
            }
            if(binding.rent.isChecked){
                rent = true
            }

            if(binding.TelephoneBill.isChecked){
                TelephoneBill= true
            }

            if(binding.ElectricityBill.isChecked){
                ElectricityBill= true
            }

            if(binding.waterBill.isChecked){
                waterBill= true
            }

            if(binding.AadharCard.isChecked){
                AadharCard= true
            }

            if(binding.GasConnec.isChecked){
                GasConnec= true
            }

            if(binding.CreditCard.isChecked){
                CreditCard= true
            }

            if(binding.Centrestate.isChecked){
                Centrestate= true
            }

            if(binding.BankPass.isChecked){
                BankPass= true
            }

            if(binding.rationCard.isChecked){
                rationCard= true
            }

            if(binding.censtatephoto.isChecked){
                censtatephoto= true
            }

            if(binding.panCard.isChecked){
                panCard= true
            }

            if(binding.Vcradphoto.isChecked){
                Vcradphoto= true
            }
            if(binding.ACphoto.isChecked){
                ACphoto= true
            }
            if(binding.DLphoto.isChecked){
                DLphoto= true
            }
            if(binding.PPid.isChecked){
                PPid= true
            }
            val fName = editTextTextPersonName.text.toString()
            val lName = editTextTextPersonName2.text.toString()
            val mobile = EditTextMobNum.text.toString()
            val email = editTextTextPersonNameEmail.text.toString()
            val subBus = spinner3.selectedItem.toString()
            val floor = EditText_Floor.text.toString()
            val pinCode = EditText_Pin.text.toString()
            val block = EditText_Block.text.toString()
            val payDate = EditText_DD.text.toString()
            val transactionIdRequest = Spinner_DDate.text.toString()
            val amount = EditText_Amount.text.toString()
            val tan = EditText_TanNo.text.toString()
            val pan = EditText_PanNo.text.toString()
            val gstNum = Spinner_GSTno.text.toString()
            val otc =Spinner_OTC.text.toString()

           if (fName.isBlank()) {
                Toast.makeText(context, "Enter First Name", Toast.LENGTH_LONG).show()
            } else if (lName.isBlank()) {
                Toast.makeText(context, "Enter Last Name", Toast.LENGTH_LONG).show()
            } else if (subBus.isBlank()) {
                Toast.makeText(context, "Select SubBusiness", Toast.LENGTH_LONG).show()
            } else if (mobile.isBlank()||(mobile.length!=10)) {
                Toast.makeText(context, "Enter Mobile Number", Toast.LENGTH_LONG).show()

            } else if (block.isBlank()) {
                Toast.makeText(context, "Enter Block ", Toast.LENGTH_LONG).show()

            } else if (email.isBlank() || (!validEmail(email))) {
                Toast.makeText(context, "Enter Email", Toast.LENGTH_LONG).show()

            } else if (floor.isBlank()) {
                Toast.makeText(context, "Enter Floor", Toast.LENGTH_LONG).show()

            } else if (pinCode.isBlank()||pinCode.length!=6) {
                Toast.makeText(context, "Enter PinCode", Toast.LENGTH_LONG).show()
            }else {
                createCaf(
                    fName, lName, mobile, email, subBus, floor, pinCode, block, payDate, amount,
                    tan, pan, gstNum, otc, transactionIdRequest
                )
            }
        }
    }

    private fun foucus() {
        val regexGST = """/^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[0-9,A-Z]{3}${'$'}/""".toRegex()
        val regexPAN = """/^([A-Z]){5}([0-9]){4}([A-Z]){1}?${'$'}/""".toRegex()
        val regexTAN = """ /^([A-Z]){4}([0-9]){5}([A-Z]){1}?${'$'}/""".toRegex()
        Spinner_GSTno.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val gst = Spinner_GSTno.text.toString()
                val isValid = regexGST.matches(gst)
                if (!isValid) {
                    Toast.makeText(context, "Please Enter GST in CAPITAL Letters and correct format (12ABCDE1234A1A1 or 12ABCDE1234A1AA)", Toast.LENGTH_LONG).show()
                }
            }
        }

        EditText_PanNo.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val gst = EditText_PanNo.text.toString()
                val isValid = regexPAN.matches(gst)
                if (!isValid) {
                    Toast.makeText(context, "Please Enter PAN in CAPITAL Letters and correct format (ABCDE1234A)", Toast.LENGTH_LONG).show()
                }
            }
        }
        EditText_TanNo.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val gst = EditText_TanNo.text.toString()
                val isValid = regexTAN.matches(gst)
                if (!isValid) {
                    Toast.makeText(context, "Please Enter TAN in CAPITAL Letters and correct format (ABCD12345A)", Toast.LENGTH_LONG).show()
                }
            }
        }
    }



    private fun createCaf(
        fName: String,
        lName: String,
        mobile: String,
        email: String,
        subBus: String,
        floor: String,
        pinCode: String,
        block: String,
        payDate: String,
        amount: String,
        tan: String,
        pan: String,
        gstNum :String,
        otc:String?,
        transactionId :String?
    ) {
      val accountDetail =  AccountDetail( strCompanyId,strGroupId,strRelation,strArea,block,
          KaizalaAppConstant.HOME, strCity,"10001",email, fName,
          floor,lName,mobile,mobile,
          pinCode,strProductId,strSalutationId,strSociety,"",subBus)

     val paymentDetail = PaymentDetail(amount,"","","","",
         "","","",otc,strPaySlip,"2022-08-01",
         strPaymentStatus, "", "",transactionId)

     val  cafDetail = Cafdetail("",strCompanyId,strGroupId,pan,strRelation,tan,
         strArea,KaizalaAppConstant.HOME,strCity,"10001",fName,email,
         gstNum,
         floor,strGstId,mobile,mobile,pinCode,
         strProductId,strSociety,
         "",subBus,"0")

      val customerImage = CustomerImage("","")
        data_image = DocumentData("", "")
        name = ArrayList<DocumentData>()
        name?.add(data_image!!)
      //  val documentData=DocumentData("","")

       val documentRequired = DocumentRequired("",strCompanyId,strFirmId,strGroupId,"",
       strRelation,"",voter,ACphoto,BankPass,
           Centrestate, censtatephoto,CreditCard,name,DrivingLic,"",
           DLphoto,ElectricityBill,
           "",GasConnec,"",panCard,passport,false,
           rationCard,
           false,TelephoneBill,"",voter,Vcradphoto,waterBill,"")

     val kcreateCafReq = KCreateCafReq(accountDetail,Constants.KCREATECAF,Constants.KAUTH,paymentDetail,
         cafDetail, customerImage, documentRequired, "","",password,userName)

        val apiService = ApiClientKaizala.getClient().create(ApiInterface::class.java)
        val call = apiService.createKaiCaf(kcreateCafReq)
        call.enqueue(object : Callback<KCafCreatedRespo?> {
            override fun onResponse(
                call: Call<KCafCreatedRespo?>,
                response: Response<KCafCreatedRespo?>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    if(response.body()?.StatusCode=="200") {
                        try {
                            Toast.makeText(context, response.body()?.Response?.get(0)?.StatusName, Toast.LENGTH_LONG)
                                .show()
                            val intent = Intent (context, KaizalaCafTabActivity::class.java)
                            startActivity(intent)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }else{
                        Toast.makeText(context, response.body()?.Response?.get(0)?.StatusName, Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }

            override fun onFailure(call: Call<KCafCreatedRespo?>, t: Throwable) {
                Log.e("RetroError", t.toString())
            }
        })

    }



    private fun listener(){
        val salutationAdapter =
            context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, SalesAppConstants.list_of_salutation) }
        salutationAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        spnr_salutation.adapter = salutationAdapter

        spnr_salutation.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                val itemPosition = adapterView.selectedItemPosition
                strSalutationId = SalesAppConstants.list_of_salutationId[itemPosition]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        val paymentStatusAdapter =
            context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, KaizalaAppConstant.paymentStatus) }
        paymentStatusAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        Spinner_PaymentStatus.adapter = paymentStatusAdapter

        Spinner_PaymentStatus.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                val itemPosition = adapterView.selectedItemPosition
                strPaymentStatus = KaizalaAppConstant.paymentStatusId[itemPosition].toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        val subBusAdapter =
            context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item,KaizalaAppConstant.subBus) }
        subBusAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        spinner3.adapter = subBusAdapter



        val firmtypeAdapter =
            context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item,resources.getStringArray(R.array.list_firm_type)) }
        firmtypeAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        Spinner_firmType.adapter = firmtypeAdapter

        Spinner_firmType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                val itemPosition = adapterView.selectedItemPosition
                strFirmId = SalesAppConstants.list_firm_type_value[itemPosition]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }


        val securityAdapter =
            context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item,resources.getStringArray(R.array.list_of_Deposit)) }
        securityAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        Spinner_DepositType.adapter = securityAdapter

        Spinner_DepositType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                val itemPosition = adapterView.selectedItemPosition
                strDepositType = resources.getStringArray(R.array.list_of_boolean_values)[itemPosition]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        val paySlipAdapter =
            context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item,resources.getStringArray(R.array.list_of_payslip)) }
        paySlipAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        Spinner_Pay.adapter = paySlipAdapter

        Spinner_Pay.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                val itemPosition = adapterView.selectedItemPosition
                strPaySlip=resources.getStringArray(R.array.list_of_payslipval)[itemPosition]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }


        val gstAdapter =
            context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item,resources.getStringArray(R.array.list_of_gst)) }
        gstAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        Spinner_GST.adapter = gstAdapter

        Spinner_GST.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                val itemPosition = adapterView.selectedItemPosition
                strGstId =  resources.getStringArray(R.array.listDNCVal)[itemPosition]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

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
                        adapter12?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        Srch_Company.adapter = adapter12

                        val adapter1 = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, group) }
                        adapter1?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        searchViewGroup.adapter = adapter1

                        Srch_Company.onItemSelectedListener =
                            object : AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(adapterView: AdapterView<*>,
                                                            view: View, i: Int, l: Long) {
                                    strCompanyId = companyId[i]
                                    strGroupId= groupId[i]
                                    getRelation(strCompanyId)

                                }

                                override fun onNothingSelected(parent: AdapterView<*>?) {

                                }
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

                        val adapter12 = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, relation) }
                        adapter12?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        Spinner_relation.adapter = adapter12

                        Spinner_relation.onItemSelectedListener =
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
                        var cityPosition = 0
                        InstallcityCode.forEachIndexed { index, s ->
                            if (s == strCity) cityPosition = index
                        }
                        val adapter121 = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, Installcity) }
                        adapter121?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        SpinnerCity.adapter = adapter121
                        SpinnerCity.setSelection(cityPosition)
                        SpinnerCity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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

                            var areaPosition = 0
                            installAreaId.forEachIndexed { index, s ->
                                if (s == strArea) areaPosition = index
                            }

                            val adapter121 = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, Installarea) }
                            adapter121?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            Srch_Area.adapter = adapter121
                            Srch_Area.setSelection(areaPosition)
                            Srch_Area.onItemSelectedListener =
                                object : AdapterView.OnItemSelectedListener {
                                    override fun onItemSelected(
                                        adapterView: AdapterView<*>,
                                        view: View, i: Int, l: Long
                                    ) {
                                        strArea = installAreaId[i]
                                        getBuilding(strArea)
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

    fun getBuilding(strAreaCode: String?) {
        val kGetBuildingReq =
            KGetBuildingReq(
                Constants.kGETBUILDING, Constants.KAUTH, strAreaCode,
                password, Constants.USER + userName,
            )


        val apiService = ApiClientKaizala.getClient().create(ApiInterface::class.java)
        val call = apiService.getKBuilding(kGetBuildingReq)
        call.enqueue(object : Callback<KGetBuildingRes?> {
            override fun onResponse(call: Call<KGetBuildingRes?>, response: Response<KGetBuildingRes?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        if(response.body()?.StatusCode==200){
                            buildingList= response.body()?.Response!!
                            building = ArrayList<String>()
                            buildingId = ArrayList<String>()
                            building.add("Select Option")
                            buildingId.add("0")
                            for (item in buildingList) {
                                building.add(item.BuildingName +" ("+item.BuildingCode+")")
                                buildingId.add(item.BuildingCode)
                            }

                            val adapter121 = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, building) }
                            adapter121?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            Srch_Building.adapter = adapter121
                            Srch_Building.onItemSelectedListener =
                                object : AdapterView.OnItemSelectedListener {
                                    override fun onItemSelected(
                                        adapterView: AdapterView<*>,
                                        view: View, i: Int, l: Long
                                    ) {
                                        strBuilding = buildingId[i]
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

            override fun onFailure(call: Call<KGetBuildingRes?>, t: Throwable) {
                Log.e("RetroError", t.toString())
            }
        })
    }

    private fun  Calender() {
        try {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            EditText_DD.setOnClickListener {
                val dpd = DatePickerDialog(requireContext(), { _, year, monthOfYear, dayOfMonth ->
                    val mnth = monthOfYear + 1
                    EditText_DD.text=("$dayOfMonth-$mnth-$year")
                    val trgt = EditText_DD.text.toString()
                    val split = trgt.split("-")
                    val dateee = split[0]
                    val month1 = split[1]
                    val year1 = split[2]
                    strDate = ("$year1-$month1-$dateee")
                }, year, month, day)
                dpd.show()
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }


    fun getProduct(strSocietyCode: String?) {
        val kGetProductReq =
            KGetProductReq(Constants.KAUTH,Constants.KREADPRODUCT,strSocietyCode,
                userName,password)


        val apiService = ApiClientKaizala.getClient().create(ApiInterface::class.java)
        val call = apiService.getKProduct(kGetProductReq)
        call.enqueue(object : Callback<KGetProductRespo?> {
            override fun onResponse(call: Call<KGetProductRespo?>, response: Response<KGetProductRespo?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        if(response.body()?.StatusCode==200){
                            productList= response.body()?.Response!!
                            product = ArrayList<String>()
                            productId = ArrayList<String>()
                            product.add("Select Option")
                            productId.add("0")
                            for (item in productList) {
                                product.add(item.ProductSegment +" ("+item.ProductID+")")
                                productId.add(item.ProductID)
                            }

                            val adapter121 = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, product) }
                            adapter121?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            Search_Product.adapter = adapter121
                            Search_Product.onItemSelectedListener =
                                object : AdapterView.OnItemSelectedListener {
                                    override fun onItemSelected(
                                        adapterView: AdapterView<*>,
                                        view: View, i: Int, l: Long) {
                                        strProductId = productId[i]
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

            override fun onFailure(call: Call<KGetProductRespo?>, t: Throwable) {
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
                        val adapter121 = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, installSociety) }
                        adapter121?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        Srch_Society.adapter = adapter121
                        Srch_Society.setSelection(societyPosition)
                        Srch_Society.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                                strSociety = installSocietyId[i]
                                getProduct(strSociety)
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
        Intent(context, KaizalaCafTabActivity::class.java).also {
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


    override fun onClick(p0: View?) {
        if (p0?.id == R.id.rl_back) {
            back()
        }
    }

}