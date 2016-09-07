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
        root = Environment.getRootDirectory().getPath();
        //-----------------------------------------------
        initializeData("/");
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
        //adding "UP" element
        items.add(0, new ItemFileExplorer(dirPath, "UP folder", R.drawable.ic_content_reply, false, true));
        if(!dirPath.equals(root))
        {
            item.add(root);
            path.add(root);
            item.add("../");
            path.add(f.getParent());
        }

        for(int i = 0; i < files.length; i++)
        {
            File file = files[i];

            if(!file.isHidden() && file.canRead()){
                path.add(file.getPath());
                if(file.isDirectory()){
                    items.add(1, new ItemFileExplorer(file.getName() + "/", "500bytes folder", R.drawable.ic_file_folder, false, true));
                } else {
                    items.add(new ItemFileExplorer(file.getName(), "500bytes file",R.drawable.ic_action_description, false, false));
                }
            }
        }
        Log.d(LOG_TAG, "ITEMS - " + items.size());


    }

    private void initializeAdapter(){
        RVAdapter adapter = new RVAdapter(items, getContext());
        adapter.setClickListener(this);
        rv.setAdapter(adapter);
    }

    @Override
    public void itemClick(View view, int position) {
        if (items.get(position).isFolder){
            if (position == 0 && !root.equals("/")) {
                if (!root.equals("/system")){
                    Log.d(LOG_TAG, "ROOT2 " + root + " " + root.length());
                    Log.d(LOG_TAG, "FOLDER2 " + items.get(position).name );
                    root = root.substring(0, root.length()-1);
                    while (root.charAt(root.length()-1) != "/".charAt(0)){
                        root = root.substring(0, root.length()-1);
                    }
                    items.clear();
                    Log.d(LOG_TAG, "ROOT AFTER " + root + " " + root.length());
                    initializeData(root);
                    rv.removeAllViews();
                    RVAdapter adapter = new RVAdapter(items, getContext());
                    adapter.setClickListener(this);
                    rv.setAdapter(adapter);
                }
            } else {
                Log.d(LOG_TAG, "ROOT " + root + " " + root.length());
                Log.d(LOG_TAG, "FOLDER " + items.get(position).name );
                if (root.equals("/system")){
                    root = "/"+items.get(position).name;
                } else {
                    root += items.get(position).name;

                }
                items.clear();
                Log.d(LOG_TAG, "ROOT AFTER " + root + " " + root.length());
                initializeData(root);
                rv.removeAllViews();
                RVAdapter adapter = new RVAdapter(items, getContext());
                adapter.setClickListener(this);
                rv.setAdapter(adapter);
            }
        } else {
            Log.d(LOG_TAG, "JUST FILE " + items.get(position).name);
        }
    }



}
