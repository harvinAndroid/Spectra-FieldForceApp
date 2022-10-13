package com.spectra.fieldforce.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.spectra.fieldforce.R;
import com.spectra.fieldforce.adapter.DashBoardAdapter;
import com.spectra.fieldforce.databinding.MainDashBinding;
import com.spectra.fieldforce.model.DashBoardModel;
import com.spectra.fieldforce.utils.AppConstant;
import com.spectra.fieldforce.utils.PrefConfig;
import java.util.ArrayList;

public class DashFragment extends Fragment {
    MainDashBinding binding;
    public static PrefConfig prefConfig;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = MainDashBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prefConfig = new PrefConfig(getActivity());
        ArrayList<DashBoardModel> list = new ArrayList();
        list.add(new DashBoardModel(AppConstant.SALES, R.drawable.sales_icon));
        list.add(new DashBoardModel(AppConstant.INSTALLATION, R.drawable.intallation_icon));
        list.add(new DashBoardModel(AppConstant.FAULT_REPAIR, R.drawable.fr_icon));
        list.add(new DashBoardModel(AppConstant.PROVISIONG_APP, R.drawable.add_circle));
         list.add(new DashBoardModel(AppConstant.SALES_HOME, R.drawable.sales_icon));
        binding.recycler.setHasFixedSize(true);
        binding.recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recycler.setAdapter(new DashBoardAdapter(getActivity(), list));
    }
}
