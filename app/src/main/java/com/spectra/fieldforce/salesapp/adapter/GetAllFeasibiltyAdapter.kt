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
            binding.CustomerName.text = "Customer Name : " +item.CustomerName
            binding.Opportunity.text = "Opportunity : " +item.Opportunity
            binding.EstimatedDoneBy.visibility=View.GONE
            binding.FeasibilityStatus.visibility=View.GONE
            binding.ApproveLevel1.visibility=View.GONE
            binding.ApproveLevel2.visibility=View.GONE
            val route = item.RouteType
            if(route==true){
                binding.RouteType.text= "Route Type : " +"Redundant Route"
            }else if(route==false){
                binding.RouteType.text= "Route Type : " +"Primary Route"
            }
            binding.RouteType.text= "Route Type : " +item.RouteType
            binding.RedunancyRequired.visibility=View.GONE
            binding.ThirdPartyFeasibilityRequired.visibility=View.GONE
            binding.Area.text = "Area : " +item.Area
            binding.CityCorrect.text = "City : " +item.CityCorrect

        }
    }
}