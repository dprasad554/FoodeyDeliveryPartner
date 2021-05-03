/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.geekhive.foodeydeliveryboy.notifications;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.DebugUtils;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

import com.geekhive.foodeydeliveryboy.R;
import com.geekhive.foodeydeliveryboy.activities.CakeNewOrderAlert;
import com.geekhive.foodeydeliveryboy.activities.GroceryNewOrderAlert;
import com.geekhive.foodeydeliveryboy.activities.MainActivity;
import com.geekhive.foodeydeliveryboy.activities.NewOrderAlertActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    Uri soundUri;
    private MediaPlayer player;
    public static final String ACTION_1 = "action_1";
    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. LoginUserData messages
        // are handled
        // here in onMessageReceived whether the app is in the foreground or background. LoginUserData
        // messages are the type
        // traditionally used with GCM. Notification messages are only received here in
        // onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated
        // notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages
        // containing both notification
        // and data payloads are treated as notification messages. The Firebase console always
        // sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            handleNow();
          /*  if (*//* Check if data needs to be processed by long running job *//* true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                scheduleJob();
            } else {
                // Handle message within 10 seconds
                handleNow();
            }*/

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getData() != null) {
            sendNotification(remoteMessage);
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getData().toString());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    // [END receive_message]


    // [START on_new_token]

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(token);
    }
    // [END on_new_token]

    /**
     * Schedule a job using FirebaseJobDispatcher.
     */

    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private void handleNow() {
        Log.d(TAG, "Short lived task is done.");
    }

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
    }


    private void sendNotification(RemoteMessage remoteMessage) {
        if (remoteMessage != null) {
            if (remoteMessage.getData().get("topic").equals("Resturant")){
                String channelId = getString(R.string.app_name);
                Intent intent = new Intent(MyFirebaseMessagingService.this, NewOrderAlertActivity.class);
                intent.putExtra("position", 0);
                intent.putExtra("topic", remoteMessage.getData().get("topic"));
                intent.putExtra("ms", remoteMessage.getData().get("body"));

                Bundle bundle = new Bundle();
                bundle.putString("ms", remoteMessage.getData().get("body"));
                Intent myIntent = new Intent("FBR-IMAGE");
                myIntent.putExtra("position", 0);
                myIntent.putExtra("topic", remoteMessage.getData().get("topic"));
                myIntent.putExtra("ms", remoteMessage.getData().get("body"));
                myIntent.putExtra("NOTIF_BODY",bundle);
                this.sendStickyBroadcast(myIntent);
                PendingIntent pendingIntent = PendingIntent.getActivity(MyFirebaseMessagingService.this, 100, intent, PendingIntent.FLAG_ONE_SHOT);
                if(remoteMessage.getData().get("body").contains("resturant confirm this order and preparing food")){
                    soundUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getApplicationContext().getPackageName() + "/" + R.raw.howl);

                }else {
                    soundUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getApplicationContext().getPackageName() + "/" + R.raw.hold);
                }


                final RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.remoteview_notification);


                String name = remoteMessage.getData().get("title") + "\n" + remoteMessage.getData().get("body");
                remoteViews.setTextViewText(R.id.remoteview_notification_headline, name);

                remoteViews.setTextColor(R.id.remoteview_notification_headline, getResources().getColor(android.R.color.black));

                // build notification
                final NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(MyFirebaseMessagingService.this, channelId)
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle(remoteMessage.getData().get("title"))
                                .setContentText(remoteMessage.getData().get("body"))
                                .setAutoCancel(true)
                                .setSound(soundUri)
                                .setContent(remoteViews)
                                .setContentIntent(pendingIntent)
                                .setPriority(NotificationCompat.PRIORITY_HIGH);

                final Notification notification = mBuilder.build();
                if (remoteMessage.getData().get("body").contains("resturant confirm this order and preparing food")){
                    notification.flags |= Notification.FLAG_INSISTENT;
                }

                // set big content view for newer androids
                if (android.os.Build.VERSION.SDK_INT >= 16) {
                    notification.bigContentView = remoteViews;
                }
                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


                    AudioAttributes attributes = new AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                            .build();

                    NotificationChannel mChannel = new NotificationChannel(channelId,
                            getApplicationContext().getString(R.string.app_name),
                            NotificationManager.IMPORTANCE_HIGH);

                    // Configure the notification channel.
                    mChannel.setDescription(remoteMessage.getData().get("body"));
                    mChannel.enableLights(true);
                    mChannel.enableVibration(true);
                    mChannel.setSound(soundUri, attributes); // This is IMPORTANT


                    if (mNotificationManager != null)
                        mNotificationManager.createNotificationChannel(mChannel);
                }


                mNotificationManager.notify(0, notification);
            } else if(remoteMessage.getData().get("topic").equals("Grocery")){
                String channelId = getString(R.string.app_name);
                Intent intent = new Intent(MyFirebaseMessagingService.this, GroceryNewOrderAlert.class);
                intent.putExtra("topic", remoteMessage.getData().get("topic"));
                intent.putExtra("ms", remoteMessage.getData().get("body"));

                Bundle bundle = new Bundle();
                bundle.putString("ms", remoteMessage.getData().get("body"));
                Intent myIntent = new Intent("FBR-IMAGE");
                myIntent.putExtra("position", 0);
                myIntent.putExtra("topic", remoteMessage.getData().get("topic"));
                myIntent.putExtra("ms", remoteMessage.getData().get("body"));
                myIntent.putExtra("NOTIF_BODY",bundle);
                this.sendStickyBroadcast(myIntent);

                PendingIntent pendingIntent = PendingIntent.getActivity(MyFirebaseMessagingService.this, 100, intent, PendingIntent.FLAG_ONE_SHOT);
                Uri soundUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getApplicationContext().getPackageName() + "/" + R.raw.hold);
                final RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.remoteview_notification);


                String name = remoteMessage.getData().get("title") + "\n" + remoteMessage.getData().get("body");
                remoteViews.setTextViewText(R.id.remoteview_notification_headline, name);

                remoteViews.setTextColor(R.id.remoteview_notification_headline, getResources().getColor(android.R.color.black));

                // build notification
                final NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(MyFirebaseMessagingService.this, channelId)
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle(remoteMessage.getData().get("title"))
                                .setContentText(remoteMessage.getData().get("body"))
                                .setAutoCancel(true)
                                .setSound(soundUri)
                                .setContent(remoteViews)
                                .setContentIntent(pendingIntent)
                                .setPriority(NotificationCompat.PRIORITY_HIGH);

                final Notification notification = mBuilder.build();

                // set big content view for newer androids
                if (android.os.Build.VERSION.SDK_INT >= 16) {
                    notification.bigContentView = remoteViews;
                }
                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


                    AudioAttributes attributes = new AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                            .build();

                    NotificationChannel mChannel = new NotificationChannel(channelId,
                            getApplicationContext().getString(R.string.app_name),
                            NotificationManager.IMPORTANCE_HIGH);

                    // Configure the notification channel.
                    mChannel.setDescription(remoteMessage.getData().get("body"));
                    mChannel.enableLights(true);
                    mChannel.enableVibration(true);
                    mChannel.setSound(soundUri, attributes); // This is IMPORTANT


                    if (mNotificationManager != null)
                        mNotificationManager.createNotificationChannel(mChannel);
                }


                mNotificationManager.notify(1, notification);
            }else if (remoteMessage.getData().get("topic").equals("Cake")){
                String channelId = getString(R.string.app_name);
                Intent intent = new Intent(MyFirebaseMessagingService.this, CakeNewOrderAlert.class);
                intent.putExtra("topic", remoteMessage.getData().get("topic"));
                intent.putExtra("ms", remoteMessage.getData().get("body"));

                Bundle bundle = new Bundle();
                bundle.putString("ms", remoteMessage.getData().get("body"));
                Intent myIntent = new Intent("FBR-IMAGE");
                myIntent.putExtra("position", 0);
                myIntent.putExtra("topic", remoteMessage.getData().get("topic"));
                myIntent.putExtra("ms", remoteMessage.getData().get("body"));
                myIntent.putExtra("NOTIF_BODY",bundle);
                this.sendStickyBroadcast(myIntent);

                PendingIntent pendingIntent = PendingIntent.getActivity(MyFirebaseMessagingService.this, 100, intent, PendingIntent.FLAG_ONE_SHOT);
                Uri soundUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getApplicationContext().getPackageName() + "/" + R.raw.hold);
                final RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.remoteview_notification);


                String name = remoteMessage.getData().get("title") + "\n" + remoteMessage.getData().get("body");
                remoteViews.setTextViewText(R.id.remoteview_notification_headline, name);

                remoteViews.setTextColor(R.id.remoteview_notification_headline, getResources().getColor(android.R.color.black));

                // build notification
                final NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(MyFirebaseMessagingService.this, channelId)
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle(remoteMessage.getData().get("title"))
                                .setContentText(remoteMessage.getData().get("body"))
                                .setAutoCancel(true)
                                .setSound(soundUri)
                                .setContent(remoteViews)
                                .setContentIntent(pendingIntent)
                                .setPriority(NotificationCompat.PRIORITY_HIGH);

                final Notification notification = mBuilder.build();

                // set big content view for newer androids
                if (android.os.Build.VERSION.SDK_INT >= 16) {
                    notification.bigContentView = remoteViews;
                }
                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


                    AudioAttributes attributes = new AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                            .build();

                    NotificationChannel mChannel = new NotificationChannel(channelId,
                            getApplicationContext().getString(R.string.app_name),
                            NotificationManager.IMPORTANCE_HIGH);

                    // Configure the notification channel.
                    mChannel.setDescription(remoteMessage.getData().get("body"));
                    mChannel.enableLights(true);
                    mChannel.enableVibration(true);
                    mChannel.setSound(soundUri, attributes); // This is IMPORTANT


                    if (mNotificationManager != null)
                        mNotificationManager.createNotificationChannel(mChannel);
                }


                mNotificationManager.notify(1, notification);
            }

        }
    }

    public static class NotificationActionService extends IntentService {
        public NotificationActionService() {
            super(NotificationActionService.class.getSimpleName());
        }

        @Override
        protected void onHandleIntent(Intent intent) {
            String action = intent.getAction();
            Log.e("Received notification: " , action);
            if (ACTION_1.equals(action)) {
                // TODO: handle action 1.
                // If you want to cancel the notification: NotificationManagerCompat.from(this).cancel(NOTIFICATION_ID);
            }
        }
    }
}

