package com.example.lifeline;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class CheckDonorAdapter extends FragmentPagerAdapter {

    public CheckDonorAdapter(@NonNull FragmentManager fm,int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:return CheckDonor2.newInstance("FirstFragment","Instance 1");
            //case 1:return CheckDonor3.newInstance("SecondFragment","Instance 2");
            default:return CheckDonor2.newInstance("FirstFragment","Instance 1");
        }

        /*CheckDonor2 checkDonor2=new CheckDonor2();
        position=1;
        //Bundle bundle=new Bundle();
        //bundle.putString("message","Fragment :"+position);
        //checkDonor2.setArguments(bundle);
        return checkDonor2;

        CheckDonor3 checkDonor3=new CheckDonor3();
        position=2;
        //Bundle bundle=new Bundle();
        //bundle.putString("message","Fragment :"+position);
        //checkDonor2.setArguments(bundle);
        return checkDonor3;*/

    }

    @Override
    public int getCount() {
        return 1;
    }
}
