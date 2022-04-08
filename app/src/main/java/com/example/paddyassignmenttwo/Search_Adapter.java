package com.example.paddyassignmenttwo;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Search_Adapter extends RecyclerView.Adapter<Search_Adapter.ItemsViewHolder> implements Filterable {

    Context context;
    ArrayList<Items_Model> items_models;
    ArrayList<Items_Model> items_modelsFull;
    private OnItemClickListener mListener;

    public Search_Adapter (List<Items_Model> items_models)
    {
        this.items_models = (ArrayList<Items_Model>) items_models;
        items_modelsFull = new ArrayList<>(items_models);

    }

    public Search_Adapter(Context c, ArrayList<Items_Model> g){
        context = c;
        items_models = g;
    }

    @NonNull
    @Override
    public Search_Adapter.ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);

        return new Search_Adapter.ItemsViewHolder(V);
    }

    @Override
    public void onBindViewHolder(@NonNull Search_Adapter.ItemsViewHolder holder, int position) {

        Items_Model items_model = items_models.get(position);

        holder.mItemName.setText(items_model.getTitle());
        holder.mItemCategoy.setText(items_model.getCategory());
        holder.mItemDescription.setText(items_model.getDescription());
        holder.mItemPrice.setText(items_model.getPrice());
        holder.mItemManufactorer.setText(items_model.getManufacturer());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Items_Model goals_id = items_models.get(position);
              //  Items_Model goal_mentor = items_models.get(position);
                Intent intent = new Intent(context, ItemDetails.class);
                intent.putExtra("itemID", goals_id.getItemID());
               // intent.putExtra("menteeid", goal_mentor.getGoalsmentorid());
               context.startActivity(intent);
            }
        });

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

        TextView mItemName,mItemPrice,mItemCategoy, mItemDescription, mItemManufactorer;
        public ItemsViewHolder(@NonNull View itemView) {
            super(itemView);

            mItemName = itemView.findViewById(R.id.itemName);
            mItemPrice = itemView.findViewById(R.id.itemPrice);
            mItemCategoy = itemView.findViewById(R.id.itemCategory);
            mItemDescription = itemView.findViewById(R.id.itemDescription);
            mItemManufactorer = itemView.findViewById(R.id.itemManufacturer);
        }
    }



    @Override
    public int getItemCount() {
        return items_models.size();
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;

    }
}


