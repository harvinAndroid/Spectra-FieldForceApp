import android.R
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.spectra.fieldforce.databinding.GetAllLeadAdapterBinding
import com.spectra.fieldforce.kaizalaapp.fragment.KaiUpdateContactFragment
import com.spectra.fieldforce.kaizalaapp.model.KContactData
import com.spectra.fieldforce.salesapp.activity.UpdateContactActivity
import com.spectra.fieldforce.salesapp.fragment.ContactFragment
import com.spectra.fieldforce.salesapp.fragment.WanFrag
import com.spectra.fieldforce.salesapp.model.ContactData


class KGetAllContactAdapter(private val items: List<ContactData>, private val context: Context?) : RecyclerView.Adapter<KGetAllContactAdapter.ViewHolder>() {

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
        fun bind(item: ContactData) {
            binding.status.text = item.FullName + " "+ "("+item.ContactId+")"
            binding.tvMob.text = "Mobile No. : "+item.MobileNumber
            binding.tvEmail.text = "Email ID : "+item.EmailAddress
            binding.orderStatus.text = "Status : "+item.Status
            binding.linearLead.setOnClickListener(){
                val b = Bundle()
                b.putString("ContactID",item.ContactId )
                b.putString("LeadStatus",item.Status)

                val activity =context as AppCompatActivity
                val kaiUpdateContactFragment = KaiUpdateContactFragment()
                kaiUpdateContactFragment.arguments=b
                activity.supportFragmentManager.beginTransaction()
                    .replace(com.spectra.fieldforce.R.id.frag_container, kaiUpdateContactFragment).addToBackStack(null).commit()
            }
        }
    }
}