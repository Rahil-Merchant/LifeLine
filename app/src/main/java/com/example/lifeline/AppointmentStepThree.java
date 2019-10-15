package com.example.lifeline;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class AppointmentStepThree extends Fragment implements View.OnClickListener {

    String fname,mname,lname,gender,occupation,organization,mobNo,bloodGrp,dob,uid,email,last_donated;
    int rewards_count,timesDonated;
    private FirebaseAuth mAuth;
    CardView option1, option2;
    String timeSlot,doa;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    static AppointmentStepThree instance;

    public static AppointmentStepThree getInstance()
    {
        if(instance == null)
        {
            instance = new AppointmentStepThree();
        }
        return instance;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View itemView =  inflater.inflate(R.layout.appointment_step_three  ,container,false);
        mAuth = FirebaseAuth.getInstance();
        timeSlot = "2-3 PM";
        doa = "15/10/2019";
        option1 = itemView.findViewById(R.id.option_one);
        option2 = itemView.findViewById(R.id.option_two);
        uid = mAuth.getUid();
        email = mAuth.getCurrentUser().getEmail();

        option2.setOnClickListener(this);
        option1.setOnClickListener(this);

        return itemView;



    }

    void saveInfo(){
        DocumentReference userRef = db.collection("users").document(uid);
        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                fname = documentSnapshot.getString("fname");
                mname = documentSnapshot.getString("mname");
                lname = documentSnapshot.getString("lname");
                gender = documentSnapshot.getString("gender");
                mobNo = documentSnapshot.getString("mobNo");
                last_donated = documentSnapshot.getString("last_donated");
                dob = documentSnapshot.getString("dob");
                occupation = documentSnapshot.getString("occupation");
                organization = documentSnapshot.getString("organization");
                rewards_count = documentSnapshot.getLong("rewards_count").intValue();
                timesDonated = documentSnapshot.getLong("timesDonated").intValue();
                bloodGrp = documentSnapshot.getString("bloodGrp");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        createAppl();
    }

    void createAppl(){
        Map<String, Object> newAppl = new HashMap<>();
        newAppl.put("fname", fname);
        newAppl.put("mname", mname);
        newAppl.put("lname", lname);
        newAppl.put("gender", gender);
        newAppl.put("occupation", occupation);
        newAppl.put("last_donated", last_donated);
        newAppl.put("organization", organization);
        newAppl.put("mobNo", mobNo);
        newAppl.put("bloodGrp", bloodGrp);
        newAppl.put("dob", dob);
        newAppl.put("rewards_count", rewards_count);
        newAppl.put("isAccepted", false);
        newAppl.put("reqReport", false);
        newAppl.put("repdoc", "no");
        newAppl.put("uid", uid);
        newAppl.put("email", email);
        newAppl.put("doa",doa);
        newAppl.put("timeSlot",timeSlot);

        DocumentReference applRef = db.collection("applwait").document(getSaltString());
        applRef.set(newAppl).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                getActivity().finish();
                startActivity(new Intent(getContext(),homeActivity.class));
                Toast.makeText(getContext(), "Appointment Scheduled", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Unexpected Error, Please Check Your Internet Connection", Toast.LENGTH_LONG).show();
            }
        });
    }

    protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.option_one:
                saveInfo();
                break;
            case R.id.option_two:
                Intent i = new Intent(getContext(), PaytmActivity.class);;
                i.putExtra("timeSlot", timeSlot);
                i.putExtra("doa", doa);
                startActivity(i);
                break;

        }
    }
}