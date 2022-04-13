package com.example.paddyassignmenttwo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    private TextView mNoAccount, mForgotPassword;
    private TextInputEditText mLoginEmail, mLoginPassword;
    private Button mLoginButton;
    private FirebaseAuth firebaseAuth;
    private Boolean emailAddressCheckers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLoginEmail = findViewById(R.id.LoginEmail);
        mLoginPassword = findViewById(R.id.LoginPassword);
        mLoginButton = findViewById(R.id.LoginButton);
        mNoAccount = findViewById(R.id.noAccount);
        mForgotPassword = findViewById(R.id.forgotPassword);
        firebaseAuth = FirebaseAuth.getInstance();

        //User will be brought to registration
        mNoAccount.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                SendUserToRegisterActivity();
            }
        });

        //User can reset password
        mForgotPassword.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(Login.this, ResetPassword.class));
            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AllowingUserToLogin();
            }
        });
    }

    private void AllowingUserToLogin()
    {
        String email = mLoginEmail.getText().toString();
        String password = mLoginPassword.getText().toString();

        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Please write your email...", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
        }
        else
        {

            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful()) {

                                //Checks to ensure email is verified
                                VerifyEmailAddress();
                            }
                            else
                            {

                                Toast.makeText(Login.this, "Error occurred " , Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        }
    }

    private void VerifyEmailAddress()
    {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        emailAddressCheckers = user.isEmailVerified();

        //If email is verified continue
        if(emailAddressCheckers)
        {

            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            String RegisteredUserID = currentUser.getUid();
            DatabaseReference dr = FirebaseDatabase.getInstance().getReference().child("users").child(RegisteredUserID);

            //Checks to see if user is a mentor or mentee
            dr.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String type = snapshot.child("type").getValue().toString();

                    if (type.equals("customer")) {

                        Toast.makeText(Login.this, "Customer log in successful", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Login.this, CustomerMainActivity.class);
                        startActivity(i);
                        finish();

                    } else if (type.equals("admin")) {

                        Toast.makeText(Login.this, "Admin log in successful", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Login.this, AdminMainActivity.class);
                        startActivity(i);
                        finish();
                    }

                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        else
        {
            Toast.makeText(this, "Please verify your Account first... ", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }


    private void SendUserToRegisterActivity()
    {
        Intent registerIntent = new Intent(Login.this, RegistrationOptions.class);
        startActivity(registerIntent);
    }


}