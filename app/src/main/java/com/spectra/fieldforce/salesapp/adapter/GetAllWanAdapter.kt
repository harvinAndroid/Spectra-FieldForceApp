import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.spectra.fieldforce.databinding.GetFeasibiltyadapterBinding
import com.spectra.fieldforce.databinding.GetSiteadapterBinding
import com.spectra.fieldforce.model.SaveQuestionareList.Answer
import com.spectra.fieldforce.salesapp.activity.SiteOpportunityAct
import com.spectra.fieldforce.salesapp.fragment.LanFrag
import com.spectra.fieldforce.salesapp.fragment.WanFrag
import com.spectra.fieldforce.salesapp.model.FeasData
import com.spectra.fieldforce.salesapp.model.SiteData
import com.spectra.fieldforce.salesapp.model.WanData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class GetAllWanAdapter(
    private val items: List<WanData>,
    private val context: Context?,
    private val screenStatus: String?
) : RecyclerView.Adapter<GetAllWanAdapter.ViewHolder>() {

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
        fun bind(item: WanData) {
            binding.FeasibilityId.text = "WAN No. : " +item.WanNo+"                                                "
            binding.CustomerName.text= "IP Allocation type : " +item.WanIPAllocationtype
            binding.Opportunity.text = "Solution Deployment Mode : " +item.SolutionDeploymentMode
            binding.FeasibilityStatus.visibility=View.GONE
            binding.approveStatus.visibility=View.GONE
            binding.subReason.visibility=View.GONE
            binding.owner.visibility=View.GONE
            binding.EstimatedDoneBy.visibility=View.GONE
            binding.linearSite.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    val b = Bundle()
                    b.putString("SiteID", item.SiteId)
                    b.putString("Status", "2")
                    b.putString("WanNo", item.WanNo)
                    b.putString("ScreenStatus",screenStatus)
                    val activity =context as AppCompatActivity
                    val wanFrag = WanFrag()
                    wanFrag.arguments=b
                    activity.supportFragmentManager.beginTransaction()
                        .add(com.spectra.fieldforce.R.id.fragmentSite, wanFrag).
                        addToBackStack(null).commit()
                }
            }

        }
    }

}