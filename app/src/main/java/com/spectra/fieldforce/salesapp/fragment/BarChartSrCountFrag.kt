package com.spectra.fieldforce.salesapp.fragment


import GetAllSRGraphAdapter
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.spectra.fieldforce.R
import com.spectra.fieldforce.api.ApiClient
import com.spectra.fieldforce.api.ApiInterface
import com.spectra.fieldforce.databinding.LeadCountCrntmonthBinding
import com.spectra.fieldforce.salesapp.model.CafData
import com.spectra.fieldforce.salesapp.model.ChartData
import com.spectra.fieldforce.salesapp.model.FFASRBarChartReq
import com.spectra.fieldforce.salesapp.model.FFASRBarChartResponse
import com.spectra.fieldforce.utils.AppConstants
import com.spectra.fieldforce.utils.Constants
import kotlinx.android.synthetic.main.fragment_all_lead_list.*
import kotlinx.android.synthetic.main.lead_count_crntmonth.*
import kotlinx.android.synthetic.main.lead_grapgh.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class BarChartSrCountFrag : Fragment(), View.OnClickListener {

    private lateinit var binding: LeadCountCrntmonthBinding
    var srChart: BarChart? = null
    var amChart: BarChart? = null
    private var inAnimation: AlphaAnimation? = null
    private var outAnimation: AlphaAnimation? = null

    var barDataSet1: BarDataSet? = null
    var barDataSet2: BarDataSet? = null
    private  var userName: String? = null
    private var password : String? = null
    // array list for storing entries.
    private var date : ArrayList<String> = ArrayList()
    private var subType : ArrayList<String> = ArrayList()
     var hashMap : LinkedHashMap<String, ArrayList<ChartData>> = LinkedHashMap<String, ArrayList<ChartData>> ()
    var hashMapSubType : LinkedHashMap<String, ArrayList<ChartData>> = LinkedHashMap<String, ArrayList<ChartData>> ()

    private var allCount: ArrayList<ChartData>? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LeadCountCrntmonthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        srChart = view.findViewById(R.id.srBarChart)
        amChart = view.findViewById(R.id.amBarChart)

        val sp1: SharedPreferences? = context?.getSharedPreferences("Login", 0)
        userName = sp1?.getString("UserName", null)
        password = sp1?.getString("Password", null)
        excuteTask()

    }

    fun excuteTask() {
        CoroutineScope(Dispatchers.IO).launch {
            getCount()
        }

    }


    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.rl_back -> {

            }
        }
    }


    fun getCount() {
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

            val ffasrBarChartReq= FFASRBarChartReq(
                Constants.GET_DASHBOARDCASES,
                Constants.AUTH_KEY,result,/*password*/"Crdm@311#",currentDate,"crm.deploy"/*userName*/)

            val apiService = ApiClient.getClient().create(ApiInterface::class.java)
            val call = apiService.getSrCount(ffasrBarChartReq)
            call.enqueue(object : Callback<FFASRBarChartResponse?> {
                override fun onResponse(
                    call: Call<FFASRBarChartResponse?>,
                    response: Response<FFASRBarChartResponse?>
                ) {
                    outAnimation = AlphaAnimation(1f, 0f)
                    inAnimation?.duration = 200
                    binding.progressLayout.progressOverlay.animation = outAnimation
                    binding.progressLayout.progressOverlay.visibility = View.GONE

                    if (response.isSuccessful && response.body() != null) {
                        try {
                            val msg = response.body()?.Response?.Message
                            if (response.body()?.Response?.StatusCode == 200) {
                                allCount = response.body()?.Response?.Data
                                date = ArrayList<String>()
                                subType= ArrayList<String>()
                                for (item in allCount!!){
                                    item.CreatedOn.let { date.add(it) }
                                }
                                for (item in allCount!!){
                                    item.SubType.let { subType.add(it) }
                                }
                                for(item in date){
                                    val leadd: ArrayList<ChartData> =
                                        ArrayList()
                                    for(leadCount in allCount!!){
                                        if(leadCount.CreatedOn == item){
                                            leadd.add(leadCount)
                                        }
                                    }
                                    hashMap.put(item,leadd)
                                    Log.e("HashMap",hashMap.size.toString())
                                }
                                for(item in subType){
                                    val subbType: ArrayList<ChartData> =
                                        ArrayList()
                                    for(leadCount in allCount!!){
                                        if(leadCount.SubType == item){
                                            subbType.add(leadCount)
                                        }
                                    }
                                    hashMapSubType.put(item,subbType)
                                    Log.e("HashMap",hashMapSubType.size.toString())
                                }
                                showGraph()
                                showSubTypeGraph()
                            }else{
                                Toast.makeText(context,msg,Toast.LENGTH_LONG).show()
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }

                override fun onFailure(call: Call<FFASRBarChartResponse?>, t: Throwable) {
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
            barEntries.add(BarEntry(index++.toFloat(),it.value.size.toFloat() ))
            barEntries2.add(BarEntry(index.toFloat(),0f))
        }
        barDataSet1 = BarDataSet(barEntries, "SR Created")
        /* barDataSet1 = BarDataSet(getBarEntriesOne(), "Lead Created")*/
        barDataSet1?.color=(resources.getColor(R.color.ColorPurple))
        barDataSet2 = BarDataSet(barEntries2, "")
        barDataSet2?.color = Color.TRANSPARENT
        val data = BarData(barDataSet1, barDataSet2)
        srChart?.data = data
        srChart?.description?.isEnabled = false
        barDataSet1?.valueTextSize=10f
        barDataSet2?.valueTextSize=0f
        val xAxis = srChart?.xAxis
        val hashSet = LinkedHashSet<String>()
        hashSet.addAll(date)
        date.clear()
        date.addAll(hashSet)
        xAxis?.valueFormatter = IndexAxisValueFormatter(date)
        xAxis?.setCenterAxisLabels(true)
        srChart?.axisRight?.isEnabled = false
        xAxis?.position = XAxis.XAxisPosition.BOTTOM
        xAxis?.granularity = 1f
        xAxis?.isGranularityEnabled = true
        srChart?.xAxis?.setDrawGridLines(false)
        srChart?.axisLeft?.setDrawGridLines(false)
        srChart?.axisRight?.setDrawGridLines(false)
        srChart?.isDragEnabled = true
        srChart?.setVisibleXRangeMaximum(3f)
        srChart?.setDrawGridBackground(false)
        val barSpace = 0.1f
        val groupSpace = 0.5f
        data.barWidth = 0.15f
        srChart?.xAxis?.axisMinimum = 0f
        srChart?.animate()
        srChart?.groupBars(0f, groupSpace, barSpace)
        srChart?.invalidate()
        srChart?.setOnClickListener(){
            if(allCount!=null){
                rv_srData.visibility=View.VISIBLE
                allCount?.let { setAdapter(it, context) }
            }

        }
    }

    private fun setAdapter(allCount: ArrayList<ChartData>, context: Context?) {
        rv_srData?.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = GetAllSRGraphAdapter(allCount,context)
        }
    }

    private fun showSubTypeGraph() {
        var index=0
        val barEntries: ArrayList<BarEntry> = ArrayList()
        val barEntries2: ArrayList<BarEntry> = ArrayList()
        hashMapSubType.forEach {
            barEntries.add(BarEntry(index++.toFloat(),it.value.size.toFloat() ))
            barEntries2.add(BarEntry(index.toFloat(),0f))
        }
        barDataSet1 = BarDataSet(barEntries, "SubType Created")
        barDataSet1?.setColors(Color.BLUE, Color.GREEN,Color.BLACK,Color.RED,Color.MAGENTA,Color.YELLOW)

        barDataSet2 = BarDataSet(barEntries2, "")
        barDataSet2?.valueTextSize=0f
        val data = BarData(barDataSet1, barDataSet2)
        amChart?.data = data
        amChart?.description?.isEnabled = false
        val xAxis = amChart?.xAxis
        val hashSet = LinkedHashSet<String>()
        hashSet.addAll(subType)
        subType.clear()
        subType.addAll(hashSet)
        amChart?.xAxis?.setDrawGridLines(false)
        amChart?.axisLeft?.setDrawGridLines(false)
        amChart?.axisRight?.setDrawGridLines(false)
        xAxis?.valueFormatter = IndexAxisValueFormatter(subType)
        xAxis?.setCenterAxisLabels(true)
        xAxis?.position = XAxis.XAxisPosition.BOTTOM
        xAxis?.granularity = 1f
        xAxis?.isGranularityEnabled = true
        amChart?.axisRight?.isEnabled = false
        amChart?.isDragEnabled = true
        amChart?.setVisibleXRangeMaximum(3f)
        barDataSet1?.valueTextSize=10f

        val barSpace = 0.1f
        val groupSpace = 0.5f
        data.barWidth = 0.15f
        amChart?.xAxis?.axisMinimum = 0f
        amChart?.animate()
        amChart?.groupBars(0f, groupSpace, barSpace)
        amChart?.invalidate()
    }
}

