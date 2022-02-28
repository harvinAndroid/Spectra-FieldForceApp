import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent
import android.os.Bundle
import com.spectra.fieldforce.databinding.GetAllOppurtunityAdapterBinding
import com.spectra.fieldforce.salesapp.activity.OpportunityActivity

import com.spectra.fieldforce.salesapp.model.OppurData


class GetAllOppurtunityAdapter(private val items: List<OppurData>, private val context: Context?) : RecyclerView.Adapter<GetAllOppurtunityAdapter.ViewHolder>() {

    lateinit var binding:GetAllOppurtunityAdapterBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = GetAllOppurtunityAdapterBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun getItemCount():
            Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    inner class ViewHolder(val binding: GetAllOppurtunityAdapterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: OppurData) {
            binding.status.text = item.LeadName + " "+ "("+item.OppId+")"
            binding.tvMob.text = "Mobile No. : "+item.Mobile
            binding.tvEmail.text = "Email ID : "+item.Email
            binding.orderStatus.text = "Status : "+item.Status
            binding.linearLead.setOnClickListener(){
                val intent = Intent(context, OpportunityActivity::class.java)
                val bundle = Bundle()
                bundle.putString("LeadId",item.LeadId )
                bundle.putString("OppId",item.OppId )
                intent.putExtras(bundle)
                context?.startActivity(intent)
            }

        }
    }
}