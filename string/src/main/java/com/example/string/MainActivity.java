package com.example.string;

import android.app.Activity;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String i = "gg";
        char[] arr = {'k','r','h'};
        SString t = new SString(arr);
        Log.d("TAG", t+"");
        Log.d("TAG", t.length()+"");
//        Log.d("TAG", arr[0]);
        System.out.print(arr[0]+" BLA");

    }
}
