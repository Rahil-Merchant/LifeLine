package com.example.lifeline;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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

    HorizontalCalendarView calendarView;
    SimpleDateFormat simpleDateFormat;
    public static Calendar selected_date = Calendar.getInstance();
    public static String string_time = "lul";
    AlertDialog dialog;
    LocalBroadcastManager localBroadcastManager;
    Unbinder unbinder;
    MaterialSpinner spinner;
    List<String> timing_list = new ArrayList<>();


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

    public static AppointmentStepOne getInstance()
    {
        if(instance == null)
        {
            instance = new AppointmentStepOne();
        }
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        timing_list.add("");
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
        return itemView;

    }



    private void confirmBooking()
    {
        Intent intent = new Intent("Confirm Booking");
        localBroadcastManager.sendBroadcast(intent);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                string_time = item.toString();
                //Snackbar.make(view, "Clicked " + string_time, Snackbar.LENGTH_LONG).show();
            }
        });


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
               // Snackbar.make(itemView, "Clicked " + selected_date.getTime(), Snackbar.LENGTH_LONG).show();
                if(selected_date.getTimeInMillis() != date.getTimeInMillis())
                {
                    selected_date = date;
                }
            }
        });

    }
}

