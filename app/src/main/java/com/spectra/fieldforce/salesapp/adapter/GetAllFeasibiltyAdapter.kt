import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spectra.fieldforce.databinding.GetFeasibiltyadapterBinding
import com.spectra.fieldforce.model.SaveQuestionareList.Answer
import com.spectra.fieldforce.salesapp.model.FeasData


class GetAllFeasibiltyAdapter(
    private val items: List<FeasData>,
    private val context: Context?,
    val productseg: String?
) : RecyclerView.Adapter<GetAllFeasibiltyAdapter.ViewHolder>() {

    lateinit var binding: GetFeasibiltyadapterBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = GetFeasibiltyadapterBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun getItemCount():
            Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    inner class ViewHolder(val binding: GetFeasibiltyadapterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FeasData) {
            binding.FeasibilityId.text = "Feasibility Id : " +item.FeasibilityId
            val fsstatus = item.FeasibilityStatus
            if(fsstatus=="1"){
                binding.FeasibilityStatus.text= "Feasibility Status : " +"Feasible"
            }else if(fsstatus=="2"){
                binding.FeasibilityStatus.text= "Feasibility Status : " +"Not Feasible"
                if(productseg=="Secured Managed Internet"){
                    binding.approveStatus.visibility=View.VISIBLE
                    binding.subReason.visibility=View.VISIBLE
                    binding.subReason.text="Sub Reason : "+ item.SubReason
                    binding.approveStatus.text="Approval Status : " + item.ApprovalStatus
                }
            }else if(fsstatus=="0"){
                binding.FeasibilityStatus.text= "Feasibility Status : " +""
            }

            binding.Opportunity.text = "Owner : " +item.Owner
            val route = item.RouteType
            if(route!!){
                binding.CustomerName.text= "Route Type : " +"Redundant Route"
            }else if(!route){
                binding.CustomerName.text= "Route Type : " +"Primary Route"
            }
            val thirdPary = item.ThirdPartyFeasibilityRequired
            if(thirdPary!!){
                binding.RouteType.visibility=View.VISIBLE
                binding.EstimatedDoneBy.visibility=View.VISIBLE
                binding.Customer.visibility=View.VISIBLE
                binding.RouteType.text= "OTC : " +item.Otc
                binding.EstimatedDoneBy.text= "ARC : " +item.Arc
                binding.Customer.text= "ISP Name : " +item.IspName
            }else if(!thirdPary){
                if(thirdPary!=null) {
                    binding.RouteType.text = "Total Calculated Cost : " + item.TotalCalculatedCost
                }else{
                    binding.RouteType.text = "Total Calculated Cost : " + ""
                }
                binding.RouteType.visibility=View.VISIBLE
                binding.EstimatedDoneBy.visibility=View.GONE
                binding.Customer.visibility=View.GONE
            }

        }
    }

}