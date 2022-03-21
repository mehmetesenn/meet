package com.mehmetesen.meet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.mehmetesen.meet.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    ActivityLoginBinding activityLoginBinding;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        progressDialog=new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("Please wait ... ");
        progressDialog.setMessage("Just a moment ");
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        activityLoginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = activityLoginBinding.getRoot();
        setContentView(view);
    }


    public void login(View view) {
        progressDialog.show();
        String email,password;
        email=activityLoginBinding.email.getText().toString();
        password=activityLoginBinding.password.getText().toString();
        if(email.equals("") || password.equals("")){
            Toast.makeText(this, "Enter email and Password", Toast.LENGTH_LONG).show();

        }else{
            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressDialog.dismiss();
                    if(task.isSuccessful()){
                        startActivity(new Intent(LoginActivity.this,DashboardActivity.class));
                        finish();
                    }else{
                        Toast.makeText(LoginActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }


    public void CreateAccount(View view) {
        Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        finish();
        startActivity(intent);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}