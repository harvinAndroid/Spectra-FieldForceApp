package com.spectra.fieldforce.adapter;



import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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
import com.spectra.fieldforce.databinding.WcrItemServiceBinding;
import com.spectra.fieldforce.databinding.WcrItemsconsumptionListBinding;
import com.spectra.fieldforce.fragment.IREditItemConsumption;
import com.spectra.fieldforce.fragment.IRFragment;
import com.spectra.fieldforce.fragment.WcrEditEquipmentConsumption;
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

public class WcrServiceConsumptionListAdapter extends RecyclerView.Adapter<WcrServiceConsumptionListAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<WcrResponse.ServiceConsumtion> serviceConsumtions;
    WcrItemServiceBinding binding;
    private String add,orderId;

    public WcrServiceConsumptionListAdapter(FragmentActivity activity, ArrayList<WcrResponse.ServiceConsumtion> serviceConsumtions,String add,String OrderId) {
        this.context = activity;
        this.serviceConsumtions = serviceConsumtions;
        this.add = add;
        this.orderId = OrderId;
    }

    @NotNull
    @Override
    public WcrServiceConsumptionListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                            int viewType) {
         binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.wcr_item_service, parent, false);

        return new WcrServiceConsumptionListAdapter.MyViewHolder(binding);
    }



    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        WcrResponse.ServiceConsumtion item = serviceConsumtions.get(position);
        holder.binding.setItemConsumption(item);
        if(add.equals("1")){
            holder.binding.delete.setVisibility(View.VISIBLE);
            holder.binding.edit.setVisibility(View.VISIBLE);
        }
        holder.binding.delete.setOnClickListener(v -> {
            deleteItem(item.getItemID(),item.getCanid());
        });

        holder.binding.edit.setOnClickListener(v -> {
            Bundle b = new Bundle();
            b.putString("ItemId", item.getItemID());
            b.putString("GuIID", item.getWcrguidid());
            b.putString("canId", item.getCanid());
            b.putString("OrderId",orderId);
            AppCompatActivity activity = (AppCompatActivity) context;
            Fragment myFragment = new WcrEditItemConsumption();
            myFragment.setArguments(b);
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, myFragment).addToBackStack(null).commit();
        });
    }

    private void deleteItem(String itemID, String canid){
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
                        if (response.body().getResponse().getStatusCode()==200){
                            Toast.makeText(context,response.body().getResponse().getMessage(),Toast.LENGTH_LONG).show();
                            Bundle b = new Bundle();
                            b.putString("canId", canid);
                            b.putString("OrderId",orderId);
                            AppCompatActivity activity = (AppCompatActivity) context;
                            Fragment myFragment = new WcrFragment();
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
        return serviceConsumtions.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
         WcrItemServiceBinding binding;

         MyViewHolder(WcrItemServiceBinding binding) {
           super(binding.getRoot());
           this.binding = binding;
        }

    }


}
