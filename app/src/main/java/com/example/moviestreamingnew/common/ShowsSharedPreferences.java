package com.example.moviestreamingnew.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.moviestreamingnew.models.User;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ShowsSharedPreferences {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;

    public ShowsSharedPreferences(Context context){
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences("showsPreferences", Context.MODE_PRIVATE);
    }

    public void storeSelectedGenres(List<String> genres){
        this.editor = context.getSharedPreferences("showsPreferences", Context.MODE_PRIVATE).edit();

        editor.putString("genre1", genres.get(0));
        editor.putString("genre2", genres.get(1));
        editor.putString("genre3", genres.get(2));

        editor.apply();
    }

    public ArrayList<String> getStoredGenres(){
        ArrayList<String> temp = new ArrayList<>();

        this.sharedPreferences = context.getSharedPreferences("showsPreferences", Context.MODE_PRIVATE);

        temp.add(sharedPreferences.getString("genre1", null));
        temp.add(sharedPreferences.getString("genre2", null));
        temp.add(sharedPreferences.getString("genre3", null));

        return temp;
    }

    public void storeUserData(User user){
        this.editor = context.getSharedPreferences("showsPreferences", Context.MODE_PRIVATE).edit();

        Gson gson = new Gson();
        String userJson = gson.toJson(user);

        editor.putString(user.getUid(), userJson);
        Log.d("ShowsSharedPreferences", "User ID: " + user.getUid());
        editor.apply();
    }

    public User getUserData(String uid){
        Gson gson = new Gson();

        String userJson = sharedPreferences.getString(uid, "");
        User user = gson.fromJson(userJson, User.class);

        //Log.d("ShowsSharedPreferences", "User ID: " + user.getUid());

        if (user != null){
            return user;
        }

        else{
            return null;
        }
    }

    public ArrayList<String> getUserLikedShows(String uid){
        Gson gson = new Gson();

        String userJson = sharedPreferences.getString(uid, "");
        User user = gson.fromJson(userJson, User.class);

        ArrayList<String> likedShows = user.getLikedShows();

        return likedShows;
    }

    public boolean hasUserLikedShow(String uid, String show){
        Gson gson = new Gson();

        boolean hasLiked = false;

        String userJson = sharedPreferences.getString(uid, "");
        User user = gson.fromJson(userJson, User.class);

        if (user == null){
            return false;
        }

        ArrayList<String> likedShows = user.getLikedShows();
        if (likedShows == null){
            return false;
        }

        for (int i = 0; i < likedShows.size(); i++){
            if (show.equals(likedShows.get(i))){
                hasLiked = true;
                break;
            }
        }

        return hasLiked;
    }

    public boolean hasUserWatchLaterShow(String uid, String show){
        Gson gson = new Gson();

        boolean hasWatchLater = false;

        String userJson = sharedPreferences.getString(uid, "");
        User user = gson.fromJson(userJson, User.class);

        if (user == null){
            return false;
        }

        ArrayList<String> watchLater = user.getWatchLaterShows();
        if (watchLater == null){
            return false;
        }

        for (int i = 0; i < watchLater.size(); i++){
            if (show.equals(watchLater.get(i))){
                hasWatchLater = true;
                break;
            }
        }

        return hasWatchLater;
    }

    public void addToWatchLaterShow(String uid, String show){
        Gson gson = new Gson();

        String userJson = sharedPreferences.getString(uid, "");
        User user = gson.fromJson(userJson, User.class);

        user.addToWatchLaterShows(show);
        storeUserData(user);
    }

    public void addToLikedShow(String uid, String show){
        Gson gson = new Gson();

        String userJson = sharedPreferences.getString(uid, "");
        User user = gson.fromJson(userJson, User.class);

        user.addToLikedShows(show);
        storeUserData(user);
    }

    public void removeFromWatchLaterShow(String uid, String show){
        Gson gson = new Gson();

        String userJson = sharedPreferences.getString(uid, "");
        User user = gson.fromJson(userJson, User.class);

        user.getWatchLaterShows().remove(show);
        storeUserData(user);
    }

    public void removeFromLikedShow(String uid, String show){
        Gson gson = new Gson();

        String userJson = sharedPreferences.getString(uid, "");
        User user = gson.fromJson(userJson, User.class);

        user.getLikedShows().remove(show);
        storeUserData(user);
    }

    public void addToWatchLaterMovie(String uid, String movie){
        Gson gson = new Gson();

        String userJson = sharedPreferences.getString(uid, "");
        User user = gson.fromJson(userJson, User.class);

        user.addToWatchLaterMovies(movie);
        storeUserData(user);
    }

    public void addToLikedMovie(String uid, String movie){
        Gson gson = new Gson();

        String userJson = sharedPreferences.getString(uid, "");
        User user = gson.fromJson(userJson, User.class);

        user.addToLikedMovies(movie);
        storeUserData(user);
    }

    public void removeFromWatchLaterMovie(String uid, String movie){
        Gson gson = new Gson();

        String userJson = sharedPreferences.getString(uid, "");
        User user = gson.fromJson(userJson, User.class);

        user.getWatchLaterMovies().remove(movie);
        storeUserData(user);
    }

    public void removeFromLikedMovie(String uid, String movie){
        Gson gson = new Gson();

        String userJson = sharedPreferences.getString(uid, "");
        User user = gson.fromJson(userJson, User.class);

        user.getLikedMovies().remove(movie);
        storeUserData(user);
    }

    public boolean hasUserLikedMovie(String uid, String movie){
        Gson gson = new Gson();

        boolean hasLiked = false;

        String userJson = sharedPreferences.getString(uid, "");
        User user = gson.fromJson(userJson, User.class);

        ArrayList<String> likedMovies = user.getLikedMovies();
        if(likedMovies == null){
            return false;
        }

        for (int i = 0; i < likedMovies.size(); i++){
            if (movie.equals(likedMovies.get(i))){
                hasLiked = true;
                break;
            }
        }

        return hasLiked;
    }

    public boolean hasUserWatchLaterMovie(String uid, String movie){
        Gson gson = new Gson();

        boolean hasWatchLater = false;

        String userJson = sharedPreferences.getString(uid, "");
        User user = gson.fromJson(userJson, User.class);

        ArrayList<String> watchLaterMovies = user.getWatchLaterMovies();
        if(watchLaterMovies == null){
            return false;
        }

        for (int i = 0; i < watchLaterMovies.size(); i++){
            if (movie.equals(watchLaterMovies.get(i))){
                hasWatchLater = true;
                break;
            }
        }

        return hasWatchLater;
    }
}
