package com.example.paddyassignmenttwo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class Customer_Order_Activity extends AppCompatActivity {

    private TextView mOrderAddress, mOrderName, mOrderEmail, mCard, mCardDate;
    private EditText mDiscount, mDigits;
    private Button mConfirmOrder;
    private DatabaseReference RootRef, Orders;
    private FirebaseAuth firebaseAuth;
    private String currentUser;
    private String Total = "";
    private String saveDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_order);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser().getUid();
        RootRef = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser);

        mOrderAddress = findViewById(R.id.customersShippingDetails);
        mOrderName = findViewById(R.id.customersName);
        mOrderEmail = findViewById(R.id.customersEmail);
        mConfirmOrder = findViewById(R.id.confirmOrder);
        mDiscount = findViewById(R.id.discount);
        mCard = findViewById(R.id.card);
        mCardDate = findViewById(R.id.cardDate);
        mDigits = findViewById(R.id.digits);

        Total = getIntent().getStringExtra("Total");

        RootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String name = snapshot.child("name").getValue().toString();
                    String email = snapshot.child("email").getValue().toString();
                    String address = snapshot.child("address").getValue().toString();
                    String card = snapshot.child("card").getValue().toString();
                    String date = snapshot.child("cardExpiryDate").getValue().toString();


                    mOrderName.setText(name);
                    mOrderAddress.setText(address);
                    mOrderEmail.setText(email);
                    mCard.setText(card);
                    mCardDate.setText(date);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        mConfirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Validations();
            }
        });


    }

    private void Validations() {
        String digits = mDigits.getText().toString();
        if(TextUtils.isEmpty(digits)){
            mDigits.setError("You must enter card security number");
        }
        else{
            ConfirmOrder();
        }

    }


    private void ConfirmOrder() {
        HashMap<String, Object> ordersMap = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat date = new SimpleDateFormat("MM dd, yyyy");
        saveDate = date.format(calendar.getTime());
        Orders = FirebaseDatabase.getInstance().getReference().child("Orders").child(currentUser).push();
        String id = Orders.getKey();


        if (mDiscount.getText().toString().equals("TEN")) {
            Total = String.valueOf(Integer.parseInt(Total) - 10);
            ordersMap.put("totalAmount", Total);
            ordersMap.put("name", mOrderName.getText().toString());
            ordersMap.put("address", mOrderAddress.getText().toString());
            ordersMap.put("email", mOrderEmail.getText().toString());
            ordersMap.put("date", saveDate);
            ordersMap.put("orderId", id);
            ordersMap.put("customerId", currentUser);
        } else {
            ordersMap.put("totalAmount", Total);
            ordersMap.put("name", mOrderName.getText().toString());
            ordersMap.put("address", mOrderAddress.getText().toString());
            ordersMap.put("email", mOrderEmail.getText().toString());
            ordersMap.put("date", saveDate);
            ordersMap.put("orderId", id);
            ordersMap.put("customerId", currentUser);

        }

            Orders.updateChildren(ordersMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        FirebaseDatabase.getInstance().getReference().child("Cart").child("Customer View").child(currentUser).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {
                                    Toast.makeText(Customer_Order_Activity.this, "Order Placed", Toast.LENGTH_SHORT).show();

                                    Intent i = new Intent(Customer_Order_Activity.this, CustomerMainActivity.class);
                                    startActivity(i);
                                    finish();
                                }

                            }
                        });
                    }
                }
            });

    }
}