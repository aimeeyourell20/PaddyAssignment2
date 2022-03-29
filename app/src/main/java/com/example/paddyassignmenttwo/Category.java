package com.example.paddyassignmenttwo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Category extends AppCompatActivity {

    private ImageView mTshirt, mJeans, mCoat, mShoes, mSocks, mHat, mDress, mJumper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        mTshirt = findViewById(R.id.tshirt);
        mJeans = findViewById(R.id.jeans);
        mCoat = findViewById(R.id.coat);
        mShoes = findViewById(R.id.shoes);
        mHat = findViewById(R.id.hat);
        mSocks = findViewById(R.id.socks);
        mDress = findViewById(R.id.dress);
        mJumper = findViewById(R.id.jumper);

        mTshirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Category.this, Items.class);
                i.putExtra("category", "tShirt");
                startActivity(i);

            }
        });

        mJeans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Category.this, Items.class);
                i.putExtra("category", "Jeans");
                startActivity(i);
            }
        });

        mCoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Category.this, Items.class);
                i.putExtra("category", "Coat");
                startActivity(i);
            }
        });

        mShoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Category.this, Items.class);
                i.putExtra("category", "Shoes");
                startActivity(i);
            }
        });

        mHat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Category.this, Items.class);
                i.putExtra("category", "Hat");
                startActivity(i);
            }
        });

        mSocks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Category.this, Items.class);
                i.putExtra("category", "Socks");
                startActivity(i);
            }
        });

        mDress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Category.this, Items.class);
                i.putExtra("category", "Dress");
                startActivity(i);
            }
        });

        mJumper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Category.this, Items.class);
                i.putExtra("category", "Jumper");
                startActivity(i);
            }
        });


    }
}