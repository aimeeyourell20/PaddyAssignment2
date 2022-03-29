package com.example.paddyassignmenttwo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Registration_Customer extends AppCompatActivity {

    private TextInputEditText mRegisterEmail, mRegisterName, mRegisterPassword;
    private Button mCustomerSignUpButton;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference RootRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mRegisterEmail = findViewById(R.id.registerEmail);
        mRegisterName = findViewById(R.id.registerName);
        mRegisterPassword = findViewById(R.id.registerPassword);
        mCustomerSignUpButton = findViewById(R.id.customerSignUpButton);

        firebaseAuth = FirebaseAuth.getInstance();

        mCustomerSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signupUser();
            }
        });
    }

    private void signupUser() {
        String email = mRegisterEmail.getText().toString().trim();
        String password = mRegisterPassword.getText().toString().trim();
        String name = mRegisterName.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            mRegisterEmail.setError("Email required");
            return;
        }
        if (TextUtils.isEmpty(name)) {
            mRegisterName.setError("Fullname required");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            mRegisterPassword.setError("Password required");
            return;
        }
        if (password.length() < 6) {
            mRegisterPassword.setError("Password must be more than 6 characters");
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (!task.isSuccessful()) {
                    Toast.makeText(Registration_Customer.this, "Registration unsuccessful", Toast.LENGTH_SHORT).show();
                } else {
                    String currentUser = firebaseAuth.getCurrentUser().getUid();
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    //user.sendEmailVerification();
                    RootRef = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser);
                    HashMap mentee = new HashMap();
                    mentee.put("name", name);
                    mentee.put("email", email);
                    mentee.put("type", "customer");
                    mentee.put("profileimage", "");


                    RootRef.updateChildren(mentee).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Registration_Customer.this, "User info successfully added", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Registration_Customer.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                            finish();
                        }
                    });

                }
            }
        });

        Intent i = new Intent(Registration_Customer.this, Login.class);
        startActivity(i);
        finish();;
    }
}

