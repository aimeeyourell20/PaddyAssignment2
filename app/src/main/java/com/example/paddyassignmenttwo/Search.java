package com.example.paddyassignmenttwo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Search extends AppCompatActivity {

    private DatabaseReference ItemsRef;
    private androidx.recyclerview.widget.RecyclerView RecyclerView;
    private androidx.recyclerview.widget.RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ItemsRef = FirebaseDatabase.getInstance().getReference().child("Items");
        RecyclerView = findViewById(R.id.recyclerView);
        RecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        RecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Items_Model> firebaseRecyclerOptions = new FirebaseRecyclerOptions.Builder<Items_Model>()
                .setQuery(ItemsRef, Items_Model.class)
                .build();

        FirebaseRecyclerAdapter<Items_Model, ItemViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Items_Model, ItemViewHolder>(firebaseRecyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i, @NonNull Items_Model items_model) {
                itemViewHolder.mItemName.setText(items_model.getTitle());
                itemViewHolder.mItemCategory.setText(items_model.getCategory());
                itemViewHolder.mItemDescription.setText(items_model.getDescription());
                itemViewHolder.mItemPrice.setText(items_model.getPrice());
                itemViewHolder.mManufacturer.setText(items_model.getManufacturer());

                itemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(Search.this, ItemDetails.class);
                        i.putExtra("itemID", items_model.getItemID());
                        startActivity(i);
                    }
                });


            }

            @NonNull
            @Override
            public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
                ItemViewHolder itemViewHolder = new ItemViewHolder(v);
                return itemViewHolder;

            }
        };

        RecyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();

    }
}