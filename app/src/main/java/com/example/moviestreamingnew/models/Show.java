package com.example.moviestreamingnew.models;

import android.app.Application;

import java.util.ArrayList;

public class Show {
    private String name;
    private double rating;
    private int episodes;
    private int likes;
    private String description;
    private String genre;
    private String imageUrl;

    //singleton instance
    private static Show show = null;

    private String platforms;

    public Show() {
        this.name = "";
        this.rating = 0;
        this.episodes = 0;
        this.likes = 0;
        this.description = "";
        this.genre = "";
        this.imageUrl = "";
    }

    public Show(String name, double rating, int episodes, int likes, String description) {
        this.name = name;
        this.rating = rating;
        this.episodes = episodes;
        this.likes = likes;
        this.description = description;
    }

    public static synchronized Show getInstance(){
        if (show == null){
            show = new Show();
        }

        return show;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getEpisodes() {
        return episodes;
    }

    public void setEpisodes(int episodes) {
        this.episodes = episodes;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setGenre(String [] genres){
        for (int i = 0; i < genres.length; i++){
            this.genre += genres[i] + ", ";
        }
    }

    public String getPlatforms() {
        return platforms;
    }

    public void setPlatforms(String platforms) {
        this.platforms = platforms;
    }

    public void setPlatforms(String [] platforms){
        for (int i = 0; i < platforms.length; i++){
            this.platforms += platforms[i] + ", ";
        }
    }
}
