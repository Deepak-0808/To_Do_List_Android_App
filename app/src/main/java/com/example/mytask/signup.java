package com.example.mytask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class signup extends AppCompatActivity {

    Button btnbacktosignin,btnSubmit;
    EditText gmailId,edtPasswordSignup,edtName;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        btnbacktosignin=findViewById(R.id.btnSignUp);
        gmailId=findViewById(R.id.gmailId);
        edtPasswordSignup=findViewById(R.id.edtPasswordSignup);
        edtName=findViewById(R.id.edtName);
        btnSubmit=findViewById(R.id.btnSubmit);

        // Set on click listener for Back to the signIn page
        btnbacktosignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(signup.this,signin.class);
                startActivity(intent);
            }
        });

        // taking FirebaseAuth instance
        mAuth=FirebaseAuth.getInstance();

        // Set on Click Listener on Registration button
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                newUserRegister();
            }
        });

    }

    // Function for email Authentication

    private void newUserRegister() {
        String email,password,name;
        name=edtName.getText().toString();
        email= gmailId.getText().toString();
        password=edtPasswordSignup.getText().toString();

        // If user not fill Name box then it will give warning
        if (TextUtils.isEmpty(name)){
            Toast.makeText(this, "Please enter Name", Toast.LENGTH_SHORT).show();
            return;
        }

        // If user not fill email box then it will give warning
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }

        // If user not fill password box then it will give warning
        if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }

        // Storing Name and Pssword on Firebase realtime database
        HashMap<String , Object> m=new HashMap<String, Object>();
        m.put("Name",edtName.getText().toString());
        m.put("Password",edtPasswordSignup.getText().toString());
        FirebaseDatabase.getInstance().getReference().child("deepak").push().setValue(m);
        Toast.makeText(signup.this, "Data added", Toast.LENGTH_SHORT).show();

        // create new user or register new user
        mAuth .createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isComplete())
                        {
                              Toast.makeText(signup.this, "Registration Successfull", Toast.LENGTH_SHORT).show();
                              Intent intent = new Intent(signup.this,homePage.class);
                              startActivity(intent);
                        }
                        else {
                            Toast.makeText(signup.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(signup.this,signup.class);
                            startActivity(intent);
                        }
                    }
                });


    }
}
