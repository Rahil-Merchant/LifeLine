package com.example.lifeline;

import androidx.fragment.app.Fragment;

public class AppointmentStepOne extends Fragment {

    static AppointmentStepOne instance;

    public static AppointmentStepOne getInstance()
    {
        if(instance == null)
        {
            instance = new AppointmentStepOne();
        }
        return instance;
    }
}
