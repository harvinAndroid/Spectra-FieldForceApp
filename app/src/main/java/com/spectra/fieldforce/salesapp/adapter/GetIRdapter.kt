import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spectra.fieldforce.databinding.GetAllFeasibiltyAdapterBinding
import com.spectra.fieldforce.databinding.GetAllLeadAdapterBinding
import com.spectra.fieldforce.salesapp.activity.CAFActivity
import com.spectra.fieldforce.salesapp.activity.UpdateLeadActivity
import com.spectra.fieldforce.salesapp.model.AllLeadData
import com.spectra.fieldforce.salesapp.model.CafData
import com.spectra.fieldforce.salesapp.model.FeasData
import com.spectra.fieldforce.salesapp.model.IRData


class GetIRdapter(private val items: List<IRData>, private val context: Context?) : RecyclerView.Adapter<GetIRdapter.ViewHolder>() {

    lateinit var binding: GetAllFeasibiltyAdapterBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = GetAllFeasibiltyAdapterBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun getItemCount():
            Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    inner class ViewHolder(val binding: GetAllFeasibiltyAdapterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: IRData) {
            binding.FeasibilityId.text = "IR Id : " +item.IRId
            binding.FeasibilityStatus.text= "IR Status : " +item.IrStatus
            binding.Opportunity.text = "Installation Date : " +item.InstallationDate
            binding.CustomerName.text= "Network Technology : " +item.Ntw_Technology
            binding.RouteType.visibility= View.GONE
            binding.EstimatedDoneBy.visibility= View.GONE


        }
    }
}