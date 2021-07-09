package com.spectra.fieldforce.activity;

import android.annotation.SuppressLint;
import android.content.Context;
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
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.spectra.fieldforce.R;
import com.spectra.fieldforce.api.ApiClient;
import com.spectra.fieldforce.api.ApiInterface;
import com.spectra.fieldforce.databinding.ProvisiongScreenFragmentBinding;
import com.spectra.fieldforce.fragment.DashboardFragment;
import com.spectra.fieldforce.fragment.ProvisioningFragment;
import com.spectra.fieldforce.fragment.WelcomeFragment;
import com.spectra.fieldforce.model.gpon.request.AccountInfoRequest;
import com.spectra.fieldforce.model.gpon.request.AddProvisioningRequest;
import com.spectra.fieldforce.model.gpon.response.AccountDetailsResponse;
import com.spectra.fieldforce.model.gpon.response.CommonClassResponse;
import com.spectra.fieldforce.utils.Constants;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProvisioningScreenActivity extends BaseActivity implements AdapterView.OnItemSelectedListener,View.OnClickListener{
    private ProvisiongScreenFragmentBinding binding;
    private String strModel,strTowerId,strSplitterId,statusReport,orderId;
    private ArrayList<String> modelName;
    private ArrayList<String> modelId;
    private String strCanId ;
    private ArrayList<AccountDetailsResponse.OnuProfile> onuProfile;
    private AlphaAnimation inAnimation;
    private AlphaAnimation outAnimation;
   /* AppCompatActivity activity;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.provisiong_screen_fragment);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            strCanId = extras.getString("canId");
            statusReport = extras.getString("StatusofReport");
            orderId = extras.getString("OrderId");
        }
        binding.searchtoolbar.rlBack.setOnClickListener(this);
        binding.searchtoolbar.tvLang.setText("Provisioning ");
        init();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.rl_back) {
          nextScreen();
        }
    }

    private void init(){
        getAccountDetails();
        binding.tvProvisioningSave.setOnClickListener(v -> {
            String ont = Objects.requireNonNull(binding.etEnterOnt.getText()).toString();
            String reont = Objects.requireNonNull(binding.reEnterOnt.getText()).toString();
            String profilename = Objects.requireNonNull(binding.tvProfileName.getText()).toString();
            String tower = Objects.requireNonNull(binding.tvTower.getText()).toString();
            String serving = Objects.requireNonNull(binding.tvServingDb.getText()).toString();
            String model = Objects.requireNonNull(binding.tvModel.getText()).toString();


            if(ont.isEmpty()){
                Toast.makeText(this,"Please Enter ONT Serial Number",Toast.LENGTH_LONG).show();
            }else if(reont.isEmpty()){
                Toast.makeText(this,"Please Enter Re enter ONT Serial Number",Toast.LENGTH_LONG).show();
            }else if (model.equals("Select Model Name")) {
                Toast.makeText(this, "Please Select Model Name", Toast.LENGTH_LONG).show();
            }else if(profilename.isEmpty()|| profilename == null){
                Toast.makeText(this,"Please Enter Profile Name",Toast.LENGTH_LONG).show();
            }
            else if(tower.isEmpty()){
                Toast.makeText(this,"Please Enter Tower/Structure",Toast.LENGTH_LONG).show();
            }
            else if(serving.isEmpty()){
                Toast.makeText(this,"Please Enter Serving db",Toast.LENGTH_LONG).show();
            }else if(ont.equals(reont)){
                updateProvisioning();
            } else{
                Toast.makeText(this,"ONT And ReOnt Value Mismatched",Toast.LENGTH_LONG).show();
            }

        });
    }

    public void getAccountDetails() {

        try {
            binding.tvModel.setOnClickListener(v -> binding.spModel.performClick());
            binding.spModel.setOnItemSelectedListener(this);
            inAnimation = new AlphaAnimation(0f, 1f);
            inAnimation.setDuration(200);
            binding.progressLayout.progressOverlay.setAnimation(inAnimation);
            binding.progressLayout.progressOverlay.setVisibility(View.VISIBLE);
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
                        outAnimation = new AlphaAnimation(1f, 0f);
                        outAnimation.setDuration(200);
                        binding.progressLayout.progressOverlay.setAnimation(outAnimation);
                        binding.progressLayout.progressOverlay.setVisibility(View.GONE);

                        if (response.body().getStatus().equals("Success")) {
                            try {
                                binding.setResponse(response.body().getResponse().data);
                                onuProfile = response.body().getResponse().getData().getOnuProfile();
                                strTowerId = response.body().getResponse().getData().getTowerDetail().get(0).getTowerId();
                                strSplitterId = response.body().getResponse().getData().getTowerDetail().get(0).getServingDB();
                                modelName = new ArrayList<>();
                                modelId = new ArrayList<>();
                                modelName.add("Select Model Name");
                                for (AccountDetailsResponse.OnuProfile onuProfile : onuProfile)
                                    modelName.add(onuProfile.getModelName());
                                for (AccountDetailsResponse.OnuProfile id : onuProfile)
                                    modelId.add(id.getProfileName());
                                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(ProvisioningScreenActivity.this, android.R.layout.simple_spinner_item, modelName);
                                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                binding.spModel.setAdapter(adapter1);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else if (response.body().getStatus().equals("Failure")) {
                            Toast.makeText(ProvisioningScreenActivity.this, response.body().getResponse().getMessage(), Toast.LENGTH_LONG).show();
                            nextScreen();
                        }
                    }

                }

                @Override
                public void onFailure(Call<AccountDetailsResponse> call, Throwable t) {
                    binding.progressLayout.progressOverlay.setVisibility(View.GONE);
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
        inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        binding.progressLayout.progressOverlay.setAnimation(inAnimation);
        binding.progressLayout.progressOverlay.setVisibility(View.VISIBLE);

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
                    outAnimation = new AlphaAnimation(1f, 0f);
                    outAnimation.setDuration(200);
                    binding.progressLayout.progressOverlay.setAnimation(outAnimation);
                    binding.progressLayout.progressOverlay.setVisibility(View.GONE);
                    try {
                        if(response.body().getStatus().equals("Success")){
                            Toast.makeText(ProvisioningScreenActivity.this,response.body().getResponse().getMessage(),Toast.LENGTH_LONG).show();
                            nextScreen();
                        }else if (response.body().getStatus().equals("Failure")) {
                            Toast.makeText(ProvisioningScreenActivity.this, response.body().getResponse().getMessage(), Toast.LENGTH_LONG).show();
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
    public void onBackPressed() {
        super.onBackPressed();
        nextScreen();
    }

    private void nextScreen(){
        ProvisioningFragment newFragment = new ProvisioningFragment();
        Bundle args = new Bundle();
        args.putString("canId", strCanId);
        args.putString("StatusofReport", statusReport);
        args.putString("OrderId", orderId);
        newFragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frag_container, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }



}