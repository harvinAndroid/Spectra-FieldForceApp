package com.spectra.fieldforce.salesapp.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.widget.Toast
import com.spectra.fieldforce.api.ApiClient
import com.spectra.fieldforce.api.ApiInterface
import com.spectra.fieldforce.databinding.ItemProductListBinding
import com.spectra.fieldforce.salesapp.activity.OpportunityActivity

import com.spectra.fieldforce.salesapp.model.*
import com.spectra.fieldforce.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import android.view.View
import android.view.animation.AlphaAnimation

import com.spectra.fieldforce.salesapp.activity.EditProduct

class GetAllProductItemAdapter(private val items: List<ItemData>, private val activity: Context?,val OppId: String?,val Status: String?) : RecyclerView.Adapter<GetAllProductItemAdapter.ViewHolder>() {

    lateinit var binding:ItemProductListBinding
    private var inAnimation: AlphaAnimation? = null
    private var outAnimation: AlphaAnimation? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProductListBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun getItemCount():
            Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    inner class ViewHolder(val binding: ItemProductListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ItemData) {
            binding.tvProductname.text = "Product Name : " + item.ProductId
            binding.tvPrcUnit.text = "Price Per Unit : " + item.PricePerUnit
            binding.tvDiscount.text = "Discount : " + item.Discount
            if(Status=="1"){
                binding.imgDelete.visibility=View.GONE
                binding.tvEdit.visibility=View.GONE
            }else {
                binding.imgDelete.visibility=View.VISIBLE
                binding.tvEdit.visibility=View.VISIBLE
            }

            val ProId:String = item.ProductId
            binding.imgDelete.setOnClickListener() {
                val addProductRequest = OppId?.let { it1 -> AddProductRequest(Constants.DELETE_OPPPRODUCT, Constants.AUTH_KEY, it1, "Target@2021#@", ProId, "manager1") }
                val apiService = ApiClient.getClient().create(ApiInterface::class.java)
                val call = apiService.deleteProduct(addProductRequest)
                call.enqueue(object : Callback<DeleteProductResponse?> {
                    override fun onResponse(call: Call<DeleteProductResponse?>, response: Response<DeleteProductResponse?>) {
                        val msg = response.body()!!.Response.Message
                        if (response.body()?.Response?.StatusCode==200) {
                            try {
                                Toast.makeText(activity, msg, Toast.LENGTH_LONG).show()
                                val intent = Intent(activity, OpportunityActivity::class.java)
                                val bundle = Bundle()
                                bundle.putString("OppId",OppId )
                                intent.putExtras(bundle)
                                activity?.startActivity(intent)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        } else {
                            Toast.makeText(activity, msg, Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<DeleteProductResponse?>, t: Throwable) {

                        Log.e("RetroError", t.toString())
                    }
                })
            }
            binding.tvEdit.setOnClickListener {
                val intent = Intent(activity, EditProduct::class.java)
                val bundle = Bundle()
                bundle.putString("ProductName",item.ProductId )
               // bundle.putString("Unit",item.Unit )
                bundle.putString("Price",item.PricePerUnit )
                bundle.putString("Discount",item.Discount )
                bundle.putString("OppId",OppId )
                intent.putExtras(bundle)
                activity?.startActivity(intent)
            }
        }

    }

}