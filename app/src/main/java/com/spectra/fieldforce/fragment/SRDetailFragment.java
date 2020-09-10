package com.spectra.fieldforce.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
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
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.spectra.fieldforce.Model.ChangeBinRequest;
import com.spectra.fieldforce.Model.ChangeBinResponse;
import com.spectra.fieldforce.Model.EndtimeRequest;
import com.spectra.fieldforce.Model.Order;
import com.spectra.fieldforce.Model.RCRequest;
import com.spectra.fieldforce.Model.SRRequest;
import com.spectra.fieldforce.Model.SendChangeBinRequest;
import com.spectra.fieldforce.Model.StarttimeRequest;
import com.spectra.fieldforce.R;
import com.spectra.fieldforce.activity.Activity_Resolve;
import com.spectra.fieldforce.activity.MainActivity;
import com.spectra.fieldforce.api.ApiClient;
import com.spectra.fieldforce.api.ApiInterface;
import com.spectra.fieldforce.utils.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SRDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SRDetailFragment extends Fragment{


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private TextView customerId, customerName, customerMobile, customerAddress, srNumber, slotTime, caseRemarks,
            srStatus, srType, srSubType, slaClock, slaStatus, customerIP, segment, devicePort, podName, etr, sessionStatus,
            startTime, endTime, startLocation, endLocation, foni, repeat_sr, massoutage, contactName, contactNumber, txtHeader;
    private Button btnHoldSubmit, btnStartTime, btnEndTime, btnETRSubmit, btnUnifySession, btnResolveSubmit,btnNoc,btnMgrt,
            btnSubmitChnageBin,btnSrDetails;
    private EditText DateEdit, rfo;
    private Spinner resolveContacted, changeStatus, rc1, holdReason, contacted,sp_change_bin;
    private String status,action_code,str_segment,bin_name,str_CanId;
    private String engId,str_etr,str_contact_name,str_contact_num;
    private FrameLayout progressOverlay;
    private boolean startFlag, endFlag;
    private RelativeLayout startLayout, endLayout, resolveLayout, holdLayout;
    private JSONArray result;
    private String loc="",startLongi="",startLati="";
    private AppCompatActivity activity;
    private Location location;
    private String fromDateString = "";
    private Calendar mCalendar;
    private AlphaAnimation inAnimation;
    private AlphaAnimation outAnimation;
    private BottomSheetBehavior sheetBehavior;
    private  ConstraintLayout layoutBottomSheet;
    private String Sr,StrSubSubType;
    private String str_bbinId,strSlotType;
    private ArrayList<String> rc1Name;
    private ArrayList<String> rc1Code;

    private ArrayList<String> changeBinName;
    //private OnItemClickListener myClickListener;

    public SRDetailFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SRDetailFragment newInstance(String param1, String param2) {
        Log.d("para", param1);
        SRDetailFragment fragment = new SRDetailFragment();
        Bundle args = new Bundle();
        args.putString("srNumber", param1);
        args.putString("slotType", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint("CutPasteId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Toolbar mtoolbar = activity.findViewById(R.id.toolbar);
        txtHeader = mtoolbar.findViewById(R.id.txtHeader);
        txtHeader.setText("SR Detail");
        try {
            Sr = Objects.requireNonNull(getArguments()).getString("srNumber");
            strSlotType = getArguments().getString("slotType");
            getAssignment(Sr, strSlotType);
        }catch (Exception ex){
            ex.getMessage();
        }

        View view = inflater.inflate(R.layout.fragment_s_r_detail, container, false);
        layoutBottomSheet = view.findViewById(R.id.bottomSheet);
        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);
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
        contactName = (TextView) view.findViewById(R.id.contactName);
        contactNumber = (TextView) view.findViewById(R.id.contactNumber);
        etr = (TextView) view.findViewById(R.id.dateTimeText);
        sessionStatus = (TextView) view.findViewById(R.id.sessionStatus);
        contacted = (Spinner) view.findViewById(R.id.contacted);
        resolveContacted = (Spinner) view.findViewById(R.id.resolveContacted);
        btnHoldSubmit = (Button) view.findViewById(R.id.btnHoldSubmit);
        btnETRSubmit = (Button) view.findViewById(R.id.btnETRSubmit);
        btnUnifySession = (Button) view.findViewById(R.id.btnUnifySession);
        btnResolveSubmit = (Button) view.findViewById(R.id.btnResolveSubmit);
        changeStatus = (Spinner) view.findViewById(R.id.changeStatus);
        holdReason = (Spinner) view.findViewById(R.id.holdReason);
        rc1 = (Spinner) view.findViewById(R.id.rc1);
        DateEdit = (EditText) view.findViewById(R.id.dateTimeText);
        rfo = (EditText) view.findViewById(R.id.rfo);
        startLayout = (RelativeLayout) view.findViewById(R.id.startLayout);
        endLayout = (RelativeLayout) view.findViewById(R.id.endLayout);
        resolveLayout = (RelativeLayout) view.findViewById(R.id.resolveLayout);
        holdLayout = (RelativeLayout) view.findViewById(R.id.holdLayout);
        progressOverlay = (FrameLayout) view.findViewById(R.id.progress_overlay);
        btnMgrt = view.findViewById(R.id.btnMgrt);
        btnNoc = view.findViewById(R.id.btnNoc);
        sp_change_bin =view.findViewById(R.id.sp_change_bin);
        btnSubmitChnageBin = view.findViewById(R.id.btnSubmitChnageBin);
        btnSrDetails = view.findViewById(R.id.btnSrDetails);
        bindChangeStatus(0);
        GetChangeBin();
        // BottomSheet();
        changeStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String status = changeStatus.getSelectedItem().toString();
                if (status == "Resolved") {
                    resolveLayout.setVisibility(View.VISIBLE);
                    holdLayout.setVisibility(View.GONE);
                    //  getRC1();
                } else if (status.equals("Hold")) {
                    resolveLayout.setVisibility(View.GONE);
                    holdLayout.setVisibility(View.VISIBLE);
                    getActionCode();
                    bindContacted();
                } else {
                    resolveLayout.setVisibility(View.GONE);
                    holdLayout.setVisibility(View.GONE);
                    bindContacted();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnStartTime.setOnClickListener(v -> {
            boolean isLoc = getLatLong(startLocation);
            if (isLoc == true) {
                Calendar c = Calendar.getInstance();
                @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formattedDate = df.format(c.getTime());
                startTime.setText(formattedDate);
                startLayout.setVisibility(View.GONE);
                endLayout.setVisibility(View.VISIBLE);
                saveStartTime();
            }
        });
        btnEndTime.setOnClickListener(v -> {
            boolean isLoc = getLatLong(endLocation);
            if (isLoc == true) {
                Calendar c = Calendar.getInstance();
                @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formattedDate = df.format(c.getTime());
                endTime.setText(formattedDate);
                endLayout.setVisibility(View.GONE);
                saveEndTime();
                bindChangeStatus(1);
            }
        });

        btnHoldSubmit.setOnClickListener(v -> {
            boolean isValid = true;
            if (holdReason.getSelectedItem().toString().equals("Select Hold Reason")) {
                isValid = false;
                Toast.makeText(activity, "Please select Hold Reason", Toast.LENGTH_LONG).show();
            } else if (contactName.getText().toString().equals("")) {
                isValid = false;
                Toast.makeText(activity, "Please enter Contact Person", Toast.LENGTH_LONG).show();
            } else if (contactNumber.getText().toString().equals("")) {
                isValid = false;
                Toast.makeText(activity, "Please enter Contact Number", Toast.LENGTH_LONG).show();
            } else if (!isValidMobile(contactNumber.getText().toString())) {
                isValid = false;
                Toast.makeText(activity, "Please enter Valid Contact Number", Toast.LENGTH_LONG).show();
            } else if (contacted.getSelectedItem().toString().equals("Select Status")) {
                isValid = false;
                Toast.makeText(activity, "Please select customer is contacted or not", Toast.LENGTH_LONG).show();
            }

            if (isValid == true) {
                submitOnHold();
            }
        });

        btnSubmitChnageBin.setOnClickListener(v -> {
                    if(str_bbinId.equals("0")){
                        Toast.makeText(getActivity(),"Please select the change bin",Toast.LENGTH_LONG).show();
                    }else{
                        SendBinDetails();
                    }
                    //  SendBinDetails();
                }
        );

        btnResolveSubmit.setOnClickListener(v -> {
            Intent i = new Intent(activity, Activity_Resolve.class);
            i.putExtra("CustomerId",customerId.getText().toString());
            i.putExtra("SrNumber",srNumber.getText().toString());
            i.putExtra("SlotType",strSlotType);
            i.putExtra("SubSubType",StrSubSubType);
            startActivity(i);
        });
        btnETRSubmit.setOnClickListener(v -> {
            boolean isValid = true;
            if (etr.getText().toString().equals("")) {
                isValid = false;
                Toast.makeText(activity, "Please enter ETR", Toast.LENGTH_LONG).show();
            } else {
                String dtStart = etr.getText().toString();
                @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                try {
                    Date etrDate = format.parse(dtStart);
                    Date currDate = Calendar.getInstance().getTime();
                    if (etrDate.compareTo(currDate) < 0) {
                        isValid = false;
                        Toast.makeText(activity, "ETR can only be of future", Toast.LENGTH_LONG).show();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if (isValid == true) {
                updateETR();
            }
        });
        customerMobile.setOnClickListener(v -> {
            Uri number = Uri.parse("tel:" + customerMobile.getText().toString());
            call_action(number);
        });
        btnMgrt.setOnClickListener(v -> {
            Bundle bundle=new Bundle();
            bundle.putString("segment", str_segment);
            bundle.putString("CustomerId",customerId.getText().toString());
            FragmentMrtg bottomSheetFragment = new FragmentMrtg();
            bottomSheetFragment.setArguments(bundle);
            bottomSheetFragment.show(activity.getSupportFragmentManager(), bottomSheetFragment.getTag());
        });

        btnNoc.setOnClickListener(v -> WebViewNoc());
        btnSrDetails.setOnClickListener(v -> {
            Bundle bundle=new Bundle();
            bundle.putString("SrNumber", Sr);
            FragmentTransaction t = Objects.requireNonNull(this.getFragmentManager()).beginTransaction();
            Fragment mFrag = new SrDetailsListFragment();
            mFrag.setArguments(bundle);
            t.replace(R.id.fregment_container, mFrag);
            t.commit();
        });
        DateEdit.setOnClickListener(v -> {
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

            }
        });
        mCalendar = Calendar.getInstance();
        fromDateString = sendDateFormat.format(mCalendar.getTime());
        return view;
    }

    private void bindChangeStatus(int isResolve) {
        ArrayList<String> caseStatus = new ArrayList<String>();
        caseStatus.add("Select Status");
        caseStatus.add("Hold");
        if (isResolve == 1) {
            caseStatus.add("Resolved");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_item, caseStatus);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        changeStatus.setAdapter(adapter);
    }

    private void bindContacted() {
        ArrayList<String> contact = new ArrayList<String>();
        contact.add("Select Status");
        contact.add("Yes");
        contact.add("No");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Objects.requireNonNull(getContext()), android.R.layout.simple_spinner_item, contact);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        contacted.setAdapter(adapter);
        resolveContacted.setAdapter(adapter);
    }


    private void GetChangeBin() {
      //  String authKey = "ac7b51de9d888e1458dd53d8aJAN3ba6f";
        String action = "getBinmovementSR";

        ChangeBinRequest changeBinRequest = new ChangeBinRequest();
        changeBinRequest.setAuthkey(Constants.AUTH_KEY);
        changeBinRequest.setAction(action);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<JsonElement> call = apiService.getChnageBinDetails(changeBinRequest);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                try {
                    if (response.isSuccessful()) {
                        JSONObject jsonObject = new JSONObject(String.valueOf(response.body()));
                        status = jsonObject.getString("Status");
                            try {
                                result = jsonObject.getJSONArray("Response");
                                if (result != null) {
                                    rc1Code = new ArrayList<String>();
                                    rc1Name = new ArrayList<String>();
                                    rc1Code.add("0");
                                    rc1Name.add("Select Change Bin");
                                    for (int i = 0; i < result.length(); i++) {
                                        JSONObject jsonData = new JSONObject(String.valueOf(result.getString(i)));
                                        Log.d("RC1Response", jsonData.toString());
                                        String name = jsonData.getString("teamName");
                                        String code = jsonData.getString("binId");
                                        rc1Code.add(code);
                                        rc1Name.add(name);
                                    }
                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_item, rc1Name);
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    sp_change_bin.setAdapter(adapter);
                                }

                                sp_change_bin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        int itemPosition = adapterView.getSelectedItemPosition();
                                        str_bbinId = rc1Code.get(itemPosition);
                                        Log.e("code", str_bbinId);
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        //rc1Code.add("0");
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                      //  }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.e("RetroError", t.toString());
            }
        });
    }

   /* private void GetChangeBin() {
        String authKey = "ac7b51de9d888e1458dd53d8aJAN3ba6f";
        String action = "getBinmovementSR";

        ChangeBinRequest changeBinRequest = new ChangeBinRequest();
        changeBinRequest.setAuthkey(authKey);
        changeBinRequest.setAction(action);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ChnageBinResponse> call = apiService.getChnageBinDetails(changeBinRequest);
        call.enqueue(new Callback<ChnageBinResponse>() {
            @Override
            public void onResponse(retrofit2.Call<ChnageBinResponse> call, Response<ChnageBinResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        changeBinList = new ArrayList<>();
                        if (response.body() != null) {
                            changeBinList.addAll(response.body().getResponse());
                        }
                        changeBinName = new ArrayList<>();
                        changeBinName.add("Change Bin");

                        for (ChnageBinResponse.Response i : changeBinList)
                        changeBinName.add(i.getTeamName());
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_item, changeBinName);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        sp_change_bin.setAdapter(adapter);
                        sp_change_bin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                              //  String text = sp_change_bin.getSelectedItem().toString();
                                int itemPosition = parentView.getSelectedItemPosition();
                                str_bbinId = changeBinList.get(position).getBinId();
                                Log.e("code", str_bbinId);

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parentView) {

                            }

                        });


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<ChnageBinResponse> call, Throwable t) {
                Log.e("RetroError", t.toString());
            }
        });
    }*/


    private void SendBinDetails() {
       // String authKey = "ac7b51de9d888e1458dd53d8aJAN3ba6f";
        String action = "saveBinmovementSR";
        SendChangeBinRequest sendChangeBinRequest = new SendChangeBinRequest();
        sendChangeBinRequest.setAuthkey(Constants.AUTH_KEY);
        sendChangeBinRequest.setAction(action);
        sendChangeBinRequest.setSrNumber(Sr);
        sendChangeBinRequest.setBinId(str_bbinId);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ChangeBinResponse> call = apiService.sendBinDetails(sendChangeBinRequest);
        call.enqueue(new Callback<ChangeBinResponse>() {
            @Override
            public void onResponse(Call<ChangeBinResponse> call, Response<ChangeBinResponse> response) {
                try {
                    if (response.isSuccessful()) {

                    String status= String.valueOf(Objects.requireNonNull(response.body()).getStatus());
                    if(status.equals("1")){
                        Toast.makeText(getActivity(),"Change Bin Submitted Sucessfully",Toast.LENGTH_LONG).show();
                        Intent i = new Intent(getActivity(),MainActivity.class);
                        startActivity(i);
                        Objects.requireNonNull(getActivity()).finish();
                    } else {
                        Toast.makeText(getActivity(),"Something went wrong...",Toast.LENGTH_LONG).show();
                    }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ChangeBinResponse> call, Throwable t) {
                Log.e("RetroError", t.toString());
            }
        });
    }

    private void getActionCode() {
       // String authKey = "ac7b51de9d888e1458dd53d8aJAN3ba6f";
        String action = "getActioncodeMst";

        RCRequest rcRequest = new RCRequest();
        rcRequest.setAuthkey(Constants.AUTH_KEY);
        rcRequest.setAction(action);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<JsonElement> call = apiService.getRCDetail(rcRequest);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                try {
                    if (response.isSuccessful()) {
                        JSONObject jsonObject = new JSONObject(String.valueOf(response.body()));
                        status = jsonObject.getString("Status");
                        if (status.equals("Failure")) {
                            Log.d("Failure", "error");
                        } else if (status.equals("1")) {
                            try {
                                result = jsonObject.getJSONArray("Response");
                                if (result != null) {
                                    ArrayList<String> action = new ArrayList<String>();
                                    if(action_code==null){
                                        action.add("Select Hold Reason");
                                        for (int i = 0; i < result.length(); i++) {
                                            JSONObject jsonData = new JSONObject(String.valueOf(result.getString(i)));
                                            String code = jsonData.getString("actionCode");
                                            action.add(code);
                                        }
                                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_item, action);
                                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        holdReason.setAdapter(adapter);
                                    }else{
                                        action.add(action_code);
                                        contactName.setText(str_contact_name);
                                        contactNumber.setText(str_contact_num);
                                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_item, action);
                                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        holdReason.setAdapter(adapter);
                                        holdReason.setSelection(Integer.parseInt(action_code));
                                    }
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
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.e("RetroError", t.toString());
            }
        });
    }

    private void getAssignment(String srText, String slotType) {
        String authKey = "ac7b51de9d888e1458dd53d8aJAN3ba6f";
        String action = "getASRBySrNumber";
        String taskType = "Assigned";

        SRRequest srRequest = new SRRequest();
        srRequest.setAuthkey(authKey);
        srRequest.setAction(action);
        srRequest.setTaskType(taskType);
        srRequest.setSlotType(slotType);
        srRequest.setSrNumber(srText);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<JsonElement> call = apiService.getSRDetail(srRequest);
        call.enqueue(new Callback<JsonElement>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
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
                                    if (order != null) {
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
                                        action_code = order.getActionCode();
                                        // contactName.setText(order.getCustomer_contacted());
                                        str_etr = order.getEtr();
                                        str_contact_name = order.getCustomerName();
                                        str_contact_num = order.getCustomerMobile();
                                        str_segment = order.getSegment();
                                        Sr = order.getSrNumber();
                                        StrSubSubType = order.getSrSubSubType();

                                    }

                                    String s = Objects.requireNonNull(order).getSlaClock();
                                    if(s!=null) {
                                        @SuppressLint("SimpleDateFormat") SimpleDateFormat f1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");//HH for hour of the day (0 - 23)
                                        Date d;
                                        try {
                                            d = f1.parse(s);

                                        @SuppressLint("SimpleDateFormat") SimpleDateFormat f2 = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
                                            slaClock.setText(f2.format(d));
                                        } catch (Exception ex) {
                                            ex.getMessage();
                                        }
                                    }

                                    slaStatus.setText(order.getSlaStatus());
                                    if (order.getSlaStatus().equals("In Progress") || order.getSlaStatus().equals("Succeeded")) {
                                        slaStatus.setTextColor(Color.parseColor("#008000"));
                                    } else if (order.getSlaStatus().equals("Paused")) {
                                        slaStatus.setTextColor(Color.parseColor("#8B0000"));
                                    } else if (order.getSlaStatus().equals("Noncompliant")) {
                                        slaStatus.setTextColor(Color.parseColor("#8B0000"));
                                    } else if (order.getSlaStatus().equals("Nearing Noncompliance")) {
                                        slaStatus.setTextColor(Color.parseColor("#FFA500"));
                                    }
                                    if (!order.getSegment().equals("Home")) {
                                        customerIP.setText(order.getCustomerIP());
                                    }
                                    if(!order.getSegment().equals("Business")){
                                        btnNoc.setVisibility(View.GONE);
                                    }
                                    segment.setText(order.getSegment());
                                    podName.setText(order.getPodName());
                                    devicePort.setText(order.getDeviceName() + " : " + order.getPortId());
                                    foni.setText((order.getFoni()));
                                    if (order.getFoni().equals("Yes")) {
                                        foni.setTextColor(Color.parseColor("#FCF6F5FF"));
                                        foni.setBackgroundColor(Color.parseColor("#8B0000"));
                                    }
                                    repeat_sr.setText((order.getRepeat_sr()));
                                    if (order.getRepeat_sr().equals("Yes")) {
                                        repeat_sr.setTextColor(Color.parseColor("#FCF6F5FF"));
                                        repeat_sr.setBackgroundColor(Color.parseColor("#8B0000"));
                                    }
                                    massoutage.setText((order.getMassoutage()));
                                    if (order.getMassoutage().equals("Yes")) {
                                        massoutage.setTextColor(Color.parseColor("#FCF6F5FF"));
                                        massoutage.setBackgroundColor(Color.parseColor("#8B0000"));
                                    }
                                    engId = order.getEngId();
                                    startFlag = order.getStartLatitude().equals("");
                                    endFlag = order.getEndLatitude().equals("");
                                    if (!startFlag) {
                                        startLayout.setVisibility(View.GONE);
                                    }
                                    if (!endFlag) {
                                        bindChangeStatus(1);
                                        endLayout.setVisibility(View.GONE);
                                    } else {
                                        endLayout.setVisibility(View.VISIBLE);
                                    }
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
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.e("RetroError", t.toString());
            }
        });
    }


    private boolean isValidMobile(String phone) {
        if (phone.length() == 10) {
            return android.util.Patterns.PHONE.matcher(phone).matches();
        } else
            return false;
    }


    private void submitOnHold() {
        String authKey = "ac7b51de9d888e1458dd53d8aJAN3ba6f";
        String action = "saveActionCode";
        Sr = srNumber.getText().toString();
        String sr = srNumber.getText().toString();
        String actionCode = holdReason.getSelectedItem().toString();
        String custName = contactName.getText().toString();
        String custNum = contactNumber.getText().toString();
        String isContacted = contacted.getSelectedItem().toString();
        String EngEmailId = MainActivity.prefConfig.readName();
        String UpdatedBy = MainActivity.prefConfig.readUserName();

        SRRequest srRequest = new SRRequest();
        srRequest.setAuthkey(authKey);
        srRequest.setAction(action);
        srRequest.setSrNumber(sr);
        srRequest.setActionCode(actionCode);
        srRequest.setEngId(EngEmailId);
        srRequest.setUpdatedBy(UpdatedBy);
        srRequest.setEmpId(engId);
        srRequest.setContactName(custName);
        srRequest.setContactNumber(custNum);
        if(isContacted.equals("Yes")){
            srRequest.setContacted("True");
        }else if(isContacted.equals("No")){
            srRequest.setContacted("False");
        }

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        progressOverlay.setAnimation(inAnimation);
        progressOverlay.setVisibility(View.VISIBLE);
        btnHoldSubmit.setEnabled(false);
        Call<JsonElement> call = apiService.getSRDetail(srRequest);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                outAnimation = new AlphaAnimation(1f, 0f);
                outAnimation.setDuration(200);
                progressOverlay.setAnimation(outAnimation);
                progressOverlay.setVisibility(View.GONE);
                try {
                    if (response.isSuccessful()) {
                        JSONObject jsonObject = new JSONObject(String.valueOf(response.body()));
                        status = jsonObject.getString("Status");
                        if (status.equals("1")) {
                            try {
                                String result = jsonObject.getString("Response");
                                Toast.makeText(activity, result, Toast.LENGTH_LONG).show();
                                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fregment_container, new WelcomeFragment(), WelcomeFragment.class.getSimpleName()).commit();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            String result = jsonObject.getString("Message");
                            btnHoldSubmit.setEnabled(true);
                            Toast.makeText(activity, result, Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (Exception e) {
                    btnHoldSubmit.setEnabled(true);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                progressOverlay.setVisibility(View.GONE);
                btnHoldSubmit.setEnabled(true);
                Log.e("RetroError", t.toString());
            }
        });
    }


    private void updateETR() {
        //String authKey = "ac7b51de9d888e1458dd53d8aJAN3ba6f";
        String action = "saveEtrDetail";
        String sr = srNumber.getText().toString();
        String dateTimeText = etr.getText().toString();
        dateTimeText = dateTimeText.contains("AM") ? dateTimeText.replace("AM", "").trim() : dateTimeText.replace("PM", "").trim();

        SRRequest srRequest = new SRRequest();
        srRequest.setAuthkey(Constants.AUTH_KEY);
        srRequest.setAction(action);
        srRequest.setSrNumber(sr);
        srRequest.setEngId(engId);
        srRequest.setETR(dateTimeText);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<JsonElement> call = apiService.getSRDetail(srRequest);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                try {
                    if (response.isSuccessful()) {
                        JSONObject jsonObject = new JSONObject(String.valueOf(response.body()));
                        status = jsonObject.getString("Status");
                        if (status.equals("1")) {
                            try {
                                String result = jsonObject.getString("Response");
                                Toast.makeText(activity, result, Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            String result = jsonObject.getString("Message");
                            Toast.makeText(activity, result, Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.e("RetroError", t.toString());
            }
        });
    }

    private void saveStartTime() {
        try {
            String authKey = "ac7b51de9d888e1458dd53d8aJAN3ba6f";
            String action = "SaveGPSTime";
            String sr = srNumber.getText().toString();
            loc = startLocation.getText().toString();
            startLongi = loc.split(", ")[0];
            startLati = loc.split(", ")[1];
            String startAdd = "Empty";
            String startDate = startTime.getText().toString();
            String EngEmailId = MainActivity.prefConfig.readName();

            StarttimeRequest startTimeRequest = new StarttimeRequest();
            startTimeRequest.setAuthkey(authKey);
            startTimeRequest.setAction(action);
            startTimeRequest.setSrNumber(sr);
            startTimeRequest.setStartLongitude(startLongi);
            startTimeRequest.setStartLatitude(startLati);
            startTimeRequest.setStartAddress(startAdd);
            startTimeRequest.setStartTime(startDate);
            startTimeRequest.setEngEmailId(EngEmailId);

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<JsonElement> call = apiService.performOrderStarttime(startTimeRequest);
            call.enqueue(new Callback<JsonElement>() {
                @Override
                public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                    try {
                        if (response.isSuccessful()) {
                            JSONObject jsonObject = new JSONObject(String.valueOf(response.body()));
                            status = jsonObject.getString("Status");
                            if (status.equals("Success")) {
                                try {
                                    String result = jsonObject.getString("response");
                                    Toast.makeText(activity, result, Toast.LENGTH_LONG).show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                String result = jsonObject.getString("Message");
                                Toast.makeText(activity, result, Toast.LENGTH_LONG).show();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<JsonElement> call, Throwable t) {
                    Log.e("RetroError", t.toString());
                }
            });
        }catch (Exception ex){
            ex.getMessage();
        }
    }

    private void saveEndTime() {
        try {
            String authKey = "ac7b51de9d888e1458dd53d8aJAN3ba6f";
            String action = "updateGPSTime";
            String sr = srNumber.getText().toString();
            loc = endLocation.getText().toString();
            String endLongi = loc.split(", ")[0];
            String endLati = loc.split(", ")[1];
            String endAdd = "Empty";
            String endDate = endTime.getText().toString();
            String EngEmailId = MainActivity.prefConfig.readName();

            EndtimeRequest endTimeRequest = new EndtimeRequest();
            endTimeRequest.setAuthkey(authKey);
            endTimeRequest.setAction(action);
            endTimeRequest.setSrNumber(sr);
            endTimeRequest.setEndLongitude(endLongi);
            endTimeRequest.setEndLatitude(endLati);
            endTimeRequest.setEndAddress(endAdd);
            endTimeRequest.setEndTime(endDate);
            endTimeRequest.setEngEmailId(EngEmailId);


            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<JsonElement> call = apiService.performOrderEndtime(endTimeRequest);
            call.enqueue(new Callback<JsonElement>() {
                @Override
                public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                    try {
                        if (response.isSuccessful()) {
                            JSONObject jsonObject = new JSONObject(String.valueOf(response.body()));
                            status = jsonObject.getString("Status");
                            if (status.equals("Success")) {
                                try {
                                    String result = jsonObject.getString("response");
                                    Toast.makeText(activity, result, Toast.LENGTH_LONG).show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                String result = jsonObject.getString("Message");
                                Toast.makeText(activity, result, Toast.LENGTH_LONG).show();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<JsonElement> call, Throwable t) {
                    Log.e("RetroError", t.toString());
                }
            });
        }catch (Exception ex){
            ex.getMessage();
        }
    }

    private boolean getLatLong(TextView txtLocation) {
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
            try {
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location == null) {
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }
                double longi = location.getLongitude();
                double lati = location.getLatitude();
                String message = longi + ", " + lati;
                txtLocation.setText(message);
            }catch (Exception ex){
                ex.getMessage();
            }
        }
        return isLoc;
    }

    private void call_action(Uri number) {
        Activity activity;
        activity = getActivity();
        Intent intent = new Intent(Intent.ACTION_CALL, number);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(activity), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.requestPermissions(new String[]{
                        Manifest.permission.CALL_PHONE}, 11);
            }
        }
        activity.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (AppCompatActivity) getActivity();

    }

    SimpleDateFormat sendDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
    @SuppressLint("SetTextI18n")
    final DatePickerDialog.OnDateSetListener mFromDateSetListener = (view, year, monthOfYear, dayOfMonth) -> {
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, monthOfYear);
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        fromDateString = sendDateFormat.format(mCalendar.getTime());
        DateEdit.setText("" + fromDateString);
    };
    @SuppressLint("SetTextI18n")
    final TimePickerDialog.OnTimeSetListener mTimeDateSetListener = (view, hourOfDay, minuteOfHour) -> {
        mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        mCalendar.set(Calendar.MINUTE, minuteOfHour);
        fromDateString = sendDateFormat.format(mCalendar.getTime());
        DateEdit.setText("" + fromDateString);
    };


    private void WebViewNoc(){
        NocFragment myFragment = new NocFragment();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fregment_container, myFragment).addToBackStack(null).commit();
    }




    private void BottomSheet(){
        btnMgrt.setOnClickListener(v -> {
            Bundle bundle=new Bundle();
            bundle.putString("segment", str_segment);
            bundle.putString("CustomerId",customerId.getText().toString());
            FragmentMrtg bottomSheetFragment = new FragmentMrtg();
            bottomSheetFragment.setArguments(bundle);
            bottomSheetFragment.show(activity.getSupportFragmentManager(), bottomSheetFragment.getTag());
        });

        btnNoc.setOnClickListener(v -> WebViewNoc());
        btnSrDetails.setOnClickListener(v -> {
            Bundle bundle=new Bundle();
            bundle.putString("SrNumber", Sr);
            FragmentTransaction t = Objects.requireNonNull(this.getFragmentManager()).beginTransaction();
            Fragment mFrag = new SrDetailsListFragment();
            mFrag.setArguments(bundle);
            t.replace(R.id.fregment_container, mFrag);
            t.commit();
        });

    }
}


