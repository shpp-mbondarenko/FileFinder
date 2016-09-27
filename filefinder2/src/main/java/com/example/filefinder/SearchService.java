package com.example.filefinder;

import android.app.NotificationManager;
import android.app.PendingIntent;
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * Created by Maxim on 19.09.2016.
 */
public class SearchService extends Service {

    private final String QUANTITY_OF_FILES = "quantity";
    private final String ARRAY = "array";
    private final String FILE_SIZES = "fileSizes";
    private final String FILE_PATH = "filePath";
    private final String ROOT = "/root";
    final String LOG_TAG = "myLogs";

    private long[] fileSizes;
    private String[] filePath;
    private Queue<Long> queueSizeOfFindFiles;
    private Map<Long,String> resultMap;
    Comparator<Long> comparator;

    private ArrayList<String> folders;
    private int filesToFind;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "onCreate");
        folders = new ArrayList<>();
        comparator = new LongComparator();
        queueSizeOfFindFiles = new PriorityQueue<>();
        resultMap = new HashMap<>();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG_TAG, "onStartCommand");
        Bundle bundle = intent.getBundleExtra(ARRAY);
        folders = bundle.getStringArrayList(ARRAY);
        filesToFind = intent.getIntExtra(QUANTITY_OF_FILES, 3);
        Log.d(LOG_TAG,"files to find - " + filesToFind);
        new Thread(new Runnable() {
            public void run() {
                if (folders.get(0).equals(ROOT)){
                    folders.clear();
                    folders.add("/");
                    searchFiles(folders, filesToFind);
                } else {
                    searchFiles(folders, filesToFind);
                }
                stopSelf();
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    public void searchFiles(ArrayList<String> folders, int numOfFindFiles){
        Queue<String> queue = new LinkedList<>();
        for (int i =0; i < folders.size(); i++){
            queue.add(folders.get(i));
        }
        int counter = 0;
        //----------------------------------------------
        while (queue.peek() != null) {
            String root;
            root = queue.peek();
            File f = new File(queue.poll());
            File[] files;
            files = f.listFiles();
            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    counter++;
                    if (counter % 500 == 0) {
                        createNotification(56, R.drawable.ic_action_view_list, "Searching files...", "More than files checked - " + counter, false);
                    }
                    File file = files[i];
                    if (file.isDirectory() && !file.isHidden()) {
                        queue.add(root + file.getName() + "/");
                    } else {
                        if (file.length() > 0 && !file.isHidden()) {//
                            putFileToResult(file.length(), root + file.getName());
                        }
                    }
                }
            }
        }
        createNotification(1, R.drawable.ic_action_view_list, "Searching done!", "Files checked - " + counter, true);
    }

    //function is deciding put file to result or not
    private void putFileToResult(long length, String s) {
        if (fileSizes == null && filePath == null) {
            fileSizes = new long[filesToFind];
            filePath = new String[filesToFind];
            Log.d(LOG_TAG, "CREATING ARRAYS");
        }
        Log.d(LOG_TAG, "File len - "+length+" File nam - "+s);
        for (int i = 0; i < fileSizes.length; i++){
            if (fileSizes[fileSizes.length - 1] > length) {
                break;
            }
            String str = filePath[i];
            long lng = fileSizes[i];
            if (fileSizes[i] < length) {
                fileSizes[i] = length;
                filePath[i] = s;
                Log.d(LOG_TAG, "File was put in " + i + " size " + length);
                if (i < fileSizes.length - 1) {
                    putFileToResult(lng, str);
                }
                break;
            }
        }
        Log.d(LOG_TAG, "function end");
    }

    //  createNotification
    private void createNotification(int nId, int iconRes, String title, String body, Boolean sound) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this).setSmallIcon(iconRes)
                .setContentTitle(title)
                .setContentText(body);
        if (sound) {
            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            mBuilder.setSound(uri);
            //add Action
            Intent intent = new Intent(this, SearchResultActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(FILE_SIZES, fileSizes);
            bundle.putSerializable(FILE_PATH, filePath);
            intent.putExtra(ARRAY, bundle);
            int requestID = (int) System.currentTimeMillis(); //unique requestID to differentiate between various notification with same NotifId
            int flags = PendingIntent.FLAG_CANCEL_CURRENT; // cancel old intent and create new one
            PendingIntent pIntent = PendingIntent.getActivity(this, requestID, intent, flags);
            mBuilder.setContentIntent(pIntent);
        }
        mBuilder.setAutoCancel(true);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(nId, mBuilder.build());
    }


    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy");
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
