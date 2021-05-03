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
import com.geekhive.foodeydeliveryboy.activities.NewOrderNotificationActivity;
import com.geekhive.foodeydeliveryboy.beans.newOrderList.NewOrderList;

public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.MyViewHolder> {
    public Typeface Montserrat_Regular;
    public Typeface Montserrat_SemiBold;
    Context context;
    NewOrderList newOrderList;
    //ProductCategoryList productCategoryList;

    public HomeListAdapter(Context context, NewOrderList newOrderList) {
        this.context = context;
        this.newOrderList = newOrderList;
        //this.productCategoryList = productCategoryList;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // set the data in items

        holder.restName.setText(newOrderList.getOrder().getResturant().get(position).getResturant().getName());
        String date = newOrderList.getOrder().getResturant().get(position).getResturant().getDate();
        holder.orderTime.setText(date);

        holder.cardRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewOrderNotificationActivity.class);
                intent.putExtra("position", position);
                context.startActivity(intent);
            }
        });

        holder.viewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewOrderNotificationActivity.class);
                intent.putExtra("position", position);
                context.startActivity(intent);
            }
        });

    }
    @Override
    public int getItemCount() {
        return newOrderList.getOrder().getResturant().size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        TextView restName,orderTime;
        Button viewOrder;
        CardView cardRequest;
        public MyViewHolder(View itemView) {
            super(itemView);
            viewOrder = itemView.findViewById(R.id.viewOrder);
            cardRequest = itemView.findViewById(R.id.cardRequest);
            restName = itemView.findViewById(R.id.restName);
            orderTime = itemView.findViewById(R.id.orderTime);

        }
    }
}