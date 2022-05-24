package com.spectra.fieldforce.adapter;



import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
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
import com.spectra.fieldforce.utils.AppConstants;
import com.spectra.fieldforce.utils.Constants;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllBucketListAdapter extends RecyclerView.Adapter<AllBucketListAdapter.MyViewHolder>  {
    private Context context;
    private List<GetAllBucketList.Response> allbucketlist;
    AdpterAllBucketListBinding binding;
    private AlphaAnimation inAnimation,outAnimation;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    private Calendar mCalendar;
    String dateTime;
 /*   contactPerson,contactNo*/

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


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        System.out.println("cureent : "+allbucketlist.get(position).getEngineerName()+ " "+allbucketlist.get(position).getEngineerName()+"postion :"+position);

        GetAllBucketList.Response itemList = allbucketlist.get(position);
        holder.binding.setAllBucketList(itemList);
        holder.binding.enggNm.setText("Engineer Name: " +itemList.getEngineerName());
        if(allbucketlist.get(position).getAddAssign().equals("Assigned")){
            holder.binding.tvAdd.setEnabled(false);
            holder.binding.tvAdd.setFocusable(false);
            holder.binding.tvAdd.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
        }
        try {
            calendar = Calendar.getInstance();
            simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss aaa z");
            dateTime = simpleDateFormat.format(itemList.getWcrslaclock()).toString();
            holder.binding.slaClock.setText(dateTime);

        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.binding.tvAdd.setOnClickListener(v -> {
            AllBucketListAdapter.this.addItemToBucket(itemList.getID(),itemList.getOrderType(),itemList.getWcrId(),itemList.getIrId(), itemList.getOrderType(), itemList.getCanId(), itemList.getCustomerName(), "",
                    itemList.getStatus(), itemList.getHoldCategory(),"",  "",
                    itemList.getAreaName(),itemList.getSlaStatus(),itemList.getVendorName(),itemList.getCreatedOn(),
                    itemList.getProduct(),itemList.getContactPerson(),itemList.getContactNo(),itemList.getWcrslaclock(),itemList.getIrslaclock(),itemList.getBusinessSegment(),itemList.getCityId(),itemList.getAddress(),itemList.getActivationOTP());

            if(itemList.getOrderType().equals("WCR")){
                updateEnginer(Constants.UPDATE_WCR_ENGINEER,  itemList.getWcrId());
            }else if(itemList.getOrderType().equals("IR")){
                updateEnginer(Constants.UPDATE_IR_ENGINEER, itemList.getIrId());
            }
        });
    }


    private void updateEnginer(String updateWCREngineer, String wcrId){
        SharedPreferences sp1 = context.getSharedPreferences("Login",0);
        String enggName =sp1.getString("EnggName", null);
        UpdateWcrEnggRequest updateWcrEnggRequest = new UpdateWcrEnggRequest();
        updateWcrEnggRequest.setAuthkey(Constants.AUTH_KEY);
        updateWcrEnggRequest.setAction(updateWCREngineer);
        updateWcrEnggRequest.setEngName(enggName);
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
                        if(response.body().getStatus().equals(AppConstants.SUCCESS)){
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


    private void addItemToBucket(String id, String OrderType, String wcrId, String irId, String orderType, String canId, String customerName, String podName, String status, String holdCategory, String holdReason, String networkTechnology, String AreaName
            , String Sla, String vendorName, String createOn, String Product, String cperson, String Num, String Wcrslaclock, String Irslaclock, String businessSegment, String cityId, String address, String activationOTP){
        inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        binding.progressbar.progressOverlay.setAnimation(inAnimation);
        binding.progressbar.progressOverlay.setVisibility(View.VISIBLE);
        SharedPreferences sp1=context.getSharedPreferences("Login",0);
        String vendor =sp1.getString("VenderCode", null);
        String enggId = sp1.getString("EnggId", null);
        String enggName =sp1.getString("EnggName", null);

        AddBucketListRequest addBucketListRequest = new AddBucketListRequest();
        addBucketListRequest.setAuthkey(Constants.AUTH_KEY);
        addBucketListRequest.setAction(Constants.ADD_ORDER_TOBUCKET);
        addBucketListRequest.setOrder_id(id);
        addBucketListRequest.setOrder_type(OrderType);
        addBucketListRequest.setWCRId(wcrId);
        addBucketListRequest.setIRId(irId);
        addBucketListRequest.setCustomerID(canId);
        addBucketListRequest.setCustomerName(customerName);
        addBucketListRequest.setCRM_status(status);
        addBucketListRequest.setHoldCategory(holdCategory);
        addBucketListRequest.setHoldReason(holdReason);
        addBucketListRequest.setEngineerName(enggName);
        addBucketListRequest.setVendorName(vendorName);
        addBucketListRequest.setSlaStatus(Sla);
        addBucketListRequest.setAreaName(AreaName);
        addBucketListRequest.setOrderCreatedOn(createOn);
        addBucketListRequest.setAppointmentDate("");
        addBucketListRequest.setProduct(Product);
        addBucketListRequest.setContactPerson(cperson);
        addBucketListRequest.setContactNo(Num);
        addBucketListRequest.setEngineerID(enggId);
        addBucketListRequest.setVendorCode(vendor);
        addBucketListRequest.setWCRSlaClock(Wcrslaclock);
        addBucketListRequest.setIRSlaClock(Irslaclock);
        addBucketListRequest.setOrder_type(orderType);
        addBucketListRequest.setSegment(businessSegment);
        addBucketListRequest.setCustomerAddress(address);
        addBucketListRequest.setCustomerCityId(cityId);
        addBucketListRequest.setActivationOTP(activationOTP);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonClassResponse> call = apiService.addItemToBucket(addBucketListRequest);
        call.enqueue(new Callback<CommonClassResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<CommonClassResponse> call, Response<CommonClassResponse> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                    outAnimation = new AlphaAnimation(1f, 0f);
                    outAnimation.setDuration(200);
                    binding.progressbar.progressOverlay.setAnimation(outAnimation);
                    binding.progressbar.progressOverlay.setVisibility(View.GONE);
                    try {
                        if (response.body().getStatus().equals(AppConstants.SUCCESS)){
                            Toast.makeText(context,"Order has been Added into bucket.",Toast.LENGTH_LONG).show();
                            Intent i = new Intent(context, BucketTabActivity.class);
                            context.startActivity(i);
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
            public void onFailure(Call<CommonClassResponse> call, Throwable t) {
                binding.progressbar.progressOverlay.setVisibility(View.GONE);

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

    public void Filter(List<GetAllBucketList.Response> allbucketlist){
        notifyDataSetChanged();
        this.allbucketlist   = allbucketlist;
        context.notifyAll();
    }

}
