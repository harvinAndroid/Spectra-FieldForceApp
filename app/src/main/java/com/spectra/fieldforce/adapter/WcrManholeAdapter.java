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
import com.spectra.fieldforce.databinding.AdapterManholeBinding;
import com.spectra.fieldforce.model.gpon.response.WcrResponse;
import com.spectra.fieldforce.utils.Constants;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class WcrManholeAdapter extends RecyclerView.Adapter<WcrManholeAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<WcrResponse.ManHoleDetails> manHoleDetails;
    AdapterManholeBinding binding;

    public WcrManholeAdapter(FragmentActivity activity, ArrayList<WcrResponse.ManHoleDetails> manHoleDetails) {
        this.context = activity;
        this.manHoleDetails = manHoleDetails;
    }

    @NotNull
    @Override
    public WcrManholeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
         binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.adapter_manhole, parent, false);
        return new WcrManholeAdapter.MyViewHolder(binding);
    }



    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        WcrResponse.ManHoleDetails manHoleDetails1 = manHoleDetails.get(position);
       holder.binding.setEquipment(manHoleDetails1);

    }


    @Override
    public int getItemCount() {

        return manHoleDetails.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        AdapterManholeBinding binding;
        MyViewHolder( AdapterManholeBinding binding) {
            super(binding.getRoot());
           this.binding = binding;
        }

    }

}
