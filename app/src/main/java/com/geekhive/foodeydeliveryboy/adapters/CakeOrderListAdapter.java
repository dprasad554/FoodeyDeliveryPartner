package com.geekhive.foodeydeliveryboy.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.geekhive.foodeydeliveryboy.R;
import com.geekhive.foodeydeliveryboy.activities.CakeNewOrderNotificationActivity;
import com.geekhive.foodeydeliveryboy.beans.cakeorderHistory.CakeOrderHistory;

public class CakeOrderListAdapter extends RecyclerView.Adapter<CakeOrderListAdapter.MyViewHolder> {
    public Typeface Montserrat_Regular;
    public Typeface Montserrat_SemiBold;
    Context context;
    CakeOrderHistory orderHistory;
    //ProductCategoryList productCategoryList;

    public CakeOrderListAdapter(Context context, CakeOrderHistory orderHistory) {
        this.context = context;
        this.orderHistory = orderHistory;
        //this.productCategoryList = productCategoryList;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cake_history_list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // set the data in items


        final String name = orderHistory.getOrderHistory().get(position).getUser().getFirstname() + " " + orderHistory.getOrderHistory().get(position).getUser().getLastname();
        holder.customerName.setText(name);
        holder.customerMob.setText(orderHistory.getOrderHistory().get(position).getUser().getMobile());
        holder.orderId.setText(orderHistory.getOrderHistory().get(position).getCakeCart().getOrderId());
        holder.orderTime.setText(orderHistory.getOrderHistory().get(position).getCakeCart().getDate());
        if (orderHistory.getOrderHistory().get(position).getCakeCart().getStatus().equals("3")) {
            holder.viewOrder.setText("On the way");
        } else if (orderHistory.getOrderHistory().get(position).getCakeCart().getStatus().equals("2")){
            holder.viewOrder.setText("Canceled");
        } else if (orderHistory.getOrderHistory().get(position).getCakeCart().getStatus().equals("4")){
            holder.viewOrder.setText("Delivered");
        }

        holder.viewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CakeNewOrderNotificationActivity.class);
                intent.putExtra("position", position);
                context.startActivity(intent);
            }
        });

        holder.productDetailsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CakeNewOrderNotificationActivity.class);
                intent.putExtra("position", position);
                context.startActivity(intent);
            }
        });

    }
    @Override
    public int getItemCount() {
        return orderHistory.getOrderHistory().size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        TextView customerName, customerMob, orderId, orderTime;
        Button viewOrder;
        CardView productDetailsCard;
        public MyViewHolder(View itemView) {
            super(itemView);
            viewOrder = itemView.findViewById(R.id.viewOrder);
            customerName = itemView.findViewById(R.id.customerName);
            customerMob = itemView.findViewById(R.id.customerMob);
            orderId = itemView.findViewById(R.id.orderId);
            productDetailsCard = itemView.findViewById(R.id.productDetailsCard);
            orderTime = itemView.findViewById(R.id.orderTime);
        }
    }
}