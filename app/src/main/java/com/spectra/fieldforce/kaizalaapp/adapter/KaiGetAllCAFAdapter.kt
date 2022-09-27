import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.spectra.fieldforce.databinding.GetAllLeadAdapterBinding
import com.spectra.fieldforce.kaizalaapp.fragment.KaiCafUpdateFragment
import com.spectra.fieldforce.salesapp.model.CafData


class KaiGetAllCAFAdapter(private val items: List<CafData>, private val context: Context?) : RecyclerView.Adapter<KaiGetAllCAFAdapter.ViewHolder>() {

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
              val b = Bundle()
              b.putString("CafId",item.Cafid )
              val activity =context as AppCompatActivity
              val kaiCafUpdateFragment = KaiCafUpdateFragment()
              kaiCafUpdateFragment.arguments=b
              activity.supportFragmentManager.beginTransaction()
                  .replace(com.spectra.fieldforce.R.id.frag_container, kaiCafUpdateFragment).addToBackStack(null).commit()

          }
        }
    }
}