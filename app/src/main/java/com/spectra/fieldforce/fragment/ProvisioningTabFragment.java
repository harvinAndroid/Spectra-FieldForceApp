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

public class ProvisioningTabFragment extends Fragment {
    private TextView tv_get_profile_info;

    public ProvisioningTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.provisioning_tab_screen, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_get_profile_info = view.findViewById(R.id.tv_get_profile_info);
        init();
    }

    private void init(){
       tv_get_profile_info.setOnClickListener(v -> {
           @SuppressLint("UseRequireInsteadOfGet") FragmentTransaction t11= Objects.requireNonNull(this.getFragmentManager()).beginTransaction();
           ProvisioningFragment provisioningFragment = new ProvisioningFragment();
           t11.replace(R.id.frag_container, provisioningFragment);
           t11.commit();
       });
    }



}