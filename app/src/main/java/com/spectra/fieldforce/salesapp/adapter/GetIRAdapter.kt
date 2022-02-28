import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spectra.fieldforce.databinding.GetAllFeasibiltyAdapterBinding
import com.spectra.fieldforce.salesapp.model.IRData


class GetIRAdapter(private val items: List<IRData>, private val context: Context?) : RecyclerView.Adapter<GetIRAdapter.ViewHolder>() {

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
        fun bind(item: IRData) {
            binding.FeasibilityId.text = "IR Id : " +item.IRId
            binding.FeasibilityStatus.text= "IR Status : " +item.IrStatus
            binding.Opportunity.text = "Route Type : " +item.RouteType
            binding.CustomerName.text="Owner : " +item.Owner
            binding.RouteType.text="Installation Date : " +item.InstallationDate
            binding.EstimatedDoneBy.text= "Network Technology : " +item.Ntw_Technology


        }
    }
}