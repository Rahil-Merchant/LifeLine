package com.example.lifeline;
import com.example.lifeline.BookingActivity;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class MyViewPageAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 3;
    public MyViewPageAdapter(FragmentManager fm) {

       super(fm);
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i)
        {
            case 0:
                return AppointmentStepOne.newInstance();
            case 1:
                return AppointmentStepTwo.newInstance();
            case 2:
                return AppointmentStepThree.newInstance();
        }
        return null;

    }


}
