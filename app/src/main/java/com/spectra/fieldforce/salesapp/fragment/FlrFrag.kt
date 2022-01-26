package com.spectra.fieldforce.salesapp.fragment

import android.R
import android.app.DatePickerDialog

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
import com.spectra.fieldforce.salesapp.activity.UpdateLeadActivity
import com.spectra.fieldforce.salesapp.model.UpdateFlrRequest
import com.spectra.fieldforce.utils.AppConstants
import com.spectra.fieldforce.utils.Constants
import kotlinx.android.synthetic.main.flr_fragment.*
import kotlinx.android.synthetic.main.opp_other_info_row.view.*
import kotlinx.android.synthetic.main.oppurtunity_demo_fragment.*
import kotlinx.android.synthetic.main.toolbar.view.*
import java.util.*
import com.spectra.fieldforce.activity.MainActivity
import android.widget.TimePicker

import android.app.TimePickerDialog
import android.text.format.DateFormat

import android.widget.DatePicker



class FlrFrag:Fragment(),View.OnClickListener,AdapterView.OnItemSelectedListener,DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
   lateinit var flrFragmentBinding: FlrFragmentBinding
    var str_LeadId : String?=null
    var  strMobile :String?=null
    var esdtDate :String ? = null
    var prfDate :String ? = null
    var list_of_status = arrayOf("Select Option","Positive","Negative","Scheduled Appointment")
    var list_of_status_values = arrayOf("Select Option","1","2","3")
    var list_of_appointment = arrayOf("Select Option","Meeting","Phone Call")
    var list_of_appointment_value = arrayOf("0","1","2")
    var day: Int?=null
    var month: Int?=null
    var year: Int?=null
    var hour: Int?=null
    var minute: Int?=null
    var myday: Int?=null
    var myMonth: Int?=null
    var myYear: Int?=null
    var myHour: Int?=null
    var myMinute: Int?=null
    var str_status :String?=null
    var str_appointment:String?=null
    var str_estmd_closure :String?=null
    var str_remrk:String?=null
    var date_tm:String?=null
    private var inAnimation: AlphaAnimation? = null
    private var outAnimation: AlphaAnimation? = null
    companion object {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        flrFragmentBinding = FlrFragmentBinding.inflate(inflater, container, false)

        return flrFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchtoolbarflr.rl_back.setOnClickListener(this)
        searchtoolbarflr.tv_lang.text= AppConstants.FLR
        val bundle = arguments
        str_LeadId = bundle?.getString("LeadId")
        strMobile =bundle?.getString("Mobile")

        tv_op_save.setOnClickListener {
             str_status = et_Status.text.toString()
             str_estmd_closure = et_est_dt.text.toString()
             str_remrk = et_remrk.text.toString()
             prfDate= et_prf_date_tm.text.toString()
            if(prfDate==""||prfDate=="Prefer Date & Time"){
                date_tm= "0"
            }else{
                date_tm=prfDate
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
            if(str_status=="1"){
                if(str_estmd_closure?.isBlank()==true){
                    Toast.makeText(context, "Please Enter Estimated Closure", Toast.LENGTH_SHORT).show()
                }else {
                    date_tm="0"
                    createFlr()
                }
            }else  if(str_status=="2"){
                if(str_remrk?.isBlank()==true){
                    Toast.makeText(context, "Please Enter Remark", Toast.LENGTH_SHORT).show()
                }else {
                    createFlr()
                }
            }else  if(str_status=="3"){
                val appoint = et_appointment.text.toString()
                if(appoint=="Select Option"||appoint.isBlank()||appoint=="null"){
                    Toast.makeText(context, "Please Select Option", Toast.LENGTH_SHORT).show()
                }else if(prfDate?.isBlank()==true){
                    Toast.makeText(context, "Please Select Preferred date & time", Toast.LENGTH_SHORT).show()
                } else {
                    str_estmd_closure="0"
                    createFlr()
                }
            }else{
                Toast.makeText(context, "Please Select the Option", Toast.LENGTH_SHORT).show()

            }

        }

        flrFragmentBinding.etStatus.setOnClickListener { flrFragmentBinding.spFlrstatus.performClick() }
        flrFragmentBinding.spFlrstatus.onItemSelectedListener = this

        flrFragmentBinding.etAppointment.setOnClickListener { flrFragmentBinding.spAppointment.performClick() }
        flrFragmentBinding.spAppointment.onItemSelectedListener = this


        et_Subject.setText("FLR for Lead $str_LeadId")
        try{

        et_est_dt.setOnClickListener {
            val calendar = Calendar.getInstance()
            year = calendar[Calendar.YEAR]
            month = calendar[Calendar.MONTH]
            day = calendar[Calendar.DAY_OF_MONTH]
            val datePickerDialog =
                year?.let { it1 -> month?.let { it2 ->
                    day?.let { it3 ->
                        DatePickerDialog(requireContext(), this, it1,
                            it2, it3
                        )
                    }
                } }
            datePickerDialog?.show()

        }


        et_prf_date_tm.setOnClickListener {
            val calendar = Calendar.getInstance()
            year = calendar[Calendar.YEAR]
            month = calendar[Calendar.MONTH]
            day = calendar[Calendar.DAY_OF_MONTH]
            val datePickerDialog =
                year?.let { it1 -> month?.let { it2 ->
                    day?.let { it3 ->
                        DatePickerDialog(requireContext(), this, it1,
                            it2, it3
                        )
                    }
                } }
            datePickerDialog?.show()

        }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        val flrStatus = context?.let { ArrayAdapter(it, R.layout.simple_spinner_item, list_of_status) }
        flrStatus?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        sp_flrstatus!!.adapter = flrStatus


        val appointmentAdapter = context?.let { ArrayAdapter(it, R.layout.simple_spinner_item, list_of_appointment) }
        appointmentAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        sp_appointment!!.adapter = appointmentAdapter


    }
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        myYear = year
        myday = day
        myMonth = month+1
        val c = Calendar.getInstance()
        hour = c[Calendar.HOUR]
        minute = c[Calendar.MINUTE]
        val timePickerDialog = TimePickerDialog(
            requireContext(),
            this,
            hour!!,
            minute!!,
            DateFormat.is24HourFormat(requireContext())
        )
        timePickerDialog.show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        myHour = hourOfDay
        myMinute = minute
        et_est_dt.setText(myYear.toString()+"-"+myMonth+"-"+myday+" "+myHour+":"+myMinute+":"+"00".trimIndent())
        et_prf_date_tm.setText(myYear.toString()+"-"+myMonth+"-"+myday+" "+myHour+":"+myMinute+":"+"00".trimIndent())
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
                                et_prf_date_tm.setText("")
                                et_appointment.setText("")
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
              str_status = list_of_status_values.get(position)
             when (et_Status.text.toString()) {
                  ("Negative") -> {
                      remark.visibility = View.VISIBLE
                      est_dt.visibility = View.GONE
                      appoint.visibility = View.GONE
                      date.visibility = View.GONE
                  }
                  ("Positive") -> {
                      remark.visibility = View.GONE
                      est_dt.visibility = View.VISIBLE
                      appoint.visibility = View.GONE
                      date.visibility = View.GONE
                  }
                  "Scheduled Appointment" -> {
                      remark.visibility = View.GONE
                      est_dt.visibility = View.GONE
                      date.visibility = View.VISIBLE
                      appoint.visibility = View.VISIBLE
                  }
              }
          }else if(parent?.id == com.spectra.fieldforce.R.id.sp_appointment){
              et_appointment.setText(list_of_appointment.get(position))
               str_appointment = list_of_appointment.get(position)
          }
      }

      override fun onNothingSelected(p0: AdapterView<*>?) {

      }

}