package com.spectra.fieldforce.salesapp.activity

import GetIRAdapter
import GetNPAdapter
import GetRFSAdapter
import GetWCRAdapter
import android.Manifest.permission
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Base64
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.spectra.fieldforce.BuildConfig
import com.spectra.fieldforce.R
import com.spectra.fieldforce.api.ApiClient
import com.spectra.fieldforce.api.ApiInterface
import com.spectra.fieldforce.databinding.CafDemoFragmentBinding
import com.spectra.fieldforce.salesapp.model.*
import com.spectra.fieldforce.utils.AppConstants
import com.spectra.fieldforce.utils.Constants
import com.spectra.fieldforce.utils.SalesAppConstants
import com.spectra.fieldforce.utils.SalesConstant
import kotlinx.android.synthetic.main.caf_add_rfs.*
import kotlinx.android.synthetic.main.caf_authorised_details.view.*
import kotlinx.android.synthetic.main.caf_company_details_row.view.*
import kotlinx.android.synthetic.main.caf_contact_info.view.*
import kotlinx.android.synthetic.main.caf_contact_person_row.view.*
import kotlinx.android.synthetic.main.caf_demo_fragment.*
import kotlinx.android.synthetic.main.caf_demo_fragment.view.*
import kotlinx.android.synthetic.main.caf_installation_address_row.view.*
import kotlinx.android.synthetic.main.caf_otherinfo_row.view.*
import kotlinx.android.synthetic.main.caf_payment_details_row.view.*
import kotlinx.android.synthetic.main.opp_add_doa.*
import kotlinx.android.synthetic.main.opp_add_doa.view.*
import kotlinx.android.synthetic.main.opp_add_feasibility.*
import kotlinx.android.synthetic.main.opp_add_feasibility.view.*
import kotlinx.android.synthetic.main.opp_add_quote.*
import kotlinx.android.synthetic.main.opp_add_quote.view.*
import kotlinx.android.synthetic.main.toolbar.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*
import java.util.regex.Pattern


class CAFActivity:AppCompatActivity(),View.OnClickListener , AdapterView.OnItemSelectedListener {
    var strCafId : String? = null
    var strOppId :String?=null
    private var cityList : ArrayList<BillData>? = null
    private var city : ArrayList<String>? = null
    private var cityCode : ArrayList<String>? = null
    private var billingcity : ArrayList<String>? = null
    private var billingcityCode : ArrayList<String>? = null
    private var authocity : ArrayList<String>? = null
    private var authocityCode : ArrayList<String>? = null
    private val PERMISSION_REQUEST_CODE = 200
    var strCity: String? = null
    var strProductId: String? = null

    var strPaymentStatus:String?=null
    var strBankName: String? = null

    var str_PrfCom:String? = null
    var str_grp :String ? = null
    var str_rltn:String ? = null
    var str_cmp :String? = null
    var str_cmpnyself :String? = null
    var str_frwall :String? = null
    var str_provider :String? = null
    var str_firmtype:String? = null
    var str_indusid:String? = null
    var str_statename:String? = null
    var str_inststateId:String? = null
    var str_city:String? = null
    var str_voip:String? = null
    var str_city_code:String? = null
    var str_add_area:String? = null
    var str_inst_building_nm:String? = null
    var str_blstatename:String? = null
    var str_blinststateId:String? = null
    var str_blcity:String? = null
    var str_blcity_code:String? = null
    var str_atstatename:String? = null
    var str_atinststateId:String? = null
    var str_atcity:String? = null
    var str_billtype:String? = null
    var str_payslip:String? = null
    var str_sctype:String? = null
    var str_gstval:String? = null
    var str_ntwrk:String? = null
    var str_customercategory:String? = null
    var str_wrkngdays:String? = null
    var str_atcity_code:String? = null
    var str_bladd_area:String? = null
    var str_blinst_building_nm:String? = null
    var str_inst_statusid:String? = null
    var str_sub_bus:String? = null
    var str_bankid:String? = null
    var str_wrknghrs:String? = null
    var caflock:String? = null
    var cafnext:String? = null
    var frwamc:String? = null
    var status:String? = null
    var cafpaydate:String? = null
    var checkdate:String? = null
    var date1:String?=null
    var userName:String? = null
    var password:String?=null
    var amount:String? = null
    private var buildingList : ArrayList<BuildData>? = null
    private var building : ArrayList<String>? = null
    private var buildingCode : ArrayList<String>? = null
    private var billingbuilding : ArrayList<String>? = null
    private var billingbuildingCode : ArrayList<String>? = null
    private var areaList : ArrayList<AreaData>? = null
    private var area : ArrayList<String>? = null
    private var areaCode : ArrayList<String>? = null
    private var billingarea : ArrayList<String>? = null
    private var billingareaCode : ArrayList<String>? = null
    private var companyId : ArrayList<String>? = null
    private var company: ArrayList<String>? = null
    private var group : ArrayList<String>? = null
    private var groupId : ArrayList<String>? = null
    private var relation : ArrayList<String>? = null
    private var relationId :ArrayList<String>? = null
    private var companyList: ArrayList<ComapnyData>? = null
    private var industryList : ArrayList<IndustryResponse>? = null
    private var instryname = arrayListOf<String>()
    private var industryid = arrayListOf<String>()
    private var bankList : ArrayList<BankData>? = null
    private var bankname = arrayListOf<String>()
    private var bankid = arrayListOf<String>()
    var strIndustry:String? = null
    var strCompany=""
    var strGroup=""
    var strRelation=""
    private var inAnimation: AlphaAnimation? = null
    private var outAnimation: AlphaAnimation? = null
    private var wcrList: ArrayList<WCRData>? = null
    private var irList: ArrayList<IRData>? = null
    private var npList: ArrayList<NPData>? = null
    private var rfsList: ArrayList<RFSData>? = null
    lateinit var binding:CafDemoFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.caf_demo_fragment)
        searchtoolbarcaf.rl_back.setOnClickListener(this)
        searchtoolbarcaf.tv_lang.text = AppConstants.Caf
        val extras = intent.extras

        if (extras != null) {
            strCafId = extras.getString("CafId")
            strOppId = extras.getString("OppId")
        }
        // init();
        // validateUser();
        val sp1: SharedPreferences = this.getSharedPreferences("Login", 0)
        userName = sp1.getString(AppConstants.USERNAME, null)
        password = sp1.getString(AppConstants.PASSWORD, null)
        listener()
        itemListener()
        getCaf()

        saveItem()
        tv_submit.setOnClickListener {
            submitCaf()
        }
        focus()
        if (checkPermission()) {
            //  Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
        } else {
            requestPermission()
        }
    }


    private fun checkPermission(): Boolean {
        // checking of permissions.
        val permission1 =
            ContextCompat.checkSelfPermission(applicationContext, permission.WRITE_EXTERNAL_STORAGE)
        val permission2 =
            ContextCompat.checkSelfPermission(applicationContext, permission.READ_EXTERNAL_STORAGE)
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        // requesting permissions if not provided.
        ActivityCompat.requestPermissions(
            this,
            arrayOf(permission.WRITE_EXTERNAL_STORAGE, permission.READ_EXTERNAL_STORAGE,permission.MANAGE_EXTERNAL_STORAGE),
            PERMISSION_REQUEST_CODE
        )
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.size > 0) {
                val writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED
                val readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED
                val manageStorage = grantResults[2] == PackageManager.PERMISSION_GRANTED
                if (writeStorage && readStorage && manageStorage) {
                    //  Toast.makeText(this, "Permission Granted..", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission Denied.", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }

    fun saveItem(){

        tv_create.setOnClickListener {
            Create(Constants.CREATECAF)
        }
        tv_update.setOnClickListener {
            Create(Constants.UPDATECAF)
        }
        binding.searchtoolbarcaf.downloadpdf.setOnClickListener { downloadPDF() }
        binding.searchtoolbarcaf.share.setOnClickListener {
            share() }
    }

    private fun downloadPDF() {
        inProgress()
        val cafPdfRequest = CafPdfRequest(
            Constants.DOWNLOADREPORTS, Constants.AUTH_KEY,
            strCafId,
            password, "CAF", userName
        )
        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getPdf(cafPdfRequest)
        call.enqueue(object : Callback<GetPdfResponse?> {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            override fun onResponse(
                call: Call<GetPdfResponse?>,
                response: Response<GetPdfResponse?>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    OutProgress()
                    if (response.body()?.Status == "Success") {
                        val data = response.body()?.Response?.Data
                        storetoPdfandOpen(data)
                    }
                }
            }

            override fun onFailure(call: Call<GetPdfResponse?>, t: Throwable) {
                binding.createprogressLayout.progressOverlay.visibility = View.GONE
                Log.e("RetroError", t.toString())
            }
        })
    }

    private fun share() {
        inProgress()
        val cafPdfRequest = CafPdfRequest(
            Constants.REPORTSEMAIL_SEND, Constants.AUTH_KEY,
            strCafId,
            password, "CAF", userName
        )
        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.shareEmail(cafPdfRequest)
        call.enqueue(object : Callback<ReportResponse?> {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            override fun onResponse(
                call: Call<ReportResponse?>,
                response: Response<ReportResponse?>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    OutProgress()
                    if (response.body()?.Status == "Success") {
                        val msg = response.body()?.Response?.Message
                        Toast.makeText(this@CAFActivity, msg, Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<ReportResponse?>, t: Throwable) {
                binding.createprogressLayout.progressOverlay.visibility = View.GONE
                Log.e("RetroError", t.toString())
            }
        })
    }

    fun storetoPdfandOpen(base: String?) {
        val extStorageDirectory = Environment.getExternalStorageDirectory()
            .toString()
        val folder = File(extStorageDirectory, "Spectra")
        folder.mkdir()
        val file = File(folder, "Spectradoc.pdf")
        try {
            file.createNewFile()
        } catch (e1: IOException) {
            e1.printStackTrace()
        }
        try {
            val out = FileOutputStream(file)
            val pdfAsBytes = Base64.decode(base, 0)
            out.write(pdfAsBytes)
            out.flush()
            out.close()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        val sendIntent = Intent(Intent.ACTION_VIEW)
        val uri: Uri
        uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", file)
        sendIntent.setDataAndType(uri, "application/pdf")
        sendIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        this.startActivity(sendIntent)
    }

    private fun focus(){
        layout_cafothr_details.et_cafemailid.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus) {
                val email =  layout_cafothr_details.et_cafemailid.text.toString()
                val isValid = Patterns.EMAIL_ADDRESS.matcher(email).matches();
                if (!isValid) {
                    Toast.makeText(this, "Invalid Email", Toast.LENGTH_LONG).show()
                }
            }
        }
        layout_cafothr_details.et_authomob.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus) {
                val mobile = layout_cafothr_details.et_authomob.text.toString()
                val isValid = Patterns.PHONE.matcher(mobile).matches();
                if (!isValid) {
                    Toast.makeText(this, "Invalid Mobile Number", Toast.LENGTH_LONG).show()
                }
            }
        }
        layout_cafinstal_address.et_cafmbnum.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus)
            {
                val mobile = layout_cafinstal_address.et_cafmbnum.text.toString()
                val isValid = Patterns.PHONE.matcher(mobile).matches();
                if (!isValid) {
                    Toast.makeText(this, "Invalid Mobile Number", Toast.LENGTH_LONG).show()
                }
            }
        }
        layout_cafinstal_address.et_cafemail.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus)
            {
                val email =  layout_cafinstal_address.et_cafemail.text.toString()
                val isValid = Patterns.EMAIL_ADDRESS.matcher(email).matches();
                if (!isValid) {
                    Toast.makeText(this, "Invalid Email", Toast.LENGTH_LONG).show()
                }
            }
        }

        caf_contact_person_row.et_caf_bilngemailid.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus)
            {
                val email =  caf_contact_person_row.et_caf_bilngemailid.text.toString()
                val isValid = Patterns.EMAIL_ADDRESS.matcher(email).matches();
                if (!isValid) {
                    Toast.makeText(this, "Invalid Email", Toast.LENGTH_LONG).show()
                }
            }
        }
        caf_contact_person_row.et_cafphn_num.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus)
            {
                val mobile = caf_contact_person_row.et_cafphn_num.text.toString()
                val isValid = Patterns.PHONE.matcher(mobile).matches();
                if (!isValid) {
                    Toast.makeText(this, "Invalid Mobile Number", Toast.LENGTH_LONG).show()
                }
            }
        }
        val regexGST = """/^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[0-9,A-Z]{3}${'$'}/""".toRegex()
        val regexPAN = """/^([A-Z]){5}([0-9]){4}([A-Z]){1}?${'$'}/""".toRegex()
        val regexTAN = """ /^([A-Z]){4}([0-9]){5}([A-Z]){1}?${'$'}/""".toRegex()
        linearcafpymnt_details.et_gstt.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus)
            {
                val gst =  linearcafpymnt_details.et_gstt.text.toString()
                val isValid= regexGST.matches(gst)
                if (!isValid) {
                    Toast.makeText(this, "Please enter GST in CAPITAL Letters and correct format (12ABCDE1234A1A1 or 12ABCDE1234A1AA)", Toast.LENGTH_LONG).show()
                }
            }
        }
        linearcafpymnt_details.et_pannum.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus)
            {
                val gst =  linearcafpymnt_details.et_pannum.text.toString()
                val isValid= regexPAN.matches(gst)
                if (!isValid) {
                    Toast.makeText(this, "Please enter PAN in CAPITAL Letters and correct format (ABCDE1234A)", Toast.LENGTH_LONG).show()
                }
            }
        }
        linearcafpymnt_details.et_tannum.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus)
            {
                val gst =  linearcafpymnt_details.et_tannum.text.toString()
                val isValid= regexTAN.matches(gst)
                if (!isValid) {
                    Toast.makeText(this, "Please enter TAN in CAPITAL Letters and correct format (ABCD12345A)", Toast.LENGTH_LONG).show()
                }
            }
        }
    }


    private fun Create(createcaf: String) {
        val strbusinessSegment =  caf_contactinfo_layout.et_cafbs_sgmnt.text.toString()
        val subbssegment = str_sub_bus.toString()
        val polock = layout_otherinfo.et_caflock.text.toString()
        val ponext = layout_otherinfo.et_cafnxt.text.toString()
        //  val contactname = layout_cafothr_details.et_cafname.text.toString()
        val authemail = layout_cafothr_details.et_cafemailid.text.toString()
        val authfather = layout_cafothr_details.et_fthr_hsb.text.toString()
        val authmobile = layout_cafothr_details.et_authomob.text.toString()
        val authname = layout_cafothr_details.et_cafname.text.toString()
        val authaddress = layout_cafothr_details.et_address.text.toString()
        val authpincode = layout_cafothr_details.et_cafauthpincode.text.toString()
        /*  val billingplot = caf_contact_person_row.et_cfblbuildng_num.text.toString()
          val billingname = caf_contact_person_row.et_caf_cntname.text.toString()
          val billingemail = caf_contact_person_row.et_caf_bilngemailid.text.toString()
          val billingfloor = caf_contact_person_row.et_cfblfloor.text.toString()
          val billingphn =   caf_contact_person_row.et_cafphn_num.text.toString()
          val billingpincode = caf_contact_person_row.et_cfblpin_code.text.toString()*/
        val instemail = layout_cafinstal_address.et_cafemail.text.toString()
        val instmobile = layout_cafinstal_address.et_cafmbnum.text.toString()
        val instpin = layout_cafinstal_address.et_cafpin.text.toString()
        amount = layout_payment.et_totalamt.text.toString()
        val approvalcode = layout_payment.et_appcode.text.toString()
        val brnch = layout_payment.et_brnch.text.toString()
        val checkdd = layout_payment.et_chkdate.text.toString()
        val checknum = layout_payment.et_chknum.text.toString()
        val carddgts = layout_payment.et_cardfrdgt.text.toString()
        val paymntdt = layout_payment.et_paymntdt.text.toString()
        val txtty = layout_payment.et_txtid.text.toString()
        val srcdepst = layout_payment.et_scdeposit.text.toString()
        val creditcrd = layout_payment.et_creditfrdgt.text.toString()
        val pan = layout_payment.et_pannum.text.toString()
        val tan = layout_payment.et_tannum.text.toString()
        val gstnum = layout_payment.et_gstt.text.toString()
        val customername = caf_contactinfo_layout.et_cstname.text.toString()
        val phonenumber = caf_contact_person_row.et_cafphn_num.text.toString()
        val email = caf_contact_person_row.et_caf_bilngemailid.text.toString()
        val buildingnumber = caf_contact_person_row.et_cfblbuildng_num.text.toString()
        val floor = caf_contact_person_row.et_cfblfloor.text.toString()
        val pincode = caf_contact_person_row.et_cfblpin_code.text.toString()

        date1 =binding.layoutOtherinfo.etFrwas.text.toString()
        if(str_wrknghrs?.isBlank()==true||str_wrknghrs=="0"||str_wrknghrs=="null"){
            Toast.makeText(this, "Please Select Working Hours", Toast.LENGTH_SHORT).show()
        } else if(str_cmp?.isBlank()==true||str_cmp=="Select Company"||str_cmp=="null"){
            Toast.makeText(this, "Please Select Company", Toast.LENGTH_SHORT).show()
        }else if(str_grp?.isBlank()==true||str_grp=="Select Group"||str_grp=="null"){
            Toast.makeText(this, "Please Select Group", Toast.LENGTH_SHORT).show()
        }else if(str_rltn?.isBlank()==true||str_rltn=="Select Relation"||str_rltn=="null"){
            Toast.makeText(this, "Please Select Relation", Toast.LENGTH_SHORT).show()
        }else if(str_wrkngdays?.isBlank()==true||str_wrkngdays=="0"||str_wrkngdays=="null"){
            Toast.makeText(this, "Please Select Business Days Option", Toast.LENGTH_SHORT).show()
        } else if(subbssegment.isBlank()){
            Toast.makeText(this, "Please enter SubBusiness Segment", Toast.LENGTH_SHORT).show()
        }else if(str_frwall?.isBlank()==true||str_frwall=="0"||(str_frwall=="null")){
            Toast.makeText(this, "Please enter FireWall", Toast.LENGTH_SHORT).show()
        }else if(frwamc?.isEmpty()==true&&str_frwall=="1"){
            Toast.makeText(this, "Please enter FireWall AMC Expiry date", Toast.LENGTH_SHORT).show()
        } else if(polock.isBlank()){
            Toast.makeText(this, "Please enter PoLock", Toast.LENGTH_SHORT).show()
        }else if(ponext.isBlank()){
            Toast.makeText(this, "Please enter PoNext", Toast.LENGTH_SHORT).show()
        }else if(str_cmpnyself?.isBlank()==true||str_cmpnyself=="0"||(str_cmpnyself=="null")){
            Toast.makeText(this, "Please enter Company Self", Toast.LENGTH_SHORT).show()
        } else if(instemail.isBlank()){
            Toast.makeText(this, "Please enter Installation Email", Toast.LENGTH_SHORT).show()
        }else if(instmobile.isBlank()){
            Toast.makeText(this, "Please enter Installation Phone Number", Toast.LENGTH_SHORT).show()
        }else if(str_billtype?.isBlank()==true||str_billtype=="Select Option"){
            Toast.makeText(this, "Please Select  Bill Type", Toast.LENGTH_SHORT).show()
        }else if(instpin.isBlank()|| instpin.length!=6){
            Toast.makeText(this, "Please enter Installation PinCode", Toast.LENGTH_SHORT).show()
        } else if(str_customercategory?.isBlank()==true||str_customercategory=="0"||(str_customercategory=="null")){
            Toast.makeText(this, "Please Select Category Of Customer", Toast.LENGTH_SHORT).show()
        }else if(customername.isBlank()){
            Toast.makeText(this, "Please enter Customer Name", Toast.LENGTH_SHORT).show()
        }else if(phonenumber.isBlank()|| phonenumber.length!=10){
            Toast.makeText(this, "Please enter Phone Number", Toast.LENGTH_SHORT).show()
        }else if(email.isBlank()||(!validEmail(email))){
            Toast.makeText(this, "Please enter Email", Toast.LENGTH_SHORT).show()
        } else if(str_blstatename?.isBlank()==true||str_blstatename=="Select State"||(str_blstatename=="null")){
            Toast.makeText(this, "Please enter State", Toast.LENGTH_SHORT).show()
        }else if(str_blcity?.isBlank()==true|| str_blcity=="Select City"||(str_blcity=="null")){
            Toast.makeText(this, "Please enter City", Toast.LENGTH_SHORT).show()
        }else if(str_bladd_area?.isBlank()==true||str_bladd_area==""||(str_bladd_area=="null")){
            Toast.makeText(this, "Please enter Area", Toast.LENGTH_SHORT).show()
        }else if(str_blinst_building_nm?.isBlank()==true||str_blinst_building_nm==""||(str_blinst_building_nm=="null")){
            Toast.makeText(this, "Please enter Building", Toast.LENGTH_SHORT).show()
        }else if(buildingnumber.isBlank()||(buildingnumber=="null")){
            Toast.makeText(this, "Please enter Building Number", Toast.LENGTH_SHORT).show()
        }else if(floor.isBlank()){
            Toast.makeText(this, "Please enter Floor", Toast.LENGTH_SHORT).show()
        }else if(pincode.isBlank()|| pincode.length!=6){
            Toast.makeText(this, "Please enter PinCode", Toast.LENGTH_SHORT).show()
        }else if(authname.isBlank()){
            Toast.makeText(this, "Please enter Name", Toast.LENGTH_SHORT).show()
        }else if(authfather.isBlank()){
            Toast.makeText(this, "Please enter Father/Husband Name", Toast.LENGTH_SHORT).show()
        }else if(authaddress.isBlank()){
            Toast.makeText(this, "Please enter Residential Address", Toast.LENGTH_SHORT).show()
        }else if(authmobile.isBlank()|| authmobile.length!=10){
            Toast.makeText(this, "Please enter Mobile Num", Toast.LENGTH_SHORT).show()
        } else if(authemail.isBlank()||(!validEmail(authemail))){
            Toast.makeText(this, "Please enter Email", Toast.LENGTH_SHORT).show()
        }else if(str_atstatename?.isBlank()==true||str_atstatename=="Select State"||(str_atstatename=="null")){
            Toast.makeText(this, "Please enter State", Toast.LENGTH_SHORT).show()
        }else if(str_atcity?.isBlank()==true|| str_atcity=="Select City"||(str_atcity=="null")){
            Toast.makeText(this, "Please enter City", Toast.LENGTH_SHORT).show()
        }else if(authpincode.isBlank()|| authpincode.length!=6) {
            Toast.makeText(this, "Please enter PinCode", Toast.LENGTH_SHORT).show()
        }else if(str_payslip?.isBlank()==true|| str_payslip=="0"||(str_payslip=="null")){
            Toast.makeText(this, "Please PaySlip Type", Toast.LENGTH_SHORT).show()
        } else if(str_sctype?.isBlank()==true|| str_sctype=="0"||(str_sctype=="null")){
            Toast.makeText(this, "Please Enter Security Deposit Type", Toast.LENGTH_SHORT).show()
        } else if(srcdepst.isBlank()){
            Toast.makeText(this, "Please enter Security Deposit", Toast.LENGTH_SHORT).show()
        } else if(amount?.isBlank()==true){
            Toast.makeText(this, "Please enter Amount", Toast.LENGTH_SHORT).show()
        }else if((str_payslip=="5"||str_payslip=="4"||str_payslip=="11")&&(txtty.isBlank())){
            Toast.makeText(this, "Please enter Transaction Refrence ID", Toast.LENGTH_SHORT).show()
        }else if((str_payslip=="5"||str_payslip=="4"||str_payslip=="11")&&paymntdt.isBlank()){
            Toast.makeText(this, "Please Select Payment Date", Toast.LENGTH_SHORT).show()
        }else if((str_payslip=="1"||str_payslip=="2"||str_payslip=="12")&&(str_bankid?.isBlank()==true|| str_bankid=="0"||(str_bankid=="null"))){
            Toast.makeText(this, "Please Select Bank Name", Toast.LENGTH_SHORT).show()
        }else if((str_payslip=="1"||str_payslip=="2"||str_payslip=="12")&&brnch.isBlank()){
            Toast.makeText(this, "Please enter Branch", Toast.LENGTH_SHORT).show()
        }else if((str_payslip=="1"||str_payslip=="2"||str_payslip=="12")&&checknum.isBlank()){
            Toast.makeText(this, "Please enter Check Number", Toast.LENGTH_SHORT).show()
        }else if((str_payslip=="1"||str_payslip=="2"||str_payslip=="12")&&checkdd.isBlank()){
            Toast.makeText(this, "Please enter Check Date", Toast.LENGTH_SHORT).show()
        }else if((str_payslip=="7")&&paymntdt.isBlank()){
            Toast.makeText(this, "Please Select Payment Date", Toast.LENGTH_SHORT).show()
        }else if((str_payslip=="7")&&approvalcode.isBlank()){
            Toast.makeText(this, "Please enter Approval Code", Toast.LENGTH_SHORT).show()
        }else if((str_payslip=="7")&&creditcrd.isBlank()){
            Toast.makeText(this, "Please enter CreditCard Number", Toast.LENGTH_SHORT).show()
        }else if(paymntdt.isBlank()&&(str_payslip=="4")){
            Toast.makeText(this, "Please Select Payment Date", Toast.LENGTH_SHORT).show()
        }else if(approvalcode.isBlank()&&(str_payslip=="4")){
            Toast.makeText(this, "Please enter Approval Code", Toast.LENGTH_SHORT).show()
        }else if(creditcrd.isBlank()&&(str_payslip=="4")){
            Toast.makeText(this, "Please enter CreditCard Number", Toast.LENGTH_SHORT).show()
        }else if(paymntdt.isBlank()&&(str_payslip=="8")){
            Toast.makeText(this, "Please Select Payment Date", Toast.LENGTH_SHORT).show()
        }else if(approvalcode.isBlank()&&(str_payslip=="8")){
            Toast.makeText(this, "Please enter Approval Code", Toast.LENGTH_SHORT).show()
        }else if(carddgts.isBlank()&&(str_payslip=="8")){
            Toast.makeText(this, "Please enter Debit Card Number", Toast.LENGTH_SHORT).show()
        }else if((str_gstval?.isEmpty()==true)||str_gstval=="0"||(str_gstval=="null")){
            Toast.makeText(this, "Please Select GST", Toast.LENGTH_SHORT).show()
        } else if(gstnum.isEmpty() && str_gstval=="569480000"){
            Toast.makeText(this, "Please enter GST Number", Toast.LENGTH_SHORT).show()
        }else {
            createCaf(createcaf,strbusinessSegment,subbssegment,customername,phonenumber,polock,ponext,authemail,authfather,
                authmobile,authname,authaddress,authpincode,buildingnumber,customername,email,floor,phonenumber,
                pincode,instemail,instmobile,instpin,approvalcode,brnch,checkdd,checknum,carddgts,
                paymntdt,txtty,srcdepst,creditcrd,pan,tan,gstnum)
        }
    }
    private fun validEmail(email: String): Boolean {
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }


    fun itemListener(){
        caf_contactinfo_layout.et_wrkngdys.setOnClickListener { caf_contactinfo_layout.sp_wrkng_days.performClick() }
        caf_contactinfo_layout.sp_wrkng_days.onItemSelectedListener = this
        caf_contactinfo_layout.et_ntwrkmtr.setOnClickListener { caf_contactinfo_layout.sp_ntwrkmtr.performClick() }
        caf_contactinfo_layout.sp_ntwrkmtr.onItemSelectedListener = this
        layout_payment.et_payslip.setOnClickListener { layout_payment.sp_payslip.performClick() }
        layout_payment.sp_payslip.onItemSelectedListener = this
        layout_payment.et_bnknm.setOnClickListener { layout_payment.sp_bnknam.performClick() }
        layout_payment.sp_bnknam.onItemSelectedListener = this
        binding.layoutPayment.etGst.setOnClickListener { binding.layoutPayment.spGst.performClick() }
        binding.layoutPayment.spGst.onItemSelectedListener = this
        layout_payment.et_sctype.setOnClickListener { layout_payment.sp_securitytype.performClick() }
        layout_payment.sp_securitytype.onItemSelectedListener = this
        caf_contactinfo_layout.et_prfcom.setOnClickListener { caf_contactinfo_layout.sp_preffered_cmmnctn.performClick() }
        caf_contactinfo_layout.sp_preffered_cmmnctn.onItemSelectedListener = this
        caf_contactinfo_layout.et_sb_bs_sgmnt.setOnClickListener { caf_contactinfo_layout.sp_cafsbbus.performClick() }
        caf_contactinfo_layout.sp_cafsbbus.onItemSelectedListener = this
        layout_otherinfo.et_provider.setOnClickListener { layout_otherinfo.sp_caf_pro.performClick() }
        layout_otherinfo.sp_caf_pro.onItemSelectedListener = this
        layout_otherinfo.et_caffrwl.setOnClickListener { layout_otherinfo.sp_caffrwal.performClick() }
        layout_otherinfo.sp_caffrwal.onItemSelectedListener = this
        layout_otherinfo.et_cafcmpny_self.setOnClickListener { layout_otherinfo.sp_cafcmpny_self.performClick() }
        layout_otherinfo.sp_cafcmpny_self.onItemSelectedListener = this
        layout_cafcompany_details.et_caffirm_type.setOnClickListener { layout_cafcompany_details.sp_caffirm_type.performClick() }
        layout_cafcompany_details.sp_caffirm_type.onItemSelectedListener = this
        layout_cafcompany_details.et_cafindus_type.setOnClickListener { layout_cafcompany_details.sp_cafindustype.performClick() }
        layout_cafcompany_details.sp_cafindustype.onItemSelectedListener = this
        caf_contactinfo_layout.et_cafcmpny.setOnClickListener { caf_contactinfo_layout.sp_cafcompany.performClick() }
        caf_contactinfo_layout.sp_cafcompany.onItemSelectedListener = this
        caf_contactinfo_layout.et_cafgrp.setOnClickListener { caf_contactinfo_layout.sp_cafgroup.performClick() }
        caf_contactinfo_layout.sp_cafgroup.onItemSelectedListener = this
        caf_contactinfo_layout.et_cafrelation.setOnClickListener { caf_contactinfo_layout.sp_cafrelation.performClick() }
        caf_contactinfo_layout.sp_cafrelation.onItemSelectedListener = this
        layout_cafinstal_address.et_cafstate.setOnClickListener { layout_cafinstal_address.sp_cafstate.performClick() }
        layout_cafinstal_address.sp_cafstate.onItemSelectedListener = this
        layout_cafinstal_address.et_add_cafcity.setOnClickListener { layout_cafinstal_address.sp_cafcity.performClick() }
        layout_cafinstal_address.sp_cafcity.onItemSelectedListener = this
        layout_cafinstal_address.et_cafinstallarea.setOnClickListener { layout_cafinstal_address.sp_cafcnarea.performClick() }
        layout_cafinstal_address.sp_cafcnarea.onItemSelectedListener = this
        layout_cafinstal_address.et_cafbuildingname.setOnClickListener { layout_cafinstal_address.sp_cafbuilding_nm.performClick() }
        layout_cafinstal_address.sp_cafbuilding_nm.onItemSelectedListener = this
        layout_cafinstal_address.et_cafbuilding_status.setOnClickListener { layout_cafinstal_address.sp_cafstatus.performClick() }
        layout_cafinstal_address.sp_cafstatus.onItemSelectedListener = this

        layout_cafinstal_address.et_custctgry.setOnClickListener { layout_cafinstal_address.sp_custctgry.performClick() }
        layout_cafinstal_address.sp_custctgry.onItemSelectedListener = this
        caf_contact_person_row.et_cfblstate.setOnClickListener { caf_contact_person_row.sp_cfblstate.performClick() }
        caf_contact_person_row.sp_cfblstate.onItemSelectedListener = this
        caf_contact_person_row.et_cfblcity.setOnClickListener { caf_contact_person_row.sp_cfblcity.performClick() }
        caf_contact_person_row.sp_cfblcity.onItemSelectedListener = this
        caf_contact_person_row.et_cfblarea.setOnClickListener { caf_contact_person_row.sp_cfblcnarea.performClick() }
        caf_contact_person_row.sp_cfblcnarea.onItemSelectedListener = this
        caf_contact_person_row.et_cfblbuilding.setOnClickListener { caf_contact_person_row.sp_cfblbuilding_nm.performClick() }
        caf_contact_person_row.sp_cfblbuilding_nm.onItemSelectedListener = this
        layout_cafothr_details.et_cafauthstate.setOnClickListener { layout_cafothr_details.sp_cafauthostate.performClick() }
        layout_cafothr_details.sp_cafauthostate.onItemSelectedListener = this
        layout_cafothr_details.et_add_cafauthcity.setOnClickListener { layout_cafothr_details.sp_cafauthcity.performClick() }
        layout_cafothr_details.sp_cafauthcity.onItemSelectedListener = this
        caf_contactinfo_layout.et_cstmrwrknghrs.setOnClickListener { caf_contactinfo_layout.sp_cstmrwrknghrs.performClick() }
        caf_contactinfo_layout.sp_cstmrwrknghrs.onItemSelectedListener = this
        layout_cafinstal_address.et_cafvoip.setOnClickListener {  layout_cafinstal_address.sp_voip.performClick() }
        layout_cafinstal_address.sp_voip.onItemSelectedListener = this
        layout_cafinstal_address.et_cafbiltype.setOnClickListener { layout_cafinstal_address.sp_cafbilltype.performClick() }
        layout_cafinstal_address.sp_cafbilltype.onItemSelectedListener = this

        lineardoc_details.setOnClickListener {
            Intent(this, DocumentCafAct::class.java).also {
                it.putExtra("CafId", strCafId)
                it.putExtra("OppId", strOppId)
                it.putExtra("Status", status)
                startActivity(it)
                finish()
            }
        }
    }

    fun createCaf(
        createcaf:String,
        strbusinessSegment: String,
        subbssegment: String,
        customername: String,
        phonenumber: String,
        polock: String,
        ponext: String,
        authemail: String,
        authfather: String,
        authname: String,
        authaddress: String,
        authpincode: String,
        billingplot: String,
        authmobile:String,
        billingname: String,
        billingemail: String,
        billingfloor: String,
        billingphn: String,
        billingpincode: String,
        instemail: String,
        instmobile: String,
        instpin: String,
        approvalcode: String,
        brnch: String,
        checkdd: String,
        checknum: String,
        carddgts: String,
        paymntdt: String,
        txtty: String,
        srcdepst: String,
        creditcrd: String,
        pan: String,
        tan: String,
        gstnum: String
    ) {
        inProgress()
        val email = layout_cafothr_details.et_cafemailid.text.toString()
        val father = layout_cafothr_details.et_fthr_hsb.text.toString()
        val mobile = layout_cafothr_details.et_authomob.text.toString()
        val name = layout_cafothr_details.et_cafname.text.toString()
        val address = layout_cafothr_details.et_address.text.toString()
        val pincode = layout_cafothr_details.et_cafauthpincode.text.toString()

        val cafDetails = CafDetail(str_wrkngdays,strbusinessSegment,strCafId,str_cmpnyself,
            str_customercategory, customername,str_wrknghrs,str_provider, frwamc,
            str_frwall, gstnum,str_gstval,str_ntwrk, pan,
            phonenumber, caflock, cafnext,str_PrfCom, subbssegment, tan,str_voip)
        val authSigDetails = AuthorSigDetails(email,father,"0",
            mobile,  name, address,str_atcity_code,
            AppConstants.COUNTRY_CODE,pincode ,str_atinststateId)

        val cafBillingAddress = CafBillingAddress(billingplot,"",
            billingname,billingemail,billingfloor,
            "0","0","0",
            str_bladd_area,str_blinst_building_nm,str_blcity_code,AppConstants.COUNTRY_CODE, billingphn,
            billingpincode,str_blinststateId)

        val cafInstallationAddress = CafInstallationAddress(str_billtype,"0","0",
            "", instemail,"0","0","0",
            instmobile, "0", str_add_area,str_inst_building_nm,str_city_code,AppConstants.COUNTRY_CODE, instpin,
            str_inststateId, strProductId)
        if(amount=="0"){
            amount="0.000"
        }

        val paymentDetail = PaymentDetail("", amount, approvalcode, str_bankid,
            brnch, checkdate, checknum, creditcrd, carddgts,str_payslip, cafpaydate,"", srcdepst,str_sctype,
            txtty)
        val createCafReqest = CreateCafReqest(createcaf, Constants.AUTH_KEY, authSigDetails,
            cafBillingAddress, cafDetails,str_cmp,str_grp,cafInstallationAddress,strOppId,
            password,paymentDetail,str_rltn,userName)
        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.createCaf(createCafReqest)
        call.enqueue(object : Callback<CafDetailResponse?> {
            override fun onResponse(call: Call<CafDetailResponse?>, response: Response<CafDetailResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    OutProgress()
                    val msg = response.body()?.Response?.Message
                    if(response.body()?.StatusCode=="200"){
                        Toast.makeText(this@CAFActivity, msg, Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@CAFActivity, CAFActivity::class.java)
                        val bundle = Bundle()
                        bundle.putString("OppId",strOppId )
                        intent.putExtras(bundle)
                        startActivity(intent)
                        finish()
                    }else{
                        Toast.makeText(this@CAFActivity, msg, Toast.LENGTH_SHORT).show()
                    }
                }
            }
            override fun onFailure(call: Call<CafDetailResponse?>, t: Throwable) {
                binding.createprogressLayout.progressOverlay.visibility = View.GONE
                Log.e("RetroError", t.toString())
            }
        })
    }

    fun submitCaf () {
        inProgress()
        val cafRequest = CafRequest(Constants.SUBMITCAF, Constants.AUTH_KEY, strCafId,"", password, userName,"")
        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getCaf(cafRequest)
        call.enqueue(object : Callback<CafDetailResponse?> {
            override fun onResponse(call: Call<CafDetailResponse?>, response: Response<CafDetailResponse?>) {
                OutProgress()
                if (response.isSuccessful && response.body() != null) {
                    val msg = response.body()?.Response?.Message
                    if(response.body()?.StatusCode=="200"){
                        Toast.makeText(this@CAFActivity, msg, Toast.LENGTH_SHORT).show()
                        tv_create.visibility=View.GONE
                        caf()
                    }else{
                        Toast.makeText(this@CAFActivity, msg, Toast.LENGTH_SHORT).show()
                    }
                }
            }
            override fun onFailure(call: Call<CafDetailResponse?>, t: Throwable) {
                binding.createprogressLayout.progressOverlay.visibility = View.GONE
                Log.e("RetroError", t.toString())
            }
        })
    }

    fun caf(){
        Intent(this, CAFActivity::class.java).also {
            it.putExtra("CafId", strCafId)
            it.putExtra("OppId", strOppId)
            startActivity(it)
            finish()
        }
    }


    fun getCaf () {
        inProgress()
        if(strCafId.isNullOrEmpty()){
            strOppId
        }else{
            strOppId=""
        }
        val cafRequest = CafRequest(Constants.GETCAF, Constants.AUTH_KEY, strCafId,strOppId, password, userName,"")
        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getCaf(cafRequest)
        call.enqueue(object : Callback<CafDetailResponse?> {
            override fun onResponse(call: Call<CafDetailResponse?>, response: Response<CafDetailResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        OutProgress()
                        //  val msg = response.body()!!.Response.Message
                        if (response.body()?.Response?.StatusCode == 200) {
                            strCafId = response.body()?.Response?.Data?.CafNo
                            wcr.add_quote.visibility=View.GONE
                            ir.add_fes.visibility=View.GONE
                            np.add_dao.visibility=View.GONE
                            linearerfs.visibility=View.GONE
                            if(strCafId.isNullOrBlank()){
                                tv_create.visibility=View.VISIBLE
                                //layout_payment.et_otc.visibility=View.GONE
                                tv_update.visibility=View.GONE
                                tv_submit.visibility=View.GONE
                                binding.searchtoolbarcaf.downloadpdf.visibility = View.GONE
                                binding.searchtoolbarcaf.share.visibility = View.GONE
                                linearwcr.visibility=View.GONE
                                linearnp.visibility=View.GONE
                                lineareir.visibility=View.GONE
                                linearten.visibility=View.GONE
                            }else{
                                tv_create.visibility=View.GONE
                                tv_update.visibility=View.VISIBLE
                                tv_submit.visibility=View.VISIBLE
                                binding.searchtoolbarcaf.downloadpdf.visibility = View.VISIBLE
                                binding.searchtoolbarcaf.share.visibility = View.GONE
                                linearwcr.visibility=View.VISIBLE
                                linearnp.visibility=View.VISIBLE
                                lineareir.visibility=View.VISIBLE
                                linearten.visibility=View.VISIBLE
                                getwcr()
                                getIr()
                                getNP()
                            }
                            strOppId =  response.body()?.Response?.Data?.OpportunityId2
                            binding.cafContactinfoLayout.cafContactInfo = response.body()?.Response?.Data
                            binding.layoutOtherinfo.cafOtherInfo = response.body()?.Response?.Data?.otherinformations
                            binding.layoutCafcompanyDetails.cafCompnyInfo = response.body()?.Response?.Data?.companyDetail
                            binding.layoutCafinstalAddress.cafInstAdress = response.body()?.Response?.Data?.installationAddresses
                            binding.cafContactPersonRow.cafBillAdd= response.body()?.Response?.Data?.billingAddress
                            binding.layoutCafothrDetails.cafauthoInfo = response.body()?.Response?.Data?.authSigDetails
                            binding.layoutPayment.cafPaymentInfo = response.body()?.Response?.Data?.payments
                            val network = response.body()?.Response?.Data?.NetworkTechnology
                            strBankName = response.body()?.Response?.Data?.payments?.BankName
                            val productseg =  response.body()?.Response?.Data?.ProductSegment
                            if(productseg=="Managed Office Solution"||productseg=="Secured Managed Internet"){
                                linearerfs.visibility=View.VISIBLE
                                getRfs()
                            }
                            getBankList()
                            strPaymentStatus =response.body()?.Response?.Data?.payments?.PaymentStatus
                            if(network=="111260000"){
                                caf_contactinfo_layout.et_netwrktech.setText(SalesConstant.GPON)
                            }else if(network=="111260001"){
                                caf_contactinfo_layout.et_netwrktech.setText(SalesConstant.NON_GPON)
                            }
                            /*  layout_payment.et_pannum.setText(response.body()?.Response?.Data?.PanNo)
                              layout_payment.et_tannum.setText(response.body()?.Response?.Data?.TanNo)
                              layout_payment.et_gstnum.setText(response.body()?.Response?.Data?.GstNumberDetial)
                            */
                            strProductId= response.body()?.Response?.Data?.ProductId
                            str_inststateId= response.body()?.Response?.Data?.installationAddresses?.Inst_State
                            str_blinststateId = response.body()?.Response?.Data?.billingAddress?.Bill_State
                            str_sub_bus = response.body()?.Response?.Data?.SubBussinessSegment
                            str_PrfCom = response.body()?.Response?.Data?.PreferredCommMode
                            str_cmpnyself = response.body()?.Response?.Data?.otherinformations?.CompanySelfPo
                            str_provider = response.body()?.Response?.Data?.otherinformations?.ExistingServiceProvider
                            str_frwall = response.body()?.Response?.Data?.otherinformations?.FireWall
                            str_firmtype= response.body()?.Response?.Data?.companyDetail?.FirmType
                            str_atinststateId = response.body()?.Response?.Data?.authSigDetails?.Auth_State
                            str_payslip = response.body()?.Response?.Data?.payments?.PayInSlip
                            str_wrkngdays = response.body()?.Response?.Data?.BusinessDays
                            val strNetwork = response.body()?.Response?.Data?.NtwMonitAlert
                            str_customercategory = response.body()?.Response?.Data?.installationAddresses?.Inst_CategoryofCustomer
                            val strWrkngHrs = response.body()?.Response?.Data?.CustomerWorkingHours
                            str_voip = response.body()?.Response?.Data?.installationAddresses?.Inst_VoidPort
                            str_billtype = response.body()?.Response?.Data?.installationAddresses?.Inst_BillType
                            str_sctype= response.body()?.Response?.Data?.payments?.SecurityDepositType
                            str_gstval = response.body()?.Response?.Data?.GstNumber
                            str_city_code = response.body()?.Response?.Data?.installationAddresses?.Inst_City
                            str_blcity_code = response.body()?.Response?.Data?.billingAddress?.Bill_City
                            str_atcity_code = response.body()?.Response?.Data?.authSigDetails?.Auth_City
                            str_add_area= response.body()?.Response?.Data?.installationAddresses?.Inst_Area
                            str_inst_building_nm = response.body()?.Response?.Data?.installationAddresses?.Inst_BuildingName
                            str_bladd_area= response.body()?.Response?.Data?.billingAddress?.Bill_Area
                            str_blinst_building_nm = response.body()?.Response?.Data?.billingAddress?.Bill_BuildingName
                            strCompany = response.body()?.Response?.Data?.Company.toString()
                            strRelation = response.body()?.Response?.Data?.Relationship.toString()
                            strGroup = response.body()?.Response?.Data?.Group.toString()
                            str_indusid = response.body()?.Response?.Data?.companyDetail?.IndustryType

                            getCompany(strCompany)
                            getRelation(strRelation)
                            getIndustryTpe()


                            var cntstatePosition = 0
                            resources.getStringArray(R.array.list_state_code).forEachIndexed { index, s ->
                                if (s == str_inststateId) cntstatePosition = index
                            }
                            val cntstateAdapter = ArrayAdapter(this@CAFActivity, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.list_of_state))
                            cntstateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            layout_cafinstal_address.sp_cafstate.adapter = cntstateAdapter
                            layout_cafinstal_address.sp_cafstate.setSelection(cntstatePosition)
                            cntstateAdapter.notifyDataSetChanged()

                            var atstatePos = 0
                            resources.getStringArray(R.array.list_state_code).forEachIndexed {
                                    index, s ->
                                if (s == str_atinststateId)
                                    atstatePos = index
                            }
                            val atstateAdapter = ArrayAdapter(this@CAFActivity, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.list_of_state))
                            atstateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            layout_cafothr_details.sp_cafauthostate.adapter = atstateAdapter
                            layout_cafothr_details.sp_cafauthostate.setSelection(atstatePos)
                            atstateAdapter.notifyDataSetChanged()
                            var blstatePosition = 0
                            resources.getStringArray(R.array.list_state_code).forEachIndexed { index, s ->
                                if (s == str_blinststateId) blstatePosition = index
                                return@forEachIndexed
                            }
                            val blstateAdapter = ArrayAdapter(this@CAFActivity, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.list_of_state))
                            blstateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            caf_contact_person_row.sp_cfblstate.adapter = blstateAdapter
                            caf_contact_person_row.sp_cfblstate.setSelection(blstatePosition)
                            blstateAdapter.notifyDataSetChanged()

                            var sbBusPosition = 0
                            resources.getStringArray(R.array.list_of_subBusSegment).forEachIndexed { index, s ->
                                if (s == str_sub_bus) sbBusPosition = index
                            }
                            val sbBusAdapter = ArrayAdapter(this@CAFActivity, android.R.layout.simple_spinner_item,resources.getStringArray(R.array.list_of_subBusSegment))
                            sbBusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            caf_contactinfo_layout.sp_cafsbbus.adapter = sbBusAdapter
                            caf_contactinfo_layout.sp_cafsbbus.setSelection(sbBusPosition)
                            sbBusAdapter.notifyDataSetChanged()

                            var prfPosition = 0
                            resources.getStringArray(R.array.list_of_prefferedvalue).forEachIndexed { index, s ->
                                if (s == str_PrfCom) prfPosition = index
                            }
                            val prefAdapter = ArrayAdapter(this@CAFActivity, android.R.layout.simple_spinner_item,resources.getStringArray(R.array.list_of_preffered))
                            prefAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            caf_contactinfo_layout.sp_preffered_cmmnctn.adapter = prefAdapter
                            caf_contactinfo_layout.sp_preffered_cmmnctn.setSelection(prfPosition)
                            prefAdapter.notifyDataSetChanged()

                            var selfPosition = 0
                            resources.getStringArray(R.array.list_of_boolean_values).forEachIndexed { index, s ->
                                if (s == str_cmpnyself) selfPosition = index
                            }
                            val selfAdapter = ArrayAdapter(this@CAFActivity, android.R.layout.simple_spinner_item,resources.getStringArray(R.array.list_of_boolean))
                            selfAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            layout_otherinfo.sp_cafcmpny_self.adapter = selfAdapter
                            layout_otherinfo.sp_cafcmpny_self.setSelection(selfPosition)
                            selfAdapter.notifyDataSetChanged()

                            var firmPosition = 0
                            resources.getStringArray(R.array.list_firm_type_value).forEachIndexed { index, s ->
                                if (s == str_firmtype) firmPosition = index
                            }
                            val firmtypeAdapter = ArrayAdapter(this@CAFActivity, android.R.layout.simple_spinner_item,resources.getStringArray(R.array.list_firm_type))
                            firmtypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            layout_cafcompany_details.sp_caffirm_type.adapter = firmtypeAdapter
                            layout_cafcompany_details.sp_caffirm_type.setSelection(firmPosition)
                            firmtypeAdapter.notifyDataSetChanged()

                            var providerPosition = 0
                            resources.getStringArray(R.array.ext_serv_one_values).forEachIndexed { index, s ->
                                if (s == str_provider) providerPosition = index
                            }
                            val provAdapter = ArrayAdapter(this@CAFActivity, android.R.layout.simple_spinner_item,resources.getStringArray(R.array.ext_serv_one))
                            provAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            layout_otherinfo.sp_caf_pro.adapter = provAdapter
                            layout_otherinfo.sp_caf_pro.setSelection(providerPosition)
                            provAdapter.notifyDataSetChanged()

                            var firewallPosition = 0
                            resources.getStringArray(R.array.list_of_boolean_values).forEachIndexed { index, s ->
                                if (s == str_frwall) firewallPosition = index
                            }
                            val firewallAdapter = ArrayAdapter(this@CAFActivity, android.R.layout.simple_spinner_item,resources.getStringArray(R.array.list_of_boolean))
                            firewallAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            layout_otherinfo.sp_caffrwal.adapter = firewallAdapter
                            layout_otherinfo.sp_caffrwal.setSelection(firewallPosition)
                            firewallAdapter.notifyDataSetChanged()

                            var workingPosition = 0
                            resources.getStringArray(R.array.list_of_wrkngdaysvalues).forEachIndexed { index, s ->
                                if (s == str_wrkngdays) workingPosition = index
                            }
                            val workingAdapter = ArrayAdapter(this@CAFActivity, android.R.layout.simple_spinner_item,resources.getStringArray(R.array.list_of_wrkngdays))
                            workingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            caf_contactinfo_layout.sp_wrkng_days.adapter = workingAdapter
                            caf_contactinfo_layout.sp_wrkng_days.setSelection(workingPosition)
                            workingAdapter.notifyDataSetChanged()

                            var networkPosition = 0
                            resources.getStringArray(R.array.list_of_monitoringvalues).forEachIndexed { index, s ->
                                if (s == strNetwork) networkPosition = index
                            }
                            val networkAdapter = ArrayAdapter(this@CAFActivity, android.R.layout.simple_spinner_item,resources.getStringArray(R.array.list_of_monitoring))
                            networkAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            caf_contactinfo_layout.sp_ntwrkmtr.adapter = networkAdapter
                            caf_contactinfo_layout.sp_ntwrkmtr.setSelection(networkPosition)
                            networkAdapter.notifyDataSetChanged()

                            var customerPosition = 0
                            resources.getStringArray(R.array.list_of_cstctgryvalues).forEachIndexed { index, s ->
                                if (s == str_customercategory) customerPosition = index
                            }
                            val customerAdapter = ArrayAdapter(this@CAFActivity, android.R.layout.simple_spinner_item,resources.getStringArray(R.array.list_of_cstmrcategory))
                            customerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            layout_cafinstal_address.sp_custctgry.adapter = customerAdapter
                            layout_cafinstal_address.sp_custctgry.setSelection(customerPosition)
                            customerAdapter.notifyDataSetChanged()

                            var hrsPosition = 0
                            resources.getStringArray(R.array.list_of_wrknghoursval).forEachIndexed { index, s ->
                                if (s == strWrkngHrs) hrsPosition = index
                            }
                            val hrsAdapter = ArrayAdapter(this@CAFActivity, android.R.layout.simple_spinner_item,resources.getStringArray(R.array.list_of_wrknghours))
                            hrsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            caf_contactinfo_layout.sp_cstmrwrknghrs.adapter = hrsAdapter
                            caf_contactinfo_layout.sp_cstmrwrknghrs.setSelection(hrsPosition)
                            hrsAdapter.notifyDataSetChanged()

                            var voipPosition = 0
                            resources.getStringArray(R.array.list_of_cstctgryvalues).forEachIndexed { index, s ->
                                if (s == str_voip) voipPosition = index
                            }
                            val voipAdapter = ArrayAdapter(this@CAFActivity, android.R.layout.simple_spinner_item,resources.getStringArray(R.array.list_of_monitoring))
                            voipAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            layout_cafinstal_address.sp_voip.adapter = voipAdapter
                            layout_cafinstal_address.sp_voip.setSelection(voipPosition)
                            voipAdapter.notifyDataSetChanged()

                            var billtypePosition = 0
                            resources.getStringArray(R.array.list_of_boolean_values).forEachIndexed { index, s ->
                                if (s == str_billtype) billtypePosition = index
                            }
                            val billtypeAdapter = ArrayAdapter(this@CAFActivity, android.R.layout.simple_spinner_item,resources.getStringArray(R.array.list_of_billtype))
                            billtypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            layout_cafinstal_address.sp_cafbilltype.adapter = billtypeAdapter
                            layout_cafinstal_address.sp_cafbilltype.setSelection(billtypePosition)
                            billtypeAdapter.notifyDataSetChanged()

                            var payslipPosition = 0
                            resources.getStringArray(R.array.list_of_payslipval).forEachIndexed { index, s ->
                                if (s == str_payslip) payslipPosition = index
                            }
                            val payslipAdapter = ArrayAdapter(this@CAFActivity, android.R.layout.simple_spinner_item,resources.getStringArray(R.array.list_of_payslip))
                            payslipAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            layout_payment.sp_payslip.adapter = payslipAdapter
                            layout_payment.sp_payslip.setSelection(payslipPosition)
                            payslipAdapter.notifyDataSetChanged()

                            var securityPosition = 0
                            resources.getStringArray(R.array.list_of_boolean_values).forEachIndexed { index, s ->
                                if (s == str_sctype) securityPosition = index
                            }
                            val securityAdapter = ArrayAdapter(this@CAFActivity, android.R.layout.simple_spinner_item,resources.getStringArray(R.array.list_of_Deposit))
                            securityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            layout_payment.sp_securitytype.adapter = securityAdapter
                            layout_payment.sp_securitytype.setSelection(securityPosition)
                            securityAdapter.notifyDataSetChanged()

                            var gstPosition = 0
                            resources.getStringArray(R.array.listDNCVal).forEachIndexed { index, s ->
                                if (s == str_gstval) gstPosition = index
                            }
                            val gstAdapter = ArrayAdapter(this@CAFActivity, android.R.layout.simple_spinner_item,resources.getStringArray(R.array.list_of_gst))
                            gstAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            binding.layoutPayment.spGst.adapter = gstAdapter
                            binding.layoutPayment.spGst.setSelection(gstPosition)
                            gstAdapter.notifyDataSetChanged()

                            val polockDate = response.body()?.Response?.Data?.otherinformations?.PoLock
                            if(polockDate?.isNotEmpty()==true){
                                val split1 = polockDate.split("-")
                                val date1 = split1.get(0)
                                val month1 = split1.get(1)
                                val year1 = split1.get(2)
                                layout_otherinfo.et_caflock.setText("$date1-$month1-$year1")
                                caflock = ("$year1-$month1-$date1")
                            }

                            val ponextdate = response.body()?.Response?.Data?.otherinformations?.PoNext
                            if(ponextdate?.isNotEmpty()==true){
                                val split2 = ponextdate.split("-")
                                val date2 = split2.get(0)
                                val month2 = split2.get(1)
                                val year2 = split2.get(2)
                                layout_otherinfo.et_cafnxt.setText("$date2-$month2-$year2")
                                cafnext = ("$year2-$month2-$date2")
                            }

                            val frwamcexpiry = response.body()?.Response?.Data?.otherinformations?.FirewallAMC
                            if(frwamcexpiry?.isNotEmpty()==true){
                                val split2 = frwamcexpiry.split("-")
                                val date2 = split2.get(0)
                                val month2 = split2.get(1)
                                val year2 = split2.get(2)
                                layout_otherinfo.et_frwas.setText("$date2-$month2-$year2")
                                frwamc = ("$year2-$month2-$date2")
                            }


                            val paymentDate = response.body()?.Response?.Data?.payments?.PaymentDate
                            if(paymentDate?.isNotEmpty()==true){
                                val split2 = paymentDate.split("-")
                                val date2 = split2.get(0)
                                val month2 = split2.get(1)
                                val year2 = split2.get(2)
                                layout_payment.et_paymntdt.setText("$date2-$month2-$year2")
                                cafpaydate = ("$year2-$month2-$date2")
                            }

                            val chqdate = response.body()?.Response?.Data?.payments?.ChequeDDDate
                            if(chqdate?.isNotEmpty()==true){
                                val split2 = chqdate.split("-")
                                val date2 = split2.get(0)
                                val month2 = split2.get(1)
                                val year2 = split2.get(2)
                                layout_payment.et_paymntdt.setText("$date2-$month2-$year2")
                                checkdate = ("$year2-$month2-$date2")
                            }
                            layout_cafinstal_address.et_cafstate.isEnabled=false
                            layout_cafinstal_address.et_add_cafcity.isEnabled=false
                            layout_cafinstal_address.et_cafinstallarea.isEnabled=false
                            layout_cafinstal_address.et_cafbuildingname.isEnabled=false

                            status = response.body()?.Response?.Data?.Status

                            if((status=="569480015")||(status=="569480012")||(status=="569480013")||(status=="569480014")||
                                (status=="569480005")||(status=="569480006")||(status=="569480000")||(status=="569480001")||
                                (status=="569480002")||(status=="569480003")||(status=="569480004")||(status=="1") ||
                                (status=="569480009")||(status=="569480010")||(status=="2")){
                                locked()
                            }else{
                                Calender()
                            }

                        }
                        else{
                            locked()
                            wcr.add_quote.visibility=View.GONE
                            ir.add_fes.visibility=View.GONE
                            np.add_dao.visibility=View.GONE
                            linearwcr.visibility=View.GONE
                            linearnp.visibility=View.GONE
                            lineareir.visibility=View.GONE

                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<CafDetailResponse?>, t: Throwable) {
                binding.createprogressLayout.progressOverlay.visibility = View.GONE
                Log.e("RetroError", t.toString())
            }
        })
    }

    fun inProgress(){
        inAnimation = AlphaAnimation(0f, 1f)
        inAnimation?.duration =200
        binding.createprogressLayout.progressOverlay.animation = inAnimation
        binding.createprogressLayout.progressOverlay.visibility = View.VISIBLE
    }

    fun OutProgress(){
        outAnimation = AlphaAnimation(1f, 0f)
        outAnimation?.duration =200
        binding.createprogressLayout.progressOverlay.animation = outAnimation
        binding.createprogressLayout.progressOverlay.visibility = View.GONE
    }



    fun getwcr() {
        //  inProgress()
        val getDocCafReq = GetDocCafReq(Constants.GETWCR,Constants.AUTH_KEY,strCafId,password,userName)

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getWCRList(getDocCafReq)
        call.enqueue(object : Callback<GetCafWCRResponse?> {
            override fun onResponse(call: Call<GetCafWCRResponse?>, response: Response<GetCafWCRResponse?>) {
                //   OutProgress()
                if (response.isSuccessful && response.body() != null) {
                    try {
                        val msg = response.body()?.Response?.Message
                        if (response.body()?.Response?.StatusCode==200) {
                            try {
                                wcrList = response.body()?.Response?.Data
                                if(wcrList?.isNotEmpty()==true) {
                                    setWcrAdapter(wcrList, this@CAFActivity)
                                }
                            } catch (e: java.lang.Exception) {
                                e.printStackTrace()
                            }
                        }
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }
                }
            }
            override fun onFailure(call: Call<GetCafWCRResponse?>, t: Throwable) {
                Log.e("RetroError", t.toString())
            }
        })
    }


    private fun setWcrAdapter(allProductItem: ArrayList<WCRData>?, context: Context?) {
        rv_add_quote?.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = allProductItem?.let { GetWCRAdapter(it,context) }
        }
    }


    fun getRfs() {
        //  inProgress()
        val getDocCafReq = GetDocCafReq(Constants.getRFS,Constants.AUTH_KEY,strCafId,password,userName)

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getRFSList(getDocCafReq)
        call.enqueue(object : Callback<GetRFSResponse?> {
            override fun onResponse(call: Call<GetRFSResponse?>, response: Response<GetRFSResponse?>) {
                //   OutProgress()
                if (response.isSuccessful && response.body() != null) {
                    try {
                        val msg = response.body()?.Response?.Message
                        if (response.body()?.Response?.StatusCode==200) {
                            try {
                                rfsList = response.body()?.Response?.Data
                                if(rfsList?.isNotEmpty()==true) {
                                    setRFSAdapter(rfsList, this@CAFActivity)
                                }
                            } catch (e: java.lang.Exception) {
                                e.printStackTrace()
                            }
                        }
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }
                }
            }
            override fun onFailure(call: Call<GetRFSResponse?>, t: Throwable) {
                Log.e("RetroError", t.toString())
            }
        })
    }


    private fun setRFSAdapter(allRFSItem: ArrayList<RFSData>?, context: Context?) {
        rv_add_rfs?.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = allRFSItem?.let { GetRFSAdapter(it,context) }
        }
    }

    fun getIr() {
        //  inProgress()
        val getDocCafReq = GetDocCafReq(Constants.getIR,Constants.AUTH_KEY,strCafId,password,userName)

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getIRList(getDocCafReq)
        call.enqueue(object : Callback<GetCafIRResponse?> {
            override fun onResponse(call: Call<GetCafIRResponse?>, response: Response<GetCafIRResponse?>) {
                //   OutProgress()
                if (response.isSuccessful && response.body() != null) {
                    try {
                        val msg = response.body()!!.Response.Message
                        if (response.body()?.Response?.StatusCode==200) {
                            try {
                                irList = response.body()?.Response?.Data
                                if(irList?.isNotEmpty()==true) {
                                    setIrAdapter(irList!!, this@CAFActivity)
                                }
                            } catch (e: java.lang.Exception) {
                                e.printStackTrace()
                            }
                        }
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }
                }
            }
            override fun onFailure(call: Call<GetCafIRResponse?>, t: Throwable) {
                //  binding.opprogressLayout.progressOverlay.visibility=View.GONE
                Log.e("RetroError", t.toString())
            }
        })
    }
    private fun setIrAdapter(irData:  ArrayList<IRData>, context: Context?) {
        rv_add_fes?.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = GetIRAdapter(irData,context)
        }
    }

    fun getNP() {
        //  inProgress()
        val getDocCafReq = GetDocCafReq(Constants.GETNP,Constants.AUTH_KEY,strCafId,password,userName)

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getNPList(getDocCafReq)
        call.enqueue(object : Callback<GetCafNPResponse?> {
            override fun onResponse(call: Call<GetCafNPResponse?>, response: Response<GetCafNPResponse?>) {
                //   OutProgress()
                if (response.isSuccessful && response.body() != null) {
                    try {
                        val msg = response.body()?.Response?.Message
                        if (response.body()?.Response?.StatusCode==200) {
                            try {
                                npList = response.body()?.Response?.Data
                                if(npList?.isNotEmpty()==true) {
                                    setNPAdapter(npList!!, this@CAFActivity)
                                }
                            } catch (e: java.lang.Exception) {
                                e.printStackTrace()
                            }
                        }

                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }
                }
            }
            override fun onFailure(call: Call<GetCafNPResponse?>, t: Throwable) {
                Log.e("RetroError", t.toString())
            }
        })
    }
    private fun setNPAdapter(npData:  ArrayList<NPData>, context: Context?) {
        rv_add_doa?.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = GetNPAdapter(npData,context)
        }
    }

    fun locked(){
        caf_contactinfo_layout.et_cstname.isEnabled= false
        caf_contactinfo_layout.et_cstmrwrknghrs.isEnabled= false
        caf_contactinfo_layout.sp_cstmrwrknghrs.isEnabled=false
        caf_contactinfo_layout.sp_cafcompany.isEnabled=false
        caf_contactinfo_layout.sp_cafgroup.isEnabled=false
        caf_contactinfo_layout.sp_cafrelation.isEnabled=false
        caf_contactinfo_layout.sp_cafsbbus.isEnabled=false
        caf_contactinfo_layout.sp_preffered_cmmnctn.isEnabled=false
        caf_contactinfo_layout.sp_wrkng_days.isEnabled=false
        caf_contactinfo_layout.et_cafcmpny.isEnabled=false
        caf_contactinfo_layout.et_cafgrp.isEnabled=false
        caf_contactinfo_layout.et_cafrelation.isEnabled=false
        caf_contactinfo_layout.et_cafbs_sgmnt.isEnabled=false
        caf_contactinfo_layout.et_sb_bs_sgmnt.isEnabled=false
        caf_contactinfo_layout.et_prfcom.isEnabled=false
        caf_contactinfo_layout.et_wrkngdys.isEnabled=false
        caf_contactinfo_layout.et_ntwrkmtr.isEnabled=false
        layout_otherinfo.et_caflock.isEnabled=false
        layout_otherinfo.et_cafcmpny_self.isEnabled=false
        layout_otherinfo.et_cafno.isEnabled=false
        layout_otherinfo.et_cafnxt.isEnabled=false
        layout_otherinfo.sp_caf_pro.isEnabled=false
        layout_otherinfo.sp_caffrwal.isEnabled=false
        layout_cafcompany_details.et_cafindus_type.isEnabled=false
        layout_otherinfo.et_provider.isEnabled=false
        layout_otherinfo.et_caffrwl.isEnabled=false
        layout_otherinfo.sp_cafcmpny_self.isEnabled=false
        layout_cafcompany_details.et_compnyname.isEnabled=false
        layout_cafcompany_details.sp_caffirm_type.isEnabled=false
        layout_cafcompany_details.sp_cafindustype.isEnabled=false
        layout_cafcompany_details.et_caffirm_type.isEnabled=false
        layout_cafcompany_details.et_lco.isEnabled=false
        layout_cafinstal_address.et_cafemail.isEnabled=false
        layout_cafinstal_address.et_cafmbnum.isEnabled=false
        layout_cafinstal_address.et_cafleadname.isEnabled=false
        layout_cafinstal_address.et_cafleadid.isEnabled=false
        layout_cafinstal_address.sp_cafbilltype.isEnabled=false
        layout_cafinstal_address.sp_cafstate.isEnabled=false
        layout_cafinstal_address.sp_cafcity.isEnabled=false
        layout_cafinstal_address.sp_cafcnarea.isEnabled=false
        layout_cafinstal_address.sp_cafbuilding_nm.isEnabled=false
        layout_cafinstal_address.sp_cafstatus.isEnabled=false
        layout_cafinstal_address.et_cafblcode_red.isEnabled=false
        layout_cafinstal_address.et_bldaddress.isEnabled=false
        layout_cafinstal_address.sp_voip.isEnabled=false
        layout_cafinstal_address.et_cafpin.isEnabled=false
        layout_cafinstal_address.sp_custctgry.isEnabled=false
        layout_cafinstal_address.et_cafbiltype.isEnabled=false
        layout_cafinstal_address.et_cafstate.isEnabled=false
        layout_cafinstal_address.et_add_cafcity.isEnabled=false
        layout_cafinstal_address.et_cafinstallarea.isEnabled=false
        layout_cafinstal_address.et_cafbuildingname.isEnabled=false
        layout_cafinstal_address.et_cafbuilding_status.isEnabled=false
        layout_cafinstal_address.et_cafvoip.isEnabled=false
        layout_cafinstal_address.et_custctgry.isEnabled=false
        caf_contact_person_row.et_caf_cntname.isEnabled=false
        caf_contact_person_row.et_caf_bilngemailid.isEnabled=false
        caf_contact_person_row.et_cafphn_num.isEnabled=false
        caf_contact_person_row.sp_cfblstate.isEnabled=false
        caf_contact_person_row.sp_cfblcity.isEnabled=false
        caf_contact_person_row.sp_cfblcnarea.isEnabled=false
        caf_contact_person_row.sp_cfblbuilding_nm.isEnabled=false
        caf_contact_person_row.et_cfblbuilding_num.isEnabled=false
        caf_contact_person_row.et_cfblbuildng_num.isEnabled=false
        caf_contact_person_row.et_cfblfloor.isEnabled=false
        caf_contact_person_row.et_cfblpin_code.isEnabled=false
        caf_contact_person_row.et_cfblstate.isEnabled=false
        caf_contact_person_row.et_cfblcity.isEnabled=false
        caf_contact_person_row.et_cfblarea.isEnabled=false
        caf_contact_person_row.et_cfblbuilding.isEnabled=false
        layout_cafothr_details.et_cafname.isEnabled=false
        layout_cafothr_details.et_fthr_hsb.isEnabled=false
        layout_cafothr_details.et_address.isEnabled=false
        layout_cafothr_details.et_authomob.isEnabled=false
        layout_cafothr_details.et_cafemailid.isEnabled=false
        layout_cafothr_details.et_national.isEnabled=false
        layout_cafothr_details.sp_cafauthostate.isEnabled=false
        layout_cafothr_details.sp_cafauthcity.isEnabled=false
        layout_cafothr_details.et_cafauthpincode.isEnabled=false
        layout_cafothr_details.et_cafauthstate.isEnabled=false
        layout_cafothr_details.et_add_cafauthcity.isEnabled=false
        layout_payment.et_cafpyid.isEnabled=false
        layout_payment.sp_payslip.isEnabled=false
        layout_payment.et_payslip.isEnabled=false
        layout_payment.et_totalamt.isEnabled=false
        layout_payment.et_securitycdeposit.isEnabled=false
        layout_payment.sp_securitytype.isEnabled=false
        layout_payment.et_sctype.isEnabled=false
        layout_payment.et_txtid.isEnabled=false
        layout_payment.et_paymntdt.isEnabled=false
        layout_payment.sp_bnknam.isEnabled=false
        layout_payment.et_brnch.isEnabled=false
        layout_payment.et_chknum.isEnabled=false
        layout_payment.et_chkdate.isEnabled=false
        layout_payment.et_appcode.isEnabled=false
        layout_payment.et_cardfrdgt.isEnabled=false
        layout_payment.et_creditfrdgt.isEnabled=false
        layout_payment.et_pannum.isEnabled=false
        layout_payment.et_tannum.isEnabled=false
        layout_payment.sp_gst.isEnabled=false
        layout_payment.et_gst.isEnabled=false
        layout_payment.et_gstt.isEnabled=false
        tv_create.visibility=View.GONE
        tv_submit.visibility = View.GONE
        tv_update.visibility = View.GONE
    }

    @SuppressLint("SetTextI18n")
    fun  Calender(){
        try {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            layout_otherinfo.et_caflock.setOnClickListener {
                val dpd = DatePickerDialog(this, { _, year, monthOfYear, dayOfMonth ->
                    val mnth = monthOfYear + 1
                    layout_otherinfo.et_caflock.setText("$dayOfMonth-$mnth-$year")
                    val trgt = layout_otherinfo.et_caflock.text.toString()
                    val split = trgt.split("-")
                    val dateee = split[0]
                    val month1 = split[1]
                    val year1 = split[2]
                    caflock = ("$year1-$month1-$dateee")
                }, year, month, day)
                dpd.show()
            }

            layout_otherinfo.et_cafnxt.setOnClickListener {
                val dpd = DatePickerDialog(this, { _, year, monthOfYear, dayOfMonth ->
                    val mnth = monthOfYear + 1
                    layout_otherinfo.et_cafnxt.setText("$dayOfMonth-$mnth-$year")
                    val trgt = layout_otherinfo.et_cafnxt.text.toString()
                    val split = trgt.split("-")
                    val dateee = split[0]
                    val month1 = split[1]
                    val year1 = split[2]
                    cafnext = ("$year1-$month1-$dateee")
                }, year, month, day)
                dpd.show()
            }
            layout_otherinfo.et_frwas.setOnClickListener {
                val dpd = DatePickerDialog(this, { _, year, monthOfYear, dayOfMonth ->
                    val mnth = monthOfYear + 1
                    layout_otherinfo.et_frwas.setText("$dayOfMonth-$mnth-$year")
                    val trgt = layout_otherinfo.et_frwas.text.toString()
                    val split = trgt.split("-")
                    val dateee = split[0]
                    val month1 = split[1]
                    val year1 = split[2]
                    frwamc = ("$year1-$month1-$dateee")
                }, year, month, day)
                dpd.show()
            }

            layout_payment.et_paymntdt.setOnClickListener {
                val dpd = DatePickerDialog(this, { _, year, monthOfYear, dayOfMonth ->
                    val mnth = monthOfYear + 1
                    layout_payment.et_paymntdt.setText("$dayOfMonth-$mnth-$year")
                    val trgt = layout_payment.et_paymntdt.text.toString()
                    val split = trgt.split("-")
                    val dateee = split[0]
                    val month1 = split[1]
                    val year1 = split[2]
                    cafpaydate = ("$year1-$month1-$dateee")
                }, year, month, day)
                dpd.show()
            }
            layout_payment.et_chkdate.setOnClickListener {
                val dpd = DatePickerDialog(this, { _, year, monthOfYear, dayOfMonth ->
                    val mnth = monthOfYear + 1
                    layout_payment.et_chkdate.setText("$dayOfMonth-$mnth-$year")
                    val trgt = layout_payment.et_chkdate.text.toString()
                    val split = trgt.split("-")
                    val dateee = split[0]
                    val month1 = split[1]
                    val year1 = split[2]
                    checkdate = ("$year1-$month1-$dateee")
                }, year, month, day)
                dpd.show()
            }

        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.rl_back -> {
                next()
            }
        }
    }

    fun getCompany(strCompany: String) {
        inProgress()
        val getCompanyRequest = GetCompanyRequest(Constants.GET_COMPANY,Constants.AUTH_KEY,strCompany,password,userName)
        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getComapnyList(getCompanyRequest)
        call.enqueue(object : Callback<GetCompanyResponse?> {
            override fun onResponse(call: Call<GetCompanyResponse?>, response: Response<GetCompanyResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        OutProgress()
                        val img = response.body()?.Response?.Message
                        img?.let { Log.e("image", it) }
                        companyList= response.body()?.Response?.Data
                        company = ArrayList<String>()
                        companyId = ArrayList<String>()
                        company?.add("Select Company")
                        companyId?.add("")
                        group = ArrayList<String>()
                        groupId = ArrayList<String>()
                        group?.add("Select Group")
                        groupId?.add("")
                        for (item in companyList!!){
                            company?.add(item.Company_Name)
                            companyId?.add(item.Company_ID)
                            group?.add(item.Group_Name)
                            groupId?.add(item.Group_ID)
                        }

                        var comPosition=0
                        company?.forEachIndexed { index, s ->
                            if (s == strCompany) {
                                comPosition = index
                                return@forEachIndexed
                            }
                        }

                        var groupPosition=0
                        group?.forEachIndexed { index, s ->
                            if(s==strGroup)
                                groupPosition=index
                            return@forEachIndexed
                        }
                        val adapter12 = ArrayAdapter(this@CAFActivity, android.R.layout.simple_spinner_item, company!!)
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        caf_contactinfo_layout.sp_cafcompany.adapter = adapter12
                        caf_contactinfo_layout.sp_cafcompany.setSelection(comPosition)
                        adapter12.notifyDataSetChanged()

                        val adapter11 = ArrayAdapter(this@CAFActivity, android.R.layout.simple_spinner_item, group!!)
                        adapter11.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        caf_contactinfo_layout.sp_cafgroup.adapter = adapter11
                        caf_contactinfo_layout.sp_cafgroup.setSelection(groupPosition)
                        adapter11.notifyDataSetChanged()
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }
                }
            }
            override fun onFailure(call: Call<GetCompanyResponse?>, t: Throwable) {
                binding.createprogressLayout.progressOverlay.visibility = View.GONE
                Log.e("RetroError", t.toString())
            }
        })
    }

    fun getRelation(str_cmny: String?) {
        val getCompanyRequest = GetCompanyRequest(Constants.GET_RELATIONSHIP,Constants.AUTH_KEY,str_cmny.toString(),password,userName)
        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getRelationList(getCompanyRequest)
        call.enqueue(object : Callback<GetRelationShipResponse?> {
            override fun onResponse(call: Call<GetRelationShipResponse?>, response: Response<GetRelationShipResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        val img = response.body()?.Response?.Message
                        img?.let { Log.e("image", it) }
                        val relationList= response.body()?.Response?.Data
                        relation = ArrayList<String>()
                        relationId = ArrayList<String>()
                        relation?.add("Select Relation")
                        relationId?.add("")
                        if (relationList != null) {
                            for (item in relationList){
                                item.Relationship_Name?.let { relation?.add(it) }
                                item.Relationship_ID?.let { relationId?.add(it) }
                            }
                        }
                        var relationPosition=0
                        relation?.forEachIndexed { index, s ->
                            if(s==strRelation)relationPosition=index
                            return@forEachIndexed
                        }
                        val adapter12 = ArrayAdapter(this@CAFActivity, android.R.layout.simple_spinner_item, relation!!)
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        caf_contactinfo_layout.sp_cafrelation.adapter = adapter12
                        caf_contactinfo_layout.sp_cafrelation.setSelection(relationPosition)
                        adapter12.notifyDataSetChanged()
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<GetRelationShipResponse?>, t: Throwable) {
                Log.e("RetroError", t.toString())
            }
        })

    }

    fun getIndustryTpe() {
        val getLeadBuildingRequest = GetLeadBuildingRequest(Constants.GET_INDUSTRYTYPE,Constants.AUTH_KEY,"","",password,userName)

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getIndustry(getLeadBuildingRequest)
        call.enqueue(object : Callback<GetIndustryTypeResponse?> {
            override fun onResponse(call: Call<GetIndustryTypeResponse?>, response: Response<GetIndustryTypeResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        industryList= response.body()?.Response
                        instryname = ArrayList<String>()
                        industryid = ArrayList<String>()
                        instryname.add("Select Industry")
                        industryid.add("0")
                        for (item in industryList!!){
                            instryname.add(item.IndTypeName)
                            industryid.add(item.IndTypeId)
                        }
                        var industryPosition=0
                        industryid.forEachIndexed { index, s ->
                            if(s==str_indusid)industryPosition=index
                            return@forEachIndexed
                        }
                        val adapter12 = ArrayAdapter(this@CAFActivity, android.R.layout.simple_spinner_item, instryname)
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        layout_cafcompany_details.sp_cafindustype.adapter = adapter12
                        layout_cafcompany_details.sp_cafindustype.setSelection(industryPosition)
                        adapter12.notifyDataSetChanged()
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<GetIndustryTypeResponse?>, t: Throwable) {
                Log.e("RetroError", t.toString())
            }
        })
    }


    fun getBankList() {
        val cafReqest = CafRequest(Constants.GETBANK,Constants.AUTH_KEY,"","",password,userName,"")

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getBankList(cafReqest)
        call.enqueue(object : Callback<BankListResponse?> {
            override fun onResponse(call: Call<BankListResponse?>, response: Response<BankListResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        bankList= response.body()?.Response?.Data
                        bankname = ArrayList<String>()
                        bankid = ArrayList<String>()
                        bankname.add("Select Bank Name")
                        bankid.add("0")
                        for (item in bankList!!){
                            item.Bankname?.let { bankname.add(it) }
                            item.BankId?.let { bankid.add(it) }
                        }
                        var bankPosition=0
                        bankname.forEachIndexed { index, s ->
                            if(s==strBankName)bankPosition=index
                            return@forEachIndexed
                        }
                        val adapter12 = ArrayAdapter(this@CAFActivity, android.R.layout.simple_spinner_item, bankname)
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        layout_payment.sp_bnknam.adapter = adapter12
                        layout_payment.sp_bnknam.setSelection(bankPosition)
                        adapter12.notifyDataSetChanged()
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<BankListResponse?>, t: Throwable) {
                Log.e("RetroError", t.toString())
            }
        })
    }

    fun listener(){
        tv_create.visibility = View.VISIBLE
        tv_submit.visibility = View.VISIBLE
        tv_update.visibility = View.VISIBLE
        linearcontactinfo.visibility = View.VISIBLE

        lineartwo.setOnClickListener {
            linearcontactinfo.visibility = View.VISIBLE
            linear_otherinfo.visibility = View.GONE
            linear_companydetails.visibility = View.GONE
            linadd.visibility = View.GONE
            linear_contact_person_address.visibility= View.GONE
            linearother_details.visibility = View.GONE
            linearcafpymnt_details.visibility = View.GONE
            lineardoc_details.visibility = View.GONE
            linear_wcrdetails.visibility = View.GONE
            linear_irdetails.visibility = View.GONE
            linear_npdetails.visibility = View.GONE
            linear_rfsdetails.visibility = View.GONE
        }

        linearthree.setOnClickListener {
            linearcontactinfo.visibility = View.GONE
            linear_otherinfo.visibility = View.VISIBLE
            linear_companydetails.visibility = View.GONE
            linadd.visibility = View.GONE
            linear_contact_person_address.visibility= View.GONE
            linearother_details.visibility = View.GONE
            linearcafpymnt_details.visibility = View.GONE
            lineardoc_details.visibility = View.GONE
            linear_wcrdetails.visibility = View.GONE
            linear_irdetails.visibility = View.GONE
            linear_npdetails.visibility = View.GONE
            linear_rfsdetails.visibility = View.GONE
        }
        linearsix.setOnClickListener {
            linearcontactinfo.visibility = View.GONE
            linear_otherinfo.visibility = View.GONE
            linear_companydetails.visibility = View.VISIBLE
            linadd.visibility = View.GONE
            linear_contact_person_address.visibility= View.GONE
            linearother_details.visibility = View.GONE
            linearcafpymnt_details.visibility = View.GONE
            lineardoc_details.visibility = View.GONE
            linear_wcrdetails.visibility = View.GONE
            linear_irdetails.visibility = View.GONE
            linear_npdetails.visibility = View.GONE
            linear_rfsdetails.visibility = View.GONE
        }
        linearfouraddres.setOnClickListener {
            linearcontactinfo.visibility = View.GONE
            linear_otherinfo.visibility = View.GONE
            linear_companydetails.visibility = View.GONE
            linadd.visibility = View.VISIBLE
            linear_contact_person_address.visibility= View.GONE
            linearother_details.visibility = View.GONE
            linearcafpymnt_details.visibility = View.GONE
            lineardoc_details.visibility = View.GONE
            linear_wcrdetails.visibility = View.GONE
            linear_irdetails.visibility = View.GONE
            linear_npdetails.visibility = View.GONE
            linear_rfsdetails.visibility = View.GONE
        }
        linearfive.setOnClickListener {
            linearcontactinfo.visibility = View.GONE
            linear_otherinfo.visibility = View.GONE
            linear_companydetails.visibility = View.GONE
            linadd.visibility = View.GONE
            linear_contact_person_address.visibility= View.VISIBLE
            linearother_details.visibility = View.GONE
            linearcafpymnt_details.visibility = View.GONE
            lineardoc_details.visibility = View.GONE
            linear_wcrdetails.visibility = View.GONE
            linear_irdetails.visibility = View.GONE
            linear_npdetails.visibility = View.GONE
            linear_rfsdetails.visibility = View.GONE
        }
        lineareight.setOnClickListener {
            linearcontactinfo.visibility = View.GONE
            linear_otherinfo.visibility = View.GONE
            linear_companydetails.visibility = View.GONE
            linadd.visibility = View.GONE
            linear_contact_person_address.visibility= View.GONE
            linearother_details.visibility = View.VISIBLE
            linearcafpymnt_details.visibility = View.GONE
            lineardoc_details.visibility = View.GONE
            linear_wcrdetails.visibility = View.GONE
            linear_irdetails.visibility = View.GONE
            linear_npdetails.visibility = View.GONE
            linear_rfsdetails.visibility = View.GONE
        }
        linearnine.setOnClickListener {
            linearcontactinfo.visibility = View.GONE
            linear_otherinfo.visibility = View.GONE
            linear_companydetails.visibility = View.GONE
            linadd.visibility = View.GONE
            linear_contact_person_address.visibility= View.GONE
            linearother_details.visibility = View.GONE
            linearcafpymnt_details.visibility = View.VISIBLE
            lineardoc_details.visibility = View.GONE
            linear_wcrdetails.visibility = View.GONE
            linear_irdetails.visibility = View.GONE
            linear_npdetails.visibility = View.GONE
            linear_rfsdetails.visibility = View.GONE
        }

        linearten.setOnClickListener {
            linearcontactinfo.visibility = View.GONE
            linear_otherinfo.visibility = View.GONE
            linear_companydetails.visibility = View.GONE
            linadd.visibility = View.GONE
            linear_contact_person_address.visibility= View.GONE
            linearother_details.visibility = View.GONE
            linearcafpymnt_details.visibility = View.GONE
            lineardoc_details.visibility = View.VISIBLE
            linear_wcrdetails.visibility = View.GONE
            linear_irdetails.visibility = View.GONE
            linear_npdetails.visibility = View.GONE
            linear_rfsdetails.visibility = View.GONE
        }
        linearwcr.setOnClickListener {
            linearcontactinfo.visibility = View.GONE
            linear_otherinfo.visibility = View.GONE
            linear_companydetails.visibility = View.GONE
            linadd.visibility = View.GONE
            linear_contact_person_address.visibility= View.GONE
            linearother_details.visibility = View.GONE
            linearcafpymnt_details.visibility = View.GONE
            lineardoc_details.visibility = View.GONE
            linear_wcrdetails.visibility = View.VISIBLE
            linear_irdetails.visibility = View.GONE
            linear_npdetails.visibility = View.GONE
            linear_rfsdetails.visibility = View.GONE
        }
        linearnp.setOnClickListener {
            linearcontactinfo.visibility = View.GONE
            linear_otherinfo.visibility = View.GONE
            linear_companydetails.visibility = View.GONE
            linadd.visibility = View.GONE
            linear_contact_person_address.visibility= View.GONE
            linearother_details.visibility = View.GONE
            linearcafpymnt_details.visibility = View.GONE
            lineardoc_details.visibility = View.GONE
            linear_wcrdetails.visibility = View.GONE
            linear_irdetails.visibility = View.GONE
            linear_npdetails.visibility = View.VISIBLE
            linear_rfsdetails.visibility = View.GONE

        }
        lineareir.setOnClickListener {
            linearcontactinfo.visibility = View.GONE
            linear_otherinfo.visibility = View.GONE
            linear_companydetails.visibility = View.GONE
            linadd.visibility = View.GONE
            linear_contact_person_address.visibility= View.GONE
            linearother_details.visibility = View.GONE
            linearcafpymnt_details.visibility = View.GONE
            linear_wcrdetails.visibility = View.GONE
            linear_irdetails.visibility = View.VISIBLE
            linear_npdetails.visibility = View.GONE
            linear_rfsdetails.visibility = View.GONE
        }
        linearerfs.setOnClickListener {
            linearcontactinfo.visibility = View.GONE
            linear_otherinfo.visibility = View.GONE
            linear_companydetails.visibility = View.GONE
            linadd.visibility = View.GONE
            linear_contact_person_address.visibility= View.GONE
            linearother_details.visibility = View.GONE
            linearcafpymnt_details.visibility = View.GONE
            linear_wcrdetails.visibility = View.GONE
            linear_irdetails.visibility = View.GONE
            linear_npdetails.visibility = View.GONE
            linear_rfsdetails.visibility = View.VISIBLE
        }

    }


    fun getInstallCity(stateCode: String) {
        val getCityRequest = GetCityRequest(Constants.GET_CITY,Constants.AUTH_KEY,password,stateCode,userName)

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getBillingCityList(getCityRequest)
        call.enqueue(object : Callback<CafBillingCityResponse?> {
            override fun onResponse(call: Call<CafBillingCityResponse?>, response: Response<CafBillingCityResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        val img = response.body()?.Response?.Message
                        img?.let { Log.e("image", it) }
                        cityList = response.body()?.Response?.Data
                        city = ArrayList<String>()
                        cityCode = ArrayList<String>()
                        city?.add("Select City")
                        cityCode?.add("")
                        for (item in cityList!!) {
                            city?.add(item.CityName)
                            cityCode?.add(item.CityCode)
                        }
                        var cityPosition=0
                        cityCode?.forEachIndexed { index, s ->
                            if(s==str_city_code)cityPosition=index
                            return@forEachIndexed
                        }
                        val adapter12 = ArrayAdapter(this@CAFActivity, android.R.layout.simple_spinner_item, city!!)
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        layout_cafinstal_address.sp_cafcity.adapter = adapter12
                        layout_cafinstal_address.sp_cafcity.setSelection(cityPosition)
                        adapter12.notifyDataSetChanged()
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<CafBillingCityResponse?>, t: Throwable) {
                Log.e("RetroError", t.toString())
            }
        })
    }

    fun getBillingCity(stateCode: String?) {
        val getCityRequest = GetCityRequest(Constants.GET_CITY,Constants.AUTH_KEY,password,stateCode,userName)

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getBillingCityList(getCityRequest)
        call.enqueue(object : Callback<CafBillingCityResponse?> {
            override fun onResponse(call: Call<CafBillingCityResponse?>, response: Response<CafBillingCityResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        val img = response.body()?.Response?.Message
                        img?.let { Log.e("image", it) }
                        cityList = response.body()?.Response?.Data
                        billingcity = ArrayList<String>()
                        billingcityCode = ArrayList<String>()
                        billingcity?.add("Select City")
                        billingcityCode?.add("")
                        for (item in cityList!!) {
                            billingcity?.add(item.CityName)
                            billingcityCode?.add(item.CityCode)
                        }
                        var cityPosition=0
                        billingcityCode?.forEachIndexed { index, s ->
                            if(s==str_blcity_code)
                                cityPosition=index
                            return@forEachIndexed
                        }
                        val adapter12 = ArrayAdapter(this@CAFActivity, android.R.layout.simple_spinner_item, billingcity!!)
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        caf_contact_person_row.sp_cfblcity.adapter = adapter12
                        caf_contact_person_row.sp_cfblcity.setSelection(cityPosition)
                        adapter12.notifyDataSetChanged()
                    } catch (e:java.lang.Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<CafBillingCityResponse?>, t: Throwable) {
                Log.e("RetroError", t.toString())
            }
        })
    }

    fun getAuthorizedCity(stateCode: String) {
        val getCityRequest = GetCityRequest(Constants.GET_CITY,Constants.AUTH_KEY,password,stateCode,userName)

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getBillingCityList(getCityRequest)
        call.enqueue(object : Callback<CafBillingCityResponse?> {
            override fun onResponse(call: Call<CafBillingCityResponse?>, response: Response<CafBillingCityResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        val img = response.body()?.Response?.Message
                        img?.let { Log.e("image", it) }
                        cityList = response.body()?.Response?.Data
                        authocity = ArrayList<String>()
                        authocityCode = ArrayList<String>()
                        authocity?.add("Select City")
                        authocityCode?.add("")
                        for (item in cityList!!) {
                            authocity?.add(item.CityName)
                            authocityCode?.add(item.CityCode)
                        }
                        var cityPosition=0
                        authocityCode?.forEachIndexed { index, s ->
                            if(s==str_atcity_code)cityPosition=index
                            return@forEachIndexed
                        }
                        val adapter12 = ArrayAdapter(this@CAFActivity, android.R.layout.simple_spinner_item, authocity!!)
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        layout_cafothr_details.sp_cafauthcity.adapter = adapter12
                        layout_cafothr_details.sp_cafauthcity.setSelection(cityPosition)
                        adapter12.notifyDataSetChanged()
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<CafBillingCityResponse?>, t: Throwable) {
                Log.e("RetroError", t.toString())
            }
        })
    }

    fun getInstallArea(str_city: String?, str_city_code: String?) {
        val getLeadAreaRequest =   GetLeadAreaRequest(Constants.Get_AREA,Constants.AUTH_KEY,
            str_city_code.toString(), str_city.toString() ,"",userName,password,false)

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getLeadArea(getLeadAreaRequest)
        call.enqueue(object : Callback<GetLeadAreaRes?> {
            override fun onResponse(call: Call<GetLeadAreaRes?>, response: Response<GetLeadAreaRes?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        val img = response.body()?.Response?.Message
                        img?.let { Log.e("image", it) }
                        areaList= response.body()?.Response?.Data
                        area = ArrayList<String>()
                        areaCode = ArrayList<String>()
                        area?.add("Select Area")
                        areaCode?.add("")
                        for (item in areaList!!){
                            item.AreaName?.let { area?.add(it) }
                            item.AreaCode?.let { areaCode?.add(it) }
                        }
                        var areaPosition=0
                        areaCode?.forEachIndexed { index, s ->
                            if(s==str_add_area)areaPosition=index
                            return@forEachIndexed
                        }

                        val adapter12 = ArrayAdapter(this@CAFActivity, android.R.layout.simple_spinner_item, area!!)
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        layout_cafinstal_address.sp_cafcnarea.adapter = adapter12
                        layout_cafinstal_address.sp_cafcnarea.setSelection(areaPosition)
                        adapter12.notifyDataSetChanged()
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<GetLeadAreaRes?>, t: Throwable) {
                Log.e("RetroError", t.toString())
            }
        })
    }
    fun getBillingArea(str_city: String?, str_city_code: String?) {
        val getLeadAreaRequest =   GetLeadAreaRequest(Constants.Get_AREA,Constants.AUTH_KEY,
            str_city_code.toString(), str_city.toString() ,"",userName,password,false)
        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getLeadArea(getLeadAreaRequest)
        call.enqueue(object : Callback<GetLeadAreaRes?> {
            override fun onResponse(call: Call<GetLeadAreaRes?>, response: Response<GetLeadAreaRes?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        val img = response.body()?.Response?.Message
                        img?.let { Log.e("image", it) }
                        areaList= response.body()?.Response?.Data
                        billingarea = ArrayList<String>()
                        billingareaCode = ArrayList<String>()
                        billingarea?.add("Select Area")
                        billingareaCode?.add("")
                        for (item in areaList!!){
                            item.AreaName?.let { billingarea?.add(it) }
                            item.AreaCode?.let { billingareaCode?.add(it) }
                        }
                        var areaPosition=0
                        billingareaCode!!.forEachIndexed { index, s ->
                            if(s==str_bladd_area)areaPosition=index
                            return@forEachIndexed
                        }

                        val adapter12 = ArrayAdapter(this@CAFActivity, android.R.layout.simple_spinner_item, billingarea!!)
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        caf_contact_person_row.sp_cfblcnarea.adapter = adapter12
                        caf_contact_person_row.sp_cfblcnarea.setSelection(areaPosition)
                        adapter12.notifyDataSetChanged()
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<GetLeadAreaRes?>, t: Throwable) {
                Log.e("RetroError", t.toString())
            }
        })
    }

    fun getInstallBuilding(areaname: String, areaCode: String?) {
        val getLeadBuildingRequest = GetLeadBuildingRequest(Constants.GET_BUILDING,Constants.AUTH_KEY,areaCode.toString(),areaname,password,userName)

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getleadBuilding(getLeadBuildingRequest)
        call.enqueue(object : Callback<GetLeadBuildingResponse?> {
            override fun onResponse(call: Call<GetLeadBuildingResponse?>, response: Response<GetLeadBuildingResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        //  val msg = response.body()?.Response?.Message
                        buildingList= response.body()?.Response?.Data
                        building = ArrayList<String>()
                        buildingCode = ArrayList<String>()
                        building?.add("Select Building")
                        buildingCode?.add("")
                        if (buildingList != null) {
                            for (item in buildingList!!) {
                                item.BuildingName?.let { building?.add(it) }
                                item.BuildingCode?.let { buildingCode?.add(it) }
                            }
                        }

                        var buildPosition=0
                        buildingCode?.forEachIndexed { index, s ->
                            if(s==str_inst_building_nm)buildPosition=index
                        }

                        val adapter12 = ArrayAdapter(this@CAFActivity, android.R.layout.simple_spinner_item, building!!)
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        layout_cafinstal_address.sp_cafbuilding_nm.adapter = adapter12
                        layout_cafinstal_address.sp_cafbuilding_nm.setSelection(buildPosition)
                        adapter12.notifyDataSetChanged()

                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<GetLeadBuildingResponse?>, t: Throwable) {
                Log.e("RetroError", t.toString())
            }
        })
    }

    fun getBillingBuilding(areaname: String, areaCode: String?) {
        val getLeadBuildingRequest = GetLeadBuildingRequest(Constants.GET_BUILDING,Constants.AUTH_KEY,areaCode.toString(),areaname,password,userName)

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getleadBuilding(getLeadBuildingRequest)
        call.enqueue(object : Callback<GetLeadBuildingResponse?> {
            override fun onResponse(call: Call<GetLeadBuildingResponse?>, response: Response<GetLeadBuildingResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        //  val msg = response.body()?.Response?.Message
                        buildingList= response.body()?.Response?.Data
                        billingbuilding = ArrayList<String>()
                        billingbuildingCode = ArrayList<String>()
                        billingbuilding?.add("Select Building")
                        billingbuildingCode?.add("")
                        if (buildingList != null) {
                            for (item in buildingList!!) {
                                item.BuildingName?.let { billingbuilding?.add(it) }
                                item.BuildingCode?.let { billingbuildingCode?.add(it) }
                            }
                        }
                        var buildPosition=0
                        billingbuildingCode?.forEachIndexed { index, s ->
                            if(s==str_blinst_building_nm)buildPosition=index
                            return@forEachIndexed
                        }

                        val adapter12 = ArrayAdapter(this@CAFActivity, android.R.layout.simple_spinner_item, billingbuilding!!)
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        caf_contact_person_row.sp_cfblbuilding_nm.adapter = adapter12
                        caf_contact_person_row.sp_cfblbuilding_nm.setSelection(buildPosition)
                        adapter12.notifyDataSetChanged()

                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<GetLeadBuildingResponse?>, t: Throwable) {
                Log.e("RetroError", t.toString())
            }
        })
    }



    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (parent?.id == R.id.sp_preffered_cmmnctn) {
            caf_contactinfo_layout.et_prfcom.setText(resources.getStringArray(R.array.list_of_preffered).get(position))
            str_PrfCom = resources.getStringArray(R.array.list_of_prefferedvalue)[position]
        }else if(parent?.id == R.id.sp_cafcompany){
            caf_contactinfo_layout.et_cafcmpny.setText(company?.get(position))
            str_cmp =  companyId?.get(position )
            caf_contactinfo_layout.et_cafgrp.setText(group?.get(position))
            str_grp = groupId?.get(position )
            getRelation(str_cmp)
        }else if(parent?.id == R.id.sp_cafgroup){
            caf_contactinfo_layout.et_cafgrp.setText(group?.get(position))
            str_grp = groupId?.get(position )
        }else if(parent?.id == R.id.sp_cafrelation){
            caf_contactinfo_layout.et_cafrelation.setText(relation?.get(position))
            str_rltn =  relationId?.get(position )
        }else if(parent?.id == R.id.sp_cafcmpny_self){
            layout_otherinfo.et_cafcmpny_self.setText(resources.getStringArray(R.array.list_of_boolean)[position])
            str_cmpnyself =  resources.getStringArray(R.array.list_of_boolean_values).get(position )
        } else if(parent?.id == R.id.sp_caffirm_type){
            layout_cafcompany_details.et_caffirm_type.setText(resources.getStringArray(R.array.list_firm_type)[position])
            str_firmtype = resources.getStringArray(R.array.list_firm_type_value)[position]
        }else if(parent?.id == R.id.sp_cafindustype){
            layout_cafcompany_details.et_cafindus_type.setText(instryname[position])
            str_indusid = industryid[position]
        }else if(parent?.id == R.id.sp_cafstate){
            layout_cafinstal_address.et_cafstate.setText(resources.getStringArray(R.array.list_of_state)[position])
            str_statename = resources.getStringArray(R.array.list_of_state)[position]
            str_inststateId= resources.getStringArray(R.array.list_state_code)[position]
            getInstallCity(str_inststateId.toString())
        }else if(parent?.id == R.id.sp_cafcity){
            layout_cafinstal_address.et_add_cafcity.setText(city?.get(position))
            str_city = city?.get(position).toString()
            str_city_code =  cityCode?.get(position )
            getInstallArea(str_city, str_city_code)
        } else if(parent?.id == R.id.sp_cafcnarea){
            layout_cafinstal_address.et_cafinstallarea.setText(area?.get(position))
            str_add_area = areaCode?.get(position )
            val cntareaname = area?.get(position).toString()
            getInstallBuilding(cntareaname,str_add_area)
        } else if(parent?.id == R.id. sp_cafbuilding_nm){
            layout_cafinstal_address.et_cafbuildingname.setText(building?.get(position))
            str_inst_building_nm =  buildingCode?.get(position )
            //  val buildingname = building?.get(position)
        }else if(parent?.id == R.id. sp_cafstatus){
            layout_cafinstal_address.et_cafbuilding_status.setText(resources.getStringArray(R.array.list_Of_Status)[position])
            str_inst_statusid = resources.getStringArray(R.array.list_Of_StatusId)[position]
            //   val installstatus = resources.getStringArray(R.array.list_Of_Status).get(position)
        }else if (parent?.id == R.id.sp_cafsbbus) {
            caf_contactinfo_layout.et_sb_bs_sgmnt.setText(resources.getStringArray(R.array.list_of_subBusSegment)[position])
            str_sub_bus = resources.getStringArray(R.array.list_of_subBusSegment)[position]
        }else if (parent?.id == R.id.sp_bnknam) {
            layout_payment.et_bnknm.setText(bankname[position])
            str_bankid = bankid[position]
        }
        else if(parent?.id == R.id.sp_cfblstate){
            caf_contact_person_row.et_cfblstate.setText(resources.getStringArray(R.array.list_of_state)[position])
            str_blstatename = resources.getStringArray(R.array.list_of_state)[position]
            str_blinststateId= resources.getStringArray(R.array.list_state_code)[position]
            getBillingCity(str_blinststateId)
            /* if(str_blstatename!="Select State"|| caf_contact_person_row.et_cfblstate.text?.isNotEmpty() == true) {
                 getBillingCity(str_blinststateId)
             }*/
        }else if(parent?.id == R.id.sp_cfblcity){
            caf_contact_person_row.et_cfblcity.setText(billingcity?.get(position))
            str_blcity = billingcity?.get(position).toString()
            str_blcity_code =  billingcityCode?.get(position)
            getBillingArea(str_blcity, str_blcity_code)
        } else if(parent?.id == R.id.sp_cfblcnarea){
            caf_contact_person_row.et_cfblarea.setText(billingarea?.get(position))
            str_bladd_area = billingareaCode?.get(position)
            val cntareaname = billingarea?.get(position).toString()
            getBillingBuilding(cntareaname,str_bladd_area)
        } else if(parent?.id == R.id. sp_cfblbuilding_nm){
            caf_contact_person_row.et_cfblbuilding.setText(billingbuilding?.get(position))
            str_blinst_building_nm =  billingbuildingCode?.get(position)
            // val buildingname = billingbuilding?.get(position)
        }else if(parent?.id == R.id.sp_caf_pro){
            layout_otherinfo.et_provider.setText(resources.getStringArray(R.array.ext_serv_one)[position])
            str_provider = resources.getStringArray(R.array.ext_serv_one_values)[position]
        }else if(parent?.id == R.id.sp_caffrwal){
            layout_otherinfo.et_caffrwl.setText(resources.getStringArray(R.array.list_of_boolean)[position])
            str_frwall = resources.getStringArray(R.array.list_of_boolean_values)[position]
            if(resources.getStringArray(R.array.list_of_boolean)[position] =="Yes"){
                layout_otherinfo.tv_frws.visibility= View.VISIBLE
            }else{
                layout_otherinfo.tv_frws.visibility= View.GONE
            }
        }else if(parent?.id == R.id.sp_cafauthostate){
            layout_cafothr_details.et_cafauthstate.setText(resources.getStringArray(R.array.list_of_state)[position])
            str_atstatename = resources.getStringArray(R.array.list_of_state)[position]
            str_atinststateId= resources.getStringArray(R.array.list_state_code)[position]
            if(str_blstatename!="Select State"|| layout_cafothr_details.et_cafauthstate.text?.isNotEmpty() == true) {
                getAuthorizedCity(str_atinststateId.toString())
            }
        }else if(parent?.id == R.id.sp_cafauthcity){
            layout_cafothr_details.et_add_cafauthcity.setText(authocity?.get(position))
            str_atcity = authocity?.get(position).toString()
            str_atcity_code =  authocityCode?.get(position )
        }else if(parent?.id == R.id.sp_wrkng_days){
            caf_contactinfo_layout.et_wrkngdys.setText(resources.getStringArray(R.array.list_of_wrkngdays)[position])
            str_wrkngdays = resources.getStringArray(R.array.list_of_wrkngdaysvalues)[position].toString()
        }else if(parent?.id == R.id.sp_ntwrkmtr){
            caf_contactinfo_layout.et_ntwrkmtr.setText(resources.getStringArray(R.array.list_of_monitoring)[position])
            str_ntwrk = resources.getStringArray(R.array.list_of_monitoringvalues)[position].toString()
        }else if(parent?.id == R.id.sp_custctgry){
            layout_cafinstal_address.et_custctgry.setText(resources.getStringArray(R.array.list_of_cstmrcategory)[position])
            str_customercategory = resources.getStringArray(R.array.list_of_cstctgryvalues)[position].toString()
        }else if (parent?.id == R.id.sp_cstmrwrknghrs) {
            caf_contactinfo_layout.et_cstmrwrknghrs.setText(resources.getStringArray(R.array.list_of_wrknghours)[position])
            str_wrknghrs = resources.getStringArray(R.array.list_of_wrknghoursval)[position]
        }else if (parent?.id == R.id.sp_voip) {
            layout_cafinstal_address.et_cafvoip.setText(resources.getStringArray(R.array.list_of_monitoring)[position])
            str_voip = resources.getStringArray(R.array.list_of_cstctgryvalues)[position]
        }else if (parent?.id == R.id.sp_cafbilltype) {
            layout_cafinstal_address.et_cafbiltype.setText(resources.getStringArray(R.array.list_of_billtype)[position])
            str_billtype = resources.getStringArray(R.array.list_of_boolean_values)[position]
        }else if (parent?.id == R.id.sp_securitytype) {
            layout_payment.et_sctype.setText(resources.getStringArray(R.array.list_of_Deposit)[position])
            str_sctype = resources.getStringArray(R.array.list_of_boolean_values)[position]
        }else if (parent?.id == R.id.sp_gst) {
            binding.layoutPayment.etGst.setText(resources.getStringArray(R.array.list_of_gst)[position])
            str_gstval = resources.getStringArray(R.array.listDNCVal)[position]
            if(str_gstval=="569480000"){
                layout_payment.et_gsttnum.visibility=View.VISIBLE
            }else{
                layout_payment.et_gsttnum.visibility=View.GONE
            }
        }
        else if (parent?.id == R.id.sp_payslip) {
            layout_payment.et_payslip.setText(resources.getStringArray(R.array.list_of_payslip)[position])
            str_payslip = resources.getStringArray(R.array.list_of_payslipval)[position]
            val pay = resources.getStringArray(R.array.list_of_payslip)[position]
            if(pay=="Select Option"){
                layout_payment.frbnk.visibility=View.GONE
                layout_payment.et_brnchname.visibility=View.GONE
                layout_payment.et_checkkdate.visibility=View.GONE
                layout_payment.et_chknumber.visibility=View.GONE
                layout_payment.et_card4dgt.visibility=View.GONE
                layout_payment.et_transactionid.visibility=View.GONE
                layout_payment.et_approvalcode.visibility=View.GONE
                layout_payment.et_paymentdate.visibility=View.GONE
                layout_payment.et_debit4dgt.visibility=View.GONE
            }else if(pay=="RTGS"){
                layout_payment.et_transactionid.visibility=View.VISIBLE
                layout_payment.et_approvalcode.visibility=View.GONE
                layout_payment.et_paymentdate.visibility=View.VISIBLE
                layout_payment.frbnk.visibility=View.GONE
                layout_payment.et_brnchname.visibility=View.GONE
                layout_payment.et_checkkdate.visibility=View.GONE
                layout_payment.et_chknumber.visibility=View.GONE
                layout_payment.et_card4dgt.visibility=View.GONE
                layout_payment.et_debit4dgt.visibility=View.GONE
            }else if(pay=="Cheque"){

                layout_payment.et_transactionid.visibility=View.GONE

                //////////////////////////////////////////
                layout_payment.et_card4dgt.visibility=View.GONE
                layout_payment.frbnk.visibility=View.VISIBLE
                layout_payment.et_brnchname.visibility=View.VISIBLE
                layout_payment.et_checkkdate.visibility=View.VISIBLE
                layout_payment.et_chknumber.visibility=View.VISIBLE
                layout_payment.et_approvalcode.visibility=View.GONE
                layout_payment.et_paymentdate.visibility=View.GONE
                layout_payment.et_debit4dgt.visibility=View.GONE
            }else if(pay=="Demand draft"){
                layout_payment.et_transactionid.visibility=View.GONE

                //////////////////////////////////
                layout_payment.frbnk.visibility=View.VISIBLE
                layout_payment.et_brnchname.visibility=View.VISIBLE
                layout_payment.et_checkkdate.visibility=View.VISIBLE
                layout_payment.et_chknumber.visibility=View.VISIBLE
                layout_payment.et_card4dgt.visibility=View.GONE
                layout_payment.et_approvalcode.visibility=View.GONE
                layout_payment.et_paymentdate.visibility=View.GONE
                layout_payment.et_debit4dgt.visibility=View.GONE
            }else if(pay=="Credit Card"){
                layout_payment.et_approvalcode.visibility=View.VISIBLE
                layout_payment.et_debit4dgt.visibility=View.VISIBLE
                layout_payment.et_paymentdate.visibility=View.VISIBLE
                layout_payment.frbnk.visibility=View.GONE
                layout_payment.et_brnchname.visibility=View.GONE
                layout_payment.et_checkkdate.visibility=View.GONE
                layout_payment.et_card4dgt.visibility=View.GONE
                layout_payment.et_chknumber.visibility=View.GONE
                layout_payment.et_transactionid.visibility=View.GONE
            }else if(pay=="NEFT"){
                layout_payment.et_transactionid.visibility=View.VISIBLE
                layout_payment.et_approvalcode.visibility=View.GONE
                layout_payment.et_paymentdate.visibility=View.VISIBLE
                layout_payment.et_brnchname.visibility=View.GONE
                layout_payment.et_checkkdate.visibility=View.GONE
                layout_payment.et_chknumber.visibility=View.GONE
                layout_payment.et_card4dgt.visibility=View.GONE
                layout_payment.et_debit4dgt.visibility=View.GONE
                layout_payment.frbnk.visibility=View.GONE
            }else if(pay=="Debit Card"){
                layout_payment.frbnk.visibility=View.GONE
                layout_payment.et_brnchname.visibility=View.GONE
                layout_payment.et_checkkdate.visibility=View.GONE
                layout_payment.et_chknumber.visibility=View.GONE
                layout_payment.et_approvalcode.visibility=View.VISIBLE
                layout_payment.et_card4dgt.visibility=View.VISIBLE
                layout_payment.et_paymentdate.visibility=View.VISIBLE
                layout_payment.et_transactionid.visibility=View.GONE
                layout_payment.et_debit4dgt.visibility=View.GONE
            }else if(pay=="Ezetap"){
                layout_payment.frbnk.visibility=View.GONE
                layout_payment.et_brnchname.visibility=View.GONE
                layout_payment.et_checkkdate.visibility=View.GONE
                layout_payment.et_chknumber.visibility=View.GONE
                layout_payment.et_card4dgt.visibility=View.GONE
                layout_payment.et_transactionid.visibility=View.VISIBLE
                layout_payment.et_approvalcode.visibility=View.GONE
                layout_payment.et_debit4dgt.visibility=View.GONE
                layout_payment.et_paymentdate.visibility=View.VISIBLE
            }else if(pay=="Ezetap-Cheque"){
                layout_payment.frbnk.visibility=View.VISIBLE
                layout_payment.et_brnchname.visibility=View.VISIBLE
                layout_payment.et_checkkdate.visibility=View.VISIBLE
                layout_payment.et_chknumber.visibility=View.VISIBLE
                layout_payment.et_card4dgt.visibility=View.GONE
                layout_payment.et_transactionid.visibility=View.GONE
                layout_payment.et_approvalcode.visibility=View.GONE
                layout_payment.et_debit4dgt.visibility=View.GONE
                layout_payment.et_paymentdate.visibility=View.GONE
            }
        }

    }
    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    override fun onBackPressed() {
        next()
    }

    private fun next(){
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        builder.setMessage("Do you want to go back to the previous screen?")
        builder.setPositiveButton("Yes") { _, _ ->
            Intent(this, CafTabActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.cancel()
        }
        val alert: AlertDialog = builder.create()
        alert.show()
    }

}