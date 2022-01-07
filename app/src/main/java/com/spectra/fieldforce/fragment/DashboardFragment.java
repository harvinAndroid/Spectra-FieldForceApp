package com.spectra.fieldforce.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.spectra.fieldforce.activity.BucketTabActivity;
import com.spectra.fieldforce.activity.DashBoardActivity;
import com.spectra.fieldforce.activity.SpectraFfaActivity;
import com.spectra.fieldforce.api.ApiClient;
import com.spectra.fieldforce.api.ApiInterface;
import com.spectra.fieldforce.databinding.MainDashboradBinding;
import com.spectra.fieldforce.salesapp.activity.SalesDashboard;
import com.spectra.fieldforce.salesapp.model.ValidateResponse;
import com.spectra.fieldforce.salesapp.model.ValidateSalesResponse;
import com.spectra.fieldforce.salesapp.model.ValidateUserRequest;
import com.spectra.fieldforce.utils.Constants;
import com.spectra.fieldforce.utils.PrefConfig;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardFragment extends Fragment {
    MainDashboradBinding binding;
    public static PrefConfig prefConfig;
    DashBoardActivity context;
    public static final String PREF ="Login";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = MainDashboradBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prefConfig = new PrefConfig(getActivity());
        init();
       // validateUser();
       /* binding.linearSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateSalesUser("S");
            }
        });
        binding.linearFfa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateUser("F");
            }
        });
        binding.linearGpon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateUser("I");
            }
        });*/

    }

    private void validateUser(String f) {
        ValidateUserRequest validateUserRequest = new ValidateUserRequest(Constants.VALIDATE_USER,Constants.AUTH_KEY,"Manager1","Target@2021#@",f);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ValidateResponse> call = apiService.validateUserReq(validateUserRequest);
        call.enqueue(new Callback<ValidateResponse>() {
            @Override
            public void onResponse(Call<ValidateResponse> call, Response<ValidateResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if(response.body().getStatus().equals("Success")) {

                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ValidateResponse> call, Throwable t) {

            }
        });
    }

    private void validateSalesUser(String s) {
        ValidateUserRequest validateUserRequest = new ValidateUserRequest(Constants.VALIDATE_USER,Constants.AUTH_KEY,"Manager1","Target@2021#@",s);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ValidateSalesResponse> call = apiService.validateSalesUserReq(validateUserRequest);
        call.enqueue(new Callback<ValidateSalesResponse>() {
            @Override
            public void onResponse(Call<ValidateSalesResponse> call, Response<ValidateSalesResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if(response.body().getStatus().equals("Success")) {

                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ValidateSalesResponse> call, Throwable t) {

            }
        });
    }


    private void init(){
        SharedPreferences sp = getActivity().getSharedPreferences(PREF , 0);
        String ffa =sp.getString("FFA", null);
        String installationAuth =sp.getString("InstallationAuth", null);

            binding.linearGpon.setOnClickListener(v -> {
                if(installationAuth!=null) {
                    if (installationAuth.equals("Y")) {
                        Intent i = new Intent(getActivity(), BucketTabActivity.class);
                        startActivity(i);
                        getActivity().finish();
                    } else if (installationAuth.equals("N")) {
                        Toast.makeText(getActivity(), "Permission Required", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getActivity(), "Something went wrong...", Toast.LENGTH_LONG).show();

                }
            });

            binding.linearFfa.setOnClickListener(v -> {
                if(ffa!=null){
                if(ffa.equals("Y")) {
                    Intent i = new Intent(getActivity(), SpectraFfaActivity.class);
                    startActivity(i);
                    getActivity().finish();
                }else if(ffa.equals("N")){
                    Toast.makeText(getActivity(),"Permission Required",Toast.LENGTH_LONG).show();
                }}else{
                    Toast.makeText(getActivity(), "Something went wrong...", Toast.LENGTH_LONG).show();

                }
            });
        binding.linearSales.setOnClickListener(view -> {
            Intent i = new Intent(getActivity(), SalesDashboard.class);
            startActivity(i);
            getActivity().finish();
        });
    }


}
