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
import com.spectra.fieldforce.databinding.AdapterWcrequipmentListBinding;
import com.spectra.fieldforce.fragment.IREditEquipmentConsumption;
import com.spectra.fieldforce.fragment.IRFragment;
import com.spectra.fieldforce.fragment.WcrEditEquipmentConsumption;
import com.spectra.fieldforce.fragment.WcrEditItemConsumption;
import com.spectra.fieldforce.fragment.WcrEquipmentConsumption;
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

public class WcrEquipmentConsumpAdapter extends RecyclerView.Adapter<WcrEquipmentConsumpAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<WcrResponse.EquipmentDetailsList> equipmentDetailsLists;
    AdapterWcrequipmentListBinding binding;
    String add,orderId;

    public WcrEquipmentConsumpAdapter(FragmentActivity activity, ArrayList<WcrResponse.EquipmentDetailsList> equipmentDetailsLists, String add,String OrderId) {
        this.context = activity;
        this.equipmentDetailsLists = equipmentDetailsLists;
        this.add = add;
        this.orderId = OrderId;
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
        holder.binding.etIte6.setText("Quantity: "+equipment.getQuantity().toString());

        if(add.equals("1")){
            holder.binding.tvDelete.setVisibility(View.VISIBLE);
            holder.binding.tvEdit.setVisibility(View.VISIBLE);
        }
        holder.binding.tvDelete.setOnClickListener(v -> {
            deleteItem(equipment.getItemID(),equipment.getCANID());
        });

        holder.binding.tvEdit.setOnClickListener(v -> {
            Bundle b = new Bundle();
            b.putString("ItemId", equipment.getItemID());
            b.putString("GuIID", equipment.getWCRGUIDID());
            b.putString("canId", equipment.getCANID());
            b.putString("OrderId", orderId);
            AppCompatActivity activity = (AppCompatActivity) context;
            Fragment myFragment = new WcrEditEquipmentConsumption();
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
                            b.putString("OrderId", orderId);
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
