package com.majkic.mirko.mmdb.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Search {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    int id;
    @ColumnInfo(name = "search_term")
    String searchTerm;
    @ColumnInfo(name = "search_time")
    long searchTime;

    public Search(String searchTerm, long searchTime) {
        this.searchTerm = searchTerm;
        this.searchTime = searchTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public long getSearchTime() {
        return searchTime;
    }

    public void setSearchTime(long searchTime) {
        this.searchTime = searchTime;
    }
}
