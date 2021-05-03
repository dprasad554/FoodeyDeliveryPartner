package com.geekhive.foodeydeliveryboy.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.geekhive.foodeydeliveryboy.R;
import com.geekhive.foodeydeliveryboy.beans.revenue.RevenueDetails;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.MyViewHolder> {
    public Typeface Montserrat_Regular;
    public Typeface Montserrat_SemiBold;
    Context context;
    RevenueDetails revenueDetails;
    //ProductCategoryList productCategoryList;

    public OrdersAdapter(Context context, RevenueDetails revenueDetails) {
        this.context = context;
        this.revenueDetails = revenueDetails;
        //this.productCategoryList = productCategoryList;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // set the data in items
        if (position == 0){
            holder.revenueHeading.setText("Today's Orders & Earnings");
            int totalReceived = revenueDetails.getRevenue().getTodaySumOrder();
            String total = String.valueOf(totalReceived);
            String todayComplete =  "₹ " +revenueDetails.getRevenue().getTodayComplete();
            String todayCancel =  "₹ " + revenueDetails.getRevenue().getTodayCancel();
            String sum = "₹ " + revenueDetails.getRevenue().getTodaySum();
            holder.orderReceived.setText(total);
            holder.orderDelivered.setText(todayComplete);
            holder.orderCanceled.setText(todayCancel);
            holder.grandTotal.setText(sum);
        } else if (position == 1){
            holder.revenueHeading.setText("Week's Orders & Earnings");
            int totalReceived = revenueDetails.getRevenue().getWeekSumOrder();
            String total = String.valueOf(totalReceived);
            String todayComplete =  "₹ " +revenueDetails.getRevenue().getWeekComplete();
            String todayCancel =  "₹ " + revenueDetails.getRevenue().getWeekCancel();
            String sum = "₹ " + revenueDetails.getRevenue().getWeekSum();
            holder.orderReceived.setText(total);
            holder.orderDelivered.setText(todayComplete);
            holder.orderCanceled.setText(todayCancel);
            holder.grandTotal.setText(sum);

        } else if (position == 2){
            holder.revenueHeading.setText("Months's Orders & Earnings");
            int totalReceived = revenueDetails.getRevenue().getMonthSumOrder();
            String total = String.valueOf(totalReceived);
            String todayComplete =  "₹ " +revenueDetails.getRevenue().getMonthComplete();
            String todayCancel =  "₹ " + revenueDetails.getRevenue().getMonthCancel();
            String sum = "₹ " + revenueDetails.getRevenue().getMonthSum();
            holder.orderReceived.setText(total);
            holder.orderDelivered.setText(todayComplete);
            holder.orderCanceled.setText(todayCancel);
            holder.grandTotal.setText(sum);
        } else if (position == 3){
            holder.revenueHeading.setText("Years's Orders & Earnings");
            int totalReceived = revenueDetails.getRevenue().getYearSumOrder();
            String total = String.valueOf(totalReceived);
            String todayComplete =  "₹ " +revenueDetails.getRevenue().getYearComplete();
            String todayCancel =  "₹ " + revenueDetails.getRevenue().getYearCancel();
            String sum = "₹ " + revenueDetails.getRevenue().getYearSum();
            holder.orderReceived.setText(total);
            holder.orderDelivered.setText(todayComplete);
            holder.orderCanceled.setText(todayCancel);
            holder.grandTotal.setText(sum);
        }

    }
    @Override
    public int getItemCount() {
        return 4;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        TextView orderReceived, orderDelivered, orderCanceled, grandTotal, revenueHeading;
        public MyViewHolder(View itemView) {
            super(itemView);
            orderReceived = itemView.findViewById(R.id.orderReceived);
            orderDelivered = itemView.findViewById(R.id.orderDelivered);
            orderCanceled = itemView.findViewById(R.id.orderCanceled);
            grandTotal = itemView.findViewById(R.id.grandTotal);
            revenueHeading = itemView.findViewById(R.id.revenueHeading);
        }
    }
}