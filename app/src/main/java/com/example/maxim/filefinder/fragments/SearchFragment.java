package com.example.maxim.filefinder.fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.maxim.filefinder.MainActivity;
import com.example.maxim.filefinder.R;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Maxim on 04.09.2016.
 */
public class SearchFragment extends Fragment {

    private static final int THREADS_QUANTITY = 2;
    final String LOG_TAG = "myLogs";
    private ListView listView;
    private ArrayList<String> folders;
    private Button btnSearch;
    private EditText etFilesQuantity;
    ArrayList<SearchItem> resultFiles;
    static int t = -1;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d(LOG_TAG, "SearchFragment onAttach");
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "SearchFragment onCreate");
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(LOG_TAG, "SearchFragment onCreateView");
        final MainActivity activity = (MainActivity) getActivity();
        folders = activity.searchFolders;

        View v = inflater.inflate(R.layout.activity_search, null);
        listView = (ListView) v.findViewById(R.id.lv_search_folders);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, android.R.id.text1, folders);
        listView.setAdapter(adapter);

btnSearch = (Button) v.findViewById(R.id.btn_start_search);
etFilesQuantity = (EditText) v.findViewById(R.id.et_files_quantity);


        btnSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int num;
                try {
                    num = Integer.parseInt(etFilesQuantity.getText().toString());
                    doExecutorService(folders, num);
                } catch(NumberFormatException nfe) {
                    Log.d(LOG_TAG, "Could not parse" + nfe);
                }
            }
        });
        return v;
    }


    public void searchFiles(String folder, int numOfFindFiles){
        Queue<String> queue = new LinkedList<>();
        queue.add(folder);

        Log.d(LOG_TAG, "queue -  " + queue.size());
        //----------------------------------------------
        while (queue.peek() != null) {
            String root;
            File[] files;
            File f = new File(queue.poll());
            root = f.getName();
//            Log.d(LOG_TAG, "ROOT Queue -  " + root);
            files = f.listFiles();
//            Log.d(LOG_TAG, "files list -  " + files.length);
            for (int i = 0; i < files.length; i++) {
                Log.d(LOG_TAG, "i = " + i);
                File file = files[i];
                if (file.isDirectory()){
                    Log.d(LOG_TAG, "ISDIReCTORY" + root + "/" + file.getName());
                    queue.add(root + "/" + file.getName());
                } else {
//                    Log.d(LOG_TAG, "FILE LENGHT -  " + file.length());
//                    addFileToResultMap(root + file.getName(), file.length(), numOfFindFiles);
                }
            }
        }
    }

    private void addFileToResultMap(String name, long length, int resSize) {
        if (resultFiles == null) {
            resultFiles = new ArrayList<>(resSize);
            Log.d(LOG_TAG, "INITIALIZE ARRAY");
        } else if (resultFiles.size() < resSize) {
            resultFiles.add(resSize+1, new SearchItem(name, length));
        } else {
            Log.d(LOG_TAG, "ARRAY SiZE - " + resultFiles.size());
        }
    }

    /**
     * multithreading. Find files.
     */
    private void doExecutorService(final ArrayList<String> folders, final int numOfFindFiles) {
        ExecutorService service = Executors.newFixedThreadPool(THREADS_QUANTITY);
        for ( int i = 0; i < folders.size(); i++) {
            final int finalI = i;
            service.submit(new Runnable() {
                @Override
                public void run() {
                    Log.d(LOG_TAG, "HELLO" + folders.get(finalI));
                    searchFiles(folders.get(finalI), numOfFindFiles);

                }
            });
        }
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(LOG_TAG, "SearchFragment onActivityCreated");
    }

    public void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "SearchFragment onStart");
    }

    public void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "SearchFragment onResume");
    }

    public void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "SearchFragment onPause");
    }

    public void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "SearchFragment onStop");
    }

    public void onDestroyView() {
        super.onDestroyView();
        Log.d(LOG_TAG, "SearchFragment onDestroyView");
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "SearchFragment onDestroy");
    }

    public void onDetach() {
        super.onDetach();
        Log.d(LOG_TAG, "SearchFragment onDetach");
    }
}
