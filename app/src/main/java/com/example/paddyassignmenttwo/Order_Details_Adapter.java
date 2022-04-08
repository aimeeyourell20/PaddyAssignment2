package com.example.paddyassignmenttwo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Order_Details_Adapter extends RecyclerView.Adapter<Order_Details_Adapter.OrdersViewHolder>{

    Context context;
    ArrayList<Cart_Model> orders_models;
   // private Order_Details_Adapter.OnItemClickListener mListener;

    public Order_Details_Adapter (List<Items_Model> items_models)
    {
        this.orders_models = (ArrayList<Cart_Model>) orders_models;


    }

    public Order_Details_Adapter(Context c, ArrayList<Cart_Model> g){
        context = c;
        orders_models = g;
    }

    @NonNull
    @Override
    public Order_Details_Adapter.OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_layout, parent, false);

        return new Order_Details_Adapter.OrdersViewHolder(V);
    }

    @Override
    public void onBindViewHolder(@NonNull Order_Details_Adapter.OrdersViewHolder orders_modelViewHolder, int position) {

        Cart_Model orders_model = orders_models.get(position);

        orders_modelViewHolder.mName.setText(orders_model.getTitle());
        orders_modelViewHolder.mPrice.setText("Price: €" + orders_model.getPrice());
        orders_modelViewHolder.mQuantity.setText("Quantity: " + orders_model.getQuantity());

    }

    class OrdersViewHolder extends RecyclerView.ViewHolder{

        TextView mName, mQuantity, mPrice;
        public OrdersViewHolder(@NonNull View itemView) {
            super(itemView);

            mName = itemView.findViewById(R.id.itemName);
            mQuantity = itemView.findViewById(R.id.itemQuantity);
            mPrice = itemView.findViewById(R.id.itemPrice);

        }
    }



    @Override
    public int getItemCount() {
        return orders_models.size();
    }

}