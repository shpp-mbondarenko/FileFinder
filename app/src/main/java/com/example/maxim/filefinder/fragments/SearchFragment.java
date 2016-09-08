package com.example.maxim.filefinder.fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.maxim.filefinder.MainActivity;
import com.example.maxim.filefinder.R;

import java.util.ArrayList;

/**
 * Created by Maxim on 04.09.2016.
 */
public class SearchFragment extends Fragment {

    final String LOG_TAG = "myLogs";
    private ListView listView;
    private ArrayList<String> folders;

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
        MainActivity activity = (MainActivity) getActivity();
        folders = activity.searchFolders;

        for (int i = 0; i < folders.size(); i++) {
            Log.d(LOG_TAG, "SEARCH " + folders.get(i));
        }

        View v = inflater.inflate(R.layout.activity_search, null);
        listView = (ListView) v.findViewById(R.id.lv_search_folders);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, android.R.id.text1, folders);
        // Assign adapter to ListView
        listView.setAdapter(adapter);

        return v;
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
