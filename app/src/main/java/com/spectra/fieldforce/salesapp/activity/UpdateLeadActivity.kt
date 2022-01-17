package com.spectra.fieldforce.salesapp.activity

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.*
import android.view.animation.AlphaAnimation
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.spectra.fieldforce.R
import com.spectra.fieldforce.api.ApiClient
import com.spectra.fieldforce.api.ApiInterface
import com.spectra.fieldforce.databinding.UpdateLeadDemoFragmentBinding
import com.spectra.fieldforce.salesapp.fragment.FlrFrag
import com.spectra.fieldforce.salesapp.fragment.GetAllLeadFrag
import com.spectra.fieldforce.salesapp.model.*
import com.spectra.fieldforce.utils.AppConstants
import com.spectra.fieldforce.utils.Constants
import kotlinx.android.synthetic.main.lead_company_details_row.view.*
import kotlinx.android.synthetic.main.lead_contact_info.view.*
import kotlinx.android.synthetic.main.lead_demo_fragment.*
import kotlinx.android.synthetic.main.lead_installation_address_row.view.*
import kotlinx.android.synthetic.main.lead_other_details_row.view.*
import kotlinx.android.synthetic.main.sales_dashboard_activity.*
import kotlinx.android.synthetic.main.toolbar.view.*
import kotlinx.android.synthetic.main.update_lead_channel_source_row.*
import kotlinx.android.synthetic.main.update_lead_channel_source_row.view.*
import kotlinx.android.synthetic.main.update_lead_company_details_row.*
import kotlinx.android.synthetic.main.update_lead_company_details_row.view.*
import kotlinx.android.synthetic.main.update_lead_company_details_row.view.et_company_name
import kotlinx.android.synthetic.main.update_lead_company_details_row.view.et_firm_type
import kotlinx.android.synthetic.main.update_lead_company_details_row.view.et_indus_type
import kotlinx.android.synthetic.main.update_lead_company_details_row.view.et_job_title
import kotlinx.android.synthetic.main.update_lead_company_details_row.view.sp_firm_type
import kotlinx.android.synthetic.main.update_lead_company_details_row.view.sp_industype
import kotlinx.android.synthetic.main.update_lead_demo_fragment.*
import kotlinx.android.synthetic.main.update_lead_demo_fragment.linadd
import kotlinx.android.synthetic.main.update_lead_demo_fragment.linear_companydetails
import kotlinx.android.synthetic.main.update_lead_demo_fragment.linear_contact_person_address
import kotlinx.android.synthetic.main.update_lead_demo_fragment.linear_insta_addres
import kotlinx.android.synthetic.main.update_lead_demo_fragment.linearcontactinfo
import kotlinx.android.synthetic.main.update_lead_demo_fragment.lineareight
import kotlinx.android.synthetic.main.update_lead_demo_fragment.linearfive
import kotlinx.android.synthetic.main.update_lead_demo_fragment.linearfouraddres
import kotlinx.android.synthetic.main.update_lead_demo_fragment.linearnine
import kotlinx.android.synthetic.main.update_lead_demo_fragment.linearother_details
import kotlinx.android.synthetic.main.update_lead_demo_fragment.linearremark_details
import kotlinx.android.synthetic.main.update_lead_demo_fragment.linearsix
import kotlinx.android.synthetic.main.update_lead_demo_fragment.linearthree
import kotlinx.android.synthetic.main.update_lead_demo_fragment.lineartwo
import kotlinx.android.synthetic.main.update_lead_general_info.view.*
import kotlinx.android.synthetic.main.update_lead_installation_address_row.*
import kotlinx.android.synthetic.main.update_lead_installation_address_row.view.*
import kotlinx.android.synthetic.main.update_lead_installation_address_row.view.et_add_build_num
import kotlinx.android.synthetic.main.update_lead_installation_address_row.view.et_add_city
import kotlinx.android.synthetic.main.update_lead_installation_address_row.view.et_add_floor
import kotlinx.android.synthetic.main.update_lead_installation_address_row.view.et_building
import kotlinx.android.synthetic.main.update_lead_installation_address_row.view.et_cntarea
import kotlinx.android.synthetic.main.update_lead_installation_address_row.view.et_country
import kotlinx.android.synthetic.main.update_lead_installation_address_row.view.et_pin_code
import kotlinx.android.synthetic.main.update_lead_installation_address_row.view.et_state
import kotlinx.android.synthetic.main.update_lead_installation_address_row.view.sp_building_nm
import kotlinx.android.synthetic.main.update_lead_installation_address_row.view.sp_city
import kotlinx.android.synthetic.main.update_lead_installation_address_row.view.sp_cnarea
import kotlinx.android.synthetic.main.update_lead_installation_address_row.view.sp_cntry
import kotlinx.android.synthetic.main.update_lead_installation_address_row.view.sp_state
import kotlinx.android.synthetic.main.update_lead_other_details_row.*
import kotlinx.android.synthetic.main.update_lead_other_details_row.view.*
import kotlinx.android.synthetic.main.update_lead_other_details_row.view.et_crt_wrk
import kotlinx.android.synthetic.main.update_lead_other_details_row.view.et_cust_void
import kotlinx.android.synthetic.main.update_lead_other_details_row.view.et_cust_wifi
import kotlinx.android.synthetic.main.update_lead_other_details_row.view.et_ext_srv
import kotlinx.android.synthetic.main.update_lead_other_details_row.view.et_ext_srv_two
import kotlinx.android.synthetic.main.update_lead_other_details_row.view.et_indus_firewl
import kotlinx.android.synthetic.main.update_lead_other_details_row.view.et_is_cus
import kotlinx.android.synthetic.main.update_lead_other_details_row.view.et_media
import kotlinx.android.synthetic.main.update_lead_other_details_row.view.et_service_pv_one
import kotlinx.android.synthetic.main.update_lead_other_details_row.view.et_srv_pv_two
import kotlinx.android.synthetic.main.update_lead_other_details_row.view.et_trgt_period
import kotlinx.android.synthetic.main.update_lead_other_details_row.view.et_vpn_srv
import kotlinx.android.synthetic.main.update_lead_other_details_row.view.sp_cst_voip
import kotlinx.android.synthetic.main.update_lead_other_details_row.view.sp_cust_wifi
import kotlinx.android.synthetic.main.update_lead_other_details_row.view.sp_ex_serv
import kotlinx.android.synthetic.main.update_lead_other_details_row.view.sp_ext_serv_two
import kotlinx.android.synthetic.main.update_lead_other_details_row.view.sp_intrs_frwal
import kotlinx.android.synthetic.main.update_lead_other_details_row.view.sp_intrsteddata_center
import kotlinx.android.synthetic.main.update_lead_other_details_row.view.sp_media
import kotlinx.android.synthetic.main.update_lead_other_details_row.view.sp_serv_pro_one
import kotlinx.android.synthetic.main.update_lead_other_details_row.view.sp_serv_pro_two
import kotlinx.android.synthetic.main.update_lead_other_details_row.view.sp_vpn_serv
import kotlinx.android.synthetic.main.update_lead_remarks_row.view.*
import kotlinx.android.synthetic.main.update_leadtoolbar.*
import kotlinx.android.synthetic.main.update_leadtoolbar.flr
import kotlinx.android.synthetic.main.updatelead__contact_person_row.*
import kotlinx.android.synthetic.main.updatelead__contact_person_row.view.*
import kotlinx.android.synthetic.main.updatelead_contact_personadd_row.*
import kotlinx.android.synthetic.main.updatelead_contact_personadd_row.view.*
import kotlinx.android.synthetic.main.updatelead_contact_personadd_row.view.et_business_seg
import kotlinx.android.synthetic.main.updatelead_contact_personadd_row.view.et_contact_person
import kotlinx.android.synthetic.main.updatelead_contact_personadd_row.view.et_customer_seg

import kotlinx.android.synthetic.main.updatelead_contact_personadd_row.view.et_saluation

import kotlinx.android.synthetic.main.updatelead_contact_personadd_row.view.sp_cust_seg

import kotlinx.android.synthetic.main.updatelead_contact_personadd_row.view.sp_salutation
import kotlinx.android.synthetic.main.updatelead_contact_personadd_row.view.sp_sub_bus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class UpdateLeadActivity:AppCompatActivity(), View.OnClickListener,AdapterView.OnItemSelectedListener {

    lateinit var binding: UpdateLeadDemoFragmentBinding

    private var source : ArrayList<String>? = null
    private var sourceList: MutableList<SrcData>? = null
    private var areaList : ArrayList<AreaData>? = null
    private var area : ArrayList<String>? = null
    private var areaCode : ArrayList<String>? = null
    private var cntarea : ArrayList<String>? = null
    private var cntareaCode : ArrayList<String>? = null
    private var cityList : ArrayList<CityData>? = null
    private var city : ArrayList<String>? = null
    private var cityCode : ArrayList<String>? = null
    private var buildingList : ArrayList<BuildData>? = null
    private var building : ArrayList<String>? = null
    private var buildingCode : ArrayList<String>? = null
    private var cntbuilding : ArrayList<String>? = null
    private var cntbuildingCode : ArrayList<String>? = null
    private var trgtdate:String?=null
    var str_city: String? = null
    var str_city_code : String? = null
    var str_Contactcity: String? = null
    var str_ContactcityCode : String? = null
    private var inAnimation: AlphaAnimation? = null
    private var outAnimation: AlphaAnimation? = null
    var str_lead_chnl : String? = null
    var str_lead_src : String? = null
    var str_sub_bus : String? = null
    var str_firm_type :String? = null
    var str_industry_type :String? = null
    var str_salutation :String? = null
    var str_state : String? = null
    var strcontact_state:String? = null
    var strcontact_stateCode:String? = null
    var str_ext_serv_pro_one : String? = null
    var str_serv_pro_two : String? = null
    var str_media : String? = null
    var str_data : String? = null
    var str_cust_frwl : String? = null
    var str_cust_vpn : String? = null
    var str_cust_serv_one : String? = null
    var str_cust_serv_two : String? = null
    var strCurentLocation: String? = null
    var str_voip : String? = null
    var str_wifi : String? = null
    var strDisqualify = ""
    var strCity = ""
    var strArea =""
    var strBuilding=""
    var strContactCity = ""
    var strContactArea =""
    var strContactBuilding=""
    var strCompany=""
    var strGroup=""
    var strRelation=""
    var strMobile=""
    var strIndustry =""
    var str_inst_country: String? = null
    var str_inst_state: String? = null

    var str_inst_area : String? = null
    var str_inst_building_nm : String? = null
    var str_inst_build_num : String? = null
    var str_inst_build_name : String? = null
    var str_add_area : String? = null
    var str_customer_segmentid :String? = null
    private var industryList : ArrayList<IndustryResponse>? = null
    private var instryname = ArrayList<String>()
    private var industryid = ArrayList<String>()
    private var companyId : ArrayList<String>? = null
    private var company: ArrayList<String>? = null
    private var group : ArrayList<String>? = null
    private var groupId : ArrayList<String>? = null
    private var relation : ArrayList<String>? = null
    private var relationId : ArrayList<String>? = null
    private var companyList: ArrayList<ComapnyData>? = null
    private var othercity : ArrayList<String>? = null
    private var othercityCode : ArrayList<String>? = null
    var str_othercity: String? = null
    var str_grp :String ? = null
    var str_rltn:String ? = null
    var str_cmp :String? = null
    var str_lead_Id : String? = null

    var str_lead_status : String? = null
    var list_of_salutation = arrayOf("Select Salutation","Mr.", "Mrs.", "Miss")
    var list_of_salutation_id = arrayOf("0","1","2","3")
    var list_of_option = arrayOf("Select Option","Yes","No")
    var list_of_mediavalue = arrayOf("0","1","2")
   var list_of_channel = arrayOf("Call/SMS-Inbound","Caretel","CM Outbound","Email/Email Campaigns","Inside Sales","Inside Sales-QC","Kaizala","NetOps Channel","Online CAF","Outbound Call",
            "Paid Campaign/Activity","Promotion/BTL/ATL/Events/Sponsorship/Visibility Activity","Self Care Portal","Self Lead","Unify Churned","Web Campaign")
    var list_of_subBusSegment = arrayOf("Connectivity Solution", "Data Centre Products", "Internet Service","SDWAN","SIP-Trunk","VOIP")
    var list_of_cust_segment = arrayOf("SDWAN","SMB","Media","LA","SP")
    var list_cust_seg_value = arrayOf("111260004","111260000","111260001","111260002","111260003")
    var list_firm_type = arrayOf("Select Firm type","Proprietorship","Partnership","Pvt Ltd","Ltd","Trust","Individual")

    var list_firm_type_value = arrayOf("","1","2","3","4","5","6")
    var list_of_state = arrayOf("Select State","Andhra Pradesh","Bihar","Delhi"
            ,"Gujarat","Haryana","Jammu and Kashmir","Karnataka"
            ,"Kerala", "Madhya Pradesh","Maharashtra","Odisha", "Other*",
            "Punjab","Rajasthan","Tamil Nadu", "Telangana","Uttar Pradesh"
            ,"Uttarakhand","West Bengal")

    var list_of_boolean = arrayOf("Select Option","Yes","No")
    var list_of_booleanvalue = arrayOf("","True","False")

    var list_state_code = arrayOf("","100009","100021","100004", "100015","100008",
            "100011","100007", "100012","100014","100002","100026",
            "100017","100025", "100010", "100003","100023","100006",
            "100016","100013")

    var country_name = arrayOf("India")

    var ext_serv_one = arrayOf("Jio", "ACT Fibernet","N.A",
            "Others","Airtel","Aircel","BSNL", "Hathway","MTNL","Nextra",
            "Reliance Communications","Sify","Tata Communications","Tata DOCOMO",
            "Tikona Infinet","Vodafone")

    var ext_serv_one_value = arrayOf("111260000",
            "569480014","569480012","569480013","569480000","569480002",
            "569480003","569480004","569480005","569480006","569480007",
            "569480008","569480009","569480010","569480011","569480001")

    var ext_serv = arrayOf(
            "Internet", "Data Center Services","VOIP Services","Other Services")
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    var list_of_media = arrayOf("Select Media","Fibre","RF")

    var ext_serv_val = arrayOf("569480000","569480001","569480002","569480003")

    var disqualifyCode = arrayOf("569480000","569480002","569480003","569480004","569480007","569480008",
    "569480010","569480011","569480012","569480014","569480015","569480016")

    var disqualify = arrayOf("Disqualify","Not Interested","Delay in Response","Pricing issue","Location Is Non-RFS",
    "Existing Customer","Customer Is defaulter","Broadband/Home user","Duplicate Lead","Incomplete information",
    "Wrong Number","Not Contactable","Language Barrier")

    override fun onCreate(savedInstanceState: Bundle?) {
       // setTheme(R.style.AppThemee);
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.update_lead_demo_fragment)
        searchtoolbarlead_update.rl_back.setOnClickListener(this)
        searchtoolbarlead_update.tv_lang.text= AppConstants.UPDATE_LEAD

        val extras = intent.extras
        if (extras != null) {
            str_lead_Id = extras.getString("LeadId")
            str_lead_status = extras.getString("LeadStatus")
        }

        init()
        itemListener()
        listener()
        flr.visibility= View.VISIBLE
        getLead()
      //  getOtherCity()

        tv_qualify.setOnClickListener { v ->
          qualifyLead()
      }

      et_disquslify.setOnClickListener { v ->
          val dis :String = et_disquslify.text.toString()
          if(strDisqualify==""||strDisqualify==""||dis==("Disqualify")){
              et_disquslify.setOnClickListener { sp_disqualify.performClick() }
              sp_disqualify.onItemSelectedListener = this

           //   Toast.makeText(this,"Please Select Reason",Toast.LENGTH_LONG).show()
          }
      }

        flr.setOnClickListener { v ->
            val fragment = FlrFrag()
            showFragmentflr(fragment)
        }

        oppurtunity.setOnClickListener { v ->
            val intent = Intent (this@UpdateLeadActivity, OpportunityActivity::class.java)
            intent.putExtra("LeadId", str_lead_Id)
            startActivity(intent)
            finish()
        }
    }

    fun validateEmail() {
        val email :String = updatelayout_contactinfo_layout.et_emailid.text.toString()
        if (email.matches(emailPattern.toRegex())) {
            Toast.makeText(applicationContext, "Valid email address",
                    Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(applicationContext, "Invalid email address",
                    Toast.LENGTH_SHORT).show()
        }
    }

    fun showFragmentflr(fragment: FlrFrag){
        val fram = supportFragmentManager.beginTransaction()
        val mBundle = Bundle()
        mBundle.putString("LeadId",str_lead_Id)
        mBundle.putString("Mobile",strMobile)
        fragment.arguments = mBundle
        fram.replace(R.id.fragment_lead,fragment)
        fram.commit()
    }
    fun locked(){
       // if(str_lead_status.equals("Qualified") || str_lead_status.equals("DisQualified")){
            updatelayout_contactinfo_layout.et_uptopic.isFocusableInTouchMode= false
            updatelayout_contactinfo_layout.et_uptopic.isEnabled= false

            updatelayout_contactinfo_layout.et_business_seg.isFocusableInTouchMode= false
            updatelayout_contactinfo_layout.et_saluation.isFocusableInTouchMode= false
            updatelayout_contactinfo_layout.et_customer_seg.isFocusableInTouchMode= false
            updatelayout_contactinfo_layout.et_contact_person.isFocusableInTouchMode= false
            updatelayout_contactinfo_layout.et_upmobile_num.isFocusableInTouchMode= false
            updatelayout_contactinfo_layout.et_customer_seg.isFocusableInTouchMode= false
            updatelayout_contactinfo_layout.et_emailid.isFocusableInTouchMode= false
            updatelayout_lead_installation_address.et_add_build_num.isFocusableInTouchMode = false
            updatelayout_lead_installation_address.et_add_floor.isFocusableInTouchMode = false
            updatelayout_lead_installation_address.et_pin_code.isFocusableInTouchMode = false
            update_lead_contact_address.et_cnt_buildng_num.isFocusableInTouchMode = false
            update_lead_contact_address.et_cnt_pin_code.isFocusableInTouchMode = false
            update_lead_contact_address.et_cnt_floor.isFocusableInTouchMode = false
            updatelayout_lead_company_details.et_company_name.isFocusableInTouchMode = false
            updatelayout_lead_company_details.et_job_title.isFocusableInTouchMode = false
            binding.updatelayoutLeadRemarks.etUpleadRemark.isFocusableInTouchMode = false
            update_lead_contact_address.et_cntspbuilding_num.isFocusableInTouchMode = false
            update_lead_contact_address.et_cntspbuilding_name.isFocusableInTouchMode = false
            updatelayout_lead_installation_address.et_upspefc_area.isFocusableInTouchMode = false
            updatelayout_lead_installation_address.et_upspecfcbuilding_num.isFocusableInTouchMode = false

        flr.visibility= View.GONE
            tv_qualify.visibility= View.GONE
            dis.visibility = View.GONE
            lead_updatesave.visibility = View.GONE
            et_saluation.isEnabled=false
            et_updtcompany.isEnabled=false
            et_uprelation.isEnabled=false
            et_upgroup.isEnabled=false
            et_sub_busiessseg.isEnabled=false
            et_customer_seg.isEnabled=false
            et_country.isEnabled=false
            et_lead_name.isEnabled=false

        et_state.isEnabled=false
        et_add_city.isEnabled=false
        et_cntarea.isEnabled=false
        et_building.isEnabled=false
        et_cnt_country.isEnabled=false
        et_cnt_state.isEnabled=false
        et_cnt_city.isEnabled=false
        et_contacttarea.isEnabled=false
        et_cnt_building.isEnabled=false
        et_crt_wrk.isEnabled = false
        et_firm_type.isEnabled=false
        et_indus_type.isEnabled=false
        et_media.isEnabled=false
        et_ext_srv.isEnabled=false
        et_ext_srv_two.isEnabled=false
        et_is_cus.isEnabled=false
        et_indus_firewl.isEnabled=false
        et_vpn_srv.isEnabled=false
        et_service_pv_one.isEnabled=false
        et_srv_pv_two.isEnabled=false
        et_cust_void.isEnabled=false
        et_cust_wifi.isEnabled=false
        dis.visibility=View.GONE
        et_disquslify.visibility = View.GONE
        sp_disqualify.isEnabled = false
        sp_disqualify.visibility = View.GONE
        sp_work_location.isEnabled = false
     //   oppurtunity.visibility = View.GONE

      //  }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.flr_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_flr-> {
                Toast.makeText(applicationContext, "click on setting", Toast.LENGTH_LONG).show()
                true
            }
            R.id.menu_oppurtunity ->{
                Toast.makeText(applicationContext, "click on share", Toast.LENGTH_LONG).show()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun init(){
        lead_updatesave.setOnClickListener {
            val remark:String = updatelayout_lead_remarks.et_uplead_remark.text.toString()

            val company:String =updatelayout_lead_company_details.et_company_name.text.toString()
            val firmtype = str_firm_type.toString()
            val industrytype= str_industry_type.toString()
            val jbtitle:String = updatelayout_lead_company_details.et_job_title.text.toString()

            val area = str_add_area.toString()
            val addres_build =str_inst_building_nm
            val city = str_ContactcityCode.toString()
            val sparea = update_lead_contact_address.et_cntspbuilding_num.text.toString()
            val spbuilg = update_lead_contact_address.et_cntspbuilding_name.text.toString()
            val floor =update_lead_contact_address.et_cnt_floor.text.toString()
            val pincode =  update_lead_contact_address.et_cnt_pin_code.text.toString()
            val state = strcontact_stateCode.toString()
            val buildingnum = update_lead_contact_address.et_cnt_buildng_num.text.toString()

            val inst_area = str_inst_area.toString()
            val inst_build =str_inst_build_num.toString()
            val inst_build_num = str_inst_build_name.toString()
            val inst_city_code = str_city_code.toString()
            val inst_city =str_city.toString()
            val block = et_upcnt_block.text.toString()

            val inst_floor = updatelayout_lead_installation_address.et_add_floor.text.toString()
            val inst_pincode = updatelayout_lead_installation_address.et_pin_code.text.toString()
            val inst_buil = updatelayout_lead_installation_address.et_add_build_num.text.toString()
            val inst_block =updatelayout_lead_installation_address.et_upad_block.text.toString()
            val inst_spbuild = updatelayout_lead_installation_address.et_upspecfcbuilding_num.text.toString()
            val inst_sparea =updatelayout_lead_installation_address.et_upspefc_area.text.toString()

            val inst_state = str_inst_state.toString()
            val inst_state_code = str_state.toString()

            val other_work = str_othercity.toString()
            val other_pro_one = str_ext_serv_pro_one.toString()
            val other_pro_two=str_serv_pro_two.toString()
            val other_date = str_data.toString()
            val other_firewal = str_cust_frwl.toString()
            val other_wifi =  str_wifi.toString()
            val other_voip =  str_voip.toString()
            val other_vpn = str_cust_vpn.toString()
            val other_media = str_media.toString()
            val other_cust_one = str_cust_serv_one.toString()
            val cust_two = str_cust_serv_two.toString()
            val other_target = updatelayout_lead_other_details.et_trgt_period.text.toString()
            val general_chnl = str_lead_chnl.toString()
            val general_src= str_lead_src.toString()

            val gnl_sub = str_sub_bus.toString()
            val salutation = str_salutation.toString()
            val cnt_info_cnt_person = updatelayout_contactinfo_layout.et_contact_person.text.toString()
            val topic = updatelayout_contactinfo_layout.et_uptopic.text.toString()
            val gnl_phn_num = updatelayout_contactinfo_layout.et_upmobile_num.text.toString()
            val general_email = updatelayout_contactinfo_layout.et_emailid.text.toString()
            val genral_name = updatelayout_contactinfo_layout.et_lead_name.text.toString()
            val customer_seg = str_customer_segmentid.toString()
            val group = str_grp.toString()
            val relation = str_rltn.toString()
            val companyid = str_cmp.toString()

            if(topic.isBlank()){
                Toast.makeText(this, "Please Enter Topic", Toast.LENGTH_SHORT).show()
            }else if(gnl_sub.isBlank()||gnl_sub=="Select Sub Business Segment"){
                Toast.makeText(this, "Please Select Sub Business Segment", Toast.LENGTH_SHORT).show()
            }else if(customer_seg.isBlank()||gnl_sub=="Select Customer Segment"){
                Toast.makeText(this, "Please Select Customer Segment", Toast.LENGTH_SHORT).show()
            }else if(salutation.isBlank()||gnl_sub=="Select Salutation"){
                Toast.makeText(this, "Please Select Salutation", Toast.LENGTH_SHORT).show()
            }else if(cnt_info_cnt_person.isBlank()){
                Toast.makeText(this, "Please Enter Contact Person", Toast.LENGTH_SHORT).show()
            }else if(general_email.isBlank()){
                Toast.makeText(this, "Please Enter Email Id", Toast.LENGTH_SHORT).show()
            }else if(gnl_phn_num.isBlank()){
                Toast.makeText(this, "Please Enter Mobile Number", Toast.LENGTH_SHORT).show()
            }else if(genral_name.isBlank()){
                Toast.makeText(this, "Please Enter Lead Name", Toast.LENGTH_SHORT).show()
            }else if(companyid.isBlank()||companyid=="Select Company"){
                Toast.makeText(this, "Please Select Company", Toast.LENGTH_SHORT).show()
            }else if(group.isBlank()||group=="Select Group"){
                Toast.makeText(this, "Please enter Group", Toast.LENGTH_SHORT).show()
            }else if(relation.isBlank()||relation=="Select Relation"){
                Toast.makeText(this, "Please Select Relation", Toast.LENGTH_SHORT).show()
            }else if(general_chnl.isBlank()||general_chnl=="Select Channel"){
                Toast.makeText(this, "Please Select Channel", Toast.LENGTH_SHORT).show()
            }else if(general_src.isBlank()){
                Toast.makeText(this, "Please Select Source", Toast.LENGTH_SHORT).show()
            }else if(inst_state.isBlank()||inst_state=="Select State"){
                Toast.makeText(this, "Please Select State", Toast.LENGTH_SHORT).show()
            }else if(inst_city.isBlank()||inst_city=="Select City"){
                Toast.makeText(this, "Please Select City", Toast.LENGTH_SHORT).show()
            }else if(inst_area.isBlank()||inst_area=="Select Area"){
                Toast.makeText(this, "Please Select Area", Toast.LENGTH_SHORT).show()
            }else if(inst_build_num.isBlank()||inst_build_num=="Select Building"){
                Toast.makeText(this, "Please Select Building Name", Toast.LENGTH_SHORT).show()
            }else if(inst_build.isBlank()){
                Toast.makeText(this, "Please enter Building No. ", Toast.LENGTH_SHORT).show()
            }else if(inst_block.isBlank()){
                Toast.makeText(this, "Please enter Block", Toast.LENGTH_SHORT).show()
            }else if(inst_floor.isBlank()){
                Toast.makeText(this, "Please enter Floor", Toast.LENGTH_SHORT).show()
            }else if(inst_pincode.isBlank()){
                Toast.makeText(this, "Please enter Pincode", Toast.LENGTH_SHORT).show()
            }else if(state.isBlank()||state=="Select State"){
                Toast.makeText(this, "Please Select State", Toast.LENGTH_SHORT).show()
            }else if(city.isBlank()||city=="Select City"){
                Toast.makeText(this, "Please Select City", Toast.LENGTH_SHORT).show()
            }else if(area.isBlank()||area=="Select Area"){
                Toast.makeText(this, "Please Select Area", Toast.LENGTH_SHORT).show()
            }else if(inst_build_num.isBlank()||inst_build_num=="Select Building Name"){
                Toast.makeText(this, "Please enter Building Name ", Toast.LENGTH_SHORT).show()
            }else if(block.isBlank()){
                Toast.makeText(this, "Please enter Block", Toast.LENGTH_SHORT).show()
            }else if(floor.isBlank()){
                Toast.makeText(this, "Please enter Floor", Toast.LENGTH_SHORT).show()
            }else if(pincode.isBlank()){
                Toast.makeText(this, "Please enter Pincode ", Toast.LENGTH_SHORT).show()
            }else if(firmtype.isBlank()||firmtype=="Select Firm Type"){
                Toast.makeText(this, "Please Select Firm Type ", Toast.LENGTH_SHORT).show()
            }else if(industrytype.isBlank()||industrytype=="Select Industry"){
                Toast.makeText(this, "Please Select Industry Type", Toast.LENGTH_SHORT).show()
            }else if(jbtitle.isBlank()){
                Toast.makeText(this, "Please enter Job Title", Toast.LENGTH_SHORT).show()
            }else if(other_media.isBlank()||other_media=="Select Media"){
                Toast.makeText(this, "Please Select Media ", Toast.LENGTH_SHORT).show()
            }else if(other_pro_one.isBlank()||other_pro_one=="Select Existing Service Provider"){
                Toast.makeText(this, "Please Select Existing Service Provider One", Toast.LENGTH_SHORT).show()
            }else if(other_pro_two.isBlank()||other_pro_two=="Select Existing Service Provider"){
                Toast.makeText(this, "Please Select Existing Service Provider Two", Toast.LENGTH_SHORT).show()
            } else if(other_work.isBlank()){
                Toast.makeText(this, "Please Select Current Work location ", Toast.LENGTH_SHORT).show()
            }else if(other_date.isBlank()){
                Toast.makeText(this, "Please Select Data Center ", Toast.LENGTH_SHORT).show()
            }else if(other_firewal.isBlank()||other_firewal=="Select Option"){
                Toast.makeText(this, "Please Select Firewall ", Toast.LENGTH_SHORT).show()
            } else if(other_vpn.isBlank()){
                Toast.makeText(this, "Please Select Vpn Services ", Toast.LENGTH_SHORT).show()
            }else if(other_cust_one.isBlank()||other_cust_one=="Select Existing Service Provider"){
                Toast.makeText(this, "Please Select Existing Service Provider One", Toast.LENGTH_SHORT).show()
            }else if(cust_two.isBlank()||cust_two=="Select Existing Service Provider"){
                Toast.makeText(this, "Please Select Existing Service Provider Two", Toast.LENGTH_SHORT).show()
            }
            else if(other_voip.isBlank()||other_voip=="Select Option"){
                Toast.makeText(this, "Please Select Voip ", Toast.LENGTH_SHORT).show()
            }else if(other_wifi.isBlank()||other_wifi=="Select Option"){
                Toast.makeText(this, "Please Select Manage Wifi ", Toast.LENGTH_SHORT).show()
            }else if(other_target.isBlank()){
                Toast.makeText(this, "Please Select Target Intallation Period ", Toast.LENGTH_SHORT).show()
            }
            else {
                     update_Lead(remark,company,firmtype,industrytype,jbtitle,area,addres_build,city,
                        sparea, spbuilg,floor,pincode,state,inst_area,inst_build,inst_build_num,
                        inst_city_code,inst_city,block,inst_floor,inst_pincode,inst_buil,inst_block,
                        inst_spbuild,inst_sparea,inst_state,inst_state_code,other_work,other_pro_one,
                        other_pro_two,other_date,other_firewal,other_wifi,other_voip,other_vpn,other_media,
                        other_cust_one,cust_two,other_target,general_chnl,general_src,gnl_sub,
                        salutation,cnt_info_cnt_person,topic,gnl_phn_num,general_email,genral_name,
                        customer_seg, group,relation,companyid,buildingnum)
            }
        }


    }
    fun itemListener(){
        updatelayout_contactinfo_layout.et_business_seg.setText(AppConstants.BUSINESS)
        updatelayout_contactinfo_layout.et_sub_busiessseg.setOnClickListener { updatelayout_contactinfo_layout.sp_sub_bus.performClick() }
        updatelayout_contactinfo_layout.sp_sub_bus.onItemSelectedListener = this
        updatelayout_contactinfo_layout.et_saluation.setOnClickListener { updatelayout_contactinfo_layout.sp_salutation.performClick() }
        updatelayout_contactinfo_layout.sp_salutation.onItemSelectedListener = this
        updatelayout_channel_source.et_lead_channel.setOnClickListener { updatelayout_channel_source.sp_leadchnl.performClick() }
        updatelayout_channel_source.sp_leadchnl.onItemSelectedListener = this
        updatelayout_channel_source.et_lead_source.setOnClickListener { updatelayout_channel_source.sp_lead_src.performClick() }
        updatelayout_channel_source.sp_lead_src.onItemSelectedListener = this
        updatelayout_contactinfo_layout.et_customer_seg.setOnClickListener { updatelayout_contactinfo_layout.sp_cust_seg.performClick() }
        updatelayout_contactinfo_layout.sp_cust_seg.onItemSelectedListener = this
        et_disquslify.setOnClickListener { sp_disqualify.performClick() }
        sp_disqualify.onItemSelectedListener = this
        updatelayout_lead_company_details.et_indus_type.setOnClickListener { updatelayout_lead_company_details.sp_industype.performClick() }
        updatelayout_lead_company_details.sp_industype.onItemSelectedListener = this
        updatelayout_lead_company_details.et_firm_type.setOnClickListener { updatelayout_lead_company_details.sp_firm_type.performClick() }
        updatelayout_lead_company_details.sp_firm_type.onItemSelectedListener = this
        updatelayout_lead_other_details.et_cust_void.setOnClickListener { updatelayout_lead_other_details.sp_cst_voip.performClick() }
        updatelayout_lead_other_details.sp_cst_voip.onItemSelectedListener = this
        updatelayout_lead_installation_address.et_country.setOnClickListener { updatelayout_lead_installation_address.sp_cntry.performClick() }
        updatelayout_lead_installation_address.sp_cntry.onItemSelectedListener = this
        updatelayout_lead_installation_address.et_state.setOnClickListener { updatelayout_lead_installation_address.sp_state.performClick() }
        updatelayout_lead_installation_address.sp_state.onItemSelectedListener = this
        updatelayout_lead_installation_address.et_add_city.setOnClickListener { updatelayout_lead_installation_address.sp_city.performClick() }
        updatelayout_lead_installation_address.sp_city.onItemSelectedListener = this
        updatelayout_lead_installation_address.et_cntarea.setOnClickListener { updatelayout_lead_installation_address.sp_cnarea.performClick() }
        updatelayout_lead_installation_address.sp_cnarea.onItemSelectedListener = this
        updatelayout_lead_installation_address.et_building.setOnClickListener { updatelayout_lead_installation_address.sp_building_nm.performClick() }
        updatelayout_lead_installation_address.sp_building_nm.onItemSelectedListener = this

        update_lead_contact_address.et_cnt_state.setOnClickListener{
            update_lead_contact_address.sp_cnt_state.performClick()
        }
        update_lead_contact_address.sp_cnt_building_nm.onItemSelectedListener = this
        update_lead_contact_address.et_cnt_building.setOnClickListener{
            update_lead_contact_address.sp_cnt_building_nm.performClick()
        }
        update_lead_contact_address.sp_cnt_state.onItemSelectedListener = this
        update_lead_contact_address.et_cnt_city.setOnClickListener{
            update_lead_contact_address.sp_cnt_city.performClick()
        }
        update_lead_contact_address.sp_cnt_city.onItemSelectedListener = this
        binding.updateLeadContactAddress.etContacttarea.setOnClickListener{
            binding.updateLeadContactAddress.spCntCnarea.performClick()
        }
        binding.updateLeadContactAddress.spCntCnarea.onItemSelectedListener = this


        updatelayout_lead_other_details.et_ext_srv.setOnClickListener { updatelayout_lead_other_details.sp_ex_serv.performClick() }
        updatelayout_lead_other_details.sp_ex_serv.onItemSelectedListener = this
        updatelayout_lead_other_details.et_ext_srv_two.setOnClickListener { updatelayout_lead_other_details.sp_ext_serv_two.performClick() }
        updatelayout_lead_other_details.sp_ext_serv_two.onItemSelectedListener = this

        updatelayout_lead_other_details.et_service_pv_one.setOnClickListener { updatelayout_lead_other_details.sp_serv_pro_one.performClick() }
        updatelayout_lead_other_details.sp_serv_pro_one.onItemSelectedListener = this
        updatelayout_lead_other_details.et_srv_pv_two.setOnClickListener { updatelayout_lead_other_details.sp_serv_pro_two.performClick() }
        updatelayout_lead_other_details.sp_serv_pro_two.onItemSelectedListener = this

        updatelayout_lead_other_details.et_indus_firewl.setOnClickListener { updatelayout_lead_other_details.sp_intrs_frwal.performClick() }
        updatelayout_lead_other_details.sp_intrs_frwal.onItemSelectedListener = this

        updatelayout_lead_other_details.et_vpn_srv.setOnClickListener { updatelayout_lead_other_details.sp_vpn_serv.performClick() }
        updatelayout_lead_other_details.sp_vpn_serv.onItemSelectedListener = this

        updatelayout_lead_other_details.et_cust_wifi.setOnClickListener { updatelayout_lead_other_details.sp_cust_wifi.performClick() }
        updatelayout_lead_other_details.sp_cust_wifi.onItemSelectedListener = this

        updatelayout_lead_other_details.et_media.setOnClickListener { updatelayout_lead_other_details.sp_media.performClick() }
        updatelayout_lead_other_details.sp_media.onItemSelectedListener = this

        updatelayout_lead_other_details.et_is_cus.setOnClickListener { updatelayout_lead_other_details.sp_intrsteddata_center.performClick() }
        updatelayout_lead_other_details.sp_intrsteddata_center.onItemSelectedListener = this
        updatelayout_lead_other_details.et_crt_wrk.setOnClickListener { updatelayout_lead_other_details.sp_work_location.performClick() }
        updatelayout_lead_other_details.sp_work_location.onItemSelectedListener = this

        updatelayout_contactinfo_layout.et_updtcompany.setOnClickListener { updatelayout_contactinfo_layout.sp_cmpny.performClick() }
        updatelayout_contactinfo_layout.sp_cmpny.onItemSelectedListener = this
        updatelayout_contactinfo_layout.et_upgroup.setOnClickListener { updatelayout_contactinfo_layout.sp_upgroup.performClick() }
        updatelayout_contactinfo_layout.sp_upgroup.onItemSelectedListener = this
        updatelayout_contactinfo_layout.et_uprelation.setOnClickListener { updatelayout_contactinfo_layout.sp_rltn.performClick() }
        updatelayout_contactinfo_layout.sp_rltn.onItemSelectedListener = this
    }




    fun listener(){
        lead_updatesave.visibility = View.VISIBLE
        linearcontactinfo.visibility = View.VISIBLE
        lineartwo.setOnClickListener { v ->
            linearcontactinfo.visibility = View.VISIBLE
            linear_insta_addres.visibility = View.GONE
            linadd.visibility = View.GONE
            linear_contact_person_address.visibility = View.GONE
            linear_companydetails.visibility= View.GONE
            linearother_details.visibility = View.GONE
            linearremark_details.visibility = View.GONE
        }
        linearthree.setOnClickListener { v ->
            linearcontactinfo.visibility = View.GONE
            linear_insta_addres.visibility = View.VISIBLE
            linadd.visibility = View.GONE
            linear_contact_person_address.visibility = View.GONE
            linear_companydetails.visibility= View.GONE
             linearother_details.visibility = View.GONE
            linearremark_details.visibility = View.GONE
        }
        linearfouraddres.setOnClickListener { v ->
            linearcontactinfo.visibility = View.GONE
            linear_insta_addres.visibility = View.GONE
            linadd.visibility = View.VISIBLE
            linear_contact_person_address.visibility = View.GONE
            linear_companydetails.visibility= View.GONE
             linearother_details.visibility = View.GONE
            linearremark_details.visibility = View.GONE
        }
        linearfive.setOnClickListener { v ->
            linearcontactinfo.visibility = View.GONE
            linear_insta_addres.visibility = View.GONE
            linadd.visibility = View.GONE
            linear_contact_person_address.visibility = View.VISIBLE
            linear_companydetails.visibility= View.GONE
            linearother_details.visibility = View.GONE
            linearremark_details.visibility = View.GONE
        }
        linearsix.setOnClickListener { v ->
            linearcontactinfo.visibility = View.GONE
            linear_insta_addres.visibility = View.GONE
            linadd.visibility = View.GONE
            linear_contact_person_address.visibility = View.GONE
            linear_companydetails.visibility= View.VISIBLE
             linearother_details.visibility = View.GONE
            linearremark_details.visibility = View.GONE
        }

        lineareight.setOnClickListener { v ->
            linearcontactinfo.visibility = View.GONE
            linear_insta_addres.visibility = View.GONE
            linadd.visibility = View.GONE
            linear_contact_person_address.visibility = View.GONE
            linear_companydetails.visibility= View.GONE
            linearother_details.visibility = View.VISIBLE
            linearremark_details.visibility = View.GONE
        }
        linearnine.setOnClickListener { v ->
            linearcontactinfo.visibility = View.GONE
            linear_insta_addres.visibility = View.GONE
            linadd.visibility = View.GONE
            linear_contact_person_address.visibility = View.GONE
            linear_companydetails.visibility= View.GONE
             linearother_details.visibility = View.GONE
            linearremark_details.visibility = View.VISIBLE
        }
    }


    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.rl_back -> {
                val fragment = GetAllLeadFrag()
                showFragment(fragment)
            }
        }
    }

    fun update_Lead(
        remark: String,
        company: String,
        firmtype: String,
        industrytype: String,
        jbtitle: String,
        area: String,
        addres_build: String?,
        city: String,
        sparea: String,
        spbuilg: String,
        floor: String,
        pincode: String,
        state: String,
        inst_area: String,
        inst_build: String,
        inst_build_num: String,
        inst_city_code: String,
        inst_city: String,
        block: String,
        inst_floor: String,
        inst_pincode: String,
        inst_buil: String,
        inst_block: String,
        inst_spbuild: String,
        inst_sparea: String,
        inst_state: String,
        inst_state_code: String,
        other_work: String,
        other_pro_one: String,
        other_pro_two: String,
        other_date: String,
        other_firewal: String,
        other_wifi: String,
        other_voip: String,
        other_vpn: String,
        other_media: String,
        other_cust_one: String,
        cust_two: String,
        other_target: String,
        general_chnl: String,
        general_src: String,
        gnl_sub: String,
        salutation: String,
        cnt_info_cnt_person: String,
        topic: String,
        gnl_phn_num: String,
        general_email: String,
        genral_name: String,
        customer_seg: String,
        group: String,
        relation: String,
        companyid: String,
        buildingnum: String?
    ) {
        inAnimation = AlphaAnimation(0f, 1f)
        inAnimation?.duration =200
        binding.updateleadProgressLayout.progressOverlay.animation = inAnimation
        binding.updateleadProgressLayout.progressOverlay.visibility = View.VISIBLE



        val otherDetail = OtherDetail(other_work,other_pro_one,other_pro_two,other_date.toBoolean(),
                other_firewal.toBoolean(),other_wifi.toBoolean(),
                other_voip.toBoolean(),other_vpn.toBoolean(),other_media,other_cust_one,cust_two,trgtdate)

        val companyDetail = CompanyDetail(company,firmtype,industrytype,jbtitle)

        val contactAddress= ContactAddress(str_add_area,addres_build, str_ContactcityCode,
                "10001",floor,pincode,buildingnum,strcontact_stateCode,
                sparea,spbuilg,block)
        var specific=""
        if(general_src=="Other"){
            specific = "Other"
        }

        val installationAddress= InstallationAddress(inst_block,inst_area,inst_build,
                inst_city_code,"10001",inst_floor,inst_pincode,inst_buil,"0",
                inst_sparea,inst_spbuild,inst_state)
        val createLeadRequest = CreateLeadRequest(Constants.UPDATE_LEAD,Constants.AUTH_KEY,
                str_lead_Id,companyDetail,
                contactAddress,installationAddress,"Business",str_cmp,
                cnt_info_cnt_person,str_customer_segmentid,general_email,
                "3",genral_name,str_grp,genral_name,general_chnl,general_src,topic,
                gnl_phn_num,otherDetail,"Target@2021#@","",str_rltn,
                remark,salutation,specific,gnl_sub,"manager1")

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.createLead(createLeadRequest)
        call.enqueue(object : Callback<CreateLeadResponse?> {
            override fun onResponse(call: Call<CreateLeadResponse?>, response: Response<CreateLeadResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    val img = response.body()!!.Response.Message

                    if(response.body()?.Response?.StatusCode=="200") {
                        try {
                            outAnimation = AlphaAnimation(1f, 0f)
                            inAnimation?.duration = 200
                            binding.updateleadProgressLayout.progressOverlay.animation = outAnimation
                            binding.updateleadProgressLayout.progressOverlay.visibility = View.GONE

                            Log.e("image", img)
                            Toast.makeText(this@UpdateLeadActivity, img, Toast.LENGTH_SHORT).show()
                            val fragmentB = GetAllLeadFrag()
                            supportFragmentManager.beginTransaction()
                                    .replace(R.id.fragment_lead, fragmentB, "fragmnetId")
                                    .commit()

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }else{
                        Toast.makeText(this@UpdateLeadActivity, img, Toast.LENGTH_SHORT).show()

                    }
                }
            }

            override fun onFailure(call: Call<CreateLeadResponse?>, t: Throwable) {
                binding.updateleadProgressLayout.progressOverlay.visibility=View.GONE
                Log.e("RetroError", t.toString())
            }
        })
    }

    @SuppressLint("SetTextI18n")
    fun  Calender(){
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        updatelayout_lead_other_details.et_trgt_period.setOnClickListener {

            val dpd = DatePickerDialog(this, { view, year, monthOfYear, dayOfMonth ->
                val mn = monthOfYear+1
                updatelayout_lead_other_details.et_trgt_period.setText("$dayOfMonth-$mn-$year")
                 trgtdate = ("$year-$mn-$dayOfMonth")
            }, year, month, day)
            dpd.show()

        }
    }


    fun getLead () {
        inAnimation = AlphaAnimation(0f, 1f)
        inAnimation?.duration =200
        binding.updateleadProgressLayout.progressOverlay.animation = inAnimation
        binding.updateleadProgressLayout.progressOverlay.visibility = View.VISIBLE

        val getLeadRequest = GetLeadRequest(Constants.GET_LEADS,Constants.AUTH_KEY,str_lead_Id.toString(),"Target@2021#@","manager1")

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getLead(getLeadRequest)
        call.enqueue(object : Callback<LeadResponsee?> {
            @SuppressLint("SimpleDateFormat", "SetTextI18n")
            override fun onResponse(call: Call<LeadResponsee?>, response: Response<LeadResponsee?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        outAnimation = AlphaAnimation(1f, 0f)
                        inAnimation?.duration =200
                        binding.updateleadProgressLayout.progressOverlay.animation = outAnimation
                        binding.updateleadProgressLayout.progressOverlay.visibility = View.GONE

                        if(response.body()?.StatusCode==200) {
                            // binding.updatelayoutChannelSource.updateChannel = response.body()
                            binding.updatelayoutContactinfoLayout.updateContact = response.body()
                            binding.updatelayoutLeadCompanyDetails.updateCompany = response.body()?.Response?.get(0)?.companyDetail
                            binding.updatelayoutLeadInstallationAddress.updateAdress = response.body()?.Response?.get(0)?.installationAddress
                            binding.updatelayoutLeadOtherDetails.updateOther = response.body()?.Response?.get(0)?.otherDetail
                            binding.updateLeadContactAddress.updateContactAdd = response.body()?.Response?.get(0)?.contactAddress
                            binding.updatelayoutLeadRemarks.updateRemarks = response.body()

                            val flrStatus = response.body()?.Response?.get(0)?.FLRstatus
                            val flrdate = response.body()?.Response?.get(0)?.Estimatedclosure
                            dis.visibility = View.VISIBLE
                            if (flrStatus == "1" && flrdate != "") {
                                tv_qualify.visibility = View.VISIBLE
                            } else if (flrStatus == "3" && flrdate != "") {
                                dis.visibility = View.VISIBLE
                            }
                            val strStatus = response.body()!!.Response[0].Status
                            strCurentLocation = response.body()?.Response?.get(0)?.otherDetail?.CurrentWorkingLocation
                            strMobile = response.body()?.Response?.get(0)?.MobileNo.toString()
                            strCompany = response.body()?.Response?.get(0)?.CompanyId.toString()
                            strGroup = response.body()?.Response?.get(0)?.GroupId.toString()
                            strRelation = response.body()?.Response?.get(0)?.RelationshipId.toString()
                            strIndustry = response.body()?.Response?.get(0)?.companyDetail?.IndustryType.toString()
                            strArea = response.body()?.Response?.get(0)?.installationAddress?.InstallAreaName.toString()
                            strCity = response.body()?.Response?.get(0)?.installationAddress?.InstallCityName.toString()
                            strContactCity = response.body()?.Response?.get(0)?.contactAddress?.ContactCityName.toString()
                            strBuilding = response.body()?.Response?.get(0)?.installationAddress?.InstallBuildingName.toString()
                            strContactArea = response.body()?.Response?.get(0)?.contactAddress?.ContactAreaName.toString()
                            strContactBuilding = response.body()?.Response?.get(0)?.contactAddress?.ContactBuilding.toString()
                            getCompany(strCompany)
                            getOtherCity(strCurentLocation)
                            val strContactstate = response.body()?.Response?.get(0)?.contactAddress?.ContactStateName
                            var cntstatePosition = 0
                            list_of_state.forEachIndexed { index, s ->
                                if (s == strContactstate) cntstatePosition = index
                            }
                            val cntstateAdapter = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item, list_of_state)
                            cntstateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            update_lead_contact_address.sp_cnt_state.adapter = cntstateAdapter
                            update_lead_contact_address.sp_cnt_state.setSelection(cntstatePosition)
                            cntstateAdapter.notifyDataSetChanged()
                            getIndustryTpe()

                            val strmedia = response.body()?.Response?.get(0)?.otherDetail?.Media
                            var mediaPosition = 0
                            list_of_media.forEachIndexed { index, s ->
                                if (index == strmedia?.toInt()) {
                                    mediaPosition = index
                                    return@forEachIndexed
                                }
                            }
                            val frstname = response.body()?.Response?.get(0)?.FirstName
                            val lastname = response.body()?.Response?.get(0)?.LastName
                            et_lead_name.setText("$frstname $lastname")
                            val media = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item, list_of_media)
                            media.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            updatelayout_lead_other_details.sp_media?.adapter = media
                            updatelayout_lead_other_details.sp_media.setSelection(mediaPosition)
                            media.notifyDataSetChanged()
                            val subbus = response.body()?.Response?.get(0)?.SubBusinessSegment
                            var sybbusPosition = 0
                            list_of_subBusSegment.forEachIndexed { index, s ->
                                if (s == subbus) {
                                    sybbusPosition = index
                                    return@forEachIndexed
                                }
                            }
                            val subBusSeg = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item, list_of_subBusSegment)
                            subBusSeg.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            updatelayout_contactinfo_layout.sp_sub_bus!!.adapter = subBusSeg
                            updatelayout_contactinfo_layout.sp_sub_bus.setSelection(sybbusPosition)
                            subBusSeg.notifyDataSetChanged()

                            val customer = response.body()?.Response?.get(0)?.CustomerSegment
                            var customersegPosition = 0
                            list_cust_seg_value.forEachIndexed { index, s ->
                                if (s == customer) {
                                    customersegPosition = index
                                    return@forEachIndexed
                                }
                            }

                            val custSeg = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item, list_of_cust_segment)
                            custSeg.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            updatelayout_contactinfo_layout.sp_cust_seg!!.adapter = custSeg
                            updatelayout_contactinfo_layout.sp_cust_seg.setSelection(customersegPosition)
                            custSeg.notifyDataSetChanged()

                            val cntry = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item, country_name)
                            cntry.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            updatelayout_lead_installation_address.sp_cntry!!.adapter = cntry

                            val disqualify = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item, disqualify)
                            disqualify.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            sp_disqualify.adapter = disqualify

                            val provider1 = response.body()?.Response?.get(0)?.otherDetail?.ExistingServiceProvider1
                            var provOnePosition = 0
                            ext_serv_one_value.forEachIndexed { index, s ->
                                if (s == provider1) {
                                    provOnePosition = index
                                    return@forEachIndexed
                                }
                            }
                            val serv1 = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item, ext_serv_one)
                            serv1.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            updatelayout_lead_other_details.sp_ex_serv?.adapter = serv1
                            updatelayout_lead_other_details.sp_ex_serv.setSelection(provOnePosition)
                            serv1.notifyDataSetChanged()

                            val provider2 = response.body()?.Response?.get(0)?.otherDetail?.ExistingServiceProvider2
                            var provTwePosition = 0
                            ext_serv_one_value.forEachIndexed { index, s ->
                                if (s == provider2) {
                                    provTwePosition = index
                                    return@forEachIndexed
                                }
                            }
                            val serv2 = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item, ext_serv_one)
                            serv2.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            updatelayout_lead_other_details.sp_ext_serv_two?.adapter = serv2
                            updatelayout_lead_other_details.sp_ext_serv_two.setSelection(provTwePosition)
                            serv2.notifyDataSetChanged()

                            val servproOne = response.body()?.Response?.get(0)?.otherDetail?.ServiceFromServiceProvider1
                            var servproOnePosition = 0
                            ext_serv_val.forEachIndexed { index, s ->
                                if (s == servproOne) {
                                    servproOnePosition = index
                                    return@forEachIndexed
                                }
                            }
                            val servOneAdap = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item, ext_serv)
                            servOneAdap.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            updatelayout_lead_other_details.sp_serv_pro_one?.adapter = servOneAdap
                            updatelayout_lead_other_details.sp_serv_pro_one.setSelection(servproOnePosition)
                            servOneAdap.notifyDataSetChanged()

                            val servproTwo = response.body()?.Response?.get(0)?.otherDetail?.ServiceFromServiceProvider2
                            var servproTwoPosition = 0
                            ext_serv_val.forEachIndexed { index, s ->
                                if (s == servproTwo) {
                                    servproTwoPosition = index
                                    return@forEachIndexed
                                }
                            }
                            val servTwoAdap = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item, ext_serv)
                            servTwoAdap.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            updatelayout_lead_other_details.sp_serv_pro_two?.adapter = servTwoAdap
                            updatelayout_lead_other_details.sp_serv_pro_two.setSelection(servproTwoPosition)
                            servTwoAdap.notifyDataSetChanged()
                            val data = response.body()?.Response?.get(0)?.otherDetail?.IsDatacenter

                            val dataadpter = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item, list_of_option)
                            dataadpter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            updatelayout_lead_other_details.sp_intrsteddata_center?.adapter = dataadpter
                            if (data == true) {
                                updatelayout_lead_other_details.sp_intrsteddata_center.setSelection(1)
                            } else {
                                updatelayout_lead_other_details.sp_intrsteddata_center.setSelection(2)
                            }
                            dataadpter.notifyDataSetChanged()

                            val strFirewall = response.body()?.Response?.get(0)?.otherDetail?.IsFirewall
                            val frwaladpter = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item, list_of_option)
                            frwaladpter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            updatelayout_lead_other_details.sp_intrs_frwal?.adapter = frwaladpter

                            if (strFirewall == true) {
                                updatelayout_lead_other_details.sp_intrs_frwal.setSelection(1)
                            } else {
                                updatelayout_lead_other_details.sp_intrs_frwal.setSelection(2)
                            }
                            frwaladpter.notifyDataSetChanged()

                            val strVpnServ = response.body()?.Response?.get(0)?.otherDetail?.IsVPN
                            val vpnadpter = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item, list_of_option)
                            vpnadpter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            updatelayout_lead_other_details.sp_vpn_serv?.adapter = vpnadpter
                            if (strVpnServ == true) {
                                updatelayout_lead_other_details.sp_vpn_serv.setSelection(1)
                            } else {
                                updatelayout_lead_other_details.sp_vpn_serv.setSelection(2)
                            }
                            vpnadpter.notifyDataSetChanged()

                            val strVoip = response.body()?.Response?.get(0)?.otherDetail?.IsVOIP
                            val voipadpter = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item, list_of_option)
                            voipadpter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            updatelayout_lead_other_details.sp_cst_voip?.adapter = voipadpter
                            if (strVoip == true) {
                                updatelayout_lead_other_details.sp_cst_voip.setSelection(1)
                            } else {
                                updatelayout_lead_other_details.sp_cst_voip.setSelection(2)
                            }
                            voipadpter.notifyDataSetChanged()

                            val strManage = response.body()?.Response?.get(0)?.otherDetail?.IsManagesWiFi
                            val wifipadpter = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item, list_of_option)
                            wifipadpter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            updatelayout_lead_other_details.sp_cust_wifi?.adapter = wifipadpter
                            if (strManage == true) {
                                updatelayout_lead_other_details.sp_cust_wifi.setSelection(1)
                            } else {
                                updatelayout_lead_other_details.sp_cust_wifi.setSelection(2)
                            }
                            wifipadpter.notifyDataSetChanged()

                            val strstate = response.body()?.Response?.get(0)?.installationAddress?.InstallStateName.toString()
                            var statePosition = 0
                            list_of_state.forEachIndexed { index, s ->
                                if (s == strstate) statePosition = index
                            }
                            val stateAdapter = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item, list_of_state)
                            stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            updatelayout_lead_installation_address.sp_state!!.adapter = stateAdapter
                            updatelayout_lead_installation_address.sp_state.setSelection(statePosition)
                            stateAdapter.notifyDataSetChanged()

                            val strchannel = response.body()?.Response?.get(0)?.LeadChannel
                            var channePosition = 0
                            list_of_channel.forEachIndexed { index, s ->
                                if (s == strchannel) channePosition = index
                            }
                            val adapterchannel = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item, list_of_channel)
                            adapterchannel.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            updatelayout_channel_source.sp_leadchnl?.adapter = adapterchannel
                            updatelayout_channel_source.sp_leadchnl.setSelection(channePosition)
                            adapterchannel.notifyDataSetChanged()


                            val sal = response.body()?.Response?.get(0)?.SalutationId
                            val firm = response.body()?.Response?.get(0)?.companyDetail?.FirmType
                            var firmPosition = 0
                            list_firm_type.forEachIndexed { index, s ->
                                if (index == firm?.toInt()) {
                                    firmPosition = index
                                    return@forEachIndexed
                                }
                            }
                            val firmAdapter = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item, list_firm_type)
                            firmAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            updatelayout_lead_company_details.sp_firm_type?.adapter = firmAdapter
                            updatelayout_lead_company_details.sp_firm_type.setSelection(firmPosition)
                            firmAdapter.notifyDataSetChanged()
                            et_lead_channel.isEnabled=false
                            et_lead_source.isEnabled=false

                            var salutationPosition = 0
                            list_of_salutation_id.forEachIndexed { index, s ->
                                if (index == sal?.toInt()) {
                                    salutationPosition = index
                                    return@forEachIndexed
                                }
                            }
                            val salutationAdapter = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item, list_of_salutation)
                            salutationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            updatelayout_contactinfo_layout.sp_salutation?.adapter = salutationAdapter
                            updatelayout_contactinfo_layout.sp_salutation.setSelection(salutationPosition)
                            salutationAdapter.notifyDataSetChanged()
                            val trgtdate = response.body()?.Response?.get(0)?.otherDetail?.TargetInstallationPeriod.toString()
                            if(trgtdate.isNullOrEmpty()){

                            }else {
                                val split = trgtdate.split("-")
                                val date = split[0]
                                val month = split[1]
                                val year = split[2]
                                updatelayout_lead_other_details.et_trgt_period.setText(date + "-" + month + "-" + year)
                            }

                            if (strStatus == "1") {
                                locked()
                                oppurtunity.visibility= View.VISIBLE
                            } else if(strStatus == "2"){
                                locked()
                                oppurtunity.visibility= View.GONE
                            } else {
                                oppurtunity.visibility= View.GONE
                                Calender()
                            }
                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<LeadResponsee?>, t: Throwable) {
                binding.updateleadProgressLayout.progressOverlay.visibility=View.GONE
                Log.e("RetroError", t.toString())
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, SalesDashboard::class.java)
        startActivity(intent)
        finish()
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
                       // instryname.add("Select Industry")
                        for (item in industryList!!){
                            instryname.add(item.IndTypeName)
                            industryid.add(item.IndTypeId)
                        }
                        var industryPosition=0
                        industryid.forEachIndexed { index, s ->
                            if(s==strIndustry)industryPosition=index
                        }
                        val adapter12 = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item, instryname)
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        updatelayout_lead_company_details.sp_industype.adapter = adapter12
                        updatelayout_lead_company_details.sp_industype.setSelection(industryPosition)
                        adapter12.notifyDataSetChanged()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<GetIndustryTypeResponse?>, t: Throwable) {
                Log.e("RetroError", t.toString())
            }
        })
    }


    fun getBuilding(areaname: String, areaCode: String?) {
        val getLeadBuildingRequest = GetLeadBuildingRequest(Constants.GET_BUILDING,Constants.AUTH_KEY,areaCode.toString(),areaname,"Target@2021#@","manager1")

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getleadBuilding(getLeadBuildingRequest)
        call.enqueue(object : Callback<GetLeadBuildingResponse?> {
            override fun onResponse(call: Call<GetLeadBuildingResponse?>, response: Response<GetLeadBuildingResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        //val msg = response.body()?.Response?.Message
                        val  buildingList= response.body()?.Response?.Data
                        building = ArrayList<String>()
                        buildingCode = ArrayList<String>()
                       // building?.add("Select Building")
                        if (buildingList != null) {
                            for (item in buildingList)
                                building?.add(item.BuildingName)
                        }
                        if (buildingList != null) {
                            for(it1 in buildingList)
                                buildingCode?.add(it1.BuildingCode)
                        }

                        var buildPosition=0
                        building!!.forEachIndexed { index, s ->
                            if(s==strBuilding)buildPosition=index
                        }

                        val adapter12 = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item, building!!)
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        updatelayout_lead_installation_address.sp_building_nm.adapter = adapter12
                        updatelayout_lead_installation_address.sp_building_nm.setSelection(buildPosition)
                        adapter12.notifyDataSetChanged()

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
    fun getContactBuilding(areaname: String, areaCode: String?) {
        val getLeadBuildingRequest = GetLeadBuildingRequest(Constants.GET_BUILDING,Constants.AUTH_KEY,areaCode.toString(),areaname,"Target@2021#@","manager1")


        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getleadBuilding(getLeadBuildingRequest)
        call.enqueue(object : Callback<GetLeadBuildingResponse?> {
            override fun onResponse(call: Call<GetLeadBuildingResponse?>, response: Response<GetLeadBuildingResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        //val msg = response.body()?.Response?.Message
                        buildingList= response.body()?.Response?.Data
                        cntbuilding = ArrayList<String>()
                        cntbuildingCode = ArrayList<String>()
                      //  cntbuilding?.add("Select Building")
                        for (item in buildingList!!){
                            cntbuilding?.add(item.BuildingName)
                            cntbuildingCode?.add(item.BuildingCode)
                        }
                        var buildPosition=0
                        cntbuildingCode!!.forEachIndexed { index, s ->
                            if(s==strContactBuilding)buildPosition=index
                        }

                        val adapter12 = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item, cntbuilding!!)
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        update_lead_contact_address.sp_cnt_building_nm.adapter = adapter12
                        update_lead_contact_address.sp_cnt_building_nm.setSelection(buildPosition)
                        adapter12.notifyDataSetChanged()

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
                        companyId?.add("Select Company")
                        group = ArrayList<String>()
                        groupId = ArrayList<String>()
                        group?.add("Select Group")
                        groupId?.add("Select Group")
                        for (item in companyList!!){
                            company!!.add(item.Company_Name +"("+item.Company_ID+")")
                            companyId?.add(item.Company_ID)
                        }

                        var comPosition=0
                        companyId?.forEachIndexed { index, s ->
                            if(s== this@UpdateLeadActivity.strCompany)comPosition=index
                        }
                        for (item in companyList!!){
                            group!!.add(item.Group_Name +"("+item.Group_ID+")")
                            groupId?.add(item.Group_ID)
                        }
                        var groupPosition=0
                        groupId!!.forEachIndexed { index, s ->
                            if(s==strGroup)groupPosition=index
                        }
                        val adapter12 = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item, company!!)
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        updatelayout_contactinfo_layout.sp_cmpny.adapter = adapter12
                        updatelayout_contactinfo_layout.sp_cmpny.setSelection(comPosition)
                        adapter12.notifyDataSetChanged()
                        val adapter11 = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item, group!!)
                        adapter11.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        updatelayout_contactinfo_layout.sp_upgroup.adapter = adapter11
                        updatelayout_contactinfo_layout.sp_upgroup.setSelection(groupPosition)
                        adapter11.notifyDataSetChanged()

                    } catch (e: Exception) {
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
                                relation!!.add(item.Relationship_Name +"("+item.Relationship_ID+")")
                                relationId?.add(item.Relationship_ID)
                            }
                        }
                        var relationPosition=0
                        relationId!!.forEachIndexed { index, s ->
                            if(s==strRelation)relationPosition=index
                        }

                        val adapter12 = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item, relation!!)
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        updatelayout_contactinfo_layout.sp_rltn.adapter = adapter12
                        updatelayout_contactinfo_layout.sp_rltn.setSelection(relationPosition)
                        adapter12.notifyDataSetChanged()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<GetRelationShipResponse?>, t: Throwable) {
                Log.e("RetroError", t.toString())
            }
        })

    }

    fun getSource(str_lead_chnl: String?) {
        val getLeadSourceRequest =
                str_lead_chnl?.let {
                    GetLeadSourceRequest(Constants.GET_SOURCE,Constants.AUTH_KEY,
                            it,"manager1","Target@2021#@")
                }
        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getLeadSource(getLeadSourceRequest)
        call.enqueue(object : Callback<GetLeadSourceResp?> {
            override fun onResponse(call: Call<GetLeadSourceResp?>, response: Response<GetLeadSourceResp?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        val img = response.body()?.Response?.Message
                        img?.let { Log.e("image", it) }
                        sourceList= response.body()?.Response?.Data
                        source = ArrayList<String>()
                        for (item in sourceList!!)
                            source?.add(item.SourceName)
                        val adapter12 = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item, source!!)
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        updatelayout_channel_source.sp_lead_src.adapter = adapter12
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

    fun getArea(str_city: String?, str_city_code: String?) {
        val getLeadAreaRequest =   GetLeadAreaRequest(Constants.Get_AREA,Constants.AUTH_KEY,
                str_city_code.toString(), str_city.toString() ,"","manager1","Target@2021#@",true)

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getLeadArea(getLeadAreaRequest)
        call.enqueue(object : Callback<GetLeadAreaRes?> {
            override fun onResponse(call: Call<GetLeadAreaRes?>, response: Response<GetLeadAreaRes?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        val img = response.body()!!.Response.Message
                        Log.e("image", img)
                        areaList= response.body()!!.Response.Data
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

                        val adapter12 = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item, area!!)
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        updatelayout_lead_installation_address.sp_cnarea.adapter = adapter12
                        updatelayout_lead_installation_address.sp_cnarea.setSelection(areaPosition)
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


    fun getCntArea(str_city: String?, str_city_code: String) {
        val getLeadAreaRequest =   GetLeadAreaRequest(Constants.Get_AREA,Constants.AUTH_KEY,
                str_city_code, str_city.toString() ,"","manager1","Target@2021#@",false)


        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getLeadArea(getLeadAreaRequest)
        call.enqueue(object : Callback<GetLeadAreaRes?> {
            override fun onResponse(call: Call<GetLeadAreaRes?>, response: Response<GetLeadAreaRes?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        val img = response.body()!!.Response.Message
                        Log.e("image", img)
                        areaList= response.body()!!.Response.Data
                        cntarea = ArrayList<String>()
                        cntareaCode = ArrayList<String>()
                        cntarea?.add("")
                        cntareaCode?.add("")
                        for (item in areaList!!){
                            cntarea?.add(item.AreaName)
                            cntareaCode?.add(item.AreaCode)
                        }
                        var areaPosition=0
                        cntarea?.forEachIndexed { index, s ->
                            if(s==strContactArea)areaPosition=index
                            println("Area code ${strContactArea}")
                        }
                        val adapter12 = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item, cntarea!!)
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        binding.updateLeadContactAddress.spCntCnarea.adapter= adapter12
                        binding.updateLeadContactAddress.spCntCnarea.setSelection(areaPosition)
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


    fun getCity(str_inst_state: String) {
        val getCityRequest = GetCityRequest(Constants.GET_CITY,Constants.AUTH_KEY,"Target@2021#@",str_inst_state,"manager1")

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
                        }
                        val adapter12 = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item, city!!)
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        updatelayout_lead_installation_address.sp_city.adapter = adapter12
                        updatelayout_lead_installation_address.sp_city.setSelection(cityPosition)
                        adapter12.notifyDataSetChanged()
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

    fun getContactCity(strcontact_stateCode: String) {
        val getCityRequest = GetCityRequest(Constants.GET_CITY,Constants.AUTH_KEY,"Target@2021#@",strcontact_stateCode,"manager1")

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getCityList(getCityRequest)
        call.enqueue(object : Callback<GetCityResponse?> {
            override fun onResponse(call: Call<GetCityResponse?>, response: Response<GetCityResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        val img = response.body()!!.Response.Message
                        Log.e("image", img)
                       val cityList = response.body()!!.Response.Data
                        city = ArrayList<String>()
                        cityCode = ArrayList<String>()
                        city?.add("Select City")
                        cityCode?.add("")
                        for (item in cityList) {
                            city?.add(item.CityName)
                            cityCode?.add(item.CityCode)
                        }
                        var cityPosition=0
                        city?.forEachIndexed { index, s ->
                            if(s==strContactCity)cityPosition=index
                        }
                        val adapter12 = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item, city!!)
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        update_lead_contact_address.sp_cnt_city.adapter = adapter12
                        update_lead_contact_address.sp_cnt_city.setSelection(cityPosition)
                        adapter12.notifyDataSetChanged()
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
        if (parent?.id == R.id.sp_sub_bus) {
            updatelayout_contactinfo_layout.et_sub_busiessseg.setText(list_of_subBusSegment.get(position))
            str_sub_bus = list_of_subBusSegment.get(position)
        }else if(parent?.id == R.id.sp_salutation){
            updatelayout_contactinfo_layout.et_saluation.setText(list_of_salutation.get(position))
            str_salutation = list_of_salutation_id.get(position )
        }else if(parent?.id ==R.id.sp_leadchnl){
            updatelayout_channel_source.et_lead_channel.setText(list_of_channel.get(position))
            str_lead_chnl = list_of_channel.get(position)
            getSource(str_lead_chnl)
        }else if(parent?.id ==R.id.sp_lead_src){
            updatelayout_channel_source.et_lead_source.setText(source?.get(position))
            str_lead_src = source?.get(position)
        }else if(parent?.id == R.id.sp_cnarea){
            updatelayout_lead_installation_address.et_cntarea.setText(area?.get(position))
            str_inst_area = areaCode?.get(position )
            val areaname: String = area?.get(position).toString()
            getBuilding(areaname,str_inst_area)
            if(areaname=="Other"){
                updatelayout_lead_installation_address.et_upspefc_area.visibility=View.VISIBLE
            }else{
                updatelayout_lead_installation_address.et_upspefc_area.visibility=View.GONE

            }
        }else if(parent?.id == R.id.sp_cnt_cnarea){
            update_lead_contact_address.et_contacttarea.setText(cntarea?.get(position))
            str_add_area = cntareaCode?.get(position )
            val cntareaname = cntarea?.get(position).toString()
            if(cntareaname=="Other"){
                update_lead_contact_address.et_cntspbuilding_num.visibility=View.VISIBLE
            }else{
                update_lead_contact_address.et_cntspbuilding_num.visibility=View.GONE
            }
            if(str_add_area!=null || str_add_area!=null) {
                getContactBuilding(cntareaname, str_add_area)
            }
        }
        else if(parent?.id == R.id. sp_cnt_building_nm){
            update_lead_contact_address.et_cnt_building.setText(cntbuilding?.get(position))
            str_inst_building_nm =  cntbuildingCode?.get(position )
            val buildingname = cntbuilding?.get(position)
            if(buildingname=="Other"){
                update_lead_contact_address.et_cntspbuilding_name.visibility=View.VISIBLE
            }else{
                update_lead_contact_address.et_cntspbuilding_name.visibility=View.GONE
            }

        }
        else if(parent?.id == R.id.sp_building_nm) {
            updatelayout_lead_installation_address.et_building.setText(building?.get(position))
            str_inst_build_num = buildingCode?.get(position)
            str_inst_build_name =  building?.get(position )
            val buildingname = building?.get(position)
            if(buildingname=="Other"){
                updatelayout_lead_installation_address.et_upspecfcbuilding_num.visibility=View.VISIBLE
            }else{
                updatelayout_lead_installation_address.et_upspecfcbuilding_num.visibility=View.GONE
            }
        }
        else if(parent?.id == R.id.sp_city){
            updatelayout_lead_installation_address.et_add_city.setText(city?.get(position))
            str_city = city?.get(position).toString()
            str_city_code =  cityCode?.get(position )
            getArea(str_city, str_city_code)
        }else if(parent?.id == R.id.sp_cnt_city){
            update_lead_contact_address.et_cnt_city.setText(city?.get(position))
            str_Contactcity = city?.get(position).toString()
            str_ContactcityCode =  cityCode?.get(position)
            getCntArea(str_Contactcity, str_ContactcityCode.toString())
        }
       else if(parent?.id == R.id.sp_cntry){
            updatelayout_lead_installation_address.et_country.setText(country_name.get(position))
            str_inst_country = country_name.get(position)
        }else if(parent?.id == R.id.sp_cust_seg){
            updatelayout_contactinfo_layout.et_customer_seg.setText(list_of_cust_segment.get(position))
           str_customer_segmentid = list_cust_seg_value.get(position )
        }else if(parent?.id == R.id.sp_cust_wifi){
            updatelayout_lead_other_details.et_cust_wifi.setText(list_of_option.get(position))
            str_wifi =  list_of_booleanvalue.get(position )
        }else if(parent?.id == R.id.sp_ex_serv){
            updatelayout_lead_other_details.et_ext_srv.setText(ext_serv_one.get(position))
            str_ext_serv_pro_one =  ext_serv_one_value.get(position )
        }
        else if(parent?.id == R.id.sp_ext_serv_two){
            updatelayout_lead_other_details.et_ext_srv_two.setText(ext_serv_one.get(position))
           str_serv_pro_two =  ext_serv_one_value.get(position )
        }else if(parent?.id == R.id.sp_serv_pro_one){
            updatelayout_lead_other_details.et_service_pv_one.setText(ext_serv.get(position))
           str_cust_serv_one = ext_serv_val.get(position )
        }else if(parent?.id == R.id.sp_serv_pro_two){
            updatelayout_lead_other_details.et_srv_pv_two.setText(ext_serv.get(position))
            str_cust_serv_two = ext_serv_val.get(position )
        }else if(parent?.id == R.id.sp_state){
            updatelayout_lead_installation_address.et_state.setText(list_of_state.get(position))
            str_state = list_of_state.get(position)
            Log.e("State",str_state.toString())
            str_inst_state= list_state_code.get(position)
            Log.e("State",str_inst_state.toString())
            getCity(str_inst_state.toString())
        }else if(parent?.id == R.id.sp_cnt_state){
            update_lead_contact_address.et_cnt_state.setText(list_of_state.get(position))
            strcontact_state = list_of_state.get(position)
            strcontact_stateCode =  list_state_code.get(position)
            getContactCity(strcontact_stateCode.toString())
        }
        else if(parent?.id == R.id.sp_cst_voip){
            updatelayout_lead_other_details.et_cust_void.setText(list_of_boolean.get(position))
            str_voip = list_of_booleanvalue.get(position)
        }else if(parent?.id == R.id.sp_industype){
            updatelayout_lead_company_details.et_indus_type.setText(instryname.get(position))
            str_industry_type = industryid.get(position)
        }else if(parent?.id == R.id.sp_firm_type){
            updatelayout_lead_company_details.et_firm_type.setText(list_firm_type.get(position))
         //   str_firm_type =  list_firm_type_value.get(position-1)
            str_firm_type = list_firm_type_value.get(position ) 

        }
        else if(parent?.id == R.id.sp_media){
            updatelayout_lead_other_details.et_media.setText(list_of_media.get(position))
            str_media = list_of_mediavalue.get(position )
        }else if(parent?.id == R.id.sp_intrsteddata_center){
            updatelayout_lead_other_details.et_is_cus.setText(list_of_boolean.get(position))
            str_data = list_of_booleanvalue.get(position)
        }else if(parent?.id == R.id.sp_intrs_frwal){
            updatelayout_lead_other_details.et_indus_firewl.setText(list_of_option.get(position))
            str_cust_frwl = list_of_booleanvalue.get(position )
        }else if(parent?.id == R.id.sp_vpn_serv){
            updatelayout_lead_other_details.et_vpn_srv.setText(list_of_boolean.get(position))
          str_cust_vpn =  list_of_booleanvalue.get(position )
        }else if(parent?.id == R.id.sp_disqualify){
            if (position != 0) strDisqualify = "" + disqualifyCode.get(position - 1) else strDisqualify= " "
            if(strDisqualify.isNotEmpty()) {
                val disqualif:String
                if (position != 0) disqualif = "" + disqualifyCode.get(position - 1) else disqualif= " "
                if(disqualif.isBlank()||disqualif.isEmpty()){

                }else{
                    disQualifyLead(disqualif)
                }
            }

        }else if(parent?.id == R.id.sp_cmpny){
            updatelayout_contactinfo_layout.et_updtcompany.setText(company?.get(position))
            str_cmp =  companyId?.get(position )
            updatelayout_contactinfo_layout.et_upgroup.setText(group?.get(position))
            str_grp = groupId?.get(position )
            getRelation(str_cmp)
        }else if(parent?.id == R.id.sp_upgroup){
            updatelayout_contactinfo_layout.et_upgroup.setText(group?.get(position))
           str_grp = groupId?.get(position )
        } else if(parent?.id == R.id.sp_rltn){
            updatelayout_contactinfo_layout.et_uprelation.setText(relation?.get(position))
            str_rltn =  relationId?.get(position )
        }else if(parent?.id == R.id.sp_work_location){
            updatelayout_lead_other_details.et_crt_wrk.setText(othercity?.get(position))
            //if (position != 0) str_othercity = "" + othercityCode?.get(position ) else str_othercity= " "
             str_othercity =  othercityCode?.get(position )

        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    fun qualifyLead() {
        inAnimation = AlphaAnimation(0f, 1f)
        inAnimation?.duration =200
        binding.updateleadProgressLayout.progressOverlay.animation = inAnimation
        binding.updateleadProgressLayout.progressOverlay.visibility = View.VISIBLE

        val qualifiedLeadRequest = QualifiedLeadRequest(Constants.QUALIFY_LEAD,Constants.AUTH_KEY,str_lead_Id.toString(),"Target@2021#@","manager1")
        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.qualifyReq(qualifiedLeadRequest)
        call.enqueue(object : Callback<QualifyLeadResponse?> {
            override fun onResponse(call: Call<QualifyLeadResponse?>, response: Response<QualifyLeadResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        outAnimation = AlphaAnimation(1f, 0f)
                        inAnimation?.duration =200
                        binding.updateleadProgressLayout.progressOverlay.animation = outAnimation
                        binding.updateleadProgressLayout.progressOverlay.visibility = View.GONE
                        if(response.body()!!.StatusCode=="200"){
                            val img = response.body()?.Response?.Message
                            val id = response.body()?.Response?.Id
                            Toast.makeText(this@UpdateLeadActivity, img, Toast.LENGTH_SHORT).show()
                             val intent = Intent (this@UpdateLeadActivity, OpportunityActivity::class.java)
                             intent.putExtra("OppId",id)
                             intent.putExtra("LeadId", str_lead_Id)
                             startActivity(intent)
                             finish()
                        }else{
                            Toast.makeText(this@UpdateLeadActivity, response.body()?.Response?.Message, Toast.LENGTH_SHORT).show()
                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<QualifyLeadResponse?>, t: Throwable) {
                binding.updateleadProgressLayout.progressOverlay.visibility=View.GONE

                Log.e("RetroError", t.toString())
            }
        })
    }
    fun showFragment(fragment: GetAllLeadFrag){
        val fram = supportFragmentManager.beginTransaction()
        fram.replace(R.id.fragment_lead,fragment)
        fram.commit()
    }
    fun getOtherCity(strCurLocation: String?) {
        val getCityRequest = GetCityRequest(Constants.GET_CITY,Constants.AUTH_KEY,"Target@2021#@","","manager1")

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getCityList(getCityRequest)
        call.enqueue(object : Callback<GetCityResponse?> {
            override fun onResponse(call: Call<GetCityResponse?>, response: Response<GetCityResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        val img = response.body()!!.Response.Message
                        Log.e("image", img)
                        cityList = response.body()!!.Response.Data
                        othercity = ArrayList<String>()
                        othercityCode = ArrayList<String>()
                        othercity!!.add("Select City")
                        othercityCode!!.add("")
                        for (item in cityList!!) {
                            othercity!!.add(item.CityName)
                            othercityCode!!.add(item.CityCode)
                        }
                        var cityPosition=0
                        othercity?.forEachIndexed { index, s ->
                            if(s== strCurLocation)cityPosition=index
                        }

                        val adapter12 = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item, othercity!!)
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        updatelayout_lead_other_details.sp_work_location.adapter = adapter12
                        updatelayout_lead_other_details.sp_work_location.setSelection(cityPosition)
                        adapter12.notifyDataSetChanged()
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
    fun disQualifyLead(disqualif: String) {
        inAnimation = AlphaAnimation(0f, 1f)
        inAnimation?.duration =200
        binding.updateleadProgressLayout.progressOverlay.animation = inAnimation
        binding.updateleadProgressLayout.progressOverlay.visibility = View.VISIBLE

        val disqualifyLead = DisqualifyLead(Constants.DISQUALIFY_LEAD,Constants.AUTH_KEY,str_lead_Id.toString(),"Target@2021#@",disqualif,"manager1")

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.disQualifyReq(disqualifyLead)
        call.enqueue(object : Callback<GetDisQualifyResponse?> {
            override fun onResponse(call: Call<GetDisQualifyResponse?>, response: Response<GetDisQualifyResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        outAnimation = AlphaAnimation(1f, 0f)
                        inAnimation?.duration =200
                        binding.updateleadProgressLayout.progressOverlay.animation = outAnimation
                        binding.updateleadProgressLayout.progressOverlay.visibility = View.GONE
                         if(response.body()?.Response?.StatusCode==200){
                             val img = response.body()?.Response?.Message
                             Toast.makeText(this@UpdateLeadActivity, img , Toast.LENGTH_SHORT).show()
                             val fragmentB = GetAllLeadFrag()
                             supportFragmentManager.beginTransaction()
                                     .replace(R.id.fragment_lead, fragmentB, "fragmnetId")
                                     .commit()
                        }else{
                             Toast.makeText(this@UpdateLeadActivity, response.body()?.Response?.Message, Toast.LENGTH_SHORT).show()
                         }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<GetDisQualifyResponse?>, t: Throwable) {
                binding.updateleadProgressLayout.progressOverlay.visibility=View.GONE
                Log.e("RetroError", t.toString())
            }
        })
    }
}