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

public class Customer_Settings extends AppCompatActivity {

    private EditText mAddress, mEmail, mCardNumber, mCardDate;
    private Spinner mPayment;
    private TextView mName, mType;
    private Button mUpdateCustomer;
    private DatabaseReference RootRef;
    private String currentUser;
    private ImageView mProfile;
    private static int PICK_IMAGE = 123;
    private StorageReference storageReference;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_settings);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser().getUid();
        RootRef = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser);
        storageReference = FirebaseStorage.getInstance().getReference().child("Profile Images");


        mName = findViewById(R.id.profileNameTxt);
        mEmail = findViewById(R.id.emailtxt);
        mType = findViewById(R.id.typeProfileTxt);
        mAddress = findViewById(R.id.address);
        mPayment = findViewById(R.id.paymentSpinner);
        mUpdateCustomer = findViewById(R.id.updateCustomer);
        mProfile = findViewById(R.id.profileImageProfile);
        mCardNumber = findViewById(R.id.cardNumber);
        mCardDate = findViewById(R.id.cardExpiryDate);

        mProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileIntent = new Intent();
                profileIntent.setType("image/*");
                profileIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(profileIntent, PICK_IMAGE);
            }
        });


        RootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String name = snapshot.child("name").getValue().toString();
                    String type = snapshot.child("type").getValue().toString();
                    String email = snapshot.child("email").getValue().toString();
                    String photo = snapshot.child("profileimage").getValue().toString();
                    Glide.with(getApplicationContext()).load(photo).into(mProfile);


                    mName.setText(name);
                    mEmail.setText(email);
                    mType.setText(type);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mUpdateCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Validation();
            }
        });

    }

    private void Validation() {

        String name = mName.getText().toString();
        String address = mAddress.getText().toString();
        String email = mEmail.getText().toString();
        String payment = mPayment.getSelectedItem().toString();
        String type = mType.getText().toString();
        String card = mCardNumber.getText().toString();
        String date = mCardDate.getText().toString();

        if(TextUtils.isEmpty(name)){
            mName.setError("Can not be left blank");
        }
        if(TextUtils.isEmpty(email)){
            mEmail.setError("Can not be left blank");
        }
        if(TextUtils.isEmpty(address)){
            mAddress.setError("Can not be left blank");
        }
        if(TextUtils.isEmpty(card)){
            mCardNumber.setError("Can not be left blank");
        }
        if(TextUtils.isEmpty(date)){
            mCardDate.setError("Can not be left blank");
        }
        else{
            UpdateMentee(name,type, email, address, payment, card, date);
        }


    }

    private void UpdateMentee(String name, String type, String email, String address, String payment, String card, String date) {
        HashMap<String, Object> MentorMap = new HashMap<>();
        MentorMap.put("name", name);
        MentorMap.put("type", type);
        MentorMap.put("address", address);
        MentorMap.put("email", email);
        MentorMap.put("payment", payment);
        MentorMap.put("card", card);
        MentorMap.put("cardExpiryDate", date);

        RootRef.updateChildren(MentorMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Toast.makeText(Customer_Settings.this, "Customer details updated successfully", Toast.LENGTH_SHORT).show();
                    SendMenteeMainActivity();
                }else{
                    Toast.makeText(Customer_Settings.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void SendMenteeMainActivity() {
        Intent i = new Intent(Customer_Settings.this, CustomerMainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==PICK_IMAGE && resultCode==RESULT_OK && data!=null){

            Uri image = data.getData();

            CropImage.activity(image).setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(1, 1).start(this);

        }

        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){

            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if(resultCode==RESULT_OK) {

                Uri resultUri = result.getUri();

                StorageReference r = storageReference.child(currentUser + ".jpg");

                r.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        r.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String url = uri.toString();

                                RootRef.child("profileimage").setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()) {

                                            Intent i = new Intent(Customer_Settings.this, Customer_Settings.class);
                                            startActivity(i);

                                            Toast.makeText(Customer_Settings.this, "Profile image stored to firebase database successfully", Toast.LENGTH_SHORT).show();

                                        } else {


                                            Toast.makeText(Customer_Settings.this, "Error", Toast.LENGTH_SHORT).show();

                                        }

                                    }
                                });
                            }
                        });
                    }


                });
            }
            else{

                Toast.makeText(Customer_Settings.this, "Error image can not be cropped", Toast.LENGTH_SHORT).show();


            }

        }

    }


}

