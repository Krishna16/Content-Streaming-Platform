package com.example.moviestreamingnew.models;

public class Show {
    private String name;
    private double rating;
    private int episodes;
    private int likes;
    private String description;
    private String genre;

    public Show() {
    }

    public Show(String name, double rating, int episodes, int likes, String description) {
        this.name = name;
        this.rating = rating;
        this.episodes = episodes;
        this.likes = likes;
        this.description = description;
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
}
