package com.example.paddyassignmenttwo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ItemDetails extends AppCompatActivity {

    private TextView mTitle, mManufacturer, mPrice, mCategory, mDescription;
    private FloatingActionButton mAddItemToCart;
    private ElegantNumberButton mQuantity;
    private ImageView mItemImage;
    private String ItemID = "";
    private DatabaseReference ItemRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        ItemRef = FirebaseDatabase.getInstance().getReference().child("Items");

        mTitle = findViewById(R.id.itemName);
        mManufacturer = findViewById(R.id.itemManufacturer);
        mDescription = findViewById(R.id.itemDescription);
        mPrice = findViewById(R.id.itemPrice);
        mCategory = findViewById(R.id.itemCategory);
        mItemImage = findViewById(R.id.itemImage);
        mAddItemToCart = findViewById(R.id.addToCartButton);
        mQuantity = findViewById(R.id.quantityCounter);

        ItemID = getIntent().getStringExtra("itemID");

        itemInformation(ItemID);
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