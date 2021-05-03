package com.geekhive.foodeydeliveryboy.activities;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.geekhive.foodeydeliveryboy.R;
import com.geekhive.foodeydeliveryboy.beans.delstatus.DelWorkingStatus;
import com.geekhive.foodeydeliveryboy.beans.delupdate.UpdateDelStatus;
import com.geekhive.foodeydeliveryboy.fragments.HomeFragment;
import com.geekhive.foodeydeliveryboy.fragments.MainFragment;
import com.geekhive.foodeydeliveryboy.fragments.MainOrderFragment;
import com.geekhive.foodeydeliveryboy.fragments.OrderFragment;
import com.geekhive.foodeydeliveryboy.fragments.ProfileFragment;
import com.geekhive.foodeydeliveryboy.utils.ConnectionDetector;
import com.geekhive.foodeydeliveryboy.utils.OnResponseListner;
import com.geekhive.foodeydeliveryboy.utils.Prefs;
import com.geekhive.foodeydeliveryboy.utils.SnackBar;
import com.geekhive.foodeydeliveryboy.utils.WebServices;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements OnResponseListner {

    private Fragment viewFragment;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    Toolbar toolbar;
    NotificationManager notificationManager;
    SwitchCompat delStatus;
    ConnectionDetector mDetector;
    TextView delTxt;
    FrameLayout containerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Prefs.getUserId(this).equals("")) {
            startActivity(new Intent(this, LoginActivity.class));
            MainActivity.this.finish();
        }
        containerView = findViewById(R.id.containerView);
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView, new MainFragment()).commit();

        mDetector = new ConnectionDetector(this);
        delTxt = findViewById(R.id.delTxt);
        delStatus = findViewById(R.id.delStatus);

        delStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    CallUpdateService("1");
                } else {
                    CallUpdateService("0");
                }
            }
        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_home:
                                viewFragment = new MainFragment();
                                mFragmentTransaction = mFragmentManager.beginTransaction();
                                mFragmentTransaction.replace(R.id.containerView, viewFragment).commit();
                                break;
                            case R.id.action_orders:
                                viewFragment = new MainOrderFragment();
                                mFragmentTransaction = mFragmentManager.beginTransaction();
                                mFragmentTransaction.replace(R.id.containerView, viewFragment).commit();
                                break;
                            case R.id.action_profile:
                                viewFragment = new ProfileFragment();
                                mFragmentTransaction = mFragmentManager.beginTransaction();
                                mFragmentTransaction.replace(R.id.containerView, viewFragment).commit();
                                break;
                        }
                        return false;
                    }
                });

        CallService();
    }

    private void CallService() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).DeliveryStatus(WebServices.Foodey_Services,
                    WebServices.ApiType.delstatus, Prefs.getUserId(this));
        }

    }

    private void CallUpdateService(String status) {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).DeliveryUpdate(WebServices.Foodey_Services,
                    WebServices.ApiType.delupdate, Prefs.getUserId(this), status);
        }

    }

    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSucces) {
        if (apiType == WebServices.ApiType.delstatus) {
            if (!isSucces) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                DelWorkingStatus delWorkingStatus = (DelWorkingStatus) response;
                if (!isSucces || delWorkingStatus == null) {
                    SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    if (delWorkingStatus.getDeliveryBoy() != null){
                        if (!delWorkingStatus.getDeliveryBoy().getWorkingStatus().isEmpty()){
                            if (delWorkingStatus.getDeliveryBoy().getWorkingStatus().equals("1")){
                                delStatus.setChecked(true);
                                delTxt.setText("Online");
                                containerView.setVisibility(View.VISIBLE);

                            } else {
                                delStatus.setChecked(false);
                                delTxt.setText("Offline");
                                containerView.setVisibility(View.GONE);
                            }
                        }
                    }
                }
            }
        } else if (apiType == WebServices.ApiType.delupdate) {
            if (!isSucces) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                UpdateDelStatus updateDelStatus = (UpdateDelStatus) response;
                if (!isSucces || updateDelStatus == null) {
                    SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    if (updateDelStatus.getMessage() != null){
                        if (!updateDelStatus.getMessage().isEmpty()){
                            if (updateDelStatus.getMessage().equals("Working")){
                                CallService();
                            }else {
                                delStatus.setChecked(false);
                                delTxt.setText("Offline");
                                containerView.setVisibility(View.GONE);
                            }
                        }
                    }
                }
            }
        }
    }


    public BroadcastReceiver myReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            registerReceiver(myReceiver, new IntentFilter("FBR-IMAGE"));
            String action = intent.getAction();
            //endProgress();
            if (notificationManager != null)
                notificationManager.cancelAll(); //closes notification
            removeStickyBroadcast(intent);
            if (intent.getStringExtra("topic").equals("Resturant")) {
                pushNotificationFood(intent);
            } else if (intent.getStringExtra("topic").equals("Grocery")) {
                pushNotificationGrocery(intent);
            } else if (intent.getStringExtra("topic").equals("Cake")) {
                pushNotificationCake(intent);
            }

            //changeUi(action);
        }
    };


    private void pushNotificationFood(Intent intent) {

        try {

            String message = intent.getExtras().getString("ms");
            if (!message.equals("")) {
                Intent intenti = new Intent(this, NewOrderAlertActivity.class);
                intenti.putExtra("position", 0);
                startActivity(intenti);

            } else {

                Log.e("Message", "No Message To Display");

            }


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private void pushNotificationCake(Intent intent) {

        try {

            String message = intent.getExtras().getString("ms");
            if (!message.equals("")) {
                Intent intenti = new Intent(this, CakeNewOrderAlert.class);
                intenti.putExtra("position", 0);
                startActivity(intenti);

            } else {

                Log.e("Message", "No Message To Display");

            }


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private void pushNotificationGrocery(Intent intent) {

        try {

            String message = intent.getExtras().getString("ms");
            if (!message.equals("")) {
                Intent intenti = new Intent(this, GroceryNewOrderAlert.class);
                intenti.putExtra("position", 0);
                startActivity(intenti);

            } else {

                Log.e("Message", "No Message To Display");

            }


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //registerReceiver(broadcastReceiver, new IntentFilter(GoogleService.str_receiver));

        try {
            registerReceiver(myReceiver, new IntentFilter("FBR-IMAGE"));
        } catch (Exception e) {
            // already registered
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        //unregisterReceiver(broadcastReceiver);

        try {
            unregisterReceiver(myReceiver);
        } catch (Exception e) {
            // already unregistered
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//        if(passHandler!=null)
//            passHandler.removeCallbacksAndMessages(null);
        //stop location updates when Activity is no longer active
//        if (mGoogleApiClient != null) {
//            if(mGoogleApiClient.isConnected())
//                LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
//        }
    }
}
