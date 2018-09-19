package com.example.tanvir.to_letapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.tanvir.to_letapp.fragments.ownerFragmets.OwnerPostFragment;
import com.example.tanvir.to_letapp.fragments.ownerFragmets.OwnerProfileFragment;
import com.example.tanvir.to_letapp.fragments.ownerFragmets.OwnerRequestFragment;
public class OwnerPagerAdapter extends FragmentStatePagerAdapter{

    int totalTab;
    public OwnerPagerAdapter(FragmentManager fm, int totalTab) {
        super(fm);
        this.totalTab = totalTab;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                OwnerPostFragment ownerPostFragment = new OwnerPostFragment();
                return ownerPostFragment;
            case 1:
                OwnerRequestFragment ownerRequestFragment = new OwnerRequestFragment();
                return ownerRequestFragment;
            case 2:
                OwnerProfileFragment ownerProfileFragment = new OwnerProfileFragment();
                return ownerProfileFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTab;
    }
}
