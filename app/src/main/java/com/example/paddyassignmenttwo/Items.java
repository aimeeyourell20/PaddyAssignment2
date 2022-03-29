package com.example.paddyassignmenttwo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Items extends AppCompatActivity {

    private EditText mTitle, mManufacturer, mPrice, mCategory, mDescription;
    private ImageView mImage;
    private Button mAddItem;
    private DatabaseReference ItemRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        ItemRef = FirebaseDatabase.getInstance().getReference();

        mTitle = findViewById(R.id.itemTitle);
        mManufacturer = findViewById(R.id.itemManufacturer);
        mDescription = findViewById(R.id.itemDescription);
        mPrice = findViewById(R.id.itemPrice);
        mCategory = findViewById(R.id.itemCategory);
        //mImage = findViewById(R.id.itemImage);
        mAddItem = findViewById(R.id.addItemButton);

        mAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String titles = mTitle.getText().toString();
                String manufacturer = mManufacturer.getText().toString();
                String descriptions = mDescription.getText().toString();
                String price = mPrice.getText().toString();
                String category = mCategory.getText().toString();
               // String id = RootRef.push().getKey();

                DatabaseReference Items = ItemRef.child("Items").push();
                Map items = new HashMap();
                items.put("title", titles);
                items.put("description", descriptions);
                items.put("manufacturer", manufacturer);
                items.put("price", price);
                items.put("category", category);



                Items.updateChildren(items).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(Items.this, "Items info successfully added", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(Items.this, AdminMainActivity.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(Items.this, "Error", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }


        });

    }
}


