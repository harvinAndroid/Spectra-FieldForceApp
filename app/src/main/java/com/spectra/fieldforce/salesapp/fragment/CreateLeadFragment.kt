package com.spectra.fieldforce.salesapp.fragment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
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
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.spectra.fieldforce.R
import com.spectra.fieldforce.api.ApiClient
import com.spectra.fieldforce.api.ApiInterface
import com.spectra.fieldforce.databinding.LeadDemoFragmentBinding
import com.spectra.fieldforce.salesapp.activity.LeadTabActivity
import com.spectra.fieldforce.salesapp.model.*
import com.spectra.fieldforce.utils.AppConstants
import com.spectra.fieldforce.utils.Constants
import kotlinx.android.synthetic.main.lead__contact_person_row.view.*
import kotlinx.android.synthetic.main.lead__contact_person_row.view.et_cnt_building
import kotlinx.android.synthetic.main.lead__contact_person_row.view.et_cnt_buildng_num
import kotlinx.android.synthetic.main.lead__contact_person_row.view.et_cnt_city
import kotlinx.android.synthetic.main.lead__contact_person_row.view.et_cnt_country
import kotlinx.android.synthetic.main.lead__contact_person_row.view.et_cnt_floor
import kotlinx.android.synthetic.main.lead__contact_person_row.view.et_cnt_pin_code
import kotlinx.android.synthetic.main.lead__contact_person_row.view.et_cnt_state
import kotlinx.android.synthetic.main.lead__contact_person_row.view.sp_cnt_building_nm
import kotlinx.android.synthetic.main.lead__contact_person_row.view.sp_cnt_city
import kotlinx.android.synthetic.main.lead__contact_person_row.view.sp_cnt_cnarea
import kotlinx.android.synthetic.main.lead__contact_person_row.view.sp_cnt_cntry
import kotlinx.android.synthetic.main.lead__contact_person_row.view.sp_cnt_state
import kotlinx.android.synthetic.main.lead_channel_source_row.view.*
import kotlinx.android.synthetic.main.lead_company_details_row.view.*
import kotlinx.android.synthetic.main.lead_contact_info.view.*
import kotlinx.android.synthetic.main.lead_contact_info.view.et_business_seg
import kotlinx.android.synthetic.main.lead_contact_info.view.et_contact_person
import kotlinx.android.synthetic.main.lead_contact_info.view.et_customer_seg
import kotlinx.android.synthetic.main.lead_contact_info.view.et_saluation
import kotlinx.android.synthetic.main.lead_contact_info.view.sp_cust_seg
import kotlinx.android.synthetic.main.lead_contact_info.view.sp_salutation
import kotlinx.android.synthetic.main.lead_contact_info.view.sp_sub_bus
import kotlinx.android.synthetic.main.lead_demo_fragment.*
import kotlinx.android.synthetic.main.lead_demo_fragment.linadd
import kotlinx.android.synthetic.main.lead_demo_fragment.linear_companydetails
import kotlinx.android.synthetic.main.lead_demo_fragment.linear_contact_person_address
import kotlinx.android.synthetic.main.lead_demo_fragment.linear_insta_addres
import kotlinx.android.synthetic.main.lead_demo_fragment.linearcontactinfo
import kotlinx.android.synthetic.main.lead_demo_fragment.lineareight
import kotlinx.android.synthetic.main.lead_demo_fragment.linearfive
import kotlinx.android.synthetic.main.lead_demo_fragment.linearfouraddres
import kotlinx.android.synthetic.main.lead_demo_fragment.linearnine
import kotlinx.android.synthetic.main.lead_demo_fragment.linearother_details
import kotlinx.android.synthetic.main.lead_demo_fragment.linearremark_details
import kotlinx.android.synthetic.main.lead_demo_fragment.linearsix
import kotlinx.android.synthetic.main.lead_demo_fragment.linearthree
import kotlinx.android.synthetic.main.lead_demo_fragment.lineartwo
import kotlinx.android.synthetic.main.lead_installation_address_row.view.*
import kotlinx.android.synthetic.main.lead_other_details_row.view.*
import kotlinx.android.synthetic.main.lead_remarks_row.view.*
import kotlinx.android.synthetic.main.toolbar.view.*
import kotlinx.android.synthetic.main.update_lead_demo_fragment.*
import kotlinx.android.synthetic.main.updatelead__contact_person_row.view.*
import kotlinx.android.synthetic.main.updatelead_contact_personadd_row.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import java.util.regex.Pattern


class CreateLeadFragment:Fragment(), View.OnClickListener,AdapterView.OnItemSelectedListener {

    lateinit var  leadFragmentBinding: LeadDemoFragmentBinding

    private var source : ArrayList<String>? = null
    private var sourceList: MutableList<SrcData>? = null
    private var areaList : ArrayList<AreaData>? = null
    private var area : ArrayList<String>? = null
    private var areaCode : ArrayList<String>? = null
    private var Installarea : ArrayList<String>? = null
    private var InstallareaCode : ArrayList<String>? = null
    private var cityList : ArrayList<CityData>? = null
    private var city : ArrayList<String>? = null
    private var cityCode : ArrayList<String>? = null
    private var Installcity : ArrayList<String>? = null
    private var InstallcityCode : ArrayList<String>? = null
    private var buildingList : ArrayList<BuildData>? = null
    private var building : ArrayList<String>? = null
    private var buildingCode : ArrayList<String>? = null
    private var company : ArrayList<String>? = null
    private var companyId : ArrayList<String>? = null
    private var companyList: ArrayList<ComapnyData>? = null
    private lateinit var companyData: ComapnyData
    private var group : ArrayList<String>? = null
    private var groupId : ArrayList<String>? = null
    private var relation : ArrayList<String>? = null
    private var relationId : ArrayList<String>? = null
    private var relationList: ArrayList<RelationshipData>? = null
    private var state : ArrayList<String>? = null
    private var stateId : ArrayList<String>? = null
    private var inAnimation: AlphaAnimation? = null
    private var outAnimation: AlphaAnimation? = null
    private var industryList : ArrayList<IndustryResponse>? = null
    private var instryname = ArrayList<String>()
    private var industryid = ArrayList<String>()
    private var othercity : ArrayList<String>? = null
    private var othercityCode : ArrayList<String>? = null
    private var str_othercity: String? = null
    private var str_area: String? = null
    private var str_city: String? = null
    private var str_city_code : String? = null
    private var strCityCode : String? = null
    private var strCompanyName : String? = null
    var str_customer_segmentid :String? = null
    var str_lead_chnl : String? = null
    var str_installbuild : String? = null
    var str_lead_src : String? = null
    var date :String ?=null
    var str_sub_bus : String? = null
    var str_firm_type :String? = null
    var str_industry_type :String? = null
    var str_salutation :String? = null
    var str_state : String? = null
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
    var str_company :String ? = null
    var str_grp :String ? = null
    var str_rltn:String ? = null
    var str_rltnname:String ? = null
    var str_cmp :String? = null
    var str_inst_country: String? = null
    var str_inst_state: String? = null
    var str_inst_area : String? = null
    var str_inst_building_name : String? = null
    var str_inst_build_num : String? = null
    var str_add_country : String? = null
    var str_add_state: String? = null
    var str_add_state_code : String? = null
    var str_add_city: String? = null
    var str_add_area_code: String? = null
    var str_add_building : String? = null
    var str_chkbox : String? = null
    var userName: String? = null
    var password : String? = null
    var str_specificbuild : String? = null
    var strCity = ""
    var strArea =""
    var strBuilding=""
     var list_of_salutation = arrayOf("Select Salutation","Mr.", "Mrs.", "Miss")
    var list_of_salutation_id = arrayOf("1","2","3")
    var list_of_option = arrayOf("Select Options","Yes","No")
    var list_of_channel = arrayOf("Select Channel","Call/SMS-Inbound","Caretel","CM Outbound","Email/Email Campaigns","Inside Sales","Inside Sales-QC","Kaizala","NetOps Channel","Online CAF","Outbound Call",
    "Paid Campaign/Activity","Promotion/BTL/ATL/Events/Sponsorship/Visibility Activity","Self Care Portal","Self Lead","Unify Churned","Web Campaign")
    var list_of_subBusSegment = arrayOf("Select Sub Business Segment","Connectivity Solution", "Data Centre Products", "Internet Service","SDWAN","SIP-Trunk","VOIP")
    var list_firm_type = arrayOf("Select Firm Type","Proprietorship","Partnership","Pvt Ltd","Ltd","Trust","Individual")
    var list_of_cust_segment = arrayOf("Select Customer Segment","SDWAN","SMB","Media","LA","SP")
    var list_cust_seg_value = arrayOf("111260004","111260000","111260001","111260002","111260003")
    var list_firm_type_value = arrayOf("1","2","3","4","5","6")
    var list_of_state = arrayOf("Select State", "Andhra Pradesh","Bihar","Delhi"
    ,"Gujarat","Haryana","Jammu and Kashmir","Karnataka"
    ,"Kerala", "Madhya Pradesh","Maharashtra","Odisha", "Other*",
    "Punjab","Rajasthan","Tamil Nadu", "Telangana","Uttar Pradesh","Uttarakhand","West Bengal")

    var list_of_boolean = arrayOf("True","False")

    var list_state_code = arrayOf("","100009","100021","100004", "100015","100008",
    "100011","100007", "100012","100014","100002","100026",
    "100017","100025", "100010", "100003","100023","100006",
    "100016","100013")

    var country_name = arrayOf("India")

    var ext_serv_one = arrayOf("Select Existing Service Provider","Jio", "ACT Fibernet","N.A",
        "Others","Airtel","Aircel","BSNL", "Hathway","MTNL","Nextra",
        "Reliance Communications","Sify","Tata Communications","Tata DOCOMO",
        "Tikona Infinet","Vodafone")

    var ext_serv_one_value = arrayOf("111260000",
        "569480014","569480012","569480013","569480000","569480002",
        "569480003","569480004","569480005","569480006","569480007",
        "569480008","569480009","569480010","569480011","569480001")

    var ext_serv = arrayOf("Select Existing Service Provider",
        "Internet", "Data Center Services","VOIP Services","Other Services")
    
    var list_of_media = arrayOf("Fibre","RF")
    var list_of_mediavalue = arrayOf("1","2")

    var ext_serv_val = arrayOf("569480000","569480001","569480002","569480003")

    companion object {
       /* fun newInstance(): CreateLeadFragment {
            return CreateLeadFragment()
        }*/
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        leadFragmentBinding = LeadDemoFragmentBinding.inflate(inflater, container, false)
        return leadFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchtoolbarlead.rl_back.setOnClickListener(this)
        searchtoolbarlead.tv_lang.text= AppConstants.LEAD_DETAILS
        val sp1: SharedPreferences? = context?.getSharedPreferences("Login", 0)
        userName = sp1?.getString("UserName", null)
        password = sp1?.getString("Password", null)
        listener()
        init()
        Calender()
        getCompany()
        getIndustryTpe()
        getOtherCity()
        setAdpter()
        itemListener()


    }


    fun init(){
        checkbx.setOnCheckedChangeListener { _, isChecked ->
            str_chkbox="1"
            val inst_state_code = str_inst_state.toString()
            var cntstatePosition = 0
            list_state_code.forEachIndexed { index, s ->
                if (s == inst_state_code) cntstatePosition = index
            }
            val cntstateAdapter = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, list_of_state) }
            cntstateAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_item)
            layout_lead_contact_address.sp_cnt_state.adapter = cntstateAdapter
            layout_lead_contact_address.sp_cnt_state.setSelection(cntstatePosition)
            cntstateAdapter?.notifyDataSetChanged()
            getAddCity(inst_state_code)
            strCity = str_city_code.toString()
            strArea = str_inst_area.toString()
            strBuilding = str_inst_build_num.toString()
            val inst_floor = layout_lead_installation_address.et_add_floor.text.toString()
            val inst_pincode = layout_lead_installation_address.et_pin_code.text.toString()
            val inst_buil = layout_lead_installation_address.et_add_build_num.text.toString()
            val inst_block = layout_lead_installation_address.et_block.text.toString()
            layout_lead_contact_address.et_cnt_floor.setText(inst_floor)
            layout_lead_contact_address.et_cnt_pin_code.setText(inst_pincode)
            layout_lead_contact_address.et_cnt_buildng_num.setText(inst_buil)
            layout_lead_contact_address.et_cnt_block.setText(inst_block)
            val inst_sp_area =layout_lead_installation_address.et_specific_area.text.toString()
            val inst_sp_building =layout_lead_installation_address.et_specific_building.text.toString()
            layout_lead_contact_address.et_cntspecific_area.setText(inst_sp_area)
            layout_lead_contact_address.et_cntbuilding_num.setText(inst_sp_building)
        }

        lead_save.setOnClickListener {
            val remark:String = layout_lead_remarks.et_lead_remark.text.toString()
            val companyname:String =layout_lead_company_details.et_company_name.text.toString()
            val firmtype:String = str_firm_type.toString()
            val industrytype: String = str_industry_type.toString()
            val jbtitle:String = layout_lead_company_details.et_job_title.text.toString()
            val area = str_add_area_code.toString()
            val addres_build =str_add_building.toString()
            val city =str_add_city.toString()
            val floor = layout_lead_contact_address.et_cnt_floor.text.toString()
            val pincode =  layout_lead_contact_address.et_cnt_pin_code.text.toString()
            val building =  layout_lead_contact_address.et_cnt_buildng_num.text.toString()
            val state = str_add_state_code.toString()
            val spcfc_area =  layout_lead_contact_address.et_cntspecific_area.text.toString()
            val spcfc_building =  layout_lead_contact_address.et_cntbuilding_num.text.toString()
            val block =  layout_lead_contact_address.et_cnt_block.text.toString()
           // val buildingname = str_add_building.toString()
            val inst_area = str_inst_area.toString()
            val inst_build =str_inst_build_num.toString()
            val inst_build_name = str_inst_building_name.toString()
            val inst_city_code = str_city_code
            val inst_city = str_city.toString()
            //val inst_country = str_inst_country.toString()
            val inst_floor = layout_lead_installation_address.et_add_floor.text.toString()
            val inst_pincode = layout_lead_installation_address.et_pin_code.text.toString()
            val inst_buil = layout_lead_installation_address.et_add_build_num.text.toString()
            val inst_block = layout_lead_installation_address.et_block.text.toString()

            val inst_state = str_inst_state.toString()
          //  val inst_state_code = str_state.toString()
            val inst_sp_area =layout_lead_installation_address.et_specific_area.text.toString()
            val inst_sp_building =layout_lead_installation_address.et_specific_building.text.toString()

            val other_work = str_othercity.toString()
            val other_pro_one = str_ext_serv_pro_one.toString()
            val other_pro_two=str_serv_pro_two.toString()
            val other_date = str_data.toString()
            val other_firewal = str_cust_frwl.toString()
            val other_targetdate = date
            val other_wifi =  str_wifi.toString()
            val other_voip =  str_voip.toString()
            val other_vpn = str_cust_vpn.toString()
            val other_media = str_media.toString()
            val other_cust_one = str_cust_serv_one.toString()
            val cust_two = str_cust_serv_two.toString()
            val genral_name = lead_contactinfo_layout.et_lead_nm.text.toString()
            val general_lst_nm = lead_contactinfo_layout.et_lead_nm.text.toString()
            val general_chnl = str_lead_chnl.toString()
            val general_src= str_lead_src.toString()
            val gnl_phn_num = lead_contactinfo_layout.et_mobile_num.text.toString()
            val gnl_sub = str_sub_bus.toString()
            val cnt_info_cnt_person = lead_contactinfo_layout.et_contact_person.text.toString()
            val general_email = lead_contactinfo_layout.et_upemailid.text.toString()
            val topic = lead_contactinfo_layout.et_topic.text.toString()
            val group = str_grp.toString()
            val relation = str_rltn.toString()
            val company = str_cmp.toString()
            val customer_seg = str_customer_segmentid.toString()
            val salutation = str_salutation.toString()
            val target =  layout_lead_other_details.et_trgt_period.text.toString()
            validEmail(general_email)
            if(topic.isBlank()){
                Toast.makeText(context, "Please Enter Topic", Toast.LENGTH_SHORT).show()
            }else if(gnl_sub.isBlank()||gnl_sub=="Select Sub Business Segment"||gnl_sub=="null"){
                Toast.makeText(context, "Please Select Sub Business Segment", Toast.LENGTH_SHORT).show()
            }else if(customer_seg.isBlank()||customer_seg=="Select Customer Segment"||customer_seg=="null"){
                Toast.makeText(context, "Please Select Customer Segment", Toast.LENGTH_SHORT).show()
            }else if(salutation.isBlank()||salutation=="Select Salutation"||(salutation=="null")){
                Toast.makeText(context, "Please Select Salutation", Toast.LENGTH_SHORT).show()
            }else if(cnt_info_cnt_person.isBlank()){
                Toast.makeText(context, "Please Enter Contact Person", Toast.LENGTH_SHORT).show()
            }else if(general_email.isBlank()||(!validEmail(general_email))){
                Toast.makeText(context, "Please Enter Email Id", Toast.LENGTH_SHORT).show()
            }else if(gnl_phn_num.isBlank()|| gnl_phn_num.length!=10){
                Toast.makeText(context, "Please Enter Mobile Number", Toast.LENGTH_SHORT).show()
            }else if(genral_name.isBlank()){
                Toast.makeText(context, "Please Enter Lead Name", Toast.LENGTH_SHORT).show()
            }else if(company.isBlank()||company=="Select Company"||(company=="null")){
                Toast.makeText(context, "Please Select Company", Toast.LENGTH_SHORT).show()
            }else if(group.isBlank()||group=="Select Group"||(group=="null")){
                Toast.makeText(context, "Please enter Group", Toast.LENGTH_SHORT).show()
            }else if(relation.isBlank()||relation=="Select Relation"||(relation=="null")){
                Toast.makeText(context, "Please Select Relation", Toast.LENGTH_SHORT).show()
            }else if(general_chnl.isBlank()||general_chnl=="Select Channel"||(general_chnl=="null")){
                Toast.makeText(context, "Please Select Channel", Toast.LENGTH_SHORT).show()
            }else if(general_src.isBlank()||general_src=="null"){
                Toast.makeText(context, "Please Select Source", Toast.LENGTH_SHORT).show()
            }else if(inst_state.isBlank()||inst_state=="Select State"||inst_state=="null"){
                Toast.makeText(context, "Please Select State", Toast.LENGTH_SHORT).show()
            }else if(inst_city.isBlank()||inst_city=="Select City"||inst_city=="null"){
                Toast.makeText(context, "Please Select City", Toast.LENGTH_SHORT).show()
            }else if(inst_area.isBlank()||inst_area=="Select Area"||(inst_area=="null")){
                Toast.makeText(context, "Please Select Area", Toast.LENGTH_SHORT).show()
            }else if((str_installbuild== "Other")&&(inst_sp_area.isEmpty())){
                Toast.makeText(context, "Please Enter Specific Area Name", Toast.LENGTH_SHORT).show()
            }else if(str_inst_building_name?.isBlank()==true||str_inst_building_name=="Select Building"||(str_inst_building_name=="null")){
                Toast.makeText(context, "Please Select Building Name", Toast.LENGTH_SHORT).show()
            }else if(inst_sp_building.isBlank()&&str_inst_building_name=="Other"){
                Toast.makeText(context, "Please Select Specific Building Name", Toast.LENGTH_SHORT).show()
            }
            else if(inst_build_name.isBlank()||inst_build_name=="Select Building"||(inst_build_name=="null")){
                Toast.makeText(context, "Please Select Building No.", Toast.LENGTH_SHORT).show()
            }else if(inst_buil.isBlank()||(inst_buil=="null")){
                Toast.makeText(context, "Please enter Building No.", Toast.LENGTH_SHORT).show()
            }else if(inst_block.isBlank()){
                Toast.makeText(context, "Please enter Block", Toast.LENGTH_SHORT).show()
            }else if(inst_floor.isBlank()){
                Toast.makeText(context, "Please enter Floor", Toast.LENGTH_SHORT).show()
            }else if(inst_pincode.isBlank()|| inst_pincode.length!=6){
                Toast.makeText(context, "Please enter PinCode", Toast.LENGTH_SHORT).show()
            }else if(state.isBlank()||state=="Select State"||(state=="null")){
                Toast.makeText(context, "Please Select State", Toast.LENGTH_SHORT).show()
            }else if(city.isBlank()||city=="Select City"||(city=="null")){
                Toast.makeText(context, "Please Select City", Toast.LENGTH_SHORT).show()
            }else if(area.isBlank()||area=="Select Area"||(area=="null")){
                Toast.makeText(context, "Please Select Area", Toast.LENGTH_SHORT).show()
            }else if((str_area=="Other")&&(spcfc_area.isEmpty())){
                Toast.makeText(context, "Please Enter Specific Area Name", Toast.LENGTH_SHORT).show()
            }else if(str_add_building?.isBlank()==true||str_add_building=="Select Building Name"||(str_add_building=="null")){
                Toast.makeText(context, "Please enter Building Name", Toast.LENGTH_SHORT).show()
            }else if(spcfc_building.isBlank()&&str_specificbuild=="Other"){
                Toast.makeText(context, "Please Select Specific Building Name", Toast.LENGTH_SHORT).show()
            }else if(building.isBlank()||(building=="null")){
                Toast.makeText(context, "Please enter Building No.", Toast.LENGTH_SHORT).show()
            }else if(block.isBlank()){
                Toast.makeText(context, "Please enter Block", Toast.LENGTH_SHORT).show()
            }else if(floor.isBlank()){
                Toast.makeText(context, "Please enter Floor", Toast.LENGTH_SHORT).show()
            }else if(pincode.isBlank()|| pincode.length!=6){
                Toast.makeText(context, "Please enter Pincode", Toast.LENGTH_SHORT).show()
            }else if(companyname.isBlank()||(companyname=="null")){
                Toast.makeText(context, "Please Enter Company Name", Toast.LENGTH_SHORT).show()
            }else if(firmtype.isBlank()||firmtype=="Select Firm Type"||(firmtype=="null")){
                Toast.makeText(context, "Please Select Firm Type", Toast.LENGTH_SHORT).show()
            }else if(industrytype.isBlank()||industrytype=="Select Industry"||(industrytype=="null")){
                Toast.makeText(context, "Please Select Industry Type", Toast.LENGTH_SHORT).show()
            }else if(jbtitle.isBlank()||(jbtitle=="null")){
                Toast.makeText(context, "Please enter Job Title", Toast.LENGTH_SHORT).show()
            }else if(other_media.isBlank()||other_media=="Select Media"){
                Toast.makeText(context, "Please Select Media", Toast.LENGTH_SHORT).show()
            }else if(other_pro_one.isBlank()||other_pro_one=="Select Existing Service Provider"||(other_pro_one=="null")){
                Toast.makeText(context, "Please Select Existing Service Provider One", Toast.LENGTH_SHORT).show()
            }else if(other_pro_two.isBlank()||other_pro_two=="Select Existing Service Provider"||(other_pro_two=="null")){
                Toast.makeText(context, "Please Select Existing Service Provider Two", Toast.LENGTH_SHORT).show()
            } else if(other_work.isBlank()||(other_work=="null")){
                Toast.makeText(context, "Please Select Current Work location", Toast.LENGTH_SHORT).show()
            }else if(other_date.isBlank()||(other_date=="null")){
                Toast.makeText(context, "Please Select Data Center", Toast.LENGTH_SHORT).show()
            }else if(other_firewal.isBlank()||other_firewal=="Select Option"||(other_firewal=="null")){
                Toast.makeText(context, "Please Select Firewall", Toast.LENGTH_SHORT).show()
            } else if(other_vpn.isBlank()||(other_vpn=="null")){
                Toast.makeText(context, "Please Select Vpn Services", Toast.LENGTH_SHORT).show()
            }else if(other_cust_one.isBlank()||other_cust_one=="Select Existing Service Provider"||(other_cust_one=="null")){
                Toast.makeText(context, "Please Select Existing Service Provider One", Toast.LENGTH_SHORT).show()
            }else if(cust_two.isBlank()||cust_two=="Select Existing Service Provider"||(cust_two=="null")){
                Toast.makeText(context, "Please Select Existing Service Provider Two", Toast.LENGTH_SHORT).show() }
            else if(other_voip.isBlank()||other_voip=="Select Option"||(other_voip=="null")){
                Toast.makeText(context, "Please Select Voip", Toast.LENGTH_SHORT).show()
            }else if(other_wifi.isBlank()||other_wifi=="Select Option"||(other_wifi=="null")){
                Toast.makeText(context, "Please Select Manage Wifi", Toast.LENGTH_SHORT).show()
            }else if(target.isBlank()||(target=="null")){
                    Toast.makeText(context, "Please Select Target Installation Period ", Toast.LENGTH_SHORT).show()
                }else {
                        createLead(remark,companyname,firmtype,industrytype,jbtitle,area,addres_build,city,floor,pincode,
                        building,spcfc_area,spcfc_building,block,inst_area,inst_build, inst_city_code,inst_floor,inst_pincode,
                        inst_buil,
                        inst_block,inst_sp_area,inst_sp_building,other_work,other_pro_one,other_pro_two,other_date,
                        other_firewal,other_targetdate,other_wifi,other_voip,other_vpn,other_media,other_cust_one,
                        cust_two,general_lst_nm,general_chnl,general_src,gnl_phn_num,gnl_sub,cnt_info_cnt_person,
                        general_email,topic,group,relation)
                }
        }
    }

    private fun validEmail(email: String): Boolean {
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }


    private fun itemListener(){
        lead_contactinfo_layout.et_business_seg.setText(AppConstants.BUSINESS)
        lead_contactinfo_layout.et_sub_businessseg.setOnClickListener { lead_contactinfo_layout.sp_sub_bus.performClick() }
        lead_contactinfo_layout.sp_sub_bus.onItemSelectedListener = this
        lead_contactinfo_layout.et_saluation.setOnClickListener { lead_contactinfo_layout.sp_salutation.performClick() }
        lead_contactinfo_layout.sp_salutation.onItemSelectedListener = this
        layout_channel_source.et_lead_channel.setOnClickListener { layout_channel_source.sp_leadchnl.performClick() }
        layout_channel_source.sp_leadchnl.onItemSelectedListener = this
        layout_channel_source.et_lead_source.setOnClickListener { layout_channel_source.sp_lead_src.performClick() }
        layout_channel_source.sp_lead_src.onItemSelectedListener = this
        lead_contactinfo_layout.et_customer_seg.setOnClickListener { lead_contactinfo_layout.sp_cust_seg.performClick() }
        lead_contactinfo_layout.sp_cust_seg.onItemSelectedListener = this
       /* lead_contactinfo_layout.et_leadcompany.setOnClickListener { lead_contactinfo_layout.sp_leadcmpny.performClick() }
        lead_contactinfo_layout.sp_leadcmpny.onItemSelectedListener = this*/
        lead_contactinfo_layout.et_group.setOnClickListener { lead_contactinfo_layout.sp_group.performClick() }
        lead_contactinfo_layout.sp_group.onItemSelectedListener = this
        lead_contactinfo_layout.et_relation.setOnClickListener { lead_contactinfo_layout.sp_relation.performClick() }
        lead_contactinfo_layout.sp_relation.onItemSelectedListener = this
        layout_lead_company_details.et_firm_type.setOnClickListener { layout_lead_company_details.sp_firm_type.performClick() }
        layout_lead_company_details.sp_firm_type.onItemSelectedListener = this

        layout_lead_company_details.et_indus_type.setOnClickListener { layout_lead_company_details.sp_industype.performClick() }
        layout_lead_company_details.sp_industype.onItemSelectedListener = this
        layout_lead_other_details.et_cust_void.setOnClickListener { layout_lead_other_details.sp_cst_voip.performClick() }
        layout_lead_other_details.sp_cst_voip.onItemSelectedListener = this
        layout_lead_installation_address.et_country.setOnClickListener { layout_lead_installation_address.sp_cntry.performClick() }
        layout_lead_installation_address.sp_cntry.onItemSelectedListener = this
        layout_lead_installation_address.et_state.setOnClickListener { layout_lead_installation_address.sp_state.performClick() }
        layout_lead_installation_address.sp_state.onItemSelectedListener = this
        layout_lead_installation_address.et_add_city.setOnClickListener { layout_lead_installation_address.sp_city.performClick() }
        layout_lead_installation_address.sp_city.onItemSelectedListener = this
        layout_lead_installation_address.et_installarea.setOnClickListener { layout_lead_installation_address.sp_cnarea.performClick() }
        layout_lead_installation_address.sp_cnarea.onItemSelectedListener = this
        layout_lead_installation_address.et_building.setOnClickListener { layout_lead_installation_address.sp_building_nm.performClick() }
        layout_lead_installation_address.sp_building_nm.onItemSelectedListener = this

        layout_lead_contact_address.et_cnt_city.setOnClickListener { layout_lead_contact_address.sp_cnt_city.performClick() }
        layout_lead_contact_address.sp_cnt_city.onItemSelectedListener = this
        layout_lead_contact_address.et_cntarea.setOnClickListener { layout_lead_contact_address.sp_cnt_cnarea.performClick() }
        layout_lead_contact_address.sp_cnt_cnarea.onItemSelectedListener = this
        layout_lead_contact_address.et_cnt_building.setOnClickListener { layout_lead_contact_address.sp_cnt_building_nm.performClick() }
        layout_lead_contact_address.sp_cnt_building_nm.onItemSelectedListener = this

        layout_lead_contact_address.et_cnt_country.setOnClickListener { layout_lead_contact_address.sp_cnt_cntry.performClick() }
        layout_lead_contact_address.sp_cnt_cntry.onItemSelectedListener = this
        layout_lead_contact_address.et_cnt_state.setOnClickListener { layout_lead_contact_address.sp_cnt_state.performClick() }
        layout_lead_contact_address.sp_cnt_state.onItemSelectedListener = this

        layout_lead_other_details.et_ext_srv.setOnClickListener { layout_lead_other_details.sp_ex_serv.performClick() }
        layout_lead_other_details.sp_ex_serv.onItemSelectedListener = this
        layout_lead_other_details.et_ext_srv_two.setOnClickListener { layout_lead_other_details.sp_ext_serv_two.performClick() }
        layout_lead_other_details.sp_ext_serv_two.onItemSelectedListener = this
        layout_lead_other_details.et_crt_wrk.setOnClickListener { layout_lead_other_details.sp_wrk_lctn.performClick() }
        layout_lead_other_details.sp_wrk_lctn.onItemSelectedListener = this

        layout_lead_other_details.et_indus_firewl.setOnClickListener { layout_lead_other_details.sp_intrs_frwal.performClick() }
        layout_lead_other_details.sp_intrs_frwal.onItemSelectedListener = this

        layout_lead_other_details.et_vpn_srv.setOnClickListener { layout_lead_other_details.sp_vpn_serv.performClick() }
        layout_lead_other_details.sp_vpn_serv.onItemSelectedListener = this

        layout_lead_other_details.et_service_pv_one.setOnClickListener { layout_lead_other_details.sp_serv_pro_one.performClick() }
        layout_lead_other_details.sp_serv_pro_one.onItemSelectedListener = this
        layout_lead_other_details.et_srv_pv_two.setOnClickListener { layout_lead_other_details.sp_serv_pro_two.performClick() }
        layout_lead_other_details.sp_serv_pro_two.onItemSelectedListener = this

        layout_lead_other_details.et_cust_wifi.setOnClickListener { layout_lead_other_details.sp_cust_wifi.performClick() }
        layout_lead_other_details.sp_cust_wifi.onItemSelectedListener = this

        layout_lead_other_details.et_media.setOnClickListener { layout_lead_other_details.sp_media.performClick() }
        layout_lead_other_details.sp_media.onItemSelectedListener = this

        layout_lead_other_details.et_is_cus.setOnClickListener { layout_lead_other_details.sp_intrsteddata_center.performClick() }
        layout_lead_other_details.sp_intrsteddata_center.onItemSelectedListener = this

        lead_contactinfo_layout.et_upemailid.setOnFocusChangeListener { v, hasFocus ->
            if(!hasFocus)
            {
                val email = lead_contactinfo_layout.et_upemailid.text.toString()
                val isValid = Patterns.EMAIL_ADDRESS.matcher(email).matches();
                if (!isValid) {
                    Toast.makeText(context, "Invalid Email", Toast.LENGTH_LONG).show()
                }
            }
        }
        lead_contactinfo_layout.et_mobile_num.setOnFocusChangeListener { v, hasFocus ->
            if(!hasFocus)
            {
                val mobile = lead_contactinfo_layout.et_mobile_num.text.toString()
                val isValid = Patterns.PHONE.matcher(mobile).matches();
                if (!isValid) {
                    Toast.makeText(context, "Invalid Mobile Number", Toast.LENGTH_LONG).show()
                }
            }
        }


    }

    fun setAdpter(){
        val salutation = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, list_of_salutation) }
        salutation?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        lead_contactinfo_layout.sp_salutation?.adapter = salutation
        val subBusSeg = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, list_of_subBusSegment) }
        subBusSeg?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        lead_contactinfo_layout.sp_sub_bus?.adapter = subBusSeg
        val channel = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, list_of_channel) }
        channel?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        layout_channel_source.sp_leadchnl!!.adapter = channel
        val custSeg = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, list_of_cust_segment) }
        custSeg?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        lead_contactinfo_layout.sp_cust_seg!!.adapter = custSeg
        val serv = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, ext_serv) }
        serv?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        layout_lead_other_details.sp_serv_pro_two!!.adapter = serv
        val firmtype = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, list_firm_type) }
        firmtype?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        layout_lead_company_details.sp_firm_type!!.adapter = firmtype

        val dataa = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, list_of_option) }
        dataa?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        layout_lead_other_details.sp_intrsteddata_center!!.adapter = dataa

        val option = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, list_of_option) }
        option?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        layout_lead_other_details.sp_cst_voip!!.adapter = option
        val cntry = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, country_name) }
        cntry?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        layout_lead_installation_address.sp_cntry!!.adapter = cntry
        val state = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, list_of_state) }
        state?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        layout_lead_installation_address.sp_state!!.adapter = state

        val ex_serv = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, ext_serv_one) }
        ex_serv?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        layout_lead_other_details.sp_ex_serv!!.adapter = ex_serv

        val firewal = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, list_of_option) }
        firewal?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        layout_lead_other_details.sp_intrs_frwal!!.adapter = firewal

        val ccntry = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, country_name) }
        ccntry?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        layout_lead_contact_address.sp_cnt_cntry!!.adapter = ccntry
        val sstate = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, list_of_state) }
        sstate?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        layout_lead_contact_address.sp_cnt_state!!.adapter = sstate

        val serv_two = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, ext_serv_one) }
        serv_two?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        layout_lead_other_details.sp_ext_serv_two!!.adapter = serv_two

        val vpn = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, list_of_option) }
        vpn?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        layout_lead_other_details.sp_vpn_serv!!.adapter = vpn

        val serv_pv = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, ext_serv) }
        serv_pv?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        layout_lead_other_details.sp_serv_pro_one!!.adapter = serv_pv

        val serv_pv_two = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, ext_serv_one) }
        serv_pv_two?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        layout_lead_other_details.sp_ext_serv_two!!.adapter = serv_pv_two

        val wifi = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, list_of_option) }
        wifi?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        layout_lead_other_details.sp_cust_wifi!!.adapter = wifi

        val media = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, list_of_media) }
        media?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        layout_lead_other_details.sp_media!!.adapter = media
    }


    fun listener(){
        lead_save.visibility = View.VISIBLE
        linearcontactinfo.visibility = View.VISIBLE
        lineartwo.setOnClickListener { 
            linearcontactinfo.visibility = View.VISIBLE
            linear_insta_addres.visibility = View.GONE
            linadd.visibility = View.GONE
            linear_contact_person_address.visibility = View.GONE
            linear_companydetails.visibility= View.GONE
            linearother_details.visibility = View.GONE
            linearremark_details.visibility = View.GONE
        }
        linearthree.setOnClickListener { 
            linearcontactinfo.visibility = View.GONE
            linear_insta_addres.visibility = View.VISIBLE
            linadd.visibility = View.GONE
            linear_contact_person_address.visibility = View.GONE
            linear_companydetails.visibility= View.GONE
            linearother_details.visibility = View.GONE
            linearremark_details.visibility = View.GONE
        }
        linearfouraddres.setOnClickListener { 
            linearcontactinfo.visibility = View.GONE
            linear_insta_addres.visibility = View.GONE
            linadd.visibility = View.VISIBLE
            linear_contact_person_address.visibility = View.GONE
            linear_companydetails.visibility= View.GONE
            linearother_details.visibility = View.GONE
            linearremark_details.visibility = View.GONE
        }
        linearfive.setOnClickListener {
            linearcontactinfo.visibility = View.GONE

            linear_insta_addres.visibility = View.GONE
            linadd.visibility = View.GONE
            linear_contact_person_address.visibility = View.VISIBLE
            linear_companydetails.visibility= View.GONE
            linearother_details.visibility = View.GONE
            linearremark_details.visibility = View.GONE
        }
        linearsix.setOnClickListener { 
            linearcontactinfo.visibility = View.GONE
            linear_insta_addres.visibility = View.GONE
            linadd.visibility = View.GONE
            linear_contact_person_address.visibility = View.GONE
            linear_companydetails.visibility= View.VISIBLE
            linearother_details.visibility = View.GONE
            linearremark_details.visibility = View.GONE
        }

        lineareight.setOnClickListener { 
            linearcontactinfo.visibility = View.GONE
            linear_insta_addres.visibility = View.GONE
            linadd.visibility = View.GONE
            linear_contact_person_address.visibility = View.GONE
            linear_companydetails.visibility= View.GONE
            linearother_details.visibility = View.VISIBLE
            linearremark_details.visibility = View.GONE
        }
        linearnine.setOnClickListener {
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
        Intent(context, LeadTabActivity::class.java).also {
            startActivity(it)
        }
    }

    fun inProgress(){
        inAnimation = AlphaAnimation(0f, 1f)
        inAnimation?.duration =200
        leadFragmentBinding.createprogressLayout.progressOverlay.animation = inAnimation
        leadFragmentBinding.createprogressLayout.progressOverlay.visibility = View.VISIBLE
    }
    fun createLead(remark: String, companyname: String, firmtype: String, industrytype: String, jbtitle: String,
                   area: String, addres_build: String, city: String, floor: String, pincode: String,
                   building: String,  spcfc_area: String, spcfc_building: String,
                   block: String,  inst_area: String, inst_build: String,
                   inst_city_code: String?, inst_floor: String, inst_pincode: String, inst_buil: String,
                   inst_block: String, inst_sp_area: String, inst_sp_building: String,
                   other_work: String, other_pro_one: String, other_pro_two: String, other_date: String,
                   other_firewal: String, other_targetdate: String?, other_wifi: String, other_voip: String,
                   other_vpn: String, other_media: String, other_cust_one: String, cust_two: String,
                    general_lst_nm: String, general_chnl: String, general_src: String,
                   gnl_phn_num: String, gnl_sub: String, cnt_info_cnt_person: String, general_email: String,
                   topic: String, group: String, relation: String) {

            inProgress()
        var specific=""
        if(general_src=="Other"){
            specific = "Other"
        }

        val otherDetail = OtherDetail( other_work,other_pro_one,other_pro_two,other_date.toBoolean(),
                other_firewal.toBoolean(),other_wifi.toBoolean(),
                other_voip.toBoolean(),other_vpn.toBoolean(),other_media,other_cust_one,cust_two,other_targetdate)

        val companyDetail = CompanyDetail(companyname,firmtype, industrytype,jbtitle)

        val contactAddress= ContactAddress(area,addres_build,city,"10001",floor,pincode,building,
                spcfc_area,spcfc_building,str_add_state_code,block)
        val installationAddress= InstallationAddress(inst_block,inst_area,inst_build,inst_city_code,
                "10001", inst_floor,inst_pincode,inst_buil,
                "0",inst_sp_area,inst_sp_building,str_inst_state)


        //val name = general_lst_nm.substring(0,32)
        val createLeadRequest = CreateLeadRequest(Constants.CREATE_LEAD,Constants.AUTH_KEY,"",
                companyDetail,contactAddress,installationAddress,"Business",str_cmp,
                cnt_info_cnt_person,str_customer_segmentid,general_email,"3",general_lst_nm
        ,group,"",general_chnl,general_src,topic,gnl_phn_num,otherDetail,password,
        "",relation,remark,str_salutation,specific,
                gnl_sub,userName,str_chkbox)

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.createLead(createLeadRequest)
        call.enqueue(object : Callback<CreateLeadResponse?> {
            override fun onResponse(call: Call<CreateLeadResponse?>, response: Response<CreateLeadResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        outProgress()
                        val img = response.body()?.Response?.Message
                        val id = response.body()?.Response?.LeadId
                        img?.let { Log.e("image", it) }
                        Toast.makeText(context, "$img($id)", Toast.LENGTH_SHORT).show()
                        if(response.body()?.Response?.StatusCode==("200")){
                            next()
                        }else{
                            Toast.makeText(context, img, Toast.LENGTH_SHORT).show()
                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<CreateLeadResponse?>, t: Throwable) {
                leadFragmentBinding.createprogressLayout.progressOverlay.visibility=View.GONE
                Log.e("RetroError", t.toString())
            }
        })
    }

private fun outProgress(){
    outAnimation = AlphaAnimation(1f, 0f)
    outAnimation?.duration =200
    leadFragmentBinding.createprogressLayout.progressOverlay.animation = outAnimation
    leadFragmentBinding.createprogressLayout.progressOverlay.visibility = View.GONE

}

    private fun getBuilding(str_inst_area: String, code: String) {
        val getLeadBuildingRequest = GetLeadBuildingRequest(Constants.GET_BUILDING,Constants.AUTH_KEY,str_inst_area,code,password,userName)

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getleadBuilding(getLeadBuildingRequest)
        call.enqueue(object : Callback<GetLeadBuildingResponse?> {
            override fun onResponse(call: Call<GetLeadBuildingResponse?>, response: Response<GetLeadBuildingResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                      //  val msg = response.body()!!.Response.Message
                        buildingList= response.body()?.Response?.Data
                        building = ArrayList<String>()
                        buildingCode = ArrayList<String>()
                        building?.add("Select Building")
                        buildingCode?.add("")
                        for (item in buildingList!!){
                            item.BuildingName?.let { building?.add(it) }
                            item.BuildingCode?.let { buildingCode?.add(it) }
                        }
                        val adapter12 = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, building!!) }
                        adapter12?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        layout_lead_installation_address.sp_building_nm.adapter = adapter12

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

    fun getIndustryTpe() {
        val getLeadBuildingRequest = GetLeadBuildingRequest(Constants.GET_INDUSTRYTYPE,Constants.AUTH_KEY,"","",password,userName)

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
                        for (item in industryList!!){
                            instryname.add(item.IndTypeName)
                            industryid.add(item.IndTypeId)
                        }
                        val adapter12 = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, instryname) }
                        adapter12?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        layout_lead_company_details.sp_industype.adapter = adapter12

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

    fun getAddBuilding(str_add_area_code: String?, str_area: String?) {
        val getLeadBuildingRequest = GetLeadBuildingRequest(Constants.GET_BUILDING,Constants.AUTH_KEY,str_add_area_code,str_area,password,userName)

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getleadBuilding(getLeadBuildingRequest)
        call.enqueue(object : Callback<GetLeadBuildingResponse?> {
            override fun onResponse(call: Call<GetLeadBuildingResponse?>, response: Response<GetLeadBuildingResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                       // val msg = response.body()!!.Response.Message
                        buildingList= response.body()?.Response?.Data
                        building = ArrayList<String>()
                        buildingCode = ArrayList<String>()
                        building?.add("Select Building")
                        buildingCode?.add("")
                        for (item in buildingList!!) {
                            item.BuildingName?.let { building?.add(it) }
                            item.BuildingCode?.let { buildingCode?.add(it) }
                        }
                        var buildPosition=0
                        buildingCode?.forEachIndexed { index, s ->
                            if(s==strBuilding)buildPosition=index
                        }
                        val adapter12 = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, building!!) }
                        adapter12?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        layout_lead_contact_address.sp_cnt_building_nm.adapter = adapter12
                        layout_lead_contact_address.sp_cnt_building_nm.setSelection(buildPosition)
                        adapter12?.notifyDataSetChanged()
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

    fun getSource(str_lead_chnl: String?) {
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
                        //source!!.add("Select ")
                        for (item in sourceList!!)
                            source?.add(item.SourceName)
                        val adapter12 = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, source!!) }
                        adapter12?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        layout_channel_source.sp_lead_src.adapter = adapter12
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

    fun getArea(str_city: String?, str_city_code: String) {
        val getLeadAreaRequest = str_city?.let {
            GetLeadAreaRequest(Constants.Get_AREA,Constants.AUTH_KEY,str_city_code,
                it,"",userName,password,true)
        }

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getLeadArea(getLeadAreaRequest)
        call.enqueue(object : Callback<GetLeadAreaRes?> {
            override fun onResponse(call: Call<GetLeadAreaRes?>, response: Response<GetLeadAreaRes?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        val img = response.body()?.Response?.Message
                        img?.let { Log.e("image", it) }
                        areaList= response.body()?.Response?.Data
                        Installarea = ArrayList<String>()
                        InstallareaCode = ArrayList<String>()
                        Installarea?.add("Select Area")
                        InstallareaCode?.add("")
                        for (item in areaList!!) {
                            item.AreaName?.let { Installarea?.add(it) }
                            item.AreaCode?.let { InstallareaCode?.add(it) }
                        }
                        val adapter12 = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, Installarea!!) }
                        adapter12?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        layout_lead_installation_address.sp_cnarea.adapter = adapter12
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


    fun getInstArea(str_city: String?, str_city_code: String?,status:Boolean) {
        val getLeadAreaRequest = str_city?.let {
            GetLeadAreaRequest(Constants.Get_AREA,Constants.AUTH_KEY,str_city_code,
                it,"",userName,password,status)
        }

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
                        for (item in areaList!!) {
                            item.AreaName?.let { area?.add(it) }
                            item.AreaCode?.let { areaCode?.add(it) }
                        }

                        var areaPosition=0
                        areaCode?.forEachIndexed { index, s ->
                            if(s==strArea)areaPosition=index
                            println("Area code ${strArea}")
                        }
                        val adapter12 = context?.let {
                            ArrayAdapter(
                                it, android.R.layout.simple_spinner_item, area!!
                            )
                        }
                        adapter12?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        layout_lead_contact_address.sp_cnt_cnarea.adapter = adapter12
                        layout_lead_contact_address.sp_cnt_cnarea.setSelection(areaPosition)
                        adapter12?.notifyDataSetChanged()
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
        val getCityRequest = GetCityRequest(Constants.GET_CITY,Constants.AUTH_KEY,password,statecode,userName)

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getCityList(getCityRequest)
        call.enqueue(object : Callback<GetCityResponse?> {
            override fun onResponse(call: Call<GetCityResponse?>, response: Response<GetCityResponse?>) {
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
                        InstallcityCode?.forEachIndexed { index, s ->
                            if (s == str_city_code) cityPosition = index
                            return@forEachIndexed
                        }
                        val adapter121 = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, Installcity!!) }
                        adapter121?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        layout_lead_installation_address.sp_city.adapter = adapter121
                        layout_lead_installation_address.sp_city.setSelection(cityPosition)
                        adapter121?.notifyDataSetChanged()

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


    fun getOtherCity() {
        val getCityRequest = GetCityRequest(Constants.GET_CITY,Constants.AUTH_KEY,password,"",userName)

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getCityList(getCityRequest)
        call.enqueue(object : Callback<GetCityResponse?> {
            override fun onResponse(call: Call<GetCityResponse?>, response: Response<GetCityResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        val img = response.body()?.Response?.Message
                        img?.let { Log.e("image", it) }
                        cityList = response.body()!!.Response.Data
                        othercity = ArrayList<String>()
                        othercityCode = ArrayList<String>()
                        othercity?.add("Select City")
                        for (item in cityList!!) {
                            othercity?.add(item.CityName)
                            othercityCode?.add(item.CityCode)
                        }
                        val adapter12 = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, othercity!!) }
                        adapter12?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        layout_lead_other_details.sp_wrk_lctn.adapter = adapter12

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
    fun getCompany() {
        inProgress()
        val getCompanyRequest = GetCompanyRequest(Constants.GET_COMPANY,Constants.AUTH_KEY,"",password,userName)
        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getComapnyList(getCompanyRequest)
        call.enqueue(object : Callback<GetCompanyResponse?> {
            override fun onResponse(call: Call<GetCompanyResponse?>, response: Response<GetCompanyResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        outProgress()
                        val img = response.body()?.Response?.Message
                        img?.let { Log.e("image", it) }
                        companyList= response.body()?.Response?.Data
                        company = ArrayList<String>()
                        companyId = ArrayList<String>()
                        company?.add("Select Company")
                        group = ArrayList<String>()
                        groupId = ArrayList<String>()
                        group?.add("Select Group")
                        for (item in companyList!!){
                           // company?.add(item.Company_Name)
                            company?.add(item.Company_Name +" ("+item.Company_ID+")")
                            companyId?.add(item.Company_ID)
                            group?.add(item.Group_Name +" ("+item.Group_ID+")")
                            groupId?.add(item.Group_ID)
                        }

                        val adapter12 = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, company!!) }
                        lead_contactinfo_layout.et_leadcompany.threshold=0
                        adapter12?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        lead_contactinfo_layout.et_leadcompany.setAdapter(adapter12)

                        lead_contactinfo_layout.et_leadcompany.setOnFocusChangeListener { _, b ->
                            if (b) lead_contactinfo_layout.et_leadcompany.showDropDown()
                        }
                        lead_contactinfo_layout.et_leadcompany.onItemClickListener = AdapterView.OnItemClickListener {
                                parent, _, position, _ ->
                            strCompanyName = adapter12?.getItem(position)
                            val split = strCompanyName?.split("(")
                            val compid = split?.get(1)
                            var compPosition=0
                            company?.forEachIndexed { index, s ->
                                if(s==strCompanyName)compPosition=index
                                strCompanyName?.let { Log.e("idddddddddd", it) }
                                return@forEachIndexed
                            }
                            val companyidd = compid?.split(")")
                            str_cmp = companyidd?.get(0)
                            str_cmp?.let { Log.e("compid", it) }
                            getRelation(str_cmp)
                            if(strCompanyName!=null||strCompanyName=="Select Company") {
                                if (strCompanyName.contentEquals("New Company (CNew)")||strCompanyName=="Select Company"){
                                    lead_contactinfo_layout.et_lead_nm.setText("")
                                    layout_lead_company_details.et_company_name.setText("")
                                    if(strCompanyName.contentEquals("New Company (CNew)")) {
                                        Toast.makeText(context, "Please search company name from drop-down before selecting New Company", Toast.LENGTH_SHORT).show()
                                    }
                                }else {
                                    val name = strCompanyName?.split("(")
                                    val cmpname = name?.get(0)
                                    lead_contactinfo_layout.et_lead_nm.setText(cmpname)
                                    layout_lead_company_details.et_company_name.setText(cmpname)
                                }
                                val adapter1 = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, group!!) }
                                adapter1?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                                lead_contactinfo_layout.sp_group.adapter = adapter1
                                lead_contactinfo_layout.sp_group.setSelection(compPosition)
                                adapter1?.notifyDataSetChanged()
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<GetCompanyResponse?>, t: Throwable) {
                leadFragmentBinding.createprogressLayout.progressOverlay.visibility=View.GONE
                Log.e("RetroError", t.toString())
            }
        })
    }

    fun getRelation(str_cmny: String?) {
        val getCompanyRequest = str_cmny?.let { GetCompanyRequest(Constants.GET_RELATIONSHIP,Constants.AUTH_KEY, it,password,userName) }
        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getRelationList(getCompanyRequest)
        call.enqueue(object : Callback<GetRelationShipResponse?> {
            override fun onResponse(call: Call<GetRelationShipResponse?>, response: Response<GetRelationShipResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        val img = response.body()?.Response?.Message
                        img?.let { Log.e("image", it) }
                        relationList= response.body()?.Response?.Data
                        relation = ArrayList<String>()
                        relationId = ArrayList<String>()
                        state = ArrayList<String>()
                        stateId = ArrayList<String>()
                        Installcity = ArrayList<String>()
                        InstallcityCode = ArrayList<String>()
                        Installcity?.add("Select City")
                        InstallcityCode?.add("")
                        relation?.add("Select Relation")
                        state?.add("Select State")
                        stateId?.add("")

                        for (item in relationList!!) {
                            relation?.add(item.Relationship_Name +" ("+item.Relationship_ID+")")
                            item.Relationship_ID?.let { relationId?.add(it) }
                            item.State_Name?.let { state?.add(it) }
                            item.State_Id?.let { stateId?.add(it) }
                            item.City_Name?.let { Installcity?.add(it) }
                            item.City_ID?.let { InstallcityCode?.add(it) }
                        }

                        val adapter12 = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, relation!!) }
                        adapter12?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        lead_contactinfo_layout.sp_relation.adapter = adapter12

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

    @SuppressLint("SetTextI18n")
    fun  Calender(){
        try{
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        layout_lead_other_details.et_trgt_period.setOnClickListener {
            val dpd = DatePickerDialog(requireContext(), { view, year, monthOfYear, dayOfMonth ->
                val mn = monthOfYear+1
                layout_lead_other_details.et_trgt_period.setText("$dayOfMonth-$mn-$year")
                val trgt =  layout_lead_other_details.et_trgt_period.text.toString()
                val split = trgt.split("-")
                val dateee = split[0]
                val month1 = split[1]
                val year1 = split[2]
                date=(year1 + "-" + month1 + "-" + dateee)
            }, year, month, day)
            dpd.show()
        }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        lead_contactinfo_layout.et_relation.setOnFocusChangeListener { v, hasFocus ->
            if(!hasFocus)
            {
            val relation =  lead_contactinfo_layout.et_relation.text.toString()
                str_city_code = ""
                getCity(str_inst_state)
                if (relation == "New Relationship ") {
                    layout_lead_installation_address.et_state.setText("")
                    layout_lead_installation_address.et_add_city.setText("")
                }
            }
        }
    }




    fun getAddCity(state_code: String) {
        val getCityRequest = GetCityRequest(Constants.GET_CITY,Constants.AUTH_KEY,password,state_code,userName)

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getCityList(getCityRequest)
        call.enqueue(object : Callback<GetCityResponse?> {
            override fun onResponse(call: Call<GetCityResponse?>, response: Response<GetCityResponse?>) {
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
                            if(s==strCity)cityPosition=index
                        }
                        val adapter12 = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, city!!) }
                        adapter12?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        layout_lead_contact_address.sp_cnt_city.adapter = adapter12
                        layout_lead_contact_address.sp_cnt_city.setSelection(cityPosition)
                        adapter12?.notifyDataSetChanged()
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
            lead_contactinfo_layout.et_sub_businessseg.setText(list_of_subBusSegment[position])
            str_sub_bus = list_of_subBusSegment.get(position)
        }else if(parent?.id == R.id.sp_salutation){
            lead_contactinfo_layout.et_saluation.setText(list_of_salutation[position])
            if (position != 0) str_salutation = "" + list_of_salutation_id[position - 1] else str_salutation = " "
        }else if(parent?.id == R.id.sp_leadchnl){
            layout_channel_source.et_lead_channel.setText(list_of_channel[position])
            str_lead_chnl = list_of_channel[position]
            getSource(str_lead_chnl)
        }else if(parent?.id ==R.id.sp_lead_src){
            layout_channel_source.et_lead_source.setText(source?.get(position))
            str_lead_src = source?.get(position)
        }else if(parent?.id == R.id.sp_cnt_cnarea){
            layout_lead_contact_address.et_cntarea.setText(area?.get(position))
            str_area = area?.get(position).toString()
            str_add_area_code =  areaCode?.get(position )
            getAddBuilding(str_add_area_code,str_area.toString())
            if(str_area=="Other"){
                layout_lead_contact_address.et_cntsecifc_area.visibility=View.VISIBLE
            }else{
                layout_lead_contact_address.et_cntsecifc_area.visibility=View.GONE
            }
        }else if(parent?.id == R.id.sp_cnarea){
            layout_lead_installation_address.et_installarea.setText(Installarea?.get(position))
            str_installbuild = Installarea?.get(position).toString()
            str_inst_area = InstallareaCode?.get(position )
            str_inst_area?.let { getBuilding(it, str_installbuild!!) }
            if(str_installbuild=="Other"){
                layout_lead_installation_address.et_spec_area.visibility=View.VISIBLE
            }else{
                layout_lead_installation_address.et_spec_area.visibility=View.GONE
            }
        }
        else if(parent?.id == R.id.sp_building_nm) {
            layout_lead_installation_address.et_building.setText(building?.get(position))
            str_inst_building_name = building?.get(position).toString()
            str_inst_build_num =  buildingCode?.get(position)
            if(str_inst_building_name=="Other"){
                layout_lead_installation_address.et_specific_build.visibility=View.VISIBLE
            }else{
                layout_lead_installation_address.et_specific_build.visibility=View.GONE
            }
        } else if(parent?.id == R.id.sp_cnt_building_nm) {
            layout_lead_contact_address.et_cnt_building.setText(building?.get(position))
            str_add_building =  buildingCode?.get(position )
            str_specificbuild= building?.get(position).toString()
            if(str_specificbuild=="Other"){
                layout_lead_contact_address.et_cntbuilding.visibility=View.VISIBLE
            }else{
                layout_lead_contact_address.et_cntbuilding.visibility=View.GONE
            }
        }
        else if(parent?.id == R.id.sp_city){
            layout_lead_installation_address.et_add_city.setText(Installcity?.get(position))
            str_city = Installcity?.get(position).toString()
            str_city_code = InstallcityCode?.get(position )
            getArea(str_city, str_city_code.toString())
        }else if(parent?.id == R.id.sp_cnt_city){
            layout_lead_contact_address.et_cnt_city.setText(city?.get(position))
            str_city = city?.get(position).toString()
            str_add_city =  cityCode?.get(position)
            getInstArea(str_city, str_add_city,false)
        }else if(parent?.id == R.id.sp_cust_seg){
            lead_contactinfo_layout.et_customer_seg.setText(list_of_cust_segment[position])
            if (position != 0) str_customer_segmentid = "" + list_cust_seg_value[position - 1] else str_customer_segmentid = " "
        }else if(parent?.id == R.id.sp_cust_wifi){
            layout_lead_other_details.et_cust_wifi.setText(list_of_option[position])
            if (position != 0) str_wifi = "" + list_of_boolean[position - 1] else str_wifi = " "
        }else if(parent?.id == R.id.sp_ex_serv){
            layout_lead_other_details.et_ext_srv.setText(ext_serv_one[position])
            if (position != 0) str_ext_serv_pro_one = "" + ext_serv_one_value[position - 1] else str_ext_serv_pro_one = " "
        }
        else if(parent?.id == R.id.sp_ext_serv_two){
            layout_lead_other_details.et_ext_srv_two.setText(ext_serv_one[position])
            if (position != 0) str_serv_pro_two = "" + ext_serv_one_value[position - 1] else str_serv_pro_two = " "
        }else if(parent?.id == R.id.sp_serv_pro_one){
            layout_lead_other_details.et_service_pv_one.setText(ext_serv[position])
            if (position != 0) str_cust_serv_one = "" + ext_serv_val[position - 1] else str_cust_serv_one= " "
        }else if(parent?.id == R.id.sp_serv_pro_two){
            layout_lead_other_details.et_srv_pv_two.setText(ext_serv[position])
            if (position != 0) str_cust_serv_two = "" + ext_serv_val[position - 1] else str_cust_serv_two = " "
        }else if(parent?.id == R.id.sp_state){
            layout_lead_installation_address.et_state.setText(list_of_state[position])
            str_state = list_of_state[position]
            str_inst_state = list_state_code[position]
            getCity(str_inst_state)
        }else if(parent?.id == R.id.sp_cnt_state){
            layout_lead_contact_address.et_cnt_state.setText(list_of_state[position])
             str_add_state = list_of_state[position]
             str_add_state_code = list_state_code[position]
            getAddCity(str_add_state_code.toString())
        }else if(parent?.id == R.id.sp_firm_type){
            layout_lead_company_details.et_firm_type.setText(list_firm_type[position])
            if (position != 0) str_firm_type = "" + list_firm_type_value[position - 1] else str_firm_type = " "
        }else if(parent?.id == R.id.sp_cst_voip){
            layout_lead_other_details.et_cust_void.setText(list_of_option[position])
            if (position != 0) str_voip = "" + list_of_boolean[position - 1] else str_voip = " "
        }else if(parent?.id == R.id.sp_industype){
            layout_lead_company_details.et_indus_type.setText(instryname[position])
            if (position != 0) str_industry_type = "" + industryid[position - 1] else str_industry_type= " "
        }else if(parent?.id == R.id.sp_media){
            layout_lead_other_details.et_media.setText(list_of_media[position])
           str_media = list_of_mediavalue[position]
        }else if(parent?.id == R.id.sp_intrsteddata_center){
            layout_lead_other_details.et_is_cus.setText(list_of_option[position])
            if (position != 0) str_data = "" + list_of_boolean[position - 1] else str_data= " "
        }else if(parent?.id == R.id.sp_intrs_frwal){
            layout_lead_other_details.et_indus_firewl.setText(list_of_option[position])
            if (position != 0) str_cust_frwl = "" + list_of_boolean[position - 1] else str_cust_frwl= " "
        }else if(parent?.id == R.id.sp_vpn_serv){
            layout_lead_other_details.et_vpn_srv.setText(list_of_option[position])
            if (position != 0) str_cust_vpn = "" + list_of_boolean[position - 1] else str_cust_vpn= " "
        }else if(parent?.id == R.id.sp_group){
            lead_contactinfo_layout.et_group.setText(group?.get(position))
            if (position != 0) str_grp = "" + groupId?.get(position - 1) else str_grp= " "
        }
        else if(parent?.id == R.id.sp_relation){
            lead_contactinfo_layout.et_relation.setText(relation?.get(position))
            str_rltnname = relation?.get(position)
            if (position != 0) str_rltn = "" + relationId?.get(position - 1) else str_rltn= " "
            val split = str_rltnname?.split('(')
            val rel = split?.get(0)
            if(rel!="Select Relation") {
                if (rel != "New Relationship ") {
                    layout_lead_installation_address.et_state.setText(state?.get(position))
                    str_state = state?.get(position)
                    str_inst_state = stateId?.get(position)
                    var cntstatePosition = 0
                    list_of_state.forEachIndexed { index, s ->
                        if (s == str_state) cntstatePosition = index
                    }
                    val cntstateAdapter = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, list_of_state) }
                    cntstateAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                    layout_lead_installation_address.sp_state.adapter = cntstateAdapter
                    layout_lead_installation_address.sp_state.setSelection(cntstatePosition)
                    cntstateAdapter?.notifyDataSetChanged()
                    layout_lead_installation_address.et_state.isEnabled = false
                   // layout_lead_installation_address.et_add_city.setText(Installcity?.get(position))
                    str_city_code = InstallcityCode?.get(position).toString()
                  //  str_city = Installcity?.get(position)
                  //  str_city_code = InstallcityCode?.get(position )
                    getCity(str_inst_state)
                    layout_lead_installation_address.et_add_city.isEnabled = false
                    getArea(str_city, str_city_code.toString())
                }else{
                    layout_lead_installation_address.et_state.setText("")
                    layout_lead_installation_address.et_add_city.setText("")
                    Installcity?.clear()
                    InstallcityCode?.clear()
                    val cntstateAdapter = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, list_of_state) }
                    cntstateAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                    layout_lead_installation_address.sp_state.adapter = cntstateAdapter
                 //   layout_lead_installation_address.sp_state.setSelection(cntstatePosition)
                    cntstateAdapter?.notifyDataSetChanged()
                    str_city_code = ""
                    getCity(str_inst_state)
                    layout_lead_installation_address.et_state.isEnabled = true
                    layout_lead_installation_address.et_add_city.isEnabled = true
                }

            }

        } else if(parent?.id == R.id.sp_wrk_lctn){
            layout_lead_other_details.et_crt_wrk.setText(othercity?.get(position))
            if (position != 0) str_othercity = "" + othercityCode?.get(position - 1) else str_othercity= " "
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }


}