package com.example.lifeline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class LeaderboardActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user;
    private CollectionReference userRef;
    private LeaderboardAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        setTitle("Leaderboard");
        userRef=db.collection("users");
        setUpRecyclerView();

        BottomNavigationView bottomNav=findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListner);
        Menu menu =bottomNav.getMenu();
        MenuItem menuItem=menu.getItem(3);
        menuItem.setChecked(true);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListner=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.navigation_profile:
                    startActivity(new Intent(LeaderboardActivity.this,homeActivity.class));
                    break;
                case R.id.navigation_appointment:
                    break;
                case R.id.navigation_events:
                    startActivity(new Intent(LeaderboardActivity.this,UserEventMain.class));
                    break;
                case R.id.navigation_leaderboard:
                    startActivity(new Intent(LeaderboardActivity.this,LeaderboardActivity.class));
                    break;

            }
            return true;
        }
    };

    private void setUpRecyclerView() {
        Query query = userRef.orderBy("timesDonated", Query.Direction.DESCENDING)
                .whereGreaterThan("timesDonated",0);

        FirestoreRecyclerOptions<Leaderboard> options= new FirestoreRecyclerOptions.Builder<Leaderboard>()
                .setQuery(query,Leaderboard.class)
                .build();

        adapter = new LeaderboardAdapter(options);
        RecyclerView recyclerView = findViewById(R.id.Leaderboard_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
