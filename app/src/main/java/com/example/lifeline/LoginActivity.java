package com.example.lifeline;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    EditText inEmailEt, inPasswordEt;
    ProgressBar Pbar;
    FirebaseUser user;
    private int RC_SIGN_IN = 1;

    GoogleSignInClient mGoogleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("Login");
        FirebaseApp.initializeApp(this);
        inEmailEt = findViewById(R.id.inEmail);
        inPasswordEt = findViewById(R.id.inPassword);
        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.inSignup).setOnClickListener(this);
        findViewById(R.id.inSignin).setOnClickListener(this);
        findViewById(R.id.inForgot).setOnClickListener(this);
        findViewById(R.id.gSignIn).setOnClickListener(this);
        Pbar=findViewById(R.id.inPbar);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient=GoogleSignIn.getClient(this,gso);
    }

    void googleSignIn(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent,RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask){
        try{
            GoogleSignInAccount acc = completedTask.getResult(ApiException.class);
            Toast.makeText(this, "Signed In Successfully", Toast.LENGTH_SHORT).show();
            firebaseAuthWithGoogle(acc);
        }
        catch (ApiException e){
            Toast.makeText(this, "Sign In Failed", Toast.LENGTH_SHORT).show();
            firebaseAuthWithGoogle(null);
        }
    }




    private void userLogin() {
        String email = inEmailEt.getText().toString();
        String password = inPasswordEt.getText().toString();

        if (email.isEmpty()) {
            inEmailEt.setError("Email is required");
            inEmailEt.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            inEmailEt.setError("Please enter a valid email");
            inEmailEt.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            inPasswordEt.setError("Password is required");
            inPasswordEt.requestFocus();
            return;
        }

        if (password.length() < 6) {
            inPasswordEt.setError("Password must contain at least 6 characters");
            inPasswordEt.requestFocus();
            return;
        }


        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void
            onComplete(@NonNull Task<AuthResult> task) {
                // inPbar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    Pbar.setVisibility(View.GONE);
                    user = mAuth.getCurrentUser();
                    if (user != null && user.getEmail().equals("rahil.merchant69@nmims.edu.in")) {
                        finish();
                        startActivity(new Intent(getApplicationContext(),AdminActivity.class));
                        return;
                    }
                    finish();
                    startActivity(new Intent(getApplicationContext(), homeActivity.class));
                }

                else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    Pbar.setVisibility(View.GONE);
                }
            }
        });
    }

   private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d("TAG", "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Pbar.setVisibility(View.GONE);
                            boolean newuser = task.getResult().getAdditionalUserInfo().isNewUser();
                            if (newuser) {
                                Toast.makeText(LoginActivity.this, "Welcome to LifeLine", Toast.LENGTH_SHORT).show();
                                //FirebaseUser user = mAuth.getCurrentUser();
                                finish();
                                startActivity(new Intent(getApplicationContext(), InfoActivity.class));
                            }
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d("TAG", "signInWithCredential:success");
                            else {
                                finish();
                                startActivity(new Intent(getApplicationContext(), homeActivity.class));
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Some error occurred", Toast.LENGTH_SHORT).show();
                            Pbar.setVisibility(View.GONE);
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.inSignup:
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
                break;

            case R.id.inSignin:
                Pbar.setVisibility(View.VISIBLE);
                userLogin();
                break;

            case R.id.inForgot:
                startActivity(new Intent(getApplicationContext(),PasswordActivity.class));
                break;

            case R.id.gSignIn:
                Pbar.setVisibility(View.VISIBLE);
                googleSignIn();
                break;

        }
    }


}
