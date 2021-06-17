package com.example.moviestreamingnew.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class User {
    private String name;
    private List genres;
    private String preference;
    private String gender;
    private String industry;

    private ArrayList<String> likedMovies;

    //singleton instance
    private static User user = null;

    public User() {
    }

    public User(String name, List genres, String preference, String gender, String industry, ArrayList<String> likedMovies) {
        this.name = name;
        this.genres = genres;
        this.preference = preference;
        this.gender = gender;
        this.industry = industry;

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

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List genres) {
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

    public ArrayList<String> getLikedMovies() {
        return likedMovies;
    }

    public void setLikedMovies(ArrayList<String> likedMovies) {
        this.likedMovies = likedMovies;
    }
}
