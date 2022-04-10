package com.example.quanlythuchi.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.quanlythuchi.fragments.CollectFragment;
import com.example.quanlythuchi.fragments.ProfileFragment;
import com.example.quanlythuchi.fragments.SpendFragment;


public class ViewPagerAdapter extends FragmentStatePagerAdapter {


    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new SpendFragment();
            case 1:
                return new CollectFragment();
            case 2:
                return new ProfileFragment();
            default:
                return new SpendFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title="";
        switch (position){
            case 0:
                title="spend";
                break;
            case 1:
                title="collect";
                break;
            case 2:
                title="profile";
                break;
            default:
                title="spend";
                break;
        }
        return title;
    }
}
