package com.example.paddyassignmenttwo;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView mItemName, mItemCategory, mItemPrice, mItemDescription, mManufacturer;
    public ItemClickListener itemClickListener;
    public ItemViewHolder(View itemView) {
        super(itemView);

        mItemName = itemView.findViewById(R.id.itemName);
        mItemCategory = itemView.findViewById(R.id.itemCategory);
        mItemPrice = itemView.findViewById(R.id.itemPrice);
        mItemDescription = itemView.findViewById(R.id.itemDescription);
        mManufacturer = itemView.findViewById(R.id.itemManufacturer);
    }

    public void setItemClickListener(ItemClickListener listener){
        this.itemClickListener = listener;
    }


    @Override
    public void onClick(View view) {

        itemClickListener.onClick(view, getAdapterPosition(), false);
    }
}
