package com.spectra.fieldforce.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.spectra.fieldforce.R;
import com.spectra.fieldforce.api.ApiClient;
import com.spectra.fieldforce.api.ApiInterface;
import com.spectra.fieldforce.databinding.WcrFragmentBinding;
import com.spectra.fieldforce.model.CommonResponse;
import com.spectra.fieldforce.model.ItemConsumption.NrgpDetails;
import com.spectra.fieldforce.model.gpon.request.AccountInfoRequest;
import com.spectra.fieldforce.model.gpon.request.AssociatedResquest;
import com.spectra.fieldforce.model.gpon.request.UpdateFmsRequest;
import com.spectra.fieldforce.model.gpon.request.UpdateWcrEnggRequest;
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

public class WcrFragment extends Fragment implements AdapterView.OnItemSelectedListener{
    private WcrFragmentBinding binding;
    private ArrayList<String> FmsType;
    private ArrayList<String> FmsId;
    private ArrayList<String> ManholeType;
    private List<GetFmsListResponse.Fms> fmsList;
    private List<GetFibreCable.Datum> fibreCable;
    private ArrayList<String> fmsName;
    private ArrayList<String> firstFmsID;
    private ArrayList<String> fibreType;
    private ArrayList<String> fibreValue;
    private ArrayList<String> holdCategory;
    private ArrayList<String> holdCategoryId;
    private String strGuuId,strSegment, strfmsId,strSecFmsId;
    private ArrayList<String> itemType;


    public WcrFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = WcrFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        initOne();
    }

    private void initOne(){
        getWcrInfo();
        getFmsList();
        Type();
        binding.layoutItemConsumption.btnItemConsumption1.setOnClickListener(v -> {
            @SuppressLint("UseRequireInsteadOfGet") FragmentTransaction t1= Objects.requireNonNull(this.getFragmentManager()).beginTransaction();
          WcrEditItemConsumption wcrItemConsumption = new WcrEditItemConsumption();
            t1.replace(R.id.frag_container, wcrItemConsumption);
            t1.commit();

          /*  WcrItemConsumption wcrItemConsumption = new WcrItemConsumption();
            t1.replace(R.id.frag_container, wcrItemConsumption);
            t1.commit();*/
        });
        getFibreCable();
        binding.layoutAssociatedDetails.btSubmitAssociate.setOnClickListener(v ->
                updateAssociateDetails());
        binding.layoutWcrFms.btSubmitFmsDetails.setOnClickListener(v ->
                updateFmsDetails());
    }



    private void init(){
        binding.layoutWcrFms.etCustomerEndFms.setOnClickListener(v-> binding.layoutWcrFms.spCustomerEndFms.performClick());
        binding.layoutWcrFms.spCustomerEndFms.setOnItemSelectedListener(this);
        binding.layoutWcrFms.etCustomerEndFmsSec.setOnClickListener(v-> binding.layoutWcrFms.spCustomerEndFmsSec.performClick());
        binding.layoutWcrFms.spCustomerEndFmsSec.setOnItemSelectedListener(this);
        binding.layoutmanholDetails.etManholeType.setOnClickListener(v-> binding.layoutmanholDetails.spManholeType.performClick());
        binding.layoutmanholDetails.spManholeType.setOnItemSelectedListener(this);
        binding.layoutmanholDetails.etFibreCable.setOnClickListener(v-> binding.layoutmanholDetails.spFibreCable.performClick());
        binding.layoutmanholDetails.spFibreCable.setOnItemSelectedListener(this);
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

    public void getFmsList() {
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
                Log.e("RetroError", t.toString());
            }
        });
    }

    private void getFibreCable() {
        AccountInfoRequest accountInfoRequest = new AccountInfoRequest();
        accountInfoRequest.setAuthkey(Constants.AUTH_KEY);
        accountInfoRequest.setAction(Constants.GET_FIBRE_CABLE);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<GetFibreCable> call = apiService.getFibreList(accountInfoRequest);
        call.enqueue(new Callback<GetFibreCable>() {
            @Override
            public void onResponse(Call<GetFibreCable> call, Response<GetFibreCable> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                    try {
                        fibreCable = response.body().response.data;
                        fibreType = new ArrayList<>();
                        fibreValue = new ArrayList<>();
                        fibreType.add("Select Fibre Type");
                        for (GetFibreCable.Datum datum : fibreCable)
                            fibreType.add(datum.type);
                        for (GetFibreCable.Datum data : fibreCable)
                            fibreValue.add(data.value);
                        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, fibreType);
                        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        binding.layoutmanholDetails.spFibreCable.setAdapter(adapter1);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<GetFibreCable> call, Throwable t) {
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

        ManholeType = new ArrayList<String>();
        ManholeType.add("Select Manhole Type");
        ManholeType.add("In");
        ManholeType.add("Out");
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, ManholeType);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.layoutmanholDetails.spManholeType.setAdapter(adapter1);



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
        }else if(parent.getId() == R.id.sp_fibre_cable){
            binding.layoutmanholDetails.etFibreCable.setText(fibreType.get(position));
        }else if(parent.getId() == R.id.sp_manhole_type){
            binding.layoutmanholDetails.etManholeType.setText(ManholeType.get(position));
        }else if(parent.getId() == R.id.sp_hold_category){
            binding.etHoldCategory.setText(holdCategory.get(position));
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void getWcrInfo() {
        AccountInfoRequest accountInfoRequest = new AccountInfoRequest();
        accountInfoRequest.setAuthkey(Constants.AUTH_KEY);
        accountInfoRequest.setAction(Constants.GET_WCR_INFO);
        accountInfoRequest.setCanId("229698");

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<WcrResponse> call = apiService.getWcrInfo(accountInfoRequest);
        call.enqueue(new Callback<WcrResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<WcrResponse> call, Response<WcrResponse> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                    try {
                        binding.tvWcrStatus.setText("WCR Status: "+response.body().getResponse().getWcr().getWCRConsumptionStatus());
                        binding.layoutWcrFragmentCustomerDetails.tvCustomerSegment.setText(response.body().getResponse().getWcr().getBusinessSegment());
                        binding.layoutWcrFragmentCustomerDetails.tvPod.setText(response.body().getResponse().getWcr().getPod());
                        binding.layoutWcrFragmentCustomerDetails.tvProductName.setText(response.body().getResponse().getWcr().getProduct());
                        binding.layoutWcrFragmentCustomerDetails.tvReduncanyRequired.setText(response.body().getResponse().getWcr().getRedundacy());
                        binding.layoutWcrFragmentCustomerDetails.tvRackRequired.setText(response.body().getResponse().getWcr().getRack());
                        binding.layoutWcrFragmentCustomerDetails.tvContactPersonName.setText(response.body().getResponse().getWcr().getTechContactName());
                        binding.layoutWcrFragmentCustomerDetails.tvContactPersonNum.setText(response.body().getResponse().getWcr().getTechcontactnumber());
                        strGuuId = response.body().getResponse().getWcr().getWcrguidid();
                        strSegment = response.body().getResponse().getWcr().getBusinessSegment();
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<WcrResponse> call, Throwable t) {
                Log.e("RetroError", t.toString());
            }
        });
    }

    private void updateAssociateDetails(){

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
                        try {
                        if(response.body().getStatus().equals("Success")){
                            Toast.makeText(getContext(),response.body().getResponse().getMessage(),Toast.LENGTH_LONG).show();
                           /* binding.layoutAssociatedDetails.etIdbLength.setText("");
                            binding.layoutAssociatedDetails.etLinkBudget.setText("");*/
                        }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }

                @Override
                public void onFailure(Call<CommonClassResponse> call, Throwable t) {
                    Log.e("RetroError", t.toString());
                }
            });

    }

    private void updateFmsDetails(){

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
                    try {
                        if(response.body().getStatus().equals("Success")){
                            Toast.makeText(getContext(),response.body().getResponse().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<CommonClassResponse> call, Throwable t) {
                Log.e("RetroError", t.toString());
            }
        });

    }

    private void updateWcrEnginer(){

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
                    try {
                        if(response.body().getStatus().equals("Success")){
                            Toast.makeText(getContext(),response.body().getResponse().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<CommonClassResponse> call, Throwable t) {
                Log.e("RetroError", t.toString());
            }
        });

    }
}