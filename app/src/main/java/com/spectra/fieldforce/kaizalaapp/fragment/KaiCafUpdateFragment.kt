package com.spectra.fieldforce.kaizalaapp.fragment


import android.annotation.SuppressLint
import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.spectra.fieldforce.R
import com.spectra.fieldforce.api.ApiClient
import com.spectra.fieldforce.api.ApiClientKaizala
import com.spectra.fieldforce.api.ApiInterface
import com.spectra.fieldforce.databinding.KaiFragCafBinding
import com.spectra.fieldforce.kaizalaapp.activity.KaizalaCafTabActivity
import com.spectra.fieldforce.kaizalaapp.model.*
import com.spectra.fieldforce.kaizalaapp.model.DocumentData
import com.spectra.fieldforce.kaizalaapp.model.DocumentRequired
import com.spectra.fieldforce.kaizalaapp.model.PaymentDetail
import com.spectra.fieldforce.salesapp.adapter.DocAdapter
import com.spectra.fieldforce.salesapp.model.*
import com.spectra.fieldforce.utils.*
import kotlinx.android.synthetic.main.caf_demo_fragment.*
import kotlinx.android.synthetic.main.caf_payment_details_row.view.*
import kotlinx.android.synthetic.main.fragment_all_lead_list.*
import kotlinx.android.synthetic.main.kai_create_lead.*
import kotlinx.android.synthetic.main.kai_frag_caf.*
import kotlinx.android.synthetic.main.kai_frag_contact.*
import kotlinx.android.synthetic.main.update_leadtoolbar.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import java.util.regex.Pattern

class KaiCafUpdateFragment:Fragment(), View.OnClickListener{

    private lateinit var  binding: KaiFragCafBinding
    private lateinit var userName: String
    private lateinit var password:String
    private lateinit var strCafId:String
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
    private var bankList : ArrayList<BankData>? = null
    private var bankname = arrayListOf<String>()
    private var bankid = arrayListOf<String>()
    private var strBank :String? = null
    private var strArea:String? =null
    private var strRelationId :String? = null
    private var strSubBusiness:String? =null

    private var strBuilding:String? =null
    private  var strCity :String?=null
    private  var strSociety :String?=null
    private var strDepositType :String ?=null
    private var strPaySlip :String ?=null
    private var strSalutationId :String ? =null
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
    private var strChequeDate:String ? =null
    private lateinit var pdfUri: Uri
    private var strPaymentStatus :String ? =null
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
        val bundle = arguments
        strCafId = bundle?.getString("CafId").toString()
        btn_newLead.rl_back.setOnClickListener(this)
        btn_newLead.tv_lang.text=AppConstants.CAF
        btn_newLead.flr.visibility=View.GONE
        val sp1: SharedPreferences? = context?.getSharedPreferences("Login", 0)
        userName = sp1?.getString(AppConstants.USERNAME, null).toString()
        password = sp1?.getString(AppConstants.PASSWORD, null).toString()

        listener()
        buttonListener()
        getCafDetails()
        focus()
    }

    private fun validEmail(email: String): Boolean {
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }


    private fun getCafDetails () {
        inProgress()
        val kGetCafReq = KGetCafReq(Constants.KGETCAF,Constants.KAUTH,strCafId,password,userName)

        val apiService = ApiClientKaizala.getClient().create(ApiInterface::class.java)
        val call = apiService.getKCaf(kGetCafReq)
        call.enqueue(object : Callback<KGetCafResp?> {
            override fun onResponse(call: Call<KGetCafResp?>, response: Response<KGetCafResp?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        if(response.body()?.StatusCode==200) {
                            binding.info = response.body()?.Response
                            outProgress()
                            strCity = response.body()?.Response?.accountDetail?.City.toString()
                            strArea = response.body()?.Response?.accountDetail?.Area.toString()
                            strSociety = response.body()?.Response?.accountDetail?.Society.toString()
                            strCompanyId = response.body()?.Response?.accountDetail?.CompanyId.toString()
                            strGroupId = response.body()?.Response?.accountDetail?.GroupId.toString()
                            strRelationId = response.body()?.Response?.accountDetail?.RelationshipId.toString()
                            strSubBusiness = response.body()?.Response?.accountDetail?.SubBusinessSegment.toString()
                            strSalutationId = response.body()?.Response?.accountDetail?.SalutationId.toString()
                            strFirmId = response.body()?.Response?.documentRequired?.FirmType.toString()
                            strPaySlip = response.body()?.Response?.paymentDetail?.PayInSlip.toString()
                            strPaymentStatus = response.body()?.Response?.paymentDetail?.PaymentStatus.toString()
                            strDepositType = response.body()?.Response?.paymentDetail?.SecurityDepositType.toString()
                            strGstId = response.body()?.Response?.cafDetail?.IsGST.toString()
                            strBank = response.body()?.Response?.paymentDetail?.BankName.toString()
                            getCompany()
                            getCity("")
                            var subBusPosition = 0
                            KaizalaAppConstant.subBus.forEachIndexed { index, s ->
                                if (s == strSubBusiness) subBusPosition = index
                            }
                            val subBusAdapter =
                                context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item,KaizalaAppConstant.subBus) }
                            subBusAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            spinner3.adapter = subBusAdapter
                            spinner3.setSelection(subBusPosition)

                            var salutationPosition = 0
                            SalesAppConstants.list_of_salutationId.forEachIndexed { index, s ->
                                if (s == strSalutationId) salutationPosition = index
                            }
                            val salutationAdapter =
                                context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, SalesAppConstants.list_of_salutation) }
                            salutationAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            spnr_salutation.adapter = salutationAdapter
                            spnr_salutation.setSelection(salutationPosition)

                            var firmPosition = 0
                            SalesAppConstants.list_firm_type_value.forEachIndexed { index, s ->
                                if (s == strFirmId) firmPosition = index
                            }
                            val firmtypeAdapter =
                                context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item,resources.getStringArray(R.array.list_firm_type)) }
                            firmtypeAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            Spinner_firmType.adapter = firmtypeAdapter
                            Spinner_firmType.setSelection(firmPosition)

                            var gstPosition = 0
                            resources.getStringArray(R.array.listDNCVal).forEachIndexed { index, s ->
                                if (s == strPaySlip) gstPosition = index
                            }
                            val gstAdapter =
                                context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item,resources.getStringArray(R.array.list_of_gst)) }
                            gstAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            Spinner_GST.adapter = gstAdapter
                            Spinner_GST.setSelection(gstPosition)

                            var depositPosition = 0
                            resources.getStringArray(R.array.listDNCVal).forEachIndexed { index, s ->
                                if (s == strDepositType) depositPosition = index
                            }
                            val securityAdapter =
                                context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item,resources.getStringArray(R.array.list_of_Deposit)) }
                            securityAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            Spinner_DepositType.adapter = securityAdapter
                            Spinner_DepositType.setSelection(depositPosition)

                            var paySlipPosition = 0
                            resources.getStringArray(R.array.list_of_payslipval).forEachIndexed { index, s ->
                                if (s == strPaySlip) paySlipPosition = index
                            }

                            val paySlipAdapter =
                                context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item,resources.getStringArray(R.array.list_of_payslip)) }
                            paySlipAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            Spinner_Pay.adapter = paySlipAdapter
                            Spinner_Pay.setSelection(paySlipPosition)

                            var paymentPosition = 0
                            KaizalaAppConstant.paymentStatusId.forEachIndexed { index, s ->
                                if (s == strPaymentStatus) paymentPosition = index
                            }
                            val paymentStatusAdapter =
                                context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, KaizalaAppConstant.paymentStatus) }
                            paymentStatusAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            Spinner_PaymentStatus.adapter = paymentStatusAdapter
                            Spinner_PaymentStatus.setSelection(paymentPosition)
                            Spinner_PaymentStatus.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                                    val itemPosition = adapterView.selectedItemPosition
                                    strPaymentStatus = KaizalaAppConstant.paymentStatusId[itemPosition].toString()
                                }
                                override fun onNothingSelected(parent: AdapterView<*>?) {

                                }
                            }

                            Calender()
                            getBankList()

                            if(response.body()?.Response?.documentRequired?.Votercard==true){
                                voterCard.isChecked = true
                            }
                            if(response.body()?.Response?.documentRequired?.DrivingLicense==true){
                                binding.DrivingLic.isChecked= true
                            }
                            if(response.body()?.Response?.documentRequired?.Passport==true){
                                binding.passport.isChecked= true
                            }
                            if(response.body()?.Response?.documentRequired?.RentAgreement==true){
                                binding.rent.isChecked= true
                            }
                            if(response.body()?.Response?.documentRequired?.TelephoneBill==true){
                                binding.TelephoneBill.isChecked= true
                            }
                            if(response.body()?.Response?.documentRequired?.ElectricityBill==true){
                                binding.ElectricityBill.isChecked= true
                            }
                            if(response.body()?.Response?.documentRequired?.WaterBill==true){
                                binding.waterBill.isChecked= true
                            }
                            if(response.body()?.Response?.documentRequired?.AadharCard==true){
                                binding.AadharCard.isChecked= true
                            }

                            if(response.body()?.Response?.documentRequired?.GasConnection==true){
                                binding.GasConnec.isChecked= true
                            }
                            if(response.body()?.Response?.documentRequired?.CreditCardStatement==true){
                                binding.CreditCard.isChecked= true
                            }
                            if(response.body()?.Response?.documentRequired?.CentralStateGovtId==true){
                                binding.Centrestate.isChecked= true
                            }
                            if(response.body()?.Response?.documentRequired?.BankPassbook==true){
                                binding.BankPass.isChecked= true
                            }
                            if(response.body()?.Response?.documentRequired?.RationCardPhotoId==true){
                                binding.rationCard.isChecked= true
                            }
                            if(response.body()?.Response?.documentRequired?.CentralStateGovtId==true){
                                binding.censtatephoto.isChecked= true
                            }
                            if(response.body()?.Response?.documentRequired?.PanCardPhotoId==true){
                                binding.panCard.isChecked= true
                            }
                            if(response.body()?.Response?.documentRequired?.VotercardPhotoId==true){
                                binding.Vcradphoto.isChecked= true
                            }
                            if(response.body()?.Response?.documentRequired?.AadharCardPhotoId==true){
                                binding.ACphoto.isChecked= true
                            }
                            if(response.body()?.Response?.documentRequired?.DrivingLicensePhotoId==true){
                                binding.DLphoto.isChecked= true
                            }
                            if(response.body()?.Response?.documentRequired?.PassportPhotoId==true){
                                binding.PPid.isChecked= true
                            }
                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<KGetCafResp?>, t: Throwable) {
                binding.contactProgressLayout.progressOverlay.visibility = View.GONE

                Log.e("ContactRetroError", t.toString())
            }
        })
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
                updateCaf(
                    fName,
                    lName,
                    mobile,
                    email,
                    subBus,
                    floor,
                    pinCode,
                    block,
                    payDate,
                    amount,
                    tan,
                    pan,
                    gstNum,
                    otc,
                    transactionIdRequest
                )
            }
        }
    }

    private fun focus(){
        val regexGST = """/^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[0-9,A-Z]{3}${'$'}/""".toRegex()
        val regexPAN = """/^([A-Z]){5}([0-9]){4}([A-Z]){1}?${'$'}/""".toRegex()
        val regexTAN = """ /^([A-Z]){4}([0-9]){5}([A-Z]){1}?${'$'}/""".toRegex()
        Spinner_GSTno.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus)
            {
                val gst =  Spinner_GSTno.text.toString()
                val isValid= regexGST.matches(gst)
                if (!isValid) {
                    Toast.makeText(context, "Please Enter GST in CAPITAL Letters and correct format (12ABCDE1234A1A1 or 12ABCDE1234A1AA)", Toast.LENGTH_LONG).show()
                }
            }
        }
        EditText_PanNo.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus)
            {
                val gst =  EditText_PanNo.text.toString()
                val isValid= regexPAN.matches(gst)
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
                    Toast.makeText(context, "Please Enter TAN in CAPITAL Letters and correct format (ABCD12345A)",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }



    private fun updateCaf(
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
        otc:String,
        transId:String?
    ) {
      val accountDetail =  AccountDetail( strCompanyId,strGroupId,strRelation,strArea,"a",
          KaizalaAppConstant.HOME, strCity,"10001",email, fName,
          floor,lName,mobile,mobile,
          pinCode,strProductId,strSalutationId,strSociety,"",subBus)

     val paymentDetail = PaymentDetail(amount,"",strBank,"","",
         "","","",otc,strPaySlip,strDate,
         strPaymentStatus, "", "",transId)

     val  cafDetail = Cafdetail(strCafId,strCompanyId,strGroupId,pan,strRelation,tan,
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

     val kcreateCafReq = KCreateCafReq(accountDetail,Constants.KUPDATECAF,Constants.KAUTH,paymentDetail,
         cafDetail, customerImage, documentRequired, strCafId,"",password,userName)

        val apiService = ApiClientKaizala.getClient().create(ApiInterface::class.java)
        val call = apiService.updateKaiCaf(kcreateCafReq)
        call.enqueue(object : Callback<KCafUpdateRes?> {
            override fun onResponse(
                call: Call<KCafUpdateRes?>,
                response: Response<KCafUpdateRes?>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    if(response.body()?.StatusCode=="200") {
                        try {
                            Toast.makeText(context, response.body()?.Response?.StatusName, Toast.LENGTH_LONG)
                                .show()
                            val intent = Intent (context, KaizalaCafTabActivity::class.java)
                            startActivity(intent)

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }else{
                        Toast.makeText(context, response.body()?.Response?.StatusName, Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }

            override fun onFailure(call: Call<KCafUpdateRes?>, t: Throwable) {
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
                            if(s==strBank)bankPosition=index
                            return@forEachIndexed
                        }
                        val adapter12 = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, bankname) }
                        adapter12?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        Spinner_BnkName.adapter = adapter12
                        Spinner_BnkName.setSelection(bankPosition)
                        adapter12?.notifyDataSetChanged()
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

    private fun selectPdf() {
        val pdfIntent = Intent(Intent.ACTION_GET_CONTENT)
        pdfIntent.type = "application/pdf"
        pdfIntent.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(pdfIntent, 12)
    }
    private fun selectImage() {
        val choice = arrayOf<CharSequence>("Take Photo", "Choose from Gallery","Attach Document", "Cancel")
        val myAlertDialog: AlertDialog.Builder? = context?.let { AlertDialog.Builder(it) }
        myAlertDialog?.setTitle("Select Image")
        myAlertDialog?.setItems(choice) { _, item ->
            when {
                choice[item] == "Choose from Gallery" -> {
                    val gallery =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                    startActivityForResult(gallery, 1)
                    /*  val pickFromGallery = Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                      pickFromGallery.type = "/image"
                      startActivityForResult(pickFromGallery, 1)*/
                }
                choice[item] == "Take Photo" -> {
                    val cameraPicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(cameraPicture, 0)
                }
                choice[item] == "Attach Document" -> {
                    selectPdf()
                }
                // Select "Cancel" to cancel the task
                choice[item] == "Cancel" -> {
                    myAlertDialog.setCancelable(true)
                }
            }
        }
        myAlertDialog?.show()
    }


    private fun listener(){

        IMVAttachPhoto.setOnClickListener {
            val cameraPicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraPicture, 0)
        }

        attachDoc.setOnClickListener {
            selectImage()
        }

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


        val subBusAdapter =
            context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item,KaizalaAppConstant.subBus) }
        subBusAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        spinner3.adapter = subBusAdapter


        Spinner_firmType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                val itemPosition = adapterView.selectedItemPosition
                strFirmId = SalesAppConstants.list_firm_type_value[itemPosition]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }



        Spinner_DepositType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                val itemPosition = adapterView.selectedItemPosition
                strDepositType = resources.getStringArray(R.array.list_of_boolean_values)[itemPosition]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }



        Spinner_Pay.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                val itemPosition = adapterView.selectedItemPosition
                strPaySlip=resources.getStringArray(R.array.list_of_payslipval)[itemPosition]
                if(strPaySlip=="5"||strPaySlip=="4"||strPaySlip=="11"){
                    TextView_DDNumber.visibility=View.VISIBLE
                    EditText_DD.visibility=View.VISIBLE
                    TextView_DDate.visibility=View.VISIBLE
                    Spinner_DDate.visibility=View.VISIBLE

                    tv_BankName.visibility=View.GONE
                    Spinner_BnkName.visibility=View.GONE
                    tv_BranchName.visibility=View.GONE
                    Spinner_BrnchName.visibility=View.GONE
                    tv_CCHQNO.visibility=View.GONE
                    Et_ChequeDDno.visibility=View.GONE
                    tv_CCHQDate.visibility=View.GONE
                    Tv_ChequeDate.visibility=View.GONE
                    tv_carddigit.visibility=View.GONE
                    et_carddigit.visibility=View.GONE
                    tv_approvalCode.visibility=View.GONE
                    et_approvalCode.visibility=View.GONE
                    TextView_DDNumber.visibility=View.GONE
                    EditText_DD.visibility=View.GONE
                }else if(strPaySlip=="1"||strPaySlip=="12"||strPaySlip=="2"){
                    tv_BankName.visibility=View.VISIBLE
                    Spinner_BnkName.visibility=View.VISIBLE
                    tv_BranchName.visibility=View.VISIBLE
                    Spinner_BrnchName.visibility=View.VISIBLE
                    tv_CCHQNO.visibility=View.VISIBLE
                    Et_ChequeDDno.visibility=View.VISIBLE
                    tv_CCHQDate.visibility=View.VISIBLE
                    Tv_ChequeDate.visibility=View.VISIBLE

                    TextView_DDNumber.visibility=View.GONE
                    EditText_DD.visibility=View.GONE
                    TextView_DDate.visibility=View.GONE
                    Spinner_DDate.visibility=View.GONE
                    tv_carddigit.visibility=View.GONE
                    et_carddigit.visibility=View.GONE
                    tv_approvalCode.visibility=View.GONE
                    et_approvalCode.visibility=View.GONE
                    TextView_DDNumber.visibility=View.GONE
                    EditText_DD.visibility=View.GONE
                }else if(strPaySlip=="7"||strPaySlip=="8"){
                    tv_carddigit.visibility=View.VISIBLE
                    et_carddigit.visibility=View.VISIBLE
                    tv_approvalCode.visibility=View.VISIBLE
                    et_approvalCode.visibility=View.VISIBLE
                    TextView_DDNumber.visibility=View.VISIBLE
                    EditText_DD.visibility=View.VISIBLE

                    tv_BankName.visibility=View.GONE
                    Spinner_BnkName.visibility=View.GONE
                    tv_BranchName.visibility=View.GONE
                    Spinner_BrnchName.visibility=View.GONE
                    tv_CCHQNO.visibility=View.GONE
                    Et_ChequeDDno.visibility=View.GONE
                    tv_CCHQDate.visibility=View.GONE
                    Tv_ChequeDate.visibility=View.GONE
                    tv_BankName.visibility=View.GONE
                    Spinner_BnkName.visibility=View.GONE
                    tv_BranchName.visibility=View.GONE
                    Spinner_BrnchName.visibility=View.GONE
                    tv_CCHQNO.visibility=View.GONE
                    Et_ChequeDDno.visibility=View.GONE
                    tv_CCHQDate.visibility=View.GONE
                    Tv_ChequeDate.visibility=View.GONE

                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        Spinner_GST.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                val itemPosition = adapterView.selectedItemPosition
                strGstId =  resources.getStringArray(R.array.listDNCVal)[itemPosition]
                if(strGstId=="569480000"){
                    TextView_GSTno.visibility = View.VISIBLE
                    Spinner_GSTno.visibility = View.VISIBLE
                }else{
                    TextView_GSTno.visibility = View.GONE
                    Spinner_GSTno.visibility = View.GONE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

    }

    @SuppressLint("Range")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
            name = ArrayList<DocumentData>();
        // For loading Image
        if (resultCode != RESULT_CANCELED) {
            when (requestCode) {
                0 -> if (resultCode == RESULT_OK && data != null) {
                    val imageSelected = data.extras!!["data"] as Bitmap?
                    IMVAttachPhoto.setImageBitmap(imageSelected)
                    attachDoc.text = imageSelected.toString()
                    data_image = DocumentData(imageSelected.toString(), ".jpg")
                    name?.add(data_image!!)
                }
                1 -> if (resultCode == RESULT_OK && data != null) {

                       /* val imageSelected = data.data
                        IMVAttachPhoto.setImageURI(imageSelected)*/

                    val imageSelected = data.data
                    val pathColumn = arrayOf(MediaStore.Images.Media.DATA)
                    if (imageSelected != null) {
                        val myCursor = context?.contentResolver?.query(
                            imageSelected,
                            pathColumn, null, null, null
                        )
                        if (myCursor != null) {
                            myCursor.moveToFirst()
                            val columnIndex = myCursor.getColumnIndex(pathColumn[0])
                            val picturePath = myCursor.getString(columnIndex)
                            IMVAttachPhoto.setImageBitmap(BitmapFactory.decodeFile(picturePath))
                            myCursor.close()
                        }
                        attachDoc.text = imageSelected.toString()
                        data_image = DocumentData(imageSelected.toString(), ".jpg")
                        name?.add(data_image!!)
                    }
                }
            }
        }

        // For loading PDF
        when (requestCode) {
            12 -> if (resultCode == RESULT_OK) {

                pdfUri = data?.data!!
                val uri: Uri = data.data!!
                val uriString: String = uri.toString()
                var pdfName: String? = null
                if (uriString.startsWith("content://")) {
                    var myCursor: Cursor? = null
                    try {
                        myCursor =
                            context?.contentResolver?.query(uri, null, null, null, null)
                        if (myCursor != null && myCursor.moveToFirst()) {
                            pdfName =
                                myCursor.getString(myCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                            attachDoc.text = pdfName
                            data_image = DocumentData(pdfName.toString(), ".jpg")
                            name?.add(data_image!!)
                        }
                    } finally {
                        myCursor?.close()
                    }
                }
            }
        }
      //  setAdapter(context,name)
    }


  /*  private fun setAdapter(context: Context?,name: ArrayList<DocumentData>) {
        RVAttachDoc?.apply {
            binding.RVAttachDoc.setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = DocAdapter(context, name)
        }
    }*/


    /*  private fun dispatchTakePictureIntent() {
          val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
          try {
              startActivityForResult(takePictureIntent, AppConstants.PERMISSION_REQUEST_CODE)
          } catch (e: ActivityNotFoundException) {
              // display error state to the user
          }

      }

      override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
          super.onActivityResult(requestCode, resultCode, data)
          if (requestCode == AppConstants.PERMISSION_REQUEST_CODE && resultCode == RESULT_OK) {
              val imageBitmap = data?.extras?.get("data") as Bitmap
              binding.IMVAttachPhoto.setImageBitmap(imageBitmap)
          }
      }*/

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

                        var companyPosition = 0
                        companyId.forEachIndexed { index, s ->
                            if (s == strCompanyId) companyPosition = index
                        }

                        var groupPosition = 0
                        groupId.forEachIndexed { index, s ->
                            if (s == strGroupId) groupPosition = index
                        }

                        val adapter12 = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, company) }
                        adapter12?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        Srch_Company.adapter = adapter12
                        Srch_Company.setSelection(companyPosition)

                        val adapter1 = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, group) }
                        adapter1?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        searchViewGroup.adapter = adapter1
                        searchViewGroup.setSelection(groupPosition)

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

                        var relationPosition = 0
                        relationId.forEachIndexed { index, s ->
                            if (s == strRelationId) relationPosition = index
                        }


                        val adapter12 = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, relation) }
                        adapter12?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        Spinner_relation.adapter = adapter12
                        Spinner_relation.setSelection(relationPosition)
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
                            var buildingPosition = 0
                            buildingId.forEachIndexed { index, s ->
                                if (s == strBuilding) buildingPosition = index
                            }

                            val adapter121 = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, building) }
                            adapter121?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            Srch_Building.adapter = adapter121
                            Srch_Building.setSelection(buildingPosition)
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

            Tv_ChequeDate.setOnClickListener {
                val dpd = DatePickerDialog(requireContext(), { _, year, monthOfYear, dayOfMonth ->
                    val mnth = monthOfYear + 1
                    Tv_ChequeDate.text=("$dayOfMonth-$mnth-$year")
                    val trgt = Tv_ChequeDate.text.toString()
                    val split = trgt.split("-")
                    val dateee = split[0]
                    val month1 = split[1]
                    val year1 = split[2]
                    strChequeDate = ("$year1-$month1-$dateee")
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

                            var productPosition = 0
                            productId.forEachIndexed { index, s ->
                                if (s == strProductId) productPosition = index
                            }
                            val adapter121 = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, product) }
                            adapter121?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            Search_Product.adapter = adapter121
                            Search_Product.setSelection(productPosition)
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