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
import com.spectra.fieldforce.databinding.AdapterIrequipmentListBinding;
import com.spectra.fieldforce.databinding.AdapterManholeListBinding;
import com.spectra.fieldforce.fragment.SRDetailFragment;
import com.spectra.fieldforce.fragment.WcrEditItemConsumption;
import com.spectra.fieldforce.fragment.WcrEditManholeFragment;
import com.spectra.fieldforce.fragment.WcrFragment;
import com.spectra.fieldforce.model.gpon.request.DeleteItemRequest;
import com.spectra.fieldforce.model.gpon.response.CommonClassResponse;
import com.spectra.fieldforce.model.gpon.response.IrInfoResponse;
import com.spectra.fieldforce.model.gpon.response.WcrResponse;
import com.spectra.fieldforce.utils.Constants;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WcrAddManholeAdapter extends RecyclerView.Adapter<WcrAddManholeAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<WcrResponse.ManHoleDetails> manHoleDetails;
    AdapterManholeListBinding binding;

    public WcrAddManholeAdapter(FragmentActivity activity,  ArrayList<WcrResponse.ManHoleDetails> manHoleDetails) {
        this.context = activity;
        this.manHoleDetails = manHoleDetails;
    }

    @NotNull
    @Override
    public WcrAddManholeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {
         binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.adapter_manhole_list, parent, false);
        return new WcrAddManholeAdapter.MyViewHolder(binding);
    }



    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        WcrResponse.ManHoleDetails manHoleDetails1 = manHoleDetails.get(position);
       holder.binding.setEquipment(manHoleDetails1);

        holder.binding.tvDelete.setOnClickListener(v -> {
            deleteItem(manHoleDetails1.getItemID());
        });
        holder.binding.tvEdit.setOnClickListener(v -> {
            Bundle b = new Bundle();
            b.putString("canId", manHoleDetails1.getCanid());
            b.putString("GuIID", manHoleDetails1.getWcrguidid());
            b.putString("ItemId",manHoleDetails1.getItemID());
            AppCompatActivity activity = (AppCompatActivity) context;
            Fragment myFragment = new WcrEditManholeFragment();
            myFragment.setArguments(b);
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, myFragment).addToBackStack(null).commit();
        });

    }

    private void deleteItem(String itemID){
        DeleteItemRequest deleteItemRequest = new DeleteItemRequest();
        deleteItemRequest.setAuthkey(Constants.AUTH_KEY);
        deleteItemRequest.setAction(Constants.DELETE_MANHOLE_BYID);
        deleteItemRequest.setItemId(itemID);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonClassResponse> call = apiService.deleteItemById(deleteItemRequest);
        call.enqueue(new Callback<CommonClassResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<CommonClassResponse> call, Response<CommonClassResponse> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                    try {
                        if (response.body().getResponse().getStatusCode()==200){
                            Toast.makeText(context,response.body().getResponse().getMessage(),Toast.LENGTH_LONG).show();
                            AppCompatActivity activity1 = (AppCompatActivity) context;
                            activity1.getSupportFragmentManager().beginTransaction().add(R.id.frag_container, new WcrFragment(), WcrFragment.class.getSimpleName()).addToBackStack(null
                            ).commit();
                        }

                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<CommonClassResponse> call, Throwable t) {
                Log.e("RetroError", t.toString());
            }
        });
    }


    @Override
    public int getItemCount() {

        return manHoleDetails.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        AdapterManholeListBinding binding;
        MyViewHolder( AdapterManholeListBinding binding) {
            super(binding.getRoot());
           this.binding = binding;
        }

    }

}
