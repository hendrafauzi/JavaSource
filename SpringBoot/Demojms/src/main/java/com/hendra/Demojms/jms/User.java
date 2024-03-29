package com.hendra.Demojms.jms;

public class User
{
    private String email;
    private Double rating;
    private boolean active;

    public User()
    {
        //Do Nothing
    }

    public User(String email, Double rating, boolean active)
    {
        this.email = email;
        this.rating = rating;
        this.active = active;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public Double getRating()
    {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString()
    {
        return "User{" +
                "email='" + email + '\'' +
                ", rating=" + rating +
                ", active=" + active +
                '}';
    }
}