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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.spectra.fieldforce.R;
import com.spectra.fieldforce.activity.BucketTabActivity;
import com.spectra.fieldforce.adapter.WcrEquipmentConsumpAdapter;
import com.spectra.fieldforce.adapter.WcrCompletteItemConsumptionListAdapter;
import com.spectra.fieldforce.databinding.WcrCompleteFragmentBinding;
import com.spectra.fieldforce.model.CommonResponse;
import com.spectra.fieldforce.model.gpon.request.AccountInfoRequest;
import com.spectra.fieldforce.model.gpon.request.HoldWcrRequest;
import com.spectra.fieldforce.model.gpon.request.ResendActivationCodeRequest;
import com.spectra.fieldforce.model.gpon.request.UpdateQualityParamRequest;
import com.spectra.fieldforce.model.gpon.request.UpdateWcrEnggRequest;
import com.spectra.fieldforce.model.gpon.request.WcrCompleteRequest;
import com.spectra.fieldforce.model.gpon.response.CommonClassResponse;
import com.spectra.fieldforce.api.ApiClient;
import com.spectra.fieldforce.api.ApiInterface;
import com.spectra.fieldforce.model.gpon.response.WcrResponse;
import com.spectra.fieldforce.utils.Constants;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WcrCompletedFragment extends Fragment implements AdapterView.OnItemSelectedListener,View.OnClickListener{
    private WcrCompleteFragmentBinding binding;
    private String strGuuId,strSegment,strholdId,strProductSegment;
    private ArrayList<WcrResponse.ItemConsumtion> itemConsumtions;
    private ArrayList<WcrResponse.EquipmentDetailsList> equipmentDetailsLists;
    private ArrayList<String> holdCategory;
    private ArrayList<String> holdCategoryId;
    private ArrayList<String> qualityParam;
    private ArrayList<String> qualityParamId;
    private String strSpeedTest,strCableCrimped,strFacePlate,streOntLogin,strWifiSsid,strEducation,strAntivirus,strCanId,strStatusofReport;
    private AlphaAnimation inAnimation;
    private AlphaAnimation outAnimation;
    public WcrCompletedFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = WcrCompleteFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.rl_back) {
            nextScreen();
        }
    }


    private void moveNext(){
        @SuppressLint("UseRequireInsteadOfGet") FragmentTransaction t= Objects.requireNonNull(this.getFragmentManager()).beginTransaction();
        WcrCompletedFragment wcrCompletedFragment = new WcrCompletedFragment();
        Bundle accountinfo = new Bundle();
        accountinfo.putString("canId", strCanId);
        accountinfo.putString("StatusofReport",strStatusofReport);
        t.replace(R.id.frag_container, wcrCompletedFragment);
        wcrCompletedFragment.setArguments(accountinfo);
        t.commit();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        strCanId = requireArguments().getString("canId");
        strStatusofReport = requireArguments().getString("StatusofReport");
        binding.tvConsumptionStatus.setText("Consumption Status: " + strStatusofReport );
      getWcrInfo();
      Listener();


      hold();
      qualityParam();
        binding.searchtoolbar.rlBack.setOnClickListener(this);
        binding.searchtoolbar.tvLang.setText("WCR Complete");
      binding.tvResendService.setOnClickListener(v -> {
          resendCode("Activation");
      });
        binding.tvResendInstallation.setOnClickListener(v -> {
            resendCode("Installation");
        });
    }

    public void getWcrInfo() {
        inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        binding.progressLayout.progressOverlay.setAnimation(inAnimation);
        binding.progressLayout.progressOverlay.setVisibility(View.VISIBLE);
        AccountInfoRequest accountInfoRequest = new AccountInfoRequest();
        accountInfoRequest.setAuthkey(Constants.AUTH_KEY);
        accountInfoRequest.setAction(Constants.GET_WCR_INFO);
        accountInfoRequest.setCanId(strCanId);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<WcrResponse> call = apiService.getWcrInfo(accountInfoRequest);
        call.enqueue(new Callback<WcrResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<WcrResponse> call, Response<WcrResponse> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                    outAnimation = new AlphaAnimation(1f, 0f);
                    outAnimation.setDuration(200);
                    binding.progressLayout.progressOverlay.setAnimation(outAnimation);
                    binding.progressLayout.progressOverlay.setVisibility(View.GONE);
                    if(response.body().getStatus().equals("Success")) {
                        try {
                            binding.setStatus(response.body().getResponse().getWcr());
                            itemConsumtions = response.body().getResponse().getItemConsumtionList();
                            binding.layoutItemconsumption.rvWcrItemlist.setHasFixedSize(true);
                            binding.layoutItemconsumption.rvWcrItemlist.setLayoutManager(new LinearLayoutManager(getActivity()));
                            binding.layoutItemconsumption.rvWcrItemlist.setAdapter(new WcrCompletteItemConsumptionListAdapter(getActivity(), itemConsumtions));
                            equipmentDetailsLists = response.body().getResponse().getEquipmentDetailsList();
                            binding.layoutWcrEquipmentdetails.rvEquipment.setHasFixedSize(true);
                            binding.layoutWcrEquipmentdetails.rvEquipment.setLayoutManager(new LinearLayoutManager(getActivity()));
                            binding.layoutWcrEquipmentdetails.rvEquipment.setAdapter(new WcrEquipmentConsumpAdapter(getActivity(), equipmentDetailsLists));
                            binding.layoutWcrcustomerNetwork.setCustomerNetwork(response.body().getResponse().getCusotmerNetwork());
                            strGuuId = response.body().getResponse().getWcr().getWcrguidid();
                            strSegment = response.body().getResponse().getWcr().getBusinessSegment();
                            strProductSegment = response.body().getResponse().getWcr().getProductSegment();
                            listener();
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }else if(response.body().getStatus().equals("Failure")){
                        Toast.makeText(getContext(),"No Data Available.",Toast.LENGTH_LONG).show();
                        nextScreen();
                    }
                }
            }
            @Override
            public void onFailure(Call<WcrResponse> call, Throwable t) {
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
        t.replace(R.id.frag_container, provisioningScreenFragment);
        provisioningScreenFragment.setArguments(accountinfo);
        t.commit();
    }


    private void listener(){
        if(strSegment.equals("Business")){
            binding.linearFour.setVisibility(View.VISIBLE);
            binding.linearNine.setVisibility(View.VISIBLE);
            binding.linearSix.setVisibility(View.GONE);
            binding.linea11.setVisibility(View.GONE);
            binding.linea13.setVisibility(View.GONE);
            binding.linea15.setVisibility(View.VISIBLE);
            binding.linea18.setVisibility(View.GONE);
            binding.linea20.setVisibility(View.VISIBLE);
        }else if(strSegment.equals("Home")){
            binding.linearFour.setVisibility(View.VISIBLE);
            binding.linea11.setVisibility(View.VISIBLE);
            binding.linearNine.setVisibility(View.GONE);
            binding.linearSix.setVisibility(View.GONE);
            binding.linearFive.setVisibility(View.GONE);
            binding.linea13.setVisibility(View.VISIBLE);
            binding.linea15.setVisibility(View.VISIBLE);
            binding.linea18.setVisibility(View.VISIBLE);
            binding.linea20.setVisibility(View.VISIBLE);
        }
    }




    private void Listener(){
        binding.etHoldCategory.setOnClickListener(v-> binding.spHoldCategory.performClick());
        binding.spHoldCategory.setOnItemSelectedListener(this);
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
        binding.layoutWcrengineer.saveEnggDetails.setOnClickListener(v -> updateWcrEnginer());
        binding.saveHold.setOnClickListener(v -> {
            String remark = binding.etRemarksText.getText().toString();
            if(remark.isEmpty()){
                Toast.makeText(getContext(),"Please Enter Remark",Toast.LENGTH_LONG).show();
            }else{
               updateHoldCategory(remark);
            }
        });
        binding.tvWcrComplete.setOnClickListener(v -> updateWcrComplete());
        binding.layoutInstallationquality.tvSaveQualityParam.setOnClickListener(v -> updateQualityParam());
    }


    public void resendCode(String type) {
        inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        binding.progressLayout.progressOverlay.setAnimation(inAnimation);
        binding.progressLayout.progressOverlay.setVisibility(View.VISIBLE);
        ResendActivationCodeRequest resendActivationCodeRequest = new ResendActivationCodeRequest();
        resendActivationCodeRequest.setAuthkey(Constants.AUTH_KEY);
        resendActivationCodeRequest.setAction(Constants.RESEND_CODE);
       resendActivationCodeRequest.setGUID(strGuuId);
       resendActivationCodeRequest.setIsIR("false");
       resendActivationCodeRequest.setType(type);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonClassResponse> call = apiService.resendCodeWcr(resendActivationCodeRequest);
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
                            Toast.makeText(getContext(),response.message(),Toast.LENGTH_LONG).show();
                            moveNext();
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

    private void qualityParam(){
        binding.layoutInstallationquality.etSpeedTest.setOnClickListener(v->  binding.layoutInstallationquality.spSpeedTest.performClick());
        binding.layoutInstallationquality.spSpeedTest.setOnItemSelectedListener(this);
        binding.layoutInstallationquality.etCableCrimped.setOnClickListener(v->  binding.layoutInstallationquality.spCableCrimped.performClick());
        binding.layoutInstallationquality.spCableCrimped.setOnItemSelectedListener(this);
        binding.layoutInstallationquality.etFacePlate.setOnClickListener(v->  binding.layoutInstallationquality.spFacePlate.performClick());
        binding.layoutInstallationquality.spFacePlate.setOnItemSelectedListener(this);
        binding.layoutInstallationquality.etOntLogin.setOnClickListener(v->  binding.layoutInstallationquality.spOntLogin.performClick());
        binding.layoutInstallationquality.spOntLogin.setOnItemSelectedListener(this);
        binding.layoutInstallationquality.etWifiSsid.setOnClickListener(v->  binding.layoutInstallationquality.spWifiSsid.performClick());
        binding.layoutInstallationquality.spWifiSsid.setOnItemSelectedListener(this);
        binding.layoutInstallationquality.etEducationCustomer.setOnClickListener(v->  binding.layoutInstallationquality.spEducationCustomer.performClick());
        binding.layoutInstallationquality.spEducationCustomer.setOnItemSelectedListener(this);
        binding.layoutInstallationquality.etEducationAntivirus.setOnClickListener(v->  binding.layoutInstallationquality.spEducationAntivirus.performClick());
        binding.layoutInstallationquality.spEducationAntivirus.setOnItemSelectedListener(this);
        qualityParam = new ArrayList<String>();
        qualityParam.add("Select Quality Type");
        qualityParam.add("Yes");
        qualityParam.add("No");
        qualityParamId = new ArrayList<String>();
        qualityParamId.add("111260000");
        qualityParamId.add("111260001");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, qualityParam);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.layoutInstallationquality.spSpeedTest.setAdapter(adapter);
        binding.layoutInstallationquality.spCableCrimped.setAdapter(adapter);
        binding.layoutInstallationquality.spFacePlate.setAdapter(adapter);
        binding.layoutInstallationquality.spOntLogin.setAdapter(adapter);
        binding.layoutInstallationquality.spWifiSsid.setAdapter(adapter);
        binding.layoutInstallationquality.spEducationCustomer.setAdapter(adapter);
        binding.layoutInstallationquality.spEducationAntivirus.setAdapter(adapter);
    }

    private void hold(){
        holdCategory = new ArrayList<String>();
        holdCategory.add("Hold Category");
        holdCategory.add("Building Permission Issue");
        holdCategory.add("Customer Site Not Ready");
        holdCategory.add("Delayed By Customer");
        holdCategory.add("Installation Report Pending To Sign");
        holdCategory.add("Local Permission Issue");
        holdCategory.add("Network Constraint");
        holdCategoryId = new ArrayList<String>();
        holdCategoryId.add("1");
        holdCategoryId.add("2");
        holdCategoryId.add("3");
        holdCategoryId.add("4");
        holdCategoryId.add("5");
        holdCategoryId.add("6");
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, holdCategory);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spHoldCategory.setAdapter(adapter2);
    }

    private void updateWcrEnginer(){
        inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        binding.progressLayout.progressOverlay.setAnimation(inAnimation);
        binding.progressLayout.progressOverlay.setVisibility(View.VISIBLE);
        UpdateWcrEnggRequest updateWcrEnggRequest = new UpdateWcrEnggRequest();
        updateWcrEnggRequest.setAuthkey(Constants.AUTH_KEY);
        updateWcrEnggRequest.setAction(Constants.UPDATE_WCR_ENGINER);
        updateWcrEnggRequest.setEngName(binding.layoutWcrengineer.etEnggName.getText().toString());
        updateWcrEnggRequest.setInstcode(binding.layoutWcrengineer.etInstallationCode.getText().toString());
        updateWcrEnggRequest.setWCRguidId(strGuuId);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonResponse> call = apiService.wcrEnggDetails(updateWcrEnggRequest);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                    outAnimation = new AlphaAnimation(1f, 0f);
                    outAnimation.setDuration(200);
                    binding.progressLayout.progressOverlay.setAnimation(outAnimation);
                    binding.progressLayout.progressOverlay.setVisibility(View.GONE);
                    try {
                        if(response.body().getStatus().equals("Success")){
                            Toast.makeText(getContext(),response.message(),Toast.LENGTH_LONG).show();
                            moveNext();
                        }else{
                            Toast.makeText(getContext(),response.body().getResponse(),Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                binding.progressLayout.progressOverlay.setVisibility(View.GONE);
                Log.e("RetroError", t.toString());
            }
        });

    }


    private void updateQualityParam(){
        inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        binding.progressLayout.progressOverlay.setAnimation(inAnimation);
        binding.progressLayout.progressOverlay.setVisibility(View.VISIBLE);
        UpdateQualityParamRequest updateQualityParamRequest = new UpdateQualityParamRequest();
        updateQualityParamRequest.setAuthkey(Constants.AUTH_KEY);
        updateQualityParamRequest.setAction(Constants.UPDATE_POST_HOLDIR_QUALITY);
        updateQualityParamRequest.setSpeed(binding.layoutInstallationquality.etSpeedTest.getText().toString());
        updateQualityParamRequest.setCable(binding.layoutInstallationquality.etCableCrimped.getText().toString());
        updateQualityParamRequest.setAntiVirus(binding.layoutInstallationquality.etEducationAntivirus.getText().toString());
        updateQualityParamRequest.setSelfCare(binding.layoutInstallationquality.etEducationCustomer.getText().toString());
        updateQualityParamRequest.setWifi(binding.layoutInstallationquality.etWifiSsid.getText().toString());
        updateQualityParamRequest.setFace(binding.layoutInstallationquality.etFacePlate.getText().toString());
        updateQualityParamRequest.setONT(binding.layoutInstallationquality.etOntLogin.getText().toString());
        updateQualityParamRequest.setWCRguidId(strGuuId);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonClassResponse> call = apiService.updateQualityParam(updateQualityParamRequest);
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
                            Toast.makeText(getContext(),response.message(),Toast.LENGTH_LONG).show();
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

    private void updateHoldCategory(String remark){
        inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        binding.progressLayout.progressOverlay.setAnimation(inAnimation);
        binding.progressLayout.progressOverlay.setVisibility(View.VISIBLE);
        HoldWcrRequest holdWcrRequest = new HoldWcrRequest();
        holdWcrRequest.setAuthkey(Constants.AUTH_KEY);
        holdWcrRequest.setAction(Constants.HOLD_WCR);
        holdWcrRequest.setCategory(strholdId);
        holdWcrRequest.setReason(remark);
        holdWcrRequest.setWCRguidId(strGuuId);
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
                            Toast.makeText(getContext(),response.message(),Toast.LENGTH_LONG).show();
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

    private void updateWcrComplete(){
        inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        binding.progressLayout.progressOverlay.setAnimation(inAnimation);
        binding.progressLayout.progressOverlay.setVisibility(View.VISIBLE);
        WcrCompleteRequest wcrCompleteRequest = new WcrCompleteRequest();
        wcrCompleteRequest.setAuthkey(Constants.AUTH_KEY);
        wcrCompleteRequest.setAction(Constants.WCR_COMPLETE);
        wcrCompleteRequest.setIsHold(strholdId);
        wcrCompleteRequest.setProductSegment(strProductSegment);
        wcrCompleteRequest.setSegment(strSegment);
        wcrCompleteRequest.setRemarks(binding.etRemarksText.getText().toString());
        wcrCompleteRequest.setWCRguidId(strGuuId);


        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonClassResponse> call = apiService.wcrComplete(wcrCompleteRequest);
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
                            Toast.makeText(getContext(),response.message(),Toast.LENGTH_LONG).show();
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId() == R.id.sp_hold_category){
            binding.etHoldCategory.setText(holdCategory.get(position));
            if (position != 0) strholdId = "" + holdCategoryId.get(position - 1);
            else strholdId = " ";
        }else  if(parent.getId() == R.id.sp_speed_test){
            binding.layoutInstallationquality.etSpeedTest.setText(qualityParam.get(position));
            if (position != 0) strSpeedTest = "" + qualityParamId.get(position - 1);
            else strSpeedTest = " ";
        }else  if(parent.getId() == R.id.sp_cable_crimped){
            binding.layoutInstallationquality.etCableCrimped.setText(qualityParam.get(position));
            if (position != 0) strCableCrimped = "" + qualityParamId.get(position - 1);
            else strCableCrimped = " ";
        }else  if(parent.getId() == R.id.sp_ont_login){
            binding.layoutInstallationquality.etOntLogin.setText(qualityParam.get(position));
            if (position != 0) streOntLogin = "" + qualityParamId.get(position - 1);
            else streOntLogin = " ";
        }else  if(parent.getId() == R.id.sp_face_plate){
            binding.layoutInstallationquality.etFacePlate.setText(qualityParam.get(position));
            if (position != 0) strFacePlate = "" + qualityParamId.get(position - 1);
            else strFacePlate = " ";
        }else  if(parent.getId() == R.id.sp_wifi_ssid){
            binding.layoutInstallationquality.etWifiSsid.setText(qualityParam.get(position));
            if (position != 0) strWifiSsid = "" + qualityParamId.get(position - 1);
            else strWifiSsid = " ";
        }else  if(parent.getId() == R.id.sp_education_customer){
            binding.layoutInstallationquality.etEducationCustomer.setText(qualityParam.get(position));
            if (position != 0) strEducation = "" + qualityParamId.get(position - 1);
            else strEducation = " ";
        }else  if(parent.getId() == R.id.sp_education_antivirus){
            binding.layoutInstallationquality.etEducationAntivirus.setText(qualityParam.get(position));
            if (position != 0) strAntivirus = "" + qualityParamId.get(position - 1);
            else strAntivirus = " ";
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}