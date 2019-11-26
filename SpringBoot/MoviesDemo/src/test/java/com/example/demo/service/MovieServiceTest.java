package com.example.demo.service;

import com.example.movies.model.ResponseSearch;
import com.example.movies.model.Search;
import org.junit.Test;

public class MovieServiceTest
{
    @Test
    public void testGetMovies()
    {
        System.out.println("Test Get Movies - OK");

        Search search1 = new Search();
        search1.setImdbID("ImdbID-1");
        search1.setPoster("Poster-1");
        search1.setTitle("Title-1");
        search1.setType("Title-2");
        search1.setYear("Title-3");

        Search search2 = new Search();
        search1.setImdbID("ImdbID-1");
        search1.setPoster("Poster-1");
        search1.setTitle("Title-1");
        search1.setType("Title-2");
        search1.setYear("Title-3");

        ResponseSearch responseSearch = new ResponseSearch();

    }
}