package com.spectra.fieldforce.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.spectra.fieldforce.fragment.GetAllBucketListFragment;
import com.spectra.fieldforce.fragment.MyBucketList;



public class MyBucketAdapter extends FragmentPagerAdapter {
    Context context;
    int totalTabs;
    public MyBucketAdapter(Context c, FragmentManager fm, int totalTabs) {
        super(fm);
        context = c;
        this.totalTabs = totalTabs;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                GetAllBucketListFragment getAllBucketListFragment = new GetAllBucketListFragment();
                return getAllBucketListFragment;
            case 1:
                MyBucketList myBucketList = new MyBucketList();
                return myBucketList;
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return totalTabs;
    }
}
