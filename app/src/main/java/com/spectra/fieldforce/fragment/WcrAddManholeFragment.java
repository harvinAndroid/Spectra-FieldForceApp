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


public class WcrAddManholeFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    AddManholeDetailsBinding binding;
    private ArrayList<String> fibreType;
    private ArrayList<String> fibreValue;
    private ArrayList<String> ManholeType;
    private List<GetFibreCable.Datum> fibreCable;
    private String strCanId ,strFibre,strGuIId;

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
        strCanId = requireArguments().getString("strCanId");

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
        binding.btAddmanholeSubmit.setOnClickListener(v -> addMahole());
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

    private void addMahole(){
         String strManhole="";
        String manhole = binding.etManholeType.getText().toString();
        if(manhole.equals("In")){
            strManhole = "0";
        }else if(manhole.equals("Out")){
            strManhole = "1";
        }
        UpdateManHoleRequest updateManHoleRequest = new UpdateManHoleRequest();
        updateManHoleRequest.setAuthkey(Constants.AUTH_KEY);
        updateManHoleRequest.setAction(Constants.UPDATE_POST_MANHOLE);
        updateManHoleRequest.setCanId(strCanId);
        updateManHoleRequest.setDistance(binding.etDistance.getText().toString());
        updateManHoleRequest.setFiberCable(strFibre);
        updateManHoleRequest.setFiberNoRunningNoWise(binding.etFiberNoRunn.getText().toString());
        updateManHoleRequest.setFiberNoTubeWise(binding.etFibreNo.getText().toString());
        updateManHoleRequest.setItemID("");
        updateManHoleRequest.setManHoleType(strManhole);
        updateManHoleRequest.setWCRguidId(strGuIId);
        updateManHoleRequest.setFiberTube(binding.etFiberTube.getText().toString());
        updateManHoleRequest.setLocationLandmark(binding.etLocation.getText().toString());

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonClassResponse> call = apiService.addManholeItem(updateManHoleRequest);
        call.enqueue(new Callback<CommonClassResponse>() {
            @Override
            public void onResponse(Call<CommonClassResponse> call, Response<CommonClassResponse> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                    try {
                        if(response.body().getResponse().getStatusCode()==200){
                            Toast.makeText(getContext(),response.body().getResponse().getMessage(),Toast.LENGTH_LONG).show();
                            nextScreen();
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
        @SuppressLint("UseRequireInsteadOfGet") FragmentTransaction t1= Objects.requireNonNull(this.getFragmentManager()).beginTransaction();
        WcrFragment wcrFragment = new WcrFragment();
        t1.replace(R.id.frag_container, wcrFragment);
        t1.commit();
    }


}
