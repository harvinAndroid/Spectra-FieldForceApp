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
import com.spectra.fieldforce.utils.SalesAppConstants
import kotlinx.android.synthetic.main.lead__contact_person_row.view.*
import kotlinx.android.synthetic.main.lead_channel_source_row.view.*
import kotlinx.android.synthetic.main.lead_company_details_row.view.*
import kotlinx.android.synthetic.main.lead_contact_info.view.*
import kotlinx.android.synthetic.main.lead_demo_fragment.*
import kotlinx.android.synthetic.main.lead_installation_address_row.view.*
import kotlinx.android.synthetic.main.lead_installation_address_row.view.et_specific_build
import kotlinx.android.synthetic.main.lead_other_details_row.view.*
import kotlinx.android.synthetic.main.lead_remarks_row.view.*
import kotlinx.android.synthetic.main.lead_sdquestionare_details_row.view.*
import kotlinx.android.synthetic.main.sales_contact_address_row.view.*
import kotlinx.android.synthetic.main.salescontact_fragment.*
import kotlinx.android.synthetic.main.toolbar.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import java.util.regex.Pattern


class CreateLeadFragment:Fragment(), View.OnClickListener,AdapterView.OnItemSelectedListener {

   private lateinit var  leadFragmentBinding: LeadDemoFragmentBinding

    private var source : ArrayList<String>? = null
    private var sourceList: MutableList<SrcData>? = null
    private var areaList : ArrayList<AreaData>? = null
    private var area : ArrayList<String>? = null
   /* private var areaCode : ArrayList<String>? = null*/
    private var installArea : ArrayList<String>? = null
    private var cityList : ArrayList<CityData>? = null
    private var city : ArrayList<String>? = null
    private var cityCode : ArrayList<String>? = null
    private var RequiredCity : ArrayList<String>? = null
    private var RequiredCityCode : ArrayList<String>? = null
    private var verticalList : ArrayList<VerticalData>? = null
    private var vertical : ArrayList<String>? = null
    private var verticalId : ArrayList<String>? = null
    private var Installcity : ArrayList<String>? = null
    private var InstallcityCode : ArrayList<String>? = null
    private var buildingList : ArrayList<BuildData>? = null
    private var building : ArrayList<String>? = null
    private var company : ArrayList<String>? = null
    private var companyList: ArrayList<ComapnyData>? = null
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
    private var strCompanyName : String? = null
    private var str_customer_segmentid :String? = null
    private var str_lead_chnl : String? = null
    private var str_installbuild : String? = null
    private var str_lead_src : String? = null
    private var date :String ?=null
    private var mpls :String ?=null
    private var firesSet :String ?=null
    private var str_sub_bus : String? = null
    private var strVertical:String ? = null
    private var strIllServices :String? = null
    private var strCityReqd :String? = null
    private var strNetworkSecurity :String? = null
    private var strHosted :String? = null
    private var strBackBone:String ? = null
    private var strCustomer:String? = null
    private var strContract :String? = null
    private var strRoutingServices :String? = null
    private var strBroadServices :String? = null
    private var strLinksManaged :String? = null
    private var str_firm_type :String? = null
    private var str_industry_type :String? = null
    private var str_salutation :String? = null
    private var str_state : String? = null
    private var str_ext_serv_pro_one : String? = null
    private var str_serv_pro_two : String? = null
    private var str_media : String? = null
    private var str_data : String? = null
    private var str_cust_frwl : String? = null
    private var str_cust_vpn : String? = null
    private var str_cust_serv_one : String? = null
    private var str_cust_serv_two : String? = null
    private var str_voip : String? = null
    private var str_wifi : String? = null
    private var str_grp :String ? = null
    private var str_rltn:String ? = null
    private var str_rltnname:String ? = null
    private var str_cmp :String? = null
    private var str_inst_state: String? = null
    private var str_inst_area : String? = null
    private var str_inst_building_name : String? = null
    private var str_inst_build_num : String? = null
    private var str_add_state: String? = null
    private var str_add_state_code : String? = null
    private var str_add_city: String? = null
    private var str_add_area_code: String? = null
    private var str_add_building : String? = null
    private var str_chkbox : String? = null
    private var userName: String? = null
    private var password : String? = null
    private var str_specificbuild : String? = null
    private var strCity = ""
    private var strArea =""


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
        userName = sp1?.getString(AppConstants.USERNAME, null)
        password = sp1?.getString(AppConstants.PASSWORD, null)

        CoroutineScope(Dispatchers.IO).launch {
            setAdapter()
        }

        itemListener()
        listener()
        CoroutineScope(Dispatchers.IO).launch {
            init()
        }
        CoroutineScope(Dispatchers.IO).launch {
            Calender()
            getCompany()
            getIndustryTpe()
            getOtherCity()
        }
    }


    fun init(){
        checkbx.setOnCheckedChangeListener { _, _ ->
            str_chkbox="1"
            var cntstatePosition = 0
            SalesAppConstants.list_state_code.forEachIndexed { index, s ->
                if (s == str_inst_state) cntstatePosition = index
            }
            val cntstateAdapter = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, SalesAppConstants.list_of_state) }
            cntstateAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_item)
            layout_lead_contact_address.sp_cnt_state.adapter = cntstateAdapter
            layout_lead_contact_address.sp_cnt_state.setSelection(cntstatePosition)
            cntstateAdapter?.notifyDataSetChanged()
            getAddCity(str_inst_state)
            strCity = str_city_code.toString()
            str_add_area_code = str_inst_area.toString()

            val areaName =  layout_lead_installation_address.et_installarea.text.toString()
            layout_lead_contact_address.et_cntarea.setText(areaName)
            val buildingName =  layout_lead_installation_address.et_building.text.toString()
            layout_lead_contact_address.et_cnt_building.setText(buildingName)
            str_inst_building_name = str_inst_build_num.toString()
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

        leadFragmentBinding.leadSave.setOnClickListener {
            val remark:String = layout_lead_remarks.et_lead_remark.text.toString()
            val companyname:String =layout_lead_company_details.et_company_name.text.toString()
            val firmtype:String = str_firm_type.toString()
            val industrytype: String = str_industry_type.toString()
            val jbtitle:String = layout_lead_company_details.et_job_title.text.toString()
            val area = str_add_area_code.toString()
            val addres_build =str_add_building.toString()
            val city = str_add_city.toString()
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
            val location = layout_leadSdQuestionare.et_no_of_location.text.toString()
            val mentionNum = layout_leadSdQuestionare.etMentionNum.text.toString()
            val links = layout_leadSdQuestionare.et_links.text.toString()
            val itSpent = layout_leadSdQuestionare.et_itSpent.text.toString()
            val questionareRemark = layout_leadSdQuestionare.et_remarkk.text.toString()
            validEmail(general_email)
            if(topic.isBlank()){
                Toast.makeText(context, "Please Enter Topic", Toast.LENGTH_SHORT).show()
            }else if(gnl_sub.isBlank()||gnl_sub=="Select Sub Business Segment"||gnl_sub=="null"){
                Toast.makeText(context, "Please Select Sub Business Segment", Toast.LENGTH_SHORT).show()
            }else if(strVertical?.isBlank() == true && gnl_sub==AppConstants.SDWAN||strVertical=="null"){
                Toast.makeText(context, "Please Select Vertical", Toast.LENGTH_SHORT).show()
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
            } else if(relation.isBlank()&&(gnl_sub!=AppConstants.SDWAN)){
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
            }else if(inst_build_name.isBlank()||inst_build_name=="Select Building"||(inst_build_name=="null")){
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
                Toast.makeText(context, "Please enter PinCode", Toast.LENGTH_SHORT).show()
            }else if(companyname.isBlank()||(companyname=="null")){
                Toast.makeText(context, "Please Enter Company Name", Toast.LENGTH_SHORT).show()
            }else if(firmtype.isBlank()||firmtype=="Select Firm Type"||(firmtype=="null")){
                Toast.makeText(context, "Please Select Firm Type", Toast.LENGTH_SHORT).show()
            }else if(industrytype.isBlank()||industrytype=="Select Industry"||(industrytype=="null")){
                Toast.makeText(context, "Please Select Industry Type", Toast.LENGTH_SHORT).show()
            }/*else if(jbtitle.isBlank()||(jbtitle=="null")){
                Toast.makeText(context, "Please enter Job Title", Toast.LENGTH_SHORT).show()
            }*/else if(other_media.isBlank()||other_media=="Select Media"){
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
            }else if(str_sub_bus==AppConstants.SDWAN &&location.isBlank()||location=="null"){
                Toast.makeText(context, "Please Enter the Location", Toast.LENGTH_SHORT).show()
            }else if(str_sub_bus==AppConstants.SDWAN && strIllServices?.isBlank() == true ||strIllServices=="null"||strIllServices=="0"){
                Toast.makeText(context, "Please Select Customer Using ILL Services", Toast.LENGTH_SHORT).show()
            }else if((strIllServices=="122050000") && mentionNum.isBlank()){
                Toast.makeText(context, "Please Enter Mention Number of Links", Toast.LENGTH_SHORT).show()
            }else if(str_sub_bus==AppConstants.SDWAN && (strBroadServices?.isBlank() == true) ||strBroadServices=="null"||strBroadServices=="0"){
                Toast.makeText(context, "Please Enter the BroadBand Services", Toast.LENGTH_SHORT).show()
            }else if(links.isBlank()&&(strBroadServices=="122050000")){
                Toast.makeText(context, "Please Enter the NO. of Links", Toast.LENGTH_SHORT).show()
            }else if(str_sub_bus==AppConstants.SDWAN &&(strLinksManaged?.isBlank()== true)||strLinksManaged=="null"||strLinksManaged=="0"){
                Toast.makeText(context, "Please Enter the Managed Links ", Toast.LENGTH_SHORT).show()
            }else if(str_sub_bus==AppConstants.SDWAN &&(strRoutingServices?.isBlank()== true)||strRoutingServices=="null"||strRoutingServices=="0"){
                Toast.makeText(context, "Please Enter the Routing Services", Toast.LENGTH_SHORT).show()
            }else if(str_sub_bus==AppConstants.SDWAN &&(firesSet?.isBlank()== true)||firesSet=="null"){
                Toast.makeText(context, "Please Select Firewall set tpo expire", Toast.LENGTH_SHORT).show()
            }else if(str_sub_bus==AppConstants.SDWAN &&(strCityReqd?.isBlank()== true)||strCityReqd=="null"||strCityReqd=="Select Option"){
                Toast.makeText(context, "Please Enter the City Required", Toast.LENGTH_SHORT).show()
            } else if(str_sub_bus==AppConstants.SDWAN &&(strNetworkSecurity?.isBlank()== true)||strNetworkSecurity=="null"||strNetworkSecurity=="0"){
                Toast.makeText(context, "Please Enter the Network Security", Toast.LENGTH_SHORT).show()
            }else if(str_sub_bus==AppConstants.SDWAN &&(strHosted?.isBlank()== true)||strHosted=="null"||strHosted=="0"){
                Toast.makeText(context, "Please Enter the Hosted", Toast.LENGTH_SHORT).show()
            }else if(str_sub_bus==AppConstants.SDWAN &&(strCustomer?.isBlank()== true)||strCustomer=="null"||strCustomer=="0"){
                Toast.makeText(context, "Please Enter the Customer", Toast.LENGTH_SHORT).show()
            }else if(str_sub_bus==AppConstants.SDWAN &&(strContract?.isBlank()== true)||strContract=="null"||strContract=="0"){
                Toast.makeText(context, "Please Enter the Contract", Toast.LENGTH_SHORT).show()
            }
            else if(str_sub_bus==AppConstants.SDWAN &&(strBackBone?.isBlank()== true)||strBackBone=="null"||strBackBone=="0"){
                Toast.makeText(context, "Please Enter the Back Bone", Toast.LENGTH_SHORT).show()
            }else if(str_sub_bus==AppConstants.SDWAN &&(mpls?.isBlank()== true)||mpls=="null"){
                Toast.makeText(context, "Please Enter the Mpls", Toast.LENGTH_SHORT).show()
            }else {
                            createLead(remark,companyname,firmtype,industrytype,jbtitle,area,addres_build,city,floor,pincode,
                            building,spcfc_area,spcfc_building,block,inst_area,inst_build, inst_city_code,inst_floor,inst_pincode,
                            inst_buil,
                            inst_block,inst_sp_area,inst_sp_building,other_work,other_pro_one,other_pro_two,other_date,
                            other_firewal,other_targetdate,other_wifi,other_voip,other_vpn,other_media,other_cust_one,
                            cust_two,general_lst_nm,general_chnl,general_src,gnl_phn_num,gnl_sub,cnt_info_cnt_person,
                            general_email,topic,group,relation,location,mentionNum,links,itSpent,questionareRemark)
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
         layout_lead_installation_address.et_state.setOnClickListener { layout_lead_installation_address.sp_state.performClick() }
        layout_lead_installation_address.sp_state.onItemSelectedListener = this
        layout_lead_installation_address.et_add_city.setOnClickListener { layout_lead_installation_address.sp_city.performClick() }
        layout_lead_installation_address.sp_city.onItemSelectedListener = this

        layout_lead_contact_address.et_cnt_city.setOnClickListener { layout_lead_contact_address.sp_cnt_city.performClick() }
        layout_lead_contact_address.sp_cnt_city.onItemSelectedListener = this
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

        lead_contactinfo_layout.et_vertical.setOnClickListener { lead_contactinfo_layout.sp_vertical.performClick() }
        lead_contactinfo_layout.sp_vertical.onItemSelectedListener = this

        layout_leadSdQuestionare.et_customerUsingIllServices.setOnClickListener { layout_leadSdQuestionare.sp_customerUsingIllServices.performClick() }
        layout_leadSdQuestionare.sp_customerUsingIllServices.onItemSelectedListener = this

        layout_leadSdQuestionare.et_broadServices.setOnClickListener { layout_leadSdQuestionare.sp_broadServices.performClick() }
        layout_leadSdQuestionare.sp_broadServices.onItemSelectedListener = this

        layout_leadSdQuestionare.et_linksManged.setOnClickListener { layout_leadSdQuestionare.sp_linksManged.performClick() }
        layout_leadSdQuestionare.sp_linksManged.onItemSelectedListener = this

        layout_leadSdQuestionare.et_routingServices.setOnClickListener { layout_leadSdQuestionare.sp_routingServices.performClick() }
        layout_leadSdQuestionare.sp_routingServices.onItemSelectedListener = this

         layout_leadSdQuestionare.et_cityRequired.setOnClickListener { layout_leadSdQuestionare.sp_cityRequired.performClick() }
        layout_leadSdQuestionare.sp_cityRequired.onItemSelectedListener = this

        layout_leadSdQuestionare.et_networkSecurity.setOnClickListener { layout_leadSdQuestionare.sp_networkSecurity.performClick() }
        layout_leadSdQuestionare.sp_networkSecurity.onItemSelectedListener = this

        layout_leadSdQuestionare.et_hostedRequired.setOnClickListener { layout_leadSdQuestionare.sp_hostedRequired.performClick() }
        layout_leadSdQuestionare.sp_hostedRequired.onItemSelectedListener = this

        layout_leadSdQuestionare.et_customerRequired.setOnClickListener { layout_leadSdQuestionare.sp_customerRequired.performClick() }
        layout_leadSdQuestionare.sp_customerRequired.onItemSelectedListener = this

        layout_leadSdQuestionare.et_backboneRequired.setOnClickListener { layout_leadSdQuestionare.sp_backboneRequired.performClick() }
        layout_leadSdQuestionare.sp_backboneRequired.onItemSelectedListener = this

        layout_leadSdQuestionare.et_contractRenewed.setOnClickListener { layout_leadSdQuestionare.sp_contractRenewed.performClick() }
        layout_leadSdQuestionare.sp_contractRenewed.onItemSelectedListener = this

        layout_leadSdQuestionare.et_cityRequired.setOnClickListener { layout_leadSdQuestionare.sp_cityRequired.performClick() }
        layout_leadSdQuestionare.sp_cityRequired.onItemSelectedListener = this

        lead_contactinfo_layout.et_upemailid.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus)
            {
                val email = lead_contactinfo_layout.et_upemailid.text.toString()
                val isValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
                if (!isValid) {
                    Toast.makeText(context, "Invalid Email", Toast.LENGTH_LONG).show()
                }
            }
        }

        lead_contactinfo_layout.et_mobile_num.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus)
            {
                val mobile = lead_contactinfo_layout.et_mobile_num.text.toString()
                val isValid = Patterns.PHONE.matcher(mobile).matches()
                if (!isValid) {
                    Toast.makeText(context, "Invalid Mobile Number", Toast.LENGTH_LONG).show()
                }
            }
        }


    }

    private fun setAdapter(){
        val salutation = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, SalesAppConstants.list_of_salutation) }
        salutation?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        lead_contactinfo_layout.sp_salutation?.adapter = salutation
        val subBusSeg = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, SalesAppConstants.list_of_subBusSegment) }
        subBusSeg?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        lead_contactinfo_layout.sp_sub_bus?.adapter = subBusSeg
        val channel = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, SalesAppConstants.list_of_channel) }
        channel?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        layout_channel_source.sp_leadchnl?.adapter = channel
        val custSeg = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, SalesAppConstants.list_of_cust_segment) }
        custSeg?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        lead_contactinfo_layout.sp_cust_seg?.adapter = custSeg
        val serv = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, SalesAppConstants.ext_serv) }
        serv?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        layout_lead_other_details.sp_serv_pro_two?.adapter = serv
        val firmtype = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, SalesAppConstants.list_firm_type) }
        firmtype?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        layout_lead_company_details.sp_firm_type?.adapter = firmtype

        val dataa = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, SalesAppConstants.list_of_option) }
        dataa?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        layout_lead_other_details.sp_intrsteddata_center?.adapter = dataa

        val option = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, SalesAppConstants.list_of_option) }
        option?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        layout_lead_other_details.sp_cst_voip?.adapter = option
        val state = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, SalesAppConstants.list_of_state) }
        state?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        layout_lead_installation_address.sp_state?.adapter = state

        val ex_serv = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, SalesAppConstants.ext_serv_one) }
        ex_serv?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        layout_lead_other_details.sp_ex_serv?.adapter = ex_serv

        val firewal = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, SalesAppConstants.list_of_option) }
        firewal?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        layout_lead_other_details.sp_intrs_frwal?.adapter = firewal


        val sstate = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, SalesAppConstants.list_of_state) }
        sstate?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        layout_lead_contact_address.sp_cnt_state?.adapter = sstate

        val serv_two = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, SalesAppConstants.ext_serv_one) }
        serv_two?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        layout_lead_other_details.sp_ext_serv_two?.adapter = serv_two

        val vpn = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, SalesAppConstants.list_of_option) }
        vpn?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        layout_lead_other_details.sp_vpn_serv?.adapter = vpn

        val serv_pv = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, SalesAppConstants.ext_serv) }
        serv_pv?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        layout_lead_other_details.sp_serv_pro_one?.adapter = serv_pv

        val serv_pv_two = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, SalesAppConstants.ext_serv_one) }
        serv_pv_two?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        layout_lead_other_details.sp_ext_serv_two?.adapter = serv_pv_two

        val wifi = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, SalesAppConstants.list_of_option) }
        wifi?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        layout_lead_other_details.sp_cust_wifi?.adapter = wifi

        val media = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, SalesAppConstants.list_of_media) }
        media?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        layout_lead_other_details.sp_media?.adapter = media

        val illServices = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.listCustomerService)) }
        illServices?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        layout_leadSdQuestionare.sp_customerUsingIllServices?.adapter = illServices

        val broadServices = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.listCustomerService)) }
        broadServices?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        layout_leadSdQuestionare.sp_broadServices?.adapter = broadServices

        val linkManaged = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.listManagedLink)) }
        linkManaged?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        layout_leadSdQuestionare.sp_linksManged?.adapter = linkManaged

        val routingService = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.listCustomerService)) }
        routingService?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        layout_leadSdQuestionare.sp_routingServices?.adapter = routingService

        val networkSecurity = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.listNetworkSecurity)) }
        networkSecurity?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        layout_leadSdQuestionare.sp_networkSecurity?.adapter = networkSecurity

        val hosted = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.listApplicants)) }
        hosted?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        layout_leadSdQuestionare.sp_hostedRequired?.adapter = hosted

        val customer = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.listInHouse)) }
        customer?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        layout_leadSdQuestionare.sp_customerRequired?.adapter = customer

        val backBone = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.listCustomerService)) }
        backBone?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        layout_leadSdQuestionare.sp_backboneRequired?.adapter = backBone

        val contract = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.listCustomerService)) }
        contract?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        layout_leadSdQuestionare.sp_contractRenewed?.adapter = contract
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
            linearquestionare_details.visibility = View.GONE
        }
        linearthree.setOnClickListener { 
            linearcontactinfo.visibility = View.GONE
            linear_insta_addres.visibility = View.VISIBLE
            linadd.visibility = View.GONE
            linear_contact_person_address.visibility = View.GONE
            linear_companydetails.visibility= View.GONE
            linearother_details.visibility = View.GONE
            linearremark_details.visibility = View.GONE
            linearquestionare_details.visibility = View.GONE
        }
        linearfouraddres.setOnClickListener { 
            linearcontactinfo.visibility = View.GONE
            linear_insta_addres.visibility = View.GONE
            linadd.visibility = View.VISIBLE
            linear_contact_person_address.visibility = View.GONE
            linear_companydetails.visibility= View.GONE
            linearother_details.visibility = View.GONE
            linearremark_details.visibility = View.GONE
            linearquestionare_details.visibility = View.GONE
        }
        linearfive.setOnClickListener {
            linearcontactinfo.visibility = View.GONE
            linear_insta_addres.visibility = View.GONE
            linadd.visibility = View.GONE
            linear_contact_person_address.visibility = View.VISIBLE
            linear_companydetails.visibility= View.GONE
            linearother_details.visibility = View.GONE
            linearremark_details.visibility = View.GONE
            linearquestionare_details.visibility = View.GONE
        }
        linearsix.setOnClickListener { 
            linearcontactinfo.visibility = View.GONE
            linear_insta_addres.visibility = View.GONE
            linadd.visibility = View.GONE
            linear_contact_person_address.visibility = View.GONE
            linear_companydetails.visibility= View.VISIBLE
            linearother_details.visibility = View.GONE
            linearremark_details.visibility = View.GONE
            linearquestionare_details.visibility = View.GONE
        }
        lineareight.setOnClickListener { 
            linearcontactinfo.visibility = View.GONE
            linear_insta_addres.visibility = View.GONE
            linadd.visibility = View.GONE
            linear_contact_person_address.visibility = View.GONE
            linear_companydetails.visibility= View.GONE
            linearother_details.visibility = View.VISIBLE
            linearremark_details.visibility = View.GONE
            linearquestionare_details.visibility = View.GONE
        }
        linearnine.setOnClickListener {
            linearcontactinfo.visibility = View.GONE
            linear_insta_addres.visibility = View.GONE
            linadd.visibility = View.GONE
            linear_contact_person_address.visibility = View.GONE
            linear_companydetails.visibility= View.GONE
            linearother_details.visibility = View.GONE
            linearremark_details.visibility = View.VISIBLE
            linearquestionare_details.visibility = View.GONE
        }
        linearqustionare.setOnClickListener {
            linearcontactinfo.visibility = View.GONE
            linear_insta_addres.visibility = View.GONE
            linadd.visibility = View.GONE
            linear_contact_person_address.visibility = View.GONE
            linear_companydetails.visibility= View.GONE
            linearother_details.visibility = View.GONE
            linearremark_details.visibility = View.GONE
            linearquestionare_details.visibility = View.VISIBLE
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
        builder.setMessage(AppConstants.PREVIOUS_SCREEN)
        builder.setPositiveButton(
            AppConstants.YES
        ) { _, _ ->
            next()
        }
        builder.setNegativeButton(
            AppConstants.NO
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
    fun createLead(
        remark: String,
        companyname: String,
        firmtype: String,
        industrytype: String,
        jbtitle: String,
        area: String,
        addres_build: String,
        city: String,
        floor: String,
        pincode: String,
        building: String,
        spcfc_area: String,
        spcfc_building: String,
        block: String,
        inst_area: String,
        inst_build: String,
        inst_city_code: String?,
        inst_floor: String,
        inst_pincode: String,
        inst_buil: String,
        inst_block: String,
        inst_sp_area: String,
        inst_sp_building: String,
        other_work: String,
        other_pro_one: String,
        other_pro_two: String,
        other_date: String,
        other_firewal: String,
        other_targetdate: String?,
        other_wifi: String,
        other_voip: String,
        other_vpn: String,
        other_media: String,
        other_cust_one: String,
        cust_two: String,
        general_lst_nm: String,
        general_chnl: String,
        general_src: String,
        gnl_phn_num: String,
        gnl_sub: String,
        cnt_info_cnt_person: String,
        general_email: String,
        topic: String,
        group: String,
        relation: String,
        location: String,
        mentionNum: String,
        links: String,
        itSpent: String,
        questionareRemark: String


    ) {
            inProgress()
        var specific=""
        if(general_src=="Other"){
            specific = "Other"
        }

        val otherDetail = OtherDetail( other_work,other_pro_one,other_pro_two,other_date.toBoolean(),
                other_firewal.toBoolean(),other_wifi.toBoolean(),
                other_voip.toBoolean(),other_vpn.toBoolean(),other_media,other_cust_one,cust_two,other_targetdate)

        val companyDetail = CompanyDetail(companyname,firmtype, industrytype,jbtitle)

        val contactAddress= ContactAddress(area,addres_build,city,AppConstants.COUNTRY_CODE,floor,pincode,building,
                spcfc_area,spcfc_building,str_add_state_code,block)
        val installationAddress= InstallationAddress(inst_block,inst_area,inst_build,inst_city_code,
                "10001", inst_floor,inst_pincode,inst_buil,
                "0",inst_sp_area,inst_sp_building,str_inst_state)
        val sdwan = SDWAN(location,strIllServices,mentionNum,strBroadServices,
            links,strLinksManaged,strRoutingServices,firesSet,
            itSpent,strCityReqd,strNetworkSecurity,strHosted,strCustomer,
            strContract,strBackBone,mpls,questionareRemark)
        val createLeadRequest = CreateLeadRequest(Constants.CREATE_LEAD,Constants.AUTH_KEY,"",strVertical,
                companyDetail,contactAddress,installationAddress,sdwan,"Business",str_cmp,
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

    private fun getBuilding(str_inst_area: String?, code: String?) {
        val getLeadBuildingRequest = GetLeadBuildingRequest(Constants.GET_BUILDING,Constants.AUTH_KEY,str_inst_area,code,password,userName)

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getleadBuilding(getLeadBuildingRequest)
        call.enqueue(object : Callback<GetLeadBuildingResponse?> {
            override fun onResponse(call: Call<GetLeadBuildingResponse?>, response: Response<GetLeadBuildingResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                      //  val msg = response.body()?.Response?.Message
                        buildingList= response.body()?.Response?.Data
                        building = ArrayList<String>()
                        for (item in buildingList!!){
                            building?.add(item.BuildingName+"("+item.BuildingCode+")")
                        }
                        val adapter12 = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, building!!) }
                        layout_lead_installation_address.et_building.threshold=0
                        adapter12?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        layout_lead_installation_address.et_building.setAdapter(adapter12)

                        layout_lead_installation_address.et_building.setOnFocusChangeListener { _, b ->
                            if (b) layout_lead_installation_address.et_building.showDropDown()
                        }
                        layout_lead_installation_address.et_building.onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, _ ->
                            str_inst_building_name = adapter12?.getItem(position)
                            val buildingName = layout_lead_installation_address.et_building.text.toString()
                           /* str_inst_build_num = buildingCode?.get(position)*/
                            val split = buildingName.split("(")
                            val buildingId = split.get(1)
                            str_inst_building_name= split.get(0)
                            var buildingPosition=0
                            building?.forEachIndexed { index, s ->
                                if(s==str_inst_building_name)buildingPosition=index
                                str_inst_building_name.let { it?.let { it1 -> Log.e("idddddddddd", it1) } }
                                return@forEachIndexed
                            }
                            val areaId = buildingId.split(")")
                            str_inst_build_num = areaId[0]
                            /* strInstallBuildCode = buildingCode?.get(position)*/
                            if(str_inst_building_name=="Other"){
                                layout_lead_installation_address.et_specific_build.visibility=View.VISIBLE
                            }else{
                                layout_lead_installation_address.et_specific_build.visibility=View.GONE
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
                        for (item in industryList!!){
                            instryname.add(item.IndTypeName)
                            industryid.add(item.IndTypeId)
                        }
                        val adapter12 = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, instryname) }
                        adapter12?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        leadFragmentBinding.layoutLeadCompanyDetails.spIndustype.adapter=adapter12

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

    private fun getAddBuilding(str_add_area_code: String?, str_area: String?) {
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

                        for (item in buildingList!!) {
                            building?.add(item.BuildingName+"("+item.BuildingCode+")")
                        }

                        val adapter12 = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, building!!) }
                        layout_lead_contact_address.et_cnt_building.threshold=0
                        adapter12?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        layout_lead_contact_address.et_cnt_building.setAdapter(adapter12)

                        layout_lead_contact_address.et_cnt_building.setOnFocusChangeListener { _, b ->
                            if (b) layout_lead_contact_address.et_cnt_building.showDropDown()
                        }
                        layout_lead_contact_address.et_cnt_building.onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, _ ->
                            str_inst_building_name = adapter12?.getItem(position)
                            val build = layout_lead_contact_address.et_cnt_building.text.toString()
                            val split = build.split("(")
                            val buildingId = split[1]
                            str_inst_building_name= split[0]
                            var buildingPosition=0
                            building?.forEachIndexed { index, s ->
                                if(s==str_inst_building_name)buildingPosition=index
                                str_inst_building_name.let { it?.let { it1 -> Log.e("idddddddddd", it1) } }
                                return@forEachIndexed
                            }
                            val areaId = buildingId.split(")")
                            str_add_building = areaId[0]
                            if(str_inst_building_name=="Other"){
                                layout_lead_contact_address.et_cntbuilding.visibility=View.VISIBLE
                            }else{
                                layout_lead_contact_address.et_cntbuilding.visibility=View.GONE
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
                        areaList= response.body()?.Response?.Data
                        installArea = ArrayList<String>()
                        for (item in areaList!!) {
                            installArea?.add(item.AreaName+"("+item.AreaCode+")")
                        }
                        val adapter12 = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, installArea!!) }
                        layout_lead_installation_address.et_installarea.threshold=0
                        adapter12?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        layout_lead_installation_address.et_installarea.setAdapter(adapter12)

                        layout_lead_installation_address.et_installarea.setOnFocusChangeListener { _, b ->
                            if (b) layout_lead_installation_address.et_installarea.showDropDown()
                        }

                        layout_lead_installation_address.et_installarea.onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, _ ->
                            str_inst_area = adapter12?.getItem(position)

                            val area = layout_lead_installation_address.et_installarea.text.toString()
                            val split = area.split("(")
                            str_installbuild =  split[0]
                            val Areaid = split[1]
                            val areaId = Areaid.split(")")
                            str_inst_area = areaId[0]
                            if(str_installbuild?.startsWith("Other") == true){
                                layout_lead_installation_address.et_specarea.visibility=View.VISIBLE
                            }else{
                                layout_lead_installation_address.et_specarea.visibility=View.GONE
                            }
                            getBuilding( str_inst_area,str_installbuild)

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
                      /*  areaCode = ArrayList<String>()*/
                        for (item in areaList!!) {
                            area?.add(item.AreaName+"("+item.AreaCode+")")
                        }

                        val adapter12 = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, area!!) }
                        layout_lead_contact_address.et_cntarea.threshold=0
                        adapter12?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        layout_lead_contact_address.et_cntarea.setAdapter(adapter12)

                        layout_lead_contact_address.et_cntarea.setOnFocusChangeListener { _, b ->
                            if (b) layout_lead_contact_address.et_cntarea.showDropDown()
                        }

                        layout_lead_contact_address.et_cntarea.onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, _ ->
                            str_area = adapter12?.getItem(position)
                            val area = layout_lead_contact_address.et_cntarea.text.toString()
                            val split = area.split("(")
                            str_area =  split[0]
                            val Areaid = split[1]
                            val areaId = Areaid.split(")")
                            str_add_area_code = areaId[0]
                            if(str_area?.startsWith("Other") == true){
                                layout_lead_contact_address.spec_area.visibility=View.VISIBLE
                            }else{
                                layout_lead_contact_address.spec_area.visibility=View.GONE
                            }
                            getAddBuilding(str_add_area_code,str_area)

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
                       /* var cityPosition = 0
                        InstallcityCode?.forEachIndexed { index, s ->
                            if (s == str_city_code) cityPosition = index
                            return@forEachIndexed
                        }*/
                        val adapter121 = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, Installcity!!) }
                        adapter121?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        layout_lead_installation_address.sp_city.adapter = adapter121
                        //layout_lead_installation_address.sp_city.setSelection(cityPosition)
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


    private fun getOtherCity() {
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
                        for (item in cityList!!) {
                            othercity?.add(item.CityName)
                            othercityCode?.add(item.CityCode)
                        }
                        val adapter12 = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, othercity!!) }
                        adapter12?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        leadFragmentBinding.layoutLeadOtherDetails.spWrkLctn.adapter = adapter12

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
                        company?.add("Select Company")
                        group = ArrayList<String>()
                        groupId = ArrayList<String>()
                        group?.add("Select Group")
                        for (item in companyList!!){
                            company?.add(item.Company_Name +" ("+item.Company_ID+")")
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
            val dpd = DatePickerDialog(requireContext(), { _, year, monthOfYear, dayOfMonth ->
                val mn = monthOfYear+1
                layout_lead_other_details.et_trgt_period.setText("$dayOfMonth-$mn-$year")
                val trgt =  layout_lead_other_details.et_trgt_period.text.toString()
                val split = trgt.split("-")
                val dateee = split[0]
                val month1 = split[1]
                val year1 = split[2]
                date=("$year1-$month1-$dateee")
            }, year, month, day)
            dpd.show()
        }

            layout_leadSdQuestionare.et_fireSet.setOnClickListener {
                val dpd = DatePickerDialog(requireContext(), { _, year, monthOfYear, dayOfMonth ->
                    val mn = monthOfYear+1
                    layout_leadSdQuestionare.et_fireSet.setText("$dayOfMonth-$mn-$year")
                    val fire =  layout_leadSdQuestionare.et_fireSet.text.toString()
                    val split = fire.split("-")
                    val dateee = split[0]
                    val month1 = split[1]
                    val year1 = split[2]
                    firesSet=("$year1-$month1-$dateee")
                }, year, month, day)
                dpd.show()
            }

            layout_leadSdQuestionare.et_MplsRequired.setOnClickListener {
                val dpd = DatePickerDialog(requireContext(), { _, year, monthOfYear, dayOfMonth ->
                    val mn = monthOfYear+1
                    layout_leadSdQuestionare.et_MplsRequired.setText("$dayOfMonth-$mn-$year")
                    val mplsRequired =  layout_leadSdQuestionare.et_MplsRequired.text.toString()
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




    fun getAddCity(state_code: String?) {
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

    fun getRequiredCity() {
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
                        RequiredCity = ArrayList<String>()
                        RequiredCityCode = ArrayList<String>()
                        RequiredCity?.add("Select City")
                        RequiredCityCode?.add("")
                        for (item in cityList!!) {
                            RequiredCity?.add(item.CityName)
                            RequiredCityCode?.add(item.CityCode)
                        }

                        val adapter12 = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, RequiredCity!!) }
                        adapter12?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        layout_leadSdQuestionare.sp_cityRequired.adapter = adapter12
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


    fun getVertcal() {
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
                        val adapter12 = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, vertical!!) }
                        adapter12?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        lead_contactinfo_layout.sp_vertical.adapter = adapter12
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



    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (parent?.id == R.id.sp_sub_bus) {
            lead_contactinfo_layout.et_sub_businessseg.setText(SalesAppConstants.list_of_subBusSegment[position])
            str_sub_bus = SalesAppConstants.list_of_subBusSegment[position]
            if(str_sub_bus==AppConstants.SDWAN) {
                str_rltn="0"
                getVertcal()
                getRequiredCity()
                lead_contactinfo_layout.relation.visibility=View.GONE
                lead_contactinfo_layout.vertical.visibility=View.VISIBLE
                linearqustionare.visibility=View.VISIBLE
            }else{
                lead_contactinfo_layout.vertical.visibility=View.GONE
                linearqustionare.visibility=View.GONE
                lead_contactinfo_layout.relation.visibility=View.VISIBLE
            }
        }
        else if (parent?.id == R.id.sp_vertical) {
            lead_contactinfo_layout.et_vertical.setText(vertical?.get(position))
            strVertical = verticalId?.get(position)
        }
        else if (parent?.id == R.id.sp_customerUsingIllServices) {
            layout_leadSdQuestionare.et_customerUsingIllServices.setText(resources.getStringArray(R.array.listCustomerService)[position])
            strIllServices =resources.getStringArray(R.array.listCustomerServiceVal)[position]
            if(strIllServices.equals("122050000")){
                layout_leadSdQuestionare.mention.visibility=View.VISIBLE
            }else{
                layout_leadSdQuestionare.mention.visibility=View.GONE
            }
        }
        else if (parent?.id == R.id.sp_broadServices) {
            layout_leadSdQuestionare.et_broadServices.setText(resources.getStringArray(R.array.listCustomerService)[position])
            strBroadServices =resources.getStringArray(R.array.listCustomerServiceVal)[position]
            if(strBroadServices.equals("122050000")){
                layout_leadSdQuestionare.links.visibility=View.VISIBLE
            }else{
                layout_leadSdQuestionare.links.visibility=View.GONE

            }
        }
        else if (parent?.id == R.id.sp_linksManged) {
            layout_leadSdQuestionare.et_linksManged.setText(resources.getStringArray(R.array.listManagedLink)[position])
            strLinksManaged =resources.getStringArray(R.array.listMangedLinkVal)[position]
        }
        else if (parent?.id == R.id.sp_routingServices) {
            layout_leadSdQuestionare.et_routingServices.setText(resources.getStringArray(R.array.listCustomerService)[position])
            strRoutingServices =resources.getStringArray(R.array.listCustomerServiceVal)[position]
        }
        else if (parent?.id == R.id.sp_cityRequired) {
            layout_leadSdQuestionare.et_cityRequired.setText(RequiredCity?.get(position) )
            strCityReqd =(RequiredCityCode?.get(position))
        }
        else if (parent?.id == R.id.sp_networkSecurity) {
            layout_leadSdQuestionare.et_networkSecurity.setText(resources.getStringArray(R.array.listNetworkSecurity)[position])
            strNetworkSecurity =resources.getStringArray(R.array.listMangedLinkVal)[position]
        }
        else if (parent?.id == R.id.sp_hostedRequired) {
            layout_leadSdQuestionare.et_hostedRequired.setText(resources.getStringArray(R.array.listApplicants)[position])
            strHosted =resources.getStringArray(R.array.listApplicantsVal)[position]
        }
        else if (parent?.id == R.id.sp_customerRequired) {
            layout_leadSdQuestionare.et_customerRequired.setText(resources.getStringArray(R.array.listInHouse)[position])
            strCustomer =resources.getStringArray(R.array.listCustomerServiceVal)[position]
        }
        else if (parent?.id == R.id.sp_backboneRequired) {
            layout_leadSdQuestionare.et_backboneRequired.setText(resources.getStringArray(R.array.listCustomerService)[position])
            strBackBone =resources.getStringArray(R.array.listCustomerServiceVal)[position]
        }
        else if (parent?.id == R.id.sp_contractRenewed) {
            layout_leadSdQuestionare.et_contractRenewed.setText(resources.getStringArray(R.array.listCustomerService)[position])
            strContract =resources.getStringArray(R.array.listCustomerServiceVal)[position]
        }
        else if (parent?.id == R.id.sp_salutation) {
            lead_contactinfo_layout.et_saluation.setText(SalesAppConstants.list_of_salutation[position])
            if (position != 0) str_salutation = "" + SalesAppConstants.list_of_salutation_id[position - 1] else str_salutation = " "
        }
        else if (parent?.id == R.id.sp_leadchnl) {
            layout_channel_source.et_lead_channel.setText(SalesAppConstants.list_of_channel[position])
            str_lead_chnl = SalesAppConstants.list_of_channel[position]
            getSource(str_lead_chnl)
        }
        else if (parent?.id == R.id.sp_lead_src) {
            layout_channel_source.et_lead_source.setText(source?.get(position))
            str_lead_src = source?.get(position)
        }
        else if (parent?.id == R.id.sp_city) {
            layout_lead_installation_address.et_add_city.setText(Installcity?.get(position))
            str_city = Installcity?.get(position).toString()
            str_city_code = InstallcityCode?.get(position )
            getArea(str_city, str_city_code.toString())
        }
        else if (parent?.id == R.id.sp_cnt_city) {
            layout_lead_contact_address.et_cnt_city.setText(city?.get(position))
            str_city = city?.get(position).toString()
            str_add_city =  cityCode?.get(position)
            getInstArea(str_city, str_add_city,false)
        }
        else if (parent?.id == R.id.sp_cust_seg) {
            lead_contactinfo_layout.et_customer_seg.setText(SalesAppConstants.list_of_cust_segment[position])
            if (position != 0) str_customer_segmentid = "" + SalesAppConstants.list_cust_seg_value[position - 1] else str_customer_segmentid = " "
        }
        else if (parent?.id == R.id.sp_cust_wifi) {
            layout_lead_other_details.et_cust_wifi.setText(SalesAppConstants.list_of_option[position])
            if (position != 0) str_wifi = "" + SalesAppConstants.list_of_boolean[position - 1] else str_wifi = " "
        }
        else if (parent?.id == R.id.sp_ex_serv) {
            layout_lead_other_details.et_ext_srv.setText(SalesAppConstants.ext_serv_one[position])
            if (position != 0) str_ext_serv_pro_one = "" + SalesAppConstants.ext_serv_one_value[position - 1] else str_ext_serv_pro_one = " "
        }
        else if (parent?.id == R.id.sp_ext_serv_two) {
            layout_lead_other_details.et_ext_srv_two.setText(SalesAppConstants.ext_serv_one[position])
            if (position != 0) str_serv_pro_two = "" + SalesAppConstants.ext_serv_one_value[position - 1] else str_serv_pro_two = " "
        }
        else if (parent?.id == R.id.sp_serv_pro_one) {
            layout_lead_other_details.et_service_pv_one.setText(SalesAppConstants.ext_serv[position])
            if (position != 0) str_cust_serv_one = "" + SalesAppConstants.ext_serv_val[position - 1] else str_cust_serv_one= " "
        }
        else if (parent?.id == R.id.sp_serv_pro_two) {
            layout_lead_other_details.et_srv_pv_two.setText(SalesAppConstants.ext_serv[position])
            if (position != 0) str_cust_serv_two = "" +SalesAppConstants. ext_serv_val[position - 1] else str_cust_serv_two = " "
        }
        else if (parent?.id == R.id.sp_state) {
            layout_lead_installation_address.et_state.setText(SalesAppConstants.list_of_state[position])
            str_state = SalesAppConstants.list_of_state[position]
            str_inst_state = SalesAppConstants.list_state_code[position]
            getCity(str_inst_state)
        }
        else if (parent?.id == R.id.sp_cnt_state) {
            layout_lead_contact_address.et_cnt_state.setText(SalesAppConstants.list_of_state[position])
            str_add_state = SalesAppConstants.list_of_state[position]
            str_add_state_code = SalesAppConstants.list_state_code[position]
            getAddCity(str_add_state_code.toString())
        }
        else if (parent?.id == R.id.sp_firm_type) {
            layout_lead_company_details.et_firm_type.setText(SalesAppConstants.list_firm_type[position])
            if (position != 0) str_firm_type = "" + SalesAppConstants.list_firm_type_value[position - 1] else str_firm_type = " "
        }
        else if (parent?.id == R.id.sp_cst_voip) {
            layout_lead_other_details.et_cust_void.setText(SalesAppConstants.list_of_option[position])
            if (position != 0) str_voip = "" + SalesAppConstants.list_of_boolean[position - 1] else str_voip = " "
        }
        else if (parent?.id == R.id.sp_industype) {
            layout_lead_company_details.et_indus_type.setText(instryname[position])
            if (position != 0) str_industry_type = "" + industryid[position - 1] else str_industry_type= " "
        }
        else if (parent?.id == R.id.sp_media) {
            layout_lead_other_details.et_media.setText(SalesAppConstants.list_of_media[position])
            str_media = SalesAppConstants.list_of_mediavalue[position]
        }
        else if (parent?.id == R.id.sp_intrsteddata_center) {
            layout_lead_other_details.et_is_cus.setText(SalesAppConstants.list_of_option[position])
            if (position != 0) str_data = "" + SalesAppConstants.list_of_boolean[position - 1] else str_data= " "
        }
        else if (parent?.id == R.id.sp_intrs_frwal) {
            layout_lead_other_details.et_indus_firewl.setText(SalesAppConstants.list_of_option[position])
            if (position != 0) str_cust_frwl = "" + SalesAppConstants.list_of_boolean[position - 1] else str_cust_frwl= " "
        }
        else if (parent?.id == R.id.sp_vpn_serv) {
            layout_lead_other_details.et_vpn_srv.setText(SalesAppConstants.list_of_option[position])
            if (position != 0) str_cust_vpn = "" + SalesAppConstants.list_of_boolean[position - 1] else str_cust_vpn= " "
        }
        else if (parent?.id == R.id.sp_group) {
            lead_contactinfo_layout.et_group.setText(group?.get(position))
            if (position != 0) str_grp = "" + groupId?.get(position - 1) else str_grp= " "
        }
        else if (parent?.id == R.id.sp_relation) {
            lead_contactinfo_layout.et_relation.setText(relation?.get(position))
            str_rltnname = relation?.get(position)
            if (position != 0) str_rltn = "" + relationId?.get(position - 1) else str_rltn= " "
            val split = str_rltnname?.split('(')
            val rel = split?.get(0)
            when {
                rel!="Select Relation" -> {
                    when {
                        rel != "New Relationship " -> {
                            layout_lead_installation_address.et_state.setText(state?.get(position))
                            str_state = state?.get(position)
                            str_inst_state = stateId?.get(position)
                            var cntstatePosition = 0
                            SalesAppConstants.list_of_state.forEachIndexed { index, s ->
                                if (s == str_state) cntstatePosition = index
                            }
                            val cntstateAdapter = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, SalesAppConstants.list_of_state) }
                            cntstateAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            layout_lead_installation_address.sp_state.adapter = cntstateAdapter
                            layout_lead_installation_address.sp_state.setSelection(cntstatePosition)
                            cntstateAdapter?.notifyDataSetChanged()
                            layout_lead_installation_address.et_state.isEnabled = false
                            str_city_code = InstallcityCode?.get(position).toString()
                            getCity(str_inst_state)
                            layout_lead_installation_address.et_add_city.isEnabled = false
                            getArea(str_city, str_city_code.toString())
                        }
                        else -> {
                            layout_lead_installation_address.et_state.setText("")
                            layout_lead_installation_address.et_add_city.setText("")
                            Installcity?.clear()
                            InstallcityCode?.clear()
                            val cntstateAdapter = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, SalesAppConstants.list_of_state) }
                            cntstateAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            layout_lead_installation_address.sp_state.adapter = cntstateAdapter
                            cntstateAdapter?.notifyDataSetChanged()
                            str_city_code = ""
                            getCity(str_inst_state)
                            layout_lead_installation_address.et_state.isEnabled = true
                            layout_lead_installation_address.et_add_city.isEnabled = true
                        }
                    }

                }
            }

        }
        else if (parent?.id == R.id.sp_wrk_lctn) {
            layout_lead_other_details.et_crt_wrk.setText(othercity?.get(position))
            if (position != 0) str_othercity = "" + othercityCode?.get(position - 1) else str_othercity= " "
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

}