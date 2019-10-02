package com.example.lifeline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    EditText upEmailEt, upPasswordEt, upConfirmPasswordEt;
    private FirebaseAuth mAuth;
    ProgressBar upPbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setTitle("Sign Up");
        upEmailEt = findViewById(R.id.upEmail);
        upPasswordEt = findViewById(R.id.upPassword);
        upPbar=findViewById(R.id.upProgressBar);
        upConfirmPasswordEt=findViewById(R.id.upConfirmPassword);
        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.upSignup).setOnClickListener(this);
        findViewById(R.id.upSignin).setOnClickListener(this);
    }

    private void registerUser() {
        String email = upEmailEt.getText().toString();
        String password = upPasswordEt.getText().toString();
        String confPassword = upConfirmPasswordEt.getText().toString();

        if (email.isEmpty()) {
            upEmailEt.setError("Email is required");
            upEmailEt.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            upEmailEt.setError("Please enter a valid email");
            upEmailEt.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            upPasswordEt.setError("Password is required");
            upPasswordEt.requestFocus();
            return;
        }

        if (password.length() < 6) {
            upPasswordEt.setError("Password must contain at least 6 characters");
            upPasswordEt.requestFocus();
            return;
        }

        if(!confPassword.equals(password)){
            upConfirmPasswordEt.setError("Passwords do not match");
            upConfirmPasswordEt.requestFocus();
            return;
        }

        upPbar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                upPbar.setVisibility(View.GONE);
                if (task.isSuccessful())
                {
                    //finish();
                    //Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
                    //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  //So that we dont return to login screen after pressing back button
                    //startActivity(intent);
                    //Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();

                    FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getApplicationContext(), "Registration successful, verification mail sent", Toast.LENGTH_SHORT).show();
                            //FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(SignUpActivity.this, InfoActivity.class));
                            finish();
                        }
                    });


                }
                else
                {
                    if(task.getException() instanceof FirebaseAuthUserCollisionException)
                    {
                        Toast.makeText(getApplicationContext(), "ERROR: This email has already been registered", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.upSignup:
                registerUser();
                break;

            case R.id.upSignin:
                finish();
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }
}
