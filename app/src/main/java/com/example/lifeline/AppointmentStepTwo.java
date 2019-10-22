package com.example.lifeline;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AppointmentStepTwo extends Fragment {

    static AppointmentStepTwo instance;

   /* public static AppointmentStepTwo getInstance()
    {
        if(instance == null)
        {
            instance = new AppointmentStepTwo();
        }
        return instance;
    }*/

    public static AppointmentStepTwo newInstance() {
        AppointmentStepTwo fragment = new AppointmentStepTwo();
        return fragment;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View itemView =  inflater.inflate(R.layout.appointment_step_two   ,container,false);
        FloatingActionButton fab = (FloatingActionButton)itemView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getContext(), MapsActivity.class);
                startActivity(intent);
            }
        });
        return itemView;


    }}
