package com.example.lifeline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class homeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle("Home");

        findViewById(R.id.homeLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(homeActivity.this, LoginActivity.class));
            }
        });

        findViewById(R.id.homeLeader).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(homeActivity.this, LeaderboardActivity.class));
            }
        });

        findViewById(R.id.homeRewards).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(homeActivity.this, RewardsActivity.class));
            }
        });
    }
}
