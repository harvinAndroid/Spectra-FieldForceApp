
package com.spectra.fieldforce.salesapp.activity


import GetAllWorkOrderAdapter
import GetPreSaleTaskAdapter
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
import com.spectra.fieldforce.BuildConfig
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.spectra.fieldforce.R
import com.spectra.fieldforce.api.ApiClient
import com.spectra.fieldforce.api.ApiInterface
import com.spectra.fieldforce.databinding.SafActivityBinding
import com.spectra.fieldforce.salesapp.model.*
import com.spectra.fieldforce.utils.AppConstants
import com.spectra.fieldforce.utils.Constants
import kotlinx.android.synthetic.main.lead_demo_fragment.*
import kotlinx.android.synthetic.main.opp_add_doa.*
import kotlinx.android.synthetic.main.opp_sales_task.view.*
import kotlinx.android.synthetic.main.saf_activity.*
import kotlinx.android.synthetic.main.saf_activity.checkbx
import kotlinx.android.synthetic.main.saf_authorised_details.view.*
import kotlinx.android.synthetic.main.saf_company_details_row.view.*
import kotlinx.android.synthetic.main.saf_contact_info.view.*
import kotlinx.android.synthetic.main.saf_contact_person_row.view.*
import kotlinx.android.synthetic.main.saf_installation_address_row.view.*
import kotlinx.android.synthetic.main.saf_payment_details_row.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*
import java.util.regex.Pattern


class SAFActivity:AppCompatActivity(),View.OnClickListener , AdapterView.OnItemSelectedListener {

    var strSafId : String? = null
    var strOppId :String?=null
    private var cityList : ArrayList<BillData>? = null
    private var city : ArrayList<String>? = null
    private var cityCode : ArrayList<String>? = null
    private var billingcity : ArrayList<String>? = null
    private var billingcityCode : ArrayList<String>? = null
    private var authocity : ArrayList<String>? = null
    private var authocityCode : ArrayList<String>? = null
    private val PERMISSION_REQUEST_CODE = 200
    private var workOrderList: ArrayList<WorkData>? = null
    private var verticalList : ArrayList<VerticalData>? = null
    private var vertical : ArrayList<String>? = null
    private var verticalId : ArrayList<String>? = null
    var strCity: String? = null
    var strServiceId: String? = null
    var strVertical:String ? = null
    var strProductId: String? = null
    var strPaymentStatus:String?=null
    var strBankName: String? = null
    var strCompanyName:String? = null
    var str_grp :String ? = null
    var strBillSameAdd:String ? = null
    var str_rltn:String ? = null
    var str_cmp :String? = null
    private var allPreSales: ArrayList<PreSaleData>? = null
    var str_frwall :String? = null
    var str_provider :String? = null
    var str_firmtype:String? = null
    var str_indusid:String? = null
    var str_statename:String? = null
    var str_inststateId:String? = null
    var str_city:String? = null
    var strPaymentId:String? = null
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
    var str_customercategory:String? = null
    var str_wrkngdays:String? = null
    var str_atcity_code:String? = null
    var str_bladd_area:String? = null
    var str_blinst_building_nm:String? = null
    var str_sub_bus:String? = null
    var str_bankid:String? = null
    var frwamc:String? = null
    var status:String? = null
    var cafpaydate:String? = null
    var checkdate:String? = null
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
    private var companyList: ArrayList<ComapnyData>? = null
    private var bankList : ArrayList<BankData>? = null
    private var bankname = arrayListOf<String>()
    private var bankid = arrayListOf<String>()
    var strIndustry:String? = null
    var strCompany=""
    var strGroup=""
    private var inAnimation: AlphaAnimation? = null
    private var outAnimation: AlphaAnimation? = null
    private lateinit var binding:SafActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.saf_activity)
        binding.searchToolbarSaf.rlBack.setOnClickListener(this)
        binding.searchToolbarSaf.tvLang.text = AppConstants.SAF
        val extras = intent.extras

        if (extras != null) {
            strSafId = extras.getString("SafId")
            strOppId = extras.getString("OppId")
        }

        val sp1: SharedPreferences = this.getSharedPreferences("Login", 0)
        userName = sp1.getString(AppConstants.USERNAME, null)
        password = sp1.getString(AppConstants.PASSWORD, null)
        listener()
        CoroutineScope(Dispatchers.IO).launch {
            getSaf()
            getWorkOrder()
        }
        itemListener()
        CoroutineScope(Dispatchers.IO).launch {
            getVertical()
            saveItem()
            getPreSales ()
        }

        tv_submit.setOnClickListener {
            submitCaf()
        }

        focus()
        if (checkPermission()) {

        } else {
            requestPermission()
        }
        binding.safSale.addPreTask.visibility=View.GONE
        init()

    }

    private fun getPreSales () {
        val CafPreSalesTaskReq =
            strSafId?.let { it -> CafPreSalesTaskReq(Constants.GET_SAFPRETASK,Constants.AUTH_KEY, password,it,userName)  }
        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getSafPreTaskList(CafPreSalesTaskReq)
        call.enqueue(object : Callback<GetPreSaleDetail?> {
            override fun onResponse(call: Call<GetPreSaleDetail?>, response: Response<GetPreSaleDetail?>) {
                // OutProgress()
                if (response.isSuccessful && response.body() != null) {
                    try {
                        if(response.body()?.Response?.StatusCode==200) {
                            allPreSales = response.body()?.Response?.Data
                            if(allPreSales?.size==1){
                                safSale.add_preTask.visibility=View.GONE
                            }
                            if(allPreSales?.size!=null) {
                                safSale.rv_addTask.visibility=View.VISIBLE
                                setPreSalesAdapter(allPreSales, this@SAFActivity)
                            }
                        }
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<GetPreSaleDetail?>, t: Throwable) {
                Log.e("RetroError", t.toString())
            }
        })
    }


    private fun setPreSalesAdapter(allSales: ArrayList<PreSaleData>?, context: Context?) {
        safSale.rv_addTask.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = allSales?.let { GetPreSaleTaskAdapter(it,context) }
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
            if (grantResults.isNotEmpty()) {
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

    private fun getVertical() {
        val getCityRequest = GetCityRequest(Constants.GETVERTICAL,Constants.AUTH_KEY,password,"",userName)
        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getVertical(getCityRequest)
        call.enqueue(object : Callback<GetVerticalData?> {
            override fun onResponse(call: Call<GetVerticalData?>, response: Response<GetVerticalData?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        val img = response.body()?.Response?.Message
                        img?.let { Log.e("image", it) }
                        verticalList = response.body()?.Response?.Data
                        vertical = ArrayList<String>()
                        verticalId = ArrayList<String>()
                        vertical?.add("Select Vertical")
                        verticalId?.add("")
                        for (item in verticalList!!) {
                            vertical?.add(item.VerticalName)
                            verticalId?.add(item.VerticalId)
                        }
                        var verticalPosition=0
                        verticalId!!.forEachIndexed { index, s ->
                            if(s==strVertical)verticalPosition=index
                        }

                        val adapter12 = ArrayAdapter(this@SAFActivity, android.R.layout.simple_spinner_item, vertical!!)
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        binding.safContactLayout.spVertical.adapter = adapter12
                        binding.safContactLayout.spVertical.setSelection(verticalPosition)
                        adapter12.notifyDataSetChanged()
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<GetVerticalData?>, t: Throwable) {
                Log.e("RetroError", t.toString())
            }
        })
    }

    private fun saveItem(){

        binding.tvCreateSaf.setOnClickListener {
         Create(Constants.CREATE_SAF)
        }
        tv_update.setOnClickListener {
            Create(Constants.UPDATE_SAF)
        }
        binding.searchToolbarSaf.downloadpdf.setOnClickListener { downloadPDF() }
        binding.searchToolbarSaf.share.setOnClickListener {
            share()
        }
    }

    private fun downloadPDF() {
        inProgress()
        val cafPdfRequest = CafPdfRequest(
            Constants.DOWNLOADREPORTS, Constants.AUTH_KEY,
            strSafId,
            password, "SAF", userName)
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
                        storeToPdfAndOpen(data)
                    }
                }
            }

            override fun onFailure(call: Call<GetPdfResponse?>, t: Throwable) {
                binding.safProgressLayout.progressOverlay.visibility = View.GONE
                Log.e("RetroError", t.toString())
            }
        })
    }

    private fun share() {
        inProgress()
        val cafPdfRequest = CafPdfRequest(
            Constants.REPORTSEMAIL_SEND, Constants.AUTH_KEY,
            strSafId,
            password, "SAF", userName
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
                        Toast.makeText(this@SAFActivity, msg, Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<ReportResponse?>, t: Throwable) {
                binding.safProgressLayout.progressOverlay.visibility = View.GONE
                Log.e("RetroError", t.toString())
            }
        })
    }

    fun storeToPdfAndOpen(base: String?) {
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
        layoutSafOtherDetails.etSafEmailId.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus) {
                val email =  layoutSafOtherDetails.etSafEmailId.text.toString()
                val isValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
                if (!isValid) {
                    Toast.makeText(this, "Invalid Email", Toast.LENGTH_LONG).show()
                }
            }
        }
        layoutSafOtherDetails.etSafMob.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus) {
                val mobile = layoutSafOtherDetails.etSafMob.text.toString()
                val isValid = Patterns.PHONE.matcher(mobile).matches()
                if (!isValid) {
                    Toast.makeText(this, "Invalid Mobile Number", Toast.LENGTH_LONG).show()
                }
            }
        }


        layoutSafInstallAddress.etSafEmail.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus)
            {
                val email =  layoutSafInstallAddress.etSafEmail.text.toString()
                val isValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
                if (!isValid) {
                    Toast.makeText(this, "Invalid Email", Toast.LENGTH_LONG).show()
                }
            }
        }

        saf_contact_person_row.et_safBillingEmailId.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus)
            {
                val email =  saf_contact_person_row.et_safBillingEmailId.text.toString()
                val isValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
                if (!isValid) {
                    Toast.makeText(this, "Invalid Email", Toast.LENGTH_LONG).show()
                }
            }
        }
        saf_contact_person_row.etSafPhoneNum.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus) {
                val mobile = saf_contact_person_row.etSafPhoneNum.text.toString()
                val isValid = Patterns.PHONE.matcher(mobile).matches()
                if (!isValid) {
                    Toast.makeText(this, "Invalid Mobile Number", Toast.LENGTH_LONG).show()
                }
            }
        }
        val regexGST = """/^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[0-9,A-Z]{3}${'$'}/""".toRegex()
        val regexPAN = """/^([A-Z]){5}([0-9]){4}([A-Z]){1}?${'$'}/""".toRegex()
        val regexTAN = """ /^([A-Z]){4}([0-9]){5}([A-Z]){1}?${'$'}/""".toRegex()
        layoutPayment.et_gstt.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus)
            {
                val gst =  layoutPayment.et_gstt.text.toString()
                val isValid= regexGST.matches(gst)
                if (!isValid) {
                    Toast.makeText(this, "Please Enter GST in CAPITAL Letters and Correct Format (12ABCDE1234A1A1 or 12ABCDE1234A1AA)", Toast.LENGTH_LONG).show()
                }
            }
        }
        layoutPayment.et_pannum.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus)
            {
                val gst =  layoutPayment.et_pannum.text.toString()
                 val isValid= regexPAN.matches(gst)
                if (!isValid) {
                    Toast.makeText(this, "Please Enter PAN in CAPITAL Letters and Correct Format (ABCDE1234A)", Toast.LENGTH_LONG).show()
                }
            }
        }
        layoutPayment.et_tannum.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus)
            {
                val gst =  layoutPayment.et_tannum.text.toString()
                val isValid= regexTAN.matches(gst)
                if (!isValid) {
                    Toast.makeText(this, "Please Enter TAN in CAPITAL Letters and correct format (ABCD12345A)", Toast.LENGTH_LONG).show()
                }
            }
        }
    }


    private fun Create(createcaf: String) {
        val strbusinessSegment =  safContactLayout.et_cafbs_sgmnt.text
        val subbssegment = str_sub_bus
        val authemail = layoutSafOtherDetails.etSafEmailId.text
        val authfather = layoutSafOtherDetails.etFatherHsb.text
        val authmobile = layoutSafOtherDetails.etSafMob.text
        val authname = layoutSafOtherDetails.etSafName.text
        val authpincode = layoutSafOtherDetails.etSafAuthPinCode.text
        val companyName =safContactLayout.etCompanyName.text

/*  val billingplot = saf_contact_person_row.et_cfblbuildng_num.text.toString()
          val billingname = saf_contact_person_row.et_caf_cntname.text.toString()
          val billingemail = saf_contact_person_row.et_caf_bilngemailid.text.toString()
          val billingfloor = saf_contact_person_row.et_cfblfloor.text.toString()
          val billingphn =   saf_contact_person_row.et_cafphn_num.text.toString()
          val billingpincode = saf_contact_person_row.et_cfblpin_code.text.toString()*/


        val instemail = layoutSafInstallAddress.etSafEmail.text
        val instmobile = layoutSafInstallAddress.et_cafmbnum.text
        val instpin = layoutSafInstallAddress.et_cafpin.text
         amount = layoutPayment.et_totalamt.text?.toString()
        val approvalcode = layoutPayment.et_appcode.text
        val brnch = layoutPayment.et_brnch.text?.toString()
        val checkdd = layoutPayment.et_chkdate.text?.toString()
        val checknum = layoutPayment.et_chknum.text?.toString()
        val debitCard4 = layoutPayment.et_cardfrdgt.text?.toString()
        val paymntdt = layoutPayment.et_paymntdt.text?.toString()
        val txtty = layoutPayment.et_txtid.text?.toString()
        val srcdepst = layoutPayment.et_scdeposit.text?.toString()
        val creditCard4 = layoutPayment.et_creditfrdgt.text?.toString()
        val pan = layoutPayment.et_pannum.text?.toString()
        val tan = layoutPayment.et_tannum.text?.toString()
        val gstnum = layoutPayment.et_gstt.text?.toString()
        val customername = safContactLayout.etCstName.text?.toString()
        val phonenumber = saf_contact_person_row.etSafPhoneNum.text?.toString()
        val email = saf_contact_person_row.et_safBillingEmailId.text?.toString()
        val buildingnumber = saf_contact_person_row.et_cfblbuildng_num.text?.toString()
        val floor = saf_contact_person_row.et_cfblfloor.text?.toString()
        val pincode = saf_contact_person_row.et_cfblpin_code.text?.toString()
        val name = saf_contact_person_row.et_caf_cntname.text.toString()

       /* if(str_wrknghrs?.isBlank()==true||str_wrknghrs=="0"||str_wrknghrs=="null"){
            Toast.makeText(this, "Please Select Working Hours", Toast.LENGTH_SHORT).show()
        } else*/ if(str_cmp?.isBlank()==true||str_cmp=="Select Company"||str_cmp=="null"){
            Toast.makeText(this, "Please Select Company", Toast.LENGTH_SHORT).show()
        }else if(str_grp?.isBlank()==true||str_grp=="Select Group"||str_grp=="null"){
            Toast.makeText(this, "Please Select Group", Toast.LENGTH_SHORT).show()
        }else if(str_rltn?.isBlank()==true||str_rltn=="Select Relation"||str_rltn=="null"){
            Toast.makeText(this, "Please Select Relation", Toast.LENGTH_SHORT).show()
        }else if(str_wrkngdays?.isBlank()==true||str_wrkngdays=="0"||str_wrkngdays=="null"){
            Toast.makeText(this, "Please Select Business Days Option", Toast.LENGTH_SHORT).show()
        } else if(subbssegment?.isBlank() == true){
            Toast.makeText(this, "Please enter SubBusiness Segment", Toast.LENGTH_SHORT).show()
        }else if(str_frwall?.isBlank()==true||str_frwall=="0"||(str_frwall=="null")){
            Toast.makeText(this, "Please enter FireWall", Toast.LENGTH_SHORT).show()
        }else if(frwamc?.isEmpty()==true&&str_frwall=="1"){
            Toast.makeText(this, "Please enter FireWall AMC Expiry date", Toast.LENGTH_SHORT).show()
        } else if(instemail?.isBlank() == true){
            Toast.makeText(this, "Please enter Installation Email", Toast.LENGTH_SHORT).show()
        }else if(instmobile?.isBlank() == true){
            Toast.makeText(this, "Please enter Installation Phone Number", Toast.LENGTH_SHORT).show()
        }else if(str_billtype?.isBlank()==true||str_billtype=="0"){
            Toast.makeText(this, "Please Enter Installation BillType", Toast.LENGTH_SHORT).show()
        } else if(instpin?.isBlank() == true || instpin?.length!=6){
            Toast.makeText(this, "Please enter Installation PinCode", Toast.LENGTH_SHORT).show()
        } else if(str_customercategory?.isBlank()==true||str_customercategory=="0"||(str_customercategory=="null")){
            Toast.makeText(this, "Please Select Category Of Customer", Toast.LENGTH_SHORT).show()
        }else if(customername?.isBlank() == true){
            Toast.makeText(this, "Please enter Customer Name", Toast.LENGTH_SHORT).show()
        }else if(phonenumber?.isBlank() == true || phonenumber?.length!=10){
            Toast.makeText(this, "Please enter Phone Number", Toast.LENGTH_SHORT).show()
        }else if(email?.isBlank() == true ||(!validEmail(email.toString()))){
            Toast.makeText(this, "Please enter Email", Toast.LENGTH_SHORT).show()
        } else if(str_blstatename?.isBlank()==true||str_blstatename=="Select State"||(str_blstatename=="null")){
            Toast.makeText(this, "Please enter State", Toast.LENGTH_SHORT).show()
        }else if(str_blcity?.isBlank()==true|| str_blcity=="Select City"||(str_blcity=="null")){
            Toast.makeText(this, "Please enter City", Toast.LENGTH_SHORT).show()
        }else if(str_bladd_area?.isBlank()==true||str_bladd_area==""||(str_bladd_area=="null")){
            Toast.makeText(this, "Please enter Area", Toast.LENGTH_SHORT).show()
        }/*else if(buildingnumber?.isBlank() == true ||(buildingnumber=="null")){
            Toast.makeText(this, "Please enter Building Number", Toast.LENGTH_SHORT).show()
        }*/
        else if(name.isBlank()){
            Toast.makeText(this, "Please enter Billing Address Contact Name", Toast.LENGTH_SHORT).show()
        }
        else if(floor?.isBlank()== true){
            Toast.makeText(this, "Please enter Floor", Toast.LENGTH_SHORT).show()
        }else if(pincode?.isBlank()== true|| pincode?.length!=6){
            Toast.makeText(this, "Please enter PinCode", Toast.LENGTH_SHORT).show()
        }else if(authname?.isBlank() == true){
            Toast.makeText(this, "Please enter Name", Toast.LENGTH_SHORT).show()
        }else if(authfather?.isBlank() == true){
            Toast.makeText(this, "Please enter Father/Husband Name", Toast.LENGTH_SHORT).show()
        }else if(authmobile?.isBlank() == true || authmobile?.length!=10){
            Toast.makeText(this, "Please enter Mobile Num", Toast.LENGTH_SHORT).show()
        } else if(authemail?.isBlank() == true ||(!validEmail(authemail.toString()))){
            Toast.makeText(this, "Please enter Email", Toast.LENGTH_SHORT).show()
        }else if(str_atstatename?.isBlank()==true||str_atstatename=="Select State"||(str_atstatename=="null")){
            Toast.makeText(this, "Please enter State", Toast.LENGTH_SHORT).show()
        }else if(str_atcity?.isBlank()==true|| str_atcity=="Select City"||(str_atcity=="null")){
            Toast.makeText(this, "Please enter City", Toast.LENGTH_SHORT).show()
        }else if(authpincode?.isBlank() == true || authpincode?.length!=6) {
            Toast.makeText(this, "Please enter PinCode", Toast.LENGTH_SHORT).show()
        }else if(str_payslip?.isBlank()==true|| str_payslip=="0"||(str_payslip=="null")){
            Toast.makeText(this, "Please PaySlip Type", Toast.LENGTH_SHORT).show()
        } else if(str_sctype?.isBlank()==true|| str_sctype=="0"||(str_sctype=="null")){
            Toast.makeText(this, "Please Enter Security Deposit Type", Toast.LENGTH_SHORT).show()
        } else if(srcdepst?.isBlank()== true){
            Toast.makeText(this, "Please enter Security Deposit", Toast.LENGTH_SHORT).show()
        } else if(amount?.isBlank()==true){
            Toast.makeText(this, "Please enter Amount", Toast.LENGTH_SHORT).show()
        }else if((str_payslip=="5"||str_payslip=="4"||str_payslip=="11")&&(txtty?.isBlank() == true)){
                Toast.makeText(this, "Please enter Transaction Refrence ID", Toast.LENGTH_SHORT).show()
        }else if((str_payslip=="5"||str_payslip=="4"||str_payslip=="11")&&paymntdt.toString().isBlank()){
                Toast.makeText(this, "Please Select Payment Date", Toast.LENGTH_SHORT).show()
        }else if((str_payslip=="1"||str_payslip=="2"||str_payslip=="12")&&(str_bankid?.isBlank()==true|| str_bankid=="0"||(str_bankid=="null"))){
                Toast.makeText(this, "Please Select Bank Name", Toast.LENGTH_SHORT).show()
            }else if((str_payslip=="1"||str_payslip=="2"||str_payslip=="12")&&brnch?.isBlank()== true){
                Toast.makeText(this, "Please enter Branch", Toast.LENGTH_SHORT).show()
            }else if((str_payslip=="1"||str_payslip=="2"||str_payslip=="12")&&checknum?.isBlank()== true){
                 Toast.makeText(this, "Please enter Check Number", Toast.LENGTH_SHORT).show()
            }else if((str_payslip=="1"||str_payslip=="2"||str_payslip=="12")&&checkdd?.isBlank()== true){
                 Toast.makeText(this, "Please enter Check Date", Toast.LENGTH_SHORT).show()
            }else if((str_payslip=="7")&&paymntdt?.isBlank()== true){
                Toast.makeText(this, "Please Select Payment Date", Toast.LENGTH_SHORT).show()
            }else if((str_payslip=="7")&&approvalcode?.isBlank()== true){
                Toast.makeText(this, "Please enter Approval Code", Toast.LENGTH_SHORT).show()
            }else if((str_payslip=="7")&&creditCard4?.isBlank()== true){
                Toast.makeText(this, "Please enter CreditCard Number", Toast.LENGTH_SHORT).show()
            }else if(paymntdt?.isBlank()== true&&(str_payslip=="4")){
                Toast.makeText(this, "Please Select Payment Date", Toast.LENGTH_SHORT).show()
            }else if(approvalcode?.isBlank()== true&&(str_payslip=="4")){
                Toast.makeText(this, "Please enter Approval Code", Toast.LENGTH_SHORT).show()
            }else if(creditCard4?.isBlank()== true&&(str_payslip=="4")){
                Toast.makeText(this, "Please enter CreditCard Number", Toast.LENGTH_SHORT).show()
        }else if(paymntdt?.isBlank()== true&&(str_payslip=="8")){
                Toast.makeText(this, "Please Select Payment Date", Toast.LENGTH_SHORT).show()
            }else if(approvalcode?.isBlank()== true&&(str_payslip=="8")){
                Toast.makeText(this, "Please enter Approval Code", Toast.LENGTH_SHORT).show()
            }else if(debitCard4?.isBlank()== true&&(str_payslip=="8")){
                Toast.makeText(this, "Please enter Debit Card Number", Toast.LENGTH_SHORT).show()
        }else if((str_gstval?.isEmpty()==true)||str_gstval=="0"||(str_gstval=="null")){
               Toast.makeText(this, "Please Select GST", Toast.LENGTH_SHORT).show()
        } else if(gstnum?.isEmpty()== true && str_gstval=="569480000"){
               Toast.makeText(this, "Please enter GST Number", Toast.LENGTH_SHORT).show()
        }else {
            createSaf(createcaf,
                strbusinessSegment?.toString(),subbssegment,customername,phonenumber,"","",
                authemail?.toString(),authfather?.toString(),
                authmobile.toString(),authname?.toString(),"",authpincode.toString(),buildingnumber,
                customername,email,floor,phonenumber,
                pincode,instemail?.toString(),instmobile?.toString(),instpin.toString(),approvalcode?.toString(),
                brnch,checkdd,checknum,debitCard4,
                paymntdt,txtty,srcdepst,creditCard4,pan,tan,gstnum,
                companyName?.toString()
            )
        }
    }
    private fun validEmail(email: String): Boolean {
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }


    fun itemListener(){
        safContactLayout.et_wrkngdys.setOnClickListener { safContactLayout.sp_wrkng_days.performClick() }
        safContactLayout.sp_wrkng_days.onItemSelectedListener = this
        layoutPayment.et_payslip.setOnClickListener { layoutPayment.sp_payslip.performClick() }
        layoutPayment.sp_payslip.onItemSelectedListener = this
        layoutPayment.et_bnknm.setOnClickListener { layoutPayment.sp_bnknam.performClick() }
        layoutPayment.sp_bnknam.onItemSelectedListener = this
        binding.layoutPayment.etGst.setOnClickListener { binding.layoutPayment.spGst.performClick() }
        binding.layoutPayment.spGst.onItemSelectedListener = this
        binding.layoutPayment.etSctype.setOnClickListener { binding.layoutPayment.spSecuritytype.performClick() }
        binding.layoutPayment.spSecuritytype.onItemSelectedListener = this
         safContactLayout.et_sb_bs_sgmnt.setOnClickListener { safContactLayout.sp_cafsbbus.performClick() }
        safContactLayout.sp_cafsbbus.onItemSelectedListener = this
          layoutSafCompany_details.et_caffirm_type.setOnClickListener { layoutSafCompany_details.sp_caffirm_type.performClick() }
        layoutSafCompany_details.sp_caffirm_type.onItemSelectedListener = this
        safContactLayout.et_cafcmpny.setOnClickListener { safContactLayout.sp_cafcompany.performClick() }
        safContactLayout.sp_cafcompany.onItemSelectedListener = this
        safContactLayout.et_cafgrp.setOnClickListener { safContactLayout.sp_cafgroup.performClick() }
        safContactLayout.sp_cafgroup.onItemSelectedListener = this

        layoutSafInstallAddress.et_cafstate.setOnClickListener { layoutSafInstallAddress.sp_cafstate.performClick() }
        layoutSafInstallAddress.sp_cafstate.onItemSelectedListener = this
        layoutSafInstallAddress.et_add_cafcity.setOnClickListener { layoutSafInstallAddress.sp_cafcity.performClick() }
        layoutSafInstallAddress.sp_cafcity.onItemSelectedListener = this
        layoutSafInstallAddress.et_cafinstallarea.setOnClickListener { layoutSafInstallAddress.sp_cafcnarea.performClick() }
        layoutSafInstallAddress.sp_cafcnarea.onItemSelectedListener = this
        layoutSafInstallAddress.et_cafbuildingname.setOnClickListener { layoutSafInstallAddress.sp_cafbuilding_nm.performClick() }
        layoutSafInstallAddress.sp_cafbuilding_nm.onItemSelectedListener = this
         saf_contact_person_row.et_cfblstate.setOnClickListener { saf_contact_person_row.sp_cfblstate.performClick() }
        saf_contact_person_row.sp_cfblstate.onItemSelectedListener = this
        saf_contact_person_row.et_cfblcity.setOnClickListener { saf_contact_person_row.sp_cfblcity.performClick() }
        saf_contact_person_row.sp_cfblcity.onItemSelectedListener = this
        saf_contact_person_row.et_cfblarea.setOnClickListener { saf_contact_person_row.sp_cfblcnarea.performClick() }
        saf_contact_person_row.sp_cfblcnarea.onItemSelectedListener = this
        saf_contact_person_row.et_cfblbuilding.setOnClickListener { saf_contact_person_row.sp_cfblbuilding_nm.performClick() }
        saf_contact_person_row.sp_cfblbuilding_nm.onItemSelectedListener = this
        binding.layoutSafOtherDetails.etSafAuthState.setOnClickListener { binding.layoutSafOtherDetails.spSafAuthState.performClick() }
        binding.layoutSafOtherDetails.spSafAuthState.onItemSelectedListener = this
        binding.layoutSafOtherDetails.etSafAuthCity.setOnClickListener { binding.layoutSafOtherDetails.spSafAuthCity.performClick() }
        binding.layoutSafOtherDetails.spSafAuthCity.onItemSelectedListener = this
        layoutSafInstallAddress.et_cafbiltype.setOnClickListener { layoutSafInstallAddress.sp_cafbilltype.performClick() }
        layoutSafInstallAddress.sp_cafbilltype.onItemSelectedListener = this
        binding.safContactLayout.etVertical.setOnClickListener { binding.safContactLayout.spVertical.performClick() }
        binding.safContactLayout.spVertical.onItemSelectedListener = this

        linearDocDetails.setOnClickListener {
            Intent(this, DocumentSafActivity::class.java).also {
                it.putExtra("SafId", strSafId)
                it.putExtra("OppId", strOppId)
                it.putExtra("Status", status)
                startActivity(it)
                finish()
            }
        }
    }

    fun createSaf(
        createcaf: String,
        strbusinessSegment: String?,
        subbssegment: String?,
        customername: String?,
        phonenumber: String,
        polock: String,
        ponext: String,
        authemail: String?,
        authfather: String?,
        authname: String?,
        authaddress: String?,
        authpincode: String?,
        billingplot: String?,
        authmobile: String?,
        billingname: String?,
        billingemail: String?,
        billingfloor: String?,
        billingphn: String,
        billingpincode: String,
        instemail: String?,
        instmobile: String?,
        instpin: String?,
        approvalcode: String?,
        brnch: String?,
        checkdd: String?,
        checknum: String?,
        debitCard4: String?,
        paymntdt: String?,
        txtty: String?,
        srcdepst: String?,
        creditCard4: String?,
        pan: String?,
        tan: String?,
        gstnum: String?,
        companyName: String?
    ) {
        inProgress()
        val email = layoutSafOtherDetails.etSafEmailId.text.toString()
        val father = layoutSafOtherDetails.etFatherHsb.text.toString()
        val mobile = layoutSafOtherDetails.etSafMob.text.toString()
        val name = layoutSafOtherDetails.etSafName.text.toString()
        val pincode = layoutSafOtherDetails.etSafAuthPinCode.text.toString()

        val safAuthSigDetails = SAFAuthSigDetails(str_atcity_code,"10001",email,
            father,mobile,name,"",pincode,str_atinststateId)
        val billingName = saf_contact_person_row.et_caf_cntname.text.toString()
        val safBillingAddress = SAFBillingAddress(str_bladd_area,str_blinst_building_nm,str_blcity_code,
            billingName , "10001",billingemail,
            billingfloor,billingphn,billingpincode,strBillSameAdd,
        "","",str_blinststateId)

        val safInstallationAddress = SAFInstallationAddresses(str_add_area,str_billtype,str_inst_building_nm,
        "0",str_city_code,AppConstants.COUNTRY_CODE,instemail,"0","0",
        "0","0",instmobile,instpin,"",
        "",str_inststateId)
        val safPayments = SAFPayments(approvalcode,str_bankid,brnch,checkdate,checknum,creditCard4,debitCard4,
        "",cafpaydate,str_payslip,"",srcdepst,str_sctype,amount,txtty,strPaymentId)
        val safCompanyReq = SAFCompanyDetails(strCompanyName,str_firmtype,"")

        if(amount=="0"){
            amount="0.000"
        }

        val createSafReq = CreateSafReq(createcaf, Constants.AUTH_KEY, safAuthSigDetails,
            safBillingAddress, safCompanyReq,safInstallationAddress,safPayments,
            strbusinessSegment,str_cmp,
            strCompanyName,customername,str_wrkngdays,str_grp,
            str_gstval,gstnum,strOppId,strOppId,pan,
            password,"",strServiceId,subbssegment,tan,userName,strVertical,strSafId)

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.createSaf(createSafReq)
        call.enqueue(object : Callback<CafDetailResponse?> {
            override fun onResponse(call: Call<CafDetailResponse?>, response: Response<CafDetailResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    OutProgress()
                    val msg = response.body()?.Response?.Message
                    if(response.body()?.StatusCode=="200"){
                        Toast.makeText(this@SAFActivity, msg, Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@SAFActivity, SafTabActivity::class.java)
                       /* val bundle = Bundle()
                        bundle.putString("OppId",strOppId)
                        intent.putExtras(bundle)*/
                        startActivity(intent)
                        finish()
                    }else{
                        Toast.makeText(this@SAFActivity, msg, Toast.LENGTH_SHORT).show()
                    }
                }
            }
            override fun onFailure(call: Call<CafDetailResponse?>, t: Throwable) {
                binding.safProgressLayout.progressOverlay.visibility = View.GONE
                Log.e("RetroError", t.toString())
            }
        })
}
    fun init() {
        strBillSameAdd="0"
        checkbx.setOnCheckedChangeListener { _, _ ->
            strBillSameAdd="1"
            checkbx.isChecked=true
            val instemail = layoutSafInstallAddress.etSafEmail.text
            val instmobile = layoutSafInstallAddress.et_cafmbnum.text
            val instpin = layoutSafInstallAddress.et_cafpin.text
            str_blinststateId = str_inststateId
            str_blcity_code = str_city_code
            str_bladd_area = str_add_area
            str_blinst_building_nm = str_inst_building_nm
            val bulCode = layoutSafInstallAddress.et_cafblcode_red.text
            var blstatePosition = 0
            resources.getStringArray(R.array.list_state_code).forEachIndexed { index, s ->
                if (s == str_inststateId) blstatePosition = index
                return@forEachIndexed
            }
            val blstateAdapter = ArrayAdapter(this@SAFActivity, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.list_of_state))
            blstateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
            saf_contact_person_row.sp_cfblstate.adapter = blstateAdapter
            saf_contact_person_row.sp_cfblstate.setSelection(blstatePosition)
            blstateAdapter.notifyDataSetChanged()
            saf_contact_person_row.et_safBillingEmailId.text = instemail
            saf_contact_person_row.etSafPhoneNum.text = instmobile
            saf_contact_person_row.et_cfblpin_code.text = instpin
            saf_contact_person_row.et_cfblbuildng_num.text = bulCode
            getBillingCity(str_blinststateId)

        }
    }

    private fun  getWorkOrder () {
        //inProgress()
        val getSafReq = strSafId?.let {
            GetSafReq(Constants.GETALL_WORKORDER, Constants.AUTH_KEY,"",password,
                it,userName)
        }
        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getWorkOrder(getSafReq)
        call.enqueue(object : Callback<GetWorkOrderRes?> {
            override fun onResponse(call: Call<GetWorkOrderRes?>, response: Response<GetWorkOrderRes?>) {
                // OutProgress()
                if (response.isSuccessful && response.body() != null) {
                    try {
                        if(response.body()?.Response?.StatusCode==200) {
                            //  val msg = response.body()!!.Response.Message
                            workOrderList = response.body()?.Response?.Data
                            if(workOrderList!=null) {
                                rv_add_doa.visibility=View.VISIBLE
                                setAllWorkListAdapter(workOrderList!!, this@SAFActivity)
                            }
                        }
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<GetWorkOrderRes?>, t: Throwable) {
                Log.e("RetroError", t.toString())
            }
        })
    }

    private fun setAllWorkListAdapter(workData: ArrayList<WorkData>, context: Context?) {
        rv_add_doa.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = GetAllWorkOrderAdapter(workData,context)
        }
    }

    private fun submitCaf () {
        inProgress()
        val submitSafReq = SubmitSafReq(Constants.SUBMIT_SAF, Constants.AUTH_KEY, password,strSafId, userName)
        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getSaf(submitSafReq)
        call.enqueue(object : Callback<ReportResponse?> {
            override fun onResponse(call: Call<ReportResponse?>, response: Response<ReportResponse?>) {
                OutProgress()
                if (response.isSuccessful && response.body() != null) {
                    val msg = response.body()?.Response?.Message
                    if(response.body()?.StatusCode=="200"){
                        Toast.makeText(this@SAFActivity, msg, Toast.LENGTH_SHORT).show()
                        tvCreateSaf.visibility=View.GONE
                        caf()
                    }else{
                        Toast.makeText(this@SAFActivity, msg, Toast.LENGTH_SHORT).show()
                    }
                }
            }
            override fun onFailure(call: Call<ReportResponse?>, t: Throwable) {
                binding.safProgressLayout.progressOverlay.visibility = View.GONE
                Log.e("RetroError", t.toString())
            }
        })
    }

    fun caf(){
        Intent(this, SAFActivity::class.java).also {
            it.putExtra("SafId", strSafId)
            it.putExtra("OppId", strOppId)
            startActivity(it)
            finish()
        }
    }


    private fun getSaf () {
        inProgress()
        if(strSafId?.isNotBlank()==true){
            strOppId=""
        }
        val safReq = GetSafReq(Constants.GET_SAF, Constants.AUTH_KEY,strOppId,password,strSafId,userName)

     //   val safReq = GetSafReq(Constants.GET_SAF, Constants.AUTH_KEY, strSafId,strOppId, password, userName)
        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getSaf(safReq)
        call.enqueue(object : Callback<GetSafRes?> {
            override fun onResponse(call: Call<GetSafRes?>, response: Response<GetSafRes?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        OutProgress()
                      //  val msg = response.body()!!.Response.Message
                        if (response.body()?.Response?.StatusCode == 200) {
                            strSafId = response.body()?.Response?.Data?.get(0)?.SafNo
                            if(strSafId.isNullOrBlank()){
                                tvCreateSaf.visibility=View.VISIBLE
                                tv_update.visibility=View.GONE
                                tv_submit.visibility=View.GONE
                                binding.searchToolbarSaf.downloadpdf.visibility = View.GONE
                                binding.searchToolbarSaf.share.visibility = View.GONE
                                linearLan.visibility=View.GONE
                                linearDoc.visibility=View.GONE
                            }else{
                                tvCreateSaf.visibility=View.GONE
                                tv_update.visibility=View.VISIBLE
                                tv_submit.visibility=View.VISIBLE
                                linearLan.visibility=View.VISIBLE
                                linearDoc.visibility=View.VISIBLE
                                binding.searchToolbarSaf.downloadpdf.visibility = View.VISIBLE
                                binding.searchToolbarSaf.share.visibility = View.GONE
                                layoutSafCompany_details.et_caffirm_type.isEnabled=false
                                layoutSafCompany_details.et_compnyname.isEnabled=false
                            }
                            //strOppId =  response.body()?.Response?.Data?.get(0)?.OpportunityId
                            strServiceId  =  response.body()?.Response?.Data?.get(0)?.ServiceId
                            strCompanyName  =  response.body()?.Response?.Data?.get(0)?.CompanyName
                            binding.safContactLayout.safContactInfo = response.body()?.Response?.Data?.get(0)
                            binding.layoutSafCompanyDetails.cafCompnyInfo = response.body()?.Response?.Data?.get(0)?.companyDetailSAF
                            binding.layoutSafInstallAddress.cafInstAdress = response.body()?.Response?.Data?.get(0)?.installationAddressesSAF
                            binding.safContactPersonRow.cafBillAdd= response.body()?.Response?.Data?.get(0)?.billingAddressSAF
                            binding.layoutSafOtherDetails.cafauthoInfo = response.body()?.Response?.Data?.get(0)?.authSigDetailsSAF
                            binding.layoutPayment.cafPaymentInfo = response.body()?.Response?.Data?.get(0)?.paymentsSAF
                            strBankName = response.body()?.Response?.Data?.get(0)?.paymentsSAF?.BankName
                            val productSeg =  response.body()?.Response?.Data?.get(0)?.ProductSegment
                            getBankList()

                            strPaymentStatus =response.body()?.Response?.Data?.get(0)?.paymentsSAF?.PaymentStatus
                            strVertical =response.body()?.Response?.Data?.get(0)?.Vertical
                            strProductId= response.body()?.Response?.Data?.get(0)?.ProductSegment
                            str_inststateId= response.body()?.Response?.Data?.get(0)?.installationAddressesSAF?.Inst_State
                            str_blinststateId = response.body()?.Response?.Data?.get(0)?.billingAddressSAF?.Bill_State
                            str_sub_bus = response.body()?.Response?.Data?.get(0)?.SubBussinessSegment
                            strPaymentId = response.body()?.Response?.Data?.get(0)?.paymentsSAF?.PaymentId
                            str_firmtype= response.body()?.Response?.Data?.get(0)?.companyDetailSAF?.FirmType
                            str_atinststateId = response.body()?.Response?.Data?.get(0)?.authSigDetailsSAF?.Auth_State
                            str_payslip = response.body()?.Response?.Data?.get(0)?.paymentsSAF?.PaymentSlip
                            str_billtype = response.body()?.Response?.Data?.get(0)?.installationAddressesSAF?.Inst_BillType
                            str_sctype= response.body()?.Response?.Data?.get(0)?.paymentsSAF?.SecurityDepositType
                            str_gstval = response.body()?.Response?.Data?.get(0)?.GstNumber
                            str_city_code = response.body()?.Response?.Data?.get(0)?.installationAddressesSAF?.Inst_City
                            str_blcity_code = response.body()?.Response?.Data?.get(0)?.billingAddressSAF?.Bill_City
                            str_atcity_code = response.body()?.Response?.Data?.get(0)?.authSigDetailsSAF?.Auth_City
                            str_add_area= response.body()?.Response?.Data?.get(0)?.installationAddressesSAF?.Inst_Area
                            str_inst_building_nm = response.body()?.Response?.Data?.get(0)?.installationAddressesSAF?.Inst_BuildingName
                            str_bladd_area= response.body()?.Response?.Data?.get(0)?.billingAddressSAF?.Bill_Area
                            str_blinst_building_nm = response.body()?.Response?.Data?.get(0)?.billingAddressSAF?.Bill_BuildingName
                            strCompany = response.body()?.Response?.Data?.get(0)?.Company.toString()
                            strGroup = response.body()?.Response?.Data?.get(0)?.Group.toString()
                            str_indusid = response.body()?.Response?.Data?.get(0)?.companyDetailSAF?.IndustryType
                            str_wrkngdays= response.body()?.Response?.Data?.get(0)?.CustomerWorkingHours
                            getCompany(strCompany)
                            binding.layoutPayment.etGstt.setText(response.body()?.Response?.Data?.get(0)?.GstNumberDetial)
                            strBillSameAdd = response.body()?.Response?.Data?.get(0)?.billingAddressSAF?.Bill_Sameaddress
                            if(strBillSameAdd=="1") {
                                checkbx.isChecked=true
                            }
                            val tan =  response.body()?.Response?.Data?.get(0)?.TanNo
                            val pan =  response.body()?.Response?.Data?.get(0)?.PanNo
                            layoutPayment.et_pannum.setText(tan)
                            layoutPayment.et_tannum.setText(pan)

                            var cntstatePosition = 0
                            resources.getStringArray(R.array.list_state_code).forEachIndexed { index, s ->
                                if (s == str_inststateId) cntstatePosition = index
                                return@forEachIndexed
                            }
                            val cntstateAdapter = ArrayAdapter(this@SAFActivity, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.list_of_state))
                            cntstateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            layoutSafInstallAddress.sp_cafstate.adapter = cntstateAdapter
                            layoutSafInstallAddress.sp_cafstate.setSelection(cntstatePosition)
                            cntstateAdapter.notifyDataSetChanged()
                            getInstallCity(str_inststateId.toString())

                            var atstatePos = 0
                            resources.getStringArray(R.array.list_state_code).forEachIndexed {
                                index, s ->
                                if (s == str_atinststateId)
                                    atstatePos = index
                                return@forEachIndexed
                            }
                            val atstateAdapter = ArrayAdapter(this@SAFActivity, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.list_of_state))
                            atstateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            binding.layoutSafOtherDetails.spSafAuthState.adapter = atstateAdapter
                            binding.layoutSafOtherDetails.spSafAuthState.setSelection(atstatePos)
                            atstateAdapter.notifyDataSetChanged()

                            var blstatePosition = 0
                            resources.getStringArray(R.array.list_state_code).forEachIndexed { index, s ->
                                if (s == str_blinststateId) blstatePosition = index
                                return@forEachIndexed
                            }
                            val blstateAdapter = ArrayAdapter(this@SAFActivity, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.list_of_state))
                            blstateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            saf_contact_person_row.sp_cfblstate.adapter = blstateAdapter
                            saf_contact_person_row.sp_cfblstate.setSelection(blstatePosition)
                            blstateAdapter.notifyDataSetChanged()

                            var sbBusPosition = 0
                            resources.getStringArray(R.array.list_of_subBusSegment).forEachIndexed { index, s ->
                                if (s == str_sub_bus) sbBusPosition = index
                                return@forEachIndexed
                            }
                            val sbBusAdapter = ArrayAdapter(this@SAFActivity, android.R.layout.simple_spinner_item,resources.getStringArray(R.array.list_of_subBusSegment))
                            sbBusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            safContactLayout.sp_cafsbbus.adapter = sbBusAdapter
                            safContactLayout.sp_cafsbbus.setSelection(sbBusPosition)
                            sbBusAdapter.notifyDataSetChanged()

                            var firmPosition = 0
                            resources.getStringArray(R.array.list_firm_type).forEachIndexed { index, s ->
                                if (s == str_firmtype) firmPosition = index
                                return@forEachIndexed
                            }
                            val firmtypeAdapter = ArrayAdapter(this@SAFActivity, android.R.layout.simple_spinner_item,resources.getStringArray(R.array.list_firm_type))
                            firmtypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            layoutSafCompany_details.sp_caffirm_type.adapter = firmtypeAdapter
                            layoutSafCompany_details.sp_caffirm_type.setSelection(firmPosition)
                            firmtypeAdapter.notifyDataSetChanged()

                            var workingPosition = 0
                            resources.getStringArray(R.array.list_of_workingHours).forEachIndexed { index, s ->
                                if (s == str_wrkngdays) workingPosition = index
                                return@forEachIndexed
                            }
                            val workingAdapter = ArrayAdapter(this@SAFActivity, android.R.layout.simple_spinner_item,resources.getStringArray(R.array.list_of_workingHours))
                            workingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            safContactLayout.sp_wrkng_days.adapter = workingAdapter
                            safContactLayout.sp_wrkng_days.setSelection(workingPosition)
                            workingAdapter.notifyDataSetChanged()

                            var billtypePosition = 0
                            resources.getStringArray(R.array.list_of_billtype).forEachIndexed { index, s ->
                                if (s == str_billtype) billtypePosition = index
                                return@forEachIndexed
                            }
                            val billtypeAdapter = ArrayAdapter(this@SAFActivity, android.R.layout.simple_spinner_item,resources.getStringArray(R.array.list_of_billtype))
                            billtypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            layoutSafInstallAddress.sp_cafbilltype.adapter = billtypeAdapter
                            layoutSafInstallAddress.sp_cafbilltype.setSelection(billtypePosition)
                            billtypeAdapter.notifyDataSetChanged()

                            var payslipPosition = 0
                            resources.getStringArray(R.array.list_of_payslipval).forEachIndexed { index, s ->
                                if (s == str_payslip) payslipPosition = index
                                return@forEachIndexed
                            }
                            val payslipAdapter = ArrayAdapter(this@SAFActivity, android.R.layout.simple_spinner_item,resources.getStringArray(R.array.list_of_payslip))
                            payslipAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            layoutPayment.sp_payslip.adapter = payslipAdapter
                            layoutPayment.sp_payslip.setSelection(payslipPosition)
                            payslipAdapter.notifyDataSetChanged()

                            var securityPosition = 0
                            resources.getStringArray(R.array.list_of_boolean_values).forEachIndexed { index, s ->
                                if (s == str_sctype) securityPosition = index
                                return@forEachIndexed
                            }
                            val securityAdapter = ArrayAdapter(this@SAFActivity, android.R.layout.simple_spinner_item,resources.getStringArray(R.array.list_of_Deposit))
                            securityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            layoutPayment.sp_securitytype.adapter = securityAdapter
                            layoutPayment.sp_securitytype.setSelection(securityPosition)
                            securityAdapter.notifyDataSetChanged()

                            var gstPosition = 0
                            resources.getStringArray(R.array.list_of_gst).forEachIndexed { index, s ->
                                if (s == str_gstval) gstPosition = index
                                return@forEachIndexed
                            }
                            val gstAdapter = ArrayAdapter(this@SAFActivity, android.R.layout.simple_spinner_item,resources.getStringArray(R.array.list_of_gst))
                            gstAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            binding.layoutPayment.spGst.adapter = gstAdapter
                            binding.layoutPayment.spGst.setSelection(gstPosition)
                            gstAdapter.notifyDataSetChanged()

                            val paymentDate = response.body()?.Response?.Data?.get(0)?.paymentsSAF?.PaymentDate
                            if(paymentDate?.isNotEmpty()==true){
                                val split2 = paymentDate.split("-")
                                val date2 = split2[0]
                                val month2 = split2[1]
                                val year2 = split2[2]
                                layoutPayment.et_paymntdt.setText("$date2-$month2-$year2")
                                cafpaydate = ("$year2-$month2-$date2")
                            }

                            val chqdate = response.body()?.Response?.Data?.get(0)?.paymentsSAF?.ChequeDDDate
                            if(chqdate?.isNotEmpty()==true){
                                val split2 = chqdate.split("-")
                                val date2 = split2[0]
                                val month2 = split2[1]
                                val year2 = split2[2]
                                layoutPayment.et_paymntdt.setText("$date2-$month2-$year2")
                                checkdate = ("$year2-$month2-$date2")
                            }

                            layoutSafInstallAddress.et_cafstate.isEnabled=false
                            layoutSafInstallAddress.et_add_cafcity.isEnabled=false
                            layoutSafInstallAddress.et_cafinstallarea.isEnabled=false
                            layoutSafInstallAddress.et_cafbuildingname.isEnabled=false
                            status = response.body()?.Response?.Data?.get(0)?.Status

                            if((status=="Verification Pending")||(status=="Approved")||(status=="Installation pending")||
                                (status=="Provisioning in Progress")||(status=="CPE dispatched")||(status=="CPE delivered @ Customer location")
                                ||(status=="Completed")||(status=="Order accepted by Customer")){
                                locked()
                            }else{
                                calender()
                            }
                        }
                        else{
                            locked()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<GetSafRes?>, t: Throwable) {
                binding.safProgressLayout.progressOverlay.visibility = View.GONE
                Log.e("RetroError", t.toString())
            }
        })
    }

    fun inProgress(){
        inAnimation = AlphaAnimation(0f, 1f)
        inAnimation?.duration =200
        binding.safProgressLayout.progressOverlay.animation = inAnimation
        binding.safProgressLayout.progressOverlay.visibility = View.VISIBLE
    }

    fun OutProgress(){
        outAnimation = AlphaAnimation(1f, 0f)
        outAnimation?.duration =200
        binding.safProgressLayout.progressOverlay.animation = outAnimation
        binding.safProgressLayout.progressOverlay.visibility = View.GONE
    }

    fun locked(){
        safContactLayout.etCstName.isEnabled= false
        safContactLayout.sp_cafcompany.isEnabled=false
        safContactLayout.sp_cafgroup.isEnabled=false
        safContactLayout.sp_cafsbbus.isEnabled=false
        safContactLayout.sp_wrkng_days.isEnabled=false
        safContactLayout.et_cafcmpny.isEnabled=false
        safContactLayout.et_cafgrp.isEnabled=false
        safContactLayout.et_cafbs_sgmnt.isEnabled=false
        safContactLayout.et_sb_bs_sgmnt.isEnabled=false
        safContactLayout.et_wrkngdys.isEnabled=false
        layoutSafCompany_details.et_compnyname.isEnabled=false
        layoutSafCompany_details.sp_caffirm_type.isEnabled=false
        layoutSafCompany_details.et_caffirm_type.isEnabled=false
        layoutSafInstallAddress.etSafEmail.isEnabled=false
        layoutSafInstallAddress.et_cafmbnum.isEnabled=false
        layoutSafInstallAddress.et_cafleadname.isEnabled=false
        layoutSafInstallAddress.et_cafleadid.isEnabled=false
        layoutSafInstallAddress.sp_cafbilltype.isEnabled=false
        layoutSafInstallAddress.sp_cafstate.isEnabled=false
        layoutSafInstallAddress.sp_cafcity.isEnabled=false
        layoutSafInstallAddress.sp_cafcnarea.isEnabled=false
        layoutSafInstallAddress.sp_cafbuilding_nm.isEnabled=false
        layoutSafInstallAddress.et_cafblcode_red.isEnabled=false
        layoutSafInstallAddress.et_cafpin.isEnabled=false
        layoutSafInstallAddress.et_cafbiltype.isEnabled=false
        layoutSafInstallAddress.et_cafstate.isEnabled=false
        layoutSafInstallAddress.et_add_cafcity.isEnabled=false
        layoutSafInstallAddress.et_cafinstallarea.isEnabled=false
        layoutSafInstallAddress.et_cafbuildingname.isEnabled=false

        saf_contact_person_row.et_caf_cntname.isEnabled=false
        saf_contact_person_row.et_safBillingEmailId.isEnabled=false
        saf_contact_person_row.etSafPhoneNum.isEnabled=false
        saf_contact_person_row.sp_cfblstate.isEnabled=false
        saf_contact_person_row.sp_cfblcity.isEnabled=false
        saf_contact_person_row.sp_cfblcnarea.isEnabled=false
        saf_contact_person_row.sp_cfblbuilding_nm.isEnabled=false
        saf_contact_person_row.et_cfblbuilding_num.isEnabled=false
        saf_contact_person_row.et_cfblbuildng_num.isEnabled=false
        saf_contact_person_row.et_cfblfloor.isEnabled=false
        saf_contact_person_row.et_cfblpin_code.isEnabled=false
        saf_contact_person_row.et_cfblstate.isEnabled=false
        saf_contact_person_row.et_cfblcity.isEnabled=false
        saf_contact_person_row.et_cfblarea.isEnabled=false
        saf_contact_person_row.et_cfblbuilding.isEnabled=false
        layoutSafOtherDetails.etSafName.isEnabled=false
        layoutSafOtherDetails.etFatherHsb.isEnabled=false
        layoutSafOtherDetails.etSafMob.isEnabled=false
        layoutSafOtherDetails.etSafEmailId.isEnabled=false
        layoutSafOtherDetails.spSafAuthState.isEnabled=false
        layoutSafOtherDetails.spSafAuthCity.isEnabled=false
        layoutSafOtherDetails.etSafAuthPinCode.isEnabled=false
        layoutSafOtherDetails.etSafAuthCity.isEnabled=false
        layoutSafOtherDetails.etSafAuthState.isEnabled=false
        layoutPayment.et_cafpyid.isEnabled=false
        layoutPayment.sp_payslip.isEnabled=false
        layoutPayment.et_payslip.isEnabled=false
        layoutPayment.et_totalamt.isEnabled=false
        layoutPayment.et_securitycdeposit.isEnabled=false
        layoutPayment.sp_securitytype.isEnabled=false
        layoutPayment.et_sctype.isEnabled=false
        layoutPayment.et_txtid.isEnabled=false
        layoutPayment.et_paymntdt.isEnabled=false
        layoutPayment.sp_bnknam.isEnabled=false
        layoutPayment.et_brnch.isEnabled=false
        layoutPayment.et_chknum.isEnabled=false
        layoutPayment.et_chkdate.isEnabled=false
        layoutPayment.et_appcode.isEnabled=false
        layoutPayment.et_cardfrdgt.isEnabled=false
        layoutPayment.et_creditfrdgt.isEnabled=false
        layoutPayment.et_pannum.isEnabled=false
        layoutPayment.et_tannum.isEnabled=false
        layoutPayment.sp_gst.isEnabled=false
        layoutPayment.et_gst.isEnabled=false
        layoutPayment.et_gstt.isEnabled=false
        tvCreateSaf.visibility=View.GONE
        tv_submit.visibility = View.GONE
        tv_update.visibility = View.GONE
    }

    @SuppressLint("SetTextI18n")
    fun  calender(){
        try {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            layoutPayment.et_paymntdt.setOnClickListener {
                val dpd = DatePickerDialog(this, { _, year, monthOfYear, dayOfMonth ->
                    val mnth = monthOfYear + 1
                    layoutPayment.et_paymntdt.setText("$dayOfMonth-$mnth-$year")
                    val trgt = layoutPayment.et_paymntdt.text.toString()
                    val split = trgt.split("-")
                    val dateee = split[0]
                    val month1 = split[1]
                    val year1 = split[2]
                    cafpaydate = ("$year1-$month1-$dateee")
                }, year, month, day)
                dpd.show()
            }
            layoutPayment.et_chkdate.setOnClickListener {
                val dpd = DatePickerDialog(this, { _, year, monthOfYear, dayOfMonth ->
                    val mnth = monthOfYear + 1
                    layoutPayment.et_chkdate.setText("$dayOfMonth-$mnth-$year")
                    val trgt = layoutPayment.et_chkdate.text.toString()
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
                        val adapter12 = ArrayAdapter(this@SAFActivity, android.R.layout.simple_spinner_item, company!!)
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        safContactLayout.sp_cafcompany.adapter = adapter12
                        safContactLayout.sp_cafcompany.setSelection(comPosition)
                        adapter12.notifyDataSetChanged()

                        val adapter11 = ArrayAdapter(this@SAFActivity, android.R.layout.simple_spinner_item, group!!)
                        adapter11.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        safContactLayout.sp_cafgroup.adapter = adapter11
                        safContactLayout.sp_cafgroup.setSelection(groupPosition)
                        adapter11.notifyDataSetChanged()
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }
                }
            }
            override fun onFailure(call: Call<GetCompanyResponse?>, t: Throwable) {
                binding.safProgressLayout.progressOverlay.visibility = View.GONE
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
                        bankid.add("")
                        for (item in bankList!!){
                            item.Bankname?.let { bankname.add(it) }
                            item.BankId?.let { bankid.add(it) }
                        }
                        var bankPosition=0
                        bankname.forEachIndexed { index, s ->
                            if(s==strBankName)bankPosition=index
                            return@forEachIndexed
                        }
                        val adapter12 = ArrayAdapter(this@SAFActivity, android.R.layout.simple_spinner_item, bankname)
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        layoutPayment.sp_bnknam.adapter = adapter12
                        layoutPayment.sp_bnknam.setSelection(bankPosition)
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
        tvCreateSaf.visibility = View.VISIBLE
        tv_submit.visibility = View.VISIBLE
        tv_update.visibility = View.VISIBLE
        linearContactDetails.visibility = View.VISIBLE

        linearCustomer.setOnClickListener {
            linearContactDetails.visibility = View.VISIBLE
            linearCompanyDetails.visibility = View.GONE
            linearInstallAddressDetails.visibility = View.GONE
            linearBillingDetails.visibility= View.GONE
            linearAuthorizedDetails.visibility = View.GONE
            linearPaymentDetails.visibility = View.GONE
            linearDocDetails.visibility = View.GONE
            binding.linearLanDetail.visibility = View.GONE
            binding.linearPreTaskDetails.visibility = View.GONE
        }
        linearCompany.setOnClickListener {
            linearContactDetails.visibility = View.GONE
            linearCompanyDetails.visibility = View.VISIBLE
            linearInstallAddressDetails.visibility = View.GONE
            linearBillingDetails.visibility= View.GONE
            linearAuthorizedDetails.visibility = View.GONE
            linearPaymentDetails.visibility = View.GONE
            linearDocDetails.visibility = View.GONE
            binding.linearLanDetail.visibility = View.GONE
            binding.linearPreTaskDetails.visibility = View.GONE
        }
        linearInstallAddress.setOnClickListener {
            linearContactDetails.visibility = View.GONE
            linearCompanyDetails.visibility = View.GONE
            linearInstallAddressDetails.visibility = View.VISIBLE
            linearBillingDetails.visibility= View.GONE
            linearAuthorizedDetails.visibility = View.GONE
            linearPaymentDetails.visibility = View.GONE
            linearDocDetails.visibility = View.GONE
            binding.linearLanDetail.visibility = View.GONE
            binding.linearPreTaskDetails.visibility = View.GONE
        }
        linearBilling.setOnClickListener {
            linearContactDetails.visibility = View.GONE
            linearCompanyDetails.visibility = View.GONE
            linearInstallAddressDetails.visibility = View.GONE
            linearBillingDetails.visibility= View.VISIBLE
            linearAuthorizedDetails.visibility = View.GONE
            linearPaymentDetails.visibility = View.GONE
            linearDocDetails.visibility = View.GONE
            binding.linearLanDetail.visibility = View.GONE
            binding.linearPreTaskDetails.visibility = View.GONE
        }
        linearAuthorized.setOnClickListener {
            linearContactDetails.visibility = View.GONE
            linearCompanyDetails.visibility = View.GONE
            linearInstallAddressDetails.visibility = View.GONE
            linearBillingDetails.visibility= View.GONE
            linearAuthorizedDetails.visibility = View.VISIBLE
            linearPaymentDetails.visibility = View.GONE
            linearDocDetails.visibility = View.GONE
            binding.linearLanDetail.visibility = View.GONE
            binding.linearPreTaskDetails.visibility = View.GONE
        }
        linearPayment.setOnClickListener {
            linearContactDetails.visibility = View.GONE
            linearCompanyDetails.visibility = View.GONE
            linearInstallAddressDetails.visibility = View.GONE
            linearBillingDetails.visibility= View.GONE
            linearAuthorizedDetails.visibility = View.GONE
            linearPaymentDetails.visibility = View.VISIBLE
            linearDocDetails.visibility = View.GONE
            binding.linearLanDetail.visibility = View.GONE
            binding.linearPreTaskDetails.visibility = View.GONE
        }

        linearDoc.setOnClickListener {
            linearContactDetails.visibility = View.GONE
            linearCompanyDetails.visibility = View.GONE
            linearInstallAddressDetails.visibility = View.GONE
            linearBillingDetails.visibility= View.GONE
            linearAuthorizedDetails.visibility = View.GONE
            linearPaymentDetails.visibility = View.GONE
            linearDocDetails.visibility = View.VISIBLE
            binding.linearLanDetail.visibility = View.GONE
            binding.linearPreTaskDetails.visibility = View.GONE
        }
        binding.linearLan.setOnClickListener {
            linearContactDetails.visibility = View.GONE
            linearCompanyDetails.visibility = View.GONE
            linearInstallAddressDetails.visibility = View.GONE
            linearBillingDetails.visibility= View.GONE
            linearAuthorizedDetails.visibility = View.GONE
            linearPaymentDetails.visibility = View.GONE
            linearDocDetails.visibility = View.GONE
            binding.linearLanDetail.visibility = View.VISIBLE
            binding.linearPreTaskDetails.visibility = View.GONE
        }
        binding.linearPreTask.setOnClickListener {
            linearContactDetails.visibility = View.GONE
            linearCompanyDetails.visibility = View.GONE
            linearInstallAddressDetails.visibility = View.GONE
            linearBillingDetails.visibility= View.GONE
            linearAuthorizedDetails.visibility = View.GONE
            linearPaymentDetails.visibility = View.GONE
            linearDocDetails.visibility = View.GONE
            binding.linearLanDetail.visibility = View.GONE
            binding.linearPreTaskDetails.visibility = View.VISIBLE
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
                        val adapter12 = ArrayAdapter(this@SAFActivity, android.R.layout.simple_spinner_item, city!!)
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        layoutSafInstallAddress.sp_cafcity.adapter = adapter12
                        layoutSafInstallAddress.sp_cafcity.setSelection(cityPosition)
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
                        val adapter12 = ArrayAdapter(this@SAFActivity, android.R.layout.simple_spinner_item, billingcity!!)
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        saf_contact_person_row.sp_cfblcity.adapter = adapter12
                        saf_contact_person_row.sp_cfblcity.setSelection(cityPosition)
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
                        val adapter12 = ArrayAdapter(this@SAFActivity, android.R.layout.simple_spinner_item, authocity!!)
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        layoutSafOtherDetails.spSafAuthCity.adapter = adapter12
                        layoutSafOtherDetails.spSafAuthCity.setSelection(cityPosition)
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

    private fun getInstallArea(str_city: String?, str_city_code: String?) {
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

                        val adapter12 = ArrayAdapter(this@SAFActivity, android.R.layout.simple_spinner_item, area!!)
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        layoutSafInstallAddress.sp_cafcnarea.adapter = adapter12
                        layoutSafInstallAddress.sp_cafcnarea.setSelection(areaPosition)
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
                str_city_code, str_city ,"",userName,password,false)
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

                        val adapter12 = ArrayAdapter(this@SAFActivity, android.R.layout.simple_spinner_item, billingarea!!)
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        saf_contact_person_row.sp_cfblcnarea.adapter = adapter12
                        saf_contact_person_row.sp_cfblcnarea.setSelection(areaPosition)
                        adapter12.notifyDataSetChanged()
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

    private fun getInstallBuilding(areaname: String, areaCode: String?) {
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

                        val adapter12 = ArrayAdapter(this@SAFActivity, android.R.layout.simple_spinner_item, building!!)
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        layoutSafInstallAddress.sp_cafbuilding_nm.adapter = adapter12
                        layoutSafInstallAddress.sp_cafbuilding_nm.setSelection(buildPosition)
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

    private fun getBillingBuilding(areaname: String, areaCode: String?) {
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

                        val adapter12 = ArrayAdapter(this@SAFActivity, android.R.layout.simple_spinner_item,
                            billingbuilding!!
                        )
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        saf_contact_person_row.sp_cfblbuilding_nm.adapter = adapter12
                        saf_contact_person_row.sp_cfblbuilding_nm.setSelection(buildPosition)
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
      if(parent?.id == R.id.sp_cafcompany){
            safContactLayout.et_cafcmpny.setText(company?.get(position))
            str_cmp =  companyId?.get(position )
            safContactLayout.et_cafgrp.setText(group?.get(position))
            str_grp = groupId?.get(position )
        }else if(parent?.id == R.id.sp_cafgroup){
            safContactLayout.et_cafgrp.setText(group?.get(position))
            str_grp = groupId?.get(position )
        } else if(parent?.id == R.id.sp_caffirm_type){
            layoutSafCompany_details.et_caffirm_type.setText(resources.getStringArray(R.array.list_firm_type)[position])
            str_firmtype = resources.getStringArray(R.array.list_firm_type_value)[position]
        }else if(parent?.id == R.id.sp_cafstate){
             layoutSafInstallAddress.et_cafstate.setText(resources.getStringArray(R.array.list_of_state)[position])
             str_statename = resources.getStringArray(R.array.list_of_state)[position]
             str_inststateId= resources.getStringArray(R.array.list_state_code)[position]
             getInstallCity(str_inststateId.toString())
        }else if(parent?.id == R.id.sp_cafcity){
            layoutSafInstallAddress.et_add_cafcity.setText(city?.get(position))
            str_city = city?.get(position).toString()
            str_city_code =  cityCode?.get(position)
            getInstallArea(str_city, str_city_code)
        } else if(parent?.id == R.id.sp_cafcnarea){
            layoutSafInstallAddress.et_cafinstallarea.setText(area?.get(position))
            str_add_area = areaCode?.get(position)
            val cntareaname = area?.get(position).toString()
            getInstallBuilding(cntareaname,str_add_area)
        } else if(parent?.id == R.id. sp_cafbuilding_nm){
            layoutSafInstallAddress.et_cafbuildingname.setText(building?.get(position))
            str_inst_building_nm =  buildingCode?.get(position )
          //  val buildingname = building?.get(position)
        }else if (parent?.id == R.id.sp_cafsbbus){
            safContactLayout.et_sb_bs_sgmnt.setText(resources.getStringArray(R.array.list_of_subBusSegment)[position])
            str_sub_bus = resources.getStringArray(R.array.list_of_subBusSegment)[position]
        }else if (parent?.id == R.id.sp_bnknam){
            layoutPayment.et_bnknm.setText(bankname[position])
            str_bankid = bankid[position]
        }
        else if(parent?.id == R.id.sp_cfblstate){
            saf_contact_person_row.et_cfblstate.setText(resources.getStringArray(R.array.list_of_state)[position])
            str_blstatename = resources.getStringArray(R.array.list_of_state)[position]
            str_blinststateId= resources.getStringArray(R.array.list_state_code)[position]
            getBillingCity(str_blinststateId)
        }else if(parent?.id == R.id.sp_cfblcity){
            saf_contact_person_row.et_cfblcity.setText(billingcity?.get(position))
            str_blcity = billingcity?.get(position).toString()
            str_blcity_code =  billingcityCode?.get(position)
         //   getBillingArea(str_blcity, str_blcity_code)
            getBillingArea(str_blcity, str_blcity_code)
        } else if(parent?.id == R.id.sp_cfblcnarea){
            saf_contact_person_row.et_cfblarea.setText(billingarea?.get(position))
            str_bladd_area = billingareaCode?.get(position)
            val cntareaname = billingarea?.get(position).toString()
            getBillingBuilding(cntareaname,str_bladd_area)
        } else if(parent?.id == R.id. sp_cfblbuilding_nm){
            saf_contact_person_row.et_cfblbuilding.setText(billingbuilding?.get(position))
            str_blinst_building_nm =  billingbuildingCode?.get(position)
        }else if(parent?.id == R.id.spSafAuthState){
            binding.layoutSafOtherDetails.etSafAuthState.setText(resources.getStringArray(R.array.list_of_state)[position])
            str_atstatename = resources.getStringArray(R.array.list_of_state)[position]
            str_atinststateId= resources.getStringArray(R.array.list_state_code)[position]
            if(str_blstatename!="Select State"|| layoutSafOtherDetails.etSafAuthState.text?.isNotEmpty() == true) {
                getAuthorizedCity(str_atinststateId.toString())
            }
        }else if(parent?.id == R.id.spSafAuthCity){
            binding.layoutSafOtherDetails.etSafAuthCity.setText(authocity?.get(position))
            str_atcity = authocity?.get(position).toString()
            str_atcity_code =  authocityCode?.get(position )
        }else if(parent?.id == R.id.sp_wrkng_days){
           safContactLayout.et_wrkngdys.setText(resources.getStringArray(R.array.list_of_workingHours)[position])
            str_wrkngdays = resources.getStringArray(R.array.listMangedLinkVal)[position].toString()
        }else if (parent?.id == R.id.sp_cafbilltype) {
            layoutSafInstallAddress.et_cafbiltype.setText(resources.getStringArray(R.array.list_of_billtype)[position])
            str_billtype = resources.getStringArray(R.array.listCustomerServiceVal)[position]
        }else if (parent?.id == R.id.sp_securitytype) {
            layoutPayment.et_sctype.setText(resources.getStringArray(R.array.list_of_Deposit)[position])
            str_sctype = resources.getStringArray(R.array.list_of_boolean_values)[position]
        }else if(parent?.id ==R.id.sp_vertical){
            binding.safContactLayout.etVertical.setText(vertical?.get(position))
            strVertical = verticalId?.get(position)
      }
      else if (parent?.id == R.id.sp_gst) {
            binding.layoutPayment.etGst.setText(resources.getStringArray(R.array.list_of_gst)[position])
            str_gstval = resources.getStringArray(R.array.listDNCVal)[position]
            if(str_gstval=="569480000"){
                layoutPayment.et_gsttnum.visibility=View.VISIBLE
            }else{
                layoutPayment.et_gsttnum.visibility=View.GONE
            }
        }
        else if (parent?.id == R.id.sp_payslip) {
           layoutPayment.et_payslip.setText(resources.getStringArray(R.array.list_of_payslip)[position])
            str_payslip = resources.getStringArray(R.array.list_of_payslipval)[position]
            when (resources.getStringArray(R.array.list_of_payslip)[position]) {
              "Select Option" ->{
                  layoutPayment.frbnk.visibility=View.GONE
                  layoutPayment.et_brnchname.visibility=View.GONE
                  layoutPayment.et_checkkdate.visibility=View.GONE
                  layoutPayment.et_chknumber.visibility=View.GONE
                  layoutPayment.et_card4dgt.visibility=View.GONE
                  layoutPayment.et_transactionid.visibility=View.GONE
                  layoutPayment.et_approvalcode.visibility=View.GONE
                  layoutPayment.et_paymentdate.visibility=View.GONE
                  layoutPayment.et_debit4dgt.visibility=View.GONE
              }
              "RTGS" -> {
                  layoutPayment.et_transactionid.visibility=View.VISIBLE
                  layoutPayment.et_approvalcode.visibility=View.GONE
                  layoutPayment.et_paymentdate.visibility=View.VISIBLE
                  layoutPayment.frbnk.visibility=View.GONE
                  layoutPayment.et_brnchname.visibility=View.GONE
                  layoutPayment.et_checkkdate.visibility=View.GONE
                  layoutPayment.et_chknumber.visibility=View.GONE
                  layoutPayment.et_card4dgt.visibility=View.GONE
                  layoutPayment.et_debit4dgt.visibility=View.GONE
              }
              "Cheque" -> {

                  layoutPayment.et_transactionid.visibility=View.GONE
                  layoutPayment.et_card4dgt.visibility=View.GONE
                  layoutPayment.frbnk.visibility=View.VISIBLE
                  layoutPayment.et_brnchname.visibility=View.VISIBLE
                  layoutPayment.et_checkkdate.visibility=View.VISIBLE
                  layoutPayment.et_chknumber.visibility=View.VISIBLE
                  layoutPayment.et_approvalcode.visibility=View.GONE
                  layoutPayment.et_paymentdate.visibility=View.GONE
                  layoutPayment.et_debit4dgt.visibility=View.GONE
              }
              "Demand draft" -> {
                  layoutPayment.et_transactionid.visibility=View.GONE
                  layoutPayment.frbnk.visibility=View.VISIBLE
                  layoutPayment.et_brnchname.visibility=View.VISIBLE
                  layoutPayment.et_checkkdate.visibility=View.VISIBLE
                  layoutPayment.et_chknumber.visibility=View.VISIBLE
                  layoutPayment.et_card4dgt.visibility=View.GONE
                  layoutPayment.et_approvalcode.visibility=View.GONE
                  layoutPayment.et_paymentdate.visibility=View.GONE
                  layoutPayment.et_debit4dgt.visibility=View.GONE
              }
              "Credit Card" -> {
                  layoutPayment.et_approvalcode.visibility=View.VISIBLE
                  layoutPayment.et_debit4dgt.visibility=View.VISIBLE
                  layoutPayment.et_paymentdate.visibility=View.VISIBLE
                  layoutPayment.frbnk.visibility=View.GONE
                  layoutPayment.et_brnchname.visibility=View.GONE
                  layoutPayment.et_checkkdate.visibility=View.GONE
                  layoutPayment.et_card4dgt.visibility=View.GONE
                  layoutPayment.et_chknumber.visibility=View.GONE
                  layoutPayment.et_transactionid.visibility=View.GONE
              }
              "NEFT" -> {
                  layoutPayment.et_transactionid.visibility=View.VISIBLE
                  layoutPayment.et_approvalcode.visibility=View.GONE
                  layoutPayment.et_paymentdate.visibility=View.VISIBLE
                  layoutPayment.et_brnchname.visibility=View.GONE
                  layoutPayment.et_checkkdate.visibility=View.GONE
                  layoutPayment.et_chknumber.visibility=View.GONE
                  layoutPayment.et_card4dgt.visibility=View.GONE
                  layoutPayment.et_debit4dgt.visibility=View.GONE
                  layoutPayment.frbnk.visibility=View.GONE
              }
              "Debit Card" -> {
                  layoutPayment.frbnk.visibility=View.GONE
                  layoutPayment.et_brnchname.visibility=View.GONE
                  layoutPayment.et_checkkdate.visibility=View.GONE
                  layoutPayment.et_chknumber.visibility=View.GONE
                  layoutPayment.et_approvalcode.visibility=View.VISIBLE
                  layoutPayment.et_card4dgt.visibility=View.VISIBLE
                  layoutPayment.et_paymentdate.visibility=View.VISIBLE
                  layoutPayment.et_transactionid.visibility=View.GONE
                  layoutPayment.et_debit4dgt.visibility=View.GONE
              }
              "Ezetap" -> {
                  layoutPayment.frbnk.visibility=View.GONE
                  layoutPayment.et_brnchname.visibility=View.GONE
                  layoutPayment.et_checkkdate.visibility=View.GONE
                  layoutPayment.et_chknumber.visibility=View.GONE
                  layoutPayment.et_card4dgt.visibility=View.GONE
                  layoutPayment.et_transactionid.visibility=View.VISIBLE
                  layoutPayment.et_approvalcode.visibility=View.GONE
                  layoutPayment.et_debit4dgt.visibility=View.GONE
                  layoutPayment.et_paymentdate.visibility=View.VISIBLE
              }
              "Ezetap-Cheque" -> {
                  layoutPayment.frbnk.visibility=View.VISIBLE
                  layoutPayment.et_brnchname.visibility=View.VISIBLE
                  layoutPayment.et_checkkdate.visibility=View.VISIBLE
                  layoutPayment.et_chknumber.visibility=View.VISIBLE
                  layoutPayment.et_card4dgt.visibility=View.GONE
                  layoutPayment.et_transactionid.visibility=View.GONE
                  layoutPayment.et_approvalcode.visibility=View.GONE
                  layoutPayment.et_debit4dgt.visibility=View.GONE
                  layoutPayment.et_paymentdate.visibility=View.GONE
              }
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
            Intent(this, SafTabActivity::class.java).also {
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
