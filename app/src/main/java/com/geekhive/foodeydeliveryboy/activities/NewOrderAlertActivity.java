package com.geekhive.foodeydeliveryboy.activities;

import android.content.ContentResolver;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
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

public class NewOrderAlertActivity extends AppCompatActivity implements OnResponseListner {

    SlideToActView slide_to_accept;
    ConnectionDetector mDetector;
    NewOrderList orderListOut;
    String price_;
    String foodId;
    //RecyclerView orderItems;
    int position_;
    TextView storeName;

    private MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_order_alert);
        Uri soundUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getApplicationContext().getPackageName() + "/" + R.raw.howl);

        player = MediaPlayer.create(this,
                soundUri);
        //setting loop play to true
        //this will make the ringtone continuously playing
        player.setLooping(true);

        //staring the player
        player.start();
        /*String message = getIntent().getExtras().getString("ms");
        if (message.contains("resturant confirm this order and preparing food")) {
            player.setLooping(false);
            player.stop();
        } else {
            player.setLooping(true);
            player.start();
        }*/


        mDetector = new ConnectionDetector(this);
        position_ = getIntent().getIntExtra("position", 0);
        slide_to_accept =  findViewById(R.id.slide_to_accept);
        storeName = findViewById(R.id.storeName);
        slide_to_accept.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(SlideToActView slideToActView) {
                if (player.isLooping()){
                    player.setLooping(false);
                    player.stop();
                }

                CallOrderService(Prefs.getUserId(NewOrderAlertActivity.this), orderListOut.getOrder().getResturant().get(position_).getCart().getId());
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
            new WebServices(this).ConfirmOrder(WebServices.Foodey_Services,
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
                    String str = orderListOut.getOrder().getResturant().get(position_).getResturant().getName() + "\n" + orderListOut.getOrder().getResturant().get(position_).getResturant().getAddress();
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
                Intent intent=new Intent(NewOrderAlertActivity.this,RestaurantReachActivity.class);
                intent.putExtra("position", position_);
                intent.putExtra("lat", orderListOut.getOrder().getResturant().get(position_).getResturant().getLat());
                intent.putExtra("lang", orderListOut.getOrder().getResturant().get(position_).getResturant().getLong());
                startActivity(intent);
                overridePendingTransition( R.anim.left_in, R.anim.left_out);
                NewOrderAlertActivity.this.finish();
            }
        }

    }
}
