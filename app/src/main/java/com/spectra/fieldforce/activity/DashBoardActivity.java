package com.spectra.fieldforce.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.spectra.fieldforce.R;
import com.spectra.fieldforce.fragment.WelcomeFragment;
import com.spectra.fieldforce.utils.PrefConfig;

public class DashBoardActivity extends AppCompatActivity {
   private LinearLayout linear_ffa,linear_gpon;
    public static PrefConfig prefConfig;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_dashborad);
        init();
    }

    private void init(){
        prefConfig = new PrefConfig(this);
        linear_ffa = findViewById(R.id.linear_ffa);
        linear_gpon = findViewById(R.id.linear_gpon);

        linear_gpon.setOnClickListener(v -> {
            Intent i = new Intent(DashBoardActivity.this,BucketTabActivity.class);
            startActivity(i);
            finish();
        });

        linear_ffa.setOnClickListener(v -> {
            getSupportFragmentManager().beginTransaction().add(R.id.fregment_container, new WelcomeFragment(), WelcomeFragment.class.getSimpleName()).addToBackStack(null).commit();
          /*  Intent ffa = new Intent(DashBoardActivity.this,MainActivity.class);
            startActivity(ffa);
            finish();*/
        });

    }

}
