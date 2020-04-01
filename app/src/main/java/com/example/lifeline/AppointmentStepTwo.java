package com.example.lifeline;


import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

//import static com.example.lifeline.AppointmentStepOne.string_time;

public class AppointmentStepTwo extends Fragment{

    private SharedViewModel viewModel;
    private SharedViewModel2 viewModel2;
    private TextView text1;
    private TextView text2;


    int c=1;
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
        c++;
        //Toast.makeText(getContext(),"1",Toast.LENGTH_LONG).show();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View itemView =  inflater.inflate(R.layout.appointment_step_two   ,container,false);
        text1 = (TextView) itemView.findViewById(R.id.tv2);
        text2 = (TextView) itemView.findViewById(R.id.tv1);

        Bundle bundle = getArguments();

        if (bundle==null){
            //String time=bundle.getString("time");
            text1.setText("Morning,8AM to 11PM");
        }

        else{
            String time=bundle.getString("time");
            //TextView text1 = (TextView) itemView.findViewById(R.id.tv2);
            text1.setText(time);
        }




        FloatingActionButton fab = (FloatingActionButton)itemView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getContext(), MapsActivity.class);
                startActivity(intent);
            }
        });
        return itemView;


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //viewModel= ViewModelProvider.of(getActivity()).get(SharedViewModel.class);
        viewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        viewModel2= ViewModelProviders.of(getActivity()).get(SharedViewModel2.class);
        viewModel.getText().observe(getViewLifecycleOwner(), new Observer<CharSequence>() {
            @Override
            public void onChanged(CharSequence charSequence) {

                text1.setText(charSequence);
            }
        });
        viewModel2.getText().observe(getViewLifecycleOwner(), new Observer<CharSequence>() {
            @Override
            public void onChanged(CharSequence charSequence) {
                text2.setText(charSequence);
            }
        });
    }
}
