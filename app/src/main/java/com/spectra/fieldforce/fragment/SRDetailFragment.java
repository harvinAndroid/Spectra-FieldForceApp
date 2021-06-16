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
import android.view.MenuItem;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.spectra.fieldforce.model.CanIdRequest;
import com.spectra.fieldforce.model.ChangeBinRequest;
import com.spectra.fieldforce.model.ChangeBinResponse;
import com.spectra.fieldforce.model.CommonResponse;
import com.spectra.fieldforce.model.EndtimeRequest;
import com.spectra.fieldforce.model.Order;
import com.spectra.fieldforce.model.RCRequest;
import com.spectra.fieldforce.model.SRRequest;
import com.spectra.fieldforce.model.SendChangeBinRequest;
import com.spectra.fieldforce.model.StarttimeRequest;
import com.spectra.fieldforce.R;
import com.spectra.fieldforce.activity.Activity_Resolve;
import com.spectra.fieldforce.activity.MainActivity;
import com.spectra.fieldforce.api.ApiClient;
import com.spectra.fieldforce.api.ApiInterface;
import com.spectra.fieldforce.utils.Constants;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
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
import static com.spectra.fieldforce.utils.AppConstants.EMPTY;
import static com.spectra.fieldforce.utils.AppConstants.FALSE;
import static com.spectra.fieldforce.utils.AppConstants.HOLD;
import static com.spectra.fieldforce.utils.AppConstants.NO;
import static com.spectra.fieldforce.utils.AppConstants.RESOLVE;
import static com.spectra.fieldforce.utils.AppConstants.SELECT_STATUS;
import static com.spectra.fieldforce.utils.AppConstants.TRUE;
import static com.spectra.fieldforce.utils.AppConstants.UNHOLD;
import static com.spectra.fieldforce.utils.AppConstants.YES;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SRDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SRDetailFragment extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener{


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private TextView customerId, customerName, customerMobile, customerAddress, srNumber, slotTime, caseRemarks,
            srStatus, srType, srSubType, slaClock, slaStatus, customerIP, segment, devicePort, podName, etr, sessionStatus,
            startTime, endTime, startLocation, endLocation, foni, repeat_sr, massoutage, contactName, contactNumber, txtHeader;
    private Button btnHoldSubmit, btnStartTime, btnEndTime, btnETRSubmit, btnUnifySession, btnResolveSubmit,
            btnSubmitChnageBin,btnUnhold,btnitemConsumption;
    private EditText DateEdit, rfo,change_bin_note;
    private FloatingActionButton fab_item_consumption;
    private Spinner resolveContacted, changeStatus, rc1, holdReason, contacted,sp_change_bin,spntemConsumption;
    private String status,action_code,str_segment,bin_name,str_CanId,strAssignmentStatus;
    private String engId,str_etr,str_contact_name,str_contact_num;
    private FrameLayout progressOverlay;
    private boolean startFlag, endFlag;
    private RelativeLayout startLayout, endLayout, resolveLayout, holdLayout,unholdlayout,additemLayout;
    private JSONArray result;
    private String loc="",startLongi="",startLati="",str_material;
    private AppCompatActivity activity;
    private Location location;
    private String fromDateString = "";
    private Calendar mCalendar;
    private AlphaAnimation inAnimation;
    private AlphaAnimation outAnimation;
    private BottomSheetBehavior sheetBehavior;
    private  ConstraintLayout layoutBottomSheet;
    private String SrNum,StrSubSubType;
    private String str_bbinId,strSlotType,strcustomerNetworkTech;
    private ArrayList<String> rc1Name;
    private ArrayList<String> rc1Code;
    private ArrayList<String> addMater;
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
            SrNum = requireArguments().getString("srNumber");
            strSlotType = getArguments().getString("slotType");
            getAssignment(SrNum, strSlotType);
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
        unholdlayout = (RelativeLayout) view.findViewById(R.id.unholdlayout);
        btnUnhold = (Button)view.findViewById(R.id.btnUnhold);
        additemLayout =(RelativeLayout)view.findViewById(R.id.additemLayout);
       // btnitemConsumption = (Button)view.findViewById(R.id.btnitemConsumption);
        spntemConsumption = (Spinner)view.findViewById(R.id.spntemConsumption);
        sp_change_bin =view.findViewById(R.id.sp_change_bin);
        btnSubmitChnageBin = view.findViewById(R.id.btnSubmitChnageBin);
        fab_item_consumption = view.findViewById(R.id.fab_item_consumption);
        change_bin_note = view.findViewById(R.id.change_bin_note);
        BottomNavigationView bottom_navigation = view.findViewById(R.id.bottom_navigation);
        bottom_navigation.setOnNavigationItemSelectedListener(this);
       /* bindChangeStatus(0);*/
        GetChangeBin();
        changeStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String status = changeStatus.getSelectedItem().toString();
                switch (status) {
                    case RESOLVE:
                        checkStatus();
                       // addMaterial();
                       /* resolveLayout.setVisibility(View.VISIBLE);*/
                        holdLayout.setVisibility(View.GONE);
                        unholdlayout.setVisibility(View.GONE);
                        break;
                    case HOLD:
                        resolveLayout.setVisibility(View.GONE);
                        unholdlayout.setVisibility(View.GONE);
                        holdLayout.setVisibility(View.VISIBLE);
                        getActionCode();
                        bindContacted();
                        break;
                    case UNHOLD:
                        unholdlayout.setVisibility(View.VISIBLE);
                        resolveLayout.setVisibility(View.GONE);
                        holdLayout.setVisibility(View.GONE);
                        break;
                    default:
                        resolveLayout.setVisibility(View.GONE);
                        holdLayout.setVisibility(View.GONE);
                        unholdlayout.setVisibility(View.GONE);
                        bindContacted();
                        break;
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

     fab_item_consumption.setOnClickListener(v -> AddMaterial());

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
               if(change_bin_note.getText().toString().equals("")){
                   Toast.makeText(getActivity(),"Please enter the Remark for Change bin",Toast.LENGTH_LONG).show();
               }else{
                   if(str_bbinId.equals("0")){
                       Toast.makeText(getActivity(),"Please select the Change bin",Toast.LENGTH_LONG).show();
                   }else{
                       String note = change_bin_note.getText().toString();
                       SendBinDetails();
                       SaveBinNote(note);
                   }
               }
               }

        );

        btnResolveSubmit.setOnClickListener(v -> {
            Intent i = new Intent(activity, Activity_Resolve.class);
            i.putExtra("CustomerId",customerId.getText().toString());
            i.putExtra("SrNumber",srNumber.getText().toString());
            i.putExtra("SlotType",strSlotType);
            i.putExtra("SubSubType",StrSubSubType);
            i.putExtra("customerNetworkTech",strcustomerNetworkTech);
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
        btnUnhold.setOnClickListener(v ->
                ChangeUnholdStatus());

       /* holdReason.setOnItemClickListener((parent, view1, position, id) -> setActionCode());*/





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
        caseStatus.add(SELECT_STATUS);

        if(strAssignmentStatus.equals(HOLD)){
            caseStatus.add(UNHOLD);
        }else{
            caseStatus.add(HOLD);
            if (isResolve == 1 ) {
                caseStatus.add(RESOLVE);
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, caseStatus);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        changeStatus.setAdapter(adapter);
    }


    private void addMaterial(){
        addMater = new ArrayList<String>();
        addMater.add("Select Option");
        addMater.add("Yes");
        addMater.add("No");
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,addMater);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spntemConsumption.setAdapter(adapter1);


    }

    private void bindContacted() {
        ArrayList<String> contact = new ArrayList<String>();
        contact.add(SELECT_STATUS);
        contact.add(YES);
        contact.add(NO);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, contact);
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
                                    rc1Name.add("Select Bin");
                                    for (int i = 0; i < result.length(); i++) {
                                        JSONObject jsonData = new JSONObject(String.valueOf(result.getString(i)));
                                        Log.d("RC1Response", jsonData.toString());
                                        String name = jsonData.getString("teamName");
                                        String code = jsonData.getString("binId");
                                        rc1Code.add(code);
                                        rc1Name.add(name);
                                    }
                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(requireActivity(), android.R.layout.simple_spinner_item, rc1Name);
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


    private void SendBinDetails() {
        String action = "saveBinmovementSR";
        SendChangeBinRequest sendChangeBinRequest = new SendChangeBinRequest();
        sendChangeBinRequest.setAuthkey(Constants.AUTH_KEY);
        sendChangeBinRequest.setAction(action);
        sendChangeBinRequest.setSrNumber(SrNum);
        sendChangeBinRequest.setBinId(str_bbinId);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ChangeBinResponse> call = apiService.sendBinDetails(sendChangeBinRequest);
        call.enqueue(new Callback<ChangeBinResponse>() {
            @Override
            public void onResponse(Call<ChangeBinResponse> call, @NotNull Response<ChangeBinResponse> response) {
                try {
                    if (response.isSuccessful()) {
                    String status= String.valueOf(Objects.requireNonNull(response.body()).getStatus());
                    if(status.equals("1")){
                        Toast.makeText(getActivity(),"Change Bin Submitted Sucessfully",Toast.LENGTH_LONG).show();
                        Intent i = new Intent(getActivity(),MainActivity.class);
                        startActivity(i);
                        requireActivity().finish();
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



    private void SaveBinNote(String note) {
        String action = "createSRNotes";
        SendChangeBinRequest sendChangeBinRequest = new SendChangeBinRequest();
        sendChangeBinRequest.setAuthkey(Constants.AUTH_KEY);
        sendChangeBinRequest.setAction(action);
        sendChangeBinRequest.setSrNumber(SrNum);
        sendChangeBinRequest.setNoteDes(note);
        sendChangeBinRequest.setNoteTitle("Bin Movement");

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ChangeBinResponse> call = apiService.sendBinDetails(sendChangeBinRequest);
        call.enqueue(new Callback<ChangeBinResponse>() {
            @Override
            public void onResponse(Call<ChangeBinResponse> call, @NotNull Response<ChangeBinResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        String status= String.valueOf(Objects.requireNonNull(response.body()).getStatus());
                        if(status.equals("1")){
                           // Toast.makeText(getActivity(),"Change Bin Submitted Sucessfully",Toast.LENGTH_LONG).show();
                            Intent i = new Intent(getActivity(),MainActivity.class);
                            startActivity(i);
                            requireActivity().finish();
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
       RCRequest rcRequest = new RCRequest();
        rcRequest.setAuthkey(Constants.AUTH_KEY);
        rcRequest.setAction(Constants.GET_ACTION_CODEMST);

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
                                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(requireActivity(), android.R.layout.simple_spinner_item, action);
                                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        holdReason.setAdapter(adapter);


                                    }else{
                                        action.add(action_code);
                                        contactName.setText(str_contact_name);
                                        contactNumber.setText(str_contact_num);
                                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(requireActivity(), android.R.layout.simple_spinner_item, action);
                                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        holdReason.setAdapter(adapter);
                                        holdReason.setSelection(Integer.parseInt(action_code));

                                    }
                                    /*holdReason.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            action.add("Select Hold Reason");
                                            for (int i = 0; i < result.length(); i++) {
                                                JSONObject jsonData = null;
                                                try {
                                                    jsonData = new JSONObject(String.valueOf(result.getString(i)));
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                                String code = null;
                                                try {
                                                    code = jsonData.getString("actionCode");
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                                action.add(code);
                                            }
                                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(requireActivity(), android.R.layout.simple_spinner_item, action);
                                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                            holdReason.setAdapter(adapter);
                                        }
                                    });*/
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


    private void setActionCode() {
        RCRequest rcRequest = new RCRequest();
        rcRequest.setAuthkey(Constants.AUTH_KEY);
        rcRequest.setAction(Constants.GET_ACTION_CODEMST);

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
                                        action.add("Select Hold Reason");
                                        for (int i = 0; i < result.length(); i++) {
                                            JSONObject jsonData = new JSONObject(String.valueOf(result.getString(i)));
                                            String code = jsonData.getString("actionCode");
                                            action.add(code);
                                        }
                                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(requireActivity(), android.R.layout.simple_spinner_item, action);
                                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        holdReason.setAdapter(adapter);

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

        SRRequest srRequest = new SRRequest();
        srRequest.setAuthkey(Constants.AUTH_KEY);
        srRequest.setAction(Constants.GETASR_BY_SRNUMBER);
        srRequest.setTaskType(Constants.ASSIGNED);
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
                                        SrNum = order.getSrNumber();
                                        StrSubSubType = order.getSrSubSubTypeID();
                                        strAssignmentStatus = order.getSrStatus();
                                        strcustomerNetworkTech = order.getCustomerNetworkTech();
                                        bindChangeStatus(0);
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
                                   /* if(!order.getSegment().equals("Business")){
                                        btnNoc.setVisibility(View.GONE);
                                    }*/
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
                                    if(!startFlag){
                                        startFlag = order.getStartLatitude().equals("");
                                    }
                                    if(! endFlag){
                                        endFlag = order.getEndLatitude().equals("");
                                    }


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
        String isContacted = contacted.getSelectedItem().toString();
        SRRequest srRequest = new SRRequest();
        srRequest.setAuthkey(Constants.AUTH_KEY);
        srRequest.setAction(Constants.SAVE_ACTION_CODE);
        srRequest.setSrNumber(srNumber.getText().toString());
        srRequest.setActionCode(holdReason.getSelectedItem().toString());
        srRequest.setEngId(MainActivity.prefConfig.readName());
        srRequest.setUpdatedBy(MainActivity.prefConfig.readUserName());
        srRequest.setEmpId(engId);
        srRequest.setContactName(contactName.getText().toString());
        srRequest.setContactNumber(contactNumber.getText().toString());
        if(isContacted.equals(YES)){
            srRequest.setContacted(TRUE);
        }else if(isContacted.equals(NO)){
            srRequest.setContacted(FALSE);
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


    private void ChangeUnholdStatus() {
        CanIdRequest changeunholdStatus = new CanIdRequest();
        changeunholdStatus.setAuthkey(Constants.AUTH_KEY);
        changeunholdStatus.setAction(Constants.GET_SR_INPROGESS);
        changeunholdStatus.setSrNumber(SrNum);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonResponse> call = apiService.setUnholdStatus(changeunholdStatus);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(@NonNull Call<CommonResponse> call, @NonNull Response<CommonResponse> response) {
                if (response.body() != null && response.body().getStatus().equals("1")) {
                    Intent i = new Intent(getActivity(),MainActivity.class);
                    Toast.makeText(getActivity(), response.body().getResponse(),Toast.LENGTH_LONG).show();
                    startActivity(i);
                    requireActivity().finish();
                }
            }

            @Override
            public void onFailure(@NonNull Call<CommonResponse> call, @NonNull Throwable t) {
                //  handleVisOfNetworkAndProgress(View.GONE, View.GONE);
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
            loc = startLocation.getText().toString();
            StarttimeRequest startTimeRequest = new StarttimeRequest();
            startTimeRequest.setAuthkey(Constants.AUTH_KEY);
            startTimeRequest.setAction(Constants.SAVE_GPS_TIME);
            startTimeRequest.setSrNumber(srNumber.getText().toString());
            startTimeRequest.setStartLongitude(loc.split(", ")[0]);
            startTimeRequest.setStartLatitude(loc.split(", ")[1]);
            startTimeRequest.setStartAddress(EMPTY);
            startTimeRequest.setStartTime(startTime.getText().toString());
            startTimeRequest.setEngEmailId( MainActivity.prefConfig.readName());

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
            loc = endLocation.getText().toString();
            EndtimeRequest endTimeRequest = new EndtimeRequest();
            endTimeRequest.setAuthkey(Constants.AUTH_KEY);
            endTimeRequest.setAction(Constants.UPDATE_GPS_TIME);
            endTimeRequest.setSrNumber(srNumber.getText().toString());
            endTimeRequest.setEndLongitude(loc.split(", ")[0]);
            endTimeRequest.setEndLatitude(loc.split(", ")[1]);
            endTimeRequest.setEndAddress(EMPTY);
            endTimeRequest.setEndTime(endTime.getText().toString());
            endTimeRequest.setEngEmailId(MainActivity.prefConfig.readName());


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

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat sendDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm a");
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
        Bundle bundle=new Bundle();
        bundle.putString("CanId", customerId.getText().toString());
        NocFragment myFragment = new NocFragment();
        myFragment.setArguments(bundle);
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fregment_container, myFragment).addToBackStack(null).commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_sr_details:
                Bundle bundle=new Bundle();
                bundle.putString("SrNumber", SrNum);
                @SuppressLint("UseRequireInsteadOfGet") FragmentTransaction t = Objects.requireNonNull(this.getFragmentManager()).beginTransaction();
                Fragment mFrag = new SrDetailsListFragment();
                mFrag.setArguments(bundle);
                t.replace(R.id.fregment_container, mFrag);
                t.commit();
                break;

            case R.id.action_noc:
                if(str_segment.equals("Home")) {
                    Toast.makeText(activity,"No NOC In case of Home",Toast.LENGTH_LONG).show();
                }else{
                    WebViewNoc();
                    }
                break;

            case R.id.action_add_item_consumption:
               editMaterial();
                break;
            case R.id.action_mrtg:
                    Bundle bundle1=new Bundle();
                    bundle1.putString("segment", str_segment);
                    bundle1.putString("CustomerId",customerId.getText().toString());
                    FragmentMrtg bottomSheetFragment = new FragmentMrtg();
                    bottomSheetFragment.setArguments(bundle1);
                    bottomSheetFragment.show(activity.getSupportFragmentManager(), bottomSheetFragment.getTag());
                break;
        }

        return true;
    }


    private void editMaterial(){
        Bundle bundle_consumption =new Bundle();
        bundle_consumption.putString("SrNumber", SrNum);
        bundle_consumption.putString("CustomerId", customerId.getText().toString());
        bundle_consumption.putString("SlotType",strSlotType);
        bundle_consumption.putString("SubSubType",StrSubSubType);
        @SuppressLint("UseRequireInsteadOfGet") FragmentTransaction t11= Objects.requireNonNull(this.getFragmentManager()).beginTransaction();
        ItemConsumptionDetailsFragment itemConsumptionFragment1 = new ItemConsumptionDetailsFragment();
        itemConsumptionFragment1.setArguments(bundle_consumption);
        t11.replace(R.id.fregment_container, itemConsumptionFragment1);
        t11.commit();
    }
    private void checkStatus() {
        ChangeBinRequest checkStatus = new ChangeBinRequest();
        checkStatus.setAction(Constants.GET_MATERIAL_CONSUMPTION_FLAG);
        checkStatus.setAuthkey(Constants.AUTH_KEY);
        checkStatus.setSrNumber(SrNum);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonResponse> call = apiService.getStatus(checkStatus);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(@NonNull Call<CommonResponse> call, @NonNull Response<CommonResponse> response) {
                String status = Objects.requireNonNull(response.body()).getStatus();
                if (status.equals("1")) {
                    String res = response.body().getResponse();
                    if (res != null) {
                        additemLayout.setVisibility(View.VISIBLE);
                        if (response.body().getResponse().equals("0")) {
                            addMater = new ArrayList<String>();
                            addMater.add("No");
                            addMater.add("Select Option");
                            addMater.add("Yes");
                            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, addMater);
                            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spntemConsumption.setAdapter(adapter1);
                            spntemConsumption.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    str_material = parent.getItemAtPosition(position).toString();
                                    if (str_material.equals("Select Option")) {
                                        fab_item_consumption.setVisibility(View.GONE);
                                        resolveLayout.setVisibility(View.GONE);
                                        Toast.makeText(activity, "Please Choose the option", Toast.LENGTH_LONG).show();
                                    } else {
                                        saveStatus(str_material);
                                    }
                                }

                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        } else if (response.body().getResponse().equals("1")) {
                            addMater = new ArrayList<String>();
                            addMater.add("Yes");
                            addMater.add("No");
                            addMater.add("Select Option");
                            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, addMater);
                            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spntemConsumption.setAdapter(adapter1);
                            fab_item_consumption.setVisibility(View.VISIBLE);
                            spntemConsumption.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    str_material = parent.getItemAtPosition(position).toString();
                                    if (str_material.equals("Select Option")) {
                                        fab_item_consumption.setVisibility(View.GONE);
                                        resolveLayout.setVisibility(View.GONE);
                                        Toast.makeText(activity, "Please Choose the option", Toast.LENGTH_LONG).show();
                                    } else {
                                        saveStatus(str_material);
                                    }
                                }

                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        } else {
                            addMater = new ArrayList<String>();
                            addMater.add("Yes");
                            addMater.add("No");
                            addMater.add("Select Option");
                            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, addMater);
                            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spntemConsumption.setAdapter(adapter1);
                            spntemConsumption.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    str_material = parent.getItemAtPosition(position).toString();
                                    if (str_material.equals("Select Option")) {
                                        fab_item_consumption.setVisibility(View.GONE);
                                        resolveLayout.setVisibility(View.GONE);
                                        Toast.makeText(activity, "Please Choose the option", Toast.LENGTH_LONG).show();
                                    } else {
                                        saveStatus(str_material);
                                    }
                                }

                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        }

                    } else {
                        additemLayout.setVisibility(View.VISIBLE);
                        addMater = new ArrayList<String>();
                        addMater.add("Select Option");
                        addMater.add("Yes");
                        addMater.add("No");
                        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, addMater);
                        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spntemConsumption.setAdapter(adapter1);
                        spntemConsumption.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                str_material = parent.getItemAtPosition(position).toString();
                                if (str_material.equals("Select Option")) {
                                    fab_item_consumption.setVisibility(View.GONE);
                                    resolveLayout.setVisibility(View.GONE);
                                    Toast.makeText(activity, "Please Choose the option", Toast.LENGTH_LONG).show();
                                } else {
                                    saveStatus(str_material);
                                }
                            }

                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }

            }

            }

            @Override
            public void onFailure(@NonNull Call<CommonResponse> call, @NonNull Throwable t) {
                //  handleVisOfNetworkAndProgress(View.GONE, View.GONE);
            }
        });
    }



    private void saveStatus(String str_material) {
        String status_local;
        ChangeBinRequest checkStatus = new ChangeBinRequest();
        checkStatus.setAction(Constants.SAVE_MATERIAL_CONSUMPTION_FLAG);
        checkStatus.setAuthkey(Constants.AUTH_KEY);
        checkStatus.setSrNumber(SrNum);
        if(str_material.equals("Yes")){
            status_local="22";
            checkStatus.setStatus("yes");
            fab_item_consumption.setVisibility(View.VISIBLE);
            resolveLayout.setVisibility(View.GONE);
        }else{
            status_local = "25";
            checkStatus.setStatus("no");
        }

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonResponse> call = apiService.saveStatus(checkStatus);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(@NonNull Call<CommonResponse> call, @NonNull Response<CommonResponse> response) {
                String status = Objects.requireNonNull(response.body()).getStatus();
                if (status.equals("1")&& status_local.equals("22")) {

                  // AddMaterial();
                }else{
                    fab_item_consumption.setVisibility(View.GONE);
                    resolveLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<CommonResponse> call, @NonNull Throwable t) {
                //  handleVisOfNetworkAndProgress(View.GONE, View.GONE);
            }
        });

    }

    private void AddMaterial(){
        Bundle bundle_consumption =new Bundle();
        bundle_consumption.putString("SrNumber", SrNum);
        bundle_consumption.putString("CustomerId", customerId.getText().toString());
        bundle_consumption.putString("SlotType",strSlotType);
        bundle_consumption.putString("SubSubType",StrSubSubType);
        bundle_consumption.putString("customerNetworkTech",strcustomerNetworkTech);
        @SuppressLint("UseRequireInsteadOfGet") FragmentTransaction t1= Objects.requireNonNull(this.getFragmentManager()).beginTransaction();
        ItemConsumptionFragment itemConsumptionFragment = new ItemConsumptionFragment();
        itemConsumptionFragment.setArguments(bundle_consumption);
        t1.replace(R.id.fregment_container, itemConsumptionFragment);
        t1.commit();
    }

}


