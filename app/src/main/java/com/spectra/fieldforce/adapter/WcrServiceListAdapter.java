package com.spectra.fieldforce.adapter;



import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.spectra.fieldforce.R;
import com.spectra.fieldforce.databinding.WcrServiceListBinding;
import com.spectra.fieldforce.model.gpon.response.WcrResponse;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public class WcrServiceListAdapter extends RecyclerView.Adapter<WcrServiceListAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<WcrResponse.ServiceConsumtion> serviceConsumtions;
    WcrServiceListBinding binding;


    public WcrServiceListAdapter(FragmentActivity activity, ArrayList<WcrResponse.ServiceConsumtion> serviceConsumtions) {
        this.context = activity;
        this.serviceConsumtions = serviceConsumtions;
    }

    @NotNull
    @Override
    public WcrServiceListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {

         binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.wcr_service_list, parent, false);
        return new WcrServiceListAdapter.MyViewHolder(binding);
    }



    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        WcrResponse.ServiceConsumtion item = serviceConsumtions.get(position);
        holder.binding.setItemConsumption(item);
    }

    @Override
    public int getItemCount() {
        return serviceConsumtions.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        WcrServiceListBinding binding;
        MyViewHolder( WcrServiceListBinding binding) {
            super(binding.getRoot());
           this.binding = binding;
        }

    }

}
