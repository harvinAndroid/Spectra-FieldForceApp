package com.spectra.fieldforce.salesapp.activity


import GetAllFeasibiltyAdapter
import GetAllQuoteAdapter
import GetAllSiteAdapter
import GetDOAViewAdapter
import GetPreSaleTaskAdapter
import android.Manifest
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.spectra.fieldforce.R
import com.spectra.fieldforce.api.ApiClient
import com.spectra.fieldforce.api.ApiInterface
import com.spectra.fieldforce.databinding.OppurtunityActivityBinding
import com.spectra.fieldforce.model.gpon.response.CommonClassResponse
import com.spectra.fieldforce.salesapp.adapter.GetAllProductItemAdapter
import com.spectra.fieldforce.salesapp.model.*
import com.spectra.fieldforce.utils.AppConstants
import com.spectra.fieldforce.utils.Constants
import com.spectra.fieldforce.utils.SalesConstant
import kotlinx.android.synthetic.main.op_product_line_item_row.*
import kotlinx.android.synthetic.main.op_product_line_item_row.view.*
import kotlinx.android.synthetic.main.opp_add_doa.*
import kotlinx.android.synthetic.main.opp_company_details_row.*
import kotlinx.android.synthetic.main.opp_company_details_row.view.*
import kotlinx.android.synthetic.main.opp_other_info_row.*
import kotlinx.android.synthetic.main.opp_other_info_row.view.*
import kotlinx.android.synthetic.main.opp_sales_task.view.*
import kotlinx.android.synthetic.main.oppurtunity_contact_info_row.*
import kotlinx.android.synthetic.main.oppurtunity_contact_info_row.view.*
import kotlinx.android.synthetic.main.oppurtunity_detail_row.view.*
import kotlinx.android.synthetic.main.oppurtunity_activity.*
import kotlinx.android.synthetic.main.oppurtunity_activity.linadd
import kotlinx.android.synthetic.main.oppurtunity_activity.linear_companydetails
import kotlinx.android.synthetic.main.oppurtunity_activity.linear_contact_person_address
import kotlinx.android.synthetic.main.oppurtunity_activity.linear_insta_addres
import kotlinx.android.synthetic.main.oppurtunity_activity.linearcontactinfo
import kotlinx.android.synthetic.main.oppurtunity_activity.linearfive
import kotlinx.android.synthetic.main.oppurtunity_activity.linearfouraddres
import kotlinx.android.synthetic.main.oppurtunity_activity.linearsix
import kotlinx.android.synthetic.main.oppurtunity_activity.linearthree
import kotlinx.android.synthetic.main.oppurtunity_activity.lineartwo
import kotlinx.android.synthetic.main.update_leadtoolbar.*
import kotlinx.android.synthetic.main.update_leadtoolbar.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import java.util.regex.Pattern

class OpportunityActivity:AppCompatActivity(), View.OnClickListener,AdapterView.OnItemSelectedListener{

    lateinit var  binding: OppurtunityActivityBinding
    private var strOppId : String? = null
    private var subBusSegment:String?=null
    private var strLeadId : String? = null
    private var cityList : ArrayList<CityData>? = null
    private var city : ArrayList<String>? = null
    private var cityCode : ArrayList<String>? = null
    private var Oprationcity : ArrayList<String>? = null
    private var generatequote: ArrayList<QuoteData>? = null
    private var allSiteList: ArrayList<SiteData>? = null
    private var allFeasibility: ArrayList<FeasData>? = null
    private var allApproval: ArrayList<AppData>? = null
    private var allPreSales: ArrayList<PreSaleData>? = null
    var strOperationCity = ""
    var strCity = ""
    var strArea= ""
    var strBuildingStatus =""
    var strBuilding = ""
    var strPrice = ""
    var strIndustry =""
    private var strRedundancy =""
    var strBusinessSement =""
    var strCreateAreaOrBuilding=""
    var strTPFeasibilty =""
    var redundancy :Boolean? = null
    var strApprovalStatus :String? = null
    var strSalutation :String? = null
    var str_customer_segmentid :String? = null
    var str_serv_pro : String? = null
    var str_createbuilding : Boolean? = null
    var str_cust_frwl : String? = null
    var str_redundancy : String? = null
    var str_industry_type :String? = null
    var str_firm_type :String? = null
    var str_inst_building_nm : String? = null
    var strcontact_state:String? = null
    var strcontact_stateCode:String? = null
    var str_city_code : String? = null
    var buildingname : String? = null
    var areaname: String?=null
    var isdiscount:Boolean=false
    var isStatus:Boolean=false
    var strturnOver :String?=null
    private var inAnimation: AlphaAnimation? = null
    private var outAnimation: AlphaAnimation? = null
    private var allProductItem: ArrayList<ItemData>? = null
    var productseg :String?= null
    var str_city: String? = null
    var fesstatus: String? = null
    var fesbilty: String? =null
    var str_inst_area : String? = null
    var str_cmpnyself : String? = null
    var str_price : String? = null
    var str_product : String? = null
    private var strType :String ? = null
    var strLost = ""
    var status :String? = null
    var strStatus :String ? = null
    var strReason:String ? =null
    var frwaws:String? = null
    var renewal:String? = null
    var polockdate:String? = null
    var datePoLock :String?=null
    var Poc:String?=null
    var mpls :String ?=null
    var firesSet :String ?=null
    var reas:String? = null
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
    private var RequiredCity : ArrayList<String>? = null
    private var RequiredCityCode :ArrayList<String>? = null
    private var verticalList : ArrayList<VerticalData>? = null
    private var vertical : ArrayList<String>? = null
    private var verticalId : ArrayList<String>? = null
    private var areaList : ArrayList<AreaData>? = null
    private var area : ArrayList<String>? = null
    private var areaCode : ArrayList<String>? = null
    private var industryList : ArrayList<IndustryResponse>? = null
    private var instryname = ArrayList<String>()
    private var industryid = ArrayList<String>()
    private var buildingList : ArrayList<BuildData>? = null
    private var building : ArrayList<String>? = null
    private var buildingCode : ArrayList<String>? = null
    private var priceList : ArrayList<PriceData>? = null
    private var price : ArrayList<String>? = null
    private var productList : ArrayList<ProData>? = null
    private var productId : ArrayList<String>? = null
    private var pricebuildingCode : ArrayList<String>? = null

    var str_media : String? = null
    private var userName : String? = null
    private var password : String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.oppurtunity_activity)
        searchtoolbaropp.rl_back.setOnClickListener(this)
        searchtoolbaropp.tv_lang.text= AppConstants.OPPURTUNUTY
        val sp1: SharedPreferences = this.getSharedPreferences("Login", 0)
        userName = sp1.getString(AppConstants.USERNAME, null)
        password = sp1.getString(AppConstants.PASSWORD, null)
        val extras = intent.extras
        if (extras != null) {
            strOppId = extras.getString("OppId")
            strLeadId = extras.getString("LeadId")
        }
        funcall()
        flr.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                if(subBusSegment==AppConstants.SDWAN){
                    saf()
                }else {
                    caf()
                }
            }
        }
        if (checkPermission()) {
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                requestPermission()
            }
        }
        binding.oppSite.addSite.setOnClickListener {
            val intent = Intent(this@OpportunityActivity, SiteOpportunityAct::class.java)
            val bundle = Bundle()
            bundle.putString("OppId",strOppId )
            bundle.putString("Status","1")
            intent.putExtras(bundle)
            startActivity(intent)
            finish()
        }
    }
    private fun funcall(){

            listener()
            init()
            CoroutineScope(Dispatchers.IO).launch{
                getOpportunity()
                getProductAddedList()
                getAllfeasList()
            }
            buttonlistener()
    }

    private fun buttonlistener(){
        layout_product_line.add_procuct.setOnClickListener{
            val product = layout_product_line.et_product_list.text.toString()
            if(product.isBlank()||product==("Select Product")){
                Toast.makeText(this, "Please Select Product", Toast.LENGTH_SHORT).show()
            }else{
                addProduct()
            }
        }

        binding.feasibility.addFes.setOnClickListener{
                addFeasibility()
        }

        binding.quote.addQuote.setOnClickListener{
                addQuote()

        }

        tv_won.setOnClickListener{
                wonOpp()
        }

        tv_reopen.setOnClickListener{
                reOpen()
        }


        et_lost.setOnClickListener {
                sp_lost.performClick()

        }

        oppSale.add_preTask.setOnClickListener {
                    createPreTask()
        }

        sp_lost.onItemSelectedListener = this

        tv_opp_save.setOnClickListener  {
                save()
        }

        binding.tvOppSubmit.setOnClickListener{
            val status = fesstatus.toString()
            val opp= strOperationCity.toString()
            val area = strArea.toString()
            val building = strBuilding.toString()
            val create = strCreateAreaOrBuilding.toString()
            val BuildingStatus = strBuildingStatus.toString()
            val red = redundancy
            val sbus = subBusSegment.toString()
            val Reason = layout_product_line.et_op_reason.text?.trim().toString()
            if(Reason.isNotBlank()) {
                if (status == "1") {
                    if ((area == "Other") && (building == "Other")
                        && (create == "false")/* && strTPFeasibilty.isNotBlank()*/) {
                        fab_create_society.visibility = View.VISIBLE
                        submitApproval()
                    } else {
                        submitApproval()
                    }
                } else if (opp == "true") {
                    if ((BuildingStatus == "C-RFS" || BuildingStatus == "P-RFS" ||
                                BuildingStatus == "B-RFS" || BuildingStatus == "A-RFS") && (red == false)
                    ) {
                        submitApproval()
                    } else if (sbus == AppConstants.SDWAN) {
                        submitApproval()
                    } else {
                        Toast.makeText(
                            this@OpportunityActivity,
                            "Please Complete the Feasibility Record",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else if (status == "0") {
                    Toast.makeText(
                        this@OpportunityActivity,
                        "Please Complete the Feasibility Record",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            else{
                Toast.makeText(
                    this@OpportunityActivity,
                    "Please Enter the Reason",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

        fab_create_society.setOnClickListener{
            if(fesstatus=="1") {
                str_createbuilding = true
                    save()
            }else{
                Toast.makeText(this@OpportunityActivity, "Please Complete the Feasibility Record", Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun caf(){
        val intent = Intent(this, CAFActivity::class.java)
        val bundle = Bundle()
        bundle.putString("OppId", strOppId)
        intent.putExtras(bundle)
        startActivity(intent)
        finish()
    }
    fun saf(){
        val intent = Intent(this, SAFActivity::class.java)
        val bundle = Bundle()
        bundle.putString("OppId", strOppId)
        intent.putExtras(bundle)
        startActivity(intent)
        finish()
    }

    fun init(){
        layout_opp_cntct_person.et_opsalutation.setOnClickListener { layout_opp_cntct_person.sp_opsalutation.performClick() }
        layout_opp_cntct_person.sp_opsalutation.onItemSelectedListener = this
        layout_opp_comy_details.et_cust_segmnt.setOnClickListener { layout_opp_comy_details.sp_opcustseg.performClick() }
        layout_opp_comy_details.sp_opcustseg.onItemSelectedListener = this
        layout_opp_comy_details.et_firm_type.setOnClickListener { layout_opp_comy_details.sp_opfrmtype.performClick() }
        layout_opp_comy_details.sp_opfrmtype.onItemSelectedListener = this
        layout_opp_comy_details.et_op_industype.setOnClickListener { layout_opp_comy_details.sp_opindusty.performClick() }
        layout_opp_comy_details.sp_opindusty.onItemSelectedListener = this
        layout_opp_cntct_person.et_op_state.setOnClickListener { layout_opp_cntct_person.sp_opstate.performClick() }
        layout_opp_cntct_person.sp_opstate.onItemSelectedListener = this
        layout_opp_cntct_person.et_city.setOnClickListener { layout_opp_cntct_person.sp_opcity.performClick() }
        layout_opp_cntct_person.sp_opcity.onItemSelectedListener = this
        layout_opp_cntct_person.et_area.setOnClickListener { layout_opp_cntct_person.sp_oparea.performClick() }
        layout_opp_cntct_person.sp_oparea.onItemSelectedListener = this
        layout_other.et_media.setOnClickListener { layout_other.sp_opmedia.performClick() }
        layout_other.sp_opmedia.onItemSelectedListener = this
        layout_other.et_ext_service.setOnClickListener { layout_other.sp_opexservice.performClick() }
        layout_other.sp_opexservice.onItemSelectedListener = this
        layout_other.et_firewl.setOnClickListener { layout_other.sp_opfirewal.performClick() }
        layout_other.sp_opfirewal.onItemSelectedListener = this
        layout_other.et_redundancy_required.setOnClickListener { layout_other.sp_opredundancy.performClick() }
        layout_other.sp_opredundancy.onItemSelectedListener = this
        layout_other.etselfpo.setOnClickListener { layout_other.sp_opselfpo.performClick() }
        layout_other.sp_opselfpo.onItemSelectedListener = this
        binding.layoutProductLine.etPriceList.setOnClickListener {  binding.layoutProductLine.spPrice.performClick() }
        binding.layoutProductLine.spPrice.onItemSelectedListener = this
        layout_product_line.et_product_list.setOnClickListener {  layout_product_line.sp_opproduct.performClick() }
        layout_product_line.sp_opproduct.onItemSelectedListener = this
        binding.layoutOppComyDetails.etVertical.setOnClickListener { binding.layoutOppComyDetails.spVertical.performClick() }
        binding.layoutOppComyDetails.spVertical.onItemSelectedListener = this
        binding.layoutLeadSdQuestionare.etCityRequired.setOnClickListener { binding.layoutLeadSdQuestionare.spCityRequired.performClick() }
        binding.layoutLeadSdQuestionare.spCityRequired.onItemSelectedListener = this

        binding.layoutLeadSdQuestionare.etCustomerUsingIllServices.setOnClickListener { binding.layoutLeadSdQuestionare.spCustomerUsingIllServices.performClick() }
        binding.layoutLeadSdQuestionare.spCustomerUsingIllServices.onItemSelectedListener = this

        binding.layoutLeadSdQuestionare.etBroadServices.setOnClickListener { binding.layoutLeadSdQuestionare.spBroadServices.performClick() }
        binding.layoutLeadSdQuestionare.spBroadServices.onItemSelectedListener = this

        binding.layoutLeadSdQuestionare.etLinksManged.setOnClickListener { binding.layoutLeadSdQuestionare.spLinksManged.performClick() }
        binding.layoutLeadSdQuestionare.spLinksManged.onItemSelectedListener = this

        binding.layoutLeadSdQuestionare.etRoutingServices.setOnClickListener { binding.layoutLeadSdQuestionare.spRoutingServices.performClick() }
        binding.layoutLeadSdQuestionare.spRoutingServices.onItemSelectedListener = this

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

        binding.layoutOppComyDetails.etTypeOrder.setOnClickListener { binding.layoutOppComyDetails.spTypeOrder.performClick() }
        binding.layoutOppComyDetails.spTypeOrder.onItemSelectedListener = this

        binding.layoutOppComyDetails.etTurnOver.setOnClickListener { binding.layoutOppComyDetails.spTurnOver.performClick() }
        binding.layoutOppComyDetails.spTurnOver.onItemSelectedListener = this

    }

    private fun save(){
        Toast.makeText(this,"Please Wait...",Toast.LENGTH_LONG).show()
        val topic = opp_contact_info_row.et_op_toptic.text.toString()
        val oppId =opp_contact_info_row.et_oppurtunity_ID.text.toString()
        val salutation = strSalutation.toString()
        val mobile = layout_opp_cntct_person.et_mob_num.text.toString()
        val media = str_media.toString()
        val industry = str_industry_type.toString()
        val noOfUser = layout_opp_comy_details.et_num_Users.text.toString()
        val POCC= layout_opp_comy_details.etPoc.text.toString()
        val noOfBeds = layout_opp_comy_details.et_num_beds.text.toString()
        val floor = layout_opp_cntct_person.et_floor.text.toString()
        val firmtype = str_firm_type.toString()
        val extprovider = str_serv_pro.toString()
        val email = layout_opp_cntct_person.et_eml_id.text.toString()
        val city = str_city_code.toString()
        val area = str_inst_area.toString()
        val building = str_inst_building_nm.toString()
        val pincode = layout_opp_cntct_person.et_op_pincode.text.toString()
        val buildingnumber = layout_opp_cntct_person.et_build_num.text.toString()
        val specificArea = layout_opp_cntct_person.et_oppspecific_area.text.toString()
        val specificBuilding = layout_opp_cntct_person.et_oppspecfc_bldng.text.toString()
        val contactperson = layout_opp_cntct_person.et_contc_prsn.text.toString()
        val customersegment = str_customer_segmentid.toString()
        val uptmsla = layout_other.et_uptime_sla.text.toString()
        val frwal = str_cust_frwl.toString()
        val comanyself = str_cmpnyself.toString()
        val polock = layout_other.et_polock.text.toString()
        val firewallAwc = layout_other.et_frwalaws.text.toString()
        val poNext = layout_other.et_oprenewal.text.toString()
        val redundancy = str_redundancy.toString()
        val state = strcontact_stateCode.toString()
        val price = str_price.toString()
        val reason = layout_product_line.et_op_reason.text.toString()
        val location = binding.layoutLeadSdQuestionare.etNoOfLocation.text.toString()
        val mentionNum = binding.layoutLeadSdQuestionare.etMentionNum.text.toString()
        val links = binding.layoutLeadSdQuestionare.etLinks.text.toString()
        val itSpent = binding.layoutLeadSdQuestionare.etItSpent.text.toString()
        val questionareRemark = binding.layoutLeadSdQuestionare.etRemarkk.text.toString()
       val Type = binding.layoutOppComyDetails.etTypeOrder.text.toString()
        if(topic.isBlank()){
            Toast.makeText(this@OpportunityActivity, "Please enter Topic", Toast.LENGTH_SHORT).show()
        }else if(salutation.isBlank()||salutation=="Select Salutation"){
            Toast.makeText(this@OpportunityActivity, "Please enter Salutation", Toast.LENGTH_SHORT).show()
        }else if(contactperson.isBlank()){
            Toast.makeText(this@OpportunityActivity, "Please enter Contact Person", Toast.LENGTH_SHORT).show()
        }else if(email.isBlank()||(!validEmail(email))){
            Toast.makeText(this@OpportunityActivity, "Please enter Email Id", Toast.LENGTH_SHORT).show()
        }else if(mobile.isBlank()|| mobile.length!=10){
            Toast.makeText(this@OpportunityActivity, "Please enter Mobile Number", Toast.LENGTH_SHORT).show()
        }else if(state.isBlank()||state=="Select State"||(state=="null")){
            Toast.makeText(this@OpportunityActivity, "Please enter State", Toast.LENGTH_SHORT).show()
        }else if(city.isBlank()|| city=="Select City"||(city=="null")){
            Toast.makeText(this@OpportunityActivity, "Please enter City", Toast.LENGTH_SHORT).show()
        }else if(area.isBlank()||area=="Select Area"||(area=="null")){
            Toast.makeText(this@OpportunityActivity, "Please enter Area", Toast.LENGTH_SHORT).show()
        }else if(specificArea.isEmpty()&&areaname=="Other"){
            Toast.makeText(this@OpportunityActivity, "Please enter Specific Area", Toast.LENGTH_SHORT).show()
        }else if(building.isBlank()||building=="Select Building"||(building=="null")){
            Toast.makeText(this@OpportunityActivity, "Please enter Building", Toast.LENGTH_SHORT).show()
        }else if(specificBuilding.isEmpty()&&buildingname=="Other"){
            Toast.makeText(this@OpportunityActivity, "Please enter Specific Building", Toast.LENGTH_SHORT).show()
        }else if(buildingnumber.isBlank()||(buildingnumber=="null")){
            Toast.makeText(this@OpportunityActivity, "Please enter Building Number", Toast.LENGTH_SHORT).show()
        }else if(floor.isBlank()){
            Toast.makeText(this@OpportunityActivity, "Please enter Floor", Toast.LENGTH_SHORT).show()
        }else if(pincode.isBlank()|| pincode.length!=6){
            Toast.makeText(this@OpportunityActivity, "Please enter PinCode", Toast.LENGTH_SHORT).show()
        }else if(firmtype.isBlank()||firmtype=="Select FirmType"||(firmtype=="null")){
            Toast.makeText(this@OpportunityActivity, "Please enter FirmType", Toast.LENGTH_SHORT).show()
        }else if(industry.isBlank()||industry=="Select Industry Type"||(industry=="null")){
            Toast.makeText(this@OpportunityActivity, "Please enter Industry", Toast.LENGTH_SHORT).show()
        }else if((productseg=="Managed Wi-Fi Business")&&(strIndustry=="Co-Living")&&(noOfBeds.isBlank())){
            Toast.makeText(this@OpportunityActivity, "Please enter No. of Beds", Toast.LENGTH_SHORT).show()
        }else if((productseg=="Managed Wi-Fi Business")&&(strIndustry=="Co-Working")&&(noOfUser.isBlank())){
            Toast.makeText(this@OpportunityActivity, "Please enter No. of User", Toast.LENGTH_SHORT).show()
        }else if(customersegment.isBlank()||customersegment=="Select Customer Segment"||(customersegment=="null")){
            Toast.makeText(this@OpportunityActivity, "Please enter Customer Segment", Toast.LENGTH_SHORT).show()
        }else if((strturnOver?.isBlank()==true)&&(subBusSegment!="SDWAN")||strturnOver=="0"){
            Toast.makeText(this, "Please Select TurnOver", Toast.LENGTH_SHORT).show()
        } else if(uptmsla.isBlank()){
            Toast.makeText(this@OpportunityActivity, "Please enter Uptime SLA", Toast.LENGTH_SHORT).show()
        }else if(extprovider.isBlank()||extprovider=="Select Ex Provider"||(extprovider=="null")){
            Toast.makeText(this@OpportunityActivity, "Please enter Service Provider", Toast.LENGTH_SHORT).show()
        }else if(frwal.isBlank()||frwal=="Select Option"||(frwal=="null")){
            Toast.makeText(this@OpportunityActivity, "Please enter FireWall", Toast.LENGTH_SHORT).show()
        }else if(firewallAwc.isEmpty()&&frwal=="1"){
            Toast.makeText(this@OpportunityActivity, "Please enter FireWall AMC Expiry date", Toast.LENGTH_SHORT).show()
        }else if(comanyself.isBlank()||comanyself=="Select Option"||(comanyself=="null")){
            Toast.makeText(this@OpportunityActivity, "Please enter Company Self", Toast.LENGTH_SHORT).show()
        }else if(polock.isBlank()||(polock=="null")){
            Toast.makeText(this@OpportunityActivity, "Please enter PoLock", Toast.LENGTH_SHORT).show()
        }else if(poNext.isBlank()||(poNext=="null")){
            Toast.makeText(this@OpportunityActivity, "Please enter Po Next", Toast.LENGTH_SHORT).show()
        }else if(strPrice.isBlank()||strPrice=="Select Price List"||(strPrice=="null")){
            Toast.makeText(this@OpportunityActivity, "Please enter Price ", Toast.LENGTH_SHORT).show()
        }else if(isdiscount&&reason.isBlank()&&subBusSegment!=AppConstants.SDWAN){
            Toast.makeText(this@OpportunityActivity, "Please enter Reason ", Toast.LENGTH_SHORT).show()
        }else if(strApprovalStatus=="Yes"&& reason.isBlank()&&strStatus=="In Progress"){
            Toast.makeText(this@OpportunityActivity, "Please enter Reason ", Toast.LENGTH_SHORT).show()
        }else if(subBusSegment=="SDWAN" && strType?.isBlank()==true||strType=="0"){
            Toast.makeText(this, "Please Enter the Type Of Order",Toast.LENGTH_SHORT).show()
        }else if(subBusSegment==AppConstants.SDWAN && strType== "122050000"&&POCC.isBlank()) {
            Toast.makeText(this, "Please Select the POC", Toast.LENGTH_SHORT).show()
        }else if(subBusSegment==AppConstants.SDWAN &&location.isBlank()||location=="null"){
            Toast.makeText(this, "Please Enter the Location", Toast.LENGTH_SHORT).show()
        }else if(subBusSegment==AppConstants.SDWAN && strIllServices?.isBlank() == true ||strIllServices=="null"||strIllServices=="0"){
            Toast.makeText(this, "Please Select Customer Using ILL Services", Toast.LENGTH_SHORT).show()
        }else if((strIllServices=="122050000") && mentionNum.isBlank()){
            Toast.makeText(this, "Please Enter Mention Number of Links", Toast.LENGTH_SHORT).show()
        }else if(subBusSegment==AppConstants.SDWAN && (strBroadServices?.isBlank() == true) ||strBroadServices=="null"||strBroadServices=="0"){
            Toast.makeText(this, "Please Enter the BroadBand Services", Toast.LENGTH_SHORT).show()
        }else if(links.isBlank() && (strBroadServices=="122050000")){
            Toast.makeText(this, "Please Enter the NO. of Links", Toast.LENGTH_SHORT).show()
        }else if(subBusSegment==AppConstants.SDWAN && (strLinksManaged?.isBlank()== true)||strLinksManaged=="null"||strLinksManaged=="0"){
            Toast.makeText(this, "Please Enter the Managed Links ", Toast.LENGTH_SHORT).show()
        }else if(subBusSegment==AppConstants.SDWAN && (strRoutingServices?.isBlank()== true)||strRoutingServices=="null"||strRoutingServices=="0"){
            Toast.makeText(this, "Please Enter the Routing Services", Toast.LENGTH_SHORT).show()
        }else if(subBusSegment==AppConstants.SDWAN && (firesSet?.isBlank()== true)||firesSet=="null"){
            Toast.makeText(this, "Please Select Firewall set tpo expire", Toast.LENGTH_SHORT).show()
        } else if(subBusSegment==AppConstants.SDWAN && (strCityReqd?.isBlank()== true)||strCityReqd=="null"||strCityReqd=="Select Option"){
            Toast.makeText(this, "Please Enter the City Required", Toast.LENGTH_SHORT).show()
        } else if(subBusSegment==AppConstants.SDWAN && (strNetworkSecurity?.isBlank()== true)||strNetworkSecurity=="null"||strNetworkSecurity=="0"){
            Toast.makeText(this, "Please Enter the Network Security", Toast.LENGTH_SHORT).show()
        }else if(subBusSegment==AppConstants.SDWAN && (strHosted?.isBlank()== true)||strHosted=="null"||strHosted=="0"){
            Toast.makeText(this, "Please Enter the Hosted", Toast.LENGTH_SHORT).show()
        }else if(subBusSegment==AppConstants.SDWAN && (strCustomer?.isBlank()== true)||strCustomer=="null"||strCustomer=="0"){
            Toast.makeText(this, "Please Enter the Customer", Toast.LENGTH_SHORT).show()
        }else if(subBusSegment==AppConstants.SDWAN && (strContract?.isBlank()== true)||strContract=="null"||strContract=="0"){
            Toast.makeText(this, "Please Enter the Contract", Toast.LENGTH_SHORT).show()
        }
        else if(subBusSegment==AppConstants.SDWAN &&(strBackBone?.isBlank()== true)||strBackBone=="null"||strBackBone=="0"){
            Toast.makeText(this, "Please Enter the Back Bone", Toast.LENGTH_SHORT).show()
        }else if(subBusSegment==AppConstants.SDWAN &&(mpls?.isBlank()== true)||mpls=="null"){
            Toast.makeText(this, "Please Enter the Mpls", Toast.LENGTH_SHORT).show()
        } else {
                updateOppurtunity(topic,oppId,salutation,mobile,media,industry,floor,firmtype,extprovider,email,
                city,area,building,pincode,buildingnumber,contactperson,customersegment,uptmsla,frwal,comanyself,
                redundancy,state,price,reason,specificArea,specificBuilding,noOfUser,noOfBeds,location,
                mentionNum,links,itSpent,questionareRemark)
        }
    }

    private fun validEmail(email: String): Boolean {
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }

    fun  getGenerateQuote () {

        //inProgress()
        val getProductListRequest = strOppId?.let { GetProductListRequest(Constants.GET_QUOTE, Constants.AUTH_KEY, it,password,userName,"","") }
        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getQuote(getProductListRequest)
        call.enqueue(object : Callback<GenQuoteResponse?> {
            override fun onResponse(call: Call<GenQuoteResponse?>, response: Response<GenQuoteResponse?>) {
               // OutProgress()
                if (response.isSuccessful && response.body() != null) {
                    try {
                        if(response.body()?.Response?.StatusCode==200) {
                            generatequote = response.body()?.Response?.Data
                            setQuoteAdapter(generatequote!!, this@OpportunityActivity)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<GenQuoteResponse?>, t: Throwable) {
              //  binding.opprogressLayout.progressOverlay.visibility=View.GONE
                Log.e("RetroError", t.toString())
            }
        })
    }

    fun  getAllSites () {

        //inProgress()
        val getAllSiteReq = strOppId?.let { GetAllSiteReq(Constants.GETALL_SITE_DETAILS, Constants.AUTH_KEY,it,password,userName) }
        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getAllSiteDetails(getAllSiteReq)
        call.enqueue(object : Callback<GetAllSiteRes?> {
            override fun onResponse(call: Call<GetAllSiteRes?>, response: Response<GetAllSiteRes?>) {
                // OutProgress()
                if (response.isSuccessful && response.body() != null) {
                    try {
                        if(response.body()?.Response?.StatusCode==200) {
                            //  val msg = response.body()!!.Response.Message
                            allSiteList = response.body()?.Response?.Data
                            if(allSiteList!=null) {
                                oppSale.add_preTask.visibility=View.VISIBLE
                                Log.e("25","Visible")
                                if(strStatus=="Lost"||strStatus=="Won"){
                                    oppSale.add_preTask.visibility=View.GONE
                                }
                                binding.oppSite.rvAddSite.visibility=View.VISIBLE
                                setAllSiteListAdapter(allSiteList!!, this@OpportunityActivity)
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<GetAllSiteRes?>, t: Throwable) {
                //  binding.opprogressLayout.progressOverlay.visibility=View.GONE
                Log.e("RetroError", t.toString())
            }
        })
    }

    private fun checkPermission(): Boolean {
        // checking of permissions.
        val permission1 =
            ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val permission2 =
            ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.READ_EXTERNAL_STORAGE)
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        // requesting permissions if not provided.
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE),
            AppConstants.PERMISSION_REQUEST_CODE
        )
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == AppConstants.PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty()) {
                val writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED
                val readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED
                if (writeStorage && readStorage) {
                    //  Toast.makeText(this, "Permission Granted..", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission Denied.", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }

    fun getAllfeasList () {
       // inProgress()
        val getProductListRequest = strOppId?.let { GetProductListRequest(Constants.GET_FEASIBILITY, Constants.AUTH_KEY, it,password,userName,"","") }
        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getAllFeasibility(getProductListRequest)
        call.enqueue(object : Callback<GetFeasibiltyResponse?> {
            override fun onResponse(call: Call<GetFeasibiltyResponse?>, response: Response<GetFeasibiltyResponse?>) {
             //   OutProgress()
                if (response.isSuccessful && response.body() != null) {

                    try {
                        if(response.body()?.Response?.StatusCode==200) {
                           // val msg = response.body()!!.Response.Message
                             allFeasibility = response.body()?.Response?.Data
                             fesstatus = allFeasibility?.get(0)?.FeasibilityStatus
                            if(allFeasibility?.size==1){
                                layout_other.et_redundancy_required.isEnabled= false
                                fesstatus = allFeasibility?.get(0)?.FeasibilityStatus
                                if(fesbilty=="1") {
                                    if(strOperationCity=="true"){
                                        if(strArea == "Other" && strBuilding == "Other"){
                                            Toast.makeText(this@OpportunityActivity, "For DOA submission you need to add Area or Building as they are still showing as Other", Toast.LENGTH_SHORT).show()
                                        }
                                    }else if(strOperationCity=="false"){
                                        if ((strArea == "Other") && (strBuilding == "Other")
                                            && (strCreateAreaOrBuilding=="false")/* && strTPFeasibilty.isNotBlank()*/){
                                            fab_create_society.visibility=View.VISIBLE
                                        }
                                    }
                                }
                            }else if(allFeasibility?.size==2) {
                                layout_other.et_redundancy_required.isEnabled= false
                                fesstatus = allFeasibility?.get(0)?.FeasibilityStatus
                                fesbilty = allFeasibility?.get(1)?.FeasibilityStatus
                                if(fesbilty=="1"&& fesbilty=="1") {
                                    if(strOperationCity=="true"){
                                        if(strArea == "Other" && strBuilding == "Other") {
                                            Toast.makeText(this@OpportunityActivity, "For DOA submission you need to add Area or Building as they are still showing as Other", Toast.LENGTH_SHORT).show()
                                        }
                                    }else if(strOperationCity=="false"){
                                        if ((strArea == "Other") && (strBuilding == "Other")
                                            && (strCreateAreaOrBuilding=="false")/* && strTPFeasibilty.isNotBlank()*/){
                                            fab_create_society.visibility=View.VISIBLE
                                            Log.e("ButtonFes", "1")
                                        }
                                    }
                                }
                            }
                            reas = layout_product_line.et_op_reason.text?.trim().toString()
                            if(allFeasibility?.size!=null){
                                binding.feasibility.addFes.visibility=View.GONE
                            }

                            if(strOperationCity=="Yes"){
                                if(fesstatus=="1"){
                                    if(strArea == "Other" && strBuilding == "Other") {
                                        Toast.makeText(this@OpportunityActivity, " For DOA submission you need to add Area or Building as they are still showing as Other", Toast.LENGTH_SHORT).show()
                                    } else{
                                        createApproval()
                                    }
                                }
                            }
                            setFesAdapter(allFeasibility, this@OpportunityActivity)

                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<GetFeasibiltyResponse?>, t: Throwable) {
               // binding.opprogressLayout.progressOverlay.visibility=View.GONE
                Log.e("RetroError", t.toString())
            }
        })
    }

    private fun setFesAdapter(allFeasibility: ArrayList<FeasData>?, context: Context?) {
        binding.feasibility.rvAddFes.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = allFeasibility?.let { GetAllFeasibiltyAdapter(it,context,productseg) }
        }
    }

    fun getPreSales () {
        //inProgress()
        val createPreTaskReq = strOppId?.let { CreatePreTaskReq(Constants.GET_PRETASK, Constants.AUTH_KEY, it,password,"",userName) }
        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getPreTaskList(createPreTaskReq)
        call.enqueue(object : Callback<GetPreSaleDetail?> {
            override fun onResponse(call: Call<GetPreSaleDetail?>, response: Response<GetPreSaleDetail?>) {
                // OutProgress()
                if (response.isSuccessful && response.body() != null) {
                    try {
                        val msg = response.body()?.Response?.Message
                        val status=response.body()?.Response?.StatusCode
                        if(response.body()?.Response?.StatusCode==200) {
                            allPreSales = response.body()?.Response?.Data

                            if(allPreSales?.size!=null) {
                                oppSale.rv_addTask.visibility=View.VISIBLE
                                setPreSalesAdapter(allPreSales, this@OpportunityActivity)
                            }
                        }else if(status==201)  {
                          // Toast.makeText(this@OpportunityActivity,msg,Toast.LENGTH_LONG).show()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<GetPreSaleDetail?>, t: Throwable) {
                //   binding.opprogressLayout.progressOverlay.visibility=View.GONE
                Log.e("RetroError", t.toString())
            }
        })
    }

    private fun setPreSalesAdapter(allSales: ArrayList<PreSaleData>?, context: Context?) {
        oppSale.rv_addTask.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = allSales?.let { GetPreSaleTaskAdapter(it,context) }
        }
    }

    fun getDOA () {
       //inProgress()
        val getProductListRequest = strOppId?.let { GetProductListRequest(Constants.GETAPPROVAL, Constants.AUTH_KEY, it,password,userName,"","") }
        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getApproval(getProductListRequest)
        call.enqueue(object : Callback<GetApprovalRersponse?> {
            override fun onResponse(call: Call<GetApprovalRersponse?>, response: Response<GetApprovalRersponse?>) {
             // OutProgress()
                if (response.isSuccessful && response.body() != null) {
                    try {
                        if(response.body()?.Response?.StatusCode==200) {
                            allApproval = response.body()?.Response?.Data
                            setDOAAdapter(allApproval, this@OpportunityActivity)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<GetApprovalRersponse?>, t: Throwable) {
             //   binding.opprogressLayout.progressOverlay.visibility=View.GONE
                Log.e("RetroError", t.toString())
            }
        })
    }

    private fun setDOAAdapter(allApproval: ArrayList<AppData>?, context: Context?) {
       rv_add_doa.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = allApproval?.let { GetDOAViewAdapter(it,context,strOppId) }
        }
    }

    private fun setQuoteAdapter(generatequote: ArrayList<QuoteData>, context: Context?) {
        binding.quote.rvAddQuote.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = strOppId?.let { GetAllQuoteAdapter(generatequote,context, it) }
        }
    }

    private fun setAllSiteListAdapter(siteList: ArrayList<SiteData>, context: Context?) {
        binding.oppSite.rvAddSite.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = strOppId?.let { GetAllSiteAdapter(siteList,context, it) }
        }
    }


    fun listener(){
        tv_opp_save.visibility = View.VISIBLE
        linearcontactinfo.visibility=View.VISIBLE
        lineartwo.setOnClickListener {
            linearcontactinfo.visibility = View.VISIBLE
            linear_insta_addres.visibility = View.GONE
            linadd.visibility = View.GONE
            linear_contact_person_address.visibility = View.GONE
            linear_companydetails.visibility= View.GONE
            linear_fesdetails.visibility=View.GONE
            linear_quotedetails.visibility= View.GONE
            linear_daodetails.visibility=View.GONE
            binding.linearquestionareDetails.visibility = View.GONE
            binding.linearSiteDetails.visibility = View.GONE
            binding.linearPreTaskDetails.visibility = View.GONE
        }
        linearthree.setOnClickListener {
            linearcontactinfo.visibility = View.GONE
            linear_insta_addres.visibility = View.VISIBLE
            linadd.visibility = View.GONE
            linear_contact_person_address.visibility = View.GONE
            linear_companydetails.visibility= View.GONE
            linear_fesdetails.visibility=View.GONE
            linear_quotedetails.visibility= View.GONE
            linear_daodetails.visibility=View.GONE
            binding.linearquestionareDetails.visibility = View.GONE
            binding.linearSiteDetails.visibility = View.GONE
            binding.linearPreTaskDetails.visibility = View.GONE
        }
        linearfouraddres.setOnClickListener {
            linearcontactinfo.visibility = View.GONE
            linear_insta_addres.visibility = View.GONE
            linadd.visibility = View.VISIBLE
            linear_contact_person_address.visibility = View.GONE
            linear_companydetails.visibility= View.GONE
            linear_fesdetails.visibility=View.GONE
            linear_quotedetails.visibility= View.GONE
            linear_daodetails.visibility=View.GONE
            binding.linearquestionareDetails.visibility = View.GONE
            binding.linearSiteDetails.visibility = View.GONE
            binding.linearPreTaskDetails.visibility = View.GONE
        }
        linearfive.setOnClickListener {
            linearcontactinfo.visibility = View.GONE
            linear_insta_addres.visibility = View.GONE
            linadd.visibility = View.GONE
            linear_contact_person_address.visibility = View.VISIBLE
            linear_companydetails.visibility= View.GONE
            linear_fesdetails.visibility=View.GONE
            linear_quotedetails.visibility= View.GONE
            linear_daodetails.visibility=View.GONE
            binding.linearquestionareDetails.visibility = View.GONE
            binding.linearSiteDetails.visibility = View.GONE
            binding.linearPreTaskDetails.visibility = View.GONE
        }
        linearsix.setOnClickListener {
            linearcontactinfo.visibility = View.GONE
            linear_insta_addres.visibility = View.GONE
            linadd.visibility = View.GONE
            linear_contact_person_address.visibility = View.GONE
            linear_companydetails.visibility= View.VISIBLE
            linear_fesdetails.visibility=View.GONE
            linear_quotedetails.visibility= View.GONE
            linear_daodetails.visibility=View.GONE
            binding.linearquestionareDetails.visibility = View.GONE
            binding.linearSiteDetails.visibility = View.GONE
            binding.linearPreTaskDetails.visibility = View.GONE
        }

        linearfes.setOnClickListener {
            linearcontactinfo.visibility = View.GONE
            linear_insta_addres.visibility = View.GONE
            linadd.visibility = View.GONE
            linear_contact_person_address.visibility = View.GONE
            linear_companydetails.visibility= View.GONE
            linear_quotedetails.visibility= View.GONE
            linear_fesdetails.visibility=View.VISIBLE
            linear_daodetails.visibility=View.GONE
            binding.linearquestionareDetails.visibility = View.GONE
            binding.linearSiteDetails.visibility = View.GONE
            binding.linearPreTaskDetails.visibility = View.GONE
        }
        linearequote.setOnClickListener {
            linearcontactinfo.visibility = View.GONE
            linear_insta_addres.visibility = View.GONE
            linadd.visibility = View.GONE
            linear_contact_person_address.visibility = View.GONE
            linear_companydetails.visibility= View.GONE
            binding.linearQuotedetails.visibility=View.VISIBLE
            linear_fesdetails.visibility=View.GONE
            linear_daodetails.visibility=View.GONE
            binding.linearquestionareDetails.visibility = View.GONE
            binding.linearSiteDetails.visibility = View.GONE
            binding.linearPreTaskDetails.visibility = View.GONE
        }
        lineardao.setOnClickListener {
            linearcontactinfo.visibility = View.GONE
            linear_insta_addres.visibility = View.GONE
            linadd.visibility = View.GONE
            linear_contact_person_address.visibility = View.GONE
            linear_companydetails.visibility= View.GONE
            binding.linearQuotedetails.visibility=View.GONE
            linear_fesdetails.visibility=View.GONE
            linear_daodetails.visibility=View.VISIBLE
            binding.linearquestionareDetails.visibility = View.GONE
            binding.linearSiteDetails.visibility = View.GONE
            binding.linearPreTaskDetails.visibility = View.GONE
        }
        binding.linearqustionare.setOnClickListener {
            linearcontactinfo.visibility = View.GONE
            linear_insta_addres.visibility = View.GONE
            linadd.visibility = View.GONE
            linear_contact_person_address.visibility = View.GONE
            linear_companydetails.visibility= View.GONE
            binding.linearQuotedetails.visibility=View.GONE
            linear_fesdetails.visibility=View.GONE
            linear_daodetails.visibility=View.GONE
            binding.linearquestionareDetails.visibility = View.VISIBLE
            binding.linearSiteDetails.visibility = View.GONE
            binding.linearPreTaskDetails.visibility = View.GONE
        }
        binding.linearSite.setOnClickListener {
            linearcontactinfo.visibility = View.GONE
            linear_insta_addres.visibility = View.GONE
            linadd.visibility = View.GONE
            linear_contact_person_address.visibility = View.GONE
            linear_companydetails.visibility= View.GONE
            binding.linearQuotedetails.visibility=View.GONE
            linear_fesdetails.visibility=View.GONE
            linear_daodetails.visibility=View.GONE
            binding.linearquestionareDetails.visibility = View.GONE
            binding.linearSiteDetails.visibility = View.VISIBLE
            binding.linearPreTaskDetails.visibility = View.GONE
        }
        binding.linearPreTask.setOnClickListener {
            linearcontactinfo.visibility = View.GONE
            linear_insta_addres.visibility = View.GONE
            linadd.visibility = View.GONE
            linear_contact_person_address.visibility = View.GONE
            linear_companydetails.visibility= View.GONE
            binding.linearQuotedetails.visibility=View.GONE
            linear_fesdetails.visibility=View.GONE
            linear_daodetails.visibility=View.GONE
            binding.linearquestionareDetails.visibility = View.GONE
            binding.linearSiteDetails.visibility = View.GONE
            binding.linearPreTaskDetails.visibility = View.VISIBLE
        }

        layout_opp_cntct_person.et_eml_id.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus)
            {
                val email =  layout_opp_cntct_person.et_eml_id.text.toString()
                val isValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
                if (!isValid) {
                    Toast.makeText(this, "Invalid Email", Toast.LENGTH_LONG).show()
                }
            }
        }
        layout_opp_cntct_person.et_mob_num.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus)
            {
                val mobile = layout_opp_cntct_person.et_mob_num.text.toString()
                val isValid = Patterns.PHONE.matcher(mobile).matches()
                if (!isValid) {
                    Toast.makeText(this, "Invalid Mobile Number", Toast.LENGTH_LONG).show()
                }
            }
        }




    }

    override fun onClick(p0: View?) {
        if (p0?.id == R.id.rl_back) {
            next()
        }
    }




    private fun updateOppurtunity(
        topic: String, oppId: String, salutation: String, mobile: String, media: String,
        industry: String, floor: String, firmtype: String, extprovider: String, email: String,
        city: String, area: String, building: String, pincode: String, buildingnumber: String,
        contactperson: String, customersegment: String, uptmsla: String, frwal: String,
        comanyself: String, redundancy: String, state: String, price: String, reason: String,
        specificArea: String, specficBuilding: String, noOfUser: String?, numBeds: String, location: String,
        mentionNum: String,
        links: String,
        itSpent: String,
        questionareRemark: String
    ) {
        val sdwanOpp = SDWANOpp(location,strIllServices,mentionNum,strBroadServices,
            links,strLinksManaged,strRoutingServices,firesSet,
            itSpent,strCityReqd,strNetworkSecurity,strHosted,strCustomer,
            strContract,strBackBone,mpls,questionareRemark)

          val updateOppurtunity = UpdateOppurtunity(Constants.UPDATE_OPPURTUNITY,Constants.AUTH_KEY,
              area,buildingnumber,building,city,comanyself,contactperson,AppConstants.COUNTRY_CODE,customersegment,
              email,extprovider,frwal,frwaws,firmtype,floor,industry,"",media,mobile,oppId, password,
              polockdate,renewal,pincode,price,redundancy,salutation,state,topic.trim(),uptmsla,userName,
              reason, str_createbuilding,specificArea,specficBuilding,noOfUser,numBeds,strType,Poc,strturnOver,sdwanOpp)

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.updateOppurtunity(updateOppurtunity)
        call.enqueue(object : Callback<CreateLeadResponse?> {
            override fun onResponse(call: Call<CreateLeadResponse?>, response: Response<CreateLeadResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        val img = response.body()?.Response?.Message
                        img?.let { Log.e("image", it) }
                        Toast.makeText(this@OpportunityActivity, img, Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@OpportunityActivity, OpportunityActivity::class.java)
                        val bundle = Bundle()
                        bundle.putString("OppId",strOppId )
                        intent.putExtras(bundle)
                        startActivity(intent)
                        finish()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<CreateLeadResponse?>, t: Throwable) {
                Log.e("RetroError", t.toString())
            }
        })
    }

    private fun addProduct() {
        //inProgress()
        val addProductRequest = str_product?.let {
            strOppId?.let { it1 -> AddProductRequest(Constants.CREATE_OPPPRODUCT,Constants.AUTH_KEY, it1,password,it,userName,"") } }

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.addProduct(addProductRequest)
        call.enqueue(object : Callback<ProdctResponse?> {
            override fun onResponse(call: Call<ProdctResponse?>, response: Response<ProdctResponse?>) {
                //OutProgress()
                if (response.body()?.Response?.StatusCode==200) {
                    try {
                      //  Toast.makeText(this@OpportunityActivity, msg, Toast.LENGTH_LONG).show()
                        val intent = Intent(this@OpportunityActivity, OpportunityActivity::class.java)
                        val bundle = Bundle()
                        bundle.putString("OppId",strOppId )
                        intent.putExtras(bundle)
                        startActivity(intent)
                        finish()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<ProdctResponse?>, t: Throwable) {
               // binding.opprogressLayout.progressOverlay.visibility=View.GONE
                Log.e("RetroError", t.toString())
            }
        })
    }


    private fun addFeasibility() {
        inProgress()
       val addProductRequest =
           strOppId?.let { it1 -> AddProductRequest(Constants.GENERATE_FEASIBILITY,Constants.AUTH_KEY, it1,password,"",userName,"")  }

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.addProduct(addProductRequest)
        call.enqueue(object : Callback<ProdctResponse?> {
            override fun onResponse(call: Call<ProdctResponse?>, response: Response<ProdctResponse?>) {
                outProgress()
                val msg = response.body()?.Response?.Message
                if (response.body()?.Response?.StatusCode==200) {
                    try {
                        Toast.makeText(this@OpportunityActivity, msg, Toast.LENGTH_LONG).show()
                        val intent = Intent(this@OpportunityActivity, OpportunityActivity::class.java)
                        val bundle = Bundle()
                        bundle.putString("OppId",strOppId )
                        intent.putExtras(bundle)
                        startActivity(intent)
                        finish()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
            override fun onFailure(call: Call<ProdctResponse?>, t: Throwable) {
             binding.opprogressLayout.progressOverlay.visibility=View.GONE
                Log.e("RetroError", t.toString())
            }
        })
    }



    private fun addQuote() {
      inProgress()
        val createQuoteRequest =
            strOppId?.let { it1 -> CreateQuoteRequest(Constants.CREATE_QUOTE,Constants.AUTH_KEY, it1,password,userName)  }

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.addQuote(createQuoteRequest)
        call.enqueue(object : Callback<ProdctResponse?> {
            override fun onResponse(call: Call<ProdctResponse?>, response: Response<ProdctResponse?>) {
                val msg = response.body()?.Response?.Message
               outProgress()
                if (response.body()?.Response?.StatusCode==200) {
                    try {
                        Toast.makeText(this@OpportunityActivity, msg, Toast.LENGTH_LONG).show()
                        val intent = Intent(this@OpportunityActivity, OpportunityActivity::class.java)
                        val bundle = Bundle()
                        bundle.putString("OppId",strOppId )
                        intent.putExtras(bundle)
                        startActivity(intent)
                        finish()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }else{
                    Toast.makeText(this@OpportunityActivity, msg, Toast.LENGTH_LONG).show()

                }
            }

            override fun onFailure(call: Call<ProdctResponse?>, t: Throwable) {
              binding.opprogressLayout.progressOverlay.visibility=View.GONE
                Log.e("RetroError", t.toString())
            }
        })
    }

    private fun createPreTask() {
        //  inProgress()
        val createPreTaskReq =
            strOppId?.let { it1 -> CreatePreTaskReq(Constants.CREATE_PRETASK,Constants.AUTH_KEY, it1,password,"",userName)  }

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.createPreTask(createPreTaskReq)
        call.enqueue(object : Callback<ProdctResponse?> {
            override fun onResponse(call: Call<ProdctResponse?>, response: Response<ProdctResponse?>) {
                val msg = response.body()?.Response?.Message
                //  OutProgress()
                if (response.body()?.Response?.StatusCode==200) {
                    try {
                        Toast.makeText(this@OpportunityActivity, msg, Toast.LENGTH_LONG).show()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }else{
                      Toast.makeText(this@OpportunityActivity, msg, Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ProdctResponse?>, t: Throwable) {
                Log.e("RetroError", t.toString())
            }
        })
    }


    fun createApproval() {
        val reason = binding.layoutProductLine.etOpReason.text.toString()
        val getProductListRequest =
            strOppId?.let { it1 -> GetProductListRequest(Constants.CREATEAPPROVAL,Constants.AUTH_KEY, it1,password,userName,"",reason)  }

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.won(getProductListRequest)
        call.enqueue(object : Callback<ProdctResponse?> {
            override fun onResponse(call: Call<ProdctResponse?>, response: Response<ProdctResponse?>) {
                val msg = response.body()?.Response?.Message
                if (response.body()?.Response?.StatusCode==200) {
                    try {
                        Toast.makeText(this@OpportunityActivity, msg, Toast.LENGTH_LONG).show()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<ProdctResponse?>, t: Throwable) {
                Log.e("RetroError", t.toString())
            }
        })
    }

    private fun wonOpp() {
        inProgress()
        val getProductListRequest =
            strOppId?.let { it1 -> GetProductListRequest(Constants.WON_OPPURTUNITY,Constants.AUTH_KEY, it1,password,userName,"","")  }

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.won(getProductListRequest)
        call.enqueue(object : Callback<ProdctResponse?> {
            override fun onResponse(call: Call<ProdctResponse?>, response: Response<ProdctResponse?>) {
                val msg = response.body()?.Response?.Message
                outProgress()
                if (response.body()?.Response?.StatusCode==200) {
                    try {
                        Toast.makeText(this@OpportunityActivity, msg, Toast.LENGTH_LONG).show()
                        val intent = Intent(this@OpportunityActivity, OpportunityActivity::class.java)
                        intent.putExtra("OppId",strOppId)
                        startActivity(intent)
                        finish()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }else{
                    Toast.makeText(this@OpportunityActivity, msg, Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ProdctResponse?>, t: Throwable) {
                    binding.opprogressLayout.progressOverlay.visibility=View.GONE
                Log.e("RetroError", t.toString())
            }
        })
    }

    private fun reOpen() {
        inProgress()
        val getOppurtunityRequest =
            strOppId?.let { it1 -> GetOppurtunityRequest(Constants.REOPEN_OPP,Constants.AUTH_KEY, it1,password,userName,"")  }

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.reOpenOpp(getOppurtunityRequest)
        call.enqueue(object : Callback<ProdctResponse?> {
            override fun onResponse(call: Call<ProdctResponse?>, response: Response<ProdctResponse?>) {
                val msg = response.body()?.Response?.Message
                outProgress()
                if (response.body()?.Response?.StatusCode==200) {
                    try {
                        Toast.makeText(this@OpportunityActivity, msg, Toast.LENGTH_LONG).show()
                           val intent = Intent(this@OpportunityActivity, OpportunityActivity::class.java)
                        val bundle = Bundle()
                        bundle.putString("OppId",strOppId )
                        intent.putExtras(bundle)
                        startActivity(intent)
                        finish()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }else{
                    Toast.makeText(this@OpportunityActivity, msg, Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ProdctResponse?>, t: Throwable) {
                binding.opprogressLayout.progressOverlay.visibility=View.GONE
                Log.e("RetroError", t.toString())
            }
        })
    }

    private fun submitApproval() {
        Toast.makeText(this@OpportunityActivity, "Please wait...", Toast.LENGTH_LONG).show()
        val reason = binding.layoutProductLine.etOpReason.text.toString()
        Log.e("Reason",reason)
        Log.e("Re",strReason.toString())
        val getProductListRequest =
            strOppId?.let { it1 -> GetProductListRequest(Constants.CREATEAPPROVAL,Constants.AUTH_KEY, it1,password,userName,"",reason)  }

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.won(getProductListRequest)
        call.enqueue(object : Callback<ProdctResponse?> {
            override fun onResponse(call: Call<ProdctResponse?>, response: Response<ProdctResponse?>) {
                val msg = response.body()?.Response?.Message
                if (response.body()?.Response?.StatusCode==200) {
                    try {
                        Toast.makeText(this@OpportunityActivity, msg, Toast.LENGTH_LONG).show()
                        val intent = Intent(this@OpportunityActivity, OpportunityActivity::class.java)
                        val bundle = Bundle()
                        bundle.putString("OppId",strOppId)
                        intent.putExtras(bundle)
                        startActivity(intent)
                        finish()
                      //  caf()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }else  if (response.body()?.Response?.StatusCode==400) {
                    Toast.makeText(this@OpportunityActivity, msg, Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<ProdctResponse?>, t: Throwable) {
                Log.e("RetroError", t.toString())
            }
        })
    }

    fun inProgress(){
        inAnimation = AlphaAnimation(0f, 1f)
        inAnimation?.duration =200
        binding.opprogressLayout.progressOverlay.animation = inAnimation
        binding.opprogressLayout.progressOverlay.visibility = View.VISIBLE
    }

    fun outProgress(){
        outAnimation = AlphaAnimation(1f, 0f)
        outAnimation?.duration =200
        binding.opprogressLayout.progressOverlay.animation = outAnimation
        binding.opprogressLayout.progressOverlay.visibility = View.GONE
    }

    fun getProductAddedList() {
      //  inProgress()
        val getProductListRequest = GetProductListRequest(Constants.GET_OPPPRODUCT,Constants.AUTH_KEY,strOppId.toString(),password,userName,"","")

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.addProductItem(getProductListRequest)
        call.enqueue(object : Callback<GetProductItemListRes?> {
            override fun onResponse(call: Call<GetProductItemListRes?>, response: Response<GetProductItemListRes?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        if (response.body()?.Response?.StatusCode==200) {
                            try {
                                allProductItem = response.body()?.Response?.Data
                                if(allProductItem?.isNotEmpty()==true) {
                                    allProductItem?.forEachIndexed { _, itemData ->
                                        if(!itemData.Discount?.startsWith("0.0")!!) {
                                            isdiscount=true
                                            return@forEachIndexed
                                        }
                                        println("chk discount:"+itemData.Discount)
                                    }
                                    if(allProductItem?.size==null){
                                        Log.e("Button51", "51")
                                        binding.tvOppSubmit.visibility = View.GONE
                                        tv_won.visibility=View.VISIBLE
                                    }
                                    setAdapter(allProductItem, this@OpportunityActivity)
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
            override fun onFailure(call: Call<GetProductItemListRes?>, t: Throwable) {
              //  binding.opprogressLayout.progressOverlay.visibility=View.GONE

                Log.e("RetroError", t.toString())
            }
        })
    }



    private fun setAdapter(allProductItem: ArrayList<ItemData>?, context: Context?) {
        rv_product_list?.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = allProductItem?.let { GetAllProductItemAdapter(it,context,strOppId,strOppId,status) }
        }
    }

    private fun lostOppurtunity(loststatus: String) {
        inProgress()
        val lostOppurtunityRequest = LostOppurtunityRequest(Constants.LOST_OPPURTUNITY,Constants.AUTH_KEY,strOppId.toString(),password,loststatus,userName)

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.lostOppurtunityRequest(lostOppurtunityRequest)
        call.enqueue(object : Callback<CommonClassResponse?> {
            override fun onResponse(call: Call<CommonClassResponse?>, response: Response<CommonClassResponse?>) {
                outProgress()
                if (response.isSuccessful && response.body() != null) {
                    try {
                        val msg = response.body()?.response?.message
                        Toast.makeText(this@OpportunityActivity, msg, Toast.LENGTH_LONG).show()
                        val intent = Intent(this@OpportunityActivity, OppTabActivity::class.java)
                        startActivity(intent)
                        finish()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<CommonClassResponse?>, t: Throwable) {
                binding.opprogressLayout.progressOverlay.visibility=View.GONE
                Log.e("RetroError", t.toString())
            }
        })
    }

    private fun getOpportunity () {
        inProgress()
        val getOppurtunityRequest = GetOppurtunityRequest(Constants.GET_OPPURTUNITY,Constants.AUTH_KEY,strOppId,password,userName,strLeadId)

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getOppurtunity(getOppurtunityRequest)
        call.enqueue(object : Callback<GetOppurtunityResponse?> {
            override fun onResponse(call: Call<GetOppurtunityResponse?>, response: Response<GetOppurtunityResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                          outProgress()
                        val msg = response.body()?.Response?.Message
                         if(response.body()?.Response?.StatusCode==200) {
                            binding.oppContactInfoRow.oppDetail = response.body()?.Response
                            binding.layoutOppCntctPerson.contact = response.body()?.Response
                            binding.layoutOppComyDetails.company = response.body()?.Response
                            binding.layoutOther.other = response.body()?.Response
                            binding.layoutProductLine.etOpReason.setText(response.body()?.Response?.Data?.get(0)?.Reason.toString())
                            strArea = response.body()?.Response?.Data?.get(0)?.Area.toString()
                            strCity = response.body()?.Response?.Data?.get(0)?.City.toString()
                            val strContactstate = response.body()?.Response?.Data?.get(0)?.State
                            val stateId = response.body()?.Response?.Data?.get(0)?.StateId
                             strApprovalStatus = response.body()?.Response?.Data?.get(0)?.ApprovalRequiredFlag
                            getCity(stateId)

                            strBuilding = response.body()?.Response?.Data?.get(0)?.Buildingname.toString()
                            strIndustry = response.body()?.Response?.Data?.get(0)?.Industry.toString()
                            strPrice = response.body()?.Response?.Data?.get(0)?.PriceList.toString()
                            strStatus  = response.body()?.Response?.Data?.get(0)?.Status.toString()
                            strReason = response.body()?.Response?.Data?.get(0)?.Reason.toString()
                            strOppId = response.body()?.Response?.Data?.get(0)?.OppId.toString()

                            subBusSegment = response.body()?.Response?.Data?.get(0)?.Subbusinesssegment.toString()
                            binding.quote.addQuote.visibility=View.VISIBLE
                            if(subBusSegment==AppConstants.SDWAN) {
                                 CoroutineScope(Dispatchers.IO).launch {
                                     getVertcal()
                                     getRequiredCity()
                                     getPreSales()
                                 }
                                 layout_other.sp_opredundancy.isEnabled=false
                                 layout_other.et_redundancy_required.isEnabled=false
                                 binding.layoutOppComyDetails.vertical.visibility=View.VISIBLE
                                 binding.layoutOppComyDetails.etVertical.isEnabled=false
                                 binding.linearqustionare.visibility=View.VISIBLE
                                 binding.layoutOppComyDetails.turnOver.visibility=View.GONE
                                 binding.linearSite.visibility=View.VISIBLE
                                 binding.linearPreTask.visibility=View.VISIBLE
                                 binding.layoutProductLine.price.visibility=View.GONE
                                 binding.layoutProductLine.reason.visibility=View.VISIBLE
                                 binding.layoutProductLine.linearProductt.visibility=View.GONE
                                 binding.layoutProductLine.rvProductList.visibility=View.GONE
                                 binding.linearfes.visibility=View.GONE
                                 binding.linearcontactinfo.relation.visibility=View.GONE
                                 binding.layoutOppComyDetails.TypeOrder.visibility=View.VISIBLE
                             }else{
                                 binding.layoutOppComyDetails.vertical.visibility=View.GONE
                                 binding.linearqustionare.visibility=View.GONE
                                 binding.linearSite.visibility=View.GONE
                                 binding.linearPreTask.visibility=View.GONE
                                /* binding.linearsix.visibility=View.VISIBLE*/
                                 binding.layoutProductLine.price.visibility=View.VISIBLE
                                 binding.layoutProductLine.reason.visibility=View.VISIBLE
                                 binding.layoutProductLine.linearProductt.visibility=View.VISIBLE
                                 binding.layoutProductLine.rvProductList.visibility=View.VISIBLE
                                 binding.linearcontactinfo.relation.visibility=View.VISIBLE
                                 binding.layoutOppComyDetails.TypeOrder.visibility=View.GONE
                                 binding.linearfes.visibility=View.VISIBLE

                             }
                             if(strOppId.isNullOrBlank()){
                                 binding.linearSite.visibility=View.GONE
                                 binding.linearPreTask.visibility=View.GONE
                             }
                             strVertical = response.body()?.Response?.Data?.get(0)?.VerticalID
                             strCityReqd = response.body()?.Response?.Data?.get(0)?.sdwanOpp?.CurrentOperationalCity

                              strIllServices = response.body()?.Response?.Data?.get(0)?.sdwanOpp?.NumberOfLinksIll
                              strIllServices = response.body()?.Response?.Data?.get(0)?.sdwanOpp?.CustomerILLservices.toString()
                              strBroadServices  = response.body()?.Response?.Data?.get(0)?.sdwanOpp?.CustomerBroadbandServices.toString()
                              strLinksManaged = response.body()?.Response?.Data?.get(0)?.sdwanOpp?.LinksManagedLinks.toString()
                              strRoutingServices  = response.body()?.Response?.Data?.get(0)?.sdwanOpp?.CustomerRoutingServices.toString()
                              strNetworkSecurity  = response.body()?.Response?.Data?.get(0)?.sdwanOpp?.NetworkSecurityServices.toString()
                              strHosted = response.body()?.Response?.Data?.get(0)?.sdwanOpp?.ApplicationsHosted.toString()
                              strCustomer  = response.body()?.Response?.Data?.get(0)?.sdwanOpp?.ItSupport.toString()
                              strBackBone = response.body()?.Response?.Data?.get(0)?.sdwanOpp?.MPLSBackbone.toString()
                              strContract  = response.body()?.Response?.Data?.get(0)?.sdwanOpp?.RedundancyMPLS.toString()
                              binding.layoutLeadSdQuestionare.etNoOfLocation.setText(response.body()?.Response?.Data?.get(0)?.sdwanOpp?.NoOfLocation.toString())
                              binding.layoutLeadSdQuestionare.etLinks.setText(response.body()?.Response?.Data?.get(0)?.sdwanOpp?.NumberOfLinksBroadbandServices.toString())
                              binding.layoutLeadSdQuestionare.etItSpent.setText(response.body()?.Response?.Data?.get(0)?.sdwanOpp?.IndicationITSpent.toString())
                              binding.layoutLeadSdQuestionare.etRemarkk.setText(response.body()?.Response?.Data?.get(0)?.sdwanOpp?.SdwanRemarks.toString())
                              binding.layoutLeadSdQuestionare.etMentionNum.setText(response.body()?.Response?.Data?.get(0)?.sdwanOpp?.NumberOfLinksIll.toString())
                             strBuildingStatus = response.body()?.Response?.Data?.get(0)?.Building_BuildingStatus.toString()
                             strBusinessSement = response.body()?.Response?.Data?.get(0)?.CustomerSegment.toString()
                             strCreateAreaOrBuilding =  response.body()?.Response?.Data?.get(0)?.CreateAreaOrBuilding.toString()
                             strTPFeasibilty =  response.body()?.Response?.Data?.get(0)?.TPFeasibilty.toString()
                             getAllfeasList()
                             strType = response.body()?.Response?.Data?.get(0)?.TypeOfOrder.toString()

                             productseg =  response.body()?.Response?.Data?.get(0)?.ProductSegment
                             if(productseg=="Managed Office Solution"||productseg=="Secured Managed Internet"){
                                 layout_other.sp_opredundancy.isEnabled=false
                                 layout_other.et_redundancy_required.isEnabled=false
                                 layout_other.et_redundancy_required.isFocusable=false
                             }

                             if((productseg=="Managed Wi-Fi Business")&&(strIndustry=="Co-Living")){
                                 layout_opp_comy_details.numBeds.visibility=View.VISIBLE
                             }else if((productseg=="Managed Wi-Fi Business")&&(strIndustry=="Co-Working")){
                                 layout_opp_comy_details.numUsers.visibility=View.VISIBLE
                             }
                             strSalutation= response.body()?.Response?.Data?.get(0)?.Salutation
                             if(strBuilding.isNotEmpty()){
                                 getPriceList(strBuilding)
                             }
                             getIndustryTpe(strIndustry)

                             var typePosition = 0
                             resources.getStringArray(R.array.siteTypeVal).forEachIndexed { index, s ->
                                 if (s == strType) typePosition = index
                                 return@forEachIndexed
                             }

                             val typeAdapter = ArrayAdapter(this@OpportunityActivity, android.R.layout.simple_spinner_item,resources.getStringArray(R.array.typeOrder))
                             typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                             binding.layoutOppComyDetails.spTypeOrder.adapter = typeAdapter
                             binding.layoutOppComyDetails.spTypeOrder.setSelection(typePosition)
                             typeAdapter.notifyDataSetChanged()
                             strturnOver =  response.body()?.Response?.Data?.get(0)?.TurnOver

                             var turnOverPosition = 0
                             SalesConstant.turnOverVal.forEachIndexed { index, s ->
                                 if (s == strturnOver) {
                                     turnOverPosition = index
                                     return@forEachIndexed
                                 }
                             }

                             val turnOverAdapter = ArrayAdapter(this@OpportunityActivity, android.R.layout.simple_spinner_item,  SalesConstant.turnOver)
                             turnOverAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                             binding.layoutOppComyDetails.spTurnOver.adapter = turnOverAdapter
                             binding.layoutOppComyDetails.spTurnOver.setSelection(turnOverPosition)
                             turnOverAdapter.notifyDataSetChanged()

                             var salutationPosition = 0
                             SalesConstant.list_of_salutation_id.forEachIndexed { index, s ->
                                 if (s == strSalutation) {
                                     salutationPosition = index
                                     return@forEachIndexed
                                 }
                             }

                            val salutationAdapter = ArrayAdapter(this@OpportunityActivity, android.R.layout.simple_spinner_item,  SalesConstant.list_of_salutation)
                            salutationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            layout_opp_cntct_person.sp_opsalutation?.adapter = salutationAdapter
                            layout_opp_cntct_person.sp_opsalutation.setSelection(salutationPosition)
                            salutationAdapter.notifyDataSetChanged()

                             strcontact_stateCode = response.body()?.Response?.Data?.get(0)?.StateId
                             str_inst_area = response.body()?.Response?.Data?.get(0)?.AreaId
                             str_inst_building_nm = response.body()?.Response?.Data?.get(0)?.BuildingId
                             str_industry_type = response.body()?.Response?.Data?.get(0)?.IndustryId
                             str_city_code = response.body()?.Response?.Data?.get(0)?.CityId
                             val build= response.body()?.Response?.Data?.get(0)?.Buildingname
                             if(build?.startsWith("Other")==true){
                                 layout_opp_cntct_person.et_oppspecfc_dng.visibility=View.VISIBLE
                             }
                            var cntstatePosition = 0
                             SalesConstant.list_of_state.forEachIndexed { index, s ->
                                if (s == strContactstate) cntstatePosition = index
                            }
                            val cntstateAdapter = ArrayAdapter(this@OpportunityActivity, android.R.layout.simple_spinner_item,  SalesConstant.list_of_state)
                            cntstateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            layout_opp_cntct_person.sp_opstate.adapter = cntstateAdapter
                            layout_opp_cntct_person.sp_opstate.setSelection(cntstatePosition)
                            cntstateAdapter.notifyDataSetChanged()

                            str_firm_type= response.body()?.Response?.Data?.get(0)?.Firmtype
                            var firmPosition = 0
                             SalesConstant.list_firm_type_value.forEachIndexed { index, _ ->
                                if (index == str_firm_type?.toInt()) {
                                    firmPosition = index
                                    return@forEachIndexed
                                }
                            }
                            val firmAdapter = ArrayAdapter(this@OpportunityActivity, android.R.layout.simple_spinner_item,  SalesConstant.list_firm_type)
                            firmAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            layout_opp_comy_details.sp_opfrmtype?.adapter = firmAdapter
                            layout_opp_comy_details.sp_opfrmtype.setSelection(firmPosition)
                            firmAdapter.notifyDataSetChanged()

                             str_customer_segmentid = response.body()?.Response?.Data?.get(0)?.CustomerSegment
                            var customersegPosition = 0
                             SalesConstant.list_cust_seg_value.forEachIndexed { index, s ->
                                if (s == str_customer_segmentid) {
                                    customersegPosition = index
                                    return@forEachIndexed
                                }
                            }

                            val custSeg = ArrayAdapter(this@OpportunityActivity, android.R.layout.simple_spinner_item,  SalesConstant.list_of_cust_segment)
                            custSeg.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            layout_opp_comy_details.sp_opcustseg?.adapter = custSeg
                            layout_opp_comy_details.sp_opcustseg.setSelection(customersegPosition)
                            custSeg.notifyDataSetChanged()

                             str_media = response.body()?.Response?.Data?.get(0)?.Media
                            var mediaPosition = 0
                             SalesConstant.list_of_selfpo_value.forEachIndexed { index, s ->
                                if (index == str_media?.toInt()) {
                                    mediaPosition = index
                                    return@forEachIndexed
                                }
                            }
                            val media = ArrayAdapter(this@OpportunityActivity, android.R.layout.simple_spinner_item,  SalesConstant.list_of_media)
                            media.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            layout_other.sp_opmedia?.adapter = media
                            layout_other.sp_opmedia.setSelection(mediaPosition)
                            media.notifyDataSetChanged()

                            val lostadapter = ArrayAdapter(this@OpportunityActivity, android.R.layout.simple_spinner_item,  SalesConstant.lost)
                            lostadapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            sp_lost.adapter = lostadapter
                             str_serv_pro = response.body()?.Response?.Data?.get(0)?.Existingprovider
                            var provOnePosition = 0
                             SalesConstant.ext_serv_one_value.forEachIndexed { index, s ->
                                if (s == str_serv_pro) {
                                    provOnePosition = index
                                    return@forEachIndexed
                                }
                            }
                            val serv1 = ArrayAdapter(this@OpportunityActivity, android.R.layout.simple_spinner_item,  SalesConstant.ext_serv_one)
                            serv1.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            layout_other.sp_opexservice?.adapter = serv1
                            layout_other.sp_opexservice.setSelection(provOnePosition)
                            serv1.notifyDataSetChanged()

                             str_cmpnyself = response.body()?.Response?.Data?.get(0)?.CompanySelf
                            var companySelfPosition = 0
                             SalesConstant.list_of_selfpo_value.forEachIndexed { index, s ->
                                if (index == str_cmpnyself?.toInt()) {
                                    companySelfPosition = index
                                    return@forEachIndexed
                                }
                            }

                            val companySelfadpter = ArrayAdapter(this@OpportunityActivity, android.R.layout.simple_spinner_item,  SalesConstant.list_of_selfpo)
                            companySelfadpter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            layout_other.sp_opselfpo?.adapter = companySelfadpter
                            layout_other.sp_opselfpo.setSelection(companySelfPosition)
                            companySelfadpter.notifyDataSetChanged()

                             str_cust_frwl = response.body()?.Response?.Data?.get(0)?.Firewall
                            var FirewallPosition = 0
                             SalesConstant.list_of_selfpo_value.forEachIndexed { index, s ->
                                if (index == str_cust_frwl?.toInt()) {
                                    FirewallPosition = index
                                    return@forEachIndexed
                                }
                            }

                            val frwaladpter = ArrayAdapter(this@OpportunityActivity, android.R.layout.simple_spinner_item,  SalesConstant.list_of_selfpo)
                            frwaladpter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            layout_other.sp_opfirewal?.adapter = frwaladpter
                            layout_other.sp_opfirewal.setSelection(FirewallPosition)
                            frwaladpter.notifyDataSetChanged()
                             redundancy = response.body()?.Response?.Data?.get(0)?.Redunancy
                             str_redundancy = response.body()?.Response?.Data?.get(0)?.Redunancy.toString()
                            val redundancyadpter = ArrayAdapter(this@OpportunityActivity, android.R.layout.simple_spinner_item,  SalesConstant.list_of_redundancy)
                            redundancyadpter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            layout_other.sp_opredundancy?.adapter = redundancyadpter
                            if (redundancy == true) {
                                layout_other.sp_opredundancy.setSelection(1)
                            } else {
                                layout_other.sp_opredundancy.setSelection(2)
                            }
                            redundancyadpter.notifyDataSetChanged()
                             var illServicesPosition = 0
                             resources.getStringArray(R.array.listCustomerServiceVal).forEachIndexed { index, s ->
                                 if (s == strIllServices) illServicesPosition = index
                                 return@forEachIndexed
                             }
                             val illServices = ArrayAdapter(this@OpportunityActivity, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.listCustomerService))
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
                             val broadServices = ArrayAdapter(this@OpportunityActivity, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.listCustomerService))
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
                             val linkManaged = ArrayAdapter(this@OpportunityActivity, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.listManagedLink))
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
                             val routingService = ArrayAdapter(this@OpportunityActivity, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.listCustomerService))
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
                             val networkSecurity = ArrayAdapter(this@OpportunityActivity, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.listNetworkSecurity))
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
                             val hosted = ArrayAdapter(this@OpportunityActivity, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.listApplicants))
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
                             val customerReq = ArrayAdapter(this@OpportunityActivity, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.listInHouse))
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
                             val backBone = ArrayAdapter(this@OpportunityActivity, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.listCustomerService))
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
                             val contract = ArrayAdapter(this@OpportunityActivity, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.listCustomerService))
                             contract.setDropDownViewResource(android.R.layout.simple_spinner_item)
                             binding.layoutLeadSdQuestionare.spContractRenewed.adapter = contract
                             binding.layoutLeadSdQuestionare.spContractRenewed.setSelection(contractPosition)
                             contract.notifyDataSetChanged()

                             val firewallSet = response.body()?.Response?.Data?.get(0)?.sdwanOpp?.FirewallSetToExpire.toString()
                             if(firewallSet.isNotEmpty()){
                                 val split = firewallSet.split("-")
                                 val date = split[0]
                                 val month = split[1]
                                 val year = split[2]
                                 binding.layoutLeadSdQuestionare.etFireSet.setText("$date-$month-$year")
                                 firesSet=("$year-$month-$date")
                             }
                             val redundancyMPLS = response.body()?.Response?.Data?.get(0)?.sdwanOpp?.MPLSContract.toString()
                             if(redundancyMPLS.isNotEmpty()){
                                 val split = redundancyMPLS.split("-")
                                 val date = split[0]
                                 val month = split[1]
                                 val year = split[2]
                                 binding.layoutLeadSdQuestionare.etMplsRequired.setText("$date-$month-$year")
                                 mpls=("$year-$month-$date")
                             }
                             val polockDate = response.body()?.Response?.Data?.get(0)?.PoLock

                             if(polockDate?.isNotEmpty()==true){
                                 val split1 = polockDate.split("-")
                                 val date1 = split1[0]
                                 val month1 = split1[1]
                                 val year1 = split1[2]
                                 layout_other.et_polock.setText("$date1-$month1-$year1")
                                 val year = year1+1
                                 polockdate=("$year1-$month1-$date1")

                                 datePoLock=("$year-$month1-$date1")
                                 Log.e("PolOckYear",year)
                             }
                             val pocDate = response.body()?.Response?.Data?.get(0)?.PocClosureDate
                             if(pocDate?.isNotEmpty()==true){
                                 val split1 = pocDate.split("-")
                                 val date1 = split1[0]
                                 val month1 = split1[1]
                                 val year1 = split1[2]
                                 binding.layoutOppComyDetails.etPoc.setText("$date1-$month1-$year1")
                                 Poc=("$year1-$month1-$date1")
                             }
                             val ponextdate = response.body()?.Response?.Data?.get(0)?.PoNext
                             if(ponextdate?.isNotEmpty()==true){
                                 val split2 = ponextdate.split("-")
                                 val date2 = split2[0]
                                 val month2 = split2[1]
                                 val year2 = split2[2]
                                 layout_other.et_oprenewal.setText("$date2-$month2-$year2")
                                 renewal=("$year2-$month2-$date2")
                             }
                             val frwcdate = response.body()?.Response?.Data?.get(0)?.FirewallAwc
                             if(frwcdate?.isNotEmpty() == true){
                                 val fr = frwcdate.split("-")
                                 val date = fr[0]
                                 val month = fr[1]
                                 val year = fr[2]
                                 layout_other.et_frwalaws.setText("$date-$month-$year")
                                 frwaws=("$year-$month-$date")
                             }
                             CoroutineScope(Dispatchers.IO).launch {
                                 getProductAddedList()
                                 getGenerateQuote()
                                 getAllSites()
                                 getProductList()
                                 getDOA()
                             }
                            // if(strOperationCity=="true") {
                                 if ((strBuildingStatus == "C-RFS" || strBuildingStatus == "P-RFS" ||
                                     strBuildingStatus == "B-RFS" || strBuildingStatus == "A-RFS") && (redundancy == false)) {
                                     binding.feasibility.addFes.visibility = View.GONE
                                 }else if ((strBuildingStatus == "C-RFS" || strBuildingStatus == "P-RFS" ||
                                     strBuildingStatus == "B-RFS" || strBuildingStatus == "A-RFS") && (redundancy == true)) {
                                     binding.feasibility.addFes.visibility = View.VISIBLE
                                 }else if((strArea == "Other" || strBuilding == "Other") && (strCreateAreaOrBuilding=="false") &&(strBuildingStatus == "Non-RFS")&&(strTPFeasibilty=="Show Create Area or Building button") ){
                                     if(allFeasibility!=null||fesstatus=="1"){
                                         fab_create_society.visibility = View.VISIBLE
                                         Log.e("ButtonFes", "4")
                                     }
                                 }
                                 val code = response.body()?.Response?.Data?.get(0)?.Statuscode
                                 if(code.equals("Waiting for Approval")){
                                     tv_opp_submit.visibility = View.GONE
                                     tv_won.visibility=View.VISIBLE
                                     Log.e("Button41", "41")
                                 }else if(code.equals("In Progress")&&(strApprovalStatus.contentEquals("Yes")||strApprovalStatus.contentEquals("yes"))){
                                     tv_opp_submit.visibility = View.VISIBLE
                                     tv_won.visibility=View.GONE
                                     Log.e("Button42", "42")
                                 }else if(code.equals("Approved")){
                                     tv_opp_submit.visibility = View.GONE
                                 }else if(code.equals("In Progress")){
                                         tv_won.visibility=View.VISIBLE
                                 }
                                if(strStatus=="Lost"||strStatus=="Won"){
                                    tv_opp_save.visibility=View.GONE
                                    tv_won.visibility = View.GONE
                                    opp_lost.visibility=View.GONE
                                    tv_opp_submit.visibility=View.GONE
                                    fab_create_society.visibility=View.GONE
                                    layout_product_line.add_procuct.visibility=View.GONE
                                    binding.quote.addQuote.visibility=View.GONE
                                    binding.feasibility.addFes.visibility=View.GONE
                                    layout_product_line.sp_opproduct.isEnabled= false
                                    layout_product_line.et_product_list.isEnabled= false
                                    oppSale.add_preTask.visibility=View.GONE
                                    Log.e("25","gone")
                                if(strStatus=="Lost"||strStatus=="Waiting for Approval"){
                                    flr.visibility=View.GONE
                                }else{
                                    flr.visibility=View.VISIBLE
                                    tv_reopen.visibility = View.VISIBLE
                                }
                                status ="1"
                                locked()
                                }else{
                                opp_lost.visibility=View.VISIBLE
                                tv_won.visibility=View.VISIBLE
                                tv_opp_save.visibility=View.VISIBLE
                                layout_product_line.add_procuct.visibility=View.VISIBLE
                               // binding.quote.addQuote.visibility=View.VISIBLE
                                flr.visibility=View.GONE
                                status="2"
                                calender()
                               }
                            }else{
                              Toast.makeText(this@OpportunityActivity,msg,Toast.LENGTH_LONG).show()
                            }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<GetOppurtunityResponse?>, t: Throwable) {
                binding.opprogressLayout.progressOverlay.visibility = View.GONE
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

                        val adapter12 = ArrayAdapter(this@OpportunityActivity, android.R.layout.simple_spinner_item, RequiredCity!!)
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
                        var verticalPosition=0
                        verticalId?.forEachIndexed { index, s ->
                            if(s==strVertical)verticalPosition=index
                        }

                        val adapter12 = ArrayAdapter(this@OpportunityActivity, android.R.layout.simple_spinner_item, vertical!!)
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        binding.layoutOppComyDetails.spVertical.adapter = adapter12
                        binding.layoutOppComyDetails.spVertical.setSelection(verticalPosition)
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


    fun locked(){
        opp_contact_info_row.et_op_toptic.isFocusableInTouchMode= false
        layout_opp_cntct_person.et_opsalutation.isEnabled= false
        layout_opp_cntct_person.et_contc_prsn.isEnabled= false
        layout_opp_cntct_person.et_eml_id.isEnabled= false
        layout_opp_cntct_person.et_mob_num.isEnabled= false
        layout_opp_cntct_person.et_op_state.isEnabled= false
        layout_opp_cntct_person.et_city.isEnabled= false
        layout_opp_cntct_person.et_area.isEnabled= false
        layout_opp_cntct_person.et_building_nm.isEnabled= false
        layout_opp_cntct_person.et_area.isEnabled= false
        layout_opp_cntct_person.et_build_num.isEnabled= false
        layout_opp_cntct_person.et_floor.isEnabled= false
        layout_opp_cntct_person.et_op_pincode.isEnabled= false
        binding.layoutOppComyDetails.etCompanyName.isEnabled= false
        layout_opp_comy_details.et_firm_type.isEnabled= false
        layout_opp_comy_details.et_op_industype.isEnabled= false
        layout_opp_comy_details.et_cust_segmnt.isEnabled= false
        layout_other.et_media.isEnabled= false
        layout_other.et_uptime_sla.isEnabled= false
        layout_other.et_ext_service.isEnabled= false
        layout_other.et_firewl.isEnabled= false
        layout_other.et_redundancy_required.isEnabled= false
        layout_other.etselfpo.isEnabled= false
        layout_other.et_polock.isEnabled= false
        layout_other.et_oprenewal.isEnabled= false
        layout_other.et_frwalaws.isEnabled= false
        layout_product_line.et_price_list.isEnabled= false
        layout_product_line.et_op_reason.isEnabled= false
        layout_opp_comy_details.et_turnOver.isEnabled= false
        binding.layoutLeadSdQuestionare.etNoOfLocation.isEnabled= false
        binding.layoutLeadSdQuestionare.etLinks.isEnabled= false
        binding.layoutLeadSdQuestionare.etItSpent.isEnabled= false
        binding.layoutLeadSdQuestionare.etRemarkk.isEnabled= false
        binding.layoutLeadSdQuestionare.etMentionNum.isEnabled= false
        binding.layoutLeadSdQuestionare.etCustomerUsingIllServices.isEnabled= false
        binding.layoutLeadSdQuestionare.etBroadServices.isEnabled= false
        binding.layoutLeadSdQuestionare.etLinksManged.isEnabled= false
        binding.layoutLeadSdQuestionare.etRoutingServices.isEnabled= false
        binding.layoutLeadSdQuestionare.etFireSet.isEnabled= false
        binding.layoutLeadSdQuestionare.etCityRequired.isEnabled= false
        binding.layoutLeadSdQuestionare.etNetworkSecurity.isEnabled= false
        binding.layoutLeadSdQuestionare.etHostedRequired.isEnabled= false
        binding.layoutLeadSdQuestionare.etCustomerRequired.isEnabled= false
        binding.layoutLeadSdQuestionare.etBackboneRequired.isEnabled= false
        binding.layoutLeadSdQuestionare.etMplsRequired.isEnabled= false
        binding.oppSite.addSite.visibility=View.GONE
        binding.oppSale.addPreTask.visibility=View.GONE
        binding.layoutOppComyDetails.etTypeOrder.isEnabled= false
        et_opsalutation.isEnabled=false
        sp_opstate.isEnabled=false
        sp_opcity.isEnabled=false
        sp_oparea.isEnabled=false
        sp_opfrmtype.isEnabled=false
        sp_opindusty.isEnabled=false
        sp_opcustseg.isEnabled=false
       // et_lead_name.isEnabled=false
        sp_opmedia.isEnabled=false
        sp_opexservice.isEnabled=false
        sp_opfirewal.isEnabled=false
        sp_opredundancy.isEnabled=false
        sp_opselfpo.isEnabled=false
        layout_product_line.sp_price.isEnabled= false
        layout_product_line.sp_opproduct.isEnabled= false
        layout_product_line.et_product_list.isEnabled= false
        opp_lost.visibility=View.GONE
        layout_product_line.add_procuct.visibility=View.GONE
        tv_opp_submit.visibility=View.GONE
      //  add_dao.visibility = View.GONE
        tv_won.visibility = View.GONE
    }

    fun getIndustryTpe(strIndusry: String) {
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
                        industryid.add("")
                        for (item in industryList!!){
                            instryname.add(item.IndTypeName)
                            industryid.add(item.IndTypeId)
                        }
                        var industryPosition=0
                        instryname.forEachIndexed { index, s ->
                            if(s==strIndusry)industryPosition=index
                        }
                        val adapter12 = ArrayAdapter(this@OpportunityActivity, android.R.layout.simple_spinner_item, instryname)
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        layout_opp_comy_details.sp_opindusty.adapter = adapter12
                        layout_opp_comy_details.sp_opindusty.setSelection(industryPosition)
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

                        val adapter12 = ArrayAdapter(this@OpportunityActivity, android.R.layout.simple_spinner_item, building!!)
                        layout_opp_cntct_person.et_building_nm.threshold=0
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        layout_opp_cntct_person.et_building_nm.setAdapter(adapter12)
                        adapter12.notifyDataSetChanged()

                        layout_opp_cntct_person.et_building_nm.setOnFocusChangeListener { _, b ->
                            if (b)   layout_opp_cntct_person.et_building_nm.showDropDown()
                        }
                        layout_opp_cntct_person.et_building_nm.onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, _ ->
                            buildingname = adapter12.getItem(position)
                              if (position != 0) str_inst_building_nm =
                                buildingCode?.get(position)
                            if(buildingname?.startsWith("Other") == true){
                                layout_opp_cntct_person.et_oppspecfc_dng.visibility=View.VISIBLE
                           }else{
                                layout_opp_cntct_person.et_oppspecfc_dng.visibility=View.GONE
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

    fun getProductList() {

        val getProductListRequest = strOppId?.let { GetProductListRequest(Constants.GET_PRODUCTLIST,Constants.AUTH_KEY, it,password,userName,"","") }

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getProductList(getProductListRequest)
        call.enqueue(object : Callback<ProductListResponse?> {
            override fun onResponse(call: Call<ProductListResponse?>, response: Response<ProductListResponse?>) {

                if (response.isSuccessful && response.body() != null) {
                    try {
                       // val msg = response.body()?.Response?.Message
                        productList= response.body()?.Response?.Data
                        productId= ArrayList<String>()
                        productId?.add("Select Product")
                        if (productList != null) {
                            for (item in productList!!){
                                productId?.add(item.ProductId)
                            }
                        }
                        val adapter12 = ArrayAdapter(this@OpportunityActivity, android.R.layout.simple_spinner_item, productId!!)
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        layout_product_line.sp_opproduct.adapter = adapter12

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<ProductListResponse?>, t: Throwable) {
                Log.e("RetroError", t.toString())
            }
        })
    }

    fun getPriceList(buildingname: String?) {
        val getProductListRequest = GetPriceListRequest(Constants.GET_PRICELIST,Constants.AUTH_KEY,buildingname,password,userName)

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getPriceList(getProductListRequest)
        call.enqueue(object : Callback<GetPriceListResponse?> {
            override fun onResponse(call: Call<GetPriceListResponse?>, response: Response<GetPriceListResponse?>) {

                if (response.isSuccessful && response.body() != null) {
                    try {
                       // val msg = response.body()?.Response?.Message
                        priceList= response.body()?.Response?.Data
                        price = ArrayList<String>()
                        pricebuildingCode = ArrayList<String>()
                        price?.add("Select Price List")
                        if (priceList != null) {
                            for (item in priceList!!){
                                price?.add(item.PriceName)
                                pricebuildingCode?.add(item.BuidingId)
                            }
                        }
                        var pricePosition=0
                        price?.forEachIndexed { index, s ->
                            if(s==strPrice)pricePosition=index
                        }
                        if(price?.size!=null){
                            layout_product_line.et_product_list
                            layout_product_line.linearsection.visibility=View.VISIBLE
                            getProductList()
                          //  getProductAddedList()
                        }

                        val adapter12 = ArrayAdapter(this@OpportunityActivity, android.R.layout.simple_spinner_item, price!!)
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        layout_product_line.sp_price.adapter = adapter12
                        layout_product_line.sp_price.setSelection(pricePosition)
                        adapter12.notifyDataSetChanged()

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<GetPriceListResponse?>, t: Throwable) {
                Log.e("RetroError", t.toString())
            }
        })
    }
    @SuppressLint("SetTextI18n")
    fun  calender(){
        try{
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
            val  finalYear =  Calendar.getInstance().get(Calendar.YEAR)

            val fyear = finalYear+1

        layout_other.et_polock.setOnClickListener {
            val dpd = DatePickerDialog(this, { _, year, monthOfYear, dayOfMonth ->
                    val mnth = monthOfYear+1
                    layout_other.et_polock.setText("$dayOfMonth-$mnth-$year")
                    val trgt =  layout_other.et_polock.text.toString()
                    val split = trgt.split("-")
                    val dateee = split[0]
                    val month1 = split[1]
                    val year1 = split[2]
                    polockdate=("$year1-$month1-$dateee")

            }, year, month, day)
             dpd.show()
        }

            binding.layoutOppComyDetails.etPoc.setOnClickListener {
                val dpd = DatePickerDialog(this, { _, year, monthOfYear, dayOfMonth ->
                    val mnth = monthOfYear+1
                    binding.layoutOppComyDetails.etPoc.setText("$dayOfMonth-$mnth-$year")
                    val trgt =  binding.layoutOppComyDetails.etPoc.text.toString()
                    val split = trgt.split("-")
                    val dateee = split[0]
                    val month1 = split[1]
                    val year1 = split[2]
                    Poc=("$year1-$month1-$dateee")
                }, year, month, day)
                dpd.show()
            }

            layout_other.et_oprenewal.setOnClickListener {
               val mYear= c[Calendar.YEAR]
              val mMonth=  c[Calendar.MONTH]
              val mDay=  c[Calendar.DAY_OF_MONTH]

                val mDialog = DatePickerDialog(
                    this, { _, mYear, mMonth, mDay ->
                        c[Calendar.YEAR] = mYear
                        c[Calendar.MONTH] = mMonth-1
                        c[Calendar.DAY_OF_MONTH] = mDay
                        binding.layoutOther.etOprenewal.setText("$mDay-$mMonth-$mYear")
                        renewal =("$mYear-$mMonth-$mDay")
                    },
                    c[Calendar.YEAR],
                    c[Calendar.MONTH],
                    c[Calendar.DAY_OF_MONTH]
                )
                val datee =  binding.layoutOther.etPolock.text.toString()
                val split1 = datee.split("-")
                val dateeePo = split1[0]
                val month1Po = split1[1]
                val year1Po = split1[2]

                c.set(fyear, month1Po.toInt(), dateeePo.toInt())
                mDialog.datePicker.minDate = c.timeInMillis
                mDialog.show()
            }

        layout_other.et_frwalaws.setOnClickListener {
            val dpd = DatePickerDialog(this, { _, year, monthOfYear, dayOfMonth ->
                val mnth = monthOfYear+1
                layout_other.et_frwalaws.setText("$dayOfMonth-$mnth-$year")
                val trgt =  layout_other.et_frwalaws.text.toString()
                val split = trgt.split("-")
                val dateee = split[0]
                val month1 = split[1]
                val year1 =  split[2]
                frwaws=("$year1-$month1-$dateee")
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

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (parent?.id == R.id.sp_opsalutation) {
            layout_opp_cntct_person.et_opsalutation.setText( SalesConstant.list_of_salutation[position])
            strSalutation =  SalesConstant.list_of_salutation_id[position]
        }
        else if (parent?.id == R.id.sp_vertical) {
            binding.layoutOppComyDetails.etVertical.setText(vertical?.get(position))
            strVertical = verticalId?.get(position)
        }
        else if (parent?.id == R.id.sp_cityRequired) {
            binding.layoutLeadSdQuestionare.etCityRequired.setText(RequiredCity?.get(position) )
            strCityReqd =(RequiredCityCode?.get(position))
        }
        else if (parent?.id == R.id.sp_customerUsingIllServices) {
            binding.layoutLeadSdQuestionare.etCustomerUsingIllServices.setText(resources.getStringArray(R.array.listCustomerService)[position])
            strIllServices =resources.getStringArray(R.array.listCustomerServiceVal)[position]
            if(strIllServices.equals("122050000")){
                binding.layoutLeadSdQuestionare.mention.visibility=View.VISIBLE
            }else{
                binding.layoutLeadSdQuestionare.mention.visibility=View.GONE
            }
        }
        else if (parent?.id == R.id.sp_broadServices) {
            binding.layoutLeadSdQuestionare.etBroadServices.setText(resources.getStringArray(R.array.listCustomerService)[position])
            strBroadServices =resources.getStringArray(R.array.listCustomerServiceVal)[position]
            if(strBroadServices.equals("122050000")){
                binding.layoutLeadSdQuestionare.links.visibility=View.VISIBLE
            }else{
                binding.layoutLeadSdQuestionare.links.visibility=View.GONE
            }
        }
        else if (parent?.id == R.id.sp_linksManged) {
            binding.layoutLeadSdQuestionare.etLinksManged.setText(resources.getStringArray(R.array.listManagedLink)[position])
            strLinksManaged =resources.getStringArray(R.array.listMangedLinkVal)[position]
        }
        else if (parent?.id == R.id.sp_routingServices) {
            binding.layoutLeadSdQuestionare.etRoutingServices.setText(resources.getStringArray(R.array.listCustomerService)[position])
            strRoutingServices =resources.getStringArray(R.array.listCustomerServiceVal)[position]
        }
        else if (parent?.id == R.id.sp_networkSecurity) {
            binding.layoutLeadSdQuestionare.etNetworkSecurity.setText(resources.getStringArray(R.array.listNetworkSecurity)[position])
            strNetworkSecurity =resources.getStringArray(R.array.listMangedLinkVal)[position]
        }
        else if (parent?.id == R.id.sp_hostedRequired) {
            binding.layoutLeadSdQuestionare.etHostedRequired.setText(resources.getStringArray(R.array.listApplicants)[position])
            strHosted =resources.getStringArray(R.array.listApplicantsVal)[position]
        }
        else if (parent?.id == R.id.sp_customerRequired) {
            binding.layoutLeadSdQuestionare.etCustomerRequired.setText(resources.getStringArray(R.array.listInHouse)[position])
            strCustomer =resources.getStringArray(R.array.listCustomerServiceVal)[position]
        }
        else if (parent?.id == R.id.sp_backboneRequired) {
            binding.layoutLeadSdQuestionare.etBackboneRequired.setText(resources.getStringArray(R.array.listCustomerService)[position])
            strBackBone =resources.getStringArray(R.array.listCustomerServiceVal)[position]
        }
        else if (parent?.id == R.id.sp_contractRenewed) {
            binding.layoutLeadSdQuestionare.etContractRenewed.setText(resources.getStringArray(R.array.listCustomerService)[position])
            strContract =resources.getStringArray(R.array.listCustomerServiceVal)[position]
        }
        else if (parent?.id == R.id.sp_opcustseg) {
            layout_opp_comy_details.et_cust_segmnt.setText( SalesConstant.list_of_cust_segment[position])
            str_customer_segmentid =  SalesConstant.list_cust_seg_value[position]
        }
        else if (parent?.id == R.id.sp_opmedia) {
            layout_other.et_media.setText( SalesConstant.list_of_media[position])
            str_media =  SalesConstant.list_of_media_value[position]
        }
        else if (parent?.id == R.id.sp_opexservice) {
            layout_other.et_ext_service.setText( SalesConstant.ext_serv_one[position])
            str_serv_pro =  SalesConstant.ext_serv_one_value[position]
        }
        else if (parent?.id == R.id.sp_turnOver) {
            layout_opp_comy_details.et_turnOver.setText(SalesConstant.turnOver[position])
            strturnOver = SalesConstant.turnOverVal[position]
        }
        else if (parent?.id == R.id.sp_opfirewal) {
            layout_other.et_firewl.setText( SalesConstant.list_of_selfpo[position])
            str_cust_frwl =  SalesConstant.list_of_selfpo_value[position]
            if( SalesConstant.list_of_selfpo[position] =="Yes"){
                linearaws.visibility= View.VISIBLE
            }else{
                linearaws.visibility= View.GONE
            }
        }
        else if (parent?.id == R.id.sp_opredundancy) {
            layout_other.et_redundancy_required.setText( SalesConstant.list_of_redundancy[position])
            strRedundancy =  SalesConstant.list_of_redundancy[position]
            str_redundancy=  SalesConstant.list_of_redundancy_value[position]
            if((strBuildingStatus=="C-RFS"||strBuildingStatus=="P-RFS" ||
                        strBuildingStatus=="B-RFS"||strBuildingStatus=="A-RFS") && strRedundancy=="No"){
                binding.feasibility.addFes.visibility=View.GONE
            }else if((strBuildingStatus=="C-RFS"||strBuildingStatus=="P-RFS" ||
                        strBuildingStatus=="B-RFS"||strBuildingStatus=="A-RFS")
                && strRedundancy=="Yes"){
                binding.feasibility.addFes.visibility=View.VISIBLE
            }else if(strBuildingStatus=="Non-RFS"){
                binding.feasibility.addFes.visibility = View.VISIBLE
            }
        }
        else if (parent?.id == R.id.sp_opfrmtype) {
            layout_opp_comy_details.et_firm_type.setText( SalesConstant.list_firm_type[position])
            str_firm_type =  SalesConstant.list_firm_type_value[position]
        }
        else if (parent?.id == R.id.sp_opindusty) {
            layout_opp_comy_details.et_op_industype.setText(instryname[position])
            str_industry_type = industryid[position]
            if((productseg=="Managed Wi-Fi Business")&&(instryname[position]=="Co-Living")){
                layout_opp_comy_details.numBeds.visibility=View.VISIBLE
            }else if((productseg=="Managed Wi-Fi Business")&&(instryname[position]=="Co-Working")){
                layout_opp_comy_details.numUsers.visibility=View.VISIBLE
            }
        }
        else if (parent?.id == R.id.sp_opstate) {
            layout_opp_cntct_person.et_op_state.setText( SalesConstant.list_of_state[position])
            strcontact_state =  SalesConstant.list_of_state[position]
            strcontact_stateCode =  SalesConstant.list_state_code[position]
            getCity(strcontact_stateCode.toString())
        }
        else if (parent?.id == R.id.sp_opcity) {
            layout_opp_cntct_person.et_city.setText(city?.get(position))
            str_city = city?.get(position).toString()
            str_city_code = cityCode?.get(position)
            strOperationCity = Oprationcity?.get(position).toString()
            getArea(str_city, str_city_code)
        }
        else if (parent?.id == R.id.sp_oparea) {
            layout_opp_cntct_person.et_area.setText(area?.get(position))
            str_inst_area = areaCode?.get(position)
            areaname = area?.get(position).toString()
            getBuilding(areaname,str_inst_area)
            if(areaname=="Other"){
                layout_opp_cntct_person.et_oppspecific_ar.visibility=View.VISIBLE
            }else{
                layout_opp_cntct_person.et_oppspecific_ar.visibility=View.GONE
            }
        }
        else if (parent?.id == R.id.sp_opselfpo) {
            layout_other.etselfpo.setText( SalesConstant.list_of_selfpo[position])
            str_cmpnyself = SalesConstant.list_of_selfpo_value[position]
        }
        else if (parent?.id == R.id.sp_price) {
            layout_product_line.et_price_list.setText(price?.get(position))
            strPrice = price?.get(position).toString()
        }
        else if (parent?.id == R.id.sp_lost) {
            if (position != 0) strLost = "" +  SalesConstant.lostCode[position - 1] else strLost= " "
            if(strLost.isNotEmpty()) {
                val loststatus:String
                if (position != 0) loststatus = "" +  SalesConstant.lostCode[position - 1] else loststatus= " "
                if(loststatus.isBlank()||loststatus.isEmpty()){

                }else{
                    lostOppurtunity(loststatus)
                }
            }

        }
        else if (parent?.id == R.id.sp_opproduct) {
            layout_product_line.et_product_list.setText(productId?.get(position))
            str_product =  productId?.get(position)
        }
        else if (parent?.id == R.id.spTypeOrder) {
            binding.layoutOppComyDetails.etTypeOrder.setText(resources.getStringArray(R.array.typeOrder)[position])
            strType = resources.getStringArray(R.array.siteTypeVal)[position]
            if(strType.equals("122050000")){
                binding.layoutOppComyDetails.poc.visibility=View.VISIBLE
            }else{
                binding.layoutOppComyDetails.poc.visibility=View.GONE
            }

        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    fun getArea(str_city: String?, str_city_code: String?) {
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
                        area?.forEachIndexed { index, s ->
                            if(s==strArea)areaPosition=index
                            return@forEachIndexed
                        }
                        val adapter12 = ArrayAdapter(this@OpportunityActivity, android.R.layout.simple_spinner_item, area!!)
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        layout_opp_cntct_person.sp_oparea.adapter = adapter12
                        layout_opp_cntct_person.sp_oparea.setSelection(areaPosition)
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


    fun getCity(stateCode: String?) {
        val getCityRequest = GetCityRequest(Constants.GET_CITY,Constants.AUTH_KEY,password,stateCode,userName)

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
                        Oprationcity = ArrayList<String>()
                        city?.add("Select City")
                        cityCode?.add("")
                        Oprationcity?.add("")
                        for (item in cityList!!) {
                            city?.add(item.CityName)
                            cityCode?.add(item.CityCode)
                            Oprationcity?.add((item.IsOperationalCity.toString()))
                            strOperationCity = item.IsOperationalCity.toString()
                        }
                        var cityPosition=0
                        city?.forEachIndexed { index, s ->
                            if(s==strCity)cityPosition=index
                            return@forEachIndexed
                        }
                        val adapter12 = ArrayAdapter(this@OpportunityActivity, android.R.layout.simple_spinner_item, city!!)
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        layout_opp_cntct_person.sp_opcity.adapter = adapter12
                        layout_opp_cntct_person.sp_opcity.setSelection(cityPosition)
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

    override fun onBackPressed() {
       next()
    }

    private fun next(){
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        builder.setMessage(AppConstants.PREVIOUS_SCREEN)
        builder.setPositiveButton(
           AppConstants.YES
        ) { _, _ ->
            val intent = Intent(this, OppTabActivity::class.java)
            startActivity(intent)
            finish()
        }
        builder.setNegativeButton(AppConstants.NO) { dialog, _ ->
            dialog.cancel()
        }
        val alert: AlertDialog = builder.create()
        alert.show()
    }
}