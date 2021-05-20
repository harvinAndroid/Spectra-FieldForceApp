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

import com.spectra.fieldforce.databinding.WcrFragmentBinding;
import com.spectra.fieldforce.model.gpon.request.AccountInfoRequest;
import com.spectra.fieldforce.model.gpon.response.IrInfoResponse;
import com.spectra.fieldforce.R;
import com.spectra.fieldforce.api.ApiClient;
import com.spectra.fieldforce.api.ApiInterface;
import com.spectra.fieldforce.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class IRFragment  extends Fragment {


    public IRFragment() {

    }

   /* @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = WcrFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.ir_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getIrInfo();
    }

    private void getIrInfo(){
        AccountInfoRequest accountInfoRequest = new AccountInfoRequest();
        accountInfoRequest.setAuthkey(Constants.AUTH_KEY);
        accountInfoRequest.setAction(Constants.GET_IR_INFO);
        accountInfoRequest.setCanId("9083252");

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<IrInfoResponse> call = apiService.getIrInfo(accountInfoRequest);
        call.enqueue(new Callback<IrInfoResponse>() {
            @Override
            public void onResponse(Call<IrInfoResponse> call, Response<IrInfoResponse> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                    try {

                     } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<IrInfoResponse> call, Throwable t) {
                Log.e("RetroError", t.toString());
            }
        });
    }
}
