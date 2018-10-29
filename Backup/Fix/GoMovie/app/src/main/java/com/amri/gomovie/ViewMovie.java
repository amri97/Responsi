package com.amri.gomovie;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ViewMovie {

    @SerializedName("page")
    private int page;
    @SerializedName("results")
    private List<ItemsMovie> results;
    @SerializedName("total_results")
    private int totalResults;
    @SerializedName("total_pages")
    private int totalPages;

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<ItemsMovie> getResults() {
        return results;
    }

    public void setResults(List<ItemsMovie> results) {
        this.results = results;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }


}