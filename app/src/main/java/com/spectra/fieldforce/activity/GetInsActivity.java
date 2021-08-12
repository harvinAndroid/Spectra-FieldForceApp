package com.spectra.fieldforce.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.spectra.fieldforce.R;
import com.spectra.fieldforce.api.ApiClient;
import com.spectra.fieldforce.api.ApiInterface;
import com.spectra.fieldforce.databinding.InsActivityBinding;
import com.spectra.fieldforce.databinding.ProvisiongScreenFragmentBinding;
import com.spectra.fieldforce.model.gpon.request.AccountInfoRequest;
import com.spectra.fieldforce.model.gpon.request.AddProvisioningRequest;
import com.spectra.fieldforce.model.gpon.request.GetINSRequest;
import com.spectra.fieldforce.model.gpon.response.AccountDetailsResponse;
import com.spectra.fieldforce.model.gpon.response.CommonClassResponse;
import com.spectra.fieldforce.model.gpon.response.GetInsResponse;
import com.spectra.fieldforce.utils.Constants;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetInsActivity extends BaseActivity implements View.OnClickListener{
    private InsActivityBinding binding;

    private ArrayList<GetInsResponse.Response> Ins;
    private AlphaAnimation inAnimation,outAnimation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.ins_activity);
        binding.searchtoolbar.rlBack.setOnClickListener(this);
        binding.searchtoolbar.tvLang.setText("INAS");
        init();
    }

    private void init(){


        binding.tvGetProfileInfo.setOnClickListener(view -> {
            String account = binding.searchAccountNum.getText().toString();
         /*   String account ="DSNW2741f5a0" ;
            binding.searchAccountNum.setText(account);*/
            if(account.isEmpty()){
                Toast.makeText(this,"Please Enter CANID/ONT Serial No.",Toast.LENGTH_LONG).show();
            }else{
                getIns(account);
            }

        });
    }

    private void getIns(String account) {
        inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        binding.progressLayout.progressOverlay.setAnimation(inAnimation);
        binding.progressLayout.progressOverlay.setVisibility(View.VISIBLE);
        GetINSRequest getINSRequest = new GetINSRequest(Constants.GET_POWER_BYONT,Constants.AUTH_KEY,account,account);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<GetInsResponse> call = apiService.getInsDetails(getINSRequest);
        call.enqueue(new Callback<GetInsResponse>() {
            @Override
            public void onResponse(Call<GetInsResponse> call, Response<GetInsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    outAnimation = new AlphaAnimation(1f, 0f);
                    outAnimation.setDuration(200);
                    binding.progressLayout.progressOverlay.setAnimation(outAnimation);
                    binding.progressLayout.progressOverlay.setVisibility(View.GONE);
                    try {
                        if (response.body().getStatus().equals("Success")) {
                            binding.linear.setVisibility(View.VISIBLE);
                            binding.setIns(response.body().getResponse());
                            float Power = Float.parseFloat((response.body().getResponse().getPowerLevel()));
                            if ((Power <= -10.0) && (Power >= -25.0)){
                                binding.tvPower.setTextColor(getResources().getColor(R.color.green));
                            }
                            else if ((Power < -25.1) && (Power >= -29.0)) {
                                binding.tvPower.setTextColor(getResources().getColor(R.color.yellow));
                            }
                            else if (Power <= -29.1) {
                                binding.tvPower.setTextColor(getResources().getColor(R.color.colorRed));
                            }
                            else if (Power > -10) {
                                binding.tvPower.setTextColor(getResources().getColor(R.color.colorRed));
                            }
                            String ActStatus = response.body().getResponse().getActiveStatus();
                            if (ActStatus.equals("active")) {
                                binding.status.setTextColor(getResources().getColor(R.color.green));
                            }
                            else {
                                binding.status.setTextColor(getResources().getColor(R.color.colorRed));
                            }

                            String Alarm = response.body().getResponse().getAlarmsInfo();
                            if ((Alarm.equals("lofi")) || (Alarm.equals("los")) || (Alarm.equals("lof"))) {
                                binding.alarm.setTextColor(getResources().getColor(R.color.colorRed));
                            }
                            else if ((Alarm.equals("losi")) || (Alarm.equals("sfi"))|| (Alarm.equals("sdi"))) {
                                binding.alarm.setTextColor(getResources().getColor(R.color.col));
                            }
                            else if (Alarm.equals("dgi")) {
                                binding.alarm.setTextColor(getResources().getColor(R.color.yellow));
                            }

                        } else if (response.body().getStatus().equals("Failure")) {
                            binding.linear.setVisibility(View.GONE);
                            Toast.makeText(GetInsActivity.this, response.body().getResponse().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetInsResponse> call, Throwable t) {
                binding.progressLayout.progressOverlay.setVisibility(View.GONE);
                Log.e("RetroError", t.toString());
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        next();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.rl_back) {
          next();
        }
    }
    private void next(){
        switchActivity(MainActivity.class);
        finish();
    }

}