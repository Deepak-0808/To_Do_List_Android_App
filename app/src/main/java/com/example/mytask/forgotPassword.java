package com.example.mytask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotPassword extends AppCompatActivity {
    EditText email;
    Button btnSubmit;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        email=findViewById(R.id.email);
        btnSubmit=findViewById(R.id.btnSubmit);
        auth=FirebaseAuth.getInstance();



        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailGet=email.getText().toString();

                if (emailGet.isEmpty())
                {
                    Toast.makeText(forgotPassword.this, "Enter your registered email", Toast.LENGTH_LONG).show();
                }
                else
                {
                    passwordForget(emailGet);
                }
            }
        });
    }

    private void passwordForget(String emailGet) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(emailGet)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(forgotPassword.this, "Reset Link Sent to your email", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(forgotPassword.this,signin.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(forgotPassword.this, "Failed to send reset email", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
    }

