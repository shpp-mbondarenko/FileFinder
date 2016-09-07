package com.example.maxim.filefinder.fragments.listFragmentHelper;

/**
 * Created by Maxim on 04.09.2016.
 */
public class ItemFileExplorer {
    public String name;
    public int icon;
    public String description;
    public Boolean isCheckedCB;
    public Boolean isFolder;

    public ItemFileExplorer(String name, String description, int icon, Boolean isCheckedCB, Boolean isFolder) {
        this.description = description;
        this.name = name;
        this.icon = icon;
        this.isCheckedCB = isCheckedCB;
        this.isFolder = isFolder;
    }
}
