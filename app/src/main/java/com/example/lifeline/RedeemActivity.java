package com.example.lifeline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.widget.Toast;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class RedeemActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference userRef;
    private RedeemAdapter adapter;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem);

        setTitle("Redeem User Rewards");
        userRef=db.collection("users");
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        Query query = userRef.orderBy("rewards_count", Query.Direction.DESCENDING)
                .whereGreaterThan("rewards_count",0);

        FirestoreRecyclerOptions<Redeem> options= new FirestoreRecyclerOptions.Builder<Redeem>()
                .setQuery(query,Redeem.class)
                .build();

        adapter = new RedeemAdapter(options);
        RecyclerView recyclerView = findViewById(R.id.Redeem_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.setOnClickListener(new RedeemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {

                uid = documentSnapshot.getId();
                userRef.document(uid)
                        .update("rewards_count", FieldValue.increment(-1)).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            finish();
                            Toast.makeText(RedeemActivity.this, "Reward Redeemed", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(RedeemActivity.this, "Unexpected Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

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
