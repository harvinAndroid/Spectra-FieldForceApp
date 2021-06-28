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
import com.spectra.fieldforce.databinding.WcrConsumptionListBinding;
import com.spectra.fieldforce.databinding.WcrItemsconsumptionListBinding;
import com.spectra.fieldforce.fragment.WcrEditItemConsumption;
import com.spectra.fieldforce.fragment.WcrFragment;
import com.spectra.fieldforce.model.gpon.request.DeleteItemRequest;
import com.spectra.fieldforce.model.gpon.response.CommonClassResponse;
import com.spectra.fieldforce.model.gpon.response.WcrResponse;
import com.spectra.fieldforce.utils.Constants;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WcrCConsumptionListAdapter extends RecyclerView.Adapter<WcrCConsumptionListAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<WcrResponse.ItemConsumtion> itemConsumtions;
    WcrConsumptionListBinding binding;


    public WcrCConsumptionListAdapter(FragmentActivity activity, ArrayList<WcrResponse.ItemConsumtion> itemConsumtions) {
        this.context = activity;
        this.itemConsumtions = itemConsumtions;
    }

    @NotNull
    @Override
    public WcrCConsumptionListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                      int viewType) {

         binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.wcr_consumption_list, parent, false);

        return new WcrCConsumptionListAdapter.MyViewHolder(binding);
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

        WcrConsumptionListBinding binding;
        MyViewHolder( WcrConsumptionListBinding binding) {
            super(binding.getRoot());
           this.binding = binding;
        }

    }

}
