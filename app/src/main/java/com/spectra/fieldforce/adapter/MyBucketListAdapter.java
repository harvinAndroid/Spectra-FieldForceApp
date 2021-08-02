package com.spectra.fieldforce.adapter;



import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.spectra.fieldforce.R;
import com.spectra.fieldforce.activity.BucketTabActivity;
import com.spectra.fieldforce.activity.DashBoardActivity;
import com.spectra.fieldforce.activity.MainActivity;
import com.spectra.fieldforce.activity.ProvisioningMainActivity;
import com.spectra.fieldforce.activity.ProvisioningTabActivity;
import com.spectra.fieldforce.api.ApiClient;
import com.spectra.fieldforce.api.ApiInterface;
import com.spectra.fieldforce.databinding.AdpterAllBucketListBinding;
import com.spectra.fieldforce.databinding.AdpterMyBucketListBinding;
import com.spectra.fieldforce.fragment.MyBucketList;
import com.spectra.fieldforce.fragment.ProvisioningFragment;
import com.spectra.fieldforce.fragment.ProvisioningTabFragment;
import com.spectra.fieldforce.fragment.WcrEditItemConsumption;
import com.spectra.fieldforce.fragment.WcrFragment;
import com.spectra.fieldforce.model.CommonResponse;
import com.spectra.fieldforce.model.gpon.request.DeleteItemRequest;
import com.spectra.fieldforce.model.gpon.request.ReleaseMyBucket;
import com.spectra.fieldforce.model.gpon.request.UpdateAppointmentRequest;
import com.spectra.fieldforce.model.gpon.request.UpdateWcrEnggRequest;
import com.spectra.fieldforce.model.gpon.response.CommonClassResponse;
import com.spectra.fieldforce.model.gpon.response.GetAllBucketList;
import com.spectra.fieldforce.model.gpon.response.GetMyBucketList;
import com.spectra.fieldforce.utils.Constants;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyBucketListAdapter extends RecyclerView.Adapter<MyBucketListAdapter.MyViewHolder> implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private Context context;
    private List<GetMyBucketList.Response> getBucketList;
    AdpterMyBucketListBinding binding;
    private AlphaAnimation inAnimation,outAnimation;
    String appointdate;
    int day, month, year, hour, minute;
    int myday, myMonth, myYear, myHour, myMinute;

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
        String appdate = itemList.getAppointmentDate();
        if(appdate!=null){
            holder.binding.etDate.setText(appdate);
        }
        String statusReport = itemList.getCrm_status();
            holder.binding.tvRelease.setOnClickListener(v -> {
                if(statusReport.equals("Installation On Hold")||statusReport.equals("hold")||statusReport.contains("hold")||statusReport.equals("Installation Hold")) {
                    holder.binding.tvRelease.setEnabled(false);
                    Toast.makeText(context,"Installation status is hold can't Release ",Toast.LENGTH_LONG).show();
                }   else{
                    releaseBucketItem(itemList.getCustomerID(), itemList.getOrder_id(), itemList.getOrder_type());
                    if(itemList.getOrder_type().equals("WCR")){
                        updateEnginer("updateWCREngineer",  itemList.getWcrId(),"");
                    }else if(itemList.getOrder_type().equals("IR")){
                        updateEnginer("updateIREngineer",itemList.getIrId(),"");
                    }
                }
        });

        holder.binding.tvNext.setOnClickListener((View v) -> {
            String date = binding.etDate.getText().toString();
            if(date.isEmpty()){
                Toast.makeText(context,"Select Appointment date",Toast.LENGTH_LONG).show();
            }else {
                SharedPreferences sp1=context.getSharedPreferences("Login",0);
                String enggName =sp1.getString("EnggName", null);
                if(itemList.getOrder_type().equals("WCR")){
                    updateEnginer("updateWCREngineer",  itemList.getWcrId(),enggName);
                }else if(itemList.getOrder_type().equals("IR")){
                    updateEnginer("updateIREngineer",itemList.getIrId(),enggName);
                }
                updateEngineerDate(date,itemList.getOrder_id(),itemList.getCustomerID());
               }
        });
        holder.binding.etDate.setOnClickListener(view1 -> {
            Calendar calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(context, this,year, month,day);
            datePickerDialog.show();
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
           /* final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                    (view, year, monthOfYear, dayOfMonth) -> binding.etDate.setText(year  + "-" + (monthOfYear + 1) + "-" + dayOfMonth), mYear, mMonth, mDay);
            datePickerDialog.show();*/

        });
    }

    private void updateEnginer(String updateWCREngineer,  String wcrId,String enggName){
        UpdateWcrEnggRequest updateWcrEnggRequest = new UpdateWcrEnggRequest();
        updateWcrEnggRequest.setAuthkey(Constants.AUTH_KEY);
        updateWcrEnggRequest.setAction(updateWCREngineer);
        updateWcrEnggRequest.setEngName(enggName);
        updateWcrEnggRequest.setInstcode("");
        updateWcrEnggRequest.setWCRguidId(wcrId);
        updateWcrEnggRequest.setAppointmentDate(binding.etDate.getText().toString());

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
                          /*  Bundle b = new Bundle();
                            b.putString("canId", customerID);
                            b.putString("OrderId",orderId);
                            AppCompatActivity activity = (AppCompatActivity) context;
                            Fragment myFragment = new ProvisioningFragment();
                            myFragment.setArguments(b);
                            activity.getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, myFragment).addToBackStack(null).commit();
*/

                            Intent i = new Intent(context, ProvisioningMainActivity.class);
                            i.putExtra("canId", customerID);
                         //   i.putExtra("StatusofReport", strStatusofReport);
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


                            /*AppCompatActivity activity1 = (AppCompatActivity) context;
                            activity1.getSupportFragmentManager().beginTransaction().add(R.id.frag_container, new Buc(), MyBucketList.class.getSimpleName()).addToBackStack(null
                            ).commit();*/
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

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        myYear = year;
        myday = day;
        myMonth = month;
        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(context, this, hour, minute, DateFormat.is24HourFormat(context));
        timePickerDialog.show();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();

        hour = hourOfDay;
        minute = minute;

        // set waking time into textview
        StringBuilder sb = new StringBuilder();
        if(hour>=12){
            sb.append(hour-12).append( ":" ).append(minute).append(" PM");
        }else{
            sb.append(hour).append( ":" ).append(minute).append(" AM");
        }
        binding.etDate.setText(myYear + "-" + myMonth + "-" + myday + " " + sb);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        AdpterMyBucketListBinding binding;
        MyViewHolder( AdpterMyBucketListBinding binding) {
            super(binding.getRoot());
           this.binding = binding;
        }

    }

    public void Filter(List<GetMyBucketList.Response> getBucketList){
        this.getBucketList  = getBucketList;
        notifyDataSetChanged();
    }

}
