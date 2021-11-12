package com.spectra.fieldforce.fragment;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.spectra.fieldforce.databinding.AlertDialogCanidBinding;
import com.spectra.fieldforce.databinding.IrFragmentBinding;
import com.spectra.fieldforce.model.CanIdRequest;
import com.spectra.fieldforce.model.CanIdResponse;
import com.spectra.fieldforce.model.MRTG;
import com.spectra.fieldforce.model.MrtgRequest;
import com.spectra.fieldforce.R;
import com.spectra.fieldforce.api.ApiClientRetrofit;
import com.spectra.fieldforce.api.ApiInterface;
import com.spectra.fieldforce.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentMrtg extends BottomSheetDialogFragment {
    private AlertDialogCanidBinding binding;
    private String canId;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = AlertDialogCanidBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String segment= requireArguments().getString("segment");
        canId= requireArguments().getString("CustomerId");

        if (segment != null && segment.equals("Home")) {
            binding.imageView.setVisibility(View.GONE);
        }
        getNoc();
        getMRTG();
    }


    public void getNoc() {
        CanIdRequest canIdRequest = new CanIdRequest();
        canIdRequest.setAuthkey(Constants.AUTH);
        canIdRequest.setAction(Constants.GET_ACCOUNT_DATA);
        canIdRequest.setCanId(canId);

        ApiInterface apiService = ApiClientRetrofit.getClient().create(ApiInterface.class);
        Call<CanIdResponse> call = apiService.getCanIdDetails(canIdRequest);
        call.enqueue(new Callback<CanIdResponse>() {
            @Override
            public void onResponse(Call<CanIdResponse> call, Response<CanIdResponse> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                    try {
                        binding.textview1.setText(response.body().getResponse().get(0).getAccountStatus());
                        binding.textview3.setText(String.valueOf(response.body().getResponse().get(0).getBarringFlag()));
                        binding.textview5.setText( String.valueOf(response.body().getResponse().get(0).getFUPFlag()));
                        binding.textview7.setText(response.body().getResponse().get(0).getProductSegment());
                        binding.textview9.setText(response.body().getResponse().get(0).getProduct());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<CanIdResponse> call, Throwable t) {
                Log.e("RetroError", t.toString());
            }
        });
    }


    public void getMRTG() {
        MrtgRequest mrtgRequest = new MrtgRequest();
        mrtgRequest.setAuthkey(Constants.AUTH);
        mrtgRequest.setAction(Constants.GET_MRTG);
        mrtgRequest.setCanID(canId);
        mrtgRequest.setDateType("1");

        ApiInterface apiService = ApiClientRetrofit.getClient().create(ApiInterface.class);
        Call<MRTG> call = apiService.getMrtgRequest(mrtgRequest);
        call.enqueue(new Callback<MRTG>() {
            @Override
            public void onResponse(Call<MRTG> call, Response<MRTG> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                    try {
                        String img =  response.body().getResponse();
                        Log.e("image", img);
                        final byte[] decodedBytes = Base64.decode(img, Base64.DEFAULT);
                        Glide.with(requireContext()).load(decodedBytes).into(binding.imageView);
                        binding.imageView.setOnClickListener(view -> {
                            AlertDialog.Builder mBuilder = new AlertDialog.Builder(requireContext());
                            View mView = requireActivity().getLayoutInflater().inflate(R.layout.dialog_custom_layout, null);
                            PhotoView photoView = mView.findViewById(R.id.imageView);
                            Glide.with(requireActivity()).load(decodedBytes).into(photoView);
                            mBuilder.setView(mView);
                            AlertDialog mDialog = mBuilder.create();
                            mDialog.show();
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<MRTG> call, Throwable t) {
                Log.e("RetroError", t.toString());
            }
        });
    }

}
