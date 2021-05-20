package com.spectra.fieldforce.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.spectra.fieldforce.model.gpon.request.AccountInfoRequest;
import com.spectra.fieldforce.model.gpon.response.AccInfoResponse;
import com.spectra.fieldforce.R;
import com.spectra.fieldforce.api.ApiClient;
import com.spectra.fieldforce.api.ApiInterface;
import com.spectra.fieldforce.utils.Constants;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProvisioningTabFragment extends Fragment {
    private TextView tv_get_profile_info;
     AppCompatEditText search_account_num;
    private String name,canId,city,area,building,segment;

    public ProvisioningTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.provisioning_tab_screen, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_get_profile_info = view.findViewById(R.id.tv_get_profile_info);
        search_account_num = view.findViewById(R.id.search_account_num);
        init();
    }

    private void init(){
       tv_get_profile_info.setOnClickListener(v -> {
           getAccountDetails();
         /*  @SuppressLint("UseRequireInsteadOfGet") FragmentTransaction t11= Objects.requireNonNull(this.getFragmentManager()).beginTransaction();
           ProvisioningFragment provisioningFragment = new ProvisioningFragment();
           t11.replace(R.id.frag_container, provisioningFragment);
           t11.commit();*/
       });


    }

    public void getAccountDetails() {
        AccountInfoRequest accountInfoRequest = new AccountInfoRequest();
        accountInfoRequest.setAuthkey(Constants.AUTH_KEY);
        accountInfoRequest.setAction(Constants.GET_ACCOUNT_INFO);
        accountInfoRequest.setCanId("221373");

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<AccInfoResponse> call = apiService.getAccountInfo(accountInfoRequest);
        call.enqueue(new Callback<AccInfoResponse>() {
            @Override
            public void onResponse(Call<AccInfoResponse> call, Response<AccInfoResponse> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                    try {
                       name = response.body().response.name;
                       canId = response.body().response.cANID;
                       city = response.body().response.city;
                       area = response.body().response.area;
                       building = response.body().response.society;
                       segment = response.body().response.segment;
                       nextScreen();
                    } catch (Exception e) {
                        e.printStackTrace();
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
        @SuppressLint("UseRequireInsteadOfGet") FragmentTransaction t11= Objects.requireNonNull(this.getFragmentManager()).beginTransaction();
        ProvisioningFragment provisioningFragment = new ProvisioningFragment();
        t11.replace(R.id.frag_container, provisioningFragment);
        provisioningFragment.setArguments(accountinfo);
        t11.commit();
    }



}