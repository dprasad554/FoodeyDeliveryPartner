package com.geekhive.foodeydeliveryboy.trackingbackground;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.geekhive.foodeydeliveryboy.beans.trackloc.UpdateCurrentLocation;
import com.geekhive.foodeydeliveryboy.utils.GitApi;
import com.geekhive.foodeydeliveryboy.utils.Prefs;
import com.geekhive.foodeydeliveryboy.utils.WebServices;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by user pc on 04-10-2017.
 */

public class Servicecall extends Service {
    public static final int APPOINTMENT_NOTIFICATION_ID = getvalue();
    public static final String TIME_INFO = "time_info";
    Intent dialogIntent;
    Random f43r = new Random();
    SharedPreferences.Editor mEditor;
    SharedPreferences mpref;
    Retrofit retrofit;
    GitApi apiService;
    Disposable disposable;
    String order_id, lat, lang;

    private static int getvalue() {
        return new Random().nextInt(16) + 65;
    }

    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(final Intent intent, int flags, int startId) {

        order_id = intent.getStringExtra("order_id");
        lat = intent.getStringExtra("latitude");
        lang = intent.getStringExtra("longitude");

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        retrofit = new Retrofit.Builder()
                .baseUrl(WebServices.Foodey_Services)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        apiService = retrofit.create(GitApi.class);

        disposable = Observable.interval(1000, 5000,
                TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::callLocationUpdate, this::onError);

        return START_STICKY;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Toast.makeText(getApplicationContext(), "onTaskRemoved called", Toast.LENGTH_LONG).show();
        System.out.println("onTaskRemoved called");
        super.onTaskRemoved(rootIntent);
    }


    public void onDestroy() {
        super.onDestroy();
        new Intent("time_info").putExtra("VALUE", "Stopped");
    }

    private void callLocationUpdate(Long aLong) {


        Observable<UpdateCurrentLocation> observable = apiService.updateCurrentLocation(MultipartBody.Part.createFormData("del_id", Prefs.getUserId(this))
                , MultipartBody.Part.createFormData("order_id", order_id),
                MultipartBody.Part.createFormData("latitude", lat),
                MultipartBody.Part.createFormData("longitude", lang));
        observable.subscribeOn(Schedulers.newThread()).
                observeOn(AndroidSchedulers.mainThread())
                .map(result -> result.getMessage())
                .subscribe(this::handleResults, this::handleError);
    }

    private void onError(Throwable throwable) {
        Toast.makeText(this, "OnError in Observable Timer",
                Toast.LENGTH_LONG).show();
    }


    private void handleResults(String message) {


        if (!message.equals("")) {
            Log.e("Location Updated", "Success");


        } else {
            Toast.makeText(this, "NO RESULTS FOUND",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void handleError(Throwable t) {

        //Add your error here.
    }
}