package com.spectra.fieldforce.salesapp.activity

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.spectra.fieldforce.R
import com.spectra.fieldforce.api.ApiClient
import com.spectra.fieldforce.api.ApiInterface
import com.spectra.fieldforce.databinding.CafDemoFragmentBinding
import com.spectra.fieldforce.salesapp.fragment.GetAllCAFFrag
import com.spectra.fieldforce.salesapp.model.*
import com.spectra.fieldforce.utils.AppConstants
import com.spectra.fieldforce.utils.Constants
import kotlinx.android.synthetic.main.caf_authorised_details.view.*
import kotlinx.android.synthetic.main.caf_company_details_row.view.*
import kotlinx.android.synthetic.main.caf_contact_info.view.*
import kotlinx.android.synthetic.main.caf_contact_info.view.et_sb_bs_sgmnt
import kotlinx.android.synthetic.main.caf_contact_person_row.*
import kotlinx.android.synthetic.main.caf_contact_person_row.view.*
import kotlinx.android.synthetic.main.caf_demo_fragment.*
import kotlinx.android.synthetic.main.caf_demo_fragment.linadd
import kotlinx.android.synthetic.main.caf_demo_fragment.linear_companydetails
import kotlinx.android.synthetic.main.caf_demo_fragment.linear_contact_person_address
import kotlinx.android.synthetic.main.caf_demo_fragment.linearcontactinfo
import kotlinx.android.synthetic.main.caf_demo_fragment.lineareight
import kotlinx.android.synthetic.main.caf_demo_fragment.linearfive
import kotlinx.android.synthetic.main.caf_demo_fragment.linearfouraddres
import kotlinx.android.synthetic.main.caf_demo_fragment.linearnine
import kotlinx.android.synthetic.main.caf_demo_fragment.linearother_details
import kotlinx.android.synthetic.main.caf_demo_fragment.linearsix
import kotlinx.android.synthetic.main.caf_demo_fragment.linearthree
import kotlinx.android.synthetic.main.caf_demo_fragment.lineartwo
import kotlinx.android.synthetic.main.caf_demo_fragment.view.*
import kotlinx.android.synthetic.main.caf_installation_address_row.view.*
import kotlinx.android.synthetic.main.caf_installation_address_row.view.et_add_cafcity
import kotlinx.android.synthetic.main.caf_installation_address_row.view.et_cafstate
import kotlinx.android.synthetic.main.caf_installation_address_row.view.sp_cafcity
import kotlinx.android.synthetic.main.caf_installation_address_row.view.sp_cafstate
import kotlinx.android.synthetic.main.caf_otherinfo_row.view.*
import kotlinx.android.synthetic.main.caf_payment_details_row.view.*
import kotlinx.android.synthetic.main.caf_payment_details_row.view.et_appcode
import kotlinx.android.synthetic.main.caf_payment_details_row.view.et_bnknm
import kotlinx.android.synthetic.main.caf_payment_details_row.view.et_brnch
import kotlinx.android.synthetic.main.caf_payment_details_row.view.et_cardfrdgt
import kotlinx.android.synthetic.main.caf_payment_details_row.view.et_chkdate
import kotlinx.android.synthetic.main.caf_payment_details_row.view.et_chknum
import kotlinx.android.synthetic.main.caf_payment_details_row.view.et_creditfrdgt
import kotlinx.android.synthetic.main.caf_payment_details_row.view.et_pannum
import kotlinx.android.synthetic.main.caf_payment_details_row.view.et_paymntdt
import kotlinx.android.synthetic.main.caf_payment_details_row.view.et_payslip
import kotlinx.android.synthetic.main.caf_payment_details_row.view.et_scdeposit
import kotlinx.android.synthetic.main.caf_payment_details_row.view.et_sctype
import kotlinx.android.synthetic.main.caf_payment_details_row.view.et_tannum
import kotlinx.android.synthetic.main.caf_payment_details_row.view.et_totalamt
import kotlinx.android.synthetic.main.caf_payment_details_row.view.et_txtid
import kotlinx.android.synthetic.main.caf_payment_details_row.view.sp_payslip
import kotlinx.android.synthetic.main.caf_payment_details_row.view.sp_securitytype
import kotlinx.android.synthetic.main.lead_contact_info.view.*
import kotlinx.android.synthetic.main.lead_demo_fragment.*
import kotlinx.android.synthetic.main.lead_other_details_row.view.*
import kotlinx.android.synthetic.main.op_product_line_item_row.view.*
import kotlinx.android.synthetic.main.opp_add_doa.*
import kotlinx.android.synthetic.main.opp_company_details_row.*
import kotlinx.android.synthetic.main.opp_company_details_row.view.*
import kotlinx.android.synthetic.main.opp_other_info_row.*
import kotlinx.android.synthetic.main.opp_other_info_row.view.*
import kotlinx.android.synthetic.main.oppurtunity_contact_info_row.*
import kotlinx.android.synthetic.main.oppurtunity_contact_info_row.view.*
import kotlinx.android.synthetic.main.oppurtunity_demo_fragment.*
import kotlinx.android.synthetic.main.oppurtunity_detail_row.view.*
import kotlinx.android.synthetic.main.toolbar.view.*
import kotlinx.android.synthetic.main.update_lead_channel_source_row.view.*
import kotlinx.android.synthetic.main.update_lead_company_details_row.view.*
import kotlinx.android.synthetic.main.update_lead_demo_fragment.*
import kotlinx.android.synthetic.main.update_lead_installation_address_row.view.*
import kotlinx.android.synthetic.main.updatelead__contact_person_row.view.*
import kotlinx.android.synthetic.main.updatelead_contact_personadd_row.*
import kotlinx.android.synthetic.main.updatelead_contact_personadd_row.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


class CAFActivity:AppCompatActivity(),View.OnClickListener , AdapterView.OnItemSelectedListener {

    var strCafId : String? = null
    var strOppId :String?=null
    private var cityList : ArrayList<CityData>? = null
    private var city : ArrayList<String>? = null
    private var cityCode : ArrayList<String>? = null
    private var billingcity : ArrayList<String>? = null
    private var billingcityCode : ArrayList<String>? = null
    private var authocity : ArrayList<String>? = null
    private var authocityCode : ArrayList<String>? = null
    var strCity: String? = null
    var strProductId: String? = null
    var strBlCity:String?=null
    var strAthCity:String?=null
    var strArea: String? = null
    var strBuilding: String? = null
    var strBlArea: String? = null
    var strBlBuilding: String? = null
    private var strCustHappy: String? = null
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
    var cafpaydate:String? = null

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

        listener()
        itemListener()
        getCaf()

        getBankList()
        tv_create.setOnClickListener {
            createCaf()
        }
        tv_update.setOnClickListener {
            updateCaf()
        }
        tv_submit.setOnClickListener {
            submitCaf()
        }
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
            val intent = Intent(this, DocumentCafAct::class.java)
            val bundle = Bundle()
            bundle.putString("CafId", strCafId)
            bundle.putString("OppId", strOppId)
            intent.putExtras(bundle)
            startActivity(intent)
            finish()
        }
    }

    fun createCaf () {
        val strbusinessSegment =  caf_contactinfo_layout.et_cafbs_sgmnt.text
        val subbssegment = str_sub_bus
        val customername = caf_contactinfo_layout.et_cstname.text
        val phonenumber = caf_contact_person_row.et_cafphn_num.text
        val polock = layout_otherinfo.et_caflock.text
        val ponext = layout_otherinfo.et_cafnxt.text
        val authemail = layout_cafothr_details.et_cafemailid.text
        val authfather = layout_cafothr_details.et_fthr_hsb.text
        val authmobile = layout_cafothr_details.et_authomob.text
        val authname = layout_cafothr_details.et_cafname.text
        val authaddress = layout_cafothr_details.et_address.text
        val authpincode = layout_cafothr_details.et_cafauthpincode.text
        val billingplot = caf_contact_person_row.et_cfblbuildng_num.text
        val billingname = caf_contact_person_row.et_caf_cntname.text
        val billingemail = caf_contact_person_row.et_caf_bilngemailid.text
        val billingfloor = caf_contact_person_row.et_cfblfloor.text
        val billingphn =   caf_contact_person_row.et_cafphn_num.text
        val billingpincode = caf_contact_person_row.et_cfblpin_code.text
        val instemail = layout_cafinstal_address.et_cafemail.text
        val instmobile = layout_cafinstal_address.et_cafmbnum.text
        val instpin = layout_cafinstal_address.et_cafpin.text
        val amount = layout_payment.et_totalamt.text
        val approvalcode = layout_payment.et_appcode.text
       // val bnkname = layout_payment.et_bnknm.text
        val brnch = layout_payment.et_brnch.text
        val checkdd = layout_payment.et_chkdate.text
        val checknum = layout_payment.et_chknum.text
        val carddgts = layout_payment.et_cardfrdgt.text
        val paymntdt = layout_payment.et_paymntdt.text
        val txtty = layout_payment.et_txtid.text
        val srcdepst = layout_payment.et_scdeposit.text
        val creditcrd = layout_payment.et_creditfrdgt.text
        val pan = layout_payment.et_pannum.text
        val tan = layout_payment.et_tannum.text
        val gstnum = layout_payment.et_gstt.text
        var date1=""
        if(str_frwall=="1"){
            date1 = "2022-01-15"
        }

        val cafDetails = CafDetail(str_wrkngdays,strbusinessSegment?.toString(),strCafId,str_cmpnyself,
                str_customercategory, customername?.toString(),str_wrknghrs,str_provider, date1,
                str_frwall, gstnum?.toString(),str_gstval,str_ntwrk, pan?.toString(),
                phonenumber?.toString(), caflock, cafnext,
                str_PrfCom, subbssegment, tan?.toString(),str_voip)

        val authSigDetails = AuthorSigDetails(authemail?.toString(), authfather?.toString(),"0",
                authmobile?.toString(), authname?.toString(), authaddress?.toString(),str_atcity_code,
                "10001", authpincode?.toString(),str_atinststateId)

        val cafBillingAddress = CafBillingAddress(billingplot?.toString(),"",
                billingname?.toString(), billingemail?.toString(), billingfloor?.toString(),
                "0","0","0",
                str_bladd_area,str_blinst_building_nm,str_blcity_code,"10001", billingphn?.toString(),
                billingpincode?.toString(),str_blinststateId)

        val cafInstallationAddress = CafInstallationAddress(str_billtype,"0","0",
                "", instemail?.toString(),"0","0","0",
                instmobile?.toString(),
                "0", str_add_area,str_inst_building_nm,str_city_code,"10001", instpin?.toString(),
                str_inststateId, strProductId)

        val paymentDetail = PaymentDetail("", amount?.toString(), approvalcode?.toString(), str_bankid,
                brnch?.toString(), checkdd?.toString(), checknum?.toString(), creditcrd?.toString(),
                carddgts?.toString(),str_payslip,
                 cafpaydate,"569480002", srcdepst?.toString(),str_sctype,
                txtty?.toString())


        val createCafReqest = CreateCafReqest(Constants.CREATECAF, Constants.AUTH_KEY, authSigDetails,
                cafBillingAddress, cafDetails,str_cmp,str_grp,cafInstallationAddress,strOppId,
                "Target@2021#@",paymentDetail,str_rltn,"manager1")
        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.createCaf(createCafReqest)
        call.enqueue(object : Callback<CafDetailResponse?> {
            override fun onResponse(call: Call<CafDetailResponse?>, response: Response<CafDetailResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    val msg = response.body()?.Response?.Message
                    if(response.body()?.StatusCode=="200"){
                        Toast.makeText(this@CAFActivity, msg, Toast.LENGTH_SHORT).show()
                        val fragmentB = GetAllCAFFrag()
                        supportFragmentManager.beginTransaction()
                                .replace(R.id.fragment_caf, fragmentB, "fragmnetId")
                                .commit()
                    }else{
                        Toast.makeText(this@CAFActivity, msg, Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<CafDetailResponse?>, t: Throwable) {
                Log.e("RetroError", t.toString())
            }
        })
}

    fun updateCaf () {
        val strbusinessSegment =  caf_contactinfo_layout.et_cafbs_sgmnt.text
        val subbssegment = str_sub_bus
        val customername = caf_contactinfo_layout.et_cstname.text
        val phonenumber = caf_contact_person_row.et_cafphn_num.text
        val polock = layout_otherinfo.et_caflock.text
        val ponext = layout_otherinfo.et_cafnxt.text

        val authemail = layout_cafothr_details.et_cafemailid.text
        val authfather = layout_cafothr_details.et_fthr_hsb.text
        val authmobile = layout_cafothr_details.et_authomob.text
        val authname = layout_cafothr_details.et_cafname.text
        val authaddress = layout_cafothr_details.et_address.text
        val authpincode = layout_cafothr_details.et_cafauthpincode.text

        val billingplot = caf_contact_person_row.et_cfblbuildng_num.text
        val billingname = caf_contact_person_row.et_caf_cntname.text
        val billingemail = caf_contact_person_row.et_caf_bilngemailid.text
        val billingfloor = caf_contact_person_row.et_cfblfloor.text
        val billingphn = caf_contact_person_row.et_cafphn_num.text
        val billingpincode = caf_contact_person_row.et_cfblpin_code.text

        val instemail = layout_cafinstal_address.et_cafemail.text
        val instmobile = layout_cafinstal_address.et_cafmbnum.text
        val instpin = layout_cafinstal_address.et_cafpin.text
        var date=""
        if(str_frwall=="1"){
            date = "2022-01-15"
        }

        val amount = layout_payment.et_totalamt.text
        val approvalcode = layout_payment.et_appcode.text

        val brnch = layout_payment.et_brnch.text
        val checkdd = layout_payment.et_chkdate.text
        val checknum = layout_payment.et_chknum.text
        val carddgts = layout_payment.et_cardfrdgt.text
        val paymntdt = layout_payment.et_paymntdt.text
        val txtty = layout_payment.et_txtid.text
        val srcdepst = layout_payment.et_scdeposit.text
        val creditcrd = layout_payment.et_creditfrdgt.text
        val pan = layout_payment.et_pannum.text
        val tan = layout_payment.et_tannum.text
        val gstnum = layout_payment.et_gstt.text

        val cafDetails = CafDetail(str_wrkngdays,strbusinessSegment?.toString(),strCafId,str_cmpnyself,
                str_customercategory, customername?.toString(),str_wrknghrs,str_provider,
                date, str_frwall, gstnum?.toString(),str_gstval,str_ntwrk, pan?.toString(),
                phonenumber?.toString(), caflock, cafnext,
                str_PrfCom, subbssegment, tan?.toString(),str_voip)

        val authSigDetails = AuthorSigDetails(authemail?.toString(), authfather?.toString(),"",
                authmobile?.toString(), authname?.toString(), authaddress?.toString(),str_atcity_code,
                "10001", authpincode?.toString(),str_atinststateId)

        val cafBillingAddress = CafBillingAddress(billingplot?.toString(),"",
                billingname?.toString(), billingemail?.toString(), billingfloor?.toString(),
                "","","",
                str_bladd_area,str_blinst_building_nm,str_blcity_code,"10001", billingphn?.toString(),
                billingpincode?.toString(),str_blinststateId)

        val cafInstallationAddress = CafInstallationAddress(str_billtype,"","",
                "", instemail?.toString(),"","","",
                instmobile?.toString(),
                "", str_add_area,str_inst_building_nm,str_city_code,"10001", instpin?.toString(),
                str_inststateId,"")
        val paymentDetail = PaymentDetail("100", amount?.toString(), approvalcode?.toString(),str_bankid,
                brnch?.toString(), checkdd?.toString(), checknum?.toString(), creditcrd?.toString(),
                carddgts?.toString(),str_payslip,
               cafpaydate,"569480002", srcdepst?.toString(),str_sctype,
                txtty?.toString())

        val createCafReqest = CreateCafReqest(Constants.UPDATECAF, Constants.AUTH_KEY, authSigDetails,
                cafBillingAddress, cafDetails,str_cmp,str_grp,cafInstallationAddress,strOppId,
                "Target@2021#@",paymentDetail,str_rltn,"manager1")
        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.createCaf(createCafReqest)
        call.enqueue(object : Callback<CafDetailResponse?> {
            override fun onResponse(call: Call<CafDetailResponse?>, response: Response<CafDetailResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    val msg = response.body()?.Response?.Message
                    if(response.body()?.StatusCode=="200"){
                        Toast.makeText(this@CAFActivity, msg, Toast.LENGTH_SHORT).show()
                        val fragmentB = GetAllCAFFrag()
                        supportFragmentManager.beginTransaction()
                                .replace(R.id.fragment_caf, fragmentB, "fragmnetId")
                                .commit()
                    }else{
                        Toast.makeText(this@CAFActivity, msg, Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<CafDetailResponse?>, t: Throwable) {
                Log.e("RetroError", t.toString())
            }
        })
    }


    fun submitCaf () {
        val cafRequest = CafRequest(Constants.SUBMITCAF, Constants.AUTH_KEY, strCafId,"", "Target@2021#@", "manager1")
        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getCaf(cafRequest)
        call.enqueue(object : Callback<CafDetailResponse?> {
            override fun onResponse(call: Call<CafDetailResponse?>, response: Response<CafDetailResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    val msg = response.body()?.Response?.Message
                    if(response.body()?.StatusCode=="200"){
                        Toast.makeText(this@CAFActivity, msg, Toast.LENGTH_SHORT).show()
                        caf()
                    }else{
                        Toast.makeText(this@CAFActivity, msg, Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<CafDetailResponse?>, t: Throwable) {
                Log.e("RetroError", t.toString())
            }
        })
    }

    fun caf(){
        val intent = Intent(this, CAFActivity::class.java)
        val bundle = Bundle()
        bundle.putString("CafId", strCafId)
        bundle.putString("OppId", strOppId)
        intent.putExtras(bundle)
        startActivity(intent)
        finish()
    }


    fun getCaf () {
        inProgress()
        val cafRequest = CafRequest(Constants.GETCAF, Constants.AUTH_KEY, strCafId,strOppId, "Target@2021#@", "manager1")
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
                            if(strCafId.isNullOrBlank()){
                                tv_create.visibility=View.VISIBLE
                                tv_update.visibility=View.GONE
                                tv_submit.visibility=View.GONE
                            }else{
                                tv_create.visibility=View.GONE
                                tv_update.visibility=View.VISIBLE
                                tv_submit.visibility=View.VISIBLE
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
                            if(network=="111260000"){
                                caf_contactinfo_layout.et_netwrktech.setText("Yes")
                            }else if(network=="111260001"){
                                caf_contactinfo_layout.et_netwrktech.setText("No")
                            }
                            strCustHappy = response.body()?.Response?.Data?.IsCustomerHappy
                            if(strCustHappy=="1"){
                                caf_contactinfo_layout.et_custhappy.setText("Yes")
                            }else if(strCustHappy=="0"){
                                caf_contactinfo_layout.et_custhappy.setText("No")
                            }
                          /*  layout_payment.et_pannum.setText(response.body()?.Response?.Data?.PanNo)
                            layout_payment.et_tannum.setText(response.body()?.Response?.Data?.TanNo)
                            layout_payment.et_gstnum.setText(response.body()?.Response?.Data?.GstNumberDetial)
                          */strProductId= response.body()?.Response?.Data?.ProductId
                            val strContactstate = response.body()?.Response?.Data?.installationAddresses?.Inst_State
                            val strBlstate = response.body()?.Response?.Data?.billingAddress?.Bill_State

                            val strSubBusSeg = response.body()?.Response?.Data?.SubBussinessSegment
                            val strPrefred = response.body()?.Response?.Data?.PreferredCommMode
                            val strProvider = response.body()?.Response?.Data?.otherinformations?.ExistingServiceProvider
                            val strFirewall = response.body()?.Response?.Data?.otherinformations?.FireWall
                            val strfirmType = response.body()?.Response?.Data?.companyDetail?.FirmType
                            val strAtstate = response.body()?.Response?.Data?.authSigDetails?.Auth_State
                            val strPaySlip = response.body()?.Response?.Data?.payments?.PayInSlip
                            val strWrkngDays = response.body()?.Response?.Data?.BusinessDays
                            val strNetwork = response.body()?.Response?.Data?.NetworkTechnology
                            val strCustomerCategory = response.body()?.Response?.Data?.installationAddresses?.Inst_CategoryofCustomer
                            val strWrkngHrs = response.body()?.Response?.Data?.installationAddresses?.Inst_CategoryofCustomer
                            val strVoip = response.body()?.Response?.Data?.installationAddresses?.Inst_VoidPort
                            val strBillType = response.body()?.Response?.Data?.installationAddresses?.Inst_BillType
                            val strSecrtyType = response.body()?.Response?.Data?.payments?.SecurityDepositType
                            val strGST = response.body()?.Response?.Data?.GstNumber

                            strCity = response.body()?.Response?.Data?.installationAddresses?.Inst_City
                            strBlCity = response.body()?.Response?.Data?.billingAddress?.Bill_City
                            strAthCity = response.body()?.Response?.Data?.authSigDetails?.Auth_City
                            strArea= response.body()?.Response?.Data?.installationAddresses?.Inst_Area
                            strBuilding = response.body()?.Response?.Data?.installationAddresses?.Inst_BuildingName
                            strBlArea= response.body()?.Response?.Data?.billingAddress?.Bill_Area
                            strBlBuilding = response.body()?.Response?.Data?.billingAddress?.Bill_BuildingName
                            strCompany = response.body()?.Response?.Data?.Company.toString()
                            strRelation = response.body()?.Response?.Data?.Relationship.toString()
                            strGroup = response.body()?.Response?.Data?.Group.toString()
                            strIndustry = response.body()?.Response?.Data?.companyDetail?.IndustryType
                            getCompany(strCompany)
                            getRelation(strRelation)
                            getIndustryTpe()

                            var cntstatePosition = 0
                            resources.getStringArray(R.array.list_of_state).forEachIndexed { index, s ->
                                if (s == strContactstate) cntstatePosition = index
                            }
                            val cntstateAdapter = ArrayAdapter(this@CAFActivity, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.list_of_state))
                            cntstateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            layout_cafinstal_address.sp_cafstate.adapter = cntstateAdapter
                            layout_cafinstal_address.sp_cafstate.setSelection(cntstatePosition)
                            cntstateAdapter.notifyDataSetChanged()

                            var atstatePos = 0
                            resources.getStringArray(R.array.list_of_state).forEachIndexed {
                                index, s ->
                                if (s == strAtstate)
                                    atstatePos = index
                            }
                            val atstateAdapter = ArrayAdapter(this@CAFActivity, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.list_of_state))
                            atstateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            layout_cafothr_details.sp_cafauthostate.adapter = atstateAdapter
                            layout_cafothr_details.sp_cafauthostate.setSelection(atstatePos)
                            atstateAdapter.notifyDataSetChanged()

                            var blstatePosition = 0
                            resources.getStringArray(R.array.list_of_state).forEachIndexed { index, s ->
                                if (s == strBlstate) blstatePosition = index
                            }
                            val blstateAdapter = ArrayAdapter(this@CAFActivity, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.list_of_state))
                            blstateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            caf_contact_person_row.sp_cfblstate.adapter = blstateAdapter
                            caf_contact_person_row.sp_cfblstate.setSelection(blstatePosition)
                            blstateAdapter.notifyDataSetChanged()

                            var sbBusPosition = 0
                            resources.getStringArray(R.array.list_of_subBusSegment).forEachIndexed { index, s ->
                                if (s == strSubBusSeg) sbBusPosition = index
                            }
                            val sbBusAdapter = ArrayAdapter(this@CAFActivity, android.R.layout.simple_spinner_item,resources.getStringArray(R.array.list_of_subBusSegment))
                            sbBusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            caf_contactinfo_layout.sp_cafsbbus.adapter = sbBusAdapter
                            caf_contactinfo_layout.sp_cafsbbus.setSelection(sbBusPosition)
                            sbBusAdapter.notifyDataSetChanged()

                            var prfPosition = 0
                            resources.getStringArray(R.array.list_of_prefferedvalue).forEachIndexed { index, s ->
                                if (s == strPrefred) prfPosition = index
                            }
                            val prefAdapter = ArrayAdapter(this@CAFActivity, android.R.layout.simple_spinner_item,resources.getStringArray(R.array.list_of_preffered))
                            prefAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            caf_contactinfo_layout.sp_preffered_cmmnctn.adapter = prefAdapter
                            caf_contactinfo_layout.sp_preffered_cmmnctn.setSelection(prfPosition)
                            prefAdapter.notifyDataSetChanged()

                            var selfPosition = 0
                            resources.getStringArray(R.array.list_of_prefferedvalue).forEachIndexed { index, s ->
                                if (s == strPrefred) selfPosition = index
                            }
                            val selfAdapter = ArrayAdapter(this@CAFActivity, android.R.layout.simple_spinner_item,resources.getStringArray(R.array.list_of_boolean))
                            selfAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            layout_otherinfo.sp_cafcmpny_self.adapter = selfAdapter
                            layout_otherinfo.sp_cafcmpny_self.setSelection(selfPosition)
                            selfAdapter.notifyDataSetChanged()

                            var firmPosition = 0
                            resources.getStringArray(R.array.list_firm_type_value).forEachIndexed { index, s ->
                                if (s == strfirmType) firmPosition = index
                            }
                            val firmtypeAdapter = ArrayAdapter(this@CAFActivity, android.R.layout.simple_spinner_item,resources.getStringArray(R.array.list_firm_type))
                            firmtypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            layout_cafcompany_details.sp_caffirm_type.adapter = firmtypeAdapter
                            layout_cafcompany_details.sp_caffirm_type.setSelection(firmPosition)
                            firmtypeAdapter.notifyDataSetChanged()

                            var providerPosition = 0
                            resources.getStringArray(R.array.ext_serv_one_values).forEachIndexed { index, s ->
                                if (s == strProvider) providerPosition = index
                            }
                            val provAdapter = ArrayAdapter(this@CAFActivity, android.R.layout.simple_spinner_item,resources.getStringArray(R.array.ext_serv_one))
                            provAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            layout_otherinfo.sp_caf_pro.adapter = provAdapter
                            layout_otherinfo.sp_caf_pro.setSelection(providerPosition)
                            provAdapter.notifyDataSetChanged()
                            var firewallPosition = 0
                            resources.getStringArray(R.array.list_of_boolean_values).forEachIndexed { index, s ->
                                if (s == strFirewall) firewallPosition = index
                            }
                            val firewallAdapter = ArrayAdapter(this@CAFActivity, android.R.layout.simple_spinner_item,resources.getStringArray(R.array.list_of_boolean))
                            firewallAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            layout_otherinfo.sp_caffrwal.adapter = firewallAdapter
                            layout_otherinfo.sp_caffrwal.setSelection(firewallPosition)
                            firewallAdapter.notifyDataSetChanged()

                            var workingPosition = 0
                            resources.getStringArray(R.array.list_of_wrkngdaysvalues).forEachIndexed { index, s ->
                                if (s == strWrkngDays) workingPosition = index
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
                                if (s == strCustomerCategory) customerPosition = index
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
                                if (s == strVoip) voipPosition = index
                            }
                            val voipAdapter = ArrayAdapter(this@CAFActivity, android.R.layout.simple_spinner_item,resources.getStringArray(R.array.list_of_monitoring))
                            voipAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            layout_cafinstal_address.sp_voip.adapter = voipAdapter
                            layout_cafinstal_address.sp_voip.setSelection(voipPosition)
                            voipAdapter.notifyDataSetChanged()

                            var billtypePosition = 0
                            resources.getStringArray(R.array.list_of_boolean_values).forEachIndexed { index, s ->
                                if (s == strBillType) billtypePosition = index
                            }
                            val billtypeAdapter = ArrayAdapter(this@CAFActivity, android.R.layout.simple_spinner_item,resources.getStringArray(R.array.list_of_billtype))
                            billtypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            layout_cafinstal_address.sp_cafbilltype.adapter = billtypeAdapter
                            layout_cafinstal_address.sp_cafbilltype.setSelection(billtypePosition)
                            billtypeAdapter.notifyDataSetChanged()

                            var payslipPosition = 0
                            resources.getStringArray(R.array.list_of_payslipval).forEachIndexed { index, s ->
                                if (s == strPaySlip) payslipPosition = index
                            }
                            val payslipAdapter = ArrayAdapter(this@CAFActivity, android.R.layout.simple_spinner_item,resources.getStringArray(R.array.list_of_payslip))
                            payslipAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            layout_payment.sp_payslip.adapter = payslipAdapter
                            layout_payment.sp_payslip.setSelection(payslipPosition)
                            payslipAdapter.notifyDataSetChanged()

                            var securityPosition = 0
                            resources.getStringArray(R.array.list_of_boolean_values).forEachIndexed { index, s ->
                                if (s == strSecrtyType) securityPosition = index
                            }
                            val securityAdapter = ArrayAdapter(this@CAFActivity, android.R.layout.simple_spinner_item,resources.getStringArray(R.array.list_of_Deposit))
                            securityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            layout_payment.sp_securitytype.adapter = securityAdapter
                            layout_payment.sp_securitytype.setSelection(securityPosition)
                            securityAdapter.notifyDataSetChanged()

                            var gstPosition = 0
                            resources.getStringArray(R.array.list_of_gstval).forEachIndexed { index, s ->
                                if (s == strGST) gstPosition = index
                            }
                            val gstAdapter = ArrayAdapter(this@CAFActivity, android.R.layout.simple_spinner_item,resources.getStringArray(R.array.list_of_gst))
                            gstAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            binding.layoutPayment.spGst.adapter = gstAdapter
                            binding.layoutPayment.spGst.setSelection(gstPosition)
                            gstAdapter.notifyDataSetChanged()

                            val polockDate = response.body()?.Response?.Data?.otherinformations?.PoLock
                            if(polockDate.isNullOrEmpty()){

                            }else {
                                val split1 = polockDate.split("-")
                                val date1 = split1.get(0)
                                val month1 = split1.get(1)
                                val year1 = split1.get(2)
                                layout_otherinfo.et_caflock.setText("$date1-$month1-$year1")
                            }

                            val ponextdate = response.body()?.Response?.Data?.otherinformations?.PoNext
                                if(ponextdate.isNullOrEmpty()){

                                }else {
                                    val split2 = ponextdate.split("-")
                                    val date2 = split2.get(0)
                                    val month2 = split2.get(1)
                                    val year2 = split2.get(2)
                                    layout_otherinfo.et_cafnxt.setText("$date2-$month2-$year2")
                                }

                            val status = response.body()?.Response?.Data?.SubmitFlag
                            if(status=="111260000"){
                                locked()
                            }else{
                                Calender()
                            }
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


    fun locked(){
        caf_contactinfo_layout.et_cstname.isEnabled= false
        caf_contactinfo_layout.et_cstmrwrknghrs.isEnabled= false
        caf_contactinfo_layout.caf_contactinfo_layout.isEnabled= false
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
        layout_otherinfo.sp_cafcmpny_self.isEnabled=false
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
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        layout_otherinfo.et_caflock.setOnClickListener {
            val dpd = DatePickerDialog(this, { view, year, monthOfYear, dayOfMonth ->
                val mnth = monthOfYear+1
                layout_otherinfo.et_caflock.setText("$dayOfMonth-$mnth-$year")
                 caflock = ("$year-$mnth-$dayOfMonth")
            }, year, month, day)
            dpd.show()
        }

        layout_otherinfo.et_cafnxt.setOnClickListener {
            val dpd = DatePickerDialog(this, { view, year, monthOfYear, dayOfMonth ->
                val mnth = monthOfYear+1
                layout_otherinfo.et_cafnxt.setText("$dayOfMonth-$mnth-$year")
                 cafnext = ("$year-$mnth-$dayOfMonth")
            }, year, month, day)
            dpd.show()
        }

        layout_payment.et_paymntdt.setOnClickListener {
            val dpd = DatePickerDialog(this, { view, year, monthOfYear, dayOfMonth ->
                val mnth = monthOfYear+1
                layout_payment.et_paymntdt.setText("$dayOfMonth-$mnth-$year")
                 cafpaydate = ("$year-$mnth-$dayOfMonth")
            }, year, month, day)
            dpd.show()
        }
    }

    override fun onClick(p0: View?) {

    }

    fun getCompany(strCompany: String) {
        val getCompanyRequest = GetCompanyRequest(Constants.GET_COMPANY,Constants.AUTH_KEY,strCompany,"Target@2021#@","manager1")
        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getComapnyList(getCompanyRequest)
        call.enqueue(object : Callback<GetCompanyResponse?> {
            override fun onResponse(call: Call<GetCompanyResponse?>, response: Response<GetCompanyResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
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
                            company!!.add(item.Company_Name)
                            companyId?.add(item.Company_ID)
                            group!!.add(item.Group_Name)
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
                        group!!.forEachIndexed { index, s ->
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
                Log.e("RetroError", t.toString())
            }
        })
    }

    fun getRelation(str_cmny: String?) {
        val getCompanyRequest = GetCompanyRequest(Constants.GET_RELATIONSHIP,Constants.AUTH_KEY,str_cmny.toString(),"Target@2021#@","manager1")
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
                                relation!!.add(item.Relationship_Name)
                                relationId?.add(item.Relationship_ID)
                            }
                        }
                        var relationPosition=0
                        relation!!.forEachIndexed { index, s ->
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
        val getLeadBuildingRequest = GetLeadBuildingRequest(Constants.GET_INDUSTRYTYPE,Constants.AUTH_KEY,"","","Target@2021#@","manager1")

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getIndustry(getLeadBuildingRequest)
        call.enqueue(object : Callback<GetIndustryTypeResponse?> {
            override fun onResponse(call: Call<GetIndustryTypeResponse?>, response: Response<GetIndustryTypeResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        industryList= response.body()!!.Response
                        instryname = ArrayList<String>()
                        industryid = ArrayList<String>()
                        instryname.add("Select Industry")
                        industryid.add("0")
                        for (item in industryList!!){
                            instryname.add(item.IndTypeName)
                            industryid.add(item.IndTypeId)
                        }
                        var industryPosition=0
                        instryname.forEachIndexed { index, s ->
                            if(s==strIndustry)industryPosition=index
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
        val cafReqest = CafRequest(Constants.GETBANK,Constants.AUTH_KEY,"","","Target@2021#@","manager1")

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getBankList(cafReqest)
        call.enqueue(object : Callback<BankListResponse?> {
            override fun onResponse(call: Call<BankListResponse?>, response: Response<BankListResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        bankList= response.body()?.Response?.Data
                        bankname = ArrayList<String>()
                        bankid = ArrayList<String>()
                        bankname.add("Select BankName")
                        bankid.add("0")
                        for (item in bankList!!){
                            bankname.add(item.Bankname)
                            bankid.add(item.BankId)
                        }
                        var bankPosition=0
                        bankname.forEachIndexed { index, s ->
                            if(s==strIndustry)bankPosition=index
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
        lineartwo.setOnClickListener { v ->
            linearcontactinfo.visibility = View.VISIBLE
            linear_otherinfo.visibility = View.GONE
            linear_companydetails.visibility = View.GONE
            linadd.visibility = View.GONE
            linear_contact_person_address.visibility= View.GONE
            linearother_details.visibility = View.GONE
            linearcafpymnt_details.visibility = View.GONE
            lineardoc_details.visibility = View.GONE
        }
        linearthree.setOnClickListener { v ->
            linearcontactinfo.visibility = View.GONE
            linear_otherinfo.visibility = View.VISIBLE
            linear_companydetails.visibility = View.GONE
            linadd.visibility = View.GONE
            linear_contact_person_address.visibility= View.GONE
            linearother_details.visibility = View.GONE
            linearcafpymnt_details.visibility = View.GONE
            lineardoc_details.visibility = View.GONE
        }
        linearsix.setOnClickListener { v ->
            linearcontactinfo.visibility = View.GONE
            linear_otherinfo.visibility = View.GONE
            linear_companydetails.visibility = View.VISIBLE
            linadd.visibility = View.GONE
            linear_contact_person_address.visibility= View.GONE
            linearother_details.visibility = View.GONE
            linearcafpymnt_details.visibility = View.GONE
            lineardoc_details.visibility = View.GONE
        }
        linearfouraddres.setOnClickListener { v ->
            linearcontactinfo.visibility = View.GONE
            linear_otherinfo.visibility = View.GONE
            linear_companydetails.visibility = View.GONE
            linadd.visibility = View.VISIBLE
            linear_contact_person_address.visibility= View.GONE
            linearother_details.visibility = View.GONE
            linearcafpymnt_details.visibility = View.GONE
            lineardoc_details.visibility = View.GONE
        }
        linearfive.setOnClickListener { v ->
            linearcontactinfo.visibility = View.GONE
            linear_otherinfo.visibility = View.GONE
            linear_companydetails.visibility = View.GONE
            linadd.visibility = View.GONE
            linear_contact_person_address.visibility= View.VISIBLE
            linearother_details.visibility = View.GONE
            linearcafpymnt_details.visibility = View.GONE
            lineardoc_details.visibility = View.GONE
        }
        lineareight.setOnClickListener { v ->
            linearcontactinfo.visibility = View.GONE
            linear_otherinfo.visibility = View.GONE
            linear_companydetails.visibility = View.GONE
            linadd.visibility = View.GONE
            linear_contact_person_address.visibility= View.GONE
            linearother_details.visibility = View.VISIBLE
            linearcafpymnt_details.visibility = View.GONE
            lineardoc_details.visibility = View.GONE
        }
        linearnine.setOnClickListener { v ->
            linearcontactinfo.visibility = View.GONE
            linear_otherinfo.visibility = View.GONE
            linear_companydetails.visibility = View.GONE
            linadd.visibility = View.GONE
            linear_contact_person_address.visibility= View.GONE
            linearother_details.visibility = View.GONE
            linearcafpymnt_details.visibility = View.VISIBLE
            lineardoc_details.visibility = View.GONE
        }

        linearten.setOnClickListener { v ->
            linearcontactinfo.visibility = View.GONE
            linear_otherinfo.visibility = View.GONE
            linear_companydetails.visibility = View.GONE
            linadd.visibility = View.GONE
            linear_contact_person_address.visibility= View.GONE
            linearother_details.visibility = View.GONE
            linearcafpymnt_details.visibility = View.GONE
            lineardoc_details.visibility = View.VISIBLE
        }

    }


    fun getInstallCity(stateCode: String) {
        val getCityRequest = GetCityRequest(Constants.GET_CITY,Constants.AUTH_KEY,"Target@2021#@",stateCode,"manager1")

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getCityList(getCityRequest)
        call.enqueue(object : Callback<GetCityResponse?> {
            override fun onResponse(call: Call<GetCityResponse?>, response: Response<GetCityResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        val img = response.body()!!.Response.Message
                        Log.e("image", img)
                        cityList = response.body()!!.Response.Data
                        city = ArrayList<String>()
                        cityCode = ArrayList<String>()
                        city?.add("Select City")
                        cityCode?.add("")
                        for (item in cityList!!) {
                            city?.add(item.CityName)
                            cityCode?.add(item.CityCode)
                        }
                        var cityPosition=0
                        city!!.forEachIndexed { index, s ->
                            if(s==strCity)cityPosition=index
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

            override fun onFailure(call: Call<GetCityResponse?>, t: Throwable) {
                Log.e("RetroError", t.toString())
            }
        })
    }


    fun getBillingCity(stateCode: String) {
        val getCityRequest = GetCityRequest(Constants.GET_CITY,Constants.AUTH_KEY,"Target@2021#@",stateCode,"manager1")

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getCityList(getCityRequest)
        call.enqueue(object : Callback<GetCityResponse?> {
            override fun onResponse(call: Call<GetCityResponse?>, response: Response<GetCityResponse?>) {
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
                        billingcity!!.forEachIndexed { index, s ->
                            if(s==strBlCity)cityPosition=index
                            return@forEachIndexed
                        }
                        val adapter12 = ArrayAdapter(this@CAFActivity, android.R.layout.simple_spinner_item, billingcity!!)
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        caf_contact_person_row.sp_cfblcity.adapter = adapter12
                        caf_contact_person_row.sp_cfblcity.setSelection(cityPosition)
                        adapter12.notifyDataSetChanged()
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<GetCityResponse?>, t: Throwable) {
                Log.e("RetroError", t.toString())
            }
        })
    }

    fun getAuthorizedCity(stateCode: String) {
        val getCityRequest = GetCityRequest(Constants.GET_CITY,Constants.AUTH_KEY,"Target@2021#@",stateCode,"manager1")

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getCityList(getCityRequest)
        call.enqueue(object : Callback<GetCityResponse?> {
            override fun onResponse(call: Call<GetCityResponse?>, response: Response<GetCityResponse?>) {
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
                        authocity!!.forEachIndexed { index, s ->
                            if(s==strAthCity)cityPosition=index
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

            override fun onFailure(call: Call<GetCityResponse?>, t: Throwable) {
                Log.e("RetroError", t.toString())
            }
        })
    }

    fun getInstallArea(str_city: String?, str_city_code: String?) {
        val getLeadAreaRequest =   GetLeadAreaRequest(Constants.Get_AREA,Constants.AUTH_KEY,
                str_city_code.toString(), str_city.toString() ,"","manager1","Target@2021#@",true)

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
                            area?.add(item.AreaName)
                            areaCode?.add(item.AreaCode)
                        }
                        var areaPosition=0
                        area!!.forEachIndexed { index, s ->
                            if(s==strArea)areaPosition=index
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
                str_city_code.toString(), str_city.toString() ,"","manager1","Target@2021#@",false)

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
                            billingarea?.add(item.AreaName)
                            billingareaCode?.add(item.AreaCode)
                        }
                        var areaPosition=0
                        billingarea!!.forEachIndexed { index, s ->
                            if(s==strBlArea)areaPosition=index
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
        val getLeadBuildingRequest = GetLeadBuildingRequest(Constants.GET_BUILDING,Constants.AUTH_KEY,areaCode.toString(),areaname,"Target@2021#@","manager1")

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
                                building?.add(item.BuildingName)
                                buildingCode?.add(item.BuildingCode)
                            }
                        }

                        var buildPosition=0
                        building!!.forEachIndexed { index, s ->
                            if(s==strBuilding)buildPosition=index
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
        val getLeadBuildingRequest = GetLeadBuildingRequest(Constants.GET_BUILDING,Constants.AUTH_KEY,areaCode.toString(),areaname,"Target@2021#@","manager1")

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
                                billingbuilding?.add(item.BuildingName)
                                billingbuildingCode?.add(item.BuildingCode)
                            }
                        }
                        var buildPosition=0
                        billingbuilding!!.forEachIndexed { index, s ->
                            if(s==strBlBuilding)buildPosition=index
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
            str_PrfCom = resources.getStringArray(R.array.list_of_prefferedvalue).get(position)
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
            layout_otherinfo.et_cafcmpny_self.setText(resources.getStringArray(R.array.list_of_boolean).get(position))
            str_cmpnyself =  resources.getStringArray(R.array.list_of_boolean_values).get(position )
        } else if(parent?.id == R.id.sp_caffirm_type){
            layout_cafcompany_details.et_caffirm_type.setText(resources.getStringArray(R.array.list_firm_type).get(position))
            str_firmtype =  resources.getStringArray(R.array.list_firm_type_value).get(position )
        }else if(parent?.id == R.id.sp_cafindustype){
            layout_cafcompany_details.et_cafindus_type.setText(instryname.get(position))
            str_indusid =  industryid.get(position )
        }else if(parent?.id == R.id.sp_cafstate){
            layout_cafinstal_address.et_cafstate.setText(resources.getStringArray(R.array.list_of_state).get(position))
            str_statename = resources.getStringArray(R.array.list_of_state).get(position)
             str_inststateId= resources.getStringArray(R.array.list_state_code).get(position)
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
            layout_cafinstal_address.et_cafbuilding_status.setText(resources.getStringArray(R.array.list_Of_Status).get(position))
            str_inst_statusid =  resources.getStringArray(R.array.list_Of_StatusId).get(position)
         //   val installstatus = resources.getStringArray(R.array.list_Of_Status).get(position)
        }else if (parent?.id == R.id.sp_cafsbbus) {
            caf_contactinfo_layout.et_sb_bs_sgmnt.setText( resources.getStringArray(R.array.list_of_subBusSegment).get(position))
            str_sub_bus =  resources.getStringArray(R.array.list_of_subBusSegment).get(position)
        }else if (parent?.id == R.id.sp_bnknam) {
            layout_payment.et_bnknm.setText(bankname.get(position))
            str_bankid =  bankid.get(position)
        }
        else if(parent?.id == R.id.sp_cfblstate){
            caf_contact_person_row.et_cfblstate.setText(resources.getStringArray(R.array.list_of_state).get(position))
            str_blstatename = resources.getStringArray(R.array.list_of_state).get(position)
            str_blinststateId= resources.getStringArray(R.array.list_state_code).get(position)
            if(str_blstatename!="Select State"|| caf_contact_person_row.et_cfblstate.text?.isNotEmpty() == true) {
                getBillingCity(str_blinststateId.toString())
            }
        }else if(parent?.id == R.id.sp_cfblcity){
            caf_contact_person_row.et_cfblcity.setText(billingcity?.get(position))
            str_blcity = billingcity?.get(position).toString()
            str_blcity_code =  billingcityCode?.get(position )
            getBillingArea(str_blcity, str_blcity_code)
        } else if(parent?.id == R.id.sp_cfblcnarea){
            caf_contact_person_row.et_cfblarea.setText(billingarea?.get(position))
            str_bladd_area = billingareaCode?.get(position )
            val cntareaname = billingarea?.get(position).toString()
            getBillingBuilding(cntareaname,str_bladd_area)
        } else if(parent?.id == R.id. sp_cfblbuilding_nm){
            caf_contact_person_row.et_cfblbuilding.setText(billingbuilding?.get(position))
            str_blinst_building_nm =  billingbuildingCode?.get(position )
           // val buildingname = billingbuilding?.get(position)
        }else if(parent?.id == R.id.sp_caf_pro){
            layout_otherinfo.et_provider.setText(resources.getStringArray(R.array.ext_serv_one).get(position))
            str_provider =  resources.getStringArray(R.array.ext_serv_one_values).get(position )
        }else if(parent?.id == R.id.sp_caffrwal){
            layout_otherinfo.et_caffrwl.setText(resources.getStringArray(R.array.list_of_boolean).get(position))
            str_frwall =  resources.getStringArray(R.array.list_of_boolean_values).get(position )
        }else if(parent?.id == R.id.sp_cafauthostate){
            layout_cafothr_details.et_cafauthstate.setText(resources.getStringArray(R.array.list_of_state).get(position))
            str_atstatename = resources.getStringArray(R.array.list_of_state).get(position)
            str_atinststateId= resources.getStringArray(R.array.list_state_code).get(position)
            if(str_blstatename!="Select State"|| layout_cafothr_details.et_cafauthstate.text?.isNotEmpty() == true) {
                getAuthorizedCity(str_atinststateId.toString())
            }
        }else if(parent?.id == R.id.sp_cafauthcity){
            layout_cafothr_details.et_add_cafauthcity.setText(authocity?.get(position))
            str_atcity = authocity?.get(position).toString()
            str_atcity_code =  authocityCode?.get(position )
        }else if(parent?.id == R.id.sp_wrkng_days){
            caf_contactinfo_layout.et_wrkngdys.setText(resources.getStringArray(R.array.list_of_wrkngdays).get(position))
            str_wrkngdays = resources.getStringArray(R.array.list_of_wrkngdaysvalues).get(position).toString()
        }else if(parent?.id == R.id.sp_ntwrkmtr){
            caf_contactinfo_layout.et_ntwrkmtr.setText(resources.getStringArray(R.array.list_of_monitoring).get(position))
            str_ntwrk = resources.getStringArray(R.array.list_of_monitoringvalues).get(position).toString()
        }else if(parent?.id == R.id.sp_custctgry){
            layout_cafinstal_address.et_custctgry.setText(resources.getStringArray(R.array.list_of_cstmrcategory).get(position))
            str_customercategory = resources.getStringArray(R.array.list_of_cstctgryvalues).get(position).toString()
        }else if (parent?.id == R.id.sp_cstmrwrknghrs) {
            caf_contactinfo_layout.et_cstmrwrknghrs.setText( resources.getStringArray(R.array.list_of_wrknghours).get(position))
            str_wrknghrs =  resources.getStringArray(R.array.list_of_wrknghoursval).get(position)
        }else if (parent?.id == R.id.sp_voip) {
            layout_cafinstal_address.et_cafvoip.setText( resources.getStringArray(R.array.list_of_monitoring).get(position))
            str_voip =  resources.getStringArray(R.array.list_of_cstctgryvalues).get(position)
        }else if (parent?.id == R.id.sp_cafbilltype) {
            layout_cafinstal_address.et_cafbiltype.setText( resources.getStringArray(R.array.list_of_billtype).get(position))
            str_billtype =  resources.getStringArray(R.array.list_of_boolean_values).get(position)
        }else if (parent?.id == R.id.sp_securitytype) {
            layout_payment.et_sctype.setText(resources.getStringArray(R.array.list_of_Deposit).get(position))
            str_sctype = resources.getStringArray(R.array.list_of_boolean_values).get(position)
        }else if (parent?.id == R.id.sp_gst) {
            binding.layoutPayment.etGst.setText(resources.getStringArray(R.array.list_of_gst).get(position))
            str_gstval = resources.getStringArray(R.array.list_of_gstval).get(position)
            if(str_gstval=="569480000"){
                layout_payment.et_gsttnum.visibility=View.VISIBLE
            }else{
                layout_payment.et_gsttnum.visibility=View.GONE
            }
        }

        else if (parent?.id == R.id.sp_payslip) {
            layout_payment.et_payslip.setText( resources.getStringArray(R.array.list_of_payslip).get(position))
            str_payslip =  resources.getStringArray(R.array.list_of_payslipval).get(position)
            val pay = resources.getStringArray(R.array.list_of_payslip).get(position)
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
                layout_payment.et_transactionid.visibility=View.VISIBLE
                layout_payment.et_card4dgt.visibility=View.GONE
                layout_payment.frbnk.visibility=View.VISIBLE
                layout_payment.et_brnchname.visibility=View.VISIBLE
                layout_payment.et_checkkdate.visibility=View.VISIBLE
                layout_payment.et_chknumber.visibility=View.VISIBLE
                layout_payment.et_approvalcode.visibility=View.GONE
                layout_payment.et_paymentdate.visibility=View.GONE
                layout_payment.et_debit4dgt.visibility=View.GONE
            }else if(pay=="Demand draft"){
                layout_payment.et_transactionid.visibility=View.VISIBLE
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
                layout_payment.et_approvalcode.visibility=View.GONE
            }else if(pay=="NEFT"){
                layout_payment.et_transactionid.visibility=View.VISIBLE
                layout_payment.et_approvalcode.visibility=View.VISIBLE
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
                layout_payment.et_card4dgt.visibility=View.VISIBLE
                layout_payment.et_transactionid.visibility=View.GONE
                layout_payment.et_approvalcode.visibility=View.GONE
                layout_payment.et_debit4dgt.visibility=View.GONE
                layout_payment.et_paymentdate.visibility=View.GONE
            }
        }

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }


}