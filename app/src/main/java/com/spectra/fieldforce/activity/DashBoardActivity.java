package com.spectra.fieldforce.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.spectra.fieldforce.R;

public class DashBoardActivity extends AppCompatActivity {
   private LinearLayout linear_ffa,linear_gpon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_dashborad);
        init();
    }

    private void init(){
        linear_ffa = findViewById(R.id.linear_ffa);
        linear_gpon = findViewById(R.id.linear_gpon);

        linear_gpon.setOnClickListener(v -> {
            Intent i = new Intent(DashBoardActivity.this,ProvisioningTabActivity.class);
            startActivity(i);
            finish();
        });

        linear_ffa.setOnClickListener(v -> {
            Intent ffa = new Intent(DashBoardActivity.this,MainActivity.class);
            startActivity(ffa);
            finish();
        });

    }

}
