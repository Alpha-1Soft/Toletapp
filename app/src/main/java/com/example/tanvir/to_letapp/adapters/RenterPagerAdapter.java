package com.example.tanvir.to_letapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.tanvir.to_letapp.fragments.renterFragments.RenterHomeFragment;
import com.example.tanvir.to_letapp.fragments.renterFragments.RenterNotificationFragment;
import com.example.tanvir.to_letapp.fragments.renterFragments.RenterProfileFragment;

public class RenterPagerAdapter extends FragmentStatePagerAdapter {

    int totalTab;
    public RenterPagerAdapter(FragmentManager fm, int totalTab) {
        super(fm);
        this.totalTab = totalTab;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                RenterHomeFragment renterHomeFragment = new RenterHomeFragment();
                return renterHomeFragment;
            case 1:
                RenterNotificationFragment renterNotificationFragment = new RenterNotificationFragment();
                return renterNotificationFragment;
            case 2:
                RenterProfileFragment renterProfileFragment = new RenterProfileFragment();
                return renterProfileFragment;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTab;
    }
}
