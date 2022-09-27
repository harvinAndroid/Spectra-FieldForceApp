package com.spectra.fieldforce.salesapp.activity

import GetAllLanAdapter
import GetAllWanAdapter
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import com.spectra.fieldforce.databinding.MainSiteBinding
import com.spectra.fieldforce.salesapp.adapter.GetAllProductItemAdapter
import com.spectra.fieldforce.salesapp.fragment.LanFrag
import com.spectra.fieldforce.salesapp.fragment.WanFrag
import com.spectra.fieldforce.salesapp.model.*
import com.spectra.fieldforce.utils.AppConstants
import com.spectra.fieldforce.utils.Constants
import kotlinx.android.synthetic.main.main_site.*
import kotlinx.android.synthetic.main.op_product_line_item_row.*
import kotlinx.android.synthetic.main.op_product_line_item_row.view.*
import kotlinx.android.synthetic.main.opp_add_doa.*
import kotlinx.android.synthetic.main.opp_add_doa.view.*
import kotlinx.android.synthetic.main.opp_add_quote.*
import kotlinx.android.synthetic.main.opp_add_quote.view.*
import kotlinx.android.synthetic.main.opp_site_details.*
import kotlinx.android.synthetic.main.opp_site_details.view.*
import kotlinx.android.synthetic.main.update_leadtoolbar.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.util.ArrayList


class SiteOpportunityAct: AppCompatActivity(), View.OnClickListener, AdapterView.OnItemSelectedListener {
    private lateinit var binding: MainSiteBinding

    private var strOppId : String? = null
    private var strSiteId : String? = null
    private var userName : String? = null
    private var password : String? = null
    private var strRelation :String? =null
    private var strCity : String ? = null
    private var strState :String ? = null
    private var strStateCode :String ? = null
    private var strDeployment : String ? = null
    private var strCategory :String ? = null
    private var strType :String ? = null
    private var strGroup:String? = null
    private var strCompany :String? =null
    private var inAnimation: AlphaAnimation? = null
    private var outAnimation: AlphaAnimation? = null
    private var strSubBus :String ? = null
    private var strCustomerSegment:String ? = null
    private var companyId : ArrayList<String>? = null
    private var company: ArrayList<String>? = null
    private var group : ArrayList<String>? = null
    private var groupId : ArrayList<String>? = null
    private var relation : ArrayList<String>? = null
    private var relationId :ArrayList<String>? = null
    private var companyList: ArrayList<ComapnyData>? = null
    private var cityList : ArrayList<BillData>? = null
    private var city : ArrayList<String>? = null
    private var cityCode : ArrayList<String>? = null
    private var strCityCode :String ? =null
    private var strStatus :String? = null
    private var strAction :String?=null
    private var screenStatus:String?=null
    private var lanDataList: ArrayList<LanData>? = null
    private var wanDataList: ArrayList<WanData>? = null
    private var allProductItem: ArrayList<ItemData>? = null
    var isDiscount:Boolean=false
    private var productList : ArrayList<ProData>? = null
    private var productId : ArrayList<String>? = null
    var status :String? = null
    var strProduct : String? = null
    var strPrice : String? = null
    var strAmount : String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.main_site)
        toolbarSite.rl_back.setOnClickListener(this)
        val sp1: SharedPreferences = this.getSharedPreferences("Login", 0)
        userName = sp1.getString(AppConstants.USERNAME, null)
        password = sp1.getString(AppConstants.PASSWORD, null)

        val extras = intent.extras
        if (extras != null) {
            strOppId = extras.getString("OppId")
            strSiteId = extras.getString("SiteID")
            strStatus =  extras.getString("Status")
        }
        toolbarSite.tv_lang.text = AppConstants.SITE
        toolbarSite.flr.visibility=View.GONE
        add_dao.text=AppConstants.ADD_LAN
        add_quote.text=AppConstants.ADD_WAN
        layoutProduct.reason.visibility=View.GONE
        layoutLan.add_dao.visibility=View.VISIBLE
        layoutWan.add_quote.visibility=View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch {
            listener()
            itemListener()
        }

        layoutProduct.add_procuct.setOnClickListener{
                    val product = layoutProduct.et_product_list.text.toString()
                    if (product.isBlank() || product == ("Select Product")) {
                        Toast.makeText(this@SiteOpportunityAct, "Please Select Product", Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        CoroutineScope(Dispatchers.IO).launch {
                            addProduct()
                        }
                    }
        }

        layoutLan.add_dao.setOnClickListener {
            val b = Bundle()
            b.putString("SiteID", strSiteId)
            b.putString("Status", "1")
            val lanFrag = LanFrag()
            lanFrag.arguments=b
            this.supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentSite, lanFrag).addToBackStack(null).commit()
        }

        layoutWan.add_quote.setOnClickListener {
            val b = Bundle()
            b.putString("SiteID", strSiteId)
            b.putString("Status", "1")
            val wanFrag = WanFrag()
            wanFrag.arguments=b
            this.supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentSite, wanFrag).addToBackStack(null).commit()
        }
        funCall()
    }


    private fun listener() {
        linearBasicSiteDetail.visibility = View.VISIBLE
        binding.layoutProduct.price.visibility=View.VISIBLE
        binding.layoutProduct.reason.visibility=View.GONE
        binding.layoutProduct.linearProductt.visibility=View.VISIBLE
        binding.layoutProduct.rvProductList.visibility=View.VISIBLE
        linearBasic.setOnClickListener {
            linearBasicSiteDetail.visibility = View.VISIBLE
            linearLanDetail.visibility = View.GONE
            linearWanDetails.visibility = View.GONE
            linearProductDetail.visibility = View.GONE
        }

        linearLan.setOnClickListener {
            linearBasicSiteDetail.visibility = View.GONE
            linearLanDetail.visibility = View.VISIBLE
            linearWanDetails.visibility = View.GONE
            linearProductDetail.visibility = View.GONE
        }
        linearWan.setOnClickListener {
            linearBasicSiteDetail.visibility = View.GONE
            linearLanDetail.visibility = View.GONE
            linearWanDetails.visibility = View.VISIBLE
            linearProductDetail.visibility = View.GONE
        }
        linearProduct.setOnClickListener {
            linearBasicSiteDetail.visibility = View.GONE
            linearLanDetail.visibility = View.GONE
            linearWanDetails.visibility = View.GONE
            linearProductDetail.visibility = View.VISIBLE
        }
    }
    private fun funCall() {
        CoroutineScope(Dispatchers.IO).launch {
            getSiteDetails()
        }

        CoroutineScope(Dispatchers.IO).launch {
            getLanList()
            getWanList()
        }

        tvCreateSite.setOnClickListener  {
            if(strStatus=="1") {
                strAction = Constants.CREATE_SITE
            }else if (strStatus=="2") {
                strAction = Constants.UPDATE_SITE
                siteDetails.sp_siteCompany.isEnabled=false
                siteDetails.sp_siteGroup.isEnabled=false
                siteDetails.et_siteGroup.isEnabled=false
                siteDetails.et_siteRelationship.isEnabled=false
            }


               val name = siteDetails.et_contact_person_name.text
               val number = siteDetails.et_customer_contact_number.text
               val email = siteDetails.et_customerEmailId.text
               val pinCode = siteDetails.et_sitePinCode.text
               val address = siteDetails.et_siteAddress.text
               val lan = siteDetails.etLanPool.text
               val wan = siteDetails.etWanLinks.text
               val emergencyNum = siteDetails.et_customer_emergencyNum.text
                if(name?.isBlank()==true){
                    Toast.makeText(this@SiteOpportunityAct, "Please Enter Name", Toast.LENGTH_SHORT).show()
                }else  if(number?.isBlank()==true){
                    Toast.makeText(this@SiteOpportunityAct, "Please Enter Contact Number", Toast.LENGTH_SHORT).show()
                }else  if(emergencyNum?.isBlank()==true){
                    Toast.makeText(this@SiteOpportunityAct, "Please Enter Emergency Contact No.", Toast.LENGTH_SHORT).show()
                }else  if(email?.isBlank()==true){
                    Toast.makeText(this@SiteOpportunityAct, "Please Enter Email", Toast.LENGTH_SHORT).show()
                }else if(strStateCode?.isBlank()==true||strStateCode=="0"||strStateCode=="null"){
                    Toast.makeText(this@SiteOpportunityAct, "Please Select State", Toast.LENGTH_SHORT).show()
                }else if(strCityCode?.isBlank()==true||strCityCode==""||strCityCode=="null"){
                    Toast.makeText(this@SiteOpportunityAct, "Please Select City", Toast.LENGTH_SHORT).show()
                }else  if(pinCode?.isBlank()==true){
                    Toast.makeText(this@SiteOpportunityAct, "Please Enter Pincode", Toast.LENGTH_SHORT).show()
                }else  if(address?.isBlank()==true){
                    Toast.makeText(this@SiteOpportunityAct, "Please Enter Address", Toast.LENGTH_SHORT).show()
                }else if(strDeployment?.isBlank()==true||strDeployment=="0"||strDeployment=="null"){
                    Toast.makeText(this@SiteOpportunityAct, "Please Select Solution Deployment Mode", Toast.LENGTH_SHORT).show()
                }else if(strRelation?.isBlank()==true||strRelation==""||strRelation=="null"){
                    Toast.makeText(this@SiteOpportunityAct, "Please Select Relation", Toast.LENGTH_SHORT).show()
                }else if(strCategory?.isBlank()==true||strCategory=="0"||strCategory=="null"){
                    Toast.makeText(this@SiteOpportunityAct, "Please Select Site Category", Toast.LENGTH_SHORT).show()
                }else if(wan?.isBlank()==true){
                    Toast.makeText(this@SiteOpportunityAct, "Please Enter WAN Pools", Toast.LENGTH_SHORT).show()
                }else if(lan?.isBlank()==true){
                    Toast.makeText(this@SiteOpportunityAct, "Please Enter LAN Pools", Toast.LENGTH_SHORT).show()
                }else{
                    createSite(
                        name?.toString(), number?.toString(), email?.toString(),
                        pinCode?.toString(), address?.toString(), emergencyNum?.toString(),
                        lan?.toString(), wan?.toString()
                    )
                }
            }
    }

    private fun addProduct() {
        //inProgress()
        val addProductRequest = strProduct?.let {
             AddProductRequest(Constants.CREATE_OPPPRODUCT,Constants.AUTH_KEY, "",password,it,userName,strSiteId) }

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.addProduct(addProductRequest)
        call.enqueue(object : Callback<ProdctResponse?> {
            override fun onResponse(call: Call<ProdctResponse?>, response: Response<ProdctResponse?>) {
                //OutProgress()
                if (response.body()?.Response?.StatusCode==200) {
                    try {
                        val intent = Intent(this@SiteOpportunityAct, SiteOpportunityAct::class.java)
                        val bundle = Bundle()
                        bundle.putString("OppId",strOppId )
                        bundle.putString("SiteID",strSiteId )
                        bundle.putString("Status",strStatus )
                        Toast.makeText(this@SiteOpportunityAct, strOppId+" "+strSiteId+" "+strStatus, Toast.LENGTH_LONG).show()
                        intent.putExtras(bundle)
                        startActivity(intent)
                        finish()
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



    private fun  getLanList () {
        val getAllLan = strSiteId?.let {
            GetAllLanReq(Constants.GET_ALLLAN, Constants.AUTH_KEY,"" ,password,
                it,userName)
        }
        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getAllLan(getAllLan)
        call.enqueue(object : Callback<GetLanRes?> {
            override fun onResponse(call: Call<GetLanRes?>, response: Response<GetLanRes?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        if(response.body()?.Response?.StatusCode==200) {
                            lanDataList = response.body()?.Response?.Data
                            if(lanDataList!=null) {
                                rv_add_doa.visibility=View.VISIBLE
                                setAllLanListAdapter(lanDataList!!, this@SiteOpportunityAct)
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<GetLanRes?>, t: Throwable) {
                Log.e("RetroError", t.toString())
            }
        })
    }

    private fun setAllLanListAdapter(lanData: ArrayList<LanData>, context: Context?) {
        rv_add_doa.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = GetAllLanAdapter(lanData,context,screenStatus)
        }
    }

    fun getProductAddedList() {
        //  inProgress()
        val getProductListRequest = GetProductListRequest(Constants.GET_OPPPRODUCT,Constants.AUTH_KEY,""/*strOppId*/,password,userName,strSiteId,"")

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.addProductItem(getProductListRequest)
        call.enqueue(object : Callback<GetProductItemListRes?> {
            override fun onResponse(call: Call<GetProductItemListRes?>, response: Response<GetProductItemListRes?>) {
                //   OutProgress()
                if (response.isSuccessful && response.body() != null) {
                    try {
                        if (response.body()?.Response?.StatusCode==200) {
                            try {
                               allProductItem = response.body()?.Response?.Data
                                if(allProductItem?.isNotEmpty()==true) {
                                    allProductItem?.forEachIndexed { _, itemData ->
                                        if(!itemData.Discount?.startsWith("0.0")!!) {
                                            isDiscount=true
                                            return@forEachIndexed
                                        }
                                        println("chk discount:"+itemData.Discount)
                                    }
                                    if(allProductItem?.size!=null){
                                        Log.e("Button51", "51")
                                        setAdapter(allProductItem, this@SiteOpportunityAct)
                                    }
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
    fun getProductList() {

        val getProductListRequest = strOppId?.let { GetProductListRequest(Constants.GET_PRODUCTLIST,Constants.AUTH_KEY, "",password,userName,strSiteId,"") }

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
                        val adapter12 = ArrayAdapter(this@SiteOpportunityAct, android.R.layout.simple_spinner_item, productId!!)
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        layoutProduct.sp_opproduct.adapter = adapter12

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

    private fun  getWanList () {
        val getWanReq = GetWanReq(Constants.GET_ALLWAN, Constants.AUTH_KEY,password,strSiteId,userName,"")
        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getAllWan(getWanReq)
        call.enqueue(object : Callback<GetWanRes?> {
            override fun onResponse(call: Call<GetWanRes?>, response: Response<GetWanRes?>) {
                // OutProgress()
                if (response.isSuccessful && response.body() != null) {
                    try {
                        if(response.body()?.Response?.StatusCode==200) {
                            wanDataList = response.body()?.Response?.Data
                            if(wanDataList!=null) {
                                rv_add_quote.visibility=View.VISIBLE
                                setAllWanListAdapter(wanDataList!!, this@SiteOpportunityAct)
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<GetWanRes?>, t: Throwable) {
                Log.e("RetroError", t.toString())
            }
        })
    }
    private fun setAllWanListAdapter(wanDataList: ArrayList<WanData>, context: Context?) {
        rv_add_quote.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter =  GetAllWanAdapter(wanDataList,context,screenStatus)
        }
    }

    private fun createSite(
        name: String?,
        number: String?,
        email: String?,
        pinCode: String?,
        address: String?,
        emergencyNum: String?,
        lan: String?,
        wan: String?
    ) {
        val siteType = siteDetails.et_siteType.text.toString()
        var site:String?=null
        when (siteType) {
            "Hub" -> {
                site="122050000"
            }
            "Branch" -> {
                site ="122050001"
            }
        }
        val createSiteReq = CreateSiteReq(strAction,Constants.AUTH_KEY,address,strCityCode,
            name,number,email,emergencyNum,
            lan,wan,strOppId,password,pinCode,strPrice,strRelation, strCategory,strDeployment,
            strStateCode,userName,strSiteId,site,strAmount)
      //  inProgress()
        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.createSite(createSiteReq)
        call.enqueue(object : Callback<CreateLeadResponse?> {
            override fun onResponse(call: Call<CreateLeadResponse?>, response: Response<CreateLeadResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                       // outProgress()
                        val img = response.body()?.Response?.Message
                        Toast.makeText(this@SiteOpportunityAct, img, Toast.LENGTH_SHORT).show()
                        if(response.body()?.Response?.StatusCode=="200") {
                            val intent = Intent(this@SiteOpportunityAct, OpportunityActivity::class.java)
                            val bundle = Bundle()
                            bundle.putString("OppId", strOppId)
                            intent.putExtras(bundle)
                            startActivity(intent)
                            finish()
                        }else if(response.body()?.Response?.StatusCode=="201"){
                            Toast.makeText(this@SiteOpportunityAct, response.body()?.Response?.Message, Toast.LENGTH_SHORT
                            ).show()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<CreateLeadResponse?>, t: Throwable) {
              //  binding.siteProgressLayout.progressOverlay.visibility = View.GONE
                Log.e("RetroError", t.toString())
            }
        })
    }



    private fun itemListener(){
        siteDetails.et_siteState.setOnClickListener { siteDetails.sp_siteState.performClick() }
        siteDetails.sp_siteState.onItemSelectedListener = this
        siteDetails.et_siteCity.setOnClickListener { siteDetails.sp_siteCity.performClick() }
        siteDetails.sp_siteCity.onItemSelectedListener = this
        siteDetails.et_siteCustomerSegment.setOnClickListener { siteDetails.sp_siteCustomerSegment.performClick() }
        siteDetails.sp_siteCustomerSegment.onItemSelectedListener = this
        siteDetails.et_siteCategory.setOnClickListener { siteDetails.sp_siteCategory.performClick() }
        siteDetails.sp_siteCategory.onItemSelectedListener = this
        siteDetails.et_siteCompany.setOnClickListener { siteDetails.sp_siteCompany.performClick() }
        siteDetails.sp_siteCompany.onItemSelectedListener = this
        siteDetails.et_siteGroup.setOnClickListener { siteDetails.sp_siteGroup.performClick() }
        siteDetails.sp_siteGroup.onItemSelectedListener = this
        siteDetails.et_siteRelationship.setOnClickListener { siteDetails.sp_siteRelationship.performClick() }
        siteDetails.sp_siteRelationship.onItemSelectedListener = this
        siteDetails.et_typeOrder.setOnClickListener { siteDetails.sp_siteTypeOrder.performClick() }
        siteDetails.sp_siteTypeOrder.onItemSelectedListener = this
        siteDetails.et_SiteDeploymentMode.setOnClickListener { siteDetails.spDeploymentMode.performClick() }
        siteDetails.spDeploymentMode.onItemSelectedListener = this
        siteDetails.et_siteSubBusSegment.setOnClickListener { siteDetails.sp_siteSubBusSegment.performClick() }
        siteDetails.sp_siteSubBusSegment.onItemSelectedListener = this
        layoutProduct.et_product_list.setOnClickListener { layoutProduct.sp_opproduct.performClick() }
        layoutProduct.sp_opproduct.onItemSelectedListener = this
    }



    private fun getSiteDetails() {
        inProgress()
        if(strSiteId?.isNotEmpty() == true){
            strOppId=""
        }
        val getSiteDetailReq = GetSiteDetailReq(
            Constants.GET_SITE,
            Constants.AUTH_KEY,strOppId,password,strSiteId,userName)

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getSiteDetails(getSiteDetailReq)
        call.enqueue(object : Callback<GetAllSiteRes?> {
            override fun onResponse(call: Call<GetAllSiteRes?>, response: Response<GetAllSiteRes?>) {
                outProgress()
                if (response.isSuccessful && response.body() != null) {
                    try {
                        strOppId =  response.body()?.Response?.Data?.get(0)?.OpportunityId
                        binding.siteDetails.siteData = response.body()?.Response?.Data?.get(0)
                        layoutProduct.et_price_list.setText(response.body()?.Response?.Data?.get(0)?.PriceList)
                        strAmount =response.body()?.Response?.Data?.get(0)?.TotalAmount
                        strSiteId = response.body()?.Response?.Data?.get(0)?.SiteID
                        strCompany = response.body()?.Response?.Data?.get(0)?.Company
                        strPrice = response.body()?.Response?.Data?.get(0)?.PriceList
                        strGroup = response.body()?.Response?.Data?.get(0)?.Group
                        strRelation = response.body()?.Response?.Data?.get(0)?.Relationship
                        strState = response.body()?.Response?.Data?.get(0)?.State
                        strCity = response.body()?.Response?.Data?.get(0)?.City
                        strDeployment = response.body()?.Response?.Data?.get(0)?.SolutionDeploymentMode
                        strCustomerSegment = response.body()?.Response?.Data?.get(0)?.CustomerSegment
                        strSubBus = response.body()?.Response?.Data?.get(0)?.SubBusinessSegment
                        strType = response.body()?.Response?.Data?.get(0)?.TypeOFOrder
                        strCategory= response.body()?.Response?.Data?.get(0)?.SiteCategory

                        getCompany(strCompany)
                        var sbBusPosition = 0
                        resources.getStringArray(R.array.list_of_subBusSegment).forEachIndexed { index, s ->
                            if (s == strSubBus) sbBusPosition = index
                        }
                        val sbBusAdapter = ArrayAdapter(this@SiteOpportunityAct, android.R.layout.simple_spinner_item,resources.getStringArray(R.array.list_of_subBusSegment))
                        sbBusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        siteDetails.sp_siteSubBusSegment.adapter = sbBusAdapter
                        siteDetails.sp_siteSubBusSegment.setSelection(sbBusPosition)
                        sbBusAdapter.notifyDataSetChanged()

                        var modePosition = 0
                        resources.getStringArray(R.array.listApplicantsVal).forEachIndexed { index, s ->
                            if (s == strDeployment)
                                modePosition = index
                            return@forEachIndexed
                        }
                        val modeAdapter = ArrayAdapter(this@SiteOpportunityAct, android.R.layout.simple_spinner_item,resources.getStringArray(R.array.solutionDeployment))
                        modeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        siteDetails.spDeploymentMode.adapter = modeAdapter
                        siteDetails.spDeploymentMode.setSelection(modePosition)
                        modeAdapter.notifyDataSetChanged()

                        var statePosition = 0
                        resources.getStringArray(R.array.list_of_state).forEachIndexed { index, s ->
                            if (s == strState) statePosition = index
                            return@forEachIndexed
                        }
                        val stateAdapter = ArrayAdapter(this@SiteOpportunityAct, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.list_of_state))
                        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        siteDetails.sp_siteState.adapter = stateAdapter
                        siteDetails.sp_siteState.setSelection(statePosition)
                        stateAdapter.notifyDataSetChanged()

                        var typePosition = 0
                        resources.getStringArray(R.array.typeOrder).forEachIndexed { index, s ->
                            if (s == strType) typePosition = index
                            return@forEachIndexed
                        }

                        val typeAdapter = ArrayAdapter(this@SiteOpportunityAct, android.R.layout.simple_spinner_item,resources.getStringArray(R.array.typeOrder))
                        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        siteDetails.sp_siteTypeOrder.adapter = typeAdapter
                        siteDetails.sp_siteTypeOrder.setSelection(typePosition)
                        typeAdapter.notifyDataSetChanged()

                        var categoryPosition = 0
                        resources.getStringArray(R.array.list_of_prefferedvalue).forEachIndexed { index, s ->
                            if (s == strCategory) categoryPosition = index
                        }
                        val categoryAdapter = ArrayAdapter(this@SiteOpportunityAct, android.R.layout.simple_spinner_item,resources.getStringArray(R.array.siteCategory))
                        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        siteDetails.sp_siteCategory.adapter = categoryAdapter
                        siteDetails.sp_siteCategory.setSelection(categoryPosition)
                        categoryAdapter.notifyDataSetChanged()

                        var segPosition = 0
                        resources.getStringArray(R.array.customerSegment).forEachIndexed { index, s ->
                            if (s == strCustomerSegment) segPosition = index
                        }
                        val segmentAdapter = ArrayAdapter(this@SiteOpportunityAct, android.R.layout.simple_spinner_item,resources.getStringArray(R.array.customerSegment))
                        segmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        siteDetails.sp_siteCustomerSegment.adapter = segmentAdapter
                        siteDetails.sp_siteCustomerSegment.setSelection(segPosition)
                        segmentAdapter.notifyDataSetChanged()
                        screenStatus = response.body()?.Response?.Data?.get(0)?.OppStatus

                        CoroutineScope(Dispatchers.IO).launch {
                            getProductAddedList()
                            getProductList()
                        }

                        if(screenStatus=="Lost"||screenStatus=="Won"||screenStatus=="Waiting for Approval"){
                            locked()
                            status="1"
                            screenStatus="1"
                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<GetAllSiteRes?>, t: Throwable) {
                binding.siteProgressLayout.progressOverlay.visibility=View.GONE
                Log.e("RetroError", t.toString())
            }
        })
    }

    private fun locked(){
        siteDetails.et_siteState.isEnabled=false
        siteDetails.et_siteCity.isEnabled=false
        siteDetails.et_siteCustomerSegment.isEnabled=false
        siteDetails.et_siteCategory.isEnabled=false
        siteDetails.et_siteCompany.isEnabled=false
        siteDetails.et_siteGroup.isEnabled=false
        siteDetails.et_siteRelationship.isEnabled=false
        siteDetails.et_typeOrder.isEnabled=false
        siteDetails.et_SiteDeploymentMode.isEnabled=false
        siteDetails.et_siteSubBusSegment.isEnabled=false
        siteDetails.et_contact_person_name.isEnabled=false
        siteDetails.et_customer_contact_number.isEnabled=false
        siteDetails.et_customer_emergencyNum.isEnabled=false
        siteDetails.et_customerEmailId.isEnabled=false
        siteDetails.et_sitePinCode.isEnabled=false
        siteDetails.et_siteAddress.isEnabled=false
        siteDetails.etWanLinks.isEnabled=false
        siteDetails.etLanPool.isEnabled=false
        siteDetails.tvCreateSite.visibility=View.GONE
        layoutProduct.et_product_list.isEnabled=false
        layoutProduct.add_procuct.visibility=View.GONE
        layoutLan.add_dao.visibility=View.GONE
        layoutWan.add_quote.visibility=View.GONE
    }

    fun inProgress(){
        inAnimation = AlphaAnimation(0f, 1f)
        inAnimation?.duration =200
        binding.siteProgressLayout.progressOverlay.animation = inAnimation
        binding.siteProgressLayout.progressOverlay.visibility = View.VISIBLE
    }

    fun outProgress(){
        outAnimation = AlphaAnimation(1f, 0f)
        outAnimation?.duration =200
        binding.siteProgressLayout.progressOverlay.animation = outAnimation
        binding.siteProgressLayout.progressOverlay.visibility = View.GONE
    }

    override fun onClick(p0: View?) {
        if (p0?.id == R.id.rl_back) {
           next()
        }
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
            val intent = Intent(this, OpportunityActivity::class.java)
            intent.putExtra("OppId",strOppId)
            startActivity(intent)
            finish()
        }
        builder.setNegativeButton(AppConstants.NO) { dialog, _ ->
            dialog.cancel()
        }
        val alert: AlertDialog = builder.create()
        alert.show()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (parent?.id) {
            R.id.sp_siteCompany -> {
                siteDetails.et_siteCompany.setText(company?.get(position))
                strCompany =  companyId?.get(position)
                siteDetails.et_siteGroup.setText(group?.get(position))
                strGroup = groupId?.get(position)
                getRelation(strCompany)
            }
            R.id.sp_siteGroup -> {
                siteDetails.et_siteGroup.setText(group?.get(position))
                strGroup = groupId?.get(position)
            }
            R.id.sp_siteRelationship -> {
                siteDetails.et_siteRelationship.setText(relation?.get(position))
                strRelation =  relationId?.get(position)
            }
            R.id.sp_siteCity ->{
                siteDetails.et_siteCity.setText(city?.get(position))
                strCity = city?.get(position).toString()
                strCityCode = cityCode?.get(position)
            }
            R.id.sp_siteState ->{
                siteDetails.et_siteState.setText(resources.getStringArray(R.array.list_of_state)[position])
                strState = resources.getStringArray(R.array.list_of_state)[position]
                strStateCode = resources.getStringArray(R.array.list_state_code)[position]
                getCity(strStateCode)
            }
            R.id.spDeploymentMode ->{
                siteDetails.et_SiteDeploymentMode.setText(resources.getStringArray(R.array.solutionDeployment)[position])
                strDeployment = resources.getStringArray(R.array.listApplicantsVal)[position]
            }
            R.id.sp_siteCategory ->{
                siteDetails.et_siteCategory.setText(resources.getStringArray(R.array.siteCategory)[position])
                strCategory = resources.getStringArray(R.array.list_of_prefferedvalue)[position]
                }
            R.id.sp_siteSubBusSegment ->{
                siteDetails.et_siteSubBusSegment.setText(resources.getStringArray(R.array.list_of_subBusSegment)[position])
                strSubBus = resources.getStringArray(R.array.list_of_subBusSegment)[position]
            }
            R.id.sp_siteCustomerSegment ->{
                siteDetails.et_siteCustomerSegment.setText(resources.getStringArray(R.array.customerSegment)[position])
                strCustomerSegment = resources.getStringArray(R.array.customerSegment)[position]
            }
            R.id.sp_siteTypeOrder ->{
                siteDetails.et_typeOrder.setText(resources.getStringArray(R.array.typeOrder)[position])
                strType = resources.getStringArray(R.array.siteTypeVal)[position]

            }
            R.id.sp_opproduct ->{
                layoutProduct.et_product_list.setText(productId?.get(position))
                strProduct =  productId?.get(position)
            }

        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

  fun  getCity(stateCode: String?) {
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
                        city?.forEachIndexed { index, s ->
                            if(s==strCity)cityPosition=index
                            return@forEachIndexed
                        }
                        val adapter12 = ArrayAdapter(this@SiteOpportunityAct, android.R.layout.simple_spinner_item, city!!)
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        siteDetails.sp_siteCity.adapter = adapter12
                        siteDetails.sp_siteCity.setSelection(cityPosition)
                        adapter12.notifyDataSetChanged()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<CafBillingCityResponse?>, t: Throwable) {
                Log.e("RetroError", t.toString())
            }
        })
    }

    fun getCompany(strCompany: String?) {
        inProgress()
        val getCompanyRequest = GetCompanyRequest(Constants.GET_COMPANY,Constants.AUTH_KEY,strCompany,password,userName)
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
                        val adapter12 = ArrayAdapter(this@SiteOpportunityAct, android.R.layout.simple_spinner_item, company!!)
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        siteDetails.sp_siteCompany.adapter = adapter12
                        siteDetails.sp_siteCompany.setSelection(comPosition)
                        adapter12.notifyDataSetChanged()

                        val adapter11 = ArrayAdapter(this@SiteOpportunityAct, android.R.layout.simple_spinner_item, group!!)
                        adapter11.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        siteDetails.sp_siteGroup.adapter = adapter11
                        siteDetails.sp_siteGroup.setSelection(groupPosition)
                        adapter11.notifyDataSetChanged()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }
            }
            override fun onFailure(call: Call<GetCompanyResponse?>, t: Throwable) {
                binding.siteProgressLayout.progressOverlay.visibility = View.GONE
                Log.e("RetroError", t.toString())
            }
        })
    }

    private fun getRelation(strCompany: String?) {
        val getCompanyRequest = GetCompanyRequest(Constants.GET_RELATIONSHIP,Constants.AUTH_KEY,strCompany.toString(),password,userName)
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
                                item.Relationship_Name?.let { relation?.add(it) }
                                item.Relationship_ID?.let { relationId?.add(it) }
                            }
                        }
                        var relationPosition=0
                        relationId?.forEachIndexed { index, s ->
                            if(s==strRelation)
                             relationPosition=index
                             return@forEachIndexed
                        }
                        val adapter12 = ArrayAdapter(this@SiteOpportunityAct, android.R.layout.simple_spinner_item, relation!!)
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item)
                        binding.siteDetails.spSiteRelationship.adapter = adapter12
                        binding.siteDetails.spSiteRelationship.setSelection(relationPosition)
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
}