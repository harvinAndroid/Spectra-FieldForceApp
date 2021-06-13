package com.spectra.fieldforce.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.spectra.fieldforce.R;
import com.spectra.fieldforce.activity.BucketTabActivity;
import com.spectra.fieldforce.activity.DashBoardActivity;
import com.spectra.fieldforce.activity.MainActivity;
import com.spectra.fieldforce.activity.SpectraFfaActivity;
import com.spectra.fieldforce.utils.PrefConfig;

public class DashboardFragment extends Fragment {

    private LinearLayout linear_ffa,linear_gpon;
    public static PrefConfig prefConfig;
    DashBoardActivity context;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_dashborad, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prefConfig = new PrefConfig(getActivity());
        linear_ffa = view.findViewById(R.id.linear_ffa);
        linear_gpon = view.findViewById(R.id.linear_gpon);
        init();
    }

    private void init(){


        linear_gpon.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), BucketTabActivity.class);
            startActivity(i);
            getActivity().finish();
        });

        linear_ffa.setOnClickListener(v -> {

           // SpectraFfaActivity.prefConfig.writeLoginStatus(true);
            Intent i = new Intent(getActivity(), SpectraFfaActivity.class);
            startActivity(i);
            getActivity().finish();
        });

    }


}
