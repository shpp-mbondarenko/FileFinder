package com.example.maxim.filefinder.fragments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.maxim.filefinder.R;
import com.example.maxim.filefinder.fragments.listFragmentHelper.ItemFileExplorer;

import java.util.List;

/**
 * Created by Maxim on 04.09.2016.
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder> {

    final String LOG_TAG = "myLogs";

    Context context;
    List<ItemFileExplorer> itemFileExplorerList;
    private ClickListener clickListener;


    RVAdapter(List<ItemFileExplorer> items, Context context){
        this.itemFileExplorerList = items;
        this.context = context;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.explorer_item_layout, viewGroup, false);
        ViewHolder vh = new ViewHolder(v, context);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder itemViewHolder, int i) {
        itemViewHolder.ivFileIcon.setImageResource(itemFileExplorerList.get(i).icon);
        itemViewHolder.tvName.setText(itemFileExplorerList.get(i).name);
        itemViewHolder.tvDescription.setText(itemFileExplorerList.get(i).description);
        itemViewHolder.cbSelected.setChecked(itemFileExplorerList.get(i).isCheckedCB);
    }

    @Override
    public int getItemCount() {
        return itemFileExplorerList.size();
    }

    public CheckBox getCheckBox(int pos) {
        return null;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvName;
        public TextView  tvDescription;
        public ImageView ivFileIcon;
        public CheckBox cbSelected;
        private Context context;


        public ViewHolder(View itemView, Context context) {
            super(itemView);
            this.context  = context;
            itemView.setOnClickListener(this);
            tvName = (TextView)itemView.findViewById(R.id.item_name);
            tvDescription = (TextView)itemView.findViewById(R.id.item_description);
            ivFileIcon = (ImageView)itemView.findViewById(R.id.file_image);
            cbSelected = (CheckBox)itemView.findViewById(R.id.is_selected_cb);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null)
                clickListener.itemClick(v, getPosition());
        }

    }


    public void setClickListener(ClickListener clickListener){
        this.clickListener = clickListener;
    }

    public  interface ClickListener{
        public void itemClick(View view, int position);
    }
}