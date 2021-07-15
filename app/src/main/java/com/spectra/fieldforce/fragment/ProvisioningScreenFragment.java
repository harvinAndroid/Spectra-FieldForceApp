package com.spectra.fieldforce.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.spectra.fieldforce.R;
import com.spectra.fieldforce.activity.BucketTabActivity;
import com.spectra.fieldforce.activity.ProvisioningMainActivity;
import com.spectra.fieldforce.api.ApiClient;
import com.spectra.fieldforce.api.ApiInterface;
import com.spectra.fieldforce.databinding.ProvisionFragmentBinding;
import com.spectra.fieldforce.databinding.ProvisiongScreenFragmentBinding;
import com.spectra.fieldforce.model.gpon.request.AccountInfoRequest;
import com.spectra.fieldforce.model.gpon.request.AddProvisioningRequest;
import com.spectra.fieldforce.model.gpon.request.AssociatedResquest;
import com.spectra.fieldforce.model.gpon.response.AccInfoResponse;
import com.spectra.fieldforce.model.gpon.response.AccountDetailsResponse;
import com.spectra.fieldforce.model.gpon.response.CommonClassResponse;
import com.spectra.fieldforce.model.gpon.response.GetFmsListResponse;
import com.spectra.fieldforce.model.gpon.response.IrInfoResponse;
import com.spectra.fieldforce.utils.Constants;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProvisioningScreenFragment extends Fragment implements AdapterView.OnItemSelectedListener,View.OnClickListener{
    private ProvisiongScreenFragmentBinding binding;
    private String strModel,strTowerId,strSplitterId;
    private ArrayList<String> modelName;
    private ArrayList<String> modelId;
    private String strCanId ;
    private ArrayList<AccountDetailsResponse.OnuProfile> onuProfile;



    public ProvisioningScreenFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = ProvisiongScreenFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.rl_back) {
            Intent i = new Intent(getActivity(), BucketTabActivity.class);
            startActivity(i);
            getActivity().finish();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        strCanId = requireArguments().getString("canId");
        binding.searchtoolbar.rlBack.setOnClickListener(this);
        binding.searchtoolbar.tvLang.setText("Provisioning ");
        init();
    }

    private void init(){
        getAccountDetails();
        binding.tvProvisioningSave.setOnClickListener(v ->
                updateProvisioning());
    }

    public void getAccountDetails() {
        try {
            binding.tvModel.setOnClickListener(v -> binding.spModel.performClick());
            binding.spModel.setOnItemSelectedListener(this);
            AccountInfoRequest accountInfoRequest = new AccountInfoRequest();
            accountInfoRequest.setAuthkey(Constants.AUTH_KEY);
            accountInfoRequest.setAction(Constants.GET_ACCOUNT_DETAILS);
            accountInfoRequest.setCanId(strCanId);

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<AccountDetailsResponse> call = apiService.getAccountDetails(accountInfoRequest);
            call.enqueue(new Callback<AccountDetailsResponse>() {
                @Override
                public void onResponse(Call<AccountDetailsResponse> call, Response<AccountDetailsResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                       if (response.body().getStatus().equals("Success")) {
                            try {
                              /*  binding.setResponse(response.body().getResponse());
                                onuProfile = response.body().getResponse().getOnuProfile();
                                strTowerId = response.body().getResponse().getTowerDetail().get(0).getTowerId();
                                strSplitterId = response.body().getResponse().getTowerDetail().get(0).getServingDB();
                              */  modelName = new ArrayList<>();
                                modelId = new ArrayList<>();
                                modelName.add("Select Model Name");
                                for (AccountDetailsResponse.OnuProfile onuProfile : onuProfile)
                                    modelName.add(onuProfile.getModelName());
                                for (AccountDetailsResponse.OnuProfile id : onuProfile)
                                    modelId.add(id.getProfileName());
                                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, modelName);
                                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                binding.spModel.setAdapter(adapter1);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else if (response.body().getStatus().equals("Failure")) {
                            Toast.makeText(getContext(), "Product is not associated with any ONU profile.", Toast.LENGTH_LONG).show();
                        }
                    }

                }

                @Override
                public void onFailure(Call<AccountDetailsResponse> call, Throwable t) {

                    Log.e("RetroError", t.toString());
                }
            });
        }catch (Exception ex){
            ex.getMessage();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId() == R.id.sp_model){
            binding.tvModel.setText(modelName.get(position));
            if (position != 0) strModel = "" + modelId.get(position - 1);
            else strModel = " ";
            binding.tvProfileName.setText(strModel);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void updateProvisioning(){

        AddProvisioningRequest addProvisioningRequest = new AddProvisioningRequest();
        addProvisioningRequest.setAuthkey(Constants.AUTH_KEY);
        addProvisioningRequest.setAction(Constants.PROVISIONING);
        addProvisioningRequest.setTowerId(strTowerId);
        addProvisioningRequest.setSplitterId(strSplitterId);
        addProvisioningRequest.setProfile(strModel);
        addProvisioningRequest.setCanId(strCanId);
        addProvisioningRequest.setOnuSerial(binding.reEnterOnt.getText().toString());
        addProvisioningRequest.setDescription(strCanId+":"+strTowerId);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonClassResponse> call = apiService.addProvisioning(addProvisioningRequest);
        call.enqueue(new Callback<CommonClassResponse>() {
            @Override
            public void onResponse(Call<CommonClassResponse> call, Response<CommonClassResponse> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                    try {
                        if(response.body().getStatus().equals("Success")){
                            Toast.makeText(getContext(),response.body().getResponse().getMessage(),Toast.LENGTH_LONG).show();
                            nextScreen();
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

    private void nextScreen(){
        Intent i = new Intent(getActivity(), ProvisioningMainActivity.class);
        i.putExtra("canId", strCanId);
        startActivity(i);
        getActivity().finish();
    }


}