import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.spectra.fieldforce.databinding.GetAllFeasibiltyAdapterBinding

import com.spectra.fieldforce.salesapp.model.AppData


class GetDOAViewAdapter(private val items: ArrayList<AppData>, private val context: Context?, val str_oppId: String?) : RecyclerView.Adapter<GetDOAViewAdapter.ViewHolder>() {

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
        fun bind(item: AppData) {
            binding.FeasibilityId.text = "Name : " +item.Name
            binding.CustomerName.text = "Approval Requested Date : " +item.ApprovalRequestedDate
            binding.Opportunity.text = "Approver : " +item.Approver
            binding.EstimatedDoneBy.text = "Approval Date : " +item.ApprovalDate
            binding.FeasibilityStatus.text ="Status : " +item.Status
            binding.RouteType.text ="Status Reason : "+item.StatusReason
        }
    }

}