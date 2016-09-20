package com.example.maxim.filefinder.fragments;

/**
 * Created by Maxim on 10.09.2016.
 */
public class SearchItem {

    private String fullPath;
    private long size;

    public SearchItem(String fullPath, long size) {
        this.fullPath = fullPath;
        this.size = size;
    }

    public String getFullPath() {
        return fullPath;
    }

    public long getSize() {
        return size;
    }


}
