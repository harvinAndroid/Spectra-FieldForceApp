package com.spectra.fieldforce.adapter;



import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.spectra.fieldforce.R;
import com.spectra.fieldforce.activity.BucketTabActivity;
import com.spectra.fieldforce.api.ApiClient;
import com.spectra.fieldforce.api.ApiInterface;
import com.spectra.fieldforce.databinding.AdpterAllBucketListBinding;
import com.spectra.fieldforce.fragment.MyBucketList;
import com.spectra.fieldforce.fragment.SRDetailFragment;
import com.spectra.fieldforce.fragment.WcrFragment;
import com.spectra.fieldforce.model.CommonResponse;
import com.spectra.fieldforce.model.gpon.request.AddBucketListRequest;
import com.spectra.fieldforce.model.gpon.request.UpdateWcrEnggRequest;
import com.spectra.fieldforce.model.gpon.response.CommonClassResponse;
import com.spectra.fieldforce.model.gpon.response.GetAllBucketList;
import com.spectra.fieldforce.utils.Constants;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllBucketListAdapter extends RecyclerView.Adapter<AllBucketListAdapter.MyViewHolder> {
    private Context context;
    private List<GetAllBucketList.Response> allbucketlist;
    AdpterAllBucketListBinding binding;

    public AllBucketListAdapter(FragmentActivity activity, List<GetAllBucketList.Response> allbucketlist) {
        this.context = activity;
        this.allbucketlist = allbucketlist;
    }

    @NotNull
    @Override
    public AllBucketListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {
         binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.adpter_all_bucket_list, parent, false);

        return new AllBucketListAdapter.MyViewHolder(binding);
    }



    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        GetAllBucketList.Response itemList = allbucketlist.get(position);
        holder.binding.setAllBucketList(itemList);
        holder.binding.enggNm.setText("Engineer Name: " +itemList.getEngineerName());
        String enggName = holder.binding.enggNm.getText().toString();
       //String enggName = itemList.getEngineerName();
        if(enggName.isEmpty()||enggName.equals("Engineer Name: ")){
            binding.tvAdd.setText("ADD");
            // binding.tvAdd.setBackgroundColor(context.getResources().getColor(android.R.color.secondary_text_light));
        }else{
            binding.tvAdd.setEnabled(false);
            binding.tvAdd.setText("Assigned");
           // Toast.makeText(context,"Already Assigned",Toast.LENGTH_LONG).show();
        }
        holder.binding.tvAdd.setOnClickListener(v -> {
            AllBucketListAdapter.this.addItemToBucket(itemList.getId(),itemList.getWcrId(),itemList.getIrId(), itemList.getOrderType(), itemList.getCanId(), itemList.getCustomerName(), itemList.getPodName(),
                    itemList.getStatus(), itemList.getHoldCategory(), itemList.getHoldReason(), itemList.getEngineerName(), itemList.getNetworkTechnology());


            if(itemList.getOrderType().equals("WCR")){
                updateEnginer("updateWCREngineer", itemList.getCustomerName(), itemList.getWcrId());
            }else if(itemList.getOrderType().equals("IR")){
                updateEnginer("engineerUpdateIR", itemList.getCustomerName(),itemList.getIrId());
            }
        });

    }

    private void updateEnginer(String updateWCREngineer, String customerName, String wcrId){
        UpdateWcrEnggRequest updateWcrEnggRequest = new UpdateWcrEnggRequest();
        updateWcrEnggRequest.setAuthkey(Constants.AUTH_KEY);
        updateWcrEnggRequest.setAction(updateWCREngineer);
        updateWcrEnggRequest.setEngName(customerName);
        updateWcrEnggRequest.setInstcode("");
        updateWcrEnggRequest.setWCRguidId(wcrId);
        updateWcrEnggRequest.setAppointmentDate("");

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonClassResponse> call = apiService.updateWcrEng(updateWcrEnggRequest);
        call.enqueue(new Callback<CommonClassResponse>() {
            @Override
            public void onResponse(Call<CommonClassResponse> call, Response<CommonClassResponse> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                    try {
                        if(response.body().getStatus().equals("Success")){
                           // Toast.makeText(context,response.body().getResponse().getMessage(),Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(context,response.body().getResponse().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<CommonClassResponse> call, Throwable t) {
                Log.e("RetroError", t.toString());
            }
        });
       notifyDataSetChanged();
    }


    private void addItemToBucket(String id, String wcrId, String irId, String orderType, String canId, String customerName, String podName, String status, String holdCategory, String holdReason, String engineerName, String networkTechnology){
        SharedPreferences sp1=context.getSharedPreferences("Login",0);
        String vendor =sp1.getString("VenderCode", null);
        String enggId = sp1.getString("EnggId", null);
        AddBucketListRequest addBucketListRequest = new AddBucketListRequest();
        addBucketListRequest.setAuthkey(Constants.AUTH_KEY);
        addBucketListRequest.setAction(Constants.ADD_ORDER_TOBUCKET);
        addBucketListRequest.setOrder_id(id);
        addBucketListRequest.setWCRId(wcrId);
        addBucketListRequest.setIRId(irId);
        addBucketListRequest.setCustomerID(canId);
        addBucketListRequest.setCustomerName(customerName);
        addBucketListRequest.setPODName(podName);
        addBucketListRequest.setCRM_status(status);
        addBucketListRequest.setHoldCategory(holdCategory);
        addBucketListRequest.setHoldReason(holdReason);
        addBucketListRequest.setEngineerName(engineerName);
        addBucketListRequest.setNetworkTechnology(networkTechnology);
        addBucketListRequest.setEngineerID(enggId);
        addBucketListRequest.setVendorCode(vendor);
        addBucketListRequest.setOrder_type(orderType);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonResponse> call = apiService.addItemToBucket(addBucketListRequest);
        call.enqueue(new Callback<CommonResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                    try {
                        if (response.body().getStatus().equals("Success")){
                            Toast.makeText(context,"Order has been Added into bucket.",Toast.LENGTH_LONG).show();
                            Intent i = new Intent(context, BucketTabActivity.class);
                            context.startActivity(i);
                           /* AppCompatActivity activity1 = (AppCompatActivity) context;
                            activity1.getSupportFragmentManager().beginTransaction().add(R.id.frag_container, new MyBucketList(), MyBucketList.class.getSimpleName()).addToBackStack(null
                            ).commit();*/
                        }else if(response.body().getStatus().equals("Failure")){
                            Toast.makeText(context,"Not Added in bucket.",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(context,"Something went wrong...",Toast.LENGTH_LONG).show();

                        }

                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                Log.e("RetroError", t.toString());
            }
        });
    }





    @Override
    public int getItemCount() {

        return allbucketlist.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        AdpterAllBucketListBinding binding;
        MyViewHolder( AdpterAllBucketListBinding binding) {
            super(binding.getRoot());
           this.binding = binding;
        }

    }

}
