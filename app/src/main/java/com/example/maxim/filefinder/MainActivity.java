package com.example.maxim.filefinder;

import android.support.annotation.IdRes;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.maxim.filefinder.fragments.ListFragment;
import com.example.maxim.filefinder.fragments.SearchFragment;
import com.example.maxim.filefinder.fragments.SettingsFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ListFragment.sendSearchFolders {

    final String LOG_TAG = "myLogs";
    public ArrayList<String> searchFolders;

    public void setSearchFolders(ArrayList<String> searchFolders) {
        this.searchFolders = searchFolders;
        for (int i = 0; i < searchFolders.size(); i++) {
            Log.d(LOG_TAG, "ACTIVITY " + searchFolders.get(i));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchFolders = new ArrayList<String>();

        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.listItem) {
                    Log.d(LOG_TAG, String.valueOf(R.id.listItem));
                    ListFragment listFragment = new ListFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.contentContainer, listFragment).commit();
                } else if (tabId == R.id.searchItem) {
                    Log.d(LOG_TAG, String.valueOf(R.id.searchItem));
                    SearchFragment searchFragment = new SearchFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.contentContainer, searchFragment).commit();
                } else if (tabId == R.id.settingsItem) {
                    Log.d(LOG_TAG, String.valueOf(R.id.searchItem));
                    SettingsFragment settingsFragment = new SettingsFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.contentContainer, settingsFragment).commit();
                } else {
                    Log.d(LOG_TAG,"MENU ERROR");
                }
                Log.d(LOG_TAG, String.valueOf(tabId));
            }
        });

    }
}
