import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.RecyclerView
import com.spectra.fieldforce.BuildConfig
import com.spectra.fieldforce.api.ApiClient
import com.spectra.fieldforce.api.ApiInterface
import com.spectra.fieldforce.databinding.GetAllQuoteAdapterBinding
import com.spectra.fieldforce.salesapp.model.CafPdfRequest
import com.spectra.fieldforce.salesapp.model.GetPdfResponse
import com.spectra.fieldforce.salesapp.model.QuoteData
import com.spectra.fieldforce.salesapp.model.ReportResponse
import com.spectra.fieldforce.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class GetAllQuoteAdapter(private val items: List<QuoteData>, private val context: Context?,val str_oppId:String) : RecyclerView.Adapter<GetAllQuoteAdapter.ViewHolder>() {

    lateinit var binding: GetAllQuoteAdapterBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = GetAllQuoteAdapterBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun getItemCount():
            Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    inner class ViewHolder(val binding: GetAllQuoteAdapterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: QuoteData) {
            binding.status.text = "Opportunity Name : "+item.OpportunityName+"                         "
            binding.tvMob.text = "Quote Id : "+item.Quoteid
            binding.orderStatus.text = "Status : "+item.Status
            binding.share.setOnClickListener {
                share(item.Quoteid)
            }
            binding.downloadpdf.setOnClickListener {
                downloadPDF(item.Quoteid)
            }

        }


    }

    private fun share(quoteid: String) {
        val sp1: SharedPreferences? = context?.getSharedPreferences("Login", 0)
        val  userName = sp1?.getString("UserName", null)
        val password = sp1?.getString("Password", null)
        val cafPdfRequest = CafPdfRequest(
            Constants.REPORTSEMAIL_SEND, Constants.AUTH_KEY, quoteid,
            password, "Quote", userName
        )
        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.shareEmail(cafPdfRequest)
        call.enqueue(object : Callback<ReportResponse?> {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            override fun onResponse(
                call: Call<ReportResponse?>,
                response: Response<ReportResponse?>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    if (response.body()?.Status == "Success") {
                        val msg = response.body()?.Response?.Message
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<ReportResponse?>, t: Throwable) {
                Log.e("RetroError", t.toString())
            }
        })
    }
    private fun downloadPDF(quoteid: String) {
        val sp1: SharedPreferences? = context?.getSharedPreferences("Login", 0)
        val  userName = sp1?.getString("EnggName", null)
        val password = sp1?.getString("Password", null)
        val cafPdfRequest = CafPdfRequest(
            Constants.DOWNLOADREPORTS, Constants.AUTH_KEY, quoteid,
            password, "Quote", userName
        )
        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getPdf(cafPdfRequest)
        call.enqueue(object : Callback<GetPdfResponse?> {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            override fun onResponse(
                call: Call<GetPdfResponse?>,
                response: Response<GetPdfResponse?>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    if (response.body()!!.Status == "Success") {
                        val data = response.body()!!.Response.Data
                        storetoPdfandOpen(data)
                    }
                }
            }

            override fun onFailure(call: Call<GetPdfResponse?>, t: Throwable) {
                Log.e("RetroError", t.toString())
            }
        })
    }

    fun storetoPdfandOpen(base: String?) {
        val extStorageDirectory = Environment.getExternalStorageDirectory()
            .toString()
        val folder = File(extStorageDirectory, "Spectra")
        folder.mkdir()
        val file = File(folder, "Spectradoc.pdf")
        try {
            file.createNewFile()
        } catch (e1: IOException) {
            e1.printStackTrace()
        }
        try {
            val out = FileOutputStream(file)
            val pdfAsBytes = Base64.decode(base, 0)
            out.write(pdfAsBytes)
            out.flush()
            out.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val sendIntent = Intent(Intent.ACTION_VIEW)
        val uri: Uri
        uri = context?.let { FileProvider.getUriForFile(it, BuildConfig.APPLICATION_ID + ".provider", file) }!!
        sendIntent.setDataAndType(uri, "application/pdf")
        sendIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        context.startActivity(sendIntent)


    }
}