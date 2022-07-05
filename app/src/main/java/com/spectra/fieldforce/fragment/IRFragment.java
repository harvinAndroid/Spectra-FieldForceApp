package com.spectra.fieldforce.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.spectra.fieldforce.activity.BucketTabActivity;
import com.spectra.fieldforce.activity.ProvisioningMainActivity;
import com.spectra.fieldforce.adapter.IRItemConsumptionListAdapter;
import com.spectra.fieldforce.adapter.IRServiceConsumptionListAdapter;
import com.spectra.fieldforce.adapter.IrEquipmentConsumpAdapter;
import com.spectra.fieldforce.adapter.IrServiceListAdapter;
import com.spectra.fieldforce.adapter.WcrEquipmentConsumpAdapter;
import com.spectra.fieldforce.databinding.IrFragmentBinding;
import com.spectra.fieldforce.databinding.WcrFragmentBinding;
import com.spectra.fieldforce.model.CommonMessageResponse;
import com.spectra.fieldforce.model.gpon.request.AccountInfoRequest;
import com.spectra.fieldforce.model.gpon.request.GetMaxCap;
import com.spectra.fieldforce.model.gpon.request.HoldWcrRequest;
import com.spectra.fieldforce.model.gpon.request.IRCompleteRequest;
import com.spectra.fieldforce.model.gpon.request.ResendActivationCodeRequest;
import com.spectra.fieldforce.model.gpon.request.ResendCodeIRRequest;
import com.spectra.fieldforce.model.gpon.request.ResendNavRequest;
import com.spectra.fieldforce.model.gpon.request.SubmitApprovalRequest;
import com.spectra.fieldforce.model.gpon.request.UpdateCpeMacRequest;
import com.spectra.fieldforce.model.gpon.request.UpdateGeneralDetails;
import com.spectra.fieldforce.model.gpon.request.UpdateIREngineer;
import com.spectra.fieldforce.model.gpon.request.UpdateQualityParamIRequest;
import com.spectra.fieldforce.model.gpon.request.UpdateQualityParamRequest;
import com.spectra.fieldforce.model.gpon.request.UpdateWcrEnggRequest;
import com.spectra.fieldforce.model.gpon.response.CommonClassResponse;
import com.spectra.fieldforce.model.gpon.response.GetMaxCapResponse;
import com.spectra.fieldforce.model.gpon.response.HoldReasonResponse;
import com.spectra.fieldforce.model.gpon.response.IrInfoResponse;
import com.spectra.fieldforce.R;
import com.spectra.fieldforce.api.ApiClient;
import com.spectra.fieldforce.api.ApiInterface;
import com.spectra.fieldforce.model.gpon.response.WcrResponse;
import com.spectra.fieldforce.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class IRFragment  extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener{
    IrFragmentBinding binding;
    private ArrayList<IrInfoResponse.ItemConsumtion> itemConsumtions;
     ArrayList<IrInfoResponse.InstallationItemList> installationItemLists;
    ArrayList<IrInfoResponse.ServiceConsumtionList> serviceConsumtionLists;
    private ArrayList<String> irCpeMac;
    private ArrayList<String> IrType;
    private ArrayList<String> irCpeMacid;
    private String strCpe,IrStatusReport,strGuiID,strSegment,strCanId,str_provisionId,strholdId,strStatusofReport,straddition,attach,orderId,strOtp,strDate;
    private ArrayList<String> holdCategory;
    private ArrayList<String> holdCategoryId;
    private AlphaAnimation inAnimation,outAnimation;
    private ArrayList<String> QualityParam;
    private ArrayList<String> QualityParamSpeed;
    private ArrayList<String> QualityParamDns;
    private ArrayList<String> QualityParamAntivirus;
    private ArrayList<String> QualityParamSelfCare;
    private ArrayList<String> QualityParamMrtg;
    private ArrayList<String> QualityParamIP;
    ArrayAdapter<String> adaptertype;
    ArrayAdapter<String> adaptercpe;
    LocationManager locationManager;
    Boolean IrStatus;
    private String add,strwcrguid;
    private List<HoldReasonResponse.WCRHoldCategory> holdList;
    String latitude, longitude,doa,strProductSegment;
    private static final int REQUEST_LOCATION = 1;
    FusedLocationProviderClient mFusedLocationClient;
    int PERMISSION_ID = 44;
    int day,month,year;
  //  private Calendar mcalendar;
    private Calendar mCalendar;
    private String fromDateString = "";
    String type="",strProdSeg;
    private String  strCpeMac,strdns,strvirus,strip,strmrtg,strspeed,strselfcare;

    public IRFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = IrFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        strCanId = requireArguments().getString("canId");
        strStatusofReport = requireArguments().getString("StatusofReport");
        orderId = requireArguments().getString("OrderId");
        IrStatusReport = requireArguments().getString("IrStatusReport");
        binding.tvIrStatus.setText("IR Status : "+ IrStatusReport);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        IrStatus = requireArguments().getBoolean("IrStatus");
        String strIrStatus = String.valueOf(IrStatus);
        binding.searchtoolbar.rlBack.setOnClickListener(this);
        binding.searchtoolbar.tvLang.setText("IR");

        if(strIrStatus.equals("true")){
            binding.linea18.setVisibility(View.VISIBLE);
        }else{
            binding.linea18.setVisibility(View.GONE);
        }
        getIrInfo();
       // getItemStatus(strGuiID);
        init();
        setType();
        ActivityCompat.requestPermissions( getActivity(),
                new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
    }

    private void init(){
        Listener();
        setData();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.rl_back) {
           nextScreen();
        }
    }

    private void setType(){
        IrType = new ArrayList<String>();
        IrType.add("Select IR Type");
        IrType.add("Yes");
        IrType.add("No");
        adaptertype = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, IrType);
        adaptertype.setDropDownViewResource(android.R.layout.simple_spinner_item);
        binding.layoutGeneralDetails.spIrAttached.setAdapter(adaptertype);
        binding.tvApproval.setOnClickListener(view -> SubmitApproval());
        binding.etHoldCategory.setOnClickListener(v-> binding.spHoldCategory.performClick());
        binding.spHoldCategory.setOnItemSelectedListener(this);
        binding.layoutInstallationparam.etSpeedTest.setOnClickListener(v-> binding.layoutInstallationparam.spSpeedTest.performClick());
        binding.layoutInstallationparam.spSpeedTest.setOnItemSelectedListener(this);
        binding.layoutInstallationparam.etIp.setOnClickListener(v-> binding.layoutInstallationparam.spIp.performClick());
        binding.layoutInstallationparam.spIp.setOnItemSelectedListener(this);
        binding.layoutInstallationparam.etMrtg.setOnClickListener(v-> binding.layoutInstallationparam.spMrtg.performClick());
        binding.layoutInstallationparam.spMrtg.setOnItemSelectedListener(this);
        binding.layoutInstallationparam.etEducationAntivirus.setOnClickListener(v-> binding.layoutInstallationparam.spEducationAntivirus.performClick());
        binding.layoutInstallationparam.spEducationAntivirus.setOnItemSelectedListener(this);
        binding.layoutInstallationparam.etDns.setOnClickListener(v-> binding.layoutInstallationparam.spDns.performClick());
        binding.layoutInstallationparam.spDns.setOnItemSelectedListener(this);
        binding.layoutInstallationparam.etSelfcare.setOnClickListener(v-> binding.layoutInstallationparam.spSelfcare.performClick());
        binding.layoutInstallationparam.spSelfcare.setOnItemSelectedListener(this);

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sendDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm a");
        @SuppressLint("SetTextI18n")
        final DatePickerDialog.OnDateSetListener mFromDateSetListener = (view, year, monthOfYear, dayOfMonth) -> {
            mCalendar.set(Calendar.YEAR, year);
            mCalendar.set(Calendar.MONTH, monthOfYear);
            mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            fromDateString = sendDateFormat.format(mCalendar.getTime());
            binding.layoutEnggDetails.etCreatedOn.setText("" + fromDateString);
        };

        binding.layoutEnggDetails.etCreatedOn.setOnClickListener(view -> {
            try {
                final DatePickerDialog fromPickerDialog = new DatePickerDialog(
                        getActivity(), android.R.style.Theme_Material_Light_Dialog_Alert,
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

    private void inProgress(){
        inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        binding.progressLayout.progressOverlay.setAnimation(inAnimation);
        binding.progressLayout.progressOverlay.setVisibility(View.VISIBLE);
    }

    private void outProgress(){
        outAnimation = new AlphaAnimation(1f, 0f);
        outAnimation.setDuration(200);
        binding.progressLayout.progressOverlay.setAnimation(outAnimation);
        binding.progressLayout.progressOverlay.setVisibility(View.GONE);
    }

    private void SubmitApproval(){
        inProgress();
        SubmitApprovalRequest submitApprovalRequest = new SubmitApprovalRequest();
        submitApprovalRequest.setAuthkey(Constants.AUTH_KEY);
        submitApprovalRequest.setAction(Constants.SUBMIT_FOR_APPROVAL);
        submitApprovalRequest.setItemId(strGuiID);
        submitApprovalRequest.setItemType("IR");

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonClassResponse> call = apiService.submitApproval(submitApprovalRequest);
        call.enqueue(new Callback<CommonClassResponse>() {
            @Override
            public void onResponse(Call<CommonClassResponse> call, Response<CommonClassResponse> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                        outProgress();
                    try {
                        if(response.body().getStatus().equals("Success")){
                            moveNext();
                            Toast.makeText(getContext(),response.body().getResponse().getMessage(),Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getContext(),response.body().getResponse().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
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


    private void setData(){

        binding.layoutAddEquipment.btnItemEqipment.setOnClickListener(v -> {
            @SuppressLint("UseRequireInsteadOfGet") FragmentTransaction t1= Objects.requireNonNull(this.getFragmentManager()).beginTransaction();
            IREquipmentConsumption irEquipmentConsumption = new IREquipmentConsumption();
            Bundle bundle = new Bundle();
            bundle.putString("strGuuId", strGuiID);
            bundle.putString("canId",strCanId);
            bundle.putString("StatusofReport", strStatusofReport);
            bundle.putString("OrderId",orderId);
            bundle.putBoolean("IrStatus",IrStatus);
            bundle.putString("IrStatusReport",IrStatusReport);

            t1.replace(R.id.frag_container, irEquipmentConsumption);
            irEquipmentConsumption.setArguments(bundle);
            t1.commit();
        });

        binding.layoutServiceDetails.btnAddService.setOnClickListener(v -> {
            @SuppressLint("UseRequireInsteadOfGet") FragmentTransaction t1= Objects.requireNonNull(this.getFragmentManager()).beginTransaction();
            IrServiceConsumption irServiceConsumption = new IrServiceConsumption();
            Bundle bundle = new Bundle();
            bundle.putString("strGuuId", strGuiID);
            bundle.putString("canId",strCanId);
            bundle.putString("IrStatusReport", strStatusofReport);
            bundle.putString("OrderId", orderId);
            bundle.putString("strWcrId", strwcrguid);
            t1.replace(R.id.frag_container, irServiceConsumption);
            irServiceConsumption.setArguments(bundle);
            t1.commit();
        });


        binding.layoutCpemac.etCpeMacShared.setOnClickListener(v->  binding.layoutCpemac.spCpeMacShared.performClick());
        binding.layoutCpemac.spCpeMacShared.setOnItemSelectedListener(this);
        irCpeMac = new ArrayList<String>();
        irCpeMacid = new ArrayList<String>();
        irCpeMac.add("Select CPE Type");
        irCpeMac.add("Completed");
        irCpeMacid.add("111260000");
        adaptercpe = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, irCpeMac);
        adaptercpe.setDropDownViewResource(android.R.layout.simple_spinner_item);
        binding.layoutCpemac.spCpeMacShared.setAdapter(adaptercpe);

        binding.tvResendOtp.setOnClickListener(v -> resendCode());

        binding.layoutCpemac.tvCustSave.setOnClickListener(v -> {
            String cpe = Objects.requireNonNull(binding.layoutGeneralDetails.etIrAttached.getText()).toString();
            if(cpe.equals("Select CPE Type")){
                Toast.makeText(getContext(), "Please Select CPE Type", Toast.LENGTH_LONG).show();
            }else {
                updateCpeMac();
            }
        });

        binding.tvIrComplete.setOnClickListener(v -> {
            String remark = Objects.requireNonNull(binding.etRemarksText.getText()).toString();
            if(strCpeMac.isEmpty()){
                Toast.makeText(getContext(), "Please Save CPE MAC", Toast.LENGTH_LONG).show();
            } else if(attach.equals("-1")||attach.isEmpty()){
                Toast.makeText(getContext(), "Please Save IR Type before IR Save", Toast.LENGTH_LONG).show();
            } else if(strdns.equals("Please Select  DNS")||strdns.isEmpty()){
                Toast.makeText(getContext(), "Please Save  DNS before IR Save", Toast.LENGTH_LONG).show();
            }else if(strvirus.equals("Please Select  Virus")||strvirus.isEmpty()){
                Toast.makeText(getContext(), "Please Save  Virus before IR Save", Toast.LENGTH_LONG).show();
            }else if(strip.equals("Please Select IP")||strip.isEmpty()){
                Toast.makeText(getContext(), "Please Save IP before IR Save", Toast.LENGTH_LONG).show();
            }else if(strmrtg.equals("Please Select  MRTG")||strmrtg.isEmpty()){
                Toast.makeText(getContext(), "Please Save  MRTG before IR Save", Toast.LENGTH_LONG).show();
            }else if(strspeed.equals("Please Select Speed test")||strspeed.isEmpty()){
                Toast.makeText(getContext(), "Please Save Speed test before IR Save", Toast.LENGTH_LONG).show();
            }else if(strselfcare.equals("Please Select Education customer")||strselfcare.isEmpty()){
                Toast.makeText(getContext(), "Please Save Education customer before IR Save", Toast.LENGTH_LONG).show();
            }else if(strDate.isEmpty()){
                Toast.makeText(getContext(),"Please Save Installation Date before IR Save",Toast.LENGTH_LONG).show();
            }
            else if (strProdSeg.equals("Managed Wi-Fi Business")||strProdSeg.equals("Managed Office Solution")||
                strProdSeg.equals("Secured Managed Internet")) {
                if(remark.isEmpty()){
                    Toast.makeText(getContext(),"Please Enter Remark",Toast.LENGTH_LONG).show();
                }else{
                    getLastLocation();
                    if (installationItemLists.size() == 0 || installationItemLists == null) {
                        Toast.makeText(getContext(), "Please Add Equipment", Toast.LENGTH_LONG).show();
                    } else if (itemConsumtions.size() == 0 || itemConsumtions == null) {
                        Toast.makeText(getContext(), "Please Add ItemConsumption", Toast.LENGTH_LONG).show();
                    } else {
                        updateIR(remark,latitude,longitude);
                    }
                }
            }else if(strOtp.isEmpty()){
                Toast.makeText(getContext(),"Please Save Installation Code before IR Save",Toast.LENGTH_LONG).show();
            }else if(remark.isEmpty()){
                Toast.makeText(getContext(),"Please Enter Remark",Toast.LENGTH_LONG).show();
            }else{
                    getLastLocation();

                    if (installationItemLists.size() == 0 || installationItemLists == null) {
                        Toast.makeText(getContext(), "Please Add Equipment", Toast.LENGTH_LONG).show();
                    } else if (itemConsumtions.size() == 0 || itemConsumtions == null) {
                        Toast.makeText(getContext(), "Please Add ItemConsumption", Toast.LENGTH_LONG).show();
                    } else {
                        updateIR(remark,latitude,longitude);
                    }
             //   }
            }
        });

        binding.saveHold.setOnClickListener(v -> {
            updateHoldCategory();
            updateHoldCategoryStatus();
        });

        binding.tvResendNav.setOnClickListener(view -> resendNav());

        binding.layoutGeneralDetails.tvGeneralDetailsSave.setOnClickListener(v -> {
            String ipattach = Objects.requireNonNull(binding.layoutGeneralDetails.etIrAttached.getText()).toString();
            if(ipattach.equals("Select IR Type")){
                Toast.makeText(getContext(), "Please Select IR Type", Toast.LENGTH_LONG).show();
            }else {
                updateGeneralDetails(ipattach);
            }
        });

        binding.layoutEnggDetails.saveEnggDetails.setOnClickListener(v -> {
            String insta = binding.layoutEnggDetails.etInstallationCode.getText().toString();
          if (strProdSeg.equals("Managed Wi-Fi Business")||strProdSeg.equals("Managed Office Solution")||
                  strProdSeg.equals("Secured Managed Internet")) {
              updateIrEnginer(insta);
            }else if(insta.isEmpty()){
                Toast.makeText(getContext(),"Please Enter Installation Code",Toast.LENGTH_LONG).show();
            }else{
                updateIrEnginer(insta);
            }
        });

        binding.layoutInstallationparam.tvSaveQualityParam.setOnClickListener((View v) -> {
            String dns = Objects.requireNonNull(binding.layoutInstallationparam.etDns.getText()).toString();
            String virus = Objects.requireNonNull(binding.layoutInstallationparam.etEducationAntivirus.getText()).toString();
            String ip = Objects.requireNonNull(binding.layoutInstallationparam.etIp.getText()).toString();
            String mrtg = Objects.requireNonNull(binding.layoutInstallationparam.etMrtg.getText()).toString();
            String speed = Objects.requireNonNull(binding.layoutInstallationparam.etSpeedTest.getText()).toString();
            String selfcare = Objects.requireNonNull(binding.layoutInstallationparam.etSelfcare.getText()).toString();
            if(dns.equals("Please Select  DNS")){
                Toast.makeText(getContext(), "Please Select  DNS", Toast.LENGTH_LONG).show();
            }else if(virus.equals("Please Select  Virus")){
                Toast.makeText(getContext(), "Please Select  Virus", Toast.LENGTH_LONG).show();
            }else if(ip.equals("Please Select IP")){
                Toast.makeText(getContext(), "Please Select IP", Toast.LENGTH_LONG).show();
            }else if(mrtg.equals("Please Select  MRTG")){
                Toast.makeText(getContext(), "Please Select  MRTG", Toast.LENGTH_LONG).show();
            }else if(speed.equals("Please Select Speed test")){
                Toast.makeText(getContext(), "Please Select Speed test", Toast.LENGTH_LONG).show();
            }else if(selfcare.equals("Please Select Education customer")){
                Toast.makeText(getContext(), "Please Select Education customer", Toast.LENGTH_LONG).show();
            }else{
                updateQualityParam(dns,virus,mrtg,ip,speed,selfcare);
            }
        });
    }



    private void getItemStatus(){
       inProgress();
        ResendActivationCodeRequest resendActivationCodeRequest = new ResendActivationCodeRequest();
        resendActivationCodeRequest.setAuthkey(Constants.AUTH_KEY);
        resendActivationCodeRequest.setAction(Constants.GET_ITEM_STATUS);
        resendActivationCodeRequest.setId(strGuiID);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonMessageResponse> call = apiService.getItemCodeStatus(resendActivationCodeRequest);
        call.enqueue(new Callback<CommonMessageResponse>() {
            @Override
            public void onResponse(Call<CommonMessageResponse> call, Response<CommonMessageResponse> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                   outProgress();
                    try {
                        if(response.body().getStatusCode().equals("403")){
                            binding.tvWcrItemStatus.setVisibility(View.GONE);
                        }else if(response.body().getStatusCode().equals("200")){
                            binding.tvWcrItemStatus.setText("Item Status : "+ response.body().getMessage());
                            binding.tvWcrItemStatus.setVisibility(View.VISIBLE);
                            // Toast.makeText(getContext(),response.body().getResponse().getMessage(),Toast.LENGTH_LONG).show();
                        }
                        else {
                            binding.tvWcrItemStatus.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<CommonMessageResponse> call, Throwable t) {
                binding.progressLayout.progressOverlay.setVisibility(View.GONE);
                Log.e("RetroError", t.toString());
            }
        });
    }




    private void updateHoldCategoryStatus(){
       inProgress();
        HoldWcrRequest holdWcrRequest = new HoldWcrRequest();
        holdWcrRequest.setAuthkey(Constants.AUTH_KEY);
        holdWcrRequest.setAction(Constants.HOLD_ORDER_INSTALLATION);
        holdWcrRequest.setHoldCategory(strholdId);
        holdWcrRequest.setHoldReason(binding.etHoldReason.getText().toString());
        holdWcrRequest.setOrderID(orderId);
        holdWcrRequest.setStatus("hold");

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonClassResponse> call = apiService.updateHoldCategory(holdWcrRequest);
        call.enqueue(new Callback<CommonClassResponse>() {
            @Override
            public void onResponse(Call<CommonClassResponse> call, Response<CommonClassResponse> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                   outProgress();
                    try {
                        if(response.body().getStatus().equals("Success")){
                            moveNext();
                            Toast.makeText(getContext(),response.body().getResponse().getMessage(),Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getContext(),response.body().getResponse().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
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

    public void getHoldReason() {
       inProgress();
        AccountInfoRequest accountInfoRequest = new AccountInfoRequest();
        accountInfoRequest.setAuthkey(Constants.AUTH_KEY);
        accountInfoRequest.setAction(Constants.WCR_HOLD_CATEGORY);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<HoldReasonResponse> call = apiService.getHoldReason(accountInfoRequest);
        call.enqueue(new Callback<HoldReasonResponse>() {
            @Override
            public void onResponse(Call<HoldReasonResponse> call, Response<HoldReasonResponse> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                  outProgress();
                    try {
                        holdList = response.body().getResponse().getWCRHoldCategory();
                        holdCategoryId = new ArrayList<>();
                        holdCategory = new ArrayList<>();
                        holdCategory.add("Hold Category");
                        for (HoldReasonResponse.WCRHoldCategory hold : holdList)
                            holdCategory.add(hold.getCategory());
                        for (HoldReasonResponse.WCRHoldCategory holdId : holdList)
                            holdCategoryId.add(holdId.getId());
                        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, holdCategory);
                        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_item);
                        binding.spHoldCategory.setAdapter(adapter2);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<HoldReasonResponse> call, Throwable t) {
                binding.progressLayout.progressOverlay.setVisibility(View.GONE);
                Log.e("RetroError", t.toString());
            }
        });
    }




    public void resendCode() {
        inProgress();
        ResendCodeIRRequest resendActivationCodeRequest = new ResendCodeIRRequest();
        resendActivationCodeRequest.setAuthkey(Constants.AUTH_KEY);
        resendActivationCodeRequest.setAction(Constants.RESEND_NAVIR);
        resendActivationCodeRequest.setIRguid(strGuiID);
        resendActivationCodeRequest.setSegment(strSegment);
        resendActivationCodeRequest.setItemType("");

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonClassResponse> call = apiService.getResendCodeIR(resendActivationCodeRequest);
        call.enqueue(new Callback<CommonClassResponse>() {
            @Override
            public void onResponse(Call<CommonClassResponse> call, Response<CommonClassResponse> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                   outProgress();
                    try {
                        if(response.body().getStatus().equals("Success")){
                            moveNext();
                            Toast.makeText(getContext(),response.body().getResponse().getMessage(),Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getContext(),response.body().getResponse().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
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


    private void updateQualityParam( String dns, String virus, String mrtg, String ip, String speed,String self){
        String dns1,virus1,mrtg1,ip1,speed1,self1;
        if(dns.equals("Yes")){
            dns1 =  "111260000";
        }else{
            dns1 = "111260001";
        }
        if(virus.equals("Yes")){
            virus1 =  "111260000";
        }else{
            virus1 = "111260001";
        }
        if(mrtg.equals("Yes")){
            mrtg1 =  "111260000";
        }else{
            mrtg1 = "111260001";
        }
        if(ip.equals("Yes")){
            ip1 =  "111260000";
        }else{
            ip1 = "111260001";
        }
        if(speed.equals("Yes")){
            speed1 =  "111260000";
        }else{
            speed1 = "111260001";
        }
        if(self.equals("Yes")){
            self1 =  "111260000";
        }else{
            self1 = "111260001";
        }
      inProgress();
        UpdateQualityParamIRequest updateQualityParamRequest = new UpdateQualityParamIRequest();
        updateQualityParamRequest.setAuthkey(Constants.AUTH_KEY);
        updateQualityParamRequest.setAction(Constants.UPDATE_IRINSTALLATION);
        updateQualityParamRequest.setDNS(dns1);
        updateQualityParamRequest.setIP(ip1);
        updateQualityParamRequest.setMRTG(mrtg1);
        updateQualityParamRequest.setSelfCare(self1);
        updateQualityParamRequest.setAntiVirus(virus1);
        updateQualityParamRequest.setSpeed(speed1);
        updateQualityParamRequest.setIRguid(strGuiID);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonClassResponse> call = apiService.updateQualityParamIR(updateQualityParamRequest);
        call.enqueue(new Callback<CommonClassResponse>() {
            @Override
            public void onResponse(Call<CommonClassResponse> call, Response<CommonClassResponse> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                   outProgress();
                    try {
                        if(response.body().getStatus().equals("Success")){
                            Toast.makeText(getContext(),response.body().getResponse().getMessage(),Toast.LENGTH_LONG).show();
                            nextScreen();
                        }else{
                            Toast.makeText(getContext(),response.body().getResponse().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
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


    private void getIrInfo(){
        inProgress();
        AccountInfoRequest accountInfoRequest = new AccountInfoRequest();
        accountInfoRequest.setAuthkey(Constants.AUTH_KEY);
        accountInfoRequest.setAction(Constants.GET_IR_INFO);
        accountInfoRequest.setCanId(strCanId);
        accountInfoRequest.setOrderId(orderId);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<IrInfoResponse> call = apiService.getIrInfo(accountInfoRequest);
        call.enqueue(new Callback<IrInfoResponse>() {
            @Override
            public void onResponse(Call<IrInfoResponse> call, Response<IrInfoResponse> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                   outProgress();
                    if(response.body().getStatus().equals("Success")) {
                    try {
                        binding.layoutCustomerDetails.setCustomerDetails(response.body().getResponse().getIr());
                        binding.setIR(response.body().getResponse().getIr());
                        String consumptionStatus = response.body().getResponse().getIr().getConsumptionStatus();
                        if (consumptionStatus.equals("Material not Available")) {
                            binding.tvResendNav.setVisibility(View.VISIBLE);
                        }
                        add="0";
                        strGuiID = response.body().getResponse().getIr().getIrguid();
                        strSegment = response.body().getResponse().getIr().getBusinessSegment();
                        str_provisionId = response.body().getResponse().getGeneral().getProvisionId();
                        strProdSeg = response.body().getResponse().getIr().getProductSegment();
                        if (strProdSeg.equals("Managed Wi-Fi Business")||strProdSeg.equals("Managed Office Solution")||
                                strProdSeg.equals("Secured Managed Internet")){
                            binding.layoutEnggDetails.code.setVisibility(View.GONE);
                        }else{
                            binding.layoutEnggDetails.code.setVisibility(View.VISIBLE);
                        }
                        String strHold = response.body().getResponse().getIr().getShowHold();
                        if (strHold.equals("true")) {
                            binding.linea18.setVisibility(View.VISIBLE);
                        } else {
                            binding.linea18.setVisibility(View.GONE);
                        }
                        strOtp = response.body().getResponse().getEngineer().getOtp();
                        strDate = response.body().getResponse().getEngineer().getInstallationDate();

                        strCpeMac= response.body().getResponse().getIr().getMACShared();
                        strdns = response.body().getResponse().getInstallationQty().getDns();
                        strvirus = response.body().getResponse().getInstallationQty().getAntiVirus();
                        strip = response.body().getResponse().getInstallationQty().getIp();
                        strmrtg = response.body().getResponse().getInstallationQty().getMrtg();
                        strspeed = response.body().getResponse().getInstallationQty().getSpeed();
                        strselfcare = response.body().getResponse().getInstallationQty().getSelfCare();
                        strwcrguid = response.body().getResponse().getIr().getWcrGuid();
                        itemConsumtions = (ArrayList<IrInfoResponse.ItemConsumtion>) response.body().getResponse().getItemConsumtionList();
                        binding.layoutItemcousumption.rvIrItemlist.setHasFixedSize(true);
                        binding.layoutItemcousumption.rvIrItemlist.setLayoutManager(new LinearLayoutManager(getActivity()));
                        binding.layoutInstallationparam.etSelfcare.setText(response.body().getResponse().getInstallationQty().getSelfCare());
                        binding.layoutInstallationparam.etDns.setText(response.body().getResponse().getInstallationQty().getDns());
                        binding.layoutInstallationparam.etEducationAntivirus.setText(response.body().getResponse().getInstallationQty().getAntiVirus());
                        strProductSegment = response.body().getResponse().getIr().getProduct();
                        if ((strSegment.equals("Home")) && (consumptionStatus.equals("Pending")) || (consumptionStatus.equals("Rejected")) || (consumptionStatus.equals("Material not Available"))) {
                            binding.layoutAddEquipment.btnItemEqipment.setVisibility(View.VISIBLE);
                            binding.layoutServiceDetails.btnAddService.setVisibility(View.VISIBLE);
                            add = "1";
                        }
                        if (strProdSeg.equals("Managed Wi-Fi Business")) {
                            binding.layoutEnggDetails.etInstallationCode.setVisibility(View.GONE);
                        } else {
                            binding.layoutEnggDetails.etInstallationCode.setVisibility(View.VISIBLE);
                        }
                        installationItemLists = (ArrayList<IrInfoResponse.InstallationItemList>) response.body().getResponse().getInstallationItemList();
                        serviceConsumtionLists = (ArrayList<IrInfoResponse.ServiceConsumtionList>) response.body().getResponse().getServiceConsumtionList();
                        if(installationItemLists.size()!=0) {
                            straddition = installationItemLists.get(0).getItemType();
                        }
                        if(serviceConsumtionLists.size()!=0){
                             type = serviceConsumtionLists.get(0).getItemType();
                        }

                        if (straddition != null) {
                            if (straddition.equals("Additional")||type.equals("Additional") && strSegment.equals("Home")) {
                                if (consumptionStatus.equals("Waiting for approval")|| consumptionStatus.equals("Approved")||consumptionStatus.equals("Approved but not consumed")) {
                                    binding.tvApproval.setVisibility(View.GONE);
                                    binding.layoutAddEquipment.btnItemEqipment.setVisibility(View.GONE);
                                    binding.layoutServiceDetails.btnAddService.setVisibility(View.GONE);
                                    add = "0";
                                } else {
                                    binding.tvApproval.setVisibility(View.VISIBLE);
                                    binding.layoutAddEquipment.btnItemEqipment.setVisibility(View.VISIBLE);
                                    binding.layoutServiceDetails.btnAddService.setVisibility(View.VISIBLE);
                                    add = "1";
                                }
                            }
                        }
                        if (((consumptionStatus.equals("true")) && strSegment.equals("Business")) || (consumptionStatus.equals("Pending")) || (consumptionStatus.equals("Rejected")) || (consumptionStatus.equals("Material not Available"))){
                            binding.layoutAddEquipment.btnItemEqipment.setVisibility(View.VISIBLE);
                            binding.layoutServiceDetails.btnAddService.setVisibility(View.VISIBLE);
                            binding.tvApproval.setVisibility(View.VISIBLE);

                            add="1";
                        }

                        doa = response.body().getResponse().getIr().getDOAFlag();
                        if(doa!=null){
                            if (doa.equals("No")) {
                                if(straddition.equals("Additional") || type.equals("Additional")) {
                                    binding.tvApproval.setVisibility(View.VISIBLE);
                                    binding.layoutAddEquipment.btnItemEqipment.setVisibility(View.VISIBLE);
                                    binding.layoutServiceDetails.btnAddService.setVisibility(View.VISIBLE);
                                    add = "1";
                                }
                            } else if (doa.equals("Yes")){
                                binding.tvApproval.setVisibility(View.GONE);
                            }
                        }

                        binding.layoutAddEquipment.rvAddEquipment.setHasFixedSize(true);
                        binding.layoutAddEquipment.rvAddEquipment.setLayoutManager(new LinearLayoutManager(getActivity()));
                        if (installationItemLists.size() != 0) {
                            binding.layoutAddEquipment.rvAddEquipment.setAdapter(new IrEquipmentConsumpAdapter(getActivity(), installationItemLists, IrStatusReport, add,orderId));
                        }
                        binding.layoutServiceDetails.rvAddService.setHasFixedSize(true);
                        binding.layoutServiceDetails.rvAddService.setLayoutManager(new LinearLayoutManager(getActivity()));
                        if (serviceConsumtionLists.size() != 0) {
                            binding.layoutServiceDetails.rvAddService.setAdapter(new IRServiceConsumptionListAdapter(getActivity(), serviceConsumtionLists, strStatusofReport, add,strGuiID,orderId));
                        }
                        if (itemConsumtions.size() != 0) {
                            binding.layoutItemcousumption.rvIrItemlist.setAdapter(new IRItemConsumptionListAdapter(getActivity(), itemConsumtions, IrStatusReport,add,orderId));
                        }
                        binding.layoutGeneralDetails.setGeneralDetails(response.body().getResponse().getGeneral());
                        binding.layoutEnggDetails.setEngineer(response.body().getResponse().getEngineer());
                        binding.setHold(response.body().getResponse().getEngineer());
                        String cpe = response.body().getResponse().getIr().getMACShared();
                        if(cpe.equals("0")){
                            irCpeMac = new ArrayList<String>();
                            irCpeMacid = new ArrayList<String>();
                            irCpeMac.add("Select CPE Type");
                            irCpeMac.add("Completed");
                            irCpeMacid.add("111260000");
                             }else  if(cpe.equals("Completed")){
                            irCpeMac = new ArrayList<String>();
                            irCpeMacid = new ArrayList<String>();
                            irCpeMac.add("Completed");
                            irCpeMacid.add("111260000");
                            irCpeMac.add("Select CPE Type");
                           }
                        adaptercpe = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, irCpeMac);
                        adaptercpe.setDropDownViewResource(android.R.layout.simple_spinner_item);
                        binding.layoutCpemac.spCpeMacShared.setAdapter(adaptercpe);
                        attach = response.body().getResponse().getGeneral().getIRAttached();
                        if(attach!=null){
                            if(response.body().getResponse().getGeneral().getIRAttached().equalsIgnoreCase("Yes")){
                                IrType = new ArrayList<String>();
                                IrType.add("Yes");
                                IrType.add("Select IR Type");
                                IrType.add("No");
                                ArrayAdapter<String> adaptertype = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, IrType);
                                adaptertype.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                binding.layoutGeneralDetails.spIrAttached.setAdapter(adaptertype);
                            }else  if(response.body().getResponse().getGeneral().getIRAttached().equalsIgnoreCase("No")){
                                IrType = new ArrayList<String>();
                                IrType.add("No");
                                IrType.add("Yes");
                                IrType.add("Select IR Type");
                                ArrayAdapter<String> adaptertype = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, IrType);
                                adaptertype.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                binding.layoutGeneralDetails.spIrAttached.setAdapter(adaptertype);
                            }
                        }
                        String test = response.body().getResponse().getInstallationQty().getSpeed();
                        if(test.equals("Yes")){
                            QualityParamSpeed = new ArrayList<String>();
                            QualityParamSpeed.add("Yes");
                            QualityParamSpeed.add("No");
                            QualityParamSpeed.add("Please Select Speed test results shown to customer");
                            ArrayAdapter<String> adapter11 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamSpeed);
                            adapter11.setDropDownViewResource(android.R.layout.simple_spinner_item);
                            binding.layoutInstallationparam.spSpeedTest.setAdapter(adapter11);
                        }else if(test.equals("No")){
                            QualityParamSpeed = new ArrayList<String>();
                            QualityParamSpeed.add("No");
                            QualityParamSpeed.add("Yes");
                            QualityParamSpeed.add("Please Select Speed test");
                            ArrayAdapter<String> adapter11 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamSpeed);
                            adapter11.setDropDownViewResource(android.R.layout.simple_spinner_item);
                            binding.layoutInstallationparam.spSpeedTest.setAdapter(adapter11);
                        }else {
                            QualityParamSpeed = new ArrayList<String>();
                            QualityParamSpeed.add("Please Select Speed test");
                            QualityParamSpeed.add("Yes");
                            QualityParamSpeed.add("No");
                            ArrayAdapter<String> adapter11 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamSpeed);
                            adapter11.setDropDownViewResource(android.R.layout.simple_spinner_item);
                            binding.layoutInstallationparam.spSpeedTest.setAdapter(adapter11);
                        }
                        String dns = response.body().getResponse().getInstallationQty().getDns();
                        if(dns.equals("Yes")){
                            QualityParamDns = new ArrayList<String>();
                            QualityParamDns.add("Yes");
                            QualityParamDns.add("No");
                            QualityParamDns.add("Please Select  DNS");
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamDns);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                            binding.layoutInstallationparam.spDns.setAdapter(adapter);
                        }else if(dns.equals("No")){
                            QualityParamDns = new ArrayList<String>();
                            QualityParamDns.add("No");
                            QualityParamDns.add("Yes");
                            QualityParamDns.add("Please Select  DNS");
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamDns);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                            binding.layoutInstallationparam.spDns.setAdapter(adapter);
                        }
                        else {
                            QualityParamDns = new ArrayList<String>();
                            QualityParamDns.add("Please Select  DNS");
                            QualityParamDns.add("Yes");
                            QualityParamDns.add("No");
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamDns);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                            binding.layoutInstallationparam.spDns.setAdapter(adapter);
                        }
                        String antivirus = response.body().getResponse().getInstallationQty().getAntiVirus();
                        if(antivirus.equals("Yes")){
                            QualityParamAntivirus = new ArrayList<String>();
                            QualityParamAntivirus.add("Yes");
                            QualityParamAntivirus.add("No");
                            QualityParamAntivirus.add("Please Select  Virus");
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamAntivirus);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                            binding.layoutInstallationparam.spEducationAntivirus.setAdapter(adapter);
                        }else if(antivirus.equals("No")){
                            QualityParamAntivirus = new ArrayList<String>();
                            QualityParamAntivirus.add("No");
                            QualityParamAntivirus.add("Yes");
                            QualityParamAntivirus.add("Please Select  Virus");
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamAntivirus);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                            binding.layoutInstallationparam.spEducationAntivirus.setAdapter(adapter);
                        }else {
                            QualityParamAntivirus = new ArrayList<String>();
                            QualityParamAntivirus.add("Please Select  Virus");
                            QualityParamAntivirus.add("Yes");
                            QualityParamAntivirus.add("No");
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamAntivirus);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                            binding.layoutInstallationparam.spEducationAntivirus.setAdapter(adapter);
                        }
                        String mrtg = response.body().getResponse().getInstallationQty().getMrtg();
                        if(mrtg.equals("Yes")){
                            QualityParamMrtg = new ArrayList<String>();
                            QualityParamMrtg.add("Yes");
                            QualityParamMrtg.add("No");
                            QualityParamMrtg.add("Please Select  MRTG");
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamMrtg);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                            binding.layoutInstallationparam.spMrtg.setAdapter(adapter);
                        }else if(mrtg.equals("No")){
                            QualityParamMrtg = new ArrayList<String>();
                            QualityParamMrtg.add("No");
                            QualityParamMrtg.add("Yes");
                            QualityParamMrtg.add("Please Select  MRTG");
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamMrtg);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                            binding.layoutInstallationparam.spMrtg.setAdapter(adapter);
                        }else {
                            QualityParamMrtg = new ArrayList<String>();
                            QualityParamMrtg.add("Please Select  MRTG");
                            QualityParamMrtg.add("Yes");
                            QualityParamMrtg.add("No");
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamMrtg);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                            binding.layoutInstallationparam.spMrtg.setAdapter(adapter);
                        }
                        String ip = response.body().getResponse().getInstallationQty().getIp();
                        if(ip.equals("Yes")){
                            QualityParamIP = new ArrayList<String>();
                            QualityParamIP.add("Yes");
                            QualityParamIP.add("No");
                            QualityParamIP.add("Please Select IP");
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamIP);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                            binding.layoutInstallationparam.spIp.setAdapter(adapter);
                        }else if(ip.equals("No")){
                            QualityParamIP = new ArrayList<String>();
                            QualityParamIP.add("No");
                            QualityParamIP.add("Yes");
                            QualityParamIP.add("Please Select IP");
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamIP);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                            binding.layoutInstallationparam.spIp.setAdapter(adapter);
                        }else {
                            QualityParamIP = new ArrayList<String>();
                            QualityParamIP.add("Please Select IP");
                            QualityParamIP.add("Yes");
                            QualityParamIP.add("No");
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamIP);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                            binding.layoutInstallationparam.spIp.setAdapter(adapter);
                        }
                        String selfCare = response.body().getResponse().getInstallationQty().getSelfCare();
                        if(selfCare.equals("Yes")){
                            QualityParamSelfCare = new ArrayList<String>();
                            QualityParamSelfCare.add("Yes");
                            QualityParamSelfCare.add("No");
                            QualityParamSelfCare.add("Please Select Education customer");
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamSelfCare);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                            binding.layoutInstallationparam.spSelfcare.setAdapter(adapter);
                        }else if(selfCare.equals("No")){
                            QualityParamSelfCare = new ArrayList<String>();
                            QualityParamSelfCare.add("No");
                            QualityParamSelfCare.add("Yes");
                            QualityParamSelfCare.add("Please Select Education customer");
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamSelfCare);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                            binding.layoutInstallationparam.spSelfcare.setAdapter(adapter);
                        }else {
                            QualityParamSelfCare = new ArrayList<String>();
                            QualityParamSelfCare.add("Please Select Education customer");
                            QualityParamSelfCare.add("Yes");
                            QualityParamSelfCare.add("No");
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamSelfCare);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                            binding.layoutInstallationparam.spSelfcare.setAdapter(adapter);
                        }
                        getHoldReason();
                      //  getItemStatus();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                      //
                    }else if(response.body().getStatus().equals("Failure")){
                        Toast.makeText(getContext(),"Account Id (CAN Id) does not exist or Inactive.",Toast.LENGTH_LONG).show();
                        nextScreen();
                    }
                }

            }

            @Override
            public void onFailure(Call<IrInfoResponse> call, Throwable t) {
                binding.progressLayout.progressOverlay.setVisibility(View.GONE);
                Log.e("RetroError", t.toString());
            }
        });
    }

    private void resendNav(){
        inProgress();
        ResendNavRequest resendNavRequest = new ResendNavRequest(Constants.AUTH_KEY,Constants.RESEND_NAVIR,"","Business","",strGuiID);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonClassResponse> call = apiService.submitNavWcr(resendNavRequest);
        call.enqueue(new Callback<CommonClassResponse>() {
            @Override
            public void onResponse(Call<CommonClassResponse> call, Response<CommonClassResponse> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                    outProgress();
                    try {
                        if(response.body().getStatus().equals("Success")){
                            moveNext();
                            Toast.makeText(getContext(),response.body().getResponse().getMessage(),Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getContext(),response.body().getResponse().getMessage(),Toast.LENGTH_LONG).show();

                        }
                    } catch (Exception e) {
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

    private void nextScreen(){
        Intent i = new Intent(getActivity(), ProvisioningMainActivity.class);
        i.putExtra("canId", strCanId);
        i.putExtra("StatusofReport", strStatusofReport);
        i.putExtra("OrderId", orderId);
        startActivity(i);
        getActivity().finish();
    }


    private void Listener(){
        binding.layoutGeneralDetails.etIrAttached.setOnClickListener(v-> binding.layoutGeneralDetails.spIrAttached.performClick());
        binding.layoutGeneralDetails.spIrAttached.setOnItemSelectedListener(this);
            binding.linearFive.setVisibility(View.VISIBLE);
            binding.linearFour.setOnClickListener(v -> {
                binding.linearFive.setVisibility(View.VISIBLE);
                binding.linearTen.setVisibility(View.GONE);
                binding.linearEight.setVisibility(View.GONE);
                binding.linear12.setVisibility(View.GONE);
                binding.linear14.setVisibility(View.GONE);
                binding.linear16.setVisibility(View.GONE);
                binding.linear19.setVisibility(View.GONE);
                binding.linear21.setVisibility(View.GONE);
                binding.linearInstallationParamDetails.setVisibility(View.GONE);
                binding.linearservicedeatils.setVisibility(View.GONE);
            });
        binding.linearNine.setOnClickListener(v -> {
            binding.linearFive.setVisibility(View.GONE);
            binding.linearTen.setVisibility(View.VISIBLE);
            binding.linearEight.setVisibility(View.GONE);
            binding.linear12.setVisibility(View.GONE);
            binding.linear14.setVisibility(View.GONE);
            binding.linear16.setVisibility(View.GONE);
            binding.linear19.setVisibility(View.GONE);
            binding.linear21.setVisibility(View.GONE);
            binding.linearInstallationParamDetails.setVisibility(View.GONE);
            binding.linearservicedeatils.setVisibility(View.GONE);
        });
        binding.linearSix.setOnClickListener(v -> {
            binding.linearFive.setVisibility(View.GONE);
            binding.linearTen.setVisibility(View.GONE);
            binding.linearEight.setVisibility(View.VISIBLE);
            binding.linear12.setVisibility(View.GONE);
            binding.linear14.setVisibility(View.GONE);
            binding.linear16.setVisibility(View.GONE);
            binding.linear19.setVisibility(View.GONE);
            binding.linear21.setVisibility(View.GONE);
            binding.linearInstallationParamDetails.setVisibility(View.GONE);
            binding.linearservicedeatils.setVisibility(View.GONE);
        });
        binding.linearFour.setOnClickListener(v -> {
            binding.linearFive.setVisibility(View.VISIBLE);
            binding.linearTen.setVisibility(View.GONE);
            binding.linearEight.setVisibility(View.GONE);
            binding.linear12.setVisibility(View.GONE);
            binding.linear14.setVisibility(View.GONE);
            binding.linear16.setVisibility(View.GONE);
            binding.linear19.setVisibility(View.GONE);
            binding.linear21.setVisibility(View.GONE);
            binding.linearInstallationParamDetails.setVisibility(View.GONE);
            binding.linearservicedeatils.setVisibility(View.GONE);
        });
        binding.linea11.setOnClickListener(v -> {
            binding.linearFive.setVisibility(View.GONE);
            binding.linearTen.setVisibility(View.GONE);
            binding.linearEight.setVisibility(View.GONE);
            binding.linear12.setVisibility(View.VISIBLE);
            binding.linear14.setVisibility(View.GONE);
            binding.linear16.setVisibility(View.GONE);
            binding.linear19.setVisibility(View.GONE);
            binding.linear21.setVisibility(View.GONE);
            binding.linearInstallationParamDetails.setVisibility(View.GONE);
            binding.linearservicedeatils.setVisibility(View.GONE);
        });
        binding.linea13.setOnClickListener(v -> {
            binding.linearFive.setVisibility(View.GONE);
            binding.linearTen.setVisibility(View.GONE);
            binding.linearEight.setVisibility(View.GONE);
            binding.linear12.setVisibility(View.GONE);
            binding.linear14.setVisibility(View.VISIBLE);
            binding.linear16.setVisibility(View.GONE);
            binding.linear19.setVisibility(View.GONE);
            binding.linear21.setVisibility(View.GONE);
            binding.linearInstallationParamDetails.setVisibility(View.GONE);
            binding.linearservicedeatils.setVisibility(View.GONE);
        });
        binding.linea15.setOnClickListener(v -> {
            binding.linearFive.setVisibility(View.GONE);
            binding.linearTen.setVisibility(View.GONE);
            binding.linearEight.setVisibility(View.GONE);
            binding.linear12.setVisibility(View.GONE);
            binding.linear14.setVisibility(View.GONE);
            binding.linear16.setVisibility(View.VISIBLE);
            binding.linear19.setVisibility(View.GONE);
            binding.linear21.setVisibility(View.GONE);
            binding.linearInstallationParamDetails.setVisibility(View.GONE);
            binding.linearservicedeatils.setVisibility(View.GONE);
        });
        binding.linea18.setOnClickListener(v -> {
            binding.linearFive.setVisibility(View.GONE);
            binding.linearTen.setVisibility(View.GONE);
            binding.linearEight.setVisibility(View.GONE);
            binding.linear12.setVisibility(View.GONE);
            binding.linear14.setVisibility(View.GONE);
            binding.linear16.setVisibility(View.GONE);
            binding.linear19.setVisibility(View.VISIBLE);
            binding.linear21.setVisibility(View.GONE);
            binding.linearInstallationParamDetails.setVisibility(View.GONE);
            binding.linearservicedeatils.setVisibility(View.GONE);
        });
        binding.linea20.setOnClickListener(v -> {
            binding.linearFive.setVisibility(View.GONE);
            binding.linearTen.setVisibility(View.GONE);
            binding.linearEight.setVisibility(View.GONE);
            binding.linear12.setVisibility(View.GONE);
            binding.linear14.setVisibility(View.GONE);
            binding.linear16.setVisibility(View.GONE);
            binding.linear19.setVisibility(View.GONE);
            binding.linear21.setVisibility(View.VISIBLE);
            binding.linearservicedeatils.setVisibility(View.GONE);
            binding.linearInstallationParamDetails.setVisibility(View.GONE);
        });
        binding.linearInstallationParam.setOnClickListener(v -> {
            binding.linearFive.setVisibility(View.GONE);
            binding.linearTen.setVisibility(View.GONE);
            binding.linearEight.setVisibility(View.GONE);
            binding.linear12.setVisibility(View.GONE);
            binding.linear14.setVisibility(View.GONE);
            binding.linear16.setVisibility(View.GONE);
            binding.linear19.setVisibility(View.GONE);
            binding.linear21.setVisibility(View.GONE);
            binding.linearInstallationParamDetails.setVisibility(View.VISIBLE);
            binding.linearservicedeatils.setVisibility(View.GONE);
        });
        binding.linearService.setOnClickListener(v -> {
            binding.linearFive.setVisibility(View.GONE);
            binding.linearTen.setVisibility(View.GONE);
            binding.linearEight.setVisibility(View.GONE);
            binding.linear12.setVisibility(View.GONE);
            binding.linear14.setVisibility(View.GONE);
            binding.linear16.setVisibility(View.GONE);
            binding.linear19.setVisibility(View.GONE);
            binding.linear21.setVisibility(View.GONE);
            binding.linearInstallationParamDetails.setVisibility(View.GONE);
            binding.linearservicedeatils.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId() == R.id.sp_cpe_mac_shared){
            binding.layoutCpemac.etCpeMacShared.setText(irCpeMac.get(position));
            if (position != 0) strCpe = "" + irCpeMacid.get(position - 1);
            else strCpe = " ";
        }else if(parent.getId() == R.id.sp_hold_category){
            binding.etHoldCategory.setText(holdCategory.get(position));
            if (position != 0) strholdId = "" + holdCategoryId.get(position - 1);
            else strholdId = " ";
        }else if(parent.getId() == R.id.sp_speed_test) {
            binding.layoutInstallationparam.etSpeedTest.setText(QualityParamSpeed.get(position));
        }
        else if(parent.getId() == R.id.sp_education_antivirus) {
            binding.layoutInstallationparam.etEducationAntivirus.setText(QualityParamAntivirus.get(position));
        }
        else if(parent.getId() == R.id.sp_selfcare) {
            binding.layoutInstallationparam.etSelfcare.setText(QualityParamSelfCare.get(position));
        }
        else if(parent.getId() == R.id.sp_dns) {
            binding.layoutInstallationparam.etDns.setText(QualityParamDns.get(position));
        }
        else if(parent.getId() == R.id.sp_mrtg) {
            binding.layoutInstallationparam.etMrtg.setText(QualityParamMrtg.get(position));
        }
        else if(parent.getId() == R.id.sp_ip) {
            binding.layoutInstallationparam.etIp.setText(QualityParamIP.get(position));
        }  else if(parent.getId() == R.id.sp_ir_attached) {
            binding.layoutGeneralDetails.etIrAttached.setText(IrType.get(position));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void updateGeneralDetails(String ipattach) {
       inProgress();
        if(ipattach.equals("Yes")){
            ipattach = "1";
        }else if(ipattach.equals("No")) {
            ipattach="0";
        }
        UpdateGeneralDetails updateGeneralDetails = new UpdateGeneralDetails();
        updateGeneralDetails.setAuthkey(Constants.AUTH_KEY);
        updateGeneralDetails.setAction(Constants.UPDATE_GENERAL_DETAILS);
        updateGeneralDetails.setIRguid(strGuiID);
        updateGeneralDetails.setCpe(binding.layoutGeneralDetails.etCpeIp.getText().toString());
        updateGeneralDetails.setDeviceId(binding.layoutGeneralDetails.etDeviceId.getText().toString());
        updateGeneralDetails.setDevicePort(binding.layoutGeneralDetails.etDevicePort.getText().toString());
        updateGeneralDetails.setFirst(binding.layoutGeneralDetails.etFirstUsableIp.getText().toString());
        updateGeneralDetails.setGateway(binding.layoutGeneralDetails.etGateway.getText().toString());
        updateGeneralDetails.setIpAddress(binding.layoutGeneralDetails.etIpAddSubnet.getText().toString());
        updateGeneralDetails.setIRAttached(ipattach);
        updateGeneralDetails.setLast(binding.layoutGeneralDetails.etLastUsableIp.getText().toString());
        updateGeneralDetails.setPrimary(binding.layoutGeneralDetails.etPrimaryDns.getText().toString());
        updateGeneralDetails.setProvisionGuid(str_provisionId);
        updateGeneralDetails.setSecondary(binding.layoutGeneralDetails.etSecondaryDns.getText().toString());
        updateGeneralDetails.setSubnet(binding.layoutGeneralDetails.etSubnetMask.getText().toString());

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonClassResponse> call = apiService.updateGeneralDetails(updateGeneralDetails);
        call.enqueue(new Callback<CommonClassResponse>() {
            @Override
            public void onResponse(Call<CommonClassResponse> call, Response<CommonClassResponse> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                    outProgress();
                    try {
                        if(response.body().getStatus().equals("Success")){
                            moveNext();
                            Toast.makeText(getContext(),response.body().getResponse().getMessage(),Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getContext(),response.body().getResponse().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
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

    private void updateIrEnginer(String insta){
        String input = binding.layoutEnggDetails.etCreatedOn.getText().toString();
        String date = input.substring(0, 10);
        inProgress();
        UpdateIREngineer updateIREngineer = new UpdateIREngineer();
        updateIREngineer.setAuthkey(Constants.AUTH_KEY);
        updateIREngineer.setAction(Constants.ENGINER_UPDATE);
        updateIREngineer.setEngName(binding.layoutEnggDetails.etEnggName.getText().toString());
        updateIREngineer.setInstattionDate(date);
        updateIREngineer.setIRguid(strGuiID);
        updateIREngineer.setOTP(insta);
        updateIREngineer.setAppointmentDate(binding.layoutEnggDetails.etCreatedOn.getText().toString());
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonClassResponse> call = apiService.updateIrEnginer(updateIREngineer);
        call.enqueue(new Callback<CommonClassResponse>() {
            @Override
            public void onResponse(Call<CommonClassResponse> call, Response<CommonClassResponse> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                    outProgress();
                    try {
                        if(response.body().getStatus().equals("Success")){
                            moveNext();
                            Toast.makeText(getContext(),response.body().getResponse().getMessage(),Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(getContext(),response.body().getResponse().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
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

    private void moveNext(){
        @SuppressLint("UseRequireInsteadOfGet") FragmentTransaction t= Objects.requireNonNull(this.getFragmentManager()).beginTransaction();
        IRFragment irFragment = new IRFragment();
        Bundle accountinfo = new Bundle();
        accountinfo.putString("canId", strCanId);
        accountinfo.putString("StatusofReport",strStatusofReport);
        accountinfo.putString("OrderId",orderId);
        accountinfo.putBoolean("IrStatus",IrStatus);
        accountinfo.putString("IrStatusReport",IrStatusReport);
        t.replace(R.id.frag_container, irFragment);
        irFragment.setArguments(accountinfo);
        t.commit();
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(task -> {
                    Location location = task.getResult();
                    if (location == null) {
                        requestNewLocationData();
                    } else {
                        latitude= String.valueOf(location.getLatitude());
                        longitude = String.valueOf((location.getLongitude()));
                    }
                });
            } else {
                Toast.makeText(getActivity(), "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(2);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            latitude = String.valueOf(mLastLocation.getLatitude());
            longitude = String.valueOf(mLastLocation.getLongitude());
        }
    };

    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }


    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }
    }

    private void updateCpeMac(){
       inProgress();
        UpdateCpeMacRequest updateCpeMacRequest = new UpdateCpeMacRequest();
        updateCpeMacRequest.setAuthkey(Constants.AUTH_KEY);
        updateCpeMacRequest.setAction(Constants.SHARED_CPEMAC);
        updateCpeMacRequest.setItemId(strGuiID);
        updateCpeMacRequest.setMACShared("111260000");
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonClassResponse> call = apiService.updateCpeMac(updateCpeMacRequest);
        call.enqueue(new Callback<CommonClassResponse>() {
            @Override
            public void onResponse(Call<CommonClassResponse> call, Response<CommonClassResponse> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                    outProgress();
                    try {
                        if(response.body().getStatus().equals("Success")){
                            moveNext();
                            Toast.makeText(getContext(),response.body().getResponse().getMessage(),Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(getContext(),response.body().getResponse().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
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

    private void updateIR(String remark, String latitude, String longitude){
       inProgress();
        IRCompleteRequest irCompleteRequest = new IRCompleteRequest();
        irCompleteRequest.setAuthkey(Constants.AUTH_KEY);
        irCompleteRequest.setAction(Constants.IR_COMPLETE);
        irCompleteRequest.setCanId(strCanId);
        irCompleteRequest.setIRguid(strGuiID);
        irCompleteRequest.setIshold(strholdId);
        irCompleteRequest.setMACShared("111260000");
        irCompleteRequest.setRemarks(remark);
        irCompleteRequest.setLat(latitude);
        irCompleteRequest.setLong(longitude);
        irCompleteRequest.setSource("FFA App");
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonClassResponse> call = apiService.updateIR(irCompleteRequest);
        call.enqueue(new Callback<CommonClassResponse>() {
            @Override
            public void onResponse(Call<CommonClassResponse> call, Response<CommonClassResponse> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                  outProgress();
                    try {
                        if(response.body().getStatus().equals("Success")){
                           nextScreen();
                            Toast.makeText(getContext(),response.body().getResponse().getMessage(),Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(getContext(),response.body().getResponse().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
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

    private void updateHoldCategory(){
       inProgress();
        HoldWcrRequest holdWcrRequest = new HoldWcrRequest();
        holdWcrRequest.setAuthkey(Constants.AUTH_KEY);
        holdWcrRequest.setAction(Constants.HOLD_IR);
        holdWcrRequest.setCategory(strholdId);
        holdWcrRequest.setReason(binding.etHoldReason.getText().toString());
        holdWcrRequest.setIRguidId(strGuiID);
        holdWcrRequest.setSegment(strSegment);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonClassResponse> call = apiService.updateHoldCategory(holdWcrRequest);
        call.enqueue(new Callback<CommonClassResponse>() {
            @Override
            public void onResponse(Call<CommonClassResponse> call, Response<CommonClassResponse> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                  outProgress();
                    try {
                        if(response.body().getStatus().equals("Success")){
                            moveNext();
                            Toast.makeText(getContext(),response.body().getResponse().getMessage(),Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getContext(),response.body().getResponse().getMessage(),Toast.LENGTH_LONG).show();

                        }
                    } catch (Exception e) {
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

}
