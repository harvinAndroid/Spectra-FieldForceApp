package com.spectra.fieldforce.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

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
    public static final String PREF ="Login";
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
        SharedPreferences sp = getActivity().getSharedPreferences(PREF , 0);
        String ffa =sp.getString("FFA", null);
        String installationAuth =sp.getString("InstallationAuth", null);



            linear_gpon.setOnClickListener(v -> {
                if(installationAuth!=null) {
                    if (installationAuth.equals("Y")) {
                        Intent i = new Intent(getActivity(), BucketTabActivity.class);
                        startActivity(i);
                        getActivity().finish();
                    } else if (installationAuth.equals("N")) {
                        Toast.makeText(getActivity(), "Permission Required", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getActivity(), "Something went wrong...", Toast.LENGTH_LONG).show();

                }
            });



            linear_ffa.setOnClickListener(v -> {
                if(ffa!=null){
                if(ffa.equals("Y")) {
                    Intent i = new Intent(getActivity(), SpectraFfaActivity.class);
                    startActivity(i);
                    getActivity().finish();
                }else if(ffa.equals("N")){
                    Toast.makeText(getActivity(),"Permission Required",Toast.LENGTH_LONG).show();
                }}else{
                    Toast.makeText(getActivity(), "Something went wrong...", Toast.LENGTH_LONG).show();

                }
            });

    }


}
