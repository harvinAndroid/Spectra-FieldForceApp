package com.spectra.fieldforce.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.spectra.fieldforce.R;
import com.spectra.fieldforce.databinding.ProvisionFragmentBinding;

import java.util.Objects;

public class ProvisioningFragment extends Fragment {
   private String name,canId,city,area,building,segment;
    private ProvisionFragmentBinding provisionFragmentBinding;
    public ProvisioningFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        provisionFragmentBinding = ProvisionFragmentBinding.inflate(inflater, container, false);
        return provisionFragmentBinding.getRoot();
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
        provisionFragmentBinding.tvCustomerAccId.setText(canId);
        provisionFragmentBinding.tvCustomerName.setText(name);
        provisionFragmentBinding.tvCity.setText(city);
        provisionFragmentBinding.tvArea.setText(area);
        provisionFragmentBinding.tvBuildingSociety.setText(building);
        provisionFragmentBinding.tvSegmentType.setText(segment);
        init();
    }

    private void init(){
        provisionFragmentBinding.tvProvisioning.setOnClickListener(v -> {
            Bundle accountinfo = new Bundle();
            accountinfo.putString("name", name);
            accountinfo.putString("canId", canId);
            accountinfo.putString("city", city);
            accountinfo.putString("area", area);
            accountinfo.putString("building", building);
            accountinfo.putString("segment",segment);
            @SuppressLint("UseRequireInsteadOfGet") FragmentTransaction t11= Objects.requireNonNull(this.getFragmentManager()).beginTransaction();
            ProvisioningScreenFragment provisioningScreenFragment = new ProvisioningScreenFragment();
            t11.replace(R.id.frag_container, provisioningScreenFragment);
            provisioningScreenFragment.setArguments(accountinfo);
            t11.commit();
        });
        provisionFragmentBinding.tvWcr.setOnClickListener(v -> {
            @SuppressLint("UseRequireInsteadOfGet") FragmentTransaction t1= Objects.requireNonNull(this.getFragmentManager()).beginTransaction();
            WcrFragment wcrFragment = new WcrFragment();
            t1.replace(R.id.frag_container, wcrFragment);
            t1.commit();
        });
        provisionFragmentBinding.tvIr.setOnClickListener(v -> {
            @SuppressLint("UseRequireInsteadOfGet") FragmentTransaction t= Objects.requireNonNull(this.getFragmentManager()).beginTransaction();
            IRFragment irFragment = new IRFragment();
            t.replace(R.id.frag_container, irFragment);
            t.commit();
        });
        provisionFragmentBinding.tvWcrCompleted.setOnClickListener(v -> {
            @SuppressLint("UseRequireInsteadOfGet") FragmentTransaction t= Objects.requireNonNull(this.getFragmentManager()).beginTransaction();
            WcrCompletedFragment wcrCompletedFragment = new WcrCompletedFragment();
            t.replace(R.id.frag_container, wcrCompletedFragment);
            t.commit();
        });



    }


}