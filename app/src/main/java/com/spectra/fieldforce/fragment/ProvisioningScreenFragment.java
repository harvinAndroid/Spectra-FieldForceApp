package com.spectra.fieldforce.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.spectra.fieldforce.R;

public class ProvisioningScreenFragment extends Fragment {

    public ProvisioningScreenFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.provisiong_screen_fragment, container, false);
    }


}