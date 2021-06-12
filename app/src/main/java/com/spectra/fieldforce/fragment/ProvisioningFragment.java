package com.spectra.fieldforce.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.spectra.fieldforce.R;
import com.spectra.fieldforce.activity.BucketTabActivity;
import com.spectra.fieldforce.databinding.ProvisionFragmentBinding;

import java.util.Objects;

public class ProvisioningFragment extends Fragment implements View.OnClickListener{
    private String name,canId,city,area,building,segment,statusReport;
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
    public void onClick(View view) {
        if (view.getId() == R.id.rl_back) {
            Intent i = new Intent(getActivity(), BucketTabActivity.class);
            startActivity(i);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            getActivity().finish();
        }
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
        statusReport = requireArguments().getString("statusReport");
        provisionFragmentBinding.tvCustomerAccId.setText(canId);
        provisionFragmentBinding.tvCustomerName.setText(name);
        provisionFragmentBinding.tvCity.setText(city);
        provisionFragmentBinding.tvArea.setText(area);
        provisionFragmentBinding.tvBuildingSociety.setText(building);
        provisionFragmentBinding.tvSegmentType.setText(segment);
        provisionFragmentBinding.searchtoolbar.rlBack.setOnClickListener(this);
        provisionFragmentBinding.searchtoolbar.tvLang.setText("Provisioning");
        if(statusReport.equals("Installation Pending")||statusReport.equals("Pending")){
            provisionFragmentBinding.tvWcr.setText("WCR");
            provisionFragmentBinding.tvWcr.setOnClickListener(v -> {
                @SuppressLint("UseRequireInsteadOfGet") FragmentTransaction t1= Objects.requireNonNull(this.getFragmentManager()).beginTransaction();
                WcrFragment wcrFragment = new WcrFragment();
                Bundle accountinfo = new Bundle();
                accountinfo.putString("canId", canId);
                t1.replace(R.id.frag_container, wcrFragment);
                wcrFragment.setArguments(accountinfo);
                t1.commit();
            });
        }else if(statusReport.equals("Installation Completed")|| statusReport.equals("Completed")){
            provisionFragmentBinding.tvWcr.setText("WCR Completed");
            provisionFragmentBinding.tvWcr.setOnClickListener(v -> {
                @SuppressLint("UseRequireInsteadOfGet") FragmentTransaction t= Objects.requireNonNull(this.getFragmentManager()).beginTransaction();
                WcrCompletedFragment wcrCompletedFragment = new WcrCompletedFragment();
                Bundle accountinfo = new Bundle();
                accountinfo.putString("canId", canId);
                t.replace(R.id.frag_container, wcrCompletedFragment);
                wcrCompletedFragment.setArguments(accountinfo);
                t.commit();
            });
        }
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
             FragmentTransaction t11= this.requireFragmentManager().beginTransaction();
            ProvisioningScreenFragment provisioningScreenFragment = new ProvisioningScreenFragment();
            t11.replace(R.id.frag_container, provisioningScreenFragment);
            provisioningScreenFragment.setArguments(accountinfo);
            t11.commit();

        });

        provisionFragmentBinding.tvIr.setOnClickListener(v -> {
            @SuppressLint("UseRequireInsteadOfGet") FragmentTransaction t= Objects.requireNonNull(this.getFragmentManager()).beginTransaction();
            IRFragment irFragment = new IRFragment();
            Bundle accountinfo = new Bundle();
            accountinfo.putString("canId", canId);
            t.replace(R.id.frag_container, irFragment);
            irFragment.setArguments(accountinfo);
            t.commit();
        });

    }


}