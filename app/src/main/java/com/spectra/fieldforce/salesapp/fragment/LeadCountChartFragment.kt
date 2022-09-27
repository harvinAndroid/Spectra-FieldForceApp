package com.spectra.fieldforce.salesapp.fragment


import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.spectra.fieldforce.R
import com.spectra.fieldforce.api.ApiClient
import com.spectra.fieldforce.api.ApiInterface
import com.spectra.fieldforce.databinding.LeadGrapghBinding
import com.spectra.fieldforce.salesapp.model.AllLeadData
import com.spectra.fieldforce.salesapp.model.GetAllLeadRequest
import com.spectra.fieldforce.salesapp.model.GetAllLeadResponse
import com.spectra.fieldforce.utils.AppConstants
import com.spectra.fieldforce.utils.Constants
import com.spectra.fieldforce.utils.SalesAppConstants
import kotlinx.android.synthetic.main.fragment_all_lead_list.*
import kotlinx.android.synthetic.main.lead_count_crntmonth.*
import kotlinx.android.synthetic.main.update_leadtoolbar.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class LeadCountChartFragment : Fragment(),View.OnClickListener{

    private lateinit var binding: LeadGrapghBinding
    var barChart: BarChart? = null
    var barChartCount:BarChart? = null
    private var inAnimation: AlphaAnimation? = null
    private var outAnimation: AlphaAnimation? = null
    // variable for our bar data set.
    var barDataSet1: BarDataSet? = null
    var barDataSet2: BarDataSet? = null
    private  var userName: String? = null
    private var password : String? = null
    // array list for storing entries.
    private var date : ArrayList<String> = ArrayList()
     var hashMap : LinkedHashMap<String, ArrayList<AllLeadData>> = LinkedHashMap<String, ArrayList<AllLeadData>> ()
    private var allLead: ArrayList<AllLeadData>? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LeadGrapghBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        barChart = view.findViewById(R.id.idBarChart)
        barChartCount = view.findViewById(R.id.barChartCount)

        val sp1: SharedPreferences? = context?.getSharedPreferences("Login", 0)
        userName = sp1?.getString("UserName", null)
        password = sp1?.getString("Password", null)
        excuteTask()
        //showGraph()

    }

    private fun excuteTask() {
        CoroutineScope(Dispatchers.IO).launch {
            getAllLeadList()

        }
    }


    private fun getAllLeadList() {
        try {
            inAnimation = AlphaAnimation(0f, 1f)
            inAnimation?.duration = 200
            binding.progressLayout.progressOverlay.animation = inAnimation
            binding.progressLayout.progressOverlay.visibility = View.VISIBLE

            val sdf = SimpleDateFormat(AppConstants.DATE_FORMAT)
            val currentDate = sdf.format(Date())
            val calendar = Date()
            val sdf1 = SimpleDateFormat(AppConstants.DATE_CURRENT, Locale.getDefault())
            val result = sdf1.format(calendar)
            val getAllLeadRequest = GetAllLeadRequest(
                Constants.GET_AllLEADS, Constants.AUTH_KEY, "",
                password, userName, "", result, currentDate,SalesAppConstants.BUSINESS)


            val apiService = ApiClient.getClient().create(ApiInterface::class.java)
            val call = apiService.getAllLead(getAllLeadRequest)
            call.enqueue(object : Callback<GetAllLeadResponse?> {
                override fun onResponse(
                    call: Call<GetAllLeadResponse?>,
                    response: Response<GetAllLeadResponse?>
                ) {
                    outAnimation = AlphaAnimation(1f, 0f)
                    inAnimation?.duration = 200
                    binding.progressLayout.progressOverlay.animation = outAnimation
                    binding.progressLayout.progressOverlay.visibility = View.GONE

                    if (response.isSuccessful && response.body() != null) {
                        try {
                            val msg = response.body()?.Response?.Message
                            if (response.body()?.Response?.StatusCode == 200) {
                                allLead = response.body()?.Response?.Data
                                date = ArrayList<String>()
                                for (item in allLead!!){
                                    item.Createdon?.let { date.add(it) }
                                }
                                for(item in date){
                                    val leadd: ArrayList<AllLeadData> =
                                        ArrayList()
                                    for(leadCount in allLead!!){
                                        if(leadCount.Createdon.equals(item)){
                                            leadd.add(leadCount)
                                        }
                                    }
                                    hashMap.put(item,leadd)
                                }
                                showGraph()
                                showCount()
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }

                override fun onFailure(call: Call<GetAllLeadResponse?>, t: Throwable) {
                    binding.progressLayout.progressOverlay.visibility = View.GONE
                    Log.e("RetroError", t.toString())
                }
            })
        }catch (E: Exception){
            E.printStackTrace()
        }
    }


    private fun showGraph() {
        var index=0
        val barEntries: ArrayList<BarEntry> = ArrayList()
        val barEntries2: ArrayList<BarEntry> = ArrayList()
        hashMap.forEach {

          val arrayListValue=hashMap.get(it.key)
          var open=0
            var qulifay=0
            arrayListValue?.forEach {
                if(it.Status.equals("Open")){
                    open++
                }else{
                    qulifay++
                }
            }

            barEntries.add(BarEntry(index++.toFloat(),open.toFloat()))
            barEntries2.add(BarEntry(index.toFloat(), qulifay.toFloat()))
        }


        barDataSet1 = BarDataSet(barEntries, "Lead Open")
       /* barDataSet1 = BarDataSet(getBarEntriesOne(), "Lead Created")*/
        barDataSet1?.color=(resources.getColor(R.color.blue))
        barDataSet2 = BarDataSet(barEntries2, "Lead Qualified")
        barDataSet2?.color = (resources.getColor(R.color.orange))
        val data = BarData(barDataSet1, barDataSet2)
        barChart?.data = data
        barChart?.description?.isEnabled = false
        val xAxis = barChart?.xAxis
        val hashSet = LinkedHashSet<String>()
        hashSet.addAll(date)
        date.clear()
        date.addAll(hashSet)
        xAxis?.valueFormatter = IndexAxisValueFormatter(date)
        xAxis?.setCenterAxisLabels(true)
        xAxis?.position = XAxis.XAxisPosition.BOTTOM
        xAxis?.granularity = 1f
        xAxis?.isGranularityEnabled = true
        barChart?.isDragEnabled = true
        barDataSet1?.valueTextSize=10f
        barDataSet2?.valueTextSize=10f
        barChart?.xAxis?.setDrawGridLines(false)
        barChart?.axisLeft?.setDrawGridLines(false)
        barChart?.axisRight?.setDrawGridLines(false)
        barChart?.axisRight?.isEnabled = false
        barChart?.setVisibleXRangeMaximum(3f)
        val barSpace = 0.1f
        val groupSpace = 0.5f
        data.barWidth = 0.15f
        barChart?.xAxis?.axisMinimum = 0f
        barChart?.animate()
        barChart?.groupBars(0f, groupSpace, barSpace)
        barChart?.invalidate()
    }

  private fun showCount() {
      var index=0
      val barEntries: ArrayList<BarEntry> = ArrayList()
      val barEntries2: ArrayList<BarEntry> = ArrayList()
      hashMap.forEach {
          barEntries.add(BarEntry(index++.toFloat(),it.value.size.toFloat() ))
          barEntries2.add(BarEntry(index.toFloat(),0f))
      }
      Log.e("BAR-ENTRIES",barEntries.toString())

      barDataSet1 = BarDataSet(barEntries, "Lead Open")
      /* barDataSet1 = BarDataSet(getBarEntriesOne(), "Lead Created")*/
      barDataSet1?.color=(resources.getColor(R.color.ColorPurple))
      barDataSet2 = BarDataSet(barEntries2, "")
      barDataSet2?.color = Color.TRANSPARENT
      val data = BarData(barDataSet1, barDataSet2)
      barChartCount?.data = data
      barChartCount?.description?.isEnabled = false
      val xAxis = barChartCount?.xAxis
      val hashSet = LinkedHashSet<String>()
      hashSet.addAll(date)
      date.clear()
      date.addAll(hashSet)
      xAxis?.valueFormatter = IndexAxisValueFormatter(date)
      xAxis?.setCenterAxisLabels(true)
      xAxis?.position = XAxis.XAxisPosition.BOTTOM
      xAxis?.granularity = 1f
      barDataSet1?.valueTextSize=10f
      barDataSet2?.valueTextSize=0f
      xAxis?.isGranularityEnabled = true
      barChartCount?.isDragEnabled = true
      barChartCount?.axisRight?.isEnabled = false
      barChartCount?.xAxis?.setDrawGridLines(false)
      barChartCount?.axisLeft?.setDrawGridLines(false)
      barChartCount?.axisRight?.setDrawGridLines(false)
      barChartCount?.setVisibleXRangeMaximum(3f)
      val barSpace = 0.1f
      val groupSpace = 0.5f
      data.barWidth = 0.15f
      barChartCount?.xAxis?.axisMinimum = 0f
      barChartCount?.animate()
      barChartCount?.groupBars(0f, groupSpace, barSpace)
      barChartCount?.invalidate()

  }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.rl_back -> {

            }
        }
    }

}

