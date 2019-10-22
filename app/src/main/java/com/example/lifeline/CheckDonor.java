package com.example.lifeline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

public class CheckDonor extends AppCompatActivity {

    private ViewPager viewPager;
    private  CheckDonorAdapter checkDonorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_donor);

        viewPager=findViewById(R.id.page1);
        checkDonorAdapter=new CheckDonorAdapter(getSupportFragmentManager(),1);
        viewPager.setAdapter(checkDonorAdapter);

    }
}
