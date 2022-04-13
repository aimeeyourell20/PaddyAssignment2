package com.example.paddyassignmenttwo.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.paddyassignmenttwo.Models.Items_Model;
import com.example.paddyassignmenttwo.Order_Details;
import com.example.paddyassignmenttwo.Models.Orders_Model;
import com.example.paddyassignmenttwo.R;

import java.util.ArrayList;
import java.util.List;

public class Orders_Adapter extends RecyclerView.Adapter<Orders_Adapter.OrdersViewHolder>{

    Context context;
    ArrayList<Orders_Model> orders_models;
    private Orders_Adapter.OnItemClickListener mListener;

    public Orders_Adapter (List<Items_Model> items_models)
    {
        this.orders_models = (ArrayList<Orders_Model>) orders_models;
       ;

    }

    public Orders_Adapter(Context c, ArrayList<Orders_Model> g){
        context = c;
        orders_models = g;
    }

    @NonNull
    @Override
    public Orders_Adapter.OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_layout, parent, false);

        return new Orders_Adapter.OrdersViewHolder(V);
    }

    @Override
    public void onBindViewHolder(@NonNull Orders_Adapter.OrdersViewHolder orders_modelViewHolder, int position) {

        Orders_Model orders_model = orders_models.get(position);

        orders_modelViewHolder.mName.setText("Name: " + orders_model.getName());
        orders_modelViewHolder.mEmail.setText("Email: " + orders_model.getEmail());
        orders_modelViewHolder.mAddress.setText("Address: " + orders_model.getAddress());
        orders_modelViewHolder.mDate.setText("Order Date: " + orders_model.getDate());
        orders_modelViewHolder.mTotal.setText("Total Price: €" + orders_model.getTotalAmount());

        orders_modelViewHolder.mOrderDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Orders_Model orders = orders_models.get(position);
                Orders_Model customer = orders_models.get(position);
                Intent intent = new Intent(context, Order_Details.class);
                intent.putExtra("orderID", orders.getOrderId());
                intent.putExtra("customerId", customer.getCustomerId());
                context.startActivity(intent);
            }
        });

    }

    class OrdersViewHolder extends RecyclerView.ViewHolder{

        TextView mName, mEmail, mAddress, mTotal, mDate;
        Button mOrderDetails;
        public OrdersViewHolder(@NonNull View itemView) {
            super(itemView);

            mName = itemView.findViewById(R.id.name);
            mEmail = itemView.findViewById(R.id.email);
            mAddress = itemView.findViewById(R.id.address);
            mTotal = itemView.findViewById(R.id.price);
            mDate = itemView.findViewById(R.id.date);
            mOrderDetails = itemView.findViewById(R.id.orderDetails);
        }
    }



    @Override
    public int getItemCount() {
        return orders_models.size();
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(Orders_Adapter.OnItemClickListener listener) {
        mListener = listener;

    }
}
