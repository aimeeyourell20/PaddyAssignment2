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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Cart extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button mContinue;
    private TextView mTotalPrice;
    private FirebaseAuth mAuth;
    private String Customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        mContinue = findViewById(R.id.continueButton);
        mTotalPrice = findViewById(R.id.totalPrice);

        mAuth = FirebaseAuth.getInstance();
        Customer = mAuth.getCurrentUser().getUid();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


    }

    @Override
    protected void onStart() {
        super.onStart();

        DatabaseReference Cart = FirebaseDatabase.getInstance().getReference().child("Cart");

        FirebaseRecyclerOptions<Cart_Model> firebaseRecyclerOptions = new FirebaseRecyclerOptions.Builder<Cart_Model>().setQuery(Cart.child("Customer View").child(Customer).child("Items"), Cart_Model.class).build();

        FirebaseRecyclerAdapter<Cart_Model, CartViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Cart_Model, CartViewHolder>(firebaseRecyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int i, @NonNull Cart_Model cart_model) {

                cartViewHolder.mItemName.setText(cart_model.getTitle());
                cartViewHolder.mItemPrice.setText("Price: €" + cart_model.getPrice());
                cartViewHolder.mItemQuantity.setText("Quantity: " + cart_model.getQuantity());
            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_layout, parent, false);
                CartViewHolder cartViewHolder = new CartViewHolder(v);
                return cartViewHolder;
            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();

    }
}