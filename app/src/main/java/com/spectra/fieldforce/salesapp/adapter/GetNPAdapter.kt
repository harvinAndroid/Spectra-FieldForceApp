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
import com.spectra.fieldforce.salesapp.model.NPData


class GetNPAdapter(private val items: List<NPData>, private val context: Context?) : RecyclerView.Adapter<GetNPAdapter.ViewHolder>() {

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
        fun bind(item: NPData) {
            binding.FeasibilityId.text = "NP Id : " +item.NpId
            binding.FeasibilityStatus.text="Network Technology : " +item.Ntw_Technology
            binding.Opportunity.text = "POD Name : " +item.PodName
            binding.CustomerName.text = "POD Code : "+item.PodCode
            binding.RouteType.text ="Owner : " +item.Owner
            binding.EstimatedDoneBy.text ="Provisioning Status : " +item.ProvisioningStatus
        }
    }
}