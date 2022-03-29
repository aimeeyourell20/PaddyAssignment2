package com.example.paddyassignmenttwo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class Admin_Profile extends AppCompatActivity {

    private TextView mName, mType, mEmail;
    private ImageView mSearch, mItem, mSettings, mProfile;
    private Button home;
    private DatabaseReference RootRef;
    private FirebaseAuth firebaseAuth;
    private String currentUser;
    private static int PICK_IMAGE = 123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser().getUid();
        RootRef = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser);

        mName = findViewById(R.id.profileNameTxt);
        mType = findViewById(R.id.typeProfileTxt);
        mEmail = findViewById(R.id.email);
        mSearch = findViewById(R.id.search);
        mItem = findViewById(R.id.item);
        mSettings = findViewById(R.id.editProfile);
        home = findViewById(R.id.home);
        mProfile = findViewById(R.id.profileImageProfile);

        RootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String name = snapshot.child("name").getValue().toString();
                    String email = snapshot.child("email").getValue().toString();
                    String type = snapshot.child("type").getValue().toString();
                    String photo = snapshot.child("profileimage").getValue().toString();
                    Glide.with(getApplicationContext()).load(photo).into(mProfile);


                    mName.setText(name);
                    mEmail.setText(email);
                    mType.setText(type);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Admin_Profile.this, Search_Admin.class);
                startActivity(i);            }
        });

        mItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Admin_Profile.this, Items.class);
                startActivity(i);
                finish();
            }
        });

        mSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Admin_Profile.this, Admin_Settings.class);
                startActivity(i);
                finish();

            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Admin_Profile.this, AdminMainActivity.class);
                startActivity(i);
                finish();

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

}



