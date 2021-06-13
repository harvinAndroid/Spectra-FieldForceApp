package com.spectra.fieldforce.adapter;



import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import com.spectra.fieldforce.activity.DashBoardActivity;
import com.spectra.fieldforce.activity.ProvisioningTabActivity;
import com.spectra.fieldforce.api.ApiClient;
import com.spectra.fieldforce.api.ApiInterface;
import com.spectra.fieldforce.databinding.AdpterAllBucketListBinding;
import com.spectra.fieldforce.databinding.AdpterMyBucketListBinding;
import com.spectra.fieldforce.fragment.MyBucketList;
import com.spectra.fieldforce.fragment.ProvisioningFragment;
import com.spectra.fieldforce.fragment.ProvisioningTabFragment;
import com.spectra.fieldforce.fragment.WcrFragment;
import com.spectra.fieldforce.model.CommonResponse;
import com.spectra.fieldforce.model.gpon.request.DeleteItemRequest;
import com.spectra.fieldforce.model.gpon.request.ReleaseMyBucket;
import com.spectra.fieldforce.model.gpon.request.UpdateWcrEnggRequest;
import com.spectra.fieldforce.model.gpon.response.CommonClassResponse;
import com.spectra.fieldforce.model.gpon.response.GetAllBucketList;
import com.spectra.fieldforce.model.gpon.response.GetMyBucketList;
import com.spectra.fieldforce.utils.Constants;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyBucketListAdapter extends RecyclerView.Adapter<MyBucketListAdapter.MyViewHolder> {
    private Context context;
    private List<GetMyBucketList.Response> getBucketList;
    AdpterMyBucketListBinding binding;

    public MyBucketListAdapter(FragmentActivity activity,  List<GetMyBucketList.Response> getBucketList) {
        this.context = activity;
        this.getBucketList = getBucketList;
    }
    

    @NotNull
    @Override
    public MyBucketListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {

         binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.adpter_my_bucket_list, parent, false);

        return new MyBucketListAdapter.MyViewHolder(binding);
    }



    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        GetMyBucketList.Response itemList = getBucketList.get(position);
        holder.binding.setAllBucketList(itemList);
        holder.binding.tvRelease.setOnClickListener(v -> {
            releaseBucketItem(itemList.getCustomerID(), itemList.getOrderId(), itemList.getOrderType());
            if(itemList.getOrderType().equals("WCR")){
                updateEnginer("updateWCREngineer",  itemList.getWcrId());
            }else if(itemList.getOrderType().equals("IR")){
                updateEnginer("engineerUpdateIR",itemList.getIrId());
            }
        });
        holder.binding.tvNext.setOnClickListener((View v) -> {
            AppCompatActivity activity1 = (AppCompatActivity) context;
            activity1.getSupportFragmentManager().beginTransaction().add(R.id.frag_container, new ProvisioningTabFragment(), ProvisioningTabFragment.class.getSimpleName()).addToBackStack(null
            ).commit();
        });
    }

    private void updateEnginer(String updateWCREngineer,  String wcrId){
        UpdateWcrEnggRequest updateWcrEnggRequest = new UpdateWcrEnggRequest();
        updateWcrEnggRequest.setAuthkey(Constants.AUTH_KEY);
        updateWcrEnggRequest.setAction(updateWCREngineer);
        updateWcrEnggRequest.setEngName("");
        updateWcrEnggRequest.setInstcode("");
        updateWcrEnggRequest.setWCRguidId(wcrId);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonClassResponse> call = apiService.updateWcrEng(updateWcrEnggRequest);
        call.enqueue(new Callback<CommonClassResponse>() {
            @Override
            public void onResponse(Call<CommonClassResponse> call, Response<CommonClassResponse> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                    try {
                        if(response.body().getResponse().getStatusCode()==200){
                            Toast.makeText(context,response.body().getResponse().getMessage(),Toast.LENGTH_LONG).show();
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
    }

    private void releaseBucketItem(String customerID, String orderId, String orderType){
        ReleaseMyBucket releaseMyBucket = new ReleaseMyBucket();
        releaseMyBucket.setAuthkey(Constants.AUTH_KEY);
        releaseMyBucket.setAction(Constants.RELEASE_ORDER);
        releaseMyBucket.setCustomerId(customerID);
        releaseMyBucket.setOrderId(orderId);
        releaseMyBucket.setOrderType(orderType);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonResponse> call = apiService.releaseMyBucket(releaseMyBucket);
        call.enqueue(new Callback<CommonResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                    try {
                        if (response.body().getStatus().equals("Success")){
                            Toast.makeText(context,response.body().getResponse(),Toast.LENGTH_LONG).show();
                            Intent i = new Intent(context, BucketTabActivity.class);
                            context.startActivity(i);

                            /*AppCompatActivity activity1 = (AppCompatActivity) context;
                            activity1.getSupportFragmentManager().beginTransaction().add(R.id.frag_container, new Buc(), MyBucketList.class.getSimpleName()).addToBackStack(null
                            ).commit();*/
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

        return getBucketList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        AdpterMyBucketListBinding binding;
        MyViewHolder( AdpterMyBucketListBinding binding) {
            super(binding.getRoot());
           this.binding = binding;
        }

    }

}
