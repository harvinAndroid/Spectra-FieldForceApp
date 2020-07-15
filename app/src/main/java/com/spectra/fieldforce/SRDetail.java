package com.spectra.fieldforce;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.internal.service.Common;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.spectra.fieldforce.Model.Order;
import com.spectra.fieldforce.Model.RCRequest;
import com.spectra.fieldforce.Model.SRRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

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
            startTime, endTime, startLocation, endLocation, foni, repeat_sr, massoutage;
    public Button btnView, btnStartTime, btnEndTime;
    private EditText DateEdit;
    public Spinner changeStatus, rc1, rc2, rc3;
    private String status;
    private JSONArray result;
    AppCompatActivity activity;
    ArrayList<String> rc1Code;
    ArrayList<String> rc1Name;
    ArrayList<String> rc2Code;
    ArrayList<String> rc2Name;
    ArrayList<String> rc3Code;
    ArrayList<String> rc3Name;
    ArrayList<String> caseStatus;
    private Location location;

    public SRDetail() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SRDetail newInstance(String param1, String param2) {
        Log.d("para", param1);
        SRDetail fragment = new SRDetail();
        Bundle args = new Bundle();
        args.putString("srNumber", param1);
        fragment.setArguments(args);
        return fragment;
    }

    private void bindChangeStatus(int isResolve) {
        caseStatus = new ArrayList<String>();
        caseStatus.add("Select Status");
        caseStatus.add("Hold");
        if (isResolve == 1) {
            caseStatus.add("Resolved");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, caseStatus);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        changeStatus.setAdapter(adapter);
    }

    private void getRC1() {
        String authKey = "ac7b51de9d888e1458dd53d8aJAN3ba6f";
        String action = "getRCone";

        RCRequest rcRequest = new RCRequest();
        rcRequest.setAuthkey(authKey);
        rcRequest.setAction(action);

        RCInterface apiService = ApiClient.getClient().create(RCInterface.class);
        Call<JsonElement> call = apiService.getRCDetail(rcRequest);
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
                                result = jsonObject.getJSONArray("data");
                                if (result != null) {
                                    rc1Code = new ArrayList<String>();
                                    rc1Name = new ArrayList<String>();
                                    for (int i = 0; i < result.length(); i++) {
                                        JSONObject jsonData = new JSONObject(String.valueOf(result.getString(i)));
                                        Log.d("RC1Response", jsonData.toString());
                                        String code = jsonData.getString("rc_oneId");
                                        String name = jsonData.getString("rootCauseOne");
                                        rc1Code.add(code);
                                        rc1Name.add(name);
                                    }
                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, rc1Name);
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    rc1.setAdapter(adapter);
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

    private void getRC2(String rc1Code) {
        String authKey = "ac7b51de9d888e1458dd53d8aJAN3ba6f";
        String action = "getRCtwo";
        String RConeId = rc1Code;

        RCRequest rcRequest = new RCRequest();
        rcRequest.setAuthkey(authKey);
        rcRequest.setAction(action);
        rcRequest.setRC1(RConeId);

        RCInterface apiService = ApiClient.getClient().create(RCInterface.class);
        Call<JsonElement> call = apiService.getRCDetail(rcRequest);
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
                                result = jsonObject.getJSONArray("data");
                                if (result != null) {
                                    rc2Code = new ArrayList<String>();
                                    rc2Name = new ArrayList<String>();
                                    for (int i = 0; i < result.length(); i++) {
                                        JSONObject jsonData = new JSONObject(String.valueOf(result.getString(i)));
                                        Log.d("RC2Response", jsonData.toString());
                                        String code = jsonData.getString("rc_twoId");
                                        String name = jsonData.getString("rootCauseTwo");
                                        rc2Code.add(code);
                                        rc2Name.add(name);
                                    }
                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, rc2Name);
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    rc2.setAdapter(adapter);
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

    private void getRC3(String rc2Code) {
        String authKey = "ac7b51de9d888e1458dd53d8aJAN3ba6f";
        String action = "getRCthree";
        String RCtwoId = rc2Code;

        RCRequest rcRequest = new RCRequest();
        rcRequest.setAuthkey(authKey);
        rcRequest.setAction(action);
        rcRequest.setRC2(RCtwoId);

        RCInterface apiService = ApiClient.getClient().create(RCInterface.class);
        Call<JsonElement> call = apiService.getRCDetail(rcRequest);
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
                                result = jsonObject.getJSONArray("data");
                                if (result != null) {
                                    rc3Code = new ArrayList<String>();
                                    rc3Name = new ArrayList<String>();
                                    for (int i = 0; i < result.length(); i++) {
                                        JSONObject jsonData = new JSONObject(String.valueOf(result.getString(i)));
                                        Log.d("RC3Response", jsonData.toString());
                                        String code = jsonData.getString("rc_thirdId");
                                        String name = jsonData.getString("rootCauseThree");
                                        rc3Code.add(code);
                                        rc3Name.add(name);
                                    }
                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, rc3Name);
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    rc3.setAdapter(adapter);
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
                                    foni.setText((order.getFoni()));
                                    repeat_sr.setText((order.getRepeat_sr()));
                                    massoutage.setText((order.getMassoutage()));
                                    btnEndTime.setVisibility(View.GONE);
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

    public boolean getLatLong(TextView txtLocation) {
        boolean isLoc = true;
        LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                isLoc = false;
                activity.requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}
                        , 10);
            }
        }
        if (isLoc == true) {
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location == null) {
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
            double longi = location.getLongitude();
            double lati = location.getLatitude();
            String message = longi + " " + lati;
            txtLocation.setText(message);
        }
        return isLoc;
    }

    public void call_action(Uri number) {
        Activity activity = new Activity();
        activity = getActivity();
        Intent intent = new Intent(Intent.ACTION_DIAL, number);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (AppCompatActivity) getActivity();
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
        foni = (TextView) view.findViewById(R.id.foni);
        repeat_sr = (TextView) view.findViewById(R.id.repeat);
        massoutage = (TextView) view.findViewById(R.id.massoutage);
        btnView = (Button) view.findViewById(R.id.btnView);
        changeStatus = (Spinner) view.findViewById(R.id.changeStatus);
        rc1 = (Spinner) view.findViewById(R.id.rc1);
        rc2 = (Spinner) view.findViewById(R.id.rc2);
        rc3 = (Spinner) view.findViewById(R.id.rc3);
        DateEdit = (EditText) view.findViewById(R.id.dateTimeText);
        RelativeLayout resolveLayout = (RelativeLayout) view.findViewById(R.id.resolveLayout);
        RelativeLayout holdLayout = (RelativeLayout) view.findViewById(R.id.holdLayout);
        bindChangeStatus(0);
        changeStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String status = changeStatus.getSelectedItem().toString();
                if (status == "Resolved") {
                    resolveLayout.setVisibility(View.VISIBLE);
                    holdLayout.setVisibility(View.GONE);
                } else if (status == "Hold") {
                    resolveLayout.setVisibility(View.GONE);
                    holdLayout.setVisibility(View.VISIBLE);
                } else {
                    resolveLayout.setVisibility(View.GONE);
                    holdLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        getRC1();
        rc1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int itemPosition = rc1.getSelectedItemPosition();
                String rc1Id = rc1Code.get(itemPosition).toString();
                getRC2(rc1Id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        rc2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int itemPosition = rc2.getSelectedItemPosition();
                String rc2Id = rc2Code.get(itemPosition).toString();
                getRC3(rc2Id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isLoc = getLatLong(startLocation);
                if (isLoc == true) {
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String formattedDate = df.format(c.getTime());
                    startTime.setText(formattedDate);
                    btnStartTime.setVisibility(View.GONE);
                    btnEndTime.setVisibility(View.VISIBLE);
                }
            }
        });
        btnEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isLoc = getLatLong(endLocation);
                if (isLoc == true) {
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String formattedDate = df.format(c.getTime());
                    endTime.setText(formattedDate);
                    btnEndTime.setVisibility(View.GONE);
                    bindChangeStatus(1);
                }
            }
        });
        customerMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri number = Uri.parse("tel:" + customerMobile.getText().toString());
                call_action(number);
            }
        });
        DateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    final TimePickerDialog timePickerDialog = new TimePickerDialog(
                            activity, mTimeDateSetListener,
                            mCalendar.get(Calendar.HOUR_OF_DAY),
                            mCalendar.get(Calendar.MINUTE),
                            DateFormat.is24HourFormat(getActivity()));
                    timePickerDialog.show();
                    final DatePickerDialog fromPickerDialog = new DatePickerDialog(
                            activity, android.R.style.Theme_Material_Light_Dialog_Alert,
                            mFromDateSetListener,
                            mCalendar.get(Calendar.YEAR),
                            mCalendar.get(Calendar.MONTH),
                            mCalendar.get(Calendar.DAY_OF_MONTH));
                    fromPickerDialog.show();
                } catch (Exception ex) {
                    // TODO: 13-07-2020 for @satyveer handle message to show user
                }
            }
        });
        mCalendar = Calendar.getInstance();
        fromDateString = sendDateFormat.format(mCalendar.getTime());
        return view;
    }

    String fromDateString = "";
    Calendar mCalendar;
    //    SimpleDateFormat returnDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
    SimpleDateFormat sendDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    final DatePickerDialog.OnDateSetListener mFromDateSetListener = (view, year, monthOfYear, dayOfMonth) -> {
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, monthOfYear);
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        fromDateString = sendDateFormat.format(mCalendar.getTime());
        DateEdit.setText("" + fromDateString);
    };
    final TimePickerDialog.OnTimeSetListener mTimeDateSetListener = (view, hourOfDay, minuteOfHour) -> {
        mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        mCalendar.set(Calendar.MINUTE, minuteOfHour);

        fromDateString = sendDateFormat.format(mCalendar.getTime());
        DateEdit.setText("" + fromDateString);
    };
}