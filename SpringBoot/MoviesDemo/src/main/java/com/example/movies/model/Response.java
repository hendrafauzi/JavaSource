package com.example.movies.model;

import java.util.List;

public class Response
{
    private String status;
    private String message;
    private List<ResponseSearch> movies;

    public Response()
    {
        //Do Nothing
    }

    public Response(String status, String message)
    {
        this.status = status;
        this.message = message;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public List<ResponseSearch> getMovies()
    {
        return movies;
    }

    public void setMovies(List<ResponseSearch> movies)
    {
        this.movies = movies;
    }
}
