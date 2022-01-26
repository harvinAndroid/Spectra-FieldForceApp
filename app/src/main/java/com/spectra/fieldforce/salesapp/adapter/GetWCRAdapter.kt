import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spectra.fieldforce.databinding.GetAllFeasibiltyAdapterBinding
import com.spectra.fieldforce.databinding.GetAllLeadAdapterBinding
import com.spectra.fieldforce.salesapp.activity.CAFActivity
import com.spectra.fieldforce.salesapp.activity.UpdateLeadActivity
import com.spectra.fieldforce.salesapp.model.AllLeadData
import com.spectra.fieldforce.salesapp.model.CafData
import com.spectra.fieldforce.salesapp.model.FeasData
import com.spectra.fieldforce.salesapp.model.WCRData


class GetWCRAdapter(private val items: List<WCRData>, private val context: Context?) : RecyclerView.Adapter<GetWCRAdapter.ViewHolder>() {

    lateinit var binding: GetAllFeasibiltyAdapterBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = GetAllFeasibiltyAdapterBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun getItemCount():
            Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    inner class ViewHolder(val binding: GetAllFeasibiltyAdapterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: WCRData) {
            binding.FeasibilityId.text = "WCR Id : " +item.WcrID
            binding.FeasibilityStatus.text= "WCR Status : " +item.WcrStatus
            binding.Opportunity.text = "CreatedOn : " +item.CreatedOn
            binding.CustomerName.text= "PodCode : " +item.PodCode
            binding.RouteType.text= "PodName : "+item.PodName
            binding.EstimatedDoneBy.text= "Zone :"+item.Zone


        }
    }
}