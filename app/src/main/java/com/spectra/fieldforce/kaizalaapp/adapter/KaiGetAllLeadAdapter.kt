import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.spectra.fieldforce.databinding.GetAllLeadAdapterBinding
import com.spectra.fieldforce.kaizalaapp.fragment.KaiUpdateContactFragment
import com.spectra.fieldforce.kaizalaapp.fragment.KaiUpdateLeadFragment

import com.spectra.fieldforce.salesapp.activity.UpdateLeadActivity
import com.spectra.fieldforce.salesapp.model.AllLeadData


class KaiGetAllLeadAdapter(private val items: List<AllLeadData>, private val context: Context?) : RecyclerView.Adapter<KaiGetAllLeadAdapter.ViewHolder>() {

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
                val b = Bundle()
                b.putString("LeadID",item.LeadId )
                b.putString("LeadStatus",item.Status)
                val activity =context as AppCompatActivity
                val kaiUpdateLeadFragment = KaiUpdateLeadFragment()
                kaiUpdateLeadFragment.arguments=b
                activity.supportFragmentManager.beginTransaction()
                    .replace(com.spectra.fieldforce.R.id.frag_container, kaiUpdateLeadFragment).addToBackStack(null).commit()
            }
        }
    }
}