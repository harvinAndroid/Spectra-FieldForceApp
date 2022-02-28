import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spectra.fieldforce.databinding.GetAllFeasibiltyAdapterBinding
import com.spectra.fieldforce.databinding.GetAllLeadAdapterBinding
import com.spectra.fieldforce.databinding.GetRfsAdapterBinding
import com.spectra.fieldforce.salesapp.activity.CAFActivity
import com.spectra.fieldforce.salesapp.activity.UpdateLeadActivity
import com.spectra.fieldforce.salesapp.model.*


class GetRFSAdapter(private val items: List<RFSData>, private val context: Context?) : RecyclerView.Adapter<GetRFSAdapter.ViewHolder>() {

    lateinit var binding: GetRfsAdapterBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = GetRfsAdapterBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun getItemCount():
            Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    inner class ViewHolder(val binding: GetRfsAdapterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RFSData) {
            binding.item1.text = "Name : " +item.Name
            binding.item2.text= "Owner : " +item.Owner
            binding.item3.text = "RFS Task Status  : " +item.RfsTaskStatus
            binding.item4.text="Gateway City : " +item.GatewayCity
            binding.item5.text="Gateway   : " +item.Gateway
            binding.item6.text= "Nagios IP  : " +item.NagiosIp
            binding.item7.text = "Nagios Host Name : " +item.NagiosHostName
            binding.item8.text= "Fail over Tested : " +item.FailoverTested
            binding.item9.text = "Speed Test  : " +item.SpeedTest
            binding.item10.text="RFS Date  : " +item.RfsDate
            binding.item11.text="Customer IP Pool   : " +item.CustomerIpPool
            binding.item12.text= "Backened Config Done  : " +item.BackenedConfigDone
            binding.item13.text= "Downtime : " +item.DownTime
        }
    }
}