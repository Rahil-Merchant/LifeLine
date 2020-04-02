package com.example.lifeline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.widget.Toast;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class PendingApplicationsActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference userRef,applRef;
    private PendingApplicationsAdapter adapter;
    String uid,date_today;
    int times_donated,rewards_countInc=0;
    private DocumentSnapshot applSnapshot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_applications);
        setTitle("Appointments");
        userRef=db.collection("users");
        applRef=db.collection("applwait");
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        date_today = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        Query query = applRef.whereEqualTo("isAccepted", false);//.whereEqualTo("doa", date_today);

        FirestoreRecyclerOptions<PendingApplications> options = new FirestoreRecyclerOptions.Builder<PendingApplications>()
                .setQuery(query, PendingApplications.class)
                .build();

        adapter = new PendingApplicationsAdapter(options);
        RecyclerView recyclerView = findViewById(R.id.Pending_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.setOnClickListener(new PendingApplicationsAdapter.OnItemClickListener() {
            @Override
            public void onDonatedBtnClick(final DocumentSnapshot documentSnapshot, int position) {
                applSnapshot = documentSnapshot;
                uid = documentSnapshot.getString("uid");
                userRef.document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot userDoc = task.getResult();
                            times_donated = userDoc.getLong("timesDonated").intValue();
                            if (times_donated%5 == 4)
                                rewards_countInc = 1;
                            else
                                rewards_countInc=0;

                            Map<String, Object> userUpdate = new HashMap<>();
                            userUpdate.put("last_donated", date_today);
                            userUpdate.put("timesDonated", FieldValue.increment(1));
                            userUpdate.put("rewards_count", FieldValue.increment(rewards_countInc));
                            userRef.document(uid).update(userUpdate).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    applUpdate();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(PendingApplicationsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else{
                            Toast.makeText(PendingApplicationsActivity.this,"Unexpected Error, Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    void applUpdate(){
        applRef.document(applSnapshot.getId()).update("isAccepted",true).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(PendingApplicationsActivity.this, "Information Updated", Toast.LENGTH_SHORT).show();
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
