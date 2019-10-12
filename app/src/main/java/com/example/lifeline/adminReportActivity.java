package com.example.lifeline;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class adminReportActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference userRef;
    private adminReportAdapter adapter;
    String uid;
    DocumentSnapshot reportSnapshot;
    Uri pdfUri;
    FirebaseStorage storage;
    FirebaseDatabase database;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_report);

        setTitle("Upload Reports");
        userRef=db.collection("applwait");
        setUpRecyclerView();

        storage=FirebaseStorage.getInstance();
        database=FirebaseDatabase.getInstance();
}
    private void setUpRecyclerView() {
        Query query = userRef.whereEqualTo("isAccepted",true);

        FirestoreRecyclerOptions<adminReport> options= new FirestoreRecyclerOptions.Builder<adminReport>()
                .setQuery(query,adminReport.class)
                .build();

        adapter = new adminReportAdapter(options);
        RecyclerView recyclerView = findViewById(R.id.admin_report_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.setOnClickListener(new adminReportAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                reportSnapshot = documentSnapshot;
                if(ContextCompat.checkSelfPermission(adminReportActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED) {
                    selectPDF();
                }
                else
                    ActivityCompat.requestPermissions(adminReportActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},2);
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

    private void selectPDF(){
        Intent intent=new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 75);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 75 && resultCode == RESULT_OK && data != null) {
            pdfUri = data.getData();
            Toast.makeText(this, "File selected : " + data.getData(), Toast.LENGTH_SHORT).show();

            if(pdfUri!=null) {
                uploadFile(pdfUri);
            }
            else
                Toast.makeText(adminReportActivity.this,"Please select a File",Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(adminReportActivity.this, "Please select a file", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            selectPDF();
        } else
            Toast.makeText(adminReportActivity.this, "Please Provide Storage Permission", Toast.LENGTH_SHORT).show();
    }

    private void uploadFile(Uri pdfUri) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Uploading file...");
        progressDialog.setProgress(0);
        progressDialog.show();
        final String fileName = System.currentTimeMillis() + "";
        StorageReference storageReference = storage.getReference();
        storageReference.child("Uploads").child(fileName).putFile(pdfUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        final String url = taskSnapshot.getStorage().getDownloadUrl().toString();//Changed from deprecated, solution at  https://www.codeproject.com/Questions/1248011/What-do-I-use-instead-of-getdownloadurl-in-android
                        DatabaseReference reference = database.getReference();
                        reference.child(fileName).setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(adminReportActivity.this, "File successfully uploaded " + url, Toast.LENGTH_LONG).show();
                                    //addUserDoc();
                                } //else
                                    //Toast.makeText(adminReportActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(adminReportActivity.this, "File not successfully uploaded", Toast.LENGTH_SHORT);
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                int currentProgress = (int) (100 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                progressDialog.setProgress(currentProgress);
                if (currentProgress == 100) {
                    progressDialog.cancel();
                    addUserDoc();
                    //Toast.makeText(adminReportActivity.this, "File successfully uploaded", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void addUserDoc(){
        uid = reportSnapshot.getString("uid");
        String applId = reportSnapshot.getId();
        String timeSlot = reportSnapshot.getString("timeSlot");
        String repdoc = reportSnapshot.getString("repdoc");
        String doa = reportSnapshot.getString("doa");
        Map<String, Object> userReport = new HashMap<>();
        userReport.put("timeSlot", timeSlot);
        userReport.put("repdoc", repdoc);
        userReport.put("doa", doa);
        db.collection("users").document(uid)
                .collection("history")
                .document(applId)
                .set(userReport)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        finish();
                        Toast.makeText(adminReportActivity.this, "Report Delivered to User", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(adminReportActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }






}
