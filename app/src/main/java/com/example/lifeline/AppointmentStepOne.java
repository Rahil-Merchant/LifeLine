package com.example.lifeline;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceControl;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarView;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;
import dmax.dialog.SpotsDialog;

public class AppointmentStepOne extends Fragment {

    private SharedViewModel viewModel;
    private SharedViewModel2 viewModel2;
    EditText editText;

    HorizontalCalendarView calendarView;
    SimpleDateFormat simpleDateFormat;
    public static Calendar selected_date = Calendar.getInstance();
    public String string_time = "lul";
    AlertDialog dialog;
    LocalBroadcastManager localBroadcastManager;
    Unbinder unbinder;
    MaterialSpinner spinner;
    List<String> timing_list = new ArrayList<>();
    Bundle bundle=new Bundle();

    @Override
    public void setRetainInstance(boolean retain) {
        super.setRetainInstance(retain);
    }

    BroadcastReceiver displayTimeSlot = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Calendar date = Calendar.getInstance();
            date.add(Calendar.DATE, 0);
        }
    };

    private BroadcastReceiver buttonNextStep = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    };

    static AppointmentStepOne instance;

    /*public static AppointmentStepOne getInstance()
    {
        if(instance == null)
        {
            instance = new AppointmentStepOne();
        }
        return instance;
    }*/
    public static AppointmentStepOne newInstance() {
        AppointmentStepOne fragment = new AppointmentStepOne();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //timing_list.add("");
        timing_list.add("Morning, 8 AM to 11 AM");
        timing_list.add("Afternoon, 1 PM to 4 PM");
        timing_list.add("Evening, 6 PM to 9 PM");



        simpleDateFormat = new SimpleDateFormat("dd_MM_yyyy");
        dialog = new SpotsDialog.Builder().setContext(getContext()).setCancelable(false).build();
        selected_date = Calendar.getInstance();
        selected_date.add(Calendar.DATE,0);



        // LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);

    }

    @Override
    public void onDestroy() {
        //localBroadcastManager.unregisterReceiver(displayTimeSlot);
        super.onDestroy();
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View itemView =  inflater.inflate(R.layout.appointment_step_one   ,container,false);
        calendarView = itemView.findViewById(R.id.calendarView);
        spinner = itemView.findViewById(R.id.spinner);
        spinner.setItems(timing_list);
        unbinder = ButterKnife.bind(this,itemView);
        init(itemView);
        //editText = itemView.findViewById(R.id.tv3);

        return itemView;
    }



    private void confirmBooking()
    {
        Intent intent = new Intent("Confirm Booking");
        localBroadcastManager.sendBroadcast(intent);
    }

    @NonNull
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                string_time = item.toString();

                AppointmentStepTwo appointmentStepTwo=new AppointmentStepTwo();
                bundle.putString("time",string_time);
                //bundle.putString("time2",string_time);
                appointmentStepTwo.setArguments(bundle);
                //Snackbar.make(view, "Clicked " + editText, Snackbar.LENGTH_LONG).show();

                viewModel.setText(bundle.getCharSequence("time"));
                //viewModel.setText(bundle.getCharSequence("time"));


            }
        });
        /*AppointmentStepTwo appointmentStepTwo=new AppointmentStepTwo();
        Bundle bundle=new Bundle();
        bundle.putString("time","Evening");
        appointmentStepTwo.setArguments(bundle);*/



    }

    private void init(final View itemView) {

        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.DATE, 0);
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.DATE, 14);

        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(itemView,R.id.calendarView).range(startDate,endDate).datesNumberOnScreen(4).mode(HorizontalCalendar.Mode.DAYS).defaultSelectedDate(startDate).build();
        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                //Snackbar.make(itemView, "Clicked " +date.getTime(), Snackbar.LENGTH_LONG).show();
                Date date1=date.getTime();
                Calendar c = Calendar.getInstance();
                c.setTime(date1);
                c.add(Calendar.DATE, 1);
                date1 = c.getTime();

                viewModel2.setText(date1.toString().substring(0,10));
                if(selected_date.getTimeInMillis() != date.getTimeInMillis())
                {
                    selected_date = date;

                }
            }
        });



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

                //text1.setText(charSequence);
            }
        });
        viewModel2.getText().observe(getViewLifecycleOwner(), new Observer<CharSequence>() {
            @Override
            public void onChanged(CharSequence charSequence) {

                //text1.setText(charSequence);
            }
        });
    }

}

