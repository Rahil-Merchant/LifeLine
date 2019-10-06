package com.example.lifeline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
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
    }

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
