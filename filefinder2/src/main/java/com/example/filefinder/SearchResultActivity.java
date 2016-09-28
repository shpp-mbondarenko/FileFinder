package com.example.filefinder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maxim on 20.09.2016.
 */
public class SearchResultActivity extends AppCompatActivity {

    private final String FILE_SIZES = "fileSizes";
    private final String FILE_PATH = "filePath";
    private final String ARRAY = "array";
    private final String LOG_TAG = "myLogs";

    private long[] fileSizes;
    private String[] resultOfSearchPath;
    List<ResultItem> items;
    MAdapter adapter;

    private RecyclerView rv;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(ARRAY);
        fileSizes = bundle.getLongArray(FILE_SIZES);
        resultOfSearchPath = bundle.getStringArray(FILE_PATH);
        items = new ArrayList<>();
        for (int i = 0; i < fileSizes.length; i++) {
            Log.d(LOG_TAG, "Size - " + fileSizes[i]);
            Log.d(LOG_TAG, "Name - " + resultOfSearchPath[i]);
            items.add(new ResultItem(getFileName("Name - " + resultOfSearchPath[i]), "<b> Path - </b>" + resultOfSearchPath[i], "Size - " + Long.toString(fileSizes[i]) + " bytes"));
        }

        rv = (RecyclerView) findViewById(R.id.rv_result);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);
        initializeAdapter();
    }

    private String getFileName(String s) {
        String res = null;
        int len = s.length();
        Log.d(LOG_TAG, "CHAR" + (int)s.charAt(6) );

        for (int i = 0; i < s.length(); i++) {
            if ((int) s.charAt(i) == (47)) {
                len = i;
            }
        }
        return s.substring(++len);
    }

    private void initializeAdapter(){
        adapter = new MAdapter(items, getApplicationContext());
        rv.setAdapter(adapter);
    }
    public class ResultItem {
        public String name;
        public String filePath;
        public String fileSize;

        public ResultItem(String name, String filePath, String fileSize) {
            this.name = name;
            this.filePath = filePath;
            this.fileSize = fileSize;
        }
    }

    public class MAdapter extends RecyclerView.Adapter<MAdapter.ViewHolder> {

        Context context;
        List<ResultItem> resultItems;

        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView tvName;
            public TextView tvPath;
            public TextView tvSize;

            public ViewHolder(View itemView, Context context) {
                super(itemView);
                tvName = (TextView) itemView.findViewById(R.id.tv_result_file_name);
                tvPath = (TextView) itemView.findViewById(R.id.tv_result_file_path);
                tvSize = (TextView) itemView.findViewById(R.id.tv_result_file_size);
            }

        }

        public MAdapter(List<ResultItem> items, Context context){
            this.resultItems = items;
            this.context = context;
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.result_item_layout, viewGroup, false);
            ViewHolder vh = new ViewHolder(v, context);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.tvName.setText(resultItems.get(position).name);
            holder.tvPath.setText(Html.fromHtml(resultItems.get(position).filePath));
            holder.tvSize.setText(resultItems.get(position).fileSize);
        }

        @Override
        public int getItemCount() {
            return resultItems.size();
        }

    }
}
