package com.example.movies.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Transaction
{
    @SerializedName("Search")
    private List<Search> search;

    public List<Search> getSearch()
    {
        return search;
    }

    public void setSearch(List<Search> search)
    {
        this.search = search;
    }
}
