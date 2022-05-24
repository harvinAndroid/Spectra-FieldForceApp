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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class GetAllLanAdapter(
    private val items: List<LanData>,
    private val context: Context?
) : RecyclerView.Adapter<GetAllLanAdapter.ViewHolder>() {

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
        fun bind(item: LanData) {
            binding.FeasibilityId.text = "LAN No. : " +item.LanNo+"                                 "
            binding.CustomerName.text= "LAN IP Allocation to User : " +item.LanIPAllocationtoUser
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
                    b.putString("LanNum",item.LanNo)
                    val activity =context as AppCompatActivity
                    val lanFrag = LanFrag()
                    lanFrag.arguments=b
                    activity.supportFragmentManager.beginTransaction()
                        .replace(com.spectra.fieldforce.R.id.fragmentSite, lanFrag).addToBackStack(null).commit()
                }
            }

        }
    }

}