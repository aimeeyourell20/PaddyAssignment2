package com.example.paddyassignmenttwo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.paddyassignmenttwo.Models.Items_Model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ItemDetails extends AppCompatActivity {

    private TextView mTitle, mManufacturer, mPrice, mCategory, mDescription;
    private Button mAddItemToCart;
    private ElegantNumberButton mQuantity;
    private ImageView mItemImage;
    private String ItemID = "";
    private DatabaseReference ItemRef;
    private FirebaseAuth mAuth;
    private String Customer;
    private final ArrayList<Items_Model> items_models  = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        ItemRef = FirebaseDatabase.getInstance().getReference().child("Items");
        mAuth = FirebaseAuth.getInstance();
        Customer = mAuth.getCurrentUser().getUid();



        mTitle = findViewById(R.id.itemName);
        mManufacturer = findViewById(R.id.itemManufacturer);
        mDescription = findViewById(R.id.itemDescription);
        mPrice = findViewById(R.id.itemPrice);
        mCategory = findViewById(R.id.itemCategory);
        mItemImage = findViewById(R.id.itemImage);
        mAddItemToCart = findViewById(R.id.addToCart);
        mQuantity = findViewById(R.id.quantityCounter);

        ItemID = getIntent().getStringExtra("itemID");

        itemInformation(ItemID);

        mAddItemToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCart();
            }
        });
    }

    private void addToCart() {

        ItemRef.child(ItemID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int quantity =snapshot.child("quantity").getValue(Integer.class);
                if(quantity <= 0){
                    Toast.makeText(ItemDetails.this, "Item not available", Toast.LENGTH_SHORT).show();
                }else{

                    DatabaseReference Cart = FirebaseDatabase.getInstance().getReference().child("Cart");
                    DatabaseReference Items = FirebaseDatabase.getInstance().getReference().child("Items");

                    HashMap<String, Object> cartMap = new HashMap<>();
                    cartMap.put("itemID", ItemID);
                    cartMap.put("title", mTitle.getText().toString());
                    cartMap.put("price", mPrice.getText().toString());
                    cartMap.put("category", mCategory.getText().toString());
                    cartMap.put("quantity", mQuantity.getNumber());
                    cartMap.put("discount", "");
                    cartMap.put("customerId", Customer);

                    //if(mQuantity.getNumber() > Items.items_models.ge)

                    Cart.child("Customer").child(Customer).child("Items").child(ItemID).updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Cart.child("Admin").child(Customer).child("Items").child(ItemID).updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if(task.isSuccessful()){
                                            Toast.makeText(ItemDetails.this, "Item added to cart", Toast.LENGTH_SHORT).show();

                                            Items.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                    for(DataSnapshot a :snapshot.getChildren()){
                                                        int quantity = a.child("quantity").getValue(Integer.class);

                                                        int total = quantity;
                                                        int item = Integer.parseInt(mQuantity.getNumber());
                                                        int quantityTotal = total - item;

                                                        HashMap<String, Object> map = new HashMap<>();
                                                        map.put("quantity", quantityTotal);
                                                        Items.child(ItemID).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                Intent i = new Intent(ItemDetails.this, CustomerMainActivity.class);
                                                                startActivity(i);
                                                            }
                                                        });
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });
                                        }

                                    }
                                });
                            }
                        }
                    });



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void itemInformation(String itemID) {

        ItemRef.child(itemID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()) {
                    Items_Model items_model = snapshot.getValue(Items_Model.class);
                    mTitle.setText(items_model.getTitle());
                    mManufacturer.setText(items_model.getManufacturer());
                    mDescription.setText(items_model.getDescription());
                    mPrice.setText(items_model.getPrice());
                    mCategory.setText(items_model.getCategory());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}