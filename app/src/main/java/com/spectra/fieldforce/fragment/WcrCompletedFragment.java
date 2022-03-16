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
import com.spectra.fieldforce.activity.ProvisioningMainActivity;
import com.spectra.fieldforce.adapter.WcrAddManholeAdapter;
import com.spectra.fieldforce.adapter.WcrCConsumptionListAdapter;
import com.spectra.fieldforce.adapter.WcrCompletteItemConsumptionListAdapter;
import com.spectra.fieldforce.adapter.WcrEquipmentAdapter;
import com.spectra.fieldforce.adapter.WcrManholeAdapter;
import com.spectra.fieldforce.adapter.WcrServiceConsumptionListAdapter;
import com.spectra.fieldforce.adapter.WcrServiceListAdapter;
import com.spectra.fieldforce.api.ApiClient;
import com.spectra.fieldforce.api.ApiInterface;
import com.spectra.fieldforce.databinding.WcrCompleteFragmentBinding;
import com.spectra.fieldforce.databinding.WcrFragmentBinding;
import com.spectra.fieldforce.model.CommonMessageResponse;
import com.spectra.fieldforce.model.gpon.request.AccountInfoRequest;
import com.spectra.fieldforce.model.gpon.request.AssociatedResquest;
import com.spectra.fieldforce.model.gpon.request.HoldWcrRequest;
import com.spectra.fieldforce.model.gpon.request.ResendActivationCodeRequest;
import com.spectra.fieldforce.model.gpon.request.ResendNavRequest;
import com.spectra.fieldforce.model.gpon.request.UpdateFmsRequest;
import com.spectra.fieldforce.model.gpon.request.UpdateWcrEnggRequest;
import com.spectra.fieldforce.model.gpon.request.WcrCompleteRequest;
import com.spectra.fieldforce.model.gpon.response.CommonClassResponse;
import com.spectra.fieldforce.model.gpon.response.GetFmsListResponse;
import com.spectra.fieldforce.model.gpon.response.WcrResponse;
import com.spectra.fieldforce.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WcrCompletedFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener{
    private WcrCompleteFragmentBinding binding;

    private String strGuuId,strSegment, strfmsId,strSecFmsId,strCanId ,strholdId,strProductSegment,strStatusofReport,OrderId;
    private ArrayList<String> itemType;
    private ArrayList<WcrResponse.ManHoleDetails> manHoleDetails;
    private ArrayList<WcrResponse.ItemConsumtion> itemConsumtions;
    private ArrayList<WcrResponse.EquipmentDetailsList> equipmentDetailsLists;
    private ArrayList<WcrResponse.ServiceConsumtion> serviceConsumtions;
    private AlphaAnimation inAnimation,outAnimation;
    private ArrayList<String> QualityParamCable;
    private ArrayList<String> QualityParamLogin;
    private ArrayList<String> QualityParamCustomer;
    private ArrayList<String> QualityParamSpeed;
    private ArrayList<String> QualityParamEducation;
    private ArrayList<String> QualityParamWifi;
    private ArrayList<String> QualityParamFace;
    ArrayAdapter<String> adapterParamCable;
    ArrayAdapter<String> adapterParamLogin;
    ArrayAdapter<String> adapterParamCustomer;
    ArrayAdapter<String> adapterParamSpeed;
    ArrayAdapter<String> adapterParamEducation;
    ArrayAdapter<String> adapterParamWifi;
    ArrayAdapter<String> adapterParamFace;


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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.searchtoolbar.rlBack.setOnClickListener(this);
        binding.searchtoolbar.tvLang.setText("WCR");
        strCanId = requireArguments().getString("canId");
        strStatusofReport = requireArguments().getString("StatusofReport");
        binding.tvWcrStatus.setText("WCR Status : "+ strStatusofReport);
        OrderId = requireArguments().getString("OrderId");
        init();
        initOne();
        Lock();
        QualityParamCable = new ArrayList<String>();
        QualityParamLogin = new ArrayList<String>();
        QualityParamCustomer = new ArrayList<String>();
        QualityParamSpeed = new ArrayList<String>();
        QualityParamEducation = new ArrayList<String>();
        QualityParamWifi = new ArrayList<String>();
        QualityParamFace = new ArrayList<String>();
    }

    private void Lock(){
        binding.layoutCustomerNetwork.tvCustSave.setVisibility(View.GONE);
        binding.layoutAssociatedDetails.btSubmitAssociate.setVisibility(View.GONE);
        binding.layoutAssociatedDetails.etLinkBudget.setFocusable(false);
        binding.layoutAssociatedDetails.etIdbLength.setFocusable(false);
        binding.layoutWcrFms.etCustomerEndFms.setFocusable(false);
        binding.layoutWcrFms.etCustomerEndFmsSec.setFocusable(false);
        binding.layoutWcrFms.etPortnumEnd.setFocusable(false);
        binding.layoutWcrFms.etPortNumCx.setFocusable(false);
        binding.layoutWcrFms.etPodEnd.setFocusable(false);
        binding.etRemarksText.setFocusable(false);
        binding.layoutWcrEngrDetails.etEnggName.setFocusable(false);
        binding.layoutWcrEngrDetails.etInstallationCode.setFocusable(false);
        binding.layoutWcrEngrDetails.etCreatedOn.setFocusable(false);
        binding.layoutWcrFms.btSubmitFmsDetails.setVisibility(View.GONE);
        binding.layoutAddEquipment.btnItemEqipment.setVisibility(View.GONE);
        binding.layoutmanholDetails.btnAddManhole.setVisibility(View.GONE);
        binding.tvWcrSave.setVisibility(View.GONE);
        binding.layoutWcrEngrDetails.etEnggName.setFocusable(false);
        binding.layoutWcrEngrDetails.etCreatedOn.setFocusable(false);
        binding.layoutInstallationparam.tvSaveQualityParam.setVisibility(View.GONE);
        binding.layoutWcrEngrDetails.saveEnggDetails.setVisibility(View.GONE);
        binding.saveHold.setVisibility(View.GONE);
        binding.etHoldReason.setFocusable(false);
        binding.layoutServiceDetails.btnAddService.setVisibility(View.GONE);

        binding.tvResendNav.setOnClickListener(view -> resendNav());
    }

    private void initOne(){
        getWcrInfo();

        binding.layoutItemConsumption.btnItemConsumption1.setVisibility(View.GONE);

        binding.tvResendService.setOnClickListener(view -> resendService("Activation"));
        binding.tvResendInstallation.setOnClickListener(view -> resendService("Installation"));
        binding.layoutInstallationparam.etOntLogin.setOnClickListener(v-> binding.layoutInstallationparam.spOntLogin.performClick());
        binding.layoutInstallationparam.spOntLogin.setOnItemSelectedListener(this);
        binding.layoutInstallationparam.etFacePlate.setOnClickListener(v-> binding.layoutInstallationparam.spFacePlate.performClick());
        binding.layoutInstallationparam.spFacePlate.setOnItemSelectedListener(this);
        binding.layoutInstallationparam.etWifiSsid.setOnClickListener(v-> binding.layoutInstallationparam.spWifiSsid.performClick());
        binding.layoutInstallationparam.spWifiSsid.setOnItemSelectedListener(this);
        binding.layoutInstallationparam.etSpeedTest.setOnClickListener(v-> binding.layoutInstallationparam.spSpeedTest.performClick());
        binding.layoutInstallationparam.spSpeedTest.setOnItemSelectedListener(this);
        binding.layoutInstallationparam.etEducationAntivirus.setOnClickListener(v-> binding.layoutInstallationparam.spEducationAntivirus.performClick());
        binding.layoutInstallationparam.spEducationAntivirus.setOnItemSelectedListener(this);
        binding.layoutInstallationparam.etCableCrimped.setOnClickListener(v-> binding.layoutInstallationparam.spCableCrimped.performClick());
        binding.layoutInstallationparam.spCableCrimped.setOnItemSelectedListener(this);
        binding.layoutInstallationparam.etEducationCustomer.setOnClickListener(v-> binding.layoutInstallationparam.spEducationCustomer.performClick());
        binding.layoutInstallationparam.spEducationCustomer.setOnItemSelectedListener(this);

    }



    private void init(){

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
            binding.linearEqipmentdetails.setVisibility(View.GONE);
            binding.linearCustomernetworkDetails.setVisibility(View.GONE);
            binding.linearInstallationParamDetails.setVisibility(View.GONE);
            binding.linearservicedeatils.setVisibility(View.GONE);
            binding.linearmisc.setVisibility(View.GONE);
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
            binding.linearEqipmentdetails.setVisibility(View.GONE);
            binding.linearCustomernetworkDetails.setVisibility(View.GONE);
            binding.linearInstallationParamDetails.setVisibility(View.GONE);
            binding.linearservicedeatils.setVisibility(View.GONE);
            binding.linearmisc.setVisibility(View.GONE);
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
            binding.linearEqipmentdetails.setVisibility(View.GONE);
            binding.linearCustomernetworkDetails.setVisibility(View.GONE);
            binding.linearInstallationParamDetails.setVisibility(View.GONE);
            binding.linearservicedeatils.setVisibility(View.GONE);
            binding.linearmisc.setVisibility(View.GONE);
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
            binding.linearEqipmentdetails.setVisibility(View.GONE);
            binding.linearCustomernetworkDetails.setVisibility(View.GONE);
            binding.linearInstallationParamDetails.setVisibility(View.GONE);
            binding.linearservicedeatils.setVisibility(View.GONE);
            binding.linearmisc.setVisibility(View.GONE);
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
            binding.linearEqipmentdetails.setVisibility(View.GONE);
            binding.linearCustomernetworkDetails.setVisibility(View.GONE);
            binding.linearInstallationParamDetails.setVisibility(View.GONE);
            binding.linearservicedeatils.setVisibility(View.GONE);
            binding.linearmisc.setVisibility(View.GONE);
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
            binding.linearEqipmentdetails.setVisibility(View.GONE);
            binding.linearCustomernetworkDetails.setVisibility(View.GONE);
            binding.linearInstallationParamDetails.setVisibility(View.GONE);
            binding.linearservicedeatils.setVisibility(View.GONE);
            binding.linearmisc.setVisibility(View.GONE);
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
            binding.linearEqipmentdetails.setVisibility(View.GONE);
            binding.linearCustomernetworkDetails.setVisibility(View.GONE);
            binding.linearInstallationParamDetails.setVisibility(View.GONE);
            binding.linearservicedeatils.setVisibility(View.GONE);
            binding.linearmisc.setVisibility(View.GONE);
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
            binding.linearEqipmentdetails.setVisibility(View.GONE);
            binding.linearCustomernetworkDetails.setVisibility(View.GONE);
            binding.linearInstallationParamDetails.setVisibility(View.GONE);
            binding.linearservicedeatils.setVisibility(View.GONE);
            binding.linearmisc.setVisibility(View.GONE);
        });
        binding.linearEquipment.setOnClickListener(v -> {
            binding.linearFive.setVisibility(View.GONE);
            binding.linearTen.setVisibility(View.GONE);
            binding.linearEight.setVisibility(View.GONE);
            binding.linear12.setVisibility(View.GONE);
            binding.linear14.setVisibility(View.GONE);
            binding.linear16.setVisibility(View.GONE);
            binding.linear19.setVisibility(View.GONE);
            binding.linear21.setVisibility(View.GONE);
            binding.linearEqipmentdetails.setVisibility(View.VISIBLE);
            binding.linearCustomernetworkDetails.setVisibility(View.GONE);
            binding.linearInstallationParamDetails.setVisibility(View.GONE);
            binding.linearservicedeatils.setVisibility(View.GONE);
            binding.linearmisc.setVisibility(View.GONE);
        });
        binding.linearCustomerNetwork.setOnClickListener(v -> {
            binding.linearFive.setVisibility(View.GONE);
            binding.linearTen.setVisibility(View.GONE);
            binding.linearEight.setVisibility(View.GONE);
            binding.linear12.setVisibility(View.GONE);
            binding.linear14.setVisibility(View.GONE);
            binding.linear16.setVisibility(View.GONE);
            binding.linear19.setVisibility(View.GONE);
            binding.linear21.setVisibility(View.GONE);
            binding.linearEqipmentdetails.setVisibility(View.GONE);
            binding.linearCustomernetworkDetails.setVisibility(View.VISIBLE);
            binding.linearInstallationParamDetails.setVisibility(View.GONE);
            binding.linearservicedeatils.setVisibility(View.GONE);
            binding.linearmisc.setVisibility(View.GONE);
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
            binding.linearEqipmentdetails.setVisibility(View.GONE);
            binding.linearCustomernetworkDetails.setVisibility(View.GONE);
            binding.linearInstallationParamDetails.setVisibility(View.VISIBLE);
            binding.linearservicedeatils.setVisibility(View.GONE);
            binding.linearmisc.setVisibility(View.GONE);
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
            binding.linearEqipmentdetails.setVisibility(View.GONE);
            binding.linearCustomernetworkDetails.setVisibility(View.GONE);
            binding.linearservicedeatils.setVisibility(View.VISIBLE);
            binding.linearInstallationParamDetails.setVisibility(View.GONE);
            binding.linearmisc.setVisibility(View.GONE);
        });
        binding.linmisc.setOnClickListener(v -> {
            binding.linearFive.setVisibility(View.GONE);
            binding.linearTen.setVisibility(View.GONE);
            binding.linearEight.setVisibility(View.GONE);
            binding.linear12.setVisibility(View.GONE);
            binding.linear14.setVisibility(View.GONE);
            binding.linear16.setVisibility(View.GONE);
            binding.linear19.setVisibility(View.GONE);
            binding.linear21.setVisibility(View.GONE);
            binding.linearEqipmentdetails.setVisibility(View.GONE);
            binding.linearCustomernetworkDetails.setVisibility(View.GONE);
            binding.linearservicedeatils.setVisibility(View.GONE);
            binding.linearmisc.setVisibility(View.VISIBLE);
            binding.linearInstallationParamDetails.setVisibility(View.GONE);
        });
    }

    private void listener(){
        if(strSegment.equals("Business")){
            binding.linearFour.setVisibility(View.VISIBLE);
            binding.linearNine.setVisibility(View.VISIBLE);
            binding.linearSix.setVisibility(View.VISIBLE);
            binding.linea11.setVisibility(View.VISIBLE);
            binding.linea13.setVisibility(View.VISIBLE);
            binding.linea15.setVisibility(View.VISIBLE);
            binding.linea18.setVisibility(View.GONE);
            binding.linearService.setVisibility(View.VISIBLE);
            binding.linearEquipment.setVisibility(View.GONE);
            binding.linearCustomerNetwork.setVisibility(View.GONE);
            binding.linearInstallationParam.setVisibility(View.GONE);
            binding.linea20.setVisibility(View.VISIBLE);
        }else if(strSegment.equals("Home")){
            binding.linearFour.setVisibility(View.VISIBLE);
            binding.linea11.setVisibility(View.VISIBLE);
            binding.linearEquipment.setVisibility(View.VISIBLE);
            binding.linearCustomerNetwork.setVisibility(View.VISIBLE);
            binding.linearInstallationParam.setVisibility(View.VISIBLE);
            binding.linearNine.setVisibility(View.GONE);
            binding.linearSix.setVisibility(View.GONE);
            binding.linea13.setVisibility(View.GONE);
            binding.linearService.setVisibility(View.VISIBLE);
            binding.linea15.setVisibility(View.VISIBLE);
            binding.linea18.setVisibility(View.VISIBLE);
            binding.linea20.setVisibility(View.VISIBLE);
        }
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
    private void resendNav(){
        inProgress();
        ResendNavRequest resendNavRequest = new ResendNavRequest(Constants.AUTH_KEY,Constants.RESEND_NAVWCR,strGuuId,"Business","","");

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonClassResponse> call = apiService.submitNavWcr(resendNavRequest);
        call.enqueue(new Callback<CommonClassResponse>() {
            @Override
            public void onResponse(Call<CommonClassResponse> call, Response<CommonClassResponse> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                   outProgress();
                    try {
                        if(response.body().getStatus().equals("Success")){
                           nextScreen();
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

    public void getWcrInfo() {
        inProgress();
        AccountInfoRequest accountInfoRequest = new AccountInfoRequest();
        accountInfoRequest.setAuthkey(Constants.AUTH_KEY);
        accountInfoRequest.setAction(Constants.GET_WCR_INFO);
        accountInfoRequest.setCanId(strCanId);
        accountInfoRequest.setOrderId(OrderId);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<WcrResponse> call = apiService.getWcrInfo(accountInfoRequest);
        call.enqueue(new Callback<WcrResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<WcrResponse> call, Response<WcrResponse> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                    outProgress();
                    if(response.body().getStatus().equals("Success")) {
                        try {
                            binding.tvConsumptionStatus.setText("Consumption Status : " + response.body().getResponse().getWcr().getWCRConsumptionStatus());
                            binding.layoutWcrcustomerDetails.setCustomerDetails(response.body().getResponse().getWcr());
                            binding.layoutCustomerNetwork.setCustomerNetwork(response.body().getResponse().getCusotmerNetwork());
                            strGuuId = response.body().getResponse().getWcr().getWcrguidid();
                            strProductSegment = response.body().getResponse().getWcr().getProductSegment();
                            strSegment = response.body().getResponse().getWcr().getBusinessSegment();

                            if(strSegment.equals("Business")){
                                binding.tvConsumptionStatus.setVisibility(View.GONE);
                            }else if(strSegment.equals("Home")) {
                                binding.tvConsumptionStatus.setVisibility(View.VISIBLE);
                            }

                            String consumptionStatus = response.body().getResponse().getWcr().getWCRConsumptionStatus();
                            if(consumptionStatus.equals("Material not Available")){
                                binding.tvResendNav.setVisibility(View.VISIBLE);
                            }
                            if (strProductSegment.equals("Managed Wi-Fi Business")||strProductSegment.equals("Managed Office Solution")||
                                    strProductSegment.equals("Secured Managed Internet")) {
                                binding.layoutWcrEngrDetails.etInstallationCode.setVisibility(View.GONE);
                            } else {
                                binding.layoutWcrEngrDetails.etInstallationCode.setVisibility(View.VISIBLE);
                            }
                            binding.etMisc.setText(response.body().getResponse().getWcr().getMiscWorkCost());
                            manHoleDetails = response.body().getResponse().getManHoleDetailsList();
                            binding.layoutAssociatedDetails.setAssociated(response.body().getResponse().getAssociated());
                            binding.layoutWcrFms.setFms(response.body().getResponse().getFMSDetails());
                            binding.layoutWcrEngrDetails.setEngg(response.body().getResponse().getEngineerDetails());
                            binding.setHoldCategory(response.body().getResponse().getEngineerDetails());
                            binding.layoutmanholDetails.rvAddManhole.setHasFixedSize(true);
                            binding.layoutmanholDetails.rvAddManhole.setLayoutManager(new LinearLayoutManager(getActivity()));
                            binding.layoutmanholDetails.rvAddManhole.setAdapter(new WcrManholeAdapter(getActivity(), manHoleDetails));
                            itemConsumtions = response.body().getResponse().getItemConsumtionList();
                            binding.layoutItemConsumption.rvWcrItemlist.setHasFixedSize(true);
                            binding.layoutItemConsumption.rvWcrItemlist.setLayoutManager(new LinearLayoutManager(getActivity()));
                            binding.layoutItemConsumption.rvWcrItemlist.setAdapter(new WcrCConsumptionListAdapter(getActivity(), itemConsumtions));
                            equipmentDetailsLists = response.body().getResponse().getEquipmentDetailsList();
                            binding.layoutAddEquipment.rvAddEquipment.setHasFixedSize(true);
                            binding.layoutAddEquipment.rvAddEquipment.setLayoutManager(new LinearLayoutManager(getActivity()));
                            binding.layoutAddEquipment.rvAddEquipment.setAdapter(new WcrEquipmentAdapter(getActivity(), equipmentDetailsLists));
                            listener();
                            serviceConsumtions = response.body().getResponse().getServiceConsumtionList();
                            binding.layoutServiceDetails.rvAddService.setHasFixedSize(true);
                            binding.layoutServiceDetails.rvAddService.setLayoutManager(new LinearLayoutManager(getActivity()));
                            binding.layoutServiceDetails.rvAddService.setAdapter(new WcrServiceListAdapter(getActivity(), serviceConsumtions));

                            binding.layoutWcrFms.etCustomerEndFms.setText(response.body().getResponse().getFMSDetails().getFmsfirst());
                            binding.layoutWcrFms.etCustomerEndFmsSec.setText(response.body().getResponse().getFMSDetails().getFmssecond());
                            binding.layoutInstallationparam.setQuality(response.body().getResponse().getInstallationQuality());

                          //  try {
                                if (response.body().getResponse().getInstallationQuality().getAntiVirus().equals("Yes")) {
                                    QualityParamEducation.add("Yes");
                                    ArrayAdapter<String> adapterParam1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamEducation);
                                    adapterParam1.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                    binding.layoutInstallationparam.spEducationAntivirus.setAdapter(adapterParam1);

                                } else if (response.body().getResponse().getInstallationQuality().getAntiVirus().equals("No")) {
                                    QualityParamEducation.add("No");
                                    ArrayAdapter<String> adapterParam1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamEducation);
                                    adapterParam1.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                    binding.layoutInstallationparam.spEducationAntivirus.setAdapter(adapterParam1);
                                }
                                if (response.body().getResponse().getInstallationQuality().getCable().equals("Yes")) {
                                    QualityParamCable.add("Yes");
                                    adapterParamCable = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamCable);
                                    adapterParamCable.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                    binding.layoutInstallationparam.spCableCrimped.setAdapter(adapterParamCable);

                                } else if (response.body().getResponse().getInstallationQuality().getCable().equals("No")) {
                                    QualityParamCable.add("No");
                                    adapterParamCable = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamCable);
                                    adapterParamCable.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                    binding.layoutInstallationparam.spCableCrimped.setAdapter(adapterParamCable);
                                }
                                if (response.body().getResponse().getInstallationQuality().getFace().equals("Yes")) {
                                    QualityParamFace.add("Yes");
                                    adapterParamFace = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamFace);
                                    adapterParamFace.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                    binding.layoutInstallationparam.spFacePlate.setAdapter(adapterParamFace);
                                } else if (response.body().getResponse().getInstallationQuality().getFace().equals("No")) {
                                    QualityParamFace.add("No");
                                    adapterParamFace = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamFace);
                                    adapterParamFace.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                    binding.layoutInstallationparam.spFacePlate.setAdapter(adapterParamFace);
                                }
                                if (response.body().getResponse().getInstallationQuality().getOnt().equals("Yes")) {
                                    QualityParamLogin.add("Yes");
                                    adapterParamLogin = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamLogin);
                                    adapterParamLogin.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                    binding.layoutInstallationparam.spOntLogin.setAdapter(adapterParamLogin);
                                } else if (response.body().getResponse().getInstallationQuality().getOnt().equals("No")) {
                                    QualityParamLogin.add("No");
                                    adapterParamLogin = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamLogin);
                                    adapterParamLogin.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                    binding.layoutInstallationparam.spOntLogin.setAdapter(adapterParamLogin);
                                }
                                if (response.body().getResponse().getInstallationQuality().getSelfCare().equals("Yes")) {
                                    QualityParamCustomer.add("Yes");
                                    adapterParamCustomer = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamCustomer);
                                    adapterParamCustomer.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                    binding.layoutInstallationparam.spEducationCustomer.setAdapter(adapterParamCustomer);
                                } else if (response.body().getResponse().getInstallationQuality().getSelfCare().equals("No")) {
                                    QualityParamCustomer.add("No");
                                    adapterParamCustomer = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamCustomer);
                                    adapterParamCustomer.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                    binding.layoutInstallationparam.spEducationCustomer.setAdapter(adapterParamCustomer);
                                }
                                if (response.body().getResponse().getInstallationQuality().getSpeed().equals("Yes")) {
                                    QualityParamSpeed.add("Yes");
                                    adapterParamSpeed = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamSpeed);
                                    adapterParamSpeed.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                    binding.layoutInstallationparam.spSpeedTest.setAdapter(adapterParamSpeed);
                                } else if (response.body().getResponse().getInstallationQuality().getSpeed().equals("No")) {
                                    QualityParamSpeed.add("No");
                                    adapterParamSpeed = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamSpeed);
                                    adapterParamSpeed.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                    binding.layoutInstallationparam.spSpeedTest.setAdapter(adapterParamSpeed);
                                }
                                if (response.body().getResponse().getInstallationQuality().getWifi().equals("Yes")) {
                                    QualityParamWifi.add("Yes");
                                    adapterParamWifi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamWifi);
                                    adapterParamWifi.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                    binding.layoutInstallationparam.spWifiSsid.setAdapter(adapterParamWifi);
                                } else if (response.body().getResponse().getInstallationQuality().getWifi().equals("No")) {
                                    QualityParamWifi.add("No");
                                    adapterParamWifi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamWifi);
                                    adapterParamWifi.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                    binding.layoutInstallationparam.spWifiSsid.setAdapter(adapterParamWifi);
                                }
                            getItemStatus();

                           /* }catch (Exception ex){
                                ex.getMessage();
                            }*/
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                       //
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
        Intent i = new Intent(getActivity(), ProvisioningMainActivity.class);
        i.putExtra("canId", strCanId);
        i.putExtra("StatusofReport", strStatusofReport);
        i.putExtra("OrderId", OrderId);
        startActivity(i);
        getActivity().finish();
    }

    private void resendService(String type){
        inProgress();
        ResendActivationCodeRequest resendActivationCodeRequest = new ResendActivationCodeRequest();
        resendActivationCodeRequest.setAuthkey(Constants.AUTH_KEY);
        resendActivationCodeRequest.setAction(Constants.RESEND_CODE);
        resendActivationCodeRequest.setType(type);
        resendActivationCodeRequest.setIsIR("false");
        resendActivationCodeRequest.setGUID(strGuuId);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonClassResponse> call = apiService.resendCodeWcr(resendActivationCodeRequest);
        call.enqueue(new Callback<CommonClassResponse>() {
            @Override
            public void onResponse(Call<CommonClassResponse> call, Response<CommonClassResponse> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                   outProgress();
                    try {
                        if(response.body().getStatus().equals("Success")){
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

    private void getItemStatus(){
        inProgress();
        ResendActivationCodeRequest resendActivationCodeRequest = new ResendActivationCodeRequest();
        resendActivationCodeRequest.setAuthkey(Constants.AUTH_KEY);
        resendActivationCodeRequest.setAction(Constants.GET_ITEM_STATUS);
        resendActivationCodeRequest.setId(strGuuId);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonMessageResponse> call = apiService.getItemCodeStatus(resendActivationCodeRequest);
        call.enqueue(new Callback<CommonMessageResponse>() {
            @Override
            public void onResponse(Call<CommonMessageResponse> call, Response<CommonMessageResponse> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                   outProgress();
                    try {
                        if(response.body().getStatusCode().equals("200")){
                            binding.tvWcrItemStatus.setText("Item Status : "+ response.body().getMessage());
                            binding.tvWcrItemStatus.setVisibility(View.VISIBLE);
                            // Toast.makeText(getContext(),response.body().getResponse().getMessage(),Toast.LENGTH_LONG).show();
                        }else {
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



    private void resendInstallation(){
       inProgress();
        ResendActivationCodeRequest resendActivationCodeRequest = new ResendActivationCodeRequest();
        resendActivationCodeRequest.setAuthkey(Constants.AUTH_KEY);
        resendActivationCodeRequest.setAction(Constants.RESEND_CODE);
        resendActivationCodeRequest.setType("Activation");
        resendActivationCodeRequest.setIsIR("false");
        resendActivationCodeRequest.setGUID(strGuuId);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonClassResponse> call = apiService.resendCodeWcr(resendActivationCodeRequest);
        call.enqueue(new Callback<CommonClassResponse>() {
            @Override
            public void onResponse(Call<CommonClassResponse> call, Response<CommonClassResponse> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                    outProgress();
                    try {
                        if(response.body().getStatus().equals("Success")){
                            // moveNext();
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        if(parent.getId() == R.id.sp_speed_test) {
            binding.layoutInstallationparam.etSpeedTest.setText(QualityParamSpeed.get(position));
        }
        else if(parent.getId() == R.id.sp_cable_crimped) {
            binding.layoutInstallationparam.etCableCrimped.setText(QualityParamCable.get(position));
        }
        else if(parent.getId() == R.id.sp_face_plate) {
            binding.layoutInstallationparam.etFacePlate.setText(QualityParamFace.get(position));
        }
        else if(parent.getId() == R.id.sp_ont_login) {
            binding.layoutInstallationparam.etOntLogin.setText(QualityParamLogin.get(position));
        }
        else if(parent.getId() == R.id.sp_wifi_ssid) {
            binding.layoutInstallationparam.etWifiSsid.setText(QualityParamWifi.get(position));
        }
        else if(parent.getId() == R.id.sp_education_customer) {
            binding.layoutInstallationparam.etEducationCustomer.setText(QualityParamCustomer.get(position));
        }
        else if(parent.getId() == R.id.sp_education_antivirus) {
            binding.layoutInstallationparam.etEducationAntivirus.setText(QualityParamEducation.get(position));
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }



}