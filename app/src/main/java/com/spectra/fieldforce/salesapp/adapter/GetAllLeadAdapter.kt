import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent
import android.os.Bundle
import com.spectra.fieldforce.databinding.GetAllLeadAdapterBinding

import com.spectra.fieldforce.salesapp.activity.UpdateLeadActivity
import com.spectra.fieldforce.salesapp.model.AllLeadData


class GetAllLeadAdapter(private val items: List<AllLeadData>, private val context: Context?) : RecyclerView.Adapter<GetAllLeadAdapter.ViewHolder>() {

    lateinit var binding: GetAllLeadAdapterBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = GetAllLeadAdapterBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun getItemCount():
            Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    inner class ViewHolder(val binding: GetAllLeadAdapterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AllLeadData) {
            binding.status.text = item.LeadName + " "+ "("+item.LeadId+")"
            binding.tvMob.text = "Mobile No. : "+item.MobileNumber
            binding.tvEmail.text = "Email ID : "+item.EmailId
            binding.orderStatus.text = "Status : "+item.Status
            binding.linearLead.setOnClickListener(){
                val intent = Intent(context, UpdateLeadActivity::class.java)
                val bundle = Bundle()
                bundle.putString("LeadId",item.LeadId )
                bundle.putString("LeadStatus",item.Status)
                intent.putExtras(bundle)
                context?.startActivity(intent)
            }
        }
    }
}