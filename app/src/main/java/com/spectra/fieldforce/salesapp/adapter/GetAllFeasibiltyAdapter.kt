import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spectra.fieldforce.databinding.GetAllFeasibiltyAdapterBinding
import com.spectra.fieldforce.salesapp.model.FeasData


class GetAllFeasibiltyAdapter(private val items: List<FeasData>, private val context: Context?) : RecyclerView.Adapter<GetAllFeasibiltyAdapter.ViewHolder>() {

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
        fun bind(item: FeasData) {
            binding.FeasibilityId.text = "Feasibility Id : " +item.FeasibilityId
            val fsstatus = item.FeasibilityStatus
            if(fsstatus=="1"){
                binding.FeasibilityStatus.text= "Feasibility Status : " +"Feasible"
            }else if(fsstatus=="2"){
                binding.FeasibilityStatus.text= "Feasibility Status : " +"Not Feasibility"
            }
            binding.Opportunity.text = "Opportunity : " +item.Opportunity
            val route = item.RouteType
            if(route){
                binding.CustomerName.text= "Route Type : " +"Redundant Route"
            }else if(!route){
                binding.CustomerName.text= "Route Type : " +"Primary Route"
            }
            binding.RouteType.visibility=View.GONE
            binding.EstimatedDoneBy.visibility=View.GONE
        }
    }
}