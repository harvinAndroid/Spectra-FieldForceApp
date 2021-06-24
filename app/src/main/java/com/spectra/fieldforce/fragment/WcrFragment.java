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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.spectra.fieldforce.R;
import com.spectra.fieldforce.activity.BucketTabActivity;
import com.spectra.fieldforce.adapter.WcrAddManholeAdapter;
import com.spectra.fieldforce.adapter.WcrCompletteItemConsumptionListAdapter;
import com.spectra.fieldforce.adapter.WcrEquipmentConsumpAdapter;
import com.spectra.fieldforce.api.ApiClient;
import com.spectra.fieldforce.api.ApiInterface;
import com.spectra.fieldforce.databinding.WcrFragmentBinding;
import com.spectra.fieldforce.model.CommonResponse;
import com.spectra.fieldforce.model.ItemConsumption.NrgpDetails;
import com.spectra.fieldforce.model.gpon.request.AccountInfoRequest;
import com.spectra.fieldforce.model.gpon.request.AssociatedResquest;
import com.spectra.fieldforce.model.gpon.request.HoldWcrRequest;
import com.spectra.fieldforce.model.gpon.request.UpdateFmsRequest;
import com.spectra.fieldforce.model.gpon.request.UpdateWcrEnggRequest;
import com.spectra.fieldforce.model.gpon.request.WcrCompleteRequest;
import com.spectra.fieldforce.model.gpon.response.CommonClassResponse;
import com.spectra.fieldforce.model.gpon.response.GetFibreCable;
import com.spectra.fieldforce.model.gpon.response.GetFmsListResponse;
import com.spectra.fieldforce.model.gpon.response.WCRHoldCategoryResponse;
import com.spectra.fieldforce.model.gpon.response.WcrResponse;
import com.spectra.fieldforce.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WcrFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener{
    private WcrFragmentBinding binding;
    private ArrayList<String> FmsType;
    private ArrayList<String> FmsId;
    private List<GetFmsListResponse.Fms> fmsList;
    private ArrayList<String> fmsName;
    private ArrayList<String> firstFmsID;
    private ArrayList<String> holdCategory;
    private ArrayList<String> holdCategoryId;
    private String strGuuId,strSegment, strfmsId,strSecFmsId;
    private ArrayList<String> itemType;
    private String strCanId ,strholdId,strProductSegment;
    private ArrayList<WcrResponse.ManHoleDetails> manHoleDetails;
    private ArrayList<WcrResponse.ItemConsumtion> itemConsumtions;
    private AlphaAnimation inAnimation;
    private AlphaAnimation outAnimation;

    public WcrFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = WcrFragmentBinding.inflate(inflater, container, false);
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
       // strStatusofReport = requireArguments().getString("StatusofReport");
        init();
        initOne();
    }

    private void initOne(){
        getWcrInfo();
        getFmsList();
        Type();

        binding.layoutItemConsumption.btnItemConsumption1.setOnClickListener(v -> {
            @SuppressLint("UseRequireInsteadOfGet") FragmentTransaction t1= Objects.requireNonNull(this.getFragmentManager()).beginTransaction();
            WcrItemConsumption wcrItemConsumption = new WcrItemConsumption();
            Bundle bundle = new Bundle();
            bundle.putString("canId", strCanId);
            bundle.putString("strGuuId", strGuuId);
            t1.replace(R.id.frag_container, wcrItemConsumption);
            wcrItemConsumption.setArguments(bundle);
            t1.commit();
        });

        binding.layoutAssociatedDetails.btSubmitAssociate.setOnClickListener((View v) -> {
            String idb = Objects.requireNonNull(binding.layoutAssociatedDetails.etIdbLength.getText()).toString();
            String link = Objects.requireNonNull(binding.layoutAssociatedDetails.etLinkBudget.getText()).toString();

            if(idb.isEmpty()){
                Toast.makeText(getContext(),"Please Enter IDB Length",Toast.LENGTH_LONG).show();
            }else if(link.isEmpty()){
                Toast.makeText(getContext(),"Please Enter Link Budget",Toast.LENGTH_LONG).show();
            }else{
                updateAssociateDetails();
            }
            }
        );

        binding.layoutWcrFms.btSubmitFmsDetails.setOnClickListener(v -> {
            String custEnd = binding.layoutWcrFms.etCustomerEndFmsSec.getText().toString();
            String custEndFms = binding.layoutWcrFms.etPodEnd.getText().toString();
            String PortNumCx = binding.layoutWcrFms.etPortNumCx.getText().toString();
            String PortnumEnd = binding.layoutWcrFms.etPortnumEnd.getText().toString();
            if(binding.layoutWcrFms.etCustomerEndFms.getText().toString().equals("Select Fms Type")){
                Toast.makeText(getContext(), "Please Select Fms Type", Toast.LENGTH_LONG).show();
            } else if (custEnd.equals("Select Customer End FMS(Second Level)")||custEnd.equals("")) {
                        Toast.makeText(getContext(), "Please Select Customer End FMS(Second Level)", Toast.LENGTH_LONG).show();
                    } else if (custEndFms.equals("POD End FMS No.:")||custEndFms.equals("")) {
                        Toast.makeText(getContext(), "Please Enter Pod End Fms", Toast.LENGTH_LONG).show();
                    } else if (PortNumCx.equals("Port Number (Cx End)")||PortNumCx.equals("")) {
                        Toast.makeText(getContext(), "Please Enter Port Number CX End", Toast.LENGTH_LONG).show();
                    } else if (PortnumEnd.equals("Port Number (POD End)")||PortnumEnd.equals("")) {
                        Toast.makeText(getContext(), "Please Enter Port Number Pod End", Toast.LENGTH_LONG).show();
                    } else {
                        updateFmsDetails();
                    }
                }
        );
        binding.layoutmanholDetails.btnAddManhole.setOnClickListener(v -> {
            @SuppressLint("UseRequireInsteadOfGet") FragmentTransaction t1= Objects.requireNonNull(this.getFragmentManager()).beginTransaction();
            WcrAddManholeFragment wcrAddManholeFragment = new WcrAddManholeFragment();
            Bundle bundle = new Bundle();
            bundle.putString("canId", strCanId);
            bundle.putString("strGuuId", strGuuId);
            wcrAddManholeFragment.setArguments(bundle);
            t1.replace(R.id.frag_container, wcrAddManholeFragment);
            t1.commit();
        });
        binding.tvWcrSave.setOnClickListener(v -> updateWcrComplete());
        binding.tvWcrSave.setOnClickListener((View v) -> {

            updateWcrEnginer();
        });
    }



    private void init(){
        binding.layoutWcrFms.etCustomerEndFms.setOnClickListener(v-> binding.layoutWcrFms.spCustomerEndFms.performClick());
        binding.layoutWcrFms.spCustomerEndFms.setOnItemSelectedListener(this);
        binding.layoutWcrFms.etCustomerEndFmsSec.setOnClickListener(v-> binding.layoutWcrFms.spCustomerEndFmsSec.performClick());
        binding.layoutWcrFms.spCustomerEndFmsSec.setOnItemSelectedListener(this);

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

    private void listener(){
        if(strSegment.equals("Business")){
            binding.linearFour.setVisibility(View.VISIBLE);
            binding.linearNine.setVisibility(View.VISIBLE);
            binding.linearSix.setVisibility(View.VISIBLE);
            binding.linea11.setVisibility(View.VISIBLE);
            binding.linea13.setVisibility(View.VISIBLE);
            binding.linea15.setVisibility(View.VISIBLE);
            binding.linea18.setVisibility(View.GONE);
            binding.linea20.setVisibility(View.VISIBLE);
        }else if(strSegment.equals("Home")){
            binding.linearFour.setVisibility(View.VISIBLE);
            binding.linea11.setVisibility(View.VISIBLE);
            binding.linearNine.setVisibility(View.GONE);
            binding.linearSix.setVisibility(View.GONE);
            binding.linea13.setVisibility(View.GONE);
            binding.linearNine.setVisibility(View.VISIBLE);
            binding.linea15.setVisibility(View.VISIBLE);
            binding.linea18.setVisibility(View.VISIBLE);
            binding.linea20.setVisibility(View.VISIBLE);
        }
    }

    public void getFmsList() {
        inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        binding.progressLayout.progressOverlay.setAnimation(inAnimation);
        binding.progressLayout.progressOverlay.setVisibility(View.VISIBLE);
        AccountInfoRequest accountInfoRequest = new AccountInfoRequest();
        accountInfoRequest.setAuthkey(Constants.AUTH_KEY);
        accountInfoRequest.setAction(Constants.GET_FMS_LIST);
        // accountInfoRequest.setCanId("221373");

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<GetFmsListResponse> call = apiService.getFmsList(accountInfoRequest);
        call.enqueue(new Callback<GetFmsListResponse>() {
            @Override
            public void onResponse(Call<GetFmsListResponse> call, Response<GetFmsListResponse> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                    outAnimation = new AlphaAnimation(1f, 0f);
                    outAnimation.setDuration(200);
                    binding.progressLayout.progressOverlay.setAnimation(outAnimation);
                    binding.progressLayout.progressOverlay.setVisibility(View.GONE);
                    try {
                        fmsList = response.body().getResponse().getFMSList();
                        firstFmsID = new ArrayList<>();
                        fmsName = new ArrayList<>();
                        fmsName.add("Select Customer End FMS(Second Level)");
                        for (GetFmsListResponse.Fms fms : fmsList)
                            fmsName.add(fms.getFms());
                        for (GetFmsListResponse.Fms fmss : fmsList)
                            firstFmsID.add(fmss.getId());
                        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, fmsName);
                        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        binding.layoutWcrFms.spCustomerEndFmsSec.setAdapter(adapter1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<GetFmsListResponse> call, Throwable t) {
                binding.progressLayout.progressOverlay.setVisibility(View.GONE);
                Log.e("RetroError", t.toString());
            }
        });
    }



    private void Type() {
        FmsType = new ArrayList<String>();
        FmsType.add("Select Fms Type");
        FmsType.add("WallMount");
        FmsType.add("RackMount");
        FmsId = new ArrayList<String>();
        FmsId.add("569480000");
        FmsId.add("569480001");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, FmsType);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.layoutWcrFms.spCustomerEndFms.setAdapter(adapter);


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

        binding.saveHold.setOnClickListener(v -> {
            if (binding.spHoldCategory.getSelectedItem().toString().equals("Hold Category")) {
                Toast.makeText(getContext(), "Please Select Hold Category", Toast.LENGTH_LONG).show();
            } else if (Objects.requireNonNull(binding.etHoldReason.getText()).toString().equals("Hold Reason:")) {
                Toast.makeText(getContext(), "Please Enter Hold Reason", Toast.LENGTH_LONG).show();
            }else {
                updateHoldCategory();
            }
          }
        );
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.sp_customer_end_fms) {
            binding.layoutWcrFms.etCustomerEndFms.setText(FmsType.get(position));
            if (position != 0) strfmsId = "" + FmsId.get(position - 1);
            else strfmsId = " ";
        }else if(parent.getId() == R.id.sp_customer_end_fms_sec){
            binding.layoutWcrFms.etCustomerEndFmsSec.setText(fmsName.get(position));
            if (position != 0) strSecFmsId = "" + firstFmsID.get(position - 1);
            else strSecFmsId = " ";
        }else if(parent.getId() == R.id.sp_hold_category){
            binding.etHoldCategory.setText(holdCategory.get(position));
            if (position != 0) strholdId = "" + holdCategoryId.get(position - 1);
            else strholdId = " ";
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
                            binding.tvWcrStatus.setText("WCR Status: " + response.body().getResponse().getWcr().getWCRConsumptionStatus());
                            binding.layoutWcrFragmentCustomerDetails.setCustomer(response.body().getResponse().getWcr());
                            strGuuId = response.body().getResponse().getWcr().getWcrguidid();
                            strProductSegment = response.body().getResponse().getWcr().getProductSegment();
                            strSegment = response.body().getResponse().getWcr().getBusinessSegment();
                            manHoleDetails = response.body().getResponse().getManHoleDetailsList();
                            binding.layoutAssociatedDetails.setAssociated(response.body().getResponse().getAssociated());
                            binding.layoutWcrFms.setFms(response.body().getResponse().getFMSDetails());
                            binding.layoutWcrEngrDetails.setEngg(response.body().getResponse().getEngineerDetails());
                            binding.setHoldCategory(response.body().getResponse().getEngineerDetails());
                            binding.layoutmanholDetails.rvAddManhole.setHasFixedSize(true);
                            binding.layoutmanholDetails.rvAddManhole.setLayoutManager(new LinearLayoutManager(getActivity()));
                            binding.layoutmanholDetails.rvAddManhole.setAdapter(new WcrAddManholeAdapter(getActivity(), manHoleDetails));
                            itemConsumtions = response.body().getResponse().getItemConsumtionList();
                            binding.layoutItemConsumption.rvWcrItemlist.setHasFixedSize(true);
                            binding.layoutItemConsumption.rvWcrItemlist.setLayoutManager(new LinearLayoutManager(getActivity()));
                            binding.layoutItemConsumption.rvWcrItemlist.setAdapter(new WcrCompletteItemConsumptionListAdapter(getActivity(), itemConsumtions));
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



    private void moveNext(){

        @SuppressLint("UseRequireInsteadOfGet") FragmentTransaction t= Objects.requireNonNull(this.getFragmentManager()).beginTransaction();
        WcrFragment wcrFragment = new WcrFragment();
        Bundle accountinfo = new Bundle();
        accountinfo.putString("canId", strCanId);
        t.replace(R.id.frag_container, wcrFragment);
        wcrFragment.setArguments(accountinfo);
        t.commit();
    }

    private void updateAssociateDetails(){
        inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        binding.progressLayout.progressOverlay.setAnimation(inAnimation);
        binding.progressLayout.progressOverlay.setVisibility(View.VISIBLE);
        AssociatedResquest associatedResquest = new AssociatedResquest();
        associatedResquest.setAuthkey(Constants.AUTH_KEY);
        associatedResquest.setAction(Constants.UPDATE_ASSOCIATE);
        associatedResquest.setIdb(binding.layoutAssociatedDetails.etIdbLength.getText().toString());
        associatedResquest.setLink(binding.layoutAssociatedDetails.etLinkBudget.getText().toString());
        associatedResquest.setWCRguidId(strGuuId);


            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<CommonClassResponse> call = apiService.updateAssociateDetails(associatedResquest);
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
                            Toast.makeText(getContext(),response.body().getResponse().getMessage(),Toast.LENGTH_LONG).show();
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

    private void updateFmsDetails(){
        inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        binding.progressLayout.progressOverlay.setAnimation(inAnimation);
        binding.progressLayout.progressOverlay.setVisibility(View.VISIBLE);
        UpdateFmsRequest updateFmsRequest = new UpdateFmsRequest();
        updateFmsRequest.setAuthkey(Constants.AUTH_KEY);
        updateFmsRequest.setAction(Constants.UPDATE_FMS_DETAILS);
        updateFmsRequest.setFmsFirst(strfmsId);
        updateFmsRequest.setFmsSecond(strSecFmsId);
        updateFmsRequest.setWCRguidId(strGuuId);
        updateFmsRequest.setFmsPODno(binding.layoutWcrFms.etPodEnd.getText().toString());
        updateFmsRequest.setFmsPortCX(binding.layoutWcrFms.etPortNumCx.getText().toString());
        updateFmsRequest.setFmsPortPOD(binding.layoutWcrFms.etPortnumEnd.getText().toString());


        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonClassResponse> call = apiService.updsteFmsDetails(updateFmsRequest);
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

    private void updateWcrEnginer(){
        inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        binding.progressLayout.progressOverlay.setAnimation(inAnimation);
        binding.progressLayout.progressOverlay.setVisibility(View.VISIBLE);
        UpdateWcrEnggRequest updateWcrEnggRequest = new UpdateWcrEnggRequest();
        updateWcrEnggRequest.setAuthkey(Constants.AUTH_KEY);
        updateWcrEnggRequest.setAction(Constants.UPDATE_WCR_ENGINER);
        updateWcrEnggRequest.setEngName(binding.layoutWcrEngrDetails.etEnggName.getText().toString());
        updateWcrEnggRequest.setInstcode(binding.layoutWcrEngrDetails.etInstallationCode.getText().toString());
        updateWcrEnggRequest.setWCRguidId(strGuuId);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonClassResponse> call = apiService.updateWcrEng(updateWcrEnggRequest);
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

    private void updateHoldCategory(){
        inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        binding.progressLayout.progressOverlay.setAnimation(inAnimation);
        binding.progressLayout.progressOverlay.setVisibility(View.VISIBLE);
        HoldWcrRequest holdWcrRequest = new HoldWcrRequest();
        holdWcrRequest.setAuthkey(Constants.AUTH_KEY);
        holdWcrRequest.setAction(Constants.HOLD_WCR);
        holdWcrRequest.setCategory(strholdId);
        holdWcrRequest.setReason(binding.etHoldReason.getText().toString());
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
}