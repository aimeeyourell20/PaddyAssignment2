package com.example.paddyassignmenttwo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class Orders_Adapter_Customer extends RecyclerView.Adapter<Orders_Adapter_Customer.OrdersViewHolder>{

    Context context;
    ArrayList<Orders_Model> orders_models;
    private Orders_Adapter_Customer.OnItemClickListener mListener;

    public Orders_Adapter_Customer(List<Items_Model> items_models)
    {
        this.orders_models = (ArrayList<Orders_Model>) orders_models;
       ;

    }

    public Orders_Adapter_Customer(Context c, ArrayList<Orders_Model> g){
        context = c;
        orders_models = g;
    }

    @NonNull
    @Override
    public Orders_Adapter_Customer.OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_layout_customer, parent, false);

        return new Orders_Adapter_Customer.OrdersViewHolder(V);
    }

    @Override
    public void onBindViewHolder(@NonNull Orders_Adapter_Customer.OrdersViewHolder orders_modelViewHolder, int position) {

        Orders_Model orders_model = orders_models.get(position);

        orders_modelViewHolder.mName.setText("Name: " + orders_model.getName());
        orders_modelViewHolder.mEmail.setText("Email: " + orders_model.getEmail());
        orders_modelViewHolder.mAddress.setText("Address: " + orders_model.getAddress());
        orders_modelViewHolder.mDate.setText("Order Date: " + orders_model.getDate());
        orders_modelViewHolder.mTotal.setText("Total Price: €" + orders_model.getTotalAmount());

        orders_modelViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CharSequence charSequence[] = new CharSequence[]{

                        "Product Details"

                };

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Product Options");

                builder.setItems(charSequence, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if(i == 0){
                            Orders_Model orders = orders_models.get(position);
                            Orders_Model customer = orders_models.get(position);
                            Intent intent = new Intent(context, Order_Details_Customer.class);
                            intent.putExtra("orderID", orders.getOrderId());
                            intent.putExtra("customerId", customer.getCustomerId());
                            context.startActivity(intent);
                        }
                        if(i == 1){
                            Orders_Model orders = orders_models.get(position);
                            Orders_Model customer = orders_models.get(position);
                            Intent intent = new Intent(context, Rate_Product_Activity.class);
                            intent.putExtra("orderID", orders.getOrderId());
                            intent.putExtra("customerId", customer.getCustomerId());
                            context.startActivity(intent);
                        }

                    }
                });

                builder.show();
            }
        });

    }


    class OrdersViewHolder extends RecyclerView.ViewHolder{

        TextView mName, mEmail, mAddress, mTotal, mDate;
        public OrdersViewHolder(@NonNull View itemView) {
            super(itemView);

            mName = itemView.findViewById(R.id.name);
            mEmail = itemView.findViewById(R.id.email);
            mAddress = itemView.findViewById(R.id.address);
            mTotal = itemView.findViewById(R.id.price);
            mDate = itemView.findViewById(R.id.date);
        }
    }



    @Override
    public int getItemCount() {
        return orders_models.size();
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(Orders_Adapter_Customer.OnItemClickListener listener) {
        mListener = listener;

    }
}
