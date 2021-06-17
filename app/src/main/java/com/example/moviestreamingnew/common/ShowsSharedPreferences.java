package com.example.moviestreamingnew.common;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ShowsSharedPreferences {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;

    public ShowsSharedPreferences(Context context){
        this.context = context;
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
}
