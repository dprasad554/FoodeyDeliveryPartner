package com.geekhive.foodeydeliveryboy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.geekhive.foodeydeliveryboy.R;
import com.geekhive.foodeydeliveryboy.beans.floatcash.FloatCash;
import com.geekhive.foodeydeliveryboy.beans.floatcash.FloatingCash;
import com.geekhive.foodeydeliveryboy.beans.wallet.WalletBal;
import com.geekhive.foodeydeliveryboy.utils.ConnectionDetector;
import com.geekhive.foodeydeliveryboy.utils.OnResponseListner;
import com.geekhive.foodeydeliveryboy.utils.Prefs;
import com.geekhive.foodeydeliveryboy.utils.SnackBar;
import com.geekhive.foodeydeliveryboy.utils.WebServices;

public class OrderCompletedActivity extends AppCompatActivity implements OnResponseListner {

    //SlideToActView sta;
    TextView tripEarning;//today_earning
    TextView wallet_earning;
    TextView float_earning;
    Button go_to_feed;
    String trip_earn = "";
    ConnectionDetector mDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_completed_activity);

        mDetector = new ConnectionDetector(this);
        trip_earn = getIntent().getStringExtra("trip_earn");
        go_to_feed = findViewById(R.id.go_to_feed);
        tripEarning = findViewById(R.id.tripEarning);
        //today_earning = findViewById(R.id.today_earning);
        wallet_earning = findViewById(R.id.wallet_earning);
        float_earning = findViewById(R.id.float_earning);

        String cs = "₹ " + trip_earn;
        tripEarning.setText(cs);

        go_to_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OrderCompletedActivity.this, MainActivity.class));
                OrderCompletedActivity.this.finish();
            }
        });

        CallWalletService();
    }

    private void CallWalletService() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).DeliveryWallet(WebServices.Foodey_Services,
                    WebServices.ApiType.wallet, Prefs.getUserId(this));
        }

    }

    private void CallFloatService() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).DeliveryFloat(WebServices.Foodey_Services,
                    WebServices.ApiType.floatcash, Prefs.getUserId(this));
        }

    }

    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSucces) {

        if (apiType == WebServices.ApiType.wallet) {
            if (!isSucces) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                WalletBal walletBal = (WalletBal) response;
                if (!isSucces || walletBal == null) {
                    SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    if (walletBal != null){
                        if (walletBal.getBalance() != null){
                            String wb = "₹ " + walletBal.getBalance().getDelWallet().getAmount();
                            wallet_earning.setText(wb);
                            CallFloatService();
                        }
                    } else {
                        SnackBar.makeText(this, walletBal.getMessage(), SnackBar.LENGTH_SHORT).show();
                    }
                }
            }
        } if (apiType == WebServices.ApiType.floatcash) {
            if (!isSucces) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                FloatingCash floatingCash = (FloatingCash) response;
                if (!isSucces || floatingCash == null) {
                    SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    if (floatingCash != null){
                        if (floatingCash.getBalance() != null){
                            String wb = "₹ " + floatingCash.getBalance().getFloatCash().getAmount();
                            float_earning.setText(wb);
                        }
                    } else {
                        SnackBar.makeText(this, floatingCash.getMessage(), SnackBar.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }
}
