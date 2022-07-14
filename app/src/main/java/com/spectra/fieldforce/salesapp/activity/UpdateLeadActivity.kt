package com.spectra.fieldforce.salesapp.activity

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.*
import android.view.animation.AlphaAnimation
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.spectra.fieldforce.R
import com.spectra.fieldforce.api.ApiClient
import com.spectra.fieldforce.api.ApiInterface
import com.spectra.fieldforce.databinding.UpdateLeadDemoFragmentBinding
import com.spectra.fieldforce.salesapp.fragment.FlrFrag
import com.spectra.fieldforce.salesapp.model.*
import com.spectra.fieldforce.utils.AppConstants
import com.spectra.fieldforce.utils.Constants
import com.spectra.fieldforce.utils.SalesConstant
import kotlinx.android.synthetic.main.lead__contact_person_row.view.*
import kotlinx.android.synthetic.main.lead_company_details_row.view.*
import kotlinx.android.synthetic.main.lead_contact_info.view.*
import kotlinx.android.synthetic.main.lead_demo_fragment.*
import kotlinx.android.synthetic.main.lead_installation_address_row.view.*
import kotlinx.android.synthetic.main.toolbar.view.*
import kotlinx.android.synthetic.main.update_lead_channel_source_row.*
import kotlinx.android.synthetic.main.update_lead_channel_source_row.view.*
import kotlinx.android.synthetic.main.update_lead_company_details_row.*
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
import kotlinx.android.synthetic.main.update_lead_installation_address_row.view.sp_city
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
import kotlinx.android.synthetic.main.updatelead__contact_person_row.view.et_cnt_building
import kotlinx.android.synthetic.main.updatelead__contact_person_row.view.et_cnt_buildng_num
import kotlinx.android.synthetic.main.updatelead__contact_person_row.view.et_cnt_city
import kotlinx.android.synthetic.main.updatelead__contact_person_row.view.et_cnt_floor
import kotlinx.android.synthetic.main.updatelead__contact_person_row.view.et_cnt_pin_code
import kotlinx.android.synthetic.main.updatelead__contact_person_row.view.et_cnt_state
import kotlinx.android.synthetic.main.updatelead__contact_person_row.view.sp_cnt_city
import kotlinx.android.synthetic.main.updatelead__contact_person_row.view.sp_cnt_state
import kotlinx.android.synthetic.main.updatelead_contact_personadd_row.*
import kotlinx.android.synthetic.main.updatelead_contact_personadd_row.view.*
import kotlinx.android.synthetic.main.updatelead_contact_personadd_row.view.et_business_seg
import kotlinx.android.synthetic.main.updatelead_contact_personadd_row.view.et_contact_person
import kotlinx.android.synthetic.main.updatelead_contact_personadd_row.view.et_customer_seg

import kotlinx.android.synthetic.main.updatelead_contact_personadd_row.view.et_saluation

import kotlinx.android.synthetic.main.updatelead_contact_personadd_row.view.sp_cust_seg

import kotlinx.android.synthetic.main.updatelead_contact_personadd_row.view.sp_salutation
import kotlinx.android.synthetic.main.updatelead_contact_personadd_row.view.sp_sub_bus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.lang.NumberFormatException
import java.util.*
import java.util.regex.Pattern
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
    private var contactCity : ArrayList<String>? = null
    private var contactCityCode : ArrayList<String>? = null
    private var building : ArrayList<String>? = null
    private var buildingCode : ArrayList<String>? = null
    private var cntbuilding : ArrayList<String>? = null
    private var cntbuildingCode : ArrayList<String>? = null
    private var RequiredCity : ArrayList<String>? = null
    private var RequiredCityCode : ArrayList<String>? = null
    private var verticalList : ArrayList<VerticalData>? = null
    private var vertical : ArrayList<String>? = null
    private var verticalId : ArrayList<String>? = null
    private var trgtdate:String?=null
    private var Status:String?=null
    var mpls :String ?=null
    private var strCompanyName : String? = null
    var firesSet :String ?=null
    var str_city: String? = null
    var str_city_code : String? = null
    var str_Contactcity: String? = null
    var str_ContactcityCode : String? = null
    private var inAnimation: AlphaAnimation? = null
    private var outAnimation: AlphaAnimation? = null
    var str_lead_chnl : String? = null
    var str_lead_src : String? = null
    var str_sub_bus : String? = null
    var strVertical:String ? = null
    var strIllServices :String? = null
    var strCityReqd :String? = null
    var strNetworkSecurity :String? = null
    var strHosted :String? = null
    var strBackBone:String ? = null
    var strCustomer:String? = null
    var strContract :String? = null
    var strRoutingServices :String? = null
    var strBroadServices :String? = null
    var strLinksManaged :String? = null
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
    var str_voip : String? = null
    var str_wifi : String? = null
    var strDisqualify = ""
    var strCity = ""
    var strArea =""
    var strBuilding=""
    var strContactCity = ""
    var strContactArea =""
    var strContactBuilding=""
    var strRelation=""
    var strMobile=""
    var strIndustry =""
    var str_inst_country: String? = null
    var str_inst_state: String? = null
    var str_inst_area : String? = null
    var str_inst_areaname : String? = null
    var str_inst_building_nm : String? = null
    var str_cntct_building_nm : String? = null
    var str_inst_build_num : String? = null
    var str_inst_build_name : String? = null
    var str_add_area : String? = null
    var cntareaname : String? = null
    var str_customer_segmentid :String? = null
    private var industryList : ArrayList<IndustryResponse>? = null
    private var instryname = ArrayList<String>()
    private var industryid = ArrayList<String>()

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
    var userName :String? = null
    var password : String? = null
    var strContactId : String? = null
    var str_lead_status : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.update_lead_demo_fragment)
        searchtoolbarlead_update.rl_back.setOnClickListener(this)
        searchtoolbarlead_update.tv_lang.text= AppConstants.UPDATE_LEAD
        val sp1: SharedPreferences = this.getSharedPreferences("Login", 0)
        userName = sp1.getString("UserName", null)
        password = sp1.getString("Password", null)
        val extras = intent.extras
        if (extras != null) {
            str_lead_Id = extras.getString("LeadId")
            str_lead_status = extras.getString("LeadStatus")
            strContactId = extras.getString("ContactID")
            Status = extras.getString("Status")
        }
        linearcontactinfo.visibility = View.VISIBLE
        linear_insta_addres.visibility = View.VISIBLE
        linadd.visibility = View.VISIBLE
        linear_contact_person_address.visibility = View.VISIBLE
        linear_companydetails.visibility= View.VISIBLE
        linearother_details.visibility = View.VISIBLE
        linearremark_details.visibility = View.VISIBLE
        if(Status=="1"){
            flr.visibility= View.GONE
        }else{
            flr.visibility= View.VISIBLE
        }
        CoroutineScope(Dispatchers.IO).launch {
            init()

        }
        itemListener()

        CoroutineScope(Dispatchers.IO).launch {
            getLead()
        }

        tv_qualify.setOnClickListener {
            qualifyLead()
      }

      et_disquslify.setOnClickListener {
          val dis :String = et_disquslify.text.toString()
          if(strDisqualify==""||strDisqualify==""||dis==("Disqualify")){
              et_disquslify.setOnClickListener { sp_disqualify.performClick() }
              sp_disqualify.onItemSelectedListener = this
          }
      }

        flr.setOnClickListener {
            val fragment = FlrFrag()
            showFragmentflr(fragment)
        }

        oppurtunity.setOnClickListener {
            val intent = Intent (this@UpdateLeadActivity, OpportunityActivity::class.java)
            intent.putExtra("LeadId", str_lead_Id)
            startActivity(intent)
            finish()
        }
    }



    private fun showFragmentflr(fragment: FlrFrag){
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
            updatelayout_contactinfo_layout.et_updtcompany.isFocusableInTouchMode= false
            updatelayout_contactinfo_layout.et_saluation.isFocusableInTouchMode= false
            updatelayout_contactinfo_layout.et_customer_seg.isFocusableInTouchMode= false
            updatelayout_contactinfo_layout.et_contact_person.isFocusableInTouchMode= false
            updatelayout_contactinfo_layout.et_upmobile_num.isFocusableInTouchMode= false
            updatelayout_contactinfo_layout.et_customer_seg.isFocusableInTouchMode= false
            updatelayout_contactinfo_layout.et_emailid.isFocusableInTouchMode= false
            updatelayout_lead_installation_address.et_add_build_num.isFocusableInTouchMode = false
            updatelayout_lead_installation_address.et_add_floor.isFocusableInTouchMode = false
            updatelayout_lead_installation_address.et_pin_code.isFocusableInTouchMode = false
            updatelayout_lead_installation_address.et_upad_block.isFocusableInTouchMode = false
            update_lead_contact_address.et_cnt_buildng_num.isFocusableInTouchMode = false
            update_lead_contact_address.et_cnt_pin_code.isFocusableInTouchMode = false
            update_lead_contact_address.et_cnt_floor.isFocusableInTouchMode = false
            updatelayout_lead_company_details.et_company_name.isFocusableInTouchMode = false
            updatelayout_lead_company_details.et_job_title.isFocusableInTouchMode = false
            binding.updatelayoutLeadRemarks.etUpleadRemark.isFocusableInTouchMode = false
            update_lead_contact_address.et_cntspbuilding_num.isFocusableInTouchMode = false
            update_lead_contact_address.et_cntspbuilding_name.isFocusableInTouchMode = false
            update_lead_contact_address.et_upcnt_block.isFocusableInTouchMode = false
            updatelayout_lead_installation_address.et_upspefc_area.isFocusableInTouchMode = false
            updatelayout_lead_installation_address.et_upspecfcbuilding_num.isFocusableInTouchMode = false
            binding.layoutLeadSdQuestionare.etNoOfLocation.isEnabled=false
            binding.layoutLeadSdQuestionare.etLinks.isEnabled=false
            binding.layoutLeadSdQuestionare.etItSpent.isEnabled=false
            binding.layoutLeadSdQuestionare.etRemarkk.isEnabled=false
            binding.layoutLeadSdQuestionare.etMentionNum.isEnabled=false
            binding.layoutLeadSdQuestionare.etCustomerUsingIllServices.isEnabled=false
            binding.layoutLeadSdQuestionare.etBroadServices.isEnabled=false
            binding.layoutLeadSdQuestionare.etLinksManged.isEnabled=false
            binding.layoutLeadSdQuestionare.etRoutingServices.isEnabled=false
            binding.layoutLeadSdQuestionare.etFireSet.isEnabled=false
            binding.layoutLeadSdQuestionare.etCityRequired.isEnabled=false
            binding.layoutLeadSdQuestionare.etNetworkSecurity.isEnabled=false
            binding.layoutLeadSdQuestionare.etHostedRequired.isEnabled=false
            binding.layoutLeadSdQuestionare.etCustomerRequired.isEnabled=false
            binding.layoutLeadSdQuestionare.etBackboneRequired.isEnabled=false
            binding.layoutLeadSdQuestionare.etContractRenewed.isEnabled=false
            binding.layoutLeadSdQuestionare.etMplsRequired.isEnabled=false

            flr.visibility= View.GONE
            tv_qualify.visibility= View.GONE
            dis.visibility = View.GONE
            et_saluation.isEnabled=false
            //et_updtcompany.isEnabled=false
            et_uprelation.isEnabled=false
            updatelayout_lead_installation_address.et_upad_block.isEnabled=false
            et_upgroup.isEnabled=false
            et_sub_busiessseg.isEnabled=false
            et_customer_seg.isEnabled=false
            et_country.isEnabled=false
            et_lead_name.isEnabled=false
            updatelayout_contactinfo_layout.et_updtcompany.isEnabled=false
            update_lead_contact_address.et_upcnt_block.isEnabled=false
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
            val location = binding.layoutLeadSdQuestionare.etNoOfLocation.text.toString()
            val mentionNum = binding.layoutLeadSdQuestionare.etMentionNum.text.toString()
            val links = binding.layoutLeadSdQuestionare.etLinks.text.toString()
            val itSpent = binding.layoutLeadSdQuestionare.etItSpent.text.toString()
            val questionareRemark = binding.layoutLeadSdQuestionare.etRemarkk.text.toString()

            if(topic.isBlank()){
                Toast.makeText(this, "Please Enter Topic", Toast.LENGTH_SHORT).show()
            }else if(gnl_sub.isBlank()||gnl_sub=="Select Sub Business Segment"||(gnl_sub=="null")){
                Toast.makeText(this, "Please Select Sub Business Segment", Toast.LENGTH_SHORT).show()
            }else if(customer_seg.isBlank()||customer_seg=="Select Customer Segment"||(customer_seg=="null")){
                Toast.makeText(this, "Please Select Customer Segment", Toast.LENGTH_SHORT).show()
            }else if(salutation.isBlank()||salutation=="Select Salutation"||(salutation=="null")){
                Toast.makeText(this, "Please Select Salutation", Toast.LENGTH_SHORT).show()
            }else if(cnt_info_cnt_person.isBlank()){
                Toast.makeText(this, "Please Enter Contact Person", Toast.LENGTH_SHORT).show()
            }else if(general_email.isBlank()||(!validEmail(general_email))){
                Toast.makeText(this, "Please Enter Email Id", Toast.LENGTH_SHORT).show()
            }else if(gnl_phn_num.isBlank()|| gnl_phn_num.length!=10){
                Toast.makeText(this, "Please Enter Mobile Number", Toast.LENGTH_SHORT).show()
            }else if(genral_name.isBlank()){
                Toast.makeText(this, "Please Enter Lead Name", Toast.LENGTH_SHORT).show()
            }else if(companyid.isBlank()||companyid=="Select Company"||(companyid=="null")){
                Toast.makeText(this, "Please Select Company", Toast.LENGTH_SHORT).show()
            }else if(group.isBlank()||group=="Select Group"||(group=="null")){
                Toast.makeText(this, "Please enter Group", Toast.LENGTH_SHORT).show()
            }
            else if(relation.isBlank()&&(gnl_sub!="SDWAN")){
                Toast.makeText(this, "Please Select Relation", Toast.LENGTH_SHORT).show()
            }else if((general_chnl.isBlank())||(general_chnl=="Select Channel")||(general_chnl=="null")){
                Toast.makeText(this, "Please Select Channel", Toast.LENGTH_SHORT).show()
            }else if(general_src.isBlank()||(general_src=="null")){
                Toast.makeText(this, "Please Select Source", Toast.LENGTH_SHORT).show()
            }else if((inst_state.isBlank())||(inst_state=="Select State")||(inst_state=="null")){
                Toast.makeText(this, "Please Select State", Toast.LENGTH_SHORT).show()
            }else if(inst_city_code.isBlank()||(inst_city_code=="")||(inst_city_code=="null")){
                Toast.makeText(this, "Please Select City", Toast.LENGTH_SHORT).show()
            }else if(inst_area.isBlank()||(inst_area=="Select Area")||(inst_area=="null")){
                Toast.makeText(this, "Please Select Area", Toast.LENGTH_SHORT).show()
            }else if((str_inst_areaname== "Other")&&(inst_sparea.isEmpty())){
                Toast.makeText(this, "Please Enter Specific Area Name", Toast.LENGTH_SHORT).show()
            }else if(str_inst_build_num?.isBlank() == true ||(str_inst_build_num=="")||(str_inst_build_num=="null")){
                Toast.makeText(this, "Please Select Building Name", Toast.LENGTH_SHORT).show()
            }else if(inst_spbuild.isBlank()&&str_inst_build_name=="Other"){
                Toast.makeText(this, "Please Select Specific Building Name", Toast.LENGTH_SHORT).show()
            }else if(inst_buil.isBlank()){
                Toast.makeText(this, "Please enter Building No. ", Toast.LENGTH_SHORT).show()
            }else if(inst_block.isBlank()){
                Toast.makeText(this, "Please enter Block", Toast.LENGTH_SHORT).show()
            }else if(inst_floor.isBlank()){
                Toast.makeText(this, "Please enter Floor", Toast.LENGTH_SHORT).show()
            }else if(inst_pincode.isBlank()|| inst_pincode.length!=6){
                Toast.makeText(this, "Please enter PinCode", Toast.LENGTH_SHORT).show()
            }else if(state.isBlank()||state=="Select State"||(state=="null")){
                Toast.makeText(this, "Please Select State", Toast.LENGTH_SHORT).show()
            }else if(city.isBlank()||city=="Select City"||(city=="null")){
                Toast.makeText(this, "Please Select City", Toast.LENGTH_SHORT).show()
            }else if(area.isBlank()||area=="Select Area"||(area=="null")){
                Toast.makeText(this, "Please Select Area", Toast.LENGTH_SHORT).show()
            }else if((cntareaname=="Other")&&(sparea.isEmpty())){
                Toast.makeText(this, "Please Enter Specific Area Name", Toast.LENGTH_SHORT).show()
            }else if(addres_build?.isBlank() == true ||addres_build==""||(addres_build=="null")){
                Toast.makeText(this, "Please enter Building Name ", Toast.LENGTH_SHORT).show()
            }else if(spbuilg.isBlank()&&str_cntct_building_nm=="Other"){
                Toast.makeText(this, "Please Select Specific Building Name", Toast.LENGTH_SHORT).show()
            }else if(buildingnum.isBlank()||(buildingnum=="null")){
                Toast.makeText(this, "Please enter Building No.", Toast.LENGTH_SHORT).show()
            }else if(block.isBlank()){
                Toast.makeText(this, "Please enter Block", Toast.LENGTH_SHORT).show()
            }else if(floor.isBlank()){
                Toast.makeText(this, "Please enter Floor", Toast.LENGTH_SHORT).show()
            }else if(pincode.isBlank()|| pincode.length!=6){
                Toast.makeText(this, "Please enter PinCode ", Toast.LENGTH_SHORT).show()
            }else if(company.isBlank()||(company=="null")){
                Toast.makeText(this, "Please Enter Company Name", Toast.LENGTH_SHORT).show()
            }else if(firmtype.isBlank()||firmtype=="Select Firm Type"||(firmtype=="null")){
                Toast.makeText(this, "Please Select Firm Type ", Toast.LENGTH_SHORT).show()
            }else if(industrytype.isBlank()||industrytype=="Select Industry"||(industrytype=="null")){
                Toast.makeText(this, "Please Select Industry Type", Toast.LENGTH_SHORT).show()
            }else if(jbtitle.isBlank()||(jbtitle=="null")){
                Toast.makeText(this, "Please enter Job Title", Toast.LENGTH_SHORT).show()
            }else if(other_media.isBlank()||other_media=="Select Media"){
                Toast.makeText(this, "Please Select Media ", Toast.LENGTH_SHORT).show()
            }else if(other_pro_one.isBlank()||other_pro_one=="0"||(other_pro_one=="null")){
                Toast.makeText(this, "Please Select Existing Service Provider One", Toast.LENGTH_SHORT).show()
            }else if(other_pro_two.isBlank()||other_pro_two=="0"||(other_pro_two=="null")){
                Toast.makeText(this, "Please Select Existing Service Provider Two", Toast.LENGTH_SHORT).show()
            } else if(other_work.isBlank()||(other_work=="null")){
                Toast.makeText(this, "Please Select Current Work location ", Toast.LENGTH_SHORT).show()
            }else if(other_date.isBlank()||(other_date=="null")){
                Toast.makeText(this, "Please Select Data Center ", Toast.LENGTH_SHORT).show()
            }else if(other_firewal.isBlank()||other_firewal=="Select Option"||(other_firewal=="null")){
                Toast.makeText(this, "Please Select Firewall ", Toast.LENGTH_SHORT).show()
            } else if(other_vpn.isBlank()||(other_vpn=="null")){
                Toast.makeText(this, "Please Select Vpn Services ", Toast.LENGTH_SHORT).show()
            }else if(other_cust_one.isBlank()||other_cust_one=="0"||(other_cust_one=="null")){
                Toast.makeText(this, "Please Select Existing Service Provider One", Toast.LENGTH_SHORT).show()
            }else if(cust_two.isBlank()||cust_two=="0"||(cust_two=="null")){
                Toast.makeText(this, "Please Select Existing Service Provider Two", Toast.LENGTH_SHORT).show() }
            else if(other_voip.isBlank()||other_voip=="Select Option"||(other_voip=="null")){
                Toast.makeText(this, "Please Select Voip ", Toast.LENGTH_SHORT).show()
            }else if(other_wifi.isBlank()||other_wifi=="Select Option"||(other_wifi=="null")){
                Toast.makeText(this, "Please Select Manage Wifi", Toast.LENGTH_SHORT).show()
            }else if(other_target.isBlank()||(other_target=="null")){
                Toast.makeText(this, "Please Select Target Installation Period", Toast.LENGTH_SHORT).show()
            }else if(str_sub_bus==AppConstants.SDWAN &&location.isBlank()||location=="null"){
                Toast.makeText(this, "Please Enter the Location", Toast.LENGTH_SHORT).show()
            }else if(str_sub_bus==AppConstants.SDWAN && strIllServices?.isBlank() == true ||strIllServices=="null"||strIllServices=="0"){
            Toast.makeText(this, "Please Select Customer Using ILL Services", Toast.LENGTH_SHORT).show()
            }else if((strIllServices=="122050000") && mentionNum.isBlank()){
                Toast.makeText(this, "Please Enter Mention Number of Links", Toast.LENGTH_SHORT).show()
            }else if(str_sub_bus==AppConstants.SDWAN && (strBroadServices?.isBlank() == true) ||strBroadServices=="null"||strBroadServices=="0"){
                Toast.makeText(this, "Please Enter the BroadBand Services", Toast.LENGTH_SHORT).show()
            }else if(links.isBlank()&&(strBroadServices=="122050000")){
                Toast.makeText(this, "Please Enter the NO. of Links", Toast.LENGTH_SHORT).show()
            }else if(str_sub_bus==AppConstants.SDWAN &&(strLinksManaged?.isBlank()== true)||strLinksManaged=="null"||strLinksManaged=="0"){
                Toast.makeText(this, "Please Enter the Managed Links ", Toast.LENGTH_SHORT).show()
            }else if(str_sub_bus==AppConstants.SDWAN &&(strRoutingServices?.isBlank()== true)||strRoutingServices=="null"||strRoutingServices=="0"){
                Toast.makeText(this, "Please Enter the Routing Services", Toast.LENGTH_SHORT).show()
            }else if(str_sub_bus==AppConstants.SDWAN &&(firesSet?.isBlank()== true)||firesSet=="null"){
            Toast.makeText(this, "Please Select Firewall set tpo expire", Toast.LENGTH_SHORT).show()
           }
           else if(str_sub_bus==AppConstants.SDWAN &&(strCityReqd?.isBlank()== true)||strCityReqd=="null"||strCityReqd=="Select Option"){
                Toast.makeText(this, "Please Enter the City Required", Toast.LENGTH_SHORT).show()
            } else if(str_sub_bus==AppConstants.SDWAN &&(strNetworkSecurity?.isBlank()== true)||strNetworkSecurity=="null"||strNetworkSecurity=="0"){
                Toast.makeText(this, "Please Enter the Network Security", Toast.LENGTH_SHORT).show()
            }else if(str_sub_bus==AppConstants.SDWAN &&(strHosted?.isBlank()== true)||strHosted=="null"||strHosted=="0"){
                Toast.makeText(this, "Please Enter the Hosted", Toast.LENGTH_SHORT).show()
            }else if(str_sub_bus==AppConstants.SDWAN &&(strCustomer?.isBlank()== true)||strCustomer=="null"||strCustomer=="0"){
                Toast.makeText(this, "Please Enter the Customer", Toast.LENGTH_SHORT).show()
            }else if(str_sub_bus==AppConstants.SDWAN &&(strContract?.isBlank()== true)||strContract=="null"||strContract=="0"){
                Toast.makeText(this, "Please Enter the Contract", Toast.LENGTH_SHORT).show()
            }
            else if(str_sub_bus==AppConstants.SDWAN &&(strBackBone?.isBlank()== true)||strBackBone=="null"||strBackBone=="0"){
                Toast.makeText(this, "Please Enter the Back Bone", Toast.LENGTH_SHORT).show()
            }else if(str_sub_bus==AppConstants.SDWAN &&(mpls?.isBlank()== true)||mpls=="null"){
                Toast.makeText(this, "Please Enter the Mpls", Toast.LENGTH_SHORT).show()
            }
            else {
                     update_Lead(remark,company,firmtype,industrytype,jbtitle,area,addres_build,city,
                        sparea, spbuilg,floor,pincode,state,inst_area,inst_build,inst_build_num,
                        inst_city_code,inst_city,block,inst_floor,inst_pincode,inst_buil,inst_block,
                        inst_spbuild,inst_sparea,inst_state,inst_state_code,other_work,other_pro_one,
                        other_pro_two,other_date,other_firewal,other_wifi,other_voip,other_vpn,other_media,
                        other_cust_one,cust_two,other_target,general_chnl,general_src,gnl_sub,
                        salutation,cnt_info_cnt_person,topic,gnl_phn_num,general_email,genral_name,
                        customer_seg, group,relation,companyid,buildingnum,location,mentionNum,links,itSpent,questionareRemark)
            }
        }
    }

    private fun validEmail(email: String): Boolean {
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }

    private fun getRequiredCity() {
        val getCityRequest = GetCityRequest(Constants.GET_CITY,Constants.AUTH_KEY,password,"",userName)

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getCityList(getCityRequest)
        call.enqueue(object : Callback<GetCityResponse?> {
            override fun onResponse(call: Call<GetCityResponse?>, response: Response<GetCityResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        val img = response.body()?.Response?.Message
                        img?.let { Log.e("image", it) }
                        val cityList = response.body()?.Response?.Data
                        RequiredCity = ArrayList<String>()
                        RequiredCityCode =ArrayList<String>()
                        RequiredCity?.add("Select City")
                        RequiredCityCode?.add("")
                        for (item in cityList!!) {
                            RequiredCity?.add(item.CityName)
                            RequiredCityCode?.add(item.CityCode)
                        }
                        var requiredCityPosition=0
                        RequiredCityCode?.forEachIndexed { index, s ->
                            if(s==strCityReqd)requiredCityPosition=index
                        }

                        val adapter12 = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item, RequiredCity!!)
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        binding.layoutLeadSdQuestionare.spCityRequired.adapter = adapter12
                        binding.layoutLeadSdQuestionare.spCityRequired.setSelection(requiredCityPosition)
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
                        verticalId?.forEachIndexed { index, ss ->
                            if(ss==strVertical)verticalPosition=index
                            return@forEachIndexed
                        }

                        val adapter12 = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item, vertical!!)
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        binding.updatelayoutContactinfoLayout.spVertical.adapter = adapter12
                        binding.updatelayoutContactinfoLayout.spVertical.setSelection(verticalPosition)
                        adapter12.notifyDataSetChanged()

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<GetVerticalData?>, t: Throwable) {
                Log.e("RetroError", t.toString())
            }
        })
    }

    fun itemListener(){
        updatelayout_contactinfo_layout.et_business_seg.setText(AppConstants.BUSINESS)
        updatelayout_contactinfo_layout.et_sub_busiessseg.setOnClickListener {
        updatelayout_contactinfo_layout.sp_sub_bus.performClick() }
        updatelayout_contactinfo_layout.sp_sub_bus.onItemSelectedListener = this
        binding.updatelayoutContactinfoLayout.etVertical.setOnClickListener { binding.updatelayoutContactinfoLayout.spVertical.performClick() }
        binding.updatelayoutContactinfoLayout.spVertical.onItemSelectedListener = this

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
       /* updatelayout_lead_installation_address.et_cntarea.setOnClickListener { updatelayout_lead_installation_address.sp_cnarea.performClick() }
        updatelayout_lead_installation_address.sp_cnarea.onItemSelectedListener = this
*/
        update_lead_contact_address.et_cnt_state.setOnClickListener{
            update_lead_contact_address.sp_cnt_state.performClick()
        }

        update_lead_contact_address.sp_cnt_state.onItemSelectedListener = this
        update_lead_contact_address.et_cnt_city.setOnClickListener{
            update_lead_contact_address.sp_cnt_city.performClick()
        }
        update_lead_contact_address.sp_cnt_city.onItemSelectedListener = this
    /*    binding.updateLeadContactAddress.etContacttarea.setOnClickListener{
            binding.updateLeadContactAddress.spCntCnarea.performClick()
        }
        binding.updateLeadContactAddress.spCntCnarea.onItemSelectedListener = this

     */   updatelayout_lead_other_details.et_ext_srv.setOnClickListener { updatelayout_lead_other_details.sp_ex_serv.performClick() }
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
      /*  updatelayout_contactinfo_layout.et_updtcompany.setOnClickListener { updatelayout_contactinfo_layout.sp_cmpny.performClick() }
        updatelayout_contactinfo_layout.sp_cmpny.onItemSelectedListener = this*/
        updatelayout_contactinfo_layout.et_upgroup.setOnClickListener { updatelayout_contactinfo_layout.sp_upgroup.performClick() }
        updatelayout_contactinfo_layout.sp_upgroup.onItemSelectedListener = this
        updatelayout_contactinfo_layout.et_uprelation.setOnClickListener { updatelayout_contactinfo_layout.sp_rltn.performClick() }
        updatelayout_contactinfo_layout.sp_rltn.onItemSelectedListener = this

        binding.layoutLeadSdQuestionare.etCustomerUsingIllServices.setOnClickListener { binding.layoutLeadSdQuestionare.spCustomerUsingIllServices.performClick() }
        binding.layoutLeadSdQuestionare.spCustomerUsingIllServices.onItemSelectedListener = this

        binding.layoutLeadSdQuestionare.etBroadServices.setOnClickListener { binding.layoutLeadSdQuestionare.spBroadServices.performClick() }
        binding.layoutLeadSdQuestionare.spBroadServices.onItemSelectedListener = this

        binding.layoutLeadSdQuestionare.etLinksManged.setOnClickListener { binding.layoutLeadSdQuestionare.spLinksManged.performClick() }
        binding.layoutLeadSdQuestionare.spLinksManged.onItemSelectedListener = this

        binding.layoutLeadSdQuestionare.etRoutingServices.setOnClickListener { binding.layoutLeadSdQuestionare.spRoutingServices.performClick() }
        binding.layoutLeadSdQuestionare.spRoutingServices.onItemSelectedListener = this

        binding.layoutLeadSdQuestionare.etCityRequired.setOnClickListener { binding.layoutLeadSdQuestionare.spCityRequired.performClick() }
        binding.layoutLeadSdQuestionare.spCityRequired.onItemSelectedListener = this

        binding.layoutLeadSdQuestionare.etNetworkSecurity.setOnClickListener { binding.layoutLeadSdQuestionare.spNetworkSecurity.performClick() }
        binding.layoutLeadSdQuestionare.spNetworkSecurity.onItemSelectedListener = this

        binding.layoutLeadSdQuestionare.etHostedRequired.setOnClickListener { binding.layoutLeadSdQuestionare.spHostedRequired.performClick() }
        binding.layoutLeadSdQuestionare.spHostedRequired.onItemSelectedListener = this

        binding.layoutLeadSdQuestionare.etCustomerRequired.setOnClickListener { binding.layoutLeadSdQuestionare.spCustomerRequired.performClick() }
        binding.layoutLeadSdQuestionare.spCustomerRequired.onItemSelectedListener = this

        binding.layoutLeadSdQuestionare.etBackboneRequired.setOnClickListener { binding.layoutLeadSdQuestionare.spBackboneRequired.performClick() }
        binding.layoutLeadSdQuestionare.spBackboneRequired.onItemSelectedListener = this

        binding.layoutLeadSdQuestionare.etContractRenewed.setOnClickListener { binding.layoutLeadSdQuestionare.spContractRenewed.performClick() }
        binding.layoutLeadSdQuestionare.spContractRenewed.onItemSelectedListener = this

        binding.layoutLeadSdQuestionare.etCityRequired.setOnClickListener { binding.layoutLeadSdQuestionare.spCityRequired.performClick() }
        binding.layoutLeadSdQuestionare.spCityRequired.onItemSelectedListener = this

        updatelayout_contactinfo_layout.et_emailid.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus)
            {
                val email = updatelayout_contactinfo_layout.et_emailid.text.toString()
                val isValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
                if (!isValid) {
                    Toast.makeText(this, "Invalid Email", Toast.LENGTH_LONG).show()
                }
            }
        }

        updatelayout_contactinfo_layout.et_upmobile_num.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus)
            {
                val mobile = updatelayout_contactinfo_layout.et_upmobile_num.text.toString()
                val isValid = Patterns.PHONE.matcher(mobile).matches()
                if (!isValid) {
                    Toast.makeText(this, "Invalid Mobile Number", Toast.LENGTH_LONG).show()
                }
            }
        }

        updatelayout_contactinfo_layout.et_updtcompany.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus)
            {
                val strCompanyName = updatelayout_contactinfo_layout.et_updtcompany.text.toString()
                if (strCompanyName.contentEquals("New Company (CNew)")||strCompanyName=="Select Company"){
                    updatelayout_contactinfo_layout.et_lead_name.setText("")
                    updatelayout_lead_company_details.et_company_name.setText("")
                }
            }
        }

    }


    fun listener(){
        linearcontactinfo.visibility = View.VISIBLE
        lineartwo.setOnClickListener {
            linearcontactinfo.visibility = View.VISIBLE
            linear_insta_addres.visibility = View.GONE
            linadd.visibility = View.GONE
            linear_contact_person_address.visibility = View.GONE
            linear_companydetails.visibility= View.GONE
            linearother_details.visibility = View.GONE
            linearremark_details.visibility = View.GONE
            binding.linearquestionareDetails.visibility = View.GONE
        }
        linearthree.setOnClickListener {
            linearcontactinfo.visibility = View.GONE
            linear_insta_addres.visibility = View.VISIBLE
            linadd.visibility = View.GONE
            linear_contact_person_address.visibility = View.GONE
            linear_companydetails.visibility= View.GONE
            linearother_details.visibility = View.GONE
            linearremark_details.visibility = View.GONE
            binding.linearquestionareDetails.visibility = View.GONE
        }
        linearfouraddres.setOnClickListener {
            linearcontactinfo.visibility = View.GONE
            linear_insta_addres.visibility = View.GONE
            linadd.visibility = View.VISIBLE
            linear_contact_person_address.visibility = View.GONE
            linear_companydetails.visibility= View.GONE
            linearother_details.visibility = View.GONE
            linearremark_details.visibility = View.GONE
            binding.linearquestionareDetails.visibility = View.GONE
        }
        linearfive.setOnClickListener {
            linearcontactinfo.visibility = View.GONE
            linear_insta_addres.visibility = View.GONE
            linadd.visibility = View.GONE
            linear_contact_person_address.visibility = View.VISIBLE
            linear_companydetails.visibility= View.GONE
            linearother_details.visibility = View.GONE
            linearremark_details.visibility = View.GONE
            binding.linearquestionareDetails.visibility = View.GONE
        }
        linearsix.setOnClickListener {
            linearcontactinfo.visibility = View.GONE
            linear_insta_addres.visibility = View.GONE
            linadd.visibility = View.GONE
            linear_contact_person_address.visibility = View.GONE
            linear_companydetails.visibility= View.VISIBLE
            linearother_details.visibility = View.GONE
            linearremark_details.visibility = View.GONE
            binding.linearquestionareDetails.visibility = View.GONE
        }

        lineareight.setOnClickListener {
            linearcontactinfo.visibility = View.GONE
            linear_insta_addres.visibility = View.GONE
            linadd.visibility = View.GONE
            linear_contact_person_address.visibility = View.GONE
            linear_companydetails.visibility= View.GONE
            linearother_details.visibility = View.VISIBLE
            linearremark_details.visibility = View.GONE
            binding.linearquestionareDetails.visibility = View.GONE
        }
        linearnine.setOnClickListener {
            linearcontactinfo.visibility = View.GONE
            linear_insta_addres.visibility = View.GONE
            linadd.visibility = View.GONE
            linear_contact_person_address.visibility = View.GONE
            linear_companydetails.visibility= View.GONE
            linearother_details.visibility = View.GONE
            linearremark_details.visibility = View.VISIBLE
            binding.linearquestionareDetails.visibility = View.GONE
        }
        binding.linearqustionare.setOnClickListener {
            linearcontactinfo.visibility = View.GONE
            linear_insta_addres.visibility = View.GONE
            linadd.visibility = View.GONE
            linear_contact_person_address.visibility = View.GONE
            linear_companydetails.visibility= View.GONE
            linearother_details.visibility = View.GONE
            linearremark_details.visibility = View.GONE
            binding.linearquestionareDetails.visibility = View.VISIBLE
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.rl_back -> {
                back()
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
        buildingnum: String?,
        location: String,
        mentionNum: String,
        links: String,
        itSpent: String,
        questionareRemark: String
    ) {
        inAnimation = AlphaAnimation(0f, 1f)
        inAnimation?.duration =200
        binding.updateleadProgressLayout.progressOverlay.animation = inAnimation
        binding.updateleadProgressLayout.progressOverlay.visibility = View.VISIBLE

        val otherDetail = OtherDetail(other_work,other_pro_one,other_pro_two,other_date.toBoolean(),
                other_firewal.toBoolean(),other_wifi.toBoolean(),
                other_voip.toBoolean(),other_vpn.toBoolean(),other_media,other_cust_one,cust_two,trgtdate)

        val companyDetail = CompanyDetail(company,firmtype,industrytype,jbtitle)

        val contactAddress= ContactAddress(str_add_area,addres_build,str_ContactcityCode,"10001",
            floor, pincode,buildingnum,sparea,spbuilg,
            state,block)
        var specific=""
        if(general_src=="Other"){
            specific = "Other"
        }

        val installationAddress= InstallationAddress(inst_block,inst_area,inst_build,
                inst_city_code,AppConstants.COUNTRY_CODE,inst_floor,inst_pincode,inst_buil,"0",
                inst_sparea,inst_spbuild,inst_state)

        val sdwan = SDWAN(location,strIllServices,mentionNum,strBroadServices,
            links,strLinksManaged,strRoutingServices,firesSet,
            itSpent,strCityReqd,strNetworkSecurity,strHosted,strCustomer,
            strContract,strBackBone,mpls,questionareRemark)

        val createLeadRequest = CreateLeadRequest(Constants.UPDATE_LEAD,Constants.AUTH_KEY,
                str_lead_Id,strVertical,companyDetail,
                contactAddress,installationAddress,sdwan,"Business",str_cmp,
                cnt_info_cnt_person,str_customer_segmentid,general_email,
                "3",genral_name,str_grp,"",general_chnl,general_src,topic,
                gnl_phn_num,otherDetail,password,"",str_rltn,
                remark,salutation,specific,gnl_sub,userName,"")

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.createLead(createLeadRequest)
        call.enqueue(object : Callback<CreateLeadResponse?> {
            override fun onResponse(call: Call<CreateLeadResponse?>, response: Response<CreateLeadResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    val img = response.body()?.Response?.Message
                    if(response.body()?.Response?.StatusCode=="200") {
                        try {
                            outAnimation = AlphaAnimation(1f, 0f)
                            outProgress()
                            img?.let { Log.e("image", it) }
                            Toast.makeText(this@UpdateLeadActivity, img, Toast.LENGTH_SHORT).show()
                            Intent(this@UpdateLeadActivity, UpdateLeadActivity::class.java).also {
                                it.putExtra("LeadId", str_lead_Id)
                                startActivity(it)
                                finish()
                            }

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
    private fun outProgress(){
        outAnimation = AlphaAnimation(1f, 0f)
        inAnimation?.duration = 200
        binding.updateleadProgressLayout.progressOverlay.animation = outAnimation
        binding.updateleadProgressLayout.progressOverlay.visibility = View.GONE

    }

    @SuppressLint("SetTextI18n")
    fun  Calender(){
        try{
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        updatelayout_lead_other_details.et_trgt_period.setOnClickListener {

            val dpd = DatePickerDialog(this, { view, year, monthOfYear, dayOfMonth ->
                val mn = monthOfYear+1
                updatelayout_lead_other_details.et_trgt_period.setText("$dayOfMonth-$mn-$year")
                val trgt =  updatelayout_lead_other_details.et_trgt_period.text.toString()
                val split = trgt.split("-")
                val dateee = split[0]
                val month1 = split[1]
                val year1 = split[2]
                trgtdate=("$year1-$month1-$dateee")

            }, year, month, day)
            dpd.show()
        }

            binding.layoutLeadSdQuestionare.etFireSet.setOnClickListener {
                val dpd = DatePickerDialog(this, { _, year, monthOfYear, dayOfMonth ->
                    val mn = monthOfYear+1
                    binding.layoutLeadSdQuestionare.etFireSet.setText("$dayOfMonth-$mn-$year")
                    val fire =   binding.layoutLeadSdQuestionare.etFireSet.text.toString()
                    val split = fire.split("-")
                    val dateee = split[0]
                    val month1 = split[1]
                    val year1 = split[2]
                    firesSet=("$year1-$month1-$dateee")
                }, year, month, day)
                dpd.show()
            }

            binding.layoutLeadSdQuestionare.etMplsRequired.setOnClickListener {
                val dpd = DatePickerDialog(this, { _, year, monthOfYear, dayOfMonth ->
                    val mn = monthOfYear+1
                    binding.layoutLeadSdQuestionare.etMplsRequired.setText("$dayOfMonth-$mn-$year")
                    val mplsRequired =  binding.layoutLeadSdQuestionare.etMplsRequired.text.toString()
                    val split = mplsRequired.split("-")
                    val dateee = split[0]
                    val month1 = split[1]
                    val year1 = split[2]
                    mpls=("$year1-$month1-$dateee")
                }, year, month, day)
                dpd.show()
            }


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun inProgress(){
        inAnimation = AlphaAnimation(0f, 1f)
        inAnimation?.duration =200
        binding.updateleadProgressLayout.progressOverlay.animation = inAnimation
        binding.updateleadProgressLayout.progressOverlay.visibility = View.VISIBLE
    }

    fun getLead () {
      inProgress()
        if(strContactId!=null){
            str_lead_Id =""
        }
        val getLeadRequest = GetLeadRequest(Constants.GET_LEADS,Constants.AUTH_KEY,str_lead_Id,password,userName,strContactId)

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getLead(getLeadRequest)
        call.enqueue(object : Callback<LeadResponsee?> {
            @SuppressLint("SimpleDateFormat", "SetTextI18n")
            override fun onResponse(call: Call<LeadResponsee?>, response: Response<LeadResponsee?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {

                        if(response.body()?.StatusCode==200) {
                            outProgress()
                            str_lead_Id =  response.body()?.Response?.get(0)?.LeadId
                            // binding.updatelayoutChannelSource.updateChannel = response.body()
                            binding.updatelayoutContactinfoLayout.updateContact = response.body()
                            binding.updatelayoutLeadCompanyDetails.updateCompany = response.body()?.Response?.get(0)?.companyDetail
                            binding.updatelayoutLeadInstallationAddress.updateAdress = response.body()?.Response?.get(0)?.installationAddress
                            binding.updatelayoutLeadOtherDetails.updateOther = response.body()?.Response?.get(0)?.otherDetail
                            binding.updateLeadContactAddress.updateContactAdd = response.body()?.Response?.get(0)?.contactAddress
                            binding.updatelayoutLeadRemarks.updateRemarks = response.body()
                            binding.updatelayoutChannelSource.updateChannel = response.body()

                            val flrStatus = response.body()?.Response?.get(0)?.FLRstatus
                            dis.visibility = View.VISIBLE
                            if (flrStatus == "1") {
                                tv_qualify.visibility = View.VISIBLE
                            } else if (flrStatus == "3") {
                                dis.visibility = View.VISIBLE
                            }

                            val strStatus = response.body()?.Response?.get(0)?.Status
                            str_othercity = response.body()?.Response?.get(0)?.otherDetail?.CurrentWorkingLocationId
                            strMobile = response.body()?.Response?.get(0)?.MobileNo.toString()
                            str_grp = response.body()?.Response?.get(0)?.GroupId.toString()
                            strRelation = response.body()?.Response?.get(0)?.RelationshipId.toString()
                            strIndustry = response.body()?.Response?.get(0)?.companyDetail?.IndustryType.toString()
                            strArea = response.body()?.Response?.get(0)?.installationAddress?.InstallAreaName.toString()
                            strCity = response.body()?.Response?.get(0)?.installationAddress?.InstallCityName.toString()
                            strContactCity = response.body()?.Response?.get(0)?.contactAddress?.ContactCityName.toString()
                            strBuilding = response.body()?.Response?.get(0)?.installationAddress?.InstallBuildingName.toString()
                            strContactArea = response.body()?.Response?.get(0)?.contactAddress?.ContactAreaName.toString()
                            strContactBuilding = response.body()?.Response?.get(0)?.contactAddress?.ContactBuilding.toString()
                            strVertical = response.body()?.Response?.get(0)?.VerticalID
                            strIllServices = response.body()?.Response?.get(0)?.sdwan?.CustomerILLservices.toString()
                            strBroadServices  = response.body()?.Response?.get(0)?.sdwan?.CustomerBroadbandServices.toString()
                            strLinksManaged = response.body()?.Response?.get(0)?.sdwan?.LinksManagedLinks.toString()
                            strRoutingServices  = response.body()?.Response?.get(0)?.sdwan?.CustomerRoutingServices.toString()
                            strCityReqd = response.body()?.Response?.get(0)?.sdwan?.CurrentOperationalCity.toString()
                            strNetworkSecurity  = response.body()?.Response?.get(0)?.sdwan?.NetworkSecurityServices.toString()
                            strHosted = response.body()?.Response?.get(0)?.sdwan?.ApplicationsHosted.toString()
                            strCustomer  = response.body()?.Response?.get(0)?.sdwan?.ItSupport.toString()
                            strBackBone = response.body()?.Response?.get(0)?.sdwan?.MPLSBackbone.toString()
                            strContract  = response.body()?.Response?.get(0)?.sdwan?.RedundancyMPLS.toString()
                            binding.layoutLeadSdQuestionare.etNoOfLocation.setText(response.body()?.Response?.get(0)?.sdwan?.NoOfLocation.toString())
                            binding.layoutLeadSdQuestionare.etLinks.setText(response.body()?.Response?.get(0)?.sdwan?.NumberOfLinksBroadbandServices.toString())
                            binding.layoutLeadSdQuestionare.etItSpent.setText(response.body()?.Response?.get(0)?.sdwan?.IndicationITSpent.toString())
                            binding.layoutLeadSdQuestionare.etRemarkk.setText(response.body()?.Response?.get(0)?.sdwan?.SdwanRemarks.toString())
                            binding.layoutLeadSdQuestionare.etMentionNum.setText(response.body()?.Response?.get(0)?.sdwan?.NumberOfLinksIll.toString())
                            str_inst_build_num = response.body()?.Response?.get(0)?.installationAddress?.InstallBuilding.toString()
                            str_inst_building_nm = response.body()?.Response?.get(0)?.contactAddress?.ContactBuilding.toString()
                            str_cntct_building_nm= response.body()?.Response?.get(0)?.contactAddress?.ContactBuildingName.toString()
                            updatelayout_lead_installation_address.et_building.setText("$strBuilding($str_inst_build_num)")

                            str_inst_areaname = response.body()?.Response?.get(0)?.installationAddress?.InstallAreaName.toString()
                            str_inst_area = response.body()?.Response?.get(0)?.installationAddress?.InstallArea.toString()
                            updatelayout_lead_installation_address.et_cntarea.setText("$strArea($str_inst_area)")
                            getCompany()

                            getOtherCity(str_othercity)
                            strCompanyName  =  response.body()?.Response?.get(0)?.CompanyNameCompany
                            str_cmp =  response.body()?.Response?.get(0)?.CompanyId
                            getRelation(str_cmp)
                            str_city_code = response.body()?.Response?.get(0)?.installationAddress?.InstallCity.toString()
                            str_lead_src = response.body()?.Response?.get(0)?.LeadSource
                            str_ContactcityCode = response.body()?.Response?.get(0)?.contactAddress?.ContactCity
                            strcontact_stateCode =response.body()?.Response?.get(0)?.contactAddress?.ContactState
                            str_add_area  =response.body()?.Response?.get(0)?.contactAddress?.ContactArea
                            cntareaname =response.body()?.Response?.get(0)?.contactAddress?.ContactAreaName
                            if(cntareaname?.isNotEmpty()==true) {
                                update_lead_contact_address.et_contacttarea.setText("$cntareaname($str_add_area)")
                            }
                            if(str_cntct_building_nm?.isNotEmpty()==true) {
                                update_lead_contact_address.et_cnt_building.setText("$str_cntct_building_nm($strContactBuilding)")
                            }
                            val strContactstate = response.body()?.Response?.get(0)?.contactAddress?.ContactStateName
                            var cntstatePosition = 0
                            SalesConstant.list_of_state.forEachIndexed { index, s ->
                                if (s == strContactstate) cntstatePosition = index
                            }
                            val cntstateAdapter = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item,  SalesConstant.list_of_state)
                            cntstateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            update_lead_contact_address.sp_cnt_state.adapter = cntstateAdapter
                            update_lead_contact_address.sp_cnt_state.setSelection(cntstatePosition)
                            cntstateAdapter.notifyDataSetChanged()
                            getIndustryTpe()
                            str_industry_type = response.body()?.Response?.get(0)?.companyDetail?.IndustryType

                            str_media = response.body()?.Response?.get(0)?.otherDetail?.Media
                            var mediaPosition = 0
                            SalesConstant.list_of_media.forEachIndexed { index, s ->
                                if (index == str_media?.toInt()) {
                                    mediaPosition = index
                                    return@forEachIndexed
                                }
                            }
                            val frstname = response.body()?.Response?.get(0)?.FirstName
                            val lastname = response.body()?.Response?.get(0)?.LastName
                            et_lead_name.setText("$frstname $lastname")
                            val media = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item,  SalesConstant.list_of_media)
                            media.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            updatelayout_lead_other_details.sp_media?.adapter = media
                            updatelayout_lead_other_details.sp_media.setSelection(mediaPosition)
                            media.notifyDataSetChanged()
                            val subbus = response.body()?.Response?.get(0)?.SubBusinessSegment
                            var sybbusPosition = 0
                            SalesConstant.list_of_subBusSegment.forEachIndexed { index, s ->
                                if (s == subbus) {
                                    sybbusPosition = index
                                    return@forEachIndexed
                                }
                            }
                            val subBusSeg = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item,  SalesConstant.list_of_subBusSegment)
                            subBusSeg.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            updatelayout_contactinfo_layout.sp_sub_bus?.adapter = subBusSeg
                            updatelayout_contactinfo_layout.sp_sub_bus.setSelection(sybbusPosition)
                            subBusSeg.notifyDataSetChanged()

                            val customer = response.body()?.Response?.get(0)?.CustomerSegment
                            var customersegPosition = 0
                            SalesConstant.list_cust_seg_value.forEachIndexed { index, s ->
                                if (s == customer) {
                                    customersegPosition = index
                                    return@forEachIndexed
                                }
                            }

                            val custSeg = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item,  SalesConstant.list_of_cust_segment)
                            custSeg.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            updatelayout_contactinfo_layout.sp_cust_seg?.adapter = custSeg
                            updatelayout_contactinfo_layout.sp_cust_seg.setSelection(customersegPosition)
                            custSeg.notifyDataSetChanged()

                            val cntry = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item,  SalesConstant.country_name)
                            cntry.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            updatelayout_lead_installation_address.sp_cntry?.adapter = cntry

                            val disqualify = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item,  SalesConstant.disqualify)
                            disqualify.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            sp_disqualify.adapter = disqualify

                            str_ext_serv_pro_one = response.body()?.Response?.get(0)?.otherDetail?.ExistingServiceProvider1
                            var provOnePosition = 0
                            SalesConstant.ext_serv_one_value.forEachIndexed { index, s ->
                                if (s == str_ext_serv_pro_one) {
                                    provOnePosition = index
                                    return@forEachIndexed
                                }
                            }
                            val serv1 = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item,  SalesConstant.ext_serv_one)
                            serv1.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            updatelayout_lead_other_details.sp_ex_serv?.adapter = serv1
                            updatelayout_lead_other_details.sp_ex_serv.setSelection(provOnePosition)
                            serv1.notifyDataSetChanged()

                            str_serv_pro_two = response.body()?.Response?.get(0)?.otherDetail?.ExistingServiceProvider2
                            var provTwePosition = 0
                            SalesConstant.ext_serv_one_value.forEachIndexed { index, s ->
                                if (s == str_serv_pro_two) {
                                    provTwePosition = index
                                    return@forEachIndexed
                                }
                            }

                            val serv2 = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item,  SalesConstant.ext_serv_one)
                            serv2.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            updatelayout_lead_other_details.sp_ext_serv_two?.adapter = serv2
                            updatelayout_lead_other_details.sp_ext_serv_two.setSelection(provTwePosition)
                            serv2.notifyDataSetChanged()

                            str_cust_serv_one = response.body()?.Response?.get(0)?.otherDetail?.ServiceFromServiceProvider1
                            var servproOnePosition = 0
                            SalesConstant.ext_serv_val.forEachIndexed { index, s ->
                                if (s == str_cust_serv_one) {
                                    servproOnePosition = index
                                    return@forEachIndexed
                                }
                            }
                            val servOneAdap = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item,  SalesConstant.ext_serv)
                            servOneAdap.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            updatelayout_lead_other_details.sp_serv_pro_one?.adapter = servOneAdap
                            updatelayout_lead_other_details.sp_serv_pro_one.setSelection(servproOnePosition)
                            servOneAdap.notifyDataSetChanged()

                            str_cust_serv_two = response.body()?.Response?.get(0)?.otherDetail?.ServiceFromServiceProvider2
                            var servproTwoPosition = 0
                            SalesConstant.ext_serv_val.forEachIndexed { index, s ->
                                if (s == str_cust_serv_two) {
                                    servproTwoPosition = index
                                    return@forEachIndexed
                                }
                            }
                            val servTwoAdap = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item,  SalesConstant.ext_serv)
                            servTwoAdap.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            updatelayout_lead_other_details.sp_serv_pro_two?.adapter = servTwoAdap
                            updatelayout_lead_other_details.sp_serv_pro_two.setSelection(servproTwoPosition)
                            servTwoAdap.notifyDataSetChanged()
                            str_data = response.body()?.Response?.get(0)?.otherDetail?.IsDatacenter?.toString()

                            val dataAdapter = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item,  SalesConstant.list_of_option)
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            updatelayout_lead_other_details.sp_intrsteddata_center?.adapter = dataAdapter
                            if (str_data.equals("true")) {
                                updatelayout_lead_other_details.sp_intrsteddata_center.setSelection(1)
                            } else  if (str_data.equals("false")) {
                                updatelayout_lead_other_details.sp_intrsteddata_center.setSelection(2)
                            }
                            dataAdapter.notifyDataSetChanged()

                            str_cust_frwl = response.body()?.Response?.get(0)?.otherDetail?.IsFirewall.toString()
                            val frwAdapter = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item,  SalesConstant.list_of_option)
                            frwAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            updatelayout_lead_other_details.sp_intrs_frwal?.adapter = frwAdapter

                            if (str_cust_frwl.equals("true")) {
                                updatelayout_lead_other_details.sp_intrs_frwal.setSelection(1)
                            } else  if (str_cust_frwl.equals("false")){
                                updatelayout_lead_other_details.sp_intrs_frwal.setSelection(2)
                            }
                            frwAdapter.notifyDataSetChanged()

                            str_cust_vpn = response.body()?.Response?.get(0)?.otherDetail?.IsVPN.toString()
                            val vpnadpter = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item,  SalesConstant.list_of_option)
                            vpnadpter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            updatelayout_lead_other_details.sp_vpn_serv?.adapter = vpnadpter
                            if (str_cust_vpn.equals("true")) {
                                updatelayout_lead_other_details.sp_vpn_serv.setSelection(1)
                            } else  if (str_cust_vpn.equals("false")) {
                                updatelayout_lead_other_details.sp_vpn_serv.setSelection(2)
                            }
                            vpnadpter.notifyDataSetChanged()

                            str_voip = response.body()?.Response?.get(0)?.otherDetail?.IsVOIP.toString()
                            val voipadpter = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item,  SalesConstant.list_of_option)
                            voipadpter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            updatelayout_lead_other_details.sp_cst_voip?.adapter = voipadpter
                            if (str_voip.equals("true")) {
                                updatelayout_lead_other_details.sp_cst_voip.setSelection(1)
                            } else if (str_voip.equals("false")){
                                updatelayout_lead_other_details.sp_cst_voip.setSelection(2)
                            }
                            voipadpter.notifyDataSetChanged()

                            str_wifi = response.body()?.Response?.get(0)?.otherDetail?.IsManagesWiFi.toString()
                            val wifipadpter = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item,  SalesConstant.list_of_option)
                            wifipadpter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            updatelayout_lead_other_details.sp_cust_wifi?.adapter = wifipadpter
                            if (str_wifi.equals("true") ) {
                                updatelayout_lead_other_details.sp_cust_wifi.setSelection(1)
                            } else  if (str_wifi.equals("false") ) {
                                updatelayout_lead_other_details.sp_cust_wifi.setSelection(2)
                            }
                            wifipadpter.notifyDataSetChanged()
                            str_inst_state = response.body()?.Response?.get(0)?.installationAddress?.InstallState.toString()
                            getCity(str_inst_state.toString())
                            val strstate = response.body()?.Response?.get(0)?.installationAddress?.InstallStateName.toString()
                            var statePosition = 0
                            SalesConstant.list_of_state.forEachIndexed { index, s ->
                                if (s == strstate) statePosition = index
                            }
                            val stateAdapter = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item,  SalesConstant.list_of_state)
                            stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            updatelayout_lead_installation_address.sp_state?.adapter = stateAdapter
                            updatelayout_lead_installation_address.sp_state.setSelection(statePosition)
                            stateAdapter.notifyDataSetChanged()

                            str_lead_chnl = response.body()?.Response?.get(0)?.LeadChannel
                            var channePosition = 0
                            SalesConstant.list_of_channel.forEachIndexed { index, s ->
                                if (s == str_lead_chnl) channePosition = index
                            }
                            val adapterchannel = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item,  SalesConstant.list_of_channel)
                            adapterchannel.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            updatelayout_channel_source.sp_leadchnl?.adapter = adapterchannel
                            updatelayout_channel_source.sp_leadchnl.setSelection(channePosition)
                            adapterchannel.notifyDataSetChanged()


                            val sal = response.body()?.Response?.get(0)?.SalutationId
                            str_firm_type = response.body()?.Response?.get(0)?.companyDetail?.FirmType
                            var firmPosition = 0
                            SalesConstant.list_firm_type.forEachIndexed { index, s ->
                                if (index == str_firm_type?.toInt()) {
                                    firmPosition = index
                                    return@forEachIndexed
                                }
                            }
                            val firmAdapter = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item,  SalesConstant.list_firm_type)
                            firmAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            updatelayout_lead_company_details.sp_firm_type?.adapter = firmAdapter
                            updatelayout_lead_company_details.sp_firm_type.setSelection(firmPosition)
                            firmAdapter.notifyDataSetChanged()
                            et_lead_channel.isEnabled=false
                            et_lead_source.isEnabled=false
                            var salutationPosition = 0
                            try {
                                if (sal != null || sal != "") {
                                    SalesConstant.list_of_salutation_id.forEachIndexed { index, _ ->
                                        if (index == sal?.toInt()) {
                                            salutationPosition = index
                                            return@forEachIndexed
                                        }
                                    }
                                }
                            }catch (E :NumberFormatException){
                                E.printStackTrace()
                            }
                            val salutationAdapter = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item,  SalesConstant.list_of_salutation)
                            salutationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            updatelayout_contactinfo_layout.sp_salutation?.adapter = salutationAdapter
                            updatelayout_contactinfo_layout.sp_salutation.setSelection(salutationPosition)
                            salutationAdapter.notifyDataSetChanged()

                            var illServicesPosition = 0
                            resources.getStringArray(R.array.listCustomerServiceVal).forEachIndexed { index, s ->
                                if (s == strIllServices) illServicesPosition = index
                                return@forEachIndexed
                            }
                            val illServices = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.listCustomerService))
                            illServices.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            binding.layoutLeadSdQuestionare.spCustomerUsingIllServices.adapter = illServices
                            binding.layoutLeadSdQuestionare.spCustomerUsingIllServices.setSelection(illServicesPosition)
                            illServices.notifyDataSetChanged()

                            var broadServicesPosition = 0
                            resources.getStringArray(R.array.listCustomerServiceVal).forEachIndexed { index, s ->
                                if (s == strBroadServices) {
                                    broadServicesPosition = index
                                    return@forEachIndexed
                                }
                            }
                            val broadServices = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.listCustomerService))
                            broadServices.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            binding.layoutLeadSdQuestionare.spBroadServices.adapter = broadServices
                            binding.layoutLeadSdQuestionare.spBroadServices.setSelection(broadServicesPosition)
                            broadServices.notifyDataSetChanged()

                            var linkPosition = 0
                            resources.getStringArray(R.array.listMangedLinkVal).forEachIndexed { index, s ->
                                if (s == strLinksManaged) {
                                    linkPosition = index
                                    return@forEachIndexed
                                }
                            }
                            val linkManaged = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.listManagedLink))
                            linkManaged.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            binding.layoutLeadSdQuestionare.spLinksManged.adapter = linkManaged
                            binding.layoutLeadSdQuestionare.spLinksManged.setSelection(linkPosition)
                            linkManaged.notifyDataSetChanged()

                            var routingPosition = 0
                            resources.getStringArray(R.array.listCustomerServiceVal).forEachIndexed { index, s ->
                                if (s == strRoutingServices) {
                                    routingPosition = index
                                    return@forEachIndexed
                                }
                            }
                            val routingService = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.listCustomerService))
                            routingService.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            binding.layoutLeadSdQuestionare.spRoutingServices.adapter = routingService
                            binding.layoutLeadSdQuestionare.spRoutingServices.setSelection(routingPosition)
                            routingService.notifyDataSetChanged()

                            var securityPosition = 0
                            resources.getStringArray(R.array.listMangedLinkVal).forEachIndexed { index, s ->
                                if (s == strNetworkSecurity) {
                                    securityPosition = index
                                    return@forEachIndexed
                                }
                            }
                            val networkSecurity = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.listNetworkSecurity))
                            networkSecurity.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            binding.layoutLeadSdQuestionare.spNetworkSecurity.adapter = networkSecurity
                            binding.layoutLeadSdQuestionare.spNetworkSecurity.setSelection(securityPosition)
                            networkSecurity.notifyDataSetChanged()

                            var hostedPosition = 0
                            resources.getStringArray(R.array.listApplicantsVal).forEachIndexed { index, s ->
                                if (s == strHosted) {
                                    hostedPosition = index
                                    return@forEachIndexed
                                }
                            }
                            val hosted = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.listApplicants))
                            hosted.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            binding.layoutLeadSdQuestionare.spHostedRequired.adapter = hosted
                            binding.layoutLeadSdQuestionare.spHostedRequired.setSelection(hostedPosition)
                            hosted.notifyDataSetChanged()

                            var customerPosition = 0
                            resources.getStringArray(R.array.listCustomerServiceVal).forEachIndexed { index, s ->
                                if (s == strCustomer) {
                                    customerPosition = index
                                    return@forEachIndexed
                                }
                            }
                            val customerReq = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.listInHouse))
                            customerReq.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            binding.layoutLeadSdQuestionare.spCustomerRequired.adapter = customerReq
                            binding.layoutLeadSdQuestionare.spCustomerRequired.setSelection(customerPosition)
                            customerReq.notifyDataSetChanged()

                            var backBonePosition = 0
                            resources.getStringArray(R.array.listCustomerServiceVal).forEachIndexed { index, s ->
                                if (s == strLinksManaged) {
                                    backBonePosition = index
                                    return@forEachIndexed
                                }
                            }
                            val backBone = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.listCustomerService))
                            backBone.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            binding.layoutLeadSdQuestionare.spBackboneRequired.adapter = backBone
                            binding.layoutLeadSdQuestionare.spBackboneRequired.setSelection(backBonePosition)
                            backBone.notifyDataSetChanged()

                            var contractPosition = 0
                            resources.getStringArray(R.array.listCustomerServiceVal).forEachIndexed { index, s ->
                                if (s == strContract) {
                                    contractPosition = index
                                    return@forEachIndexed
                                }
                            }
                            val contract = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.listCustomerService))
                            contract.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            binding.layoutLeadSdQuestionare.spContractRenewed.adapter = contract
                            binding.layoutLeadSdQuestionare.spContractRenewed.setSelection(contractPosition)
                            contract.notifyDataSetChanged()

                            val targetdate = response.body()?.Response?.get(0)?.otherDetail?.TargetInstallationPeriod.toString()
                             if(targetdate.isNotEmpty()){
                                 val split = targetdate.split("-")
                                 val date = split[0]
                                 val month = split[1]
                                 val year = split[2]
                                 updatelayout_lead_other_details.et_trgt_period.setText("$date-$month-$year")
                                 trgtdate=("$year-$month-$date")
                             }
                            val firewallSet = response.body()?.Response?.get(0)?.sdwan?.FirewallSetToExpire.toString()
                            if(firewallSet.isNotEmpty()){
                                val split = firewallSet.split("-")
                                val date = split[0]
                                val month = split[1]
                                val year = split[2]
                                binding.layoutLeadSdQuestionare.etFireSet.setText("$date-$month-$year")
                                firesSet=("$year-$month-$date")
                            }
                            val redundancyMPLS = response.body()?.Response?.get(0)?.sdwan?.MPLSContract.toString()
                            if(redundancyMPLS.isNotEmpty()){
                                val split = redundancyMPLS.split("-")
                                val date = split[0]
                                val month = split[1]
                                val year = split[2]
                                binding.layoutLeadSdQuestionare.etMplsRequired.setText("$date-$month-$year")
                                mpls=("$year-$month-$date")
                            }

                            when (strStatus) {
                                "1" -> {
                                    locked()
                                    oppurtunity.visibility= View.VISIBLE
                                    lead_updatesave.visibility=View.GONE
                                }
                                "2" -> {
                                    locked()
                                    oppurtunity.visibility= View.GONE
                                    lead_updatesave.visibility = View.GONE

                                }
                                else -> {
                                    oppurtunity.visibility= View.GONE
                                    lead_updatesave.visibility = View.VISIBLE
                                    Calender()
                                }
                            }

                            linearcontactinfo.visibility = View.VISIBLE
                            linear_insta_addres.visibility = View.GONE
                            linadd.visibility = View.GONE
                            linear_contact_person_address.visibility = View.GONE
                            linear_companydetails.visibility= View.GONE
                            linearother_details.visibility = View.GONE
                            linearremark_details.visibility = View.GONE
                            listener()

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
      back()
    }
    fun back(){
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        builder.setMessage("Do you want to go back to the previous screen?")
        builder.setPositiveButton(
            "Yes"
        ) { _, _ ->
            val intent = Intent(this, LeadTabActivity::class.java)
            startActivity(intent)
            finish()
        }
        builder.setNegativeButton(
            "No"
        ) { dialog, _ ->
            dialog.cancel()
        }
        val alert: AlertDialog = builder.create()
        alert.show()
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


    private fun getBuilding(areaname: String?, areaCode: String?) {
        val getLeadBuildingRequest = GetLeadBuildingRequest(Constants.GET_BUILDING,Constants.AUTH_KEY,areaCode,areaname,password,userName)

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
                        if (buildingList != null) {
                            for (item in buildingList) {
                                building?.add(item.BuildingName+"("+item.BuildingCode+")")
                              /*  item.BuildingName?.let { building?.add(it) }
                                item.BuildingCode?.let { buildingCode?.add(it) }*/
                            }
                        }
                        val adapter12 = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item, building!!)
                        updatelayout_lead_installation_address.et_building.threshold=0
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        updatelayout_lead_installation_address.et_building.setAdapter(adapter12)
                        adapter12.notifyDataSetChanged()

                        updatelayout_lead_installation_address.et_building.setOnFocusChangeListener { _, b ->
                            if (b)   updatelayout_lead_installation_address.et_building.showDropDown()
                        }
                        updatelayout_lead_installation_address.et_building.onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, _ ->
                            str_inst_build_name = adapter12.getItem(position)
                            val buildingName =  updatelayout_lead_installation_address.et_building.text.toString()
                            /* str_inst_build_num = buildingCode?.get(position)*/
                            val split = buildingName.split("(")
                            val buildingId = split.get(1)
                            val buildingNamee = split.get(1)
                            var buildingPosition=0
                            building?.forEachIndexed { index, s ->
                                if(s==str_inst_build_name)buildingPosition=index
                                str_inst_build_name.let { it?.let { it1 -> Log.e("idddddddddd", it1) } }
                                return@forEachIndexed
                            }
                            val areaId = buildingId.split(")")
                            str_inst_build_num = areaId[0]
                            /* strInstallBuildCode = buildingCode?.get(position)*/

                            if(buildingNamee=="Other"){
                                updatelayout_lead_installation_address.et_upspecfcbuilding_num.visibility=View.VISIBLE
                            }else{
                                updatelayout_lead_installation_address.et_upspecfcbuilding_num.visibility=View.GONE
                            }
                           /* if (position != 0) str_inst_build_num =
                                buildingCode?.get(position)
                            if(str_inst_build_name=="Other"){
                                updatelayout_lead_installation_address.et_upspecfcbuilding_num.visibility=View.VISIBLE
                            }else{
                                updatelayout_lead_installation_address.et_upspecfcbuilding_num.visibility=View.GONE
                            }*/
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

    private fun getContactBuilding(areaname: String?, areaCode: String?) {
        val getLeadBuildingRequest = GetLeadBuildingRequest(Constants.GET_BUILDING,Constants.AUTH_KEY,areaCode,areaname,password,userName)

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getleadBuilding(getLeadBuildingRequest)
        call.enqueue(object : Callback<GetLeadBuildingResponse?> {
            override fun onResponse(call: Call<GetLeadBuildingResponse?>, response: Response<GetLeadBuildingResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        //val msg = response.body()?.Response?.Message
                        val  buildingArray= response.body()?.Response?.Data
                        cntbuilding = ArrayList<String>()
                        cntbuildingCode = ArrayList<String>()
                   /*     cntbuilding?.add("Select Building")
                        cntbuildingCode?.add("")*/
                        if (buildingArray != null) {
                            for (item in buildingArray){
                                cntbuilding?.add(item.BuildingName+"("+item.BuildingCode+")")

                                /*  item.BuildingName?.let { cntbuilding?.add(it) }
                                  item.BuildingCode?.let { cntbuildingCode?.add(it) }*/
                            }
                        }
                        val adapter12 = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item, cntbuilding!!)
                        update_lead_contact_address.et_cnt_building.threshold=0
                        update_lead_contact_address.et_cnt_building.setAdapter(adapter12)
                        //adapter12.notifyDataSetChanged()

                        update_lead_contact_address.et_cnt_building.setOnFocusChangeListener { _, b ->
                            if (b)   update_lead_contact_address.et_cnt_building.showDropDown()
                        }
                        update_lead_contact_address.et_cnt_building.onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, _ ->
                            str_cntct_building_nm = adapter12.getItem(position)
                            val build =   update_lead_contact_address.et_cnt_building.text.toString()
                            val split = build.split("(")
                            val buildingId = split[1]
                            val buildingName = split[0]
                            var buildingPosition=0
                            building?.forEachIndexed { index, s ->
                                if(s==str_cntct_building_nm)buildingPosition=index
                                str_cntct_building_nm.let { it?.let { it1 -> Log.e("idddddddddd", it1) } }
                                return@forEachIndexed
                            }
                            val areaId = buildingId.split(")")
                            str_inst_building_nm = areaId[0]
                            /* strInstallBuildCode = buildingCode?.get(position)*/
                            if(buildingName=="Other"){
                                update_lead_contact_address.et_cntspbuilding_name.visibility=View.VISIBLE
                            }else{
                                update_lead_contact_address.et_cntspbuilding_name.visibility=View.GONE
                            }
                           /* str_cntct_building_nm=name
                             str_inst_building_nm =
                                cntbuildingCode?.get(position)
                             if(str_cntct_building_nm=="Other"){
                                update_lead_contact_address.et_cntspbuilding_name.visibility=View.VISIBLE
                            }else{
                                update_lead_contact_address.et_cntspbuilding_name.visibility=View.GONE
                            }*/
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

    fun getCompany() {
        inProgress()
        val getCompanyRequest = GetCompanyRequest(Constants.GET_COMPANY,Constants.AUTH_KEY,str_cmp,password,userName)
        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getComapnyList(getCompanyRequest)
        call.enqueue(object : Callback<GetCompanyResponse?> {
            override fun onResponse(call: Call<GetCompanyResponse?>, response: Response<GetCompanyResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        outProgress()
                        val img = response.body()?.Response?.Message
                        img?.let { Log.e("image", it) }
                        companyList = response.body()?.Response?.Data
                        company = ArrayList<String>()
                        company?.add("Select Company")
                        group = ArrayList<String>()
                        groupId = ArrayList<String>()
                        group?.add("Select Group")
                        groupId?.add("0")
                        for (item in companyList!!) {
                            company?.add(item.Company_Name + " (" + item.Company_ID + ")")
                            group?.add(item.Group_Name + " (" + item.Group_ID + ")")
                            groupId?.add(item.Group_ID)
                        }

                        val adapter12 = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item, company!!)
                        updatelayout_contactinfo_layout.et_updtcompany.threshold=0
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        updatelayout_contactinfo_layout.et_updtcompany.setAdapter(adapter12)

                        updatelayout_contactinfo_layout.et_updtcompany.setOnFocusChangeListener { _, b ->
                            if (b) updatelayout_contactinfo_layout.et_updtcompany.showDropDown()
                        }
                        updatelayout_contactinfo_layout.et_updtcompany.onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, _ ->
                            strCompanyName = adapter12.getItem(position)
                            val split = strCompanyName?.split("(")
                            val compid = split?.get(1)
                            var compPosition = 0
                            company?.forEachIndexed { index, s ->
                                if (s == strCompanyName) compPosition = index
                                strCompanyName?.let { Log.e("idddddddddd", it) }
                                return@forEachIndexed
                            }
                            val companyidd = compid?.split(")")
                            str_cmp = companyidd?.get(0)
                            str_cmp?.let { Log.e("compid", it) }
                            getRelation(str_cmp)
                            if (strCompanyName != null || strCompanyName == "Select Company") {
                                if (strCompanyName.contentEquals("New Company (CNew)") || strCompanyName == "Select Company") {
                                    updatelayout_contactinfo_layout.et_lead_name.setText("")
                                    updatelayout_lead_company_details.et_company_name.setText("")
                                    if (strCompanyName.contentEquals("New Company (CNew)")) {
                                        Toast.makeText(this@UpdateLeadActivity, "Please search company name from drop-down before selecting New Company", Toast.LENGTH_SHORT).show()
                                    }
                                } else {
                                    val name = strCompanyName?.split("(")
                                    val cmpname = name?.get(0)
                                    updatelayout_contactinfo_layout.et_lead_name.setText(cmpname)
                                    updatelayout_lead_company_details.et_company_name.setText(cmpname)
                                }
                                val adapter1 = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item, group!!)
                                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_item)
                                updatelayout_contactinfo_layout.sp_upgroup.adapter = adapter1
                                updatelayout_contactinfo_layout.sp_upgroup.setSelection(compPosition)
                                adapter1.notifyDataSetChanged()

                            }
                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<GetCompanyResponse?>, t: Throwable) {
                binding.updateleadProgressLayout.progressOverlay.visibility = View.GONE
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
                                relation?.add(item.Relationship_Name +" ("+item.Relationship_ID+")")
                                item.Relationship_ID?.let { relationId?.add(it) }
                            }
                        }
                        var relationPosition=0
                        relationId?.forEachIndexed { index, s ->
                            if(s==strRelation)relationPosition=index
                            return@forEachIndexed
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

    private fun getSource(str_lead_chnl: String?) {
        val getLeadSourceRequest =
                str_lead_chnl?.let {
                    GetLeadSourceRequest(Constants.GET_SOURCE,Constants.AUTH_KEY,
                            it,userName,password)
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
                str_city_code.toString(), str_city.toString() ,"",userName,password,true)

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
                            area?.add(item.AreaName+"("+item.AreaCode+")")
                            /*  item.AreaName?.let { area?.add(it) }
                              item.AreaCode?.let { areaCode?.add(it) }*/
                        }


                        val adapter12 = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item, area!!)
                        updatelayout_lead_installation_address.et_cntarea.threshold=0
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        updatelayout_lead_installation_address.et_cntarea.setAdapter(adapter12)

                        updatelayout_lead_installation_address.et_cntarea.setOnFocusChangeListener { _, b ->
                            if (b) updatelayout_lead_installation_address.et_cntarea.showDropDown()
                        }

                        updatelayout_lead_installation_address.et_cntarea.onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, _ ->
                            str_inst_areaname = adapter12.getItem(position)
                            val areaaa = updatelayout_lead_installation_address.et_cntarea.text.toString()
                            val split = areaaa.split("(")
                            val Area =  split[0]
                            val Areaid = split[1]
                            var areaPosition=0
                            area?.forEachIndexed { index, s ->
                                if(s==str_inst_areaname)areaPosition=index
                                return@forEachIndexed
                            }
                            val areaId = Areaid.split(")")
                            str_inst_area = areaId[0]
                            if(Area=="Other"){
                                updatelayout_lead_installation_address.et_upspefc_area.visibility=View.VISIBLE
                            }else{
                                updatelayout_lead_installation_address.et_upspefc_area.visibility=View.GONE
                            }
                            getBuilding(str_inst_areaname,str_inst_area)

                       /*     if (position != 0) str_inst_area = areaCode?.get(position)
*/
                           /* if(str_inst_areaname=="Other"){
                                updatelayout_lead_installation_address.et_upspefc_area.visibility=View.VISIBLE
                            }else{
                                updatelayout_lead_installation_address.et_upspefc_area.visibility=View.GONE
                            }
                         */

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


    private fun getCntArea(str_city: String?, str_city_code: String) {
        val getLeadAreaRequest =   GetLeadAreaRequest(Constants.Get_AREA,Constants.AUTH_KEY,
                str_city_code, str_city.toString() ,"",userName,password,true)


        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getLeadArea(getLeadAreaRequest)
        call.enqueue(object : Callback<GetLeadAreaRes?> {
            override fun onResponse(call: Call<GetLeadAreaRes?>, response: Response<GetLeadAreaRes?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        val img = response.body()?.Response?.Message
                        img?.let { Log.e("image", it) }
                        areaList= response.body()?.Response?.Data
                        cntarea = ArrayList<String>()
                        cntareaCode = ArrayList<String>()
                     /*   cntarea?.add("")
                        cntareaCode?.add("")*/
                        for (item in areaList!!){
                            cntarea?.add(item.AreaName+"("+item.AreaCode+")")
                            /*  item.AreaName?.let { cntarea?.add(it) }
                              item.AreaCode?.let { cntareaCode?.add(it) }*/
                        }

                        val adapter12 = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item, cntarea!!)
                        update_lead_contact_address.et_contacttarea.threshold=0
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        update_lead_contact_address.et_contacttarea.setAdapter(adapter12)

                        update_lead_contact_address.et_contacttarea.setOnFocusChangeListener { _, b ->
                            if (b)  update_lead_contact_address.et_contacttarea.showDropDown()
                        }

                        update_lead_contact_address.et_contacttarea.onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, _ ->
                            cntareaname = adapter12.getItem(position)
                            val area =  update_lead_contact_address.et_contacttarea.text.toString()
                            val split = area.split("(")
                            val Area =  split[0]
                            val Areaid = split[1]
                            var areaPosition=0
                            cntarea?.forEachIndexed { index, s ->
                                if(s==cntareaname)areaPosition=index
                                area.let { Log.e("idddddddddd", it) }
                                return@forEachIndexed
                            }
                            val areaId = Areaid.split(")")
                            str_add_area = areaId[0]
                            if(Area=="Other"){
                                update_lead_contact_address.et_cntspbuilding_num.visibility=View.VISIBLE
                            }else{
                                update_lead_contact_address.et_cntspbuilding_num.visibility=View.GONE
                            }
                            getContactBuilding(Area,str_add_area)
                           /* if (position != 0) str_add_area = areaCode?.get(position)
                            if(cntareaname=="Other"){
                                update_lead_contact_address.et_cntspbuilding_num.visibility=View.VISIBLE
                            }else{
                                update_lead_contact_address.et_cntspbuilding_num.visibility=View.GONE
                            }
                          */

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


    fun getCity(str_inst_state: String) {
        val getCityRequest = GetCityRequest(Constants.GET_CITY,Constants.AUTH_KEY,password,str_inst_state,userName)

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getCityList(getCityRequest)
        call.enqueue(object : Callback<GetCityResponse?> {
            override fun onResponse(call: Call<GetCityResponse?>, response: Response<GetCityResponse?>) {
              if (response.isSuccessful && response.body() != null) {
                    try {
                        val img = response.body()?.Response?.Message
                        img?.let { Log.e("image", it) }
                        val cityList = response.body()?.Response?.Data
                        city = ArrayList<String>()
                        cityCode = ArrayList<String>()
                        city?.add("Select City")
                        cityCode?.add("")
                        for (item in cityList!!) {
                            city?.add(item.CityName)
                            cityCode?.add(item.CityCode)
                        }
                        var cityPosition=0
                        city?.forEachIndexed { index, s ->
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

    private fun getContactCity(strcontact_stateCode: String) {
        val getCityRequest = GetCityRequest(Constants.GET_CITY,Constants.AUTH_KEY,password,strcontact_stateCode,userName)

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getCityList(getCityRequest)
        call.enqueue(object : Callback<GetCityResponse?> {
            override fun onResponse(call: Call<GetCityResponse?>, response: Response<GetCityResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        val img = response.body()?.Response?.Message
                        img?.let { Log.e("image", it) }
                       val cityList = response.body()?.Response?.Data
                        contactCity = ArrayList<String>()
                        contactCityCode = ArrayList<String>()
                        contactCity?.add("Select City")
                        contactCityCode?.add("")
                        for (item in cityList!!) {
                            contactCity?.add(item.CityName)
                            contactCityCode?.add(item.CityCode)
                        }
                        var cityPosition=0
                        contactCity?.forEachIndexed { index, s ->
                            if(s==strContactCity)cityPosition=index
                        }
                        val adapter12 = ArrayAdapter(this@UpdateLeadActivity, android.R.layout.simple_spinner_item, contactCity!!)
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
            updatelayout_contactinfo_layout.et_sub_busiessseg.setText( SalesConstant.list_of_subBusSegment.get(position))
            str_sub_bus =  SalesConstant.list_of_subBusSegment[position]
            if(str_sub_bus==AppConstants.SDWAN) {
                str_rltn="0"
                CoroutineScope(Dispatchers.IO).launch {
                    getVertical()
                    getRequiredCity()
                }
                binding.updatelayoutContactinfoLayout.relation.visibility=View.GONE
                binding.updatelayoutContactinfoLayout.vertical.visibility=View.VISIBLE
                binding.linearqustionare.visibility=View.VISIBLE
            }else{
               // str_rltn=""
                binding.updatelayoutContactinfoLayout.relation.visibility=View.VISIBLE
                binding.updatelayoutContactinfoLayout.vertical.visibility=View.GONE
                binding.linearqustionare.visibility=View.GONE
            }
        }else if(parent?.id ==R.id.sp_vertical){
            binding.updatelayoutContactinfoLayout.etVertical.setText(vertical?.get(position))
            strVertical = verticalId?.get(position)
        }else if(parent?.id ==R.id.sp_customerUsingIllServices){
            binding.layoutLeadSdQuestionare.etCustomerUsingIllServices.setText(resources.getStringArray(R.array.listCustomerService)[position])
            strIllServices =resources.getStringArray(R.array.listCustomerServiceVal)[position]
            if(strIllServices.equals("122050000")){
                binding.layoutLeadSdQuestionare.mention.visibility=View.VISIBLE
            }else{
                binding.layoutLeadSdQuestionare.mention.visibility=View.GONE
            }
        }
        else if(parent?.id ==R.id.sp_broadServices){
            binding.layoutLeadSdQuestionare.etBroadServices.setText(resources.getStringArray(R.array.listCustomerService)[position])
            strBroadServices =resources.getStringArray(R.array.listCustomerServiceVal)[position]
            if(strBroadServices.equals("122050000")){
                binding.layoutLeadSdQuestionare.links.visibility=View.VISIBLE
            }else{
                binding.layoutLeadSdQuestionare.links.visibility=View.GONE
            }
        }
        else if(parent?.id ==R.id.sp_linksManged){
            binding.layoutLeadSdQuestionare.etLinksManged.setText(resources.getStringArray(R.array.listManagedLink)[position])
            strLinksManaged =resources.getStringArray(R.array.listMangedLinkVal)[position]
        }
        else if(parent?.id ==R.id.sp_routingServices){
            binding.layoutLeadSdQuestionare.etRoutingServices.setText(resources.getStringArray(R.array.listCustomerService)[position])
            strRoutingServices =resources.getStringArray(R.array.listCustomerServiceVal)[position]
        }
        else if(parent?.id ==R.id.sp_cityRequired){
            binding.layoutLeadSdQuestionare.etCityRequired.setText(RequiredCity?.get(position) )
            strCityReqd =(RequiredCityCode?.get(position))
        } else if(parent?.id ==R.id.sp_networkSecurity){
            binding.layoutLeadSdQuestionare.etNetworkSecurity.setText(resources.getStringArray(R.array.listNetworkSecurity)[position])
            strNetworkSecurity =resources.getStringArray(R.array.listMangedLinkVal)[position]
        }else if(parent?.id ==R.id.sp_hostedRequired){
            binding.layoutLeadSdQuestionare.etHostedRequired.setText(resources.getStringArray(R.array.listApplicants)[position])
            strHosted =resources.getStringArray(R.array.listApplicantsVal)[position]
        }else if(parent?.id ==R.id.sp_customerRequired){
            binding.layoutLeadSdQuestionare.etCustomerRequired.setText(resources.getStringArray(R.array.listInHouse)[position])
            strCustomer =resources.getStringArray(R.array.listCustomerServiceVal)[position]
        }else if(parent?.id ==R.id.sp_backboneRequired){
            binding.layoutLeadSdQuestionare.etBackboneRequired.setText(resources.getStringArray(R.array.listCustomerService)[position])
            strBackBone =resources.getStringArray(R.array.listCustomerServiceVal)[position]
        }else if(parent?.id ==R.id.sp_contractRenewed){
            binding.layoutLeadSdQuestionare.etContractRenewed.setText(resources.getStringArray(R.array.listCustomerService)[position])
            strContract =resources.getStringArray(R.array.listCustomerServiceVal)[position]
        } else if(parent?.id == R.id.sp_salutation){
            updatelayout_contactinfo_layout.et_saluation.setText( SalesConstant.list_of_salutation[position])
            str_salutation =  SalesConstant.list_of_salutation_id[position]
        }else if(parent?.id ==R.id.sp_leadchnl){
            updatelayout_channel_source.et_lead_channel.setText( SalesConstant.list_of_channel[position])
            str_lead_chnl =  SalesConstant.list_of_channel[position]
            getSource(str_lead_chnl)
        }else if(parent?.id ==R.id.sp_lead_src){
            updatelayout_channel_source.et_lead_source.setText(source?.get(position))
            str_lead_src = source?.get(position)
        }/*else if(parent?.id == R.id.sp_cnarea){
            updatelayout_lead_installation_address.et_cntarea.setText(area?.get(position))
            str_inst_area = areaCode?.get(position )
            str_inst_areaname= area?.get(position).toString()
            if(str_inst_areaname=="Other"){
                updatelayout_lead_installation_address.et_upspefc_area.visibility=View.VISIBLE
            }else{
                updatelayout_lead_installation_address.et_upspefc_area.visibility=View.GONE
            }
            if(str_inst_area!=null || str_inst_areaname!=null) {
                getBuilding(str_inst_areaname, str_inst_area)
            }
        }*//*else if(parent?.id == R.id.sp_cnt_cnarea){
            update_lead_contact_address.et_contacttarea.setText(cntarea?.get(position))
            str_add_area = cntareaCode?.get(position )
            cntareaname= cntarea?.get(position).toString()
            if(cntareaname=="Other"){
                update_lead_contact_address.et_cntspbuilding.visibility=View.VISIBLE
            }else{
                update_lead_contact_address.et_cntspbuilding.visibility=View.GONE
            }
            if(str_add_area!=null || cntareaname!=null) {
                getContactBuilding(cntareaname, str_add_area)
            }
        }*/
        else if(parent?.id == R.id.sp_city){
            updatelayout_lead_installation_address.et_add_city.setText(city?.get(position))
            str_city = city?.get(position).toString()
            str_city_code =  cityCode?.get(position)
            getArea(str_city, str_city_code)
            val city =city?.get(position).toString()
            if(city=="Select City"){
                updatelayout_lead_installation_address.et_building.setText("")
            }
        }else if(parent?.id == R.id.sp_cnt_city){
            update_lead_contact_address.et_cnt_city.setText(contactCity?.get(position))
            str_Contactcity = contactCity?.get(position).toString()
            str_ContactcityCode =  contactCityCode?.get(position)
            getCntArea(str_Contactcity, str_ContactcityCode.toString())
            if(str_ContactcityCode==""){
                update_lead_contact_address.et_cnt_building.setText("")
            }
        }
       else if(parent?.id == R.id.sp_cntry){
            updatelayout_lead_installation_address.et_country.setText( SalesConstant.country_name[position])
            str_inst_country =  SalesConstant.country_name[position]
        }else if(parent?.id == R.id.sp_cust_seg){
            updatelayout_contactinfo_layout.et_customer_seg.setText( SalesConstant.list_of_cust_segment[position])
           str_customer_segmentid =  SalesConstant.list_cust_seg_value[position]
        }else if(parent?.id == R.id.sp_cust_wifi){
            updatelayout_lead_other_details.et_cust_wifi.setText( SalesConstant.list_of_option[position])
            str_wifi =  SalesConstant.list_of_booleanvalue[position]
        }else if(parent?.id == R.id.sp_ex_serv){
            updatelayout_lead_other_details.et_ext_srv.setText( SalesConstant.ext_serv_one[position])
            str_ext_serv_pro_one =  SalesConstant.ext_serv_one_value[position]
        }
        else if(parent?.id == R.id.sp_ext_serv_two){
            updatelayout_lead_other_details.et_ext_srv_two.setText( SalesConstant.ext_serv_one[position])
           str_serv_pro_two =  SalesConstant.ext_serv_one_value[position]
        }else if(parent?.id == R.id.sp_serv_pro_one){
            updatelayout_lead_other_details.et_service_pv_one.setText( SalesConstant.ext_serv[position])
           str_cust_serv_one =  SalesConstant.ext_serv_val[position]
        }else if(parent?.id == R.id.sp_serv_pro_two){
            updatelayout_lead_other_details.et_srv_pv_two.setText( SalesConstant.ext_serv[position])
            str_cust_serv_two =  SalesConstant.ext_serv_val[position]
        }else if(parent?.id == R.id.sp_state){
            updatelayout_lead_installation_address.et_state.setText( SalesConstant.list_of_state[position])
            str_state =  SalesConstant.list_of_state[position]
            Log.e("State",str_state.toString())
            str_inst_state=  SalesConstant.list_state_code[position]
            Log.e("State",str_inst_state.toString())
            getCity(str_inst_state.toString())
        }else if(parent?.id == R.id.sp_cnt_state){
            update_lead_contact_address.et_cnt_state.setText( SalesConstant.list_of_state[position])
            strcontact_state =  SalesConstant.list_of_state[position]
            strcontact_stateCode =  SalesConstant.list_state_code[position]
            getContactCity(strcontact_stateCode.toString())
        }
        else if(parent?.id == R.id.sp_cst_voip){
            updatelayout_lead_other_details.et_cust_void.setText( SalesConstant.list_of_boolean[position])
            str_voip =  SalesConstant.list_of_booleanvalue[position]
        }else if(parent?.id == R.id.sp_industype){
            updatelayout_lead_company_details.et_indus_type.setText(instryname[position])
            str_industry_type = industryid[position]
        }else if(parent?.id == R.id.sp_firm_type){
            updatelayout_lead_company_details.et_firm_type.setText( SalesConstant.list_firm_type[position])
         //   str_firm_type =  list_firm_type_value.get(position-1)
            str_firm_type =  SalesConstant.list_firm_type_value[position]
        }
        else if(parent?.id == R.id.sp_media){
            updatelayout_lead_other_details.et_media.setText( SalesConstant.list_of_media[position])
            str_media =  SalesConstant.list_of_mediavalue[position]
        }else if(parent?.id == R.id.sp_intrsteddata_center){
            updatelayout_lead_other_details.et_is_cus.setText(SalesConstant.list_of_option[position])
            str_data =  SalesConstant.list_of_booleanvalue[position]
        }else if(parent?.id == R.id.sp_intrs_frwal){
            updatelayout_lead_other_details.et_indus_firewl.setText( SalesConstant.list_of_option[position])
            str_cust_frwl =  SalesConstant.list_of_booleanvalue[position]
        }else if(parent?.id == R.id.sp_vpn_serv){
            updatelayout_lead_other_details.et_vpn_srv.setText( SalesConstant.list_of_boolean[position])
          str_cust_vpn =  SalesConstant.list_of_booleanvalue[position]
        }else if(parent?.id == R.id.sp_disqualify){
            if (position != 0) strDisqualify = "" +  SalesConstant.disqualifyCode[position - 1] else strDisqualify= " "
            if(strDisqualify.isNotEmpty()) {
                val disqualif:String
                if (position != 0) disqualif = "" +  SalesConstant.disqualifyCode[position - 1] else disqualif= " "
                if(disqualif.isEmpty() || disqualif.isBlank()){

                }else{
                    disQualifyLead(disqualif)
                }
            }

        }/*else if(parent?.id == R.id.sp_cmpny){
            updatelayout_contactinfo_layout.et_updtcompany.setText(company?.get(position))
            str_cmp =  companyId?.get(position )
            updatelayout_contactinfo_layout.et_upgroup.setText(group?.get(position))
            val strCompanyName = company?.get(position)
            if (strCompanyName.contentEquals("New Company (CNew)")||strCompanyName=="Select Company"){

            }else {
                val name = strCompanyName?.split("(")
                val cmpname = name?.get(0)
                updatelayout_contactinfo_layout.et_lead_name.setText(cmpname)
                updatelayout_lead_company_details.et_company_name.setText(cmpname)
            }

            str_grp = groupId?.get(position )
            getRelation(str_cmp)
        }*/else if(parent?.id == R.id.sp_upgroup){
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

    private fun qualifyLead() {
        inAnimation = AlphaAnimation(0f, 1f)
        inAnimation?.duration =200
        binding.updateleadProgressLayout.progressOverlay.animation = inAnimation
        binding.updateleadProgressLayout.progressOverlay.visibility = View.VISIBLE

        val qualifiedLeadRequest = QualifiedLeadRequest(Constants.QUALIFY_LEAD,Constants.AUTH_KEY,str_lead_Id.toString(),password,userName)
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
                        if(response.body()?.StatusCode=="200"){
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

    fun getOtherCity(strCurLocation: String?) {
        val getCityRequest = GetCityRequest(Constants.GET_CITY,Constants.AUTH_KEY,password,"",userName)

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getCityList(getCityRequest)
        call.enqueue(object : Callback<GetCityResponse?> {
            override fun onResponse(call: Call<GetCityResponse?>, response: Response<GetCityResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        val img = response.body()?.Response?.Message
                        img?.let { Log.e("image", it) }
                        cityList = response.body()?.Response?.Data
                        othercity = ArrayList<String>()
                        othercityCode = ArrayList<String>()
                        othercity?.add("Select City")
                        othercityCode?.add("")
                        for (item in cityList!!) {
                            othercity?.add(item.CityName)
                            othercityCode?.add(item.CityCode)
                        }
                        var cityPosition=0
                        othercityCode?.forEachIndexed { index, s ->
                            if(s==strCurLocation)cityPosition=index
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

    private fun disQualifyLead(disqualif: String) {
        inAnimation = AlphaAnimation(0f, 1f)
        inAnimation?.duration =200
        binding.updateleadProgressLayout.progressOverlay.animation = inAnimation
        binding.updateleadProgressLayout.progressOverlay.visibility = View.VISIBLE

        val disqualifyLead = DisqualifyLead(Constants.DISQUALIFY_LEAD,Constants.AUTH_KEY,str_lead_Id.toString(),password,disqualif,userName)

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
                             Intent(this@UpdateLeadActivity, LeadTabActivity::class.java).also {
                                 it.putExtra("LeadId", str_lead_Id)
                                 startActivity(it)
                                 finish()
                             }
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