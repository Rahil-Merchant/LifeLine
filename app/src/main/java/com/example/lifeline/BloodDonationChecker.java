package com.example.lifeline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Toast;

public class BloodDonationChecker extends AppCompatActivity {
    private CheckBox checkBox1;
    private CheckBox checkBox2;
    private CheckBox checkBox3;
    private CheckBox checkBox4;
    private CheckBox checkBox5;
    private CheckBox checkBox6;
    private CheckBox checkBox7;
    RadioButton radioButton1;
    RadioButton radioButton2;
    private CheckBox checkBox8;
    private CheckBox checkBox9;
    private CheckBox checkBox10;
    private CheckBox checkBox11;
    private CheckBox checkBox12;
    private CheckBox checkBox13;
    private CheckBox checkBox14;
    private CheckBox checkBox15;
    private CheckBox checkBox16;
    private CheckBox checkBox17;
    private CheckBox checkBox18;
    private CheckBox checkBox19;
    private CheckBox checkBox20;
    public int count=0;
    private int countr=0;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_donation_checker);

        checkBox1 = findViewById(R.id.checkbox1);
        checkBox2 = findViewById(R.id.checkbox2);
        checkBox3 = findViewById(R.id.checkbox3);
        checkBox4 = findViewById(R.id.checkbox4);
        checkBox5 = findViewById(R.id.checkbox8);
        checkBox6 = findViewById(R.id.checkbox9);
        checkBox7 = findViewById(R.id.checkbox10);
        checkBox8=findViewById(R.id.checkbox11);
        checkBox9=findViewById(R.id.checkbox12);
        checkBox10=findViewById(R.id.checkbox13);
        checkBox11=findViewById(R.id.checkbox14);
        checkBox12=findViewById(R.id.checkbox15);
        checkBox13=findViewById(R.id.checkbox16);
        checkBox14=findViewById(R.id.checkbox17);
        checkBox15=findViewById(R.id.checkbox18);
        checkBox16=findViewById(R.id.checkbox20);
        checkBox17=findViewById(R.id.checkbox21);
        checkBox18=findViewById(R.id.checkbox22);
        checkBox19=findViewById(R.id.checkbox23);
        button=findViewById(R.id.finish);









        //final GlobalCount globalCount=(GlobalCount) getApplicationContext();



        checkBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox1.isChecked()){
                    count++;
                }

                else{
                    count--;
                }

                //Toast.makeText(getContext()," "+count,Toast.LENGTH_LONG).show();
            }
        });


        checkBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox2.isChecked()) {
                    count++;
                }
                else{
                    count--;
                }
                //Toast.makeText(getContext()," "+count,Toast.LENGTH_LONG).show();
            }
        });


        checkBox3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox3.isChecked()){
                    count++;
                }

                else{
                    count--;
                }

                //Toast.makeText(getContext()," "+count,Toast.LENGTH_LONG).show();
            }
        });

        checkBox4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox4.isChecked()){
                    count--;
                }

                else{
                    count--;
                }

                //Toast.makeText(getContext()," "+count,Toast.LENGTH_LONG).show();
            }
        });

        checkBox5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox5.isChecked()){
                    count++;
                }

                else{
                    count--;
                }

                //Toast.makeText(getContext()," "+count,Toast.LENGTH_LONG).show();
            }
        });


        checkBox6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox6.isChecked()){
                    count++;
                }

                else{
                    count--;
                }

                //Toast.makeText(getContext()," "+count,Toast.LENGTH_LONG).show();
            }
        });

        checkBox7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox7.isChecked()){
                    count++;
                }

                else{
                    count--;
                }

                //Toast.makeText(getContext()," "+count,Toast.LENGTH_LONG).show();
            }
        });

        checkBox8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox8.isChecked()){
                    count++;
                }

                else{
                    count--;
                }

                //Toast.makeText(getContext()," "+count,Toast.LENGTH_LONG).show();
            }
        });

        checkBox9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox9.isChecked()){
                    count++;
                }

                else{
                    count--;
                }

                //Toast.makeText(getContext()," "+count,Toast.LENGTH_LONG).show();
            }
        });

        checkBox10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox10.isChecked()){
                    count++;
                }

                else{
                    count--;
                }

                //Toast.makeText(getContext()," "+count,Toast.LENGTH_LONG).show();
            }
        });

        checkBox11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox11.isChecked()){
                    count++;
                }

                else{
                    count--;
                }

                //Toast.makeText(getContext()," "+count,Toast.LENGTH_LONG).show();
            }
        });

        checkBox12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox12.isChecked()){
                    count++;
                }

                else{
                    count--;
                }

                //Toast.makeText(getContext()," "+count,Toast.LENGTH_LONG).show();
            }
        });

        checkBox13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox13.isChecked()){
                    count++;
                }

                else{
                    count--;
                }

                //Toast.makeText(getContext()," "+count,Toast.LENGTH_LONG).show();
            }
        });

        checkBox14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox14.isChecked()){
                    count++;
                }

                else{
                    count--;
                }

                //Toast.makeText(getContext()," "+count,Toast.LENGTH_LONG).show();
            }
        });

        checkBox15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox15.isChecked()){
                    count++;
                }

                else{
                    count--;
                }

                //Toast.makeText(getContext()," "+count,Toast.LENGTH_LONG).show();
            }
        });

        checkBox16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox16.isChecked()){
                    count++;
                }

                else{
                    count--;
                }

                //Toast.makeText(getContext()," "+count,Toast.LENGTH_LONG).show();
            }
        });

        checkBox17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox17.isChecked()){
                    count++;
                }

                else{
                    count--;
                }

                //Toast.makeText(getContext()," "+count,Toast.LENGTH_LONG).show();
            }
        });

        checkBox18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox7.isChecked()){
                    count++;
                }

                else{
                    count--;
                }

                //Toast.makeText(getContext()," "+count,Toast.LENGTH_LONG).show();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count==0)
                {
                    Toast.makeText(BloodDonationChecker.this,"You are Eligible for Donating Blood",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(BloodDonationChecker.this,homeActivity.class));
                }
                else {
                    Toast.makeText(BloodDonationChecker.this,"You are Not Eligible for Donating Blood",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(BloodDonationChecker.this,AppointmentStepThree.class));
                }
            }
        });
    }
}
