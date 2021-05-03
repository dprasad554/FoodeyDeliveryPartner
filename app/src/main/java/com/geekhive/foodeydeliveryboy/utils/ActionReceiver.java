package com.geekhive.foodeydeliveryboy.utils;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaSession2;
import android.media.session.MediaSession;
import android.net.Uri;

import com.geekhive.foodeydeliveryboy.R;

public class ActionReceiver extends BroadcastReceiver {
    MediaPlayer player;
    MediaSession mediaSession;
    @Override
    public void onReceive(Context context, Intent intent) {

        //Toast.makeText(context,"recieved",Toast.LENGTH_SHORT).show();
        Uri soundUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.getApplicationContext().getPackageName() + "/" + R.raw.howl);
        /*player = MediaPlayer.create(context,
                soundUri);*/

        String action=intent.getStringExtra("action");
        if(action.equals("new order")){
            performAction1();
        }
        else if(action.equals("ongoing")){
            performAction2();

        }
        //This is used to close the notification tray
        Intent it = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        context.sendBroadcast(it);
    }

    public void performAction1(){
        if (player.isLooping() || player.isPlaying()){
            player.setLooping(false);
            player.stop();
        }
    }

    public void performAction2(){

    }

}
