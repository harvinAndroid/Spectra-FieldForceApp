package com.spectra.fieldforce.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.spectra.fieldforce.activity.BucketTabActivity;
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
   // DashBoardActivity context;
    private String emailId,password;
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

        SharedPreferences sp1=getActivity().getSharedPreferences("Login",0);
         emailId =sp1.getString("EmailId", null);
        password = sp1.getString("Password", null);
        binding.linearSales.setOnClickListener(view1 -> validateSalesUser("S"));
        binding.linearFfa.setOnClickListener(view13 -> validateffa("F"));
        binding.linearGpon.setOnClickListener(view12 ->
                validateGpon("I"));

        binding.linearDepro.setOnClickListener(view12 ->
                next()

        );

    }
    private void next(){
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.GPON)));
       /* Intent send = new Intent(getActivity(), DeProvisionIng.class);
        startActivity(send);*/
    }

    private void validateffa(String f) {
        ValidateUserRequest validateUserRequest = new ValidateUserRequest(Constants.VALIDATE_USER,Constants.AUTH_KEY,emailId,password,f);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ValidateResponse> call = apiService.validateUserReq(validateUserRequest);
        call.enqueue(new Callback<ValidateResponse>() {
            @Override
            public void onResponse(Call<ValidateResponse> call, Response<ValidateResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if(response.body().getStatus().equals("Success")) {
                                SharedPreferences sp = getActivity().getSharedPreferences(PREF , 0);
                                SharedPreferences.Editor myEdit = sp.edit();
                                myEdit.putString("VenderCode", response.body().getResponse().get(0).getVendorCode());
                                myEdit.putString("EnggId", response.body().getResponse().get(0).getUserId());
                                myEdit.putString("EnggName",response.body().getResponse().get(0).getName());
                                myEdit.putString("EmailId",response.body().getResponse().get(0).getEmailId());
                                myEdit.commit();
                                Intent i = new Intent(getActivity(), SpectraFfaActivity.class);
                                startActivity(i);
                                getActivity().finish();
                                }else if(response.body().getStatus().equals("Failure")) {
                                Toast.makeText(getContext(), "Access Denied", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ValidateResponse> call, Throwable t) {
               Toast.makeText(getContext(), "Access Denied", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void validateGpon(String f) {
        ValidateUserRequest validateUserRequest = new ValidateUserRequest(Constants.VALIDATE_USER,Constants.AUTH_KEY,emailId,password,f);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ValidateResponse> call = apiService.validateUserReq(validateUserRequest);
        call.enqueue(new Callback<ValidateResponse>() {
            @Override
            public void onResponse(Call<ValidateResponse> call, Response<ValidateResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if(response.body().getStatus().equals("Success")) {
                                SharedPreferences sp = getActivity().getSharedPreferences(PREF , 0);
                                SharedPreferences.Editor myEdit = sp.edit();
                                myEdit.putString("VenderCode", response.body().getResponse().get(0).getVendorCode());
                                myEdit.putString("EnggId", response.body().getResponse().get(0).getUserId());
                                myEdit.putString("EnggName",response.body().getResponse().get(0).getName());
                                myEdit.putString("EmailId",response.body().getResponse().get(0).getEmailId());
                                myEdit.commit();
                                Intent i = new Intent(getActivity(), BucketTabActivity.class);
                                startActivity(i);
                                getActivity().finish();
                            }else  if(response.body().getStatus().equals("Failure")) {
                                Toast.makeText(getContext(), "Access Denied", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ValidateResponse> call, Throwable t) {
               Toast.makeText(getContext(), "Access Denied", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void validateSalesUser(String s) {
        ValidateUserRequest validateUserRequest = new ValidateUserRequest(Constants.VALIDATE_USER,Constants.AUTH_KEY,emailId,password,s);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ValidateSalesResponse> call = apiService.validateSalesUserReq(validateUserRequest);
        call.enqueue(new Callback<ValidateSalesResponse>() {
            @Override
            public void onResponse(Call<ValidateSalesResponse> call, Response<ValidateSalesResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getResponse().getStatusCode() == 200) {
                                SharedPreferences sp = requireActivity().getSharedPreferences(PREF , 0);
                                SharedPreferences.Editor myEdit = sp.edit();
                                myEdit.putString("EnggId", response.body().getResponse().getData().getEmployeeID());
                                myEdit.putString("EnggName",response.body().getResponse().getData().getUserName());
                                myEdit.putString("EmailId",response.body().getResponse().getData().getPrimaryEmail());
                                myEdit.commit();
                                Intent i = new Intent(getActivity(), SalesDashboard.class);
                                startActivity(i);
                                requireActivity().finish();
                            } else if (response.body().getStatus().equals("Failure")) {
                                Toast.makeText(requireContext(), "Access Denied", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ValidateSalesResponse> call, Throwable t) {
                Toast.makeText(requireContext(), "Access Denied", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
