package com.spectra.fieldforce.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.spectra.fieldforce.R;

import java.util.Objects;

public class ProvisioningFragment extends Fragment {
    private TextView tv_provisioning,tv_wcr;

    public ProvisioningFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.provision_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_provisioning = view.findViewById(R.id.tv_provisioning);
        tv_wcr = view.findViewById(R.id.tv_wcr);
        init();
    }

    private void init(){
        tv_provisioning.setOnClickListener(v -> {
            @SuppressLint("UseRequireInsteadOfGet") FragmentTransaction t11= Objects.requireNonNull(this.getFragmentManager()).beginTransaction();
            ProvisioningScreenFragment provisioningScreenFragment = new ProvisioningScreenFragment();
            t11.replace(R.id.frag_container, provisioningScreenFragment);
            t11.commit();
        });
        tv_wcr.setOnClickListener(v -> {
            @SuppressLint("UseRequireInsteadOfGet") FragmentTransaction t11= Objects.requireNonNull(this.getFragmentManager()).beginTransaction();
            WcrCompletedFragment wcrCompletedFragment = new WcrCompletedFragment();
            t11.replace(R.id.frag_container, wcrCompletedFragment);
            t11.commit();
        });

    }


}