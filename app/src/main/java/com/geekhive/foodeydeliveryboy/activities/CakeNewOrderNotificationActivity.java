package com.geekhive.foodeydeliveryboy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.geekhive.foodeydeliveryboy.R;
import com.geekhive.foodeydeliveryboy.beans.newOrderList.NewOrderList;
import com.geekhive.foodeydeliveryboy.beans.orderConfirmed.OrderConfirmed;
import com.geekhive.foodeydeliveryboy.utils.ConnectionDetector;
import com.geekhive.foodeydeliveryboy.utils.OnResponseListner;
import com.geekhive.foodeydeliveryboy.utils.Prefs;
import com.geekhive.foodeydeliveryboy.utils.SnackBar;
import com.geekhive.foodeydeliveryboy.utils.WebServices;
import com.ncorti.slidetoact.SlideToActView;

public class CakeNewOrderNotificationActivity extends AppCompatActivity implements OnResponseListner {

    SlideToActView slide_to_accept;
    ConnectionDetector mDetector;
    NewOrderList orderListOut;
    String price_;
    String foodId;
    //RecyclerView orderItems;
    int position_;
    TextView storeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_order_notification);
        mDetector = new ConnectionDetector(this);
        position_ = getIntent().getIntExtra("position", 0);
        slide_to_accept =  findViewById(R.id.slide_to_accept);
        storeName = findViewById(R.id.storeName);
        slide_to_accept.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(SlideToActView slideToActView) {
                CallOrderService(Prefs.getUserId(CakeNewOrderNotificationActivity.this), orderListOut.getOrder().getCake().get(position_).getCakeCart().getId());
            }
        });

        CallService();

    }

    private void CallService() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).NewOrderList(WebServices.Foodey_Services,
                    WebServices.ApiType.newOrderList, Prefs.getUserId(this), Prefs.getCityId(this));
        }

    }

    private void CallOrderService(String del_id, String cart_id) {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).ConfirmCakeOrder(WebServices.Foodey_Services,
                    WebServices.ApiType.confirmOrder, del_id, cart_id);
        }

    }

    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSucces) {

        if (apiType == WebServices.ApiType.newOrderList) {
            if (!isSucces) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {

                orderListOut = (NewOrderList) response;
                if (!isSucces || orderListOut == null) {
                    SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    String str = orderListOut.getOrder().getCake().get(position_).getCakeStore().getName() + "\n" + orderListOut.getOrder().getCake().get(position_).getCakeStore().getAddress();
                    storeName.setText(str);
                }
            }
        } if (apiType == WebServices.ApiType.confirmOrder){
            OrderConfirmed orderConfirmation = (OrderConfirmed) response;
            if (!isSucces || orderConfirmation == null) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                /*viewOnMap.setVisibility(View.GONE);
                orderCancel.setVisibility(View.VISIBLE);*/
            } else {
                Intent intent=new Intent(CakeNewOrderNotificationActivity.this,CakeRestaurantReachActivity.class);
                intent.putExtra("position", position_);
                intent.putExtra("lat", orderListOut.getOrder().getCake().get(position_).getCakeStore().getLatitude());
                intent.putExtra("lang", orderListOut.getOrder().getCake().get(position_).getCakeStore().getLongitude());
                startActivity(intent);
                overridePendingTransition( R.anim.left_in, R.anim.left_out);
                CakeNewOrderNotificationActivity.this.finish();
            }
        }

    }
}