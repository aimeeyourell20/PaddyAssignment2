package com.example.paddyassignmenttwo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Product_Review_Activity extends AppCompatActivity {

    private Button mProductButton;
    private String itemId = "";
    private String customerId;
    private DatabaseReference RootRef;
    private EditText mProductReview;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_review);

        RootRef = FirebaseDatabase.getInstance().getReference().child("Items");

        mProductButton = findViewById(R.id.rateProduct);
        mProductReview = findViewById(R.id.productReview);
        mAuth = FirebaseAuth.getInstance();
        customerId = mAuth.getCurrentUser().getUid();


        mProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Validation();
            }
        });

    }

    public void Validation(){
        String review = mProductReview.getText().toString();

        if(TextUtils.isEmpty(review)){
            mProductReview.setError("Please enter review");
        }
        else{
            AddReview(review);

        }
    }

    private void AddReview(String review) {

        HashMap<String, Object> reviewMap = new HashMap<>();
        reviewMap.put("review", review);

        RootRef.child(itemId).child("Rating").child(customerId).updateChildren(reviewMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Toast.makeText(Product_Review_Activity.this, "Review saved", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Product_Review_Activity.this, CustomerMainActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    Toast.makeText(Product_Review_Activity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}

