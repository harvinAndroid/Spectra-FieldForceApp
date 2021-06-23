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
import com.spectra.fieldforce.databinding.IrItemConsumptionDetailsBinding;
import com.spectra.fieldforce.databinding.IrItemCousumptionBinding;
import com.spectra.fieldforce.databinding.WcrItemsconsumptionListBinding;
import com.spectra.fieldforce.fragment.IREditItemConsumption;
import com.spectra.fieldforce.fragment.IRFragment;
import com.spectra.fieldforce.fragment.WcrEditItemConsumption;
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
        holder.binding.tvEdit.setOnClickListener(v -> {
            Bundle b = new Bundle();
            b.putString("ItemId", item.getItemID());
            b.putString("IrID", item.getIrguid());
            b.putString("canId",item.getCanid());
            AppCompatActivity activity = (AppCompatActivity) context;
            Fragment myFragment = new IREditItemConsumption();
            myFragment.setArguments(b);
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, myFragment).addToBackStack(null).commit();
        });
        holder.binding.tvDelete.setOnClickListener(v -> {
            deleteItem(item.getItemID(),item.getCanid());
        });
    }

    private void deleteItem(String id, String itemID){
        DeleteItemRequest deleteItemRequest = new DeleteItemRequest();
        deleteItemRequest.setAuthkey(Constants.AUTH_KEY);
        deleteItemRequest.setAction(Constants.DELETE_ITEM_CONSUMPTIONBY_ID);
        deleteItemRequest.setItemId(itemID);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonClassResponse> call = apiService.deleteItemById(deleteItemRequest);
        call.enqueue(new Callback<CommonClassResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<CommonClassResponse> call, Response<CommonClassResponse> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                    try {
                        if (response.body().getStatus().equals("Success")){
                            Toast.makeText(context,response.body().getResponse().getMessage(),Toast.LENGTH_LONG).show();

                            Bundle b = new Bundle();
                            b.putString("canId", id);
                            AppCompatActivity activity = (AppCompatActivity) context;
                            Fragment myFragment = new IRFragment();
                            myFragment.setArguments(b);
                            activity.getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, myFragment).addToBackStack(null).commit();

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
