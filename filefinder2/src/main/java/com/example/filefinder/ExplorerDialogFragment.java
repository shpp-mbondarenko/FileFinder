package com.example.filefinder;

import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maxim on 18.09.2016.
 */
public class ExplorerDialogFragment extends DialogFragment implements View.OnClickListener, RVAdapter.ClickListener {

    private RecyclerView rv;
    private List<ItemFileExplorer> items;
    final String LOG_TAG = "myLogs";
    private List<String> item = null;
    private List<String> path = null;
    private String root;
    boolean[] checked;
    ArrayList<String> searchFolders;
    sendSearchFolders folders;
    Context context;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchFolders = new ArrayList<String>();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = inflater.getContext();
        folders = (sendSearchFolders) getActivity();
        View v = inflater.inflate(R.layout.activity_fragment_dialog, null);
        v.findViewById(R.id.btnYes).setOnClickListener(this);
        //-----------------------------------------------
        rv = (RecyclerView) v.findViewById(R.id.rView);
        LinearLayoutManager llm = new LinearLayoutManager(context);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);
        //-----------------------------------------------
        root = Environment.getRootDirectory().getPath();
        //-----------------------------------------------
        initializeData("/");
        initializeAdapter();
        return v;
    }
    private void initializeData(String dirPath){
        items = new ArrayList<>();

        getDialog().setTitle("Location: " + dirPath);
        item = new ArrayList<String>();
        path = new ArrayList<String>();
        File f = new File(dirPath);
        File[] files = f.listFiles();
        //adding "UP" element
        items.add(0, new ItemFileExplorer(dirPath, "UP folder", R.drawable.ic_content_reply, false, true));

        for(int i = 0; i < files.length; i++) {
            File file = files[i];
            if(!file.isHidden() && file.canRead()){
                path.add(file.getPath());
                if(file.isDirectory()){
                    items.add(1, new ItemFileExplorer(file.getName() + "/", "Folder", R.drawable.ic_file_folder, false, true));
                } else {
                    items.add(new ItemFileExplorer(file.getName(), file.length() + " bytes", R.drawable.ic_action_description, false, false));
                }
            }
        }
    }

    private void initializeAdapter(){
        RVAdapter adapter = new RVAdapter(items, context);
        adapter.setClickListener(this);
        rv.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        checked = RVAdapter.getChecked();
        for (int i = 1; i < checked.length; i++) {
            boolean b = checked[i];
            if (b) {
                if (root.charAt(root.length()-1) == "/".charAt(0)){
                    searchFolders.add(root + items.get(i).name);
                } else {
                    searchFolders.add("/" + items.get(i).name);
                }
            }
        }
        for (String t : searchFolders){
            Log.d(LOG_TAG, t );
        }
        folders.setSearchFolders(searchFolders);
        dismiss();
    }

    @Override
    public void itemClick(View view, int position) {
        if (items.get(position).isFolder){
            if (position == 0 && !root.equals("/")) {
                if (!root.equals("/system")){
                    root = root.substring(0, root.length()-1);
                    while (root.charAt(root.length()-1) != "/".charAt(0)){
                        root = root.substring(0, root.length()-1);
                    }
                    items.clear();
                    initializeData(root);
                    rv.removeAllViews();
                    initializeAdapter();
                }
            } else {
                if (root.equals("/system")){
                    root = "/"+items.get(position).name;
                } else {
                    root += items.get(position).name;
                }
                items.clear();
                initializeData(root);
                rv.removeAllViews();
                initializeAdapter();
            }
        } else {
            Log.d(LOG_TAG, "JUST FILE " + items.get(position).name);
        }
    }

    public interface sendSearchFolders {
        void setSearchFolders(ArrayList<String> searchFolders);
    }
}
