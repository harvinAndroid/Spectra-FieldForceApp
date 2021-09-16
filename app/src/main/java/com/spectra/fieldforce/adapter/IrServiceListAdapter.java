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
import com.spectra.fieldforce.databinding.IrServiceListBinding;
import com.spectra.fieldforce.databinding.WcrServiceListBinding;
import com.spectra.fieldforce.model.gpon.response.IrInfoResponse;
import com.spectra.fieldforce.model.gpon.response.WcrResponse;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class IrServiceListAdapter extends RecyclerView.Adapter<IrServiceListAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<IrInfoResponse.ServiceConsumtionList> serviceConsumtions;
    IrServiceListBinding binding;
    String IrStatusReport,add ;

    public IrServiceListAdapter(FragmentActivity activity, ArrayList<IrInfoResponse.ServiceConsumtionList> serviceConsumtions,String irStatusReport,String add) {
        this.context = activity;
        this.serviceConsumtions = serviceConsumtions;
        this.IrStatusReport = irStatusReport;
        this.add = add;
    }

    @NotNull
    @Override
    public IrServiceListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {

         binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.ir_service_list, parent, false);

        return new IrServiceListAdapter.MyViewHolder(binding);
    }



    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        IrInfoResponse.ServiceConsumtionList item = serviceConsumtions.get(position);
        holder.binding.setItemConsumption(item);
    }

    @Override
    public int getItemCount() {
        return serviceConsumtions.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        IrServiceListBinding binding;
        MyViewHolder( IrServiceListBinding binding) {
            super(binding.getRoot());
           this.binding = binding;
        }

    }

}
