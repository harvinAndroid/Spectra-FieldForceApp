package com.spectra.fieldforce.salesapp.fragment

import android.R
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
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
import com.spectra.fieldforce.api.ApiClient
import com.spectra.fieldforce.api.ApiInterface
import com.spectra.fieldforce.databinding.FlrFragmentBinding
import com.spectra.fieldforce.model.gpon.response.CommonClassResponse
import com.spectra.fieldforce.salesapp.activity.OppurtunityActivity
import com.spectra.fieldforce.salesapp.activity.SalesDashboard
import com.spectra.fieldforce.salesapp.activity.UpdateLeadActivity
import com.spectra.fieldforce.salesapp.model.UpdateFlrRequest
import com.spectra.fieldforce.utils.AppConstants
import com.spectra.fieldforce.utils.Constants
import kotlinx.android.synthetic.main.flr_fragment.*
import kotlinx.android.synthetic.main.toolbar.view.*
import java.util.*

class FlrFrag:Fragment(),View.OnClickListener,AdapterView.OnItemSelectedListener{
   lateinit var flrFragmentBinding: FlrFragmentBinding
   lateinit var str_LeadId : String
   lateinit var  strMobile :String
    var fromDateString :String ? = null
    var list_of_status = arrayOf("Select Option","Positive","Negative","Scheduled Appointment")
    var list_of_status_values = arrayOf("Select Option","1","2","3")
    var list_of_appointment = arrayOf("Select Appointment","Meeting","Phone Call")
    var list_of_appointment_value = arrayOf("0","1","2")

    var str_status = ""
    var str_appointment = ""
    private var inAnimation: AlphaAnimation? = null
    private var outAnimation: AlphaAnimation? = null
    companion object {
        fun newInstance(): FlrFrag {
            return FlrFrag()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        flrFragmentBinding = FlrFragmentBinding.inflate(inflater, container, false)
        val activity = activity as Context
        return flrFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchtoolbarflr.rl_back.setOnClickListener(this)
        searchtoolbarflr.tv_lang.text= AppConstants.FLR
        val bundle = arguments
        str_LeadId = bundle!!.getString("LeadId")!!
        strMobile =bundle.getString("Mobile")!!

        tv_op_save.setOnClickListener { v ->
            createFlr()
        }

        flrFragmentBinding.etStatus.setOnClickListener { flrFragmentBinding.spFlrstatus.performClick() }
        flrFragmentBinding.spFlrstatus.onItemSelectedListener = this

        flrFragmentBinding.etAppointment.setOnClickListener { flrFragmentBinding.spAppointment.performClick() }
        flrFragmentBinding.spAppointment.onItemSelectedListener = this


        et_Subject.setText("FLR for Lead $str_LeadId")

      /*  et_Status.setOnClickListener { sp_status.performClick() }
        sp_status.onItemSelectedListener = this*/
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)


        et_est_dt.setOnClickListener {

            val dpd = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                val mnth = java.lang.String.valueOf(c[Calendar.MONTH] + 1)

                et_est_dt.setText("$dayOfMonth-$mnth-$year")
            }, year, month, day)
            dpd.show()

        }

        et_prf_date_tm.setOnClickListener {

            val dpd = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                val mnth = java.lang.String.valueOf(c[Calendar.MONTH] + 1)

                et_prf_date_tm.setText("$dayOfMonth-$mnth-$year")
            }, year, month, day)
            dpd.show()

        }

        val flrStatus = context?.let { ArrayAdapter(it, R.layout.simple_spinner_item, list_of_status) }
        flrStatus?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        sp_flrstatus!!.adapter = flrStatus


        val appointmentAdapter = context?.let { ArrayAdapter(it, R.layout.simple_spinner_item, list_of_appointment) }
        appointmentAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        sp_appointment!!.adapter = appointmentAdapter


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }



    override fun onClick(p0: View?) {
        if (p0?.id == com.spectra.fieldforce.R.id.rl_back) {
            val intent = Intent(context, UpdateLeadActivity::class.java)
            intent.putExtra("LeadId",str_LeadId)
            context?.startActivity(intent)
        }
    }

    fun createFlr () {
        inAnimation = AlphaAnimation(0f, 1f)
        inAnimation?.duration =200
        flrFragmentBinding.flrprogressLayout.progressOverlay.animation = inAnimation
        flrFragmentBinding.flrprogressLayout.progressOverlay.visibility = View.VISIBLE

        var str_status = et_Status.text.toString()
        val str_estmd_closure = et_est_dt.text.toString()
        val str_remrk = et_remrk.text.toString()
        var date_tm = et_prf_date_tm.text.toString()
        if(date_tm==""||date_tm=="Prefer Date & Time"){
            date_tm= "0"
        }
        if(str_status=="Positive"){
            str_status="1"
        }else if(str_status=="Negative"){
            str_status="2"
        }else if(str_status=="Scheduled Appointment"){
            str_status="3"
        }

        if(str_appointment=="Meeting"){
            str_appointment="1"
        }else if(str_appointment=="Phone Call"){
            str_appointment="2"
        }else{
            str_appointment="0"
        }



        val updateFlrRequest = UpdateFlrRequest(Constants.UPDATE_FLR,Constants.AUTH_KEY, str_LeadId,
            strMobile, str_status,str_estmd_closure, str_remrk,str_appointment,
                date_tm,"manager1",
            "Target@2021#@")

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.updateFlr(updateFlrRequest)
        call.enqueue(object : retrofit2.Callback<CommonClassResponse?> {
            override fun onResponse(call: retrofit2.Call<CommonClassResponse?>, response: retrofit2.Response<CommonClassResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    outAnimation = AlphaAnimation(1f, 0f)
                    inAnimation?.duration =200
                    flrFragmentBinding.flrprogressLayout.progressOverlay.animation = outAnimation
                    flrFragmentBinding.flrprogressLayout.progressOverlay.visibility = View.GONE
                    val img = response.body()!!.response.message
                        if(response.body()?.statusCode==200) {
                            try {
                                val img = response.body()!!.response.message
                                Toast.makeText(context,img,Toast.LENGTH_LONG).show()
                                Log.e("image", img)
                                val intent = Intent(context, UpdateLeadActivity::class.java)
                                intent.putExtra("LeadId",str_LeadId)
                                context?.startActivity(intent)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }else{
                            Toast.makeText(context,img,Toast.LENGTH_LONG).show()
                        }

                }
            }

            override fun onFailure(call: retrofit2.Call<CommonClassResponse?>, t: Throwable) {
               flrFragmentBinding.flrprogressLayout.progressOverlay.visibility=View.GONE
                Log.e("RetroError", t.toString())
            }
        })
    }

    fun showFragmentflr(fragment: FlrFrag){
        val fram = parentFragmentManager.beginTransaction()
        fram.replace(com.spectra.fieldforce.R.id.fragment_lead,fragment)
        fram.commit()
    }


      override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
          if (parent?.id == com.spectra.fieldforce.R.id.sp_flrstatus) {
              et_Status.setText(list_of_status.get(position))
              if (position != 0) str_status = "" + list_of_status_values.get(position - 1) else str_status= " "
              Log.e("Id",str_status)
             val status :String = et_Status.text.toString()
              if(status.equals("Negative")){
                  et_remrk.visibility = View.VISIBLE
                  et_est_dt.visibility = View.GONE
                  appoint.visibility = View.GONE
              }else if(status.equals("Positive")){
                  et_remrk.visibility = View.GONE
                  et_est_dt.visibility = View.VISIBLE
                  appoint.visibility = View.GONE
              }else if(status.equals("Scheduled Appointment")){
                  et_remrk.visibility = View.GONE
                  et_est_dt.visibility = View.GONE
                  date.visibility = View.VISIBLE
                  appoint.visibility = View.VISIBLE
              }
          }else if(parent?.id == com.spectra.fieldforce.R.id.sp_appointment){
              et_appointment.setText(list_of_appointment.get(position))
              if (position != 0) str_appointment = "" + list_of_appointment.get(position - 1) else str_appointment= " "
              Log.e("apd",list_of_appointment_value.get(position) )
              Log.e("apdnm",list_of_appointment.get(position))
          }
      }

      override fun onNothingSelected(p0: AdapterView<*>?) {
          TODO("Not yet implemented")
      }

}