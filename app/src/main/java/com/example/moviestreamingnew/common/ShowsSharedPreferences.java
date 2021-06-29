package com.example.moviestreamingnew.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.moviestreamingnew.models.User;
import com.google.gson.Gson;

import java.lang.reflect.Array;
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
        editor.apply();
    }

    public User getUserData(String uid){
        Gson gson = new Gson();

        String userJson = sharedPreferences.getString(uid, "");
        User user = gson.fromJson(userJson, User.class);

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

    public boolean hasUserLiked(String uid, String show){
        Gson gson = new Gson();

        boolean hasLiked = false;

        String userJson = sharedPreferences.getString(uid, "");
        User user = gson.fromJson(userJson, User.class);

        ArrayList<String> likedShows = user.getLikedShows();

        for (int i = 0; i < likedShows.size(); i++){
            if (show.equals(likedShows.get(i))){
                hasLiked = true;
                break;
            }
        }

        return hasLiked;
    }

    public boolean hasUserWatchLater(String uid, String show){
        Gson gson = new Gson();

        boolean hasWatchLater = false;

        String userJson = sharedPreferences.getString(uid, "");
        User user = gson.fromJson(userJson, User.class);

        ArrayList<String> watchLater = user.getWatchLater();

        for (int i = 0; i < watchLater.size(); i++){
            if (show.equals(watchLater.get(i))){
                hasWatchLater = true;
                break;
            }
        }

        return hasWatchLater;
    }

    public void addToWatchLater(String uid, String show){
        Gson gson = new Gson();

        String userJson = sharedPreferences.getString(uid, "");
        User user = gson.fromJson(userJson, User.class);

        user.addToWatchLater(show);
        storeUserData(user);
    }

    public void addToLiked(String uid, String show){
        Gson gson = new Gson();

        String userJson = sharedPreferences.getString(uid, "");
        User user = gson.fromJson(userJson, User.class);

        user.addToLiked(show);
        storeUserData(user);
    }

    public void removeFromWatchLater(String uid, String show){
        Gson gson = new Gson();

        String userJson = sharedPreferences.getString(uid, "");
        User user = gson.fromJson(userJson, User.class);

        user.getWatchLater().remove(show);
        storeUserData(user);
    }

    public void removeFromLiked(String uid, String show){
        Gson gson = new Gson();

        String userJson = sharedPreferences.getString(uid, "");
        User user = gson.fromJson(userJson, User.class);

        user.getLikedShows().remove(show);
        storeUserData(user);
    }
}
