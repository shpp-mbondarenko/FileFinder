package com.example.maxim.filefinder.fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.maxim.filefinder.R;

/**
 * Created by Maxim on 04.09.2016.
 */
public class SearchFragment extends Fragment {

    final String LOG_TAG = "myLogs";

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d(LOG_TAG, "SearchFragment onAttach");
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "SearchFragment onCreate");
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(LOG_TAG, "SearchFragment onCreateView");
        View v = inflater.inflate(R.layout.activity_search, null);
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
