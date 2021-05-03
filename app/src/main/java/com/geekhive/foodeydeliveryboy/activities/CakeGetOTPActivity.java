package com.geekhive.foodeydeliveryboy.activities;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.geekhive.foodeydeliveryboy.R;
import com.geekhive.foodeydeliveryboy.beans.orderHistory.OrderHistory;
import com.geekhive.foodeydeliveryboy.beans.otpverify.VerifyOtp;
import com.geekhive.foodeydeliveryboy.beans.resendotp.ResendOtp;
import com.geekhive.foodeydeliveryboy.trackingbackground.Servicecall;
import com.geekhive.foodeydeliveryboy.utils.ConnectionDetector;
import com.geekhive.foodeydeliveryboy.utils.OnResponseListner;
import com.geekhive.foodeydeliveryboy.utils.Prefs;
import com.geekhive.foodeydeliveryboy.utils.SnackBar;
import com.geekhive.foodeydeliveryboy.utils.WebServices;

import java.util.Calendar;

public class CakeGetOTPActivity extends AppCompatActivity implements OnResponseListner {
    Button get_otp;
    Dialog OtpPopup;
    ConnectionDetector mDetector;
    OrderHistory orderListOut;
    double lat;
    double lang;
    int position_;
    double latitude, longitude;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_otp_activity);
        mDetector = new ConnectionDetector(this);
        get_otp = findViewById(R.id.get_otp);
        lat = Double.parseDouble(getIntent().getStringExtra("lat"));
        lang = Double.parseDouble(getIntent().getStringExtra("lang"));
        position_ = getIntent().getIntExtra("position", 0);
        get_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InitializeConfirmOtppopup();
                initializeConfirmOtpPopup();

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

    private void CallResendService() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).ResendDelOtp(WebServices.Foodey_Services,
                    WebServices.ApiType.resendOtp, orderListOut.getOrderHistory().get(position_).getCart().getOrderId(), Prefs.getUserId(this));
        }

    }

    private void CallOtpService(String otp, String order_id) {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).VerifyCakeDeliveryOtp(WebServices.Foodey_Services,
                    WebServices.ApiType.otpD, otp, Prefs.getUserId(this), order_id);
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
                    latitude = Double.parseDouble(orderListOut.getOrderHistory().get(position_).getResturant().getLat());
                    longitude = Double.parseDouble(orderListOut.getOrderHistory().get(position_).getResturant().getLong());
                }
            }
        } if (apiType == WebServices.ApiType.resendOtp) {
            if (!isSucces) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {

                ResendOtp resendOtp = (ResendOtp) response;
                if (!isSucces || resendOtp == null) {
                    SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    if (resendOtp != null){
                        if (!resendOtp.getMessage().isEmpty()){
                            SnackBar.makeText(this, resendOtp.getMessage(), SnackBar.LENGTH_SHORT).show();
                            InitializeOtppopup();
                            initializeOtpPopup();
                        } else {
                            SnackBar.makeText(this, "Incorrect otp", SnackBar.LENGTH_SHORT).show();
                        }
                    } else {
                        SnackBar.makeText(this, "Incorrect otp", SnackBar.LENGTH_SHORT).show();
                    }
                }
            }

        } if (apiType == WebServices.ApiType.otpD) {
            if (!isSucces) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                VerifyOtp verifyOtp = (VerifyOtp) response;
                if (!isSucces || verifyOtp == null) {
                    SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    if (verifyOtp.getMessage() != null) {
                        if (!verifyOtp.getMessage().isEmpty()) {
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTimeInMillis(System.currentTimeMillis());
                            calendar.add(Calendar.SECOND, 1);
                            Intent intent = new Intent(getApplicationContext(), Servicecall.class);
                            intent.putExtra("order_id", orderListOut.getOrderHistory().get(position_).getCart().getOrderId());
                            intent.putExtra("latitude", String.valueOf(latitude));
                            intent.putExtra("longitude", String.valueOf(longitude));
                            PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), 1212, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                            if (alarmManager != null) {
                                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 5000, pendingIntent);
                            }
                            startService(intent);
                            Intent intenti = new Intent(CakeGetOTPActivity.this, PickOrderActivity.class);
                            intenti.putExtra("position", position_);
                            intenti.putExtra("lat", orderListOut.getOrderHistory().get(position_).getResturant().getLat());
                            intenti.putExtra("lang", orderListOut.getOrderHistory().get(position_).getResturant().getLong());
                            startActivity(intenti);
                        } else {
                            SnackBar.makeText(this, "Please check otp entered", SnackBar.LENGTH_SHORT).show();
                        }
                    } else {
                        SnackBar.makeText(this, "Seems like otp entered is incorrect", SnackBar.LENGTH_SHORT).show();
                    }

                }
            }
        }
    }

    private void InitializeConfirmOtppopup() {
        OtpPopup = new Dialog(CakeGetOTPActivity.this);
        OtpPopup.requestWindowFeature(Window.FEATURE_NO_TITLE);
        OtpPopup.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        OtpPopup.setContentView(R.layout.otp_confirmation_popup);
        OtpPopup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        OtpPopup.setCancelable(false);
        OtpPopup.setCanceledOnTouchOutside(false);
    }

    private void initializeConfirmOtpPopup() {
        OtpPopup.setContentView(R.layout.otp_confirmation_popup);
        OtpPopup.setCancelable(false);
        OtpPopup.setCanceledOnTouchOutside(false);
        OtpPopup.show();

        final Button yes = OtpPopup.findViewById(R.id.yes);
        //final Button submit_otp = OtpPopup.findViewById(R.id.submit_otp);

        int width = getResources().getDisplayMetrics().widthPixels - 100;
        int height = getResources().getDisplayMetrics().heightPixels - 250;
        OtpPopup.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        OtpPopup.getWindow().setGravity(Gravity.BOTTOM);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (OtpPopup.isShowing()) {
                    OtpPopup.dismiss();
                    CallResendService();
                }
            }


        });

        OtpPopup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        OtpPopup.setCancelable(true);
        OtpPopup.setCanceledOnTouchOutside(true);
        OtpPopup.show();


    }

    private void InitializeOtppopup() {
        OtpPopup = new Dialog(CakeGetOTPActivity.this);
        OtpPopup.requestWindowFeature(Window.FEATURE_NO_TITLE);
        OtpPopup.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        OtpPopup.setContentView(R.layout.otp_submit_popup);
        OtpPopup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        OtpPopup.setCancelable(false);
        OtpPopup.setCanceledOnTouchOutside(false);
    }

    private void initializeOtpPopup() {
        OtpPopup.setContentView(R.layout.otp_submit_popup);
        OtpPopup.setCancelable(false);
        OtpPopup.setCanceledOnTouchOutside(false);
        OtpPopup.show();

        final TextView need_help = OtpPopup.findViewById(R.id.need_help);
        final Button submit_otp = OtpPopup.findViewById(R.id.submit_otp);
        final EditText etOtp = OtpPopup.findViewById(R.id.etOtp);

        int width = getResources().getDisplayMetrics().widthPixels - 100;
        int height = getResources().getDisplayMetrics().heightPixels - 250;
        OtpPopup.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        OtpPopup.getWindow().setGravity(Gravity.BOTTOM);


        need_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CallResendService();
            }
        });
        submit_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (OtpPopup.isShowing()) {
                    OtpPopup.dismiss();
                    if (!etOtp.getText().toString().equals("") || !etOtp.getText().toString().isEmpty()) {
                        CallOtpService(etOtp.getText().toString(), orderListOut.getOrderHistory().get(position_).getCart().getOrderId());
                    }
                }
            }
        });

        OtpPopup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        OtpPopup.setCancelable(true);
        OtpPopup.setCanceledOnTouchOutside(true);
        OtpPopup.show();
    }
}
