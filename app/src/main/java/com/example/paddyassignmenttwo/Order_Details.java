package com.example.paddyassignmenttwo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.paddyassignmenttwo.Adapters.Order_Details_Adapter;
import com.example.paddyassignmenttwo.Models.Cart_Model;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Order_Details extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference RootRef;
    private String orderId = "";
    private String customerId = "";
    private final ArrayList<Cart_Model> orders_models  = new ArrayList<>();
    private Order_Details_Adapter orders_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        orderId = getIntent().getStringExtra("orderId");
        customerId = getIntent().getStringExtra("customerId");
        RootRef = FirebaseDatabase.getInstance().getReference();
        FetchOrders();

    }
        private void FetchOrders() {
            RootRef.child("Cart").child("Admin").child(customerId).child("Items").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    //Loops through to retrieve goals
                    for (DataSnapshot a : snapshot.getChildren()) {

                            Cart_Model g = a.getValue(Cart_Model.class);
                            orders_models.add(g);


                    }
                    orders_adapter = new Order_Details_Adapter(Order_Details.this, orders_models);
                    mRecyclerView.setAdapter(orders_adapter);
                    orders_adapter.notifyDataSetChanged();


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(Order_Details.this, "Error", Toast.LENGTH_SHORT).show();
                }

            });
        }
    }