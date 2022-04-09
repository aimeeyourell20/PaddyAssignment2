package com.example.paddyassignmenttwo;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CustomerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView mName, mEmail, mAddress, mPayment;
    private ItemClickListener itemClickListener;

    public CustomerViewHolder(@NonNull View itemView) {
        super(itemView);

        mName = itemView.findViewById(R.id.name);
        mEmail = itemView.findViewById(R.id.email);
        mAddress = itemView.findViewById(R.id.address);
        mPayment = itemView.findViewById(R.id.payment);
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), false);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
