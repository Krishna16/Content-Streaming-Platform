package com.example.moviestreamingnew.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;

    private List<String> genres;

    private String preference;

    private String gender;

    private String industry;

    private String uid;

    private ArrayList<String> likedShows;
    private ArrayList<String> watchLaterShows;

    private ArrayList<String> likedMovies;
    private ArrayList<String> watchLaterMovies;

    //singleton instance
    private static User user = null;

    public User() {
        this.name = "";
        this.genres = new ArrayList<>();
        this.preference = "";
        this.gender = "";
        this.industry = "";

        this.likedShows = new ArrayList<>();
        this.genres = new ArrayList<>();
        this.watchLaterShows = new ArrayList<>();

        this.watchLaterMovies = new ArrayList<>();
        this.likedMovies = new ArrayList<>();
    }

    public User(String name, List<String> genres, String preference, String gender, String industry, ArrayList<String> likedShows) {
        this.name = name;
        this.genres = genres;
        this.preference = preference;
        this.gender = gender;
        this.industry = industry;

        this.likedShows = likedShows;
        this.genres = new ArrayList<>();
        this.watchLaterShows = new ArrayList<>();
    }

    public static synchronized User getInstance(){
        if (user == null){
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

    public ArrayList<String> getLikedShows() {
        return likedShows;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public ArrayList<String> getWatchLaterShows() {
        return watchLaterShows;
    }

    public void setWatchLaterShows(ArrayList<String> watchLaterShows) {
        this.watchLaterShows = watchLaterShows;
    }

    public void addToWatchLaterShows(String show){
        this.watchLaterShows.add(show);
    }

    public void addToLikedShows(String show) {
        this.likedShows.add(show);
    }

    public void setLikedMovies(ArrayList<String> likedShows) {
        this.likedShows = likedShows;
    }

    public ArrayList<String> getLikedMovies() {
        return likedMovies;
    }

    public ArrayList<String> getWatchLaterMovies() {
        return watchLaterMovies;
    }

    public void setWatchLaterMovies(ArrayList<String> watchLaterMovies) {
        this.watchLaterMovies = watchLaterMovies;
    }

    public void addToWatchLaterMovies(String movie){
        this.watchLaterMovies.add(movie);
    }

    public void addToLikedMovies(String movie){
        this.likedMovies.add(movie);
    }
}
