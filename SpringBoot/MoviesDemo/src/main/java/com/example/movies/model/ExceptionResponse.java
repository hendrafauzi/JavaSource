package com.example.movies.model;

public class ExceptionResponse
{
    private String status;
    private String message;

    public ExceptionResponse(String status, String message)
    {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
