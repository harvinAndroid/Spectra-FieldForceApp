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
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.spectra.fieldforce.Model.CanIdRequest;
import com.spectra.fieldforce.Model.CanIdResponse;
import com.spectra.fieldforce.Model.MRTG;
import com.spectra.fieldforce.Model.MrtgRequest;
import com.spectra.fieldforce.R;
import com.spectra.fieldforce.api.ApiClientRetrofit;
import com.spectra.fieldforce.api.ApiInterface;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentMrtg extends BottomSheetDialogFragment {
    private TextView textview1,textview2,textview3,textview4,textview5,textview6,textview7,textview9;
    private PhotoView imageView;
    private String canId;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.alert_dialog_canid,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textview1=view.findViewById(R.id.textview1);
      //  textview2=view.findViewById(R.id.textview2);
        textview3=view.findViewById(R.id.textview3);
      //  textview4=view.findViewById(R.id.textview4);
        textview5=view.findViewById(R.id.textview5);
       // textview6=view.findViewById(R.id.textview6);
        textview7=view.findViewById(R.id.textview7);
        textview9=view.findViewById(R.id.textview9);
        imageView = view.findViewById(R.id.imageView);
        String segment= requireArguments().getString("segment");
        canId= requireArguments().getString("CustomerId");

        if (segment != null && segment.equals("Home")) {
            imageView.setVisibility(View.GONE);
        }
        getNoc();
        getMRTG();
    }


    public void getNoc() {
        String authKey = "AdgT68HnjkehEqlkd4";
        String action = "getAccountData";
        CanIdRequest canIdRequest = new CanIdRequest();
        canIdRequest.setAuthkey(authKey);
        canIdRequest.setAction(action);
        canIdRequest.setCanId(canId);

        ApiInterface apiService = ApiClientRetrofit.getClient().create(ApiInterface.class);
        Call<CanIdResponse> call = apiService.getCanIdDetails(canIdRequest);
        call.enqueue(new Callback<CanIdResponse>() {
            @Override
            public void onResponse(Call<CanIdResponse> call, Response<CanIdResponse> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                    try {
                        textview1.setText(response.body().getResponse().get(0).getAccountStatus());
                        textview3.setText(String.valueOf(response.body().getResponse().get(0).getBarringFlag()));
                        textview5.setText( String.valueOf(response.body().getResponse().get(0).getFUPFlag()));
                        textview7.setText(response.body().getResponse().get(0).getProductSegment());
                        textview9.setText(response.body().getResponse().get(0).getProduct());
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
        String authKey = "AdgT68HnjkehEqlkd4";
        String action = "getMRTGbycanid";
        String dateType="1";
        MrtgRequest mrtgRequest = new MrtgRequest();
        mrtgRequest.setAuthkey(authKey);
        mrtgRequest.setAction(action);
        mrtgRequest.setCanID(canId);
        mrtgRequest.setDateType(dateType);

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
                        Glide.with(requireContext()).load(decodedBytes).into(imageView);
                        imageView.setOnClickListener(view -> {
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
