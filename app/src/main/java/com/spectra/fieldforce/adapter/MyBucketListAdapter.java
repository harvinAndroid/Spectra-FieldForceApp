package com.spectra.fieldforce.adapter;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.spectra.fieldforce.R;
import com.spectra.fieldforce.activity.BucketTabActivity;
import com.spectra.fieldforce.activity.ProvisioningMainActivity;
import com.spectra.fieldforce.api.ApiClient;
import com.spectra.fieldforce.api.ApiInterface;
import com.spectra.fieldforce.databinding.AdpterMyBucketListBinding;
import com.spectra.fieldforce.model.gpon.request.ReleaseMyBucket;
import com.spectra.fieldforce.model.gpon.request.UpdateAppointmentRequest;
import com.spectra.fieldforce.model.gpon.request.UpdateWcrEnggRequest;
import com.spectra.fieldforce.model.gpon.response.CommonClassResponse;
import com.spectra.fieldforce.model.gpon.response.GetMyBucketList;
import com.spectra.fieldforce.utils.Constants;
import org.jetbrains.annotations.NotNull;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyBucketListAdapter extends RecyclerView.Adapter<MyBucketListAdapter.MyViewHolder> /*implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener*/ {
    private Context context;
    private List<GetMyBucketList.Response> getBucketList;
    AdpterMyBucketListBinding binding;
    private AlphaAnimation inAnimation,outAnimation;
    private String fromDateString = "";
    String dateTime;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    private Calendar mCalendar;

    public MyBucketListAdapter(FragmentActivity activity,  List<GetMyBucketList.Response> getBucketList) {
        this.context = activity;
        this.getBucketList = getBucketList;
    }

    @NotNull
    @Override
    public MyBucketListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

         binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.adpter_my_bucket_list, parent, false);

        return new MyBucketListAdapter.MyViewHolder(binding);
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
        GetMyBucketList.Response itemList = getBucketList.get(position);
        holder.binding.setAllBucketList(itemList);

        try {
            calendar = Calendar.getInstance();
            simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss aaa z");
            dateTime = simpleDateFormat.format(itemList.getWcrSlaClock()).toString();
            holder.binding.slaClock.setText(dateTime);

        } catch (Exception e) {
            e.printStackTrace();
        }
        String appdate = itemList.getAppointmentDate();
        if(appdate!=null){
            holder.binding.etDate.setText(appdate);
        }
        holder.binding.tvNext.setOnClickListener((View v) -> {
            String date = holder.binding.etDate.getText().toString();
            if(date.isEmpty()){
                Toast.makeText(context,"Select Appointment date",Toast.LENGTH_LONG).show();
            }else {
                SharedPreferences sp1=context.getSharedPreferences("Login",0);
                String enggName =sp1.getString("EnggName", null);
                if(itemList.getOrder_type().equals("WCR")){
                    updateEnginer("updateWCREngineer",  itemList.getWcrId(),enggName,date);
                }else if(itemList.getOrder_type().equals("IR")){
                    updateEnginer("updateIREngineer",itemList.getIrId(),enggName,date);
                }
                updateEngineerDate(date,itemList.getOrder_id(),itemList.getCustomerID());
            }
        });
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sendDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm a");
        @SuppressLint("SetTextI18n")
        final DatePickerDialog.OnDateSetListener mFromDateSetListener = (view, year, monthOfYear, dayOfMonth) -> {
            mCalendar.set(Calendar.YEAR, year);
            mCalendar.set(Calendar.MONTH, monthOfYear);
            mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            fromDateString = sendDateFormat.format(mCalendar.getTime());
            holder.binding.etDate.setText("" + fromDateString);
        };

        @SuppressLint("SetTextI18n")
        final TimePickerDialog.OnTimeSetListener mTimeDateSetListener = (view, hourOfDay, minuteOfHour) -> {
            mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            mCalendar.set(Calendar.MINUTE, minuteOfHour);
            fromDateString = sendDateFormat.format(mCalendar.getTime());
            holder.binding.etDate.setText("" + fromDateString);
        };

        String statusReport = itemList.getCrm_status();
            holder.binding.tvRelease.setOnClickListener(v -> {
                if(statusReport.equals("Installation On Hold")||statusReport.equals("hold")||statusReport.contains("hold")||statusReport.equals("Installation Hold")) {
                    holder.binding.tvRelease.setEnabled(false);
                    Toast.makeText(context,"Installation status is hold can't Release ",Toast.LENGTH_LONG).show();
                }   else{
                    releaseBucketItem(itemList.getCustomerID(), itemList.getOrder_id(), itemList.getOrder_type());
                    if(itemList.getOrder_type().equals("WCR")){
                        updateEnginer("updateWCREngineer",  itemList.getWcrId(),"","");
                    }else if(itemList.getOrder_type().equals("IR")){
                        updateEnginer("updateIREngineer",itemList.getIrId(),"","");
                    }
                }
        });


        holder.binding.etDate.setOnClickListener(view -> {
            try {
                final TimePickerDialog timePickerDialog = new TimePickerDialog(
                        context, mTimeDateSetListener,
                        mCalendar.get(Calendar.HOUR_OF_DAY),
                        mCalendar.get(Calendar.MINUTE),
                        DateFormat.is24HourFormat(context));

                timePickerDialog.show();
                final DatePickerDialog fromPickerDialog = new DatePickerDialog(
                        context, android.R.style.Theme_Material_Light_Dialog_Alert,
                        mFromDateSetListener,
                        mCalendar.get(Calendar.YEAR),
                        mCalendar.get(Calendar.MONTH),
                        mCalendar.get(Calendar.DAY_OF_MONTH));
                fromPickerDialog.show();
                fromPickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            } catch (Exception ex) {

            }
        });
        mCalendar = Calendar.getInstance();
        fromDateString = sendDateFormat.format(mCalendar.getTime());
    }

    private void updateEnginer(String updateWCREngineer,  String wcrId,String enggName,String date){
        UpdateWcrEnggRequest updateWcrEnggRequest = new UpdateWcrEnggRequest();
        updateWcrEnggRequest.setAuthkey(Constants.AUTH_KEY);
        updateWcrEnggRequest.setAction(updateWCREngineer);
        updateWcrEnggRequest.setEngName(enggName);
        updateWcrEnggRequest.setInstcode("");
        updateWcrEnggRequest.setWCRguidId(wcrId);
        updateWcrEnggRequest.setAppointmentDate(date);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonClassResponse> call = apiService.updateWcrEng(updateWcrEnggRequest);
        call.enqueue(new Callback<CommonClassResponse>() {
            @Override
            public void onResponse(Call<CommonClassResponse> call, Response<CommonClassResponse> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                    try {
                        if(response.body().getResponse().getStatusCode()==200){
                          //  Toast.makeText(context,response.body().getResponse().getMessage(),Toast.LENGTH_LONG).show();
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

    private void updateEngineerDate(String date, String orderId, String customerID){
        UpdateAppointmentRequest updateWcrEnggRequest = new UpdateAppointmentRequest();
        updateWcrEnggRequest.setAuthkey(Constants.AUTH_KEY);
        updateWcrEnggRequest.setAction(Constants.UPDATE_APPOINTMENT);
        updateWcrEnggRequest.setOrderID(orderId);
        updateWcrEnggRequest.setAppointmentDate(date);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonClassResponse> call = apiService.updateAppointmentDate(updateWcrEnggRequest);
        call.enqueue(new Callback<CommonClassResponse>() {
            @Override
            public void onResponse(Call<CommonClassResponse> call, Response<CommonClassResponse> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                    try {
                        if(response.body().getResponse().getStatusCode()==200){

                            Intent i = new Intent(context, ProvisioningMainActivity.class);
                            i.putExtra("canId", customerID);
                            i.putExtra("OrderId", orderId);
                            context.startActivity(i);
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
        inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        binding.progressLayout.progressOverlay.setAnimation(inAnimation);
        binding.progressLayout.progressOverlay.setVisibility(View.VISIBLE);
        ReleaseMyBucket releaseMyBucket = new ReleaseMyBucket();
        releaseMyBucket.setAuthkey(Constants.AUTH_KEY);
        releaseMyBucket.setAction(Constants.RELEASE_ORDER);
        releaseMyBucket.setCustomerId(customerID);
        releaseMyBucket.setOrderId(orderId);
        releaseMyBucket.setOrderType(orderType);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonClassResponse> call = apiService.releaseMyBucket(releaseMyBucket);
        call.enqueue(new Callback<CommonClassResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<CommonClassResponse> call, Response<CommonClassResponse> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                    outAnimation = new AlphaAnimation(1f, 0f);
                    outAnimation.setDuration(200);
                    binding.progressLayout.progressOverlay.setAnimation(outAnimation);
                    binding.progressLayout.progressOverlay.setVisibility(View.GONE);
                    try {
                        if (response.body().getStatus().equals("Success")){
                            Toast.makeText(context,response.body().getResponse().getMessage(),Toast.LENGTH_LONG).show();
                            Intent i = new Intent(context, BucketTabActivity.class);
                            context.startActivity(i);
                        }else{
                            Toast.makeText(context,response.body().getResponse().getMessage(),Toast.LENGTH_LONG).show();
                        }

                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<CommonClassResponse> call, Throwable t) {
                binding.progressLayout.progressOverlay.setVisibility(View.GONE);
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

    public void Filter(List<GetMyBucketList.Response> getBucketList){
        notifyDataSetChanged();
        this.getBucketList  = getBucketList;
        context.notifyAll();
    }  


}
