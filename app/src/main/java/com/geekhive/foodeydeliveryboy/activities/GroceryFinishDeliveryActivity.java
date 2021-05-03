package com.geekhive.foodeydeliveryboy.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.geekhive.foodeydeliveryboy.R;
import com.geekhive.foodeydeliveryboy.beans.alreadypaid.AlreadyPaid;
import com.geekhive.foodeydeliveryboy.beans.cashpay.CashPayment;
import com.geekhive.foodeydeliveryboy.beans.groceryorderHistory.GrocOrderHistory;
import com.geekhive.foodeydeliveryboy.beans.onlinepay.OnlinePayment;
import com.geekhive.foodeydeliveryboy.beans.orderDelivered.OrderDelivered;
import com.geekhive.foodeydeliveryboy.utils.ConnectionDetector;
import com.geekhive.foodeydeliveryboy.utils.OnResponseListner;
import com.geekhive.foodeydeliveryboy.utils.Prefs;
import com.geekhive.foodeydeliveryboy.utils.SnackBar;
import com.geekhive.foodeydeliveryboy.utils.WebServices;
import com.hsalf.smilerating.SmileRating;
import com.ncorti.slidetoact.SlideToActView;

public class GroceryFinishDeliveryActivity extends AppCompatActivity implements OnResponseListner {

    //SlideToActView sta;
    ConnectionDetector mDetector;
    Button vB_total_cash_amount, vB_finish_do;
    TextView vT_cash_amount, vT_cash_visible;
    Dialog RatePopup;
    SlideToActView vB_cash_collected;
    int count = 0;
    double lat;
    double lang;
    int position_;
    double latitude, longitude;
    GrocOrderHistory orderListOut;
    Dialog paypopup;
    TextView addressUser, itemUsr;
    TextView customerName, orderId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finish_deliver_order_activity);
        mDetector = new ConnectionDetector(this);

        latitude = Double.parseDouble(getIntent().getStringExtra("lat"));
        longitude = Double.parseDouble(getIntent().getStringExtra("lang"));
        position_ = getIntent().getIntExtra("position", 0);
        addressUser = findViewById(R.id.addressUser);
        itemUsr = findViewById(R.id.itemUsr);
        customerName = findViewById(R.id.customerName);
        orderId = findViewById(R.id.orderId);
        vB_cash_collected = findViewById(R.id.vB_cash_collected);
        vB_total_cash_amount = findViewById(R.id.vB_total_cash_amount);

        vT_cash_amount = findViewById(R.id.vT_cash_amount);
        vB_finish_do = findViewById(R.id.vB_finish_do);
        vT_cash_visible = findViewById(R.id.vT_cash_visible);

        vB_total_cash_amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vT_cash_amount.setVisibility(View.GONE);
                vT_cash_visible.setVisibility(View.VISIBLE);
                vB_finish_do.setVisibility(View.GONE);
                vB_cash_collected.setVisibility(View.VISIBLE);
                vB_total_cash_amount.setVisibility(View.GONE);
                InitializeOnlinePopup();
                initializeOnlinePopup();
            }
        });


        vB_cash_collected.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(SlideToActView slideToActView) {
                if (orderListOut.getOrderHistory().get(position_).getGCart().getPaymentStatus().equals("1")){
                    CallAlreadyService();
                } else {
                    CallDeliverService();
                }
                /*InitializeRatepopup();
                initializeRatePopup();*/

            }
        });

        CallService();

    }


    private void CallService() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).GrocOrderHistory(WebServices.Foodey_Services,
                    WebServices.ApiType.restaurantList, Prefs.getUserId(this));
        }

    }

    private void CallCashService() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).CashGrocPay(WebServices.Foodey_Services,
                    WebServices.ApiType.cpay, orderListOut.getOrderHistory().get(position_).getGCart().getId(), Prefs.getUserId(this));
        }

    }

    private void CallOnlineService() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).OnlineGrocPay(WebServices.Foodey_Services,
                    WebServices.ApiType.opay, orderListOut.getOrderHistory().get(position_).getGCart().getId(), Prefs.getUserId(this));
        }

    }

    private void CallAlreadyService() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).AlreadyGrocPay(WebServices.Foodey_Services,
                    WebServices.ApiType.apay, orderListOut.getOrderHistory().get(position_).getGCart().getId(), Prefs.getUserId(this));
        }

    }

    private void CallDeliverService() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).DeliveredGrocOrder(WebServices.Foodey_Services,
                    WebServices.ApiType.orderDelivered, Prefs.getUserId(this), orderListOut.getOrderHistory().get(position_).getGCart().getId());
        }

    }

    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSucces) {
        if (apiType == WebServices.ApiType.restaurantList) {
            if (!isSucces) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                orderListOut = (GrocOrderHistory) response;
                if (!isSucces || orderListOut == null) {
                    SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    latitude = Double.parseDouble(orderListOut.getOrderHistory().get(position_).getStore().getLatitude());
                    longitude = Double.parseDouble(orderListOut.getOrderHistory().get(position_).getStore().getLongitude());
                    String custName = orderListOut.getOrderHistory().get(position_).getUser().getFirstname() + " " +
                            orderListOut.getOrderHistory().get(position_).getUser().getLastname();
                    customerName.setText(custName);
                    String confTest ="Give " + orderListOut.getOrderHistory().get(position_).getGCartDetail().size() + " item(s) to customer";
                    itemUsr.setText(confTest);
                    orderId.setText(orderListOut.getOrderHistory().get(position_).getGCart().getOrderId());
                    String address = orderListOut.getOrderHistory().get(position_).getAddress().getHouse() + ", "
                            + orderListOut.getOrderHistory().get(position_).getAddress().getApartment() + ", "
                            + orderListOut.getOrderHistory().get(position_).getAddress().getStreet() + ", "
                            + orderListOut.getOrderHistory().get(position_).getAddress().getLandmark() + ", "
                            + orderListOut.getOrderHistory().get(position_).getAddress().getArea() + ", "
                            + orderListOut.getOrderHistory().get(position_).getAddress().getCity() + ", "
                            + orderListOut.getOrderHistory().get(position_).getAddress().getState() + ", "
                            + orderListOut.getOrderHistory().get(position_).getAddress().getCountry() + ", "
                            + orderListOut.getOrderHistory().get(position_).getAddress().getPincode();
                    addressUser.setText(address);
                    if (orderListOut.getOrderHistory().get(position_).getGCart().getPaymentStatus().equals("0")){
                        String cash = " Collect from customer ₹ " + orderListOut.getOrderHistory().get(position_).getGCart().getGrandTotal() +" ";
                        vT_cash_amount.setText(cash);
                        String csh = "Collect ₹ " + orderListOut.getOrderHistory().get(position_).getGCart().getGrandTotal() + " cash";
                        vB_total_cash_amount.setText(csh);
                        vB_total_cash_amount.setVisibility(View.VISIBLE);
                    } else {
                        String cash = " Online Payment of ₹ " + orderListOut.getOrderHistory().get(position_).getGCart().getGrandTotal() +" ";
                        vT_cash_amount.setText(cash);
                        vB_total_cash_amount.setVisibility(View.GONE);
                        vB_finish_do.setVisibility(View.GONE);
                        vB_cash_collected.setVisibility(View.VISIBLE);
                    }
                }
            }
        } if(apiType == WebServices.ApiType.apay){
            if (!isSucces) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                AlreadyPaid alreadyPaid = (AlreadyPaid) response;
                if (!isSucces || alreadyPaid == null) {
                    SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    if (!alreadyPaid.getMessage().isEmpty()){
                        CallDeliverService();
                    }
                }
            }
        } if(apiType == WebServices.ApiType.cpay){
            if (!isSucces) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                CashPayment cashPayment = (CashPayment) response;
                if (!isSucces || cashPayment == null) {
                    SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    if (!cashPayment.getMessage().isEmpty()){
                        SnackBar.makeText(this, "Payment Status updated", SnackBar.LENGTH_SHORT).show();
                    }
                }
            }
        } if(apiType == WebServices.ApiType.opay){
            if (!isSucces) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                OnlinePayment onlinePayment = (OnlinePayment) response;
                if (!isSucces || onlinePayment == null) {
                    SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    if (!onlinePayment.getMessage().isEmpty()){
                        SnackBar.makeText(this, "Payment Status updated", SnackBar.LENGTH_SHORT).show();
                    }
                }
            }
        } if (apiType == WebServices.ApiType.orderDelivered) {
            OrderDelivered orderDelivered = (OrderDelivered) response;
            if (!isSucces || orderDelivered == null) {
                vB_cash_collected.resetSlider();
                Intent intent = new Intent(GroceryFinishDeliveryActivity.this, OrderCompletedActivity.class);
                intent.putExtra("trip_earn", orderListOut.getOrderHistory().get(position_).getGCart().getDeliveryBoyCharge());
                startActivity(intent);
                //SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                //orderCancel.setVisibility(View.GONE);
                Intent intent = new Intent(GroceryFinishDeliveryActivity.this, OrderCompletedActivity.class);
                startActivity(intent);
            }
        }
    }


    private void InitializeRatepopup() {
        RatePopup = new Dialog(GroceryFinishDeliveryActivity.this);
        RatePopup.requestWindowFeature(Window.FEATURE_NO_TITLE);
        RatePopup.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        RatePopup.setContentView(R.layout.customer_feedback_popup);
        RatePopup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        RatePopup.setCancelable(false);
        RatePopup.setCanceledOnTouchOutside(false);
    }

    private void initializeRatePopup() {
        RatePopup.setContentView(R.layout.customer_feedback_popup);
        RatePopup.setCancelable(false);
        RatePopup.setCanceledOnTouchOutside(false);
        RatePopup.show();

        final RelativeLayout vR_smiley = RatePopup.findViewById(R.id.vR_smiley);

        final SmileRating smile_rating = RatePopup.findViewById(R.id.smile_rating);
        final LinearLayout layout1 = RatePopup.findViewById(R.id.layout1);
        final TextView vT_cst = RatePopup.findViewById(R.id.vT_cst);
        final TextView vT_cwn = RatePopup.findViewById(R.id.vT_cwn);
        final TextView vT_cgt = RatePopup.findViewById(R.id.vT_cgt);
        final TextView vT_cot = RatePopup.findViewById(R.id.vT_cot);
        final TextView vT_cgtw = RatePopup.findViewById(R.id.vT_cgtw);
        final TextView skip = RatePopup.findViewById(R.id.skip);
        final TextView submit_rating = RatePopup.findViewById(R.id.submit_rating);


//                vR_smiley.setVisibility(View.GONE);
        //final Button submit_otp = OtpPopup.findViewById(R.id.submit_otp);

        int width = getResources().getDisplayMetrics().widthPixels - 100;
        int height = getResources().getDisplayMetrics().heightPixels - 250;
        RatePopup.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        RatePopup.getWindow().setGravity(Gravity.BOTTOM);

        smile_rating.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(int smiley, boolean reselected) {
                vR_smiley.setVisibility(View.VISIBLE);

            }
        });
        vT_cst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count == 0) {
                    vT_cst.setTextColor(getResources().getColor(R.color.text_color_light2));
                    count = 1;
                } else {
                    vT_cst.setTextColor(getResources().getColor(R.color.green));

                    count = 0;
                }

            }
        });
        vT_cwn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count == 0) {
                    vT_cwn.setTextColor(getResources().getColor(R.color.text_color_light2));

                    count = 1;
                } else {
                    vT_cwn.setTextColor(getResources().getColor(R.color.green));

                    count = 0;
                }

            }
        });
        vT_cgt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count == 0) {
                    vT_cgt.setTextColor(getResources().getColor(R.color.text_color_light2));

                    count = 1;
                } else {
                    vT_cgt.setTextColor(getResources().getColor(R.color.green));

                    count = 0;
                }

            }
        });
        vT_cot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count == 0) {
                    vT_cot.setTextColor(getResources().getColor(R.color.text_color_light2));

                    count = 1;
                } else {
                    vT_cot.setTextColor(getResources().getColor(R.color.green));

                    count = 0;
                }

            }
        });
        vT_cgtw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count == 0) {
                    vT_cgtw.setTextColor(getResources().getColor(R.color.text_color_light2));

                    count = 1;
                } else {
                    vT_cgtw.setTextColor(getResources().getColor(R.color.green));

                    count = 0;
                }

            }
        });
        submit_rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GroceryFinishDeliveryActivity.this, OrderCompletedActivity.class);
                startActivity(intent);

            }
        });


        RatePopup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        RatePopup.setCancelable(true);
        RatePopup.setCanceledOnTouchOutside(true);
        RatePopup.show();


    }


    private void InitializeOnlinePopup() {
        paypopup = new Dialog(GroceryFinishDeliveryActivity.this);
        paypopup.requestWindowFeature(Window.FEATURE_NO_TITLE);
        paypopup.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        paypopup.setContentView(R.layout.pay_popupx);
        paypopup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        paypopup.setCancelable(true);
        paypopup.setCanceledOnTouchOutside(true);
    }

    private void initializeOnlinePopup() {
        paypopup.setContentView(R.layout.pay_popupx);
        paypopup.setCancelable(true);
        paypopup.setCanceledOnTouchOutside(true);
        paypopup.show();

        final Button v_online = paypopup.findViewById(R.id.v_online);
        final Button v_cash = paypopup.findViewById(R.id.v_cash);

//        final TextView view_cart = (TextView) paypopup.findViewById(R.id.view_cart);
//        final ImageView view_cart_img =  paypopup.findViewById(R.id.view_cart_img);


        int width = getResources().getDisplayMetrics().widthPixels - 100;
        int height = getResources().getDisplayMetrics().heightPixels - 250;
        paypopup.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        paypopup.getWindow().setGravity(Gravity.CENTER);


        v_online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (paypopup.isShowing()){
                    paypopup.dismiss();
                    CallOnlineService();
                }

            }
        });

        v_cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (paypopup.isShowing()){
                    paypopup.dismiss();
                    CallCashService();
                }

            }
        });


        paypopup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        paypopup.setCancelable(true);
        paypopup.setCanceledOnTouchOutside(true);
        paypopup.show();
    }
}
