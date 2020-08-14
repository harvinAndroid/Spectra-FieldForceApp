package com.spectra.fieldforce.Model;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonElement;
import com.spectra.fieldforce.ApiClient;
import com.spectra.fieldforce.ApiInterface;
import com.spectra.fieldforce.R;
import com.spectra.fieldforce.SRDetail;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.MyViewHolder> {
    private List<Order> orderList;
    private Activity activity;
    private String status;
    private JSONArray result;
    String s="";

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView srNumber, customerId, customerName, customerMobile, slotTime, slaClock, srStatus, customerAddress;
        public Button btnView;

        public MyViewHolder(View view) {
            super(view);
            customerId = (TextView) view.findViewById(R.id.customerId);
            customerName = (TextView) view.findViewById(R.id.customerName);
            customerMobile = (TextView) view.findViewById(R.id.customerMobile);
            customerAddress = (TextView) view.findViewById(R.id.customerAddress);
            srNumber = (TextView) view.findViewById(R.id.srNumber);
            slotTime = (TextView) view.findViewById(R.id.slotTime);
            srStatus = (TextView) view.findViewById(R.id.srStatus);
            slaClock = (TextView) view.findViewById(R.id.slaClock);
            btnView = (Button) view.findViewById(R.id.btnView);

        }
    }

    public AssignmentAdapter(Activity activity, List<Order> orderList) {
        this.activity = activity;
        this.orderList = orderList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Order order = orderList.get(position);
        holder.customerId.setText(order.getCustomerID());
        holder.customerName.setText(order.getCustomerName());
        holder.customerMobile.setText(order.getCustomerMobile());
        holder.customerAddress.setText(order.getCustomerAddress());
        holder.srNumber.setText(order.getSrNumber());
        holder.slotTime.setText(order.getRoasterDate() + " " + order.getFromtime() + " - " + order.getTotime());
        holder.srStatus.setText(order.getSrStatus());
        try{
             s = order.getSlaClock();
        }catch (Exception ex){
            ex.getMessage();
        }

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat f1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");//HH for hour of the day (0 - 23)
        Date d = null;
        try {
            d = f1.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat f2 = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
        holder.slaClock.setText(f2.format(d)); // "12:18am"

        if (order.getAcknowledge_status().equals("1")) {
            holder.btnView.setText("View");
        }
        holder.customerMobile.setOnClickListener(v -> {
            Uri number = Uri.parse("tel:" + holder.customerMobile.getText().toString());
            call_action(number);
        });
        holder.btnView.setOnClickListener(v -> {
            if (order.getAcknowledge_status().equals("0")) {
                updateAcknow(order.getSrNumber(), order.getSlotType());
            } else {
                AppCompatActivity activity1 = (AppCompatActivity) activity;
                Bundle b = new Bundle();
                b.putString("srNumber", order.getSrNumber());
                b.putString("slotType", order.getSlotType());
                activity1.getSupportFragmentManager().beginTransaction().add(R.id.fregment_container, new SRDetail().newInstance(order.getSrNumber(), order.getSlotType()), SRDetail.class.getSimpleName()).addToBackStack(null
                ).commit();
            }
        });

    }

    public void call_action(Uri number) {
        Intent intent = new Intent(Intent.ACTION_CALL, number);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 11);
            }
        }
        activity.startActivity(intent);
    }

    private void updateAcknow(String srText, String slotType) {
        String authKey = "ac7b51de9d888e1458dd53d8aJAN3ba6f";
        String action = "AcknowledgeSR";
        String srNumber = srText;
        String actionCode = slotType == "FRE" ? "101-Fibre Team" : "102-Field Engineer";

        SRRequest srRequest = new SRRequest();
        srRequest.setAuthkey(authKey);
        srRequest.setAction(action);
        srRequest.setSrNumber(srText);
        srRequest.setActionCode(actionCode);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<JsonElement> call = apiService.getSRDetail(srRequest);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(retrofit2.Call<JsonElement> call, Response<JsonElement> response) {
                try {
                    if (response.isSuccessful()) {
                        JSONObject jsonObject = new JSONObject(String.valueOf(response.body()));
                        status = jsonObject.getString("Status");
                        if (status.equals("1")) {
                            try {
                                JSONObject jsonData = jsonObject.getJSONObject("Response");
                                String code = jsonData.getString("Message");
                                Toast.makeText(activity, code, Toast.LENGTH_LONG).show();
                                AppCompatActivity activity1 = (AppCompatActivity) activity;
                                Bundle b = new Bundle();
                                b.putString("srNumber", srText);
                                b.putString("slotType", slotType);
                                activity1.getSupportFragmentManager().beginTransaction().add(R.id.fregment_container, new SRDetail().newInstance(srText, slotType), SRDetail.class.getSimpleName()).addToBackStack(null
                                ).commit();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<JsonElement> call, Throwable t) {
                Log.e("RetroError", t.toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}