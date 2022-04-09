package com.example.paddyassignmenttwo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class View_Customers_Activity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private DatabaseReference RootRef;
    private FirebaseAuth firebaseAuth;
    private String MenteeOnline;
    private ImageView mHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_customers);

        firebaseAuth = FirebaseAuth.getInstance();
        MenteeOnline = firebaseAuth.getCurrentUser().getUid();
        //Goes into users child
        RootRef = FirebaseDatabase.getInstance().getReference().child("users");


        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        DisplayAllCustomers();

    }

    public void DisplayAllCustomers(){
        super.onStart();

        FirebaseRecyclerOptions<Customer_Model> firebaseRecyclerOptions = new FirebaseRecyclerOptions.Builder<Customer_Model>().setQuery(RootRef, Customer_Model.class).build();

        FirebaseRecyclerAdapter<Customer_Model, CustomerViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Customer_Model, CustomerViewHolder>(firebaseRecyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull CustomerViewHolder cartViewHolder, int i, @NonNull Customer_Model customer_model) {

                if(customer_model.getType().equals("admin")){
                    cartViewHolder.itemView.setVisibility(View.GONE);
                    cartViewHolder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                }

                cartViewHolder.mName.setText(customer_model.getName());
                cartViewHolder.mEmail.setText("Email: " + customer_model.getEmail());
                cartViewHolder.mAddress.setText("Address: " + customer_model.getAddress());
                cartViewHolder.mPayment.setText("Payment: " + customer_model.getPayment());
            }


            @NonNull
            @Override
            public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_customers_displayed, parent, false);
                CustomerViewHolder cartViewHolder = new CustomerViewHolder(v);
                return cartViewHolder;
            }
        };

        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();

    }
}