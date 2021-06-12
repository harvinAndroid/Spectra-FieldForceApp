package com.spectra.fieldforce.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.spectra.fieldforce.R;
import com.spectra.fieldforce.adapter.MyBucketAdapter;
import com.spectra.fieldforce.databinding.ProvisionFaultTabScreenBinding;
import com.spectra.fieldforce.databinding.ProvisiongScreenFragmentBinding;

public class BucketTabActivity extends BaseActivity {
    ProvisionFaultTabScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         binding = DataBindingUtil.setContentView(this, R.layout.provision_fault_tab_screen);
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("BUCKET"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("MY BUCKET"));
        binding.tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final MyBucketAdapter adapter = new MyBucketAdapter(this,getSupportFragmentManager(),
                binding.tabLayout.getTabCount());
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout));
        binding.searchtoolbar.rlBack.setOnClickListener(this);
        binding.searchtoolbar.tvLang.setText("Bucket List");
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
               binding.viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    public void onClick(View view) {
        if (view.getId() == R.id.rl_back) {
            switchActivity(MainActivity.class);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        switchActivity(MainActivity.class);
        finish();
    }


}