package com.spectra.fieldforce.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.spectra.fieldforce.activity.BucketTabActivity;
import com.spectra.fieldforce.adapter.IRItemConsumptionListAdapter;
import com.spectra.fieldforce.adapter.IrEquipmentConsumpAdapter;
import com.spectra.fieldforce.adapter.WcrEquipmentConsumpAdapter;
import com.spectra.fieldforce.databinding.IrFragmentBinding;
import com.spectra.fieldforce.databinding.WcrFragmentBinding;
import com.spectra.fieldforce.model.gpon.request.AccountInfoRequest;
import com.spectra.fieldforce.model.gpon.request.HoldWcrRequest;
import com.spectra.fieldforce.model.gpon.request.IRCompleteRequest;
import com.spectra.fieldforce.model.gpon.request.ResendActivationCodeRequest;
import com.spectra.fieldforce.model.gpon.request.ResendCodeIRRequest;
import com.spectra.fieldforce.model.gpon.request.UpdateCpeMacRequest;
import com.spectra.fieldforce.model.gpon.request.UpdateGeneralDetails;
import com.spectra.fieldforce.model.gpon.request.UpdateIREngineer;
import com.spectra.fieldforce.model.gpon.request.UpdateWcrEnggRequest;
import com.spectra.fieldforce.model.gpon.response.CommonClassResponse;
import com.spectra.fieldforce.model.gpon.response.IrInfoResponse;
import com.spectra.fieldforce.R;
import com.spectra.fieldforce.api.ApiClient;
import com.spectra.fieldforce.api.ApiInterface;
import com.spectra.fieldforce.model.gpon.response.WcrResponse;
import com.spectra.fieldforce.utils.Constants;

import java.util.ArrayList;
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
    private String strCpe,strGuiID,strSegment,strCanId,str_provisionId,strholdId,strStatusofReport;
    private ArrayList<String> holdCategory;
    private ArrayList<String> holdCategoryId;
    private AlphaAnimation inAnimation;
    private AlphaAnimation outAnimation;

    public IRFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = IrFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        strCanId = requireArguments().getString("canId");
      /*  strStatusofReport = requireArguments().getString("StatusofReport");
        binding.tvConsumptionStatus.setText("Consumption Status: " + strStatusofReport );*/
        binding.searchtoolbar.rlBack.setOnClickListener(this);
        binding.searchtoolbar.tvLang.setText("IR");
        getIrInfo();
        init();
        setType();
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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, IrType);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.layoutGeneralDetails.spIrAttached.setAdapter(adapter);

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
    }


    private void setData(){

        binding.layoutAddEquipment.btnItemEqipment.setOnClickListener(v -> {
            @SuppressLint("UseRequireInsteadOfGet") FragmentTransaction t1= Objects.requireNonNull(this.getFragmentManager()).beginTransaction();
            IREquipmentConsumption irEquipmentConsumption = new IREquipmentConsumption();
            Bundle bundle = new Bundle();
            bundle.putString("strGuuId", strGuiID);
            bundle.putString("canId",strCanId);
        //    bundle.putString("StatusofReport",strStatusofReport);
            t1.replace(R.id.frag_container, irEquipmentConsumption);
            irEquipmentConsumption.setArguments(bundle);
            t1.commit();
        });

        binding.layoutCpemac.etCpeMacShared.setOnClickListener(v->  binding.layoutCpemac.spCpeMacShared.performClick());
        binding.layoutCpemac.spCpeMacShared.setOnItemSelectedListener(this);
        irCpeMac = new ArrayList<String>();
        irCpeMacid = new ArrayList<String>();
        irCpeMac.add("Select CPE Type");
        irCpeMac.add("Completed");
        irCpeMacid.add("111260000");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, irCpeMac);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.layoutCpemac.spCpeMacShared.setAdapter(adapter);

        binding.tvResendOtp.setOnClickListener(v -> resendCode());
        binding.layoutCpemac.tvCustSave.setOnClickListener(v -> updateCpeMac());
        binding.tvIrComplete.setOnClickListener(v -> {
            String remark = binding.etRemarksText.getText().toString();
            if(remark.isEmpty()){
                Toast.makeText(getContext(),"Please Enter Remark",Toast.LENGTH_LONG).show();
            }else{
                updateIR(remark);
            }


        });
        binding.saveHold.setOnClickListener(v -> updateHoldCategory());

        binding.layoutGeneralDetails.tvGeneralDetailsSave.setOnClickListener(v -> updateGeneralDetails());
        binding.layoutEnggDetails.saveEnggDetails.setOnClickListener(v -> updateIrEnginer());

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
                    //irstatusofreport
                    binding.progressLayout.progressOverlay.setAnimation(outAnimation);
                    binding.progressLayout.progressOverlay.setVisibility(View.GONE);
                    if(response.body().getStatus().equals("Success")) {
                    try {
                        binding.layoutCustomerDetails.setCustomerDetails(response.body().getResponse().getIr());
                        binding.setIR(response.body().getResponse().getIr());
                        strGuiID = response.body().getResponse().getIr().getIrguid();
                        strSegment = response.body().getResponse().getIr().getBusinessSegment();
                        str_provisionId = response.body().getResponse().getGeneral().getProvisionId();
                        itemConsumtions = (ArrayList<IrInfoResponse.ItemConsumtion>) response.body().getResponse().getItemConsumtionList();
                        binding.layoutItemcousumption.rvIrItemlist.setHasFixedSize(true);
                        binding.layoutItemcousumption.rvIrItemlist.setLayoutManager(new LinearLayoutManager(getActivity()));
                        if(itemConsumtions.size()!=0){
                            binding.layoutItemcousumption.rvIrItemlist.setAdapter(new IRItemConsumptionListAdapter(getActivity(),itemConsumtions));
                        }
                        installationItemLists = (ArrayList<IrInfoResponse.InstallationItemList>) response.body().getResponse().getInstallationItemList();
                        binding.layoutAddEquipment.rvAddEquipment.setHasFixedSize(true);
                        binding.layoutAddEquipment.rvAddEquipment.setLayoutManager(new LinearLayoutManager(getActivity()));
                        if(installationItemLists.size()!=0){
                            binding.layoutAddEquipment.rvAddEquipment.setAdapter(new IrEquipmentConsumpAdapter(getActivity(),installationItemLists));
                        }
                        binding.layoutGeneralDetails.setGeneralDetails(response.body().getResponse().getGeneral());
                        binding.layoutEnggDetails.setEngineer(response.body().getResponse().getEngineer());
                        binding.setHold(response.body().getResponse().getEngineer());

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
        @SuppressLint("UseRequireInsteadOfGet") FragmentTransaction t= Objects.requireNonNull(this.getFragmentManager()).beginTransaction();
        ProvisioningTabFragment provisioningScreenFragment = new ProvisioningTabFragment();
        Bundle accountinfo = new Bundle();
        accountinfo.putString("canId", strCanId);
      //  accountinfo.putString("StatusofReport",strStatusofReport);
        t.replace(R.id.frag_container, provisioningScreenFragment);
        provisioningScreenFragment.setArguments(accountinfo);
        t.commit();
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
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void updateGeneralDetails() {
        inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        binding.progressLayout.progressOverlay.setAnimation(inAnimation);
        binding.progressLayout.progressOverlay.setVisibility(View.VISIBLE);
        String ir;
        ir = binding.layoutGeneralDetails.etIrAttached.getText().toString();
        if(ir.equals("Yes")){
            ir = "1";
        }else{
            ir="0";
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
        updateGeneralDetails.setIRAttached(ir);
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
                        if(response.body().getResponse().getStatusCode()==200){
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

    private void updateIrEnginer(){
        inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        binding.progressLayout.progressOverlay.setAnimation(inAnimation);
        binding.progressLayout.progressOverlay.setVisibility(View.VISIBLE);
        UpdateIREngineer updateIREngineer = new UpdateIREngineer();
        updateIREngineer.setAuthkey(Constants.AUTH_KEY);
        updateIREngineer.setAction(Constants.ENGINER_UPDATE);
        updateIREngineer.setEngName(binding.layoutEnggDetails.etEnggName.getText().toString());
        updateIREngineer.setInstattionDate(binding.layoutEnggDetails.etInstallationCode.getText().toString());
        updateIREngineer.setIRguid(strGuiID);
        updateIREngineer.setInstattionDate("");
        updateIREngineer.setOTP(binding.layoutEnggDetails.etInstallationCode.getText().toString());
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
                        if(response.body().getResponse().getStatusCode()==200){
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
    //    accountinfo.putString("StatusofReport",strStatusofReport);
        t.replace(R.id.frag_container, irFragment);
        irFragment.setArguments(accountinfo);
        t.commit();
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
                            Toast.makeText(getContext(),"Something went wrong..",Toast.LENGTH_LONG).show();
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

    private void updateIR(String remark){
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
