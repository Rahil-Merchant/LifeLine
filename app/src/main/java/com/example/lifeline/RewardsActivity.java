package com.example.lifeline;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

public class RewardsActivity extends AppCompatActivity {
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    String uid,reward_count,rewards_remaining;
    int reward_countInt,timesDonatedInt,rewards_remainingInt;
    ProgressBar rewardPbar;
    TextView reward_countTv,reward_remainingTv;
    DocumentReference docRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards);
        setTitle("My Rewards");
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        rewardPbar = findViewById(R.id.rewards_pbar);
        reward_countTv = findViewById(R.id.rewards_count);
        reward_remainingTv = findViewById(R.id.rewards_remaining);
        uid = mAuth.getCurrentUser().getUid();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //here instead of onCreate so that query doesn't run in background and inc bandwidth
        docRef = db.collection("users").document(uid);
        docRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Toast.makeText(RewardsActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    reward_countInt = snapshot.getLong("rewards_count").intValue();
                    reward_count = String.valueOf(reward_countInt);
                    reward_countTv.setText(reward_count);
                    timesDonatedInt = snapshot.getLong("timesDonated").intValue();
                    timesDonatedInt = timesDonatedInt % 5;
                    rewards_remainingInt = 5 - timesDonatedInt;
                    rewards_remaining = String.valueOf(rewards_remainingInt);
                    reward_remainingTv.setText(rewards_remaining);
                    rewardPbar.setProgress(timesDonatedInt);
                } else {
                    Toast.makeText(RewardsActivity.this, "Unexpected Error", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
