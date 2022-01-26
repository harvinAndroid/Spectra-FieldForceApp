package com.spectra.fieldforce.salesapp.activity


import GetAllFeasibiltyAdapter
import GetAllQuoteAdapter
import GetDOAViewAdapter
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.spectra.fieldforce.R
import com.spectra.fieldforce.api.ApiClient
import com.spectra.fieldforce.api.ApiInterface
import com.spectra.fieldforce.databinding.OppurtunityDemoFragmentBinding
import com.spectra.fieldforce.model.gpon.response.CommonClassResponse
import com.spectra.fieldforce.salesapp.adapter.GetAllProductItemAdapter
import com.spectra.fieldforce.salesapp.fragment.GetAllOppurtunityFrag
import com.spectra.fieldforce.salesapp.model.*
import com.spectra.fieldforce.utils.AppConstants
import com.spectra.fieldforce.utils.Constants
import kotlinx.android.synthetic.main.caf_demo_fragment.*
import kotlinx.android.synthetic.main.fragment_all_lead_list.*
import kotlinx.android.synthetic.main.op_product_add_item_row.view.*
import kotlinx.android.synthetic.main.op_product_line_item_row.*
import kotlinx.android.synthetic.main.op_product_line_item_row.view.*
import kotlinx.android.synthetic.main.opp_add_doa.*
import kotlinx.android.synthetic.main.opp_company_details_row.*
import kotlinx.android.synthetic.main.opp_company_details_row.view.*
import kotlinx.android.synthetic.main.opp_company_details_row.view.et_firm_type
import kotlinx.android.synthetic.main.opp_other_info_row.*
import kotlinx.android.synthetic.main.opp_other_info_row.view.*
import kotlinx.android.synthetic.main.opp_other_info_row.view.et_media
import kotlinx.android.synthetic.main.oppurtunity_contact_info_row.*
import kotlinx.android.synthetic.main.oppurtunity_contact_info_row.view.*
import kotlinx.android.synthetic.main.oppurtunity_demo_fragment.*
import kotlinx.android.synthetic.main.oppurtunity_demo_fragment.linadd
import kotlinx.android.synthetic.main.oppurtunity_demo_fragment.linear_companydetails
import kotlinx.android.synthetic.main.oppurtunity_demo_fragment.linear_contact_person_address
import kotlinx.android.synthetic.main.oppurtunity_demo_fragment.linear_insta_addres
import kotlinx.android.synthetic.main.oppurtunity_demo_fragment.linearcontactinfo
import kotlinx.android.synthetic.main.oppurtunity_demo_fragment.linearfive
import kotlinx.android.synthetic.main.oppurtunity_demo_fragment.linearfouraddres
import kotlinx.android.synthetic.main.oppurtunity_demo_fragment.linearsix
import kotlinx.android.synthetic.main.oppurtunity_demo_fragment.linearthree
import kotlinx.android.synthetic.main.oppurtunity_demo_fragment.lineartwo
import kotlinx.android.synthetic.main.oppurtunity_demo_fragment.tv_opp_save
import kotlinx.android.synthetic.main.oppurtunity_detail_row.view.*
import kotlinx.android.synthetic.main.update_lead_company_details_row.*
import kotlinx.android.synthetic.main.update_lead_company_details_row.view.*
import kotlinx.android.synthetic.main.update_lead_demo_fragment.*
import kotlinx.android.synthetic.main.update_lead_installation_address_row.*
import kotlinx.android.synthetic.main.update_lead_installation_address_row.view.*
import kotlinx.android.synthetic.main.update_lead_other_details_row.*
import kotlinx.android.synthetic.main.update_lead_other_details_row.view.*
import kotlinx.android.synthetic.main.update_lead_remarks_row.view.*
import kotlinx.android.synthetic.main.update_leadtoolbar.*
import kotlinx.android.synthetic.main.update_leadtoolbar.view.*
import kotlinx.android.synthetic.main.updatelead__contact_person_row.*
import kotlinx.android.synthetic.main.updatelead__contact_person_row.view.*
import kotlinx.android.synthetic.main.updatelead_contact_personadd_row.*
import kotlinx.android.synthetic.main.updatelead_contact_personadd_row.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class OpportunityActivity:AppCompatActivity(), View.OnClickListener,AdapterView.OnItemSelectedListener{

    lateinit var  binding: OppurtunityDemoFragmentBinding
    var str_Opp_Id : String? = null
    var str_Lead_Id : String? = null
    private var cityList : ArrayList<CityData>? = null
    private var city : ArrayList<String>? = null
    private var cityCode : ArrayList<String>? = null
    private var Oprationcity : ArrayList<String>? = null
    private var generatequote: ArrayList<QuoteData>? = null
    private var allFeasibility: ArrayList<FeasData>? = null
    private var allApproval: ArrayList<AppData>? = null
    var strOperationCity = ""
    var strCity = ""
    var strArea= ""
    var strBuildingStatus =""
    var strBuilding = ""
    var strPrice = ""
    var strIndustry =""
    var strRedundancy =""
    var strBusinessSement =""
    var strCreateAreaOrBuilding=""
    var strTPFeasibilty =""
    var str_salutation :String? = null
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
    var str_discount : String? = null
    var isdiscount:Boolean=false
    private var inAnimation: AlphaAnimation? = null
    private var outAnimation: AlphaAnimation? = null
    private var allProductItem: ArrayList<ItemData>? = null
    var str_city: String? = null
    var fesstatus: String? = null
    var str_inst_area : String? = null
    var str_cmpnyself : String? = null
    var str_price : String? = null
    var str_product : String? = null
    var strLost = ""
    var status :String? = null
    var strStatus :String ? = null
    var strReason:String ? =null
    var frwaws:String? = null
    var renewal:String? = null
    var polockdate:String? = null
    var reas:String? = null
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
    var list_of_salutation = arrayOf("Select Salutation","Mr.", "Mrs.", "Miss")
    var list_of_salutation_id = arrayOf("0","1","2","3")
    var str_media : String? = null
    var list_of_redundancy = arrayOf("Select Option","Yes","No")
    var list_of_redundancy_value = arrayOf("","1","0")
    var list_of_media = arrayOf("Select Media","Fibre","RF")
    var list_of_media_value = arrayOf("0","1","2")
    var ext_serv_one = arrayOf("Jio", "ACT Fibernet","N.A",
            "Others","Airtel","Aircel","BSNL", "Hathway","MTNL","Nextra",
            "Reliance Communications","Sify","Tata Communications","Tata DOCOMO",
            "Tikona Infinet","Vodafone")

    var ext_serv_one_value = arrayOf("111260000",
            "569480014","569480012","569480013","569480000","569480002",
            "569480003","569480004","569480005","569480006","569480007",
            "569480008","569480009","569480010","569480011","569480001")

    var list_of_selfpo = arrayOf("Select Option","Yes","No")
    var list_of_selfpo_value = arrayOf("0","1","2")

    var list_of_state = arrayOf("State Name", "Andhra Pradesh","Bihar","Delhi"
            ,"Gujarat","Haryana","Jammu and Kashmir","Karnataka"
            ,"Kerala", "Madhya Pradesh","Maharashtra","Odisha", "Other*",
            "Punjab","Rajasthan","Tamil Nadu", "Telangana","Uttar Pradesh"
            ,"Uttarakhand","West Bengal")

    var list_state_code = arrayOf("0","100009","100021","100004", "100015","100008",
            "100011","100007", "100012","100014","100002","100026",
            "100017","100025", "100010", "100003","100023","100006",
            "100016","100013")
    var list_firm_type = arrayOf("Select Firm Type","Proprietorship","Partnership","Pvt Ltd","Ltd","Trust","Individual")
    var list_firm_type_value = arrayOf("1","2","3","4","5","6")
    var list_of_cust_segment = arrayOf("Select Customer Segment","SDWAN","SMB","Media","LA","SP")
    var list_cust_seg_value = arrayOf("","111260004","111260000","111260001","111260002","111260003")

    var lostCode = arrayOf("569480006","4","569480007","569480008","569480009","569480010","569480011",
            "569480012","569480000","569480001","569480002","569480003","569480004",
            "569480005","5")

    var lost = arrayOf("Lost Opportunity","Customer Found Our Product Costly","cancelled","Location Is Non-RFS",
            "Customer Looking For Promotional Offers","Customer Wanted Information Only",
            "Existing Customer"," Customer Do Not Have Required Docuemnts","Customer Is defaulter",
            "Duplicate Lead","Incorrect Demographic Details","Broadband-Home User",
            "Customer Not Interested","Customer Moved To Other Service Provider","Delay in Response","Out-Sold")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.oppurtunity_demo_fragment)

        searchtoolbaropp.rl_back.setOnClickListener(this)
        searchtoolbaropp.tv_lang.text= AppConstants.OPPURTUNUTY
        val extras = intent.extras
        if (extras != null) {
             str_Opp_Id = extras.getString("OppId")
             str_Lead_Id = extras.getString("LeadId")
        }
        listener()
        getOppurtunity()
        init()
        flr.setOnClickListener {
           caf()
        }
    }
    fun caf(){
        val intent = Intent(this, CAFActivity::class.java)
        val bundle = Bundle()
        bundle.putString("OppId", str_Opp_Id)
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
        layout_opp_cntct_person.et_building_nm.setOnClickListener { layout_opp_cntct_person.sp_opbuilding.performClick() }
        layout_opp_cntct_person.sp_opbuilding.onItemSelectedListener = this
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

        layout_product_line.add_procuct.setOnClickListener{
            addProduct()
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

        tv_opp_submit.setOnClickListener{
            if(fesstatus=="1") {
                if(strOperationCity=="true"){
                    if(strArea == "Other" && strBuilding == "Other" && (strCreateAreaOrBuilding=="false")) {
                        Toast.makeText(this@OpportunityActivity, "For DOA submission you need to add Area or Building as they are still showing as Other", Toast.LENGTH_SHORT).show()
                    }else{
                        submitApproval()
                    }
                }else if(strOperationCity=="false"){
                    if (/* strBusinessSement != "SDWAN"
                        &&*/ (strArea == "Other") && (strBuilding == "Other")
                        && (strCreateAreaOrBuilding=="false")/* && strTPFeasibilty.isNotBlank()*/){
                        fab_create_society.visibility=View.VISIBLE
                    }
                }
            }else{
                Toast.makeText(this@OpportunityActivity, "Please Complete the Feasibility Record", Toast.LENGTH_SHORT).show()
            }
        }

        fab_create_society.setOnClickListener{
            str_createbuilding=true
            save()
        }

        et_lost.setOnClickListener { sp_lost.performClick() }
        sp_lost.onItemSelectedListener = this

        tv_opp_save.setOnClickListener  {
            save()
        }
    }

    private fun save(){
        val topic = opp_contact_info_row.et_op_toptic.text.toString()
        val oppId =opp_contact_info_row.et_oppurtunity_ID.text.toString()
        val salutation = str_salutation.toString()
        val mobile = layout_opp_cntct_person.et_mob_num.text.toString()
        val media = str_media.toString()
        val industry = str_industry_type.toString()
        val floor = layout_opp_cntct_person.et_floor.text.toString()
        val firmtype = str_firm_type.toString()
        val extprovider = str_serv_pro.toString()
        val email = layout_opp_cntct_person.et_eml_id.text.toString()
        val city = str_city_code.toString()
        val area = str_inst_area.toString()
        val building = str_inst_building_nm.toString()
        val pincode = layout_opp_cntct_person.et_op_pincode.text.toString()
        val buildingnumber = layout_opp_cntct_person.et_build_num.text.toString()
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
        if(topic.isBlank()){
            Toast.makeText(this@OpportunityActivity, "Please enter Topic", Toast.LENGTH_SHORT).show()
        }else if(salutation.isBlank()||salutation=="Select Salutation"){
            Toast.makeText(this@OpportunityActivity, "Please enter Salutation", Toast.LENGTH_SHORT).show()
        }else if(contactperson.isBlank()){
            Toast.makeText(this@OpportunityActivity, "Please enter Contact Person", Toast.LENGTH_SHORT).show()
        }else if(email.isBlank()){
            Toast.makeText(this@OpportunityActivity, "Please enter Email Id", Toast.LENGTH_SHORT).show()
        }else if(mobile.isBlank()){
            Toast.makeText(this@OpportunityActivity, "Please enter Mobile Number", Toast.LENGTH_SHORT).show()
        }else if(state.isBlank()||state=="Select State"||(state=="null")){
            Toast.makeText(this@OpportunityActivity, "Please enter State", Toast.LENGTH_SHORT).show()
        }else if(city.isBlank()|| city=="Select City"||(city=="null")){
            Toast.makeText(this@OpportunityActivity, "Please enter City", Toast.LENGTH_SHORT).show()
        }else if(area.isBlank()||area=="Select Area"||(area=="null")){
            Toast.makeText(this@OpportunityActivity, "Please enter Area", Toast.LENGTH_SHORT).show()
        }else if(building.isBlank()||building=="Select Building"||(building=="null")){
            Toast.makeText(this@OpportunityActivity, "Please enter Building", Toast.LENGTH_SHORT).show()
        }else if(buildingnumber.isBlank()||(buildingnumber=="null")){
            Toast.makeText(this@OpportunityActivity, "Please enter Building Number", Toast.LENGTH_SHORT).show()
        }else if(floor.isBlank()){
            Toast.makeText(this@OpportunityActivity, "Please enter Floor", Toast.LENGTH_SHORT).show()
        }else if(pincode.isBlank()){
            Toast.makeText(this@OpportunityActivity, "Please enter PinCode", Toast.LENGTH_SHORT).show()
        }else if(firmtype.isBlank()||firmtype=="Select FirmType"||(firmtype=="null")){
            Toast.makeText(this@OpportunityActivity, "Please enter FirmType", Toast.LENGTH_SHORT).show()
        }else if(industry.isBlank()||industry=="Select Industry Type"||(industry=="null")){
            Toast.makeText(this@OpportunityActivity, "Please enter Industry", Toast.LENGTH_SHORT).show()
        }else if(customersegment.isBlank()||customersegment=="Select Customer Segment"||(customersegment=="null")){
            Toast.makeText(this@OpportunityActivity, "Please enter Customer Segment", Toast.LENGTH_SHORT).show()
        }else if(uptmsla.isBlank()){
            Toast.makeText(this@OpportunityActivity, "Please enter Uptime SLA", Toast.LENGTH_SHORT).show()
        }else if(extprovider.isBlank()||extprovider=="Select Ex Provider"||(extprovider=="null")){
            Toast.makeText(this@OpportunityActivity, "Please enter Service Provider", Toast.LENGTH_SHORT).show()
        }else if(frwal.isBlank()||frwal=="Select Option"||(frwal=="null")){
            Toast.makeText(this@OpportunityActivity, "Please enter FireWall", Toast.LENGTH_SHORT).show()
        }else if(comanyself.isBlank()||comanyself=="Select Option"||(comanyself=="null")){
            Toast.makeText(this@OpportunityActivity, "Please enter Company Self", Toast.LENGTH_SHORT).show()
        }else if(polock.isBlank()||(polock=="null")){
            Toast.makeText(this@OpportunityActivity, "Please enter PoLock", Toast.LENGTH_SHORT).show()
        }else if(poNext.isBlank()||(poNext=="null")){
            Toast.makeText(this@OpportunityActivity, "Please enter Po Next", Toast.LENGTH_SHORT).show()
        }else if(strPrice.isBlank()||strPrice=="Select Price List"||(strPrice=="null")){
            Toast.makeText(this@OpportunityActivity, "Please enter Price ", Toast.LENGTH_SHORT).show()
        }else if(isdiscount&&reason.isBlank()){
            Toast.makeText(this@OpportunityActivity, "Please enter Reason ", Toast.LENGTH_SHORT).show()
        }
        else {
                updateOppurtunity(topic,oppId,salutation,mobile,media,industry,floor,firmtype,extprovider,email,
                city,area,building,pincode,buildingnumber,contactperson,customersegment,uptmsla,frwal,comanyself,
                polock,firewallAwc,poNext,redundancy,state,price,reason)
        }
    }

    fun getGenerateQuote () {
        //inProgress()
        val getProductListRequest = str_Opp_Id?.let { GetProductListRequest(Constants.GET_QUOTE, Constants.AUTH_KEY, it,"Target@2021#@","manager1") }
        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getQuote(getProductListRequest)
        call.enqueue(object : Callback<GenQuoteResponse?> {
            override fun onResponse(call: Call<GenQuoteResponse?>, response: Response<GenQuoteResponse?>) {
               // OutProgress()
                if (response.isSuccessful && response.body() != null) {
                    try {
                        if(response.body()?.Response?.StatusCode==200) {
                          //  val msg = response.body()!!.Response.Message
                            generatequote = response.body()!!.Response.Data
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

    fun getAllfeasList () {
       // inProgress()
        val getProductListRequest = str_Opp_Id?.let { GetProductListRequest(Constants.GET_FEASIBILITY, Constants.AUTH_KEY, it,"Target@2021#@","manager1") }
        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getAllFeasibility(getProductListRequest)
        call.enqueue(object : Callback<GetFeasibiltyResponse?> {
            override fun onResponse(call: Call<GetFeasibiltyResponse?>, response: Response<GetFeasibiltyResponse?>) {
             //   OutProgress()
                if (response.isSuccessful && response.body() != null) {
                    try {
                        if(response.body()?.Response?.StatusCode==200) {
                           // val msg = response.body()!!.Response.Message
                            allFeasibility = response.body()!!.Response.Data
                             fesstatus = allFeasibility?.get(0)?.FeasibilityStatus
                             reas = layout_product_line.et_op_reason.text.toString()
                            if(allFeasibility?.size!=null){
                                binding.feasibility.addFes.visibility=View.GONE
                            }
                            if ((fesstatus == "1") && (reas!=null)&&(str_discount!=null)&&(allFeasibility?.size!=null)){
                                tv_opp_submit.visibility=View.VISIBLE
                            }
                            if(fesstatus=="1") {
                                if(strOperationCity=="true"){
                                    if(strArea == "Other" && strBuilding == "Other") {
                                        Toast.makeText(this@OpportunityActivity, "For DOA submission you need to add Area or Building as they are still showing as Other", Toast.LENGTH_SHORT).show()
                                    }
                                }else if(strOperationCity=="false"){
                                    if (/* strBusinessSement != "SDWAN"
                        &&*/    (strArea == "Other") && (strBuilding == "Other")
                                        && (strCreateAreaOrBuilding=="false")/* && strTPFeasibilty.isNotBlank()*/){
                                        fab_create_society.visibility=View.VISIBLE
                                    }
                                }
                            }else{
                                Toast.makeText(this@OpportunityActivity, "Please Complete the Feasibility Record", Toast.LENGTH_SHORT).show()
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
                            setFesAdapter(allFeasibility!!, this@OpportunityActivity)
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

    private fun setFesAdapter(allFeasibility: ArrayList<FeasData>, context: Context?) {
        binding.feasibility.rvAddFes.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = GetAllFeasibiltyAdapter(allFeasibility,context)
        }
    }


    fun getDOA () {
       //inProgress()
        val getProductListRequest = str_Opp_Id?.let { GetProductListRequest(Constants.GETAPPROVAL, Constants.AUTH_KEY, it,"Target@2021#@","manager1") }
        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getApproval(getProductListRequest)
        call.enqueue(object : Callback<GetApprovalRersponse?> {
            override fun onResponse(call: Call<GetApprovalRersponse?>, response: Response<GetApprovalRersponse?>) {
             // OutProgress()
                if (response.isSuccessful && response.body() != null) {
                    try {
                        if(response.body()?.Response?.StatusCode==200) {
                          //  val msg = response.body()!!.Response.Message
                            allApproval = response.body()!!.Response.Data
                            /*if(allApproval?.size!=null){
                                tv_opp_submit.visibility=View.GONE
                            }*/
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
           adapter = allApproval?.let { GetDOAViewAdapter(it,context,str_Opp_Id) }
        }
    }

    private fun setQuoteAdapter(generatequote: ArrayList<QuoteData>, context: Context?) {
        binding.quote.rvAddQuote.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = str_Opp_Id?.let { GetAllQuoteAdapter(generatequote,context, it) }
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
        }

    }

    override fun onClick(p0: View?) {
        if (p0?.id == R.id.rl_back) {
            next()
        }
    }




    private fun updateOppurtunity(topic: String, oppId: String, salutation: String, mobile: String, media: String,
                                  industry: String, floor: String, firmtype: String, extprovider: String, email: String,
                                  city: String, area: String, building: String, pincode: String, buildingnumber: String,
                                  contactperson: String, customersegment: String, uptmsla: String, frwal: String,
                                  comanyself: String, polock: String, firewallAwc: String, poNext: String,
                                  redundancy: String, state: String, price: String, reason: String) {
          val updateOppurtunity = UpdateOppurtunity(Constants.UPDATE_OPPURTUNITY,Constants.AUTH_KEY,
                area,buildingnumber,building,city,comanyself,contactperson,"10001",customersegment,
                email,extprovider,frwal,frwaws,firmtype,floor,industry,"",media,mobile,oppId,
                "Target@2021#@",
              polockdate,renewal,pincode,price,redundancy,salutation,state,topic,uptmsla,"manager1",reason,str_createbuilding)

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.updateOppurtunity(updateOppurtunity)
        call.enqueue(object : Callback<CreateLeadResponse?> {
            override fun onResponse(call: Call<CreateLeadResponse?>, response: Response<CreateLeadResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        val img = response.body()!!.Response.Message
                        Log.e("image", img)
                        Toast.makeText(this@OpportunityActivity, img, Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@OpportunityActivity, OpportunityActivity::class.java)
                        val bundle = Bundle()
                        bundle.putString("OppId",str_Opp_Id )
                        intent.putExtras(bundle)
                        startActivity(intent)
                        finish()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<CreateLeadResponse?>, t: Throwable) {
               // binding.opprogressLayout.progressOverlay.visibility=View.GONE
                Log.e("RetroError", t.toString())
            }
        })
    }

    fun addProduct() {
        //inProgress()
        val addProductRequest = str_product?.let {
            str_Opp_Id?.let { it1 -> AddProductRequest(Constants.CREATE_OPPPRODUCT,Constants.AUTH_KEY, it1,"Target@2021#@",it,"manager1") } }

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.addProduct(addProductRequest)
        call.enqueue(object : Callback<ProdctResponse?> {
            override fun onResponse(call: Call<ProdctResponse?>, response: Response<ProdctResponse?>) {
                val msg = response.body()!!.Response.Message
                //OutProgress()
                if (response.body()?.Response?.StatusCode==200) {
                    try {
                      //  Toast.makeText(this@OpportunityActivity, msg, Toast.LENGTH_LONG).show()
                        val intent = Intent(this@OpportunityActivity, OpportunityActivity::class.java)
                        val bundle = Bundle()
                        bundle.putString("OppId",str_Opp_Id )
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


    fun addFeasibility() {
        inProgress()
       val addProductRequest =
        str_Opp_Id?.let { it1 -> AddProductRequest(Constants.GENERATE_FEASIBILITY,Constants.AUTH_KEY, it1,"Target@2021#@","","manager1")  }

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.addProduct(addProductRequest)
        call.enqueue(object : Callback<ProdctResponse?> {
            override fun onResponse(call: Call<ProdctResponse?>, response: Response<ProdctResponse?>) {
                OutProgress()
                val msg = response.body()!!.Response.Message
                if (response.body()?.Response?.StatusCode==200) {
                    try {
                        Toast.makeText(this@OpportunityActivity, msg, Toast.LENGTH_LONG).show()
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



    fun addQuote() {
      inProgress()
        val createQuoteRequest =
                str_Opp_Id?.let { it1 -> CreateQuoteRequest(Constants.CREATE_QUOTE,Constants.AUTH_KEY, it1,"Target@2021#@","manager1")  }

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.addQuote(createQuoteRequest)
        call.enqueue(object : Callback<ProdctResponse?> {
            override fun onResponse(call: Call<ProdctResponse?>, response: Response<ProdctResponse?>) {
                val msg = response.body()!!.Response.Message
               OutProgress()
                if (response.body()?.Response?.StatusCode==200) {
                    try {
                        Toast.makeText(this@OpportunityActivity, msg, Toast.LENGTH_LONG).show()
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

    fun createApproval() {
        //  inProgress()
        val getProductListRequest =
                str_Opp_Id?.let { it1 -> GetProductListRequest(Constants.CREATEAPPROVAL,Constants.AUTH_KEY, it1,"Target@2021#@","manager1")  }

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.won(getProductListRequest)
        call.enqueue(object : Callback<ProdctResponse?> {
            override fun onResponse(call: Call<ProdctResponse?>, response: Response<ProdctResponse?>) {
                val msg = response.body()!!.Response.Message
                //  OutProgress()
                if (response.body()?.Response?.StatusCode==200) {
                    try {
                        Toast.makeText(this@OpportunityActivity, msg, Toast.LENGTH_LONG).show()
                       // OppAct()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }else{
                  //  Toast.makeText(this@OpportunityActivity, msg, Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ProdctResponse?>, t: Throwable) {
                //    binding.opprogressLayout.progressOverlay.visibility=View.GONE
                Log.e("RetroError", t.toString())
            }
        })
    }

    fun wonOpp() {
        inProgress()
        val getProductListRequest =
                str_Opp_Id?.let { it1 -> GetProductListRequest(Constants.WON_OPPURTUNITY,Constants.AUTH_KEY, it1,"Target@2021#@","manager1")  }

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.won(getProductListRequest)
        call.enqueue(object : Callback<ProdctResponse?> {
            override fun onResponse(call: Call<ProdctResponse?>, response: Response<ProdctResponse?>) {
                val msg = response.body()!!.Response.Message
                OutProgress()
                if (response.body()?.Response?.StatusCode==200) {
                    try {
                        Toast.makeText(this@OpportunityActivity, msg, Toast.LENGTH_LONG).show()
                        caf()
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

    fun submitApproval() {
        inProgress()
        val getProductListRequest =
                str_Opp_Id?.let { it1 -> GetProductListRequest(Constants.CREATEAPPROVAL,Constants.AUTH_KEY, it1,"Target@2021#@","manager1")  }
        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.won(getProductListRequest)
        call.enqueue(object : Callback<ProdctResponse?> {
            override fun onResponse(call: Call<ProdctResponse?>, response: Response<ProdctResponse?>) {
                val msg = response.body()!!.Response.Message
                OutProgress()
                if (response.body()?.Response?.StatusCode==200) {
                    try {
                        Toast.makeText(this@OpportunityActivity, msg, Toast.LENGTH_LONG).show()
                        val intent = Intent(this@OpportunityActivity, OpportunityActivity::class.java)
                        val bundle = Bundle()
                        bundle.putString("OppId",str_Opp_Id )
                        intent.putExtras(bundle)
                        startActivity(intent)
                        finish()
                      //  caf()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }else{
                   // Toast.makeText(this@OpportunityActivity, msg, Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<ProdctResponse?>, t: Throwable) {
                binding.opprogressLayout.progressOverlay.visibility=View.GONE
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

    fun OutProgress(){
        outAnimation = AlphaAnimation(1f, 0f)
        outAnimation?.duration =200
        binding.opprogressLayout.progressOverlay.animation = outAnimation
        binding.opprogressLayout.progressOverlay.visibility = View.GONE
    }

    fun getProductAddedList() {
      //  inProgress()
        val getProductListRequest = GetProductListRequest(Constants.GET_OPPPRODUCT,Constants.AUTH_KEY,str_Opp_Id.toString(),"Target@2021#@","manager1")

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.addProductItem(getProductListRequest)
        call.enqueue(object : Callback<GetProductItemListRes?> {
            override fun onResponse(call: Call<GetProductItemListRes?>, response: Response<GetProductItemListRes?>) {
             //   OutProgress()
                if (response.isSuccessful && response.body() != null) {
                    try {
                        val msg = response.body()!!.Response.Message
                        if (response.body()?.Response?.StatusCode==200) {
                            try {
                               // Toast.makeText(this@OpportunityActivity, msg, Toast.LENGTH_LONG).show()
                                allProductItem = response.body()!!.Response.Data
                                if(allProductItem?.isNotEmpty()==true) {
                                    allProductItem?.forEachIndexed { index, itemData ->

                                        if(!itemData.Discount.startsWith("0.0"))
                                        {
                                            isdiscount=true
                                            return@forEachIndexed
                                        }
                                        println("chk discount:"+itemData.Discount)
                                    }
                                    str_discount= allProductItem?.get(0)?.Discount
                                    setAdapter(allProductItem, this@OpportunityActivity)
                                }

                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }else{
                          //  Toast.makeText(this@OpportunityActivity, msg, Toast.LENGTH_LONG).show()
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
    fun trimLeadingZeros(list: List<Int>): List<Int>? {
        for (i in list.indices) if (list[i] != 0) return list.subList(i, list.size)
        return Collections.emptyList()
    }


    private fun setAdapter(allProductItem: ArrayList<ItemData>?, context: Context?) {
        rv_product_list?.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = allProductItem?.let { GetAllProductItemAdapter(it,context,str_Opp_Id,status) }
        }
    }

    fun lostOppurtunity(loststatus: String) {
      //  inProgress()
        val lostOppurtunityRequest = LostOppurtunityRequest(Constants.LOST_OPPURTUNITY,Constants.AUTH_KEY,str_Opp_Id.toString(),"Target@2021#@",loststatus,"manager1")

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.lostOppurtunityRequest(lostOppurtunityRequest)
        call.enqueue(object : Callback<CommonClassResponse?> {
            override fun onResponse(call: Call<CommonClassResponse?>, response: Response<CommonClassResponse?>) {
              //  OutProgress()
                if (response.isSuccessful && response.body() != null) {
                    try {
                        val msg = response.body()!!.response.message
                        Toast.makeText(this@OpportunityActivity, msg, Toast.LENGTH_LONG).show()
                        val fragmentB = GetAllOppurtunityFrag()
                        supportFragmentManager.beginTransaction()
                                .replace(R.id.fragment_opp, fragmentB)
                                .commit()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<CommonClassResponse?>, t: Throwable) {
              //  binding.opprogressLayout.progressOverlay.visibility=View.GONE
                Log.e("RetroError", t.toString())
            }
        })
    }

    fun getOppurtunity () {
        inProgress()
        val getOppurtunityRequest = GetOppurtunityRequest(Constants.GET_OPPURTUNITY,Constants.AUTH_KEY,str_Opp_Id,"Target@2021#@","manager1",str_Lead_Id)

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getOppurtunity(getOppurtunityRequest)
        call.enqueue(object : Callback<GetOppurtunityResponse?> {
            override fun onResponse(call: Call<GetOppurtunityResponse?>, response: Response<GetOppurtunityResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        OutProgress()
                         if(response.body()?.Response?.StatusCode==200) {
                           // val msg = response.body()!!.Response.Message
                            binding.oppContactInfoRow.oppDetail = response.body()!!.Response
                            binding.layoutOppCntctPerson.contact = response.body()!!.Response
                            binding.layoutOppComyDetails.company = response.body()!!.Response
                            binding.layoutOther.other = response.body()!!.Response
                            binding.layoutProductLine.etOpReason.setText(response.body()?.Response?.Data?.Reason.toString())
                            strArea = response.body()?.Response?.Data?.Area.toString()
                            strCity = response.body()?.Response?.Data?.City.toString()
                            strBuilding = response.body()?.Response?.Data?.Buildingname.toString()
                            strIndustry = response.body()?.Response?.Data?.Industry.toString()
                            strPrice = response.body()?.Response?.Data?.PriceList.toString()
                            strStatus  = response.body()?.Response?.Data?.Status.toString()
                             strReason = response.body()?.Response?.Data?.Reason.toString()
                            str_Opp_Id = response.body()?.Response?.Data?.OppId.toString()
                            strBuildingStatus = response.body()?.Response?.Data?.Building_BuildingStatus.toString()
                            strBusinessSement = response.body()?.Response?.Data?.CustomerSegment.toString()
                            strCreateAreaOrBuilding =  response.body()?.Response?.Data?.CreateAreaOrBuilding.toString()
                            strTPFeasibilty =  response.body()?.Response?.Data?.TPFeasibilty.toString()
                             val sal = response.body()?.Response?.Data?.Salutation
                             if(strBuilding.isNotEmpty()){
                                 strBuilding.let { getPriceList(it) }
                             }
                             getIndustryTpe(strIndustry)
                             var salutationPosition = 0
                             list_of_salutation_id.forEachIndexed { index, s ->
                                if (index == sal?.toInt()) {
                                    salutationPosition = index
                                    return@forEachIndexed
                                }
                            }

                            val salutationAdapter = ArrayAdapter(this@OpportunityActivity, android.R.layout.simple_spinner_item, list_of_salutation)
                            salutationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            layout_opp_cntct_person.sp_opsalutation?.adapter = salutationAdapter
                            layout_opp_cntct_person.sp_opsalutation.setSelection(salutationPosition)
                            salutationAdapter.notifyDataSetChanged()

                            val strContactstate = response.body()?.Response?.Data?.State
                            var cntstatePosition = 0
                            list_of_state.forEachIndexed { index, s ->
                                if (s == strContactstate) cntstatePosition = index
                            }
                            val cntstateAdapter = ArrayAdapter(this@OpportunityActivity, android.R.layout.simple_spinner_item, list_of_state)
                            cntstateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            layout_opp_cntct_person.sp_opstate.adapter = cntstateAdapter
                            layout_opp_cntct_person.sp_opstate.setSelection(cntstatePosition)
                            cntstateAdapter.notifyDataSetChanged()

                            val firm = response.body()?.Response?.Data?.Firmtype
                            var firmPosition = 0
                            list_firm_type_value.forEachIndexed { index, s ->
                                if (index == firm?.toInt()) {
                                    firmPosition = index
                                    return@forEachIndexed
                                }
                            }
                            val firmAdapter = ArrayAdapter(this@OpportunityActivity, android.R.layout.simple_spinner_item, list_firm_type)
                            firmAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            layout_opp_comy_details.sp_opfrmtype?.adapter = firmAdapter
                            layout_opp_comy_details.sp_opfrmtype.setSelection(firmPosition)
                            firmAdapter.notifyDataSetChanged()

                            val customer = response.body()?.Response?.Data?.CustomerSegment
                            var customersegPosition = 0
                            list_cust_seg_value.forEachIndexed { index, s ->
                                if (s == customer) {
                                    customersegPosition = index
                                    return@forEachIndexed
                                }
                            }

                            val custSeg = ArrayAdapter(this@OpportunityActivity, android.R.layout.simple_spinner_item, list_of_cust_segment)
                            custSeg.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            layout_opp_comy_details.sp_opcustseg!!.adapter = custSeg
                            layout_opp_comy_details.sp_opcustseg.setSelection(customersegPosition)
                            custSeg.notifyDataSetChanged()

                            val strmedia = response.body()?.Response?.Data?.Media
                            var mediaPosition = 0
                            list_of_selfpo_value.forEachIndexed { index, s ->
                                if (index == strmedia?.toInt()) {
                                    mediaPosition = index
                                    return@forEachIndexed
                                }
                            }
                            val media = ArrayAdapter(this@OpportunityActivity, android.R.layout.simple_spinner_item, list_of_media)
                            media.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            layout_other.sp_opmedia?.adapter = media
                            layout_other.sp_opmedia.setSelection(mediaPosition)
                            media.notifyDataSetChanged()

                            val lostadapter = ArrayAdapter(this@OpportunityActivity, android.R.layout.simple_spinner_item, lost)
                            lostadapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            sp_lost.adapter = lostadapter
                            val provider1 = response.body()?.Response?.Data?.Existingprovider
                            var provOnePosition = 0
                            ext_serv_one_value.forEachIndexed { index, s ->
                                if (s == provider1) {
                                    provOnePosition = index
                                    return@forEachIndexed
                                }
                            }
                            val serv1 = ArrayAdapter(this@OpportunityActivity, android.R.layout.simple_spinner_item, ext_serv_one)
                            serv1.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            layout_other.sp_opexservice?.adapter = serv1
                            layout_other.sp_opexservice.setSelection(provOnePosition)
                            serv1.notifyDataSetChanged()

                            val strCompanySelf = response.body()?.Response?.Data?.CompanySelf
                            var companySelfPosition = 0
                            list_of_selfpo_value.forEachIndexed { index, s ->
                                if (index == strCompanySelf?.toInt()) {
                                    companySelfPosition = index
                                    return@forEachIndexed
                                }
                            }

                            val companySelfadpter = ArrayAdapter(this@OpportunityActivity, android.R.layout.simple_spinner_item, list_of_selfpo)
                            companySelfadpter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            layout_other.sp_opselfpo?.adapter = companySelfadpter
                            layout_other.sp_opselfpo.setSelection(companySelfPosition)
                            companySelfadpter.notifyDataSetChanged()

                            val strFirewall = response.body()?.Response?.Data?.Firewall
                            var FirewallPosition = 0
                            list_of_selfpo_value.forEachIndexed { index, s ->
                                if (index == strFirewall?.toInt()) {
                                    FirewallPosition = index
                                    return@forEachIndexed
                                }
                            }

                            val frwaladpter = ArrayAdapter(this@OpportunityActivity, android.R.layout.simple_spinner_item, list_of_selfpo)
                            frwaladpter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            layout_other.sp_opfirewal?.adapter = frwaladpter
                            layout_other.sp_opfirewal.setSelection(FirewallPosition)
                            frwaladpter.notifyDataSetChanged()

                            val redundancy = response.body()?.Response?.Data?.Redunancy
                            val redundancyadpter = ArrayAdapter(this@OpportunityActivity, android.R.layout.simple_spinner_item, list_of_redundancy)
                            redundancyadpter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                            layout_other.sp_opredundancy?.adapter = redundancyadpter
                            if (redundancy == true) {
                                layout_other.sp_opredundancy.setSelection(1)
                            } else {
                                layout_other.sp_opredundancy.setSelection(2)
                            }
                            redundancyadpter.notifyDataSetChanged()
                             val frwcdate = response.body()?.Response?.Data?.FirewallAwc
                             if(frwcdate?.isNotEmpty() == true){
                                 val fr = frwcdate.split("-")
                                 val date = fr.get(0)
                                 val month = fr.get(1)
                                 val year = fr.get(2)
                                 layout_other.et_frwalaws.setText("$date-$month-$year")
                             }
                             val polockDate = response.body()?.Response?.Data?.PoLock
                             if(polockDate?.isNotEmpty()==true){
                                 val split1 = polockDate?.split("-")
                                 val date1 = split1.get(0)
                                 val month1 = split1.get(1)
                                 val year1 = split1.get(2)
                                 layout_other.et_polock.setText("$date1-$month1-$year1")
                             }
                             val ponextdate = response.body()?.Response?.Data?.PoNext
                             if(ponextdate?.isNotEmpty()==true){
                                 val split2 = ponextdate.split("-")
                                 val date2 = split2.get(0)
                                 val month2 = split2.get(1)
                                 val year2 = split2.get(2)
                                 layout_other.et_oprenewal.setText("$date2-$month2-$year2")
                             }
                             getGenerateQuote()
                             getAllfeasList()
                             getDOA()

                             if((strBuildingStatus=="C-RFS"||strBuildingStatus=="P-RFS" ||
                                         strBuildingStatus=="B-RFS"||strBuildingStatus=="A-RFS") && redundancy==false){
                                     binding.feasibility.addFes.visibility=View.GONE
                                 if((reas!=null)&&(str_discount!=null)&&(allFeasibility?.size!=null)){
                                     tv_opp_submit.visibility=View.VISIBLE
                                 }
                                 }else if((strBuildingStatus=="C-RFS"||strBuildingStatus=="P-RFS" ||
                                         strBuildingStatus=="B-RFS"||strBuildingStatus=="A-RFS")
                                         && redundancy==true){
                                     binding.feasibility.addFes.visibility=View.VISIBLE
                                 }else if(strBuildingStatus=="Non-RFS"){
                                     binding.feasibility.addFes.visibility = View.VISIBLE
                                 }
                            if(strStatus=="Lost"||strStatus=="Won"){
                                if(strStatus=="Lost"){
                                    flr.visibility=View.GONE
                                }else{
                                    flr.visibility=View.VISIBLE
                                }
                                tv_opp_save.visibility=View.GONE
                                tv_won.visibility = View.GONE
                                opp_lost.visibility=View.GONE
                                layout_product_line.add_procuct.visibility=View.GONE
                                binding.quote.addQuote.visibility=View.GONE
                                binding.feasibility.addFes.visibility=View.GONE
                                layout_product_line.sp_opproduct.isEnabled= false
                                layout_product_line.et_product_list.isEnabled= false

                                tv_opp_submit.visibility=View.GONE
                                fab_create_society.visibility=View.GONE
                                status ="1"
                                locked()

                            }else{
                                opp_lost.visibility=View.VISIBLE
                                tv_opp_save.visibility=View.VISIBLE
                                tv_won.visibility = View.VISIBLE
                                layout_product_line.add_procuct.visibility=View.VISIBLE
                                binding.quote.addQuote.visibility=View.VISIBLE
                              //  binding.feasibility.addFes.visibility=View.VISIBLE
                                flr.visibility=View.GONE
                                status="2"
                                Calender()

                            }

                        }else{
                          Toast.makeText(this@OpportunityActivity, response.body()?.Response?.Message,Toast.LENGTH_LONG).show()
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
        tv_submit.visibility=View.GONE
      //  add_dao.visibility = View.GONE
        tv_won.visibility = View.GONE
    }

    fun getIndustryTpe(strIndusry: String) {
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
                        industryid.add(" ")
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


    fun getBuilding(areaname: String, areaCode: String?) {
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
                            for (item in buildingList!!)
                                building?.add(item.BuildingName)
                        }
                        if (buildingList != null) {
                            for(it1 in buildingList!!)
                                buildingCode?.add(it1.BuildingCode)
                        }

                        var buildPosition=0
                        building!!.forEachIndexed { index, s ->
                            if(s==strBuilding)buildPosition=index
                        }

                        val adapter12 = ArrayAdapter(this@OpportunityActivity, android.R.layout.simple_spinner_item, building!!)
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        layout_opp_cntct_person.sp_opbuilding.adapter = adapter12
                        layout_opp_cntct_person.sp_opbuilding.setSelection(buildPosition)
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

    fun getProductList() {

        val getProductListRequest = str_Opp_Id?.let { GetProductListRequest(Constants.GET_PRODUCTLIST,Constants.AUTH_KEY, it,"Target@2021#@","manager1") }

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

    fun getPriceList(buildingname: String) {
        val getProductListRequest = GetPriceListRequest(Constants.GET_PRICELIST,Constants.AUTH_KEY,buildingname,"Target@2021#@","manager1")

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
                            getProductAddedList()
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
    fun  Calender(){
        try{
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        layout_other.et_polock.setOnClickListener {
            val dpd = DatePickerDialog(this, { view, year, monthOfYear, dayOfMonth ->
                val mnth = monthOfYear+1
                layout_other.et_polock.setText("$dayOfMonth-$mnth-$year")
                val trgt =  layout_other.et_polock.text.toString()
                val split = trgt.split("-")
                val dateee = split[0]
                val month1 = split[1]
                val year1 = split[2]
                polockdate=(year1+ "-" + month1+"-" +dateee)
            }, year, month, day)
            dpd.show()
        }

        layout_other.et_oprenewal.setOnClickListener {
            val dpd = DatePickerDialog(this, { view, year, monthOfYear, dayOfMonth ->
                val mnth = monthOfYear+1
                layout_other.et_oprenewal.setText("$dayOfMonth-$mnth-$year")
                val trgt =  layout_other.et_oprenewal.text.toString()
                val split = trgt.split("-")
                val dateee = split[0]
                val month1 = split[1]
                val year1  = split[2]
                renewal=(year1+ "-" +month1+ "-" + dateee)
            }, year, month, day)
            dpd.show()
        }

        layout_other.et_frwalaws.setOnClickListener {
            val dpd = DatePickerDialog(this, { view, year, monthOfYear, dayOfMonth ->
                val mnth = monthOfYear+1
                layout_other.et_frwalaws.setText("$dayOfMonth-$mnth-$year")
                val trgt =  layout_other.et_frwalaws.text.toString()
                val split = trgt.split("-")
                val dateee = split[0]
                val month1 = split[1]
                val year1 =  split[2]
                frwaws=(year1+ "-" +month1+ "-" +dateee)
            }, year, month, day)
            dpd.show()
        }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (parent?.id == R.id.sp_opsalutation) {
            layout_opp_cntct_person.et_opsalutation.setText(list_of_salutation.get(position))
            str_salutation = list_of_salutation_id.get(position )
        }else if (parent?.id == R.id.sp_opcustseg) {
            layout_opp_comy_details.et_cust_segmnt.setText(list_of_cust_segment.get(position))
            str_customer_segmentid = list_cust_seg_value.get(position )
        }else if(parent?.id == R.id.sp_opmedia){
             layout_other.et_media.setText(list_of_media.get(position))
             str_media =  list_of_media_value.get(position )
        }else if(parent?.id == R.id.sp_opexservice){
             layout_other.et_ext_service.setText(ext_serv_one.get(position))
             str_serv_pro = ext_serv_one_value.get(position)
        }else if(parent?.id == R.id.sp_opfirewal){
            layout_other.et_firewl.setText(list_of_selfpo.get(position))
            str_cust_frwl = list_of_selfpo_value.get(position)
            if(list_of_selfpo.get(position)=="Yes"){
               linearaws.visibility= View.VISIBLE
            }else{
                linearaws.visibility= View.GONE
            }
        }else if(parent?.id == R.id.sp_opredundancy){
            layout_other.et_redundancy_required.setText(list_of_redundancy.get(position))
            strRedundancy = list_of_redundancy.get(position)
            str_redundancy=list_of_redundancy_value.get(position )
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
        }else if(parent?.id == R.id.sp_opfrmtype){
            layout_opp_comy_details.et_firm_type.setText(list_firm_type.get(position))
           str_firm_type = list_firm_type_value.get(position )
        }else if(parent?.id == R.id.sp_opindusty){
            layout_opp_comy_details.et_op_industype.setText(instryname.get(position))
            str_industry_type = industryid.get(position)
        }else if(parent?.id == R.id.sp_opstate){
            layout_opp_cntct_person.et_op_state.setText(list_of_state.get(position))
            strcontact_state = list_of_state.get(position)
            strcontact_stateCode = list_state_code.get(position)
            getCity(strcontact_stateCode.toString())
        } else if(parent?.id == R.id.sp_opcity){
            layout_opp_cntct_person.et_city.setText(city?.get(position))
            str_city = city?.get(position).toString()
            str_city_code = cityCode?.get(position)
            strOperationCity = Oprationcity?.get(position).toString()
            getArea(str_city, str_city_code)
        }else if(parent?.id == R.id.sp_oparea){
            layout_opp_cntct_person.et_area.setText(area?.get(position))
            str_inst_area = areaCode?.get(position)
            val areaname: String = area?.get(position).toString()
            getBuilding(areaname,str_inst_area)
        } else if(parent?.id == R.id.sp_opbuilding){
            layout_opp_cntct_person.et_building_nm.setText(building?.get(position))
            str_inst_building_nm = buildingCode?.get(position)
            val buildingname = building?.get(position)
            if(buildingname?.isNotEmpty() == true){
                buildingname.let { getPriceList(it) }
            }
        }else if(parent?.id == R.id.sp_opselfpo){
            layout_other.etselfpo.setText(list_of_selfpo.get(position))
            str_cmpnyself =  list_of_selfpo_value.get(position)
        }else if(parent?.id == R.id.sp_price){
             layout_product_line.et_price_list.setText(price?.get(position))
             str_price =  price?.get(position)
        }else if(parent?.id == R.id.sp_lost){
            if (position != 0) strLost = "" + lostCode.get(position - 1) else strLost= " "
            if(strLost.isNotEmpty()) {
                val loststatus:String
                if (position != 0) loststatus = "" + lostCode.get(position - 1) else loststatus= " "
                if(loststatus.isBlank()||loststatus.isEmpty()){

                }else{
                    lostOppurtunity(loststatus)
                }
            }

        }else if(parent?.id == R.id.sp_opproduct){
            layout_product_line.et_product_list.setText(productId?.get(position))
            str_product =  productId?.get(position)
        }

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    fun getArea(str_city: String?, str_city_code: String?) {
        val getLeadAreaRequest =   GetLeadAreaRequest(Constants.Get_AREA,Constants.AUTH_KEY,
                str_city_code.toString(), str_city.toString() ,"","manager1","Target@2021#@",false)

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


    fun getCity(stateCode: String) {
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
                        Oprationcity = ArrayList<String>()
                        city?.add("Select City")
                        cityCode?.add("")
                        Oprationcity?.add("")
                        for (item in cityList!!) {
                            city?.add(item.CityName)
                            cityCode?.add(item.CityCode)
                            Oprationcity?.add(item.IsOperationalCity.toString())
                        }
                        var cityPosition=0
                        city!!.forEachIndexed { index, s ->
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
        builder.setMessage("Do you want to go back to the previous screen?")
        builder.setPositiveButton(
            "Yes",
            DialogInterface.OnClickListener { dialog, which ->
                val intent = Intent(this, OppTabActivity::class.java)
                startActivity(intent)
                finish()
            })
        builder.setNegativeButton(
            "No",
            DialogInterface.OnClickListener { dialog, which ->
                dialog.cancel()
            })
        val alert: AlertDialog = builder.create()
        alert.show()
    }
}