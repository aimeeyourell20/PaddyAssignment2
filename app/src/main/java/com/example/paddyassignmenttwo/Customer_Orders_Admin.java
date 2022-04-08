package com.example.paddyassignmenttwo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Customer_Orders_Admin extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private DatabaseReference RootRef;
    private final ArrayList<Orders_Model> orders_models  = new ArrayList<>();
    private Orders_Adapter orders_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_orders_admin);

        RootRef = FirebaseDatabase.getInstance().getReference().child("Orders");

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        FetchOrders();
    }

    private void FetchOrders() {
        RootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //Loops through to retrieve goals
                for (DataSnapshot a : snapshot.getChildren()) {
                    for (DataSnapshot b : a.getChildren()) {
                        Orders_Model g = b.getValue(Orders_Model.class);
                        orders_models.add(g);
                    }

                }
                    orders_adapter = new Orders_Adapter(Customer_Orders_Admin.this, orders_models);
                    mRecyclerView.setAdapter(orders_adapter);
                    orders_adapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Customer_Orders_Admin.this, "Error", Toast.LENGTH_SHORT).show();
            }

        });
    }
}