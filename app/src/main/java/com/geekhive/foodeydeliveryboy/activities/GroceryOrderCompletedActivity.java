package com.geekhive.foodeydeliveryboy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.geekhive.foodeydeliveryboy.R;

public class GroceryOrderCompletedActivity extends AppCompatActivity {

    //SlideToActView sta;
    TextView tripEarning;
    Button go_to_feed;
    String trip_earn = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_completed_activity);

        trip_earn = getIntent().getStringExtra("trip_earn");
        go_to_feed = findViewById(R.id.go_to_feed);
        tripEarning = findViewById(R.id.tripEarning);
        //today_earning = findViewById(R.id.today_earning);

        String cs = "₹ " + trip_earn;
        tripEarning.setText(cs);

        go_to_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GroceryOrderCompletedActivity.this, MainActivity.class));
                GroceryOrderCompletedActivity.this.finish();
            }
        });

    }
}
