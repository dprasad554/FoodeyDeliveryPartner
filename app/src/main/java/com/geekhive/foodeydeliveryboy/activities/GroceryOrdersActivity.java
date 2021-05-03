package com.geekhive.foodeydeliveryboy.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geekhive.foodeydeliveryboy.R;
import com.geekhive.foodeydeliveryboy.adapters.OrdersAdapter;
import com.geekhive.foodeydeliveryboy.beans.revenue.RevenueDetails;
import com.geekhive.foodeydeliveryboy.utils.ConnectionDetector;
import com.geekhive.foodeydeliveryboy.utils.OnResponseListner;
import com.geekhive.foodeydeliveryboy.utils.Prefs;
import com.geekhive.foodeydeliveryboy.utils.SnackBar;
import com.geekhive.foodeydeliveryboy.utils.WebServices;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GroceryOrdersActivity extends AppCompatActivity implements View.OnClickListener, OnResponseListner {

    @BindView(R.id.vI_aac_revenue_back)
    ImageView vI_aac_revenue_back;
    @BindView(R.id.revenueItems)
    RecyclerView revenueItems;

    ConnectionDetector mDetector;
    LinearLayoutManager linearLayoutManager;
    OrdersAdapter ordersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        ButterKnife.bind(this);

        setValues();
        CallService();

    }

    public void setValues(){
        mDetector = new ConnectionDetector(this);
        vI_aac_revenue_back.setOnClickListener(this);
    }

    private void CallService() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).RevenueList(WebServices.Foodey_Services,
                    WebServices.ApiType.revenuelist, Prefs.getUserId(this));
        }

    }

    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSucces) {
        if (apiType == WebServices.ApiType.revenuelist) {
            if (!isSucces) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                RevenueDetails revenueDetails = (RevenueDetails) response;
                if (revenueDetails != null){
                    ordersAdapter = new OrdersAdapter(this, revenueDetails);
                    linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                    revenueItems.setLayoutManager(linearLayoutManager);
                    revenueItems.setAdapter(ordersAdapter);
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.vI_aac_revenue_back:
                finish();
                break;
        }
    }
}
