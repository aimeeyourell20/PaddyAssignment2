package com.example.paddyassignmenttwo.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.paddyassignmenttwo.Models.Cart_Model;
import com.example.paddyassignmenttwo.Models.Items_Model;
import com.example.paddyassignmenttwo.Product_Review_Activity;
import com.example.paddyassignmenttwo.R;
import com.example.paddyassignmenttwo.Rate_Product_Activity;

import java.util.ArrayList;
import java.util.List;

public class Order_Details_Adapter_Customer extends RecyclerView.Adapter<Order_Details_Adapter_Customer.OrdersViewHolder>{

    Context context;
    ArrayList<Cart_Model> orders_models;
    private Order_Details_Adapter_Customer.OnItemClickListener mListener;

    public Order_Details_Adapter_Customer(List<Items_Model> items_models)
    {
        this.orders_models = (ArrayList<Cart_Model>) orders_models;


    }

    public Order_Details_Adapter_Customer(Context c, ArrayList<Cart_Model> g){
        context = c;
        orders_models = g;
    }

    @NonNull
    @Override
    public Order_Details_Adapter_Customer.OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_layout, parent, false);

        return new Order_Details_Adapter_Customer.OrdersViewHolder(V);
    }

    @Override
    public void onBindViewHolder(@NonNull Order_Details_Adapter_Customer.OrdersViewHolder orders_modelViewHolder, int position) {

        Cart_Model orders_model = orders_models.get(position);

        orders_modelViewHolder.mName.setText(orders_model.getTitle());
        orders_modelViewHolder.mPrice.setText("Price: €" + orders_model.getPrice());
        orders_modelViewHolder.mQuantity.setText("Quantity: " + orders_model.getQuantity());

        orders_modelViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CharSequence charSequence[] = new CharSequence[]{

                        "Rate Product",
                        "Write Review"

                };

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Options");

                builder.setItems(charSequence, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if(i == 0){
                            Cart_Model orders = orders_models.get(position);
                            Cart_Model customer = orders_models.get(position);
                            Intent intent = new Intent(context, Rate_Product_Activity.class);
                            intent.putExtra("itemId", orders.getItemID());
                            intent.putExtra("customerId", customer.getCustomerId());
                            context.startActivity(intent);
                        }
                        if(i == 1){
                            Cart_Model orders = orders_models.get(position);
                            Cart_Model customer = orders_models.get(position);
                            Intent intent = new Intent(context, Product_Review_Activity.class);
                            intent.putExtra("itemId", orders.getItemID());
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

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(Order_Details_Adapter_Customer.OnItemClickListener listener) {
        mListener = listener;

    }

}