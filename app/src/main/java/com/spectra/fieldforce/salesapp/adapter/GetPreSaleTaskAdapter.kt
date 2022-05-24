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
import com.spectra.fieldforce.salesapp.fragment.WanFrag
import com.spectra.fieldforce.salesapp.model.LanData
import com.spectra.fieldforce.salesapp.model.PreSaleData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class GetPreSaleTaskAdapter(
    private val items: List<PreSaleData>,
    private val context: Context?
) : RecyclerView.Adapter<GetPreSaleTaskAdapter.ViewHolder>() {

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
        fun bind(item: PreSaleData) {
            binding.FeasibilityId.text ="Pre Sales Task ID : " +item.PresalesTaskID+"                                 "
            binding.CustomerName.text= "Contact Person Name : " +item.ContactPersonName
            binding.Opportunity.text = "IT Support Customer : " +item.ITsupportcustomer
            binding.FeasibilityStatus.text="Owner :"+item.CreatedBY
            binding.approveStatus.text="Status :"+item.Status
            binding.subReason.visibility=View.GONE
            binding.owner.visibility=View.GONE
            binding.EstimatedDoneBy.visibility=View.GONE
            binding.linearSite.setOnClickListener {
                binding.linearSite.setOnClickListener {
                    CoroutineScope(Dispatchers.IO).launch {
                        val b = Bundle()
                     //   b.putString("WanNo", item.PresalesTaskID)
                        b.putString("SiteId", item.PresalesTaskID)
                        b.putString("Status", "3")

                        val activity =context as AppCompatActivity
                        val wanFrag = WanFrag()
                        wanFrag.arguments=b
                        activity.supportFragmentManager.beginTransaction()
                            .replace(com.spectra.fieldforce.R.id.fragment_opp, wanFrag).addToBackStack(null).commit()
                    }
                }
            }

        }
    }

}