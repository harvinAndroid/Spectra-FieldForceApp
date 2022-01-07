import android.content.Context
import android.view.LayoutInflater
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
            binding.EstimatedDoneBy.text = "Estimated DoneBy : " +item.EstimatedDoneBy.toString()
            binding.FeasibilityStatus.text = "Feasibility Status : " +item.FeasibilityStatus
            binding.ApproveLevel1.text = "Approve Level1 : " +item.ApproveLevel1
            binding.ApproveLevel2.text = "Approve Level2 : " +item.ApproveLevel2
            binding.RouteType.text = "Route Type : " +item.RouteType.toString()
            binding.RedunancyRequired.text = "Redunancy Required : " +item.RedunancyRequired.toString()
            binding.ThirdPartyFeasibilityRequired.text = "Third-Party Feasibility Required : "+item.ThirdPartyFeasibilityRequired.toString()
            binding.Area.text = "Area : " +item.Area
            binding.CityCorrect.text = "City Correct : " +item.CityCorrect
           /* binding.tvMob.text = "Mobile No. : "+item.MobileNumber
            binding.orderStatus.text = "Status : "+item.Status
             binding.linearLead.setOnClickListener(){
                val intent = Intent(context, UpdateLeadActivity::class.java)
                val bundle = Bundle()
                bundle.putString("LeadId",item.LeadId )
                bundle.putString("LeadStatus",item.Status)
                intent.putExtras(bundle)
                context?.startActivity(intent)

            }*/

        }
    }
}