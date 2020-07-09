package com.spectra.fieldforce;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.spectra.fieldforce.Model.AssignmentAdapter;
import com.spectra.fieldforce.Model.AssignmentRequest;
import com.spectra.fieldforce.Model.Order;
import com.spectra.fieldforce.Model.SRRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SRDetail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SRDetail extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public TextView customerId, customerName, customerMobile, customerAddress, srNumber, slotTime, caseRemarks,
            srStatus, srType, srSubType, slaClock, slaStatus, customerIP, segment, devicePort, podName,
            startTime, endTime, startLocation, endLocation;
    public Button btnView, btnStartTime, btnEndTime;
    private String status;
    private JSONArray result;
    AppCompatActivity activity;
    private Location location;

    public SRDetail() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SRDetail.
     */
    // TODO: Rename and change types and number of parameters
    public static SRDetail newInstance(String param1, String param2) {
        Log.d("para", param1);
        SRDetail fragment = new SRDetail();
        Bundle args = new Bundle();
        args.putString("srNumber", param1);
        fragment.setArguments(args);
        return fragment;
    }

    private void getAssignment(String srText) {
        String authKey = "ac7b51de9d888e1458dd53d8aJAN3ba6f";
        String action = "getASRBySrNumber";
        String taskType = "Assigned";
        String slotType = "FRE";

        SRRequest srRequest = new SRRequest();
        srRequest.setAuthkey(authKey);
        srRequest.setAction(action);
        srRequest.setTaskType(taskType);
        srRequest.setSlotType(slotType);
        srRequest.setSrNumber(srText);

        SRInterface apiService = ApiClient.getClient().create(SRInterface.class);
        Call<JsonElement> call = apiService.getSRDetail(srRequest);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(retrofit2.Call<JsonElement> call, Response<JsonElement> response) {
                try {
                    if (response.isSuccessful()) {
                        JSONObject jsonObject = new JSONObject(String.valueOf(response.body()));
                        status = jsonObject.getString("Status");
                        if (status.equals("Failure")) {
                            Log.d("Failure", "error");
                        } else if (status.equals("Success")) {
                            try {
                                result = jsonObject.getJSONArray("response");
                                if (result != null) {
                                    Order order = null;
                                    for (int i = 0; i < result.length(); i++) {
                                        JSONObject jsonData = new JSONObject(String.valueOf(result.getString(i)));
                                        Log.d("APIResponse", jsonData.toString());
                                        Gson gson = new Gson();
                                        order = gson.fromJson(jsonData.toString(), Order.class);
                                    }
                                    customerId.setText(order.getCustomerID());
                                    customerName.setText(order.getCustomerName());
                                    customerMobile.setText(order.getCustomerMobile());
                                    customerAddress.setText(order.getCustomerAddress());
                                    srNumber.setText(srText);
                                    slotTime.setText(order.getRoasterDate() + " " + order.getFromtime() + " - " + order.getTotime());
                                    caseRemarks.setText(order.getCaseRemarks());
                                    srStatus.setText(order.getSrStatus());
                                    srType.setText(order.getSrType());
                                    srSubType.setText(order.getSrSubType());
                                    slaClock.setText(order.getSlaClock());
                                    slaStatus.setText(order.getSlaStatus());
                                    customerIP.setText(order.getCustomerIP());
                                    segment.setText(order.getSegment());
                                    podName.setText(order.getPodName());
                                    devicePort.setText(order.getDeviceName() + " : " + order.getPortId());
                                    btnEndTime.setVisibility(View.GONE);
                                    btnStartTime.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            getLatLong(startLocation);
                                            Calendar c = Calendar.getInstance();
                                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                            String formattedDate = df.format(c.getTime());
                                            startTime.setText(formattedDate);
                                            btnStartTime.setVisibility(View.GONE);
                                            btnEndTime.setVisibility(View.VISIBLE);
                                        }
                                    });
                                    btnEndTime.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            getLatLong(endLocation);
                                            Calendar c = Calendar.getInstance();
                                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                            String formattedDate = df.format(c.getTime());
                                            endTime.setText(formattedDate);
                                            btnEndTime.setVisibility(View.GONE);
                                        }
                                    });
                                    customerMobile.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Uri number = Uri.parse("tel:" + customerMobile.getText().toString());
                                            call_action(number);
                                        }
                                    });
                                }
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

    public void getLatLong(TextView txtLocation) {
        activity = (AppCompatActivity) getActivity();
        LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}
                        , 10);
            }
            return;
        }
        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location == null) {
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        double longi = location.getLongitude();
        double lati = location.getLatitude();
        String message = longi + " " + lati;
        txtLocation.setText(message);
    }

    public void call_action(Uri number) {
        Intent intent = new Intent(Intent.ACTION_DIAL, number);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        String srText = getArguments().getString("srNumber");
        getAssignment(srText);

        View view = inflater.inflate(R.layout.fragment_s_r_detail, container, false);
        customerId = (TextView) view.findViewById(R.id.customerId);
        customerName = (TextView) view.findViewById(R.id.customerName);
        customerMobile = (TextView) view.findViewById(R.id.customerMobile);
        customerAddress = (TextView) view.findViewById(R.id.customerAddress);
        srNumber = (TextView) view.findViewById(R.id.srNumber);
        slotTime = (TextView) view.findViewById(R.id.slotTime);
        caseRemarks = (TextView) view.findViewById(R.id.caseRemarks);
        srStatus = (TextView) view.findViewById(R.id.srStatus);
        srType = (TextView) view.findViewById(R.id.srType);
        srSubType = (TextView) view.findViewById(R.id.srSubType);
        slaClock = (TextView) view.findViewById(R.id.slaClock);
        slaStatus = (TextView) view.findViewById(R.id.slaStatus);
        customerIP = (TextView) view.findViewById(R.id.customerIP);
        segment = (TextView) view.findViewById(R.id.businessSegemnt);
        podName = (TextView) view.findViewById(R.id.podName);
        devicePort = (TextView) view.findViewById(R.id.devicePort);
        btnStartTime = (Button) view.findViewById(R.id.btnStartTime);
        btnEndTime = (Button) view.findViewById(R.id.btnEndTime);
        startTime = (TextView) view.findViewById(R.id.startTime);
        endTime = (TextView) view.findViewById(R.id.endTime);
        startLocation = (TextView) view.findViewById(R.id.startLocation);
        endLocation = (TextView) view.findViewById(R.id.endLocation);
        btnView = (Button) view.findViewById(R.id.btnView);
        return view;
    }
}