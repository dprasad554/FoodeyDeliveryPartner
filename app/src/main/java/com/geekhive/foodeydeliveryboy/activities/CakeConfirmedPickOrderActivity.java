package com.geekhive.foodeydeliveryboy.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.geekhive.foodeydeliveryboy.R;
import com.ncorti.slidetoact.SlideToActView;

public class CakeConfirmedPickOrderActivity extends AppCompatActivity {

    //SlideToActView sta;
    SlideToActView picked_order;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_pic_order_activity);

        picked_order=findViewById(R.id.picked_order);
        picked_order.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(SlideToActView slideToActView) {
                Intent intent=new Intent(CakeConfirmedPickOrderActivity.this,ReachDropLocation.class);
                startActivity(intent);
                overridePendingTransition( R.anim.left_in, R.anim.left_out);
            }
        });
    }
}
