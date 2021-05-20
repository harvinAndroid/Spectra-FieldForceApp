package com.spectra.fieldforce.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.spectra.fieldforce.R;
import com.spectra.fieldforce.databinding.ProvisionFragmentBinding;
import com.spectra.fieldforce.databinding.ProvisiongScreenFragmentBinding;
import com.spectra.fieldforce.model.gpon.request.AddProvisioningRequest;
import com.spectra.fieldforce.utils.Constants;

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
        name= requireArguments().getString("name");
        canId = requireArguments().getString("canId");
        city = requireArguments().getString("city");
        area = requireArguments().getString("area");
        building = requireArguments().getString("building");
        segment = requireArguments().getString("segment");
        init();
    }

    private void init(){
        binding.tvCustomerAccountId.setText(canId);
        binding.tvCustomerName.setText(name);
        binding.tvCity.setText(city);
        binding.tvBuildingSociety.setText(building);
        binding.tvSegmentType.setText(segment);
        binding.tvAddressDetail.setText(building);
        addProvisioning();
    }

    private void addProvisioning(){
       /* AddProvisioningRequest addProvisioningRequest = new AddProvisioningRequest();
        addProvisioningRequest.setAuthkey(Constants.AUTH_KEY);
        addProvisioningRequest.setAction(Constants.PROVISIONING);
        addProvisioningRequest.setCanId(canId);
        addProvisioningRequest.setDescription(binding.de);
        addProvisioningRequest.setOnuSerial();
        addProvisioningRequest.setProfile();
        addProvisioningRequest.setSplitterId();
        addProvisioningRequest.setTowerId();*/

    }
}