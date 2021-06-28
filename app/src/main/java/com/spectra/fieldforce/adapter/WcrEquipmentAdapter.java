package com.spectra.fieldforce.adapter;



import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.spectra.fieldforce.R;
import com.spectra.fieldforce.api.ApiClient;
import com.spectra.fieldforce.api.ApiInterface;
import com.spectra.fieldforce.databinding.AdapterWcrequipmentBinding;
import com.spectra.fieldforce.databinding.AdapterWcrequipmentListBinding;
import com.spectra.fieldforce.fragment.IREditEquipmentConsumption;
import com.spectra.fieldforce.fragment.IRFragment;
import com.spectra.fieldforce.model.gpon.request.DeleteItemRequest;
import com.spectra.fieldforce.model.gpon.response.CommonClassResponse;
import com.spectra.fieldforce.model.gpon.response.WcrResponse;
import com.spectra.fieldforce.utils.Constants;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WcrEquipmentAdapter extends RecyclerView.Adapter<WcrEquipmentAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<WcrResponse.EquipmentDetailsList> equipmentDetailsLists;
    AdapterWcrequipmentBinding binding;

    public WcrEquipmentAdapter(FragmentActivity activity, ArrayList<WcrResponse.EquipmentDetailsList> equipmentDetailsLists) {
        this.context = activity;
        this.equipmentDetailsLists = equipmentDetailsLists;
    }

    @NotNull
    @Override
    public WcrEquipmentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
         binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.adapter_wcrequipment, parent, false);
        return new WcrEquipmentAdapter.MyViewHolder(binding);
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

        AdapterWcrequipmentBinding binding;
        MyViewHolder( AdapterWcrequipmentBinding binding) {
            super(binding.getRoot());
           this.binding = binding;
        }

    }

}
