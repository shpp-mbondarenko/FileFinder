package com.example.filefinder;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by Maxim on 19.09.2016.
 */
public class SearchService extends Service {

    private static final String ARRAY = "array";
    final String LOG_TAG = "myLogs";

    private ArrayList<String> folders;
    Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "onCreate");
        folders = new ArrayList<String>();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG_TAG, "onStartCommand");
        Bundle bundle = intent.getBundleExtra(ARRAY);
        folders = bundle.getStringArrayList(ARRAY);
        for (int i =0; i < folders.size(); i++){
            Log.d(LOG_TAG,"SERVICE folders - " + folders.get(i));
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            createNotification(56, R.drawable.ic_action_view_list, "Searching files...", "files checked - " + i);
        }
        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy");
    }

    //  createNotification(56, R.drawable.ic_launcher, "New Message",
//      "There is a new message from Bob!");
    private void createNotification(int nId, int iconRes, String title, String body) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this).setSmallIcon(iconRes)
                .setContentTitle(title)
                .setContentText(body);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(nId, mBuilder.build());
    }



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(LOG_TAG, "onBind");
        return null;
    }
}
