package com.example.lifeline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditInfoActivity extends AppCompatActivity {

    String fname,mname,lname,gender,occupation,organization,mobNo,bloodGrp,dob,bloodGrp1,bloodGrp2,uid;
    int dobDay,dobMonth,dobYear,year,month,day,maxYear,minYear;
    Calendar calendar;
    private FirebaseAuth mAuth;
    EditText fnameEt,mnameEt,lnameEt,occupationEt,organizationEt,mobNoEt;
    Button dobBtn,proceedBtn;
    DatePickerDialog datePickerDialog;

    Spinner genderSp,bloodGrpSp1,bloodGrpSp2;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        setTitle("Edit Information");
        mAuth= FirebaseAuth.getInstance();

        fnameEt=findViewById(R.id.infoFname);
        mnameEt=findViewById(R.id.infoMname);
        lnameEt=findViewById(R.id.infoLname);
        occupationEt=findViewById(R.id.infoOccupation);
        organizationEt=findViewById(R.id.infoOrg);
        mobNoEt=findViewById(R.id.infoMobNo);
        proceedBtn=findViewById(R.id.infoProceed);
        proceedBtn.setText("Update");

        genderSp=findViewById(R.id.InfoGender);
        bloodGrpSp1=findViewById(R.id.InfoBloodGrp1);
        bloodGrpSp2=findViewById(R.id.InfoBloodGrp2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.genders,android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,R.array.bloodGrp1string,android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,R.array.bloodGrp2string,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSp.setAdapter(adapter);
        bloodGrpSp1.setAdapter(adapter1);
        bloodGrpSp2.setAdapter(adapter2);
        genderSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                gender=genderSp.getSelectedItem().toString();
                Toast.makeText(EditInfoActivity.this, gender, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        bloodGrpSp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bloodGrp1=bloodGrpSp1.getSelectedItem().toString();
                Toast.makeText(EditInfoActivity.this, bloodGrp1, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        bloodGrpSp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bloodGrp2=bloodGrpSp2.getSelectedItem().toString();
                Toast.makeText(EditInfoActivity.this, bloodGrp2, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        dobBtn = findViewById(R.id.InfoDob);
        dobBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar=Calendar.getInstance();
                year=calendar.get(Calendar.YEAR);
                maxYear=year-18;
                minYear=year-60;
                month=calendar.get(Calendar.MONTH);
                day=calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(EditInfoActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                dobYear = i;
                                dobMonth = i1+1;
                                dobDay = i2;
                                dob = dobDay + "/" + dobMonth + "/" + dobYear;
                                dobBtn.setText(dob);
                            }
                        },maxYear,month,day);
                datePickerDialog.getDatePicker().setMaxDate(getTimeInMillis(day,month,maxYear));
                datePickerDialog.getDatePicker().setMinDate(getTimeInMillis(day,month,minYear));
                datePickerDialog.show();
            }
        });

        findViewById(R.id.infoProceed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveInfo();
            }
        });

        //end of OnCreate
    }

    public static long getTimeInMillis(int day, int month, int year){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month,day);
        return  calendar.getTimeInMillis();
    }

    public static boolean check_mobNo(String s)
    {
        Pattern p = Pattern.compile("^(?:(?:\\+|0{0,2})91(\\s*[\\ -]\\s*)?|[0]?)?[789]\\d{9}|(\\d[ -]?){10}\\d$");
        Matcher m = p.matcher(s);
        return (m.find() && m.group().equals(s));
    }

    void saveInfo(){
        fname=fnameEt.getText().toString();
        mname=mnameEt.getText().toString();
        lname=lnameEt.getText().toString();
        occupation=occupationEt.getText().toString();
        organization=organizationEt.getText().toString();
        mobNo=mobNoEt.getText().toString();
        bloodGrp=bloodGrp1+" "+bloodGrp2;

        if(fname.isEmpty())
        {
            fnameEt.setError("Enter Your First Name");
            fnameEt.requestFocus();
            return;
        }

        if(mname.isEmpty())
        {
            mnameEt.setError("Enter Your Middle Name");
            mnameEt.requestFocus();
            return;
        }
        if(lname.isEmpty())
        {
            lnameEt.setError("Enter Your Last Name");
            lnameEt.requestFocus();
            return;
        }
        if(occupation.isEmpty())
        {
            occupationEt.setError("Enter Your Occupation");
            occupationEt.requestFocus();
            return;
        }
        if(organization.isEmpty())
        {
            organizationEt.setError("Enter Your Organization");
            organizationEt.requestFocus();
            return;
        }
        if(mobNo.isEmpty()|| !check_mobNo(mobNo))
        {
            mobNoEt.setError("Enter a valid mobile number");
            mobNoEt.requestFocus();
            return;
        }

        FirebaseUser user = mAuth.getCurrentUser();

        if(user!=null)
        {
            //Firestore

            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
            DocumentReference uidRef = rootRef.collection("users").document(uid);

            Map<String, Object> newInfo = new HashMap<>();
            newInfo.put("fname", fname);
            newInfo.put("mname", mname);
            newInfo.put("lname", lname);
            newInfo.put("gender", gender);
            newInfo.put("occupation", occupation);
            newInfo.put("organization", organization);
            newInfo.put("mobNo", mobNo);
            newInfo.put("bloodGrp", bloodGrp);
            newInfo.put("dob", dob);

            uidRef.update(newInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(EditInfoActivity.this, "Data Successfully Added", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(getApplicationContext(), homeActivity.class));
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(EditInfoActivity.this, "Unexpected Error, Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else
        {
            Toast.makeText(this, "Unexpected Error", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
