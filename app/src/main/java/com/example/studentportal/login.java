package com.example.studentportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Timer;
import java.util.TimerTask;

public class login extends AppCompatActivity {
    EditText email,password;
    Button login;
    TextView error;
    FirebaseAuth myauth;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Login");
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        error = findViewById(R.id.error);

        myauth = FirebaseAuth.getInstance();

//        forgotPass.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                myauth.sendPasswordResetEmail(email.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        String username = email.getText().toString();
//                        String pass = password.getText().toString();
//
//                        if (username.isEmpty()) {
//                            email.setError("Please enter your email");
//                        }else if (pass.isEmpty()) {
//                            password.setText("forgot");
//                        }  else {
//                            Toast.makeText(login.this, "Password reset email has been sent successfully", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        error.setText("Password reset email not sent, please try again");
//                        error.setVisibility(View.VISIBLE);
//                        Timer t = new Timer(false);
//                        t.schedule(new TimerTask() {
//                            @Override
//                            public void run() {
//                                runOnUiThread(new Runnable() {
//                                    public void run() {
//                                        error.setVisibility(View.INVISIBLE);
//                                    }
//                                });
//                            }
//                        }, 5000);
//                    }
//                });
//
//            }
//        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = email.getText().toString();
                String pass = password.getText().toString();

                if (username.isEmpty()) {
                    email.setError("Please enter your email");
                } else if (pass.isEmpty()) {
                    password.setError("Please enter your password");
                } else {

                    myauth.signInWithEmailAndPassword(username, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(com.example.studentportal.login.this, Homepage.class);
                                intent.putExtra("email", username);
                                startActivity(intent);
                            } else {
                                error.setText("Please check your credentials or network");
                                error.setVisibility(View.VISIBLE);
                                Timer t = new Timer(false);
                                t.schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        runOnUiThread(new Runnable() {
                                            public void run() {
                                                error.setVisibility(View.INVISIBLE);
                                            }
                                        });
                                    }
                                }, 5000);
                            }
                        }
                    });
                }
            }
        });

    }
}