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
    private final String QUANTITY_OF_FILES = "quantity";
    private final String ARRAY = "array";
    private final String ROOT = "root/";
    final String LOG_TAG = "myLogs";
    private ListView listView;
    private ArrayList<String> folders;
    private Button btnSearch;
    private Button btnAddFolders;
    private EditText etFilesQuantity;
    public ArrayList<String> searchFolders;
    ExplorerDialogFragment dialogFragment;

    static boolean active = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        folders = new ArrayList<>();
        searchFolders = new ArrayList<>();
        folders.add(ROOT);
        listView = (ListView) findViewById(R.id.lv_search_folders);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.row_layout, R.id.tv_list_item, folders);
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
                int num = 1;
                try {
                    num = Integer.parseInt(etFilesQuantity.getText().toString());
                } catch(NumberFormatException nfe) {
                    Log.d(LOG_TAG, "Could not parse" + nfe);
                }
                Bundle bundle = new Bundle();
                bundle.putSerializable(ARRAY, folders);
                Intent i = new Intent(getApplicationContext(), SearchService.class);
                i.putExtra(QUANTITY_OF_FILES, num);
                i.putExtra(ARRAY, bundle);
                startService(i);
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void setSearchFolders(ArrayList<String> searchFolders) {
        this.searchFolders = searchFolders;
        if (searchFolders.size() == 0) {
        } else {
            folders = searchFolders;
        }
        for (int i = 0; i < folders.size(); i++){
            Log.d(LOG_TAG,"folders - " + folders.get(i));
            searchFolders.set(i, folders.get(i).substring(1));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.row_layout, R.id.tv_list_item, searchFolders);
        listView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        active = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        active = false;
    }
}
