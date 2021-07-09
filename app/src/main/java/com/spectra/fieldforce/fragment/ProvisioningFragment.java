package com.spectra.fieldforce.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.spectra.fieldforce.R;
import com.spectra.fieldforce.activity.BucketTabActivity;
import com.spectra.fieldforce.activity.ProvisioningScreenActivity;
import com.spectra.fieldforce.api.ApiClient;
import com.spectra.fieldforce.api.ApiInterface;
import com.spectra.fieldforce.databinding.ProvisionFragmentBinding;
import com.spectra.fieldforce.model.gpon.request.AccountInfoRequest;
import com.spectra.fieldforce.model.gpon.response.AccInfoResponse;
import com.spectra.fieldforce.utils.Constants;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProvisioningFragment extends Fragment implements View.OnClickListener{
    private String strCanId,canId,city,area,building,segment,statusReport,orderId;
    private ProvisionFragmentBinding provisionFragmentBinding;
    public ProvisioningFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        provisionFragmentBinding = ProvisionFragmentBinding.inflate(inflater, container, false);
        return provisionFragmentBinding.getRoot();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.rl_back) {
          nextScreen();
        }
    }

    private void nextScreen(){
        Intent i = new Intent(getActivity(), BucketTabActivity.class);
        startActivity(i);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        getActivity().finish();
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        canId = requireArguments().getString("canId");
        if(getArguments()!=null){
            orderId = requireArguments().getString("OrderId");
        }

        getAccountDetails();

        provisionFragmentBinding.searchtoolbar.rlBack.setOnClickListener(this);

        init();
    }

    public void getAccountDetails() {
        AccountInfoRequest accountInfoRequest = new AccountInfoRequest();
        accountInfoRequest.setAuthkey(Constants.AUTH_KEY);
        accountInfoRequest.setAction(Constants.GET_ACCOUNT_INFO);
        accountInfoRequest.setCanId(canId);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<AccInfoResponse> call = apiService.getAccountInfo(accountInfoRequest);
        call.enqueue(new Callback<AccInfoResponse>() {
            @Override
            public void onResponse(Call<AccInfoResponse> call, Response<AccInfoResponse> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                    if(response.body().status.equals("Success")){
                        try {
                            provisionFragmentBinding.setProvisioning(response.body().response);
                            segment = response.body().response.segment;
                            statusReport = response.body().response.statusofReport;
                            setDate();
                           // nextScreen();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else if(response.body().status.equals("Failure")){
                        Toast.makeText(getContext(),"Account Id (CAN Id) does not exist or Inactive.",Toast.LENGTH_LONG).show();
                        nextScreen();
                    }

                }

            }

            @Override
            public void onFailure(Call<AccInfoResponse> call, Throwable t) {
                Log.e("RetroError", t.toString());
            }
        });
    }

    private void setDate(){
        if(segment.equals("Home")){
            provisionFragmentBinding.tvIr.setVisibility(View.INVISIBLE);
        }else if(statusReport.equals("Installation Completed")|| statusReport.equals("Completed")){
            if(segment.equals("Business")){
                provisionFragmentBinding.tvIr.setVisibility(View.VISIBLE);
            }else{
                provisionFragmentBinding.tvIr.setVisibility(View.INVISIBLE);
            }

        }else{
            provisionFragmentBinding.tvIr.setVisibility(View.INVISIBLE);
        }
        provisionFragmentBinding.searchtoolbar.tvLang.setText("Provisioning");
        if(statusReport.equals("Installation Pending")||statusReport.equals("Pending")||statusReport.equals("Installation On Hold")){
            provisionFragmentBinding.tvWcr.setText("WCR");
            provisionFragmentBinding.tvWcr.setOnClickListener(v -> {
                @SuppressLint("UseRequireInsteadOfGet") FragmentTransaction t1= Objects.requireNonNull(this.getFragmentManager()).beginTransaction();
                WcrFragment wcrFragment = new WcrFragment();
                Bundle accountinfo = new Bundle();
                accountinfo.putString("StatusofReport",statusReport);
                accountinfo.putString("canId", canId);
                accountinfo.putString("OrderId",orderId);
                t1.replace(R.id.frag_container, wcrFragment);
                wcrFragment.setArguments(accountinfo);
                t1.commit();
            });
        }else if(statusReport.equals("Installation Completed")|| statusReport.equals("Completed")){
            provisionFragmentBinding.tvWcr.setText("WCR Completed");
            provisionFragmentBinding.tvWcr.setOnClickListener(v -> {
                @SuppressLint("UseRequireInsteadOfGet") FragmentTransaction t= Objects.requireNonNull(this.getFragmentManager()).beginTransaction();
                WcrCompletedFragment wcrCompletedFragment = new WcrCompletedFragment();
                Bundle accountinfo = new Bundle();
                accountinfo.putString("canId", canId);
                accountinfo.putString("StatusofReport",statusReport);
                accountinfo.putString("OrderId",orderId);
                t.replace(R.id.frag_container, wcrCompletedFragment);
                wcrCompletedFragment.setArguments(accountinfo);
                t.commit();
            });
        }
    }

    private void init(){
        provisionFragmentBinding.tvProvisioning.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), ProvisioningScreenActivity.class);
          //  i.putExtra("name", name);
            i.putExtra("canId", canId);
            i.putExtra("StatusofReport",statusReport);
            i.putExtra("OrderId",orderId);
            startActivity(i);
            getActivity().finish();
        });

        provisionFragmentBinding.tvIr.setOnClickListener(v -> {
            @SuppressLint("UseRequireInsteadOfGet") FragmentTransaction t= Objects.requireNonNull(this.getFragmentManager()).beginTransaction();
            IRFragment irFragment = new IRFragment();
            Bundle accountinfo = new Bundle();
            accountinfo.putString("canId", canId);
            accountinfo.putString("StatusofReport",statusReport);
            accountinfo.putString("OrderId",orderId);
            t.replace(R.id.frag_container, irFragment);
            irFragment.setArguments(accountinfo);
            t.commit();
        });

    }
}