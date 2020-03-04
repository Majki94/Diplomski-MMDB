package com.majkic.mirko.mmdb.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Search {

    @PrimaryKey
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
