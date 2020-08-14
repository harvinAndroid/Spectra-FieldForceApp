package com.spectra.fieldforce;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.JsonObject;
import com.spectra.fieldforce.Model.CanIdRequest;
import com.spectra.fieldforce.Model.CanIdResponse;
import com.spectra.fieldforce.Model.MRTG;
import com.spectra.fieldforce.Model.MrtgRequest;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentMrtg extends BottomSheetDialogFragment {
    TextView textview1,textview2,textview3,textview4,textview5,textview6;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.alert_dialog_canid,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textview1=view.findViewById(R.id.textview1);
        textview2=view.findViewById(R.id.textview2);
        textview3=view.findViewById(R.id.textview3);
        textview4=view.findViewById(R.id.textview4);
        textview5=view.findViewById(R.id.textview5);
        textview6=view.findViewById(R.id.textview6);
        getNoc();
        getMRTG();
    }


    public void getNoc() {
        String authKey = "AdgT68HnjkehEqlkd4";
        String action = "getAccountData";
        String canID="199624";
        CanIdRequest canIdRequest = new CanIdRequest();
        canIdRequest.setAuthkey(authKey);
        canIdRequest.setAction(action);
        canIdRequest.setCanId(canID);

        ApiInterface apiService = ApiClientRetrofit.getClient().create(ApiInterface.class);
        Call<CanIdResponse> call = apiService.getCanIdDetails(canIdRequest);
        call.enqueue(new Callback<CanIdResponse>() {
            @Override
            public void onResponse(retrofit2.Call<CanIdResponse> call, Response<CanIdResponse> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                    try {
                        String account=  response.body().getResponse().get(0).getAccountStatus();
                        String barring = String.valueOf(response.body().getResponse().get(0).getBarringFlag());
                        String flup = String.valueOf(response.body().getResponse().get(0).getFUPFlag());
                        textview1.setText(account);
                        textview3.setText(barring);
                        textview5.setText(flup);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(retrofit2.Call<CanIdResponse> call, Throwable t) {
                Log.e("RetroError", t.toString());
            }
        });
    }


    public void getMRTG() {
        String authKey = "AdgT68HnjkehEqlkd4";
        String action = "getMRTGbycanid";
        String canID="199624";
        String dateType="1";
        MrtgRequest mrtgRequest = new MrtgRequest();
        mrtgRequest.setAuthkey(authKey);
        mrtgRequest.setAction(action);
        mrtgRequest.setCanID(canID);
        mrtgRequest.setDateType(dateType);

        ApiInterface apiService = ApiClientRetrofit.getClient().create(ApiInterface.class);
        Call<MRTG> call = apiService.getMrtgRequest(mrtgRequest);
        call.enqueue(new Callback<MRTG>() {
            @Override
            public void onResponse(retrofit2.Call<MRTG> call, Response<MRTG> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                    try {
                        String img =  response.body().getResponse();
                        //byte[] decodedString = response.body().getResponse().toByteArray();
                        //Toast.makeText(getActivity(),img,Toast.LENGTH_LONG).show();
                        Log.e("image", img);
                      /*  final byte[] decodedBytes = Base64.decode(img, Base64.DEFAULT);
                       // Glide.with(Activity_Resolve.this).load(decodedBytes).fitCenter().into(image);*/
                       /* Glide.with(Activity_Resolve.this)
                                .load(Base64.decode(img, Base64.DEFAULT))
                                .into(image);*/

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(retrofit2.Call<MRTG> call, Throwable t) {
                Log.e("RetroError", t.toString());
            }
        });
    }



}
