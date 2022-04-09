package com.example.paddyassignmenttwo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;

public class Admin_Edit_Item extends AppCompatActivity {

    private EditText mTitle, mManufacturer, mPrice, mCategory, mDescription, mQuantity;
    private ImageView mImage;
    private Button mAddItem;
    private DatabaseReference ItemRef;
    private String Category;
    private String itemId = "";
    private static int PICK_IMAGE = 123;
    private StorageReference StorageReference;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_item);


        StorageReference = FirebaseStorage.getInstance().getReference().child("Profile Images");


        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();

            if (extras != null) {
                itemId = (String) extras.get("itemID");
                //messageReceiverName = (String) extras.get("name");
            }
        }

        ItemRef = FirebaseDatabase.getInstance().getReference().child("Items").child(itemId);

        mTitle = findViewById(R.id.itemTitle);
        mManufacturer = findViewById(R.id.itemManufacturer);
        mDescription = findViewById(R.id.itemDescription);
        mPrice = findViewById(R.id.itemPrice);
        mImage = findViewById(R.id.itemImage);
        mAddItem = findViewById(R.id.addItemButton);
        mQuantity = findViewById(R.id.itemQuantity);


        ItemRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String description = snapshot.child("description").getValue().toString();
                    String manufacturer = snapshot.child("manufacturer").getValue().toString();
                    String price = snapshot.child("price").getValue().toString();
                    int quantity =snapshot.child("quantity").getValue(Integer.class);
                    String title = snapshot.child("title").getValue().toString();


                    mTitle.setText(title);
                    mManufacturer.setText(manufacturer);
                    mDescription.setText(description);
                    mPrice.setText(price);
                    mQuantity.setText(Integer.toString(quantity));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Validation();
            }
        });

    }

    private void Validation() {


        String title = mTitle.getText().toString();
        String manufacturer = mManufacturer.getText().toString();
        String description = mDescription.getText().toString();
        String price = mPrice.getText().toString();
        int quantity = Integer.parseInt(mQuantity.getText().toString());

        UpdateProduct(title,manufacturer, description, price, quantity);


    }

    private void UpdateProduct(String title, String manufacturer, String description, String price, int quantity) {
        int item = Integer.parseInt(mQuantity.getText().toString());
        int quantityTotal = quantity + item;

        HashMap<String, Object> itemMap = new HashMap<>();
        itemMap.put("title", title);
        itemMap.put("description", description);
        itemMap.put("manufacturer", manufacturer);
        itemMap.put("price", price);
        itemMap.put("quantity", quantityTotal);

        ItemRef.updateChildren(itemMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Toast.makeText(Admin_Edit_Item.this, "Product Updated", Toast.LENGTH_SHORT).show();
                    SendMenteeMainActivity();
                }else{
                    Toast.makeText(Admin_Edit_Item.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void SendMenteeMainActivity() {
        Intent i = new Intent(Admin_Edit_Item.this, AdminMainActivity.class);
        startActivity(i);
        finish();
    }

}







