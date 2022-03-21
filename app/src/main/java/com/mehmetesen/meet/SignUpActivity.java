package com.mehmetesen.meet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mehmetesen.meet.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding activitySignUpBinding;
    private FirebaseAuth auth;
    ProgressDialog progressDialog;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        progressDialog= new ProgressDialog(SignUpActivity.this);
        progressDialog.setTitle("Creating Account ");
        progressDialog.setMessage("we are creating your account ");
        activitySignUpBinding = ActivitySignUpBinding.inflate(getLayoutInflater());
        View view = activitySignUpBinding.getRoot();
        setContentView(view);
    }

    public void Account(View view) {
        progressDialog.show();
        String email,password,name;
        email=activitySignUpBinding.emailsignup.getText().toString();
        password=activitySignUpBinding.passwordsignup.getText().toString();
        name = activitySignUpBinding.signupname.getText().toString();
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setName(name);





        if(email.equals("") || password.equals("")){
            Toast.makeText(this, "Enter email and Password", Toast.LENGTH_SHORT).show();
        }else{
            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressDialog.dismiss();
                    if(task.isSuccessful()){
                        firebaseFirestore.collection("Users").document().set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                                finish();
                            }
                        });

                        Toast.makeText(SignUpActivity.this, "Account is Created", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(SignUpActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }


    }


    public void AlreadyAccount(View view) {
        Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(intent);
    }
}