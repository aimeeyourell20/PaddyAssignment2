package com.example.paddyassignmenttwo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;



public class Rate_Product_Activity extends AppCompatActivity {

    private Button mRateProductButton;
    private String itemId = "";
    private String customerId;
    private DatabaseReference RootRef;
    private RatingBar mRatingBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_product);

        mRateProductButton = findViewById(R.id.rateProduct);
        mRatingBar = findViewById(R.id.RatingBar);
        mAuth = FirebaseAuth.getInstance();
        customerId = mAuth.getCurrentUser().getUid();

        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();

            if (extras != null) {
                itemId = (String) extras.get("itemId");
                //messageReceiverName = (String) extras.get("name");
            }
        }

        mRateProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Rate_Product_Activity.this, CustomerMainActivity.class);
                startActivity(i);
                finish();
            }
        });

        RootRef = FirebaseDatabase.getInstance().getReference().child("Items");

        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                RootRef.child(itemId).child("Rating").child(customerId).child("rating").push();
                RootRef.child(itemId).child("Rating").child(customerId).child("rating").setValue(v);
            }
        });
    }
}

