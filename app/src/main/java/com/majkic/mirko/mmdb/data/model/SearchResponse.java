package com.majkic.mirko.mmdb.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResponse {

    @SerializedName("total_pages")
    int totalPages;
    @SerializedName("total_results")
    int totalResults;
    @SerializedName("page")
    int page;
    @SerializedName("results")
    List<Movie> results;

    public SearchResponse(int totalPages, int totalResults, int page, List<Movie> results) {
        this.totalPages = totalPages;
        this.totalResults = totalResults;
        this.page = page;
        this.results = results;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }
}