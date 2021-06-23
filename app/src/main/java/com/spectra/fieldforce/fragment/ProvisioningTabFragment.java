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
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.spectra.fieldforce.activity.BucketTabActivity;
import com.spectra.fieldforce.activity.MainActivity;
import com.spectra.fieldforce.databinding.ProvisionFaultTabScreenBinding;
import com.spectra.fieldforce.databinding.ProvisioningTabScreenBinding;
import com.spectra.fieldforce.databinding.WcrAddItemConsumptionBinding;
import com.spectra.fieldforce.model.gpon.request.AccountInfoRequest;
import com.spectra.fieldforce.model.gpon.response.AccInfoResponse;
import com.spectra.fieldforce.R;
import com.spectra.fieldforce.api.ApiClient;
import com.spectra.fieldforce.api.ApiInterface;
import com.spectra.fieldforce.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProvisioningTabFragment extends Fragment implements View.OnClickListener{
    ProvisioningTabScreenBinding binding;
    private String name,canId,city,area,building,segment,statusReport,strCanId;

    public ProvisioningTabFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = ProvisioningTabScreenBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.searchtoolbar.rlBack.setOnClickListener(this);
        binding.searchtoolbar.tvLang.setText("Provisioning");
        strCanId = requireArguments().getString("canId");
        init();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.rl_back) {
            Intent i = new Intent(getActivity(), BucketTabActivity.class);
            startActivity(i);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            getActivity().finish();
        }
    }

    private void init(){
        binding.searchAccountNum.setText(strCanId);
       binding.tvGetProfileInfo.setOnClickListener(v -> {

         String accountid = binding.searchAccountNum.getText().toString();
           if(accountid.isEmpty()){
               Toast.makeText(getContext(),"Please Enter CANID",Toast.LENGTH_LONG).show();
           }else{
               getAccountDetails();
           }

       });
    }

    public void getAccountDetails() {
        AccountInfoRequest accountInfoRequest = new AccountInfoRequest();
        accountInfoRequest.setAuthkey(Constants.AUTH_KEY);
        accountInfoRequest.setAction(Constants.GET_ACCOUNT_INFO);
        accountInfoRequest.setCanId(binding.searchAccountNum.getText().toString());
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<AccInfoResponse> call = apiService.getAccountInfo(accountInfoRequest);
        call.enqueue(new Callback<AccInfoResponse>() {
            @Override
            public void onResponse(Call<AccInfoResponse> call, Response<AccInfoResponse> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                    if(response.body().status.equals("Success")){
                        try {
                            name = response.body().response.name;
                            canId = response.body().response.cANID;
                            city = response.body().response.city;
                            area = response.body().response.area;
                            building = response.body().response.society;
                            segment = response.body().response.segment;
                            statusReport = response.body().response.statusofReport;
                            nextScreen();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else if(response.body().status.equals("Failure")){
                            Toast.makeText(getContext(),"Account Id (CAN Id) does not exist or Inactive.",Toast.LENGTH_LONG).show();
                    }

                }

            }

            @Override
            public void onFailure(Call<AccInfoResponse> call, Throwable t) {
                Log.e("RetroError", t.toString());
            }
        });
    }

    private void nextScreen(){
        Bundle accountinfo = new Bundle();
        accountinfo.putString("name", name);
        accountinfo.putString("canId", canId);
        accountinfo.putString("city", city);
        accountinfo.putString("area", area);
        accountinfo.putString("building", building);
        accountinfo.putString("segment",segment);
        accountinfo.putString("statusReport",statusReport);
        @SuppressLint("UseRequireInsteadOfGet") FragmentTransaction t11= Objects.requireNonNull(this.getFragmentManager()).beginTransaction();
        ProvisioningFragment provisioningFragment = new ProvisioningFragment();
        t11.replace(R.id.frag_container, provisioningFragment);
        provisioningFragment.setArguments(accountinfo);
        t11.commit();
    }



}