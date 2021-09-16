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
import com.spectra.fieldforce.databinding.IrItemServiceBinding;
import com.spectra.fieldforce.databinding.WcrItemServiceBinding;
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

public class IRServiceConsumptionListAdapter extends RecyclerView.Adapter<IRServiceConsumptionListAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<IrInfoResponse.ServiceConsumtionList> serviceConsumtions;
    IrItemServiceBinding binding;
    String IrStatusReport,add ;
    public IRServiceConsumptionListAdapter(FragmentActivity activity, ArrayList<IrInfoResponse.ServiceConsumtionList> serviceConsumtions,String irStatusReport,String add) {
        this.context = activity;
        this.serviceConsumtions = serviceConsumtions;
        this.IrStatusReport = irStatusReport;
        this.add = add;
    }

    @NotNull
    @Override
    public IRServiceConsumptionListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                           int viewType) {
         binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.ir_item_service, parent, false);

        return new IRServiceConsumptionListAdapter.MyViewHolder(binding);
    }



    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        IrInfoResponse.ServiceConsumtionList item = serviceConsumtions.get(position);
        holder.binding.setItemConsumption(item);

        holder.binding.delete.setOnClickListener(v -> {
            deleteItem(item.getItemID(),item.getCANID());
        });

        holder.binding.edit.setOnClickListener(v -> {
            Bundle b = new Bundle();
            b.putString("ItemId", item.getItemID());
            b.putString("GuIID", item.getWCRGUIDID());
            b.putString("canId", item.getCANID());
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
                              /*  AppCompatActivity activity1 = (AppCompatActivity) context;
                                Bundle b = new Bundle();
                                b.putString("canId", id);
                                myFragment.setArguments(b);
                                activity1.getSupportFragmentManager().beginTransaction().add(R.id.frag_container, new WcrFragment(), WcrFragment.class.getSimpleName()).addToBackStack(null
                                ).commit();*/

                            Bundle b = new Bundle();
                            b.putString("canId", canid);
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
        IrItemServiceBinding binding;

         MyViewHolder(IrItemServiceBinding binding) {
           super(binding.getRoot());
           this.binding = binding;
        }

    }


}
