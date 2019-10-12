package com.example.lifeline;

import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class adminReportActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference userRef;
    private adminReportAdapter adapter;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_report);

        setTitle("View Reports");
        userRef=db.collection("applwait");
        setUpRecyclerView();
}
    private void setUpRecyclerView() {
        Query query = userRef/*.orderBy("doa", Query.Direction.DESCENDING).whereEqualTo("isAccepted",true)*/;

        FirestoreRecyclerOptions<adminReport> options= new FirestoreRecyclerOptions.Builder<adminReport>()
                .setQuery(query,adminReport.class)
                .build();

        adapter = new adminReportAdapter(options);
        RecyclerView recyclerView = findViewById(R.id.admin_report_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

//        adapter.setOnClickListener(new RedeemAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
//
//                uid = documentSnapshot.getId();
//                userRef.document(uid)
//                        .update("rewards_count", FieldValue.increment(-1)).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if(task.isSuccessful()){
//                            finish();
//                            Toast.makeText(RedeemActivity.this, "Reward Redeemed", Toast.LENGTH_SHORT).show();
//                        }
//                        else{
//                            Toast.makeText(RedeemActivity.this, "Unexpected Error", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//            }
//        });

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
