package com.example.moviestreamingnew.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Movie implements Serializable {
    private String movie_id;
    private String backdrop_path;
    private String title;
    private String description;
    private String poster_path;
    private String rating;
    private ArrayList<String> watchProviders;

    public static Movie movie;

    public Movie() {
    }

    public Movie(String movie_id, String backdrop_path, String title, String description, String poster_path, String rating) {
        this.movie_id = movie_id;
        this.backdrop_path = backdrop_path;
        this.title = title;
        this.description = description;
        this.poster_path = poster_path;
        this.rating = rating;
    }

    public static synchronized Movie getInstance(){
        if (movie == null){
            movie = new Movie();
        }

        return movie;
    }

    public String getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(String movie_id) {
        this.movie_id = movie_id;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public ArrayList<String> getWatchProviders() {
        return watchProviders;
    }

    public void setWatchProviders(ArrayList<String> watchProviders) {
        this.watchProviders = watchProviders;
    }
}
