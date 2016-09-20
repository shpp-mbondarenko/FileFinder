package com.example.filefinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener, ExplorerDialogFragment.sendSearchFolders{
    private static final String ARRAY = "array";
    final String LOG_TAG = "myLogs";
    private ListView listView;
    private ArrayList<String> folders;
    private Button btnSearch;
    private Button btnAddFolders;
    private EditText etFilesQuantity;
    public ArrayList<String> searchFolders;
    ExplorerDialogFragment dialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(LOG_TAG,"OnCreate");
        folders = new ArrayList<String>();
        searchFolders = new ArrayList<String>();
        folders.add("/");
        listView = (ListView) findViewById(R.id.lv_search_folders);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1, android.R.id.text1, folders);
        listView.setAdapter(adapter);

        btnSearch = (Button) findViewById(R.id.btn_start_search);
        btnAddFolders = (Button) findViewById(R.id.btn_add_folders);
        etFilesQuantity = (EditText) findViewById(R.id.et_files_quantity);

        dialogFragment = new ExplorerDialogFragment();
        btnAddFolders.setOnClickListener(this);
        btnSearch.setOnClickListener(this);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_folders:
                dialogFragment.show(getFragmentManager(), "exp");
                break;
            case R.id.btn_start_search:
                Bundle bundle = new Bundle();
                bundle.putSerializable(ARRAY, folders);
                Intent i = new Intent(getApplicationContext(), SearchService.class);
                i.putExtra(ARRAY, bundle);
                startService(i);
                break;
            default:
                break;
        }
    }

    @Override
    public void setSearchFolders(ArrayList<String> searchFolders) {
        Log.d(LOG_TAG,"etSearchFolders");
        this.searchFolders = searchFolders;
        if (folders.size() != 0) {
            folders = searchFolders;
        }
//        else {
//            folders.add("/");
//        }
        for (int i =0; i < folders.size(); i++){
            Log.d(LOG_TAG,"folders - " + folders.get(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1, android.R.id.text1, folders);
        listView.setAdapter(adapter);
    }
}
