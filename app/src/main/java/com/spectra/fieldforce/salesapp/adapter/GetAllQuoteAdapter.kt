import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent
import android.os.Bundle
import com.spectra.fieldforce.databinding.GetAllFeasibiltyAdapterBinding
import com.spectra.fieldforce.databinding.GetAllLeadAdapterBinding

import com.spectra.fieldforce.salesapp.activity.UpdateLeadActivity
import com.spectra.fieldforce.salesapp.model.AllLeadData
import com.spectra.fieldforce.salesapp.model.FeasData
import com.spectra.fieldforce.salesapp.model.QuoteData


class GetAllQuoteAdapter(private val items: List<QuoteData>, private val context: Context?,val str_oppId:String) : RecyclerView.Adapter<GetAllQuoteAdapter.ViewHolder>() {

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
        fun bind(item: QuoteData) {
            binding.status.text = "Opportunity Name : "+item.OpportunityName
            binding.tvMob.text = "Quote Id : "+item.Quoteid
            binding.orderStatus.text = "Status : "+item.Status
        }
    }
}