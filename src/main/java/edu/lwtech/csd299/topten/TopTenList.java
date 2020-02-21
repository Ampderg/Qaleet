package edu.lwtech.csd299.topten;

import java.util.*;

public class TopTenList {
    
    private int id;                 // Database ID (or -1 if it isn't in the database yet)
    private String title;           // Title of the Top Ten List
    private List<String> items;     // The 10 items that make up the list
    
    public TopTenList(String title, List<String> items) {
        this(-1, title, items);
    }
    
    public TopTenList(int id, String title, List<String> items) {

        if (id < -1 || title == null || title == "" || items == null) {
            throw new IllegalArgumentException("Invalid arguments for new TopTenList");
        }

        this.id = id;
        this.title = title;
        this.items = items;
    }
    
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
    
    public List<String> getItems() {
        return items;
    }
    
    @Override
    public String toString() {
        String s = id + ": " + title + " \n";
        if (items != null) {
            for (String item : items) {
                s += item + "\n";
            }
        }
        return s;
    }

}
