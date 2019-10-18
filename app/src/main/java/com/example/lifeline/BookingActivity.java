package com.example.lifeline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.shuhart.stepview.StepView;

import java.util.ArrayList;
import java.util.List;

public class BookingActivity extends AppCompatActivity {
    public static int step = 0;


    StepView stepView;

    ViewPager viewPager;

    Button btn_prev_step;

    Button btn_next_step;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        setTitle("Book Appointment");
        BottomNavigationView bottomNav=findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListner);
        Menu menu =bottomNav.getMenu();
        MenuItem menuItem=menu.getItem(1);
        menuItem.setChecked(true);
        ButterKnife.bind(BookingActivity.this);
        stepView = findViewById(R.id.step_view);
        viewPager = findViewById(R.id.view_pager);
        btn_prev_step = findViewById(R.id.btn_previous_step);
        btn_next_step = findViewById(R.id.btn_next_step);
        btn_prev_step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(step == 2 || step>0)
                {
                    step--;
                    viewPager.setCurrentItem(step);

                }

            }
        });

        btn_next_step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(step < 2 || step == 0)
                {
                    step++;
                    viewPager.setCurrentItem(step);
                }

            }
        });
        setupStepView();
        setColorButton();

        viewPager.setAdapter(new MyViewPageAdapter(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(3);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int step) {
                stepView.go(step,true);
                if(step==0)
                    btn_prev_step.setEnabled(false);
                else if(step==2)
                    btn_next_step.setEnabled(false);
                else
                    btn_prev_step.setEnabled(true);

                setColorButton();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setColorButton()
    {
        if(btn_next_step.isEnabled())
        {
            btn_next_step.setBackgroundResource(R.color.red);
        }
        else
        {
            btn_next_step.setBackgroundResource(android.R.color.darker_gray);
        }
        if(btn_prev_step.isEnabled())
        {
            btn_prev_step.setBackgroundResource(R.color.red);
        }
        else
        {
            btn_prev_step.setBackgroundResource(android.R.color.darker_gray);
        }
    }

    private void setupStepView()
    {
        List<String> stepList = new ArrayList<>();
        stepList.add("Date And Time");
        stepList.add("Hospital");
        stepList.add("Confirmation");
        stepView.setSteps(stepList);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListner=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.navigation_profile:
                    startActivity(new Intent(BookingActivity.this,homeActivity.class));
                    break;
                case R.id.navigation_appointment:
                    // do nothing
                    break;
                case R.id.navigation_events:
                    startActivity(new Intent(BookingActivity.this,UserEventMain.class));
                    break;
                case R.id.navigation_leaderboard:
                    startActivity(new Intent(BookingActivity.this,LeaderboardActivity.class));
                    break;

            }
            return true;
        }
    };
}
