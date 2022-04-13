package com.example.paddyassignmenttwo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.paddyassignmenttwo.Adapters.Search_Adapter;
import com.example.paddyassignmenttwo.Models.Items_Model;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Search extends AppCompatActivity {

    private DatabaseReference ItemsRef;
    private androidx.recyclerview.widget.RecyclerView RecyclerView;
    private androidx.recyclerview.widget.RecyclerView.LayoutManager layoutManager;
    private final ArrayList<Items_Model> items_models = new ArrayList<>();
    private Search_Adapter items_adapter;
    private SearchView mSearchView;
   private Button mSortCatA, mSortCatD, mSortManuA, mSortManuD, mSortTitleA, mSortTitleD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ItemsRef = FirebaseDatabase.getInstance().getReference().child("Items");
        RecyclerView = findViewById(R.id.recyclerView);
        RecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        RecyclerView.setLayoutManager(layoutManager);
        mSearchView = findViewById(R.id.search_box_input);
        mSortCatA = findViewById(R.id.catsortasc);
        mSortCatD = findViewById(R.id.catsortdesc);
        mSortManuA = findViewById(R.id.manusortasc);
        mSortManuD = findViewById(R.id.manusortdes);
        mSortTitleA = findViewById(R.id.titlesortasc);
        mSortTitleD = findViewById(R.id.titlesortdesc);

        FetchItems();

        mSortCatA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CategoryAscending();
            }
        });

        mSortManuA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ManufactoringAscending();
            }
        });

        mSortTitleA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TitleAscending();
            }
        });

        mSortCatD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CategoryDescending();
            }
        });

        mSortManuD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ManufactoringDescending();
            }
        });

        mSortTitleD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TitleDescending();
            }
        });
    }


    public void CategoryAscending() {

            Collections.sort(items_models, new Comparator<Items_Model>() {
                @Override
                public int compare(Items_Model o1, Items_Model o2) {
                    return o1.getCategory().compareToIgnoreCase(o2.getCategory());
                }
            });
            items_adapter.notifyDataSetChanged();

    }

    public void ManufactoringAscending() {

        Collections.sort(items_models, new Comparator<Items_Model>() {
            @Override
            public int compare(Items_Model o1, Items_Model o2) {
                return o1.getManufacturer().compareToIgnoreCase(o2.getManufacturer());
            }
        });
        items_adapter.notifyDataSetChanged();

    }

    public void TitleAscending() {

        Collections.sort(items_models, new Comparator<Items_Model>() {
            @Override
            public int compare(Items_Model o1, Items_Model o2) {
                return o1.getTitle().compareToIgnoreCase(o2.getTitle());
            }
        });
        items_adapter.notifyDataSetChanged();

    }

    public void CategoryDescending() {

        Collections.sort(items_models, new Comparator<Items_Model>() {
            @Override
            public int compare(Items_Model o1, Items_Model o2) {
                return o2.getCategory().compareToIgnoreCase(o1.getCategory());
            }
        });
        items_adapter.notifyDataSetChanged();

    }

    public void ManufactoringDescending() {

        Collections.sort(items_models, new Comparator<Items_Model>() {
            @Override
            public int compare(Items_Model o1, Items_Model o2) {
                return o2.getManufacturer().compareToIgnoreCase(o1.getManufacturer());
            }
        });
        items_adapter.notifyDataSetChanged();

    }

    public void TitleDescending() {

        Collections.sort(items_models, new Comparator<Items_Model>() {
            @Override
            public int compare(Items_Model o1, Items_Model o2) {
                return o2.getTitle().compareToIgnoreCase(o1.getTitle());
            }
        });
        items_adapter.notifyDataSetChanged();

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


                items_adapter = new Search_Adapter(Search.this, items_models);
                RecyclerView.setAdapter(items_adapter);
                items_adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Search.this, "Error", Toast.LENGTH_SHORT).show();
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
            if(g.getTitle().toLowerCase().contains(newText.toLowerCase()) || g.getManufacturer().toLowerCase().contains(newText.toLowerCase())){
                items_modelsFull.add(g);
            }
        }
        items_adapter = new Search_Adapter(items_modelsFull);
        RecyclerView.setAdapter(items_adapter);
    }

}




