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
import com.spectra.fieldforce.databinding.IrItemConsumptionDetailsBinding;
import com.spectra.fieldforce.databinding.IrItemCousumptionBinding;
import com.spectra.fieldforce.databinding.WcrItemsconsumptionListBinding;
import com.spectra.fieldforce.model.gpon.response.IrInfoResponse;
import com.spectra.fieldforce.model.gpon.response.WcrResponse;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class IRItemConsumptionListAdapter extends RecyclerView.Adapter<IRItemConsumptionListAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<IrInfoResponse.ItemConsumtion> itemConsumtions;
    IrItemCousumptionBinding binding;

    public IRItemConsumptionListAdapter(FragmentActivity activity, ArrayList<IrInfoResponse.ItemConsumtion> itemConsumtions) {
        this.context = activity;
        this.itemConsumtions = itemConsumtions;
    }

    @NotNull
    @Override
    public IRItemConsumptionListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                        int viewType) {

         binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.ir_item_cousumption, parent, false);

        return new IRItemConsumptionListAdapter.MyViewHolder(binding);
    }



    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        IrInfoResponse.ItemConsumtion item = itemConsumtions.get(position);
        holder.binding.setItemConsumption(item);
    }


    @Override
    public int getItemCount() {

        return itemConsumtions.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        IrItemCousumptionBinding binding;
        MyViewHolder( IrItemCousumptionBinding binding) {
            super(binding.getRoot());
           this.binding = binding;
        }

    }

}
