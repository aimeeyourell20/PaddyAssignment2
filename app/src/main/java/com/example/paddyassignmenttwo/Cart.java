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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.paddyassignmenttwo.Models.Cart_Model;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
    private int Total = 0;

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



        mContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Cart.this, Customer_Order_Activity.class);
                i.putExtra("Total", String.valueOf(Total));
                startActivity(i);
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        DatabaseReference Cart = FirebaseDatabase.getInstance().getReference().child("Cart");

        FirebaseRecyclerOptions<Cart_Model> firebaseRecyclerOptions = new FirebaseRecyclerOptions.Builder<Cart_Model>().setQuery(Cart.child("Customer").child(Customer).child("Items"), Cart_Model.class).build();

        FirebaseRecyclerAdapter<Cart_Model, CartViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Cart_Model, CartViewHolder>(firebaseRecyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int i, @NonNull Cart_Model cart_model) {

                cartViewHolder.mItemName.setText(cart_model.getTitle());
                cartViewHolder.mItemPrice.setText("Price: €" + cart_model.getPrice());
                cartViewHolder.mItemQuantity.setText("Quantity: " + cart_model.getQuantity());

                int TotalPrice = ((Integer.valueOf(cart_model.getPrice()))) * Integer.valueOf(cart_model.getQuantity());
                Total = Total + TotalPrice;
                mTotalPrice.setText("Total Price: " + String.valueOf(Total));

                cartViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CharSequence charSequence[] = new CharSequence[]{

                                "Edit Item",
                                "Delete Item"

                        };

                        //Builder Pattern
                        AlertDialog.Builder builder = new AlertDialog.Builder(Cart.this);
                        builder.setTitle("Options: ");

                        builder.setItems(charSequence, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                if(i == 0){
                                    Intent intent = new Intent(Cart.this, ItemDetails.class);
                                    intent.putExtra("itemID", cart_model.getItemID());
                                    startActivity(intent);
                                }
                                if(i == 1){
                                    Cart.child("Customer").child(Customer).child("Items").child(cart_model.getItemID()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if(task.isSuccessful()){
                                                Toast.makeText(Cart.this, "Item deleted successfully", Toast.LENGTH_SHORT).show();

                                                Intent intent = new Intent(Cart.this, CustomerMainActivity.class);
                                                startActivity(intent);
                                            }

                                        }
                                    });
                                }

                            }
                        });

                        builder.show();
                    }
                });
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