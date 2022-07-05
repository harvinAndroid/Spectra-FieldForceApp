package com.spectra.fieldforce.salesapp.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.spectra.fieldforce.R
import com.spectra.fieldforce.api.ApiClient
import com.spectra.fieldforce.api.ApiInterface
import com.spectra.fieldforce.databinding.EditProductDetailsBinding
import com.spectra.fieldforce.salesapp.model.ProdctResponse
import com.spectra.fieldforce.salesapp.model.UpdateProductRequest
import com.spectra.fieldforce.utils.AppConstants
import com.spectra.fieldforce.utils.Constants
import kotlinx.android.synthetic.main.edit_product_details.*
import kotlinx.android.synthetic.main.toolbar.view.*

class EditProduct : AppCompatActivity(), View.OnClickListener , AdapterView.OnItemSelectedListener{
    lateinit var  binding:EditProductDetailsBinding
    var strOppId : String? = null
    var str_Product : String? = null
    var strSiteId : String? = null
    var str_Price : String? = null
    var str_Discount : String? = null
    var str_pricing :String?= null
    var userName : String? = null
    var password :String?= null
    var list_of_pricing = arrayOf("Use Default","Override Price")
    var list_pricing_value = arrayOf("0","1")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.edit_product_details)
        searchtoolbar.rl_back.setOnClickListener(this)
        searchtoolbar.tv_lang.text= AppConstants.UPDATEPRODUCT
        val sp1: SharedPreferences = this.getSharedPreferences("Login", 0)
        userName = sp1.getString(AppConstants.USERNAME, null)
        password = sp1.getString(AppConstants.PASSWORD, null)
        val extras = intent.extras
        if (extras != null) {
            strOppId = extras.getString("OppId")
            str_Product = extras.getString("ProductName")
            str_Price = extras.getString("Price")
            str_Discount = extras.getString("Discount")
            strSiteId = extras.getString("SiteID")
            binding.etProNm.setText(str_Product)
            binding.etPrice.setText(str_Price)
            binding.etDiscount.setText(str_Discount)

            bt_pro_submit.setOnClickListener {
                updateProduct()
            }
        }
        init()
    }
    fun init(){
        et_pricing.setOnClickListener {  sp_pricing.performClick() }
        sp_pricing.onItemSelectedListener = this
        val pricingAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, list_of_pricing)
        pricingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        sp_pricing.adapter = pricingAdapter
    }

    private fun updateProduct () {

        val discount = et_discount.text.toString()
        val price = et_price.text.toString()
        val product = et_pro_nm.text.toString()
        var str_Opp_Id:String?=null
        str_Opp_Id=strOppId

        if(strSiteId?.isNotBlank() == true){
            str_Opp_Id=""
        }
        val updateProductRequest =
            str_pricing?.let { it1 ->
                UpdateProductRequest(Constants.UPDATE_OPPPRODUCT, Constants.AUTH_KEY,discount,str_Opp_Id,
                        password,price, it1,product,"",userName,strSiteId)

        }

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.updateOppProduct(updateProductRequest)
        call.enqueue(object : retrofit2.Callback<ProdctResponse?> {
            override fun onResponse(call: retrofit2.Call<ProdctResponse?>, response: retrofit2.Response<ProdctResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                       val img = response.body()?.Response?.Message
                    if(response.body()?.Response?.StatusCode==200) {
                        try {
                            Toast.makeText(this@EditProduct,img,Toast.LENGTH_LONG).show()
                            img?.let { Log.e("image", it) }
                            val intent = Intent(this@EditProduct, OpportunityActivity::class.java)
                            intent.putExtra("OppId",strOppId)
                            startActivity(intent)
                            finish()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }else{
                        Toast.makeText(this@EditProduct,img,Toast.LENGTH_LONG).show()
                    }

                }
            }

            override fun onFailure(call: retrofit2.Call<ProdctResponse?>, t: Throwable) {
                Log.e("RetroError", t.toString())
            }
        })
    }

    override fun onClick(p0: View?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (parent?.id == R.id.sp_pricing) {
            et_pricing.setText(list_of_pricing[position])
            str_pricing = list_of_pricing[position]
             val price = list_of_pricing[position]
            if(price=="Use Default"){
                et_price.isFocusable=false
                et_price.isFocusableInTouchMode=false
                et_discount.isFocusableInTouchMode=true
            }else if(price=="Override Price"){
                et_price.isFocusable=true
                et_price.isFocusableInTouchMode=true
                et_discount.isFocusableInTouchMode =false
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

}
