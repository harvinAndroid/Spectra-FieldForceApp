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
import com.spectra.fieldforce.adapter.IrEquipmentConsumpAdapter;
import com.spectra.fieldforce.adapter.WcrEquipmentConsumpAdapter;
import com.spectra.fieldforce.databinding.IrFragmentBinding;
import com.spectra.fieldforce.databinding.WcrFragmentBinding;
import com.spectra.fieldforce.model.gpon.request.AccountInfoRequest;
import com.spectra.fieldforce.model.gpon.request.GetMaxCap;
import com.spectra.fieldforce.model.gpon.request.HoldWcrRequest;
import com.spectra.fieldforce.model.gpon.request.IRCompleteRequest;
import com.spectra.fieldforce.model.gpon.request.ResendActivationCodeRequest;
import com.spectra.fieldforce.model.gpon.request.ResendCodeIRRequest;
import com.spectra.fieldforce.model.gpon.request.SubmitApprovalRequest;
import com.spectra.fieldforce.model.gpon.request.UpdateCpeMacRequest;
import com.spectra.fieldforce.model.gpon.request.UpdateGeneralDetails;
import com.spectra.fieldforce.model.gpon.request.UpdateIREngineer;
import com.spectra.fieldforce.model.gpon.request.UpdateQualityParamIRequest;
import com.spectra.fieldforce.model.gpon.request.UpdateQualityParamRequest;
import com.spectra.fieldforce.model.gpon.request.UpdateWcrEnggRequest;
import com.spectra.fieldforce.model.gpon.response.CommonClassResponse;
import com.spectra.fieldforce.model.gpon.response.GetMaxCapResponse;
import com.spectra.fieldforce.model.gpon.response.IrInfoResponse;
import com.spectra.fieldforce.R;
import com.spectra.fieldforce.api.ApiClient;
import com.spectra.fieldforce.api.ApiInterface;
import com.spectra.fieldforce.model.gpon.response.WcrResponse;
import com.spectra.fieldforce.utils.Constants;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class IRFragment  extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener{
    IrFragmentBinding binding;
    private ArrayList<IrInfoResponse.ItemConsumtion> itemConsumtions;
     ArrayList<IrInfoResponse.InstallationItemList> installationItemLists;
    private ArrayList<String> irCpeMac;
    private ArrayList<String> IrType;
    private ArrayList<String> irCpeMacid;
    private String strCpe,IrStatusReport,strGuiID,strSegment,strCanId,str_provisionId,strholdId,strStatusofReport,straddition,attach,orderId;
    private ArrayList<String> holdCategory;
    private ArrayList<String> holdCategoryId;
    private AlphaAnimation inAnimation,outAnimation;
    private ArrayList<String> QualityParam;
    ArrayAdapter<String> adaptertype;
    ArrayAdapter<String> adaptercpe;
    LocationManager locationManager;
    Boolean IrStatus;
    String latitude, longitude,doa;
    private static final int REQUEST_LOCATION = 1;
    FusedLocationProviderClient mFusedLocationClient;
    int PERMISSION_ID = 44;
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
        binding.tvIrStatus.setText("IR Status:"+ IrStatusReport);
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
        adaptertype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.layoutGeneralDetails.spIrAttached.setAdapter(adaptertype);
        binding.tvApproval.setOnClickListener(view -> SubmitApproval());
        binding.etHoldCategory.setOnClickListener(v-> binding.spHoldCategory.performClick());
        binding.spHoldCategory.setOnItemSelectedListener(this);

        holdCategory = new ArrayList<String>();
        holdCategory.add("Hold Category");
        holdCategory.add("Customer Not Available");
        holdCategory.add("Server Room not available");
        holdCategory.add("Client IT infrastructure not ready");
        holdCategory.add("IT Person not available");
        holdCategory.add("Power Not available");
        holdCategory.add("Building ID incorrectly updated");
        holdCategory.add("Wrong commitment from Sales team");
        holdCategory.add("CRM related issues");
        holdCategory.add("Duplicate order");
        holdCategoryId = new ArrayList<String>();
        holdCategoryId.add("569480000");
        holdCategoryId.add("569480001");
        holdCategoryId.add("569480002");
        holdCategoryId.add("569480003");
        holdCategoryId.add("569480004");
        holdCategoryId.add("569480005");
        holdCategoryId.add("569480006");
        holdCategoryId.add("569480007");
        holdCategoryId.add("569480008");
        holdCategoryId.add("569480009");
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, holdCategory);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spHoldCategory.setAdapter(adapter2);

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
    }

    private void SubmitApproval(){
        inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        binding.progressLayout.progressOverlay.setAnimation(inAnimation);
        binding.progressLayout.progressOverlay.setVisibility(View.VISIBLE);
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
                    outAnimation = new AlphaAnimation(1f, 0f);
                    outAnimation.setDuration(200);
                    binding.progressLayout.progressOverlay.setAnimation(outAnimation);
                    binding.progressLayout.progressOverlay.setVisibility(View.GONE);
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

        QualityParam = new ArrayList<String>();
        QualityParam.add("Select Type");
        QualityParam.add("Yes");
        QualityParam.add("No");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParam);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.layoutInstallationparam.spDns.setAdapter(adapter);
        binding.layoutInstallationparam.spEducationAntivirus.setAdapter(adapter);
        binding.layoutInstallationparam.spIp.setAdapter(adapter);
        binding.layoutInstallationparam.spMrtg.setAdapter(adapter);
        binding.layoutInstallationparam.spSpeedTest.setAdapter(adapter);
        binding.layoutInstallationparam.spSelfcare.setAdapter(adapter);

        binding.layoutCpemac.etCpeMacShared.setOnClickListener(v->  binding.layoutCpemac.spCpeMacShared.performClick());
        binding.layoutCpemac.spCpeMacShared.setOnItemSelectedListener(this);
        irCpeMac = new ArrayList<String>();
        irCpeMacid = new ArrayList<String>();
        irCpeMac.add("Select CPE Type");
        irCpeMac.add("Completed");
        irCpeMacid.add("111260000");
        adaptercpe = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, irCpeMac);
        adaptercpe.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.layoutCpemac.spCpeMacShared.setAdapter(adaptercpe);

        binding.tvResendOtp.setOnClickListener(v -> resendCode());
        binding.layoutCpemac.tvCustSave.setOnClickListener(v -> {
            String cpe = Objects.requireNonNull(binding.layoutGeneralDetails.etIrAttached.getText()).toString();
            if(cpe.equals(" Select CPE Type")){
                Toast.makeText(getContext(), "Please  Select CPE Type", Toast.LENGTH_LONG).show();
            }else {
                updateCpeMac();
            }


        });

        binding.tvIrComplete.setOnClickListener(v -> {
            String remark = Objects.requireNonNull(binding.etRemarksText.getText()).toString();
            if(remark.isEmpty()){
                Toast.makeText(getContext(),"Please Enter Remark",Toast.LENGTH_LONG).show();
            }else{
                getLastLocation();
               /* locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    OnGPS();
                } else {
                    getLocation();*/
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
            if(insta.isEmpty()){
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
            if(dns.isEmpty()){
                Toast.makeText(getContext(), "Please Select  DNS", Toast.LENGTH_LONG).show();
            }if(virus.isEmpty()){
                Toast.makeText(getContext(), "Please Select  Virus", Toast.LENGTH_LONG).show();
            }if(ip.isEmpty()){
                Toast.makeText(getContext(), "Please Select IP", Toast.LENGTH_LONG).show();
            }if(mrtg.isEmpty()){
                Toast.makeText(getContext(), "Please Select  MRTG", Toast.LENGTH_LONG).show();
            }if(speed.isEmpty()){
                Toast.makeText(getContext(), "Please Select Speed test results shown to coustomer", Toast.LENGTH_LONG).show();
            }if(selfcare.isEmpty()){
                Toast.makeText(getContext(), "Please Select Education customer Regarding Selfcare Portal", Toast.LENGTH_LONG).show();
            }else{
                updateQualityParam(dns,virus,mrtg,ip,speed,selfcare);
            }
        });
    }

    private void updateHoldCategoryStatus(){
        inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        binding.progressLayout.progressOverlay.setAnimation(inAnimation);
        binding.progressLayout.progressOverlay.setVisibility(View.VISIBLE);
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
                    outAnimation = new AlphaAnimation(1f, 0f);
                    outAnimation.setDuration(200);
                    binding.progressLayout.progressOverlay.setAnimation(outAnimation);
                    binding.progressLayout.progressOverlay.setVisibility(View.GONE);
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

    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new  DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(
                getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS != null) {
                double lat = locationGPS.getLatitude();
                double longi = locationGPS.getLongitude();
                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);

                // showLocation.setText("Your Location: " + "\n" + "Latitude: " + latitude + "\n" + "Longitude: " + longitude);
            } else {
                Toast.makeText(getActivity(), latitude+longitude, Toast.LENGTH_SHORT).show();
            }
        }
    }




    public void resendCode() {
        inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        binding.progressLayout.progressOverlay.setAnimation(inAnimation);
        binding.progressLayout.progressOverlay.setVisibility(View.VISIBLE);
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
                    outAnimation = new AlphaAnimation(1f, 0f);
                    outAnimation.setDuration(200);
                    binding.progressLayout.progressOverlay.setAnimation(outAnimation);
                    binding.progressLayout.progressOverlay.setVisibility(View.GONE);
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
        inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        binding.progressLayout.progressOverlay.setAnimation(inAnimation);
        binding.progressLayout.progressOverlay.setVisibility(View.VISIBLE);
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
                    outAnimation = new AlphaAnimation(1f, 0f);
                    outAnimation.setDuration(200);
                    binding.progressLayout.progressOverlay.setAnimation(outAnimation);
                    binding.progressLayout.progressOverlay.setVisibility(View.GONE);
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
        inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        binding.progressLayout.progressOverlay.setAnimation(inAnimation);
        binding.progressLayout.progressOverlay.setVisibility(View.VISIBLE);
        AccountInfoRequest accountInfoRequest = new AccountInfoRequest();
        accountInfoRequest.setAuthkey(Constants.AUTH_KEY);
        accountInfoRequest.setAction(Constants.GET_IR_INFO);
        accountInfoRequest.setCanId(strCanId);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<IrInfoResponse> call = apiService.getIrInfo(accountInfoRequest);
        call.enqueue(new Callback<IrInfoResponse>() {
            @Override
            public void onResponse(Call<IrInfoResponse> call, Response<IrInfoResponse> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                    outAnimation = new AlphaAnimation(1f, 0f);
                    outAnimation.setDuration(200);
                    binding.progressLayout.progressOverlay.setAnimation(outAnimation);
                    binding.progressLayout.progressOverlay.setVisibility(View.GONE);
                    if(response.body().getStatus().equals("Success")) {
                    try {
                        binding.layoutCustomerDetails.setCustomerDetails(response.body().getResponse().getIr());
                        binding.setIR(response.body().getResponse().getIr());
                        strGuiID = response.body().getResponse().getIr().getIrguid();
                        strSegment = response.body().getResponse().getIr().getBusinessSegment();
                        str_provisionId = response.body().getResponse().getGeneral().getProvisionId();
                        String strHold = response.body().getResponse().getIr().getShowHold();
                        if(strHold.equals("true")){
                            binding.linea18.setVisibility(View.VISIBLE);
                        }else{
                            binding.linea18.setVisibility(View.GONE);
                        }
                        itemConsumtions = (ArrayList<IrInfoResponse.ItemConsumtion>) response.body().getResponse().getItemConsumtionList();
                        binding.layoutItemcousumption.rvIrItemlist.setHasFixedSize(true);
                        binding.layoutItemcousumption.rvIrItemlist.setLayoutManager(new LinearLayoutManager(getActivity()));

                        if(itemConsumtions.size()!=0){
                            binding.layoutItemcousumption.rvIrItemlist.setAdapter(new IRItemConsumptionListAdapter(getActivity(),itemConsumtions,IrStatusReport));
                        }
                        installationItemLists = (ArrayList<IrInfoResponse.InstallationItemList>) response.body().getResponse().getInstallationItemList();
                        binding.layoutAddEquipment.rvAddEquipment.setHasFixedSize(true);
                        binding.layoutAddEquipment.rvAddEquipment.setLayoutManager(new LinearLayoutManager(getActivity()));
                        if(installationItemLists.size()!=0){
                            binding.layoutAddEquipment.rvAddEquipment.setAdapter(new IrEquipmentConsumpAdapter(getActivity(),installationItemLists,IrStatusReport));
                        }
                        straddition = installationItemLists.get(0).getItemType();
                        if(straddition.equals("Additional")&& strSegment.equals("Home")){
                            binding.tvApproval.setVisibility(View.VISIBLE);
                        }
                        doa = response.body().getResponse().getIr().getDOAFlag();
                        if(doa.equals("No")&& straddition.equals("Additional")){
                            binding.tvApproval.setVisibility(View.VISIBLE);
                        }
                        binding.layoutGeneralDetails.setGeneralDetails(response.body().getResponse().getGeneral());
                        binding.layoutEnggDetails.setEngineer(response.body().getResponse().getEngineer());
                        binding.setHold(response.body().getResponse().getEngineer());
                        String cpe = response.body().getResponse().getIr().getMACShared();
                        if(cpe!=null&& !cpe.equals("")){
                            irCpeMac = new ArrayList<String>();
                            irCpeMacid = new ArrayList<String>();
                            irCpeMac.add("Completed");
                            irCpeMacid.add("111260000");
                            irCpeMac.add("Select CPE Type");
                            adaptercpe = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, irCpeMac);
                            adaptercpe.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            binding.layoutCpemac.spCpeMacShared.setAdapter(adaptercpe);
                        }
                        attach = response.body().getResponse().getGeneral().getIRAttached();
                        if(attach!=null){
                            if(response.body().getResponse().getGeneral().getIRAttached().equals("1")){
                               // IrType.clear();
                                IrType = new ArrayList<String>();
                                IrType.add("Yes");
                                IrType.add("Select IR Type");
                                IrType.add("No");
                                ArrayAdapter<String> adaptertype = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, IrType);
                                adaptertype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                binding.layoutGeneralDetails.spIrAttached.setAdapter(adaptertype);
                            }else  if(response.body().getResponse().getGeneral().getIRAttached().equals("0")){
                               // IrType.clear();
                                IrType = new ArrayList<String>();
                                IrType.add("No");
                                IrType.add("Yes");
                                IrType.add("Select IR Type");
                                ArrayAdapter<String> adaptertype = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, IrType);
                                adaptertype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                binding.layoutGeneralDetails.spIrAttached.setAdapter(adaptertype);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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

    private void nextScreen(){
        Intent i = new Intent(getActivity(), ProvisioningMainActivity.class);
        i.putExtra("canId", strCanId);
        i.putExtra("StatusofReport", strStatusofReport);
        i.putExtra("OrderId", orderId);
      //  i.putExtra("IrStatusReport",IrStatusReport);

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
            binding.layoutInstallationparam.etSpeedTest.setText(QualityParam.get(position));
        }
        else if(parent.getId() == R.id.sp_education_antivirus) {
            binding.layoutInstallationparam.etEducationAntivirus.setText(QualityParam.get(position));
        }
        else if(parent.getId() == R.id.sp_selfcare) {
            binding.layoutInstallationparam.etSelfcare.setText(QualityParam.get(position));
        }
        else if(parent.getId() == R.id.sp_dns) {
            binding.layoutInstallationparam.etDns.setText(QualityParam.get(position));
        }
        else if(parent.getId() == R.id.sp_mrtg) {
            binding.layoutInstallationparam.etMrtg.setText(QualityParam.get(position));
        }
        else if(parent.getId() == R.id.sp_ip) {
            binding.layoutInstallationparam.etIp.setText(QualityParam.get(position));
        }  else if(parent.getId() == R.id.sp_ir_attached) {
            binding.layoutGeneralDetails.etIrAttached.setText(IrType.get(position));
        }



    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void updateGeneralDetails(String ipattach) {
        inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        binding.progressLayout.progressOverlay.setAnimation(inAnimation);
        binding.progressLayout.progressOverlay.setVisibility(View.VISIBLE);
        //String ir;
      //  ir = binding.layoutGeneralDetails.etIrAttached.getText().toString();
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
                    outAnimation = new AlphaAnimation(1f, 0f);
                    outAnimation.setDuration(200);
                    binding.progressLayout.progressOverlay.setAnimation(outAnimation);
                    binding.progressLayout.progressOverlay.setVisibility(View.GONE);
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
        inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        binding.progressLayout.progressOverlay.setAnimation(inAnimation);
        binding.progressLayout.progressOverlay.setVisibility(View.VISIBLE);
        UpdateIREngineer updateIREngineer = new UpdateIREngineer();
        updateIREngineer.setAuthkey(Constants.AUTH_KEY);
        updateIREngineer.setAction(Constants.ENGINER_UPDATE);
        updateIREngineer.setEngName(binding.layoutEnggDetails.etEnggName.getText().toString());
        updateIREngineer.setInstattionDate(insta);
        updateIREngineer.setIRguid(strGuiID);
        updateIREngineer.setInstattionDate("");
        updateIREngineer.setOTP(binding.layoutEnggDetails.etInstallationCode.getText().toString());
        updateIREngineer.setAppointmentDate("");
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonClassResponse> call = apiService.updateIrEnginer(updateIREngineer);
        call.enqueue(new Callback<CommonClassResponse>() {
            @Override
            public void onResponse(Call<CommonClassResponse> call, Response<CommonClassResponse> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                    outAnimation = new AlphaAnimation(1f, 0f);
                    outAnimation.setDuration(200);
                    binding.progressLayout.progressOverlay.setAnimation(outAnimation);
                    binding.progressLayout.progressOverlay.setVisibility(View.GONE);
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
        // check if permissions are given
        if (checkPermissions()) {

            // check if location is enabled
            if (isLocationEnabled()) {

                // getting last
                // location from
                // FusedLocationClient
                // object
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
            // if permissions aren't available,
            // request for permissions
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(2);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
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

    // method to check for permissions
    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        // If we want background location
        // on Android 10.0 and higher,
        // use:
        // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    // method to request for permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    // method to check
    // if location is enabled
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    // If everything is alright then
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
        inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        binding.progressLayout.progressOverlay.setAnimation(inAnimation);
        binding.progressLayout.progressOverlay.setVisibility(View.VISIBLE);
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
                    outAnimation = new AlphaAnimation(1f, 0f);
                    outAnimation.setDuration(200);
                    binding.progressLayout.progressOverlay.setAnimation(outAnimation);
                    binding.progressLayout.progressOverlay.setVisibility(View.GONE);
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
        inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        binding.progressLayout.progressOverlay.setAnimation(inAnimation);
        binding.progressLayout.progressOverlay.setVisibility(View.VISIBLE);
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
        irCompleteRequest.setSource("App");
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonClassResponse> call = apiService.updateIR(irCompleteRequest);
        call.enqueue(new Callback<CommonClassResponse>() {
            @Override
            public void onResponse(Call<CommonClassResponse> call, Response<CommonClassResponse> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                    outAnimation = new AlphaAnimation(1f, 0f);
                    outAnimation.setDuration(200);
                    binding.progressLayout.progressOverlay.setAnimation(outAnimation);
                    binding.progressLayout.progressOverlay.setVisibility(View.GONE);
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
        inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        binding.progressLayout.progressOverlay.setAnimation(inAnimation);
        binding.progressLayout.progressOverlay.setVisibility(View.VISIBLE);
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
                    outAnimation = new AlphaAnimation(1f, 0f);
                    outAnimation.setDuration(200);
                    binding.progressLayout.progressOverlay.setAnimation(outAnimation);
                    binding.progressLayout.progressOverlay.setVisibility(View.GONE);
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
