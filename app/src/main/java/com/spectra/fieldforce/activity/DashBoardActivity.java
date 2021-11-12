package com.spectra.fieldforce.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.spectra.fieldforce.R;
import com.spectra.fieldforce.databinding.MainActivityBinding;
import com.spectra.fieldforce.databinding.MainDashboradBinding;
import com.spectra.fieldforce.fragment.WelcomeFragment;
import com.spectra.fieldforce.salesapp.activity.SalesDashboard;
import com.spectra.fieldforce.utils.PrefConfig;

public class DashBoardActivity extends AppCompatActivity {
    MainDashboradBinding binding;
    public static PrefConfig prefConfig;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.main_dashborad);

        setContentView(R.layout.main_dashborad);
        init();
    }

    private void init(){
        prefConfig = new PrefConfig(this);

        binding.linearGpon.setOnClickListener(v -> {
            Intent i = new Intent(DashBoardActivity.this,BucketTabActivity.class);
            startActivity(i);
            finish();
        });

        binding.linearFfa.setOnClickListener(v -> {
            Intent i = new Intent(DashBoardActivity.this,SpectraFfaActivity.class);
            startActivity(i);
            finish();
        });

        binding.linearSales.setOnClickListener(view -> {
            Intent i = new Intent(DashBoardActivity.this, SalesDashboard.class);
            startActivity(i);
            finish();
        });

    }

}
