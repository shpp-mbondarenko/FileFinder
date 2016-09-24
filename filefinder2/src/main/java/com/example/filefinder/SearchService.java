package com.example.filefinder;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by Maxim on 19.09.2016.
 */
public class SearchService extends Service {

    private final String QUANTITY_OF_FILES = "quantity";
    private final String ARRAY = "array";
    private final String ROOT = "ROOT/...";
    final String LOG_TAG = "myLogs";

    private Queue<Long> bestFiles;
    Comparator<Long> comparator;

    private ArrayList<String> folders;
    private int filesToFind;
    Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "onCreate");
        folders = new ArrayList<>();
        comparator = new LongComparator();
        bestFiles = new PriorityQueue<>();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG_TAG, "onStartCommand");
        Bundle bundle = intent.getBundleExtra(ARRAY);
        folders = bundle.getStringArrayList(ARRAY);
        filesToFind = intent.getIntExtra(QUANTITY_OF_FILES, 3);
        Log.d(LOG_TAG,"files to find" + filesToFind);
        new Thread(new Runnable() {
            public void run() {
                searchFiles(folders, filesToFind);
                stopSelf();
            }
        }).start();


//            createNotification(56, R.drawable.ic_action_view_list, "Searching files...", "files checked - " + i);


        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy");
    }

    public void searchFiles(ArrayList<String> folders, int numOfFindFiles){
        Queue<String> queue = new LinkedList<>();
        for (int i =0; i < folders.size(); i++){
           queue.add(folders.get(i));
        }
        int counter = 0;
        Log.d(LOG_TAG, "queue -  " + queue.size());
        //----------------------------------------------
        while (queue.peek() != null) {
            String root;
            root = queue.peek();
            File f = new File(queue.poll());
            File[] files;
//            Log.d(LOG_TAG, "ROOT Queue - " + root);
            files = f.listFiles();
            if (files != null) {
//                Log.d(LOG_TAG, "files list -  " + files.length);
                for (int i = 0; i < files.length; i++) {
                    counter++;
                    if (counter%500 == 0) {
                        createNotification(56, R.drawable.ic_action_view_list, "Searching files...", "More than files checked - " + counter, false);
                    }
                    File file = files[i];
                    if (file.isDirectory()) {
//                        Log.d(LOG_TAG, "ISDIReCTORY " + root + file.getName() + "/");
                        queue.add(root + file.getName() + "/");
                    } else {
//                        Log.d(LOG_TAG, "FILE LENGHT -  " + file.length() + " " + root + file.getName());
                        if (file.length() != 0) {
                            bestFiles.add(file.length());
                        }                   }
                }
            }
        }
        Log.d(LOG_TAG, "counter = " + counter);
        if (filesToFind > bestFiles.size()) {
            filesToFind = bestFiles.size();
        }
        Long[] b = new Long[filesToFind];
//        filesToFind = bestFiles.size() - filesToFind;
        int placeInArray = filesToFind;
        for (int i = bestFiles.size() - 1; i >= 0; --i) {
            if (i < filesToFind){
                b[--placeInArray] = bestFiles.poll();
            } else {
                bestFiles.remove();
            }

        }
        for (int i =0; i < b.length; i++) {
            Log.d(LOG_TAG, "BEST FILES -  " + b[i]);
        }
        createNotification(1, R.drawable.ic_action_view_list, "Searching done!", "Files checked - " + counter, true);
    }


    //  createNotification(56, R.drawable.ic_launcher, "New Message",
    //      "There is a new message from Bob!");
    private void createNotification(int nId, int iconRes, String title, String body, Boolean sound) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this).setSmallIcon(iconRes)
                .setContentTitle(title)
                .setContentText(body);
        if (sound) {
            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            mBuilder.setSound(uri);
        }
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

    public class LongComparator implements Comparator<Long> {

        @Override
        public int compare(Long first, Long second) {
            return (int) (first - second);
        }
    }
}
