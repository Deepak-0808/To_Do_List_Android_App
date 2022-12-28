package com.example.mytask;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signin extends AppCompatActivity {

    Button signup,signIn;
    EditText email,password;
    TextView forgotPassword;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        signup=findViewById(R.id.btnSignUp);
        signIn=findViewById(R.id.btnsignin);
        email=findViewById(R.id.edtEmail);
        password=findViewById(R.id.edtPassword);
        forgotPassword=findViewById(R.id.forgotPassword);
        auth=FirebaseAuth.getInstance();

        // Forgot password

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(signin.this,forgotPassword.class);
                startActivity(intent);
            }
        });

        // For SignUp page
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(signin.this,signup.class);
                startActivity(intent);
            }
        });

        // For Signin

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailGet=email.getText().toString();
                String passwordGet=password.getText().toString();

                if (emailGet.isEmpty()||passwordGet.isEmpty())
                {
                    Toast.makeText(signin.this, "Please enter email and password ", Toast.LENGTH_LONG).show();
                }
                else
                {
                    signinUSer(emailGet,passwordGet);
                }
            }
        });
    }

    // function of signin

    private void signinUSer(String emailGet, String passwordGet) {
        auth.signInWithEmailAndPassword(emailGet,passwordGet).addOnSuccessListener(signin.this, new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(signin.this, "SignIn successful ", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(signin.this,homePage.class);
                startActivity(intent);
            }
            
        });

//        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();

    }
}