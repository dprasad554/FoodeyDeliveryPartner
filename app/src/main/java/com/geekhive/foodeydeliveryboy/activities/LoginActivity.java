package com.geekhive.foodeydeliveryboy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.geekhive.foodeydeliveryboy.R;
import com.geekhive.foodeydeliveryboy.beans.login.LoginOut;
import com.geekhive.foodeydeliveryboy.utils.ConnectionDetector;
import com.geekhive.foodeydeliveryboy.utils.OnResponseListner;
import com.geekhive.foodeydeliveryboy.utils.Prefs;
import com.geekhive.foodeydeliveryboy.utils.SnackBar;
import com.geekhive.foodeydeliveryboy.utils.WebServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;


public class LoginActivity extends AppCompatActivity implements OnResponseListner {

    EditText vE_al_email, vE_al_password;
    ConnectionDetector mDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        vE_al_email = findViewById(R.id.vE_al_email);
        vE_al_password = findViewById(R.id.vE_al_password);
        mDetector = new ConnectionDetector(this);
    }

    public void doLogin(View view) {
        if (!(vE_al_email.getText().toString().equals("")) || !(vE_al_email.getText().toString().isEmpty())) {
            if (!(vE_al_password.getText().toString().equals("")) || !(vE_al_password.getText().toString().isEmpty())) {
                initializeFirebase();
            }else {
                vE_al_password.setError("Please enter password");
            }
        } else {
            vE_al_email.setError("Please enter email id or phone number");
        }
    }

    private void CallService(String token) {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).Login(WebServices.Foodey_Services,
                    WebServices.ApiType.login, vE_al_email.getText().toString(), vE_al_password.getText().toString(), token);
        }

    }

    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSucces) {

        if (apiType == WebServices.ApiType.login) {
            if (!isSucces) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {

                LoginOut loginOut = (LoginOut) response;
                if (!isSucces || loginOut == null) {
                    SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    if (loginOut.getDeliveryBoy() != null) {
                        Prefs.setUserId(this, loginOut.getDeliveryBoy().getId());
                        Prefs.setUserEmail(this, loginOut.getDeliveryBoy().getEmail());
                        Prefs.setUserFirstName(this, loginOut.getDeliveryBoy().getFirstname());
                        Prefs.setUserLastName(this, loginOut.getDeliveryBoy().getLastname());
                        Prefs.setMobileNumber(this, loginOut.getDeliveryBoy().getMobile());
                        Prefs.setUserProfileUrl(this, loginOut.getDeliveryBoy().getProfile());
                        Prefs.setBikeNo(this, loginOut.getDeliveryBoy().getBikeNumber());
                        Prefs.setCityId(this, loginOut.getDeliveryBoy().getCityId());
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                        LoginActivity.this.finish();
                    } else {
                        SnackBar.makeText(LoginActivity.this, loginOut.getMessage(), SnackBar.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    public void doSignUp(View view) {
        startActivity(new Intent(this, SignUpActivity.class));
        LoginActivity.this.finish();
    }

    private void initializeFirebase() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("Home Activity", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        CallService(token);
                        // Log and toast
                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d("Home Activiy", msg);
                        //Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
        // [END retrieve_current_token]
    }
}
