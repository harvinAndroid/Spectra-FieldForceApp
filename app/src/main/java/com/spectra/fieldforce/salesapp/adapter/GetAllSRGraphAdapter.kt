import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import com.spectra.fieldforce.databinding.GetAllLeadAdapterBinding

import com.spectra.fieldforce.salesapp.activity.UpdateLeadActivity
import com.spectra.fieldforce.salesapp.model.AllLeadData
import com.spectra.fieldforce.salesapp.model.ChartData


class GetAllSRGraphAdapter(private val items: List<ChartData>, private val context: Context?) : RecyclerView.Adapter<GetAllSRGraphAdapter.ViewHolder>() {

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
        fun bind(item: ChartData) {
            binding.status.text = "Created On : "+item.CreatedOn
            binding.tvMob.text = "SubType : "+item.SubType
            binding.tvEmail.text = "Customer : "+item.Customer
            binding.orderStatus.visibility=View.GONE
        }
    }
}