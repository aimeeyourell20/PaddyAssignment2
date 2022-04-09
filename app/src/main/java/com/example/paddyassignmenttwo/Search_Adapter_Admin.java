package com.example.paddyassignmenttwo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Search_Adapter_Admin extends RecyclerView.Adapter<Search_Adapter_Admin.ItemsViewHolder> implements Filterable {

        Context context;
        ArrayList<Items_Model> items_models;
        ArrayList<Items_Model> items_modelsFull;
        DatabaseReference Items = FirebaseDatabase.getInstance().getReference().child("Items");
private OnItemClickListener mListener;

public Search_Adapter_Admin (List<Items_Model> items_models)
        {
        this.items_models = (ArrayList<Items_Model>) items_models;
        items_modelsFull = new ArrayList<>(items_models);

        }

public Search_Adapter_Admin(Context c, ArrayList<Items_Model> g){
        context = c;
        items_models = g;
        }

@NonNull
@Override
public Search_Adapter_Admin.ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);

        return new Search_Adapter_Admin.ItemsViewHolder(V);
        }

@Override
public void onBindViewHolder(@NonNull Search_Adapter_Admin.ItemsViewHolder holder, int position) {

    Items_Model items_model = items_models.get(position);

    holder.mItemName.setText(items_model.getTitle());
    holder.mItemCategoy.setText(items_model.getCategory());
    holder.mItemDescription.setText(items_model.getDescription());
    holder.mItemPrice.setText(items_model.getPrice());
    holder.mItemManufactorer.setText(items_model.getManufacturer());

    holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            CharSequence charSequence[] = new CharSequence[]{

                    "Edit Item",
                    "Delete Item",
                    "View Item"

            };

            //Builder Pattern
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Options: ");

            builder.setItems(charSequence, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    if (i == 0) {
                        Items_Model items = items_models.get(position);
                        Intent intent = new Intent(context, Admin_Edit_Item.class);
                        intent.putExtra("itemID", items.getItemID());
                        context.startActivity(intent);
                    }
                    if (i == 1) {
                        Items_Model items = items_models.get(position);
                        Items.child(items.getItemID()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {
                                    Toast.makeText(context, "Item deleted successfully", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(context, AdminMainActivity.class);
                                    context.startActivity(intent);
                                }

                            }
                        });
                    }
                    if (i == 2) {
                        Items_Model goals_id = items_models.get(position);
                        //  Items_Model goal_mentor = items_models.get(position);
                        Intent intent = new Intent(context, Item_Details_Admin.class);
                        intent.putExtra("itemID", goals_id.getItemID());
                        // intent.putExtra("menteeid", goal_mentor.getGoalsmentorid());
                        context.startActivity(intent);
                    }

                }
            });

            builder.show();
        }
    });
}

    @Override
    public int getItemCount() {
        return items_models.size();
    }

    @Override
    public Filter getFilter() {
        return goalsFilter;
    }

    private final Filter goalsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Items_Model> filterList = new ArrayList<>();

            if(charSequence == null || charSequence.length() == 0){
                filterList.addAll(items_modelsFull);
            }else {
                String filter = charSequence.toString().toLowerCase().trim();

                for(Items_Model g : items_modelsFull){
                    if(g.getTitle().toLowerCase().contains(filter)){
                        filterList.add(g);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filterList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            items_models.clear();
            //goals_models.addAll((List)filterResults.values);
            notifyDataSetChanged();

        }
    };

    class ItemsViewHolder extends RecyclerView.ViewHolder{

        TextView mItemName,mItemPrice,mItemCategoy, mItemDescription, mItemManufactorer, mItemQuantity;
        public ItemsViewHolder(@NonNull View itemView) {
            super(itemView);

            mItemName = itemView.findViewById(R.id.itemName);
            mItemPrice = itemView.findViewById(R.id.itemPrice);
            mItemCategoy = itemView.findViewById(R.id.itemCategory);
            mItemDescription = itemView.findViewById(R.id.itemDescription);
            mItemManufactorer = itemView.findViewById(R.id.itemManufacturer);
        }
    }

public interface OnItemClickListener{
    void onItemClick(int position);
}

    public void setOnItemClickListener(Search_Adapter_Admin.OnItemClickListener listener) {
        mListener = listener;

    }
}


