package com.example.moviestreamingnew.models;

import java.util.ArrayList;

public class User {
    private String name;
    private ArrayList<String> genres;
    private String preference;
    private String gender;
    private String industry;
    private boolean formFilled;
    private ArrayList<String> likedMovies;

    //singleton instance
    private static User user = null;

    public User() {
    }

    public User(String name, ArrayList<String> genres, String preference, String gender, String industry, boolean formFilled, ArrayList<String> likedMovies) {
        this.name = name;
        this.genres = genres;
        this.preference = preference;
        this.gender = gender;
        this.industry = industry;
        this.formFilled = formFilled;
        this.likedMovies = likedMovies;
    }

    public static synchronized User getInstance(){
        if (null == user){
            user = new User();
        }

        return user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    public void addGenre(String genre){
        this.genres.add(genre);
    }

    //movie theatre vs ott platform
    public String getPreference() {
        return preference;
    }

    public void setPreference(String preference) {
        this.preference = preference;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public boolean isFormFilled() {
        return formFilled;
    }

    public void setFormFilled(boolean formFilled) {
        this.formFilled = formFilled;
    }

    public ArrayList<String> getLikedMovies() {
        return likedMovies;
    }

    public void setLikedMovies(ArrayList<String> likedMovies) {
        this.likedMovies = likedMovies;
    }
}
