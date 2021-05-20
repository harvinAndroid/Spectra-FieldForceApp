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
import com.spectra.fieldforce.databinding.AdapterWcrequipmentListBinding;
import com.spectra.fieldforce.model.gpon.response.WcrResponse;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class WcrEquipmentConsumpAdapter extends RecyclerView.Adapter<WcrEquipmentConsumpAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<WcrResponse.EquipmentDetailsList> equipmentDetailsLists;
    AdapterWcrequipmentListBinding binding;

    public WcrEquipmentConsumpAdapter(FragmentActivity activity, ArrayList<WcrResponse.EquipmentDetailsList> equipmentDetailsLists) {
        this.context = activity;
        this.equipmentDetailsLists = equipmentDetailsLists;
    }

    @NotNull
    @Override
    public WcrEquipmentConsumpAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                      int viewType) {
         binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.adapter_wcrequipment_list, parent, false);
        return new WcrEquipmentConsumpAdapter.MyViewHolder(binding);
    }



    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        WcrResponse.EquipmentDetailsList equipment = equipmentDetailsLists.get(position);
       holder.binding.setEquipment(equipment);
    }


    @Override
    public int getItemCount() {

        return equipmentDetailsLists.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

         AdapterWcrequipmentListBinding binding;
        MyViewHolder( AdapterWcrequipmentListBinding binding) {
            super(binding.getRoot());
           this.binding = binding;
        }

    }

}
