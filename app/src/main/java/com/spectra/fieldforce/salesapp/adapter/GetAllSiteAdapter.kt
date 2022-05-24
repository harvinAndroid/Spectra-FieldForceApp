import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spectra.fieldforce.databinding.GetFeasibiltyadapterBinding
import com.spectra.fieldforce.databinding.GetSiteadapterBinding
import com.spectra.fieldforce.model.SaveQuestionareList.Answer
import com.spectra.fieldforce.salesapp.activity.SiteOpportunityAct
import com.spectra.fieldforce.salesapp.model.FeasData
import com.spectra.fieldforce.salesapp.model.SiteData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class GetAllSiteAdapter(
    private val items: List<SiteData>,
    private val context: Context?,
    val productseg: String?
) : RecyclerView.Adapter<GetAllSiteAdapter.ViewHolder>() {

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
        fun bind(item: SiteData) {
            binding.FeasibilityId.text = "Site Id : " +item.SiteID
            binding.CustomerName.text= "Solution Deployment Mode : " +item.SolutionDeploymentMode
            binding.Opportunity.text = "Site Category : " +item.SiteCategory
            binding.FeasibilityStatus.text = "Type OF Order : " +item.TypeOFOrder
            binding.approveStatus.text= "Site Type : " +item.SiteType
            binding.subReason.text = "No Of LAN Pool : " +item.NoOfLanPool
            binding.owner.text= "No Of WAN links : " +item.NoOfWanlinks
            binding.EstimatedDoneBy.text = "Site Status: " +item.SiteStatus
            binding.linearSite.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    val intent = Intent(context, SiteOpportunityAct::class.java)
                    val bundle = Bundle()
                    bundle.putString("SiteID", item.SiteID)
                    bundle.putString("Status","2")
                    intent.putExtras(bundle)
                    context?.startActivity(intent)
                }
            }

        }
    }

}