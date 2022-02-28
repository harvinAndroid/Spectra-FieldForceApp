import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spectra.fieldforce.databinding.GetAllLeadAdapterBinding
import com.spectra.fieldforce.salesapp.activity.CAFActivity
import com.spectra.fieldforce.salesapp.model.CafData

 class ViewDocumentAdapter(private val items: List<CafData>, private val context: Context?) : RecyclerView.Adapter<ViewDocumentAdapter.ViewHolder>() {

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
        fun bind(item: CafData) {
            binding.status.text = item.LeadName + " " + "(" + item.Cafid + ")"
            binding.tvMob.text = "Mobile No. : " + item.MobileNo
            binding.tvEmail.text = "Email ID : " + item.EmailID
            binding.orderStatus.text = "Status : " + item.Status
            binding.linearLead.setOnClickListener() {
                val intent = Intent(context, CAFActivity::class.java)
                val bundle = Bundle()
                bundle.putString("CafId", item.Cafid)
                intent.putExtras(bundle)
                context?.startActivity(intent)

            }

        }
    }
}