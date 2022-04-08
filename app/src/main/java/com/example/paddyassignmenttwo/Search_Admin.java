package com.example.paddyassignmenttwo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Search_Admin extends AppCompatActivity {

    private DatabaseReference ItemsRef;
    private RecyclerView RecyclerView;
    private androidx.recyclerview.widget.RecyclerView.LayoutManager layoutManager;
    private final ArrayList<Items_Model> items_models  = new ArrayList<>();
    private Search_Adapter_Admin items_adapter;
    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_admin);

        ItemsRef = FirebaseDatabase.getInstance().getReference().child("Items");
        RecyclerView = findViewById(R.id.recyclerView);
        RecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        RecyclerView.setLayoutManager(layoutManager);
        mSearchView = findViewById(R.id.search_box_input);
        FetchItems();
    }

    private void FetchItems() {
        ItemsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //Loops through to retrieve goals
                for(DataSnapshot a : snapshot.getChildren()) {
                    Items_Model g = a.getValue(Items_Model.class);
                    items_models.add(g);
                }


                items_adapter = new Search_Adapter_Admin(Search_Admin.this, items_models);
                RecyclerView.setAdapter(items_adapter);
                items_adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Search_Admin.this, "Error", Toast.LENGTH_SHORT).show();
            }

        });

        if(mSearchView != null){
            mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    search(newText);
                    return false;
                }
            });
        }
    }

    private void search(String newText) {

        ArrayList<Items_Model> items_modelsFull = new ArrayList<>();

        for(Items_Model g : items_models){
            if(g.getTitle().toLowerCase().contains(newText.toLowerCase()) || g.getCategory().toLowerCase().contains(newText.toLowerCase())){
                items_modelsFull.add(g);
            }
        }
        items_adapter = new Search_Adapter_Admin(items_modelsFull);
        RecyclerView.setAdapter(items_adapter);
    }
}





