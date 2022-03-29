package com.example.paddyassignmenttwo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class CustomerMainActivity extends AppCompatActivity {

    private ImageView mSettings, mProfile, mLogout, mCart, mSearch;
    private TextView mProfileName, mType;
    private DatabaseReference RootRef, CustomerRef;
    private FirebaseAuth firebaseAuth;
    private static int PICK_IMAGE = 123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main);

        firebaseAuth = FirebaseAuth.getInstance();
        CustomerRef = FirebaseDatabase.getInstance().getReference().child("users");

        mSettings = findViewById(R.id.settings);
        mProfileName = findViewById(R.id.profilename);
        mProfile = findViewById(R.id.profileimage);
        mType = findViewById(R.id.type);
        mCart = findViewById(R.id.cart);
        mLogout = findViewById(R.id.logout);
        mSearch = findViewById(R.id.search);



        RootRef = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        RootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String name = snapshot.child("name").getValue().toString();
                    mProfileName.setText(name);

                    String types = snapshot.child("type").getValue().toString();
                    mType.setText(types);

                    String photo = snapshot.child("profileimage").getValue().toString();
                    Glide.with(getApplicationContext()).load(photo).into(mProfile);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        mSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Settings();
            }
        });



        mProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Profile();
            }
        });

        mCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cart();
            }
        });

        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Signout();
            }
        });

        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Search();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==PICK_IMAGE && resultCode==RESULT_OK && data!=null){

            Uri image = data.getData();

            CropImage.activity(image).setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(1, 1).start(this);

        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if(currentUser == null){

            Login();
        }else{
            CheckUser();
        }
    }

    private void CheckUser() {

        final String user1 = firebaseAuth.getCurrentUser().getUid();

        CustomerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(!snapshot.hasChild(user1)){

                    RegistrationActivity2();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void RegistrationActivity2() {
        Intent i = new Intent(CustomerMainActivity.this, Registration_Customer.class);
        startActivity(i);
        finish();
    }

    private void Login() {

        Intent i = new Intent(CustomerMainActivity.this, Login.class);
        startActivity(i);
        finish();
    }



    private void Cart() {
        Intent i = new Intent(CustomerMainActivity.this, Cart.class);
        startActivity(i);
        finish();

    }

    private void Search() {
        Intent i = new Intent(CustomerMainActivity.this, Search.class);
        startActivity(i);
        finish();
    }


    private void Profile() {
        Intent i = new Intent(CustomerMainActivity.this, Customer_Profile.class);
        startActivity(i);
        finish();
    }

    private void Settings() {
        Intent i = new Intent(CustomerMainActivity.this, Customer_Settings.class);
        startActivity(i);
        finish();
    }

    private void Signout() {
        Intent i = new Intent(CustomerMainActivity.this, Login.class);
        startActivity(i);
        finish();
    }

}
