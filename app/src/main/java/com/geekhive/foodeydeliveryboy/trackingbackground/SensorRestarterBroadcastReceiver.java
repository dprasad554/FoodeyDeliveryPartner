package com.geekhive.foodeydeliveryboy.trackingbackground;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by user pc on 06-10-2017.
 */

public class SensorRestarterBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intentq) {

        //todo
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 1);
        Intent intent = new Intent(context.getApplicationContext(), Servicecall.class);
        //intentToQB.putExtra("Email",APP_USER_MAIL);
        PendingIntent pendingIntent = PendingIntent.getService(context.getApplicationContext(), 1212, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 5000, pendingIntent);
        }
        context.startService(intent);

    }
}