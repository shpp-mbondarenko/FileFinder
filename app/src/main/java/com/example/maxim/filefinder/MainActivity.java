package com.example.maxim.filefinder;

import android.support.annotation.IdRes;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

public class MainActivity extends AppCompatActivity {

    private String LOG = "log";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.listItem) {
                    // The tab with id R.id.tab_favorites was selected,
                    // change your content accordingly.
                    Toast.makeText(getApplicationContext(), "LIST", Toast.LENGTH_SHORT).show();
                    Log.d(LOG, String.valueOf(R.id.listItem));
                } else if (tabId == R.id.searchItem) {
                    Toast.makeText(getApplicationContext(), "SEARCH", Toast.LENGTH_SHORT).show();
                    Log.d(LOG, String.valueOf(R.id.searchItem));
                } else if (tabId == R.id.settingsItem) {
                    Toast.makeText(getApplicationContext(), "SETTINGS", Toast.LENGTH_SHORT).show();
                    Log.d(LOG, String.valueOf(R.id.searchItem));
                } else {
                    Log.d(LOG,"MENU ERROR");
                }
                Log.d(LOG, String.valueOf(tabId));
            }
        });

    }
}
