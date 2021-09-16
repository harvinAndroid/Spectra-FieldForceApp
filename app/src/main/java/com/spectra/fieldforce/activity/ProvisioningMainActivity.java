package com.spectra.fieldforce.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;

import com.spectra.fieldforce.R;
import com.spectra.fieldforce.api.ApiClient;
import com.spectra.fieldforce.api.ApiInterface;
import com.spectra.fieldforce.databinding.ProvisionFragmentBinding;
import com.spectra.fieldforce.fragment.IRFragment;
import com.spectra.fieldforce.fragment.WcrCompletedFragment;
import com.spectra.fieldforce.fragment.WcrFragment;
import com.spectra.fieldforce.model.gpon.request.AccountInfoRequest;
import com.spectra.fieldforce.model.gpon.response.AccInfoResponse;
import com.spectra.fieldforce.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProvisioningMainActivity extends BaseActivity implements View.OnClickListener{
    private String strCanId,canId,city,area,building,segment,statusReport,orderId,IrStatusReport,provButton;
    private Boolean  IrStatus,WcrStatus;
    private ProvisionFragmentBinding provisionFragmentBinding;
    private AlphaAnimation inAnimation;
    private AlphaAnimation outAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        provisionFragmentBinding = DataBindingUtil.setContentView(this, R.layout.provision_fragment);
        Bundle bundle=getIntent().getExtras();
        String s=bundle.getString("name");
        canId = bundle.getString("canId");
        if(bundle!=null){
            orderId = bundle.getString("OrderId");

        }

        getAccountDetails();


        provisionFragmentBinding.searchtoolbar.rlBack.setOnClickListener(this);

        init();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.rl_back) {
          nextScreen();
        }
    }

    private void nextScreen(){
        Intent i = new Intent(this, BucketTabActivity.class);
        startActivity(i);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
    }

    public void getAccountDetails() {
        inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        provisionFragmentBinding.progressLayout.progressOverlay.setAnimation(inAnimation);
        provisionFragmentBinding.progressLayout.progressOverlay.setVisibility(View.VISIBLE);
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
                        outAnimation = new AlphaAnimation(1f, 0f);
                        outAnimation.setDuration(200);
                        provisionFragmentBinding.progressLayout.progressOverlay.setAnimation(outAnimation);
                        provisionFragmentBinding.progressLayout.progressOverlay.setVisibility(View.GONE);
                        try {
                            provisionFragmentBinding.setProvisioning(response.body().response);
                            segment = response.body().response.segment;
                            statusReport = response.body().response.statusofReport;
                            IrStatus = response.body().response.iRStatus;
                            WcrStatus = response.body().response.wCRStatus;
                            IrStatusReport = response.body().response.IRStatusofReport;
                            provButton = response.body().response.IsProShow;
                            setDate();
                           // nextScreen();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else if(response.body().status.equals("Failure")){
                        Toast.makeText(ProvisioningMainActivity.this,"Account Id (CAN Id) does not exist or Inactive.",Toast.LENGTH_LONG).show();
                        nextScreen();
                    }

                }

            }

            @Override
            public void onFailure(Call<AccInfoResponse> call, Throwable t) {
                provisionFragmentBinding.progressLayout.progressOverlay.setVisibility(View.GONE);
                Log.e("RetroError", t.toString());
            }
        });
    }

    private void setDate(){
        if(segment.equals("Home")){
            provisionFragmentBinding.tvIr.setVisibility(View.GONE);
        }else if(statusReport.equals("Installation Completed")|| statusReport.equals("Completed")){
            if(segment.equals("Business")){
                provisionFragmentBinding.tvIr.setVisibility(View.VISIBLE);
            }else{
                provisionFragmentBinding.tvIr.setVisibility(View.GONE);
            }
        }else{
            provisionFragmentBinding.tvIr.setVisibility(View.GONE);
        }
        if(provButton.equals("true")){
            provisionFragmentBinding.tvProvisioning.setVisibility(View.VISIBLE);
        }else{
            provisionFragmentBinding.tvProvisioning.setVisibility(View.GONE);
        }
        provisionFragmentBinding.searchtoolbar.tvLang.setText("Provisioning");
        if(statusReport.equals("Installation Pending")||statusReport.equals("Pending")||statusReport.equals("Installation On Hold")){
            provisionFragmentBinding.tvWcr.setText("WCR");
            provisionFragmentBinding.tvWcr.setOnClickListener(v -> {
                WcrFragment wcrFragment = new WcrFragment();
                Bundle accountinfo = new Bundle();
                accountinfo.putString("StatusofReport",statusReport);
                accountinfo.putString("canId", canId);
                accountinfo.putString("OrderId",orderId);
                accountinfo.putBoolean("WcrStatus",WcrStatus);
                wcrFragment.setArguments(accountinfo);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frag_container, wcrFragment);
                transaction.addToBackStack(null);
                transaction.commit();

            });
        }else if(statusReport.equals("Installation Completed")|| statusReport.equals("Completed")){
            provisionFragmentBinding.tvWcr.setText("WCR Completed");
            provisionFragmentBinding.tvWcr.setOnClickListener(v -> {
                WcrCompletedFragment wcrCompletedFragment = new WcrCompletedFragment();
                Bundle accountinfo = new Bundle();
                accountinfo.putString("canId", canId);
                accountinfo.putString("StatusofReport",statusReport);
                accountinfo.putString("OrderId",orderId);
              //  accountinfo.putBoolean("IrStatus",IrStatus);
                wcrCompletedFragment.setArguments(accountinfo);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frag_container, wcrCompletedFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            });
        }
    }

    private void init(){
        provisionFragmentBinding.tvProvisioning.setOnClickListener(v -> {
            Intent i = new Intent(this, ProvisioningScreenActivity.class);
          //  i.putExtra("name", name);
            i.putExtra("canId", canId);
            i.putExtra("StatusofReport",statusReport);
            i.putExtra("OrderId",orderId);
            startActivity(i);
           finish();
        });

        provisionFragmentBinding.tvIr.setOnClickListener(v -> {
            IRFragment irFragment = new IRFragment();
            Bundle accountinfo = new Bundle();
            accountinfo.putString("canId", canId);
            accountinfo.putString("StatusofReport",statusReport);
            accountinfo.putString("OrderId",orderId);
           accountinfo.putString("IrStatusReport",IrStatusReport);
            accountinfo.putBoolean("IrStatus",IrStatus);
            irFragment.setArguments(accountinfo);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frag_container, irFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });

    }
}