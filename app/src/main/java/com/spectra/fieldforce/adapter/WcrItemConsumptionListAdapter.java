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
import com.spectra.fieldforce.databinding.WcrItemsconsumptionListBinding;
import com.spectra.fieldforce.model.gpon.response.WcrResponse;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class WcrItemConsumptionListAdapter extends RecyclerView.Adapter<WcrItemConsumptionListAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<WcrResponse.ItemConsumtion> itemConsumtions;
    WcrItemsconsumptionListBinding binding;

    public WcrItemConsumptionListAdapter(FragmentActivity activity, ArrayList<WcrResponse.ItemConsumtion> itemConsumtions) {
        this.context = activity;
        this.itemConsumtions = itemConsumtions;
    }

    @NotNull
    @Override
    public WcrItemConsumptionListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                         int viewType) {

         binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.wcr_itemsconsumption_list, parent, false);

        return new WcrItemConsumptionListAdapter.MyViewHolder(binding);
    }



    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        WcrResponse.ItemConsumtion item = itemConsumtions.get(position);
        holder.binding.setItemConsumption(item);
    }


    @Override
    public int getItemCount() {

        return itemConsumtions.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

         WcrItemsconsumptionListBinding binding;
        MyViewHolder( WcrItemsconsumptionListBinding binding) {
            super(binding.getRoot());
           this.binding = binding;
        }

    }

}
