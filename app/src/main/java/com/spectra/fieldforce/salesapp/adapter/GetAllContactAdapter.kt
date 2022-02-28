import android.R
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.spectra.fieldforce.databinding.GetAllLeadAdapterBinding
import com.spectra.fieldforce.salesapp.activity.UpdateContactActivity
import com.spectra.fieldforce.salesapp.fragment.ContactFragment
import com.spectra.fieldforce.salesapp.model.ContactData


class GetAllContactAdapter(private val items: List<ContactData>, private val context: Context?) : RecyclerView.Adapter<GetAllContactAdapter.ViewHolder>() {

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

                val intent = Intent(context, UpdateContactActivity::class.java)
                val bundle = Bundle()
                bundle.putString("ContactID",item.ContactId )
                bundle.putString("LeadStatus",item.Status)
                intent.putExtras(bundle)
                context?.startActivity(intent)
            }
        }
    }
}