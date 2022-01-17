package com.spectra.fieldforce.salesapp.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.spectra.fieldforce.R
import com.spectra.fieldforce.api.ApiClient
import com.spectra.fieldforce.api.ApiInterface
import com.spectra.fieldforce.databinding.LeadDemoFragmentBinding
import com.spectra.fieldforce.salesapp.model.*
import com.spectra.fieldforce.utils.AppConstants
import com.spectra.fieldforce.utils.Constants
import kotlinx.android.synthetic.main.lead__contact_person_row.view.*
import kotlinx.android.synthetic.main.lead_channel_source_row.view.*
import kotlinx.android.synthetic.main.lead_company_details_row.view.*
import kotlinx.android.synthetic.main.lead_contact_info.view.*
import kotlinx.android.synthetic.main.lead_contact_info.view.et_sub_businessseg
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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import kotlinx.android.synthetic.main.lead__contact_person_row.view.et_cnt_building
import kotlinx.android.synthetic.main.lead__contact_person_row.view.et_cnt_buildng_num
import kotlinx.android.synthetic.main.lead__contact_person_row.view.et_cnt_city
import kotlinx.android.synthetic.main.lead__contact_person_row.view.et_cnt_country
import kotlinx.android.synthetic.main.lead__contact_person_row.view.et_cnt_floor
import kotlinx.android.synthetic.main.lead__contact_person_row.view.et_cnt_pin_code
import kotlinx.android.synthetic.main.lead__contact_person_row.view.et_cnt_state
import kotlinx.android.synthetic.main.lead__contact_person_row.view.et_cntarea
import kotlinx.android.synthetic.main.lead__contact_person_row.view.sp_cnt_building_nm
import kotlinx.android.synthetic.main.lead__contact_person_row.view.sp_cnt_city
import kotlinx.android.synthetic.main.lead__contact_person_row.view.sp_cnt_cnarea
import kotlinx.android.synthetic.main.lead__contact_person_row.view.sp_cnt_cntry
import kotlinx.android.synthetic.main.lead__contact_person_row.view.sp_cnt_state
import kotlinx.android.synthetic.main.lead_installation_address_row.view.et_add_build_num
import kotlinx.android.synthetic.main.lead_installation_address_row.view.et_add_city
import kotlinx.android.synthetic.main.lead_installation_address_row.view.et_add_floor
import kotlinx.android.synthetic.main.lead_installation_address_row.view.et_building
import kotlinx.android.synthetic.main.lead_installation_address_row.view.et_country
import kotlinx.android.synthetic.main.lead_installation_address_row.view.et_pin_code
import kotlinx.android.synthetic.main.lead_installation_address_row.view.et_state
import kotlinx.android.synthetic.main.lead_installation_address_row.view.sp_building_nm
import kotlinx.android.synthetic.main.lead_installation_address_row.view.sp_city
import kotlinx.android.synthetic.main.lead_installation_address_row.view.sp_cnarea
import kotlinx.android.synthetic.main.lead_installation_address_row.view.sp_cntry
import kotlinx.android.synthetic.main.lead_installation_address_row.view.sp_state
import kotlinx.android.synthetic.main.lead_other_details_row.view.et_crt_wrk
import kotlinx.android.synthetic.main.lead_other_details_row.view.et_cust_void
import kotlinx.android.synthetic.main.lead_other_details_row.view.et_cust_wifi
import kotlinx.android.synthetic.main.lead_other_details_row.view.et_ext_srv
import kotlinx.android.synthetic.main.lead_other_details_row.view.et_ext_srv_two
import kotlinx.android.synthetic.main.lead_other_details_row.view.et_indus_firewl
import kotlinx.android.synthetic.main.lead_other_details_row.view.et_is_cus
import kotlinx.android.synthetic.main.lead_other_details_row.view.et_media
import kotlinx.android.synthetic.main.lead_other_details_row.view.et_service_pv_one
import kotlinx.android.synthetic.main.lead_other_details_row.view.et_srv_pv_two
import kotlinx.android.synthetic.main.lead_other_details_row.view.et_trgt_period
import kotlinx.android.synthetic.main.lead_other_details_row.view.et_vpn_srv
import kotlinx.android.synthetic.main.lead_other_details_row.view.sp_cst_voip
import kotlinx.android.synthetic.main.lead_other_details_row.view.sp_cust_wifi
import kotlinx.android.synthetic.main.lead_other_details_row.view.sp_ex_serv
import kotlinx.android.synthetic.main.lead_other_details_row.view.sp_ext_serv_two
import kotlinx.android.synthetic.main.lead_other_details_row.view.sp_intrs_frwal
import kotlinx.android.synthetic.main.lead_other_details_row.view.sp_intrsteddata_center
import kotlinx.android.synthetic.main.lead_other_details_row.view.sp_media
import kotlinx.android.synthetic.main.lead_other_details_row.view.sp_serv_pro_one
import kotlinx.android.synthetic.main.lead_other_details_row.view.sp_serv_pro_two
import kotlinx.android.synthetic.main.lead_other_details_row.view.sp_vpn_serv


class CreateLeadFragment:Fragment(), View.OnClickListener,AdapterView.OnItemSelectedListener {

    lateinit var  leadFragmentBinding: LeadDemoFragmentBinding

    private var source : ArrayList<String>? = null
    private var sourceList: MutableList<SrcData>? = null
    private var areaList : ArrayList<AreaData>? = null
    private var area : ArrayList<String>? = null
    private var areaCode : ArrayList<String>? = null
    private var cityList : ArrayList<CityData>? = null
    private var city : ArrayList<String>? = null
    private var cityCode : ArrayList<String>? = null
    private var buildingList : ArrayList<BuildData>? = null
    private var building : ArrayList<String>? = null
    private var buildingCode : ArrayList<String>? = null
    private var company : ArrayList<String>? = null
    private var companyId : ArrayList<String>? = null
    private var companyList: ArrayList<ComapnyData>? = null
    private var group : ArrayList<String>? = null
    private var groupId : ArrayList<String>? = null
    private var relation : ArrayList<String>? = null
    private var relationId : ArrayList<String>? = null
    private var relationList: ArrayList<RelationshipData>? = null
    private var inAnimation: AlphaAnimation? = null
    private var outAnimation: AlphaAnimation? = null
    private var industryList : ArrayList<IndustryResponse>? = null
    private var instryname = ArrayList<String>()
    private var industryid = ArrayList<String>()
    private var othercity : ArrayList<String>? = null
    private var othercityCode : ArrayList<String>? = null
    var str_othercity: String? = null
    var str_area: String? = null
    var str_city: String? = null
    var str_city_code : String? = null
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
    var list_of_state = arrayOf("State Name", "Andhra Pradesh","Bihar","Delhi"
    ,"Gujarat","Haryana","Jammu and Kashmir","Karnataka"
    ,"Kerala", "Madhya Pradesh","Maharashtra","Odisha", "Other*",
    "Punjab","Rajasthan","Tamil Nadu", "Telangana","Uttar Pradesh"
    ,"Uttarakhand","West Bengal")

    var list_of_boolean = arrayOf("True","False")

    var list_state_code = arrayOf("0","100009","100021","100004", "100015","100008",
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
       // val activity = activity as Context
        return leadFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchtoolbarlead.rl_back.setOnClickListener(this)
        searchtoolbarlead.tv_lang.text= AppConstants.LEAD_DETAILS

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
            val inst_state_code = str_inst_state.toString()

            val inst_city_code = str_city_code
          //  val inst_city = str_city.toString()
            val inst_area = str_inst_area.toString()

          //  val city = layout_lead_contact_address.et_cnt_city.setText(inst_city)
            val citycode = inst_city_code
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


         //   getCpAdArea(str_city,str_city_code.toString(),true)

            /*  strCity = str_city_code.toString()
              strArea = str_inst_area.toString()
              val area =  str_inst_area.toString()
              str_add_area_code = str_inst_area!!
              getAddCity(inst_state_code)
              getCpAdArea(str_city,str_city_code.toString(),true)
              strBuilding = str_inst_building_name.toString()
              getAddBuilding(str_inst_area!!,str_installbuild!!)
             */
            val inst_floor = layout_lead_installation_address.et_add_floor.text.toString()
            val inst_pincode = layout_lead_installation_address.et_pin_code.text.toString()
            val inst_buil = layout_lead_installation_address.et_add_build_num.text.toString()
            val inst_block = layout_lead_installation_address.et_block.text.toString()
            layout_lead_contact_address.et_cnt_floor.setText(inst_floor)
            layout_lead_contact_address.et_cnt_pin_code.setText(inst_pincode)
            layout_lead_contact_address.et_cnt_buildng_num.setText(inst_buil)
            layout_lead_contact_address.et_cnt_block.setText(inst_block)
          //  layout_lead_contact_address.et_building.setText(strBuilding)
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
            val buildingname = str_add_building.toString()
            val inst_area = str_inst_area.toString()
            val inst_build =str_inst_build_num.toString()
            val inst_build_name = str_inst_building_name.toString()
            val inst_city_code = str_city_code
            val inst_city = str_city.toString()
            val inst_country = str_inst_country.toString()
            val inst_floor = layout_lead_installation_address.et_add_floor.text.toString()
            val inst_pincode = layout_lead_installation_address.et_pin_code.text.toString()
            val inst_buil = layout_lead_installation_address.et_add_build_num.text.toString()
            val inst_block = layout_lead_installation_address.et_block.text.toString()

            val inst_state = str_inst_state.toString()
            val inst_state_code = str_state.toString()
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
            if(topic.isBlank()){
                Toast.makeText(context, "Please Enter Topic", Toast.LENGTH_SHORT).show()
            }else if(gnl_sub.isBlank()||gnl_sub=="Select Sub Business Segment"){
                Toast.makeText(context, "Please Select Sub Business Segment", Toast.LENGTH_SHORT).show()
            }else if(customer_seg.isBlank()||customer_seg=="Select Customer Segment"){
                Toast.makeText(context, "Please Select Customer Segment", Toast.LENGTH_SHORT).show()
            }else if(salutation.isBlank()||gnl_sub=="Select Salutation"){
                Toast.makeText(context, "Please Select Salutation", Toast.LENGTH_SHORT).show()
            }else if(cnt_info_cnt_person.isBlank()){
                Toast.makeText(context, "Please Enter Contact Person", Toast.LENGTH_SHORT).show()
            }else if(general_email.isBlank()){
                Toast.makeText(context, "Please Enter Email Id", Toast.LENGTH_SHORT).show()
            }else if(gnl_phn_num.isBlank()){
                Toast.makeText(context, "Please Enter Mobile Number", Toast.LENGTH_SHORT).show()
            }else if(genral_name.isBlank()){
                Toast.makeText(context, "Please Enter Lead Name", Toast.LENGTH_SHORT).show()
            }else if(company.isBlank()||company=="Select Company"){
                Toast.makeText(context, "Please Select Company", Toast.LENGTH_SHORT).show()
            }else if(group.isBlank()||group=="Select Group"){
                Toast.makeText(context, "Please enter Group", Toast.LENGTH_SHORT).show()
            }else if(relation.isBlank()||relation=="Select Relation"){
                Toast.makeText(context, "Please Select Relation", Toast.LENGTH_SHORT).show()
            }else if(general_chnl.isBlank()||general_chnl=="Select Channel"){
                Toast.makeText(context, "Please Select Channel", Toast.LENGTH_SHORT).show()
            }else if(general_src.isBlank()){
                Toast.makeText(context, "Please Select Source", Toast.LENGTH_SHORT).show()
            }else if(inst_state.isBlank()||inst_state=="Select State"){
                Toast.makeText(context, "Please Select State", Toast.LENGTH_SHORT).show()
            }else if(inst_city.isBlank()||inst_city=="Select City"){
                Toast.makeText(context, "Please Select City", Toast.LENGTH_SHORT).show()
            }else if(inst_area.isBlank()||inst_area=="Select Area"){
                Toast.makeText(context, "Please Select Area", Toast.LENGTH_SHORT).show()
            }else if(inst_build_name.isBlank()||inst_build=="Select Building"){
                Toast.makeText(context, "Please Select Building Name", Toast.LENGTH_SHORT).show()
            }else if(inst_build.isBlank()){
                Toast.makeText(context, "Please enter Building No. ", Toast.LENGTH_SHORT).show()
            }else if(inst_block.isBlank()){
                Toast.makeText(context, "Please enter Block", Toast.LENGTH_SHORT).show()
            }else if(inst_floor.isBlank()){
                Toast.makeText(context, "Please enter Floor", Toast.LENGTH_SHORT).show()
            }else if(inst_pincode.isBlank()){
                Toast.makeText(context, "Please enter Pincode", Toast.LENGTH_SHORT).show()
            }else if(state.isBlank()||state=="Select State"){
                Toast.makeText(context, "Please Select State", Toast.LENGTH_SHORT).show()
            }else if(city.isBlank()||city=="Select City"){
                Toast.makeText(context, "Please Select City", Toast.LENGTH_SHORT).show()
            }else if(area.isBlank()||area=="Select Area"){
                Toast.makeText(context, "Please Select Area", Toast.LENGTH_SHORT).show()
            }else if(buildingname.isBlank()||buildingname=="Select Building Name"){
                Toast.makeText(context, "Please enter Building Name ", Toast.LENGTH_SHORT).show()
            }else if(block.isBlank()){
                Toast.makeText(context, "Please enter Block", Toast.LENGTH_SHORT).show()
            }else if(floor.isBlank()){
                Toast.makeText(context, "Please enter Floor", Toast.LENGTH_SHORT).show()
            }else if(pincode.isBlank()){
                Toast.makeText(context, "Please enter Pincode ", Toast.LENGTH_SHORT).show()
            }else if(firmtype.isBlank()||firmtype=="Select Firm Type"){
                Toast.makeText(context, "Please Select Firm Type ", Toast.LENGTH_SHORT).show()
            }else if(industrytype.isBlank()||industrytype=="Select Industry"){
                Toast.makeText(context, "Please Select Industry Type", Toast.LENGTH_SHORT).show()
            }else if(jbtitle.isBlank()){
                Toast.makeText(context, "Please enter Job Title", Toast.LENGTH_SHORT).show()
            }else if(other_media.isBlank()||other_media=="Select Media"){
                Toast.makeText(context, "Please Select Media ", Toast.LENGTH_SHORT).show()
            }else if(other_pro_one.isBlank()||other_pro_one=="Select Existing Service Provider"){
                Toast.makeText(context, "Please Select Existing Service Provider One", Toast.LENGTH_SHORT).show()
            }else if(other_pro_two.isBlank()||other_pro_two=="Select Existing Service Provider"){
                Toast.makeText(context, "Please Select Existing Service Provider Two", Toast.LENGTH_SHORT).show()
            } else if(other_work.isBlank()){
                Toast.makeText(context, "Please Select Current Work location ", Toast.LENGTH_SHORT).show()
            }else if(other_date.isBlank()){
                Toast.makeText(context, "Please Select Data Center ", Toast.LENGTH_SHORT).show()
            }else if(other_firewal.isBlank()||other_firewal=="Select Option"){
                Toast.makeText(context, "Please Select Firewall ", Toast.LENGTH_SHORT).show()
            } else if(other_vpn.isBlank()){
                Toast.makeText(context, "Please Select Vpn Services ", Toast.LENGTH_SHORT).show()
            }else if(other_cust_one.isBlank()||other_cust_one=="Select Existing Service Provider"){
                Toast.makeText(context, "Please Select Existing Service Provider One", Toast.LENGTH_SHORT).show()
            }else if(cust_two.isBlank()||cust_two=="Select Existing Service Provider"){
                Toast.makeText(context, "Please Select Existing Service Provider Two", Toast.LENGTH_SHORT).show()
            }
            else if(other_voip.isBlank()||other_voip=="Select Option"){
                Toast.makeText(context, "Please Select Voip ", Toast.LENGTH_SHORT).show()
            }else if(other_wifi.isBlank()||other_wifi=="Select Option"){
                Toast.makeText(context, "Please Select Manage Wifi ", Toast.LENGTH_SHORT).show()
            }else if(other_targetdate?.isBlank()==true){
                Toast.makeText(context, "Please Select Target Intallation Period ", Toast.LENGTH_SHORT).show()
            }
            else {
                createLead(remark,companyname,firmtype,industrytype,jbtitle,area,addres_build,city,floor,pincode,
                        building,state,spcfc_area,spcfc_building,block,buildingname,inst_area,inst_build,inst_build_name,
                        inst_city_code,inst_city,inst_country,inst_floor,inst_pincode,inst_buil,inst_state,inst_state_code,
                        inst_block,inst_sp_area,inst_sp_building,other_work,other_pro_one,other_pro_two,other_date,
                        other_firewal,other_targetdate,other_wifi,other_voip,other_vpn,other_media,other_cust_one,
                        cust_two,genral_name,general_lst_nm,general_chnl,general_src,gnl_phn_num,gnl_sub,cnt_info_cnt_person,
                        general_email,topic,group,relation
                )
            }

        }

    }

    fun itemListener(){
        val email = lead_contactinfo_layout.et_upemailid.text.toString()

        lead_contactinfo_layout.et_upemailid.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?,
                                           p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?,
                                       p1: Int, p2: Int, p3: Int) {
                if(Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                }
                else{
                    Toast.makeText(context, "Invalid email address", Toast.LENGTH_SHORT).show()
                }
            }

            override fun afterTextChanged(p0: Editable?) { }
        })
        lead_contactinfo_layout.et_business_seg.setText(AppConstants.BUSINESS)
        lead_contactinfo_layout.et_sub_businessseg.setOnClickListener { lead_contactinfo_layout.sp_sub_bus.performClick() }
        lead_contactinfo_layout.sp_sub_bus.onItemSelectedListener = this
        lead_contactinfo_layout.et_saluation.setOnClickListener { lead_contactinfo_layout.sp_salutation.performClick() }
        lead_contactinfo_layout.sp_salutation.onItemSelectedListener = this
        layout_channel_source.setOnClickListener { layout_channel_source.sp_leadchnl.performClick() }
        lead_contactinfo_layout.sp_salutation.onItemSelectedListener = this
        layout_channel_source.et_lead_channel.setOnClickListener { layout_channel_source.sp_leadchnl.performClick() }
        layout_channel_source.sp_leadchnl.onItemSelectedListener = this
        layout_channel_source.et_lead_source.setOnClickListener { layout_channel_source.sp_lead_src.performClick() }
        layout_channel_source.sp_lead_src.onItemSelectedListener = this
        lead_contactinfo_layout.et_customer_seg.setOnClickListener { lead_contactinfo_layout.sp_cust_seg.performClick() }
        lead_contactinfo_layout.sp_cust_seg.onItemSelectedListener = this
        lead_contactinfo_layout.et_leadcompany.setOnClickListener { lead_contactinfo_layout.sp_leadcmpny.performClick() }
        lead_contactinfo_layout.sp_leadcmpny.onItemSelectedListener = this
        lead_contactinfo_layout.et_group.setOnClickListener { lead_contactinfo_layout.sp_group.performClick() }
        lead_contactinfo_layout.sp_group.onItemSelectedListener = this
        lead_contactinfo_layout.et_relation.setOnClickListener { lead_contactinfo_layout.sp_relation.performClick() }
        lead_contactinfo_layout.sp_relation.onItemSelectedListener = this
        layout_lead_company_details.et_firm_type.setOnClickListener { layout_lead_company_details.sp_firm_type.performClick() }
        layout_lead_company_details.sp_firm_type.onItemSelectedListener = this
        layout_lead_company_details.et_lco.setOnClickListener { layout_lead_company_details.sp_lco.performClick() }
        layout_lead_company_details.sp_lco.onItemSelectedListener = this
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




    }

    fun setAdpter(){
        val salutation = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, list_of_salutation) }
        salutation?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        lead_contactinfo_layout.sp_salutation!!.adapter = salutation
        val subBusSeg = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, list_of_subBusSegment) }
        subBusSeg?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        lead_contactinfo_layout.sp_sub_bus!!.adapter = subBusSeg
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
        val lco = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, list_of_option) }
        custSeg?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        layout_lead_company_details.sp_lco!!.adapter = lco
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
        if (p0?.id == R.id.rl_back) {
            showFragment()
        }

    }

    fun showFragment(){
        val fragmentB = GetAllLeadFrag()
        activity?.getSupportFragmentManager()?.beginTransaction()
                ?.replace(R.id.fragment_main, fragmentB, "fragmnetId")
                ?.commit()
    }

    fun createLead(remark: String, companyname: String, firmtype: String, industrytype: String, jbtitle: String,
                   area: String, addres_build: String, city: String, floor: String, pincode: String,
                   building: String, state: String, spcfc_area: String, spcfc_building: String,
                   block: String, buildingname: String, inst_area: String, inst_build: String,
                   inst_build_name: String, inst_city_code: String?, inst_city: String, inst_country: String,
                   inst_floor: String, inst_pincode: String, inst_buil: String, inst_state: String,
                   inst_state_code: String, inst_block: String, inst_sp_area: String, inst_sp_building: String,
                   other_work: String, other_pro_one: String, other_pro_two: String, other_date: String,
                   other_firewal: String, other_targetdate: String?, other_wifi: String, other_voip: String,
                   other_vpn: String, other_media: String, other_cust_one: String, cust_two: String,
                   genral_name: String, general_lst_nm: String, general_chnl: String, general_src: String,
                   gnl_phn_num: String, gnl_sub: String, cnt_info_cnt_person: String, general_email: String,
                   topic: String, group: String, relation: String) {
        inAnimation = AlphaAnimation(0f, 1f)
        inAnimation?.duration =200
        leadFragmentBinding.createprogressLayout.progressOverlay.animation = inAnimation
        leadFragmentBinding.createprogressLayout.progressOverlay.visibility = View.VISIBLE

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

        val createLeadRequest = CreateLeadRequest(Constants.CREATE_LEAD,Constants.AUTH_KEY,"",
                companyDetail,contactAddress,installationAddress,"Business",str_cmp,
                cnt_info_cnt_person,str_customer_segmentid,general_email,"3",general_lst_nm
        ,group,"",general_chnl,general_src,topic,gnl_phn_num,otherDetail,"Target@2021#@",
        "",relation,remark,str_salutation,specific,
                gnl_sub,"manager1")

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.createLead(createLeadRequest)
        call.enqueue(object : Callback<CreateLeadResponse?> {
            override fun onResponse(call: Call<CreateLeadResponse?>, response: Response<CreateLeadResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        outAnimation = AlphaAnimation(1f, 0f)
                        inAnimation?.duration =200
                        leadFragmentBinding.createprogressLayout.progressOverlay.animation = outAnimation
                        leadFragmentBinding.createprogressLayout.progressOverlay.visibility = View.GONE

                        val img = response.body()!!.Response.Message
                        val id = response.body()!!.Response.LeadId
                        Log.e("image", img)
                        Toast.makeText(context, img +"("+ id +")", Toast.LENGTH_SHORT).show()
                        if(response.body()!!.Response.StatusCode.equals("200")){
                            val fragmentB = GetAllLeadFrag()
                            parentFragmentManager.beginTransaction()
                                    .replace(R.id.fragment_main, fragmentB, "fragmnetId")
                                    .commit()
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



    fun getBuilding(str_inst_area: String, code: String) {
        val getLeadBuildingRequest = GetLeadBuildingRequest(Constants.GET_BUILDING,Constants.AUTH_KEY,str_inst_area,code,"Target@2021#@","manager1")

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getleadBuilding(getLeadBuildingRequest)
        call.enqueue(object : Callback<GetLeadBuildingResponse?> {
            override fun onResponse(call: Call<GetLeadBuildingResponse?>, response: Response<GetLeadBuildingResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                      //  val msg = response.body()!!.Response.Message
                        buildingList= response.body()!!.Response.Data
                        building = ArrayList<String>()
                        buildingCode = ArrayList<String>()
                        building!!.add("Select Building")
                        buildingCode!!.add("")
                        for (item in buildingList!!){
                            building!!.add(item.BuildingName)
                            buildingCode!!.add(item.BuildingCode)
                        }
                        val adapter12 = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, building!!)
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
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
                        for (item in industryList!!){
                            instryname.add(item.IndTypeName)
                            industryid.add(item.IndTypeId)
                        }
                        val adapter12 = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, instryname)
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
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

    fun getAddBuilding(str_add_area_code: String, str_area: String) {
        val getLeadBuildingRequest = GetLeadBuildingRequest(Constants.GET_BUILDING,Constants.AUTH_KEY,str_add_area_code,str_area,"Target@2021#@","manager1")

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getleadBuilding(getLeadBuildingRequest)
        call.enqueue(object : Callback<GetLeadBuildingResponse?> {
            override fun onResponse(call: Call<GetLeadBuildingResponse?>, response: Response<GetLeadBuildingResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                       // val msg = response.body()!!.Response.Message
                        buildingList= response.body()!!.Response.Data
                        building = ArrayList<String>()
                        buildingCode = ArrayList<String>()
                        building!!.add("Select Building")
                        buildingCode!!.add("")
                        for (item in buildingList!!) {
                            building!!.add(item.BuildingName)
                            buildingCode!!.add(item.BuildingCode)
                        }
                        var buildPosition=0
                        buildingCode!!.forEachIndexed { index, s ->
                            if(s==strBuilding)buildPosition=index
                        }
                        val adapter12 = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, building!!)
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        layout_lead_contact_address.sp_cnt_building_nm.adapter = adapter12
                        layout_lead_contact_address.sp_cnt_building_nm.setSelection(buildPosition)
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
                        val img = response.body()!!.Response.Message
                        Log.e("image", img)
                        sourceList= response.body()!!.Response.Data
                        source = ArrayList<String>()
                        //source!!.add("Select ")
                        for (item in sourceList!!)
                            source!!.add(item.SourceName)
                        val adapter12 = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, source!!)
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
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
                it,"","manager1","Target@2021#@",true)
        }

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
                        area!!.add("Select Area")
                        areaCode!!.add("")
                        for (item in areaList!!) {
                            area!!.add(item.AreaName)
                            areaCode!!.add(item.AreaCode)
                        }
                        val adapter12 = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, area!!)
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
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


    fun getInstArea(str_city: String?, str_city_code: String,status:Boolean) {
        val getLeadAreaRequest = str_city?.let {
            GetLeadAreaRequest(Constants.Get_AREA,Constants.AUTH_KEY,str_city_code,
                it,"","manager1","Target@2021#@",status)
        }

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
                        area!!.add("Select Area")
                        areaCode!!.add("")
                        for (item in areaList!!) {
                            area!!.add(item.AreaName)
                            areaCode!!.add(item.AreaCode)
                        }

                        var areaPosition=0
                        areaCode?.forEachIndexed { index, s ->
                            if(s==strArea)areaPosition=index
                            println("Area code ${strArea}")
                        }
                        val adapter12 = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, area!!)
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        layout_lead_contact_address.sp_cnt_cnarea.adapter = adapter12
                        layout_lead_contact_address.sp_cnt_cnarea.setSelection(areaPosition)
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


    fun getCpAdArea(str_city: String?, str_city_code: String,status:Boolean) {
        val getLeadAreaRequest = str_city?.let {
            GetLeadAreaRequest(Constants.Get_AREA,Constants.AUTH_KEY,str_city_code,
                    it,"","manager1","Target@2021#@",status)
        }

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
                        area!!.add("Select Area")
                        areaCode!!.add("")
                        for (item in areaList!!) {
                            area!!.add(item.AreaName)
                            areaCode!!.add(item.AreaCode)
                        }

                        var areaPosition=0
                        areaCode?.forEachIndexed { index, s ->
                            if(s==strArea)areaPosition=index
                            println("Area code ${strArea}")
                        }
                        val adapter12 = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, area!!)
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        layout_lead_contact_address.sp_cnt_cnarea.adapter = adapter12
                        layout_lead_contact_address.sp_cnt_cnarea.setSelection(areaPosition)
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

 fun getCity(statecode: String) {
        val getCityRequest = GetCityRequest(Constants.GET_CITY,Constants.AUTH_KEY,"Target@2021#@",statecode,"manager1")

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
                        city!!.add("Select City")
                        cityCode!!.add("")
                        for (item in cityList!!) {
                            city!!.add(item.CityName)
                            cityCode!!.add(item.CityCode)
                        }

                        val adapter12 = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, city!!)
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        layout_lead_installation_address.sp_city.adapter = adapter12

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
                        for (item in cityList!!) {
                            othercity!!.add(item.CityName)
                            othercityCode!!.add(item.CityCode)
                        }
                        val adapter12 = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, othercity!!)
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
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
        val getCompanyRequest = GetCompanyRequest(Constants.GET_COMPANY,Constants.AUTH_KEY,"","Target@2021#@","manager1")
        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getComapnyList(getCompanyRequest)
        call.enqueue(object : Callback<GetCompanyResponse?> {
            override fun onResponse(call: Call<GetCompanyResponse?>, response: Response<GetCompanyResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        val img = response.body()!!.Response.Message
                        Log.e("image", img)
                        companyList= response.body()!!.Response.Data
                        company = ArrayList<String>()
                        companyId = ArrayList<String>()
                        company?.add("Select Company")
                        group = ArrayList<String>()
                        groupId = ArrayList<String>()
                        group!!.add("Select Group")
                        for (item in companyList!!){
                            company!!.add(item.Company_Name +"("+item.Company_ID+")")
                            companyId!!.add(item.Company_ID)
                        }

                        for (item in companyList!!) {
                            group!!.add(item.Group_Name +"("+item.Group_ID+")")
                            groupId!!.add(item.Group_ID)
                        }
                        val adapter12 = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, company!!)
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        lead_contactinfo_layout.sp_leadcmpny.adapter = adapter12

                        val adapter1 = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, group!!)
                        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        lead_contactinfo_layout.sp_group.adapter = adapter1

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
        val getCompanyRequest = str_cmny?.let { GetCompanyRequest(Constants.GET_RELATIONSHIP,Constants.AUTH_KEY, it,"Target@2021#@","manager1") }
        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getRelationList(getCompanyRequest)
        call.enqueue(object : Callback<GetRelationShipResponse?> {
            override fun onResponse(call: Call<GetRelationShipResponse?>, response: Response<GetRelationShipResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        val img = response.body()!!.Response.Message
                        Log.e("image", img)
                        relationList= response.body()!!.Response.Data
                        relation = ArrayList<String>()
                        relationId = ArrayList<String>()
                        relation!!.add("Select Relation")
                        for (item in relationList!!) {
                            relation!!.add(item.Relationship_Name +"("+item.Relationship_ID+")")
                            relationId!!.add(item.Relationship_ID)
                        }

                        val adapter12 = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, relation!!)
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
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

    fun  Calender(){
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        layout_lead_other_details.et_trgt_period.setOnClickListener {

            val dpd = DatePickerDialog(requireContext(), { view, year, monthOfYear, dayOfMonth ->
                val mn = monthOfYear+1
                layout_lead_other_details.et_trgt_period.setText("$dayOfMonth-$mn-$year")
                date = ("$year-$mn-$dayOfMonth")
            }, year, month, day)
            dpd.show()
        }
    }




    fun getAddCity(state_code: String) {
        val getCityRequest = GetCityRequest(Constants.GET_CITY,Constants.AUTH_KEY,"Target@2021#@",state_code,"manager1")

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
                        city!!.add("Select City")
                        cityCode!!.add("")
                        for (item in cityList!!) {
                            city!!.add(item.CityName)
                            cityCode!!.add(item.CityCode)
                        }
                        var cityPosition=0
                        cityCode!!.forEachIndexed { index, s ->
                            if(s==strCity)cityPosition=index
                        }
                        val adapter12 = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, city!!)
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        layout_lead_contact_address.sp_cnt_city.adapter = adapter12
                        layout_lead_contact_address.sp_cnt_city.setSelection(cityPosition)
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
            lead_contactinfo_layout.et_sub_businessseg.setText(list_of_subBusSegment.get(position))
            str_sub_bus = list_of_subBusSegment.get(position)
        }else if(parent?.id == R.id.sp_salutation){
            lead_contactinfo_layout.et_saluation.setText(list_of_salutation.get(position))
            if (position != 0) str_salutation = "" + list_of_salutation_id.get(position - 1) else str_salutation = " "
        }else if(parent?.id ==R.id.sp_leadchnl){
            layout_channel_source.et_lead_channel.setText(list_of_channel.get(position))
            str_lead_chnl = list_of_channel.get(position)
            getSource(str_lead_chnl)
        }else if(parent?.id ==R.id.sp_lead_src){
            layout_channel_source.et_lead_source.setText(source?.get(position))
            str_lead_src = source?.get(position)
        }else if(parent?.id == R.id.sp_cnt_cnarea){
            layout_lead_contact_address.et_cntarea.setText(area?.get(position))
            str_area = area?.get(position).toString()
            str_add_area_code =  areaCode?.get(position )
            getAddBuilding(str_add_area_code!!,str_area.toString())
            if(str_area=="Other"){
                layout_lead_contact_address.et_cntspecific_area.visibility=View.VISIBLE
            }
        }else if(parent?.id == R.id.sp_cnarea){
            layout_lead_installation_address.et_installarea.setText(area?.get(position))
            str_installbuild = area?.get(position).toString()
            str_inst_area = areaCode?.get(position )
            str_inst_area?.let { getBuilding(it, str_installbuild!!) }
            if(str_installbuild=="Other"){
                layout_lead_installation_address.et_specific_area.visibility=View.VISIBLE
            }
        }
        else if(parent?.id == R.id.sp_building_nm) {
            layout_lead_installation_address.et_building.setText(building?.get(position))
            str_inst_building_name = building?.get(position).toString()
            str_inst_build_num =  buildingCode?.get(position)
            if(str_inst_building_name=="Other"){
                layout_lead_installation_address.et_specific_building.visibility=View.VISIBLE
            }
        } else if(parent?.id == R.id.sp_cnt_building_nm) {
            layout_lead_contact_address.et_cnt_building.setText(building?.get(position))
            str_add_building =  buildingCode?.get(position )
            val str_inst_building= building?.get(position).toString()
            if(str_inst_building=="Other"){
                layout_lead_contact_address.et_cntbuilding_num.visibility=View.VISIBLE
            }
        }
        else if(parent?.id == R.id.sp_city){
            layout_lead_installation_address.et_add_city.setText(city?.get(position))
            str_city = city?.get(position).toString()
            str_city_code = cityCode?.get(position )
            getArea(str_city, str_city_code.toString())
        }else if(parent?.id == R.id.sp_cnt_city){
            layout_lead_contact_address.et_cnt_city.setText(city?.get(position))
            str_city = city?.get(position).toString()
            str_add_city =  cityCode?.get(position )
            getInstArea(str_city, str_add_city!!,false)
        }
      else if(parent?.id == R.id.sp_cntry){
           layout_lead_installation_address.et_country.setText(country_name.get(position))
            str_inst_country = country_name.get(position)
        }else if(parent?.id == R.id.sp_cnt_cntry){
            layout_lead_contact_address.et_cnt_country.setText(country_name.get(position))
            str_add_country = country_name.get(position)
        }else if(parent?.id == R.id.sp_cust_seg){
            lead_contactinfo_layout.et_customer_seg.setText(list_of_cust_segment.get(position))
            if (position != 0) str_customer_segmentid = "" + list_cust_seg_value.get(position - 1) else str_customer_segmentid = " "

        }else if(parent?.id == R.id.sp_cust_wifi){
            layout_lead_other_details.et_cust_wifi.setText(list_of_option.get(position))
            if (position != 0) str_wifi = "" + list_of_boolean.get(position - 1) else str_wifi = " "
        }else if(parent?.id == R.id.sp_ex_serv){
            layout_lead_other_details.et_ext_srv.setText(ext_serv_one.get(position))
            if (position != 0) str_ext_serv_pro_one = "" + ext_serv_one_value.get(position - 1) else str_ext_serv_pro_one = " "
        }
        else if(parent?.id == R.id.sp_ext_serv_two){
            layout_lead_other_details.et_ext_srv_two.setText(ext_serv_one.get(position))
            if (position != 0) str_serv_pro_two = "" + ext_serv_one_value.get(position - 1) else str_serv_pro_two = " "
        }else if(parent?.id == R.id.sp_serv_pro_one){
            layout_lead_other_details.et_service_pv_one.setText(ext_serv.get(position))
            if (position != 0) str_cust_serv_one = "" + ext_serv_val.get(position - 1) else str_cust_serv_one= " "
        }else if(parent?.id == R.id.sp_serv_pro_two){
            layout_lead_other_details.et_srv_pv_two.setText(ext_serv.get(position))
            if (position != 0) str_cust_serv_two = "" + ext_serv_val.get(position - 1) else str_cust_serv_two = " "
        }else if(parent?.id == R.id.sp_state){
            layout_lead_installation_address.et_state.setText(list_of_state.get(position))
            str_state = list_of_state.get(position)
           str_inst_state =list_state_code.get(position)
            getCity(str_inst_state.toString())
        }else if(parent?.id == R.id.sp_cnt_state){
            layout_lead_contact_address.et_cnt_state.setText(list_of_state.get(position))
            str_add_state = list_of_state.get(position)
             str_add_state_code = list_state_code.get(position)
            getAddCity(str_add_state_code.toString())
        }else if(parent?.id == R.id.sp_firm_type){
            layout_lead_company_details.et_firm_type.setText(list_firm_type.get(position))
            if (position != 0) str_firm_type = "" + list_firm_type_value.get(position - 1) else str_firm_type = " "
        }else if(parent?.id == R.id.sp_lco){
            layout_lead_company_details.et_lco.setText(list_of_option.get(position))
        }else if(parent?.id == R.id.sp_cst_voip){
            layout_lead_other_details.et_cust_void.setText(list_of_option.get(position))
            if (position != 0) str_voip = "" + list_of_boolean.get(position - 1) else str_voip = " "
        }else if(parent?.id == R.id.sp_industype){
            layout_lead_company_details.et_indus_type.setText(instryname.get(position))
            if (position != 0) str_industry_type = "" + industryid.get(position - 1) else str_industry_type= " "
        }else if(parent?.id == R.id.sp_media){
            layout_lead_other_details.et_media.setText(list_of_media.get(position))
           str_media =list_of_mediavalue.get(position)
        }else if(parent?.id == R.id.sp_intrsteddata_center){
            layout_lead_other_details.et_is_cus.setText(list_of_option.get(position))
            if (position != 0) str_data = "" + list_of_boolean.get(position - 1) else str_data= " "
        }else if(parent?.id == R.id.sp_intrs_frwal){
            layout_lead_other_details.et_indus_firewl.setText(list_of_option.get(position))
            if (position != 0) str_cust_frwl = "" + list_of_boolean.get(position - 1) else str_cust_frwl= " "
        }else if(parent?.id == R.id.sp_vpn_serv){
            layout_lead_other_details.et_vpn_srv.setText(list_of_option.get(position))
            if (position != 0) str_cust_vpn = "" + list_of_boolean.get(position - 1) else str_cust_vpn= " "
        }else if(parent?.id == R.id.sp_leadcmpny){
            lead_contactinfo_layout.et_leadcompany.setText(company?.get(position))
            if (position != 0) str_cmp = "" + companyId?.get(position - 1) else str_cmp= " "
            lead_contactinfo_layout.et_group.setText(group?.get(position))
            if (position != 0) str_grp = "" + groupId?.get(position - 1) else str_grp= " "
            str_company = company?.get(position)
            getRelation(str_cmp!!)
            if(str_company!=null) {
                if (str_company.contentEquals("New Company(CNew)")||str_company=="Select Company"){
                    lead_contactinfo_layout.et_lead_nm.setText("")
                    layout_lead_company_details.et_company_name.setText("")
                }else {
                    val cm = str_company
                    val split = cm?.split('(')
                    val nm = split?.get(0)
                    lead_contactinfo_layout.et_lead_nm.setText(nm)
                    layout_lead_company_details.et_company_name.setText(nm)
                }
            }
        }else if(parent?.id == R.id.sp_group){
            lead_contactinfo_layout.et_group.setText(group?.get(position))
            if (position != 0) str_grp = "" + groupId?.get(position - 1) else str_grp= " "
        }
        else if(parent?.id == R.id.sp_relation){
            lead_contactinfo_layout.et_relation.setText(relation?.get(position))
            if (position != 0) str_rltn = "" + relationId?.get(position - 1) else str_rltn= " "
        } else if(parent?.id == R.id.sp_wrk_lctn){
            layout_lead_other_details.et_crt_wrk.setText(othercity?.get(position))
            if (position != 0) str_othercity = "" + othercityCode?.get(position - 1) else str_othercity= " "
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

}