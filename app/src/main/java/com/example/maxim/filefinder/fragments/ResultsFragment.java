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
public class ResultsFragment extends Fragment {

    final String LOG_TAG = "myLogs";

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d(LOG_TAG, "ResultsFragment onAttach");
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "ResultsFragment onCreate");
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(LOG_TAG, "ResultsFragment onCreateView");
        View v = inflater.inflate(R.layout.activity_search_result, null);
        return v;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(LOG_TAG, "ResultsFragment onActivityCreated");
    }

    public void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "ResultsFragment onStart");
    }

    public void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "ResultsFragment onResume");
    }

    public void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "ResultsFragment onPause");
    }

    public void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "ResultsFragment onStop");
    }

    public void onDestroyView() {
        super.onDestroyView();
        Log.d(LOG_TAG, "ResultsFragment onDestroyView");
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "ResultsFragment onDestroy");
    }

    public void onDetach() {
        super.onDetach();
        Log.d(LOG_TAG, "ResultsFragment onDetach");
    }
}
