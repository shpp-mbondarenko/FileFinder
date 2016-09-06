package com.example.maxim.filefinder.fragments;

import android.app.Activity;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.maxim.filefinder.R;
import com.example.maxim.filefinder.fragments.listFragmentHelper.ItemFileExplorer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maxim on 04.09.2016.
 */
public class ListFragment extends Fragment implements RVAdapter.ClickListener{
    private RecyclerView rv;
    private List<ItemFileExplorer> items;
    final String LOG_TAG = "myLogs";

    private List<String> item = null;
    private List<String> path = null;
    private String root;
    private TextView fullPath;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d(LOG_TAG, "ListFragment onAttach");
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "ListFragment onCreate");
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(LOG_TAG, "ListFragment onCreateView");
        View v = inflater.inflate(R.layout.activity_list, null);
        //-----------------------------------------------
        rv = (RecyclerView) v.findViewById(R.id.rView);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);
        //-----------------------------------------------
        fullPath = (TextView) v.findViewById(R.id.fullPath);
        root = Environment.getExternalStorageDirectory().getPath();
        //-----------------------------------------------
        initializeData(root);
        initializeAdapter();

        return v;
    }

    private void initializeData(String dirPath){
        items = new ArrayList<>();

        fullPath.setText("Location: " + dirPath);
        item = new ArrayList<String>();
        path = new ArrayList<String>();
        File f = new File(dirPath);
        File[] files = f.listFiles();

        if(!dirPath.equals(root))
        {
            item.add(root);
            path.add(root);
            item.add("../");
            path.add(f.getParent());
        }

        for(int i=0; i < files.length; i++)
        {
            File file = files[i];

            if(!file.isHidden() && file.canRead()){
                path.add(file.getPath());
                if(file.isDirectory()){
                    items.add(new ItemFileExplorer(file.getName() + "/", "500bytes folder", R.drawable.ic_action_settings, false));
                } else {
                    items.add(new ItemFileExplorer(file.getName(), "500bytes file",R.drawable.ic_action_search, false));
                }
            }
        }
    }

    private void initializeAdapter(){
        RVAdapter adapter = new RVAdapter(items, getContext());
        adapter.setClickListener(this);
        rv.setAdapter(adapter);

    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(LOG_TAG, "ListFragment onActivityCreated");
    }

    public void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "ListFragment onStart");
    }

    public void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "ListFragment onResume");
    }

    public void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "ListFragment onPause");
    }

    public void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "ListFragment onStop");
    }

    public void onDestroyView() {
        super.onDestroyView();
        Log.d(LOG_TAG, "ListFragment onDestroyView");
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "ListFragment onDestroy");
    }

    public void onDetach() {
        super.onDetach();
        Log.d(LOG_TAG, "ListFragment onDetach");
    }

    @Override
    public void itemClick(View view, int position) {
        Log.d(LOG_TAG, position + "" + view.toString());
        Log.d(LOG_TAG, position + " Is cheked - " + view.findViewById(R.id.is_selected_cb).toString());
        RecyclerView.ViewHolder b = (RVAdapter.ViewHolder)rv.findViewHolderForAdapterPosition(position);
        Log.d(LOG_TAG, position + " ");

    }
}
