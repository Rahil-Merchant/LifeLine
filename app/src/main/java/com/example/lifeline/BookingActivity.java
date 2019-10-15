package com.example.lifeline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
        ButterKnife.bind(BookingActivity.this);
        stepView = (StepView) findViewById(R.id.step_view);
        viewPager = (ViewPager)findViewById(R.id.view_pager);
        btn_prev_step = (Button)findViewById(R.id.btn_previous_step);
        btn_next_step = (Button)findViewById(R.id.btn_next_step);
        btn_prev_step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(step == 3 || step>0)
                {
                    step--;
                    viewPager.setCurrentItem(step);

                }

            }
        });

        btn_next_step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(step < 3 || step == 0)
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
            public void onPageSelected(int i) {
                stepView.go(i,true);
                if(i==0)
                    btn_prev_step.setEnabled(false);
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
}
