package com.geekhive.foodeydeliveryboy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geekhive.foodeydeliveryboy.R;
import com.geekhive.foodeydeliveryboy.beans.orderHistory.OrderHistory;
import com.geekhive.foodeydeliveryboy.beans.orderPicked.OrderPicked;
import com.geekhive.foodeydeliveryboy.utils.ConnectionDetector;
import com.geekhive.foodeydeliveryboy.utils.OnResponseListner;
import com.geekhive.foodeydeliveryboy.utils.Prefs;
import com.geekhive.foodeydeliveryboy.utils.SnackBar;
import com.geekhive.foodeydeliveryboy.utils.WebServices;
import com.ncorti.slidetoact.SlideToActView;

public class PickOrderActivity extends AppCompatActivity implements OnResponseListner {

    //SlideToActView sta;
    ConnectionDetector mDetector;
    Button confirmed_order, viewOnMap;
    SlideToActView pickup;
    TextView orderId;
    RecyclerView orderItems;
    TextView conf;
    TextView customerName;
    TextView custAddress;
    OrderHistory orderListOut;
    double lat;
    double lang;
    int position_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pick_order_activity);
        mDetector = new ConnectionDetector(this);
        lat = Double.parseDouble(getIntent().getStringExtra("lat"));
        lang = Double.parseDouble(getIntent().getStringExtra("lang"));
        position_ = getIntent().getIntExtra("position", 0);
        orderId = findViewById(R.id.orderId);
        orderItems = findViewById(R.id.orderItems);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        orderItems.setLayoutManager(linearLayoutManager);
        conf = findViewById(R.id.conf);
        viewOnMap = findViewById(R.id.viewOnMap);
        pickup = findViewById(R.id.pickup);
        confirmed_order = findViewById(R.id.confirmed_order);
        customerName = findViewById(R.id.customerName);
        custAddress = findViewById(R.id.custAddress);

        confirmed_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmed_order.setBackgroundColor(getResources().getColor(R.color.white_nav));
                confirmed_order.setClickable(false);
                viewOnMap.setVisibility(View.GONE);
                pickup.setVisibility(View.VISIBLE);
            }
        });

        pickup.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(SlideToActView slideToActView) {
                CallPickedService(orderListOut.getOrderHistory().get(position_).getCart().getId());
            }
        });


        CallService();

    }

    private void CallService() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).OrderHistory(WebServices.Foodey_Services,
                    WebServices.ApiType.restaurantList, Prefs.getUserId(this));
        }

    }

    private void CallPickedService(String cart_id) {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).OrderPicked(WebServices.Foodey_Services,
                    WebServices.ApiType.orderpicked, cart_id);
        }

    }

    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSucces) {

        if (apiType == WebServices.ApiType.restaurantList) {
            if (!isSucces) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                orderListOut = (OrderHistory) response;
                if (!isSucces || orderListOut == null) {
                    SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    orderId.setText(orderListOut.getOrderHistory().get(position_).getCart().getOrderId());
                    String confTest ="Confirm " + orderListOut.getOrderHistory().get(position_).getCartDetail().size() + " items";
                    conf.setText(confTest);
                    String custName = orderListOut.getOrderHistory().get(position_).getUser().getFirstname() + " " +
                            orderListOut.getOrderHistory().get(position_).getUser().getLastname();
                    customerName.setText(custName);
                    String address = orderListOut.getOrderHistory().get(position_).getAddress().getHouse() + ", "
                            + orderListOut.getOrderHistory().get(position_).getAddress().getApartment() + ", "
                            + orderListOut.getOrderHistory().get(position_).getAddress().getStreet() + ", "
                            + orderListOut.getOrderHistory().get(position_).getAddress().getLandmark() + ", "
                            + orderListOut.getOrderHistory().get(position_).getAddress().getArea() + ", "
                            + orderListOut.getOrderHistory().get(position_).getAddress().getCity() + ", "
                            + orderListOut.getOrderHistory().get(position_).getAddress().getState() + ", "
                            + orderListOut.getOrderHistory().get(position_).getAddress().getCountry() + ", "
                            + orderListOut.getOrderHistory().get(position_).getAddress().getPincode();
                    custAddress.setText(address);
                    MenuAdapter menuAdapter = new MenuAdapter();
                    orderItems.setAdapter(menuAdapter);
                }
            }
        } if (apiType == WebServices.ApiType.orderpicked) {
            OrderPicked orderPicked = (OrderPicked) response;
            if (!isSucces || orderPicked == null) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
                Intent intent = new Intent(PickOrderActivity.this, ReachDropLocation.class);
                intent.putExtra("position", position_);
                intent.putExtra("lat", orderListOut.getOrderHistory().get(position_).getAddress().getLatitude() + "");
                intent.putExtra("lang", orderListOut.getOrderHistory().get(position_).getAddress().getLongitude() + "");
                startActivity(intent);
            }
        }
    }

    public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MyViewHolder> {

        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_menu_details, parent, false));
        }

        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            String items = orderListOut.getOrderHistory().get(position_).getCartDetail().get(position).getFood().getName() + " * " + orderListOut.getOrderHistory().get(position_).getCartDetail().get(position).getQuantity();
            holder.vT_admd_text.setText(items);
            /*String price = "₹ " + orderListOut.getOrderHistory().get(position_).getCartDetail().get(position).getFood().getPrice();
            holder.vT_price.setText(price);*/
        }

        public int getItemCount() {
            return orderListOut.getOrderHistory().get(position_).getCartDetail().size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            LinearLayout vL_admd_main, vL_admd_hide;
            ImageView vI_admd_image;
            TextView vT_admd_text, vT_price;
            ImageView qtyM, qtyA;
            TextView qty;
            Button buttonAdd;

            public MyViewHolder(View view) {
                super(view);
                this.vL_admd_main = (LinearLayout) view.findViewById(R.id.vL_admd_main);
                this.vL_admd_hide = (LinearLayout) view.findViewById(R.id.vL_admd_hide);
                this.vT_admd_text = (TextView) view.findViewById(R.id.vT_admd_text);
                vT_price = view.findViewById(R.id.vT_price);
            }
        }
    }
}
