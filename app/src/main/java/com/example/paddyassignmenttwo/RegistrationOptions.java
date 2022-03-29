package com.example.paddyassignmenttwo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegistrationOptions extends AppCompatActivity {

    private Button mCustomerButton, mAdminButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_options);


        mCustomerButton = findViewById(R.id.customerButton);
        mAdminButton = findViewById(R.id.adminButton);

        mCustomerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(RegistrationOptions.this, Registration_Customer.class);
                startActivity(i);
            }
        });

        mAdminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(RegistrationOptions.this, Registration_Admin.class);
                startActivity(i);
            }
        });
    }
}