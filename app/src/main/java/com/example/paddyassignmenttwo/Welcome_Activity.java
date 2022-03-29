package com.example.paddyassignmenttwo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Welcome_Activity extends AppCompatActivity {

    private Button mAlreadyAccount, mSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        mAlreadyAccount =    findViewById(R.id.loginButton);
        mSignup =            findViewById(R.id.registerButton);

        mAlreadyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Welcome_Activity.this, Login.class);
                startActivity(i);
            }
        });

        mSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Welcome_Activity.this, RegistrationOptions.class);
                startActivity(i);
            }
        });
    }
}