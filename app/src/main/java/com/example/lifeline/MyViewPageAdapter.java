package com.example.lifeline;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyViewPageAdapter extends FragmentPagerAdapter {
    public MyViewPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i)
        {
            case 0:
                return AppointmentStepOne.getInstance();
            case 1:
                return AppointmentStepTwo.getInstance();
            case 2:
                return AppointmentStepThree.getInstance();
        }
        return null;

    }

    @Override
    public int getCount() {
        return 3;
    }
}
