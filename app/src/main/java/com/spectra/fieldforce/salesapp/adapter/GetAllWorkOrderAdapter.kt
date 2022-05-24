import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.spectra.fieldforce.databinding.GetFeasibiltyadapterBinding
import com.spectra.fieldforce.databinding.GetSiteadapterBinding
import com.spectra.fieldforce.fragment.IRFragment
import com.spectra.fieldforce.salesapp.fragment.LanFrag
import com.spectra.fieldforce.salesapp.model.LanData
import com.spectra.fieldforce.salesapp.model.WorkData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class GetAllWorkOrderAdapter(
    private val items: List<WorkData>,
    private val context: Context?
) : RecyclerView.Adapter<GetAllWorkOrderAdapter.ViewHolder>() {

    lateinit var binding: GetFeasibiltyadapterBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = GetSiteadapterBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun getItemCount():
            Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    inner class ViewHolder(val binding: GetSiteadapterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: WorkData) {
            binding.FeasibilityId.text = "Site Id : " +item.SiteId+"                                 "
            binding.CustomerName.text= "Site Type : " +item.SiteType
            binding.Opportunity.text = "Work Order ID : " +item.WorkOrderID
            binding.FeasibilityStatus.text = "Owner: " +item.Owner
            binding.approveStatus.text = "Order Status : " +item.OrderStatus
            binding.subReason.text = "Order Accepted : " +item.StateserviceActivation
            binding.owner.text = "Accepted By Customer Date : " +item.AcceptedByCustomerDate
            binding.EstimatedDoneBy.visibility=View.GONE

        }
    }

}