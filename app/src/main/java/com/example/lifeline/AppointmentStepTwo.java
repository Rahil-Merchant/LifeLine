package com.example.lifeline;

import androidx.fragment.app.Fragment;

public class AppointmentStepTwo extends Fragment {

    static AppointmentStepTwo instance;

    public static AppointmentStepTwo getInstance()
    {
        if(instance == null)
        {
            instance = new AppointmentStepTwo();
        }
        return instance;
    }
}
