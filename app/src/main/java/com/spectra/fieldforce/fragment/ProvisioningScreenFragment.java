package com.spectra.fieldforce.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.spectra.fieldforce.R;
import com.spectra.fieldforce.api.ApiClient;
import com.spectra.fieldforce.api.ApiInterface;
import com.spectra.fieldforce.databinding.ProvisionFragmentBinding;
import com.spectra.fieldforce.databinding.ProvisiongScreenFragmentBinding;
import com.spectra.fieldforce.model.gpon.request.AccountInfoRequest;
import com.spectra.fieldforce.model.gpon.request.AddProvisioningRequest;
import com.spectra.fieldforce.model.gpon.response.AccInfoResponse;
import com.spectra.fieldforce.model.gpon.response.AccountDetailsResponse;
import com.spectra.fieldforce.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProvisioningScreenFragment extends Fragment {
    private ProvisiongScreenFragmentBinding binding;
    private String name,canId,city,area,building,segment;

    public ProvisioningScreenFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = ProvisiongScreenFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init(){
        getAccountDetails();
    }

    public void getAccountDetails() {
        AccountInfoRequest accountInfoRequest = new AccountInfoRequest();
        accountInfoRequest.setAuthkey(Constants.AUTH_KEY);
        accountInfoRequest.setAction(Constants.GET_ACCOUNT_DETAILS);
        accountInfoRequest.setCanId("9081950");

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<AccountDetailsResponse> call = apiService.getAccountDetails(accountInfoRequest);
        call.enqueue(new Callback<AccountDetailsResponse>() {
            @Override
            public void onResponse(Call<AccountDetailsResponse> call, Response<AccountDetailsResponse> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                    try {

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<AccountDetailsResponse> call, Throwable t) {
                Log.e("RetroError", t.toString());
            }
        });
    }
}