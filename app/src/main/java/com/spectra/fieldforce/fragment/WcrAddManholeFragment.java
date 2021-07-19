package com.spectra.fieldforce.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.spectra.fieldforce.R;
import com.spectra.fieldforce.api.ApiClient;
import com.spectra.fieldforce.api.ApiInterface;
import com.spectra.fieldforce.databinding.AddManholeDetailsBinding;
import com.spectra.fieldforce.model.CommonResponse;
import com.spectra.fieldforce.model.gpon.request.AccountInfoRequest;
import com.spectra.fieldforce.model.gpon.request.UpdateManHoleRequest;
import com.spectra.fieldforce.model.gpon.request.UpdateWcrEnggRequest;
import com.spectra.fieldforce.model.gpon.response.CommonClassResponse;
import com.spectra.fieldforce.model.gpon.response.GetFibreCable;
import com.spectra.fieldforce.utils.AppConstants;
import com.spectra.fieldforce.utils.Constants;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WcrAddManholeFragment extends Fragment implements AdapterView.OnItemSelectedListener,View.OnClickListener{

    AddManholeDetailsBinding binding;
    private ArrayList<String> fibreType;
    private ArrayList<String> fibreValue;
    private ArrayList<String> ManholeType;
    private List<GetFibreCable.Datum> fibreCable;
    private String strCanId ,strFibre,strGuIId,OrderId,StatusOfReport;
    private Boolean WcrStatus;
    public WcrAddManholeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = AddManholeDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        strGuIId = requireArguments().getString("strGuuId");
        strCanId = requireArguments().getString("canId");
        StatusOfReport = requireArguments().getString("StatusofReport");
        OrderId = requireArguments().getString("OrderId");
        WcrStatus =  requireArguments().getBoolean("WcrStatus");
        binding.searchtoolbar.rlBack.setOnClickListener(this);
        binding.searchtoolbar.tvLang.setText("Add Manhole");

        init();
        getFibreCable();
    }


    private void init(){
        binding.etManholeType.setOnClickListener(v-> binding.spManholeType.performClick());
        binding.spManholeType.setOnItemSelectedListener(this);
        binding.etFibreCable.setOnClickListener(v-> binding.spFibreCable.performClick());
        binding.spFibreCable.setOnItemSelectedListener(this);
        ManholeType = new ArrayList<String>();
        ManholeType.add(AppConstants.SELECT_MANHOLE_TYPE);
        ManholeType.add("In");
        ManholeType.add("Out");
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, ManholeType);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spManholeType.setAdapter(adapter1);
        binding.btAddmanholeSubmit.setOnClickListener(v -> {

            String manhole = binding.etManholeType.getText().toString();

            String distance = binding.etDistance.getText().toString();
            String fibreRun = binding.etFiberNoRunn.getText().toString();
            String fibreNo = binding.etFibreNo.getText().toString();
            String fibreTube = binding.etFiberTube.getText().toString();
            String Location = binding.etLocation.getText().toString();
            if(Location.equals("Location with Landmark")||Location.isEmpty()){
                Toast.makeText(getContext(), "Please Enter Location with Landmark", Toast.LENGTH_LONG).show();
            }else if(distance.equals("Distance")||distance.isEmpty()){
                Toast.makeText(getContext(), "Please Enter Distance", Toast.LENGTH_LONG).show();
            }else  if(fibreRun.equals("Fiber No Running No. Wise")||fibreRun.isEmpty()){
                Toast.makeText(getContext(), "Please Enter Fiber No Running No. Wise", Toast.LENGTH_LONG).show();
            }else  if(fibreNo.equals("Fiber No. Tube Wise")||fibreNo.isEmpty()){
                Toast.makeText(getContext(), "Please Enter Fiber No. Tube Wise", Toast.LENGTH_LONG).show();
            }else  if(fibreTube.equals("Fiber Tube")||fibreTube.isEmpty()){
                Toast.makeText(getContext(), "Please Enter Fiber Tube", Toast.LENGTH_LONG).show();
            }
              else  if(manhole.equals(AppConstants.SELECT_MANHOLE_TYPE)||manhole.isEmpty()){
                Toast.makeText(getContext(), "Please Select Manhole Type", Toast.LENGTH_LONG).show();
            } else  if(strFibre.equals(AppConstants.SELECT_FIBRE_TYPE)||strFibre.isEmpty()){
                Toast.makeText(getContext(), "Please Select Fibre Type", Toast.LENGTH_LONG).show();
            }
            else {
                String strManhole="";
                if(manhole.equals("In")){
                    strManhole = "0";
                }else if(manhole.equals("Out")){
                    strManhole = "1";
                }
                addMahole(distance,fibreRun,fibreNo,fibreTube,Location,strManhole);
            }


        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId() == R.id.sp_fibre_cable){
            binding.etFibreCable.setText(fibreType.get(position));
            if (position != 0) strFibre = "" + fibreValue.get(position - 1);
            else strFibre = " ";
        }else if(parent.getId() == R.id.sp_manhole_type){
            binding.etManholeType.setText(ManholeType.get(position));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

      private void getFibreCable() {
        AccountInfoRequest accountInfoRequest = new AccountInfoRequest();
        accountInfoRequest.setAuthkey(Constants.AUTH_KEY);
        accountInfoRequest.setAction(Constants.GET_FIBRE_CABLE);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<GetFibreCable> call = apiService.getFibreList(accountInfoRequest);
        call.enqueue(new Callback<GetFibreCable>() {
            @Override
            public void onResponse(Call<GetFibreCable> call, Response<GetFibreCable> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                    try {
                        fibreCable = response.body().response.data;
                        fibreType = new ArrayList<>();
                        fibreValue = new ArrayList<>();
                        fibreType.add(AppConstants.SELECT_FIBRE_TYPE);
                        for (GetFibreCable.Datum datum : fibreCable)
                            fibreType.add(datum.type);
                        for (GetFibreCable.Datum data : fibreCable)
                            fibreValue.add(data.value);
                        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, fibreType);
                        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        binding.spFibreCable.setAdapter(adapter1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<GetFibreCable> call, Throwable t) {
                Log.e("RetroError", t.toString());
            }
        });
    }

    private void addMahole(String distance, String fibreRun, String fibreNo, String fibreTube, String location,String manhole){

        UpdateManHoleRequest updateManHoleRequest = new UpdateManHoleRequest();
        updateManHoleRequest.setAuthkey(Constants.AUTH_KEY);
        updateManHoleRequest.setAction(Constants.UPDATE_POST_MANHOLE);
        updateManHoleRequest.setCanId(strCanId);
        updateManHoleRequest.setDistance(distance);
        updateManHoleRequest.setFiberCable(strFibre);
        updateManHoleRequest.setFiberNoRunningNoWise(fibreRun);
        updateManHoleRequest.setFiberNoTubeWise(fibreNo);
        updateManHoleRequest.setItemID("");
        updateManHoleRequest.setManHoleType(manhole);
        updateManHoleRequest.setWCRguidId(strGuIId);
        updateManHoleRequest.setFiberTube(fibreTube);
        updateManHoleRequest.setLocationLandmark(location);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonClassResponse> call = apiService.addManholeItem(updateManHoleRequest);
        call.enqueue(new Callback<CommonClassResponse>() {
            @Override
            public void onResponse(Call<CommonClassResponse> call, Response<CommonClassResponse> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                    try {
                        if(response.body().getStatus().equals("Success")){
                            Toast.makeText(getContext(),response.body().getResponse().getMessage(),Toast.LENGTH_LONG).show();
                            nextScreen();
                        }else{
                            Toast.makeText(getContext(),response.body().getResponse().getMessage(),Toast.LENGTH_LONG).show();

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }

            @Override
            public void onFailure(Call<CommonClassResponse> call, Throwable t) {
                Log.e("RetroError", t.toString());
            }
        });

    }

    private void nextScreen(){
        @SuppressLint("UseRequireInsteadOfGet") FragmentTransaction t= Objects.requireNonNull(this.getFragmentManager()).beginTransaction();
        WcrFragment wcrFragment = new WcrFragment();
        Bundle accountinfo = new Bundle();
        accountinfo.putString("canId", strCanId);
        accountinfo.putString("StatusofReport", StatusOfReport);
        accountinfo.putString("OrderId", OrderId);
        accountinfo.putBoolean("WcrStatus", WcrStatus);
        t.replace(R.id.frag_container, wcrFragment);
        wcrFragment.setArguments(accountinfo);
        t.commit();
    }


    @Override
    public void onClick(View v) {
        nextScreen();
    }
}
