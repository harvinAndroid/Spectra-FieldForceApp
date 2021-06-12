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
import com.spectra.fieldforce.databinding.AdapterIrequipmentListBinding;
import com.spectra.fieldforce.databinding.AdapterWcrequipmentListBinding;
import com.spectra.fieldforce.model.gpon.response.IrInfoResponse;
import com.spectra.fieldforce.model.gpon.response.WcrResponse;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class IrEquipmentConsumpAdapter extends RecyclerView.Adapter<IrEquipmentConsumpAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<IrInfoResponse.InstallationItemList> equipmentDetailsLists;
        AdapterIrequipmentListBinding binding;

    public IrEquipmentConsumpAdapter(FragmentActivity activity, ArrayList<IrInfoResponse.InstallationItemList> equipmentDetailsLists) {
        this.context = activity;
        this.equipmentDetailsLists = equipmentDetailsLists;
    }

    @NotNull
    @Override
    public IrEquipmentConsumpAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                     int viewType) {
         binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.adapter_irequipment_list, parent, false);
        return new IrEquipmentConsumpAdapter.MyViewHolder(binding);
    }



    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        IrInfoResponse.InstallationItemList equipment = equipmentDetailsLists.get(position);
       holder.binding.setEquipment(equipment);
    }


    @Override
    public int getItemCount() {

        return equipmentDetailsLists.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

         AdapterIrequipmentListBinding binding;
        MyViewHolder( AdapterIrequipmentListBinding binding) {
            super(binding.getRoot());
           this.binding = binding;
        }

    }

}
