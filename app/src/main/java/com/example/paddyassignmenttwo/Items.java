package com.example.paddyassignmenttwo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Items extends AppCompatActivity {

    private EditText mTitle, mManufacturer, mPrice, mCategory, mDescription;
    private ImageView mImage;
    private Button mAddItem;
    private DatabaseReference ItemRef;
    private String Category;
    private String CurrentItem;
    private static int PICK_IMAGE = 123;
    private StorageReference StorageReference;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        firebaseAuth = FirebaseAuth.getInstance();
        CurrentItem = firebaseAuth.getCurrentUser().getUid();
        ItemRef = FirebaseDatabase.getInstance().getReference();

        Category = getIntent().getExtras().get("category").toString();
        Toast.makeText(Items.this, Category, Toast.LENGTH_SHORT).show();
        StorageReference = FirebaseStorage.getInstance().getReference().child("Profile Images");

        mTitle = findViewById(R.id.itemTitle);
        mManufacturer = findViewById(R.id.itemManufacturer);
        mDescription = findViewById(R.id.itemDescription);
        mPrice = findViewById(R.id.itemPrice);
        mImage = findViewById(R.id.itemImage);
        mAddItem = findViewById(R.id.addItemButton);

        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profileIntent = new Intent();
                profileIntent.setType("image/*");
                profileIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(profileIntent, PICK_IMAGE);
            }
        });

        mAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String titles = mTitle.getText().toString();
                String manufacturer = mManufacturer.getText().toString();
                String descriptions = mDescription.getText().toString();
                String price = mPrice.getText().toString();

                DatabaseReference Items = ItemRef.child("Items").push();
                Map items = new HashMap();
                items.put("title", titles);
                items.put("description", descriptions);
                items.put("manufacturer", manufacturer);
                items.put("price", price);
                items.put("category", Category);



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

    //Set and save image to database
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {

            Uri image = data.getData();

            CropImage.activity(image).setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(1, 1).start(this);

        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {

                Uri resultUri = result.getUri();

                StorageReference r = StorageReference.child(CurrentItem + ".jpg");

                r.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        r.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String url = uri.toString();

                                ItemRef.child("profileimage").setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()) {

                                            Intent i = new Intent(Items.this, Items.class);
                                            startActivity(i);

                                            Toast.makeText(Items.this, "Profile image stored to firebase database successfully", Toast.LENGTH_SHORT).show();

                                        } else {


                                            Toast.makeText(Items.this, "Error", Toast.LENGTH_SHORT).show();

                                        }

                                    }
                                });
                            }
                        });
                    }


                });
            } else {

                Toast.makeText(Items.this, "Error image can not be cropped", Toast.LENGTH_SHORT).show();


            }

        }
    }
}


