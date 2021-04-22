package com.spectra.fieldforce.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.spectra.fieldforce.fragment.FaultTabFragment;
import com.spectra.fieldforce.fragment.ProvisioningTabFragment;

public class MyAdapter extends FragmentPagerAdapter {
    Context context;
    int totalTabs;
    public MyAdapter(Context c, FragmentManager fm, int totalTabs) {
        super(fm);
        context = c;
        this.totalTabs = totalTabs;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ProvisioningTabFragment provisioningTabFragment = new ProvisioningTabFragment();
                return provisioningTabFragment;
            case 1:
                FaultTabFragment faultTabFragment = new FaultTabFragment();
                return faultTabFragment;
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return totalTabs;
    }
}
